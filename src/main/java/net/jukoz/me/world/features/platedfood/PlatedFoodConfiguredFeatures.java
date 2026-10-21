package net.jukoz.me.world.features.platedfood;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.world.features.chain.ChainFeatureConfig;
import net.jukoz.me.world.gen.ModFeatures;

public class PlatedFoodConfiguredFeatures {
    // NOTE keep it here just in case
    //public static final ResourceKey<ConfiguredFeature<?, ?>> CERAMIC_PLATE = registerKey("ceramic_plate");

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.of(Registries.CONFIGURED_FEATURE, new ResourceLocation(MiddleEarth.MOD_ID, name));
    }

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> featureRegisterable) {
        //ConfiguredFeature.register(featureRegisterable, CERAMIC_PLATE, ModFeatures.PLATED_FOOD,
        //        new PlatedFoodFeatureConfig(ModDecorativeBlocks.CERAMIC_PLATE.getDefaultState(), MiddleEarth.of("structures/shire/food")));

    }
}