package net.jukoz.me.block.special.gemstones;

import net.jukoz.me.block.registration.ModBlocks;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BuddingAmethystBlock;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;

import java.util.ArrayList;
import java.util.List;

public class CustomBuddingGemBlock extends BuddingAmethystBlock {

    private static final Direction[] DIRECTIONS = Direction.values();

    private final List<Block> clusters;

    public CustomBuddingGemBlock(Settings settings, List<Block> clustersIn) {
        super(settings);
        clusters = clustersIn;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        if (random.nextInt(5) == 0) {
            Direction direction = DIRECTIONS[random.nextInt(DIRECTIONS.length)];
            BlockPos blockPos = pos.offset(direction);
            BlockState blockState = world.getBlockState(blockPos);
            Block block = null;
            if (canGrowIn(blockState)) {
                block = clusters.get(0);
            } else if (blockState.isOf(clusters.get(0)) && blockState.get(AmethystClusterBlock.FACING) == direction) {
                block = clusters.get(1);
            } else if (blockState.isOf(clusters.get(1)) && blockState.get(AmethystClusterBlock.FACING) == direction) {
                block = clusters.get(2);
            } else if (blockState.isOf(clusters.get(2)) && blockState.get(AmethystClusterBlock.FACING) == direction) {
                block = clusters.get(3);
            }



            if (block != null) {
                BlockState blockState2 = (BlockState)((BlockState)block.getDefaultState().with(AmethystClusterBlock.FACING, direction)).with(AmethystClusterBlock.WATERLOGGED, blockState.getFluidState().getFluid() == Fluids.WATER);
                world.setBlockState(blockPos, blockState2);
            }

        }
    }
}
