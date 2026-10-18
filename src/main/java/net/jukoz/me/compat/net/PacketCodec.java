package net.jukoz.me.compat.net;

import java.util.function.Function;

/**
 * Stand-in for 1.20.5+'s {@code net.minecraft.network.codec.PacketCodec}.
 *
 * <p>The donor serialises its data objects (NPC data, factions, variants, recipes) with a
 * codec made of field codecs plus a factory reference. 1.20.1 has no such type - packets are
 * written by hand against {@code PacketByteBuf} - so this class reproduces the shape the
 * donor's declarations use and keeps those declarations compiling.
 *
 * <p>The wire format is this port's own: it only has to agree with itself, not with 1.21.
 */
public interface PacketCodec<B, V> {

    void encode(B buf, V value);

    V decode(B buf);

    interface Encoder<B, V> {
        void encode(B buf, V value);
    }

    interface Decoder<B, V> {
        V decode(B buf);
    }

    static <B, V> PacketCodec<B, V> of(Encoder<B, V> encoder, Decoder<B, V> decoder) {
        return new PacketCodec<>() {
            @Override
            public void encode(B buf, V value) {
                encoder.encode(buf, value);
            }

            @Override
            public V decode(B buf) {
                return decoder.decode(buf);
            }
        };
    }

    /** 1.21 spells the stateless form {@code ofStatic}; the meaning is the same. */
    static <B, V> PacketCodec<B, V> ofStatic(Encoder<B, V> encoder, Decoder<B, V> decoder) {
        return of(encoder, decoder);
    }

    static <B, V> PacketCodec<B, V> unit(V value) {
        return of((buf, ignored) -> {
        }, buf -> value);
    }

    // -- tuple -----------------------------------------------------------------
    // The donor writes tuple(codec1, getter1, ..., factory). Each arity needs its own
    // functional interface because the factory's parameter count is what picks the
    // composite constructor. R is the composite, A/B/C... are the field types.

    interface Factory1<A, R> {
        R create(A a);
    }

    interface Factory2<A, B, R> {
        R create(A a, B b);
    }

    interface Factory3<A, B, C, R> {
        R create(A a, B b, C c);
    }

    interface Factory4<A, B, C, D, R> {
        R create(A a, B b, C c, D d);
    }

    interface Factory5<A, B, C, D, E, R> {
        R create(A a, B b, C c, D d, E e);
    }

    interface Factory6<A, B, C, D, E, F, R> {
        R create(A a, B b, C c, D d, E e, F f);
    }

    static <T, A, R> PacketCodec<T, R> tuple(PacketCodec<T, A> c1, Function<R, A> g1, Factory1<A, R> factory) {
        return of((buf, value) -> c1.encode(buf, g1.apply(value)),
                buf -> factory.create(c1.decode(buf)));
    }

    static <T, A, B, R> PacketCodec<T, R> tuple(PacketCodec<T, A> c1, Function<R, A> g1,
                                                PacketCodec<T, B> c2, Function<R, B> g2,
                                                Factory2<A, B, R> factory) {
        return of((buf, value) -> {
                    c1.encode(buf, g1.apply(value));
                    c2.encode(buf, g2.apply(value));
                },
                buf -> factory.create(c1.decode(buf), c2.decode(buf)));
    }

    static <T, A, B, C, R> PacketCodec<T, R> tuple(PacketCodec<T, A> c1, Function<R, A> g1,
                                                   PacketCodec<T, B> c2, Function<R, B> g2,
                                                   PacketCodec<T, C> c3, Function<R, C> g3,
                                                   Factory3<A, B, C, R> factory) {
        return of((buf, value) -> {
                    c1.encode(buf, g1.apply(value));
                    c2.encode(buf, g2.apply(value));
                    c3.encode(buf, g3.apply(value));
                },
                buf -> factory.create(c1.decode(buf), c2.decode(buf), c3.decode(buf)));
    }

    static <T, A, B, C, D, R> PacketCodec<T, R> tuple(PacketCodec<T, A> c1, Function<R, A> g1,
                                                      PacketCodec<T, B> c2, Function<R, B> g2,
                                                      PacketCodec<T, C> c3, Function<R, C> g3,
                                                      PacketCodec<T, D> c4, Function<R, D> g4,
                                                      Factory4<A, B, C, D, R> factory) {
        return of((buf, value) -> {
                    c1.encode(buf, g1.apply(value));
                    c2.encode(buf, g2.apply(value));
                    c3.encode(buf, g3.apply(value));
                    c4.encode(buf, g4.apply(value));
                },
                buf -> factory.create(c1.decode(buf), c2.decode(buf), c3.decode(buf), c4.decode(buf)));
    }

    static <T, A, B, C, D, E, R> PacketCodec<T, R> tuple(PacketCodec<T, A> c1, Function<R, A> g1,
                                                         PacketCodec<T, B> c2, Function<R, B> g2,
                                                         PacketCodec<T, C> c3, Function<R, C> g3,
                                                         PacketCodec<T, D> c4, Function<R, D> g4,
                                                         PacketCodec<T, E> c5, Function<R, E> g5,
                                                         Factory5<A, B, C, D, E, R> factory) {
        return of((buf, value) -> {
                    c1.encode(buf, g1.apply(value));
                    c2.encode(buf, g2.apply(value));
                    c3.encode(buf, g3.apply(value));
                    c4.encode(buf, g4.apply(value));
                    c5.encode(buf, g5.apply(value));
                },
                buf -> factory.create(c1.decode(buf), c2.decode(buf), c3.decode(buf), c4.decode(buf),
                        c5.decode(buf)));
    }

    static <T, A, B, C, D, E, F, R> PacketCodec<T, R> tuple(PacketCodec<T, A> c1, Function<R, A> g1,
                                                            PacketCodec<T, B> c2, Function<R, B> g2,
                                                            PacketCodec<T, C> c3, Function<R, C> g3,
                                                            PacketCodec<T, D> c4, Function<R, D> g4,
                                                            PacketCodec<T, E> c5, Function<R, E> g5,
                                                            PacketCodec<T, F> c6, Function<R, F> g6,
                                                            Factory6<A, B, C, D, E, F, R> factory) {
        return of((buf, value) -> {
                    c1.encode(buf, g1.apply(value));
                    c2.encode(buf, g2.apply(value));
                    c3.encode(buf, g3.apply(value));
                    c4.encode(buf, g4.apply(value));
                    c5.encode(buf, g5.apply(value));
                    c6.encode(buf, g6.apply(value));
                },
                buf -> factory.create(c1.decode(buf), c2.decode(buf), c3.decode(buf), c4.decode(buf),
                        c5.decode(buf), c6.decode(buf)));
    }

    /** {@code PacketCodecs.STRING.collect(PacketCodecs.toList())} - a counted list. */
    default <R> PacketCodec<B, R> collect(Collector<B, V, R> collector) {
        return collector.collect(this);
    }

    interface Collector<B, V, R> {
        PacketCodec<B, R> collect(PacketCodec<B, V> element);
    }
}
