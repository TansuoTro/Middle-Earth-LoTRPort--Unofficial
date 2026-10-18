package net.jukoz.me.network.packets.C2S;

import net.minecraft.world.dimension.DimensionTypes;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.jukoz.me.resources.persistent_datas.PlayerDataService;

public class PacketSetSpawnData extends ClientToServerPacket<PacketSetSpawnData>
{
    public static final Identifier ID = new Identifier(MiddleEarth.MOD_ID, "packet_spawn_data");

    public static final PacketType<PacketSetSpawnData> TYPE = PacketType.create(ID, PacketSetSpawnData::new);

    private final int overworldX;
    private final int overworldY;
    private final int overworldZ;
    public PacketSetSpawnData(int overworldX, int overworldY, int overworldZ){
        this.overworldX = overworldX;
        this.overworldY = overworldY;
        this.overworldZ = overworldZ;
    }



    public PacketSetSpawnData(PacketByteBuf buf) {
        this(buf.readInt(), buf.readInt(), buf.readInt());
    }

    @Override
    public PacketType<PacketSetSpawnData> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(this.overworldX);
        buf.writeInt(this.overworldY);
        buf.writeInt(this.overworldZ);
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {

                MinecraftServer server = context.player().getServer();
                ServerPlayerEntity player = server.getPlayerManager().getPlayer(context.player().getUuid());


                BlockPos overworldSpawnBlockpos = new BlockPos(overworldX, overworldY, overworldZ);
                PlayerDataService.setOrigin(player, player.getWorld(), DimensionTypes.OVERWORLD_ID, overworldSpawnBlockpos);
            });
        } catch (Exception e){
            MiddleEarth.LOGGER.logError("SpawnDataPacket::Apply - Tried applying the spawn data packet",e);
        }
    }

    @Override
    public String toString() {
        return "Overworld="+overworldX+","+overworldY+","+overworldZ+";";
    }
}