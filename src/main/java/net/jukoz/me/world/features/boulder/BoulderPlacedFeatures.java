package net.jukoz.me.world.features.boulder;

import net.jukoz.me.MiddleEarth;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.data.worldgen.BootstrapContext;

public class BoulderPlacedFeatures {
    public static final ResourceKey<PlacedFeature> ANDESITE_BOULDER = registerKey("andesite_boulder");
    public static final ResourceKey<PlacedFeature> CALCITE_BOULDER = registerKey("calcite_boulder");
    public static final ResourceKey<PlacedFeature> DIORITE_BOULDER = registerKey("diorite_boulder");
    public static final ResourceKey<PlacedFeature> GRANITE_BOULDER = registerKey("granite_boulder");
    public static final ResourceKey<PlacedFeature> LIMESTONE_BOULDER = registerKey("limestone_boulder");
    public static final ResourceKey<PlacedFeature> MIRKWOOD_ROOTS_BOULDER = registerKey("mirkwood_roots_boulder");
    public static final ResourceKey<PlacedFeature> SANDSTONE_BOULDER = registerKey("sandstone_boulder");
    public static final ResourceKey<PlacedFeature> STONE_BOULDER = registerKey("stone_boulder");
    public static final ResourceKey<PlacedFeature> MOSSY_BOULDER = registerKey("mossy_boulder");

    public static final ResourceKey<PlacedFeature> SMALL_BOULDER_ANDESITE   = registerKey("small_boulder_andesite");
    public static final ResourceKey<PlacedFeature> MEDIUM_BOULDER_ANDESITE  = registerKey("medium_boulder_andesite");
    public static final ResourceKey<PlacedFeature> BIG_BOULDER_ANDESITE     = registerKey("big_boulder_andesite");

    public static final ResourceKey<PlacedFeature> SMALL_BOULDER_ASHEN_STONE    = registerKey("small_boulder_ashen_stone");
    public static final ResourceKey<PlacedFeature> MEDIUM_BOULDER_ASHEN_STONE   = registerKey("medium_boulder_ashen_stone");
    public static final ResourceKey<PlacedFeature> BIG_BOULDER_ASHEN_STONE      = registerKey("big_boulder_ashen_stone");

    public static final ResourceKey<PlacedFeature> SMALL_BOULDER_BASALT     = registerKey("small_boulder_basalt");
    public static final ResourceKey<PlacedFeature> MEDIUM_BOULDER_BASALT    = registerKey("medium_boulder_basalt");
    public static final ResourceKey<PlacedFeature> BIG_BOULDER_BASALT       = registerKey("big_boulder_basalt");

    public static final ResourceKey<PlacedFeature> SMALL_BOULDER_BLUE_TUFF  = registerKey("small_boulder_blue_tuff");
    public static final ResourceKey<PlacedFeature> MEDIUM_BOULDER_BLUE_TUFF = registerKey("medium_boulder_blue_tuff");
    public static final ResourceKey<PlacedFeature> BIG_BOULDER_BLUE_TUFF    = registerKey("big_boulder_blue_tuff");

    public static final ResourceKey<PlacedFeature> SMALL_BOULDER_CALCITE    = registerKey("small_boulder_calcite");
    public static final ResourceKey<PlacedFeature> MEDIUM_BOULDER_CALCITE   = registerKey("medium_boulder_calcite");
    public static final ResourceKey<PlacedFeature> BIG_BOULDER_CALCITE      = registerKey("big_boulder_calcite");

    public static final ResourceKey<PlacedFeature> SMALL_BOULDER_DIORITE    = registerKey("small_boulder_diorite");
    public static final ResourceKey<PlacedFeature> MEDIUM_BOULDER_DIORITE   = registerKey("medium_boulder_diorite");
    public static final ResourceKey<PlacedFeature> BIG_BOULDER_DIORITE      = registerKey("big_boulder_diorite");

    public static final ResourceKey<PlacedFeature> SMALL_BOULDER_DOLOMITE   = registerKey("small_boulder_dolomite");
    public static final ResourceKey<PlacedFeature> MEDIUM_BOULDER_DOLOMITE  = registerKey("medium_boulder_dolomite");
    public static final ResourceKey<PlacedFeature> BIG_BOULDER_DOLOMITE     = registerKey("big_boulder_dolomite");

    public static final ResourceKey<PlacedFeature> SMALL_BOULDER_SMOOTH_DOLOMITE   = registerKey("small_boulder_smooth_dolomite");
    public static final ResourceKey<PlacedFeature> MEDIUM_BOULDER_SMOOTH_DOLOMITE  = registerKey("medium_boulder_smooth_dolomite");
    public static final ResourceKey<PlacedFeature> BIG_BOULDER_SMOOTH_DOLOMITE     = registerKey("big_boulder_smooth_dolomite");

    public static final ResourceKey<PlacedFeature> SMALL_BOULDER_GNEISS     = registerKey("small_boulder_gneiss");
    public static final ResourceKey<PlacedFeature> MEDIUM_BOULDER_GNEISS    = registerKey("medium_boulder_gneiss");
    public static final ResourceKey<PlacedFeature> BIG_BOULDER_GNEISS       = registerKey("big_boulder_gneiss");

    public static final ResourceKey<PlacedFeature> SMALL_BOULDER_GALONN   = registerKey("small_boulder_gallon");
    public static final ResourceKey<PlacedFeature> MEDIUM_BOULDER_GALONN  = registerKey("medium_boulder_gallon");
    public static final ResourceKey<PlacedFeature> BIG_BOULDER_GALONN     = registerKey("big_boulder_gallon");

    public static final ResourceKey<PlacedFeature> SMALL_BOULDER_GRANITE    = registerKey("small_boulder_granite");
    public static final ResourceKey<PlacedFeature> MEDIUM_BOULDER_GRANITE   = registerKey("medium_boulder_granite");
    public static final ResourceKey<PlacedFeature> BIG_BOULDER_GRANITE      = registerKey("big_boulder_granite");

    public static final ResourceKey<PlacedFeature> SMALL_BOULDER_HEMATITE   = registerKey("small_boulder_hematite");
    public static final ResourceKey<PlacedFeature> MEDIUM_BOULDER_HEMATITE  = registerKey("medium_boulder_hematite");
    public static final ResourceKey<PlacedFeature> BIG_BOULDER_HEMATITE     = registerKey("big_boulder_hematite");

    public static final ResourceKey<PlacedFeature> SMALL_BOULDER_IRONSTONE  = registerKey("small_boulder_ironstone");
    public static final ResourceKey<PlacedFeature> MEDIUM_BOULDER_IRONSTONE = registerKey("medium_boulder_ironstone");
    public static final ResourceKey<PlacedFeature> BIG_BOULDER_IRONSTONE    = registerKey("big_boulder_ironstone");

    public static final ResourceKey<PlacedFeature> SMALL_BOULDER_LIMESTONE  = registerKey("small_boulder_limestone");
    public static final ResourceKey<PlacedFeature> MEDIUM_BOULDER_LIMESTONE = registerKey("medium_boulder_limestone");
    public static final ResourceKey<PlacedFeature> BIG_BOULDER_LIMESTONE    = registerKey("big_boulder_limestone");

    public static final ResourceKey<PlacedFeature> SMALL_BOULDER_MOSSY_STONE    = registerKey("small_boulder_mossy_stone");
    public static final ResourceKey<PlacedFeature> MEDIUM_BOULDER_MOSSY_STONE   = registerKey("medium_boulder_mossy_stone");
    public static final ResourceKey<PlacedFeature> BIG_BOULDER_MOSSY_STONE      = registerKey("big_boulder_mossy_stone");

    public static final ResourceKey<PlacedFeature> SMALL_BOULDER_SANDSTONE  = registerKey("small_boulder_sandstone");
    public static final ResourceKey<PlacedFeature> MEDIUM_BOULDER_SANDSTONE = registerKey("medium_boulder_sandstone");
    public static final ResourceKey<PlacedFeature> BIG_BOULDER_SANDSTONE    = registerKey("big_boulder_mossy_sandstone");

    public static final ResourceKey<PlacedFeature> SMALL_BOULDER_STONE  = registerKey("small_boulder_stone");
    public static final ResourceKey<PlacedFeature> MEDIUM_BOULDER_STONE = registerKey("medium_boulder_stone");
    public static final ResourceKey<PlacedFeature> BIG_BOULDER_STONE    = registerKey("big_boulder_stone");

    public static final ResourceKey<PlacedFeature> SMALL_BOULDER_SLATE  = registerKey("small_boulder_slate");

    public static final ResourceKey<PlacedFeature> SMALL_BOULDER_GABBRO  = registerKey("small_boulder_gabbro");
    public static final ResourceKey<PlacedFeature> MEDIUM_BOULDER_GABBRO = registerKey("medium_boulder_gabbro");
    public static final ResourceKey<PlacedFeature> BIG_BOULDER_GABBRO    = registerKey("big_boulder_gabbro");

    public static void bootstrap(BootstrapContext<PlacedFeature> featureRegisterable) {
        HolderGetter<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(Registries.CONFIGURED_FEATURE);
        Holder.Reference<ConfiguredFeature<?, ?>> andesite = registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.ANDESITE_BOULDER);
        Holder.Reference<ConfiguredFeature<?, ?>> calcite = registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.CALCITE_BOULDER);
        Holder.Reference<ConfiguredFeature<?, ?>> diorite = registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.DIORITE_BOULDER);
        Holder.Reference<ConfiguredFeature<?, ?>> granite = registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.GRANITE_BOULDER);
        Holder.Reference<ConfiguredFeature<?, ?>> limestone = registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.LIMESTONE_BOULDER);
        Holder.Reference<ConfiguredFeature<?, ?>> mirkwoodRoots = registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MIRKWOOD_ROOTS_BOULDER);
        Holder.Reference<ConfiguredFeature<?, ?>> sandStone = registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.STONE_BOULDER);
        Holder.Reference<ConfiguredFeature<?, ?>> stone = registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.STONE_BOULDER);
        Holder.Reference<ConfiguredFeature<?, ?>> mossy = registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MOSSY_BOULDER);

        PlacementModifier common = PlacedFeature.createCountExtraModifier(0, 0.5f, 1);
        PlacementModifier uncommon = PlacedFeature.createCountExtraModifier(0, 0.25f, 1);
        PlacementModifier sparse = PlacedFeature.createCountExtraModifier(0, 0.025f, 1);
        PlacementModifier scarce = PlacedFeature.createCountExtraModifier(0, 0.02f, 1);
        PlacementModifier rare = PlacedFeature.createCountExtraModifier(0, 0.01f, 1);
        PlacementModifier veryRare = PlacedFeature.createCountExtraModifier(0, 0.005f, 1);

        PlacedFeature.register(featureRegisterable, ANDESITE_BOULDER, andesite, uncommon,
                InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, CALCITE_BOULDER, calcite, uncommon,
                InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, DIORITE_BOULDER, diorite, uncommon,
                InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, GRANITE_BOULDER, granite, uncommon,
                InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, LIMESTONE_BOULDER, limestone, uncommon,
                InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, MIRKWOOD_ROOTS_BOULDER, mirkwoodRoots, common,
                InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, STONE_BOULDER, stone, uncommon,
                InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, SANDSTONE_BOULDER, sandStone, uncommon,
                InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, MOSSY_BOULDER, mossy, uncommon,
                InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());

        PlacedFeature.register(featureRegisterable, SMALL_BOULDER_ANDESITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_ANDESITE), 
                sparse, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, MEDIUM_BOULDER_ANDESITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_ANDESITE),
                rare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, BIG_BOULDER_ANDESITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_ANDESITE),
                veryRare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, SMALL_BOULDER_ASHEN_STONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_ASHEN_STONE),
                sparse, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, MEDIUM_BOULDER_ASHEN_STONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_ASHEN_STONE),
                rare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, BIG_BOULDER_ASHEN_STONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_ASHEN_STONE),
                veryRare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, SMALL_BOULDER_BASALT, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_BASALT),
                sparse, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, MEDIUM_BOULDER_BASALT, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_BASALT),
                rare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, BIG_BOULDER_BASALT, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_BASALT),
                veryRare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, SMALL_BOULDER_BLUE_TUFF, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_BLUE_TUFF),
                uncommon, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, MEDIUM_BOULDER_BLUE_TUFF, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_BLUE_TUFF),
                scarce, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, BIG_BOULDER_BLUE_TUFF, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_BLUE_TUFF),
                veryRare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, SMALL_BOULDER_CALCITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_CALCITE),
                scarce, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, MEDIUM_BOULDER_CALCITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_CALCITE),
                rare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, BIG_BOULDER_CALCITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_CALCITE),
                veryRare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, SMALL_BOULDER_DIORITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_DIORITE),
                sparse, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, MEDIUM_BOULDER_DIORITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_DIORITE),
                rare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, BIG_BOULDER_DIORITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_DIORITE),
                veryRare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, SMALL_BOULDER_DOLOMITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_DOLOMITE),
                sparse, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, MEDIUM_BOULDER_DOLOMITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_DOLOMITE),
                rare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, BIG_BOULDER_DOLOMITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_DOLOMITE),
                veryRare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, SMALL_BOULDER_SMOOTH_DOLOMITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_SMOOTH_DOLOMITE),
                sparse, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, MEDIUM_BOULDER_SMOOTH_DOLOMITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_SMOOTH_DOLOMITE),
                rare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, BIG_BOULDER_SMOOTH_DOLOMITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_SMOOTH_DOLOMITE),
                veryRare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, SMALL_BOULDER_GALONN, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_GALONN),
                sparse, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, MEDIUM_BOULDER_GALONN, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_GALONN),
                rare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, BIG_BOULDER_GALONN, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_GALONN),
                veryRare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, SMALL_BOULDER_GNEISS, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_GNEISS),
                sparse, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, MEDIUM_BOULDER_GNEISS, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_GNEISS),
                rare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, BIG_BOULDER_GNEISS, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_GNEISS),
                veryRare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, SMALL_BOULDER_GRANITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_GRANITE),
                sparse, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, MEDIUM_BOULDER_GRANITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_GRANITE),
                rare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, BIG_BOULDER_GRANITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_GRANITE),
                veryRare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, SMALL_BOULDER_HEMATITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_HEMATITE),
                sparse, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, MEDIUM_BOULDER_HEMATITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_HEMATITE),
                rare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, BIG_BOULDER_HEMATITE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_HEMATITE),
                veryRare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, SMALL_BOULDER_IRONSTONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_IRONSTONE),
                sparse, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, MEDIUM_BOULDER_IRONSTONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_IRONSTONE),
                rare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, BIG_BOULDER_IRONSTONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_IRONSTONE),
                veryRare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, SMALL_BOULDER_LIMESTONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_LIMESTONE),
                sparse, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, MEDIUM_BOULDER_LIMESTONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_LIMESTONE),
                rare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, BIG_BOULDER_LIMESTONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_LIMESTONE),
                veryRare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, SMALL_BOULDER_MOSSY_STONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_MOSSY_STONE),
                sparse, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, MEDIUM_BOULDER_MOSSY_STONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_MOSSY_STONE),
                rare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, BIG_BOULDER_MOSSY_STONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_MOSSY_STONE),
                veryRare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, SMALL_BOULDER_SANDSTONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_SANDSTONE),
                sparse, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, MEDIUM_BOULDER_SANDSTONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_SANDSTONE),
                rare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, BIG_BOULDER_SANDSTONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_SANDSTONE),
                veryRare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, SMALL_BOULDER_STONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_STONE),
                sparse, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, MEDIUM_BOULDER_STONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_STONE),
                rare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, BIG_BOULDER_STONE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_STONE),
                veryRare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, SMALL_BOULDER_SLATE, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_SLATE),
                uncommon, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, SMALL_BOULDER_GABBRO, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.SMALL_BOULDER_GABBRO),
                sparse, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, MEDIUM_BOULDER_GABBRO, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.MEDIUM_BOULDER_GABBRO),
                rare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, BIG_BOULDER_GABBRO, registryEntryLookup.getOrThrow(BoulderConfiguredFeatures.BIG_BOULDER_GABBRO),
                veryRare, InSquarePlacement.of(), Heightmap.Types.WORLD_SURFACE_WG, PlacementModifier.of());
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(MiddleEarth.MOD_ID, name));
    }
}
