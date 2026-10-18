package net.jukoz.me.compat;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

/**
 * 1.21 stores enchantments in an {@code ItemEnchantmentsComponent}; 1.20.1 still exposes the raw
 * {@code Enchantments} NBT list from {@link ItemStack#getEnchantments()}. The ported code was
 * written against the component API, so these helpers bridge the two.
 */
public final class MEEnchants {
    private MEEnchants() {
    }

    /** The enchantments of {@code stack} as a plain list, for {@code EnchantmentHelper} calls. */
    public static List<Enchantment> list(ItemStack stack) {
        List<Enchantment> result = new ArrayList<>();
        NbtList enchantments = stack.getEnchantments();
        for (int i = 0; i < enchantments.size(); i++) {
            NbtCompound entry = enchantments.getCompound(i);
            Enchantment enchantment = Registries.ENCHANTMENT.get(Identifier.tryParse(entry.getString("id")));
            if (enchantment != null) {
                result.add(enchantment);
            }
        }
        return result;
    }

    public static boolean has(ItemStack stack, RegistryEntry<Enchantment> enchantment) {
        if (!enchantment.hasKeyAndValue()) {
            return false;
        }
        NbtList enchantments = stack.getEnchantments();
        for (int i = 0; i < enchantments.size(); i++) {
            NbtElement element = enchantments.get(i);
            if (element.getType() == NbtElement.COMPOUND_TYPE) {
                NbtCompound entry = (NbtCompound) element;
                if (enchantment.getKey().orElseThrow().getValue().toString().equals(entry.getString("id"))) {
                    return true;
                }
            }
        }
        return false;
    }
}
