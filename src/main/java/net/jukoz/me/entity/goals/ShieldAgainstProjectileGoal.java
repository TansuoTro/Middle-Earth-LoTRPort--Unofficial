package net.jukoz.me.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.jukoz.me.entity.goals.interfaces.Shielder;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.control.Control;

public class ShieldAgainstProjectileGoal extends Goal {
    private final PathfinderMob blocker;
    private final Shielder shielder;
    private LivingEntity target;
    private Path path;
    private int minDistance;
    private int maxDistance;

    public ShieldAgainstProjectileGoal(PathfinderMob mob, Shielder shielder, int minDistance, int maxDistance) {
        this.blocker = mob;
        this.shielder = shielder;
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.setControls(EnumSet.of(Control.JUMP, Control.MOVE));
    }

    @Override
    public boolean canStart() {
        if (this.blocker.hasPassengers()) {
            return false;
        }
        this.target = this.blocker.getTarget();
        if (this.target == null) {
            return false;
        }

        return shouldContinue();
    }

    @Override
    public void tick() {
        super.tick();
        this.path = this.blocker.getNavigation().findPathTo(target, 0);
        if(this.path != null) {
            this.blocker.getNavigation().startMovingTo(target, 0.7f);
        }
        this.blocker.getLookControl().lookAt(this.target.getX(), this.target.getEyeY(), this.target.getZ());
        this.blocker.addStatusEffect(new MobEffectInstance(MobEffects.RESISTANCE, 21, 1));
    }

    @Override
    public boolean shouldContinue() {
        double d = this.blocker.distanceTo(this.target);
        if (d < this.minDistance || d > this.maxDistance) {
            return false;
        }

        this.path = this.blocker.getNavigation().findPathTo(target, 0);
        if(path == null) return false;

        ItemStack mainStack = target.getMainHandStack();
        if (mainStack.getItem() instanceof ProjectileWeaponItem) {
            if(target instanceof Player player) {
                return player.getItemUseTime() > 1;
            } else if(mainStack.getItem() instanceof CrossbowItem) {
                if(CrossbowItem.isCharged(mainStack)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void start() {
        super.start();
        this.blocker.getNavigation().startMovingAlong(this.path, 0.7f);
        shielder.blockShield();
    }

    @Override
    public void stop() {
        super.stop();
        shielder.unblockShield();
    }
}
