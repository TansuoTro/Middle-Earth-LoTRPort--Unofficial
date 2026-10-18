package net.jukoz.me.mixin.client;

import net.jukoz.me.item.MEComponents;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * Adds the MEComponents tooltip lines.
 *
 * <p>1.21 lets a data component append its own lines through {@code TooltipAppender}; 1.20.1
 * has no component registry, so the components are walked here instead.
 */
@Mixin(ItemStack.class)
public class ItemStackTooltipMixin {

    @Inject(method = "getTooltip", at = @At("RETURN"))
    private void me$componentTooltips(PlayerEntity player, TooltipContext context,
                                      CallbackInfoReturnable<List<Text>> cir) {
        MEComponents.appendTooltips((ItemStack) (Object) this, context, cir.getReturnValue());
    }
}
