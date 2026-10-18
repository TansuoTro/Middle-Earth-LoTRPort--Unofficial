package net.jukoz.me.block.special.plants;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.jukoz.me.utils.BlockTagsME;

public class DesertPlantBlock extends CustomPlantBlock {
    public DesertPlantBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        return floor.isIn(BlockTags.DIRT) || floor.isIn(BlockTags.SAND) || floor.isIn(BlockTagsME.FARMLANDS);
    }
}