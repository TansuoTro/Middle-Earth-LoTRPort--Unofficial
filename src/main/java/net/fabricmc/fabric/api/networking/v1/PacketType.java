package net.fabricmc.fabric.api.networking.v1;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

/** Forge compat shim for Fabric's {@code PacketType}. */
public final class PacketType<T extends FabricPacket> {
    private static int nextId = 0;

    private final ResourceLocation id;
    private final int protocolId;
    private final PacketReader<T> reader;

    private PacketType(ResourceLocation id, PacketReader<T> reader) {
        this.id = id;
        this.protocolId = nextId++;
        this.reader = reader;
    }

    public static <T extends FabricPacket> PacketType<T> create(ResourceLocation id, PacketReader<T> reader) {
        return new PacketType<>(id, reader);
    }

    public ResourceLocation getId() {
        return id;
    }

    /** Internal discriminator used by the Forge channel payload. */
    public int protocolId() {
        return protocolId;
    }

    public T read(FriendlyByteBuf buf) {
        return reader.read(buf);
    }

    public interface PacketReader<T> {
        T read(FriendlyByteBuf buf);
    }

    /** Convenience so callers can pass a constructor reference. */
    public static <T extends FabricPacket> PacketType<T> create(ResourceLocation id,
                                                                Function<FriendlyByteBuf, T> factory) {
        return create(id, factory::apply);
    }
}
