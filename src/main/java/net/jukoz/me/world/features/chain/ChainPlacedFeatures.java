package net.jukoz.me.world.features.chain;

import net.jukoz.me.MiddleEarth;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.PlacedFeature;
import net.minecraft.world.gen.feature.PlacedFeatures;

/**
 * The donor's {@code rope_ladder_down} template-pool feature element points at a
 * placed feature that was never generated. Register it so structure jigsaw
 * features resolve instead of leaving an unbound registry entry.
 */
public class ChainPlacedFeatures {
    public static final RegistryKey<PlacedFeature> ROPE_LADDER_DOWN = registerKey("rope_ladder_down");

    public static RegistryKey<PlacedFeature> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.PLACED_FEATURE, MiddleEarth.of(name));
    }

    public static void bootstrap(Registerable<PlacedFeature> featureRegisterable) {
        RegistryEntryLookup<ConfiguredFeature<?, ?>> configured = featureRegisterable.getRegistryLookup(RegistryKeys.CONFIGURED_FEATURE);
        PlacedFeatures.register(featureRegisterable, ROPE_LADDER_DOWN,
                configured.getOrThrow(ChainConfiguredFeatures.ROPE_LADDER_DOWN));
    }
}
