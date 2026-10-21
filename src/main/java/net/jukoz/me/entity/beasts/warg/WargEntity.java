package net.jukoz.me.entity.beasts.warg;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.EntitiesME;
import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.jukoz.me.entity.EntityAttributesME;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.jukoz.me.entity.beasts.great_horn.GreatHornEntity;
import net.jukoz.me.entity.goals.*;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.jukoz.me.resources.datas.common.RaceType;
import net.jukoz.me.resources.datas.races.RaceUtil;
import org.jetbrains.annotations.Nullable;
import java.util.List;
import java.util.function.DoubleSupplier;
import java.util.function.IntUnaryOperator;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.entity.Mob;

public class WargEntity extends AbstractBeastEntity {

    private static final float MIN_MOVEMENT_SPEED_BONUS = (float) WargEntity.getChildMovementSpeedBonus(() -> 0.0);
    private static final float MAX_MOVEMENT_SPEED_BONUS = (float)WargEntity.getChildMovementSpeedBonus(() -> 1.0);
    private static final float MIN_ATTACK_DAMAGE_BONUS = (float)WargEntity.getChildAttackDamageBonus(() -> 0.0);
    private static final float MAX_ATTACK_DAMAGE_BONUS = (float)WargEntity.getChildAttackDamageBonus(() -> 1.0);
    private static final float MIN_HEALTH_BONUS = WargEntity.getChildHealthBonus(max -> 0);
    private static final float MAX_HEALTH_BONUS = WargEntity.getChildHealthBonus(max -> max - 1);
    private static final EntityDataAccessor<Integer> VARIANT = SynchedEntityData.registerData(WargEntity.class, EntityDataSerializers.INTEGER);
    private static final EntityDataAccessor<Integer> EYE_VARIANT = SynchedEntityData.registerData(WargEntity.class, EntityDataSerializers.INTEGER);
    private static final double PASSENGER_SADDLE_Y_OFFSET = 0.12;
    private static final double PASSENGER_SADDLE_BACKWARD_OFFSET = 0.18;
    public int idleAnimationTimeout = this.random.nextInt(600) + 1700;
    private static final EntityDimensions BABY_BASE_DIMENSIONS = EntitiesME.WARG.getDimensions().scaled(0.5f);

    public WargEntity(EntityType<? extends WargEntity> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return net.minecraft.world.entity.Mob.createMobAttributes()
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.MAX_HEALTH, 24.0d)
                .add(Attributes.KNOCKBACK_RESISTANCE, 0.2d)
                .add(Attributes.GENERIC_ATTACK_SPEED, 1.0d)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 38.0d)
                .add(Attributes.ATTACK_DAMAGE, 4.0d)
                .add(EntityAttributesME.SAFE_FALL_DISTANCE.value(), 6.0d);
    }

    protected void initAttributes(RandomSource random) {
        this.getAttributeInstance(Attributes.MAX_HEALTH).setBaseValue(this.getChildHealthBonus(random::nextInt));
        this.getAttributeInstance(Attributes.MOVEMENT_SPEED).setBaseValue(this.getChildMovementSpeedBonus(random::nextDouble));
        this.getAttributeInstance(Attributes.ATTACK_DAMAGE).setBaseValue(this.getChildAttackDamageBonus(random::nextDouble));
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new FloatGoal(this));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 2, false));
        this.goalSelector.add(4, new ChargeAttackGoal(this, this.getDisposition(), maxChargeCooldown()));
        this.goalSelector.add(5, new BreedGoal(this, 1.5));
        this.goalSelector.add(6, new TemptGoal(this, 0.9, net.minecraft.world.item.crafting.Ingredient.fromTag(TagKey.create(Registries.ITEM, ResourceLocation.of(MiddleEarth.MOD_ID, "warg_food"))), false));
        this.goalSelector.add(7, new WaterAvoidingRandomStrollGoal(this, 0.5));
        this.goalSelector.add(8, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.add(9, new RandomLookAroundGoal(this));
        this.targetSelector.add(3, new BeastRevengeGoal(this, new Class[0]).setGroupRevenge());
        this.targetSelector.add(4, new BeastTargetPlayerGoal(this, this.getDisposition()));
        this.targetSelector.add(10, new BeastActiveTargetGoal<>(this, BroadhoofGoatEntity.class, true));
        this.targetSelector.add(10, new BeastActiveTargetGoal<>(this, GreatHornEntity.class, true));
        this.targetSelector.add(11, new BeastActiveTargetGoal<>(this, Sheep.class, true));
        this.targetSelector.add(12, new BeastActiveTargetGoal<>(this, Goat.class, true));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.entityData.define(VARIANT, 0);
        this.entityData.define(EYE_VARIANT, 0);
    }

    @Override
    public void writeCustomDataToNbt(net.minecraft.nbt.CompoundTag nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putInt("Variant", this.getTypeVariant());
        nbt.putInt("EyeVariant", this.getEyeTypeVariant());
    }

    @Override
    public void readCustomDataFromNbt(net.minecraft.nbt.CompoundTag nbt) {
        super.readCustomDataFromNbt(nbt);
        this.entityData.set(VARIANT, nbt.getInt("Variant"));
        this.entityData.set(EYE_VARIANT, nbt.getInt("EyeVariant"));
    }

    protected static float getChildHealthBonus(IntUnaryOperator randomIntGetter) {
        return 12.0f + (float)randomIntGetter.applyAsInt(6) + (float)randomIntGetter.applyAsInt(6);
    }

    protected static double getChildAttackDamageBonus(DoubleSupplier randomDoubleGetter) {
        return (double)4f + randomDoubleGetter.getAsDouble() + randomDoubleGetter.getAsDouble() + randomDoubleGetter.getAsDouble();
    }

    protected static double getChildMovementSpeedBonus(DoubleSupplier randomDoubleGetter) {
        return ((double)0.5 + randomDoubleGetter.getAsDouble() * 0.25 + randomDoubleGetter.getAsDouble() * 0.25 + randomDoubleGetter.getAsDouble() * 0.25) * 0.3;
    }

    @Override
    public void tick() {
        super.tick();

        if(this.isCharging()) {
            if(!chargeAnimationState.isRunning()) {
                this.chargeAnimationState.start(this.age);
            }

            if(this.chargeTimeout <= maxChargeCooldown() - 10 && this.isOnGround()) {
                this.setCharging(false);
                this.setHasCharged(false);
            }
        }
    }

    @Override
    public InteractionResult interactMob(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        if(!this.level().isClientSide() && !player.isCreative()) {
            RaceType playerRace = RaceUtil.getRaceType(player);

            if(playerRace == RaceType.NONE || (this.getCompatibleRaces() != null && !this.getCompatibleRaces().contains(playerRace))) {
                return InteractionResult.FAIL;
            }
        }

        if(this.isTame()) {
            if (this.isBreedingItem(itemStack)) {
                if(this.getHealth() < this.getMaxHealth()) {
                    if (!player.isCreative()) itemStack.decrement(1);
                    net.minecraft.world.food.FoodProperties foodComponent = itemStack.getItem().getFoodProperties();
                    float f = foodComponent != null ? (float)foodComponent.getNutrition() : 1.0f;
                    this.heal(2.0f * f);
                    return InteractionResult.SUCCESS;
                }
                else if (!this.level().isClientSide && this.getAge() == 0 && this.canEat()) {
                    this.eat(player, hand, itemStack);
                    this.setInLove(player);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return super.interactMob(player, hand);
    }

    @Override
    @Nullable
    public AgeableMob createChild(ServerLevel world, AgeableMob entity) {
        WargEntity wargEntity = (WargEntity) entity;
        WargEntity wargEntity2 = EntitiesME.WARG.create(world);
        if (wargEntity2 != null) {
            int i = this.random.nextInt(9);
            WargVariant wargVariant = i < 4 ? this.getVariant() : (i < 8 ? wargEntity.getVariant() : Util.getRandom(WargVariant.values(), this.random));
            wargEntity2.setVariant(wargVariant);
            int j = this.random.nextInt(9);
            WargEyeVariant eyeVariant = j < 4 ? this.getEyeVariant() : (j < 8 ? wargEntity.getEyeVariant() : Util.getRandom(WargEyeVariant.values(), this.random));
            wargEntity2.setEyeVariant(eyeVariant);
            this.setChildAttributes(entity, wargEntity2);
        }
        return wargEntity2;
    }

    @Override
    protected void setChildAttributes(AgeableMob other, AbstractHorse child) {
        this.setChildAttribute(other, child, Attributes.MAX_HEALTH, MIN_HEALTH_BONUS, MAX_HEALTH_BONUS);
        this.setChildAttribute(other, child, Attributes.ATTACK_DAMAGE, MIN_ATTACK_DAMAGE_BONUS, MAX_ATTACK_DAMAGE_BONUS);
        this.setChildAttribute(other, child, Attributes.MOVEMENT_SPEED, MIN_MOVEMENT_SPEED_BONUS, MAX_MOVEMENT_SPEED_BONUS);
    }

    @Override
    public DispositionType getDisposition() {
        return DispositionType.EVIL;
    }

    @Override
    public List<RaceType> getCompatibleRaces() {
        return List.of(RaceType.SNAGA, RaceType.GOBLIN, RaceType.ORC, RaceType.URUK);
    }

    @Override
    public boolean usesTameness() {
        return false;
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return this.isBaby() ? BABY_BASE_DIMENSIONS : super.getDimensions(pose);
    }

    @Override
    public boolean canBreedWith(Animal other) {
        return other instanceof WargEntity;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.is(TagKey.create(Registries.ITEM, ResourceLocation.of(MiddleEarth.MOD_ID, "warg_food")));
    }

    @Override
    protected float getSaddledSpeed(Player controllingPlayer) {
        if(!this.isSitting()) {
            return controllingPlayer.isSprinting() ? ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED)) : ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.25f);
        }

        return super.getSaddledSpeed(controllingPlayer);
    }

    @Override
    protected float getNpcSaddledSpeed(NpcEntity controllingNpc) {
        if(!this.isSitting()) {
            return controllingNpc.isSprinting() ? ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED)) : ((float)this.getAttributeValue(Attributes.MOVEMENT_SPEED) * 0.25f);
        }

        return super.getNpcSaddledSpeed(controllingNpc);
    }

    @Override
    public boolean canSprintAsVehicle() {
        return true;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();

        if(this.isAggressive() || this.getAttacker() != null) {
            if(!this.isTame() || this.isTame() && this.getAttacker() != null) {
                this.setSitting(false);
            }

            if(!this.hasControllingPassenger()) {
                this.setRunning(true);
            }

            //this.idleAnimationTimeout = this.random.nextInt(600) + 1700;
        }

        if(!this.isAggressive() && this.getAttacker() == null) {
            this.setRunning(false);
        }

        if(this.isSitting()) {
            this.getNavigation().stop();
        }
    }

    @Override
    protected void setupAnimationStates() {
        this.idleAnimationState.startIfNotRunning(this.age);
        if(this.isSitting()) {
            if(!this.startSittingAnimationState.isRunning() && !this.sittingAnimationState.isRunning()) {
                this.startSittingAnimationState.startIfNotRunning(this.age);
            }
            if(this.startSittingAnimationState.getTimeRunning() > 2000) {
                this.sittingAnimationState.startIfNotRunning(this.age);
                this.startSittingAnimationState.stop();
            }
        }
        else if(this.startSittingAnimationState.isRunning() || this.sittingAnimationState.isRunning()) {
            this.startSittingAnimationState.stop();
            this.sittingAnimationState.stop();
            this.stopSittingAnimationState.startIfNotRunning(this.age);
        }
        if(this.stopSittingAnimationState.getTimeRunning() > 1500) {
            this.stopSittingAnimationState.stop();
        }
    }
    @Override
    public boolean isCommandItem(ItemStack stack) {
        return stack.is(TagKey.create(Registries.ITEM, ResourceLocation.of(MiddleEarth.MOD_ID, "bones")));
    }

    @Override
    public boolean isFoodItem(ItemStack itemStack) {
        return false;
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean result = super.tryAttack(target);
        if(result) {
            this.setRunning(true);
            this.level().sendEntityStatus(this, EntityEvent.PLAY_ATTACK_SOUND);
        }
        return result;
    }

    @Override
    public void chargeAttack() {
        if(!this.hasCharged()) {
            this.setHasCharged(true);
            if(!this.isTame() && !this.level().isClientSide) {
                if(targetDir == Vec3.ZERO && this.getTarget() != null) {
                    targetDir = new Vec3( this.getTarget().getBlockPos().getX() - this.getBlockPos().getX(),
                            this.getTarget().getBlockPos().getY() - this.getBlockPos().getY(),
                            this.getTarget().getBlockPos().getZ() - this.getBlockPos().getZ());
                }
                this.setVelocity(targetDir.multiply(1,0,1).normalize().add(0,0.6,0).multiply(0.7f));
            }
            else if (this.level().isClientSide) {
                this.setHasCharged(true);
                this.setVelocity(this.getRotationVector().multiply(1,0,1).normalize().add(0,0.35,0).multiply(1.3f));
            }
        }
        if(!this.isTame() && !this.level().isClientSide) {
            this.setYaw((float) Math.toDegrees(Math.atan2(-targetDir.x, targetDir.z)));
        }

        List<Entity> entities = this.level().getEntities(this, this.getBoundingBox().expand(0.2f, 0.0, 0.2f));

        Entity owner = this.getOwner();
        for(Entity entity : entities) {
            if((owner == null || entity.getUuid() != owner.getUuid()) && entity != this && !this.getPassengers().contains(entity) && !((entity instanceof WargEntity) && !this.isTame())) {
                if(getWorld() instanceof ServerLevel serverWorld)
                    entity.damage(entity.damageSources().mobAttack(this), this.getAttackDamage());

                if(entity instanceof ServerPlayer) {
                    entity.stopRiding();
                }
                else if(!this.level().isClientSide) {
                    entity.dismountVehicle();
                    entity.ejectPassengers();
                }

                this.setCharging(false);
                this.setHasCharged(false);
            }
        }
    }

    @Override
    public boolean shouldAttackWhenMounted() {
        return true;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damagePerDistance, DamageSource damageSource) {
        int i;
        if (fallDistance > 1.0f) {
            this.playSound(SoundEvents.ENTITY_WOLF_STEP, 2.5f, 0.7f);
        }
        if ((i = this.computeFallDamage(fallDistance, damagePerDistance)) <= 0) {
            return false;
        }
        this.damage(damageSource, i);
        if (this.isVehicle()) {
            if(getWorld() instanceof ServerLevel serverWorld){
                for (Entity entity : this.getIndirectPassengers()) {
                    entity.damage(damageSource, i);
                }
            }
        }
        this.playBlockFallSound();
        return true;    }

    //@Override
    public boolean isHorseArmor(ItemStack stack) {
        return stack.is(TagKey.create(Registries.ITEM, ResourceLocation.of(MiddleEarth.MOD_ID, "warg_armor")));
    }

    public boolean hasCharged() {
        return hasCharged;
    }

    public void setHasCharged(boolean hasCharged) {
        this.hasCharged = hasCharged;
    }

    @Override
    public boolean isBondingItem(ItemStack itemStack) {
        return itemStack.is(TagKey.create(Registries.ITEM, ResourceLocation.of(MiddleEarth.MOD_ID, "warg_food")));
    }

    @Override
    public int chargeDuration() {
        return 50;
    }

    @Override
    public int maxChargeCooldown() {
        return 200;
    }

    @Override
    public boolean canCarryChest() {
        return false;
    }

    /* VARIANTS */
    public SpawnGroupData initialize(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason,
                                 @Nullable SpawnGroupData entityData) {
        WargVariant variant = Util.getRandom(WargVariant.values(), this.random);
        setVariant(variant);
        WargEyeVariant eyeVariant = Util.getRandom(WargEyeVariant.values(), this.random);
        setEyeVariant(eyeVariant);
        return super.initialize(world, difficulty, spawnReason, entityData, null);
    }

    public WargVariant getVariant() {
        return WargVariant.byId(this.getTypeVariant() & 255);
    }

    public WargEyeVariant getEyeVariant() {
        return WargEyeVariant.byId(this.getEyeTypeVariant() & 255);
    }
    private int getTypeVariant() {
        return this.entityData.get(VARIANT);
    }

    private int getEyeTypeVariant() {
        return this.entityData.get(EYE_VARIANT);
    }
    private void setVariant(WargVariant variant) {
        this.entityData.set(VARIANT, variant.getId() & 255);
    }

    private void setEyeVariant(WargEyeVariant variant) {
        this.entityData.set(EYE_VARIANT, variant.getId() & 255);
    }
    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WOLF_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WOLF_HURT;
    }
    @Override
    protected void playHurtSound(DamageSource damageSource) {
        this.playSound(this.getHurtSound(damageSource), 1.0f, 0.7f);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WOLF_AMBIENT;

    }

    @Override
    public void playAmbientSound() {
        this.playSound(this.getAmbientSound(), 1.0f, 0.7f);
    }

    @Nullable
    @Override
    public SoundEvent getAmbientStandSound() {
        return SoundEvents.ENTITY_WOLF_PANT;
    }

    @Nullable
    @Override
    protected SoundEvent getAngrySound() {
        return SoundEvents.ENTITY_WOLF_GROWL;
    }

    @Override
    public void playAngrySound() {
        this.playSound(this.getAngrySound(), 1.0f, 0.7f);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15f, 0.7f);
    }

    @Override
    protected void playWalkSound(SoundType group) {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 1.0f, 0.7f);
    }

    @Override
    protected void playJumpSound() {
        this.playSound(SoundEvents.ENTITY_WOLF_STEP, 1.1f, 0.7f);
    }
}
