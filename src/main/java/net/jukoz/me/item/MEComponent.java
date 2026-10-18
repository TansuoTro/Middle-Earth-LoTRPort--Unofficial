package net.jukoz.me.item;

import com.mojang.serialization.Codec;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

/**
 * Stand-in for 1.21's {@code ComponentType<T>}.
 *
 * <p>1.20.1 has no data component registry, so a component is just an id plus the
 * codec used to (de)serialise it into the stack's NBT. Values live under
 * {@code ItemStack.tag.MEComponents.<id>}.
 *
 * @param <T> value type stored on the stack
 */
public final class MEComponent<T> {
    private final Identifier id;
    private final Codec<T> codec;
    @Nullable
    private final T defaultValue;

    public MEComponent(Identifier id, Codec<T> codec) {
        this(id, codec, null);
    }

    public MEComponent(Identifier id, Codec<T> codec, @Nullable T defaultValue) {
        this.id = id;
        this.codec = codec;
        this.defaultValue = defaultValue;
    }

    public Identifier id() {
        return id;
    }

    public Codec<T> codec() {
        return codec;
    }

    @Nullable
    public T defaultValue() {
        return defaultValue;
    }

    @Override
    public String toString() {
        return id.toString();
    }
}
