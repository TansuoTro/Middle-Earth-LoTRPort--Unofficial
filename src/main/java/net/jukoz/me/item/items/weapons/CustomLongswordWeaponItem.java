package net.jukoz.me.item.items.weapons;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.jukoz.me.compat.BluntWeapon;
import net.jukoz.me.item.utils.WeaponTypesME;
import net.minecraft.world.item.Tier;
import net.minecraft.world.entity.EquipmentSlot;

public class CustomLongswordWeaponItem extends ReachWeaponItem {
    public CustomLongswordWeaponItem(Tier toolMaterial, Item.Settings settings) {
        super(toolMaterial, WeaponTypesME.LONGSWORD, settings);
        // Keep in case of pvp re-balancing feedbacks
        /*.component(DataComponentTypes.BLOCKS_ATTACKS,
                        new BlocksAttacksComponent(0.25F, 1.0F, List.of(
                                new BlocksAttacksComponent.DamageReduction(90.0F, Optional.empty(), 0.0F, 1.0F)),
                                new BlocksAttacksComponent.ItemDamage(3.0F, 1.0F, 1.0F), Optional.of(DamageTypeTags.BYPASSES_SHIELD),
                                Optional.of(SoundEvents.ITEM_SHIELD_BLOCK), Optional.of(SoundEvents.ITEM_SHIELD_BREAK)))
                .component(DataComponentTypes.BREAK_SOUND, SoundEvents.ITEM_SHIELD_BREAK)*/
        this.type = WeaponTypesME.LONGSWORD;
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        super.postHit(stack, target, attacker);
        if (!BluntWeapon.isBlunted(stack)) {
            // The donor's WEAPON component carries damagePerHit = 1; 1.20.1's Item.postHit does not
            // damage the stack, so the per-hit durability loss lives here.
            stack.damage(1, attacker, e -> e.sendEquipmentBreakStatus(net.minecraft.world.entity.EquipmentSlot.MAINHAND));
        }
        return true;
    }
}
