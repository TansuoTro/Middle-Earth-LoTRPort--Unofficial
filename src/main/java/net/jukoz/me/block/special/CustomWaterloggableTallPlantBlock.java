package net.jukoz.me.block.special;

import net.minecraft.block.*;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccess;
import net.minecraft.world.level.LevelView;


public class CustomWaterloggableTallPlantBlock extends TallPlantBlock implements Fertilizable, Waterloggable {
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private final boolean randomBoneMeal;
    public CustomWaterloggableTallPlantBlock(Settings settings, boolean random) {
        super(settings);
        this.randomBoneMeal = random;
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(HALF, DoubleBlockHalf.LOWER).with(WATERLOGGED, false));
    }

    public void placeAt(WorldAccess world, BlockPos pos, boolean waterlogged, int flags) {
        world.setBlockState(pos, this.getDefaultState().with(HALF, DoubleBlockHalf.LOWER).with(WATERLOGGED, waterlogged), flags);
        world.setBlockState(pos.up(), this.getDefaultState().with(HALF, DoubleBlockHalf.UPPER).with(WATERLOGGED, false), flags);
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(HALF) == DoubleBlockHalf.LOWER && state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        DoubleBlockHalf doubleBlockHalf = (DoubleBlockHalf)state.get(HALF);
        if (direction.getAxis() == Direction.Axis.Y && doubleBlockHalf == DoubleBlockHalf.LOWER == (direction == Direction.UP) && (!neighborState.isOf(this) || neighborState.get(HALF) == doubleBlockHalf)) {
            return Blocks.AIR.getDefaultState();
        } else {
            return doubleBlockHalf == DoubleBlockHalf.LOWER && direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        }
    }

    @Override
    public void neighborUpdate(BlockState state, Level world, BlockPos pos, Block sourceBlock, BlockPos fromPos, boolean notify) {
        if (!world.isClient && state.get(HALF) == DoubleBlockHalf.UPPER && (
                world.getBlockState(pos.up()).getFluidState().isOf(Fluids.WATER)
                        || world.getBlockState(pos.north()).getFluidState().isOf(Fluids.WATER)
                        || world.getBlockState(pos.south()).getFluidState().isOf(Fluids.WATER)
                        || world.getBlockState(pos.east()).getFluidState().isOf(Fluids.WATER)
                        || world.getBlockState(pos.west()).getFluidState().isOf(Fluids.WATER))) {
            this.breakUpperFromWater(world, pos, Blocks.AIR.getDefaultState());
            return;
        }
        super.neighborUpdate(state, world, pos, sourceBlock, fromPos, notify);
    }

    @Override
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        Level world = ctx.getWorld();
        if(blockPos.getY() < world.getTopY() - 2 && world.getBlockState(blockPos.up()).canReplace(ctx)){
            if(blockState.isOf(Blocks.WATER)){
                return this.getDefaultState().with(WATERLOGGED, true);
            } else{
                return this.getDefaultState();
            }
        } else {
            return null;
        }
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HALF, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(HALF) == DoubleBlockHalf.LOWER && state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    @Override
    public boolean canBucketPlace(BlockState state, Fluid fluid) {
        return state.get(HALF) == DoubleBlockHalf.LOWER && super.canBucketPlace(state, fluid);
    }

    @Override
    public boolean canFillWithFluid(BlockGetter world, BlockPos pos, BlockState state, Fluid fluid) {
        if (fluid != Fluids.WATER) {
            return false;
        }

        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            return true;
        }
        return Waterloggable.super.canFillWithFluid(world, pos, state, fluid);
    }

    @Override
    public boolean tryFillWithFluid(WorldAccess world, BlockPos pos, BlockState state, FluidState fluidState) {
        if (!fluidState.isOf(Fluids.WATER)) {
            return false;
        }

        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            this.breakUpperFromWater(world, pos, Blocks.WATER.getDefaultState());
            return true;
        }
        return Waterloggable.super.tryFillWithFluid(world, pos, state, fluidState);
    }

    @Override
    public void onPlaced(Level world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        world.setBlockState(pos.up(), state.with(HALF, DoubleBlockHalf.UPPER).with(WATERLOGGED, false), 3);
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean canGrow(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        if(this.randomBoneMeal) {
            float val = random.nextFloat();
            if(val > 0.90f){
                dropStack(world, pos, new ItemStack(this));
            }
        } else {
            dropStack(world, pos, new ItemStack(this));
        }
    }

    private void breakUpperFromWater(WorldAccess world, BlockPos upperPos, BlockState upperReplacementState) {
        BlockState upperState = world.getBlockState(upperPos);
        if (!upperState.isOf(this) || upperState.get(HALF) != DoubleBlockHalf.UPPER) {
            return;
        }

        if (world instanceof Level actualWorld && !actualWorld.isClient) {
            dropStacks(upperState, actualWorld, upperPos);
        }

        BlockPos lowerPos = upperPos.down();
        BlockState lowerState = world.getBlockState(lowerPos);
        if (lowerState.isOf(this) && lowerState.get(HALF) == DoubleBlockHalf.LOWER) {
            BlockState lowerReplacementState = lowerState.get(WATERLOGGED) ? Blocks.WATER.getDefaultState() : Blocks.AIR.getDefaultState();
            world.setBlockState(lowerPos, lowerReplacementState, 3);
        }

        world.setBlockState(upperPos, upperReplacementState, 3);
    }
}
