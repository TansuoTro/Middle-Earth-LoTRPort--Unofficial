package net.jukoz.me.block.special.plants;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.jukoz.me.utils.BlockTagsME;

public class DesertPlantBlock extends CustomPlantBlock {
    public DesertPlantBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT) || floor.isIn(BlockTags.SAND) || floor.isIn(BlockTagsME.FARMLANDS);
    }
}
