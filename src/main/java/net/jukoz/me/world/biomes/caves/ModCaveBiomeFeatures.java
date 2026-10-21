package net.jukoz.me.world.biomes.caves;

import net.jukoz.me.entity.EntitiesME;
import net.jukoz.me.world.features.underground.CavesPlacedFeatures;
import net.minecraft.world.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;

public class ModCaveBiomeFeatures {

    public static void addAmethystGeode(BiomeGenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, CavesPlacedFeatures.AMETHYST_GEODE);
    }
    public static void addCitrineGeode(BiomeGenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, CavesPlacedFeatures.CITRINE_GEODE);
    }
    public static void addGlowstoneGeode(BiomeGenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, CavesPlacedFeatures.GLOWSTONE_GEODE);
    }
    public static void addRedAgateGeode(BiomeGenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, CavesPlacedFeatures.RED_AGATE_GEODE);
    }
    public static void addQuartzGeode(BiomeGenerationSettings.LookupBackedBuilder builder) {
        builder.feature(GenerationStep.Feature.LOCAL_MODIFICATIONS, CavesPlacedFeatures.QUARTZ_GEODE);
    }

    public static void addAxolotls(MobSpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.AXOLOTLS, new MobSpawnSettings.SpawnEntry(EntityType.AXOLOTL, 12, 2, 5));
    }
    public static void addBats(MobSpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.AMBIENT, new MobSpawnSettings.SpawnEntry(EntityType.BAT, 1, 1, 2));
    }
    public static void addFrogs(MobSpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.CREATURE, new MobSpawnSettings.SpawnEntry(EntityType.FROG, 4, 1, 4));
    }
    public static void addSnails(MobSpawnSettings.Builder spawnSettings) {
    }

    // Monsters
    public static void addSparseNpc(MobSpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.MONSTER, new MobSpawnSettings.SpawnEntry(EntitiesME.NPC, 4, 1, 1));
                //.spawnCost(EntitiesME.NPC, 0.7, 0.5);
    }
    public static void addCaveTroll(MobSpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.MONSTER, new MobSpawnSettings.SpawnEntry(EntitiesME.CAVE_TROLL, 5, 1, 2));
                //.spawnCost(EntitiesME.CAVE_TROLL, 20, 0.1);
    }
    public static void addSpiders(MobSpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.MONSTER, new MobSpawnSettings.SpawnEntry(EntitiesME.SHELOBITE_SCUTTLER, 3, 1, 5));
        spawnSettings.spawn(SpawnGroup.MONSTER, new MobSpawnSettings.SpawnEntry(EntitiesME.SPAWN_OF_SHELOB, 5, 1, 1));
    }
    public static void addSpiderLarvas(MobSpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.MONSTER, new MobSpawnSettings.SpawnEntry(EntitiesME.SHELOBITE_LARVA, 2, 2, 4))
                .spawnCost(EntitiesME.SHELOBITE_LARVA, 0.7, 0.3);
    }
    public static void addSnowTrolls(MobSpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(SpawnGroup.MONSTER, new MobSpawnSettings.SpawnEntry(EntitiesME.SNOW_TROLL, 4, 1, 2));
    }

}
