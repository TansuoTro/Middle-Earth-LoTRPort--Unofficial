package net.jukoz.me.block.special.statues;

import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
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
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FlipStatueBlock extends StatueBlock {
    public static final BooleanProperty FLIP = BooleanProperty.create("flip");

    public FlipStatueBlock(BlockBehaviour.Properties settings) {
        super(settings);
        setDefaultState(getDefaultState().with(HORIZONTAL_FACING, Direction.NORTH).with(HALF, DoubleBlockHalf.LOWER).with(WATERLOGGED, false).with(FLIP, false));
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FLIP);
        super.appendProperties(builder);
    }

    @Override
    public InteractionResult onUse(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (!world.isClientSide && player.getAbilities().allowModifyWorld) {
            world.setBlock(pos, state.cycle(FLIP));
            BlockState updatedState = world.getBlockState(pos);
            if(state.get(HALF) == DoubleBlockHalf.LOWER) {
                BlockState otherState = world.getBlockState(pos.above());
                world.setBlock(pos.above(), otherState.setValue(FLIP, updatedState.get(FLIP)));
            } else {
                BlockState otherState = world.getBlockState(pos.below());
                world.setBlock(pos.below(), otherState.setValue(FLIP, updatedState.get(FLIP)));
            }
        }
        return InteractionResult.SUCCESS;
    }
}
