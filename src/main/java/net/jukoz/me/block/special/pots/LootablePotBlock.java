package net.jukoz.me.block.special.pots;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BlockRenderType;
import net.minecraft.world.level.block.DecoratedPotBlock;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelView;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.level.block.RenderShape;

public class LootablePotBlock extends DecoratedPotBlock {
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty CRACKED = BlockStateProperties.CRACKED;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public LootablePotBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        FluidState fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return getDefaultState()
                .with(FACING, ctx.getHorizontalPlayerFacing().getOpposite())
                .with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER)
                .with(CRACKED, false);
    }

    @Override
    public ItemStack getPickStack(net.minecraft.world.level.BlockGetter world, BlockPos pos, BlockState state) {
        return new ItemStack(this);
    }

    @Override
    public RenderShape getRenderType(BlockState state) {
        return RenderShape.INVISIBLE;
    }
}
