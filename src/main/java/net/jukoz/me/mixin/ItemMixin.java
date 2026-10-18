package net.jukoz.me.mixin;

import net.jukoz.me.item.MEComponents;
import net.jukoz.me.item.MESettings;
import net.minecraft.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Hands the components declared on an {@link MESettings} to {@link MEComponents} once the
 * item exists, so they can be resolved from a stack later.
 */
@Mixin(Item.class)
public class ItemMixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void me$captureComponentDefaults(Item.Settings settings, CallbackInfo ci) {
        if (settings instanceof MESettings meSettings) {
            if (!meSettings.components().isEmpty()) {
                MEComponents.captureDefaults((Item) (Object) this, meSettings.components());
            }
            if (meSettings.isUnbreakable()) {
                net.jukoz.me.item.UnbreakableItems.add((Item) (Object) this);
            }
            if (meSettings.lore() != null) {
                net.jukoz.me.item.ItemExtras.lore((Item) (Object) this, meSettings.lore());
            }
            if (meSettings.bannerPatternTag() != null) {
                net.jukoz.me.item.ItemExtras.bannerPatternTag((Item) (Object) this, meSettings.bannerPatternTag());
            }
        }
    }
}
