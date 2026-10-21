package net.fabricmc.fabric.api.networking.v1;

import io.netty.buffer.Unpooled;
import net.minecraft.network.FriendlyByteBuf;

/** Forge compat shim for Fabric's {@code PacketByteBufs}. */
public final class PacketByteBufs {
    private PacketByteBufs() {}

    public static FriendlyByteBuf create() {
        return new FriendlyByteBuf(Unpooled.buffer());
    }

    public static FriendlyByteBuf copy(FriendlyByteBuf other) {
        return new FriendlyByteBuf(Unpooled.copiedBuffer(other.nioBuffer()));
    }
}
