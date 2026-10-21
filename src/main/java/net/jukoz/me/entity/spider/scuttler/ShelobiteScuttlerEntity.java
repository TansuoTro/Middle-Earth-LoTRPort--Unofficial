package net.jukoz.me.entity.spider.scuttler;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Dynamic;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
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
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.nbt.CompoundTag;
import net.jukoz.me.compat.nbt.ReadView;
import net.jukoz.me.compat.nbt.WriteView;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccess;
import net.minecraft.world.level.biome.Biome;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.TrackedDataHandlerRegistryME;
import net.jukoz.me.entity.VariantsME;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntity;
import net.jukoz.me.entity.goals.SpiderPonceAtTargetGoal;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.entity.spider.Pouncer;
import net.jukoz.me.entity.spider.SpiderVariant;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.registries.content.spidervariants.SpiderVariantRegistry;
import net.jukoz.me.resources.datas.biome_events.BiomeEventDataLookup;
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

public class ShelobiteScuttlerEntity extends Monster implements Pouncer {
    public static final int CLIMBING_TIME_TRANSITION = 12;
    public static final int LEAPING_TIME_TRANSITION = 8;
    public static final float MOVEMENT_SPEED = 1.15f;
    private static final EntityDataAccessor<Byte> SPIDER_FLAGS;
    private static final EntityDataAccessor<Integer> BITE_FLAG;
    private static final EntityDataAccessor<Integer> POUNCE_FLAG;
    private static final EntityDataAccessor<Holder<SpiderVariant>> VARIANT;

    // region Brain
    protected static final ImmutableList<SensorType<? extends Sensor<? super ShelobiteScuttlerEntity>>> SENSOR_TYPES = ImmutableList.of(
            SensorType.NEAREST_LIVING_ENTITIES, SensorType.NEAREST_PLAYERS, SensorType.NEAREST_ITEMS, SensorType.HURT_BY
    );
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULE_TYPES = ImmutableList.of(
            MemoryModuleType.LOOK_TARGET,
            MemoryModuleType.MOBS,
            MemoryModuleType.VISIBLE_MOBS,
            MemoryModuleType.NEAREST_VISIBLE_PLAYER,
            MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER,
            MemoryModuleType.HURT_BY,
            MemoryModuleType.HURT_BY_ENTITY,
            MemoryModuleType.WALK_TARGET,
            MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
            MemoryModuleType.ATTACK_TARGET,
            MemoryModuleType.ATTACK_COOLING_DOWN,
            MemoryModuleType.INTERACTION_TARGET,
            MemoryModuleType.PATH,
            MemoryModuleType.ANGRY_AT,
            MemoryModuleType.NEAREST_VISIBLE_NEMESIS,
            MemoryModuleType.HOME,
            MemoryModuleType.LONG_JUMP_COOLING_DOWN,
            MemoryModuleType.LONG_JUMP_MID_JUMP
    );
    // endregion

    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState walkingAnimation = new AnimationState();
    public final AnimationState biteAnimation = new AnimationState();
    public final AnimationState pounceAnimation = new AnimationState();

    private int climbingTicks = 0;
    private int leapingTicks = 0;
    private int biteAnimationCooldown = 0;

    public ShelobiteScuttlerEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Monster.createHostileAttributes()
                .add(Attributes.GENERIC_MAX_HEALTH, 16.0)
                .add(Attributes.GENERIC_MOVEMENT_SPEED, 0.34)
                .add(Attributes.GENERIC_ATTACK_DAMAGE, 4)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 36.0);
    }

    @Nullable
    @Override
    public SpawnGroupData initialize(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag nbt) {
        if (entityData instanceof SpiderData spiderData) {
            this.setVariant(spiderData.variant);
        } else {
            Optional<Holder<SpiderVariant>> optional = VariantsME.select(world,
                    VariantsME.registry(world.toServerWorld().getRegistryManager(), DynamicRegistriesME.SPIDER_VARIANTS));
            if (optional.isPresent()) {
                this.setVariant(optional.get());
                entityData = new SpiderData(optional.get());
            }
        }
        return super.initialize(world, difficulty, spawnReason, entityData, nbt);
    }

    @Override
    public boolean isPersistent() {
        return false;
    }

    @Override
    protected Brain.Profile<ShelobiteScuttlerEntity> createBrainProfile() {
        return Brain.createProfile(MEMORY_MODULE_TYPES, SENSOR_TYPES);
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return ShelobiteScuttlerBrain.create(this, this.createBrainProfile().deserialize(dynamic));
    }

    protected void initGoals() {
        this.goalSelector.add(1, new FloatGoal(this));
        this.goalSelector.add(3, new SpiderPonceAtTargetGoal(this, this,
                0.45F, 0.25f, 4, 14, 17));
        this.goalSelector.add(4, new MeleeAttackGoal(this, MOVEMENT_SPEED , false));
        this.goalSelector.add(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.add(6, new RandomLookAroundGoal(this));
        this.targetSelector.add(1, new HurtByTargetGoal(this));
        this.targetSelector.add(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.add(3, new NearestAttackableTargetGoal<>(this, NpcEntity.class, true));
        this.targetSelector.add(4, new NearestAttackableTargetGoal<>(this, CaveTrollEntity.class, true));
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
        this.dataTracker.startTracking(SPIDER_FLAGS, (byte)0);
        this.dataTracker.startTracking(BITE_FLAG, 0);
        this.dataTracker.startTracking(POUNCE_FLAG, 0);
    }

    //@Override
    //public Brain<MirkwoodSpiderEntity> getBrain() {
    //    return (Brain<MirkwoodSpiderEntity>) super.getBrain();
    //}

    protected void setupAnimationStates() {
        if (!this.idleAnimation.isRunning()) {
            this.idleAnimation.start(this.age);
        }
        if (!this.walkingAnimation.isRunning()) {
            this.walkingAnimation.start(this.age);
        }

        setTrackerState(BITE_FLAG, biteAnimation);
        setTrackerState(POUNCE_FLAG, pounceAnimation);
    }

    protected void setTrackerState(EntityDataAccessor<Integer> trackedData, AnimationState animationState) {
        int state = this.dataTracker.get(trackedData);
        if(state == 1) {
            animationState.start(this.age);
        } else if (state == -1) {
            animationState.stop();
        }
        this.dataTracker.set(trackedData, 0);
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.dataTracker.set(BITE_FLAG, 1);
        if(biteAnimationCooldown == 0) biteAnimationCooldown = 40;
        return super.tryAttack(target);
    }

    @Override
    public void onLanding() {
        if (this.getWorld() instanceof ServerLevel serverWorld) {
            if (this.isOnGround() && this.fallDistance > 1.5) {
                Vec3 vec3d = getPos().add(0.0, 0.5, 0.0);
                BlockState blockState = this.getSteppingBlockState();
                int count = (int) Mth.clamp(20.0 * this.fallDistance - 1, 0.0, 120.0);
                serverWorld.spawnParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), vec3d.x, vec3d.y, vec3d.z,
                        count, this.random.nextDouble() - 0.5, 0.15, this.random.nextDouble() - 0.5, 0.16f);
            }
        }
        super.onLanding();
    }

    public void startPounceAnimation() {
        this.dataTracker.set(POUNCE_FLAG, 1);
    }
    public void stopPounceAnimation() {
        this.dataTracker.set(POUNCE_FLAG, -1);
    }

    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            if(biteAnimationCooldown <= 1) {
                this.dataTracker.set(BITE_FLAG, -1);
            }
            biteAnimationCooldown = Math.max(biteAnimationCooldown - 1, 0);
            this.setClimbingWall(this.horizontalCollision);
        } else {
            setupAnimationStates();
        }

    }

    @Override
    protected void mobTick() {
        super.mobTick();
        //ProfilerFiller profiler = Profilers.get();
        //profiler.push("shelobiteScuttlerBrain");
        //this.getBrain().tick(world, this);
        //profiler.pop();
        //ShelobiteScuttlerBrain.updateActivities(this);
        //ShelobiteScuttlerBrain.playSoundRandomly(this);
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if(isClimbingWall()) {
            this.climbingTicks = this.climbingTicks + 1;
        } else {
            this.climbingTicks = Math.max(0, this.climbingTicks - 1);
        }

        if(isOnGround()) {
            leapingTicks = 0;
        } else {
            leapingTicks++;
        }
    }

    @Override
    public int getXpToDrop() {
        return 6 + this.random.nextInt(3);
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

    public int getLeapingTicks() {
        return this.leapingTicks;
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
        SPIDER_FLAGS = SynchedEntityData.registerData(ShelobiteScuttlerEntity.class, EntityDataSerializers.BYTE);
        BITE_FLAG = SynchedEntityData.registerData(ShelobiteScuttlerEntity.class, EntityDataSerializers.INTEGER);
        POUNCE_FLAG = SynchedEntityData.registerData(ShelobiteScuttlerEntity.class, EntityDataSerializers.INTEGER);
        VARIANT = SynchedEntityData.registerData(ShelobiteScuttlerEntity.class, TrackedDataHandlerRegistryME.SPIDER_VARIANT);
    }

    public static boolean canSpawn(EntityType<ShelobiteScuttlerEntity> type, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos blockPos, RandomSource random) {
        if(spawnReason != MobSpawnType.NATURAL)
            return ShelobiteScuttlerEntity.canSpawnInDark(type, serverWorldAccess, spawnReason, blockPos, random);
        return SpawnUtil.canSpawn(blockPos, serverWorldAccess, spawnReason);
    }

    public static class SpiderData implements SpawnGroupData {
        public final Holder<SpiderVariant> variant;

        public SpiderData(Holder<SpiderVariant> variant) {
            this.variant = variant;
        }
    }
}
