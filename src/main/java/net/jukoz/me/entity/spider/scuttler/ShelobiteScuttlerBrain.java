package net.jukoz.me.entity.spider.scuttler;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.GlobalPos;
import net.minecraft.util.valueproviders.UniformInt;
import net.jukoz.me.entity.EntitiesME;
import net.jukoz.me.entity.tasks.SpiderPounceTask;
import java.util.Optional;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.InteractWith;
import net.minecraft.world.entity.ai.behavior.LongJumpMidJump;
import net.minecraft.world.entity.ai.behavior.LongJumpToRandomPos;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.RandomStroll;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetEntityLookTarget;
import net.minecraft.world.entity.ai.behavior.SetLookAndInteract;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetAwayFrom;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.StopBeingAngryIfTargetDead;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.util.Tuple;

public class ShelobiteScuttlerBrain {
	private static final UniformInt POUNCE_COOLDOWN_RANGE = UniformInt.create(50, 80);
	public static final int POUNCE_VERTICAL_RANGE = 1;
	public static final int POUNCE_HORIZONTAL_RANGE = 3;

	protected static Brain<?> create(ShelobiteScuttlerEntity shelobiteScuttlerEntity, Brain<ShelobiteScuttlerEntity> brain) {
		addCoreActivities(shelobiteScuttlerEntity, brain);
		addIdleActivities(shelobiteScuttlerEntity, brain);
		addFightActivities(shelobiteScuttlerEntity, brain);
		addPounceActivities(shelobiteScuttlerEntity, brain);
		brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
		brain.setDefaultActivity(Activity.IDLE);
		brain.resetPossibleActivities();
		return brain;
	}

	protected static void setCurrentPosAsHome(ShelobiteScuttlerEntity shelobiteScuttler) {
		shelobiteScuttler.getBrain().remember(net.jukoz.me.api.entity.ai.brain.MemoryModulesAPI.SHELTER_POS, shelobiteScuttler.getBlockPos());
	}

	private static void addCoreActivities(ShelobiteScuttlerEntity shelobiteScuttler, Brain<ShelobiteScuttlerEntity> brain) {
		brain.setTaskList(
				Activity.CORE, 0, ImmutableList.of(
						StopBeingAngryIfTargetDead.create()
				)
		);
	}

	private static void addIdleActivities(ShelobiteScuttlerEntity shelobiteScuttler, Brain<ShelobiteScuttlerEntity> brain) {
		brain.setTaskList(
				Activity.IDLE,
				10,
				ImmutableList.of(
						StartAttacking.<ShelobiteScuttlerEntity>create(ShelobiteScuttlerBrain::getTarget),
						getFollowTasks(),
						getIdleTasks(),
						SetLookAndInteract.create(EntityType.PLAYER, 4)
				)
		);
	}

	private static void addFightActivities(ShelobiteScuttlerEntity shelobiteScuttler, Brain<ShelobiteScuttlerEntity> brain) {
		brain.setTaskList(
				Activity.FIGHT,
				10,
				ImmutableList.of(
						StopAttackingIfTargetInvalid.create(),
						SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(1.0F),
						MeleeAttack.create(20)
				),
				MemoryModuleType.ATTACK_TARGET
		);
	}

	private static void addPounceActivities(ShelobiteScuttlerEntity shelobiteScuttler, Brain<ShelobiteScuttlerEntity> brain) {
		brain.setTaskList(
				Activity.LONG_JUMP,
				ImmutableList.of(
						Tuple.of(0, StopAttackingIfTargetInvalid.create()),
						//new LongJumpMidJump(POUNCE_COOLDOWN_RANGE, SoundEvents.ENTITY_SPIDER_STEP),
						Tuple.of(1, new SpiderPounceTask<>(
								POUNCE_COOLDOWN_RANGE, POUNCE_VERTICAL_RANGE, POUNCE_HORIZONTAL_RANGE,
								3.5714288F, spider -> SoundEvents.ENTITY_SPIDER_STEP
						))
				),
				ImmutableSet.of(
						Tuple.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT),
						Tuple.of(MemoryModuleType.LONG_JUMP_COOLING_DOWN, MemoryStatus.VALUE_ABSENT)
				),
				ImmutableSet.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleType.LONG_JUMP_COOLING_DOWN)
				//MemoryModuleType.LONG_JUMP_COOLING_DOWN
				//Activity.LONG_JUMP,
				//10,
				//ImmutableList.of(
				//		Tuple.of(0, new LongJumpMidJump(POUNCE_COOLDOWN_RANGE, SoundEvents.ENTITY_SPIDER_STEP)),
				//		Tuple.of(
				//				1,
				//				new LongJumpToRandomPos<>(
				//						POUNCE_COOLDOWN_RANGE, POUNCE_VERTICAL_RANGE, POUNCE_HORIZONTAL_RANGE, 3.5714288F, spider -> SoundEvents.ENTITY_SPIDER_STEP
				//				)
				//		)
				//),
				//MemoryModuleType.ATTACK_TARGET
				//ImmutableSet.of(
						//Tuple.of(MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT),
						//Tuple.of(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT),
						//Tuple.of(MemoryModuleType.LONG_JUMP_COOLING_DOWN, MemoryStatus.VALUE_ABSENT)
				//)
		);
	}

	private static RunOne<ShelobiteScuttlerEntity> getFollowTasks() {
		return new RunOne<>(
				ImmutableList.of(
						Tuple.of(SetEntityLookTarget.create(EntityType.PLAYER, 8.0F), 1),
						Tuple.of(SetEntityLookTarget.create(EntitiesME.SHELOBITE_SCUTTLER, 8.0F), 1),
						Tuple.of(SetEntityLookTarget.create(8.0F), 1),
						Tuple.of(new DoNothing(30, 60), 1)
				)
		);
	}

	private static RunOne<ShelobiteScuttlerEntity> getIdleTasks() {
		return new RunOne<>(
				ImmutableList.of(
						Tuple.of(RandomStroll.create(0.6F), 2),
						Tuple.of(InteractWith.create(EntitiesME.SHELOBITE_SCUTTLER, 8, MemoryModuleType.INTERACTION_TARGET, 0.6F, 2), 2),
						Tuple.of(SetWalkTargetAwayFrom.createPosBased(net.jukoz.me.api.entity.ai.brain.MemoryModulesAPI.SHELTER_POS, 0.6F, 2, true), 2),
						
						Tuple.of(new DoNothing(30, 60), 1)
				)
		);
	}
/*
	protected static void tick(MirkwoodSpiderEntity shelobiteScuttler) {
		RandomSource random = shelobiteScuttler.getWorld().getRandom();
		Brain<MirkwoodSpiderEntity> brain = shelobiteScuttler.getBrain();
		List<BehaviorControl<? super MirkwoodSpiderEntity>> runningTasks = brain.getRunningTasks();
		brain.resetPossibleActivities(ImmutableList.of(Activity.LONG_JUMP, Activity.FIGHT, Activity.IDLE));
		System.out.println(shelobiteScuttler.getBrain().getOptionalRegisteredMemory(MemoryModuleType.LONG_JUMP_COOLING_DOWN));
		shelobiteScuttler.setAttacking(brain.hasMemoryModule(MemoryModuleType.ATTACK_TARGET));
	}

	protected static void updateActivities(MirkwoodSpiderEntity shelobiteScuttler) {
		//Brain<MirkwoodSpiderEntity> brain = shelobiteScuttler.getBrain();
		brain.resetPossibleActivities(ImmutableList.of(Activity.IDLE, Activity.FIGHT, Activity.LONG_JUMP));

		Optional<LivingEntity> isAttacking = brain.getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
		if(isAttacking != null && isAttacking.isPresent()) {
			Optional<Integer> canJump = brain.getOptionalMemory(MemoryModuleType.LONG_JUMP_COOLING_DOWN);
			if(canJump != null && canJump.isPresent()) {
				int cooldown =  canJump.get();
				if(cooldown == 0) {
					System.out.println("JUMP!!");
					brain.forget(MemoryModuleType.LONG_JUMP_COOLING_DOWN);
					brain.doExclusively(Activity.LONG_JUMP);
				} else {
					brain.doExclusively(Activity.FIGHT);
				}
			} else {
				brain.resetPossibleActivities(ImmutableList.of(Activity.LONG_JUMP));
				brain.doExclusively(Activity.FIGHT);
				brain.remember(MemoryModuleType.LONG_JUMP_COOLING_DOWN, POUNCE_COOLDOWN_RANGE.get(shelobiteScuttler.getRandom()));
			}
		}
		else {
			brain.doExclusively(Activity.IDLE);
		}
		Activity activity = brain.getFirstPossibleNonCoreActivity().orElse(null);
		System.out.println(activity);
	}

	protected static void updateActivities(MirkwoodSpiderEntity shelobiteScuttler) {
		Brain<MirkwoodSpiderEntity> brain = shelobiteScuttler.getBrain();
		Optional<LivingEntity> isAttacking = brain.getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
		if(isAttacking != null && isAttacking.isPresent()) {
			Optional<Integer> canJump = brain.getOptionalMemory(MemoryModuleType.LONG_JUMP_COOLING_DOWN);
			if(canJump != null && canJump.isPresent()) {
				int cooldown =  canJump.get();
				if(cooldown == 0) {
					System.out.println("JUMP!!");
					brain.forget(MemoryModuleType.LONG_JUMP_COOLING_DOWN);
				}
				else {
					brain.resetPossibleActivities(ImmutableList.of(Activity.IDLE, Activity.FIGHT));
				}
			} else {
				brain.resetPossibleActivities(ImmutableList.of(Activity.LONG_JUMP));
				brain.remember(MemoryModuleType.LONG_JUMP_COOLING_DOWN, POUNCE_COOLDOWN_RANGE.get(shelobiteScuttler.getRandom()));
			}
		}
		else {
			brain.resetPossibleActivities(ImmutableList.of(Activity.IDLE, Activity.FIGHT, Activity.LONG_JUMP));
		}
		brain.resetPossibleActivities(ImmutableList.of(Activity.IDLE, Activity.FIGHT, Activity.LONG_JUMP));
		shelobiteScuttler.getBrain().refreshActivities(shelobiteScuttler.getWorld().getTimeOfDay(), shelobiteScuttler.getWorld().getTime());

		Activity activity = brain.getFirstPossibleNonCoreActivity().orElse(null);
		System.out.println(activity);
	}

	public static void updateActivities(Axolotl axolotl) {
		Brain<Axolotl> brain = axolotl.getBrain();
		Activity activity = (Activity)brain.getFirstPossibleNonCoreActivity().orElse(null);
		if (activity != Activity.PLAY_DEAD) {
			brain.resetPossibleActivities(ImmutableList.of(Activity.PLAY_DEAD, Activity.FIGHT, Activity.IDLE));
			if (activity == Activity.FIGHT && brain.getFirstPossibleNonCoreActivity().orElse(null) != Activity.FIGHT) {
				brain.remember(MemoryModuleType.HAS_HUNTING_COOLDOWN, true, 2400L);
			}
		}
	}*/

	private static boolean isTarget(ServerLevel world, ShelobiteScuttlerEntity shelobiteScuttler, LivingEntity target) {
		return getTarget(shelobiteScuttler).filter(targetx -> targetx == target).isPresent();
	}

	private static Optional<? extends LivingEntity> getTarget(ShelobiteScuttlerEntity shelobiteScuttler) {
		Optional<LivingEntity> optional = net.minecraft.entity.ai.brain.task.BehaviorUtils.getEntity(shelobiteScuttler, MemoryModuleType.ANGRY_AT);
		if (optional.isPresent() && Sensor.testAttackableTargetPredicateIgnoreVisibility(shelobiteScuttler, (LivingEntity)optional.get())) {
			return optional;
		} else {
			Optional<? extends LivingEntity> optional2 = shelobiteScuttler.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER);
			return optional2.isPresent() ? optional2 : shelobiteScuttler.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_NEMESIS);
		}
	}

	protected static void tryRevenge(ServerLevel world, ShelobiteScuttlerEntity shelobiteScuttler, LivingEntity target) {
		if (!(target instanceof AbstractPiglin)) {
			tryRevenge(world, shelobiteScuttler, target);
		}
	}

	protected static void setTarget(ShelobiteScuttlerEntity shelobiteScuttler, LivingEntity target) {
		shelobiteScuttler.getBrain().forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		shelobiteScuttler.getBrain().remember(MemoryModuleType.ANGRY_AT, target.getUuid(), 600L);
	}

	protected static void playSoundRandomly(ShelobiteScuttlerEntity shelobiteScuttlerEntity) {
		if (shelobiteScuttlerEntity.getWorld().random.nextFloat() < 0.0125) {
			playSoundIfAngry(shelobiteScuttlerEntity);
		}
	}

	private static void playSoundIfAngry(ShelobiteScuttlerEntity shelobiteScuttlerEntity) {
		shelobiteScuttlerEntity.getBrain().getFirstPossibleNonCoreActivity().ifPresent(activity -> {
			if (activity == Activity.FIGHT) {
				//mirkwoodSpiderEntity.playAngrySound();
			}
		});
	}
}
