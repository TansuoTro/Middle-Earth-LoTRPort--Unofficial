package net.jukoz.me.block.special.curtains;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LichenGrower;
import net.minecraft.block.MultifaceGrowthBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import net.jukoz.me.utils.BlockTagsME;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * 1.20.1 port: the donor extends 1.21.x MultifaceBlock, which is named
 * MultifaceGrowthBlock in 1.20.1 and does not provide WATERLOGGED, the
 * createShapeFunction helpers or VoxelShapes.createFacingShapeMap, so those
 * pieces are implemented here.
 */
public class CurtainsBlock extends MultifaceGrowthBlock implements Waterloggable {
    public static final BooleanProperty TIP = BooleanProperty.of("tip");
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;

    private static final Map<Direction, VoxelShape> FACING_SHAPES = Map.of(
            Direction.NORTH, Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 3.0, 3.0),
            Direction.SOUTH, Block.createCuboidShape(0.0, 0.0, 13.0, 16.0, 3.0, 16.0),
            Direction.WEST, Block.createCuboidShape(0.0, 0.0, 0.0, 3.0, 3.0, 16.0),
            Direction.EAST, Block.createCuboidShape(13.0, 0.0, 0.0, 16.0, 3.0, 16.0));

    private final Function<BlockState, VoxelShape> shapeFunction;
    private final LichenGrower grower = new LichenGrower(this);

    public CurtainsBlock(AbstractBlock.Settings settings) {
        super(settings);
        shapeFunction = createShapeFunction();
    }

    @Override
    public LichenGrower getGrower() {
        return grower;
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        World world = ctx.getWorld();
        BlockPos blockPos = ctx.getBlockPos();
        BlockState blockState = world.getBlockState(blockPos);
        BlockState returnState = (BlockState) Arrays.stream(ctx.getPlacementDirections()).map((direction) -> {
            return this.withDirection(blockState, world, blockPos, direction);
        }).filter(Objects::nonNull).findFirst().orElse((BlockState) null);
        if (returnState != null) returnState = returnState.with(TIP,
                !world.getBlockState(blockPos.down()).isIn(BlockTagsME.CURTAINS));
        return returnState;
    }

    @Override
    protected boolean canHaveDirection(Direction direction) {
        return direction != Direction.UP && direction != Direction.DOWN;
    }

    @Override
    public boolean canGrowWithDirection(BlockView world, BlockState state, BlockPos pos, Direction direction) {
        return canHaveDirection(direction);
    }

    @Override
    public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        boolean sneaking = false;
        if (context.getPlayer() != null) sneaking = context.getPlayer().isSneaking();
        boolean placeVertically = false;
        if (context.getSide() == Direction.UP) {
            if (!context.getStack().isOf(this.asItem())) {
                placeVertically = true;
            } else {
                BlockState neighbor = context.getWorld().getBlockState(context.getBlockPos().up());
                if (context.getStack().isOf(neighbor.getBlock().asItem()) || neighbor.isAir()) {
                    placeVertically = true;
                }
            }
        } else if (context.getSide() == Direction.DOWN) {
            if (!context.getStack().isOf(this.asItem())) {
                placeVertically = true;
            } else {
                BlockState neighbor = context.getWorld().getBlockState(context.getBlockPos().down());
                if (context.getStack().isOf(neighbor.getBlock().asItem()) || neighbor.isAir()) {
                    placeVertically = true;
                }
            }
        }
        if (sneaking) placeVertically = !placeVertically;
        return isNotFullBlock(state) && !placeVertically && context.getStack().isOf(this.asItem());
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        BlockState updated = super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
        return updated.with(TIP, !world.getBlockState(pos.down()).isIn(BlockTagsME.CURTAINS));
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
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return (VoxelShape) this.shapeFunction.apply(state);
    }

    private Function<BlockState, VoxelShape> createShapeFunction() {
        return (state) -> {
            VoxelShape voxelShape = VoxelShapes.empty();
            Direction[] var3 = DIRECTIONS;
            int var4 = var3.length;

            for (int var5 = 0; var5 < var4; ++var5) {
                Direction direction = var3[var5];
                if (hasDirection(state, direction)) {
                    voxelShape = VoxelShapes.union(voxelShape, (VoxelShape) FACING_SHAPES.get(direction));
                }
            }

            return voxelShape.isEmpty() ? VoxelShapes.fullCube() : voxelShape;
        };
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{TIP, WATERLOGGED});
        super.appendProperties(builder);
    }

    
}
