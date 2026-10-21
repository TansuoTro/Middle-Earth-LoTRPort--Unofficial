package net.jukoz.me.block.special;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.GlowLichenBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.jukoz.me.MiddleEarthClient;
import net.jukoz.me.utils.DamageablePlantsUtil;

public class ThornyGrowthBlock extends GlowLichenBlock {
    public ThornyGrowthBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void onEntityCollision(BlockState state, Level world, BlockPos pos, Entity entity) {
        if (entity instanceof LivingEntity livingEntity && world instanceof ServerLevel serverWorld) {
            Vec3 movement = new Vec3(livingEntity.getX() - livingEntity.lastRenderX, 0.0, livingEntity.getZ() - livingEntity.lastRenderZ);
            if (movement.horizontalLengthSquared() > 0.0) {
                double d = Math.abs(movement.getX());
                double e = Math.abs(movement.getZ());
                if (d >= 0.003000000026077032 || e >= 0.003000000026077032) {
                    DamageablePlantsUtil.tryDamageEntity(livingEntity, serverWorld, serverWorld.getDamageSources().sweetBerryBush());
                }
            }
        }
    }
}
