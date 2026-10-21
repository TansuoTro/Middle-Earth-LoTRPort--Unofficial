package net.jukoz.me.entity.ai.brain.sensor;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestVisibleLivingEntitySensor;
import net.minecraft.server.level.ServerLevel;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntity;

public class CaveTrollAttackablesSensor extends NearestVisibleLivingEntitySensor {
    @Override
    protected boolean matches(LivingEntity entity, LivingEntity target) {
        return CaveTrollEntity.shouldTarget(target) && !((CaveTrollEntity)entity).isTame() && !isPassenger(entity, target);
    }

    private boolean isPassenger(LivingEntity troll, LivingEntity target) {
        return ((CaveTrollEntity)troll).getPassengerList().contains(target);
    }

    @Override
    protected MemoryModuleType<LivingEntity> getOutputMemoryModule() {
        return MemoryModuleType.NEAREST_ATTACKABLE;
    }
}
