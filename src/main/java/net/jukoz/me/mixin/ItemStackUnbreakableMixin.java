package net.jukoz.me.mixin;

import net.jukoz.me.item.UnbreakableItems;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Makes {@link UnbreakableItems} behave like 1.21's {@code UNBREAKABLE} component. */
@Mixin(ItemStack.class)
public class ItemStackUnbreakableMixin {

    @Inject(method = "isUnbreakable", at = @At("HEAD"), cancellable = true)
    private void me$unbreakableItems(CallbackInfoReturnable<Boolean> cir) {
        if (UnbreakableItems.contains(((ItemStack) (Object) this).getItem())) {
            cir.setReturnValue(true);
        }
    }
}
