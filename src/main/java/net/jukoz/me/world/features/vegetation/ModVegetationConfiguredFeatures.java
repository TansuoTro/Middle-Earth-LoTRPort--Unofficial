package net.jukoz.me.world.features.vegetation;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlocks;
import net.jukoz.me.block.registration.ModNatureBlocks;
import net.jukoz.me.block.registration.StoneBlockSets;
import net.jukoz.me.block.registration.WoodBlockSets;
import net.jukoz.me.item.ResourceItemsME;
import net.jukoz.me.world.features.columns.CaveColumnFeatureConfig;
import net.jukoz.me.world.features.growth.MultifaceStateFeatureConfig;
import net.jukoz.me.world.features.underground.CavesConfiguredFeatures;
import net.jukoz.me.world.gen.ModFeatures;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.HolderList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.util.InclusiveRange;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.BlockPredicateFilter;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.DualNoiseProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import java.util.List;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.SweetBerryBushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockPileConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.DeltaFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.core.HolderSet;

public class ModVegetationConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> WATER_DELTA = registerKey("water_delta");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_ALLIUM = registerKey("flower_allium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_ATHELAS = registerKey("flower_athelas");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_AZURE_BLUET = registerKey("flower_azure_bluet");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_BLUE_GENTIAN = registerKey("flower_blue_gentian");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_CORNFLOWER = registerKey("flower_cornflower");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_DORWINION = registerKey("flower_dorwinion");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_GREEN_JEWEL = registerKey("flower_green_jewel");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_LILAC = registerKey("flower_lilac");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_LEBENNIN = registerKey("flower_lebennin");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_LOSSARNACH = registerKey("flower_lossarnach");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_MALLOS = registerKey("flower_mallos");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_NOBLEWHITE = registerKey("flower_noblewhite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_POPPY = registerKey("flower_poppy");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_ELANOR = registerKey("flower_elanor");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_NIPHREDIL = registerKey("flower_niphredil");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_SIMBELMYNE = registerKey("flower_simbelmyne");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_ROSE_BUSH = registerKey("flower_rose_bush");

    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWERS_LIGHT_BLUE = registerKey("flowers_light_blue");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWERS_MAGENTA = registerKey("flowers_magenta");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWERS_ORANGE = registerKey("flowers_orange");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWERS_PINK = registerKey("flowers_pink");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWERS_PURPLE = registerKey("flowers_purple");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWERS_RED = registerKey("flowers_red");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWERS_WHITE = registerKey("flowers_white");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWERS_YELLOW = registerKey("flowers_yellow");

    // region FIELDS
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIELD_HEATHER = registerKey("field_heather");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIELD_DRY_HEATHER = registerKey("field_dry_heather");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIELD_LAVENDER = registerKey("field_lavender");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIELD_WILD_WHEAT = registerKey("field_wild_wheat");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIELD_BLUE_FESCUE = registerKey("field_blue_fescue");

    // endregion
    public static final ResourceKey<ConfiguredFeature<?, ?>> MIRKWOOD_VINES = registerKey("mirkwood_vines");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILLOW_VINES = registerKey("willow_vines");

    // region GROWTH
    public static final ResourceKey<ConfiguredFeature<?, ?>> AZALEA_GROWTH = registerKey("azalea_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DRY_GROWTH = registerKey("dry_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> IVY_GROWTH = registerKey("ivy_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FROZEN_GROWTH = registerKey("patch_frozen_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GREEN_GROWTH = registerKey("patch_green_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_THORNY_GROWTH = registerKey("patch_thorny_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LILAC_FLOWER_GROWTH = registerKey("lilac_flower_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_FLOWER_GROWTH = registerKey("red_flower_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_FLOWER_GROWTH = registerKey("yellow_flower_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINK_FLOWER_GROWTH = registerKey("pink_flower_growth");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_FLOWER_GROWTH = registerKey("white_flower_growth");
    public static final HolderSet.Direct<Block> BLOCKS_GROWTH = HolderSet.of(Block::getRegistryEntry,
            Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.MOSSY_COBBLESTONE,
            Blocks.MOSS_BLOCK, Blocks.CLAY, Blocks.COBBLESTONE, Blocks.DEEPSLATE, Blocks.OAK_LOG, Blocks.BIRCH_LOG, Blocks.DARK_OAK_LOG, Blocks.SPRUCE_LOG,
            WoodBlockSets.BEECH_SET.logBlocks.log(), WoodBlockSets.MAPLE_SET.logBlocks.log(), WoodBlockSets.SILVER_MAPLE_SET.logBlocks.log(), WoodBlockSets.HOLLY_SET.logBlocks.log());
    // endregion

    // region FOLIAGE
    public static final  HolderSet<Block> BLOCKS_MOSS = HolderSet.of(Block::getRegistryEntry,
            Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.MOSSY_COBBLESTONE, Blocks.COBBLESTONE,
            Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.MOSS_BLOCK, Blocks.SPRUCE_LOG, Blocks.OAK_LOG, Blocks.BIRCH_LOG, Blocks.DARK_OAK_LOG,
            WoodBlockSets.PINE_SET.logBlocks.log(), WoodBlockSets.LARCH_SET.logBlocks.log(), WoodBlockSets.BEECH_SET.logBlocks.log(), WoodBlockSets.MAPLE_SET.logBlocks.log(),
            WoodBlockSets.SILVER_MAPLE_SET.logBlocks.log(), WoodBlockSets.BLACK_LEBETHRON_SET.logBlocks.log(), WoodBlockSets.WHITE_LEBETHRON_SET.logBlocks.log());

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BASALT = registerKey("patch_basalt");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BLACKSTONE = registerKey("patch_blackstone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_PUMICE = registerKey("patch_pumice");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_COBWEB = registerKey("patch_cobweb");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_PERSISTENT_WEBBING = registerKey("patch_persistent_webbing");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WEBBING = registerKey("patch_webbing");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SPIDER_EGGS = registerKey("patch_spider_eggs");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PUMICE_COLUMN = registerKey("pumice_column");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PUMICE_COLUMN_LARGE = registerKey("pumice_column_large");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BEACH_GRASS = registerKey("patch_beach_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BUSHES = registerKey("patch_bushes");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CLOVERS = registerKey("patch_clovers");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BRACKEN = registerKey("patch_bracken");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GIANT_BUTTERBUR = registerKey("patch_giant_butterbur");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BROWN_GRASS = registerKey("patch_brown_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SHORT_BULRUSH = registerKey("patch_short_bulrush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TALL_BULRUSH = registerKey("patch_tall_bulrush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SHORT_CATTAIL = registerKey("patch_short_cattail");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TALL_CATTAIL = registerKey("patch_tall_cattail");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_COASTAL_PANIC_GRASS = registerKey("patch_coastal_panic_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CORRUPTED_MOSS = registerKey("patch_corrupted_moss");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CORRUPTED_MOSS_CARPET = registerKey("patch_corrupted_moss_carpet");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DEAD_RUSHES = registerKey("patch_dead_rushes");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DRY_GRASS = registerKey("patch_dry_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SMALL_DRY_SHRUB = registerKey("patch_small_dry_shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DUCKWEED = registerKey("patch_duckweed");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DYING_GRASS = registerKey("patch_dying_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FALLEN_LEAVES = registerKey("patch_fallen_leaves");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FALLEN_MALLORN_LEAVES = registerKey("patch_fallen_mallorn_leaves");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FALLEN_MIRKWOOD_LEAVES = registerKey("patch_fallen_mirkwood_leaves");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FALSE_OATGRASS = registerKey("patch_false_oatgrass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FLOATING_ICE = registerKey("patch_floating_ice");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FOREST_MOSS = registerKey("patch_forest_moss");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FOREST_MOSS_CARPET = registerKey("patch_forest_moss_carpet");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FROZEN_GRASS = registerKey("patch_frozen_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FROZEN_SHRUB = registerKey("patch_frozen_shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FROZEN_TUFT = registerKey("patch_frozen_tuft");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GREEN_SHRUB = registerKey("patch_green_shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GRIM_GRASS = registerKey("patch_grim_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_HEATH = registerKey("patch_heath");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_HEATHER = registerKey("patch_heather");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DEAD_HEATHER = registerKey("patch_dead_heather");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DRY_HEATHER = registerKey("patch_dry_heather");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_RED_HEATHER = registerKey("patch_red_heather");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIELD_DEAD_NORMAL_HEATHER = registerKey("patch_dead_normal_heather");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BLUE_LAVENDER = registerKey("patch_blue_lavender");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_LAVENDER = registerKey("patch_lavender");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WHITE_LAVENDER = registerKey("patch_white_lavender");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_HOGWEED = registerKey("patch_hogweed");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_HOBBIT_SUNFLOWERS = registerKey("patch_hobbit_sunflowers");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BLUE_BIGLEAF_HYDRANGEA = registerKey("patch_blue_bigleaf_hydrangea");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_PINK_BIGLEAF_HYDRANGEA = registerKey("patch_pink_bigleaf_hydrangea");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WHITE_BIGLEAF_HYDRANGEA = registerKey("patch_white_bigleaf_hydrangea");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CAMPION = registerKey("patch_campion");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_REEDS = registerKey("patch_reeds");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SHORT_REEDS = registerKey("patch_short_reeds");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_STICKY_SNOW = registerKey("patch_sticky_snow");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_LARGE_LILY_PAD = registerKey("patch_large_lily_pad");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_LARGE_FLOWERING_LILY_PAD = registerKey("patch_large_flowering_lily_pad");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_LILY_PADS = registerKey("patch_lily_pads");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_FLOWERING_LILY_PADS = registerKey("patch_flowering_lily_pads");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SMALL_LILY_PADS = registerKey("patch_small_lily_pads");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SMALL_FLOWERING_LILY_PADS = registerKey("patch_small_flowering_lily_pads");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MIRKWOOD = registerKey("patch_mirkwood");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MIRKWOOD_ROOTS = registerKey("patch_mirkwood_roots");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MISTWEED = registerKey("patch_mistweed");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MOSS = registerKey("patch_moss");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MOSS_CARPET = registerKey("patch_moss_carpet");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SCORCHED_GRASS = registerKey("patch_scorched_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SCORCHED_SHRUB = registerKey("patch_scorched_shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SCORCHED_TUFT = registerKey("patch_scorched_tuft");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SEDUM = registerKey("patch_sedum");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SEDUM_ORANGE = registerKey("patch_sedum_orange");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SEDUM_RED = registerKey("patch_sedum_red");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SEDUM_YELLOW = registerKey("patch_sedum_yellow");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SEDUMS = registerKey("patch_sedums");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SHRIVELED_SHRUB = registerKey("patch_shriveled_shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_LARGE_SHRIVELED_SHRUB = registerKey("patch_large_shriveled_shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_RUSHES = registerKey("patch_rushes");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SHORT_RUSHES = registerKey("patch_short_rushes");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_STRAWBERRY_BUSH = registerKey("patch_strawberry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TAN_SHRUB = registerKey("patch_tan_shrub");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_YELLOW_TROLLIUS = registerKey("patch_yellow_trollius");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TEMPERATE_GRASS = registerKey("patch_temperate_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SWEET_BERRY_BUSH = registerKey("patch_sweet_berry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TOUGH_BERRY_BUSH = registerKey("patch_tough_berry_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TUFT_GRASS = registerKey("patch_tuft_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SPARSE_GRASS = registerKey("patch_sparse_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WHEAT_GRASS = registerKey("patch_wheat_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MEADOW_GRASS = registerKey("patch_meadow_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MIXED_WILD_WHEAT = registerKey("patch_mixed_wild_wheat");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_GRASS = registerKey("patch_wild_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILDER_GRASS = registerKey("patch_wilder_grass");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_YELLOW_FLOWER = registerKey("patch_yellow_flower");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_NETTLES = registerKey("patch_nettles");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_THISTLE = registerKey("patch_thistle");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MORDOR_BRAMBLES = registerKey("patch_mordor_brambles");
    // endregion

    // region MUSHROOMS
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BROWN_BOLETE = registerKey("patch_brown_bolete");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_BROWN_BOLETE_TILLER = registerKey("patch_brown_bolete_tiller");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MORSEL = registerKey("patch_morsel");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_MORSEL_TILLER = registerKey("patch_morsel_tiller");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WHITE_MUSHROOM = registerKey("patch_white_mushroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WHITE_MUSHROOM_TILLER = registerKey("patch_white_mushroom_tiller");

    // endregion

    // region WILD CROPS
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_BEETROOT = registerKey("patch_wild_beetroot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_BELL_PEPPER = registerKey("patch_wild_bell_pepper");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_CARROT = registerKey("patch_wild_carrot");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_CUCUMBER = registerKey("patch_wild_cucumber");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_FLAX = registerKey("patch_wild_flax");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_GARLIC = registerKey("patch_wild_garlic");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_LEEK = registerKey("patch_wild_leek");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_LETTUCE = registerKey("patch_wild_lettuce");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_ONION = registerKey("patch_wild_onion");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_PIPEWEED = registerKey("patch_wild_pipeweed");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_POTATO = registerKey("patch_wild_potato");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_WILD_TOMATO = registerKey("patch_wild_tomato");
    // endregion

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> featureRegisterable) {
        FeatureUtils.register(featureRegisterable, WATER_DELTA, ModFeatures.DELTA_FEATURE,
                new DeltaFeatureConfiguration(Blocks.WATER.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState(), UniformInt.create(3, 7), UniformInt.create(0, 2)));

        FeatureUtils.register(featureRegisterable, FIELD_HEATHER, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.RANDOM_PATCH,
                        new RandomPatchConfiguration(8, 3, 2,
                                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                                .add(ModNatureBlocks.HEATHER.getDefaultState(), 1)
                                                .add(ModNatureBlocks.HEATHER_BUSH.getDefaultState(), 2))
                                )))));
        FeatureUtils.register(featureRegisterable, FIELD_DRY_HEATHER, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.RANDOM_PATCH,
                        new RandomPatchConfiguration(8, 3, 2,
                                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                                .add(ModNatureBlocks.DRY_HEATHER.getDefaultState(), 1)
                                                .add(ModNatureBlocks.DRY_HEATHER_BUSH.getDefaultState(), 2))
                                )))));

        FeatureUtils.register(featureRegisterable, FIELD_LAVENDER, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.LAVENDER))));

        FeatureUtils.register(featureRegisterable, FIELD_WILD_WHEAT, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.RANDOM_PATCH,
                        new RandomPatchConfiguration(8, 3, 2,
                                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                                .add(ModNatureBlocks.TALL_WILD_WHEAT.getDefaultState(), 1)
                                                .add(ModNatureBlocks.WILD_WHEAT.getDefaultState(), 3))
                                )))));

        FeatureUtils.register(featureRegisterable, MIRKWOOD_VINES, ModFeatures.MIRKWOOD_VINE, new NoneFeatureConfiguration());
        FeatureUtils.register(featureRegisterable, WILLOW_VINES, ModFeatures.WILLOW_VINE, new NoneFeatureConfiguration());

        FeatureUtils.register(featureRegisterable, FLOWER_ALLIUM, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(Blocks.ALLIUM))));
        FeatureUtils.register(featureRegisterable, FLOWER_ATHELAS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.ATHELAS))));
        FeatureUtils.register(featureRegisterable, FLOWER_AZURE_BLUET, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(Blocks.AZURE_BLUET))));
        FeatureUtils.register(featureRegisterable, FLOWER_BLUE_GENTIAN, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.BLUE_GENTIAN))));
        FeatureUtils.register(featureRegisterable, FLOWER_CORNFLOWER, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(Blocks.CORNFLOWER))));
        FeatureUtils.register(featureRegisterable, FLOWER_GREEN_JEWEL, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.GREEN_JEWEL_CORNFLOWER))));
        FeatureUtils.register(featureRegisterable, FLOWER_LILAC, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(Blocks.LILAC))));
        FeatureUtils.register(featureRegisterable, FLOWER_LEBENNIN, Feature.FLOWER,
                new RandomPatchConfiguration(96, 6, 2, PlacementUtils.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(new DualNoiseProvider(new InclusiveRange<>(1, 3), new NormalNoise.NoiseParameters(-10, 1.0), 1.0f, 2143L,
                                new NormalNoise.NoiseParameters(-3, 1.0), 1.0f,
                                List.of(Blocks.TALL_GRASS.getDefaultState(), Blocks.RED_TULIP.getDefaultState(), Blocks.POPPY.getDefaultState(), Blocks.AZURE_BLUET.getDefaultState(),
                                        ModNatureBlocks.LIGHT_BLUE_FLOWERS.getDefaultState(), Blocks.CORNFLOWER.getDefaultState(), Blocks.OXEYE_DAISY.getDefaultState(), Blocks.GRASS.getDefaultState()))))));
        FeatureUtils.register(featureRegisterable, FLOWER_LOSSARNACH, Feature.FLOWER,
                new RandomPatchConfiguration(96, 6, 2, PlacementUtils.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(new DualNoiseProvider(new InclusiveRange<>(1, 3), new NormalNoise.NoiseParameters(-10, 1.0), 1.0f, 2241L,
                                new NormalNoise.NoiseParameters(-3, 1.0), 1.0f,
                                List.of(Blocks.TALL_GRASS.getDefaultState(), Blocks.RED_TULIP.getDefaultState(), Blocks.POPPY.getDefaultState(), Blocks.PINK_TULIP.getDefaultState(),
                                        Blocks.ROSE_BUSH.getDefaultState(), Blocks.ORANGE_TULIP.getDefaultState(), Blocks.OXEYE_DAISY.getDefaultState(),
                                        Blocks.LILY_OF_THE_VALLEY.getDefaultState(), Blocks.ALLIUM.getDefaultState(), Blocks.GRASS.getDefaultState()))))));
        FeatureUtils.register(featureRegisterable, FLOWER_DORWINION, Feature.FLOWER,
                new RandomPatchConfiguration(96, 6, 2, PlacementUtils.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(new DualNoiseProvider(new InclusiveRange<>(1, 3), new NormalNoise.NoiseParameters(-10, 1.0), 1.0f, 2345L,
                                new NormalNoise.NoiseParameters(-3, 1.0), 1.0f,
                                List.of(Blocks.TALL_GRASS.getDefaultState(), Blocks.PINK_TULIP.getDefaultState(), Blocks.WHITE_TULIP.getDefaultState(), Blocks.AZURE_BLUET.getDefaultState(),
                                        Blocks.ALLIUM.getDefaultState(), Blocks.CORNFLOWER.getDefaultState(), Blocks.LILY_OF_THE_VALLEY.getDefaultState(), Blocks.GRASS.getDefaultState()))))));
        FeatureUtils.register(featureRegisterable, FLOWER_MALLOS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.MALLOS))));
        FeatureUtils.register(featureRegisterable, FLOWER_NOBLEWHITE, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.NOBLEWHITE))));
        FeatureUtils.register(featureRegisterable, FLOWER_POPPY, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(Blocks.POPPY))));
        FeatureUtils.register(featureRegisterable, FLOWER_ELANOR, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.ELANOR))));
        FeatureUtils.register(featureRegisterable, FLOWER_NIPHREDIL, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.NIPHREDIL))));
        FeatureUtils.register(featureRegisterable, FLOWER_SIMBELMYNE, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.SIMBELMYNE))));
        FeatureUtils.register(featureRegisterable, FLOWER_ROSE_BUSH, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(Blocks.ROSE_BUSH))));

        FeatureUtils.register(featureRegisterable, FLOWERS_LIGHT_BLUE, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.LIGHT_BLUE_FLOWERS))));
        FeatureUtils.register(featureRegisterable, FLOWERS_MAGENTA, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.MAGENTA_FLOWERS))));
        FeatureUtils.register(featureRegisterable, FLOWERS_ORANGE, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.ORANGE_FLOWERS))));
        FeatureUtils.register(featureRegisterable, FLOWERS_PINK, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.PINK_FLOWERS))));
        FeatureUtils.register(featureRegisterable, FLOWERS_PURPLE, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.PURPLE_FLOWERS))));
        FeatureUtils.register(featureRegisterable, FLOWERS_RED, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.RED_FLOWERS))));
        FeatureUtils.register(featureRegisterable, FLOWERS_WHITE, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.WHITE_FLOWERS))));
        FeatureUtils.register(featureRegisterable, FLOWERS_YELLOW, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.YELLOW_FLOWERS))));

        FeatureUtils.register(featureRegisterable, AZALEA_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceBlock)ModNatureBlocks.AZALEA_FLOWER_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, DRY_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceBlock)ModNatureBlocks.DRY_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, IVY_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceBlock)ModNatureBlocks.IVY_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, PATCH_FROZEN_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceBlock)ModNatureBlocks.FROZEN_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, PATCH_GREEN_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceBlock)ModNatureBlocks.GREEN_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, PATCH_THORNY_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceBlock)ModNatureBlocks.THORNY_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, LILAC_FLOWER_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceBlock)ModNatureBlocks.LILAC_FLOWER_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, RED_FLOWER_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceBlock)ModNatureBlocks.RED_FLOWER_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, YELLOW_FLOWER_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceBlock)ModNatureBlocks.YELLOW_FLOWER_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, PINK_FLOWER_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceBlock)ModNatureBlocks.PINK_FLOWER_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, WHITE_FLOWER_GROWTH, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceBlock)ModNatureBlocks.WHITE_FLOWER_GROWTH,
                        20, true, true, true, 0.5f, BLOCKS_GROWTH));

        FeatureUtils.register(featureRegisterable, PATCH_BASALT, Feature.BLOCK_PILE,
                new BlockPileConfiguration(BlockStateProvider.of(Blocks.BASALT)));
        FeatureUtils.register(featureRegisterable, PATCH_BLACKSTONE, Feature.BLOCK_PILE,
                new BlockPileConfiguration(BlockStateProvider.of(Blocks.BLACKSTONE)));
        FeatureUtils.register(featureRegisterable, PATCH_PUMICE, Feature.BLOCK_PILE,
                new BlockPileConfiguration(BlockStateProvider.of(StoneBlockSets.PUMICE_SET.baseBlocks.base())));

        FeatureUtils.register(featureRegisterable, PATCH_COBWEB, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(Blocks.COBWEB))));

        FeatureUtils.register(featureRegisterable, PATCH_PERSISTENT_WEBBING, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceBlock)ModNatureBlocks.WEBBING,
                        20, true, true, true, 0.95f, BLOCKS_GROWTH));
        FeatureUtils.register(featureRegisterable, PATCH_WEBBING, ModFeatures.MULTIFACE_PERSISTENT,
                new MultifaceStateFeatureConfig(ModNatureBlocks.WEBBING,
                        20, true, true, true, false, 0.95f, BLOCKS_GROWTH));

        FeatureUtils.register(featureRegisterable, PATCH_SPIDER_EGGS, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.SHELOBITE_LARVA_EGG))));

        FeatureUtils.register(featureRegisterable, PUMICE_COLUMN, ModFeatures.CAVE_COLUMN,
                new CaveColumnFeatureConfig(ConstantInt.create(1), UniformInt.create(1, 4), StoneBlockSets.PUMICE_SET.baseBlocks.base().getDefaultState()));
        FeatureUtils.register(featureRegisterable, PUMICE_COLUMN_LARGE, ModFeatures.CAVE_COLUMN,
                new CaveColumnFeatureConfig(UniformInt.create(2, 3), UniformInt.create(5, 10), StoneBlockSets.PUMICE_SET.baseBlocks.base().getDefaultState()));

        FeatureUtils.register(featureRegisterable, PATCH_BRACKEN, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.BRACKEN))));

        FeatureUtils.register(featureRegisterable, PATCH_GIANT_BUTTERBUR, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.GIANT_BUTTERBUR))));

        FeatureUtils.register(featureRegisterable, FIELD_BLUE_FESCUE, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.RANDOM_PATCH,
                        new RandomPatchConfiguration(8, 3, 2,
                                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                                .add(ModNatureBlocks.BLUE_FESCUE.getDefaultState(), 2)
                                                .add(ModNatureBlocks.LARGE_BLUE_FESCUE.getDefaultState(), 1))
                                )))));

        FeatureUtils.register(featureRegisterable, PATCH_BROWN_GRASS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.BROWN_GRASS))));

        FeatureUtils.register(featureRegisterable, PATCH_SHORT_BULRUSH, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.SHORT_BULRUSH))));
        FeatureUtils.register(featureRegisterable, PATCH_SHORT_CATTAIL, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.SHORT_CATTAILS))));

        FeatureUtils.register(featureRegisterable, PATCH_TALL_BULRUSH, Feature.FLOWER,
                createShallowWaterPatchFeatureConfig(ModNatureBlocks.TALL_BULRUSH, 96, 7, 3));
        FeatureUtils.register(featureRegisterable, PATCH_TALL_CATTAIL, Feature.FLOWER,
                createShallowWaterPatchFeatureConfig(ModNatureBlocks.TALL_CATTAILS, 96, 7, 3));

        FeatureUtils.register(featureRegisterable, PATCH_COASTAL_PANIC_GRASS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.COASTAL_PANIC_GRASS))));

        FeatureUtils.register(featureRegisterable, PATCH_CORRUPTED_MOSS, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceBlock)ModNatureBlocks.CORRUPTED_MOSS,
                        20, true, true, true, 0.5f,
                        HolderSet.of(Block::getRegistryEntry,
                                Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.MOSSY_COBBLESTONE, Blocks.COBBLESTONE,
                                Blocks.GRASS_BLOCK, Blocks.DIRT,  Blocks.SPRUCE_LOG, Blocks.OAK_LOG, Blocks.BIRCH_LOG, Blocks.DARK_OAK_LOG,
                                WoodBlockSets.MIRKWOOD_SET.logBlocks.log(), ModNatureBlocks.OLD_PODZOL)));

        FeatureUtils.register(featureRegisterable, PATCH_CORRUPTED_MOSS_CARPET, Feature.BLOCK_PILE,
                new BlockPileConfiguration(BlockStateProvider.of(ModNatureBlocks.CORRUPTED_MOSS_CARPET)));

        FeatureUtils.register(featureRegisterable, PATCH_DEAD_RUSHES, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.DEAD_RUSHES))));

        FeatureUtils.register(featureRegisterable, PATCH_BEACH_GRASS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.BEACH_GRASS))));
        FeatureUtils.register(featureRegisterable, PATCH_BUSHES, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                .add(ModNatureBlocks.LARGE_BUSH.getDefaultState(), 4))), List.of(), 15));
        FeatureUtils.register(featureRegisterable, PATCH_CLOVERS, Feature.FLOWER, new RandomPatchConfiguration(48, 6, 2,
                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(CavesConfiguredFeatures.get4StagesBlockBuilder(ModNatureBlocks.CLOVERS))))));
        FeatureUtils.register(featureRegisterable, PATCH_DRY_GRASS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                .add(Blocks.DEAD_BUSH.getDefaultState(), 4))), List.of(), 15));
        FeatureUtils.register(featureRegisterable, PATCH_SMALL_DRY_SHRUB, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.SMALL_DRY_SHRUB))));

        FeatureUtils.register(featureRegisterable, PATCH_DUCKWEED, Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(48, 1, 3, PlacementUtils.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.DUCKWEED)))));

        FeatureUtils.register(featureRegisterable, PATCH_DYING_GRASS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.DYING_GRASS))));

        FeatureUtils.register(featureRegisterable, PATCH_FALLEN_LEAVES, Feature.BLOCK_PILE,
                new BlockPileConfiguration(BlockStateProvider.of(ModNatureBlocks.FALLEN_LEAVES)));
        FeatureUtils.register(featureRegisterable, PATCH_FALLEN_MALLORN_LEAVES, Feature.BLOCK_PILE,
                new BlockPileConfiguration(BlockStateProvider.of(ModNatureBlocks.FALLEN_MALLORN_LEAVES)));
        FeatureUtils.register(featureRegisterable, PATCH_FALLEN_MIRKWOOD_LEAVES, Feature.BLOCK_PILE,
                new BlockPileConfiguration(BlockStateProvider.of(ModNatureBlocks.FALLEN_MIRKWOOD_LEAVES)));

        FeatureUtils.register(featureRegisterable, PATCH_FALSE_OATGRASS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.FALSE_OATGRASS))));

        FeatureUtils.register(featureRegisterable, PATCH_FOREST_MOSS, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceBlock)ModNatureBlocks.FOREST_MOSS,
                    20, true, true, true, 0.5f, BLOCKS_MOSS));

        FeatureUtils.register(featureRegisterable, PATCH_FLOATING_ICE, Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 7, 3, PlacementUtils.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.FLOATING_ICE)))));

        FeatureUtils.register(featureRegisterable, PATCH_FOREST_MOSS_CARPET, Feature.BLOCK_PILE,
                new BlockPileConfiguration(BlockStateProvider.of(ModNatureBlocks.FOREST_MOSS_CARPET)));

        FeatureUtils.register(featureRegisterable, PATCH_FROZEN_GRASS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.FROZEN_GRASS))));
        FeatureUtils.register(featureRegisterable, PATCH_FROZEN_SHRUB, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.FROZEN_SHRUB))));
        FeatureUtils.register(featureRegisterable, PATCH_FROZEN_TUFT, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.FROZEN_TUFT))));

        FeatureUtils.register(featureRegisterable, PATCH_GREEN_SHRUB, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.GREEN_SHRUB))));

        FeatureUtils.register(featureRegisterable, PATCH_GRIM_GRASS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.GRIM_GRASS))));

        FeatureUtils.register(featureRegisterable, PATCH_HEATH, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.HEATH))));
        FeatureUtils.register(featureRegisterable, PATCH_HEATHER, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.RANDOM_PATCH,
                        new RandomPatchConfiguration(8, 3, 2,
                                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                                .add(ModNatureBlocks.HEATHER.getDefaultState(), 1)
                                                .add(ModNatureBlocks.HEATHER_BUSH.getDefaultState(), 1))
                                )))));
        FeatureUtils.register(featureRegisterable, PATCH_DEAD_HEATHER, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.RANDOM_PATCH,
                        new RandomPatchConfiguration(8, 3, 2,
                                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                                .add(ModNatureBlocks.DEAD_HEATHER.getDefaultState(), 1)
                                                .add(ModNatureBlocks.DEAD_HEATHER_BUSH.getDefaultState(), 1))
                                )))));
        FeatureUtils.register(featureRegisterable, PATCH_DRY_HEATHER, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.RANDOM_PATCH,
                        new RandomPatchConfiguration(8, 3, 2,
                                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                                .add(ModNatureBlocks.DRY_HEATHER.getDefaultState(), 1)
                                                .add(ModNatureBlocks.DRY_HEATHER_BUSH.getDefaultState(), 1))
                                )))));

        FeatureUtils.register(featureRegisterable, PATCH_RED_HEATHER, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.RANDOM_PATCH,
                        new RandomPatchConfiguration(8, 3, 2,
                                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                                .add(ModNatureBlocks.RED_HEATHER.getDefaultState(), 1)
                                                .add(ModNatureBlocks.RED_HEATHER_BUSH.getDefaultState(), 1))
                                )))));
        FeatureUtils.register(featureRegisterable, FIELD_DEAD_NORMAL_HEATHER, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.RANDOM_PATCH,
                        new RandomPatchConfiguration(8, 3, 2,
                                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                                .add(ModNatureBlocks.HEATHER.getDefaultState(), 1)
                                                .add(ModNatureBlocks.HEATHER_BUSH.getDefaultState(), 1)
                                                .add(ModNatureBlocks.DEAD_HEATHER.getDefaultState(), 1)
                                                .add(ModNatureBlocks.DEAD_HEATHER_BUSH.getDefaultState(), 1))
                                )))));

        FeatureUtils.register(featureRegisterable, PATCH_BLUE_LAVENDER, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.BLUE_LAVENDER))));

        FeatureUtils.register(featureRegisterable, PATCH_LAVENDER, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.LAVENDER))));

        FeatureUtils.register(featureRegisterable, PATCH_WHITE_LAVENDER, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.WHITE_LAVENDER))));

        FeatureUtils.register(featureRegisterable, PATCH_HOGWEED, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.RANDOM_PATCH,
                        new RandomPatchConfiguration(8, 3, 2,
                                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                                .add(ModNatureBlocks.HOGWEED.getDefaultState(), 1)
                                                .add(ModNatureBlocks.SHORT_HOGWEED.getDefaultState(), 3))
                                        )))));

        FeatureUtils.register(featureRegisterable, PATCH_HOBBIT_SUNFLOWERS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.HOBBIT_SUNFLOWERS))));

        FeatureUtils.register(featureRegisterable, PATCH_BLUE_BIGLEAF_HYDRANGEA, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.BLUE_BIGLEAF_HYDRANGEA))));
        FeatureUtils.register(featureRegisterable, PATCH_PINK_BIGLEAF_HYDRANGEA, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.PINK_BIGLEAF_HYDRANGEA))));
        FeatureUtils.register(featureRegisterable, PATCH_WHITE_BIGLEAF_HYDRANGEA, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.WHITE_BIGLEAF_HYDRANGEA))));

        FeatureUtils.register(featureRegisterable, PATCH_CAMPION, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.CAMPION))));

        FeatureUtils.register(featureRegisterable, PATCH_REEDS, Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(256, 12, 3, PlacementUtils.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ResourceItemsME.REEDS)),
                        BlockPredicateFilter.of(BlockPredicate.allOf(BlockPredicate.anyOf(BlockPredicate.allOf(BlockPredicate.IS_AIR,
                                                BlockPredicate.anyOf(
                                                        BlockPredicate.matchingFluids(new BlockPos(1, -1, 0), Fluids.WATER, Fluids.FLOWING_WATER),
                                                        BlockPredicate.matchingFluids(new BlockPos(-1, -1, 0), Fluids.WATER, Fluids.FLOWING_WATER),
                                                        BlockPredicate.matchingFluids(new BlockPos(0, -1, 1), Fluids.WATER, Fluids.FLOWING_WATER),
                                                        BlockPredicate.matchingFluids(new BlockPos(0, -1, -1), Fluids.WATER, Fluids.FLOWING_WATER),
                                                        BlockPredicate.matchingFluids(new BlockPos(1, -1, 1), Fluids.WATER, Fluids.FLOWING_WATER),
                                                        BlockPredicate.matchingFluids(new BlockPos(-1, -1, -1), Fluids.WATER, Fluids.FLOWING_WATER),
                                                        BlockPredicate.matchingFluids(new BlockPos(-1, -1, 1), Fluids.WATER, Fluids.FLOWING_WATER),
                                                        BlockPredicate.matchingFluids(new BlockPos(1, -1, -1), Fluids.WATER, Fluids.FLOWING_WATER))),
                                BlockPredicate.allOf(BlockPredicate.matchingFluids(BlockPos.ORIGIN, Fluids.WATER, Fluids.FLOWING_WATER), hasAdjacentBank())),
                                BlockPredicate.wouldSurvive(ResourceItemsME.REEDS.getDefaultState(), BlockPos.ORIGIN))))));
        FeatureUtils.register(featureRegisterable, PATCH_SHORT_REEDS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.SHORT_REEDS))));

        FeatureUtils.register(featureRegisterable, PATCH_MISTWEED, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.MISTWEED))));

        FeatureUtils.register(featureRegisterable, PATCH_SCORCHED_GRASS, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.SCORCHED_GRASS))));
        FeatureUtils.register(featureRegisterable, PATCH_SCORCHED_SHRUB, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.SCORCHED_SHRUB))));
        FeatureUtils.register(featureRegisterable, PATCH_SCORCHED_TUFT, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.SCORCHED_TUFT))));
        FeatureUtils.register(featureRegisterable, PATCH_SEDUM, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.SEDUM))));
        FeatureUtils.register(featureRegisterable, PATCH_SEDUM_ORANGE, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.ORANGE_SEDUM))));
        FeatureUtils.register(featureRegisterable, PATCH_SEDUM_RED, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.RED_SEDUM))));
        FeatureUtils.register(featureRegisterable, PATCH_SEDUM_YELLOW, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.YELLOW_SEDUM))));
        FeatureUtils.register(featureRegisterable, PATCH_SEDUMS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.RANDOM_PATCH,
                        new RandomPatchConfiguration(8, 3, 2,
                                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(
                                        new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                                .add(ModNatureBlocks.ORANGE_SEDUM.getDefaultState(), 1)
                                                .add(ModNatureBlocks.RED_SEDUM.getDefaultState(), 1)
                                                .add(ModNatureBlocks.YELLOW_SEDUM.getDefaultState(), 1))
                                )))));

        FeatureUtils.register(featureRegisterable, PATCH_SHRIVELED_SHRUB, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.SHRIVELED_SHRUB))));
        FeatureUtils.register(featureRegisterable, PATCH_LARGE_SHRIVELED_SHRUB, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.LARGE_SHRIVELED_SHRUB))));

        FeatureUtils.register(featureRegisterable, PATCH_RUSHES, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.RUSHES))));
        FeatureUtils.register(featureRegisterable, PATCH_SHORT_RUSHES, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.SHORT_RUSHES))));

        FeatureUtils.register(featureRegisterable, PATCH_STRAWBERRY_BUSH, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.STRAWBERRY_BUSH.getDefaultState()
                                .with(SweetBerryBushBlock.AGE, 0))), List.of(Blocks.GRASS_BLOCK)));

        FeatureUtils.register(featureRegisterable, PATCH_YELLOW_TROLLIUS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.YELLOW_TROLLIUS))));

        FeatureUtils.register(featureRegisterable, PATCH_TAN_SHRUB, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.TAN_SHRUB))));

        FeatureUtils.register(featureRegisterable, PATCH_TEMPERATE_GRASS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.TEMPERATE_GRASS))));

        FeatureUtils.register(featureRegisterable, PATCH_SWEET_BERRY_BUSH, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(Blocks.SWEET_BERRY_BUSH.getDefaultState()
                                .with(SweetBerryBushBlock.AGE, 0))), List.of(
                                        Blocks.GRASS_BLOCK, Blocks.DIRT, ModBlocks.GRASSY_DIRT,
                                ModBlocks.LOAM_GRASS_BLOCK, ModBlocks.LOAM, ModBlocks.GRASSY_LOAM,
                                ModBlocks.PEAT_GRASS_BLOCK, ModBlocks.PEAT, ModBlocks.GRASSY_PEAT,
                                ModBlocks.SILT_GRASS_BLOCK, ModBlocks.SILT, ModBlocks.GRASSY_SILT)));

        FeatureUtils.register(featureRegisterable, PATCH_TOUGH_BERRY_BUSH, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.TOUGH_BERRY_BUSH.getDefaultState()
                                .with(SweetBerryBushBlock.AGE, 0))), List.of(Blocks.GRASS_BLOCK, Blocks.DIRT, ModBlocks.ASHEN_DIRT)));

        FeatureUtils.register(featureRegisterable, PATCH_TUFT_GRASS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.GRASS_TUFT))));

        FeatureUtils.register(featureRegisterable, PATCH_SPARSE_GRASS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.SPARSE_GRASS))));

        FeatureUtils.register(featureRegisterable, PATCH_LARGE_LILY_PAD, Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 7, 3, PlacementUtils.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.LARGE_LILY_PAD)))));
        FeatureUtils.register(featureRegisterable, PATCH_LARGE_FLOWERING_LILY_PAD, Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 7, 3, PlacementUtils.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.LARGE_FLOWERING_LILY_PAD)))));

        FeatureUtils.register(featureRegisterable, PATCH_LILY_PADS, Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 7, 3, PlacementUtils.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.LILY_PADS)))));
        FeatureUtils.register(featureRegisterable, PATCH_FLOWERING_LILY_PADS, Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(12, 7, 3, PlacementUtils.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.FLOWERING_LILY_PADS)))));

        FeatureUtils.register(featureRegisterable, PATCH_SMALL_LILY_PADS, Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(10, 7, 3, PlacementUtils.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.SMALL_LILY_PADS)))));
        FeatureUtils.register(featureRegisterable, PATCH_SMALL_FLOWERING_LILY_PADS, Feature.RANDOM_PATCH,
                new RandomPatchConfiguration(10, 7, 3, PlacementUtils.createEntry(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.SMALL_FLOWERING_LILY_PADS)))));

        FeatureUtils.register(featureRegisterable, PATCH_MIRKWOOD, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                                .add(Blocks.GRASS.getDefaultState(), 8)
                                .add(Blocks.FERN.getDefaultState(), 8)
                                .add(Blocks.TALL_GRASS.getDefaultState(), 15)
                                .add(Blocks.LARGE_FERN.getDefaultState(), 10)
                                .add(Blocks.BROWN_MUSHROOM.getDefaultState(), 1))), List.of(), 15));

        FeatureUtils.register(featureRegisterable, PATCH_MIRKWOOD_ROOTS, Feature.BLOCK_PILE,
                new BlockPileConfiguration(new WeightedStateProvider(SimpleWeightedRandomList.<BlockState>builder()
                        .add(ModNatureBlocks.MIRKWOOD_ROOTS.getDefaultState(), 3))));

        FeatureUtils.register(featureRegisterable, PATCH_MOSS, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceBlock)ModNatureBlocks.MOSS,
                        20, true, true, true, 0.5f, BLOCKS_MOSS));
        FeatureUtils.register(featureRegisterable, PATCH_MOSS_CARPET, Feature.BLOCK_PILE,
                new BlockPileConfiguration(BlockStateProvider.of(Blocks.MOSS_CARPET)));

        FeatureUtils.register(featureRegisterable, PATCH_STICKY_SNOW, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceBlock)ModNatureBlocks.STICKY_SNOW,
                        32, true, true, true, 0.75f,
                        HolderSet.of(Block::getRegistryEntry,
                                Blocks.STONE, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.MOSSY_COBBLESTONE, Blocks.COBBLESTONE,
                                Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.BASALT)));

        FeatureUtils.register(featureRegisterable, PATCH_MIXED_WILD_WHEAT, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(new WeightedStateProvider((SimpleWeightedRandomList.<BlockState>builder()
                                .add(ModNatureBlocks.TALL_WILD_WHEAT.getDefaultState(), 1)
                                .add(ModNatureBlocks.WILD_WHEAT.getDefaultState(), 3))
                        ))));

        FeatureUtils.register(featureRegisterable, PATCH_WHEAT_GRASS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.WHEATGRASS))));

        FeatureUtils.register(featureRegisterable, PATCH_MEADOW_GRASS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.MEADOWGRASS))));

        FeatureUtils.register(featureRegisterable, PATCH_WILD_GRASS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.WILD_GRASS))));
        FeatureUtils.register(featureRegisterable, PATCH_WILDER_GRASS, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.WILDERGRASS))));

        FeatureUtils.register(featureRegisterable, PATCH_YELLOW_FLOWER, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.YELLOW_FLOWER))));

        FeatureUtils.register(featureRegisterable, PATCH_NETTLES, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.NETTLES))));

        FeatureUtils.register(featureRegisterable, PATCH_THISTLE, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.THISTLE))));

        FeatureUtils.register(featureRegisterable, PATCH_MORDOR_BRAMBLES, Feature.FLOWER,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.BRAMBLES_OF_MORDOR))));

        // region MUSHROOMS
        FeatureUtils.register(featureRegisterable, PATCH_BROWN_BOLETE, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.BROWN_BOLETE))));
        FeatureUtils.register(featureRegisterable, PATCH_BROWN_BOLETE_TILLER, Feature.FLOWER, new RandomPatchConfiguration(48, 6, 2,
                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(CavesConfiguredFeatures.get4StagesBlockBuilder(ModNatureBlocks.BROWN_BOLETE_TILLER))))));

        FeatureUtils.register(featureRegisterable, PATCH_MORSEL, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.MORSEL))));
        FeatureUtils.register(featureRegisterable, PATCH_MORSEL_TILLER, Feature.FLOWER, new RandomPatchConfiguration(48, 6, 2,
                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(CavesConfiguredFeatures.get4StagesBlockBuilder(ModNatureBlocks.MORSEL_TILLER))))));

        FeatureUtils.register(featureRegisterable, PATCH_WHITE_MUSHROOM, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.WHITE_MUSHROOM))));
        FeatureUtils.register(featureRegisterable, PATCH_WHITE_MUSHROOM_TILLER, Feature.FLOWER, new RandomPatchConfiguration(48, 6, 2,
                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(CavesConfiguredFeatures.get4StagesBlockBuilder(ModNatureBlocks.WHITE_MUSHROOM_TILLER))))));
        // endregion

        // region WILD CROPS
        FeatureUtils.register(featureRegisterable, PATCH_WILD_BEETROOT, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.WILD_BEETROOT))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_BELL_PEPPER, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.WILD_BELL_PEPPER))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_CARROT, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.WILD_CARROT))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_CUCUMBER, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.WILD_CUCUMBER))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_FLAX, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.WILD_FLAX))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_GARLIC, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.WILD_GARLIC))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_LEEK, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.WILD_LEEK))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_LETTUCE, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.WILD_LETTUCE))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_ONION, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.WILD_ONION))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_PIPEWEED, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.WILD_PIPEWEED))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_POTATO, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.WILD_POTATO))));
        FeatureUtils.register(featureRegisterable, PATCH_WILD_TOMATO, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.WILD_TOMATO))));
        // endregion
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.of(Registries.CONFIGURED_FEATURE, new ResourceLocation(MiddleEarth.MOD_ID, name));
    }

    private static RandomPatchConfiguration createShallowWaterPatchFeatureConfig(Block block, int tries, int xzSpread, int ySpread) {
        BlockState state = block.getDefaultState();
        return new RandomPatchConfiguration(tries, xzSpread, ySpread, PlacementUtils.createEntry(Feature.SIMPLE_BLOCK,
                new SimpleBlockConfiguration(BlockStateProvider.of(state)),
                BlockPredicateFilter.of(BlockPredicate.allOf(BlockPredicate.anyOf(
                                BlockPredicate.IS_AIR,
                                BlockPredicate.allOf(
                                        BlockPredicate.matchingFluids(BlockPos.ORIGIN, Fluids.WATER, Fluids.FLOWING_WATER), hasAdjacentBank())), BlockPredicate.wouldSurvive(state, BlockPos.ORIGIN)))));
    }

    private static BlockPredicate hasAdjacentBank() {
        return BlockPredicate.anyOf(
                BlockPredicate.hasSturdyFace(new BlockPos(1, 0, 0), Direction.UP),
                BlockPredicate.hasSturdyFace(new BlockPos(-1, 0, 0), Direction.UP),
                BlockPredicate.hasSturdyFace(new BlockPos(0, 0, 1), Direction.UP),
                BlockPredicate.hasSturdyFace(new BlockPos(0, 0, -1), Direction.UP));
    }
}
