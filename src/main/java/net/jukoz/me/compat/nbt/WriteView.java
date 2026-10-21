package net.jukoz.me.compat.nbt;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.resources.RegistryOps;

import java.util.function.Consumer;

/**
 * Stand-in for 1.21.5's {@code net.minecraft.storage.WriteView}, backed by an {@link CompoundTag}.
 *
 * @see ReadView
 */
public final class WriteView {
    private final CompoundTag nbt;
    private final DynamicOps<NbtElement> ops;

    public WriteView(CompoundTag nbt) {
        this(nbt, null);
    }

    public WriteView(CompoundTag nbt, DynamicRegistryManager registries) {
        this.nbt = nbt;
        this.ops = registries == null ? NbtOps.INSTANCE : RegistryOps.of(NbtOps.INSTANCE, registries);
    }

    public CompoundTag nbt() {
        return this.nbt;
    }

    public <T> void put(String key, Codec<T> codec, T value) {
        if (value == null) {
            this.nbt.remove(key);
            return;
        }
        codec.encodeStart(ops, value).result().ifPresent(encoded -> this.nbt.put(key, encoded));
    }

    public ListAppender<NbtElement> getListAppender(String key, int type) {
        NbtList list = new NbtList();
        this.nbt.put(key, list);
        return new ListAppender<>(list);
    }

    public <T> ListAppender<T> getListAppender(String key, Codec<T> codec) {
        NbtList list = new NbtList();
        this.nbt.put(key, list);
        return new ListAppender<>(list, codec, ops);
    }

    public void putInt(String key, int value) {
        this.nbt.putInt(key, value);
    }

    public void putShort(String key, short value) {
        this.nbt.putShort(key, value);
    }

    public void putLong(String key, long value) {
        this.nbt.putLong(key, value);
    }

    public void putFloat(String key, float value) {
        this.nbt.putFloat(key, value);
    }

    public void putDouble(String key, double value) {
        this.nbt.putDouble(key, value);
    }

    public void putBoolean(String key, boolean value) {
        this.nbt.putBoolean(key, value);
    }

    public void putString(String key, String value) {
        this.nbt.putString(key, value);
    }

    /** Collects values into an {@link NbtList}, mirroring the donor's list appender. */
    public static final class ListAppender<T> {
        private final NbtList list;
        private final Codec<T> codec;
        private final DynamicOps<NbtElement> ops;

        ListAppender(NbtList list) {
            this(list, null, NbtOps.INSTANCE);
        }

        ListAppender(NbtList list, Codec<T> codec, DynamicOps<NbtElement> ops) {
            this.list = list;
            this.codec = codec;
            this.ops = ops;
        }

        @SuppressWarnings("unchecked")
        public void add(T value) {
            if (this.codec == null) {
                this.list.add((NbtElement) value);
                return;
            }
            this.codec.encodeStart(ops, value).result().ifPresent(this.list::add);
        }

        public void forEach(Consumer<T> consumer) {
            if (this.codec == null) {
                return;
            }
            for (int i = 0; i < this.list.size(); i++) {
                this.codec.parse(ops, this.list.get(i)).result().ifPresent(consumer);
            }
        }
    }
}
