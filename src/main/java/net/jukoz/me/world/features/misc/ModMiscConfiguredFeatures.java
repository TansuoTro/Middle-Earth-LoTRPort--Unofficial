package net.jukoz.me.world.features.misc;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.StoneBlockSets;
import net.jukoz.me.world.features.columns.ColumnsFeatureConfig;
import net.jukoz.me.world.gen.ModFeatures;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.data.worldgen.BootstrapContext;

public class ModMiscConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> LAVA_MAGMA_POOL = registerKey("lava_magma_pool");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_PUMICE_COLUMNS = registerKey("small_pumice_columns");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_PUMICE_COLUMNS = registerKey("large_pumice_columns");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> featureRegisterable) {
        ConfiguredFeature.register(featureRegisterable, LAVA_MAGMA_POOL, Feature.LAKE,
                new LakeFeature.Configuration(BlockStateProvider.of(Blocks.LAVA.defaultBlockState()), BlockStateProvider.of(Blocks.MAGMA_BLOCK.defaultBlockState())));

        ConfiguredFeature.register(featureRegisterable, SMALL_PUMICE_COLUMNS, ModFeatures.COLUMNS,
                new ColumnsFeatureConfig(ConstantInt.create(1), UniformInt.create(1, 4), StoneBlockSets.PUMICE_SET.baseBlocks.base().defaultBlockState()));
        ConfiguredFeature.register(featureRegisterable, LARGE_PUMICE_COLUMNS, ModFeatures.COLUMNS,
                new ColumnsFeatureConfig(UniformInt.create(2, 3), UniformInt.create(4, 7), StoneBlockSets.PUMICE_SET.baseBlocks.base().defaultBlockState()));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(MiddleEarth.MOD_ID, name));
    }
}
