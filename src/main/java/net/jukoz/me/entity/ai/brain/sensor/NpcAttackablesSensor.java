package net.jukoz.me.entity.ai.brain.sensor;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.NearestVisibleLivingEntitySensor;
import net.minecraft.server.world.ServerWorld;
import net.jukoz.me.entity.npcs.NpcEntity;

public class NpcAttackablesSensor extends NearestVisibleLivingEntitySensor {
    @Override
    protected boolean matches(LivingEntity entity, LivingEntity target) {
        return NpcEntity.shouldTarget((NpcEntity) entity, target);
    }

    @Override
    protected MemoryModuleType<LivingEntity> getOutputMemoryModule() {
        return MemoryModuleType.NEAREST_ATTACKABLE;
    }
}
