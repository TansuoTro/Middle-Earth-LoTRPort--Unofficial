package net.jukoz.me.world.spawners;

import net.jukoz.me.entity.EntitiesME;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;

public class ModSpawnSettingsBuilder {
    public static void addRiverAnimals(MobSpawnSettings.Builder builder) {
        builder.spawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.SALMON, 12, 1, 5));
    }
    public static void addOceanAnimals(MobSpawnSettings.Builder builder) {
        builder.spawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.COD, 10, 1, 5));
        builder.spawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SQUID, 6, 1, 4));
        builder.spawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.TURTLE, 4, 1, 2));
        builder.spawn(MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DOLPHIN, 3, 1, 2));
    }

    public static void addColdWaterAnimals(MobSpawnSettings.Builder builder) {
        builder.spawn(MobCategory.WATER_AMBIENT, new MobSpawnSettings.SpawnerData(EntityType.COD, 5, 1, 5));
    }

    public static void addFarmAnimals(MobSpawnSettings.Builder builder) {
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 12, 4, 4));
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.PIG, 10, 4, 4));
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 10, 4, 4));
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.COW, 8, 4, 4));
    }

    public static void addRareWarg(MobSpawnSettings.Builder builder){
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntitiesME.WARG, 3, 1, 3));
    }
    public static void addUncommonWarg(MobSpawnSettings.Builder builder){
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntitiesME.WARG, 6, 1, 3));
    }

    public static void addRareStoneTroll(MobSpawnSettings.Builder builder){
        builder.spawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntitiesME.STONE_TROLL, 3, 1, 2));
    }
    public static void addRareCaveTroll(MobSpawnSettings.Builder builder){
        builder.spawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntitiesME.CAVE_TROLL, 3, 1, 1));
    }
    public static void addMirkwoodSpider(MobSpawnSettings.Builder builder){
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntitiesME.SHELOBITE_SCUTTLER, 9, 2, 4));
    }
    public static void addRareMirkwoodSpider(MobSpawnSettings.Builder builder){
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntitiesME.SHELOBITE_SCUTTLER, 5, 1, 2));
    }

    public static void addUncommonBats(MobSpawnSettings.Builder builder){
    builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.BAT, 8, 2, 4));
    }

    public static void addPlainsMobs(MobSpawnSettings.Builder builder) {
        addFarmAnimals(builder);
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.HORSE, 5, 2, 6));
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.DONKEY, 1, 1, 3));
    }

    public static void addRabbits(MobSpawnSettings.Builder builder) {
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 3, 1, 5));
    }

    public static void addCats(MobSpawnSettings.Builder builder) {
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CAT, 5, 1, 3));
    }
    public static void addWolves(MobSpawnSettings.Builder builder) {
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 3, 1, 3));
    }
    public static void addCommonWolves(MobSpawnSettings.Builder builder) {
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 8, 1, 3));
    }
    public static void addRareWolves(MobSpawnSettings.Builder builder) {
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.WOLF, 1, 1, 2));
    }

    public static void addMountainsMobs(MobSpawnSettings.Builder builder) {
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.GOAT, 4, 1, 3));
    }
    public static void addBroadhoofGoats(MobSpawnSettings.Builder builder) {
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntitiesME.BROADHOOF_GOAT, 4, 1, 3));
    }

    public static void addNordicMobs(MobSpawnSettings.Builder builder) {
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 4, 2, 4));
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 2, 1, 3));
    }

    public static void addForochelMobs(MobSpawnSettings.Builder builder) {
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 2, 1, 3));
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.POLAR_BEAR, 1, 1, 2));
    }

    public static void addSwampMobs(MobSpawnSettings.Builder builder) {
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FROG, 8, 1, 4));
    }

    public static void addRareSnails(MobSpawnSettings.Builder builder) {
    }

    public static void addEriadorMobs(MobSpawnSettings.Builder builder) {
        addPlainsMobs(builder);
        addPheasant(builder);
    }

    public static void addArmadillo(MobSpawnSettings.Builder builder) {
    }
    public static void addHaradMobs(MobSpawnSettings.Builder builder) {
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 3, 1, 4));
    }

    public static void addCamel(MobSpawnSettings.Builder builder) {
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CAMEL, 1, 1, 2));
    }

    public static void addLlama(MobSpawnSettings.Builder builder) {
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.LLAMA, 2, 1, 3));
    }

    public static void addPheasant(MobSpawnSettings.Builder builder) {
    }

    public static void addSwan(MobSpawnSettings.Builder builder) {
    }

    public static void addDeer(MobSpawnSettings.Builder builder) {
    }

    public static void addNpcs(MobSpawnSettings.Builder spawnSettings) {
        spawnSettings.spawn(MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(EntitiesME.NPC, 3, 1, 1));
    }

    public static void addGreatHorn(MobSpawnSettings.Builder builder) {
        builder.spawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntitiesME.GREAT_HORN, 5, 1, 2));
    }
}
