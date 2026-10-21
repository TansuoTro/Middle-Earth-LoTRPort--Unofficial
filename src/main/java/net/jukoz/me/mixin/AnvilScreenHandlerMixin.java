package net.jukoz.me.mixin;

import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.inventory.AnvilMenu;
import net.jukoz.me.config.ModServerConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilMenu.class)
public class AnvilScreenHandlerMixin {
    @Redirect(method = "updateResult", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/inventory/ResultContainer;setStack(ILnet/minecraft/item/ItemStack;)V"))
    private void interceptAnvilOutput(ResultContainer outputInventory, int slot, ItemStack stack) {
        if (!stack.isEmpty()) {
            for (var entry : EnchantmentHelper.get(stack).entrySet()) {
                var enchant = entry.getKey();
                if ((enchant == Enchantments.SHARPNESS && entry.getValue() >= ModServerConfigs.SHARPNESS_MAX_LEVEL)
                        || (enchant == Enchantments.POWER && entry.getValue() >= ModServerConfigs.POWER_MAX_LEVEL)) {
                    outputInventory.setStack(slot, ItemStack.EMPTY);
                    return;
                }
            }
        }
        outputInventory.setStack(slot, stack);
    }
}
