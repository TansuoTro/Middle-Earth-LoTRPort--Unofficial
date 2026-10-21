package net.jukoz.me.block.special;

import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.network.chat.Component;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.jukoz.me.MiddleEarth;
import org.jetbrains.annotations.Nullable;

public class ThinBarrelBlock extends BarrelBlock {
    public ThinBarrelBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.get(FACING)){
            case UP,DOWN -> Block.createCuboidShape(1, 0, 1, 15, 16, 15);
            case NORTH,SOUTH -> Block.createCuboidShape(1, 0, 0, 15, 14, 16);
            case EAST,WEST -> Block.createCuboidShape(0, 0, 1, 16, 14, 15);
        };
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new BarrelBlockEntity(pos, state){
            @Override
            protected Component getContainerName() {
                return Component.translatable("container.%s.thin_barrel".formatted(MiddleEarth.MOD_ID));
            }
        };
    }
}
