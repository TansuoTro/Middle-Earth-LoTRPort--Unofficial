package net.jukoz.me.block.special.plants;

import com.mojang.serialization.MapCodec;
import net.jukoz.me.block.registration.StoneBlockSets;
import net.minecraft.tags.BlockTags;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.jukoz.me.utils.BlockTagsME;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class MordorPricklyPlantBlock extends PricklyPlantBlock {

    public MordorPricklyPlantBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockGetter world, BlockPos pos) {
        return  floor.is(BlockTags.DIRT) || floor.is(BlockTags.SAND) || floor.is(StoneBlockSets.ASHENSTONE_SET.baseBlocks.base())
                || floor.is(Blocks.BASALT) || floor.is(BlockTagsME.FARMLANDS);
    }
}
