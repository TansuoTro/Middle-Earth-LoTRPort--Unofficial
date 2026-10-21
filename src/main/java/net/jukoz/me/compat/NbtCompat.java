package net.jukoz.me.compat;

import net.jukoz.me.compat.NbtCompat;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import java.util.function.Consumer;

/**
 * NBT access in the shape the 1.21.8 tree expects.
 *
 * <p>1.21.5 turned the type-checked NBT accessors into {@code Optional}: code reads
 * {@code NbtCompat.compound(element)} and {@code NbtCompat.isList(element)}.
 * 1.20.1 has no such methods, only {@code instanceof}. Rather than rewriting the call
 * sites into explicit casts (they are nested inside fluent chains all over the
 * dynamic-data loaders), the importer points them here.
 */
public final class NbtCompat {
    private NbtCompat() {
    }

    /** The compound behind {@code element}, or null when it is not one. */
    public static CompoundTag compound(Tag element) {
        return element instanceof CompoundTag compound ? compound : null;
    }

    public static boolean isCompound(Tag element) {
        return element instanceof CompoundTag;
    }

    public static void ifCompound(Tag element, Consumer<CompoundTag> action) {
        if (element instanceof CompoundTag compound) {
            action.accept(compound);
        }
    }

    /** The list behind {@code element}, or null when it is not one. */
    public static ListTag list(Tag element) {
        return element instanceof ListTag list ? list : null;
    }

    public static boolean isList(Tag element) {
        return element instanceof ListTag;
    }

    public static void ifList(Tag element, Consumer<ListTag> action) {
        if (element instanceof ListTag list) {
            action.accept(list);
        }
    }

    /** 1.21.5's two-argument getString, which returns the fallback when the key is absent. */
    public static String getString(CompoundTag nbt, String key, String fallback) {
        return nbt.contains(key) ? nbt.getString(key) : fallback;
    }

    /** Same, defaulting to null rather than to the empty string. */
    public static String getString(CompoundTag nbt, String key) {
        return nbt.contains(key) ? nbt.getString(key) : null;
    }
}
