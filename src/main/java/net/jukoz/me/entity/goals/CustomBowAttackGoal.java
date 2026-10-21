package net.jukoz.me.entity.goals;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.RangedAttackMob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionHand;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.control.Control;

public class CustomBowAttackGoal<T extends LivingEntity> extends Goal {
        private final T actor;
        private final double speed;
        private int attackInterval;
        private final float squaredRange;
        private int cooldown = -1;
        private int targetSeeingTicker;
        private boolean movingToLeft;
        private boolean backward;
        private int combatTicks = -1;

    public CustomBowAttackGoal(T actor, double speed, int attackInterval, float range) {
            this.actor = actor;
            this.speed = speed;
            this.attackInterval = attackInterval;
            this.squaredRange = range * range;
            this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        }

        public void setAttackInterval(int attackInterval) {
            this.attackInterval = attackInterval;
        }

        @Override
        public boolean canStart() {
            if (((Mob)this.actor).getTarget() == null) {
                return false;
            }
            return this.isHoldingBow();
        }

        protected boolean isHoldingBow() {
            ItemStack stack = actor.getStackInHand(InteractionHand.MAIN_HAND);
            return stack.getItem() instanceof BowItem;
        }

        @Override
        public boolean shouldContinue() {
            return (this.canStart() || !((Mob)this.actor).getNavigation().isIdle()) && this.isHoldingBow();
        }

        @Override
        public void start() {
            super.start();
            ((Mob)this.actor).setAttacking(true);
        }

        @Override
        public void stop() {
            super.stop();
            ((Mob)this.actor).setAttacking(false);
            this.targetSeeingTicker = 0;
            this.cooldown = -1;
            ((LivingEntity)this.actor).clearActiveItem();
        }

        @Override
        public boolean shouldRunEveryTick() {
            return true;
        }

        @Override
        public void tick() {
            boolean bl2;
            LivingEntity livingEntity = ((Mob)this.actor).getTarget();
            if (livingEntity == null) {
                return;
            }
            double d = ((Entity)this.actor).squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ());
            boolean bl = ((Mob)this.actor).getVisibilityCache().canSee(livingEntity);
            boolean bl3 = bl2 = this.targetSeeingTicker > 0;
            if (bl != bl2) {
                this.targetSeeingTicker = 0;
            }
            this.targetSeeingTicker = bl ? ++this.targetSeeingTicker : --this.targetSeeingTicker;
            if (d > (double)this.squaredRange || this.targetSeeingTicker < 20) {
                ((Mob)this.actor).getNavigation().startMovingTo(livingEntity, this.speed);
                this.combatTicks = -1;
            } else {
                ((Mob)this.actor).getNavigation().stop();
                ++this.combatTicks;
            }
            if(!livingEntity.isAlive()) {
                ((Mob)this.actor).getNavigation().stop();
                return;
            }

            if (this.combatTicks >= 20) {
                if ((double)((LivingEntity)this.actor).getRandom().nextFloat() < 0.3) {
                    boolean bl4 = this.movingToLeft = !this.movingToLeft;
                }
                if ((double)((LivingEntity)this.actor).getRandom().nextFloat() < 0.3) {
                    this.backward = !this.backward;
                }
                this.combatTicks = 0;
            }
            if (this.combatTicks > -1) {
                if (d > (double)(this.squaredRange * 0.75f)) {
                    this.backward = false;
                } else if (d < (double)(this.squaredRange * 0.25f)) {
                    this.backward = true;
                }
                ((Mob)this.actor).getMoveControl().strafeTo(this.backward ? -0.5f : 0.5f, this.movingToLeft ? 0.5f : -0.5f);
                ((Mob)this.actor).lookAtEntity(livingEntity, 30.0f, 30.0f);
            } else {
                ((Mob)this.actor).getLookControl().lookAt(livingEntity, 30.0f, 30.0f);
            }
            if (((LivingEntity)this.actor).isUsingItem()) {
                int i;
                if (!bl && this.targetSeeingTicker < -60) {
                    ((LivingEntity)this.actor).clearActiveItem();
                } else if (bl && (i = ((LivingEntity)this.actor).getItemUseTime()) >= 20) {
                    ((LivingEntity)this.actor).clearActiveItem();
                    ((RangedAttackMob)this.actor).attack(livingEntity, BowItem.getPullProgress(i));
                    this.cooldown = this.attackInterval;
                }
            } else if (--this.cooldown <= 0 && this.targetSeeingTicker >= -60) {
                ((LivingEntity)this.actor).setCurrentHand(InteractionHand.MAIN_HAND);
            }
        }
}
