package net.jukoz.me.block.special.plants;

import net.minecraft.world.level.LevelAccessor;
import net.jukoz.me.block.registration.ModNatureBlocks;
import net.jukoz.me.block.special.CustomWaterloggableTallPlantBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class WaterloggablePlant extends CustomPlantBlock implements BonemealableBlock, SimpleWaterloggedBlock {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public WaterloggablePlant(BlockBehaviour.Properties settings) {
        super(settings);
        this.setDefaultState((BlockState)((BlockState)this.stateManager.defaultBlockState()).with(WATERLOGGED, false));
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());

        if(blockState.is(Blocks.WATER)){
            return this.defaultBlockState().with(WATERLOGGED, true);
        } else{
            return this.defaultBlockState();
        }
    }

    @Override
    public boolean isFertilizable(LevelReader world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canGrow(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        CustomWaterloggableTallPlantBlock tallPlantBlock = (CustomWaterloggableTallPlantBlock)(state.is(ModNatureBlocks.SHORT_BULRUSH) ? ModNatureBlocks.TALL_BULRUSH : ModNatureBlocks.TALL_CATTAILS);
        if (tallPlantBlock.defaultBlockState().canPlaceAt(world, pos) && world.isAir(pos.above())) {
            tallPlantBlock.placeAt(world, pos, state.get(WATERLOGGED), 2);
        }
    }
}
