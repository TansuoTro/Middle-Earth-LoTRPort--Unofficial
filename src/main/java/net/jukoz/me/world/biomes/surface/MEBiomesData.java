package net.jukoz.me.world.biomes.surface;

import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.caves.CaveType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;

import java.awt.Color;
import java.util.*;

/** Adapts the complete donor biome palette to the 1.20.1 terrain interfaces. */
public class MEBiomesData {
    private static final Map<Integer, MEBiome> byColor = new HashMap<>();
    private static final Map<RegistryKey<Biome>, MEBiome> byKey = new HashMap<>();
    private static final List<MEBiome> biomes = new ArrayList<>();
    public static List<RegistryKey<Biome>> waterBiomes = MapBasedBiomePool.waterBiomes;
    public static List<RegistryKey<Biome>> frozenBiomes = MapBasedBiomePool.frozenBiomes;
    public static List<RegistryKey<Biome>> wastePondBiomes = MapBasedBiomePool.wastePondBiomes;
    public static List<RegistryKey<Biome>> mirkwoodSwampBiomes = MapBasedBiomePool.mirkwoodSwampBiomes;
    public static List<RegistryKey<Biome>> oasisBiomes = MapBasedBiomePool.oasisBiomes;
    public static MEBiome defaultBiome, frozenPond, oasis, pond, wastePond, mirkwoodSwamp;

    public static MEBiome getBiomeByColor(Integer rgb) {
        MEBiome biome = byColor.get(rgb | 0xff000000);
        if (biome == null) throw new IllegalArgumentException("Unmapped biome map color: #" + Integer.toHexString(rgb & 0xffffff));
        return biome;
    }

    public static MEBiome getBiomeById(Short id) { return biomes.get(id); }
    public static Integer getColorByBiomeId(Short id) { return getBiomeById(id).color.getRGB(); }
    public static Map<Integer, MEBiome> getColorPalette() { return Collections.unmodifiableMap(byColor); }

    public static void loadBiomes() {
        MapBasedBiomePool.loadBiomes();
        byColor.clear();
        byKey.clear();
        biomes.clear();
        MapBasedBiomePool.getColorPalette().entrySet().stream()
                .sorted(Comparator.comparingInt(entry -> entry.getKey().getRGB()))
                .forEach(entry -> {
                    MEBiome biome = adapt(entry.getValue());
                    biome.color = entry.getKey();
                    byColor.put(entry.getKey().getRGB(), biome);
                    byKey.put(biome.biome, biome);
                    biomes.add(biome);
                });
        defaultBiome = adapt(MapBasedBiomePool.defaultBiome);
        frozenPond = adapt(MapBasedBiomePool.frozenPond);
        oasis = adapt(MapBasedBiomePool.oasis);
        pond = adapt(MapBasedBiomePool.pond);
        wastePond = adapt(MapBasedBiomePool.wastePond);
        mirkwoodSwamp = adapt(MapBasedBiomePool.mirkwoodSwamp);
        // This sub-biome has no dedicated map color; it inherits its parent height.
        byKey.put(MEBiomeKeys.GREY_FOREST, adapt(MapBiomeData.getBiome(MEBiomeKeys.GREY_FOREST),
                MapBasedBiomePool.getBiomeByKey(MEBiomeKeys.GREY_PLAINS).getHeight()));
        byKey.put(MEBiomeKeys.RIVER_RUNNING, adapt(MapBiomeData.getBiome(MEBiomeKeys.RIVER_RUNNING),
                MapBasedBiomePool.getBiomeByKey(MEBiomeKeys.RIVER).getHeight()));
    }

    private static MEBiome adapt(MapBasedCustomBiome source) {
        MEBiome biome = adapt(source.getBiome(), source.getHeight());
        byKey.put(biome.biome, biome);
        return biome;
    }

    private static MEBiome adapt(BiomeData data, int height) {
        Block surface = data.getSlopeMap().getBlockAtAngle(0);
        List<net.jukoz.me.world.biomes.BlocksLayeringData.LayerData> layers = data.getBlocksLayering().layers;
        if (layers.isEmpty()) throw new IllegalStateException("Missing terrain layers for " + data.getBiomeRegistryKey());
        Block stone = layers.get(0).block;
        Block upperStone = layers.get(layers.size() - 1).block;
        Block underSurface = surface == Blocks.GRASS_BLOCK ? Blocks.DIRT : surface;
        MEBiome biome = new MEBiome(height, data.getBiomeRegistryKey(), surface, underSurface,
                upperStone, stone, data.getCaveType() == null ? CaveType.DEFAULT : data.getCaveType());
        biome.data = data;
        return biome;
    }

    public static MEBiome getBiomeByKey(RegistryEntry<Biome> biome) {
        return getBiomeByKey(biome.getKey().orElseThrow());
    }

    public static MEBiome getBiomeByKey(RegistryKey<Biome> key) {
        MEBiome biome = byKey.get(key);
        if (biome == null) throw new IllegalArgumentException("No terrain metadata for " + key.getValue());
        return biome;
    }
}
