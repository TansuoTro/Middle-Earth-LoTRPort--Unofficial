package net.jukoz.me.world.features.misc;

import net.jukoz.me.MiddleEarth;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.core.RegistryEntryLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.NetherConfiguredFeatures;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.gen.placementmodifier.CountMultilayerPlacementModifier;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;

public class ModMiscPlacedFeatures {

    public static final ResourceKey<PlacedFeature> LAVA_MAGMA_POOL = registerKey("lava_magma_pool");
    public static final ResourceKey<PlacedFeature> SMALL_BASALT_COLUMNS = registerKey("small_basalt_columns");
    public static final ResourceKey<PlacedFeature> SMALL_PUMICE_COLUMNS = registerKey("small_pumice_columns");
    public static final ResourceKey<PlacedFeature> LARGE_PUMICE_COLUMNS = registerKey("large_pumice_columns");

    static PlacementModifier uncommon = PlacedFeature.createCountExtraModifier(1, 0.2f, 1);
    static PlacementModifier sparse = PlacedFeature.createCountExtraModifier(0, 0.5f, 1);

    public static void bootstrap(BootstrapContext<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> registryEntryLookup = featureRegisterable.getRegistryLookup(Registries.CONFIGURED_FEATURE);

        PlacedFeature.register(featureRegisterable, LAVA_MAGMA_POOL, registryEntryLookup.getOrThrow(ModMiscConfiguredFeatures.LAVA_MAGMA_POOL),
                PlacedFeature.createCountExtraModifier(0, 0.05f, 1), InSquarePlacement.of(), PlacedFeature.WORLD_SURFACE_WG_HEIGHTMAP, PlacementModifier.of());

        PlacedFeature.register(featureRegisterable, SMALL_BASALT_COLUMNS, registryEntryLookup.getOrThrow(NetherConfiguredFeatures.SMALL_BASALT_COLUMNS),
                CountMultilayerPlacementModifier.of(1), PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, SMALL_PUMICE_COLUMNS, registryEntryLookup.getOrThrow(ModMiscConfiguredFeatures.SMALL_PUMICE_COLUMNS),
                CountMultilayerPlacementModifier.of(2), PlacementModifier.of());
        PlacedFeature.register(featureRegisterable, LARGE_PUMICE_COLUMNS, registryEntryLookup.getOrThrow(ModMiscConfiguredFeatures.LARGE_PUMICE_COLUMNS),
                CountMultilayerPlacementModifier.of(1), PlacementModifier.of());
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.of(Registries.PLACED_FEATURE, new ResourceLocation(MiddleEarth.MOD_ID, name));
    }
}
