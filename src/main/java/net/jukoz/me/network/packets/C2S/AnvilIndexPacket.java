package net.jukoz.me.network.packets.C2S;

import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.shapingAnvil.ShapingAnvilBlockEntity;
import net.jukoz.me.gui.artisantable.ArtisanTableScreenHandler;
import net.jukoz.me.gui.shapinganvil.ShapingAnvilScreenHandler;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

public class AnvilIndexPacket extends ClientToServerPacket<AnvilIndexPacket> {
    public static final Identifier ID = MiddleEarth.of("anvil_index_packet");
    public static final PacketType<AnvilIndexPacket> TYPE = PacketType.create(ID, AnvilIndexPacket::new);

    public int getIndex() {
        return index;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    private final int index;
    private final double x;
    private final double y;
    private final double z;

    public AnvilIndexPacket(int index, double x, double y, double z) {
        this.index = index;
        this.x = x;
        this.y = y;
        this.z = z;
    }



    public AnvilIndexPacket(PacketByteBuf buf) {
        this(buf.readInt(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    @Override
    public PacketType<AnvilIndexPacket> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeInt(this.index);
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            if(index >= 0) {
                context.player().getServer().execute(() -> {
                    Vec3d coordinates = new Vec3d(x, y, z);
                    ShapingAnvilBlockEntity.updateIndex(index, coordinates, context.player());
                });
            } else {
                ServerPlayerEntity player = context.player();
                ScreenHandler screenHandler = player.currentScreenHandler;
                if (screenHandler instanceof ShapingAnvilScreenHandler anvilScreenHandler) {
                    anvilScreenHandler.updateScreen();
                }
            }
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketAnvilIndex error: ", e);
        }
    }
}
