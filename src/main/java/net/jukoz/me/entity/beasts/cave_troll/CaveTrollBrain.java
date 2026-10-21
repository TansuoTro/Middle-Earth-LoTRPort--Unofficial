package net.jukoz.me.entity.beasts.cave_troll;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.entity.ai.brain.task.*;
import net.minecraft.server.level.ServerLevel;
import net.jukoz.me.entity.ai.brain.ActivitiesME;
import net.jukoz.me.entity.ai.brain.MemoryModulesME;
import net.jukoz.me.entity.ai.brain.SensorsME;
import net.jukoz.me.entity.ai.brain.task.*;

import java.util.Optional;

public class CaveTrollBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super CaveTrollEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    protected static Brain<?> create(CaveTrollEntity troll, Dynamic<?> dynamic) {
        Brain.Profile<CaveTrollEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
        Brain<CaveTrollEntity> brain = profile.deserialize(dynamic);

        addCoreActivities(brain);
        addIdleActivities(brain);
        addTamedActivities(brain);
        addFightActivities(brain, troll);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.FIGHT);

        brain.resetPossibleActivities();
        return brain;
    }

    private static void addCoreActivities(Brain<CaveTrollEntity> brain) {
        brain.forget(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
        ));
    }

    private static void addIdleActivities(Brain<CaveTrollEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(
                Pair.of(0, UpdateAttackTargetTask.create(CaveTrollBrain::getAttackTarget)),
                Pair.of(0, UpdateAttackTargetTask.create(CaveTrollBrain::getHurtBy)),
                Pair.of(1, new RandomTask<>(ImmutableList.of(
                        Pair.of(StrollTask.create(1.0F), 5),
                        Pair.of(new CompositeTask<>(ImmutableMap.of(MemoryModulesME.ACTION_TIMEOUT, MemoryModuleState.VALUE_ABSENT), ImmutableSet.of(),
                                CompositeTask.Order.ORDERED,
                                CompositeTask.RunMode.TRY_ALL,
                                ImmutableList.of(
                                        Pair.of(new CaveTrollDigForFoodTask(), 1),
                                        Pair.of(new CaveTrollEatFoodTask(), 1),
                                        Pair.of(new CaveTrollSleepTask(), 1)
                                )), 1)
                )))
        ),
                ImmutableSet.of(
                        Pair.of(MemoryModulesME.TAME, MemoryModuleState.VALUE_ABSENT)
                ));
    }

    private static void addTamedActivities(Brain<CaveTrollEntity> brain) {
        brain.setTaskList(ActivitiesME.TAMED, ImmutableList.of(
                Pair.of(0, UpdateAttackTargetTask.create(CaveTrollBrain::getAttackTarget)),
                Pair.of(1, new CompositeTask<>(ImmutableMap.of(MemoryModulesME.SITTING, MemoryModuleState.VALUE_ABSENT), ImmutableSet.of(),
                        CompositeTask.Order.SHUFFLED,
                        CompositeTask.RunMode.RUN_ONE,
                        ImmutableList.of(
                                Pair.of(StrollTask.create(1.0f), 1)
                        )))
        ),
                ImmutableSet.of(
                        Pair.of(MemoryModulesME.TAME, MemoryModuleState.VALUE_PRESENT)
                )
        );
    }

    private static void addFightActivities(Brain<CaveTrollEntity> brain, CaveTrollEntity troll) {
        brain.setTaskList(Activity.FIGHT, ImmutableList.of(
                        Pair.of(0, ForgetAttackTargetTask.create()),
                        Pair.of(2, new RandomTask<>(ImmutableList.of(
                                Pair.of(MeleeAttackTask.create(30), 4),
                                Pair.of(RangedApproachTask.create(2.5F), 3),
                                Pair.of(new BeastChargeTask(troll.chargeDuration(), troll.maxChargeCooldown()), 1),
                                Pair.of(new CaveTrollRoarTask(), 2),
                                Pair.of(new CaveTrollSmashTask(), 2)
                        )))
                ),
                ImmutableSet.of(
                        Pair.of(MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT)
                )
        );
    }

    private static Optional<? extends LivingEntity> getAttackTarget(CaveTrollEntity troll) {
        return (troll.isSleeping() || troll.isSitting()) ? troll.getBrain().getOptionalRegisteredMemory(MemoryModuleType.HURT_BY_ENTITY) : troll.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    private static Optional<? extends LivingEntity> getHurtBy(CaveTrollEntity troll) {
        return troll.getBrain().getOptionalRegisteredMemory(MemoryModuleType.HURT_BY_ENTITY);
    }

    public static void updateActivities(CaveTrollEntity troll) {
        troll.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT, ActivitiesME.TAMED, Activity.IDLE));
        troll.getBrain().refreshActivities(troll.getWorld().getTimeOfDay(), troll.getWorld().getTime());
    }

    static {
        SENSORS = ImmutableList.of(
                SensorType.HURT_BY,
                SensorType.NEAREST_PLAYERS,
                SensorType.NEAREST_LIVING_ENTITIES,
                SensorType.IS_IN_WATER,
                SensorsME.CAVE_TROLL_ATTACKABLES
        );
        MEMORY_MODULES = ImmutableList.of(
                MemoryModuleType.WALK_TARGET,
                MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                MemoryModuleType.PATH,
                MemoryModuleType.HOME,
                MemoryModuleType.LAST_WOKEN,
                MemoryModuleType.HURT_BY,
                MemoryModuleType.HURT_BY_ENTITY,
                MemoryModuleType.VISIBLE_MOBS,
                MemoryModuleType.ATTACK_TARGET,
                MemoryModuleType.ATTACK_COOLING_DOWN,
                MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.NEAREST_ATTACKABLE,
                MemoryModuleType.NEAREST_PLAYERS,
                MemoryModulesME.DIG_FOR_FOOD_COOLDOWN,
                MemoryModulesME.FOOD_EATEN_COUNT,
                MemoryModulesME.TAME,
                MemoryModulesME.SITTING,
                MemoryModulesME.ROAR_COOLDOWN,
                MemoryModulesME.SMASH_COOLDOWN,
                MemoryModulesME.ACTION_TIMEOUT
        );
    }
}
