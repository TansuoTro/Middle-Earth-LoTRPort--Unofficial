package net.jukoz.me.entity.ai.brain.task.npc;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.EntityTracker;
import net.minecraft.world.entity.ai.memory.NearestVisibleLivingEntities;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.Mob;
import java.util.Optional;
import java.util.function.Function;

public class NpcRangedApproachTask {
    public static BehaviorControl<Mob> create(float speed, int distance) {
        return create((entity) -> speed, (entity) -> distance);
    }

    public static BehaviorControl<Mob> create(Function<LivingEntity, Float> speed, Function<LivingEntity, Integer> distance) {
        return BehaviorBuilder.task((context) -> context.group(
                    context.queryMemoryOptional(MemoryModuleType.WALK_TARGET),
                    context.queryMemoryOptional(MemoryModuleType.LOOK_TARGET),
                    context.queryMemoryValue(MemoryModuleType.ATTACK_TARGET),
                    context.queryMemoryOptional(MemoryModuleType.VISIBLE_MOBS)
                )
                .apply(context, (walkTarget, lookTarget, attackTarget, visibleMobs) -> (world, entity, time) -> {
                    LivingEntity livingEntity = context.getValue(attackTarget);
                    Optional<NearestVisibleLivingEntities> optionalVisibleAttackTargets = context.getOptionalValue(visibleMobs);
                    if (optionalVisibleAttackTargets.isPresent() && (optionalVisibleAttackTargets.get()).contains(livingEntity) && entity.squaredDistanceTo(livingEntity) <= 1.0) {
                        walkTarget.forget();
                    } else {
                        lookTarget.remember(new EntityTracker(livingEntity, true));
                        walkTarget.remember(new WalkTarget(new EntityTracker(livingEntity, false), speed.apply(entity), distance.apply(entity)));
                    }

                    return true;
                }
            )
        );
    }
}
