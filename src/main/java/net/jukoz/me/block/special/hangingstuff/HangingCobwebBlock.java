package net.jukoz.me.block.special.hangingstuff;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.WebBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import org.jetbrains.annotations.Nullable;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class HangingCobwebBlock extends CustomHangingBlock {
    public HangingCobwebBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    public void onEntityCollision(BlockState state, Level world, BlockPos pos, Entity entity) {
        Vec3 vec3d = new Vec3(0.25, 0.05000000074505806, 0.25);
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.hasStatusEffect(MobEffects.SLOWNESS)) {
                vec3d = new Vec3(0.5, 0.25, 0.5);
            }
        }

        entity.slowMovement(state, vec3d);
    }

    @Override
    public void neighborUpdate(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos fromPos, boolean notify) {
        if (!world.isClientSide) {
            for (Direction direction : Direction.values()) {
                if (world.getFluidState(pos.offset(direction)).is(net.minecraft.tags.FluidTags.WATER)) {
                    world.breakBlock(pos, true);
                    return;
                }
            }
        }
        super.neighborUpdate(state, world, pos, sourceBlock, fromPos, notify);
    }

}
