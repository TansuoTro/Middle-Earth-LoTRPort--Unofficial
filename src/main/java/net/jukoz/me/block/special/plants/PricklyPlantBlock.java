package net.jukoz.me.block.special.plants;


import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.jukoz.me.utils.DamageablePlantsUtil;

public class PricklyPlantBlock extends CustomPlantBlock {
    public PricklyPlantBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && world instanceof ServerWorld serverWorld) {
            Vec3d vec3d = new Vec3d(entity.getX() - entity.lastRenderX, 0.0, entity.getZ() - entity.lastRenderZ);
            if (vec3d.horizontalLengthSquared() > 0.0) {
                double d = Math.abs(vec3d.getX());
                double e = Math.abs(vec3d.getZ());
                if (d >= 0.003 || e >= 0.003) {
                    DamageablePlantsUtil.tryDamageEntity(livingEntity, serverWorld, serverWorld.getDamageSources().cactus());
                }
            }
        }
    }
}
