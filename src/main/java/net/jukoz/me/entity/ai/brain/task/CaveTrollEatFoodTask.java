package net.jukoz.me.entity.ai.brain.task;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.world.item.Item;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.ai.brain.MemoryModulesME;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntity;

import java.util.Optional;

public class CaveTrollEatFoodTask extends MultiTickTask<CaveTrollEntity> {
    private long startTime;
    public static TagKey<Item> TROLL_FOOD = TagKey.of(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "troll_food"));

    public CaveTrollEatFoodTask() {
        super(
                ImmutableMap.of(
                        MemoryModuleType.WALK_TARGET,
                        MemoryModuleState.VALUE_ABSENT,
                        MemoryModuleType.ATTACK_TARGET,
                        MemoryModuleState.VALUE_ABSENT,
                        MemoryModulesME.FOOD_EATEN_COUNT,
                        MemoryModuleState.REGISTERED
                ),
                200
        );
    }

    @Override
    protected boolean shouldRun(ServerLevel world, CaveTrollEntity entity) {
        return !entity.getMainHandStack().isEmpty() && entity.getMainHandStack().isIn(TROLL_FOOD);
    }

    @Override
    protected boolean shouldKeepRunning(ServerLevel world, CaveTrollEntity entity, long time) {
        return hasRequiredMemoryState(entity) && !entity.getMainHandStack().isEmpty();
    }

    @Override
    protected void run(ServerLevel world, CaveTrollEntity entity, long time) {
        entity.setSitting(true);
        this.startTime = time;
    }

    @Override
    protected void keepRunning(ServerLevel world, CaveTrollEntity entity, long time) {
        if((time - startTime) > 60 && !entity.getMainHandStack().isEmpty()) {
            ItemStackParticleEffect particles = new ItemStackParticleEffect(ParticleTypes.ITEM, entity.getMainHandStack());
            Vec3 position = new Vec3(entity.getX() - Math.sin(Math.toRadians(entity.getBodyYaw())), entity.getEyeY(), entity.getZ() + Math.cos(Math.toRadians(entity.getBodyYaw())));
            world.spawnParticles(particles, position.getX(), position.getY() - 1.0, position.getZ(),7, 0.0, 0.0, 0.0, 0.1);
        }
    }

    @Override
    protected void finishRunning(ServerLevel world, CaveTrollEntity entity, long time) {
        Optional<Integer> foodCount = entity.getBrain().getOptionalRegisteredMemory(MemoryModulesME.FOOD_EATEN_COUNT);
        foodCount.ifPresentOrElse(
                count -> entity.getBrain().remember(MemoryModulesME.FOOD_EATEN_COUNT, count + 1), // If present
                () -> entity.getBrain().remember(MemoryModulesME.FOOD_EATEN_COUNT, 1)); // If absent

        entity.getBrain().remember(MemoryModulesME.ACTION_TIMEOUT, 200);
        entity.setSitting(false);

        entity.getMainHandStack().decrement(1);
    }
}
