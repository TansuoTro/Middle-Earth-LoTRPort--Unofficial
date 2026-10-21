package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class CustomSwordWeaponItem extends SwordItem {
    private final MutableComponent faction;
    private final MutableComponent subFaction;
    private final Tier material;

    public CustomSwordWeaponItem(Tier toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings.maxDamage(toolMaterial.getUses()));
        this.faction = null;
        this.subFaction = null;
        this.material = toolMaterial;
    }

    public CustomSwordWeaponItem(Tier toolMaterial, int attackDamage, float attackSpeed, MutableComponent faction, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings.maxDamage(toolMaterial.getUses()));
        this.faction = faction;
        this.subFaction = null;
        this.material = toolMaterial;
    }

    public CustomSwordWeaponItem(Tier toolMaterial, int attackDamage, float attackSpeed, MutableComponent faction, MutableComponent subFaction, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings.maxDamage(toolMaterial.getUses()));
        this.faction = faction;
        this.subFaction = subFaction;
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
    public void appendTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.of(""));
        if (Screen.hasShiftDown()) {
            if (this.faction != null) {
                tooltip.add(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(this.faction));
            }
            if (this.subFaction != null) {
                tooltip.add(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(this.subFaction));
            }
            tooltip.add(Component.of(""));
        } else {
            tooltip.add(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }
        tooltip.add(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".reach").append(Float.toString(4.5f)).append(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".blocks_range")).formatted(ChatFormatting.DARK_GREEN));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
