package net.jukoz.me.network.packets.S2C;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.map.MapScreenController;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.network.packets.ServerToClientPacket;

public class PacketOpenMapScreen extends ServerToClientPacket<PacketOpenMapScreen> {
    public static final Identifier ID = MiddleEarth.of("packet_open_map_screen");
    public static final PacketType<PacketOpenMapScreen> TYPE = PacketType.create(ID, PacketOpenMapScreen::new);
    private final boolean canTeleport;

    public PacketOpenMapScreen(boolean canTeleport) {
        this.canTeleport = canTeleport;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void process(ClientPacketContext context) {
        PlayerEntity player = context.player();
        MapScreenController controller = new MapScreenController(player.getWorld(), player);
        controller.open(canTeleport);
    }

    public PacketOpenMapScreen(PacketByteBuf buf) {
        this(buf.readBoolean());
    }

    @Override
    public PacketType<PacketOpenMapScreen> getType() {
        return TYPE;
    }

    @Override
    public void write(PacketByteBuf buf) {
        buf.writeBoolean(this.canTeleport);
    }


}
