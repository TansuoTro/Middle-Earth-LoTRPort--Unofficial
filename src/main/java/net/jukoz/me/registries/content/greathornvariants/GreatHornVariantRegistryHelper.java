package net.jukoz.me.registries.content.greathornvariants;

import net.jukoz.me.compat.spawn.BiomeSpawnCondition;
import net.jukoz.me.compat.spawn.SpawnConditionSelectors;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.HolderSet;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.great_horn.GreatHornVariant;
import net.minecraft.core.HolderSet;

public class GreatHornVariantRegistryHelper {
    private static final String TEXTURE_PATH = "entities/great_horn/";
    private static final String ENTITY_NAME = "_great_horn";

    public static GreatHornVariant.GreatHornAssetInfo createAssetInfos(String textureName){
        return new GreatHornVariant.GreatHornAssetInfo(
                new GreatHornVariant.AssetInfo(MiddleEarth.of(TEXTURE_PATH + textureName + ENTITY_NAME)));
    }

    public static SpawnConditionSelectors createSpawnConditions(BootstrapContext<GreatHornVariant> registry, TagKey<Biome> biomeTag, int priority) {
        return createSpawnConditions(registry.getRegistryLookup(Registries.BIOME).getOrThrow(biomeTag), priority);
    }

    public static SpawnConditionSelectors createSpawnConditions(HolderSet<Biome> requiredBiomes, int priority) {
        return SpawnConditionSelectors.createSingle(new BiomeSpawnCondition(requiredBiomes), priority);
    }
}
