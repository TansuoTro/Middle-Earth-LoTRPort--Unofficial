package net.jukoz.me.entity.ai.brain.task.npc;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.server.level.ServerLevel;
import net.jukoz.me.entity.ai.brain.MemoryModulesME;
import net.jukoz.me.entity.npcs.NpcEntity;

public class NpcOnGroundApproachTask extends Behavior<NpcEntity> {
    public NpcOnGroundApproachTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryStatus.VALUE_PRESENT,
                        MemoryModulesME.SMASH_COOLDOWN,
                        MemoryStatus.VALUE_ABSENT
                )
        );
    }

    @Override
    protected boolean shouldRun(ServerLevel world, NpcEntity entity) {
        return entity.getTarget() != null && !entity.isPassenger() && entity.distanceTo(entity.getTarget()) < 16;
    }

    @Override
    protected boolean shouldKeepRunning(ServerLevel world, NpcEntity entity, long time) {
        return !entity.isPassenger();
    }

    @Override
    protected void run(ServerLevel world, NpcEntity entity, long time) {
        LivingEntity target = entity.getTarget();
        if(target == null || entity.isPassenger()){
            entity.getBrain().erase(MemoryModuleType.WALK_TARGET);
            return;
        }

        float distance = target.distanceTo(entity);
        entity.setBlocking(true);

        entity.getBrain().erase(MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected void finishRunning(ServerLevel world, NpcEntity entity, long time) {
        entity.setBlocking(false);
    }
}
