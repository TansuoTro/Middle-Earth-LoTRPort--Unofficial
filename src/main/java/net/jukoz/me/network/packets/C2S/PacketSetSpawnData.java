package net.jukoz.me.network.packets.C2S;

import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.jukoz.me.resources.persistent_datas.PlayerDataService;

public class PacketSetSpawnData extends ClientToServerPacket<PacketSetSpawnData>
{
    public static final ResourceLocation ID = new ResourceLocation(MiddleEarth.MOD_ID, "packet_spawn_data");

    public static final PacketType<PacketSetSpawnData> TYPE = PacketType.create(ID, PacketSetSpawnData::new);

    private final int overworldX;
    private final int overworldY;
    private final int overworldZ;
    public PacketSetSpawnData(int overworldX, int overworldY, int overworldZ){
        this.overworldX = overworldX;
        this.overworldY = overworldY;
        this.overworldZ = overworldZ;
    }

    public PacketSetSpawnData(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readInt(), buf.readInt());
    }

    @Override
    public PacketType<PacketSetSpawnData> getType() {
        return TYPE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.overworldX);
        buf.writeInt(this.overworldY);
        buf.writeInt(this.overworldZ);
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {

                MinecraftServer server = context.player().getServer();
                ServerPlayer player = server.getPlayerList().getPlayer(context.player().getUuid());

                BlockPos overworldSpawnBlockpos = new BlockPos(overworldX, overworldY, overworldZ);
                PlayerDataService.setOrigin(player, player.level(), BuiltinDimensionTypes.OVERWORLD_ID, overworldSpawnBlockpos);
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
