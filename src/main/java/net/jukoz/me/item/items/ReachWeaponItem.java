package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Formatting;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ReachWeaponItem extends SwordItem {
    private final float rangeDistance;
    private final MutableComponent faction;
    private final MutableComponent subFaction;
    private final ToolMaterial material;

    public ReachWeaponItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, float rangeDistance, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings.maxDamage(toolMaterial.getDurability()));
        this.rangeDistance = rangeDistance;
        this.faction = null;
        this.subFaction = null;
        this.material = toolMaterial;
    }

    public ReachWeaponItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, float rangeDistance, MutableComponent faction, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings.maxDamage(toolMaterial.getDurability()));
        this.rangeDistance = rangeDistance;
        this.faction = faction;
        this.subFaction = null;
        this.material = toolMaterial;
    }

    public ReachWeaponItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, float rangeDistance, MutableComponent faction, MutableComponent subFaction, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings.maxDamage(toolMaterial.getDurability()));
        this.rangeDistance = rangeDistance;
        this.faction = faction;
        this.subFaction = subFaction;
        this.material = toolMaterial;
    }

    public float getRangeDistance() {
        return rangeDistance;
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
    public void appendTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipContext context) {
        tooltip.add(Component.of(""));
        if (Screen.hasShiftDown()) {
            if(this.faction != null){
                tooltip.add(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(this.faction));
            }
            if (this.subFaction != null) {
                tooltip.add(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(this.subFaction));
            }
            tooltip.add(Component.of(""));
        } else {
            tooltip.add(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }
        tooltip.add(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".reach").append(Float.toString(rangeDistance)).append(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".blocks_range")).formatted(Formatting.DARK_GREEN));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
