package net.jukoz.me.block.special;

import net.jukoz.me.block.registration.ModNatureBlocks;
import net.minecraft.block.*;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.shapes.VoxelShape;

public class GlowWormBottomBlock extends AbstractPlantStemBlock {
    protected static final VoxelShape SHAPE = Block.createCuboidShape(4.0, 9.0, 4.0, 12.0, 16.0, 12.0);

    public GlowWormBottomBlock(AbstractBlock.Settings settings) {
        super(settings, Direction.DOWN, SHAPE, false, 0.05);
    }

    protected int getGrowthLength(RandomSource random) {
        return random.nextInt(5);
    }

    protected Block getPlant() {
        return ModNatureBlocks.GLOWWORM_MAIN;
    }

    protected boolean chooseStemState(BlockState state) {
        return VineLogic.isValidForWeepingStem(state);
    }
}
