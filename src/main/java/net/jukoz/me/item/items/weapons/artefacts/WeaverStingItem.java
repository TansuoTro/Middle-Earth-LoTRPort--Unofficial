package net.jukoz.me.item.items.weapons.artefacts;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.jukoz.me.compat.BluntWeapon;
import net.minecraft.world.item.Tier;
import net.jukoz.me.item.items.weapons.CustomDaggerWeaponItem;

public class WeaverStingItem extends CustomDaggerWeaponItem {
    public WeaverStingItem(Tier toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // donor postDamageEntity: poison on every hit
        target.addStatusEffect(new MobEffectInstance(MobEffects.POISON, 50));
        // per-hit damage (donor's WEAPON component damagePerHit = 1)
        super.postHit(stack, target, attacker);
        if (stack.getDamage() == stack.getMaxDamage() - 1) {
            BluntWeapon.blunt(stack);
        }
        return true;
    }
}
