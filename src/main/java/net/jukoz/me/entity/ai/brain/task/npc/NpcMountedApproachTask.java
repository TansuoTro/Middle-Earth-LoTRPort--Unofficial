package net.jukoz.me.entity.ai.brain.task.npc;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.server.level.ServerLevel;
import net.jukoz.me.entity.ai.brain.MemoryModulesME;
import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.jukoz.me.entity.npcs.NpcEntity;

public class NpcMountedApproachTask extends MultiTickTask<NpcEntity> {
    public NpcMountedApproachTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryModuleState.VALUE_PRESENT,
                        MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                        MemoryModuleState.VALUE_PRESENT,
                        MemoryModuleType.ATTACK_COOLING_DOWN,
                        MemoryModuleState.VALUE_PRESENT,
                        MemoryModuleType.WALK_TARGET,
                        MemoryModuleState.VALUE_PRESENT
                ),35
        );
    }

    @Override
    protected boolean shouldRun(ServerLevel world, NpcEntity entity) {
        return entity.getTarget() != null && entity.hasVehicle() && entity.squaredDistanceTo(entity.getTarget()) < 16;
    }

    @Override
    protected boolean shouldKeepRunning(ServerLevel world, NpcEntity entity, long time) {
        return entity.hasVehicle();
    }

    @Override
    protected void run(ServerLevel world, NpcEntity entity, long time) {
        LivingEntity target = entity.getTarget();
        if(target == null || !entity.hasVehicle()){
            entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
            return;
        }
        float distance = target.distanceTo(entity);

        entity.setBlocking(true);

        if(entity.getVehicle() instanceof AbstractBeastEntity beastEntity){
            if(distance > 5 && distance < 16){
                beastEntity.chargeAttack();
            }
        }

        entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected void finishRunning(ServerLevel world, NpcEntity entity, long time) {
        entity.getBrain().remember(MemoryModulesME.SMASH_COOLDOWN, 200);
        entity.setBlocking(false);
    }
}

