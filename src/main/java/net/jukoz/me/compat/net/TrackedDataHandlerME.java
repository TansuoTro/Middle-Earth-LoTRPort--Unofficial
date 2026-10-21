package net.jukoz.me.compat.net;

import net.minecraft.entity.data.TrackedDataHandler;
import net.jukoz.me.compat.net.TrackedDataHandlerME;
import net.minecraft.network.FriendlyByteBuf;

/** 1.20.1 bridge for 1.21.5's {@code TrackedDataHandlerME.create(PacketCodec)}. */
public final class TrackedDataHandlerME {

    private TrackedDataHandlerME() {
    }

    public static <T> TrackedDataHandler<T> create(PacketCodec<FriendlyByteBuf, T> codec) {
        return new TrackedDataHandler<T>() {
            @Override
            public void write(FriendlyByteBuf buf, T value) {
                codec.encode(buf, value);
            }

            @Override
            public T read(FriendlyByteBuf buf) {
                return codec.decode(buf);
            }

            @Override
            public T copy(T value) {
                return value;
            }
        };
    }
}
