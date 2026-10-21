package net.jukoz.me.world.features.chain;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.world.gen.ModFeatures;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

public class ChainConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ROPE_LADDER_DOWN = registerKey("rope_ladder_down");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.of(Registries.CONFIGURED_FEATURE, MiddleEarth.of(name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> featureRegisterable) {
        FeatureUtils.register(featureRegisterable, ROPE_LADDER_DOWN, ModFeatures.CHAIN,
                new ChainFeatureConfig(24, false, Direction.DOWN, Direction.EAST, ModDecorativeBlocks.ROPE_LADDER.getDefaultState()));

    }
}
