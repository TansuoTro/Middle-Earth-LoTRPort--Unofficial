package net.jukoz.me.entity.tasks;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.behavior.LongJumpToRandomPos;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.Mob;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.util.valueproviders.UniformInt;
import org.jetbrains.annotations.Nullable;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;

public class SpiderPounceTask<E extends Mob> extends Behavior<E> {
	protected static final int MAX_TARGET_SEARCH_TIME = 20;
	private static final int JUMP_WINDUP_TIME = 40;
	protected static final int PATHING_DISTANCE = 8;
	private static final int RUN_TIME = 200;
	private static final List<Integer> POUNCE_ANGLES = Lists.<Integer>newArrayList(30, 37, 45, 50, 55);
	private final UniformInt cooldownRange;
	protected final int verticalRange;
	protected final int horizontalRange;
	protected final float maxRange;
	protected List<Target> potentialTargets = Lists.<Target>newArrayList();
	protected Optional<Vec3> startPos = Optional.empty();
	@Nullable
	protected Vec3 currentTarget;
	protected int targetSearchTime;
	protected long targetPickedTime;
	private final Function<E, SoundEvent> entityToSound;
	private final BiPredicate<E, BlockPos> jumpToPredicate;

	public SpiderPounceTask(UniformInt cooldownRange, int verticalRange, int horizontalRange, float maxRange,
			Function<E, SoundEvent> entityToSound) {
		super(
				ImmutableMap.of(
						MemoryModuleType.LOOK_TARGET,
						MemoryStatus.REGISTERED
						//MemoryModuleType.LONG_JUMP_COOLING_DOWN,
						//MemoryStatus.VALUE_ABSENT,
						//MemoryModuleType.LONG_JUMP_MID_JUMP,
						//MemoryStatus.VALUE_ABSENT
				),
				200
		);
		this.cooldownRange = cooldownRange;
		this.verticalRange = verticalRange;
		this.horizontalRange = horizontalRange;
		this.maxRange = maxRange;
		this.entityToSound = entityToSound;
		this.jumpToPredicate = LongJumpToRandomPos::shouldJumpTo;
	}

	@Override
	protected boolean shouldRun(ServerLevel serverWorld, Mob mobEntity) {
		boolean validTerrain = mobEntity.isOnGround();
				//&& !mobEntity.isTouchingWater()
				//&& !mobEntity.isInLava()
				//&& !serverWorld.getBlockState(mobEntity.getBlockPos()).isOf(Blocks.HONEY_BLOCK);
		if (!validTerrain) {
			mobEntity.getBrain().remember(MemoryModuleType.LONG_JUMP_COOLING_DOWN, 40);
			System.out.println("Can't shouldRun, bad terrain, 40");
		}

		return validTerrain;
	}

	@Override
	protected boolean shouldKeepRunning(ServerLevel serverWorld, Mob mobEntity, long l) {
		boolean canJump = this.startPos.isPresent();
		canJump = canJump && this.targetSearchTime > 0;
		canJump = canJump && !mobEntity.isTouchingWater();
		canJump = canJump && (this.currentTarget != null || !this.potentialTargets.isEmpty());

		//Optional<Integer> optionalMemory = mobEntity.getBrain().getOptionalMemory(MemoryModuleType.LONG_JUMP_COOLING_DOWN);
		//if(optionalMemory != null && optionalMemory.isPresent()) {
		//	canJump = false;
		//}

		if (!canJump) { //&& mobEntity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.LONG_JUMP_MID_JUMP).isEmpty()) {
			System.out.println("Should not keep running... 50. StartPos present: " + startPos.isPresent() + " TargetST:"
					+ targetSearchTime + ". Current Trgt: " + currentTarget + ". Potential Trgt: " + potentialTargets.size());
			mobEntity.getBrain().remember(MemoryModuleType.LONG_JUMP_COOLING_DOWN, cooldownRange.get(mobEntity.getRandom()));
		}

		return canJump;
	}

	@Override
	protected void run(ServerLevel serverWorld, E mobEntity, long l) {
		this.currentTarget = null;
		Optional<LivingEntity> targetEntity = mobEntity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET);
		if(targetEntity.isPresent()) {
			LivingEntity value = targetEntity.get();
			this.potentialTargets.add(new Target(value.getPos(), 1));
			System.out.println("add potential target pos");
		}
		this.targetSearchTime = 20;
		this.startPos = Optional.of(mobEntity.getPos());
	}

	@Override
	protected void keepRunning(ServerLevel serverWorld, E mobEntity, long l) {
		if (this.currentTarget != null) {
			if (l - this.targetPickedTime >= 40L) {
				System.out.println("JUMP RUNNING!");
				mobEntity.setYaw(mobEntity.bodyYaw);
				mobEntity.setNoDrag(true);
				double d = this.currentTarget.length();
				double e = d + mobEntity.getJumpBoostVelocityModifier();
				mobEntity.setVelocity(this.currentTarget.multiply(e / d));
				mobEntity.getBrain().remember(MemoryModuleType.LONG_JUMP_COOLING_DOWN, cooldownRange.get(mobEntity.getRandom()));
				serverWorld.playSoundFromEntity(null, mobEntity, (SoundEvent)this.entityToSound.apply(mobEntity), SoundSource.NEUTRAL, 1.0F, 1.0F);
				potentialTargets.clear();
				targetSearchTime = 0;
			}
		} else {
			this.targetSearchTime--;
			System.out.println("Searching target vec");
			this.pickTarget(serverWorld, mobEntity, l);
		}
	}

	@Override
	protected void finishRunning(ServerLevel world, E entity, long time) {
		super.finishRunning(world, entity, time);
		entity.getBrain().forget(MemoryModuleType.ATTACK_TARGET);
		entity.getBrain().remember(MemoryModuleType.LONG_JUMP_COOLING_DOWN, this.cooldownRange.get(world.getRandom()));
		System.out.println("FINISH POUNCE");
	}

	protected void pickTarget(ServerLevel world, E entity, long time) {
		while (!this.potentialTargets.isEmpty()) {
			Optional<Target> optional = this.removeRandomTarget(world);
			if (!optional.isEmpty()) {
				Target target = optional.get();
				BlockPos pos = new BlockPos((int)target.pos.x, (int)target.pos.y, (int)target.pos.z);
				if (this.canJumpTo(world, entity, pos)) {
					Vec3 vec3d = Vec3.ofCenter(pos);
					Vec3 vec3d2 = this.getJumpingVelocity(entity, vec3d);
					if (vec3d2 != null) {
						entity.getBrain().remember(MemoryModuleType.LOOK_TARGET, new BlockPosTracker(pos));
						PathNavigation entityNavigation = entity.getNavigation();
						Path path = entityNavigation.findPathTo(pos, 0, 8);
						if (path == null || !path.reachesTarget()) {
							this.currentTarget = vec3d2;
							this.targetPickedTime = time;
							return;
						}
					}
				}
			}
		}
	}

	protected Optional<Target> removeRandomTarget(ServerLevel world) {
		if (this.potentialTargets.isEmpty()) return Optional.empty();
		int totalWeight = 0;
		for (Target target : this.potentialTargets) totalWeight += target.weight();
		int roll = world.random.nextInt(totalWeight);
		for (Target target : this.potentialTargets) {
			roll -= target.weight();
			if (roll < 0) {
				this.potentialTargets.remove(target);
				return Optional.of(target);
			}
		}
		return Optional.empty();
	}

	private boolean canJumpTo(ServerLevel world, E entity, BlockPos pos) {
		BlockPos blockPos = entity.getBlockPos();
		int i = blockPos.getX();
		int j = blockPos.getZ();
		return i == pos.getX() && j == pos.getZ() ? false : this.jumpToPredicate.test(entity, pos);
	}

	@Nullable
	protected Vec3 getJumpingVelocity(Mob entity, Vec3 targetPos) {
		List<Integer> list = Lists.<Integer>newArrayList(POUNCE_ANGLES);
		Collections.shuffle(list);
		float f = 0.42F * this.maxRange;

		for (int i : list) {
			Optional<Vec3> optional = net.jukoz.me.compat.LongJumpUtilME.getJumpingVelocity(entity, targetPos, f, i, true);
			if (optional.isPresent()) {
				return (Vec3)optional.get();
			}
		}

		return null;
	}

	public record Target(Vec3 pos, int weight) {
	}
}
