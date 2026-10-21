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

public class FirCofferBlock extends ChestBlock {

    public FirCofferBlock(Settings settings) {
        super(settings, () -> ModBlockEntities.FIR_COFFER);
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new FirCofferBlockEntity(pos, state);
    }

    @Override
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        Direction direction = ctx.getHorizontalPlayerFacing();
        FluidState fluidstate = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return this.getDefaultState().with(FACING, direction).with(WATERLOGGED, fluidstate.getFluid() == Fluids.WATER);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        if(state.get(FACING) == Direction.NORTH || state.get(FACING) == Direction.SOUTH) {
            return Block.createCuboidShape(3.0, 0.0, 4.0, 13.0, 8.0, 12.0);
        } else {
            return Block.createCuboidShape(4.0, 0.0, 3.0, 12.0, 8.0, 13.0);
        }
    }

}
