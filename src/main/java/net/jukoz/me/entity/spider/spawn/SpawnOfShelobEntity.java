package net.jukoz.me.entity.spider.spawn;

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
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
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
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.world.*;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.TrackedDataHandlerRegistryME;
import net.jukoz.me.entity.VariantsME;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntity;
import net.jukoz.me.entity.goals.PounceRetreatGoal;
import net.jukoz.me.entity.goals.ShieldAgainstProjectileGoal;
import net.jukoz.me.entity.goals.SmartProjectileAttackGoal;
import net.jukoz.me.entity.goals.SpiderPonceAtTargetGoal;
import net.jukoz.me.entity.goals.interfaces.CooldownRangedAttackMob;
import net.jukoz.me.entity.goals.interfaces.Shielder;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.entity.projectile.WebbedEntity;
import net.jukoz.me.entity.spider.Pouncer;
import net.jukoz.me.entity.spider.SpiderVariant;
import net.jukoz.me.entity.spider.scuttler.ShelobiteScuttlerEntity;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.registries.content.spidervariants.SpiderVariantRegistry;
import net.jukoz.me.utils.SpawnUtil;
import org.jetbrains.annotations.Nullable;
import java.util.Optional;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;

public class SpawnOfShelobEntity extends Monster implements Pouncer, Shielder, CooldownRangedAttackMob {
    public static final int CLIMBING_MAX_TICKS = 40;
    public static final int PASSIVE_HEALING_COOLDOWN = 80;
    public static final int CLIMBING_TIME_TRANSITION = 12;
    public static final int LEAPING_TIME_TRANSITION = 9;
    public static final float MOVEMENT_SPEED = 1.15f;
    public static final float WEB_PROJECTILE_DAMAGE = 2f;

    private static final EntityDataAccessor<Byte> SPIDER_FLAGS;
    private static final EntityDataAccessor<Integer> BITE_FLAG;
    private static final EntityDataAccessor<Integer> POUNCE_FLAG;
    private static final EntityDataAccessor<Integer> BLOCK_FLAG;
    private static final EntityDataAccessor<Holder<SpiderVariant>> VARIANT;

    public final AnimationState idleAnimation = new AnimationState();
    public final AnimationState walkingAnimation = new AnimationState();
    public final AnimationState biteAnimation = new AnimationState();
    public final AnimationState pounceAnimation = new AnimationState();
    public final AnimationState blockAnimation = new AnimationState();

    private int passiveHealingCooldown = 0;
    private int climbingTicks = 0;
    private int timelineTicks = 0;
    private int leapingTicks = 0;
    private int shootCooldown = 0;
    private int biteAnimationCooldown = 0;

    public SpawnOfShelobEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Monster.createHostileAttributes()
                .add(Attributes.GENERIC_MAX_HEALTH, 36.0)
                .add(Attributes.GENERIC_MOVEMENT_SPEED, 0.3)
                .add(Attributes.GENERIC_ATTACK_DAMAGE, 7)
                .add(Attributes.GENERIC_ARMOR, 3)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 48.0);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new FloatGoal(this));
        this.goalSelector.add(2, new PounceRetreatGoal(this, 0.8f, 1.15f, 0.3f));
        this.goalSelector.add(3, new ShieldAgainstProjectileGoal(this, this, 13, 32));
        this.goalSelector.add(4, new SmartProjectileAttackGoal(this, 0.75f, 40, 90, 17, 40));
        this.goalSelector.add(5, new SpiderPonceAtTargetGoal(this, this,
                0.5F, 0.25f, 4, 17, 4));
        this.goalSelector.add(5, new MeleeAttackGoal(this, MOVEMENT_SPEED , false));
        this.goalSelector.add(6, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.add(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.add(8, new RandomLookAroundGoal(this));
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
        this.dataTracker.startTracking(SPIDER_FLAGS, (byte)0);
        this.dataTracker.startTracking(BITE_FLAG, 0);
        this.dataTracker.startTracking(POUNCE_FLAG, 0);
        this.dataTracker.startTracking(BLOCK_FLAG, 0);
        Holder<SpiderVariant> spiderVariantRegistryEntry = VariantsME.getOrDefaultOrThrow(this.getWorld().getRegistryManager(), SpiderVariantRegistry.DEFAULT);
        this.dataTracker.startTracking(VARIANT, spiderVariantRegistryEntry);
    }

    protected void setupAnimationStates() {
        if(!this.idleAnimation.isRunning()) {
            this.idleAnimation.start(this.age);
        }
        setTrackerState(POUNCE_FLAG, pounceAnimation);
        setTrackerState(BLOCK_FLAG, blockAnimation);
        setTrackerState(BITE_FLAG, biteAnimation);
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

    public void startPounceAnimation() {
        this.dataTracker.set(POUNCE_FLAG, 1);
    }
    public void stopPounceAnimation() {
        this.dataTracker.set(POUNCE_FLAG, -1);
    }

    @Override
    public void blockShield() {
        this.dataTracker.set(BLOCK_FLAG, 1);
    }
    @Override
    public void unblockShield() {
        this.dataTracker.set(BLOCK_FLAG, -1);
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean result = super.tryAttack(target);
        this.dataTracker.set(BITE_FLAG, 1);
        if(biteAnimationCooldown == 0) biteAnimationCooldown = 40;
        if (target instanceof LivingEntity) {
            int i = 0;
            if (this.getWorld().getDifficulty() == Difficulty.NORMAL) {
                i = 7;
            } else if (this.getWorld().getDifficulty() == Difficulty.HARD) {
                i = 15;
            }

            if (i > 0) {
                ((LivingEntity)target).addStatusEffect(new MobEffectInstance(MobEffects.POISON, i * 20, 0), this);
            }
        }
        return result;
    }

    @Override
    public void onLanding() {
        if (this.getWorld() instanceof ServerLevel serverWorld) {
            if (this.isOnGround() && this.fallDistance > 1.5) {
                Vec3 vec3d = getPos().add(0.0, 0.5, 0.0);
                BlockState blockState = this.getSteppingBlockState();
                int count = (int) Mth.clamp(25.0 * this.fallDistance - 1, 0.0, 150.0);
                serverWorld.spawnParticles(new BlockParticleOption(ParticleTypes.BLOCK, blockState), vec3d.x, vec3d.y, vec3d.z,
                        count, this.random.nextDouble() - 0.5, 0.15, this.random.nextDouble() - 0.5, 0.2f);
            }
        }
        super.onLanding();
    }

    public void tick() {
        super.tick();
        if (!this.getWorld().isClient) {
            this.setClimbingWall(this.horizontalCollision);

            if(biteAnimationCooldown <= 1) {
                this.dataTracker.set(BITE_FLAG, -1);
            }
            biteAnimationCooldown = Math.max(biteAnimationCooldown - 1, 0);

            this.shootCooldown = Math.max(0, this.shootCooldown - 1);
            if(!this.hasStatusEffect(MobEffects.REGENERATION)) {
                passiveHealingCooldown = Math.max(0, passiveHealingCooldown - 1);
                if(passiveHealingCooldown == 0 && this.getHealth() < this.getMaxHealth()) {
                    this.heal(1);
                    passiveHealingCooldown = PASSIVE_HEALING_COOLDOWN;
                }
            }
        } else {
            setupAnimationStates();
        }

    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if(isClimbingWall()) {
            timelineTicks++;
            this.climbingTicks = Math.min( this.climbingTicks + 1, CLIMBING_MAX_TICKS);
        } else {
            int amount = 1;
            if(this.climbingTicks > CLIMBING_MAX_TICKS / 3) amount = 4;
            this.climbingTicks = Math.max(0, this.climbingTicks - amount);
        }

        if(isOnGround()) {
            leapingTicks = 0;
        } else {
            leapingTicks++;
        }
    }

    @Override
    public int getXpToDrop() {
        return 13 + this.random.nextInt(4);
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

    @Override
    public void attack(LivingEntity target, float pullProgress) {
        this.shootAt(target, pullProgress);
    }

    public void shootAt(LivingEntity target, float pullProgress) {
        double dX = target.getX() - this.getX();
        double e = target.getEyeY() - 1.1F;
        double dZ = target.getZ() - this.getZ();
        double g = Math.sqrt(dX * dX + dZ * dZ) * 0.2F;
        if (this.getWorld() instanceof ServerLevel serverWorld) {
            WebbedEntity web = new WebbedEntity(serverWorld, this, WEB_PROJECTILE_DAMAGE * pullProgress);
            web.setVelocity(dX, e + g - web.getY(), dZ, 1.6F, 8 - this.getWorld().getDifficulty().getId() * 4);
            serverWorld.spawnEntity(web);
        }

        this.playSound(SoundEvents.ENTITY_SNOWBALL_THROW, 1.0F, 0.7F + (this.getRandom().nextFloat() * 0.6F));
    }

    @Override
    public int getRangeAttackCooldown() {
        return this.shootCooldown;
    }

    @Override
    public void setRangeAttackCooldown(int cooldown) {
        this.shootCooldown = cooldown;
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

    public int getTimelineTicks() {
        return this.timelineTicks;
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
        SPIDER_FLAGS = SynchedEntityData.registerData(SpawnOfShelobEntity.class, EntityDataSerializers.BYTE);
        BITE_FLAG = SynchedEntityData.registerData(SpawnOfShelobEntity.class, EntityDataSerializers.INTEGER);
        POUNCE_FLAG = SynchedEntityData.registerData(SpawnOfShelobEntity.class, EntityDataSerializers.INTEGER);
        BLOCK_FLAG = SynchedEntityData.registerData(SpawnOfShelobEntity.class, EntityDataSerializers.INTEGER);
        VARIANT = SynchedEntityData.registerData(SpawnOfShelobEntity.class, TrackedDataHandlerRegistryME.SPIDER_VARIANT);
    }

    public static boolean canSpawn(EntityType<SpawnOfShelobEntity> type, ServerLevelAccessor serverWorldAccess, MobSpawnType spawnReason, BlockPos blockPos, RandomSource random) {
        if(spawnReason != MobSpawnType.NATURAL)
            return SpawnOfShelobEntity.canSpawnInDark(type, serverWorldAccess, spawnReason, blockPos, random);
        return SpawnUtil.canSpawn(blockPos, serverWorldAccess, spawnReason);
    }

    @Override
    public boolean canSpawn(LevelAccessor world, MobSpawnType spawnReason) {
        return true;
    }
}
