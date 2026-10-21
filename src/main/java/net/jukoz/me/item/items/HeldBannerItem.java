package net.jukoz.me.item.items;


import net.jukoz.me.compat.MEBannerPatterns;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Formatting;
import net.minecraft.world.level.Level;

import java.util.List;

public class HeldBannerItem extends Item {
    public HeldBannerItem(Settings settings) {
        super(settings.maxCount(1));
    }

    public static void appendBannerTooltip(ItemStack stack, List<Component> tooltip) {
        MEBannerPatterns bannerPatternsComponent = MEBannerPatterns.get(stack);
        if (bannerPatternsComponent != null) {
            for(int i = 0; i < Math.min(bannerPatternsComponent.layers().size(), 6); ++i) {
                MEBannerPatterns.Layer layer = (MEBannerPatterns.Layer)bannerPatternsComponent.layers().get(i);
                tooltip.add(Component.translatable("block.minecraft.banner." + layer.pattern().value().getId()).formatted(Formatting.GRAY));
            }

        }
    }

    @Override
    public void appendTooltip(ItemStack stack, Level world, List<Component> tooltip, TooltipContext context) {
        appendBannerTooltip(stack, tooltip);
    }
}
