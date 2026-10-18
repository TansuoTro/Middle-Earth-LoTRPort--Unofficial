package net.jukoz.me.network.packets.C2S;

import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.world.dimension.ModDimensions;
import net.jukoz.me.world.map.MiddleEarthMapUtils;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.joml.Vector2i;

public class PacketTeleportToDynamicCoordinate extends ClientToServerPacket<PacketTeleportToDynamicCoordinate> {
    public static final Identifier ID = new Identifier(MiddleEarth.MOD_ID, "packet_teleport_dynamic_spawn");
    public static final PacketType<PacketTeleportToDynamicCoordinate> TYPE = PacketType.create(ID, PacketTeleportToDynamicCoordinate::new);
    private final double xCoordinate;
    private final double zCoordinate;
    private final boolean welcomeNeeded;

    public PacketTeleportToDynamicCoordinate(double xCoordinate, double zCoordinate, boolean welcomeNeeded){
        this.xCoordinate = xCoordinate;
        this.zCoordinate = zCoordinate;
        this.welcomeNeeded = welcomeNeeded;
    }



    public PacketTeleportToDynamicCoordinate(PacketByteBuf buf) {
        this(buf.readDouble(), buf.readDouble(), buf.readBoolean());
    }

    @Override
    public PacketType<PacketTeleportToDynamicCoordinate> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeDouble(this.xCoordinate);
        buf.writeDouble(this.zCoordinate);
        buf.writeBoolean(this.welcomeNeeded);
    }

    @Override
    public void process(ServerPacketContext context) {
        context.player().getServer().execute(() -> {
            Vector2i worldCoordinate = MiddleEarthMapUtils.getInstance().getWorldCoordinateFromInitialMap((int) xCoordinate, (int) zCoordinate);
            MinecraftServer server = context.player().getServer();
            Vec3d coordinates = new Vec3d(worldCoordinate.x, ModDimensions.getDimensionHeight((int)worldCoordinate.x, (int)worldCoordinate.y).y, worldCoordinate.y);
            ModDimensions.teleportPlayerToMe(context.player(), coordinates, true, welcomeNeeded);
        });
    }
}
