package net.fabricmc.fabric.api.networking.v1;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

/** Forge compat shim for Fabric's {@code ClientPlayNetworking} (modern + legacy APIs). */
public final class ClientPlayNetworking {
    /** Fabric's modern channel handler: {@code (packet, client, handler, responseSender) -> ...}. */
    public interface PlayPacketHandler<T> {
        void receive(T packet, Minecraft client, ClientPacketListener handler, PacketSender responseSender);
    }

    /** Fabric's legacy channel handler: {@code (client, handler, buf, responseSender) -> ...}. */
    public interface PlayChannelHandler {
        void receive(Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf,
                     PacketSender responseSender);
    }

    private static final Map<Integer, PlayPacketHandler<?>> PACKET_HANDLERS = new HashMap<>();
    private static final Map<ResourceLocation, PlayChannelHandler> CHANNEL_HANDLERS = new HashMap<>();

    private ClientPlayNetworking() {}

    public static <T extends FabricPacket> PacketType<T> registerGlobalReceiver(PacketType<T> type,
                                                                                PlayPacketHandler<T> handler) {
        PACKET_HANDLERS.put(type.protocolId(), handler);
        NetworkingImpl.trackClient(type);
        NetworkingImpl.ensureClientCarrier();
        return type;
    }

    public static void registerGlobalReceiver(ResourceLocation channel, PlayChannelHandler handler) {
        CHANNEL_HANDLERS.put(channel, handler);
        NetworkingImpl.ensureClientCarrier();
    }

    public static void send(FabricPacket packet) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        packet.write(buf);
        send(packet.getType(), buf);
    }

    public static void send(PacketType<?> type, FriendlyByteBuf buf) {
        NetworkingImpl.sendToServer(buf, type.getId());
    }

    public static void send(ResourceLocation channel, FriendlyByteBuf buf) {
        NetworkingImpl.sendToServer(buf, channel);
    }

    public static boolean canSend(PacketType<?> type) {
        return true;
    }

    @SuppressWarnings("unchecked")
    static void dispatch(int typeId, ResourceLocation channel, FriendlyByteBuf buf) {
        Minecraft client = Minecraft.getInstance();
        ClientPacketListener listener = client.getConnection();
        if (channel != null) {
            PlayChannelHandler handler = CHANNEL_HANDLERS.get(channel);
            if (handler != null) {
                handler.receive(client, listener, buf, () -> {
                });
            }
            return;
        }
        PlayPacketHandler<?> handler = PACKET_HANDLERS.get(typeId);
        PacketType<?> type = NetworkingImpl.clientType(typeId);
        if (handler == null || type == null) {
            return;
        }
        handler.receive(type.read(buf), client, listener, () -> {
        });
    }
}
