package net.jukoz.me.entity.goals;

import net.minecraft.world.entity.monster.CrossbowAttackMob;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.jukoz.me.compat.MECrossbow;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.utils.ItemTagsME;
import java.util.EnumSet;
import java.util.List;
import net.minecraft.world.entity.ai.control.Control;

public class NpcCrossBowAttackGoal<T extends NpcEntity & RangedAttackMob & CrossbowAttackMob> extends Goal {
    public static final UniformInt COOLDOWN_RANGE = TimeUtil.betweenSeconds(1, 2);
    private static int USING_ITEM_FLAG = 1;
    private final T actor;
    private Stage stage = Stage.UNCHARGED;
    private final double speed;
    private final float squaredRange;
    private int seeingTargetTicker;
    private int chargedTicksLeft;
    private int cooldown;
    private int useTicks;

    public NpcCrossBowAttackGoal(T actor, double speed, float range) {
        this.actor = actor;
        this.speed = speed;
        this.squaredRange = range * range;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
    }

    @Override
    public boolean canStart() {
        return this.hasAliveTarget() && this.isEntityHoldingCrossbow();
    }

    private boolean isEntityHoldingCrossbow() {
        ItemStack itemStack = actor.getMainHandStack();
        return (itemStack.isOf(Items.CROSSBOW) || itemStack.isIn(ItemTagsME.CROSSBOW));
    }

    @Override
    public boolean shouldContinue() {
        return this.hasAliveTarget() && (this.canStart() || !this.actor.getNavigation().isIdle()) && this.isEntityHoldingCrossbow();
    }

    private boolean hasAliveTarget() {
        return this.actor.getTarget() != null && this.actor.getTarget().isAlive();
    }

    @Override
    public void start() {
        super.start();
        useTicks = 0;
    }

    @Override
    public void stop() {
        super.stop();
        this.actor.setAttacking(false);
        this.actor.setTarget(null);
        this.seeingTargetTicker = 0;
        if (this.actor.isUsingItem()) {
            this.actor.clearActiveItem();
            this.actor.setCharging(false);
            MECrossbow.clear(this.actor.getActiveItem());
        }
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity livingEntity = this.actor.getTarget();
        if (livingEntity != null) {
            boolean canSee = this.actor.getVisibilityCache().canSee(livingEntity);

            boolean canSeeTargetTicker = this.seeingTargetTicker > 0;
            if (canSee != canSeeTargetTicker) {
                this.seeingTargetTicker = 0;
            }

            if (canSee) {
                this.seeingTargetTicker++;
            } else {
                this.seeingTargetTicker--;
            }

            double distance = this.actor.squaredDistanceTo(livingEntity);
            boolean inRange = (distance > this.squaredRange || this.seeingTargetTicker < 5) && this.chargedTicksLeft == 0;
            if (inRange) {
                this.cooldown--;
                if (this.cooldown <= 0) {
                    this.actor.getNavigation().startMovingTo(livingEntity, this.isUncharged() ? this.speed : this.speed * 0.5);
                    this.cooldown = COOLDOWN_RANGE.get(this.actor.getRandom());
                }
            } else {
                this.cooldown = 0;
                this.actor.getNavigation().stop();
            }

            this.actor.getLookControl().lookAt(livingEntity, 30.0F, 30.0F);
            if (this.stage == Stage.UNCHARGED) {
                if (!inRange) {
                    this.actor.setCurrentHand(ProjectileUtil.getHandPossiblyHolding(this.actor, Items.CROSSBOW));
                    this.stage = Stage.CHARGING;
                    this.actor.setCharging(true);
                }
            } else if (this.stage == Stage.CHARGING) {
                useTicks++;
                this.actor.setCharging(true);
                ItemStack itemStack = this.actor.getActiveItem();
                this.actor.setNpcFlag(USING_ITEM_FLAG, true);
                if (useTicks >= CrossbowItem.getPullTime(itemStack)) {
                    this.actor.stopUsingItem();
                    this.stage = Stage.CHARGED;
                    this.chargedTicksLeft = 30 + this.actor.getRandom().nextInt(15);
                    this.actor.setCharging(false);
                }
            } else if (this.stage == Stage.CHARGED) {
                this.chargedTicksLeft--;
                this.actor.setCharging(false);
                this.actor.setNpcFlag(USING_ITEM_FLAG, true);
                if(this.chargedTicksLeft <= 1) {
                    MECrossbow.setCharged(this.actor.getMainHandStack(), List.of(new ItemStack(Items.ARROW)));
                }
                if (this.chargedTicksLeft == 0) {
                    this.stage = Stage.READY_TO_ATTACK;
                }
            } else if (this.stage == Stage.READY_TO_ATTACK && canSee) {
                this.actor.shootCrossbowAt(livingEntity);
                ItemStack crossbow =  this.actor.getMainHandStack();
                MECrossbow.clear(crossbow);
                this.actor.equipStack(EquipmentSlot.MAINHAND, crossbow);
                this.stage = Stage.UNCHARGED;
                this.actor.setNpcFlag(USING_ITEM_FLAG, false);
                this.actor.stopUsingItem();
            }
        }
    }

    private boolean isUncharged() {
        return this.stage == Stage.UNCHARGED;
    }

    enum Stage {
        UNCHARGED,
        CHARGING,
        CHARGED,
        READY_TO_ATTACK;
    }
}
