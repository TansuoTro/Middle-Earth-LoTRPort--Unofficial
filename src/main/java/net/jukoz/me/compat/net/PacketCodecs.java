package net.jukoz.me.compat.net;

import com.mojang.serialization.Codec;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

/**
 * Stand-in for 1.20.5+'s {@code net.minecraft.network.codec.PacketCodecs}.
 *
 * <p>Only the members the donor actually uses are here. {@link #registryEntry} is the one
 * that cannot be finished on 1.20.1: a dynamic-registry entry has no static registry to
 * resolve an id against while decoding a packet, so the id is written but the decode side
 * returns {@code null}. See PORTING_NOTES.md.
 */
public final class PacketCodecs {

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
     * <p>TODO(port): 1.20.1 cannot resolve a dynamic registry id from inside a packet decode -
     * there is no registry manager on the wire. The id is written correctly, but decoding
     * returns {@code null} until the value is rebound against the world's registry manager.
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
            value.getKey().ifPresentOrElse(
                    key -> buf.writeIdentifier(key.getValue()),
                    () -> buf.writeIdentifier(new net.minecraft.util.Identifier("unknown", "unknown")));
            if (element != null) {
                element.encode(buf, value.value());
            }
        }, buf -> {
            if (!buf.readBoolean()) {
                return null;
            }
            buf.readIdentifier();
            if (element != null) {
                element.decode(buf);
            }
            return null;
        });
    }
}
