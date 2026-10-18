package net.jukoz.me.item.items.weapons.artefacts;
import net.jukoz.me.entity.EntityAttributesME;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.jukoz.me.compat.BluntWeapon;
import net.minecraft.item.ToolMaterial;

public class MorgulKnifeItem extends ArtefactCustomDaggerWeaponItem {

    public MorgulKnifeItem(ToolMaterial toolMaterial, Item.Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public void postDamageEntity(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (stack.getDamage() == stack.getMaxDamage() - 1) {
            BluntWeapon.blunt(stack);
        }
    }
}
