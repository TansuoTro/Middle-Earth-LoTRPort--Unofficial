package net.jukoz.me.block.utils;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.jukoz.me.compat.net.PacketCodec;
import net.jukoz.me.compat.net.PacketCodecs;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import java.util.function.IntFunction;

public enum BlockAuthor implements StringIdentifiable {
    BOENNDAL(0, "Boenndal"),
    SCOSHER(1, "Scosher"),
    COFFEE_VIKING(2, "CoffeeViking"),
    NAUTILUS(3, "Nautilus4000"),
    SINDAVAR(4, "Sindavar"),
    ANGMARZKU(5, "Angmarzku")
    ;

    private static final IntFunction<BlockAuthor> BY_ID = ValueLists.createIdToValueFunction(BlockAuthor::getId, BlockAuthor.values(), ValueLists.OutOfBoundsHandling.ZERO);;
    private final int id;
    private final String authorName;

    public static final Codec<BlockAuthor> CODEC = StringIdentifiable.createCodec(BlockAuthor::values);
    public static final PacketCodec<net.minecraft.network.PacketByteBuf, BlockAuthor> PACKET_CODEC = PacketCodecs.indexed(BY_ID, BlockAuthor::getId);

    BlockAuthor(int id, String authorName) {
        this.id = id;
        this.authorName = authorName;
    }

    public int getId() {
        return id;
    }

    @Override
    public String asString() {
        return this.name();
    }

    public String getAuthorName() {
        return authorName;
    }
}
