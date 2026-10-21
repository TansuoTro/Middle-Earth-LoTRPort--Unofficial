package net.jukoz.me.entity.beasts.trolls.snow;

import net.jukoz.me.entity.NpcEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.jukoz.me.entity.goals.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;

public class SnowTrollEntity extends TrollEntity {
    public SnowTrollEntity(EntityType<? extends AbstractChestedHorse> entityType, Level world) {
        super(entityType, world);
    }

    public static AttributeSupplier.Builder setAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.GENERIC_MOVEMENT_SPEED, 0.35f)
                .add(Attributes.GENERIC_MAX_HEALTH, 180.0)
                .add(Attributes.GENERIC_KNOCKBACK_RESISTANCE, 0.8)
                .add(Attributes.GENERIC_ATTACK_SPEED, 0.65)
                .add(Attributes.GENERIC_FOLLOW_RANGE, 28.0)
                .add(Attributes.GENERIC_ATTACK_DAMAGE, 14.0)
                .add(Attributes.HORSE_JUMP_STRENGTH, 0.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new FloatGoal(this));
        this.goalSelector.add(2, new MeleeAttackGoal(this, 0.9f, false));
        this.goalSelector.add(4, new WaterAvoidingRandomStrollGoal(this, 1.0));
        this.goalSelector.add(5, new LookAtPlayerGoal(this, Player.class, 6.0f));
        this.goalSelector.add(6, new RandomLookAroundGoal(this));
        this.targetSelector.add(1, new HurtByTargetGoal(this, new Class[0]));
        this.targetSelector.add(2, new TargetPlayerGoal(this));
        this.targetSelector.add(3, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.targetSelector.add(4, new NearestAttackableTargetGoal<>(this, NpcEntity.class, true));
        this.targetSelector.add(5, new NearestAttackableTargetGoal<>(this, Animal.class, true));
    }

    @Override
    public Item getBondingItem() {
        return null;
    }
}
