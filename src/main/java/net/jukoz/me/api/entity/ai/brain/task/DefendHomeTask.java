package net.jukoz.me.api.entity.ai.brain.task;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.SingleTickTask;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.GlobalPos;
import net.jukoz.me.api.entity.ai.brain.MemoryModulesAPI;

import java.util.List;
import java.util.Optional;

public class DefendHomeTask {
    public static SingleTickTask<LivingEntity> create(double radius) {
        return TaskTriggerer.task((context) -> {
            return context.group(context.queryMemoryAbsent(MemoryModuleType.ATTACK_TARGET)).apply(context, (attackTarget) -> {
                return (world, entity, time) -> {
                    Optional<LivingEntity> optional = Optional.ofNullable(threatClose(world, entity, radius));

                    if(optional.isEmpty()) {
                        return true;
                    }
                    entity.getBrain().remember(MemoryModulesAPI.DEFENDING_HOME, true);
                    attackTarget.remember(optional);
                    return true;
                };
            });
        });
    }


    private static LivingEntity threatClose(ServerLevel world, LivingEntity entity, double radius) {
        if(entity.isBaby()) {
            return null;
        }

        Optional<GlobalPos> optionalHome = entity.getBrain().getOptionalMemory(MemoryModuleType.HOME);
        Optional<List<Player>> optionalPlayers = entity.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_PLAYERS);

        if(optionalHome != null && optionalHome.isPresent()) {
            if(optionalPlayers != null && optionalPlayers.isPresent()) {
                for(Player player : optionalPlayers.get()) {
                    if(world.isNight() && player.isSneaking()) { // Don't trigger if player is sneaking
                        return null;
                    }

                    if(entity instanceof AnimalEntity && ((AnimalEntity)entity).isInLove()) { // Don't trigger if entity is distracted
                        return null;
                    }

                    if(player.getPos().squaredDistanceTo(optionalHome.get().getPos().toCenterPos()) < (radius*radius) && !player.isCreative()) {
                        return player;
                    }
                }
            }
        }

        return null;
    }
}
