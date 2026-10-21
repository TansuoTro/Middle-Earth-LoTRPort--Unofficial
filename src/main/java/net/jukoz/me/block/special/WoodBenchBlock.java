package net.jukoz.me.block.special;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.VoxelShapes;
import net.minecraft.world.level.BlockGetter;

public class WoodBenchBlock extends SeatBlock implements Waterloggable {

    public WoodBenchBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch(state.get(FACING)) {
            case WEST, EAST -> Block.createCuboidShape(2, 0, 0, 14, 9, 16);
            case SOUTH, NORTH -> Block.createCuboidShape(0, 0, 2, 16, 9, 14);
            default -> VoxelShapes.cuboid(1,1,1,1,1,1);
        };
    }
}
