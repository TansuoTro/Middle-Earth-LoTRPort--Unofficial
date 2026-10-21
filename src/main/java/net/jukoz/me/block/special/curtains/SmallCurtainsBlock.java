package net.jukoz.me.block.special.curtains;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.phys.shapes.Shapes;

public class SmallCurtainsBlock extends HorizontalDirectionalBlock {
    public static final BooleanProperty OPEN;

    public SmallCurtainsBlock(BlockBehaviour.Settings settings) {
        super(settings);
    }

    @Nullable
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return this.getDefaultState().with(BlockStateProperties.HORIZONTAL_FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClient && player.getAbilities().allowModifyWorld) {
            world.setBlockState(pos, state.cycle(OPEN));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.get(BlockStateProperties.HORIZONTAL_FACING)) {
            case WEST -> Block.createCuboidShape(14, 2, 0, 16, 16, 16);
            case EAST -> Block.createCuboidShape(0, 2, 0, 2, 16, 16);
            case SOUTH -> Block.createCuboidShape(0, 2, 0, 16, 16, 2);
            case NORTH -> Block.createCuboidShape(0, 2, 14, 16, 16, 16);
            default -> Shapes.cuboid(0, 2, 0, 16, 16, 16);
        };
    }

    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{OPEN, FACING});
    }

    static {
        OPEN = BlockStateProperties.OPEN;
    }
}
