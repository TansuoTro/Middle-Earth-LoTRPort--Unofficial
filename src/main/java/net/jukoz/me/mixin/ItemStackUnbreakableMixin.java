package net.jukoz.me.mixin;

import net.jukoz.me.item.UnbreakableItems;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Makes {@link UnbreakableItems} behave like 1.21's {@code UNBREAKABLE} component.
 *
 * <p>1.20.1 has no {@code ItemStack.isUnbreakable()}; the vanilla {@code Unbreakable}
 * NBT flag is checked inside {@code isDamageable()}, which both {@code damage(...)}
 * overloads gate on. So unbreakable items simply report {@code isDamageable() == false}.
 */
@Mixin(ItemStack.class)
public class ItemStackUnbreakableMixin {

    @Inject(method = "isDamageable", at = @At("HEAD"), cancellable = true)
    private void me$unbreakableItems(CallbackInfoReturnable<Boolean> cir) {
        if (UnbreakableItems.contains(((ItemStack) (Object) this).getItem())) {
            cir.setReturnValue(false);
        }
    }
}
