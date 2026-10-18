package net.jukoz.me.item.items;


import net.jukoz.me.compat.MEBannerPatterns;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class HeldBannerItem extends Item {
    public HeldBannerItem(Settings settings) {
        super(settings.maxCount(1));
    }

    public static void appendBannerTooltip(ItemStack stack, List<Text> tooltip) {
        MEBannerPatterns bannerPatternsComponent = MEBannerPatterns.get(stack);
        if (bannerPatternsComponent != null) {
            for(int i = 0; i < Math.min(bannerPatternsComponent.layers().size(), 6); ++i) {
                MEBannerPatterns.Layer layer = (MEBannerPatterns.Layer)bannerPatternsComponent.layers().get(i);
                tooltip.add(Text.translatable("block.minecraft.banner." + layer.pattern().getHashPattern()).formatted(Formatting.GRAY));
            }

        }
    }

    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        appendBannerTooltip(stack, tooltip);
    }
}
