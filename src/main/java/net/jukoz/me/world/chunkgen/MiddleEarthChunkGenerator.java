package net.jukoz.me.world.chunkgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.jukoz.me.block.registration.StoneBlockSets;
import net.jukoz.me.utils.noises.BlendedNoise;
import net.jukoz.me.utils.noises.SimplexNoise;
import net.jukoz.me.world.map.MiddleEarthMapRuntime;
import net.jukoz.me.world.map.MiddleEarthMapUtils;
import net.jukoz.me.world.biomes.surface.MEBiome;
import net.jukoz.me.world.biomes.MEBiomeKeys;
import net.jukoz.me.world.biomes.surface.MEBiomesData;
import net.jukoz.me.world.biomes.surface.ModBiomeSource;
import net.jukoz.me.world.chunkgen.map.MiddleEarthHeightMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryOps;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.CheckedRandom;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.RandomSeed;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.*;
import net.minecraft.world.gen.noise.NoiseConfig;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class MiddleEarthChunkGenerator extends ChunkGenerator {
    public static final int MEDGON_LEVEL = -32;
    public static final int NURGON_LEVEL = 0;

    public static final int EPMOSTO_LEVEL = -32;
    public static final int DIFTOMIN_LEVEL = 0;
    public static final int DEEPSLATE_LEVEL = 32;
    public static final int STONE_HEIGHT = 36;
    public static final int WATER_HEIGHT = 64;
    public static final int LAVA_HEIGHT = -60;
    public static final int HEIGHT = 27 + STONE_HEIGHT;
    public static final int DIRT_HEIGHT = 3 + HEIGHT;
    public static final int CAVE_NOISE = 5;

    MiddleEarthMapUtils middleEarthMapUtils;
    MiddleEarthMapRuntime middleEarthMapRuntime;

    private static final int CAVE_STRETCH_H = 60;
    private static final int CAVE_STRETCH_V = 50;
    private static float minNoise = 10000;
    private static float maxNoise = -10000;
    RegistryEntryLookup<Biome> biomeRegistry;
    public static final Codec<MiddleEarthChunkGenerator> CODEC = RecordCodecBuilder.create((instance) ->
            instance.group(RegistryOps.getEntryLookupCodec(RegistryKeys.BIOME))
                    .apply(instance, instance.stable(MiddleEarthChunkGenerator::new)));

    public MiddleEarthChunkGenerator(RegistryEntryLookup<Biome> biomeRegistry) {
        super(new ModBiomeSource(
                new ArrayList<>(Arrays.asList(
                    biomeRegistry.getOrThrow(MEBiomeKeys.OCEAN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ANDUIN_VALES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ANDUIN_VALES_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ANORIEN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ANORIEN_RIVERSIDE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ANORIEN_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BARROW_DOWNS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BELERIAND_ISLAND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BELFALAS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BELFALAS_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BELFALAS_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BELFALAS_BEACH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BLACKROOT_VALE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BLACKROOT_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BLUE_MOUNTAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BLUE_MOUNTAINS_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BLUE_MOUNTAINS_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BLUE_MOUNTAINS_HIGH_LANDS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BLUE_MOUNTAINS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BLUE_MOUNTAINS_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BROWN_LANDS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.CARADHRAS_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.CARADHRAS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.CARADHRAS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.CELEBDIL_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.CELEBDIL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.CELEBDIL_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.CORSAIR_COASTS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DALE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DALE_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DALE_MEADOW),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DALE_CITY),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DAGORLAD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DALE_RIVERSIDE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DARK_MIRKWOOD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DARK_MIRKWOOD_EDGE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DARK_ANDUIN_VALES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DEAD_MARSHES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DEAD_MARSHES_WATER),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DESOLATED_LANDS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DOL_GULDUR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DOL_GULDUR_HILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DORWINION),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DORWINION_LAVENDER_FIELD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DORWINION_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DUNLAND_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DUNLAND_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EAST_BIGHT),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EASTERN_NURN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EASTERN_RHOVANION),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EASTERN_RHOVANION_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EMYN_MUIL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EMYN_MUIL_CLIFFS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EMYN_MUIL_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EMYN_MUIL_POND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ENEDWAITH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ENEDWAITH_FIELD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ENEDWAITH_WHEAT_FIELD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EREGION),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EREGION_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EREGION_GLADE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ETHIR_ANDUIN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ETHIR_ANDUIN_RIVER_DELTA),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FANGORN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FANGORN_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FANUIDHOL_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FANUIDHOL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FANUIDHOL_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MIRKWOOD_RIVER),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREAT_RIVER),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GUNDABAD_PLAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GUNDABAD_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FORODWAITH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FROZEN_OCEAN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FROZEN_POND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LINDON_SHORES_CLIFFS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LINDON_SHORES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GONDOR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GONDOR_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GONDOR_HILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GORGOROTH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GORGOROTH_ASHEN_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GORGOROTH_DELTA),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREY_MOUNTAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREY_MOUNTAINS_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREY_MOUNTAINS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREY_ASHEN_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREY_PLAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GREY_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HARAD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HARAD_DESERT),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HARAD_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HARONDOR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HILLS_OF_EVENDIM),
                    biomeRegistry.getOrThrow(MEBiomeKeys.IRON_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.IRON_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.IRON_HILLS_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.IRON_HILLS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.IRON_HILLS_PLAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NORTHERN_RHOVANION_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NORTHERN_RHOVANION_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ISENGARD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ISENGARD_HILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ITHILIEN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ITHILIEN_GLADE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ITHILIEN_WASTES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ITHILIEN_WASTES_GLADE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LAMEDON),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LAMEDON_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LEBENNIN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LEBENNIN_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LEBENNIN_SHORES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LINDON),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LINDON_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LINDON_HIDDEN_BLOSSOM),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LINDON_MEADOW),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONELY_MOUNTAIN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONELY_MOUNTAIN_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONELY_MOUNTAIN_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONELY_MOUNTAIN_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONELY_MOUNTAIN_TAIGA),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONG_LAKE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONG_LAKE_SHORES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LONG_MARSHES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LORIEN_EDGE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOSSARNACH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOSSARNACH_CHERRY_BLOSSOM),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOSSARNACH_VALLEY),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOSSARNACH_VALLEY_RED),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOSSARNACH_VALLEY_ORANGE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOSSARNACH_VALLEY_YELLOW),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOSSARNACH_VALLEY_GREEN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOTHLORIEN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOTHLORIEN_GLADE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LOTHLORIEN_BLOSSOM),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OASIS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.POND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MANGROVE_POND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MINHIRIATH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MINHIRIATH_WHEAT_FIELD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MIRKWOOD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MIRKWOOD_EDGE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MIRKWOOD_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MIRKWOOD_MOUNTAINS_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MIRKWOOD_MOUNTAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MIRKWOOD_MOUNTAINS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MIRKWOOD_MARSHES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MIRKWOOD_SWAMP),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MISTY_MOUNTAINS_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MISTY_MOUNTAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MISTY_MOUNTAINS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORDOR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORDOR_ASHEN_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORDOR_HILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ERED_LITHUI),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ERED_LITHUI_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ERED_LITHUI_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORDOR_WASTES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORGUL_VALE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORGUL_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MORGUL_RIVER),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MOUNT_GUNDABAD_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MOUNT_GUNDABAD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MOUNT_GUNDABAD_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MOUNT_DOOM_PIT),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MOUNT_DOOM),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NAN_CURUNIR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NEN_HITHOEL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NEN_HITHOEL_RAPIDS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NEN_HITHOEL_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NEN_HITHOEL_SHORES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NINDALF),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NORTH_DOWNS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DUNLAND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NORTHERN_DUNLAND_GLADE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NORTHERN_MIRKWOOD_MARSHES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NORTHERN_MIRKWOOD_SWAMP),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NORTHERN_WASTELANDS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NURN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NURN_EDGE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NURN_EDGE_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NURN_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NURN_HILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NURN_RIVER),
                    biomeRegistry.getOrThrow(MEBiomeKeys.NURN_SEA),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OCEAN_COAST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_ANGMAR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_ANGMAR_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_ANGMAR_COLD_HILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_ANGMAR_FROZEN_HILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_ARTHEDAIN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_ARTHEDAIN_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_ARTHEDAIN_MEADOW),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_ARTHEDAIN_FOOTHILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_CARDOLAN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_CARDOLAN_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_CARDOLAN_HILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_RHUDAUR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_RHUDAUR_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OLD_RHUDAUR_HILL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.OSGILIATH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.PELENNOR_FIELDS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.PELENNOR_WHEAT_FIELD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.RIVER),
                    biomeRegistry.getOrThrow(MEBiomeKeys.RHUN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.RHUN_FIELD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.RHUN_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.RHUN_HIDDEN_BLOSSOM),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HIGH_MOOR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HIGH_MOOR_VALE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.HIGH_MOOR_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ROHAN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ROHAN_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ROHAN_FIELD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SEA_OF_RHUN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SARN_GEBIR_WILDLANDS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SARN_GEBIR_SHORES),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SHIRE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SHIRE_EDGE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SHIRE_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SHIRE_HILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SHIRE_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SOUTHEAST_RHOVANION),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SOUTHEAST_RHOVANION_FIELD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DRUWAITH_IAUR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.SOUTHERN_FOROCHEL),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EPHEL_DUATH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EPHEL_DUATH_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.EPHEL_DUATH_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.THE_ANGLE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.THE_OLD_FOREST),
                    biomeRegistry.getOrThrow(MEBiomeKeys.THE_WOLD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.THE_WOLD_WHEAT_FIELD),
                    biomeRegistry.getOrThrow(MEBiomeKeys.THE_WHITE_DOWNS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.TOLFALAS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.TOROGWAITH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.TROLLSHAWS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.UDUN),
                    biomeRegistry.getOrThrow(MEBiomeKeys.UMBAR),
                    biomeRegistry.getOrThrow(MEBiomeKeys.UMBAR_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WASTE_POND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WEBBED_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WEBBED_DARK_WOODS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WITHERED_HEATH),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WHITE_MOUNTAINS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WHITE_MOUNTAINS_BASE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WHITE_MOUNTAINS_PEAKS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WOODLAND_REALM),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WOODLAND_FOOTHILLS),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WOODLAND_GLADE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.AUTUMN_WOODLAND),
                    biomeRegistry.getOrThrow(MEBiomeKeys.WOODLAND_HILLS),

                    biomeRegistry.getOrThrow(MEBiomeKeys.BASIC_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LUSH_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DRIPSTONE_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DOLOMITE_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GALONN_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.GILDED_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.IZHERABAN_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.LIMESTONE_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MOUNTAIN_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MUD_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.FUNGUS_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MITHRIL_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.BASALT_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.MAGMA_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.DRY_CAVE),
                    biomeRegistry.getOrThrow(MEBiomeKeys.ICE_CAVE)
                ))
            )
        );
        this.biomeRegistry = biomeRegistry;

        this.middleEarthMapUtils = MiddleEarthMapUtils.getInstance();
        this.middleEarthMapRuntime = MiddleEarthMapRuntime.getInstance();

    }

    @Override
    protected Codec<? extends ChunkGenerator> getCodec() {
        return CODEC;
    }

    @Override
    public void carve(ChunkRegion chunkRegion, long seed, NoiseConfig noiseConfig,
                      BiomeAccess biomeAccess, StructureAccessor structureAccessor,
                      Chunk chunk2, GenerationStep.Carver carverStep) {

    }



    @Override
    public void buildSurface(ChunkRegion region, StructureAccessor structures, NoiseConfig noiseConfig, Chunk chunk) {
        int bottomY = chunk.getBottomY();
        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                int posX = (chunk.getPos().x * 16) + x;
                int posZ = (chunk.getPos().z * 16) + z;
                MEBiome meBiome = null;

                if(middleEarthMapUtils.isWorldCoordinateInBorder(posX, posZ)) {
                    RegistryEntry<Biome> biome = region.getBiome(new BlockPos(posX, chunk.getTopY(), posZ));
                    meBiome = MEBiomesData.getBiomeByKey(biome);
                    if(meBiome == null) {
                        // The biome at the world top is a cave biome: a tall mountain's surface
                        // sits above the build limit, so ModBiomeSource reports the cave variant.
                        // Use the raw surface biome from the map to get the correct blocks.
                        meBiome = middleEarthMapRuntime.getBiome(posX, posZ);
                    }
                    if(meBiome == null) {
                        meBiome = MEBiomesData.defaultBiome;
                    }
                } else {
                    meBiome = MEBiomesData.defaultBiome;
                }

                float height = MiddleEarthHeightMap.getHeight(posX, posZ);
                float caveBlendNoise = (float) ((2 * CAVE_NOISE * BlendedNoise.noise((double) posX / 24,  (double) posZ / 24)) - CAVE_NOISE);

                chunk.setBlockState(chunk.getPos().getBlockPos(x, bottomY, z), Blocks.BEDROCK.getDefaultState(), false);
                for(int y = bottomY + 1; y <= LAVA_HEIGHT; y++) {
                    chunk.setBlockState(chunk.getPos().getBlockPos(x, y, z), Blocks.LAVA.getDefaultState(), false);
                }

                for(int y = bottomY + 1; y < EPMOSTO_LEVEL + caveBlendNoise; y++) {
                    trySetBlock(chunk, chunk.getPos().getBlockPos(x, y, z), StoneBlockSets.IZHERABAN_SET.base().getDefaultState());
                }
                if(Math.random() < 0.5f) chunk.setBlockState(chunk.getPos().getBlockPos(x, chunk.getBottomY() + 1, z),
                        Blocks.BEDROCK.getDefaultState(), false);

                for(int y = EPMOSTO_LEVEL + (int) caveBlendNoise; y < DIFTOMIN_LEVEL + caveBlendNoise; y++) {
                    trySetBlock(chunk, chunk.getPos().getBlockPos(x, y, z), StoneBlockSets.ZIGILABAN_SET.base().getDefaultState());
                }
                for(int y = DIFTOMIN_LEVEL + (int) caveBlendNoise; y < DEEPSLATE_LEVEL + caveBlendNoise; y++) {
                    trySetBlock(chunk, chunk.getPos().getBlockPos(x, y, z), Blocks.DEEPSLATE.getDefaultState());
                }
                float dirtHeight = HEIGHT + height - 1;
                for(int y = DEEPSLATE_LEVEL + (int) caveBlendNoise; y < (dirtHeight / 2); y++) {
                    trySetBlock(chunk, chunk.getPos().getBlockPos(x, y, z), meBiome.stoneBlock.getDefaultState());
                }
                for(int y = (int) (dirtHeight / 2); y < dirtHeight; y++) {
                    trySetBlock(chunk, chunk.getPos().getBlockPos(x, y, z), meBiome.upperStoneBlock.getDefaultState());
                }

                chunk.setBlockState(chunk.getPos().getBlockPos(x, (int) (HEIGHT + height - 1), z), meBiome.stoneBlock.getDefaultState(), false);
                for(int y = (int) (HEIGHT + height); y < DIRT_HEIGHT + height; y++) {
                    chunk.setBlockState(chunk.getPos().getBlockPos(x, y, z), meBiome.underSurfaceBlock.getDefaultState(), false);
                }

                BlockState surfaceBlock = meBiome.surfaceBlock.getDefaultState();

                if(DIRT_HEIGHT + height < WATER_HEIGHT && meBiome.surfaceBlock == Blocks.GRASS_BLOCK) {
                    surfaceBlock = Blocks.DIRT.getDefaultState();
                }

                chunk.setBlockState(chunk.getPos().getBlockPos(x, (int) (DIRT_HEIGHT + height), z), surfaceBlock, false);

                for(int y = (int) (DIRT_HEIGHT + height + 1); y <= WATER_HEIGHT; y++) {
                    chunk.setBlockState(chunk.getPos().getBlockPos(x, y, z), Blocks.WATER.getDefaultState(), false);
                }
            }
        }

    }

    private void trySetBlock(Chunk chunk, BlockPos blockPos, BlockState blockState) {
        float noise = 0;
        if(blockPos.getY() < WATER_HEIGHT) {
            noise =(float) SimplexNoise.noise(
                    (float) blockPos.getX() / CAVE_STRETCH_H, Math.tan((float) blockPos.getY() / CAVE_STRETCH_V), (float) blockPos.getZ() / CAVE_STRETCH_H);
            noise += 0.5f * (float) SimplexNoise.noise(
                    (float) blockPos.getX() / (CAVE_STRETCH_H * 0.5f), (float) blockPos.getY() / (CAVE_STRETCH_V * 0.5f), (float) blockPos.getZ() / (CAVE_STRETCH_H * 0.5f));
            noise = noise / (1 + 0.5f);
        }
        float noise3 = (float) SimplexNoise.noise((float) blockPos.getX() / 90, (float) blockPos.getY() / 60, (float) blockPos.getZ() / 90);
        float miniNoise = (float) SimplexNoise.noise((float) blockPos.getX() / 40, (float) blockPos.getY() / 30, (float) blockPos.getZ() / 40);

        if(noise < 0.4f && noise3 < 0.75f && miniNoise < 0.8f) { //
            chunk.setBlockState(blockPos, blockState, false);
        }
    }
    
    @Override
    public void generateFeatures(StructureWorldAccess world, Chunk chunk, StructureAccessor structureAccessor) {
        super.generateFeatures(world, chunk, structureAccessor);
    }

    @Override
    public void populateEntities(ChunkRegion region) {
        ChunkPos chunkPos = region.getCenterPos();
        RegistryEntry<Biome> registryEntry = region.getBiome(chunkPos.getStartPos().withY(region.getTopY() - 1));
        ChunkRandom chunkRandom = new ChunkRandom(new CheckedRandom(RandomSeed.getSeed()));
        chunkRandom.setPopulationSeed(region.getSeed(), chunkPos.getStartX(), chunkPos.getStartZ());
        SpawnHelper.populateEntities(region, registryEntry, chunkPos, chunkRandom);
    }

    @Override
    public net.minecraft.world.gen.chunk.placement.StructurePlacementCalculator createStructurePlacementCalculator(
            net.minecraft.registry.RegistryWrapper<net.minecraft.structure.StructureSet> structureSetRegistry,
            NoiseConfig noiseConfig, long seed) {
        // The base ChunkGenerator returns StructurePlacementCalculator.EMPTY, which silently
        // disables every structure set (and makes /locate structure throw). A custom generator
        // has to build the real calculator from its biome source.
        return net.minecraft.world.gen.chunk.placement.StructurePlacementCalculator.create(
                noiseConfig, seed, this.getBiomeSource(), structureSetRegistry);
    }

    public int getWorldHeight() {
        return 384;
    }


    @Override
    public CompletableFuture<Chunk> populateNoise(Executor executor, Blender blender, NoiseConfig noiseConfig, StructureAccessor structureAccessor, Chunk chunk) {

        return CompletableFuture.completedFuture(chunk);
    }

    @Override
    public int getSeaLevel() {
        return WATER_HEIGHT;
    }

    @Override
    public int getMinimumY() {
        return -4;
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmap, HeightLimitView world, NoiseConfig noiseConfig) {
        float worldHeight = 1 + DIRT_HEIGHT + MiddleEarthHeightMap.getHeight(x, z);
        return (int)worldHeight;
    }

    @Override
    public VerticalBlockSample getColumnSample(int x, int z, HeightLimitView world, NoiseConfig noiseConfig) {
        return new VerticalBlockSample(0, new BlockState[0]);
    }

    @Override
    public void getDebugHudText(List<String> text, NoiseConfig noiseConfig, BlockPos pos) {

    }
}
