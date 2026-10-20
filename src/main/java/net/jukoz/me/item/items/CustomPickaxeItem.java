package net.jukoz.me.item.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.PickaxeItem;
import net.minecraft.item.ToolMaterial;

import java.util.Map;

/**
 * PickaxeItem with a fractional attack-damage bonus.
 *
 * <p>The 1.21 donor builds tools with {@code settings.pickaxe(material, attackDamage, attackSpeed)},
 * which takes a float; 1.20.1's {@code PickaxeItem} constructor takes an int and would truncate the
 * bronze/crude pickaxes' +0.5 bonus away. The fraction is folded into the damage modifier here.
 */
public class CustomPickaxeItem extends PickaxeItem {

    private final double extraAttackDamage;

    public CustomPickaxeItem(ToolMaterial material, float attackDamage, float attackSpeed, Item.Settings settings) {
        super(material, (int) attackDamage, attackSpeed, settings);
        this.extraAttackDamage = attackDamage - (int) attackDamage;
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (this.extraAttackDamage == 0.0 || slot != EquipmentSlot.MAINHAND) {
            return super.getAttributeModifiers(slot);
        }
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        for (Map.Entry<EntityAttribute, EntityAttributeModifier> entry : super.getAttributeModifiers(slot).entries()) {
            EntityAttributeModifier modifier = entry.getValue();
            if (entry.getKey() == EntityAttributes.GENERIC_ATTACK_DAMAGE) {
                builder.put(entry.getKey(), new EntityAttributeModifier(modifier.getId(), modifier.getName(),
                        modifier.getValue() + this.extraAttackDamage, modifier.getOperation()));
            } else {
                builder.put(entry);
            }
        }
        return builder.build();
    }
}
