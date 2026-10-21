package net.jukoz.me.network.packets.C2S;

import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.shapingAnvil.ShapingAnvilBlockEntity;
import net.jukoz.me.gui.artisantable.ArtisanTableScreenHandler;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;

public class ArtisanIndexPacket extends ClientToServerPacket<ArtisanIndexPacket> {
    public static final ResourceLocation ID = MiddleEarth.of("artisan_index_packet");
    public static final PacketType<ArtisanIndexPacket> TYPE = PacketType.create(ID, ArtisanIndexPacket::new);

    public int getIndex() {
        return index;
    }

    public int getSyncId() {
        return syncId;
    }

    private final int index;
    private final int syncId;

    public ArtisanIndexPacket(int index, int syncId) {
        this.index = index;
        this.syncId = syncId;
    }



    public ArtisanIndexPacket(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readInt());
    }

    @Override
    public PacketType<ArtisanIndexPacket> getType() {
        return TYPE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeInt(this.index);
        buf.writeInt(this.syncId);
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                ServerPlayer player = context.player();
                ScreenHandler screenHandler = player.currentScreenHandler;
                if (screenHandler.syncId == this.syncId && screenHandler instanceof ArtisanTableScreenHandler artisanTableScreenHandler) {
                    artisanTableScreenHandler.updateIndex(index);
                }
            });
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("Artisan Table Packet error: ", e);
        }
    }
}
