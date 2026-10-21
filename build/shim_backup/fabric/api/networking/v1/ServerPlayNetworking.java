package net.fabricmc.fabric.api.networking.v1;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.PacketDistributor;

import java.util.HashMap;
import java.util.Map;

/** Forge compat shim for Fabric's {@code ServerPlayNetworking} (modern + legacy APIs). */
public final class ServerPlayNetworking {
    /** Fabric's modern channel handler: {@code (packet, player, responseSender) -> ...}. */
    public interface PlayPacketHandler<T> {
        void receive(T packet, ServerPlayer player, PacketSender responseSender);
    }

    /** Fabric's legacy channel handler: {@code (buf, player) -> ...}. */
    public interface PlayChannelHandler {
        void receive(FriendlyByteBuf buf, ServerPlayer player);
    }

    private static final Map<Integer, PlayPacketHandler<?>> PACKET_HANDLERS = new HashMap<>();
    private static final Map<ResourceLocation, PlayChannelHandler> CHANNEL_HANDLERS = new HashMap<>();

    private ServerPlayNetworking() {}

    public static <T extends FabricPacket> PacketType<T> registerGlobalReceiver(PacketType<T> type,
                                                                                PlayPacketHandler<T> handler) {
        PACKET_HANDLERS.put(type.protocolId(), handler);
        NetworkingImpl.trackServer(type);
        NetworkingImpl.ensureServerCarrier();
        return type;
    }

    public static void registerGlobalReceiver(ResourceLocation channel, PlayChannelHandler handler) {
        CHANNEL_HANDLERS.put(channel, handler);
        NetworkingImpl.ensureServerCarrier();
    }

    public static <T extends FabricPacket> void send(ServerPlayer player, PacketType<T> type,
                                                     FriendlyByteBuf buf) {
        NetworkingImpl.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),
                NetworkingImpl.server(type.protocolId(), buf));
    }

    public static void send(ServerPlayer player, ResourceLocation channel, FriendlyByteBuf buf) {
        NetworkingImpl.CHANNEL.send(PacketDistributor.PLAYER.with(() -> player),
                NetworkingImpl.server(channel, buf));
    }

    public static void send(ServerPlayer player, FabricPacket packet) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        packet.write(buf);
        send(player, packet.getType(), buf);
    }

    public static void send(CommandSourceStack source, FabricPacket packet) {
        send(source.getPlayerOrException(), packet);
    }

    public static boolean canSend(ServerPlayer player, PacketType<?> type) {
        return true;
    }

    @SuppressWarnings("unchecked")
    static void dispatch(int typeId, ResourceLocation channel, FriendlyByteBuf buf, ServerPlayer player) {
        if (channel != null) {
            PlayChannelHandler handler = CHANNEL_HANDLERS.get(channel);
            if (handler != null) {
                handler.receive(buf, player);
            }
            return;
        }
        PlayPacketHandler<?> handler = PACKET_HANDLERS.get(typeId);
        PacketType<?> type = NetworkingImpl.serverType(typeId);
        if (handler == null || type == null) {
            return;
        }
        handler.receive(type.read(buf), player, () -> {
        });
    }
}
