package net.jukoz.me.registries.content.spidervariants;

import net.jukoz.me.compat.spawn.BiomeSpawnCondition;
import net.jukoz.me.compat.spawn.SpawnConditionSelectors;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.HolderList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.spider.SpiderVariant;

public class SpiderVariantRegistryHelper {
    private static final String TEXTURE_PATH = "entities/spiders/";
    private static final String ENTITY_NAME = "_shelobite_";


    public static SpiderVariant.SpiderAssetInfo createAssetInfos(String textureName){
        return new SpiderVariant.SpiderAssetInfo(
            new SpiderVariant.AssetInfo(MiddleEarth.of(TEXTURE_PATH + textureName + ENTITY_NAME + "larva")),
            new SpiderVariant.AssetInfo(MiddleEarth.of(TEXTURE_PATH + textureName + ENTITY_NAME + "scuttler")),
            new SpiderVariant.AssetInfo(MiddleEarth.of(TEXTURE_PATH + textureName + "_spawn_of_shelob")));
    }

    public static SpawnConditionSelectors createSpawnConditions(BootstrapContext<SpiderVariant> registry, TagKey<Biome> biomeTag, int priority) {
        return createSpawnConditions(registry.getRegistryLookup(Registries.BIOME).getOrThrow(biomeTag), priority);
    }

    public static SpawnConditionSelectors createSpawnConditions(RegistryEntryList<Biome> requiredBiomes, int priority) {
        return SpawnConditionSelectors.createSingle(new BiomeSpawnCondition(requiredBiomes), priority);
    }
}
