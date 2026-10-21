package net.jukoz.me.item.items;

import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

/**
 * HoeItem that restores the donor's tool durability, repair material and
 * enchantability (1.20.1 has no data-component settings, so these come from the
 * Tier via overrides, mirroring {@code ReachWeaponItem}).
 */
public class CustomHoeItem extends HoeItem {
    private final Tier material;

    public CustomHoeItem(Tier material, int attackDamage, float attackSpeed, Settings settings) {
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
