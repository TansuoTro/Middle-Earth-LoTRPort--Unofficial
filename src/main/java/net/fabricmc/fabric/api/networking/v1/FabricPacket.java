package net.fabricmc.fabric.api.networking.v1;

import net.minecraft.network.FriendlyByteBuf;

/** Forge compat shim for Fabric's {@code FabricPacket}. */
public interface FabricPacket {
    void write(FriendlyByteBuf buf);

    PacketType<?> getType();
}
