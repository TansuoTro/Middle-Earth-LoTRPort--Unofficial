package net.jukoz.me.compat.net;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.RegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Holder;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;

/**
 * Stand-in for 1.20.5+'s {@code net.minecraft.network.codec.PacketCodecs}.
 *
 * Registry entries resolve against the receiving connection's synced registries. Explicit
 * decode contexts take precedence over the client/server lifecycle suppliers.
 */
public final class PacketCodecs {
    private static final ThreadLocal<RegistryAccess> REGISTRIES = new ThreadLocal<>();
    private static volatile MinecraftServer server;
    private static volatile Supplier<RegistryAccess> clientRegistries = () -> null;

    public static void setServer(MinecraftServer value) {
        server = value;
    }

    public static void setClientRegistries(Supplier<RegistryAccess> supplier) {
        clientRegistries = supplier;
    }

    public static <T> T decode(PacketCodec<FriendlyByteBuf, T> codec, FriendlyByteBuf buf,
                               RegistryAccess registries) {
        RegistryAccess previous = REGISTRIES.get();
        REGISTRIES.set(registries);
        try {
            return codec.decode(buf);
        } finally {
            if (previous == null) REGISTRIES.remove();
            else REGISTRIES.set(previous);
        }
    }

    private static RegistryAccess registries() {
        RegistryAccess explicit = REGISTRIES.get();
        if (explicit != null) return explicit;
        MinecraftServer activeServer = server;
        if (activeServer != null && activeServer.isSameThread()) return activeServer.registryAccess();
        RegistryAccess client = clientRegistries.get();
        if (client != null) return client;
        return activeServer == null ? null : activeServer.registryAccess();
    }

    public static final PacketCodec<FriendlyByteBuf, net.minecraft.resources.ResourceLocation> IDENTIFIER =
            PacketCodec.of((buf, value) -> buf.writeIdentifier(value), FriendlyByteBuf::readIdentifier);

    public static final PacketCodec<FriendlyByteBuf, net.minecraft.core.BlockPos> BLOCK_POS =
            PacketCodec.of((buf, value) -> buf.writeBlockPos(value), FriendlyByteBuf::readBlockPos);

    public static final PacketCodec<FriendlyByteBuf, Boolean> BOOLEAN =
            PacketCodec.of((buf, value) -> buf.writeBoolean(value), FriendlyByteBuf::readBoolean);

    public static final PacketCodec<FriendlyByteBuf, Byte> BYTE =
            PacketCodec.of((buf, value) -> buf.writeByte(value), FriendlyByteBuf::readByte);

    public static final PacketCodec<FriendlyByteBuf, Integer> INTEGER =
            PacketCodec.of((buf, value) -> buf.writeVarInt(value), FriendlyByteBuf::readVarInt);

    public static final PacketCodec<FriendlyByteBuf, Long> LONG =
            PacketCodec.of((buf, value) -> buf.writeLong(value), FriendlyByteBuf::readLong);

    public static final PacketCodec<FriendlyByteBuf, Float> FLOAT =
            PacketCodec.of((buf, value) -> buf.writeFloat(value), FriendlyByteBuf::readFloat);

    public static final PacketCodec<FriendlyByteBuf, Double> DOUBLE =
            PacketCodec.of((buf, value) -> buf.writeDouble(value), FriendlyByteBuf::readDouble);

    public static final PacketCodec<FriendlyByteBuf, String> STRING =
            PacketCodec.of((buf, value) -> buf.writeString(value), FriendlyByteBuf::readString);

    public static final PacketCodec<FriendlyByteBuf, CompoundTag> NBT_COMPOUND =
            PacketCodec.of((buf, value) -> buf.writeNbt(value), FriendlyByteBuf::readNbt);

    public static final PacketCodec<FriendlyByteBuf, byte[]> BYTE_ARRAY =
            PacketCodec.of((buf, value) -> buf.writeByteArray(value), FriendlyByteBuf::readByteArray);

    private PacketCodecs() {
    }

    public static <T> PacketCodec<FriendlyByteBuf, Optional<T>> optional(PacketCodec<FriendlyByteBuf, T> element) {
        return PacketCodec.of((buf, value) -> {
            boolean present = value != null && value.isPresent();
            buf.writeBoolean(present);
            if (present) {
                element.encode(buf, value.get());
            }
        }, buf -> buf.readBoolean() ? Optional.ofNullable(element.decode(buf)) : Optional.empty());
    }

    public static <T> PacketCodec<FriendlyByteBuf, List<T>> list(PacketCodec<FriendlyByteBuf, T> element) {
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

    public static <T> PacketCodec.Collector<FriendlyByteBuf, T, List<T>> toList() {
        return PacketCodecs::list;
    }

    public static <T> PacketCodec<FriendlyByteBuf, T> indexed(IntFunction<T> byId, ToIntFunction<T> toId) {
        return PacketCodec.of((buf, value) -> buf.writeVarInt(toId.applyAsInt(value)),
                buf -> byId.apply(buf.readVarInt()));
    }

    /** NBT round-trip for any data codec; the donor uses it for whole-object fields. */
    public static <T> PacketCodec<FriendlyByteBuf, T> codec(Codec<T> codec) {
        return PacketCodec.of((buf, value) -> {
            Tag encoded = codec.encodeStart(NbtOps.INSTANCE, value).result().orElse(new CompoundTag());
            buf.writeNbt((CompoundTag) encoded);
        }, buf -> {
            CompoundTag nbt = buf.readNbt();
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
    public static <T> PacketCodec<FriendlyByteBuf, Holder<T>> registryEntry(
            ResourceKey<? extends Registry<T>> registryKey) {
        return registryEntry(registryKey, null);
    }

    public static <T> PacketCodec<FriendlyByteBuf, Holder<T>> registryEntry(
            ResourceKey<? extends Registry<T>> registryKey, PacketCodec<FriendlyByteBuf, T> element) {
        return PacketCodec.of((buf, value) -> {
            if (value == null) {
                buf.writeBoolean(false);
                return;
            }
            buf.writeBoolean(true);
            ResourceLocation id = value.getKey().map(ResourceKey::getValue).orElse(null);
            if (id == null && element == null) {
                throw new IllegalArgumentException("Inline registry entry requires a codec: " + registryKey.location());
            }
            buf.writeIdentifier(id == null ? new ResourceLocation("unknown", "unknown") : id);
            if (element != null) {
                element.encode(buf, value.value());
            }
        }, buf -> {
            if (!buf.readBoolean()) {
                return null;
            }
            ResourceLocation id = buf.readResourceLocation();
            T inline = element == null ? null : element.decode(buf);
            RegistryAccess manager = registries();
            Holder<T> resolved = manager == null ? null : manager.getOptional(registryKey)
                    .flatMap(registry -> registry.getEntry(ResourceKey.create(registryKey, id))).orElse(null);
            if (resolved != null) return resolved;
            if (inline != null) return Holder.of(inline);
            throw new io.netty.handler.codec.DecoderException("Unknown registry entry " + registryKey.location()
                    + "/" + id + (manager == null ? " (no registry context)" : ""));
        });
    }
}
