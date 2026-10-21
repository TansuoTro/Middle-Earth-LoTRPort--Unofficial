package net.jukoz.me.network.packets.C2S;

import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.artisantable.ArtisanTableScreenHandler;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;

public class ArtisanTableTabPacket extends ClientToServerPacket<ArtisanTableTabPacket> {
    public static final ResourceLocation ID = MiddleEarth.of("artisan_table_tab_packet");
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

    public ArtisanTableTabPacket(FriendlyByteBuf buf) {
        this(buf.readUtf(), buf.readInt());
    }

    @Override
    public PacketType<ArtisanTableTabPacket> getType() {
        return TYPE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeString(this.shapeId);
        buf.writeInt(this.syncId);
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                ServerPlayer player = context.player();
                AbstractContainerMenu screenHandler = player.currentScreenHandler;
                if (screenHandler.syncId == this.syncId && screenHandler instanceof ArtisanTableScreenHandler artisanTableScreenHandler) {
                    artisanTableScreenHandler.changeTab(shapeId);
                }
            });
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("Artisan Table Packet error: ", e);
        }
    }
}
