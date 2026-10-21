package net.jukoz.me.block.special.artefact.arkenstone;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.*;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;

public class ArkenstoneWallBlock extends HorizontalFacingBlock {

    public ArkenstoneWallBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH));
    }



    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return (BlockState)((BlockState)this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing()));
    }

    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, ShapeContext context) {
        return switch (state.get(FACING)){
            default -> Block.createCuboidShape(6, 6, 0, 10, 10, 4);
            case SOUTH -> Block.createCuboidShape(6, 6, 12, 10, 10, 16);
            case EAST -> Block.createCuboidShape(12, 6, 6, 16, 10, 10);
            case WEST -> Block.createCuboidShape(0, 6, 6, 4, 10, 10);
        };
    }

}
