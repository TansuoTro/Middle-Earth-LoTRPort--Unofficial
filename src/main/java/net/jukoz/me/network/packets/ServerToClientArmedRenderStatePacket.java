package net.jukoz.me.network.packets;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.jukoz.me.network.contexts.ClientPacketContext;
import net.jukoz.me.network.contexts.RenderStatePacketContext;
import net.jukoz.me.network.packets.S2C.PacketLivingEntityData;

public abstract class ServerToClientArmedRenderStatePacket<T extends ServerToClientArmedRenderStatePacket<T>> implements CustomPayload {
    @Override
    public abstract Id<T> getId();
    public abstract PacketCodec<RegistryByteBuf, T> streamCodec();
    public abstract void process(RenderStatePacketContext context);
}