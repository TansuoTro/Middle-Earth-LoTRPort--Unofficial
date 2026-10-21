package net.jukoz.me.block.special.plants;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.tags.BlockTags;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class FlowerbedMushroomBlock extends PinkPetalsBlock {
    public FlowerbedMushroomBlock(BlockBehaviour.Properties settings) {
        super(settings);
    }

    @Override
    public boolean canPlaceAt(BlockState state, LevelReader world, BlockPos pos) {
        BlockPos blockPos = pos.below();
        BlockState blockState = world.getBlockState(blockPos);
        if (blockState.is(BlockTags.MUSHROOM_GROW_BLOCK)) {
            return true;
        }
        return world.getRawBrightness(pos, 0) < 13 && this.canPlantOnTop(blockState, world, blockPos);
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockGetter world, BlockPos pos) {
        return floor.isOpaqueFullCube(world, pos);
    }
}
