package net.jukoz.me.item.dataComponents;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import net.jukoz.me.compat.net.PacketCodec;
import net.minecraft.network.PacketByteBuf;

import java.util.UUID;
import java.util.function.Function;

/** Small codec helpers shared by the ported data components. */
public final class ComponentCodecs {
    private ComponentCodecs() {
    }

    /** 1.20.1 has no {@code Uuids.PACKET_CODEC}; the wire form is the vanilla uuid pair of longs. */
    public static final PacketCodec<PacketByteBuf, UUID> UUID_CODEC =
            PacketCodec.of((buf, value) -> buf.writeUuid(value), PacketByteBuf::readUuid);

    /** 1.20.1's {@code ColorHelper} has no {@code fullAlpha}; the donor passes an opaque rgb. */
    public static int fullAlpha(int rgb) {
        return 0xFF000000 | (rgb & 0xFFFFFF);
    }

    /**
     * 1.20.1's DataFixerUpper has no {@code Codec.withAlternative} (that arrived with the
     * 1.20.5 component work), so the donor's "also accept the short form" pattern is
     * rebuilt on top of {@link Codec#either}.
     */
    public static <T, A> Codec<T> alternative(Codec<T> primary, Codec<A> fallback, Function<A, T> converter) {
        return Codec.either(primary, fallback).xmap(
                either -> either.map(value -> value, converter),
                Either::left);
    }
}
