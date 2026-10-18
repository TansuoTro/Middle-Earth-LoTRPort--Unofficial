package net.jukoz.me.network.packets.C2S;

import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.forge.ForgeBlockEntity;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;

public class ForgeModeSwitchPacket extends ClientToServerPacket<ForgeModeSwitchPacket> {
    public static final Identifier ID = MiddleEarth.of("forge_mode_switch_packet");
    public static final PacketType<ForgeModeSwitchPacket> TYPE = PacketType.create(ID, ForgeModeSwitchPacket::new);

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    private final double x;
    private final double y;
    private final double z;

    public ForgeModeSwitchPacket(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }



    public ForgeModeSwitchPacket(PacketByteBuf buf) {
        this(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    @Override
    public PacketType<ForgeModeSwitchPacket> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeDouble(this.x);
        buf.writeDouble(this.y);
        buf.writeDouble(this.z);
    }

    @Override
    public void process(ServerPacketContext context) {
        try{
            context.player().getServer().execute(() -> {
                Vec3d coordinates = new Vec3d(x, y, z);
                ForgeBlockEntity.switchMode(coordinates, context.player());
            });
        }catch (Exception e){
            MiddleEarth.LOGGER.logError("PacketForgeOutput error: ", e);
        }
    }
}
