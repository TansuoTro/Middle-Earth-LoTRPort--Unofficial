package net.jukoz.me.entity.beasts;

import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Blocks;

import net.minecraft.item.FoodComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.pathing.EntityNavigation;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.AbstractHorseEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.StackReference;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import net.jukoz.me.entity.ai.brain.MemoryModulesME;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.jukoz.me.resources.datas.common.RaceType;

import java.util.List;

public abstract class AbstractBeastEntity extends AbstractHorseEntity {
    public static final TrackedData<Boolean> CHARGING = DataTracker.registerData(AbstractBeastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public static final TrackedData<Boolean> SITTING = DataTracker.registerData(AbstractBeastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> CHEST = DataTracker.registerData(AbstractBeastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> RUNNING = DataTracker.registerData(AbstractBeastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> FIGHTING = DataTracker.registerData(AbstractBeastEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    // The tameness value ranges from 0-100, updating every in-game day. If it reaches 0, the beast will break free from its owner.
    private static final TrackedData<Integer> TAMENESS = DataTracker.registerData(AbstractBeastEntity.class, TrackedDataHandlerRegistry.INTEGER);

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
    protected Vec3d targetDir = Vec3d.ZERO;

    // region Initializing
    protected AbstractBeastEntity(EntityType<? extends AbstractBeastEntity> entityType, World world) {
        super(entityType, world);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(CHARGING, false);
        this.dataTracker.startTracking(SITTING, false);
        this.dataTracker.startTracking(CHEST, false);
        this.dataTracker.startTracking(RUNNING, false);
        this.dataTracker.startTracking(FIGHTING, false);
        this.dataTracker.startTracking(TAMENESS, 75);
    }

    @Override
    protected void initGoals() {
    }

    protected abstract void setupAnimationStates();

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        if (!this.firstUpdate && CHARGING.equals(data)) {
            this.chargeTimeout = this.chargeTimeout == 0 ? maxChargeCooldown() : this.chargeTimeout;
        }
        super.onTrackedDataSet(data);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (!this.items.getStack(1).isEmpty()) nbt.put("ArmorItem", this.items.getStack(1).writeNbt(new NbtCompound()));
        nbt.putBoolean("Sitting", this.isSitting());
        nbt.putBoolean("ChestedBeast", this.hasChest());
        nbt.putInt("Tameness", this.getTameness());
        if (this.hasChest()) {
            NbtList nbtList = new NbtList();

            for (int i = 0; i < this.items.size(); i++) {
                ItemStack itemStack = this.items.getStack(i);
                if (!itemStack.isEmpty()) {
                    NbtCompound nbtCompound = new NbtCompound();
                    nbtCompound.putByte("Slot", (byte) i);
                    itemStack.writeNbt(nbtCompound);
                    nbtList.add(nbtCompound);
                }
            }

            nbt.put("Items", nbtList);
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.setSitting(nbt.getBoolean("Sitting"));
        this.setHasChest(nbt.getBoolean("ChestedBeast"));
        this.setTameness(nbt.getInt("Tameness"));
        this.onChestedStatusChanged();
        if (this.hasChest()) {
            NbtList nbtList = nbt.getList("Items", 10);

            for (int i = 0; i < nbtList.size(); i++) {
                NbtCompound nbtCompound = nbtList.getCompound(i);
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

    protected boolean isTamable(PlayerEntity player) {
        return true;
    }

    public boolean canCarryChest() {
        return false;
    }

    public final boolean cannotFollowOwner() {
        return this.isSitting() || this.hasVehicle() || this.isLeashed() || this.getOwner() != null && this.getOwner().isSpectator();
    }

    public boolean shouldAttackWhenMounted() {
        return false;
    }

    public boolean canCharge() {
        return !this.isSitting() && !this.hasPassengers();
    }

    @Override
    public boolean isPersistent() {
        return isTame() || getTameness() <= 0;
    }

    public void resetTameness(){
        this.setTameness(75);
    }

    protected boolean isClientWorld() {
        return this.getWorld().isClient();
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
                !this.getPassengerList().contains(livingEntity) &&                      // Is not a passenger
                !(this.getOwner() != null && this.getOwner().equals(livingEntity)) &&   // Is not its owner
                !(livingEntity instanceof PlayerEntity player && player.isCreative());  // Is not a creative player
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        if (source.isOf(DamageTypes.IN_WALL) && this.hasPassengers() && getControllingPassenger() instanceof NpcEntity) {
            return true;
        }
        return super.isInvulnerableTo(source);
    }
    // endregion

    // region DataTracker

    public int getTameness() {
        return this.dataTracker.get(TAMENESS);
    }
    public void setTameness(int tameness) {
        this.dataTracker.set(TAMENESS, tameness);
    }

    public boolean hasChest() {
        return this.dataTracker.get(CHEST);
    }

    public void setHasChest(boolean hasChest) {
        this.dataTracker.set(CHEST, hasChest);
    }

    public boolean isRunning() {
        return this.dataTracker.get(RUNNING);
    }

    public void setRunning(boolean running) {
        this.dataTracker.set(RUNNING, running);
    }

    public boolean isCharging() {
        return this.dataTracker.get(CHARGING);
    }

    public void setCharging(boolean charging) {
        this.dataTracker.set(CHARGING, charging);
    }

    public boolean isSitting() {
        return this.dataTracker.get(SITTING);
    }

    public void setSitting(boolean sitting) {
        this.dataTracker.set(SITTING, sitting);
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
        return (double)this.getHeight() - 0.19 + (double)(0.12F * MathHelper.cos(g * 1.5F) * 2.0F * f);
    }

    public PlayerEntity getOwner() {
        if(super.getOwner() instanceof PlayerEntity playerEntity)
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
        return (float)this.getAttributeValue(EntityAttributes.GENERIC_ATTACK_DAMAGE);
    }

    // endregion

    // region Equipment

    protected void dropInventory() {
        super.dropInventory();
        if (this.hasChest()) {
            this.dropItem(Blocks.CHEST);
            this.setHasChest(false);
        }
    }

    @Override
    public StackReference getStackReference(int mappedIndex) {
        return mappedIndex == 499 ? new StackReference() {
            @Override
            public ItemStack get() {
                return AbstractBeastEntity.this.hasChest() ? new ItemStack(Items.CHEST) : ItemStack.EMPTY;
            }

            @Override
            public boolean set(ItemStack stack) {
                if (stack.isEmpty()) {
                    if (AbstractBeastEntity.this.hasChest()) {
                        AbstractBeastEntity.this.setHasChest(false);
                        AbstractBeastEntity.this.onChestedStatusChanged();
                    }

                    return true;
                } else if (stack.isOf(Items.CHEST)) {
                    if (!AbstractBeastEntity.this.hasChest()) {
                        AbstractBeastEntity.this.setHasChest(true);
                        AbstractBeastEntity.this.onChestedStatusChanged();
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

    private void addChest(PlayerEntity player, ItemStack chest) {
        this.setHasChest(true);
        this.playAddChestSound();
        if (!player.isCreative()) {
            chest.decrement(1);
        }
        this.onChestedStatusChanged();
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
            this.getBrain().forget(MemoryModulesME.TAME);
        }
    }

    @Override
    protected void jump(float strength, Vec3d movementInput) {
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

    public void tryBonding(PlayerEntity player) {
        if(random.nextDouble() <= 0.1d || player.isCreative()) {
            this.tameBeast(player);
            this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES);

            this.chargeTimeout = 0;
        }
        else {
            this.getWorld().sendEntityStatus(this, EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES);
        }
    }

    public void tameBeast(PlayerEntity player) {
        if (player instanceof ServerPlayerEntity) {
            this.setOwnerUuid(player.getUuid());
            this.setTame(true);
            Criteria.TAME_ANIMAL.trigger((ServerPlayerEntity)player, this);
        }
    }

    public void tameBeast(LivingEntity livingEntity) {
        this.setOwnerUuid(livingEntity.getUuid());
        this.setTame(true);
    }

    @Override
    public ActionResult interactMob(PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);

        if(isBondingItem(player.getStackInHand(hand)) && !this.isTame() && this.isTamable(player)) {
            if(!this.getWorld().isClient()) {
                this.tryBonding(player);
                this.eat(player, hand, itemStack);
            }
            return ActionResult.SUCCESS;
        }

        if(this.isTame()) {
            if (this.isHorseArmor(itemStack) && this.items.getStack(1).isEmpty() && player == getOwner()) {
                if (!getWorld().isClient) {
                    ItemStack armor = itemStack.copy();
                    armor.setCount(1);
                    this.items.setStack(1, armor);
                    if (!player.getAbilities().creativeMode) itemStack.decrement(1);
                }
                return ActionResult.success(getWorld().isClient);
            }
            if(isFoodItem(itemStack) && getOwner() == player) {
                int tamenessIncrease;

                FoodComponent component = itemStack.getItem().getFoodComponent();
                if(component != null) {
                    tamenessIncrease = component.getHunger();
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
                return ActionResult.SUCCESS;
            }

            if(isCommandItem(itemStack) && player == getOwner()) {
                this.setSitting(!isSitting());
                return ActionResult.SUCCESS;
            }

            if (!this.hasChest() && itemStack.isOf(Items.CHEST) && canCarryChest()) {
                this.addChest(player, itemStack);
                return ActionResult.SUCCESS;
            }

            if(!(isCommandItem(itemStack) || isBreedingItem(itemStack) || itemStack.isOf(Items.CHEST)) && this.isMountable()) {
                return super.interactMob(player, hand);
            }
        }

        return ActionResult.FAIL;
    }

    @Override
    public ActionResult interactHorse(PlayerEntity player, ItemStack stack) {
        return ActionResult.PASS;
    }

    @Override
    protected void putPlayerOnBack(PlayerEntity player) {
        ItemStack item = player.getStackInHand(Hand.MAIN_HAND);
        if(this.canAddPassenger(player) && item.isEmpty()) {
            super.putPlayerOnBack(player);
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(!source.equals(getDamageSources().drown()) && !source.equals(getDamageSources().lava())
                && !source.equals(getDamageSources().cramming()) && !source.equals(getDamageSources().magic())) {
            amount *= (1 - RESISTANCE);
        }
        return !this.getWorld().isClient() && super.damage(source, amount);
    }

    public void chargeAttack() {
    }

    public void setChargeVelocity(Vec3d direction) {
        this.setVelocity(new Vec3d(direction.x, 0.0, direction.z)
                .normalize() // Remove the y-axis and normalize the vector
                .multiply(1.0d - ((double)(this.chargeTimeout - (maxChargeCooldown() - chargeDuration())) / chargeDuration())) // Progressively get faster during charge (linear)
                .add(0, this.getVelocity().y, 0)); // Add y-Velocity to make beast fall and climb steps
    }

    @Override
    protected EntityNavigation createNavigation(World world) {
        return new BeastEntityNavigation(this, world);
    }

    @Override
    protected float getSaddledSpeed(PlayerEntity controllingPlayer) {
        return this.isSitting() ? 0 : super.getSaddledSpeed(controllingPlayer);
    }

    protected float getNpcSaddledSpeed(NpcEntity controllingNpc) {
        return this.isSitting() ? 0 : (float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED);
    }

    // endregion

    // region Tick Management
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
            this.targetDir = Vec3d.ZERO;
        }
        if(!this.isCharging()) {
            this.chargeAnimationState.stop();
        }
        if(chargeTimeout > 0) {
            --this.chargeTimeout;
        }

        if(this.hasControllingPassenger() && !this.shouldAttackWhenMounted()) {
            this.setAttacker(null);
            this.setAttacking(false);
            this.setTarget(null);
        }

        if (this.getWorld().isClient) {
            setupAnimationStates();
        }

        if (!this.isClientWorld() && isTame() && this.getOwner() != null) {
            if(this.getWorld().getTimeOfDay() == 6500) { // Tameness always decreases shortly after noon
                List<? extends PlayerEntity> players = this.getWorld().getPlayers();
                if(this.getOwner() != null && players.contains(this.getOwner())) { // Check if owner is online
                    // Get amount of other beasts using the Tameness mechanic in a 25 block radius. The tameness decreases exponentially faster for each of them.
                    int affectingBeasts = this.getWorld().getEntitiesByClass(AbstractBeastEntity.class, this.getBoundingBox().expand(25), (entity) -> usesTameness() && getOwner() == this.getOwner()).size();

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
            Vec3d movementInput = new Vec3d(this.sidewaysSpeed, this.upwardSpeed, this.forwardSpeed);
            if (this.canMoveVoluntarily()) {
                this.setMovementSpeed(this.getNpcSaddledSpeed(npc));
                this.travel(movementInput);
            } else {
                this.setVelocity(Vec3d.ZERO);
            }
        }
    }

    // endregion

    protected void setChildAttribute(PassiveEntity other, AbstractHorseEntity child, EntityAttribute attribute, double min, double max) {
        double d = this.calculateAttributeBaseValue(this.getAttributeBaseValue(attribute), other.getAttributeBaseValue(attribute), min, max, this.random);
        child.getAttributeInstance(attribute).setBaseValue(d);
    }

    static double calculateAttributeBaseValue(double parentBase, double otherParentBase, double min, double max, Random random) {
        double g;
        if (max <= min) {
            throw new IllegalArgumentException("Incorrect range for an attribute");
        }
        parentBase = MathHelper.clamp(parentBase, min, max);
        otherParentBase = MathHelper.clamp(otherParentBase, min, max);
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
        if (status == EntityStatuses.PLAY_ATTACK_SOUND) {
            this.attackTicksLeft = ATTACK_COOLDOWN;
            this.attackAnimationState.start(this.age);
        }
        if (status == EntityStatuses.ADD_POSITIVE_PLAYER_REACTION_PARTICLES) {
            this.spawnPlayerReactionParticles(true);
        } else if (status == EntityStatuses.ADD_NEGATIVE_PLAYER_REACTION_PARTICLES) {
            this.spawnPlayerReactionParticles(false);
        } else {
            super.handleStatus(status);
        }
    }

    @Override
    public net.minecraft.world.EntityView method_48926() {
        return this.getWorld();
    }

    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == EntityPose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
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
        if (this.items == null || this.items.size() < 2 || getWorld().isClient) return;
        ItemStack armor = this.items.getStack(1);
        var equipment = net.jukoz.me.item.MEComponents.get(armor, net.jukoz.me.item.MEComponents.MOUNT_EQUIPPABLE);
        boolean allowed = equipment != null && equipment.allows(this);
        equipStack(EquipmentSlot.CHEST, allowed ? armor.copy() : ItemStack.EMPTY);
        setEquipmentDropChance(EquipmentSlot.CHEST, 0.0F);
        me$mountArmorBonus(EntityAttributes.GENERIC_ARMOR, "armor", allowed ? equipment.armor() : 0);
        me$mountArmorBonus(EntityAttributes.GENERIC_ARMOR_TOUGHNESS, "toughness", allowed ? equipment.toughness() : 0);
        me$mountArmorBonus(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, "knockback", allowed ? equipment.knockbackResistance() : 0);
    }

    private void me$mountArmorBonus(EntityAttribute attribute, String name, double amount) {
        var instance = getAttributeInstance(attribute);
        if (instance == null) return;
        var id = java.util.UUID.nameUUIDFromBytes(("me:mount_armor_" + name).getBytes(java.nio.charset.StandardCharsets.UTF_8));
        instance.removeModifier(id);
        if (amount != 0) instance.addTemporaryModifier(new net.minecraft.entity.attribute.EntityAttributeModifier(
                id, "me:mount_armor_" + name, amount, net.minecraft.entity.attribute.EntityAttributeModifier.Operation.ADDITION));
    }

    @Override
    public void onInventoryChanged(net.minecraft.inventory.Inventory inventory) {
        ItemStack previous = getBodyArmor().copy();
        super.onInventoryChanged(inventory);
        var current = getBodyArmor();
        var equipment = net.jukoz.me.item.MEComponents.get(current, net.jukoz.me.item.MEComponents.MOUNT_EQUIPPABLE);
        if (age > 20 && equipment != null && !ItemStack.areEqual(previous, current)) {
            var sound = net.minecraft.registry.Registries.SOUND_EVENT.get(equipment.equipSound());
            if (sound != null) playSound(sound, 0.5F, 1.0F);
        }
    }
}
