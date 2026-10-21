package net.jukoz.me.block.special.artefact.arkenstone;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ArkenstoneWallBlock extends HorizontalDirectionalBlock {

    public ArkenstoneWallBlock(BlockBehaviour.Properties settings) {
        super(settings);
        this.setDefaultState(this.stateManager.defaultBlockState().with(FACING, Direction.NORTH));
    }

    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return (BlockState)((BlockState)this.defaultBlockState().with(FACING, ctx.getHorizontalDirection()));
    }

    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    public RenderShape getRenderType(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.get(FACING)){
            default -> Block.box(6, 6, 0, 10, 10, 4);
            case SOUTH -> Block.box(6, 6, 12, 10, 10, 16);
            case EAST -> Block.box(12, 6, 6, 16, 10, 10);
            case WEST -> Block.box(0, 6, 6, 4, 10, 10);
        };
    }

}
