package net.jukoz.me.block.special;

import net.minecraft.world.level.LevelAccess;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelView;
import java.util.Objects;

public class PaperSheetBlock extends Block {
    private static final int MAX_PAPERS = 6;
    public static final IntegerProperty PAPERS = IntegerProperty.of("papers", 1, MAX_PAPERS);
    public static final EnumProperty<Direction> HORIZONTAL_FACING = BlockStateProperties.HORIZONTAL_FACING;
    private static final VoxelShape[] SHAPES_BY_PAPERS;

    public PaperSheetBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(HORIZONTAL_FACING, Direction.NORTH).with(PAPERS, 1));
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, PAPERS);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPES_BY_PAPERS[state.get(PAPERS) - 1];
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack stack = player.getStackInHand(hand);
        if(stack.getItem() == this.asItem() && state.get(PAPERS) < MAX_PAPERS) {
            if (!player.isCreative()) stack.decrement(1);
            world.setBlockState(pos, state.cycle(PAPERS));
            return InteractionResult.CONSUME;
        }
        if (world.isClient) {
            return InteractionResult.SUCCESS;
        } else if (player.isCreative()) {
            world.setBlockState(pos, state.cycle(PAPERS));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public boolean canReplace(BlockState state, BlockPlaceContext context) {
        boolean bl1 = !context.shouldCancelInteraction();
        boolean bl2 = context.getStack().getItem() == this.asItem();
        boolean bl3 = state.get(PAPERS) <= MAX_PAPERS;
        return bl1 && bl2 && bl3 || super.canReplace(state, context);
    }

    public BlockState getPlacementState(BlockPlaceContext ctx) {
        BlockState blockState = ctx.getWorld().getBlockState(ctx.getBlockPos());
        if (blockState.isOf(this)) {
            return blockState.cycle(PAPERS).with(HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
        } else {
            return Objects.requireNonNull(super.getPlacementState(ctx)).with(HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
        }
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() :
                super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.with(HORIZONTAL_FACING, rotation.rotate(state.get(HORIZONTAL_FACING)));
    }

    public BlockState mirror(BlockState state, Mirror mirror) {
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
