package net.jukoz.me.resources;


import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.jukoz.me.resources.persistent_datas.PlayerData;

import java.util.HashMap;
import java.util.UUID;
/**
 * <a href="https://fabricmc.net/wiki/tutorial:persistent_states">Documentation</a><br/>
 * <a href="https://github.com/TerraformersMC/Biolith/blob/main/common/src/main/java/com/terraformersmc/biolith/impl/config/BiolithState.java">Other Source</a>
 */
public class StateSaverAndLoader extends PersistentState {
    private static final String KEY = "middle_earth_player_datas";

    private HashMap<UUID, PlayerData> players;

    public StateSaverAndLoader() {
        this.players = new HashMap<>();
    }
    public StateSaverAndLoader(NbtCompound nbt){
        this.players = new HashMap<>();
        NbtList list = nbt.getList("list", NbtElement.COMPOUND_TYPE);
        for(int i = 0; i < list.size(); i++){
            NbtCompound playerDataNbt = list.getCompound(i);
            if(!playerDataNbt.contains("uuid", NbtElement.STRING_TYPE)) continue;
            PlayerData newPlayerData = new PlayerData(playerDataNbt.getCompound("data"));
            this.players.put(UUID.fromString(playerDataNbt.getString("uuid")), newPlayerData);
        }
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.put("list", getPlayerDataNbt().getList("list", NbtElement.COMPOUND_TYPE));
        return nbt;
    }

    public static StateSaverAndLoader createNew() {
        StateSaverAndLoader state = new StateSaverAndLoader();
        state.players = new HashMap<>();
        return state;
    }

    private NbtCompound getPlayerDataNbt() {
        NbtCompound nbt = new NbtCompound();
        NbtList list = new NbtList();
        for(UUID uuid : players.keySet()){
            NbtCompound specificNbt = new NbtCompound();
            specificNbt.putString("uuid", uuid.toString());
            specificNbt.put("data", this.players.get(uuid).createNbt());
            list.add(specificNbt);
        }
        nbt.put("list", list);
        return nbt;
    }

    public static StateSaverAndLoader getServerState(MinecraftServer server) {
        PersistentStateManager persistentStateManager = server.getWorld(World.OVERWORLD).getPersistentStateManager();
        StateSaverAndLoader state = persistentStateManager.getOrCreate(StateSaverAndLoader::new, StateSaverAndLoader::new, KEY);
        state.markDirty();
        return state;
    }
    public static PlayerData getPlayerState(PlayerEntity player) {
        if(player == null) return null;
        StateSaverAndLoader serverState = getServerState((MinecraftServer) player.getServer());
        UUID playerUUID = player.getUuid();
        serverState.players.computeIfAbsent(playerUUID, k -> new PlayerData());
        return serverState.players.get(playerUUID);
    }

}






