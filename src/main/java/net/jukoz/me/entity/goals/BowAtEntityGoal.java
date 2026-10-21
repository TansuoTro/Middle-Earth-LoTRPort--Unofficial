package net.jukoz.me.entity.goals;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.server.level.ServerLevel;
import org.jetbrains.annotations.Nullable;
import java.util.EnumSet;
import java.util.function.Predicate;
import net.minecraft.world.entity.ai.control.Control;

public class BowAtEntityGoal extends Goal {
	public static final float DEFAULT_CHANCE = 0.02F;
	protected final PathfinderMob mob;
	@Nullable
	protected Entity target;
	protected final float range;
	private int lookTime;
	private final boolean lookForward;
	private final PathNavigation navigation;
	protected final Class<? extends LivingEntity> targetType;
	protected final TargetingConditions targetPredicate;
	protected final Predicate<LivingEntity> targetSelector;

	public BowAtEntityGoal(PathfinderMob mob, Class<? extends LivingEntity> targetType, float range, Predicate<LivingEntity> targetSelector) {
		this(mob, targetType, range, targetSelector, false);
	}

	public BowAtEntityGoal(PathfinderMob mob, Class<? extends LivingEntity> targetType, float range, Predicate<LivingEntity> targetSelector, boolean lookForward) {
		this.mob = mob;
		this.targetType = targetType;
		this.range = range;
		this.lookForward = lookForward;
		this.navigation = mob.getNavigation();
		this.targetSelector = targetSelector;
		this.setControls(EnumSet.of(Control.LOOK));
		if (targetType == Player.class) {
			Predicate<Entity> predicate = EntitySelector.rides(mob);
			this.targetPredicate = TargetingConditions.createNonAttackable().setBaseMaxDistance((double)range).setPredicate(targetSelector);
		} else {
			this.targetPredicate = TargetingConditions.createNonAttackable().setBaseMaxDistance((double)range);
		}
	}

	public boolean canStart() {
		if (this.mob.getTarget() != null) {
			this.target = this.mob.getTarget();
		}

		if (!(this.mob.getWorld() instanceof ServerLevel serverWorld)) {
			return false;
		}
		if (this.targetType == Player.class) {
			this.target = serverWorld.getClosestPlayer(this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
		} else {
			this.target = serverWorld.getClosestEntity(this.mob.getWorld().getEntitiesByClass(this.targetType, this.mob.getBoundingBox().expand((double)this.range, 3.0, (double)this.range), (livingEntity) -> {
				return true;
			}), this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());
		}

		return this.target != null;

	}

	public boolean shouldContinue() {
		if (!this.target.isAlive()) {
			return false;
		} else if (this.mob.squaredDistanceTo(this.target) > (double)(this.range * this.range)) {
			return false;
		} else {
			return this.lookTime > 0;
		}
	}

	public void start() {
		this.lookTime = 85;
		this.navigation.stop();
	}

	public void stop() {
		this.target = null;
	}

	public void tick() {
		if (this.target.isAlive()) {
			this.navigation.stop();
			double d = this.lookForward ? this.mob.getEyeY() : this.target.getEyeY();
			this.mob.getLookControl().lookAt(this.target.getX(), d, this.target.getZ());
			--this.lookTime;
		}
	}
}
