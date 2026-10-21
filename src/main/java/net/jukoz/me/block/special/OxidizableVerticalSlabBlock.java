package net.jukoz.me.block.special;

import net.jukoz.me.block.special.verticalSlabs.VerticalSlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.Oxidizable;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;

public class OxidizableVerticalSlabBlock extends VerticalSlabBlock implements Oxidizable {
    private final OxidationLevel oxidationLevel;

    public OxidizableVerticalSlabBlock(OxidationLevel oxidationLevel, Settings settings) {
        super(settings);
        this.oxidationLevel = oxidationLevel;
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        this.tickDegradation(state, world, pos, random);
    }

    public boolean hasRandomTicks(BlockState state) {
        return Oxidizable.getIncreasedOxidationBlock(state.getBlock()).isPresent();
    }

    public OxidationLevel getDegradationLevel() {
        return this.oxidationLevel;
    }
}
