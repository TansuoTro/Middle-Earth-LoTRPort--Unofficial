package net.fabricmc.fabric.api.networking.v1;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkDirection;
import java.util.HashMap;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.SimpleChannel;

/**
 * Shared plumbing for the Forge networking compat shims. One Forge {@link SimpleChannel} carries
 * every packet, tagged either with a {@link PacketType} discriminator (modern API) or with a raw
 * channel {@link ResourceLocation} (legacy API), so the port keeps both spellings.
 */
final class NetworkingImpl {
    static final ResourceLocation CHANNEL_ID = new ResourceLocation("me", "shim_main");
    static final String PROTOCOL = "1";
    static final int LEGACY = -1;

    static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(CHANNEL_ID, () -> PROTOCOL,
            PROTOCOL::equals, PROTOCOL::equals);

    static final java.util.Map<Integer, PacketType<?>> SERVER_TYPES = new HashMap<>();
    static final java.util.Map<Integer, PacketType<?>> CLIENT_TYPES = new HashMap<>();
    private static int serverIndex = 0;
    private static int clientIndex = 0;

    private NetworkingImpl() {}

    static void trackServer(PacketType<?> type) {
        SERVER_TYPES.put(type.protocolId(), type);
    }

    static void trackClient(PacketType<?> type) {
        CLIENT_TYPES.put(type.protocolId(), type);
    }

    static void ensureServerCarrier() {
        if (serverIndex == 0) {
            CHANNEL.messageBuilder(RawPayload.class, serverIndex++, NetworkDirection.PLAY_TO_SERVER)
                    .encoder(RawPayload::write)
                    .decoder(RawPayload::new)
                    .consumerMainThread((payload, context) -> payload.dispatchServer(context))
                    .add();
        }
    }

    static void ensureClientCarrier() {
        if (clientIndex == 0) {
            CHANNEL.messageBuilder(RawPayload.class, clientIndex++, NetworkDirection.PLAY_TO_CLIENT)
                    .encoder(RawPayload::write)
                    .decoder(RawPayload::new)
                    .consumerMainThread((payload, context) -> payload.dispatchClient(context))
                    .add();
        }
    }

    static PacketType<?> serverType(int typeId) {
        return SERVER_TYPES.get(typeId);
    }

    static PacketType<?> clientType(int typeId) {
        return CLIENT_TYPES.get(typeId);
    }

    static RawPayload server(int typeId, FriendlyByteBuf buf) {
        return new RawPayload(typeId, null, buf);
    }

    static RawPayload server(ResourceLocation channel, FriendlyByteBuf buf) {
        return new RawPayload(LEGACY, channel, buf);
    }

    /** One carrier for both directions; the direction is chosen by the channel it travels on. */
    static final class RawPayload {
        private final int typeId;
        private final ResourceLocation channel;
        private final FriendlyByteBuf buf;

        RawPayload(FriendlyByteBuf buf) {
            this.typeId = buf.readVarInt();
            this.channel = this.typeId == LEGACY ? buf.readResourceLocation() : null;
            this.buf = buf;
        }

        RawPayload(int typeId, ResourceLocation channel, FriendlyByteBuf buf) {
            this.typeId = typeId;
            this.channel = channel;
            this.buf = buf;
        }

        void write(FriendlyByteBuf out) {
            out.writeVarInt(typeId);
            if (channel != null) {
                out.writeResourceLocation(channel);
            }
            out.writeBytes(buf);
        }

        void dispatchServer(net.minecraftforge.network.NetworkEvent.Context context) {
            try {
                ServerPlayNetworking.dispatch(typeId, channel, buf, context.getSender());
            } finally {
                context.setPacketHandled(true);
            }
        }

        void dispatchClient(net.minecraftforge.network.NetworkEvent.Context context) {
            try {
                ClientPlayNetworking.dispatch(typeId, channel, buf);
            } finally {
                context.setPacketHandled(true);
            }
        }
    }
}
