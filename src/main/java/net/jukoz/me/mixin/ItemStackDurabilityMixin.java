package net.jukoz.me.mixin;

import net.jukoz.me.item.MEComponents;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Makes the per-stack {@code max_damage} component behave like 1.21's {@code MAX_DAMAGE}. */
@Mixin(ItemStack.class)
public class ItemStackDurabilityMixin {

    @Inject(method = "getMaxDamage", at = @At("HEAD"), cancellable = true)
    private void me$customMaxDamage(CallbackInfoReturnable<Integer> cir) {
        Integer maxDamage = MEComponents.get((ItemStack) (Object) this, MEComponents.MAX_DAMAGE);
        if (maxDamage != null) {
            cir.setReturnValue(maxDamage);
        }
    }

    @Inject(method = "isDamageable", at = @At("HEAD"), cancellable = true)
    private void me$customDamageable(CallbackInfoReturnable<Boolean> cir) {
        if (MEComponents.contains((ItemStack) (Object) this, MEComponents.MAX_DAMAGE)) {
            cir.setReturnValue(true);
        }
    }
}
