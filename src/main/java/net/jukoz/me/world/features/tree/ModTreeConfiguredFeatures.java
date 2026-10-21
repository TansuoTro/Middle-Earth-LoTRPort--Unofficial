package net.jukoz.me.world.features.tree;

import com.google.common.collect.ImmutableList;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlocks;
import net.jukoz.me.block.registration.ModNatureBlocks;
import net.jukoz.me.block.registration.WoodBlockSets;
import net.jukoz.me.world.features.tree.foliages.OvalFoliagePlacer;
import net.jukoz.me.world.features.tree.foliages.PalmFoliagePlacer;
import net.jukoz.me.world.features.tree.trunks.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.core.RegistryEntryLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.TreeFeatureConfig;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BushFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.PineFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.SpruceFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.LeaveVineDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;

public class ModTreeConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASPEN_TREE_KEY = registerKey("aspen_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BEECH_TREE_KEY = registerKey("beech_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BIRCH_TREE_KEY = registerKey("birch_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CHESTNUT_TREE_KEY = registerKey("chestnut_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DARK_OAK_TREE_KEY = registerKey("dark_oak_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_DARK_OAK_TREE_KEY = registerKey("mega_dark_oak_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_BIRCH_TREE_KEY = registerKey("mega_birch_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEADWOOD_TREE_KEY = registerKey("deadwood_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> FIR_TREE_KEY = registerKey("fir_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HOLLY_TREE_KEY = registerKey("holy_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> LARCH_TREE_KEY = registerKey("larch_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACK_LEBETHRON_TREE_KEY = registerKey("black_lebethron_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_LEBETHRON_TREE_KEY = registerKey("white_lebethron_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_MIRKWOOD_TREE_KEY = registerKey("small_mirkwood_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MIRKWOOD_TREE_KEY = registerKey("mirkwood_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEAD_MIRKWOOD_TREE_KEY = registerKey("dead_mirkwood_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_MIRKWOOD_TREE_KEY = registerKey("mega_mirkwood_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEAD_MEGA_MIRKWOOD_TREE_KEY = registerKey("dead_mega_mirkwood_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MALLORN_TREE_KEY = registerKey("mallorn_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SMALL_MALLORN_TREE_KEY = registerKey("small_mallorn_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MALLORN_BUSH_KEY = registerKey("mallorn_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MALLORN_FLOWERING_BUSH_KEY = registerKey("mallorn_flowering_bush");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_MALLORN_TREE_KEY = registerKey("mega_mallorn_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_MALLORN_STRUCTURE_TREE_KEY = registerKey("mega_mallorn_structure_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_TREE_KEY = registerKey("maple_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> YELLOW_MAPLE_TREE_KEY = registerKey("yellow_maple_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ORANGE_MAPLE_TREE_KEY = registerKey("orange_maple_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> RED_MAPLE_TREE_KEY = registerKey("red_maple_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILVER_MAPLE_TREE_KEY = registerKey("silver_maple_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILVER_YELLOW_MAPLE_TREE_KEY = registerKey("silver_yellow_maple_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILVER_ORANGE_MAPLE_TREE_KEY = registerKey("silver_orange_maple_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILVER_RED_MAPLE_TREE_KEY = registerKey("silver_red_maple_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OAK_BUSH_TREE_KEY = registerKey("silver_oak_bush_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OAK_TREE_KEY = registerKey("oak_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BEES_OAK_TREE_KEY = registerKey("bees_oak_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OAK_TREE_VINES_KEY = registerKey("oak_vines_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> OAK_SMALL_TREE_VINES_KEY = registerKey("oak_small_vines_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MEGA_OAK_TREE_KEY = registerKey("mega_oak_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALM_TREE_KEY = registerKey("palm_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_PALM_TREE_KEY = registerKey("white_palm_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PINE_TREE_KEY = registerKey("pine_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEAD_PINE_TREE_KEY = registerKey("dead_pine_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DRY_PINE_TREE_KEY = registerKey("dry_pine_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DRY_PINE_BUSH_TREE_KEY = registerKey("dry_pine_bush_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> BLACK_PINE_TREE_KEY = registerKey("black_pine_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> DEAD_BLACK_PINE_TREE_KEY = registerKey("dead_black_pine_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ROTTEN_TREE_KEY = registerKey("rotten_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SCORCHED_TREE_KEY = registerKey("scorched_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPRUCE_TREE_KEY = registerKey("spruce_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SPRUCE_BUSH_TREE_KEY = registerKey("spruce_bush_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_SPRUCE_TREE_KEY = registerKey("white_spruce_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WHITE_SPRUCE_BUSH_TREE_KEY = registerKey("white_spruce_bush_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILLOW_TREE_KEY = registerKey("willow_tree");

    public static void bootstrap(BootstrapContext<ConfiguredFeature<?, ?>> context) {
        HolderGetter<Block> registryEntryLookup = context.getRegistryLookup(Registries.BLOCK);
        final ArrayList<BlockState> emptyList = new ArrayList<>();
        final ArrayList<BlockState> mallornLeaves = new ArrayList<>(Arrays.asList(WoodBlockSets.MALLORN_SET.leaves.getDefaultState(), WoodBlockSets.MALLORN_SET.leaves.getDefaultState(), ModNatureBlocks.FLOWERING_MALLORN_LEAVES.getDefaultState()));
        final ArrayList<BlockState> hollyLeaves = new ArrayList<>(Arrays.asList(WoodBlockSets.HOLLY_SET.leaves.getDefaultState(), WoodBlockSets.HOLLY_SET.leaves.getDefaultState(), ModNatureBlocks.BERRY_HOLLY_LEAVES.getDefaultState()));

        register(context, ASPEN_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
                BlockStateProvider.of(WoodBlockSets.ASPEN_SET.logBlocks.log()),
                new CanopyTrunkPlacer(9, 2, 0.9f, 0.87f, 8.6f, 1, 0.4f, -0.15f, 0,1),
                BlockStateProvider.of(WoodBlockSets.ASPEN_SET.leaves),
                new OvalFoliagePlacer(3, ConstantInt.create(0), ConstantInt.create(2), emptyList,0.4f),
                new TwoLayersFeatureSize(1, 0, 2))
                .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        
        register(context, BEECH_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.BEECH_SET.logBlocks.log()),
            new CanopyTrunkPlacer(12, 2, 0.91f, 0.87f, 5.0f, 3, 0.42f, -0.1f, 1,1),
            BlockStateProvider.of(WoodBlockSets.BEECH_SET.leaves),
            new OvalFoliagePlacer(3, ConstantInt.create(0), ConstantInt.create(2), emptyList,0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, BIRCH_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(Blocks.BIRCH_LOG),
            new CanopyTrunkPlacer(15, 2, 0.95f, 0.9f, 4.3f, 3, 0.37f,  0.025f,1,1),
            BlockStateProvider.of(Blocks.BIRCH_LEAVES),
            new OvalFoliagePlacer(3, ConstantInt.create(0), ConstantInt.create(2), emptyList,0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, MEGA_BIRCH_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(Blocks.BIRCH_LOG),
            new CanopyTrunkPlacer(18, 3, 1.0f, 0.67f, 5.2f, 3, 0.44f, -0.05f, 2, 1),
            BlockStateProvider.of(Blocks.BIRCH_LEAVES),
            new OvalFoliagePlacer(2, ConstantInt.create(0), ConstantInt.create(3), emptyList,0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, CHESTNUT_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.CHESTNUT_SET.logBlocks.log()),
            new CanopyTrunkPlacer(7, 2, 0.9f, 0.87f, 8.6f, 2, 0.4f, -0.15f, 0,1),
            BlockStateProvider.of(WoodBlockSets.CHESTNUT_SET.leaves),
            new OvalFoliagePlacer(2, ConstantInt.create(0), ConstantInt.create(2), emptyList,0.14f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, DARK_OAK_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(Blocks.DARK_OAK_LOG),
            new CanopyTrunkPlacer(11, 2, 0.97f, 0.87f, 5.0f, 3, 0.42f, -0.1f, 1,1),
            BlockStateProvider.of(Blocks.DARK_OAK_LEAVES),
            new OvalFoliagePlacer(2, ConstantInt.create(0), ConstantInt.create(3), emptyList, 0.3f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, MEGA_DARK_OAK_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(Blocks.DARK_OAK_LOG),
            new CanopyTrunkPlacer(21, 3, 1.8f, 0.55f, 6.1f, 3, 0.44f, -0.15f, 2, 0),
            BlockStateProvider.of(Blocks.DARK_OAK_LEAVES),
            new OvalFoliagePlacer(2, ConstantInt.create(0), ConstantInt.create(3), emptyList,0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, DEADWOOD_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
                BlockStateProvider.of(WoodBlockSets.DEADWOOD_SET.logBlocks.wood()),
                new LargeTrunkPlacer(13, 2, 0.82f, 0.5f, 2.1f, 1, 0.31f),
                BlockStateProvider.of(Blocks.AIR),
                new OvalFoliagePlacer(1, ConstantInt.create(-1), ConstantInt.create(1), emptyList, 0.0f),
                new TwoLayersFeatureSize(1, 0, 2))
                .dirtProvider(BlockStateProvider.of(ModBlocks.FOUL_DIRT)).build());

        register(context, FIR_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.FIR_SET.logBlocks.log()),
            new StraightTrunkPlacer(11, 2 , 1),
            BlockStateProvider.of(WoodBlockSets.FIR_SET.leaves),
            new SpruceFoliagePlacer(ConstantInt.create(3), UniformInt.create(0, 2), UniformInt.create(2, 3)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, HOLLY_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.HOLLY_SET.logBlocks.log()),
            new CanopyTrunkPlacer(9, 2, 0.9f, 0.87f, 8.6f, 1, 0.4f, -0.15f, 0,1),
            BlockStateProvider.of(WoodBlockSets.HOLLY_SET.leaves),
            new OvalFoliagePlacer(3, ConstantInt.create(-1), ConstantInt.create(2), hollyLeaves, 0.17f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, LARCH_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.LARCH_SET.logBlocks.log()),
            new StraightTrunkPlacer(11, 2 , 1),
            BlockStateProvider.of(WoodBlockSets.LARCH_SET.leaves),
            new SpruceFoliagePlacer(ConstantInt.create(3), UniformInt.create(0, 2), UniformInt.create(2, 3)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, BLACK_LEBETHRON_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.BLACK_LEBETHRON_SET.logBlocks.log()),
            new CanopyTrunkPlacer(10, 2, 0.9f, 0.87f, 8.6f, 3, 0.42f, -0.15f, 0,1),
            BlockStateProvider.of(ModNatureBlocks.LEBETHRON_LEAVES),
            new OvalFoliagePlacer(2, ConstantInt.create(0), ConstantInt.create(2), emptyList, 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, WHITE_LEBETHRON_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.WHITE_LEBETHRON_SET.logBlocks.log()),
            new CanopyTrunkPlacer(10, 2, 0.9f, 0.87f, 8.6f, 3, 0.42f, -0.15f, 0,1),
            BlockStateProvider.of(ModNatureBlocks.LEBETHRON_LEAVES),
            new OvalFoliagePlacer(2, ConstantInt.create(0), ConstantInt.create(2), emptyList, 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, MALLORN_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.MALLORN_SET.logBlocks.log()),
            new CanopyTrunkPlacer(16, 2, 0.9f, 0.87f, 5.2f, 3, 0.45f, -0.15f, 0,1),
            BlockStateProvider.of(WoodBlockSets.MALLORN_SET.leaves),
            new OvalFoliagePlacer(2, ConstantInt.create(0), ConstantInt.create(3), emptyList,0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, SMALL_MALLORN_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.MALLORN_SET.logBlocks.log()),
            new CanopyTrunkPlacer(9, 2, 0.9f, 0.87f, 5.2f, 2, 0.45f, -0.15f, 0,1),
            BlockStateProvider.of(WoodBlockSets.MALLORN_SET.leaves),
            new OvalFoliagePlacer(2, ConstantInt.create(0), ConstantInt.create(3), emptyList,  0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, MEGA_MALLORN_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.MALLORN_SET.logBlocks.log()),
            new CanopyTrunkPlacer(34, 3, 1.6f, 0.56f, 8.3f, 4, 0.48f, 0f, 2,1),
            BlockStateProvider.of(WoodBlockSets.MALLORN_SET.leaves),
            new OvalFoliagePlacer(3, ConstantInt.create(-1), ConstantInt.create(4), emptyList,  0.7f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, MEGA_MALLORN_STRUCTURE_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.MALLORN_SET.logBlocks.log()),
            new CanopyTrunkStructurePlacer(35, 3, 1.6f, 0.56f, 8.3f, 4,
                    0.48f, 0f, 2,1, 0.39f),
            BlockStateProvider.of(WoodBlockSets.MALLORN_SET.leaves),
            new OvalFoliagePlacer(3, ConstantInt.create(-1), ConstantInt.create(4), emptyList,  0.7f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, MALLORN_BUSH_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.MALLORN_SET.logBlocks.log()),
            new StraightTrunkPlacer(1, 0, 0), BlockStateProvider.of(WoodBlockSets.MALLORN_SET.leaves),
            new BushFoliagePlacer(ConstantInt.create(2), ConstantInt.create(1), 2),
            new TwoLayersFeatureSize(0, 0, 0)).build());
        register(context, MALLORN_FLOWERING_BUSH_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.MALLORN_SET.logBlocks.log()),
            new StraightTrunkPlacer(1, 0, 0), BlockStateProvider.of(ModNatureBlocks.FLOWERING_MALLORN_LEAVES),
            new BushFoliagePlacer(ConstantInt.create(2), ConstantInt.create(1), 1),
            new TwoLayersFeatureSize(0, 0, 0)).build());

        CanopyTrunkPlacer mapleTrunk = new CanopyTrunkPlacer(11, 2, 0.91f, 0.87f,
                5.1f, 2, 0.37f, -0.1f, 1,1);
        OvalFoliagePlacer mapleFoliage = new OvalFoliagePlacer(2, ConstantInt.create(0), ConstantInt.create(3), emptyList, 0.3f);

        register(context, MAPLE_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.MAPLE_SET.logBlocks.log()),
            mapleTrunk,
            BlockStateProvider.of(WoodBlockSets.MAPLE_SET.leaves),
            mapleFoliage,
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, YELLOW_MAPLE_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.MAPLE_SET.logBlocks.log()),
            mapleTrunk,
            BlockStateProvider.of(ModNatureBlocks.YELLOW_MAPLE_LEAVES),
            mapleFoliage,
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, ORANGE_MAPLE_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.MAPLE_SET.logBlocks.log()),
            mapleTrunk,
            BlockStateProvider.of(ModNatureBlocks.ORANGE_MAPLE_LEAVES),
            mapleFoliage,
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, RED_MAPLE_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.MAPLE_SET.logBlocks.log()),
            mapleTrunk,
            BlockStateProvider.of(ModNatureBlocks.RED_MAPLE_LEAVES),
            mapleFoliage,
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, SILVER_MAPLE_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.SILVER_MAPLE_SET.logBlocks.log()),
            mapleTrunk,
            BlockStateProvider.of(WoodBlockSets.MAPLE_SET.leaves),
            mapleFoliage,
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, SILVER_YELLOW_MAPLE_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.SILVER_MAPLE_SET.logBlocks.log()),
            mapleTrunk,
            BlockStateProvider.of(ModNatureBlocks.YELLOW_MAPLE_LEAVES),
            mapleFoliage,
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, SILVER_ORANGE_MAPLE_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.SILVER_MAPLE_SET.logBlocks.log()),
            mapleTrunk,
            BlockStateProvider.of(ModNatureBlocks.ORANGE_MAPLE_LEAVES),
            mapleFoliage,
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, SILVER_RED_MAPLE_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.SILVER_MAPLE_SET.logBlocks.log()),
            mapleTrunk,
            BlockStateProvider.of(ModNatureBlocks.RED_MAPLE_LEAVES),
            mapleFoliage,
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, SMALL_MIRKWOOD_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.MIRKWOOD_SET.logBlocks.wood()),
            new CanopyTrunkPlacer(7, 2, 0.9f, 0.87f, 3.2f, 1, 0.28f, -0.15f, 0, 0),
            BlockStateProvider.of(WoodBlockSets.MIRKWOOD_SET.leaves),
            new OvalFoliagePlacer(2, ConstantInt.create(-1), ConstantInt.create(2), emptyList, 0.3f),
            new TwoLayersFeatureSize(1, 0, 2))
            .decorators(ImmutableList.of(new LeaveVineDecorator(0.1F)))
            .dirtProvider(BlockStateProvider.of(ModBlocks.LOAM)).build());
        register(context, MIRKWOOD_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.MIRKWOOD_SET.logBlocks.wood()),
            new LargeTrunkPlacer(14, 2, 1.1f, 0.55f, 3.2f, 2, 0.28f),
            BlockStateProvider.of(WoodBlockSets.MIRKWOOD_SET.leaves),
            new OvalFoliagePlacer(2, ConstantInt.create(-1), ConstantInt.create(3), emptyList, 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .decorators(ImmutableList.of(new LeaveVineDecorator(0.25F)))
            .dirtProvider(BlockStateProvider.of(ModBlocks.LOAM)).build());
        register(context, MEGA_MIRKWOOD_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.MIRKWOOD_SET.logBlocks.log()),
            new LargeTrunkPlacer(27, 3, 2.3f, 0.6f, 6.2f, 5, 0.25f),
            BlockStateProvider.of(WoodBlockSets.MIRKWOOD_SET.leaves),
            new OvalFoliagePlacer(3, ConstantInt.create(-1), ConstantInt.create(4), emptyList, 0.5f),
            new TwoLayersFeatureSize(1, 0, 2))
            .decorators(ImmutableList.of(new LeaveVineDecorator(0.25F)))
            .dirtProvider(BlockStateProvider.of(ModBlocks.LOAM)).build());

        register(context, DEAD_MIRKWOOD_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
                BlockStateProvider.of(WoodBlockSets.MIRKWOOD_SET.logBlocks.wood()),
                new LargeTrunkPlacer(14, 2, 1.1f, 0.55f, 3.2f, 2, 0.28f),
                BlockStateProvider.of(Blocks.AIR),
                new OvalFoliagePlacer(2, ConstantInt.create(-1), ConstantInt.create(3), emptyList, 0.4f),
                new TwoLayersFeatureSize(1, 0, 2))
                .dirtProvider(BlockStateProvider.of(ModBlocks.LOAM)).build());
        register(context, DEAD_MEGA_MIRKWOOD_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
                BlockStateProvider.of(WoodBlockSets.MIRKWOOD_SET.logBlocks.log()),
                new LargeTrunkPlacer(27, 3, 2.3f, 0.6f, 6.2f, 5, 0.25f),
                BlockStateProvider.of(Blocks.AIR),
                new OvalFoliagePlacer(3, ConstantInt.create(-1), ConstantInt.create(4), emptyList, 0.5f),
                new TwoLayersFeatureSize(1, 0, 2))
                .dirtProvider(BlockStateProvider.of(ModBlocks.LOAM)).build());

        register(context, OAK_BUSH_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(Blocks.OAK_LOG),
            new StraightTrunkPlacer(1, 0, 0), BlockStateProvider.of(Blocks.OAK_LEAVES),
            new BushFoliagePlacer(ConstantInt.create(2), ConstantInt.create(1), 2),
            new TwoLayersFeatureSize(0, 0, 0)).build());

        register(context, OAK_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(Blocks.OAK_LOG),
            new CanopyTrunkPlacer(12, 2, 0.91f, 0.87f, 5.0f, 3, 0.42f, -0.1f, 1,1),
            BlockStateProvider.of(Blocks.OAK_LEAVES),
            new OvalFoliagePlacer(2, ConstantInt.create(0), ConstantInt.create(3), emptyList, 0.3f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, BEES_OAK_TREE_KEY, Feature.TREE, oak().decorators(List.of(new BeehiveDecorator(0.25F))).build());
        register(context, OAK_TREE_VINES_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(Blocks.OAK_LOG),
            new CanopyTrunkPlacer(10, 2, 0.91f, 0.87f, 5.0f, 3, 0.42f, -0.1f, 1,1),
            BlockStateProvider.of(Blocks.OAK_LEAVES),
            new OvalFoliagePlacer(2, ConstantInt.create(0), ConstantInt.create(3), emptyList, 0.3f),
            new TwoLayersFeatureSize(1, 0, 2))
            .decorators(ImmutableList.of(new LeaveVineDecorator(0.25F)))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, OAK_SMALL_TREE_VINES_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(Blocks.OAK_LOG),
            new StraightTrunkPlacer(4, 2, 0),
            BlockStateProvider.of(Blocks.OAK_LEAVES),
            new BlobFoliagePlacer(ConstantInt.create(2), ConstantInt.create(0), 3),
            new TwoLayersFeatureSize(1, 0, 2))
            .decorators(ImmutableList.of(new LeaveVineDecorator(0.25F)))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, MEGA_OAK_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(Blocks.OAK_LOG),
            new CanopyTrunkPlacer(20, 3, 1.8f, 0.55f, 5.7f, 3, 0.38f, -0.15f, 2, 0),
            BlockStateProvider.of(Blocks.OAK_LEAVES),
            new OvalFoliagePlacer(2, ConstantInt.create(0), ConstantInt.create(3), emptyList, 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, PALM_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.PALM_SET.logBlocks.log()),
            new ArcTrunkPlacer(10, 2, 0.02f, 0.07f, 0),
            BlockStateProvider.of(WoodBlockSets.PALM_SET.leaves),
            new PalmFoliagePlacer(4, ConstantInt.create(0), ConstantInt.create(1), -0.3f, 0.3f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, WHITE_PALM_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.WHITE_PALM_SET.logBlocks.log()),
            new ArcTrunkPlacer(10, 2, 0.02f, 0.07f, 0),
            BlockStateProvider.of(WoodBlockSets.PALM_SET.leaves),
            new PalmFoliagePlacer(4, ConstantInt.create(0), ConstantInt.create(1), -0.3f, 0.3f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, PINE_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.PINE_SET.logBlocks.log()),
            new SpruceTrunkPlacer(14, 3),
            BlockStateProvider.of(WoodBlockSets.PINE_SET.leaves),
            new PineFoliagePlacer(ConstantInt.create(1), ConstantInt.create(1), UniformInt.create(3, 4)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, DEAD_PINE_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.PINE_SET.logBlocks.log()),
            new SpruceTrunkPlacer(14, 3),
            BlockStateProvider.of(Blocks.AIR),
            new PineFoliagePlacer(ConstantInt.create(1), ConstantInt.create(1), ConstantInt.create(1)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, DRY_PINE_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.PINE_SET.logBlocks.log()),
            new SpruceTrunkPlacer(14, 3),
            BlockStateProvider.of(ModNatureBlocks.DRY_PINE_LEAVES),
            new PineFoliagePlacer(ConstantInt.create(1), ConstantInt.create(1), UniformInt.create(2, 3)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, DRY_PINE_BUSH_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(ModNatureBlocks.PINE_BRANCHES),
            new StraightTrunkPlacer(1, 0, 0), BlockStateProvider.of(ModNatureBlocks.DRY_PINE_LEAVES),
            new BushFoliagePlacer(ConstantInt.create(2), ConstantInt.create(1), 2),
            new TwoLayersFeatureSize(0, 0, 0)).build());

        register(context, BLACK_PINE_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.BLACK_PINE_SET.logBlocks.log()),
            new SpruceTrunkPlacer(13, 3),
            BlockStateProvider.of(WoodBlockSets.BLACK_PINE_SET.leaves),
            new PineFoliagePlacer(ConstantInt.create(1), ConstantInt.create(1), UniformInt.create(3, 4)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, DEAD_BLACK_PINE_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.BLACK_PINE_SET.logBlocks.log()),
            new SpruceTrunkPlacer(13, 3),
            BlockStateProvider.of(Blocks.AIR),
            new PineFoliagePlacer(ConstantInt.create(1), ConstantInt.create(1), ConstantInt.create(1)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());

        register(context, ROTTEN_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.ROTTEN_SET.logBlocks.log()),
            new CanopyTrunkPlacer(10, 2, 0.92f, 0.87f, 5.5f, 2, 0.38f, -0.1f, 1,1),
            BlockStateProvider.of(Blocks.AIR),
            new PineFoliagePlacer(ConstantInt.create(1), ConstantInt.create(1), ConstantInt.create(1)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(ModBlocks.FOUL_DIRT)).build());

        register(context, SCORCHED_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.SCORCHED_SET.logBlocks.log()),
            new CanopyTrunkPlacer(10, 2, 0.92f, 0.87f, 5.5f, 3, 0.38f, -0.1f, 1,1),
            BlockStateProvider.of(Blocks.AIR),
            new PineFoliagePlacer(ConstantInt.create(1), ConstantInt.create(1), ConstantInt.create(1)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(ModBlocks.ASHEN_DIRT)).build());

        register(context, SPRUCE_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(Blocks.SPRUCE_LOG),
            new StraightTrunkPlacer(14, 2, 0),
            BlockStateProvider.of(Blocks.SPRUCE_LEAVES),
            new SpruceFoliagePlacer(ConstantInt.create(3), UniformInt.create(0, 1), ConstantInt.create(2)),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, SPRUCE_BUSH_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(Blocks.SPRUCE_LOG),
            new StraightTrunkPlacer(1, 0, 0), BlockStateProvider.of(Blocks.SPRUCE_LEAVES),
            new BushFoliagePlacer(ConstantInt.create(2), ConstantInt.create(1), 2),
            new TwoLayersFeatureSize(0, 0, 0)).build());

        register(context, WHITE_SPRUCE_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
                BlockStateProvider.of(WoodBlockSets.WHITE_SPRUCE_SET.logBlocks.log()),
                new StraightTrunkPlacer(14, 2, 0),
                BlockStateProvider.of(WoodBlockSets.WHITE_SPRUCE_SET.leaves),
                new SpruceFoliagePlacer(ConstantInt.create(3), UniformInt.create(0, 1), ConstantInt.create(2)),
                new TwoLayersFeatureSize(1, 0, 2))
                .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
        register(context, WHITE_SPRUCE_BUSH_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
                BlockStateProvider.of(WoodBlockSets.WHITE_SPRUCE_SET.logBlocks.log()),
                new StraightTrunkPlacer(1, 0, 0), BlockStateProvider.of(WoodBlockSets.WHITE_SPRUCE_SET.leaves),
                new BushFoliagePlacer(ConstantInt.create(2), ConstantInt.create(1), 2),
                new TwoLayersFeatureSize(0, 0, 0)).build());

        register(context, WILLOW_TREE_KEY, Feature.TREE, new TreeConfiguration.Builder(
            BlockStateProvider.of(WoodBlockSets.WILLOW_SET.logBlocks.log()),
            new LargeTrunkPlacer(13, 2, 1.2f, 0.67f, 6.0f, 3, 0.32f),
            BlockStateProvider.of(WoodBlockSets.WILLOW_SET.leaves),
            new OvalFoliagePlacer(2, ConstantInt.create(-1), ConstantInt.create(3), emptyList, 0.4f),
            new TwoLayersFeatureSize(1, 0, 2))
            .dirtProvider(BlockStateProvider.of(Blocks.GRASS_BLOCK)).build());
    }

    private static TreeConfiguration.Builder builder(Block log, Block leaves, int baseHeight, int firstRandomHeight, int secondRandomHeight, int radius) {
        return new TreeConfiguration.Builder(BlockStateProvider.of(log), new StraightTrunkPlacer(baseHeight, firstRandomHeight, secondRandomHeight), BlockStateProvider.of(leaves), new BlobFoliagePlacer(ConstantInt.create(radius), ConstantInt.create(0), 3), new TwoLayersFeatureSize(1, 0, 1));
    }

    private static TreeConfiguration.Builder oak() {
        return builder(Blocks.OAK_LOG, Blocks.OAK_LEAVES, 4, 2, 0, 2).ignoreVines();
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
