package net.jukoz.me.network.packets.S2C;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.map.MapScreenController;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.network.packets.ServerToClientPacket;

public class PacketOpenMapScreen extends ServerToClientPacket<PacketOpenMapScreen> {
    public static final ResourceLocation ID = MiddleEarth.of("packet_open_map_screen");
    public static final PacketType<PacketOpenMapScreen> TYPE = PacketType.create(ID, PacketOpenMapScreen::new);
    private final boolean canTeleport;

    public PacketOpenMapScreen(boolean canTeleport) {
        this.canTeleport = canTeleport;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void process(ClientPacketContext context) {
        Player player = context.player();
        MapScreenController controller = new MapScreenController(player.level(), player);
        controller.open(canTeleport);
    }

    public PacketOpenMapScreen(FriendlyByteBuf buf) {
        this(buf.readBoolean());
    }

    @Override
    public PacketType<PacketOpenMapScreen> getType() {
        return TYPE;
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(this.canTeleport);
    }

}
