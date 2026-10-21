package net.jukoz.me.item.items.weapons.artefacts;

import net.jukoz.me.entity.EntityAttributesME;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

public class MorgulKnifeItem extends ArtefactCustomDaggerWeaponItem {

    public MorgulKnifeItem(Tier toolMaterial, Item.Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        // donor postDamageEntity: the curse lands while the knife is not blunted yet
        if (stack.getDamage() < stack.getMaxDamage() - 1) {
            target.addStatusEffect(new MobEffectInstance(MobEffects.WITHER, 6000));
            target.addStatusEffect(new MobEffectInstance(MobEffects.BLINDNESS, 6000));
        }
        // per-hit damage + the blunted trigger at maxDamage - 1 (ArtefactCustomDaggerWeaponItem)
        return super.postHit(stack, target, attacker);
    }
}
