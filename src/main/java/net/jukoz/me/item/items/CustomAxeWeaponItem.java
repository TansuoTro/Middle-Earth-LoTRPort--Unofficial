package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Formatting;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomAxeWeaponItem extends AxeItem {
    private final MutableComponent faction;
    private final MutableComponent subFaction;
    public CustomAxeWeaponItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings.maxDamage(toolMaterial.getDurability()));
        this.faction = null;
        this.subFaction = null;
    }

    public CustomAxeWeaponItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, MutableComponent faction, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings.maxDamage(toolMaterial.getDurability()));
        this.faction = faction;
        this.subFaction = null;
    }

    public CustomAxeWeaponItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, MutableComponent faction, MutableComponent subFaction, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings.maxDamage(toolMaterial.getDurability()));
        this.faction = faction;
        this.subFaction = subFaction;
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
        tooltip.add(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".reach").append(Float.toString(4.5f)).append(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".blocks_range")).formatted(Formatting.DARK_GREEN));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
