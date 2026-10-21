package net.jukoz.me.block.special.skull;
import net.minecraft.world.level.LevelAccess;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.state.property.IntProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.math.RotationPropertyHelper;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelView;
import net.jukoz.me.block.special.bellows.BellowsBlock;
import net.jukoz.me.block.special.bellows.BellowsBlockEntity;
import org.jetbrains.annotations.Nullable;

public class OldSkullBlock extends BlockWithEntity {
    public static final IntProperty ROTATION = BlockStateProperties.ROTATION;
    public static final int MAX_ROTATION_INDEX = RotationPropertyHelper.getMax();
    private static final int MAX_ROTATIONS = MAX_ROTATION_INDEX + 1;
    private static final VoxelShape SHAPE = Block.createCuboidShape(8.0 - (8.0), 0.0, 8.0 - (8.0), 8.0 + (8.0), 8.0, 8.0 + (8.0));

    public OldSkullBlock(Settings settings) {
        super(settings);
        this.setDefaultState((this.stateManager.getDefaultState()).with(ROTATION, 0));
    }



    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ROTATION);
    }

    @Override
    public boolean canPathfindThrough(BlockState state, net.minecraft.world.level.BlockGetter world, BlockPos pos, NavigationType type) {
        return false;
    }

    @Override
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return super.getPlacementState(ctx).with(ROTATION, RotationPropertyHelper.fromYaw(ctx.getPlayerYaw()));
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        return direction == Direction.DOWN && !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() :
                super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(ROTATION, rotation.rotate((Integer)state.get(ROTATION), MAX_ROTATIONS));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.with(ROTATION, mirror.mirror((Integer)state.get(ROTATION), MAX_ROTATIONS));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new OldSkullBlockEntity(pos, state);
    }
}
