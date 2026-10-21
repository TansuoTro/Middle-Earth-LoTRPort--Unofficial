package net.jukoz.me.compat.spawn;

import net.minecraft.core.HolderList;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.core.HolderSet;

/** 1.21.5 spawn-condition shim: carries the biomes a variant may spawn in. */
public record BiomeSpawnCondition(HolderSet<Biome> requiredBiomes) {
}
