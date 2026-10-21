package net.jukoz.me.entity.ai.brain.task.npc;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.ai.brain.EntityLookTarget;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.world.entity.Mob;

import java.util.Optional;
import java.util.function.Function;

public class NpcRangedApproachTask {
    public static Task<Mob> create(float speed, int distance) {
        return create((entity) -> speed, (entity) -> distance);
    }

    public static Task<Mob> create(Function<LivingEntity, Float> speed, Function<LivingEntity, Integer> distance) {
        return TaskTriggerer.task((context) -> context.group(
                    context.queryMemoryOptional(MemoryModuleType.WALK_TARGET),
                    context.queryMemoryOptional(MemoryModuleType.LOOK_TARGET),
                    context.queryMemoryValue(MemoryModuleType.ATTACK_TARGET),
                    context.queryMemoryOptional(MemoryModuleType.VISIBLE_MOBS)
                )
                .apply(context, (walkTarget, lookTarget, attackTarget, visibleMobs) -> (world, entity, time) -> {
                    LivingEntity livingEntity = context.getValue(attackTarget);
                    Optional<LivingTargetCache> optionalVisibleAttackTargets = context.getOptionalValue(visibleMobs);
                    if (optionalVisibleAttackTargets.isPresent() && (optionalVisibleAttackTargets.get()).contains(livingEntity) && entity.squaredDistanceTo(livingEntity) <= 1.0) {
                        walkTarget.forget();
                    } else {
                        lookTarget.remember(new EntityLookTarget(livingEntity, true));
                        walkTarget.remember(new WalkTarget(new EntityLookTarget(livingEntity, false), speed.apply(entity), distance.apply(entity)));
                    }

                    return true;
                }
            )
        );
    }
}
