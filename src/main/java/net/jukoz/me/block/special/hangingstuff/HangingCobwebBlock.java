package net.jukoz.me.block.special.hangingstuff;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.block.CobwebBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelView;
import org.jetbrains.annotations.Nullable;

public class HangingCobwebBlock extends CustomHangingBlock {
    public HangingCobwebBlock(Settings settings) {
        super(settings);
    }

    public void onEntityCollision(BlockState state, Level world, BlockPos pos, Entity entity) {
        Vec3 vec3d = new Vec3(0.25, 0.05000000074505806, 0.25);
        if (entity instanceof LivingEntity livingEntity) {
            if (livingEntity.hasStatusEffect(StatusEffects.SLOWNESS)) {
                vec3d = new Vec3(0.5, 0.25, 0.5);
            }
        }

        entity.slowMovement(state, vec3d);
    }


    @Override
    public void neighborUpdate(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos fromPos, boolean notify) {
        if (!world.isClient) {
            for (Direction direction : Direction.values()) {
                if (world.getFluidState(pos.offset(direction)).isIn(net.minecraft.tags.FluidTags.WATER)) {
                    world.breakBlock(pos, true);
                    return;
                }
            }
        }
        super.neighborUpdate(state, world, pos, sourceBlock, fromPos, notify);
    }

}
