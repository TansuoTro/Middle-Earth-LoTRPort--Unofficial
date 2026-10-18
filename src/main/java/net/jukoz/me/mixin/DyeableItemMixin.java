package net.jukoz.me.mixin;

import net.jukoz.me.compat.DyedColorComponent;
import net.jukoz.me.item.MEComponents;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Gives 1.21's default dyed colour a meaning on 1.20.1.
 *
 * <p>The donor declares {@code component(MEComponents.DYED_COLOR, ...)} on the item
 * settings to give leather a non-white base colour. 1.20.1 reads the colour from
 * {@code display.color} NBT and otherwise falls back to white, so without this the whole
 * leather armour set would render white until dyed. An explicit NBT colour still wins,
 * so dyeing keeps working.
 */
@Mixin(DyeableItem.class)
public interface DyeableItemMixin {
    @Inject(method = "getColor", at = @At("HEAD"), cancellable = true)
    private void me$defaultDyedColor(ItemStack stack, CallbackInfoReturnable<Integer> cir) {
        if (stack.hasNbt() && stack.getNbt().getCompound(DyeableItem.DISPLAY_KEY).contains(DyeableItem.COLOR_KEY)) {
            return;
        }
        DyedColorComponent dyed = MEComponents.get(stack, MEComponents.DYED_COLOR);
        if (dyed != null) {
            cir.setReturnValue(dyed.rgb());
        }
    }
}
