package net.jukoz.me.entity.goals;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.pathfinder.PathNodeType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.EntitySelector;
import org.jetbrains.annotations.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.ai.control.Control;
import net.minecraft.world.level.pathfinder.BlockPathTypes;

public class FollowDifferentMobGoal<T extends Mob> extends Goal {
    private final Mob mob;
    private final Predicate<Mob> targetPredicate;
    @Nullable
    private Mob target;
    private final double speed;
    private final PathNavigation navigation;
    private int updateCountdownTicks;
    private final float minDistance;
    private float oldWaterPathFindingPenalty;
    private final float maxDistance;
    private final Class<T> followedClass;

    public FollowDifferentMobGoal(Mob follower, Class<T> followed, double speed, float minDistance, float maxDistance) {
        this.mob = follower;
        this.followedClass = followed;
        this.targetPredicate = target -> target != null && followed != target.getClass();
        this.speed = speed;
        this.navigation = follower.getNavigation();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        if (!(follower.getNavigation() instanceof GroundPathNavigation) && !(follower.getNavigation() instanceof FlyingPathNavigation)) {
            throw new IllegalArgumentException("Unsupported follower type for FollowMobGoal");
        }
    }

    @Override
    public boolean canStart() {
        List<T> list = this.mob.getWorld().getEntitiesByClass(this.followedClass, this.mob.getBoundingBox().expand(this.maxDistance), EntitySelector.VALID_ENTITY);
        if (!list.isEmpty()) {
            for (Mob mobEntity : list) {
                if (!mobEntity.isInvisible()) {
                    this.target = mobEntity;
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean shouldContinue() {
        return this.target != null && !this.navigation.isIdle() && this.mob.squaredDistanceTo(this.target) > (this.minDistance/2) * (this.minDistance/2);
    }

    @Override
    public void start() {
        this.updateCountdownTicks = 0;
        this.oldWaterPathFindingPenalty = this.mob.getPathfindingPenalty(BlockPathTypes.WATER);
        this.mob.setPathfindingPenalty(BlockPathTypes.WATER, 0.0F);
    }

    @Override
    public void stop() {
        this.target = null;
        this.navigation.stop();
        this.mob.setPathfindingPenalty(BlockPathTypes.WATER, this.oldWaterPathFindingPenalty);
    }

    @Override
    public void tick() {
        if (this.target != null && !this.mob.isLeashed()) {
            this.mob.getLookControl().lookAt(this.target, 10.0F, this.mob.getMaxLookPitchChange());
            if (--this.updateCountdownTicks <= 0) {
                this.updateCountdownTicks = this.getTickCount(10);
                double d = this.mob.getX() - this.target.getX();
                double e = this.mob.getY() - this.target.getY();
                double f = this.mob.getZ() - this.target.getZ();
                double g = d * d + e * e + f * f;
                if (!(g <= this.minDistance * this.minDistance)) {
                    this.navigation.startMovingTo(this.target, this.speed);
                } else {
                    this.navigation.stop();
                    LookControl lookControl = this.target.getLookControl();
                    if (g <= this.minDistance
                            || lookControl.getLookX() == this.mob.getX() && lookControl.getLookY() == this.mob.getY() && lookControl.getLookZ() == this.mob.getZ()) {
                        double h = this.target.getX() - this.mob.getX();
                        double i = this.target.getZ() - this.mob.getZ();
                        this.navigation.startMovingTo(this.mob.getX() - h, this.mob.getY(), this.mob.getZ() - i, this.speed);
                    }
                }
            }
        }
    }
}
