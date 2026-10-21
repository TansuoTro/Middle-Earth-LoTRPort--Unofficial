package net.jukoz.me.block.special.plants;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelView;

public class TallMushroomBlock extends DoublePlantBlock {
    public TallMushroomBlock(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, LevelReader world, BlockPos pos) {
        if (state.get(HALF) == DoubleBlockHalf.UPPER) {
            BlockState blockState = world.getBlockState(pos.down());
            return blockState.isOf(this) && blockState.get(HALF) == DoubleBlockHalf.LOWER;
        }
        if (state.isIn(BlockTags.MUSHROOM_GROW_BLOCK)) {
            return true;
        }
        BlockPos floor = pos.down();
        boolean dark = world.getBaseLightLevel(floor, 0) < 13;
        boolean opaque = world.getBlockState(floor).isOpaqueFullCube(world, floor);
        return dark & opaque;
    }
}
