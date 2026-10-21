package net.jukoz.me.compat.nbt;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DynamicOps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.resources.RegistryOps;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Stand-in for 1.21.5's {@code net.minecraft.storage.ReadView}, backed by an {@link CompoundTag}.
 *
 * <p>1.20.1 has no view abstraction: every persistent object reads straight out of the
 * {@code CompoundTag} it was handed. The donor's serialisation bodies were written against
 * {@code ReadView}, so this class reproduces the handful of methods they use and lets those
 * bodies stay as they are; the {@code readNbt} / {@code readCustomDataFromNbt} override at the
 * top of each class wraps the compound in one of these.
 */
public final class ReadView {
    private final CompoundTag nbt;
    private final DynamicOps<NbtElement> ops;

    public ReadView(CompoundTag nbt) {
        this(nbt, null);
    }

    public ReadView(CompoundTag nbt, DynamicRegistryManager registries) {
        this.nbt = nbt;
        this.ops = registries == null ? NbtOps.INSTANCE : RegistryOps.of(NbtOps.INSTANCE, registries);
    }

    public CompoundTag nbt() {
        return this.nbt;
    }

    public boolean contains(String key) {
        return this.nbt.contains(key);
    }

    public <T> Optional<T> read(String key, Codec<T> codec) {
        NbtElement element = this.nbt.get(key);
        if (element == null) {
            return Optional.empty();
        }
        return codec.parse(ops, element).result();
    }

    public <T> List<T> getTypedListView(String key, Codec<T> codec) {
        NbtList list = this.nbt.getList(key, NbtElement.COMPOUND_TYPE);
        List<T> out = new ArrayList<>(list.size());
        for (int i = 0; i < list.size(); i++) {
            codec.parse(ops, list.getCompound(i)).result().ifPresent(out::add);
        }
        return out;
    }

    public CompoundTag getCompound(String key) {
        return this.nbt.getCompound(key);
    }

    public int getInt(String key, int fallback) {
        return this.nbt.contains(key, NbtElement.NUMBER_TYPE) ? this.nbt.getInt(key) : fallback;
    }

    public short getShort(String key, short fallback) {
        return this.nbt.contains(key, NbtElement.NUMBER_TYPE) ? this.nbt.getShort(key) : fallback;
    }

    public long getLong(String key, long fallback) {
        return this.nbt.contains(key, NbtElement.NUMBER_TYPE) ? this.nbt.getLong(key) : fallback;
    }

    public float getFloat(String key, float fallback) {
        return this.nbt.contains(key, NbtElement.NUMBER_TYPE) ? this.nbt.getFloat(key) : fallback;
    }

    public double getDouble(String key, double fallback) {
        return this.nbt.contains(key, NbtElement.NUMBER_TYPE) ? this.nbt.getDouble(key) : fallback;
    }

    public boolean getBoolean(String key, boolean fallback) {
        return this.nbt.contains(key, NbtElement.NUMBER_TYPE) ? this.nbt.getBoolean(key) : fallback;
    }

    public String getString(String key, String fallback) {
        return this.nbt.contains(key, NbtElement.STRING_TYPE) ? this.nbt.getString(key) : fallback;
    }
}
