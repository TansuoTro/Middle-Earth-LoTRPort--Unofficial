package net.jukoz.me.entity.ai.brain.task.npc;

import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.behavior.OneShot;
import net.minecraft.world.entity.ai.behavior.declarative.BehaviorBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.world.phys.Vec3;
import net.jukoz.me.entity.npcs.NpcEntity;
import java.util.Optional;

public class NpcWalkTowardsTask {
    public static OneShot<NpcEntity> create(MemoryModuleType<GlobalPos> destination, float speed, int completionRange, int maxDistance, int maxRunTime) {
        return BehaviorBuilder.task((context) -> context.group(context.queryMemoryOptional(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE), context.queryMemoryAbsent(MemoryModuleType.WALK_TARGET), context.queryMemoryValue(destination)).apply(context, (cantReachWalkTargetSince, walkTarget, destinationResult) -> (world, entity, time) -> {
            GlobalPos globalPos = (GlobalPos)context.getValue(destinationResult);
            Optional<Long> optional = context.getOptionalValue(cantReachWalkTargetSince);
            if (globalPos.getDimension() == world.getRegistryKey() && (!optional.isPresent() || world.getTime() - (Long)optional.get() <= (long)maxRunTime)) {
                if (globalPos.getPos().getManhattanDistance(entity.getBlockPos()) > maxDistance) {
                    Vec3 vec3d = null;
                    int l = 0;
                    int m = 1000;

                    while(vec3d == null || BlockPos.ofFloored(vec3d).getManhattanDistance(entity.getBlockPos()) > maxDistance) {
                        vec3d = DefaultRandomPos.findTo(entity, 15, 7, Vec3.ofBottomCenter(globalPos.getPos()), (double)((float)Math.PI / 2F));
                        ++l;
                        if (l == 1000) {
                            //entity.releaseTicketFor(destination);
                            destinationResult.forget();
                            cantReachWalkTargetSince.remember(time);
                            return true;
                        }
                    }

                    walkTarget.remember(new WalkTarget(vec3d, speed, completionRange));
                } else if (globalPos.getPos().getManhattanDistance(entity.getBlockPos()) > completionRange) {
                    walkTarget.remember(new WalkTarget(globalPos.getPos(), speed, completionRange));
                }
            } else {
                //entity.releaseTicketFor(destination);
                destinationResult.forget();
                cantReachWalkTargetSince.remember(time);
            }

            return true;
        }));
    }

}
