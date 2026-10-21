package net.jukoz.me.block.special;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.ScaffoldingBlock;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.jukoz.me.item.DecorativeItemsME;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

public class ReinforcedScaffoldingBlock extends ScaffoldingBlock {
    public static final int MAX_SUPPORT_DISTANCE = 14;
    private static final int MAX_STORED_DISTANCE = 7;
    public static final IntegerProperty SUPPORT_DISTANCE = IntegerProperty.create("support_distance", 0, MAX_SUPPORT_DISTANCE);

    public ReinforcedScaffoldingBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.setDefaultState(this.stateManager.defaultBlockState().with(SUPPORT_DISTANCE, 0));
    }

    @Override
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        BlockPos pos = ctx.getBlockPos();
        Level world = ctx.getWorld();
        int distance = calculateDistance(world, pos, this);
        return this.defaultBlockState()
                .with(WATERLOGGED, world.getFluidState(pos).getFluid() == Fluids.WATER)
                .with(DISTANCE, toStoredDistance(distance))
                .with(SUPPORT_DISTANCE, distance)
                .with(BOTTOM, shouldBeBottom(world, pos, distance, this));
    }

    @Override
    public ItemStack getPickStack(BlockGetter world, BlockPos pos, BlockState state) {
        return DecorativeItemsME.REINFORCED_SCAFFOLDING.getDefaultInstance();
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }

        if (!world.isClientSide()) {
            world.scheduleRecheck(pos, this, 1);
        }

        return state;
    }

    @Override
    public void scheduledTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int distance = calculateDistance(world, pos, this);
        BlockState updatedState = state.setValue(DISTANCE, toStoredDistance(distance))
                .with(SUPPORT_DISTANCE, distance)
                .with(BOTTOM, shouldBeBottom(world, pos, distance, this));
        if (distance == MAX_SUPPORT_DISTANCE) {
            if (state.get(SUPPORT_DISTANCE) == MAX_SUPPORT_DISTANCE) {
                FallingBlockEntity.spawnFromBlock(world, pos, updatedState);
            } else {
                world.breakBlock(pos, true);
            }
        } else if (state != updatedState) {
            world.setBlock(pos, updatedState, 3);
        }
    }

    @Override
    public boolean canPlaceAt(BlockState state, LevelReader world, BlockPos pos) {
        return calculateDistance(world, pos, this) < MAX_SUPPORT_DISTANCE;
    }

    @Override
    public boolean canReplace(BlockState state, BlockPlaceContext context) {
        return context.getItem().getItem() instanceof BlockItem blockItem
                && blockItem.getBlock() == this;
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(SUPPORT_DISTANCE);
    }

    public static boolean shouldBeBottom(BlockGetter world, BlockPos pos, int distance, Block scaffoldingBlock) {
        return distance > 0 && !world.getBlockState(pos.below()).is(scaffoldingBlock);
    }

    public static int calculateDistance(BlockGetter world, BlockPos pos, Block scaffoldingBlock) {
        Map<BlockPos, Integer> supportDistances = new HashMap<>();
        ArrayDeque<BlockPos> positionsToCheck = new ArrayDeque<>();
        BlockPos startPos = pos.toImmutable();

        supportDistances.put(startPos, 0);
        positionsToCheck.add(startPos);

        while (!positionsToCheck.isEmpty()) {
            BlockPos currentPos = positionsToCheck.removeFirst();
            int currentDistance = supportDistances.get(currentPos);
            BlockPos belowPos = currentPos.below();
            BlockState belowState = world.getBlockState(belowPos);

            if (belowState.is(scaffoldingBlock)) {
                addSupportPosition(positionsToCheck, supportDistances, belowPos, currentDistance, true);
            } else if (belowState.isSideSolidFullSquare(world, belowPos, Direction.UP)) {
                return Math.min(currentDistance, MAX_SUPPORT_DISTANCE);
            }

            if (currentDistance >= MAX_SUPPORT_DISTANCE) {
                continue;
            }

            for (Direction direction : Direction.Axis.HORIZONTAL) {
                BlockPos horizontalPos = currentPos.offset(direction);
                if (world.getBlockState(horizontalPos).is(scaffoldingBlock)) {
                    addSupportPosition(positionsToCheck, supportDistances, horizontalPos, currentDistance + 1, false);
                }
            }
        }

        return MAX_SUPPORT_DISTANCE;
    }

    private static void addSupportPosition(ArrayDeque<BlockPos> positionsToCheck, Map<BlockPos, Integer> supportDistances, BlockPos pos, int distance, boolean checkFirst) {
        Integer knownDistance = supportDistances.get(pos);
        if (knownDistance != null && knownDistance <= distance) {
            return;
        }

        supportDistances.put(pos, distance);
        if (checkFirst) {
            positionsToCheck.addFirst(pos);
        } else {
            positionsToCheck.addLast(pos);
        }
    }

    private static int toStoredDistance(int distance) {
        return Math.min(distance, MAX_STORED_DISTANCE);
    }
}
