package net.jukoz.me.network.packets;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.jukoz.me.network.contexts.ClientPacketContext;

/**
 * Server to client packet.
 *
 * <p>1.21.8 builds these on vanilla's {@code CustomPayload} + {@code PacketCodec} registry,
 * which 1.20.1 does not have. Fabric's {@link FabricPacket} covers the same ground with a
 * {@code write(PacketByteBuf)} / {@code PacketType} pair, so each packet keeps its own
 * fields and gains a buffer constructor and a writer instead of a codec.
 */
public abstract class ServerToClientPacket<T extends ServerToClientPacket<T>> implements FabricPacket {
    public abstract void process(ClientPacketContext context);
}
