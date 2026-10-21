package net.jukoz.me.item.items.weapons;

import net.jukoz.me.item.MEComponents;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.jukoz.me.item.dataComponents.WeaponTypeDataComponent;
import net.jukoz.me.item.utils.WeaponTypesME;

public class CustomAxeWeaponItem extends AxeItem {

    private final Tier material;

    public CustomAxeWeaponItem(Tier toolMaterial, Item.Settings settings) {
        super(toolMaterial, WeaponTypesME.AXE.attack, WeaponTypesME.AXE.attackSpeed,
                ((net.jukoz.me.item.MESettings) settings).component(MEComponents.WEAPON_TYPE_DATA, new WeaponTypeDataComponent(WeaponTypesME.AXE.name)));
        this.material = toolMaterial;
    }

    /** Donor repairable(material.repairItems()). */
    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return this.material.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
    }

    /** Donor enchantable(material.enchantmentValue()). */
    @Override
    public int getEnchantability() {
        return this.material.getEnchantability();
    }

    @Override
    public Component getName(ItemStack stack) {
        if(Registries.ITEM.getId(this).getPath().contains("_noble")
                || Registries.ITEM.getId(this).getPath().contains("_elite")
                || Registries.ITEM.getId(this).getPath().contains("uruk_hai")
                || Registries.ITEM.getId(this).getPath().contains("heyday")
                || Registries.ITEM.getId(this).getPath().contains("numenorean")){
            return Component.translatable(this.getTranslationKey()).formatted(ChatFormatting.GOLD);
        }
        return super.getName(stack);
    }
}
