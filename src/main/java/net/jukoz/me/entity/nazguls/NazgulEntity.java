package net.jukoz.me.entity.nazguls;

import net.jukoz.me.entity.dwarves.longbeards.LongbeardDwarfEntity;
import net.jukoz.me.entity.elves.galadhrim.GaladhrimElfEntity;
import net.jukoz.me.entity.goals.PanicFireGoal;
import net.jukoz.me.entity.hobbits.shire.ShireHobbitEntity;
import net.jukoz.me.entity.humans.bandit.BanditHumanEntity;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.jukoz.me.entity.humans.rohan.RohanHumanEntity;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ModWeaponItems;
import net.jukoz.me.sound.ModSounds;
import net.jukoz.me.utils.ParticleManager;
import net.minecraft.world.level.pathfinder.PathNodeType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
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
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class NazgulEntity extends Monster {
    public static int FADING_TIME = 45;
    public static int ALERT_TIME = 600;
    public static float DAMAGE_MULTIPLIER = 0.4f;
    private int ticksSinceDeath = 0;
    private int ticksSinceAlert = ALERT_TIME;
    public NazgulEntity(EntityType<? extends Monster> entityType, Level world) {
        super(entityType, world);
        this.setPathfindingPenalty(BlockPathTypes.WATER, -1.0f);
    }

    @Nullable
    @Override
    public SpawnGroupData initialize(ServerLevelAccessor world, DifficultyInstance difficulty, MobSpawnType spawnReason, @Nullable SpawnGroupData entityData, @Nullable CompoundTag entityNbt) {
        entityData = super.initialize(world, difficulty, spawnReason, entityData, entityNbt);
        RandomSource random = world.getRandom();
        this.initEquipment(random, difficulty);
        return entityData;
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.GENERIC_MOVEMENT_SPEED, 0.3f)
                .add(Attributes.GENERIC_MAX_HEALTH, 30.0)
                .add(Attributes.GENERIC_ATTACK_SPEED, 2.0)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 40.0)
                .add(Attributes.GENERIC_ATTACK_DAMAGE, 3.5);
    }

    @Override
    protected void initEquipment(RandomSource random, DifficultyInstance localDifficulty) {
        super.initEquipment(random, localDifficulty);
        this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(ModWeaponItems.MORGUL_BLADE));

        this.equipStack(EquipmentSlot.HEAD, new ItemStack(ModEquipmentItems.NAZGUL_CLOAK_HOOD));
        this.equipStack(EquipmentSlot.CHEST, new ItemStack(ModEquipmentItems.NAZGUL_CLOAK));
        this.equipStack(EquipmentSlot.LEGS, new ItemStack(ModEquipmentItems.NAZGUL_PANTS));
        this.equipStack(EquipmentSlot.FEET, new ItemStack(ModEquipmentItems.NAZGUL_BOOTS));
    }

    @Override
    protected void initGoals() {
        int i = 0;
        this.goalSelector.add(++i, new FloatGoal(this));
        this.goalSelector.add(++i, new PanicFireGoal(this, 1.1f));
        this.goalSelector.add(++i, new MeleeAttackGoal(this, 1.2f, false));
        this.goalSelector.add(++i, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.add(++i, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.add(++i, new RandomLookAroundGoal(this));
        i = 0;
        this.targetSelector.add(++i, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, GaladhrimElfEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, LongbeardDwarfEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, GondorHumanEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, RohanHumanEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, BanditHumanEntity.class, true));
        this.targetSelector.add(++i, new NearestAttackableTargetGoal<>(this, ShireHobbitEntity.class, true));
    }

    public enum State {
        NEUTRAL,
        ATTACKING,
        ON_FIRE,
        FADING;

    }

    public State getState() {
        if(this.isOnFire()) {
            return State.ON_FIRE;
        }
        if(this.isFading()) {
            return State.FADING;
        }
        else if (this.isAttacking()) {
            return State.ATTACKING;
        }
        return State.NEUTRAL;
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(!source.isOf(DamageTypes.IN_FIRE) && !source.isOf(DamageTypes.ON_FIRE) && !source.isOf(DamageTypes.LAVA)) {
            amount *= DAMAGE_MULTIPLIER;
        }
        return super.damage(source, amount);
    }

    @Override
    public void kill() {
        // Empty
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return ModSounds.NAZGUL_SCREAM;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.NAZGUL_FADE;
    }

    @Override
    protected void updatePostDeath() {
        ++this.ticksSinceDeath;
        ParticleManager.createParticles(getWorld(), getPos(), ParticleTypes.SMOKE, (int) Math.pow((float)ticksSinceDeath / 9, 1.5f));
        if (this.ticksSinceDeath >= FADING_TIME && this.getWorld() instanceof ServerLevel) {
            this.remove(Entity.RemovalReason.KILLED);
            this.emitGameEvent(GameEvent.ENTITY_DIE);
        }
    }

    public boolean isFading() {
        return ticksSinceDeath > 0;
    }

    public int getFadingTicks() {
        return ticksSinceDeath;
    }

    @Override
    public void tickMovement() {
        super.tickMovement();
        ticksSinceAlert = Math.min(ticksSinceAlert + 1, ALERT_TIME);
        LivingEntity livingEntity = this.getTarget();
        if (livingEntity != null && livingEntity.isAlive() && ticksSinceAlert == ALERT_TIME) {
            ticksSinceAlert = 0;
            this.playSound(ModSounds.NAZGUL_SCREAM, 1.25f, getSoundPitch() - 0.05f);
        }
    }
}
