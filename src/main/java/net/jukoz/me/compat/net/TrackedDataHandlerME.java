package net.jukoz.me.compat.net;

import net.minecraft.entity.data.TrackedDataHandler;
import net.jukoz.me.compat.net.TrackedDataHandlerME;
import net.minecraft.network.PacketByteBuf;

/** 1.20.1 bridge for 1.21.5's {@code TrackedDataHandlerME.create(PacketCodec)}. */
public final class TrackedDataHandlerME {

    private TrackedDataHandlerME() {
    }

    public static <T> TrackedDataHandler<T> create(PacketCodec<PacketByteBuf, T> codec) {
        return new TrackedDataHandler<T>() {
            @Override
            public void write(PacketByteBuf buf, T value) {
                codec.encode(buf, value);
            }

            @Override
            public T read(PacketByteBuf buf) {
                return codec.decode(buf);
            }

            @Override
            public T copy(T value) {
                return value;
            }
        };
    }
}
