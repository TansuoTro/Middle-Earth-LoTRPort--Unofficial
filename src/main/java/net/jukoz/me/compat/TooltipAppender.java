package net.jukoz.me.compat;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.List;

/**
 * 1.21.5's {@code net.minecraft.item.tooltip.TooltipAppender}, in 1.20.1 terms.
 *
 * <p>On 1.21 a data component can append its own tooltip lines. 1.20.1 has no component
 * system at all, so the components are plain NBT-backed records; this interface is what
 * {@code MEComponents} walks when a stack's tooltip is built.
 */
public interface TooltipAppender {

    void appendTooltip(ItemStack stack, Item.TooltipContext context, List<Text> tooltip);
}
