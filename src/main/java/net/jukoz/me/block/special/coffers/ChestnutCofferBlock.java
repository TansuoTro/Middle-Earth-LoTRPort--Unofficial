package net.jukoz.me.block.special.coffers;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.jukoz.me.block.registration.ModBlockEntities;

public class ChestnutCofferBlock extends ChestBlock {

    public ChestnutCofferBlock(Settings settings) {
        super(settings, () -> ModBlockEntities.CHESTNUT_COFFER);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ChestnutCofferBlockEntity(pos, state);
    }

    @Override
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        Direction direction = ctx.getHorizontalPlayerFacing();
        FluidState fluidstate = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(FACING, direction).with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if(state.get(FACING) == Direction.NORTH) {
            return Block.createCuboidShape(0.0, 0.0, 2.0, 16.0, 12.0, 13.0);
        } else if(state.get(FACING) == Direction.SOUTH) {
            return Block.createCuboidShape(0.0, 0.0, 3.0, 16.0, 12.0, 14.0);
        } else if(state.get(FACING) == Direction.EAST) {
            return Block.createCuboidShape(3.0, 0.0, 0.0, 14.0, 12.0, 16.0);
        } else {
            return Block.createCuboidShape(2.0, 0.0, 0.0, 13.0, 12.0, 16.0);
        }
    }
}
