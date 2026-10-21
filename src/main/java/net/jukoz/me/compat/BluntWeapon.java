package net.jukoz.me.compat;

import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;

/**
 * The donor's "blunted" state for artefact weapons.
 *
 * <p>On 1.21 the weapon carries {@code ATTRIBUTE_MODIFIERS} and {@code WEAPON} components, and
 * one point before breaking it swaps in a zeroed modifier set and drops the weapon component.
 * 1.20.1 has neither: the attributes come from the item class, and there is no weapon flag. So
 * the state lives in the stack's NBT and {@code ItemStackAttributeMixin} hides the modifiers
 * while it is set.
 */
public final class BluntWeapon {

    public static final String KEY = "me_blunted";

    private BluntWeapon() {
    }

    public static boolean isBlunted(ItemStack stack) {
        CompoundTag nbt = stack.getNbt();
        return nbt != null && nbt.getBoolean(KEY);
    }

    public static void blunt(ItemStack stack) {
        stack.getOrCreateNbt().putBoolean(KEY, true);
    }
}
