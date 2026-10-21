package net.jukoz.me.world.features.tree;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlocks;
import net.jukoz.me.block.registration.ModNatureBlocks;
import net.jukoz.me.block.MushroomBlockSets;
import net.jukoz.me.block.registration.WoodBlockSets;
import net.jukoz.me.world.features.tree.foliages.OvalFoliagePlacer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.core.RegistryEntryLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.TreeFeatureConfig;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.PineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

public class MushroomTreeConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> BROWN_BOLETTE_TREE_KEY = registerKey("brown_bolette_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CAVE_AMANITA_TREE_KEY = registerKey("cave_amanita_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEEP_FIRECAP_TREE_KEY = registerKey("deep_firecap_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SKY_FIRECAP_TREE_KEY = registerKey("sky_firecap_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_AMANITA_TREE_KEY = registerKey("yellow_amanita_tree");


    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        RegistryEntryLookup<Block> registryEntryLookup = context.getRegistryLookup(Registries.BLOCK);

        register(context, BROWN_BOLETTE_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.MUSHROOM_STEM),
            new StraightTrunkPlacer(5, 2 , 0),
            BlockStateProvider.of(ModNatureBlocks.BROWN_BOLETE_BLOCK),
            new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 2),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(ModBlocks.STONE_MYCELIUM)).build());

        register(context, CAVE_AMANITA_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(MushroomBlockSets.GRAY_MUSHROOM.stem()),
            new StraightTrunkPlacer(3, 2 , 0),
            BlockStateProvider.of(ModNatureBlocks.CAVE_AMANITA_BLOCK),
            new PineFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), ConstantIntProvider.create(2)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(ModBlocks.STONE_MYCELIUM)).build());

        register(context, DEEP_FIRECAP_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(MushroomBlockSets.GRAY_MUSHROOM.stem()),
            new StraightTrunkPlacer(5, 2 , 0),
            BlockStateProvider.of(ModNatureBlocks.DEEP_FIRECAP_BLOCK),
            new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 2),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(ModBlocks.STONE_MYCELIUM)).build());

        register(context, SKY_FIRECAP_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(MushroomBlockSets.DARK_MUSHROOM.stem()),
            new StraightTrunkPlacer(5, 2 , 0),
            BlockStateProvider.of(ModNatureBlocks.SKY_FIRECAP_BLOCK),
            new BlobFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), 2),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(ModBlocks.STONE_MYCELIUM)).build());

        register(context, YELLOW_AMANITA_TREE_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
            BlockStateProvider.of(Blocks.MUSHROOM_STEM),
            new StraightTrunkPlacer(3, 2 , 0),
            BlockStateProvider.of(ModNatureBlocks.YELLOW_AMANITA_BLOCK),
            new PineFoliagePlacer(ConstantIntProvider.create(2), ConstantIntProvider.create(0), ConstantIntProvider.create(2)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(ModBlocks.STONE_MYCELIUM)).build());
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.of(Registries.CONFIGURED_FEATURE, new ResourceLocation(MiddleEarth.MOD_ID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(
            BootstrapContext<ConfiguredFeature<?, ?>> context,
            ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
