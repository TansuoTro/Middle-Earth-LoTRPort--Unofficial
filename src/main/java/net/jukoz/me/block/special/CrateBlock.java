package net.jukoz.me.block.special;

import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.jukoz.me.MiddleEarth;
import org.jetbrains.annotations.Nullable;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class CrateBlock extends BarrelBlock {
    public CrateBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public BlockState getPlacementState(BlockPlaceContext ctx) {
        return this.defaultBlockState().with(BlockStateProperties.FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.get(FACING)) {
            case DOWN, UP, NORTH, SOUTH -> Block.box(0, 0, 3, 16, 7, 13);
            case WEST,EAST -> Block.box(3, 0, 0, 13, 7, 16);
        };
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BarrelBlockEntity(pos, state){
            @Override
            protected Component getContainerName() {
                return Component.translatable("container.%s.small_crate".formatted(MiddleEarth.MOD_ID));
            }
        };
    }
}
