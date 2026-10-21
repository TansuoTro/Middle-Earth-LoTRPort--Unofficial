package net.jukoz.me.world.features.underground;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlocks;
import net.jukoz.me.block.registration.ModNatureBlocks;
import net.jukoz.me.block.registration.OreRockSets;
import net.jukoz.me.block.registration.StoneBlockSets;
import net.jukoz.me.block.special.DroopingIciclesBlock;
import net.jukoz.me.world.features.columns.ClusterFeatureConfig;
import net.jukoz.me.world.features.columns.SmallPointedStoneFeatureConfig;
import net.jukoz.me.world.features.ores.ModOreFeatureConfig;
import net.jukoz.me.world.features.ores.SurfaceOreFeatureConfig;
import net.jukoz.me.world.features.pillar.PillarFeatureConfig;
import net.jukoz.me.world.gen.ModFeatures;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.core.RegistryEntryLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.HolderList;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.levelgen.structure.templatesystem.BlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.TagMatchTest;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.core.Direction;
import net.minecraft.world.level.levelgen.placement.CaveSurface;
import net.minecraft.util.valueproviders.ClampedNormalFloat;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.placement.EnvironmentScanPlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.RandomOffsetPlacement;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import java.util.List;
import net.minecraft.data.worldgen.features.CaveFeatures;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.PinkPetalsBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.GeodeBlockSettings;
import net.minecraft.world.level.levelgen.GeodeCrackSettings;
import net.minecraft.world.level.levelgen.GeodeLayerSettings;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockColumnConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.GeodeConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.MultifaceGrowthConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.OreConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleRandomFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SpringConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.VegetationPatchConfiguration;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;

public class CavesConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> AMETHYST_GEODE = registerKey("amethyst_geode");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CITRINE_GEODE = registerKey("citrine_geode");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWSTONE_GEODE = registerKey("glowstone_geode");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_AGATE_GEODE = registerKey("red_agate_geode");
    public static final ResourceKey<ConfiguredFeature<?, ?>> QUARTZ_GEODE = registerKey("quartz_geode");

    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_DOLOMITE = registerKey("ore_dolomite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_OLD_DOLOMITE = registerKey("ore_old_dolomite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MUD = registerKey("ore_mud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> POOL_MUD = registerKey("pool_mud");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MAGMA = registerKey("ore_magma");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_OBSIDIAN = registerKey("ore_obsidian");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DISK_MYCELIUM = registerKey("disk_mycelium");
    public static final ResourceKey<ConfiguredFeature<?, ?>> POOL_MAGMA = registerKey("pool_magma");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ASH = registerKey("ore_ash");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ASHEN_DIRT = registerKey("ore_ashen_dirt");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_DRY_DIRT = registerKey("ore_dry_dirt");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_PACKED_ICE = registerKey("ore_packed_ice");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_BLUE_ICE = registerKey("ore_blue_ice");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GABBRO = registerKey("ore_gabbro");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GALONN = registerKey("ore_galonn");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_OLD_GALONN = registerKey("ore_old_galonn");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GNEISS = registerKey("ore_gneiss");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GREEN_TUFF = registerKey("ore_green_tuff");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GILDED_GREEN_TUFF = registerKey("ore_gilded_green_tuff");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GOLD_GREEN_TUFF = registerKey("ore_gold_green_tuff");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GOLDEN_GREEN_TUFF = registerKey("ore_golden_green_tuff");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_IZHER_ABAN = registerKey("ore_izher_aban");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ZIGIL_ABAN = registerKey("ore_zigil_aban");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_OLD_IZHER_ABAN = registerKey("ore_old_izher_aban");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_LIMESTONE = registerKey("ore_limestone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_OLD_LIMESTONE = registerKey("ore_old_limestone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SAND = registerKey("ore_sand");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SANDSTONE = registerKey("ore_sandstone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SCHIST = registerKey("ore_schist");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SNOW = registerKey("ore_snow");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TERRACOTTA = registerKey("ore_terracotta");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TUFF = registerKey("ore_tuff");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_BASALT = registerKey("ore_basalt");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_BLACKSTONE = registerKey("ore_blackstone");

    public static final ResourceKey<ConfiguredFeature<?, ?>> DOLOMITE_CLUSTER = registerKey("dolomite_cluster");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_DOLOMITE = registerKey("large_dolomite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> POINTED_DOLOMITE = registerKey("pointed_dolomite");

    public static final ResourceKey<ConfiguredFeature<?, ?>> GALONN_CLUSTER = registerKey("galonn_cluster");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_GALONN = registerKey("large_galonn");
    public static final ResourceKey<ConfiguredFeature<?, ?>> POINTED_GALONN = registerKey("pointed_galonn");

    public static final ResourceKey<ConfiguredFeature<?, ?>> IZHER_ABAN_CLUSTER = registerKey("izher_aban_cluster");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_IZHER_ABAN = registerKey("large_izher_aban");
    public static final ResourceKey<ConfiguredFeature<?, ?>> POINTED_IZHER_ABAN = registerKey("pointed_izher_aban");

    public static final ResourceKey<ConfiguredFeature<?, ?>> LIMESTONE_CLUSTER = registerKey("limestone_cluster");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARGE_LIMESTONE = registerKey("large_limestone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> POINTED_LIMESTONE = registerKey("pointed_limestone");

    public static final ResourceKey<ConfiguredFeature<?, ?>> PILLAR_BASALT = registerKey("pillar_basalt");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PILLAR_BLACKSTONE = registerKey("pillar_blackstone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PILLAR_PACKED_ICE = registerKey("pillar_packed_ice");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PILLAR_SMOOTH_SANDSTONE = registerKey("pillar_smooth_sandstone");

    // region Material Ores
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_COAL = registerKey("ore_coal");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_COPPER = registerKey("ore_copper");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_TIN = registerKey("ore_tin");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_LAPIS = registerKey("ore_lapis");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_LEAD = registerKey("ore_lead");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_QUARTZITE = registerKey("ore_quartzite");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_IRON = registerKey("ore_iron");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SILVER = registerKey("ore_silver");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_JADE = registerKey("ore_jade");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_GOLD = registerKey("ore_gold");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_MITHRIL = registerKey("ore_mithril");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_ADAMANT = registerKey("ore_adamant");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_EMERALD = registerKey("ore_emerald");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_RUBY = registerKey("ore_ruby");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORE_SAPPHIRE = registerKey("ore_sapphire");
    // endregion

    // region MUSHROOMS
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CAVE_AMANITA = registerKey("patch_cave_amanita");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_CAVE_AMANITA_TILLER = registerKey("patch_cave_amanita_tiller");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DEEP_FIRECAP = registerKey("patch_deep_firecap");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_DEEP_FIRECAP_TILLER = registerKey("patch_deep_firecap_tiller");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GHOSTSHROOM = registerKey("patch_ghostshroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_GHOSTSHROOM_TILLER = registerKey("patch_ghostshroom_tiller");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SKY_FIRECAP = registerKey("patch_sky_firecap");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_SKY_FIRECAP_TILLER = registerKey("patch_sky_firecap_tiller");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TUBESHROOMS = registerKey("patch_tubeshrooms");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TALL_TUBESHROOMS = registerKey("patch_tall_tubeshrooms");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TRUMPET_SHROOM = registerKey("patch_trumpet_shroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_TALL_TRUMPET_SHROOM = registerKey("patch_tall_trumpet_shroom");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_VIOLET_CAPS = registerKey("patch_violet_caps");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_VIOLET_CAPS_TILLER = registerKey("patch_violet_caps_tiller");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_YELLOW_AMANITA = registerKey("patch_yellow_amanita");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PATCH_YELLOW_AMANITA_TILLER = registerKey("patch_yellow_amanita_tiller");
    public static final ResourceKey<ConfiguredFeature<?, ?>> GLOWWORM_WEBBING = registerKey("glowworm_webbing");
    // endregion

    public static final ResourceKey<ConfiguredFeature<?, ?>> DROOPING_ICICLES = registerKey("drooping_icicles");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SHORT_ICICLES = registerKey("short_icicles");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STICKY_ICE = registerKey("sticky_ice");
    public static final ResourceKey<ConfiguredFeature<?, ?>> STICKY_SNOW = registerKey("sticky_snow");

    public static final ResourceKey<ConfiguredFeature<?, ?>> SPRING_LAVA = registerKey("spring_lava");

    // region TESTS
    static TagMatchTest baseStone = new TagMatchTest(BlockTags.BASE_STONE_OVERWORLD);
    static TagMatchTest stoneTest = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);
    static BlockMatchTest ashenStoneTest = new BlockMatchTest(StoneBlockSets.ASHENSTONE_SET.baseBlocks.base());
    static BlockMatchTest blueTuffTest = new BlockMatchTest(StoneBlockSets.KHAGALABAN_SET.baseBlocks.base());
    static BlockMatchTest dolomiteTest = new BlockMatchTest(StoneBlockSets.DOLOMITE_SET.baseBlocks.base());
    static BlockMatchTest hematiteTest = new BlockMatchTest(StoneBlockSets.HEMATITE_SET.baseBlocks.base());
    static BlockMatchTest greenTuffTest = new BlockMatchTest(StoneBlockSets.GREEN_TUFF_SET.baseBlocks.base());
    static BlockMatchTest galonnTest = new BlockMatchTest(StoneBlockSets.GALONN_SET.baseBlocks.base());
    static BlockMatchTest izherAbanTest = new BlockMatchTest(StoneBlockSets.IZHERABAN_SET.baseBlocks.base());
    static BlockMatchTest calciteTest = new BlockMatchTest(Blocks.CALCITE);
    static BlockMatchTest dioriteTest = new BlockMatchTest(Blocks.DIORITE);
    static BlockMatchTest ironStoneTest = new BlockMatchTest(StoneBlockSets.IRONSTONE_SET.baseBlocks.base());
    static BlockMatchTest gonluinTest = new BlockMatchTest(StoneBlockSets.KHAGALABAN_SET.baseBlocks.base());
    static BlockMatchTest limestoneTest = new BlockMatchTest(StoneBlockSets.LIMESTONE_SET.baseBlocks.base());
    static BlockMatchTest slateTest = new BlockMatchTest(StoneBlockSets.SLATE_SET.baseBlocks.base());
    static TagMatchTest deepslateTest = new TagMatchTest(BlockTags.DEEPSLATE_ORE_REPLACEABLES);
    static BlockMatchTest nurgonTest = new BlockMatchTest(StoneBlockSets.NURGON_SET.baseBlocks.base());
    static BlockMatchTest medgonTest = new BlockMatchTest(StoneBlockSets.MEDGON_SET.baseBlocks.base());
    // endregion

    // region LISTS
    static List<OreConfiguration.Target> dolomiteReplaceTest = List.of(
            OreConfiguration.createTarget(baseStone, StoneBlockSets.DOLOMITE_SET.baseBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, StoneBlockSets.DOLOMITE_SET.baseBlocks.base().getDefaultState()));
    static List<OreConfiguration.Target> oldDolomiteReplaceTest = List.of(
            OreConfiguration.createTarget(baseStone, StoneBlockSets.DOLOMITE_SET.oldBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, StoneBlockSets.DOLOMITE_SET.oldBlocks.base().getDefaultState()));
    static List<OreConfiguration.Target> mudList = List.of(
            OreConfiguration.createTarget(gonluinTest, Blocks.MUD.getDefaultState()),
            OreConfiguration.createTarget(baseStone, Blocks.MUD.getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, Blocks.MUD.getDefaultState()));
    static List<OreConfiguration.Target> ashList = List.of(
            OreConfiguration.createTarget(ashenStoneTest, ModBlocks.ASH_BLOCK.getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, ModBlocks.ASH_BLOCK.getDefaultState()),
            OreConfiguration.createTarget(nurgonTest, ModBlocks.ASH_BLOCK.getDefaultState()),
            OreConfiguration.createTarget(medgonTest, ModBlocks.ASH_BLOCK.getDefaultState()));
    static List<OreConfiguration.Target> ashenDirtList = List.of(
            OreConfiguration.createTarget(ashenStoneTest, ModBlocks.ASHEN_DIRT.getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, ModBlocks.ASHEN_DIRT.getDefaultState()),
            OreConfiguration.createTarget(nurgonTest, ModBlocks.ASHEN_DIRT.getDefaultState()));
    static List<OreConfiguration.Target> dryDirtList = List.of(
            OreConfiguration.createTarget(stoneTest, ModBlocks.DRY_DIRT.getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, ModBlocks.DRY_DIRT.getDefaultState()));
    static List<OreConfiguration.Target> gabbroList = List.of(
            OreConfiguration.createTarget(stoneTest, StoneBlockSets.GABBRO_SET.baseBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, StoneBlockSets.GABBRO_SET.baseBlocks.base().getDefaultState()));
    static List<OreConfiguration.Target> galonnList = List.of(
            OreConfiguration.createTarget(stoneTest, StoneBlockSets.GALONN_SET.baseBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(limestoneTest, StoneBlockSets.GALONN_SET.baseBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, StoneBlockSets.GALONN_SET.baseBlocks.base().getDefaultState()));
    static List<OreConfiguration.Target> oldGalonnList = List.of(
            OreConfiguration.createTarget(stoneTest, StoneBlockSets.GALONN_SET.oldBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(limestoneTest, StoneBlockSets.GALONN_SET.oldBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, StoneBlockSets.GALONN_SET.oldBlocks.base().getDefaultState()));
    static List<OreConfiguration.Target> gneissList = List.of(
            OreConfiguration.createTarget(stoneTest, StoneBlockSets.GNEISS_SET.baseBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, StoneBlockSets.GNEISS_SET.baseBlocks.base().getDefaultState()));
    static List<OreConfiguration.Target> greenTuffList = List.of(
            OreConfiguration.createTarget(stoneTest, StoneBlockSets.GREEN_TUFF_SET.baseBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, StoneBlockSets.GREEN_TUFF_SET.baseBlocks.base().getDefaultState()));
    static List<OreConfiguration.Target> gildedGreenTuffList = List.of(
            OreConfiguration.createTarget(stoneTest, StoneBlockSets.GILDED_GREEN_TUFF_SET.baseBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, StoneBlockSets.GILDED_GREEN_TUFF_SET.baseBlocks.base().getDefaultState()));
    static List<ModOreFeatureConfig.Target> goldenGreenTuffList = List.of(
            ModOreFeatureConfig.createTarget(stoneTest, StoneBlockSets.GILDED_GREEN_TUFF_SET.baseBlocks.base().getDefaultState()),
            ModOreFeatureConfig.createTarget(deepslateTest, StoneBlockSets.GILDED_GREEN_TUFF_SET.baseBlocks.base().getDefaultState()));
    static List<OreConfiguration.Target> izherAbanList = List.of(
            OreConfiguration.createTarget(stoneTest, StoneBlockSets.IZHERABAN_SET.baseBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, StoneBlockSets.IZHERABAN_SET.baseBlocks.base().getDefaultState()));
    static List<OreConfiguration.Target> zigilAbanList = List.of(
            OreConfiguration.createTarget(stoneTest, StoneBlockSets.ZIGILABAN_SET.baseBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, StoneBlockSets.ZIGILABAN_SET.baseBlocks.base().getDefaultState()));
    static List<OreConfiguration.Target> oldIzherAbanList = List.of(
            OreConfiguration.createTarget(stoneTest, StoneBlockSets.IZHERABAN_SET.oldBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, StoneBlockSets.IZHERABAN_SET.oldBlocks.base().getDefaultState()));
    static List<OreConfiguration.Target> limestoneList = List.of(
            OreConfiguration.createTarget(stoneTest, StoneBlockSets.LIMESTONE_SET.baseBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, StoneBlockSets.LIMESTONE_SET.baseBlocks.base().getDefaultState()));
    static List<OreConfiguration.Target> oldLimestoneList = List.of(
            OreConfiguration.createTarget(stoneTest, StoneBlockSets.LIMESTONE_SET.oldBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, StoneBlockSets.LIMESTONE_SET.oldBlocks.base().getDefaultState()));
    static List<OreConfiguration.Target> schistList = List.of(
            OreConfiguration.createTarget(stoneTest, StoneBlockSets.SCHIST_SET.baseBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, StoneBlockSets.SCHIST_SET.baseBlocks.base().getDefaultState()));
    static List<OreConfiguration.Target> sandList = List.of(
            OreConfiguration.createTarget(stoneTest, Blocks.SAND.getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, Blocks.SAND.getDefaultState()));
    static List<OreConfiguration.Target> sandStoneList = List.of(
            OreConfiguration.createTarget(stoneTest, Blocks.SMOOTH_SANDSTONE.getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, Blocks.SMOOTH_SANDSTONE.getDefaultState()));
    static List<OreConfiguration.Target> snowList = List.of(
            OreConfiguration.createTarget(stoneTest, Blocks.SNOW_BLOCK.getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, Blocks.SNOW_BLOCK.getDefaultState()));
    static List<OreConfiguration.Target> packedIceList = List.of(
            OreConfiguration.createTarget(stoneTest, Blocks.PACKED_ICE.getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, Blocks.PACKED_ICE.getDefaultState()));
    static List<OreConfiguration.Target> blueIceList = List.of(
            OreConfiguration.createTarget(stoneTest, Blocks.BLUE_ICE.getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, Blocks.BLUE_ICE.getDefaultState()));
    static List<OreConfiguration.Target> terracottaList = List.of(
            OreConfiguration.createTarget(stoneTest, Blocks.TERRACOTTA.getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, Blocks.TERRACOTTA.getDefaultState()));
    static List<OreConfiguration.Target> tuffList = List.of(
            OreConfiguration.createTarget(nurgonTest, Blocks.TUFF.getDefaultState()));
    static List<OreConfiguration.Target> basaltList = List.of(
            OreConfiguration.createTarget(nurgonTest, Blocks.SMOOTH_BASALT.getDefaultState()));
    static List<OreConfiguration.Target> blackstoneList = List.of(
            OreConfiguration.createTarget(medgonTest, Blocks.BLACKSTONE.getDefaultState()));
    static List<OreConfiguration.Target> magmaList = List.of(
            OreConfiguration.createTarget(nurgonTest, Blocks.MAGMA_BLOCK.getDefaultState()),
            OreConfiguration.createTarget(medgonTest, Blocks.MAGMA_BLOCK.getDefaultState()));
    static List<OreConfiguration.Target> coalList = List.of(
            OreConfiguration.createTarget(stoneTest, Blocks.COAL_ORE.getDefaultState()),
            OreConfiguration.createTarget(blueTuffTest, OreRockSets.KHAGALABAN.coal_ore().getDefaultState()),
            OreConfiguration.createTarget(dolomiteTest, Blocks.COAL_ORE.getDefaultState()),
            OreConfiguration.createTarget(hematiteTest, OreRockSets.IRONSTONE.coal_ore().getDefaultState()),
            OreConfiguration.createTarget(galonnTest, OreRockSets.LIMESTONE.coal_ore().getDefaultState()),
            OreConfiguration.createTarget(greenTuffTest, Blocks.COAL_ORE.getDefaultState()),
            OreConfiguration.createTarget(izherAbanTest, OreRockSets.LIMESTONE.coal_ore().getDefaultState()),
            OreConfiguration.createTarget(ashenStoneTest, OreRockSets.ASHEN.coal_ore().getDefaultState()),
            OreConfiguration.createTarget(calciteTest, OreRockSets.CALCITE.coal_ore().getDefaultState()),
            OreConfiguration.createTarget(dioriteTest, OreRockSets.CALCITE.coal_ore().getDefaultState()),
            OreConfiguration.createTarget(ironStoneTest, OreRockSets.IRONSTONE.coal_ore().getDefaultState()),
            OreConfiguration.createTarget(gonluinTest, OreRockSets.KHAGALABAN.coal_ore().getDefaultState()),
            OreConfiguration.createTarget(limestoneTest, OreRockSets.LIMESTONE.coal_ore().getDefaultState()),
            OreConfiguration.createTarget(slateTest, OreRockSets.SLATE.coal_ore().getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, Blocks.DEEPSLATE_COAL_ORE.getDefaultState()));
    static List<OreConfiguration.Target> copperList = List.of(
            OreConfiguration.createTarget(stoneTest, Blocks.COPPER_ORE.getDefaultState()),
            OreConfiguration.createTarget(blueTuffTest, OreRockSets.KHAGALABAN.copper_ore().getDefaultState()),
            OreConfiguration.createTarget(dolomiteTest, Blocks.COPPER_ORE.getDefaultState()),
            OreConfiguration.createTarget(hematiteTest, OreRockSets.IRONSTONE.copper_ore().getDefaultState()),
            OreConfiguration.createTarget(galonnTest, OreRockSets.LIMESTONE.copper_ore().getDefaultState()),
            OreConfiguration.createTarget(greenTuffTest, Blocks.COPPER_ORE.getDefaultState()),
            OreConfiguration.createTarget(izherAbanTest, OreRockSets.LIMESTONE.copper_ore().getDefaultState()),
            OreConfiguration.createTarget(ashenStoneTest, OreRockSets.ASHEN.copper_ore().getDefaultState()),
            OreConfiguration.createTarget(calciteTest, OreRockSets.CALCITE.copper_ore().getDefaultState()),
            OreConfiguration.createTarget(dioriteTest, OreRockSets.CALCITE.copper_ore().getDefaultState()),
            OreConfiguration.createTarget(ironStoneTest, OreRockSets.IRONSTONE.copper_ore().getDefaultState()),
            OreConfiguration.createTarget(gonluinTest, OreRockSets.KHAGALABAN.copper_ore().getDefaultState()),
            OreConfiguration.createTarget(limestoneTest, OreRockSets.LIMESTONE.copper_ore().getDefaultState()),
            OreConfiguration.createTarget(slateTest, OreRockSets.SLATE.copper_ore().getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, Blocks.DEEPSLATE_COPPER_ORE.getDefaultState()));
    static List<OreConfiguration.Target> tinList = List.of(
            OreConfiguration.createTarget(stoneTest, OreRockSets.STONE.tin_ore().getDefaultState()),
            OreConfiguration.createTarget(blueTuffTest, OreRockSets.KHAGALABAN.tin_ore().getDefaultState()),
            OreConfiguration.createTarget(dolomiteTest, OreRockSets.STONE.tin_ore().getDefaultState()),
            OreConfiguration.createTarget(hematiteTest, OreRockSets.IRONSTONE.tin_ore().getDefaultState()),
            OreConfiguration.createTarget(galonnTest, OreRockSets.LIMESTONE.tin_ore().getDefaultState()),
            OreConfiguration.createTarget(greenTuffTest, OreRockSets.STONE.tin_ore().getDefaultState()),
            OreConfiguration.createTarget(izherAbanTest, OreRockSets.LIMESTONE.tin_ore().getDefaultState()),
            OreConfiguration.createTarget(ashenStoneTest, OreRockSets.ASHEN.tin_ore().getDefaultState()),
            OreConfiguration.createTarget(calciteTest, OreRockSets.CALCITE.tin_ore().getDefaultState()),
            OreConfiguration.createTarget(dioriteTest, OreRockSets.CALCITE.tin_ore().getDefaultState()),
            OreConfiguration.createTarget(ironStoneTest, OreRockSets.IRONSTONE.tin_ore().getDefaultState()),
            OreConfiguration.createTarget(gonluinTest, OreRockSets.KHAGALABAN.tin_ore().getDefaultState()),
            OreConfiguration.createTarget(limestoneTest, OreRockSets.LIMESTONE.tin_ore().getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, OreRockSets.DEEPSLATE.tin_ore().getDefaultState()),
            OreConfiguration.createTarget(slateTest, OreRockSets.SLATE.tin_ore().getDefaultState()),
            OreConfiguration.createTarget(nurgonTest, OreRockSets.NURGON.tin_ore().getDefaultState()));
    static List<OreConfiguration.Target> lapisList = List.of(OreConfiguration.createTarget(deepslateTest, Blocks.DEEPSLATE_LAPIS_ORE.getDefaultState()));
    static List<OreConfiguration.Target> leadList = List.of(
            OreConfiguration.createTarget(deepslateTest, OreRockSets.DEEPSLATE.lead_ore().getDefaultState()),
            OreConfiguration.createTarget(nurgonTest, OreRockSets.NURGON.lead_ore().getDefaultState()),
            OreConfiguration.createTarget(medgonTest, OreRockSets.MEDGON.lead_ore().getDefaultState()));

    static List<OreConfiguration.Target> quartziteList = List.of(
            OreConfiguration.createTarget(stoneTest, StoneBlockSets.QUARTZITE_SET.baseBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(ashenStoneTest, StoneBlockSets.QUARTZITE_SET.baseBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(gonluinTest, StoneBlockSets.QUARTZITE_SET.baseBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(limestoneTest, StoneBlockSets.QUARTZITE_SET.baseBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(deepslateTest, StoneBlockSets.QUARTZITE_SET.baseBlocks.base().getDefaultState()));
    static List<OreConfiguration.Target> ironList = List.of(
            OreConfiguration.createTarget(nurgonTest, OreRockSets.NURGON.iron_ore().getDefaultState()),
            OreConfiguration.createTarget(medgonTest, OreRockSets.MEDGON.iron_ore().getDefaultState()));
    static List<OreConfiguration.Target> silverList = List.of(
            OreConfiguration.createTarget(nurgonTest, OreRockSets.NURGON.silver_ore().getDefaultState()),
            OreConfiguration.createTarget(medgonTest, OreRockSets.MEDGON.silver_ore().getDefaultState()));
    static List<OreConfiguration.Target> jadeList = List.of(
            OreConfiguration.createTarget(nurgonTest, StoneBlockSets.JADEITE_SET.baseBlocks.base().getDefaultState()),
            OreConfiguration.createTarget(medgonTest, StoneBlockSets.JADEITE_SET.baseBlocks.base().getDefaultState()));
    static List<OreConfiguration.Target> goldList = List.of(
            OreConfiguration.createTarget(nurgonTest, OreRockSets.NURGON.gold_ore().getDefaultState()),
            OreConfiguration.createTarget(medgonTest, OreRockSets.MEDGON.gold_ore().getDefaultState()));
    static List<OreConfiguration.Target> emeraldList = List.of(
            OreConfiguration.createTarget(nurgonTest, OreRockSets.NURGON.emerald_ore().getDefaultState()),
            OreConfiguration.createTarget(medgonTest, OreRockSets.MEDGON.emerald_ore().getDefaultState()));
    static List<OreConfiguration.Target> rubyList = List.of(
            OreConfiguration.createTarget(nurgonTest, OreRockSets.NURGON.ruby_ore().getDefaultState()),
            OreConfiguration.createTarget(medgonTest, OreRockSets.MEDGON.ruby_ore().getDefaultState()));
    static List<OreConfiguration.Target> sapphireList = List.of(
            OreConfiguration.createTarget(nurgonTest, OreRockSets.NURGON.sapphire_ore().getDefaultState()),
            OreConfiguration.createTarget(medgonTest, OreRockSets.MEDGON.sapphire_ore().getDefaultState()));
    static List<OreConfiguration.Target> adamentList = List.of(OreConfiguration.createTarget(medgonTest, OreRockSets.MEDGON.adamant_ore().getDefaultState()));
    static List<OreConfiguration.Target> mithrilList = List.of(OreConfiguration.createTarget(medgonTest, OreRockSets.MEDGON.mithril_ore().getDefaultState()));
    // endregion

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> featureRegisterable) {
        HolderGetter<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(Registries.CONFIGURED_FEATURE);

        registerGeode(AMETHYST_GEODE, featureRegisterable, Blocks.AMETHYST_BLOCK, Blocks.BUDDING_AMETHYST, Blocks.SMALL_AMETHYST_BUD,
                Blocks.MEDIUM_AMETHYST_BUD, Blocks.LARGE_AMETHYST_BUD, Blocks.AMETHYST_CLUSTER, Blocks.CALCITE);
        registerGeode(CITRINE_GEODE, featureRegisterable, ModBlocks.CITRINE_BLOCK, ModBlocks.BUDDING_CITRINE, ModBlocks.SMALL_CITRINE_BUD,
                ModBlocks.MEDIUM_CITRINE_BUD, ModBlocks.LARGE_CITRINE_BUD, ModBlocks.CITRINE_CLUSTER, Blocks.CALCITE);
        registerGeode(GLOWSTONE_GEODE, featureRegisterable, ModBlocks.GLOWSTONE_BLOCK, ModBlocks.BUDDING_GLOWSTONE, ModBlocks.SMALL_GLOWSTONE_BUD,
                ModBlocks.MEDIUM_GLOWSTONE_BUD, ModBlocks.LARGE_GLOWSTONE_BUD, ModBlocks.GLOWSTONE_CLUSTER, Blocks.CALCITE);
        registerGeode(QUARTZ_GEODE, featureRegisterable, ModBlocks.QUARTZ_BLOCK, ModBlocks.BUDDING_QUARTZ, ModBlocks.SMALL_QUARTZ_BUD,
                ModBlocks.MEDIUM_QUARTZ_BUD, ModBlocks.LARGE_QUARTZ_BUD, ModBlocks.QUARTZ_CLUSTER, StoneBlockSets.QUARTZITE_SET.baseBlocks.base());
        registerGeode(RED_AGATE_GEODE, featureRegisterable, ModBlocks.RED_AGATE_BLOCK, ModBlocks.BUDDING_RED_AGATE, ModBlocks.SMALL_RED_AGATE_BUD,
                ModBlocks.MEDIUM_RED_AGATE_BUD, ModBlocks.LARGE_RED_AGATE_BUD, ModBlocks.RED_AGATE_CLUSTER, Blocks.CALCITE);

        FeatureUtils.register(featureRegisterable, ORE_DOLOMITE, Feature.ORE, new OreConfiguration(dolomiteReplaceTest, 64));
        FeatureUtils.register(featureRegisterable, ORE_OLD_DOLOMITE, Feature.ORE, new OreConfiguration(oldDolomiteReplaceTest, 42));
        FeatureUtils.register(featureRegisterable, ORE_MUD, Feature.ORE, new OreConfiguration(mudList, 41));
        FeatureUtils.register(featureRegisterable, POOL_MUD, Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.LUSH_GROUND_REPLACEABLE,
                BlockStateProvider.of(Blocks.MUD), PlacementUtils.createEntry(registryEntryLookup.getOrThrow(CaveFeatures.DRIPLEAF), new PlacementModifier[0]), CaveSurface.FLOOR,
                ConstantInt.create(3), 0.8f, 5, 0.1f, UniformInt.create(4, 7), 0.7f));
        FeatureUtils.register(featureRegisterable, ORE_MAGMA, Feature.ORE, new OreConfiguration(magmaList, 31, 0.4f));
        FeatureUtils.register(featureRegisterable, ORE_OBSIDIAN, Feature.ORE, new OreConfiguration(medgonTest, Blocks.OBSIDIAN.getDefaultState(), 27));
        FeatureUtils.register(featureRegisterable, DISK_MYCELIUM, ModFeatures.SURFACE_ORE, new SurfaceOreFeatureConfig(baseStone, ModBlocks.STONE_MYCELIUM.getDefaultState(), 56));
        FeatureUtils.register(featureRegisterable, POOL_MAGMA, Feature.WATERLOGGED_VEGETATION_PATCH, new VegetationPatchConfiguration(BlockTags.BASE_STONE_OVERWORLD,
                BlockStateProvider.of(Blocks.MAGMA_BLOCK), PlacementUtils.createEntry(registryEntryLookup.getOrThrow(CaveFeatures.DRIPLEAF), new PlacementModifier[0]), CaveSurface.FLOOR,
                ConstantInt.create(3), 0.8f, 5, 0.1f, UniformInt.create(4, 7), 0.7f));
        FeatureUtils.register(featureRegisterable, ORE_ASH, Feature.ORE, new OreConfiguration(ashList, 48, 0.2f));
        FeatureUtils.register(featureRegisterable, ORE_ASHEN_DIRT, Feature.ORE, new OreConfiguration(ashenDirtList, 33));
        FeatureUtils.register(featureRegisterable, ORE_DRY_DIRT, Feature.ORE, new OreConfiguration(dryDirtList, 37));
        FeatureUtils.register(featureRegisterable, ORE_PACKED_ICE, Feature.ORE, new OreConfiguration(packedIceList, 33));
        FeatureUtils.register(featureRegisterable, ORE_BLUE_ICE, Feature.ORE, new OreConfiguration(blueIceList, 33));
        FeatureUtils.register(featureRegisterable, ORE_GABBRO, Feature.ORE, new OreConfiguration(gabbroList, 64));
        FeatureUtils.register(featureRegisterable, ORE_GALONN, Feature.ORE, new OreConfiguration(galonnList, 64));
        FeatureUtils.register(featureRegisterable, ORE_OLD_GALONN, Feature.ORE, new OreConfiguration(oldGalonnList, 42));
        FeatureUtils.register(featureRegisterable, ORE_GNEISS, Feature.ORE, new OreConfiguration(gneissList, 64));
        FeatureUtils.register(featureRegisterable, ORE_GREEN_TUFF, Feature.ORE, new OreConfiguration(greenTuffList, 64));
        FeatureUtils.register(featureRegisterable, ORE_GILDED_GREEN_TUFF, Feature.ORE, new OreConfiguration(gildedGreenTuffList, 40));
        FeatureUtils.register(featureRegisterable, ORE_GOLDEN_GREEN_TUFF, ModFeatures.ORE, new ModOreFeatureConfig(goldenGreenTuffList, Blocks.RAW_GOLD_BLOCK.getDefaultState(), 42, 0.0f));
        FeatureUtils.register(featureRegisterable, ORE_IZHER_ABAN, Feature.ORE, new OreConfiguration(izherAbanList, 64));
        FeatureUtils.register(featureRegisterable, ORE_ZIGIL_ABAN, Feature.ORE, new OreConfiguration(zigilAbanList, 64));
        FeatureUtils.register(featureRegisterable, ORE_OLD_IZHER_ABAN, Feature.ORE, new OreConfiguration(oldIzherAbanList, 42));
        FeatureUtils.register(featureRegisterable, ORE_LIMESTONE, Feature.ORE, new OreConfiguration(limestoneList, 64));
        FeatureUtils.register(featureRegisterable, ORE_OLD_LIMESTONE, Feature.ORE, new OreConfiguration(oldLimestoneList, 42));
        FeatureUtils.register(featureRegisterable, ORE_SAND, Feature.ORE, new OreConfiguration(sandList, 48));
        FeatureUtils.register(featureRegisterable, ORE_SANDSTONE, Feature.ORE, new OreConfiguration(sandStoneList, 37));
        FeatureUtils.register(featureRegisterable, ORE_SCHIST, Feature.ORE, new OreConfiguration(schistList, 48));
        FeatureUtils.register(featureRegisterable, ORE_SNOW, Feature.ORE, new OreConfiguration(snowList, 48));
        FeatureUtils.register(featureRegisterable, ORE_TERRACOTTA, Feature.ORE, new OreConfiguration(terracottaList, 42));
        FeatureUtils.register(featureRegisterable, ORE_TUFF, Feature.ORE, new OreConfiguration(tuffList, 42));
        FeatureUtils.register(featureRegisterable, ORE_BASALT, Feature.ORE, new OreConfiguration(basaltList, 42));
        FeatureUtils.register(featureRegisterable, ORE_BLACKSTONE, Feature.ORE, new OreConfiguration(blackstoneList, 42));

        FeatureUtils.register(featureRegisterable, DOLOMITE_CLUSTER, ModFeatures.CLUSTER, new ClusterFeatureConfig(12,
                StoneBlockSets.DOLOMITE_SET.baseBlocks.base().getDefaultState(), ModBlocks.POINTED_DOLOMITE.getDefaultState(), UniformInt.create(3, 6),
                UniformInt.create(2, 8), 1, 3, UniformInt.create(2, 4), UniformFloat.create(0.3F, 0.7F),
                ClampedNormalFloat.create(0.1F, 0.3F, 0.1F, 0.9F), 0.1F, 3, 8));
        FeatureUtils.register(featureRegisterable, LARGE_DOLOMITE, ModFeatures.PILLAR, new PillarFeatureConfig(30, UniformInt.create(3, 19),
                UniformFloat.create(0.4f, 2.0f), 0.33f, UniformFloat.create(0.3f, 0.9f), UniformFloat.create(0.4f, 1.0f),
                UniformFloat.create(0.0f, 0.3f), 4, 0.6f, StoneBlockSets.DOLOMITE_SET.baseBlocks.base().getDefaultState()));
        FeatureUtils.register(featureRegisterable, POINTED_DOLOMITE, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.of(
                PlacementUtils.createEntry(ModFeatures.SMALL_POINTED_STONE,
                        new SmallPointedStoneFeatureConfig(0.2F, StoneBlockSets.DOLOMITE_SET.baseBlocks.base().getDefaultState(), ModBlocks.POINTED_DOLOMITE.getDefaultState(), 0.7F, 0.5F, 0.5F),
                        EnvironmentScanPlacement.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR_OR_WATER, 12),
                        RandomOffsetPlacement.vertically(ConstantInt.create(1))), PlacementUtils.createEntry(
                                ModFeatures.SMALL_POINTED_STONE,
                        new SmallPointedStoneFeatureConfig(0.2F, StoneBlockSets.DOLOMITE_SET.baseBlocks.base().getDefaultState(), ModBlocks.POINTED_DOLOMITE.getDefaultState(), 0.7F, 0.5F, 0.5F),
                        EnvironmentScanPlacement.of(Direction.UP, BlockPredicate.solid(), BlockPredicate.IS_AIR_OR_WATER, 12),
                        RandomOffsetPlacement.vertically(ConstantInt.create(-1))))));

        FeatureUtils.register(featureRegisterable, GALONN_CLUSTER, ModFeatures.CLUSTER, new ClusterFeatureConfig(12,
                StoneBlockSets.GALONN_SET.baseBlocks.base().getDefaultState(), ModBlocks.POINTED_GALONN.getDefaultState(), UniformInt.create(3, 6),
                UniformInt.create(2, 8), 1, 3, UniformInt.create(2, 4), UniformFloat.create(0.3F, 0.7F),
                ClampedNormalFloat.create(0.1F, 0.3F, 0.1F, 0.9F), 0.1F, 3, 8));
        FeatureUtils.register(featureRegisterable, LARGE_GALONN, ModFeatures.PILLAR, new PillarFeatureConfig(30, UniformInt.create(3, 19),
                UniformFloat.create(0.4f, 2.0f), 0.33f, UniformFloat.create(0.3f, 0.9f), UniformFloat.create(0.4f, 1.0f),
                UniformFloat.create(0.0f, 0.3f), 4, 0.6f, StoneBlockSets.GALONN_SET.baseBlocks.base().getDefaultState()));
        FeatureUtils.register(featureRegisterable, POINTED_GALONN, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.of(
                PlacementUtils.createEntry(ModFeatures.SMALL_POINTED_STONE,
                        new SmallPointedStoneFeatureConfig(0.2F, StoneBlockSets.GALONN_SET.baseBlocks.base().getDefaultState(), ModBlocks.POINTED_GALONN.getDefaultState(), 0.7F, 0.5F, 0.5F),
                        EnvironmentScanPlacement.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR_OR_WATER, 12),
                        RandomOffsetPlacement.vertically(ConstantInt.create(1))), PlacementUtils.createEntry(
                                ModFeatures.SMALL_POINTED_STONE,
                        new SmallPointedStoneFeatureConfig(0.2F, StoneBlockSets.GALONN_SET.baseBlocks.base().getDefaultState(), ModBlocks.POINTED_GALONN.getDefaultState(), 0.7F, 0.5F, 0.5F),
                        EnvironmentScanPlacement.of(Direction.UP, BlockPredicate.solid(), BlockPredicate.IS_AIR_OR_WATER, 12),
                        RandomOffsetPlacement.vertically(ConstantInt.create(-1))))));

        FeatureUtils.register(featureRegisterable, IZHER_ABAN_CLUSTER, ModFeatures.CLUSTER, new ClusterFeatureConfig(12,
                StoneBlockSets.IZHERABAN_SET.baseBlocks.base().getDefaultState(), ModBlocks.POINTED_IZHERABAN.getDefaultState(), UniformInt.create(3, 6),
                UniformInt.create(2, 8), 1, 3, UniformInt.create(2, 4), UniformFloat.create(0.3F, 0.7F),
                ClampedNormalFloat.create(0.1F, 0.3F, 0.1F, 0.9F), 0.1F, 3, 8));
        FeatureUtils.register(featureRegisterable, LARGE_IZHER_ABAN, ModFeatures.PILLAR, new PillarFeatureConfig(30, UniformInt.create(3, 19),
                UniformFloat.create(0.4f, 2.0f), 0.33f, UniformFloat.create(0.3f, 0.9f), UniformFloat.create(0.4f, 1.0f),
                UniformFloat.create(0.0f, 0.3f), 4, 0.6f, StoneBlockSets.IZHERABAN_SET.baseBlocks.base().getDefaultState()));
        FeatureUtils.register(featureRegisterable, POINTED_IZHER_ABAN, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.of(
                PlacementUtils.createEntry(ModFeatures.SMALL_POINTED_STONE,
                        new SmallPointedStoneFeatureConfig(0.2F, StoneBlockSets.IZHERABAN_SET.baseBlocks.base().getDefaultState(), ModBlocks.POINTED_IZHERABAN.getDefaultState(), 0.7F, 0.5F, 0.5F),
                        EnvironmentScanPlacement.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR_OR_WATER, 12),
                        RandomOffsetPlacement.vertically(ConstantInt.create(1))), PlacementUtils.createEntry(
                        ModFeatures.SMALL_POINTED_STONE,
                        new SmallPointedStoneFeatureConfig(0.2F, StoneBlockSets.IZHERABAN_SET.baseBlocks.base().getDefaultState(), ModBlocks.POINTED_IZHERABAN.getDefaultState(), 0.7F, 0.5F, 0.5F),
                        EnvironmentScanPlacement.of(Direction.UP, BlockPredicate.solid(), BlockPredicate.IS_AIR_OR_WATER, 12),
                        RandomOffsetPlacement.vertically(ConstantInt.create(-1))))));

        FeatureUtils.register(featureRegisterable, LIMESTONE_CLUSTER, ModFeatures.CLUSTER, new ClusterFeatureConfig(12,
                StoneBlockSets.LIMESTONE_SET.baseBlocks.base().getDefaultState(), ModBlocks.POINTED_LIMESTONE.getDefaultState(), UniformInt.create(3, 6),
                UniformInt.create(2, 8), 1, 3, UniformInt.create(2, 4), UniformFloat.create(0.3F, 0.7F),
                ClampedNormalFloat.create(0.1F, 0.3F, 0.1F, 0.9F), 0.1F, 3, 8));
        FeatureUtils.register(featureRegisterable, LARGE_LIMESTONE, ModFeatures.PILLAR, new PillarFeatureConfig(30, UniformInt.create(3, 19),
                UniformFloat.create(0.4f, 2.0f), 0.33f, UniformFloat.create(0.3f, 0.9f), UniformFloat.create(0.4f, 1.0f),
                UniformFloat.create(0.0f, 0.3f), 4, 0.6f, StoneBlockSets.LIMESTONE_SET.baseBlocks.base().getDefaultState()));
        FeatureUtils.register(featureRegisterable, POINTED_LIMESTONE, Feature.SIMPLE_RANDOM_SELECTOR, new SimpleRandomFeatureConfiguration(HolderSet.of(
                PlacementUtils.createEntry(ModFeatures.SMALL_POINTED_STONE,
                        new SmallPointedStoneFeatureConfig(0.2F, StoneBlockSets.LIMESTONE_SET.baseBlocks.base().getDefaultState(), ModBlocks.POINTED_LIMESTONE.getDefaultState(), 0.7F, 0.5F, 0.5F),
                        EnvironmentScanPlacement.of(Direction.DOWN, BlockPredicate.solid(), BlockPredicate.IS_AIR_OR_WATER, 12),
                        RandomOffsetPlacement.vertically(ConstantInt.create(1))), PlacementUtils.createEntry(
                                ModFeatures.SMALL_POINTED_STONE,
                        new SmallPointedStoneFeatureConfig(0.2F, StoneBlockSets.LIMESTONE_SET.baseBlocks.base().getDefaultState(), ModBlocks.POINTED_LIMESTONE.getDefaultState(), 0.7F, 0.5F, 0.5F),
                        EnvironmentScanPlacement.of(Direction.UP, BlockPredicate.solid(), BlockPredicate.IS_AIR_OR_WATER, 12),
                        RandomOffsetPlacement.vertically(ConstantInt.create(-1))))));

        FeatureUtils.register(featureRegisterable, PILLAR_BASALT, ModFeatures.PILLAR, new PillarFeatureConfig(30, UniformInt.create(3, 19),
                UniformFloat.create(0.4f, 2.0f), 0.33f, UniformFloat.create(0.3f, 0.9f), UniformFloat.create(0.4f, 1.0f),
                UniformFloat.create(0.0f, 0.3f), 4, 0.6f, Blocks.BASALT.getDefaultState()));
        FeatureUtils.register(featureRegisterable, PILLAR_BLACKSTONE, ModFeatures.PILLAR, new PillarFeatureConfig(30, UniformInt.create(3, 19),
                UniformFloat.create(0.4f, 2.0f), 0.33f, UniformFloat.create(0.3f, 0.9f), UniformFloat.create(0.4f, 1.0f),
                UniformFloat.create(0.0f, 0.3f), 4, 0.6f, Blocks.BLACKSTONE.getDefaultState()));
        FeatureUtils.register(featureRegisterable, PILLAR_PACKED_ICE, ModFeatures.PILLAR, new PillarFeatureConfig(30, UniformInt.create(3, 19),
                UniformFloat.create(0.4f, 2.0f), 0.33f, UniformFloat.create(0.3f, 0.9f), UniformFloat.create(0.4f, 1.0f),
                UniformFloat.create(0.0f, 0.3f), 4, 0.6f, Blocks.PACKED_ICE.getDefaultState()));
        FeatureUtils.register(featureRegisterable, PILLAR_SMOOTH_SANDSTONE, ModFeatures.PILLAR, new PillarFeatureConfig(30, UniformInt.create(3, 19),
                UniformFloat.create(0.4f, 2.0f), 0.33f, UniformFloat.create(0.3f, 0.9f), UniformFloat.create(0.4f, 1.0f),
                UniformFloat.create(0.0f, 0.3f), 4, 0.6f, Blocks.SMOOTH_SANDSTONE.getDefaultState()));

        FeatureUtils.register(featureRegisterable, ORE_COAL, Feature.ORE, new OreConfiguration(coalList, 17, 0.25f));
        FeatureUtils.register(featureRegisterable, ORE_COPPER, Feature.ORE, new OreConfiguration(copperList, 15, 0.25f));
        FeatureUtils.register(featureRegisterable, ORE_TIN, Feature.ORE, new OreConfiguration(tinList, 12, 0.25f));
        FeatureUtils.register(featureRegisterable, ORE_LAPIS, Feature.ORE, new OreConfiguration(lapisList, 6, 0.3f));
        FeatureUtils.register(featureRegisterable, ORE_LEAD, Feature.ORE, new OreConfiguration(leadList, 12, 0.3f));
        FeatureUtils.register(featureRegisterable, ORE_QUARTZITE, Feature.ORE, new OreConfiguration(quartziteList, 21, 0.4f));
        FeatureUtils.register(featureRegisterable, ORE_IRON, Feature.ORE, new OreConfiguration(ironList, 10, 0.3f));
        FeatureUtils.register(featureRegisterable, ORE_SILVER, Feature.ORE, new OreConfiguration(silverList, 7, 0.5f));
        FeatureUtils.register(featureRegisterable, ORE_JADE, Feature.ORE, new OreConfiguration(jadeList, 16, 0.5f));
        FeatureUtils.register(featureRegisterable, ORE_GOLD, Feature.ORE, new OreConfiguration(goldList, 7, 0.42f));
        FeatureUtils.register(featureRegisterable, ORE_GOLD_GREEN_TUFF, Feature.SCATTERED_ORE, new OreConfiguration(greenTuffTest, Blocks.RAW_GOLD_BLOCK.getDefaultState(), 1, 1.0f));
        FeatureUtils.register(featureRegisterable, ORE_EMERALD, Feature.SCATTERED_ORE, new OreConfiguration(emeraldList, 5, 0.68f));
        FeatureUtils.register(featureRegisterable, ORE_RUBY, Feature.SCATTERED_ORE, new OreConfiguration(rubyList, 5, 0.68f));
        FeatureUtils.register(featureRegisterable, ORE_SAPPHIRE, Feature.SCATTERED_ORE, new OreConfiguration(sapphireList, 5, 0.68f));
        FeatureUtils.register(featureRegisterable, ORE_ADAMANT, Feature.SCATTERED_ORE, new OreConfiguration(adamentList, 3, 0.72f));
        FeatureUtils.register(featureRegisterable, ORE_MITHRIL, Feature.SCATTERED_ORE, new OreConfiguration(mithrilList, 1, 1.0f));

        FeatureUtils.register(featureRegisterable, PATCH_CAVE_AMANITA, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.CAVE_AMANITA))));
        FeatureUtils.register(featureRegisterable, PATCH_CAVE_AMANITA_TILLER, Feature.FLOWER, new RandomPatchConfiguration(48, 6, 2,
                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(get4StagesBlockBuilder(ModNatureBlocks.CAVE_AMANITA_TILLER))))));

        FeatureUtils.register(featureRegisterable, PATCH_DEEP_FIRECAP, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.DEEP_FIRECAP))));
        FeatureUtils.register(featureRegisterable, PATCH_DEEP_FIRECAP_TILLER, Feature.FLOWER, new RandomPatchConfiguration(48, 6, 2,
                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(get4StagesBlockBuilder(ModNatureBlocks.DEEP_FIRECAP_TILLER))))));

        FeatureUtils.register(featureRegisterable, PATCH_GHOSTSHROOM, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.GHOSTSHROOM))));
        FeatureUtils.register(featureRegisterable, PATCH_GHOSTSHROOM_TILLER, Feature.FLOWER, new RandomPatchConfiguration(48, 6, 2,
                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(get4StagesBlockBuilder(ModNatureBlocks.GHOSTSHROOM_TILLER))))));

        FeatureUtils.register(featureRegisterable, PATCH_SKY_FIRECAP, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.SKY_FIRECAP))));
        FeatureUtils.register(featureRegisterable, PATCH_SKY_FIRECAP_TILLER, Feature.FLOWER, new RandomPatchConfiguration(48, 6, 2,
                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(get4StagesBlockBuilder(ModNatureBlocks.SKY_FIRECAP_TILLER))))));

        FeatureUtils.register(featureRegisterable, PATCH_TUBESHROOMS, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.TUBESHRROM))));
        FeatureUtils.register(featureRegisterable, PATCH_TALL_TUBESHROOMS, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.TALL_TUBESHROOM))));

        FeatureUtils.register(featureRegisterable, PATCH_TRUMPET_SHROOM, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.TRUMPET_SHROOM))));
        FeatureUtils.register(featureRegisterable, PATCH_TALL_TRUMPET_SHROOM, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.TALL_TRUMPET_SHROOM))));

        FeatureUtils.register(featureRegisterable, PATCH_VIOLET_CAPS, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.VIOLET_CAPS))));
        FeatureUtils.register(featureRegisterable, PATCH_VIOLET_CAPS_TILLER, Feature.FLOWER, new RandomPatchConfiguration(48, 6, 2,
                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(get4StagesBlockBuilder(ModNatureBlocks.VIOLET_CAPS_TILLER))))));

        FeatureUtils.register(featureRegisterable, PATCH_YELLOW_AMANITA, Feature.RANDOM_PATCH,
                FeatureUtils.createRandomPatchFeatureConfig(Feature.SIMPLE_BLOCK,
                        new SimpleBlockConfiguration(BlockStateProvider.of(ModNatureBlocks.YELLOW_AMANITA))));
        FeatureUtils.register(featureRegisterable, PATCH_YELLOW_AMANITA_TILLER, Feature.FLOWER, new RandomPatchConfiguration(48, 6, 2,
                PlacementUtils.createEntry(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(new WeightedStateProvider(get4StagesBlockBuilder(ModNatureBlocks.YELLOW_AMANITA_TILLER))))));

        FeatureUtils.register(featureRegisterable, GLOWWORM_WEBBING, Feature.BLOCK_COLUMN,
                new BlockColumnConfiguration(List.of(
                        BlockColumnConfiguration.createLayer(UniformInt.create(2, 8), BlockStateProvider.of(ModNatureBlocks.GLOWWORM_MAIN)),
                        BlockColumnConfiguration.createLayer(ConstantInt.create(1), BlockStateProvider.of(ModNatureBlocks.GLOWWORM_WEBBING))),
                        Direction.DOWN, BlockPredicate.IS_AIR, true));

        FeatureUtils.register(featureRegisterable, DROOPING_ICICLES, Feature.BLOCK_COLUMN,
                new BlockColumnConfiguration(List.of(
                        BlockColumnConfiguration.createLayer(UniformInt.create(1, 5), BlockStateProvider.of(ModNatureBlocks.DROOPING_ICICLES.getDefaultState().with(DroopingIciclesBlock.HALF, DoubleBlockHalf.UPPER))),
                        BlockColumnConfiguration.createLayer(ConstantInt.create(1), BlockStateProvider.of(ModNatureBlocks.DROOPING_ICICLES.getDefaultState().with(DroopingIciclesBlock.HALF, DoubleBlockHalf.LOWER)))),
                        Direction.DOWN, BlockPredicate.IS_AIR, true));
        FeatureUtils.register(featureRegisterable, SHORT_ICICLES, Feature.BLOCK_COLUMN,
                new BlockColumnConfiguration(List.of(
                        BlockColumnConfiguration.createLayer(ConstantInt.create(1), BlockStateProvider.of(ModNatureBlocks.SHORT_ICICLES))),
                        Direction.DOWN, BlockPredicate.IS_AIR, true));

        HolderSet<Block> stickyBlocks = HolderSet.of(Block::getRegistryEntry, Blocks.STONE, Blocks.SNOW, Blocks.ANDESITE, Blocks.DIORITE, Blocks.GRANITE, Blocks.DRIPSTONE_BLOCK, Blocks.CALCITE,
                Blocks.TUFF, Blocks.DEEPSLATE, Blocks.ICE, Blocks.BLUE_ICE);
        FeatureUtils.register(featureRegisterable, STICKY_ICE, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceBlock)ModNatureBlocks.STICKY_ICE, 20, false, true, true, 0.5F, stickyBlocks));
        FeatureUtils.register(featureRegisterable, STICKY_SNOW, Feature.MULTIFACE_GROWTH,
                new MultifaceGrowthConfiguration((MultifaceBlock)ModNatureBlocks.STICKY_SNOW, 20, false, true, true, 0.5F, stickyBlocks));

        FeatureUtils.register(featureRegisterable, SPRING_LAVA, Feature.SPRING_FEATURE, new SpringConfiguration(Fluids.LAVA.getDefaultState(),
                true, 4, 1, HolderSet.of(Block::getRegistryEntry, StoneBlockSets.NURGON_SET.baseBlocks.base(), StoneBlockSets.MEDGON_SET.baseBlocks.base())));
    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.of(Registries.CONFIGURED_FEATURE, new ResourceLocation(MiddleEarth.MOD_ID, name));
    }

    private static void registerGeode(ResourceKey<ConfiguredFeature<?, ?>> registryKey, BootstrapContext<ConfiguredFeature<?, ?>> featureRegisterable, Block geodeBlock, Block budding, Block smallBud, Block mediumBud, Block largeBud, Block cluster, Block inner) {
        FeatureUtils.register(featureRegisterable, registryKey, Feature.GEODE, new GeodeConfiguration(
                new GeodeBlockSettings(BlockStateProvider.of(Blocks.AIR), BlockStateProvider.of(geodeBlock), BlockStateProvider.of(budding),
                        BlockStateProvider.of(inner), BlockStateProvider.of(Blocks.SMOOTH_BASALT), List.of(smallBud.getDefaultState(),
                        mediumBud.getDefaultState(), largeBud.getDefaultState(), cluster.getDefaultState()),
                        BlockTags.FEATURES_CANNOT_REPLACE, BlockTags.GEODE_INVALID_BLOCKS),
                new GeodeLayerSettings(1.7, 2.2, 3.2, 4.2),
                new GeodeCrackSettings(0.95, 2.0, 2), 0.35, 0.083,
                true, UniformInt.create(3, 4), UniformInt.create(2, 3), UniformInt.create(1, 2),
                -16, 16, 0.05, 1));
    }

    public static SimpleWeightedRandomList.Builder<BlockState> get4StagesBlockBuilder(Block tiller) {
        SimpleWeightedRandomList.Builder<BlockState> builder = SimpleWeightedRandomList.builder();
        for (int i = 1; i <= 4; ++i) {
            for (Direction direction : Direction.Type.HORIZONTAL) {
                builder.add((tiller.getDefaultState().with(PinkPetalsBlock.FLOWER_AMOUNT, i)).with(BlockStateProperties.HORIZONTAL_FACING, direction), 1);
            }
        }
        return builder;
    }
}
