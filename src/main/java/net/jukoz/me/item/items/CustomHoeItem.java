package net.jukoz.me.item.items;

import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ToolMaterial;

/**
 * HoeItem that restores the donor's tool durability, repair material and
 * enchantability (1.20.1 has no data-component settings, so these come from the
 * ToolMaterial via overrides, mirroring {@code ReachWeaponItem}).
 */
public class CustomHoeItem extends HoeItem {
    private final ToolMaterial material;

    public CustomHoeItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
        this.material = material;
    }

    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return this.material.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
    }

    @Override
    public int getEnchantability() {
        return this.material.getEnchantability();
    }
}
