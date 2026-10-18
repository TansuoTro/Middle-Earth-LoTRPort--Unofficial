package net.jukoz.me.block.special.hangingstuff;

import net.minecraft.block.BlockState;
import net.minecraft.block.HangingMossBlock;
import net.minecraft.block.MultifaceBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

public class CustomHangingBlock extends HangingMossBlock {

    public CustomHangingBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return this.canPlaceAt(world, pos);
    }

    private boolean canPlaceAt(BlockView world, BlockPos pos) {
        BlockPos blockPos = pos.offset(Direction.UP);
        BlockState blockState = world.getBlockState(blockPos);
        return MultifaceBlock.canGrowOn(world, Direction.UP, blockPos, blockState) || blockState.isOf(this);
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (!this.canPlaceAt(world, pos)) {
            world.scheduleBlockTick(pos, this, 1);
        }

        return (BlockState)state.with(TIP, !world.getBlockState(pos.down()).isOf(this));
    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!this.canPlaceAt(world, pos)) {
            world.breakBlock(pos, true);
        }
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {

    }
}
