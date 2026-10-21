package net.jukoz.me.item.items;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
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
    private final Tier material;

    public CustomPickaxeItem(Tier material, float attackDamage, float attackSpeed, Item.Properties settings) {
        super(material, (int) attackDamage, attackSpeed, settings);
        this.extraAttackDamage = attackDamage - (int) attackDamage;
        this.material = material;
    }

    /** Donor repairable(material.repairItems()). */
    @Override
    public boolean canRepair(ItemStack stack, ItemStack ingredient) {
        return this.material.getRepairIngredient().test(ingredient) || super.canRepair(stack, ingredient);
    }

    /** Donor enchantable(material.enchantmentValue()). */
    @Override
    public int getEnchantability() {
        return this.material.getEnchantability();
    }

    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (this.extraAttackDamage == 0.0 || slot != EquipmentSlot.MAINHAND) {
            return super.getAttributeModifiers(slot);
        }
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        for (Map.Entry<Attribute, AttributeModifier> entry : super.getAttributeModifiers(slot).entries()) {
            AttributeModifier modifier = entry.getValue();
            if (entry.getKey() == Attributes.ATTACK_DAMAGE) {
                builder.put(entry.getKey(), new AttributeModifier(modifier.getId(), modifier.getName(),
                        modifier.getValue() + this.extraAttackDamage, modifier.getOperation()));
            } else {
                builder.put(entry);
            }
        }
        return builder.build();
    }
}
