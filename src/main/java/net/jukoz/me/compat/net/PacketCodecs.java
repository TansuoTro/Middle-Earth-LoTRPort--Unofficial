package net.jukoz.me.compat.net;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;
import java.util.function.Supplier;

/**
 * Stand-in for 1.20.5+'s {@code net.minecraft.network.codec.PacketCodecs}.
 *
 * Registry entries resolve against the receiving connection's synced registries. Explicit
 * decode contexts take precedence over the client/server lifecycle suppliers.
 */
public final class PacketCodecs {
    private static final ThreadLocal<DynamicRegistryManager> REGISTRIES = new ThreadLocal<>();
    private static volatile MinecraftServer server;
    private static volatile Supplier<DynamicRegistryManager> clientRegistries = () -> null;

    public static void setServer(MinecraftServer value) {
        server = value;
    }

    public static void setClientRegistries(Supplier<DynamicRegistryManager> supplier) {
        clientRegistries = supplier;
    }

    public static <T> T decode(PacketCodec<PacketByteBuf, T> codec, PacketByteBuf buf,
                               DynamicRegistryManager registries) {
        DynamicRegistryManager previous = REGISTRIES.get();
        REGISTRIES.set(registries);
        try {
            return codec.decode(buf);
        } finally {
            if (previous == null) REGISTRIES.remove();
            else REGISTRIES.set(previous);
        }
    }

    private static DynamicRegistryManager registries() {
        DynamicRegistryManager explicit = REGISTRIES.get();
        if (explicit != null) return explicit;
        MinecraftServer activeServer = server;
        if (activeServer != null && activeServer.isOnThread()) return activeServer.getRegistryManager();
        DynamicRegistryManager client = clientRegistries.get();
        if (client != null) return client;
        return activeServer == null ? null : activeServer.getRegistryManager();
    }

    public static final PacketCodec<PacketByteBuf, net.minecraft.util.Identifier> IDENTIFIER =
            PacketCodec.of((buf, value) -> buf.writeIdentifier(value), PacketByteBuf::readIdentifier);

    public static final PacketCodec<PacketByteBuf, net.minecraft.util.math.BlockPos> BLOCK_POS =
            PacketCodec.of((buf, value) -> buf.writeBlockPos(value), PacketByteBuf::readBlockPos);

    public static final PacketCodec<PacketByteBuf, Boolean> BOOLEAN =
            PacketCodec.of((buf, value) -> buf.writeBoolean(value), PacketByteBuf::readBoolean);

    public static final PacketCodec<PacketByteBuf, Byte> BYTE =
            PacketCodec.of((buf, value) -> buf.writeByte(value), PacketByteBuf::readByte);

    public static final PacketCodec<PacketByteBuf, Integer> INTEGER =
            PacketCodec.of((buf, value) -> buf.writeVarInt(value), PacketByteBuf::readVarInt);

    public static final PacketCodec<PacketByteBuf, Long> LONG =
            PacketCodec.of((buf, value) -> buf.writeLong(value), PacketByteBuf::readLong);

    public static final PacketCodec<PacketByteBuf, Float> FLOAT =
            PacketCodec.of((buf, value) -> buf.writeFloat(value), PacketByteBuf::readFloat);

    public static final PacketCodec<PacketByteBuf, Double> DOUBLE =
            PacketCodec.of((buf, value) -> buf.writeDouble(value), PacketByteBuf::readDouble);

    public static final PacketCodec<PacketByteBuf, String> STRING =
            PacketCodec.of((buf, value) -> buf.writeString(value), PacketByteBuf::readString);

    public static final PacketCodec<PacketByteBuf, NbtCompound> NBT_COMPOUND =
            PacketCodec.of((buf, value) -> buf.writeNbt(value), PacketByteBuf::readNbt);

    public static final PacketCodec<PacketByteBuf, byte[]> BYTE_ARRAY =
            PacketCodec.of((buf, value) -> buf.writeByteArray(value), PacketByteBuf::readByteArray);

    private PacketCodecs() {
    }

    public static <T> PacketCodec<PacketByteBuf, Optional<T>> optional(PacketCodec<PacketByteBuf, T> element) {
        return PacketCodec.of((buf, value) -> {
            boolean present = value != null && value.isPresent();
            buf.writeBoolean(present);
            if (present) {
                element.encode(buf, value.get());
            }
        }, buf -> buf.readBoolean() ? Optional.ofNullable(element.decode(buf)) : Optional.empty());
    }

    public static <T> PacketCodec<PacketByteBuf, List<T>> list(PacketCodec<PacketByteBuf, T> element) {
        return PacketCodec.of((buf, value) -> {
            buf.writeVarInt(value.size());
            for (T t : value) {
                element.encode(buf, t);
            }
        }, buf -> {
            int size = buf.readVarInt();
            List<T> out = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                out.add(element.decode(buf));
            }
            return out;
        });
    }

    public static <T> PacketCodec.Collector<PacketByteBuf, T, List<T>> toList() {
        return PacketCodecs::list;
    }

    public static <T> PacketCodec<PacketByteBuf, T> indexed(IntFunction<T> byId, ToIntFunction<T> toId) {
        return PacketCodec.of((buf, value) -> buf.writeVarInt(toId.applyAsInt(value)),
                buf -> byId.apply(buf.readVarInt()));
    }

    /** NBT round-trip for any data codec; the donor uses it for whole-object fields. */
    public static <T> PacketCodec<PacketByteBuf, T> codec(Codec<T> codec) {
        return PacketCodec.of((buf, value) -> {
            NbtElement encoded = codec.encodeStart(NbtOps.INSTANCE, value).result().orElse(new NbtCompound());
            buf.writeNbt((NbtCompound) encoded);
        }, buf -> {
            NbtCompound nbt = buf.readNbt();
            if (nbt == null) {
                return null;
            }
            return codec.parse(NbtOps.INSTANCE, nbt).result().orElse(null);
        });
    }

    /**
     * Dynamic-registry entry, by id.
     *
     * Named entries retain their registry identity. Inline entries require an element codec;
     * unresolved named entries fail decoding instead of silently erasing tracked entity data.
     */
    public static <T> PacketCodec<PacketByteBuf, RegistryEntry<T>> registryEntry(
            RegistryKey<? extends Registry<T>> registryKey) {
        return registryEntry(registryKey, null);
    }

    public static <T> PacketCodec<PacketByteBuf, RegistryEntry<T>> registryEntry(
            RegistryKey<? extends Registry<T>> registryKey, PacketCodec<PacketByteBuf, T> element) {
        return PacketCodec.of((buf, value) -> {
            if (value == null) {
                buf.writeBoolean(false);
                return;
            }
            buf.writeBoolean(true);
            Identifier id = value.getKey().map(RegistryKey::getValue).orElse(null);
            if (id == null && element == null) {
                throw new IllegalArgumentException("Inline registry entry requires a codec: " + registryKey.getValue());
            }
            buf.writeIdentifier(id == null ? new Identifier("unknown", "unknown") : id);
            if (element != null) {
                element.encode(buf, value.value());
            }
        }, buf -> {
            if (!buf.readBoolean()) {
                return null;
            }
            Identifier id = buf.readIdentifier();
            T inline = element == null ? null : element.decode(buf);
            DynamicRegistryManager manager = registries();
            RegistryEntry<T> resolved = manager == null ? null : manager.getOptional(registryKey)
                    .flatMap(registry -> registry.getEntry(RegistryKey.of(registryKey, id))).orElse(null);
            if (resolved != null) return resolved;
            if (inline != null) return RegistryEntry.of(inline);
            throw new io.netty.handler.codec.DecoderException("Unknown registry entry " + registryKey.getValue()
                    + "/" + id + (manager == null ? " (no registry context)" : ""));
        });
    }
}
