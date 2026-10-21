package net.jukoz.me.entity.beasts;

import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.item.items.TrollArmorItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.EntityGetter;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import java.util.UUID;
import java.util.function.Predicate;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;

// Beasts are mostly aggressive Entities which work much like wolves, while also allowing the player to mount them.
public class BeastEntity extends AbstractChestedHorse {
    public static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.registerData(BeastEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.registerData(BeastEntity.class, EntityDataSerializers.BOOLEAN);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState chargeAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();

    private int idleAnimationTimeout = 0;
    private int attackTicksLeft = 0;

    protected int chargeTimeout;

    public static final int ATTACK_COOLDOWN = 10;
    public static final float RESISTANCE = 0.15f;
    protected Vec3 targetDir = Vec3.ZERO;
    private static final UUID BEAST_ARMOR_BONUS_ID = UUID.fromString("667E1665-8B10-40C8-8F9D-CF9B1667F295");

    // Initializing ====================================================================================================
    protected BeastEntity(EntityType<? extends AbstractChestedHorse> entityType, Level world) {
        super(entityType, world);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.entityData.define(CHARGING, false);
        this.entityData.define(SITTING, false);
    }

    @Override
    protected void initAttributes(RandomSource random) {

    }

    protected void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
        if(this.isSitting()) {
            this.sittingAnimationState.startIfNotRunning(this.age);
        }
        else {
            this.sittingAnimationState.stop();
        }
    }

    @Override
    public void onTrackedDataSet(EntityDataAccessor<?> data) {
        if (!this.firstUpdate && CHARGING.equals(data)) {
            this.chargeTimeout = this.chargeTimeout == 0 ? maxChargeCooldown() : this.chargeTimeout;
        }
        super.onTrackedDataSet(data);
    }

    private SlotAccess createInventoryStackReference(final int slot, final Predicate<ItemStack> predicate) {
        return new SlotAccess(){

            @Override
            public ItemStack get() {
                return BeastEntity.this.items.getItem(slot);
            }

            @Override
            public boolean set(ItemStack stack) {
                if (!predicate.test(stack)) {
                    return false;
                }
                BeastEntity.this.items.setStack(slot, stack);
                BeastEntity.this.updateSaddle();
                return true;
            }
        };
    }

    @Override
    public void writeCustomDataToNbt(CompoundTag nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("ChestedBeast", this.hasChest());
        if (this.hasChest()) {
            ListTag nbtList = new ListTag();
            for(int i = 2; i < this.items.size(); ++i) {
                ItemStack itemStack = this.items.getItem(i);
                if (!itemStack.isEmpty()) {
                    CompoundTag nbtCompound = new CompoundTag();
                    nbtCompound.putByte("Slot", (byte)i);
                    itemStack.writeNbt(nbtCompound);
                    nbtList.add(nbtCompound);
                }
            }
            nbt.put("Items", nbtList);
        }
        if (!this.items.getItem(1).isEmpty()) {
            nbt.put("ArmorItem", this.items.getItem(1).writeNbt(new CompoundTag()));
        }
    }

    @Override
    public void readCustomDataFromNbt(CompoundTag nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setHasChest(nbt.getBoolean("ChestedBeast"));
        this.createInventory();
        if (this.hasChest()) {
            ListTag nbtList = nbt.getList("Items", 10);

            for(int i = 0; i < nbtList.size(); ++i) {
                CompoundTag nbtCompound = nbtList.getCompound(i);
                int j = nbtCompound.getByte("Slot") & 255;
                if (j >= 2 && j < this.items.size()) {
                    this.items.setStack(j, ItemStack.fromNbt(nbtCompound));
                }
            }
        }
        if (nbt.contains("ArmorItem", 10)) {
            ItemStack itemStack = ItemStack.fromNbt(nbt.getCompound("ArmorItem"));
            if (!itemStack.isEmpty() && this.isHorseArmor(itemStack)) {
                this.items.setStack(1, itemStack);
            }
        }
        this.updateSaddle();
    }

    // Getters and Setters =============================================================================================

    public boolean canCharge() {
        return !this.isSitting();
    }
    @Override
    protected float getJumpVelocity() {
        return 0.5f * this.getBlockJumpFactor() + this.getJumpBoostPower();
    }

    @Override
    public int getJumpCooldown() {
        return this.chargeTimeout;
    }

    public double getMountedHeightOffset() {
        float f = Math.min(0.25F, this.limbAnimator.getSpeed());
        float g = this.limbAnimator.getPos();
        return (double)this.getHeight() - 0.19 + (double)(0.12F * Mth.cos(g * 1.5F) * 2.0F * f);
    }

    @Override
    protected Vec3 getControlledMovementInput(Player controllingPlayer, Vec3 movementInput) {
        if (this.isOnGround() && this.jumpStrength == 0.0f && this.isAngry() && !this.jumping || this.isSitting()) {
            return Vec3.ZERO;
        }
        float f = controllingPlayer.sidewaysSpeed * 0.5f;
        float g = controllingPlayer.forwardSpeed * 0.75f;
        if (g <= 0.0f) {
            g *= 0.25f;
        }
        return new Vec3(f, 0.0, g);
    }

    public Player getOwner() {
        if(this.getOwnerUuid() != null) {
            return getPlayerByUuid(this.getOwnerUuid());
        }
        return null;
    }

    @Override
    public boolean isPersistent() {
        return isTame();
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(SITTING, sitting);
    }

    public boolean isCommandItem(ItemStack stack) {
        return false;
    }

    public void setCharging(boolean charging) {
        this.entityData.set(CHARGING, charging);
    }

    public boolean isCharging() {
        return this.entityData.get(CHARGING);
    }

    public int getChargeTimeout() {
        return this.chargeTimeout;
    }
    public void setChargeTimeout(int chargeTimeout) {
        this.chargeTimeout = chargeTimeout;
    }

    public int maxChargeCooldown() {
        return 400;
    }
    public int chargeDuration() {
        return 20;
    }

    private float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    // Equipable =======================================================================================================

    protected void dropInventory() {
        super.dropInventory();
        if (this.hasChest()) {
            if (!this.level().isClientSide) {
                this.dropItem(Blocks.CHEST);
            }

            this.setHasChest(false);
        }
    }

    @Override
    public void onInventoryChanged(Inventory sender) {
        ItemStack itemStack = this.getArmorType();
        super.onInventoryChanged(sender);
        ItemStack itemStack2 = this.getArmorType();
        if (this.age > 20 && this.isHorseArmor(itemStack2) && itemStack != itemStack2) {
            this.playSound(SoundEvents.ITEM_ARMOR_EQUIP_IRON, 0.5F, 1.0F);
        }
    }

    @Override
    public SlotAccess getStackReference(int mappedIndex) {
        int j;
        int i = mappedIndex - 400;
        if (i >= 0 && i < 2 && i < this.items.size()) {
            if (i == 0) {
                return this.createInventoryStackReference(i, stack -> stack.isEmpty() || stack.is(Items.SADDLE));
            }
            if (i == 1) {
                if (!this.hasArmorSlot()) {
                    return SlotAccess.EMPTY;
                }
                return this.createInventoryStackReference(i, stack -> stack.isEmpty() || this.isHorseArmor((ItemStack)stack));
            }
        }
        if ((j = mappedIndex - 500 + 2) >= 2 && j < this.items.size()) {
            return SlotAccess.of(this.items, j);
        }
        return super.getStackReference(mappedIndex);
    }

    protected int getInventorySize() {
        return this.hasChest() ? 17 : super.getInventorySize();
    }

    public int getInventoryColumns() {
        return 5;
    }

    private void addChest(Player player, ItemStack chest) {
        this.setHasChest(true);
        this.playAddChestSound();
        if (!player.getAbilities().creativeMode) {
            chest.decrement(1);
        }
        this.createInventory();
    }

    protected void playAddChestSound() {
        this.playSound(SoundEvents.ENTITY_DONKEY_CHEST, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
    }

    private void equipArmor(ItemStack stack) {
        this.equipStack(EquipmentSlot.CHEST, stack);
        this.setEquipmentDropChance(EquipmentSlot.CHEST, 0.0F);
    }

    public ItemStack getArmorType() {
        return this.getEquippedStack(EquipmentSlot.CHEST);
    }

    @Override
    public boolean hasArmorSlot() {
        return true;
    }

    private void setArmorTypeFromStack(ItemStack stack) {
        this.equipArmor(stack);
        if (!this.level().isClientSide) {
            this.getAttributeInstance(Attributes.ARMOR).removeModifier(BEAST_ARMOR_BONUS_ID);
            if (this.isHorseArmor(stack)) {
                int i = ((TrollArmorItem)stack.getItem()).getProtection();
                if (i != 0) {
                    this.getAttributeInstance(Attributes.ARMOR).addTemporaryModifier(new AttributeModifier(BEAST_ARMOR_BONUS_ID, "Beast armor bonus", (double)i, AttributeModifier.Operation.ADDITION));
                }
            }
        }
    }

    @Override
    protected void updateSaddle() {
        if (!this.level().isClientSide) {
            super.updateSaddle();
            this.setArmorTypeFromStack(this.items.getItem(1));
            this.setEquipmentDropChance(EquipmentSlot.CHEST, 0.0F);
        }
    }

    @Override
    public SoundEvent getSaddleSound() {
        return super.getSaddleSound();
    }

    // Move Set and Behavior ===========================================================================================
    @Override
    protected void jump(float strength, Vec3 movementInput) {
        if(this.isSitting()) {
            this.setSitting(false);
        }
        else if(this.chargeTimeout <= 0) {
            this.setCharging(true);
            this.chargeTimeout = maxChargeCooldown();
        }
    }

    @Override
    public void startJumping(int height) {
        if(!this.isSitting()) {
            this.playSound(SoundEvents.ENTITY_CAMEL_DASH, 1.0f, 1.0f);
            this.setCharging(true);
        }
        else {
            this.setSitting(false);
        }
    }

    public void tryBonding(Player player) {
        if(random.nextDouble() <= 0.1d) {
            if (player instanceof ServerPlayer) {
                this.setOwnerUuid(player.getUuid());
                this.setTame(true);
                CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer)player, this);
            }
            this.level().sendEntityStatus(this, EntityEvent.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);

            this.chargeTimeout = 0;
        }
        else {
            this.level().sendEntityStatus(this, EntityEvent.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
        }
    }

    public InteractionResult interactMob(Player player, InteractionHand hand) {
        boolean bl = !this.isBaby() && this.isTame() && player.isSecondaryUseActive();

        if(this.isTame() && isCommandItem(player.getItemInHand(hand)) && player.getUuid() == this.getOwnerUuid()) {
            this.setSitting(!isSitting());
        }

        if(this.isTame() && !isCommandItem(player.getItemInHand(hand))) {
            super.interactMob(player, hand);
        }

        if(player.getItemInHand(hand).is(getBondingItem()) && !this.isTame() && !this.level().isClientSide) {
            this.tryBonding(player);
        }

        if (!this.isVehicle() && !bl) {
            ItemStack itemStack = player.getItemInHand(hand);
            if (!itemStack.isEmpty()) {
                if (!this.hasChest() && itemStack.is(Items.CHEST)) {
                    this.addChest(player, itemStack);
                    return InteractionResult.success(this.level().isClientSide);
                }
                if (this.hasArmorSlot() && this.isHorseArmor(itemStack) && !this.isWearingArmor()) {
                    this.equipHorseArmor(player, itemStack);
                    return InteractionResult.success(this.level().isClientSide);
                }
            }
        }

        return InteractionResult.success(this.level().isClientSide);
    }

    public Item getBondingItem() {
        return null;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(!source.equals(damageSources().drown()) && !source.equals(damageSources().lava())
                && !source.equals(damageSources().cramming()) && !source.equals(damageSources().magic())) {
            amount *= (1 - RESISTANCE);
        }
        return super.damage(source, amount);
    }

    public void chargeAttack() {
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.attackTicksLeft = ATTACK_COOLDOWN;
        this.level().sendEntityStatus(this, EntityEvent.PLAY_ATTACK_SOUND);
        float f = this.getAttackDamage();
        float g = (int)f > 0 ? f / 2.0f + (float)this.random.nextInt((int)f) : f;
        boolean bl = target.damage(this.damageSources().mobAttack(this), g);
        if (bl) {
            double d;
            if (target instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)target;
                d = livingEntity.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            } else {
                d = 0.0;
            }
            double e = Math.max(0.0, 1.0 - d);
            target.setVelocity(target.getVelocity().multiply(1f + (0.8f * e))); //.add(0.0, (double)0.1f * e, 0.0));
            this.applyDamageEffects(this, target);
        }
        this.playSound(SoundEvents.ENTITY_HOGLIN_ATTACK, 1.5f, 0.8f);
        return bl;
    }

    // SavedTick Management =================================================================================================
    @Override
    public void tick() {
        super.tick();

        if(this.getTarget() != null) {
            this.getLookControl().lookAt(this.getTarget());
        }

        if(this.isCharging()) {
            chargeAttack();
            if(!chargeAnimationState.isRunning()) {
                this.chargeAnimationState.start(this.age);
            }
        }
        if(this.chargeTimeout <= (maxChargeCooldown() - chargeDuration()) || !isCharging()) {
            this.setCharging(false);
            this.targetDir = Vec3.ZERO;
        }
        if(!this.isCharging()) {
            this.chargeAnimationState.stop();
        }
        if(chargeTimeout > 0) {
            --this.chargeTimeout;
        }

        if (this.level().isClientSide) {
            setupAnimationStates();
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.attackTicksLeft > 0) {
            --this.attackTicksLeft;
        }
    }

    // =================================================================================================================

    @Override
    public void handleStatus(byte status) {
        if (status == EntityEvent.PLAY_ATTACK_SOUND) {
            this.attackTicksLeft = ATTACK_COOLDOWN;
        }
        if(status == 4){
            this.attackAnimationState.start(this.age);
        }
        if (status == EntityEvent.ADD_POSITIVE_PLAYER_REACTION_PARTICLES) {
            this.spawnPlayerReactionParticles(true);
        } else if (status == EntityEvent.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES) {
            this.spawnPlayerReactionParticles(false);
        } else {
            super.handleStatus(status);
        }
    }

    public Player getPlayerByUuid(UUID uuid) {
        for (int i = 0; i < this.level().getPlayers().size(); ++i) {
            Player playerEntity = this.level().getPlayers().get(i);
            if (!uuid.equals(playerEntity.getUuid())) continue;
            return playerEntity;
        }
        return null;
    }

    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == Pose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WARDEN_STEP, 0.15F, 2.0F);
    }

    @Override
    public boolean cannotBeSilenced() {
        return super.cannotBeSilenced();
    }

    @Override
    public EntityGetter method_48926() {
        return null;
    }
}
