package net.jukoz.me.compat;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.ListTag;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

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
        ListTag enchantments = stack.getEnchantments();
        for (int i = 0; i < enchantments.size(); i++) {
            CompoundTag entry = enchantments.getCompound(i);
            Enchantment enchantment = Registries.ENCHANTMENT.get(ResourceLocation.tryParse(entry.getString("id")));
            if (enchantment != null) {
                result.add(enchantment);
            }
        }
        return result;
    }

    public static boolean has(ItemStack stack, Holder<Enchantment> enchantment) {
        if (!enchantment.hasKeyAndValue()) {
            return false;
        }
        ListTag enchantments = stack.getEnchantments();
        for (int i = 0; i < enchantments.size(); i++) {
            Tag element = enchantments.get(i);
            if (element.getType() == Tag.COMPOUND_TYPE) {
                CompoundTag entry = (CompoundTag) element;
                if (enchantment.getKey().orElseThrow().getValue().toString().equals(entry.getString("id"))) {
                    return true;
                }
            }
        }
        return false;
    }
}
