package net.jukoz.me.network.packets.C2S;

import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.artisantable.ArtisanTableScreenHandler;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class ArtisanTableTabPacket extends ClientToServerPacket<ArtisanTableTabPacket> {
    public static final Identifier ID = MiddleEarth.of("artisan_table_tab_packet");
    public static final PacketType<ArtisanTableTabPacket> TYPE = PacketType.create(ID, ArtisanTableTabPacket::new);

    public String getShapeId() {
        return shapeId;
    }

    public int getSyncId() {
        return syncId;
    }

    private final String shapeId;
    private final int syncId;

    public ArtisanTableTabPacket(String shapeId, int syncId) {
        this.shapeId = shapeId;
        this.syncId = syncId;
    }



    public ArtisanTableTabPacket(PacketByteBuf buf) {
        this(buf.readString(), buf.readInt());
    }

    @Override
    public PacketType<ArtisanTableTabPacket> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeString(this.shapeId);
        buf.writeInt(this.syncId);
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                ServerPlayerEntity player = context.player();
                ScreenHandler screenHandler = player.currentScreenHandler;
                if (screenHandler.syncId == this.syncId && screenHandler instanceof ArtisanTableScreenHandler artisanTableScreenHandler) {
                    artisanTableScreenHandler.changeTab(shapeId);
                }
            });
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("Artisan Table Packet error: ", e);
        }
    }
}
