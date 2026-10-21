package net.jukoz.me.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.server.level.ServerLevel;
import net.jukoz.me.entity.ai.brain.MemoryModulesME;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntity;

public class CaveTrollSmashTask extends Behavior<CaveTrollEntity> {
    public CaveTrollSmashTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryStatus.VALUE_PRESENT,
                        MemoryModulesME.SMASH_COOLDOWN,
                        MemoryStatus.VALUE_ABSENT
                ),30
        );
    }

    @Override
    protected boolean shouldRun(ServerLevel world, CaveTrollEntity entity) {
        return entity.getTarget() != null && entity.squaredDistanceTo(entity.getTarget()) < 16;
    }

    @Override
    protected boolean shouldKeepRunning(ServerLevel world, CaveTrollEntity entity, long time) {
        return true;
    }

    @Override
    protected void run(ServerLevel world, CaveTrollEntity entity, long time) {
        entity.setSmashing(true);
        entity.getBrain().forget(MemoryModuleType.WALK_TARGET);
    }

    @Override
    protected void finishRunning(ServerLevel world, CaveTrollEntity entity, long time) {
        entity.smashAttack(entity.getRandom().nextBetween(75, 125));
        entity.getBrain().remember(MemoryModulesME.SMASH_COOLDOWN, 200);
    }
}
