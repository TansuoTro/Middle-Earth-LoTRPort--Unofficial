package net.jukoz.me.registries.content.greathornvariants;

import net.jukoz.me.compat.spawn.SpawnConditionSelectors;
import net.minecraft.tags.TagKey;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.great_horn.GreatHornVariant;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;

public class GreatHornVariantRegistry {
    private static final ResourceKey<Registry<GreatHornVariant>> GREAT_HORN_VARIANTS_KEY = DynamicRegistriesME.GREAT_HORN_VARIANTS;

    public static final ResourceKey<GreatHornVariant> BROWN = DynamicRegistriesME.of(GREAT_HORN_VARIANTS_KEY, MiddleEarth.of("brown"));
    public static final ResourceKey<GreatHornVariant> TEMPERATE = DynamicRegistriesME.of(GREAT_HORN_VARIANTS_KEY, MiddleEarth.of("temperate"));
    public static final ResourceKey<GreatHornVariant> WARM = DynamicRegistriesME.of(GREAT_HORN_VARIANTS_KEY, MiddleEarth.of("warm"));
    public static final ResourceKey<GreatHornVariant> COLD = DynamicRegistriesME.of(GREAT_HORN_VARIANTS_KEY, MiddleEarth.of("cold"));
    public static final ResourceKey<GreatHornVariant> DEFAULT = BROWN;

    public static void bootstrap(BootstapContext<GreatHornVariant> context) {
        HolderGetter<GreatHornVariant> registryEntryLookup = context.getRegistryLookup(GREAT_HORN_VARIANTS_KEY);

        register(context, registryEntryLookup, BROWN, new GreatHornVariant(
                GreatHornVariantRegistryHelper.createAssetInfos("brown"),
                SpawnConditionSelectors.createFallback(0)));

        register(context, registryEntryLookup, TEMPERATE, new GreatHornVariant(
                GreatHornVariantRegistryHelper.createAssetInfos("temperate"),
                SpawnConditionSelectors.createFallback(0)));

        register(context, registryEntryLookup, WARM, new GreatHornVariant(
                GreatHornVariantRegistryHelper.createAssetInfos("warm"),
                GreatHornVariantRegistryHelper.createSpawnConditions(context, TagKey.create(Registries.BIOME, MiddleEarth.of("spawns_warm_variant_great_horn")), 0)));

        register(context, registryEntryLookup, COLD, new GreatHornVariant(
                GreatHornVariantRegistryHelper.createAssetInfos("cold"),
                GreatHornVariantRegistryHelper.createSpawnConditions(context, TagKey.create(Registries.BIOME, MiddleEarth.of("spawns_cold_variant_great_horn")), 1)));
    }

    private static void register(BootstapContext<GreatHornVariant> context, HolderGetter<GreatHornVariant> registryEntryLookup, ResourceKey<GreatHornVariant> registryKey, GreatHornVariant element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        // [LANG datagen]
        // None
    }
}
