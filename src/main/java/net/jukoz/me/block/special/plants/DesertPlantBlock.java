package net.jukoz.me.block.special.plants;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.jukoz.me.utils.BlockTagsME;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class DesertPlantBlock extends CustomPlantBlock {
    public DesertPlantBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.is(BlockTags.DIRT) || floor.is(BlockTags.SAND) || floor.is(BlockTagsME.FARMLANDS);
    }
}
