package net.jukoz.me.entity.spider;

import net.jukoz.me.block.registration.ModNatureBlocks;
import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.goals.FastPonceAtTargetGoal;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.orcs.mordor.MordorOrcEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
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
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.Difficulty;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.Spider;

public class MirkwoodSpiderEntity extends Monster {
    public static final int CLIMBING_TIME_TRANSITION = 12;
    public static final int ADULT_AGE = 20 * 60 * 2; // 2 min of baby time
    public static final float MOVEMENT_SPEED = 1.2f;
    private static final EntityDataAccessor<Byte> SPIDER_FLAGS;
    private int climbingTicks = 0;

    public MirkwoodSpiderEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 16.0)
                .add(Attributes.MOVEMENT_SPEED, 0.4)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 36.0);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new FloatGoal(this));
        this.goalSelector.add(3, new FastPonceAtTargetGoal(this, 0.3F, 0.4f));
        this.goalSelector.add(4, new MeleeAttackGoal(this, MOVEMENT_SPEED , false));
        this.goalSelector.add(5, new WaterAvoidingRandomStrollGoal(this, 0.8));
        this.goalSelector.add(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.add(6, new RandomLookAroundGoal(this));

        this.targetSelector.add(1, new HurtByTargetGoal(this));
        this.targetSelector.add(2, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.add(3, new NearestAttackableTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(4, new NearestAttackableTargetGoal<>(this, LongbeardDwarfEntity.class, true));
        this.targetSelector.add(5, new NearestAttackableTargetGoal<>(this, ShireHobbitEntity.class, true));
        this.targetSelector.add(6, new NearestAttackableTargetGoal<>(this, MordorOrcEntity.class, true));
    }

    public boolean tryAttack(Entity target) {
        if (super.tryAttack(target)) {
            if (target instanceof LivingEntity) {
                int i = 0;
                if (this.level().getDifficulty() == Difficulty.NORMAL) {
                    i = 7;
                } else if (this.level().getDifficulty() == Difficulty.HARD) {
                    i = 15;
                }

                if (i > 1) {
                    ((LivingEntity)target).addStatusEffect(new MobEffectInstance(MobEffects.POISON, i * 20, 0), this);
                    double random = Math.random();
                    if(random < 0.2f) {
                        ((LivingEntity)target).addStatusEffect(new MobEffectInstance(MobEffects.NAUSEA, i * 20, 0), this);
                    }
                }
            }

            return true;
        } else {
            return false;
        }
    }

    public double getMountedHeightOffset() {
        return (double)(this.getHeight() * 0.5F);
    }

    protected PathNavigation createNavigation(Level world) {
        return new WallClimberNavigation(this, world);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.entityData.define(SPIDER_FLAGS, (byte)0);
    }

    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.setClimbingWall(this.horizontalCollision);
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
        if (!state.is(Blocks.COBWEB) && !state.is(ModNatureBlocks.CORNER_COBWEB) && !state.is(net.jukoz.me.compat.MEBlocksLegacy.HANGING_COBWEB)) {
            super.slowMovement(state, multiplier);
        }
    }

    public MobType getGroup() {
        return MobType.ARTHROPOD;
    }

    // Immune to Poison
    public boolean canHaveStatusEffect(MobEffectInstance effect) {
        return effect.getEffectType() != MobEffects.POISON && super.canHaveStatusEffect(effect);
    }

    public boolean isClimbingWall() {
        return (this.entityData.get(SPIDER_FLAGS) & 1) != 0;
    }

    public boolean isCollidingWall() {
        return this.horizontalCollision;
    }

    public void setClimbingWall(boolean climbing) {
        byte b = (Byte)this.entityData.get(SPIDER_FLAGS);
        if (climbing) {
            b = (byte)(b | 1);
        } else {
            b &= -2;
        }

        this.entityData.set(SPIDER_FLAGS, b);
    }

    public int getClimbingTicks() {
        return this.climbingTicks;
    }

    public MirkwoodSpiderVariants getVariant() {
        return MirkwoodSpiderVariants.byId(this.getId());
    }

    static {
        SPIDER_FLAGS = SynchedEntityData.registerData(net.minecraft.entity.mob.Spider.class, EntityDataSerializers.BYTE);
    }
}
