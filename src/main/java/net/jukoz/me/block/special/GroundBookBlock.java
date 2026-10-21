package net.jukoz.me.block.special;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.InteractionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class GroundBookBlock extends Block {
    public static final EnumProperty<Direction> HORIZONTAL_FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty OPEN = BooleanProperty.of("open");

    public GroundBookBlock(Settings settings) {
        super(settings);
        setDefaultState(getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH).with(OPEN, false));
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if(!world.isClient && player.getAbilities().allowModifyWorld){
            world.setBlockState(pos, state.cycle(OPEN), 2 | 3);
        }
        return InteractionResult.SUCCESS;
    }

    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HORIZONTAL_FACING, OPEN);
    }

    @Nullable
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return this.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite()).with(OPEN, false);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(HORIZONTAL_FACING, rotation.rotate(state.get(HORIZONTAL_FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        Direction direction = state.get(HORIZONTAL_FACING);
        switch (mirror) {
            case LEFT_RIGHT -> {
                if (direction.getAxis() != Direction.Axis.Z) break;
                return state.rotate(BlockRotation.CLOCKWISE_180);
            }
            case FRONT_BACK -> {
                if (direction.getAxis() != Direction.Axis.X) break;
                return state.rotate(BlockRotation.CLOCKWISE_180);
            }
        }
        return super.mirror(state, mirror);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if(state.get(OPEN)){
            return switch(state.get(BlockStateProperties.HORIZONTAL_FACING)) {
                case WEST, EAST -> Block.createCuboidShape(2, 0, 0, 14, 5, 16);
                case SOUTH, NORTH -> Block.createCuboidShape(0, 0, 2, 16, 5, 14);
                default -> VoxelShapes.cuboid(1,1,1,1,1,1);
            };
        } else {
            return switch(state.get(BlockStateProperties.HORIZONTAL_FACING)) {
                case WEST, EAST -> Block.createCuboidShape(3, 0, 4, 13, 3, 12);
                case SOUTH, NORTH -> Block.createCuboidShape(4, 0, 3, 12, 3, 13);
                default -> VoxelShapes.cuboid(1,1,1,1,1,1);
            };
        }

    }
}
