package net.jukoz.me.api.entity.ai.brain.task;

import net.minecraft.entity.ai.NoPenaltySolidTargeting;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.entity.ai.brain.task.SingleTickTask;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.ai.brain.task.TaskTriggerer;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class StrollAroundHomeTask {
    public static Task<PathAwareEntity> create(float speed, int radius, boolean strollInWater) {
        return create(speed, entity -> findTargetPos(entity, radius), strollInWater ? entity -> true : entity -> !entity.isTouchingWater());
    }

    private static SingleTickTask<PathAwareEntity> create(float speed, Function<PathAwareEntity, Vec3> targetGetter, Predicate<PathAwareEntity> shouldRun) {
        return TaskTriggerer.task(
                context -> context.group(context.queryMemoryAbsent(MemoryModuleType.WALK_TARGET)).apply(context, walkTarget -> (world, entity, time) -> {
                    if (!shouldRun.test(entity)) {
                        return false;
                    } else {
                        Optional<Vec3> optional = Optional.ofNullable((Vec3)targetGetter.apply(entity));
                        walkTarget.remember(optional.map(pos -> new WalkTarget(pos, speed, 0)));
                        return true;
                    }
                })
        );
    }

    @Nullable
    private static Vec3 findTargetPos(PathAwareEntity entity, int radius) {
        Optional<GlobalPos> optional = entity.getBrain().getOptionalMemory(MemoryModuleType.HOME);
        Vec3 homePos;
        Vec3 entityPos = entity.getPos();

        if(optional != null && optional.isPresent()) {
            homePos = Vec3.ofBottomCenter(optional.get().getPos());
        }
        else {
            return null;
        }



        Vec3 direction = entityPos.relativize(homePos).normalize();
        return NoPenaltySolidTargeting.find(entity, radius, radius, -2, direction.x, direction.z, (float) (Math.PI / 2));
    }}
