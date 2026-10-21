package net.jukoz.me.compat;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.armortrim.ArmorTrim;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.core.Holder;
import org.jetbrains.annotations.Nullable;

/**
 * NBT-backed replacement for 1.21's {@code TRIM} data component.
 *
 * <p>1.21 resolves the trim's entries when the component is read. In 1.20.1 the trim is
 * stored in the stack's {@code Trim} sub-NBT as the two registry ids ({@code material},
 * {@code pattern}) and can only be decoded against a registry manager, because trim
 * materials/patterns are dynamic registries ({@code RegistryKeys.TRIM_MATERIAL} /
 * {@code RegistryKeys.TRIM_PATTERN}) with no static {@code Registries} counterpart.
 */
public final class MEArmorTrim {

    private static final String MATERIAL_KEY = "material";
    private static final String PATTERN_KEY = "pattern";

    private MEArmorTrim() {
    }

    /**
     * The trim applied to {@code stack}, or null when it is untrimmed or the ids no longer
     * resolve. This is the 1.20.1-faithful form: pass the registry manager that owns the
     * dynamic {@code TRIM_MATERIAL}/{@code TRIM_PATTERN} registries.
     */
    @Nullable
    public static ArmorTrim get(ItemStack stack, DynamicRegistryManager registries) {
        return ArmorTrim.getTrim(registries, stack).orElse(null);
    }

    /**
     * Donor-compatible signature with no registry manager.
     *
     * <p>1.20.1 has no way to resolve a {@link Holder} for a trim without a
     * {@link DynamicRegistryManager} (unlike 1.21, where the component already holds the
     * resolved entries), so this returns null. Prefer
     * {@link #get(ItemStack, DynamicRegistryManager)}.
     */
    @Nullable
    public static ArmorTrim get(ItemStack stack) {
        return null;
    }

    /** Writes {@code trim} to the stack's {@code Trim} sub-NBT, replacing any existing trim. */
    public static void set(ItemStack stack, ArmorTrim trim) {
        CompoundTag tag = new CompoundTag();
        putEntryId(tag, MATERIAL_KEY, trim.getMaterial());
        putEntryId(tag, PATTERN_KEY, trim.getPattern());
        stack.setSubNbt(ArmorTrim.NBT_KEY, tag);
    }

    private static void putEntryId(CompoundTag tag, String key, Holder<?> entry) {
        entry.getKey().ifPresent(registryKey -> tag.putString(key, registryKey.getValue().toString()));
    }
}
