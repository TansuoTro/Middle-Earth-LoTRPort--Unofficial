package net.jukoz.me.mixin;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.jukoz.me.compat.BluntWeapon;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Drops the attribute modifiers of a blunted artefact weapon. */
@Mixin(ItemStack.class)
public class ItemStackAttributeMixin {

    @Inject(method = "getAttributeModifiers", at = @At("HEAD"), cancellable = true)
    private void me$bluntedWeaponAttributes(EquipmentSlot slot,
                                            CallbackInfoReturnable<Multimap<EntityAttribute, EntityAttributeModifier>> cir) {
        if (BluntWeapon.isBlunted((ItemStack) (Object) this)) {
            cir.setReturnValue(ImmutableMultimap.of());
        }
    }
}
