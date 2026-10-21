package net.jukoz.me.world.biomes.caves;

import net.minecraft.resources.ResourceKey;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.level.biome.Biome;

public class CaveBiomeDTO {
    ResourceKey<Biome> biome;
    Vec2f coordinates;

    public CaveBiomeDTO(ResourceKey<Biome> biome, Vec2f coordinates) {
        this.biome = biome;
        this.coordinates = coordinates;
    }
}
