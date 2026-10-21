package net.jukoz.me.network.packets.C2S;

import me.lucko.fabric.api.permissions.v0.Permissions;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.network.contexts.ServerPacketContext;
import net.jukoz.me.network.packets.ClientToServerPacket;
import net.jukoz.me.permissions.PermissionsME;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;

public class PacketTeleportToDynamicWorldCoordinate extends ClientToServerPacket<PacketTeleportToDynamicWorldCoordinate> {
    public static final ResourceLocation ID = new ResourceLocation(MiddleEarth.MOD_ID, "packet_teleport_dynamic_world_coordinate");
    public static final PacketType<PacketTeleportToDynamicWorldCoordinate> TYPE = PacketType.create(ID, PacketTeleportToDynamicWorldCoordinate::new);
    private final double xCoordinate;
    private final double zCoordinate;

    public PacketTeleportToDynamicWorldCoordinate(double xCoordinate, double zCoordinate){
        this.xCoordinate = xCoordinate;
        this.zCoordinate = zCoordinate;
    }



    public PacketTeleportToDynamicWorldCoordinate(FriendlyByteBuf buf) {
        this(buf.readDouble(), buf.readDouble());
    }

    @Override
    public PacketType<PacketTeleportToDynamicWorldCoordinate> getType() {
        return TYPE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeDouble(this.xCoordinate);
        buf.writeDouble(this.zCoordinate);
    }

    @Override
    public void process(ServerPacketContext context) {
        context.player().getServer().execute(() -> {
            if(!PermissionsME.checkMapTeleport(context.player()))
                return;
            Vec3 coordinates = new Vec3(xCoordinate, ModDimensions.getDimensionHeight((int)xCoordinate, (int)zCoordinate).y, zCoordinate);
            ModDimensions.teleportPlayerToMe(context.player(), coordinates, false, false);
        });
    }
}
