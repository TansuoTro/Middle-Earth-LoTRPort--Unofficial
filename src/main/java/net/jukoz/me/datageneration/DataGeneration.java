package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.jukoz.me.datageneration.recipes.AlloyAndAnvilRecipeProvider;
import net.jukoz.me.datageneration.recipes.ArtisanTableArmorRecipeProvider;
import net.jukoz.me.datageneration.recipes.ArtisanTableGenericArmorRecipeProvider;
import net.jukoz.me.datageneration.recipes.ArtisanTableHandheldRecipeProvider;
import net.jukoz.me.datageneration.recipes.InscriptionRecipeProvider;
import net.jukoz.me.world.biomes.surface.ModBiomes;
import net.jukoz.me.world.biomes.caves.ModCaveBiomes;
import net.jukoz.me.world.features.boulder.BoulderConfiguredFeatures;
import net.jukoz.me.world.features.boulder.BoulderPlacedFeatures;
import net.jukoz.me.world.features.chain.ChainConfiguredFeatures;
import net.jukoz.me.world.features.chain.ChainPlacedFeatures;
import net.jukoz.me.world.features.misc.ModMiscConfiguredFeatures;
import net.jukoz.me.world.features.misc.ModMiscPlacedFeatures;
import net.jukoz.me.world.features.ores.OreConfiguredFeatures;
import net.jukoz.me.world.features.ores.OrePlacedFeatures;
import net.jukoz.me.world.features.tree.ModTreeConfiguredFeatures;
import net.jukoz.me.world.features.tree.ModTreePlacedFeatures;
import net.jukoz.me.world.features.tree.MushroomTreeConfiguredFeatures;
import net.jukoz.me.world.features.underground.CavesConfiguredFeatures;
import net.jukoz.me.world.features.underground.CavesPlacedFeatures;
import net.jukoz.me.world.features.vegetation.ModVegetationConfiguredFeatures;
import net.jukoz.me.world.features.vegetation.ModVegetationPlacedFeatures;
import net.minecraft.core.RegistryBuilder;
import net.minecraft.core.registries.Registries;

public class DataGeneration implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {

        HelpingGenerator.generateFiles();

        var pack = fabricDataGenerator.createPack();
        pack.addProvider(BlockTagProvider::new);
        pack.addProvider(BlockLootTableProvider::new);
        pack.addProvider(ItemTagProvider::new);
        pack.addProvider(ModelProvider::new);
        pack.addProvider(RecipeProvider::new);
        pack.addProvider((FabricDataOutput output) -> new ArtisanTableArmorRecipeProvider(output));
        pack.addProvider((FabricDataOutput output) -> new ArtisanTableGenericArmorRecipeProvider(output));
        pack.addProvider((FabricDataOutput output) -> new ArtisanTableHandheldRecipeProvider(output));
        pack.addProvider((FabricDataOutput output) -> new InscriptionRecipeProvider(output));
        pack.addProvider((FabricDataOutput output) -> new AlloyAndAnvilRecipeProvider(output));
        pack.addProvider(LanguageProvider::new);

        pack.addProvider(DataWorldGenerator::new);
    }

    /** Fabric re-creates the registry wrapper for later providers, which would replay every
     *  bootstrap on the same builder and trip "Duplicate registration" for all biomes/features. */
    private static boolean registriesBuilt = false;

    @Override
    public void buildRegistry(RegistryBuilder registryBuilder) {
        if (registriesBuilt) {
            return;
        }
        registriesBuilt = true;
        DataGeneratorEntrypoint.super.buildRegistry(registryBuilder);
        registryBuilder.addRegistry(Registries.BIOME, ModBiomes::bootstrap);
        registryBuilder.addRegistry(Registries.BIOME, ModCaveBiomes::bootstrap);
        registryBuilder.addRegistry(Registries.CONFIGURED_FEATURE, ModTreeConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(Registries.CONFIGURED_FEATURE, ModVegetationConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(Registries.CONFIGURED_FEATURE, BoulderConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(Registries.CONFIGURED_FEATURE, OreConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(Registries.CONFIGURED_FEATURE, CavesConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(Registries.CONFIGURED_FEATURE, ModMiscConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(Registries.CONFIGURED_FEATURE, MushroomTreeConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(Registries.CONFIGURED_FEATURE, ChainConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(Registries.PLACED_FEATURE, ModTreePlacedFeatures::bootstrap);
        registryBuilder.addRegistry(Registries.PLACED_FEATURE, ModVegetationPlacedFeatures::bootstrap);
        registryBuilder.addRegistry(Registries.PLACED_FEATURE, BoulderPlacedFeatures::bootstrap);
        registryBuilder.addRegistry(Registries.PLACED_FEATURE, OrePlacedFeatures::bootstrap);
        registryBuilder.addRegistry(Registries.PLACED_FEATURE, CavesPlacedFeatures::bootstrap);
        registryBuilder.addRegistry(Registries.PLACED_FEATURE, ModMiscPlacedFeatures::bootstrap);
        registryBuilder.addRegistry(Registries.PLACED_FEATURE, ChainPlacedFeatures::bootstrap);
        // The mod's own dynamic registries (races, factions, npc types, spider/great-horn
        // variants, ...) are bootstrapped here so DataWorldGenerator can emit their JSON.
        net.jukoz.me.registries.DynamicRegistriesME.prepareBoostrap(registryBuilder);
    }
}
