package net.jukoz.me.registries.content.biomevents;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.registries.content.biomevents.pools.GenericHostilesBiomeEventPool;
import net.jukoz.me.resources.datas.biome_events.BiomeEventData;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;

public class BiomeEventRegistryUtil {
    private static final ResourceKey<Registry<BiomeEventData>> BIOME_EVENT_KEY = DynamicRegistriesME.BIOME_EVENT;

    public static List<ResourceKey<Biome>> biomeEntries = new ArrayList<>();

    public static void addBiomeEntry(ResourceKey<Biome> biome) {
        if(biomeEntries == null) {
            biomeEntries = new ArrayList<>();
        }
        if(biomeEntries.contains(biome)) {
            return;
        }
        biomeEntries.add(biome);
    }

    public static void removeBiomeEntry(ResourceLocation biomeId) {
        if (biomeEntries != null) {
            biomeEntries.removeIf(entry -> entry.getValue().equals(biomeId));
        }
    }

    public static void registerDefaults(BootstapContext<BiomeEventData> context, HolderGetter<BiomeEventData> registryEntryLookup) {
        if(biomeEntries == null)
            return;

        for(ResourceKey<Biome> key : biomeEntries){
            DynamicRegistriesME.register(context, registryEntryLookup, of(key), GenericHostilesBiomeEventPool.EMPTY);
        }
    }

    public static ResourceKey<BiomeEventData> of(ResourceKey<Biome> key){
        return DynamicRegistriesME.of(BIOME_EVENT_KEY, key.getValue());
    }

    public static void register(BootstapContext<BiomeEventData> context, HolderGetter<BiomeEventData> registryEntryLookup, ResourceKey<BiomeEventData> registryKey, BiomeEventData element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        BiomeEventRegistryUtil.removeBiomeEntry(registryKey.getValue());
        // [LANG datagen]
        // None
    }

    public static ResourceKey<Structure> register(String name) {
        return ResourceKey.of(Registries.STRUCTURE, new ResourceLocation(MiddleEarth.MOD_ID, name));
    }
}
