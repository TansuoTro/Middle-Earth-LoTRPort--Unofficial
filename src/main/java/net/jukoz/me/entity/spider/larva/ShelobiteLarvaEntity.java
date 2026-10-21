package net.jukoz.me.entity.spider.larva;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.jukoz.me.compat.nbt.ReadView;
import net.jukoz.me.compat.nbt.WriteView;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.TrackedDataHandlerRegistryME;
import net.jukoz.me.entity.VariantsME;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntity;
import net.jukoz.me.entity.goals.FollowDifferentMobGoal;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.entity.spider.SpiderVariant;
import net.jukoz.me.entity.spider.scuttler.ShelobiteScuttlerEntity;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.registries.content.spidervariants.SpiderVariantRegistry;
import net.jukoz.me.utils.SpawnUtil;
import org.jetbrains.annotations.Nullable;
import java.util.Optional;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class ShelobiteLarvaEntity extends Monster {
    public static final int CLIMBING_TIME_TRANSITION = 12;
    public static final float MOVEMENT_SPEED = 1f;
    private static final EntityDataAccessor<Byte> SPIDER_FLAGS;
    private static final EntityDataAccessor<Boolean> ATTACK_FLAG;
    private static final EntityDataAccessor<Holder<SpiderVariant>> VARIANT;

    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState walkingAnimation = new AnimationState();
    public final AnimationState biteAnimation = new AnimationState();

    private int climbingTicks = 0;

    public ShelobiteLarvaEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Monster.createHostileAttributes()
                .add(Attributes.GENERIC_MAX_HEALTH, 8.0)
                .add(Attributes.GENERIC_MOVEMENT_SPEED, 0.27)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 24.0);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new FloatGoal(this));
        this.goalSelector.add(3, new MeleeAttackGoal(this, MOVEMENT_SPEED , false));
        this.goalSelector.add(4, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.add(5, new FollowDifferentMobGoal<ShelobiteScuttlerEntity>(this,
                ShelobiteScuttlerEntity.class, 1.0, 7, 16));
        this.goalSelector.add(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.add(6, new RandomLookAroundGoal(this));
        this.targetSelector.add(1, new HurtByTargetGoal(this));
        this.targetSelector.add(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.add(3, new NearestAttackableTargetGoal<>(this, NpcEntity.class, true));
        this.targetSelector.add(4, new NearestAttackableTargetGoal<>(this, CaveTrollEntity.class, true));
    }

    @Nullable
    @Override
    public SpawnGroupData initialize(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag nbt) {
        if (entityData instanceof ShelobiteScuttlerEntity.SpiderData spiderData) {
            this.setVariant(spiderData.variant);
        } else {
            Optional<Holder<SpiderVariant>> optional = VariantsME.select(world,
                    VariantsME.registry(world.toServerWorld().getRegistryManager(), DynamicRegistriesME.SPIDER_VARIANTS));
            if (optional.isPresent()) {
                this.setVariant(optional.get());
                entityData = new ShelobiteScuttlerEntity.SpiderData(optional.get());
            }
        }
        return super.initialize(world, difficulty, spawnReason, entityData, nbt);
    }

    public double getMountedHeightOffset() {
        return (double)(this.getHeight() * 0.5F);
    }

    @Override
    protected PathNavigation createNavigation(Level world) {
        return new WallClimberNavigation(this, world);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        Holder<SpiderVariant> spiderVariantRegistryEntry = VariantsME.getOrDefaultOrThrow(this.getWorld().getRegistryManager(), SpiderVariantRegistry.DEFAULT);
        this.dataTracker.startTracking(VARIANT, spiderVariantRegistryEntry);
        this.dataTracker.startTracking(ATTACK_FLAG, false);
        this.dataTracker.startTracking(SPIDER_FLAGS, (byte)0);
    }

    protected void setupAnimationStates() {
        if (!this.idleAnimation.isRunning()) {
            this.idleAnimation.start(this.age);
        }
        boolean attackState = this.dataTracker.get(ATTACK_FLAG);
        if(attackState) {
            this.biteAnimation.stop();
            this.biteAnimation.start(this.age);
            this.dataTracker.set(ATTACK_FLAG, false);
        }
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean result = super.tryAttack(target);
        this.dataTracker.set(ATTACK_FLAG, result);
        return result;
    }

    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            this.setClimbingWall(this.horizontalCollision);
        } else {
            setupAnimationStates();
        }

    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if(isClimbingWall()) {
            this.climbingTicks = Math.min(CLIMBING_TIME_TRANSITION, this.climbingTicks + 1);
        } else {
            this.climbingTicks = Math.max(0, this.climbingTicks - 1);
        }
    }

    @Override
    public int getXpToDrop() {
        return 1;
    }

    public SpiderVariant getVariant() {
        return getRegistryVariant().value();
    }

    private Holder<SpiderVariant> getRegistryVariant() {
        return this.dataTracker.get(VARIANT);
    }

    private void setVariant(Holder<SpiderVariant> variant) {
        this.dataTracker.set(VARIANT, variant);
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_SPIDER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SPIDER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SPIDER_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
    }

    public boolean isClimbing() {
        return this.isClimbingWall();
    }

    public void slowMovement(BlockState state, Vec3 multiplier) {
        if (!state.isIn(TagKey.of(Registries.BLOCK, new ResourceLocation(MiddleEarth.MOD_ID, "cobwebs")))) {
            super.slowMovement(state, multiplier);
        }
    }

    // Immune to Poison
    public boolean canHaveStatusEffect(MobEffectInstance effect) {
        return effect.getEffectType() != MobEffects.POISON && super.canHaveStatusEffect(effect);
    }

    public boolean isClimbingWall() {
        return (this.dataTracker.get(SPIDER_FLAGS) & 1) != 0;
    }

    public boolean isCollidingWall() {
        return this.horizontalCollision;
    }

    public void setClimbingWall(boolean climbing) {
        byte b = (Byte)this.dataTracker.get(SPIDER_FLAGS);
        if (climbing) {
            b = (byte)(b | 1);
        } else {
            b &= -2;
        }

        this.dataTracker.set(SPIDER_FLAGS, b);
    }

    public int getClimbingTicks() {
        return this.climbingTicks;
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
        VariantsME.readVariantFromNbt(view, this.getWorld().getRegistryManager(), DynamicRegistriesME.SPIDER_VARIANTS).ifPresent(this::setVariant);
    }

    static {
        SPIDER_FLAGS = SynchedEntityData.registerData(ShelobiteLarvaEntity.class, EntityDataSerializers.BYTE);
        ATTACK_FLAG = SynchedEntityData.registerData(ShelobiteLarvaEntity.class, EntityDataSerializers.BOOLEAN);
        VARIANT = SynchedEntityData.registerData(ShelobiteLarvaEntity.class, TrackedDataHandlerRegistryME.SPIDER_VARIANT);
    }

    public static boolean canSpawn(EntityType<ShelobiteLarvaEntity> type, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos blockPos, RandomSource random) {
        if(spawnReason != MobSpawnType.NATURAL)
            return ShelobiteLarvaEntity.canSpawnInDark(type, serverWorldAccess, spawnReason, blockPos, random);
        return SpawnUtil.canSpawn(blockPos, serverWorldAccess, spawnReason);
    }
}
