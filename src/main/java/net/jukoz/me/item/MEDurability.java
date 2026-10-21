package net.jukoz.me.item;

import net.minecraft.world.item.ItemStack;

/**
 * Per-stack durability override, backing 1.21's {@code MAX_DAMAGE} component.
 *
 * <p>The value is kept in {@link MEComponents#MAX_DAMAGE}; {@code ItemStackDurabilityMixin}
 * makes {@link ItemStack#getMaxDamage()} and {@link ItemStack#isDamageable()} read it.
 */
public final class MEDurability {

    private MEDurability() {
    }

    public static boolean hasCustom(ItemStack stack) {
        return MEComponents.contains(stack, MEComponents.MAX_DAMAGE);
    }

    public static void set(ItemStack stack, int maxDamage) {
        MEComponents.set(stack, MEComponents.MAX_DAMAGE, maxDamage);
    }

    /** The stack's override, or {@code vanillaMax} when it has none. */
    public static int get(ItemStack stack, int vanillaMax) {
        return MEComponents.getOrDefault(stack, MEComponents.MAX_DAMAGE, vanillaMax);
    }
}
