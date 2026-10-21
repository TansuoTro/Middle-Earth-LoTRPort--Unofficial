package net.jukoz.me.block.special;

import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;

public class LayersBlock extends SnowLayerBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public Block fullBlock;

    public LayersBlock(Settings settings, Block fullBlockIn) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.defaultBlockState()).with(LAYERS, 1).with(WATERLOGGED, false));

        fullBlock = fullBlockIn;
    }

    @Override
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.is(this)) {
            int i = (Integer)blockState.get(LAYERS);
            if(i == 7){
                return fullBlock.defaultBlockState();
            } else {
                if (blockState.get(WATERLOGGED)){
                    return (BlockState)blockState.setValue(LAYERS, Math.min(8, i + 1)).with(WATERLOGGED, true);
                } else {
                    return (BlockState)blockState.setValue(LAYERS, Math.min(8, i + 1));
                }
            }
        } else {
            if(blockState.is(Blocks.WATER)){
                return super.getPlacementState(ctx).with(WATERLOGGED, true);
            } else {
                return super.getPlacementState(ctx);

            }
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(LAYERS, WATERLOGGED);
    }
}
