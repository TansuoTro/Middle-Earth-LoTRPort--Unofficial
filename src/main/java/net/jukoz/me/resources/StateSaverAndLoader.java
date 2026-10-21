package net.jukoz.me.resources;

import net.minecraft.world.entity.player.Player;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.Level;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import java.util.HashMap;
import java.util.UUID;

/**
 * <a href="https://fabricmc.net/wiki/tutorial:persistent_states">Documentation</a><br/>
 * <a href="https://github.com/TerraformersMC/Biolith/blob/main/common/src/main/java/com/terraformersmc/biolith/impl/config/BiolithState.java">Other Channel</a>
 */
public class StateSaverAndLoader extends SavedData {
    private static final String KEY = "middle_earth_player_datas";

    private HashMap<UUID, PlayerData> players;

    public StateSaverAndLoader() {
        this.players = new HashMap<>();
    }
    public StateSaverAndLoader(CompoundTag nbt){
        this.players = new HashMap<>();
        ListTag list = nbt.getList("list", Tag.COMPOUND_TYPE);
        for(int i = 0; i < list.size(); i++){
            CompoundTag playerDataNbt = list.getCompound(i);
            if(!playerDataNbt.contains("uuid", Tag.STRING_TYPE)) continue;
            PlayerData newPlayerData = new PlayerData(playerDataNbt.getCompound("data"));
            this.players.put(UUID.fromString(playerDataNbt.getString("uuid")), newPlayerData);
        }
    }

    @Override
    public CompoundTag writeNbt(CompoundTag nbt) {
        nbt.put("list", getPlayerDataNbt().getList("list", Tag.COMPOUND_TYPE));
        return nbt;
    }

    public static StateSaverAndLoader createNew() {
        StateSaverAndLoader state = new StateSaverAndLoader();
        state.players = new HashMap<>();
        return state;
    }

    private CompoundTag getPlayerDataNbt() {
        CompoundTag nbt = new CompoundTag();
        ListTag list = new ListTag();
        for(UUID uuid : players.keySet()){
            CompoundTag specificNbt = new CompoundTag();
            specificNbt.putString("uuid", uuid.toString());
            specificNbt.put("data", this.players.get(uuid).createNbt());
            list.add(specificNbt);
        }
        nbt.put("list", list);
        return nbt;
    }

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        DimensionDataStorage persistentStateManager = server.getWorld(Level.OVERWORLD).getPersistentStateManager();
        StateSaverAndLoader state = persistentStateManager.getOrCreate(StateSaverAndLoader::new, StateSaverAndLoader::new, KEY);
        state.markDirty();
        return state;
    }
    public static PlayerData getPlayerState(Player player) {
        if(player == null) return null;
        StateSaverAndLoader serverState = getServerState((MinecraftServer) player.getServer());
        UUID playerUUID = player.getUuid();
        serverState.players.computeIfAbsent(playerUUID, k -> new PlayerData());
        return serverState.players.get(playerUUID);
    }

}
