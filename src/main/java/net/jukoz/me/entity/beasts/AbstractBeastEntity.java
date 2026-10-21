package net.jukoz.me.entity.beasts;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.jukoz.me.entity.ai.brain.MemoryModulesME;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.jukoz.me.resources.datas.common.RaceType;
import java.util.List;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

public abstract class AbstractBeastEntity extends AbstractHorse {
    public static final EntityDataAccessor<Boolean> CHARGING = SynchedEntityData.registerData(AbstractBeastEntity.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.registerData(AbstractBeastEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CHEST = SynchedEntityData.registerData(AbstractBeastEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RUNNING = SynchedEntityData.registerData(AbstractBeastEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FIGHTING = SynchedEntityData.registerData(AbstractBeastEntity.class, EntityDataSerializers.BOOLEAN);

    // The tameness value ranges from 0-100, updating every in-game day. If it reaches 0, the beast will break free from its owner.
    private static final EntityDataAccessor<Integer> TAMENESS = SynchedEntityData.registerData(AbstractBeastEntity.class, EntityDataSerializers.INTEGER);

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    public final AnimationState chargeAnimationState = new AnimationState();
    public final AnimationState sittingAnimationState = new AnimationState();
    public final AnimationState startSittingAnimationState = new AnimationState();
    public final AnimationState stopSittingAnimationState = new AnimationState();

    protected int idleAnimationTimeout = 1000;
    protected int attackTicksLeft = 0;
    protected boolean hasCharged = false;

    protected int chargeTimeout; // ticking cooldown of the charge attack

    public static final int ATTACK_COOLDOWN = 10;
    public static final float RESISTANCE = 0.15f;
    protected Vec3 targetDir = Vec3.ZERO;

    // region Initializing
    protected AbstractBeastEntity(EntityType<? extends AbstractBeastEntity> entityType, Level world) {
        super(entityType, world);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.entityData.define(CHARGING, false);
        this.entityData.define(SITTING, false);
        this.entityData.define(CHEST, false);
        this.entityData.define(RUNNING, false);
        this.entityData.define(FIGHTING, false);
        this.entityData.define(TAMENESS, 75);
    }

    @Override
    protected void initGoals() {
    }

    protected abstract void setupAnimationStates();

    @Override
    public void onTrackedDataSet(EntityDataAccessor<?> data) {
        if (!this.firstUpdate && CHARGING.equals(data)) {
            this.chargeTimeout = this.chargeTimeout == 0 ? maxChargeCooldown() : this.chargeTimeout;
        }
        super.onTrackedDataSet(data);
    }

    @Override
    public void writeCustomDataToNbt(CompoundTag nbt) {
        super.writeCustomDataToNbt(nbt);
        if (!this.items.getItem(1).isEmpty()) nbt.put("ArmorItem", this.items.getItem(1).writeNbt(new CompoundTag()));
        nbt.putBoolean("Sitting", this.isSitting());
        nbt.putBoolean("ChestedBeast", this.hasChest());
        nbt.putInt("Tameness", this.getTameness());
        if (this.hasChest()) {
            ListTag nbtList = new ListTag();

            for (int i = 0; i < this.items.size(); i++) {
                ItemStack itemStack = this.items.getItem(i);
                if (!itemStack.isEmpty()) {
                    CompoundTag nbtCompound = new CompoundTag();
                    nbtCompound.putByte("Slot", (byte) i);
                    itemStack.writeNbt(nbtCompound);
                    nbtList.add(nbtCompound);
                }
            }

            nbt.put("Items", nbtList);
        }
    }

    @Override
    public void readCustomDataFromNbt(CompoundTag nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSitting(nbt.getBoolean("Sitting"));
        this.setHasChest(nbt.getBoolean("ChestedBeast"));
        this.setTameness(nbt.getInt("Tameness"));
        this.createInventory();
        if (this.hasChest()) {
            ListTag nbtList = nbt.getList("Items", 10);

            for (int i = 0; i < nbtList.size(); i++) {
                CompoundTag nbtCompound = nbtList.getCompound(i);
                int j = nbtCompound.getByte("Slot") & 255;
                if (j < this.items.size()) {
                    this.items.setStack(j, ItemStack.fromNbt(nbtCompound));
                }
            }
        }
        if (nbt.contains("ArmorItem", 10)) {
            ItemStack armor = ItemStack.fromNbt(nbt.getCompound("ArmorItem"));
            if (this.isHorseArmor(armor)) this.items.setStack(1, armor);
        }
        this.updateSaddle();
    }

    // endregion

    // region Conditions
    public abstract DispositionType getDisposition();

    public abstract List<RaceType> getCompatibleRaces();

    public abstract boolean usesTameness();

    public abstract boolean isCommandItem(ItemStack stack);
    public abstract boolean isBondingItem(ItemStack itemStack);
    public abstract boolean isFoodItem(ItemStack itemStack);

    public boolean isMountable() {
        return true;
    }

    protected boolean isTamable(Player player) {
        return true;
    }

    public boolean canCarryChest() {
        return false;
    }

    public final boolean cannotFollowOwner() {
        return this.isSitting() || this.isPassenger() || this.isLeashed() || this.getOwner() != null && this.getOwner().isSpectator();
    }

    public boolean shouldAttackWhenMounted() {
        return false;
    }

    public boolean canCharge() {
        return !this.isSitting() && !this.isVehicle();
    }

    @Override
    public boolean isPersistent() {
        return isTame() || getTameness() <= 0;
    }

    public void resetTameness(){
        this.setTameness(75);
    }

    protected boolean isClientWorld() {
        return this.level().isClientSide();
    }

    public boolean isOwner(LivingEntity entity) {
        return this.getOwner() != null && this.getOwner() == entity;
    }

    /**
     * Checks an entity for beast-specific targeting criteria (LivingEntity, not a passenger, not its owner, not a creative player)
     * @param entity
     * @return isValidTarget
     */
    public boolean isValidTarget(Entity entity) {
        return entity instanceof LivingEntity livingEntity &&                           // Entity is LivingEntity
                !this.getPassengers().contains(livingEntity) &&                      // Is not a passenger
                !(this.getOwner() != null && this.getOwner().equals(livingEntity)) &&   // Is not its owner
                !(livingEntity instanceof Player player && player.isCreative());  // Is not a creative player
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.is(DamageTypes.IN_WALL) && this.isVehicle() && getControllingPassenger() instanceof NpcEntity) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }
    // endregion

    // region SynchedEntityData

    public int getTameness() {
        return this.entityData.get(TAMENESS);
    }
    public void setTameness(int tameness) {
        this.entityData.set(TAMENESS, tameness);
    }

    public boolean hasChest() {
        return this.entityData.get(CHEST);
    }

    public void setHasChest(boolean hasChest) {
        this.entityData.set(CHEST, hasChest);
    }

    public boolean isRunning() {
        return this.entityData.get(RUNNING);
    }

    public void setRunning(boolean running) {
        this.entityData.set(RUNNING, running);
    }

    public boolean isCharging() {
        return this.entityData.get(CHARGING);
    }

    public void setCharging(boolean charging) {
        this.entityData.set(CHARGING, charging);
    }

    public boolean isSitting() {
        return this.entityData.get(SITTING);
    }

    public void setSitting(boolean sitting) {
        this.entityData.set(SITTING, sitting);
    }

    public boolean isFighting() {
        return dataTracker.get(FIGHTING);
    }

    public void setFighting(boolean isFighting) {
        dataTracker.set(FIGHTING, isFighting);
    }

    // endregion

    // region Non-tracked Getters and Setters
    public boolean hasCharged() {
        return hasCharged;
    }

    public void setHasCharged(boolean hasCharged) {
        this.hasCharged = hasCharged;
    }

    public int getChargeTimeout() {
        return this.chargeTimeout;
    }

    public void setChargeTimeout(int chargeTimeout) {
        this.chargeTimeout = chargeTimeout;
    }

    @Override
    public int getJumpCooldown() {
        return this.chargeTimeout;
    }

    public double getMountedHeightOffset() {
        float f = Math.min(0.25F, this.limbAnimator.getSpeed());
        float g = this.limbAnimator.getSpeed(); // TODO : was this.limbAnimator.getPos();
        return (double)this.getHeight() - 0.19 + (double)(0.12F * Mth.cos(g * 1.5F) * 2.0F * f);
    }

    public Player getOwner() {
        if(super.getOwner() instanceof Player playerEntity)
            return playerEntity;
        return null;
    }

    public int maxChargeCooldown() {
        return 400;
    }
    public int chargeDuration() {
        return 20;
    }

    protected float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.ATTACK_DAMAGE);
    }

    // endregion

    // region Equipable

    protected void dropInventory() {
        super.dropInventory();
        if (this.hasChest()) {
            this.dropItem(Blocks.CHEST);
            this.setHasChest(false);
        }
    }

    @Override
    public SlotAccess getStackReference(int mappedIndex) {
        return mappedIndex == 499 ? new SlotAccess() {
            @Override
            public ItemStack get() {
                return AbstractBeastEntity.this.hasChest() ? new ItemStack(Items.CHEST) : ItemStack.EMPTY;
            }

            @Override
            public boolean set(ItemStack stack) {
                if (stack.isEmpty()) {
                    if (AbstractBeastEntity.this.hasChest()) {
                        AbstractBeastEntity.this.setHasChest(false);
                        AbstractBeastEntity.this.createInventory();
                    }

                    return true;
                } else if (stack.is(Items.CHEST)) {
                    if (!AbstractBeastEntity.this.hasChest()) {
                        AbstractBeastEntity.this.setHasChest(true);
                        AbstractBeastEntity.this.createInventory();
                    }

                    return true;
                } else {
                    return false;
                }
            }
        } : super.getStackReference(mappedIndex);
    }

    public int getInventoryColumns() {
        return this.hasChest() ? 5 : 0;
    }

    private void addChest(Player player, ItemStack chest) {
        this.setHasChest(true);
        this.playAddChestSound();
        if (!player.isCreative()) {
            chest.decrement(1);
        }
        this.createInventory();
    }

    protected void playAddChestSound() {
        this.playSound(SoundEvents.ENTITY_DONKEY_CHEST, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
    }

    // endregion

    // region Move Set and Behavior

    public void breakFree() {
        this.setTame(false);
        this.setOwnerUuid(null);
        this.setSitting(false);

        if(this.getBrain() != null) {
            this.getBrain().erase(MemoryModulesME.TAME);
        }
    }

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
    public boolean isAngry() {
        return false;
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
        if(random.nextDouble() <= 0.1d || player.isCreative()) {
            this.tameBeast(player);
            this.level().sendEntityStatus(this, EntityEvent.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);

            this.chargeTimeout = 0;
        }
        else {
            this.level().sendEntityStatus(this, EntityEvent.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
        }
    }

    public void tameBeast(Player player) {
        if (player instanceof ServerPlayer) {
            this.setOwnerUuid(player.getUuid());
            this.setTame(true);
            CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer)player, this);
        }
    }

    public void tameBeast(LivingEntity livingEntity) {
        this.setOwnerUuid(livingEntity.getUuid());
        this.setTame(true);
    }

    @Override
    public InteractionResult interactMob(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if(isBondingItem(player.getItemInHand(hand)) && !this.isTame() && this.isTamable(player)) {
            if(!this.level().isClientSide()) {
                this.tryBonding(player);
                this.eat(player, hand, itemStack);
            }
            return InteractionResult.SUCCESS;
        }

        if(this.isTame()) {
            if (this.isHorseArmor(itemStack) && this.items.getItem(1).isEmpty() && player == getOwner()) {
                if (!getWorld().isClientSide) {
                    ItemStack armor = itemStack.copy();
                    armor.setCount(1);
                    this.items.setStack(1, armor);
                    if (!player.getAbilities().creativeMode) itemStack.decrement(1);
                }
                return InteractionResult.success(getWorld().isClientSide);
            }
            if(isFoodItem(itemStack) && getOwner() == player) {
                int tamenessIncrease;

                FoodProperties component = itemStack.getItem().getFoodProperties();
                if(component != null) {
                    tamenessIncrease = component.getNutrition();
                }
                else {
                    tamenessIncrease = 4;
                }

                this.setTameness(this.getTameness() + tamenessIncrease);
                if(this.getTameness() > 100) {
                    this.setTameness(100);
                }

                this.eat(player, hand, itemStack);
                playSound(SoundEvents.ENTITY_HORSE_EAT, 1.0F, 1.0F);
                return InteractionResult.SUCCESS;
            }

            if(isCommandItem(itemStack) && player == getOwner()) {
                this.setSitting(!isSitting());
                return InteractionResult.SUCCESS;
            }

            if (!this.hasChest() && itemStack.is(Items.CHEST) && canCarryChest()) {
                this.addChest(player, itemStack);
                return InteractionResult.SUCCESS;
            }

            if(!(isCommandItem(itemStack) || isBreedingItem(itemStack) || itemStack.is(Items.CHEST)) && this.isMountable()) {
                return super.interactMob(player, hand);
            }
        }

        return InteractionResult.FAIL;
    }

    @Override
    public InteractionResult interactHorse(Player player, ItemStack stack) {
        return InteractionResult.PASS;
    }

    @Override
    protected void putPlayerOnBack(Player player) {
        ItemStack item = player.getItemInHand(InteractionHand.MAIN_HAND);
        if(this.canAddPassenger(player) && item.isEmpty()) {
            super.putPlayerOnBack(player);
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(!source.equals(damageSources().drown()) && !source.equals(damageSources().lava())
                && !source.equals(damageSources().cramming()) && !source.equals(damageSources().magic())) {
            amount *= (1 - RESISTANCE);
        }
        return !this.level().isClientSide() && super.damage(source, amount);
    }

    public void chargeAttack() {
    }

    public void setChargeVelocity(Vec3 direction) {
        this.setVelocity(new Vec3(direction.x, 0.0, direction.z)
                .normalize() // Remove the y-axis and normalize the vector
                .multiply(1.0d - ((double)(this.chargeTimeout - (maxChargeCooldown() - chargeDuration())) / chargeDuration())) // Progressively get faster during charge (linear)
                .add(0, this.getVelocity().y, 0)); // Add y-Velocity to make beast fall and climb steps
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new BeastEntityNavigation(this, world);
    }

    @Override
    protected float getSaddledSpeed(Player controllingPlayer) {
        return this.isSitting() ? 0 : super.getSaddledSpeed(controllingPlayer);
    }

    protected float getNpcSaddledSpeed(NpcEntity controllingNpc) {
        return this.isSitting() ? 0 : (float)this.getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    // endregion

    // region SavedTick Management
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

        if(this.hasControllingPassenger() && !this.shouldAttackWhenMounted()) {
            this.setAttacker(null);
            this.setAggressive(false);
            this.setTarget(null);
        }

        if (this.level().isClientSide) {
            setupAnimationStates();
        }

        if (!this.isClientWorld() && isTame() && this.getOwner() != null) {
            if(this.level().getTimeOfDay() == 6500) { // Tameness always decreases shortly after noon
                List<? extends Player> players = this.level().getPlayers();
                if(this.getOwner() != null && players.contains(this.getOwner())) { // Check if owner is online
                    // Get amount of other beasts using the Tameness mechanic in a 25 block radius. The tameness decreases exponentially faster for each of them.
                    int affectingBeasts = this.level().getEntitiesByClass(AbstractBeastEntity.class, this.getBoundingBox().expand(25), (entity) -> usesTameness() && getOwner() == this.getOwner()).size();

                    // The number of entities includes itself, therefore the smallest value to decrease is at 10
                    this.setTameness(this.getTameness() - (int)(5 * Math.pow(2, affectingBeasts)));

                    if(this.getTameness() <= 0) { // Tameness is 0, break free
                        this.breakFree();
                    }
                }
            }
        }
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.attackTicksLeft > 0) {
            --this.attackTicksLeft;
        }

        if (this.getControllingPassenger() instanceof NpcEntity npc && this.isAlive()) {
            Vec3 movementInput = new Vec3(this.sidewaysSpeed, this.upwardSpeed, this.forwardSpeed);
            if (this.isEffectiveAi()) {
                this.setSpeed(this.getNpcSaddledSpeed(npc));
                this.travel(movementInput);
            } else {
                this.setVelocity(Vec3.ZERO);
            }
        }
    }

    // endregion

    protected void setChildAttribute(AgeableMob other, AbstractHorse child, Attribute attribute, double min, double max) {
        double d = this.calculateAttributeBaseValue(this.getAttributeBaseValue(attribute), other.getAttributeBaseValue(attribute), min, max, this.random);
        child.getAttributeInstance(attribute).setBaseValue(d);
    }

    static double calculateAttributeBaseValue(double parentBase, double otherParentBase, double min, double max, RandomSource random) {
        double g;
        if (max <= min) {
            throw new IllegalArgumentException("Incorrect range for an attribute");
        }
        parentBase = Mth.clamp(parentBase, min, max);
        otherParentBase = Mth.clamp(otherParentBase, min, max);
        double d = 0.15 * (max - min);
        double f = (parentBase + otherParentBase) / 2.0;
        double e = Math.abs(parentBase - otherParentBase) + d * 2.0;
        double h = f + e * (g = (random.nextDouble() + random.nextDouble() + random.nextDouble()) / 3.0 - 0.5);
        if (h > max) {
            double i = h - max;
            return max - i;
        }
        if (h < min) {
            double i = min - h;
            return min + i;
        }
        return h;
    }

    @Override
    public void handleStatus(byte status) {
        if (status == EntityEvent.PLAY_ATTACK_SOUND) {
            this.attackTicksLeft = ATTACK_COOLDOWN;
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

    @Override
    public net.minecraft.world.level.EntityGetter method_48926() {
        return this.level();
    }

    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == Pose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }

    public ItemStack getBodyArmor() {
        return this.getEquippedStack(EquipmentSlot.CHEST);
    }

    @Override
    public boolean hasArmorSlot() {
        return true;
    }

    @Override
    public boolean isHorseArmor(ItemStack stack) {
        var equipment = net.jukoz.me.item.MEComponents.get(stack, net.jukoz.me.item.MEComponents.MOUNT_EQUIPPABLE);
        return equipment != null && equipment.allows(this);
    }

    @Override
    protected void updateSaddle() {
        super.updateSaddle();
        if (this.items == null || this.items.size() < 2 || getWorld().isClientSide) return;
        ItemStack armor = this.items.getItem(1);
        var equipment = net.jukoz.me.item.MEComponents.get(armor, net.jukoz.me.item.MEComponents.MOUNT_EQUIPPABLE);
        boolean allowed = equipment != null && equipment.allows(this);
        equipStack(EquipmentSlot.CHEST, allowed ? armor.copy() : ItemStack.EMPTY);
        setEquipmentDropChance(EquipmentSlot.CHEST, 0.0F);
        me$mountArmorBonus(Attributes.ARMOR, "armor", allowed ? equipment.armor() : 0);
        me$mountArmorBonus(Attributes.ARMOR_TOUGHNESS, "toughness", allowed ? equipment.toughness() : 0);
        me$mountArmorBonus(Attributes.KNOCKBACK_RESISTANCE, "knockback", allowed ? equipment.knockbackResistance() : 0);
    }

    private void me$mountArmorBonus(Attribute attribute, String name, double amount) {
        var instance = getAttributeInstance(attribute);
        if (instance == null) return;
        var id = java.util.UUID.nameUUIDFromBytes(("me:mount_armor_" + name).getBytes(java.nio.charset.StandardCharsets.UTF_8));
        instance.removeModifier(id);
        if (amount != 0) instance.addTemporaryModifier(new net.minecraft.world.entity.ai.attributes.AttributeModifier(
                id, "me:mount_armor_" + name, amount, net.minecraft.entity.attribute.AttributeModifier.Operation.ADDITION));
    }

    @Override
    public void onInventoryChanged(net.minecraft.world.entity.player.Inventory inventory) {
        ItemStack previous = getBodyArmor().copy();
        super.onInventoryChanged(inventory);
        var current = getBodyArmor();
        var equipment = net.jukoz.me.item.MEComponents.get(current, net.jukoz.me.item.MEComponents.MOUNT_EQUIPPABLE);
        if (age > 20 && equipment != null && !ItemStack.areEqual(previous, current)) {
            var sound = net.minecraft.core.registries.Registries.SOUND_EVENT.get(equipment.equipSound());
            if (sound != null) playSound(sound, 0.5F, 1.0F);
        }
    }
}
