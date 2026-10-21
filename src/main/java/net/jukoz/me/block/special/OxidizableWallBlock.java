package net.jukoz.me.block.special;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;

public class OxidizableWallBlock  extends WallBlock implements WeatheringCopper {
    private final WeatheringCopper.WeatherState oxidationLevel;

    public OxidizableWallBlock(WeatheringCopper.WeatherState oxidationLevel, Settings settings) {
        super(settings);
        this.oxidationLevel = oxidationLevel;
    }

    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        this.onRandomTick(state, world, pos, random);
    }

    public boolean hasRandomTicks(BlockState state) {
        return WeatheringCopper.getIncreasedOxidationBlock(state.getBlock()).isPresent();
    }

    public WeatheringCopper.WeatherState getDegradationLevel() {
        return this.oxidationLevel;
    }
}
