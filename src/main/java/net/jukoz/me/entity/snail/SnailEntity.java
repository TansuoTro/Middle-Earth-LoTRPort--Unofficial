package net.jukoz.me.entity.snail;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.ModEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class SnailEntity extends Animal {

    public static final int CLIMBING_TIME_TRANSITION = 12;
    private static final EntityDataAccessor<Byte> SNAIL_FLAGS;
    private int climbingTicks = 0;
    int moreCropsTicks;

    public SnailEntity(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SnailEatCropGoal(this));
        this.goalSelector.add(1, new WaterAvoidingRandomStrollGoal(this, 0.8));
    }

    public static AttributeSupplier.Builder createSnailAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 2)
                .add(Attributes.MOVEMENT_SPEED, 0.05f)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1)
                .add(Attributes.ARMOR, 0.5f);
    }

    public SnailVariant getVariant() {
        return SnailVariant.byId(this.getId());
    }
    @Nullable
    @Override
    public AgeableMob createChild(ServerLevel world, AgeableMob entity) {
        return ModEntities.SNAIL.create(world);
    }

    static class SnailEatCropGoal
            extends MoveToBlockGoal {
        private final SnailEntity snail;
        private boolean wantsCrops;
        private boolean hasTarget;

        public SnailEatCropGoal(SnailEntity snail) {
            super(snail, 1.0f, 16);
            this.snail = snail;
        }

        @Override
        public boolean canStart() {
            if (this.cooldown <= 0) {
                if (!this.snail.getWorld().getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
                    return false;
                }
                this.hasTarget = false;
                this.wantsCrops = this.snail.wantsCrops();
            }
            return super.canStart();
        }

        @Override
        public boolean shouldContinue() {
            return this.hasTarget && super.shouldContinue();
        }

        @Override
        public void tick() {
            super.tick();
            this.snail.getLookControl().lookAt((double)this.targetPos.getX() + 0.5, this.targetPos.getY() + 1, (double)this.targetPos.getZ() + 0.5, 10.0f, this.snail.getMaxHeadXRot());
            if (this.isReachedTarget()) {
                Level world = this.snail.getWorld();
                BlockPos blockPos = this.targetPos.above();
                BlockState blockState = world.getBlockState(blockPos);
                Block block = blockState.getBlock();
                if (this.hasTarget && block.defaultBlockState().is(BlockTags.CROPS)) {
                    world.setBlock(blockPos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS);
                    world.breakBlock(blockPos, true, this.snail);
                    this.snail.onEatingGrass();
                    this.snail.moreCropsTicks = 1200;
                }
                this.hasTarget = false;
                this.cooldown = 10;
            }
        }

        @Override
        protected boolean isTargetPos(LevelReader world, BlockPos pos) {
            BlockState blockState = world.getBlockState(pos);
            if (blockState.is(Blocks.FARMLAND) && this.wantsCrops && !this.hasTarget && (blockState = world.getBlockState(pos.above())).is(BlockTags.CROPS)) {
                this.hasTarget = true;
                return true;
            }

            return false;
        }
    }

    boolean wantsCrops() {
        return this.moreCropsTicks <= 0;
    }

    @Override
    protected void mobTick() {
        if (this.moreCropsTicks > 0) {
            this.moreCropsTicks -= this.random.nextInt(3);
            if (this.moreCropsTicks < 0) {
                this.moreCropsTicks = 0;
            }
        }
        super.mobTick();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SLIME_DEATH;
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SLIME_HURT;
    }

    @Override
    protected void jump() {
        // Snail is not able to jump
    }

    protected PathNavigation createNavigation(Level world) {
        return new WallClimberNavigation(this, world);
    }

    protected void initDataTracker() {
        super.initDataTracker();
        this.entityData.define(SNAIL_FLAGS, (byte)0);
    }

    @Override
    public void onEatingGrass() {
        super.onEatingGrass();
        if (this.isBaby()) {
            this.ageUp(60);
        }
        if(random.nextDouble() <= 0.15D) {
            Level world = this.level();
            SnailEntity snailSpawn = ((EntityType<SnailEntity>) EntityType.get(MiddleEarth.MOD_ID + ":snail").get()).create(world);
            snailSpawn.updatePosition(this.getX(), this.getY(), this.getZ());
            world.spawnEntity(snailSpawn);
        }
    }

    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            this.setClimbingWall(this.horizontalCollision);
        }
        if(isClimbing()) {
            this.setVelocity(0, 0.01, 0);
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

    public boolean isClimbing() {
        return this.isClimbingWall();
    }

    public boolean isClimbingWall() {
        return (this.entityData.get(SNAIL_FLAGS) & 1) != 0;
    }

    public boolean isCollidingWall() {
        return this.horizontalCollision;
    }

    public void setClimbingWall(boolean climbing) {
        byte b = (Byte)this.entityData.get(SNAIL_FLAGS);
        if (climbing) {
            b = (byte)(b | 1);
        } else {
            b &= -2;
        }

        this.entityData.set(SNAIL_FLAGS, b);
    }

    public int getClimbingTicks() {
        return this.climbingTicks;
    }

    static {
        SNAIL_FLAGS = SynchedEntityData.registerData(SnailEntity.class, EntityDataSerializers.BYTE);
    }
}
