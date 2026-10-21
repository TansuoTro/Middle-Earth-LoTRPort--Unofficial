package net.jukoz.me.item.items;

import net.minecraft.world.item.ItemStack;
import net.minecraft.item.ShovelItem;
import net.minecraft.item.ToolMaterial;

/**
 * ShovelItem that restores the donor's tool durability, repair material and
 * enchantability (1.20.1 has no data-component settings, so these come from the
 * ToolMaterial via overrides, mirroring {@code ReachWeaponItem}).
 */
public class CustomShovelItem extends ShovelItem {
    private final ToolMaterial material;

    public CustomShovelItem(ToolMaterial material, float attackDamage, float attackSpeed, Settings settings) {
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
