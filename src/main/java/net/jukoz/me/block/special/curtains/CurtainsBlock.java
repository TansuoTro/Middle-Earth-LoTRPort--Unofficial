package net.jukoz.me.block.special.curtains;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.MultifaceSpreader;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.jukoz.me.utils.BlockTagsME;
import org.jetbrains.annotations.Nullable;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import net.minecraft.world.phys.shapes.Shapes;

/**
 * 1.20.1 port: the donor extends 1.21.x MultifaceBlock, which is named
 * MultifaceBlock in 1.20.1 and does not provide WATERLOGGED, the
 * createShapeFunction helpers or Shapes.createFacingShapeMap, so those
 * pieces are implemented here.
 */
public class CurtainsBlock extends MultifaceBlock implements SimpleWaterloggedBlock {
    public static final BooleanProperty TIP = BooleanProperty.create("tip");
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    private static final Map<Direction, VoxelShape> FACING_SHAPES = Map.of(
            Direction.NORTH, Block.box(0.0, 0.0, 0.0, 16.0, 3.0, 3.0),
            Direction.SOUTH, Block.box(0.0, 0.0, 13.0, 16.0, 3.0, 16.0),
            Direction.WEST, Block.box(0.0, 0.0, 0.0, 3.0, 3.0, 16.0),
            Direction.EAST, Block.box(13.0, 0.0, 0.0, 16.0, 3.0, 16.0));

    private final Function<BlockState, VoxelShape> shapeFunction;
    private final MultifaceSpreader grower = new MultifaceSpreader(this);

    public CurtainsBlock(BlockBehaviour.Properties settings) {
        super(settings);
        shapeFunction = createShapeFunction();
    }

    @Override
    public MultifaceSpreader getGrower() {
        return grower;
    }

    @Override
    @Nullable
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        Level world = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        BlockState returnState = (BlockState) Arrays.stream(ctx.getNearestLookingDirections()).map((direction) -> {
            return this.withDirection(blockState, world, blockPos, direction);
        }).filter(Objects::nonNull).findFirst().orElse((BlockState) null);
        if (returnState != null) returnState = returnState.setValue(TIP,
                !world.getBlockState(blockPos.below()).is(BlockTagsME.CURTAINS));
        return returnState;
    }

    @Override
    protected boolean canHaveDirection(Direction direction) {
        return direction != Direction.UP && direction != Direction.DOWN;
    }

    @Override
    public boolean canGrowWithDirection(BlockGetter world, BlockState state, BlockPos pos, Direction direction) {
        return canHaveDirection(direction);
    }

    @Override
    public boolean canPlaceAt(BlockState state, LevelReader world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean canReplace(BlockState state, BlockPlaceContext context) {
        boolean sneaking = false;
        if (context.getPlayer() != null) sneaking = context.getPlayer().isShiftKeyDown();
        boolean placeVertically = false;
        if (context.getSide() == Direction.UP) {
            if (!context.getItem().is(this.asItem())) {
                placeVertically = true;
            } else {
                BlockState neighbor = context.getWorld().getBlockState(context.getBlockPos().above());
                if (context.getItem().is(neighbor.getBlock().asItem()) || neighbor.isAir()) {
                    placeVertically = true;
                }
            }
        } else if (context.getSide() == Direction.DOWN) {
            if (!context.getItem().is(this.asItem())) {
                placeVertically = true;
            } else {
                BlockState neighbor = context.getWorld().getBlockState(context.getBlockPos().below());
                if (context.getItem().is(neighbor.getBlock().asItem()) || neighbor.isAir()) {
                    placeVertically = true;
                }
            }
        }
        if (sneaking) placeVertically = !placeVertically;
        return isNotFullBlock(state) && !placeVertically && context.getItem().is(this.asItem());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        BlockState updated = super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        return updated.setValue(TIP, !world.getBlockState(pos.below()).is(BlockTagsME.CURTAINS));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.get(WATERLOGGED) ? Fluids.WATER.getStill(false) : super.getFluidState(state);
    }

    private static boolean isNotFullBlock(BlockState state) {
        Direction[] var1 = DIRECTIONS;
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            Direction direction = var1[var3];
            if (!hasDirection(state, direction)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return (VoxelShape) this.shapeFunction.apply(state);
    }

    private Function<BlockState, VoxelShape> createShapeFunction() {
        return (state) -> {
            VoxelShape voxelShape = Shapes.empty();
            Direction[] var3 = DIRECTIONS;
            int var4 = var3.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Direction direction = var3[var5];
                if (hasDirection(state, direction)) {
                    voxelShape = Shapes.union(voxelShape, (VoxelShape) FACING_SHAPES.get(direction));
                }
            }

            return voxelShape.isEmpty() ? Shapes.fullCube() : voxelShape;
        };
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{TIP, WATERLOGGED});
        super.appendProperties(builder);
    }

}
