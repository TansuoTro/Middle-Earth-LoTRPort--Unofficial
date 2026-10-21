package net.jukoz.me.entity.npcs;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Range;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Dynamic;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.GlobalPos;
import net.jukoz.me.api.entity.ai.brain.MemoryModulesAPI;
import net.jukoz.me.entity.ai.brain.MemoryModulesME;
import net.jukoz.me.entity.ai.brain.SensorsME;
import net.jukoz.me.entity.ai.brain.task.npc.NpcMountedApproachTask;
import net.jukoz.me.entity.ai.brain.task.npc.NpcOnGroundApproachTask;
import net.jukoz.me.entity.ai.brain.task.npc.NpcRangedApproachTask;
import net.jukoz.me.entity.ai.brain.task.npc.NpcRangedAttackTask;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntity;
import net.jukoz.me.resources.datas.combatarchetypes.MeleeCombatArchetypeData;
import net.jukoz.me.resources.datas.combatarchetypes.data.CombatArchetype;
import net.jukoz.me.resources.datas.combatarchetypes.runtime.MeleeCombatArchetypeRuntimeData;
import net.jukoz.me.resources.datas.combatarchetypes.runtime.RangedCombatArchetypeRuntimeData;
import java.util.Optional;
import net.minecraft.world.entity.ai.behavior.DoNothing;
import net.minecraft.world.entity.ai.behavior.MeleeAttack;
import net.minecraft.world.entity.ai.behavior.RunOne;
import net.minecraft.world.entity.ai.behavior.SetWalkTargetFromAttackTargetIfTargetOutOfReach;
import net.minecraft.world.entity.ai.behavior.StartAttacking;
import net.minecraft.world.entity.ai.behavior.StopAttackingIfTargetInvalid;
import net.minecraft.world.entity.ai.behavior.UpdateActivityFromSchedule;
import net.minecraft.util.Tuple;

public class NpcBrain {
    protected static final ImmutableList<SensorType<? extends Sensor<? super NpcEntity>>> SENSORS;
    protected static final ImmutableList<MemoryModuleType<?>> MEMORY_MODULES;

    public NpcBrain() {
    }

    protected static Brain<?> create(Dynamic<?> dynamic) {
        Brain.Provider<NpcEntity> profile = Brain.createProfile(MEMORY_MODULES, SENSORS);
        Brain<NpcEntity> brain = profile.deserialize(dynamic);

        addCoreActivities(brain);
        addIdleActivities(brain);
        brain.setCoreActivities(ImmutableSet.of(Activity.CORE));
        brain.setDefaultActivity(Activity.FIGHT);

        brain.resetPossibleActivities();
        return brain;
    }

    public static void setMeleeActivities(Brain<NpcEntity> brain, NpcEntity npcEntity, MeleeCombatArchetypeRuntimeData runtimeData){
        addCoreActivities(brain);

        addIdleActivities(brain);
        addFightActivities(npcEntity, brain);
    }

    public static void setRangedActivities(Brain<NpcEntity> brain, NpcEntity npcEntity, RangedCombatArchetypeRuntimeData runtimeData){
        addCoreActivities(brain);

        addIdleActivities(brain);
        // FIGHT
        float movementSpeed = npcEntity.getFightingMovementSpeed();
        int attackSpeed = npcEntity.getTickAttackSpeedCooldown();

        brain.setTaskList(Activity.FIGHT, ImmutableList.of(
                        Tuple.of(0, StartAttacking.create(NpcBrain::getAttackTarget)),
                        Tuple.of(1, StopAttackingIfTargetInvalid.create()),
                        Tuple.of(2, NpcRangedAttackTask.create(attackSpeed)),
                        //Tuple.of(2, new NpcMountedApproachTask()),
                        Tuple.of(3, NpcRangedApproachTask.create(movementSpeed, 10))
                ),
                ImmutableSet.of(
                        Tuple.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT)
                ));
    }

    public static void updateActivities(NpcEntity npc) {
        Optional<LivingEntity> optionalTarget = npc.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET);
        if(optionalTarget != null && optionalTarget.isPresent()) {
            npc.getBrain().resetPossibleActivities(ImmutableList.of(Activity.FIGHT));
        }
        else {
            npc.getBrain().resetPossibleActivities(ImmutableList.of());
        }
        npc.getBrain().refreshActivities(npc.getWorld().getTimeOfDay(), npc.getWorld().getTime());
    }

    private static void addCoreActivities(Brain<NpcEntity> brain) {
        brain.erase(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);

        brain.setTaskList(Activity.CORE, 0, ImmutableList.of(
                //StartAttacking.create((world, npc) -> npc.getHurtBy()),
                StartAttacking.create(NpcBrain::getAttackTarget))
        );
    }

    private static void addIdleActivities(Brain<NpcEntity> brain) {
        brain.setTaskList(Activity.IDLE, ImmutableList.of(
                Tuple.of(1, new RunOne(ImmutableMap.of(MemoryModuleType.HOME, MemoryStatus.VALUE_PRESENT),
                    ImmutableList.of(
                        Tuple.of(new DoNothing(20, 100), 3)
                ))),
                Tuple.of(2, StartAttacking.create(NpcBrain::getAttackTarget)),
                Tuple.of(99, UpdateActivityFromSchedule.create())
        ));
    }

    private static void addFightActivities(NpcEntity npc, Brain<NpcEntity> brain) {
        float movementSpeed = npc.getFightingMovementSpeed();
        int attackSpeed = npc.getTickAttackSpeedCooldown();

        brain.setTaskList(Activity.FIGHT, ImmutableList.of(
                        Tuple.of(0, StartAttacking.create(NpcBrain::getAttackTarget)),
                        Tuple.of(1, StopAttackingIfTargetInvalid.create()),
                        Tuple.of(2, new NpcMountedApproachTask()),
                        Tuple.of(3, new NpcOnGroundApproachTask()),
                        Tuple.of(4, SetWalkTargetFromAttackTargetIfTargetOutOfReach.create(movementSpeed)),
                        Tuple.of(5, MeleeAttack.create(30))
                ),
                ImmutableSet.of(
                        Tuple.of(MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT)
                ));
    }

    private static Optional<? extends LivingEntity> getAttackTarget(NpcEntity npc) {
        return npc.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_ATTACKABLE);
    }

    private static boolean shouldForgetTarget(ServerLevel world, LivingEntity target, NpcEntity npc) {
        Optional<Boolean> defendingHome = npc.getBrain().getOptionalMemory(MemoryModulesAPI.DEFENDING_HOME);
        Optional<GlobalPos> home = npc.getBrain().getOptionalMemory(MemoryModuleType.HOME);

        if(home != null && home.isPresent() && defendingHome != null && defendingHome.isPresent()) {
            return target.blockPosition().getSquaredDistance(npc.blockPosition()) > 36;
        }

        return false;
    }

    static {
        SENSORS = ImmutableList.of(
                SensorType.HURT_BY,
                SensorType.NEAREST_BED,
                SensorType.NEAREST_PLAYERS,
                SensorType.NEAREST_LIVING_ENTITIES,
                SensorsME.NPC_ATTACKABLES
        );
        MEMORY_MODULES = ImmutableList.of(
                // Generic
                MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.WALK_TARGET,
                MemoryModuleType.PATH,
                MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,

                // NPC specific
                MemoryModulesME.STRUCTURE_MANAGER_HOST_POS,
                MemoryModulesME.ASSIGNED_BED_POS,

                // Fight specific
                MemoryModuleType.HOME,
                MemoryModuleType.NEAREST_ATTACKABLE,
                MemoryModuleType.ATTACK_TARGET,
                MemoryModuleType.ATTACK_COOLING_DOWN,
                MemoryModuleType.VISIBLE_MOBS,
                MemoryModuleType.HURT_BY,
                MemoryModuleType.HURT_BY_ENTITY
        );
    }
}
