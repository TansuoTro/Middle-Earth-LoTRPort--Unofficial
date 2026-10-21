package net.jukoz.me.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.jukoz.me.entity.ai.brain.MemoryModulesME;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntity;
import java.util.List;

public class CaveTrollRoarTask extends Behavior<CaveTrollEntity> {
    long startTime;
    public CaveTrollRoarTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryStatus.VALUE_PRESENT,
                        MemoryModulesME.ROAR_COOLDOWN,
                        MemoryStatus.VALUE_ABSENT
                ),
                35
        );
    }

    @Override
    protected boolean shouldKeepRunning(ServerLevel world, CaveTrollEntity entity, long time) {
        return true;
    }

    @Override
    protected void run(ServerLevel world, CaveTrollEntity troll, long time) {
        this.startTime = time;
        troll.setRoaring(true);
        troll.getBrain().forget(MemoryModuleType.WALK_TARGET);

        List<Entity> entities = world.getOtherEntities(troll, troll.getBoundingBox().expand(15, 15, 15)); // Get nearby entities
        for(Entity entity : entities) { // Run through all entities
            if(troll.isValidTarget(entity)) { // Apply bad effects if entity is valid target
                ((LivingEntity)entity).addStatusEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200), troll);
                ((LivingEntity)entity).addStatusEffect(new MobEffectInstance(MobEffects.SLOWNESS, 100), troll);
            }
        }
    }

    @Override
    protected void keepRunning(ServerLevel world, CaveTrollEntity entity, long time) {
        if(time - startTime == 10) {
            entity.playRoarSound();
        }
    }

    @Override
    protected boolean isTimeLimitExceeded(long time) {
        return super.isTimeLimitExceeded(time);
    }

    @Override
    protected void finishRunning(ServerLevel world, CaveTrollEntity entity, long time) {
        entity.setRoaring(false);
        entity.getBrain().remember(MemoryModulesME.ROAR_COOLDOWN, 600);
    }
}
