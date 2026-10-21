package net.jukoz.me.entity.beasts.great_horn;
import net.jukoz.me.entity.EntityAttributesME;

import net.minecraft.world.level.block.state.BlockState;

import net.minecraft.item.FoodComponent;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.passive.*;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.Holder;
import net.minecraft.tags.BiomeTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.jukoz.me.compat.nbt.ReadView;
import net.jukoz.me.compat.nbt.WriteView;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccess;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.entity.EntitiesME;
import net.jukoz.me.entity.TrackedDataHandlerRegistryME;
import net.jukoz.me.entity.VariantsME;
import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.jukoz.me.entity.beasts.trolls.stone.StoneTrollEntity;
import net.jukoz.me.entity.beasts.warg.WargEntity;
import net.jukoz.me.entity.goals.BowAtEntityGoal;
import net.jukoz.me.entity.goals.ChargeAttackGoal;
import net.jukoz.me.entity.goals.SmartFleeEntityGoal;
import net.jukoz.me.entity.goals.interfaces.Evader;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.registries.content.greathornvariants.GreatHornVariantRegistry;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.jukoz.me.resources.datas.common.RaceType;
import net.jukoz.me.resources.datas.races.RaceUtil;
import net.jukoz.me.sound.SoundsME;
import net.jukoz.me.utils.ItemTagsME;
import net.jukoz.me.utils.SpawnUtil;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.DoubleSupplier;
import java.util.function.IntUnaryOperator;

public class GreatHornEntity extends AbstractBeastEntity implements Evader {
    private static final int HORNS_ATTACK_COOLDOWN = 50;
    private static final float MIN_MOVEMENT_SPEED_BONUS = (float) GreatHornEntity.getChildMovementSpeedBonus(() -> 0.0);
    private static final float MAX_MOVEMENT_SPEED_BONUS = (float) GreatHornEntity.getChildMovementSpeedBonus(() -> 1.0);
    private static final float MIN_HEALTH_BONUS = GreatHornEntity.getChildHealthBonus(max -> 0);
    private static final float MAX_HEALTH_BONUS = GreatHornEntity.getChildHealthBonus(max -> max - 1);
    private static final TrackedData<Holder<GreatHornVariant>> VARIANT = DataTracker.registerData(GreatHornEntity.class, TrackedDataHandlerRegistryME.GREAT_HORN_VARIANT);;
    private static final TrackedData<Integer> BOW = DataTracker.registerData(GreatHornEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> ATTACK = DataTracker.registerData(GreatHornEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Boolean> BLUE_SADDLE = DataTracker.registerData(GreatHornEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> MOUNTABLE = DataTracker.registerData(GreatHornEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    private static final TrackedData<Boolean> EVADING = DataTracker.registerData(GreatHornEntity.class, TrackedDataHandlerRegistry.BOOLEAN);
    public final AnimationState earWigglingAnimationState = new AnimationState();
    public final AnimationState gallopAnimationState = new AnimationState();
    public final AnimationState bowAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private static final EntityDimensions BABY_BASE_DIMENSIONS = EntitiesME.GREAT_HORN.getDimensions().scaled(0.5f);
    protected int attackAnimationCooldown = 0;
    protected int bowAnimationTimeout = 0;

    public GreatHornEntity(EntityType<? extends AbstractBeastEntity> entityType, Level world) {
        super(entityType, world);
        idleAnimationTimeout = 200;
    }

    public static DefaultAttributeContainer.Builder setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(Attributes.GENERIC_MAX_HEALTH, 50.0d)
                .add(Attributes.GENERIC_KNOCKBACK_RESISTANCE, 0.3d)
                .add(Attributes.GENERIC_ATTACK_SPEED, 1.0d)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 38.0d)
                .add(Attributes.GENERIC_ATTACK_DAMAGE, 4.0d)
                .add(EntityAttributesME.WATER_MOVEMENT_EFFICIENCY.value(), 0.5f)
                .add(EntityAttributesME.SAFE_FALL_DISTANCE.value(), 7.0d)
                .add(Attributes.HORSE_JUMP_STRENGTH, 0.75d);
    }

    @Override
    protected void initAttributes(RandomSource random) {
        this.getAttributeInstance(Attributes.GENERIC_MAX_HEALTH).setBaseValue(this.getChildHealthBonus(random::nextInt));
        this.getAttributeInstance(Attributes.GENERIC_MOVEMENT_SPEED).setBaseValue(this.getChildMovementSpeedBonus(random::nextDouble));
        this.getAttributeInstance(Attributes.HORSE_JUMP_STRENGTH).setBaseValue(this.getChildJumpStrengthBonus(random::nextDouble));
    }


    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new SwimGoal(this));
        this.goalSelector.add(2, new BowAtEntityGoal(this, Player.class, 16, (livingEntity -> {
            return this.shouldBow((Player) livingEntity);
        }) ));
        this.goalSelector.add(3, new SmartFleeEntityGoal<Player>(this, (Evader) this,
                Player.class, 20.0F, 1.6, 1.9, (entity) -> {
            return !this.canTrust((Player)entity);
        }));
        this.goalSelector.add(4, new SmartFleeEntityGoal<WargEntity>(this, (Evader) this,
                WargEntity.class, 20.0F, 1.7, 2.0, (entity) -> true));
        this.goalSelector.add(4, new ChargeAttackGoal(this, maxChargeCooldown()));
        this.goalSelector.add(5, new AnimalMateGoal(this, 1.5));
        this.goalSelector.add(6, new TemptGoal(this, 1.0, Ingredient.fromTag(ItemTagsME.ELK_FOOD), false));
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0));
        this.goalSelector.add(8, new LookAtEntityGoal(this, Player.class, 6.0f));
        this.goalSelector.add(9, new LookAroundGoal(this));
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        Holder<GreatHornVariant> greatHornVariantRegistryEntry = VariantsME.getOrDefaultOrThrow(this.getWorld().getRegistryManager(), GreatHornVariantRegistry.DEFAULT);
        this.dataTracker.startTracking(BOW, 0);
        this.dataTracker.startTracking(BLUE_SADDLE, false);
        this.dataTracker.startTracking(MOUNTABLE, true);
        this.dataTracker.startTracking(EVADING, false);
        this.dataTracker.startTracking(ATTACK, 0);
        this.dataTracker.startTracking(VARIANT, greatHornVariantRegistryEntry);
    }

    @Override
    public void writeCustomDataToNbt(CompoundTag nbt) {
        super.writeCustomDataToNbt(nbt);
        WriteView view = new WriteView(nbt);
        VariantsME.writeVariantToNbt(view, this.getRegistryVariant());
    }

    @Override
    public void readCustomDataFromNbt(CompoundTag nbt) {
        super.readCustomDataFromNbt(nbt);
        ReadView view = new ReadView(nbt);
        VariantsME.readVariantFromNbt(view, this.getWorld().getRegistryManager(), DynamicRegistriesME.GREAT_HORN_VARIANTS).ifPresent(this::setVariant);
        this.dataTracker.set(MOUNTABLE, ModServerConfigs.ENABLE_MOUNT_BROADHOOF_GOAT);
    }

    protected static float getChildHealthBonus(IntUnaryOperator randomIntGetter) {
        return 20.0f + (float)randomIntGetter.applyAsInt(8) + (float)randomIntGetter.applyAsInt(8);
    }

    protected static double getChildJumpStrengthBonus(DoubleSupplier randomDoubleGetter) {
        return 0;
    }

    protected static double getChildMovementSpeedBonus(DoubleSupplier randomDoubleGetter) {
        return ((double)0.4f + randomDoubleGetter.getAsDouble() * 0.25 + randomDoubleGetter.getAsDouble() * 0.25 + randomDoubleGetter.getAsDouble() * 0.2) * 0.27;
    }

    @Override
    public DispositionType getDisposition() {
        return DispositionType.GOOD;
    }

    @Override
    public List<RaceType> getCompatibleRaces() {
        return List.of(RaceType.ELF);
    }

    @Override
    public boolean usesTameness() {
        return false;
    }

    public boolean hasBlueSaddle() {
        return this.dataTracker.get(BLUE_SADDLE);
    }

    @Override
    public InteractionResult interactMob(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getStackInHand(hand);

        if(!this.getWorld().isClient() && !player.isCreative()) {
            RaceType playerRace = RaceUtil.getRaceType(player);

            if(playerRace == null || playerRace == RaceType.NONE || (this.getCompatibleRaces() != null && !this.getCompatibleRaces().contains(playerRace))) {
                return InteractionResult.FAIL;
            }
        }

        if(this.isTame() && this.isTamable(player)) {
            if (this.isBreedingItem(itemStack)) {
                if(this.getHealth() < this.getMaxHealth()) {
                    if (!player.getAbilities().creativeMode) itemStack.decrement(1);
                    FoodComponent foodComponent = itemStack.getItem().getFoodComponent();
                    float f = foodComponent != null ? (float)foodComponent.getHunger() : 1.0f;
                    this.heal(2.0f * f);
                    return InteractionResult.SUCCESS;
                }
                else if (!this.getWorld().isClient && this.getBreedingAge() == 0 && this.canEat()) {
                    this.eat(player, hand, itemStack);
                    this.lovePlayer(player);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        if(itemStack.getItem().equals(Items.BLUE_DYE) && !this.dataTracker.get(BLUE_SADDLE)) {
            this.dataTracker.set(BLUE_SADDLE, true);
        } else if(itemStack.getItem().equals(Items.RED_DYE) && this.dataTracker.get(BLUE_SADDLE)) {
            this.dataTracker.set(BLUE_SADDLE, false);
        }

        return super.interactMob(player, hand);
    }

    @Override
    public boolean canCarryChest() {
        return false;
    }

    @Override
    protected void updatePassengerPosition(Entity passenger, Entity.PositionUpdater positionUpdater) {
        super.updatePassengerPosition(passenger, positionUpdater);
        float f = this.limbAnimator.getPos() / 20;
        float g = this.limbAnimator.getPos() * (Mth.PI / 180) * 18;

        double y = 0.45;
        if(gallopAnimationState.isRunning()) {
            y += -0.025 + Mth.cos((f/0.75f) * (Mth.PI*2)) * 0.15;
        } else {
            y += Mth.cos(g - Mth.PI) * 0.02 - 0.2;
        }

        passenger.setPos(passenger.getX(), passenger.getY() + y, passenger.getZ());
    }

    @Override
    @Nullable
    public PassiveEntity createChild(ServerLevel world, PassiveEntity entity) {
        GreatHornEntity greatHornEntity2 = EntitiesME.GREAT_HORN.create(world);
        if (greatHornEntity2 != null) {
            this.setChildAttributes(entity, greatHornEntity2);
            if (this.random.nextBoolean()) {
                greatHornEntity2.setVariant(this.getRegistryVariant());
            } else {
                greatHornEntity2.setVariant(((GreatHornEntity)entity).getRegistryVariant());
            }
        }
        return greatHornEntity2;
    }

    @Override
    protected void setChildAttributes(PassiveEntity other, AbstractHorseEntity child) {
        this.setChildAttribute(other, child, Attributes.GENERIC_MAX_HEALTH, MIN_HEALTH_BONUS, MAX_HEALTH_BONUS);
        this.setChildAttribute(other, child, Attributes.GENERIC_MOVEMENT_SPEED, MIN_MOVEMENT_SPEED_BONUS, MAX_MOVEMENT_SPEED_BONUS);
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return this.isBaby() ? BABY_BASE_DIMENSIONS : super.getDimensions(pose);
    }

    @Override
    protected boolean isTamable(Player player) {
        return this.isMountable();
    }

    @Override
    public boolean canBreedWith(AnimalEntity other) {
        return other instanceof GreatHornEntity;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.isIn(ItemTagsME.ELK_FOOD);
    }

    @Override
    protected void jump(float strength, Vec3 movementInput) {
        if(this.chargeTimeout <= 0 && this.hasControllingPassenger()
                && this.getControllingPassenger().isSprinting()) {
            this.setCharging(true);
            this.chargeTimeout = maxChargeCooldown();
            float entitySpeed = (float) this.getAttributeInstance(Attributes.GENERIC_MOVEMENT_SPEED).getValue();
            if(!this.getWorld().isClient) {
                Vec2f vec2f = this.getControlledRotation(this.getControllingPassenger());
                this.setVelocity(new Vec3(vec2f.x,0,vec2f.y).normalize().add(0,0.4,0).multiply(strength * (1.4f + entitySpeed)));
            }
            else if (this.getWorld().isClient) {
                this.setVelocity(this.getRotationVector().multiply(1,0,1).normalize().add(0,0.4,0).multiply(strength * (1.4f + entitySpeed)));
            }
            this.chargeAnimationState.startIfNotRunning(this.age);
        }
    }

    @Override
    public void startJumping(int height) {
        if(this.hasControllingPassenger()) {
            this.jumping = true;
            this.playJumpSound();
            float jumpPercentage = (float)height/100;
            if(!this.getControllingPassenger().isSprinting()) {
                this.setChargeTimeout(HORNS_ATTACK_COOLDOWN);
                dataTracker.set(ATTACK, HORNS_ATTACK_COOLDOWN);
                attackAnimationCooldown = HORNS_ATTACK_COOLDOWN;
                List<Entity> entities = this.getWorld().getOtherEntities(this, this.getBoundingBox().expand(2.5,2,2.5));

                for(Entity entity : entities) {
                    if(!this.getPassengerList().contains(entity)) {
                        if(!this.getWorld().isClient()) {
                            entity.damage(entity.getDamageSources().mobAttack(this), jumpPercentage * getAttackDamage());
                        }
                        double dx = entity.getX() - this.getX();
                        double dz = entity.getZ() - this.getZ();

                        Vec3 velocity = new Vec3(dx, 1.25f + getRandom().nextFloat() * 0.5f, dz).normalize();
                        velocity.multiply(jumpPercentage);
                        entity.addVelocity(velocity);

                        this.setCharging(false);
                    }
                }
            } else {
                this.playSound(SoundsME.GREAT_HORN_CALL, 1.0f, 1.0f);
            }
        }
        else {
            super.startJumping(height);
        }
    }

    @Override
    public int getJumpCooldown() {
        return Math.max(super.getJumpCooldown(), this.dataTracker.get(ATTACK));
    }

    @Override
    public int maxChargeCooldown() {
        return 80;
    }

    @Override
    public int chargeDuration() {
        return 16;
    }

    @Override
    public void tick() {
        super.tick();

        if(this.isCharging()) {
            if(this.chargeTimeout <= maxChargeCooldown() - 10 && this.isOnGround()) {
                this.setCharging(false);
                this.setHasCharged(false);
            }
        }

        if(bowAnimationTimeout > 0) {
            bowAnimationTimeout = Math.max(bowAnimationTimeout - 1, 0);
            if(bowAnimationTimeout == 0) {
                dataTracker.set(BOW, -1);
            }
        }
        if(attackAnimationCooldown > 0) {
            attackAnimationCooldown = Math.max(attackAnimationCooldown - 1, 0);
            dataTracker.set(ATTACK, attackAnimationCooldown);
        }
        if (this.getWorld().isClient && bowAnimationState.isRunning()) {
            if(random.nextInt(2) == 0) {
                Vector3f randPos = new Vector3f(this.random.nextFloat()*6 - 3f, this.random.nextFloat()*1.25f, this.random.nextFloat()*6 - 3f);
                this.getWorld().addParticle(ParticleTypes.INSTANT_EFFECT, this.getX() + randPos.x, this.getY() + randPos.y, this.getZ() + randPos.z,
                        0.0, 0.75f + this.random.nextFloat(), 0.0);
            }
        }
    }

    public void slowMovement(BlockState state, Vec3 multiplier) {
        float pow = 0.1f;
        Vec3 lessPenalty = new Vec3(Math.pow(multiplier.x, pow), Math.pow(multiplier.y, pow), Math.pow(multiplier.z, pow));
        super.slowMovement(state, lessPenalty);
    }

    protected void setupAnimationStates() {
        this.idleAnimationState.startIfNotRunning(this.age);

        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.earWigglingAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }

        int bowState = dataTracker.get(BOW);
        if(bowState == 1) {
            this.bowAnimationState.startIfNotRunning(this.age);
            dataTracker.set(BOW, 0);
        } else if(bowState == -1) {
            this.bowAnimationState.stop();
            dataTracker.set(BOW, 0);
        }

        int attack = dataTracker.get(ATTACK);
        if(attack == HORNS_ATTACK_COOLDOWN) {
            this.attackAnimationState.start(this.age);
        } else if(attack == 0) {
            this.attackAnimationState.stop();
        }

        if(hasControllingPassenger()) {
            if((getControllingPassenger().isSprinting())) {
                this.gallopAnimationState.startIfNotRunning(this.age);
            } else {
                this.gallopAnimationState.stop();
            }
        } else if(this.dataTracker.get(EVADING)) {
            this.gallopAnimationState.startIfNotRunning(this.age);
        } else {
            this.gallopAnimationState.stop();
        }
    }

    @Override
    public void startFlee() {
        this.dataTracker.set(EVADING, true);
    }

    @Override
    public void stopFlee() {
        this.dataTracker.set(EVADING, false);
    }

    @Override
    public boolean isCommandItem(ItemStack stack) {
        return stack.isOf(Items.STICK);
    }

    @Override
    protected float getSaddledSpeed(Player controllingPlayer) {
        float speed = ((float)this.getAttributeValue(Attributes.GENERIC_MOVEMENT_SPEED));
        if(this.getWorld().getBiome(this.getBlockPos()).isIn(BiomeTags.IS_FOREST)) {
            speed *= 1.1f;
        }
        if (controllingPlayer.isSprinting()) {
            return speed;
        } else {
            return speed * 0.5f;
        }
    }

    @Override
    public boolean canSprintAsVehicle() {
        return true;
    }

    public boolean canTrust(Player playerEntity) {
        RaceType playerRace = RaceUtil.getRaceType(playerEntity);
        return (playerRace != null && playerRace != RaceType.NONE) && (this.getCompatibleRaces() != null && this.getCompatibleRaces().contains(playerRace));
    }

    public boolean shouldBow(Player playerEntity) {
        return isOwner(playerEntity) && bowAnimationTimeout > 0;
    }

    public boolean isOwner(Player playerEntity) {
        Player owner = this.getOwner();
        return (owner != null && owner.getUuid().equals(playerEntity.getUuid()));
    }

    @Override
    public boolean isBondingItem(ItemStack itemStack) {
        return itemStack.isIn(ItemTagsME.ELK_FOOD);
    }

    @Override
    public boolean isFoodItem(ItemStack itemStack) {
        return false;
    }

    @Override
    public void setOwnerUuid(@Nullable UUID uuid) {
        super.setOwnerUuid(uuid);
        if (uuid != null) {
            this.dataTracker.set(BOW, 1);
            bowAnimationTimeout = 80;
        }
    }

    /* VARIANTS */
    public EntityData initialize(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason,
                                 @Nullable EntityData entityData, @Nullable CompoundTag nbt) {
        if (entityData instanceof GreatHornData greatHornData) {
            this.setVariant(greatHornData.variant);
        } else {
            Optional<Holder<GreatHornVariant>> optional = VariantsME.select(world, VariantsME.registry(world.toServerWorld().getRegistryManager(), DynamicRegistriesME.GREAT_HORN_VARIANTS));
            if (optional.isPresent()) {
                this.setVariant(optional.get());
                entityData = new GreatHornData(optional.get());
            }
        }
        return super.initialize(world, difficulty, spawnReason, entityData, nbt);
    }

    private void setVariant(Holder<GreatHornVariant> variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    public GreatHornVariant getVariant() {
        return getRegistryVariant().value();
    }

    private Holder<GreatHornVariant> getRegistryVariant() {
        return this.dataTracker.get(VARIANT);
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundsME.GREAT_HORN_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundsME.GREAT_HORN_HURT;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return SoundsME.GREAT_HORN_IDLE;
    }

    @Override
    public void playAmbientSound() {
        this.playSound(this.getAmbientSound(), 1.0f, 0.7f);
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_GOAT_STEP, 0.15f, 0.7f);
    }

    @Override
    protected void playWalkSound(BlockSoundGroup group) {
        this.playSound(SoundEvents.ENTITY_GOAT_STEP, 1.0f, 0.7f);
    }

    @Override
    protected void playJumpSound() {
        this.playSound(SoundEvents.ENTITY_GOAT_LONG_JUMP, 1.0f, 0.7f);
    }

    public static class GreatHornData extends PassiveEntity.PassiveData {
        public final Holder<GreatHornVariant> variant;

        public GreatHornData(Holder<GreatHornVariant> variant) {
            super(0.075f);
            this.variant = variant;
        }
    }

    public static boolean canSpawn(EntityType<GreatHornEntity> type, ServerWorldAccess serverWorldAccess, SpawnReason spawnReason, BlockPos blockPos, RandomSource random) {
        return SpawnUtil.canSpawn(blockPos, serverWorldAccess, spawnReason);
    }

    @Override
    public boolean canSpawn(WorldAccess world, SpawnReason spawnReason) {
        return true;
    }
}
