package net.jukoz.me.block.special.plants;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class TallMushroomBlock extends DoublePlantBlock {
    public TallMushroomBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, LevelReader world, BlockPos pos) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            BlockState blockState = world.getBlockState(pos.below());
            return blockState.is(this) && blockState.get(HALF) == DoubleBlockHalf.LOWER;
        }
        if (state.is(BlockTags.MUSHROOM_GROW_BLOCK)) {
            return true;
        }
        BlockPos floor = pos.below();
        boolean dark = world.getRawBrightness(floor, 0) < 13;
        boolean opaque = world.getBlockState(floor).isOpaqueFullCube(world, floor);
        return dark & opaque;
    }
}
