package net.jukoz.me.compat.spawn;

import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.world.biome.Biome;

/** 1.21.5 spawn-condition shim: carries the biomes a variant may spawn in. */
public record BiomeSpawnCondition(RegistryEntryList<Biome> requiredBiomes) {
}
