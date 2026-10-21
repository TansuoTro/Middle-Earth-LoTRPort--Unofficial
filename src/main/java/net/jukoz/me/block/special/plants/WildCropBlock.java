package net.jukoz.me.block.special.plants;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelView;

public class WildCropBlock extends CustomPlantBlock implements Fertilizable {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(2.0, 0.0, 2.0, 14.0, 13.0, 14.0);

    public WildCropBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean canGrow(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        float val = random.nextFloat();
        if(val > 0.90f){
            dropStack(world, pos, new ItemStack(this));
        }
    }
}
