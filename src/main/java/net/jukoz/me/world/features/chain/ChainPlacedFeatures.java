package net.jukoz.me.world.features.chain;

import net.jukoz.me.MiddleEarth;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.core.HolderGetter;

/**
 * The donor's {@code rope_ladder_down} template-pool feature element points at a
 * placed feature that was never generated. Register it so structure jigsaw
 * features resolve instead of leaving an unbound registry entry.
 */
public class ChainPlacedFeatures {
    public static final ResourceKey<PlacedFeature> ROPE_LADDER_DOWN = registerKey("rope_ladder_down");

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, MiddleEarth.of(name));
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> featureRegisterable) {
        HolderGetter<ConfiguredFeature<?, ?>> configured = featureRegisterable.getRegistryLookup(Registries.CONFIGURED_FEATURE);
        PlacedFeature.register(featureRegisterable, ROPE_LADDER_DOWN,
                configured.getOrThrow(ChainConfiguredFeatures.ROPE_LADDER_DOWN));
    }
}
