package net.jukoz.me.block.special;
import net.minecraft.world.WorldAccess;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;

import java.util.Objects;

public class PaperSheetBlock extends Block {
    private static final int MAX_PAPERS = 6;
    public static final IntProperty PAPERS = IntProperty.of("papers", 1, MAX_PAPERS);
    public static final EnumProperty<Direction> HORIZONTAL_FACING = Properties.HORIZONTAL_FACING;
    private static final VoxelShape[] SHAPES_BY_PAPERS;

    public PaperSheetBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(HORIZONTAL_FACING, Direction.NORTH).with(PAPERS, 1));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, PAPERS);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPES_BY_PAPERS[state.get(PAPERS) - 1];
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }  else {
            if (player.isCreative()) {
                world.setBlockState(pos, state.cycle(PAPERS));
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    protected ActionResult onUseWithItem(ItemStack stack, BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        ItemStack itemStack = player.getStackInHand(hand);
        if(itemStack.getItem() == this.asItem() && state.get(PAPERS) < MAX_PAPERS) {
            stack.decrementUnlessCreative(1, player);
            world.setBlockState(pos, state.cycle(PAPERS));
            return ActionResult.CONSUME;
        }
        return super.onUseWithItem(stack, state, world, pos, player, hand, hit);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        boolean bl1 = !context.shouldCancelInteraction();
        boolean bl2 = context.getStack().getItem() == this.asItem();
        boolean bl3 = state.get(PAPERS) <= MAX_PAPERS;
        return bl1 && bl2 && bl3 || super.canReplace(state, context);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            return blockState.cycle(PAPERS).with(HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
        } else {
            return Objects.requireNonNull(super.getPlacementState(ctx)).with(HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
        }
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() :
                super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(HORIZONTAL_FACING, rotation.rotate(state.get(HORIZONTAL_FACING)));
    }

    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(HORIZONTAL_FACING)));
    }

    static {
        SHAPES_BY_PAPERS = new VoxelShape[]{
                Block.createCuboidShape(8.0 - (8.0), 0.0, 8.0 - (8.0), 8.0 + (8.0), 1.0, 8.0 + (8.0)),
                Block.createCuboidShape(8.0 - (12.0), 0.0, 8.0 - (12.0), 8.0 + (12.0), 1.0, 8.0 + (12.0)),
                Block.createCuboidShape(8.0 - (14.0), 0.0, 8.0 - (14.0), 8.0 + (14.0), 1.0, 8.0 + (14.0)),
                Block.createCuboidShape(8.0 - (16.0), 0.0, 8.0 - (16.0), 8.0 + (16.0), 3.0, 8.0 + (16.0)),
                Block.createCuboidShape(8.0 - (16.0), 0.0, 8.0 - (16.0), 8.0 + (16.0), 8.0, 8.0 + (16.0)),
                Block.createCuboidShape(8.0 - (16.0), 0.0, 8.0 - (16.0), 8.0 + (16.0), 12.0, 8.0 + (16.0)),
        };
    }
}
