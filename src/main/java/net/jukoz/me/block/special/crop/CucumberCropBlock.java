package net.jukoz.me.block.special.crop;

import net.jukoz.me.item.ResourceItemsME;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.item.ItemConvertible;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.state.property.IntProperty;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class CucumberCropBlock extends CropBlock {
    public static final int MAX_AGE = 3;
    public static final IntProperty AGE = BlockStateProperties.AGE_3;
    public CucumberCropBlock(Settings settings) {
        super(settings);
    }
    @Override
    protected ItemConvertible getSeedsItem() {
        return ResourceItemsME.CUCUMBER_SEEDS;
    }

    @Override
    public IntProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    @Override
    protected void appendProperties(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }
}
