package net.jukoz.me.registries.content.spidervariants;

import net.jukoz.me.compat.spawn.SpawnConditionSelectors;
import net.minecraft.tags.TagKey;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.spider.SpiderVariant;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;

public class SpiderVariantRegistry {
    private static final ResourceKey<Registry<SpiderVariant>> SPIDER_VARIANTS_KEY = DynamicRegistriesME.SPIDER_VARIANTS;

    public static final ResourceKey<SpiderVariant> MIRKWOOD = DynamicRegistriesME.of(SPIDER_VARIANTS_KEY, MiddleEarth.of("mirkwood"));
    public static final ResourceKey<SpiderVariant> CAVE = DynamicRegistriesME.of(SPIDER_VARIANTS_KEY, MiddleEarth.of("cave"));
    public static final ResourceKey<SpiderVariant> MORDOR = DynamicRegistriesME.of(SPIDER_VARIANTS_KEY, MiddleEarth.of("mordor"));
    public static final ResourceKey<SpiderVariant> DEFAULT = MIRKWOOD;

    public static void bootstrap(BootstapContext<SpiderVariant> context) {
        HolderGetter<SpiderVariant> registryEntryLookup = context.getRegistryLookup(SPIDER_VARIANTS_KEY);

        register(context, registryEntryLookup, MIRKWOOD, new SpiderVariant(
            SpiderVariantRegistryHelper.createAssetInfos("mirkwood"),
            SpawnConditionSelectors.createFallback(0)));
        register(context, registryEntryLookup, CAVE, new SpiderVariant(
            SpiderVariantRegistryHelper.createAssetInfos("blind"),
            SpiderVariantRegistryHelper.createSpawnConditions(context, TagKey.of(Registries.BIOME, MiddleEarth.of("is_cave")), 1)));
        register(context, registryEntryLookup, MORDOR, new SpiderVariant(
            SpiderVariantRegistryHelper.createAssetInfos("mordor"),
            SpiderVariantRegistryHelper.createSpawnConditions(context, TagKey.of(Registries.BIOME, MiddleEarth.of("is_mordor")), 2)));
    }

    private static void register(BootstapContext<SpiderVariant> context, HolderGetter<SpiderVariant> registryEntryLookup, ResourceKey<SpiderVariant> registryKey, SpiderVariant element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        // [LANG datagen]
        // None
    }

}
