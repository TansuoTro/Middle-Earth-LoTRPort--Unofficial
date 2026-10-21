package net.jukoz.me.world.dimension;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.resources.datas.attributes.AttributePool;
import net.jukoz.me.resources.datas.factions.FactionUtil;
import net.jukoz.me.resources.datas.races.Race;
import net.jukoz.me.resources.datas.races.RaceUtil;
import net.jukoz.me.resources.persistent_datas.PlayerDataService;
import net.jukoz.me.utils.LoggerUtil;
import net.jukoz.me.world.chunkgen.MiddleEarthChunkGenerator;
import net.jukoz.me.world.chunkgen.map.MiddleEarthHeightMap;
import net.jukoz.me.world.map.MiddleEarthMapConfigs;
import net.jukoz.me.world.map.MiddleEarthMapUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.LevelStem;
import org.joml.Vector2i;
import org.joml.Vector3i;

public class ModDimensions {
    public static final Vector3i ME_SPAWN_LOCATION = new Vector3i(939, 90, 915);
    public static final String PATH = "middle-earth";

    public static final ResourceLocation ME_DIMENSION_ID = new ResourceLocation(MiddleEarth.MOD_ID, PATH);
    public static final ResourceLocation OW_DIMENSION_ID = new ResourceLocation("overworld");

    public static final ResourceKey<LevelStem> ME_DIMENSION_KEY =
            ResourceKey.of(Registries.DIMENSION, ME_DIMENSION_ID);

    public static ResourceKey<Level> ME_WORLD_KEY =
            ResourceKey.of(Registries.WORLD, ME_DIMENSION_KEY.getValue());

    public static final ResourceKey<LevelStem> OW_DIMENSION_KEY =
            ResourceKey.of(Registries.DIMENSION, OW_DIMENSION_ID);

    public static ResourceKey<Level> OW_WORLD_KEY =
            ResourceKey.of(Registries.WORLD, OW_DIMENSION_KEY.getValue());

    public static void register() {
        Registry.register(Registries.CHUNK_GENERATOR, ME_DIMENSION_ID, MiddleEarthChunkGenerator.CODEC);
        ME_WORLD_KEY = ResourceKey.of(Registries.WORLD, ME_DIMENSION_ID);

        LoggerUtil.getInstance().logDebugMsg("Registering ModDimensions for " + MiddleEarth.MOD_ID);
    }

    public static Vector3i getDimensionHeight(int x, int z) {
        int height = (int) (1 + MiddleEarthChunkGenerator.DIRT_HEIGHT + MiddleEarthHeightMap.getHeight(x, z));
        return new Vector3i(x, height, z);
    }

    public static void teleportPlayerToME(Player player) {
        Vector2i coordinates = MiddleEarthMapUtils.getInstance().getWorldCoordinateFromInitialMap(ME_SPAWN_LOCATION.x, ME_SPAWN_LOCATION.z);
        teleportPlayerToMe(player, new Vec3(coordinates.x, getDimensionHeight(coordinates.x, coordinates.y).y, coordinates.y), true, false);
    }

    public static void teleportPlayerToMe(Player player, Vector3i coordinates) {
        teleportPlayerToMe(player, new Vec3(coordinates.x, coordinates.y, coordinates.z), false, false);
    }

    public static void teleportPlayerToMe(Player player, Vec3 coordinates, boolean setSpawnPoint, boolean welcomeNeeded) {
        if (player.getWorld().isClient() || !(player instanceof ServerPlayer serverPlayer)) return;

        ServerLevel serverWorld = serverPlayer.getServer().getWorld(ME_WORLD_KEY);
        if (serverWorld == null) return;

        serverPlayer.wakeUp();
        serverPlayer.teleport(serverWorld, coordinates.x, coordinates.y, coordinates.z, serverPlayer.getYaw(), serverPlayer.getPitch());
        serverPlayer.refreshPositionAfterTeleport(coordinates.x, coordinates.y, coordinates.z);

        if (setSpawnPoint) {
            serverPlayer.setSpawnPoint(ME_WORLD_KEY,
                    BlockPos.ofFloored(coordinates.x, coordinates.y, coordinates.z), serverPlayer.getYaw(), true, true);
        }
        if (welcomeNeeded) {
            FactionUtil.sendOnFactionJoinMessage(player);
        }
        Race race = PlayerDataService.getPlayerRace(player, serverWorld);
        if (race != null) {
            RaceUtil.updateRace(player, race, false);
        }
    }

    public static boolean teleportPlayerToOverworld(Player player) {
        if (player.getWorld().isClient() || !(player instanceof ServerPlayer serverPlayer)) return false;

        ServerLevel overworld = serverPlayer.getServer().getOverworld();
        if (overworld == null) return false;

        PlayerDataService.OriginAggregate origin = PlayerDataService.getOriginAggregate(player, player.getWorld());
        BlockPos coordinate = origin == null ? overworld.getSpawnPos() : origin.origin();

        serverPlayer.teleport(overworld, coordinate.getX() + 0.5, coordinate.getY(), coordinate.getZ() + 0.5,
                serverPlayer.getYaw(), serverPlayer.getPitch());
        serverPlayer.refreshPositionAfterTeleport(coordinate.getX() + 0.5, coordinate.getY(), coordinate.getZ() + 0.5);

        if (!ModServerConfigs.ENABLE_KEEP_RACE_ON_DIMENSION_SWAP) {
            AttributePool.reverse(player);
        }
        return true;
    }

    public static int getHighestYAtXZ(int x, int z) {
        return (int) MiddleEarthHeightMap.getHeight(x, z);
    }

    /**
     * For future usage only, not necessary for now
     * @return world coordinate for current map coordinate selected based on map iteration/pixel weight
     */
    public static Vector3i getSpawnCoordinate(){
        double worldIteration = Math.pow(2, MiddleEarthMapConfigs.MAP_ITERATION);
        int x = (int)((ME_SPAWN_LOCATION.x * worldIteration));
        int z = (int)((ME_SPAWN_LOCATION.z * worldIteration));

        return new Vector3i(x, ME_SPAWN_LOCATION.y, z);
    }

    public static boolean isInMiddleEarth(Level world){
        return world.getRegistryKey().getValue().equals(ME_DIMENSION_ID);
    }

    public static boolean isInOverworld(Level world){
        return world.getRegistryKey().getValue().equals(OW_DIMENSION_ID);
    }
}
