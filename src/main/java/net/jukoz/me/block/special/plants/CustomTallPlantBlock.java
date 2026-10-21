package net.jukoz.me.block.special.plants;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;

public class CustomTallPlantBlock extends DoublePlantBlock implements BonemealableBlock {
    private final boolean randomBoneMeal;
    public CustomTallPlantBlock(Settings settings, boolean random) {
        super(settings);
        this.randomBoneMeal = random;
    }

    @Override
    public boolean isFertilizable(LevelReader world, BlockPos pos, BlockState state, boolean isClient) {
        return true;
    }

    public boolean canGrow(Level world, RandomSource random, BlockPos pos, BlockState state) {
        return true;
    }

    public void grow(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        if(this.randomBoneMeal) {
            float val = random.nextFloat();
            if(val > 0.90f){
                dropStack(world, pos, new ItemStack(this));
            }
        } else {
            dropStack(world, pos, new ItemStack(this));
        }
    }
}
