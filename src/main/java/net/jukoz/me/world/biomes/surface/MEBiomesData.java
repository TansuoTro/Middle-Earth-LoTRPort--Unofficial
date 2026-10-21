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

    /** Colours found in a map tile but absent from the palette (tile/palette version drift). */
    private static final Map<Integer, MEBiome> unknownColorCache = new HashMap<>();
    private static final Set<Integer> loggedUnknown = new HashSet<>();
    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger("MEBiomesData");

    public static MEBiome getBiomeByColor(Integer rgb) {
        int key = rgb | 0xff000000;
        MEBiome biome = byColor.get(key);
        if (biome != null) return biome;
        biome = unknownColorCache.get(key);
        if (biome != null) return biome;
        // A single unknown tile colour used to abort /locate and worldgen with an exception.
        // Fall back to the closest palette colour so generation keeps going.
        MEBiome nearest = nearestBiome(rgb & 0xffffff);
        if (loggedUnknown.add(key)) {
            LOGGER.warn("Unmapped biome map colour #{}, using nearest palette colour", String.format("%06x", rgb & 0xffffff));
        }
        unknownColorCache.put(key, nearest);
        return nearest;
    }

    private static MEBiome nearestBiome(int rgb) {
        int tr = (rgb >> 16) & 255, tg = (rgb >> 8) & 255, tb = rgb & 255;
        int best = Integer.MAX_VALUE;
        MEBiome bestBiome = defaultBiome;
        for (Map.Entry<Integer, MEBiome> entry : byColor.entrySet()) {
            int c = entry.getKey();
            int dr = ((c >> 16) & 255) - tr, dg = ((c >> 8) & 255) - tg, db = (c & 255) - tb;
            int distance = dr * dr + dg * dg + db * db;
            if (distance < best) {
                best = distance;
                bestBiome = entry.getValue();
            }
        }
        return bestBiome != null ? bestBiome : defaultBiome;
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
        // Cave biomes (me:*_cave) carry no surface terrain metadata: the biome source returns
        // them at the world top when a mountain's surface sits above the build limit. Returning
        // null lets callers fall back to the map's surface biome instead of aborting chunk gen
        // with "No terrain metadata for me:mountain_cave".
        return byKey.get(key);
    }
}
