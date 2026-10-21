package net.jukoz.me.api.entity.ai.brain.task;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.ai.util.LandRandomPos;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.world.entity.Pose;

public class FleeFromEntityTask<E extends PathfinderMob> extends Behavior<E> {
    private ImmutableList<Class<? extends Entity>> entities;
    private int distance;
    private float speed;

    public FleeFromEntityTask(ImmutableList<Class<? extends Entity>> entities, int distance, float speed) {
        super(Map.of(), 100, 120);

        this.entities = entities;
        this.distance = distance;
        this.speed = speed;
    }

    @Override
    protected boolean shouldRun(ServerLevel world, E pathAwareEntity) {
        for(Class<? extends Entity> mob : entities) {
            if(!world.getEntitiesByClass(mob, pathAwareEntity.getBoundingBox().expand(distance), EntitySelector.EXCEPT_CREATIVE_OR_SPECTATOR).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    protected void run(ServerLevel serverWorld, E pathAwareEntity, long l) {
        pathAwareEntity.getBrain().remember(MemoryModuleType.IS_PANICKING, true);
        pathAwareEntity.getBrain().forget(MemoryModuleType.WALK_TARGET);
        pathAwareEntity.setPose(Pose.STANDING);
    }

    protected void finishRunning(ServerLevel serverWorld, E pathAwareEntity, long l) {
        Brain<?> brain = pathAwareEntity.getBrain();
        brain.forget(MemoryModuleType.IS_PANICKING);
        pathAwareEntity.setPose(Pose.STANDING);
    }

    @Override
    protected boolean shouldKeepRunning(ServerLevel world, E entity, long time) {
        return true;
    }

    protected void keepRunning(ServerLevel serverWorld, E pathAwareEntity, long l) {
        if (pathAwareEntity.getNavigation().isIdle()) {
            Vec3 vec3d = this.findTarget(pathAwareEntity, serverWorld);
            if (vec3d != null) {
                pathAwareEntity.getBrain().remember(MemoryModuleType.WALK_TARGET, new WalkTarget(vec3d, this.speed, 0));
            }
        }
    }

    @Nullable
    private Vec3 findTarget(E entity, ServerLevel world) {
        List<? extends Entity> fleeEntities = new ArrayList<>();
        Vec3 direction = entity.getPos();

        for(Class<? extends Entity> mob : entities) {
            if(!(fleeEntities = world.getEntitiesByClass(mob, entity.getBoundingBox().expand(distance), EntitySelector.EXCEPT_CREATIVE_OR_SPECTATOR)).isEmpty()) {
                break;
            }
        }
        if(!fleeEntities.isEmpty()) {
            return LandRandomPos.findFrom(entity, distance, 4, fleeEntities.get(0).getPos());
        }

        return LandRandomPos.findTo(entity, 7, 4, direction);
    }
}
