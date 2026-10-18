package net.jukoz.me.network.packets;

import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.jukoz.me.network.contexts.ServerPacketContext;

/** Mirror of {@link ServerToClientPacket} for the client to server direction. */
public abstract class ClientToServerPacket<T extends ClientToServerPacket<T>> implements FabricPacket {
    public abstract void process(ServerPacketContext context);
}
