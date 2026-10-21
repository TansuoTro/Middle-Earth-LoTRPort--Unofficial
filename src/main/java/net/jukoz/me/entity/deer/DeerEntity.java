package net.jukoz.me.entity.deer;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import java.util.Objects;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;

public class DeerEntity extends Animal {

    public final AnimationState idleAnimationState = new AnimationState();
    private int idleAnimationTimeout = this.random.nextInt(100) + 950;

    public DeerEntity(EntityType<? extends Animal> entityType, Level world) {
        super(entityType, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new FloatGoal(this));
        this.goalSelector.add(1, new PanicGoal(this, 1.5f));
        this.goalSelector.add(2, new AvoidEntityGoal<Player>(this, Player.class, 8.0f, 1.5f, 1.7f));
        this.goalSelector.add(2, new AvoidEntityGoal<Wolf>(this, Wolf.class, 10.0f, 1.5f, 1.7f));
        this.goalSelector.add(2, new AvoidEntityGoal<Monster>(this, Monster.class, 4.0f, 1.5f, 1.7f));
        this.goalSelector.add(3, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.add(4, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.add(5, new RandomLookAroundGoal(this));
    }

    public static AttributeSupplier.Builder createDeerAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.GENERIC_MAX_HEALTH, 8.0)
                .add(Attributes.GENERIC_MOVEMENT_SPEED, 0.3f);
    }

    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == Pose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(100) + 950;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getWorld().isClient()) {
            setupAnimationStates();
        }
    }

    @Override
    public double getMountedHeightOffset() {
        return (double)this.getHeight() * 0.6;
    }

    @Nullable
    @Override
    public AgeableMob createChild(ServerLevel world, AgeableMob entity) {
        return null;
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_HORSE_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_HORSE_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_HORSE_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_COW_STEP, 0.15F, 2.0F);
    }
}
