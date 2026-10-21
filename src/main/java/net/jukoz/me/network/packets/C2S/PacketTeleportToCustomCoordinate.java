package net.jukoz.me.network.packets.C2S;

import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class PacketTeleportToCustomCoordinate extends ClientToServerPacket<PacketTeleportToCustomCoordinate> {
    public static final ResourceLocation ID = new ResourceLocation(MiddleEarth.MOD_ID, "packet_teleport_custom_spawn");
    public static final PacketType<PacketTeleportToCustomCoordinate> TYPE = PacketType.create(ID, PacketTeleportToCustomCoordinate::new);
    private final double xCoordinate;
    private final double yCoordinate;
    private final double zCoordinate;
    private final boolean welcomeNeeded;

    public PacketTeleportToCustomCoordinate(Double xCoordinate, Double yCoordinate, Double zCoordinate, boolean welcomeNeeded){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.zCoordinate = zCoordinate;
        this.welcomeNeeded = welcomeNeeded;
    }



    public PacketTeleportToCustomCoordinate(FriendlyByteBuf buf) {
        this(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readBoolean());
    }

    @Override
    public PacketType<PacketTeleportToCustomCoordinate> getType() {
        return TYPE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeDouble(this.xCoordinate);
        buf.writeDouble(this.yCoordinate);
        buf.writeDouble(this.zCoordinate);
        buf.writeBoolean(this.welcomeNeeded);
    }

    @Override
    public void process(ServerPacketContext context) {
        context.player().getServer().execute(() -> {
            Vec3 coordinates = new Vec3(xCoordinate, yCoordinate, zCoordinate);
            ModDimensions.teleportPlayerToMe(context.player(), coordinates, true, welcomeNeeded);
        });
    }
}
