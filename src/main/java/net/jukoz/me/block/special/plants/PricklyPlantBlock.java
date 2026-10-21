package net.jukoz.me.block.special.plants;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.jukoz.me.utils.DamageablePlantsUtil;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class PricklyPlantBlock extends CustomPlantBlock {
    public PricklyPlantBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && world instanceof ServerLevel serverWorld) {
            Vec3 vec3d = new Vec3(entity.getX() - entity.lastRenderX, 0.0, entity.getZ() - entity.lastRenderZ);
            if (vec3d.horizontalLengthSquared() > 0.0) {
                double d = Math.abs(vec3d.getX());
                double e = Math.abs(vec3d.getZ());
                if (d >= 0.003 || e >= 0.003) {
                    DamageablePlantsUtil.tryDamageEntity(livingEntity, serverWorld, serverWorld.damageSources().cactus());
                }
            }
        }
    }
}
