package net.jukoz.me.mixin;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.AnvilScreenHandler;
import net.jukoz.me.config.ModServerConfigs;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AnvilScreenHandler.class)
public class AnvilScreenHandlerMixin {
    @Redirect(method = "updateResult", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/inventory/CraftingResultInventory;setStack(ILnet/minecraft/item/ItemStack;)V"))
    private void interceptAnvilOutput(CraftingResultInventory outputInventory, int slot, ItemStack stack) {
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
