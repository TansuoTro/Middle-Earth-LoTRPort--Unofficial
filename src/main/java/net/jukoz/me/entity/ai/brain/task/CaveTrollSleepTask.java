package net.jukoz.me.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.server.level.ServerLevel;
import net.jukoz.me.entity.ai.brain.MemoryModulesME;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntity;
import java.util.Optional;

public class CaveTrollSleepTask extends Behavior<CaveTrollEntity> {
    public CaveTrollSleepTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.WALK_TARGET,
                        MemoryStatus.VALUE_ABSENT,
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryStatus.VALUE_ABSENT,
                        MemoryModuleType.HURT_BY_ENTITY,
                        MemoryStatus.REGISTERED,
                        MemoryModulesME.FOOD_EATEN_COUNT,
                        MemoryStatus.VALUE_PRESENT
                ),1200, 3600
        );
    }

    @Override
    protected boolean shouldRun(ServerLevel world, CaveTrollEntity entity) {
        Optional<Integer> foodCount = entity.getBrain().getOptionalRegisteredMemory(MemoryModulesME.FOOD_EATEN_COUNT);

        return foodCount.isPresent() && foodCount.get() >= 3;
    }

    @Override
    protected boolean shouldKeepRunning(ServerLevel world, CaveTrollEntity entity, long time) {
        return entity.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).isEmpty();
    }

    @Override
    protected void run(ServerLevel world, CaveTrollEntity entity, long time) {
        entity.getBrain().remember(MemoryModulesME.FOOD_EATEN_COUNT, 0);
        entity.startSleeping();
    }

    @Override
    protected void finishRunning(ServerLevel world, CaveTrollEntity entity, long time) {
        entity.stopSleeping();
        entity.getBrain().remember(MemoryModulesME.DIG_FOR_FOOD_COOLDOWN, 600);
        entity.getBrain().remember(MemoryModulesME.ACTION_TIMEOUT, 200);
    }
}
