package net.jukoz.me.item.items;

import net.jukoz.me.MiddleEarth;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class CustomShieldItem extends ShieldItem {
    private final MutableComponent faction;
    private final MutableComponent subFaction;

    public CustomShieldItem(Settings settings, MutableComponent faction) {
        super(settings);
        this.faction = faction;
        this.subFaction = null;
    }

    public CustomShieldItem(Settings settings, MutableComponent faction, MutableComponent subFaction) {
        super(settings);
        this.faction = faction;
        this.subFaction = subFaction;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {

        tooltip.add(Component.of(""));
        if (Screen.hasShiftDown()) {
            tooltip.add(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".faction").append(this.faction));
            if (this.subFaction != null) {
                tooltip.add(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".sub_faction").append(this.subFaction));
            }
            tooltip.add(Component.of(""));
        } else {
            tooltip.add(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".shift"));
        }

        super.appendTooltip(stack, world, tooltip, context);
    }
}
