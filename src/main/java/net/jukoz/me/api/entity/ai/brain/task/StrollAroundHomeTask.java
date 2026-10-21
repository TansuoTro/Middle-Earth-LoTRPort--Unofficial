package net.jukoz.me.api.entity.ai.brain.task;

import net.minecraft.world.entity.ai.util.AirAndWaterRandomPos;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

public class StrollAroundHomeTask {
    public static BehaviorControl<PathfinderMob> create(float speed, int radius, boolean strollInWater) {
        return create(speed, entity -> findTargetPos(entity, radius), strollInWater ? entity -> true : entity -> !entity.isInWater());
    }

    private static OneShot<PathfinderMob> create(float speed, Function<PathfinderMob, Vec3> targetGetter, Predicate<PathfinderMob> shouldRun) {
        return BehaviorBuilder.task(
                context -> context.group(context.absent(MemoryModuleType.WALK_TARGET)).apply(context, walkTarget -> (world, entity, time) -> {
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
    private static Vec3 findTargetPos(PathfinderMob entity, int radius) {
        Optional<GlobalPos> optional = entity.getBrain().getOptionalMemory(MemoryModuleType.HOME);
        Vec3 homePos;
        Vec3 entityPos = entity.position();

        if(optional != null && optional.isPresent()) {
            homePos = Vec3.atBottomCenterOf(optional.get().getPos());
        }
        else {
            return null;
        }

        Vec3 direction = entityPos.relativize(homePos).normalize();
        return AirAndWaterRandomPos.find(entity, radius, radius, -2, direction.x, direction.z, (float) (Math.PI / 2));
    }}
