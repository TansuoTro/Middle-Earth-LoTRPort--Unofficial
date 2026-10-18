package net.jukoz.me.item.items.weapons.artefacts;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.jukoz.me.compat.BluntWeapon;
import net.minecraft.item.ToolMaterial;
import net.jukoz.me.item.items.weapons.CustomDaggerWeaponItem;

public class WeaverStingItem extends CustomDaggerWeaponItem {
    public WeaverStingItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.getDamage() == stack.getMaxDamage() - 1) {
            BluntWeapon.blunt(stack);
        }
    }
}
