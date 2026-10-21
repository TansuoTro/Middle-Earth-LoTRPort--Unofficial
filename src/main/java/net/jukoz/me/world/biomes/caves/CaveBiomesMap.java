package net.jukoz.me.world.biomes.caves;


import net.minecraft.resources.ResourceKey;
import net.minecraft.util.math.Vec2f;
import net.minecraft.world.level.biome.Biome;

import java.util.ArrayList;

public class CaveBiomesMap {
    ArrayList<CaveBiomeDTO> caves;

    public CaveBiomesMap() {
        this.caves = new ArrayList<>();
    }

    public void addCave(CaveBiomeDTO caveBiomeDTO) {
        caves.add(caveBiomeDTO);
    }

    public ResourceKey<Biome> getClosestBiome(Vec2f coordinates) {
        ResourceKey<Biome> biome = null;
        float currentDistance = 10.0f;
        for (CaveBiomeDTO biomeDTO: caves) {
            float distance = biomeDTO.coordinates.distanceSquared(coordinates);
            if(distance < currentDistance) {
                currentDistance = distance;
                biome = biomeDTO.biome;
            }
        }
        return biome;
    }
}
