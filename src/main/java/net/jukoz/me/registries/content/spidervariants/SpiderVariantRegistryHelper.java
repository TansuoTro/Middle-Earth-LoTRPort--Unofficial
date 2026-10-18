package net.jukoz.me.registries.content.spidervariants;

import net.jukoz.me.compat.spawn.BiomeSpawnCondition;
import net.jukoz.me.compat.spawn.SpawnConditionSelectors;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
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

    public static SpawnConditionSelectors createSpawnConditions(Registerable<SpiderVariant> registry, TagKey<Biome> biomeTag, int priority) {
        return createSpawnConditions(registry.getRegistryLookup(RegistryKeys.BIOME).getOrThrow(biomeTag), priority);
    }

    public static SpawnConditionSelectors createSpawnConditions(RegistryEntryList<Biome> requiredBiomes, int priority) {
        return SpawnConditionSelectors.createSingle(new BiomeSpawnCondition(requiredBiomes), priority);
    }
}
