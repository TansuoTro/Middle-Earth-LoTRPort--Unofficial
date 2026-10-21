package net.jukoz.me.compat;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;

/**
 * Helpers for reading the 1.21 attribute-component data back out of a 1.20.1 stack.
 *
 * <p>1.21 exposes attack damage through {@code ATTRIBUTE_MODIFIERS}; 1.20.1 keeps it on
 * {@link ItemStack#getAttributeModifiers(EquipmentSlot)} as a {@code Multimap}.
 */
public final class MEAttributes {

    private MEAttributes() {
    }

    /** Sum of the main-hand attack-damage modifiers on {@code stack}. */
    public static double weaponAttackDamage(ItemStack stack) {
        double damage = 0.0;
        for (AttributeModifier modifier : stack.getAttributeModifiers(EquipmentSlot.MAINHAND)
                .get(Attributes.ATTACK_DAMAGE)) {
            damage += modifier.getValue();
        }
        return damage;
    }
}
