package net.jukoz.me.entity.balrog;

import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import java.util.Random;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.util.RandomSource;

public class BalrogEntity extends Monster {

    private int attackTicksLeft = 0;
    public static final int ATTACK_COOLDOWN = 10;

    public final AnimationState idleAnimationState = new AnimationState();
    public final AnimationState attackAnimationState = new AnimationState();
    private int idleAnimationTimeout = 0;
    private int attackAnimationTimeout = 0;

    private RandomSource randomRegen = new RandomSource();

    public BalrogEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
    }

    protected void initGoals() {
        this.goalSelector.add(1, new FloatGoal(this));
        this.goalSelector.add(2, new HurtByTargetGoal(this));
        this.goalSelector.add(3, new MeleeAttackGoal(this, 1.0f, true));
        this.goalSelector.add(4, new RandomStrollGoal(this, 1.0F));
        this.goalSelector.add(5, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.add(6, new RandomLookAroundGoal(this));

        this.targetSelector.add(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.add(2, new NearestAttackableTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(3, new NearestAttackableTargetGoal<>(this, LongbeardDwarfEntity.class, true));
        this.targetSelector.add(4, new NearestAttackableTargetGoal<>(this, ShireHobbitEntity.class, true));

    }

    public static AttributeSupplier.Builder createBalrogAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.GENERIC_MAX_HEALTH, 300.0)
                .add(Attributes.GENERIC_MOVEMENT_SPEED, 0.4)
                .add(Attributes.GENERIC_KNOCKBACK_RESISTANCE, 1.0)
                .add(Attributes.GENERIC_ATTACK_SPEED, 1.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 40.0)
                .add(Attributes.GENERIC_ATTACK_DAMAGE, 25.0)
                .add(Attributes.GENERIC_ARMOR, 10.0);
    }

    public int getAttackTicksLeft() {
        return this.attackTicksLeft;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        if (this.attackTicksLeft > 0) {
            --this.attackTicksLeft;
        }
    }

    @Override
    public boolean tryAttack(Entity target) {
        this.attackTicksLeft = ATTACK_COOLDOWN;
        this.getWorld().sendEntityStatus(this, EntityEvent.PLAY_ATTACK_SOUND);
        float f = this.getAttackDamage();
        float g = (int)f > 0 ? f / 2.0f + (float)this.random.nextInt((int)f) : f;
        boolean bl = target.damage(this.getDamageSources().mobAttack(this), g);
        if (bl) {
            double d;
            if (target instanceof LivingEntity) {
                LivingEntity livingEntity = (LivingEntity)target;
                d = livingEntity.getAttributeValue(Attributes.GENERIC_KNOCKBACK_RESISTANCE);
            } else {
                d = 0.0;
            }
            double e = Math.max(0.0, 1.0 - d);
            target.setVelocity(target.getVelocity().multiply(1f + (0.8f * e))); //.add(0.0, (double)0.1f * e, 0.0));
            this.applyDamageEffects(this, target);
        }
        this.playSound(SoundEvents.ENTITY_WARDEN_ATTACK_IMPACT, 1.5f, 0.8f);
        return bl;
    }

    private float getAttackDamage() {
        return (float)this.getAttributeValue(Attributes.GENERIC_ATTACK_DAMAGE);
    }

    @Override
    public boolean disablesShield() {
        return true;
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeout <= 0) {
            this.idleAnimationTimeout = this.random.nextInt(40) + 80;
            this.idleAnimationState.start(this.age);
        } else {
            --this.idleAnimationTimeout;
        }
    }

    @Override
    protected void updateLimbs(float posDelta) {
        float f = this.getPose() == Pose.STANDING ? Math.min(posDelta * 6.0f, 1.0f) : 0.0f;
        this.limbAnimator.updateLimbs(f, 0.2f);
    }

    @Override
    public void tick() {
        super.tick();
        if(this.getWorld().isClient){
            setupAnimationStates();
        }

        if(this.getHealth() < this.getMaxHealth() / 2){
            if(randomRegen.nextInt(100) <= 5){
                this.addStatusEffect(new MobEffectInstance(MobEffects.REGENERATION, 100));
            }
        }
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_WARDEN_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_WARDEN_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_WARDEN_DEATH;
    }

    protected void playStepSound(BlockPos pos, BlockState state) {
        this.playSound(SoundEvents.ENTITY_WARDEN_STEP, 0.15F, 2.0F);
    }

    public BalrogVariant getVariant() {
        return BalrogVariant.byId(this.getId());
    }

    public boolean canBreatheInWater() {
        return false;
    }

    public boolean isPushedByFluids() {
        return false;
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier, DamageSource damageSource) {
        return fallDistance > 20;
    }

    @Override
    public void handleStatus(byte status) {
        if(status == 4){
            this.attackAnimationState.start(this.age);
        }
    }

    @Override
    public boolean isPersistent() {
        return true;
    }

}
