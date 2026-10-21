package net.jukoz.me.block.special;

import net.minecraft.block.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import net.jukoz.me.compat.MEBlockShapes;
import net.minecraft.world.level.BlockGetter;

import java.util.Map;

public class StoneLecternBlock extends LecternBlock {
    private static final VoxelShape BASE_SHAPE;
    private static final Map<Direction, VoxelShape> OUTLINE_SHAPES_BY_DIRECTION;

    public StoneLecternBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getCullingShape(BlockState state, BlockGetter world, BlockPos pos) {
        return BASE_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, ShapeContext context) {
        return BASE_SHAPE;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, ShapeContext context) {
        return (VoxelShape)OUTLINE_SHAPES_BY_DIRECTION.get(state.get(FACING));
    }

    static {
        BASE_SHAPE = VoxelShapes.union(Block.createCuboidShape(8.0 - (16.0), 0.0, 8.0 - (16.0), 8.0 + (16.0), 2.0, 8.0 + (16.0)), Block.createCuboidShape(8.0 - (12.0), 2.0, 8.0 - (12.0), 8.0 + (12.0), 14.0, 8.0 + (12.0)));
        OUTLINE_SHAPES_BY_DIRECTION = MEBlockShapes.createHorizontalFacingShapeMap(VoxelShapes.union(MEBlockShapes.createCuboidZShape(16.0, 10.0, 14.0, 1.0, 5.333333), new VoxelShape[]{MEBlockShapes.createCuboidZShape(16.0, 12.0, 16.0, 5.333333, 9.666667), MEBlockShapes.createCuboidZShape(16.0, 14.0, 18.0, 9.666667, 14.0), BASE_SHAPE}));
    }
}
