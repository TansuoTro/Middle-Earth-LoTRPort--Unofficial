package net.jukoz.me.item.dataComponents;

import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;

import java.util.function.Function;

/** Small codec helpers shared by the ported data components. */
public final class ComponentCodecs {
    private ComponentCodecs() {
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
