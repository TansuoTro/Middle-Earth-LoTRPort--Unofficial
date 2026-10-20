package net.jukoz.me.mixin;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.jukoz.me.compat.BluntWeapon;
import net.jukoz.me.item.utils.ItemSettingsME;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * The donor's "blunted" swap for artefact weapons: instead of dropping the modifiers entirely
 * (which leaves the weapon at the vanilla base 4.0 attack speed), a blunted weapon reports
 * 0 damage, -3.0 speed and 0 range.
 */
@Mixin(ItemStack.class)
public class ItemStackAttributeMixin {

    @Inject(method = "getAttributeModifiers", at = @At("HEAD"), cancellable = true)
    private void me$bluntedWeaponAttributes(EquipmentSlot slot,
                                            CallbackInfoReturnable<Multimap<EntityAttribute, EntityAttributeModifier>> cir) {
        if (BluntWeapon.isBlunted((ItemStack) (Object) this)) {
            // Built per call on purpose: touching EntityAttributesME from a mixin static would
            // force the attribute registry during ItemStack's clinit.
            cir.setReturnValue(slot == EquipmentSlot.MAINHAND
                    ? ItemSettingsME.createBluntedWeaponAttibutes()
                    : ImmutableMultimap.of());
        }
    }
}
