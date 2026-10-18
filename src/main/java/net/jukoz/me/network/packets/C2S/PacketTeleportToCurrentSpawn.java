package net.jukoz.me.network.packets.C2S;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.resources.datas.factions.data.SpawnData;
import net.jukoz.me.resources.persistent_datas.PlayerDataService;
import net.jukoz.me.world.dimension.ModDimensions;


public class PacketTeleportToCurrentSpawn extends ClientToServerPacket<PacketTeleportToCurrentSpawn> {
    public static final Identifier ID = new Identifier(MiddleEarth.MOD_ID, "packet_teleport_current_spawn");
    public static final PacketType<PacketTeleportToCurrentSpawn> TYPE = PacketType.create(ID, PacketTeleportToCurrentSpawn::new);
    private Boolean welcomeNeeded;

    public PacketTeleportToCurrentSpawn(boolean welcomeNeeded){
        this.welcomeNeeded = welcomeNeeded;
    }





    public PacketTeleportToCurrentSpawn(PacketByteBuf buf) {
        this(buf.readBoolean());
    }

    @Override
    public PacketType<PacketTeleportToCurrentSpawn> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBoolean(this.welcomeNeeded);
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                if(PlayerDataService.playerPassedOnboarding(context.player())){
                    SpawnData spawnData = PlayerDataService.getPlayerSpawnData(context.player(), context.player().getWorld());
                    if(spawnData == null)
                        return;
                    BlockPos spawnCoordinates = spawnData.getBlockPos();
                    if(spawnCoordinates != null)
                        ModDimensions.teleportPlayerToMe(context.player(), spawnCoordinates.toCenterPos(), true, welcomeNeeded);

                }
            });
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("TeleportToMeSpawnRequestPacket::Apply - Tried applying the teleport to me request packet",e);
        }
    }
}