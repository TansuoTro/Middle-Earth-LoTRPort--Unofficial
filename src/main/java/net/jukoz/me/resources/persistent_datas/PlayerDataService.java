package net.jukoz.me.resources.persistent_datas;

import net.minecraft.world.entity.player.Player;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.DimensionTypes;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.FactionLookup;
import net.jukoz.me.resources.datas.factions.data.SpawnData;
import net.jukoz.me.resources.datas.factions.data.SpawnDataHandler;
import net.jukoz.me.resources.datas.races.Race;
import net.jukoz.me.resources.datas.races.RaceLookup;
import net.jukoz.me.world.dimension.ModDimensions;

public class PlayerDataService {
    private static PlayerData getPlayerData(Player player){
        return StateSaverAndLoader.getPlayerState(player);
    }

    public static boolean clearPlayerData(Player player){
        PlayerData data = getPlayerData(player);
        data.assignNewRace(null);
        data.assignNewFactionInformation(null, null);
        data.assignNewOrigin(null, null);
        return true;
    }
    public static boolean playerPassedOnboarding(Player player){
        PlayerData playerData = getPlayerData(player);
        if(playerData == null) return false;
        return !(playerData.getFaction() == null || playerData.getSpawn() == null);
    }
    public static Faction getPlayerFaction(Player player, Level world){
        PlayerData playerData = getPlayerData(player);
        if(playerData == null) return null;
        ResourceLocation factionId = playerData.getFaction();
        if(factionId == null) return null;
        try{
            return FactionLookup.getFactionById(world, factionId);
        } catch (FactionIdentifierException exception){
            return null;
        }
    }
    public static boolean setNewFactionInformation(Player player, Level world, ResourceLocation factionId){
        PlayerData playerData = getPlayerData(player);
        try{
            Faction faction = FactionLookup.getFactionById(world, factionId);
            setNewFactionInformation(player, world, factionId, faction.getSpawnData().getDefaultSpawn());
            return true;
        } catch (FactionIdentifierException exception){
            return false;
        }
    }
    public static boolean setNewFactionInformation(Player player, Level world, ResourceLocation factionId, ResourceLocation spawnId){
        PlayerData playerData = getPlayerData(player);
        playerData.assignNewFactionInformation(factionId, spawnId);
        return true;
    }
    public static DispositionType getPlayerDisposition(Player player, Level world){
        Faction faction = getPlayerFaction(player, world);
        if(faction == null) return DispositionType.NEUTRAL;
        return faction.getDisposition();
    }
    public static Race getPlayerRace(Player player, Level world){
        PlayerData playerData = getPlayerData(player);
        if(playerData == null) return null;
        ResourceLocation raceId = playerData.getRace();
        if(raceId == null) return null;
        try{
            return RaceLookup.getRace(world, raceId);
        } catch (Exception exception){
            return null;
        }
    }
    public static boolean setRace(Player player, Level world, ResourceLocation raceId){
        Race newRace = RaceLookup.getRace(world, raceId);
        if(newRace == null) return false;
        PlayerData playerData = getPlayerData(player);
        playerData.assignNewRace(raceId);
        newRace.applyPlayerAttributes(player);
        return true;
    }
    public static SpawnData getPlayerSpawnData(Player player, Level world){
        Faction faction = getPlayerFaction(player, world);
        if(faction == null) return null;
        PlayerData playerData = getPlayerData(player);
        if(playerData == null) return null;
        ResourceLocation spawnId = playerData.getSpawn();
        if(spawnId == null) return null;
        return faction.getSpawnData().findSpawn(spawnId);
    }
    public static boolean setSpawn(ServerPlayer player, Level world, ResourceLocation spawnId) {
        Faction faction = getPlayerFaction(player, world);
        if(faction == null) return false;
        if(faction.getSpawnData().findSpawn(spawnId) != null){
            PlayerData data = getPlayerData(player);
            data.assignNewFactionInformation(faction.getId(), spawnId);

            if(ModDimensions.isInMiddleEarth(player.getWorld())){
                player.setSpawnPoint(ModDimensions.ME_WORLD_KEY, getPlayerSpawnData(player, world).getBlockPos(), 0, true, false);
                return true;
            }

            return true;
        }
        return false;
    }
    public static boolean resetSpawn(ServerPlayer player, Level world) {
        Faction faction = getPlayerFaction(player, world);
        if(faction == null) return false;
        SpawnDataHandler spawnDataHandler= faction.getSpawnData();
        if(spawnDataHandler == null) return false;
        setSpawn(player, world, spawnDataHandler.getDefaultSpawn());
        return true;
    }
    public static OriginAggregate getOriginAggregateOrDefault(Player player, Level world){
        PlayerData playerData = getPlayerData(player);
        if(playerData == null) return getDefaultOriginAggregate(world);
        BlockPos originPos = playerData.getOriginPos();
        ResourceLocation dimensionId = playerData.getDimensionOrigin();
        if(originPos == null){
            return getDefaultOriginAggregate(world);
        }
        if(dimensionId == null){
            dimensionId = DimensionTypes.OVERWORLD.getValue();
        }
        return new OriginAggregate(dimensionId, originPos);
    }
    public static OriginAggregate getOriginAggregate(Player player, Level world){
        PlayerData playerData = getPlayerData(player);
        if(playerData == null) return null;
        BlockPos originPos = playerData.getOriginPos();
        ResourceLocation dimensionId = playerData.getDimensionOrigin();
        if(originPos == null){
            return null;
        }
        if(dimensionId == null){
            dimensionId = DimensionTypes.OVERWORLD.getValue();
        }
        return new OriginAggregate(dimensionId, originPos);
    }
    public static boolean setOrigin(ServerPlayer player, Level world, ResourceLocation dimensionId, BlockPos originPos) {
        Registry<DimensionType> registry = world.getRegistryManager().getOptional(Registries.DIMENSION_TYPE).orElse(null);
        if(registry != null){
            if(registry.get(dimensionId) == null){
                PlayerData data = getPlayerData(player);
                data.assignNewOrigin(dimensionId, originPos);
                return true;
            }
        }
        return false;
    }
    public static boolean resetOrigin(ServerPlayer player, Level world) {
        OriginAggregate newOrigin = getDefaultOriginAggregate(world);
        PlayerData data = getPlayerData(player);
        data.assignNewOrigin(newOrigin.dimensionId, newOrigin.origin);
        return true;
    }
    private static OriginAggregate getDefaultOriginAggregate(Level world){
        return new OriginAggregate(
                DimensionTypes.OVERWORLD.getValue(),
                world.getServer().getOverworld().getSpawnPos()
        );
    }

    public record OriginAggregate(ResourceLocation dimensionId, BlockPos origin){

    }
}
