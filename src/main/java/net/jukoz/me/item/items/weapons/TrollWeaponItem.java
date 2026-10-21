package net.jukoz.me.item.items.weapons;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.jukoz.me.entity.EntityAttributesME;
import net.jukoz.me.item.MEComponents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.jukoz.me.item.dataComponents.WeaponTypeDataComponent;
import net.jukoz.me.item.utils.ItemSettingsME;
import net.jukoz.me.item.utils.WeaponTypesME;

public class TrollWeaponItem extends SwordItem {

    private final Tier material;

    public TrollWeaponItem(Tier toolMaterial, Settings settings) {
        super(toolMaterial, (int) WeaponTypesME.TROLL_WEAPON.attack, WeaponTypesME.TROLL_WEAPON.attackSpeed,
                ((net.jukoz.me.item.MESettings) settings).maxDamage(toolMaterial.getUses()).component(MEComponents.WEAPON_TYPE_DATA, new WeaponTypeDataComponent(WeaponTypesME.TROLL_WEAPON.name)));
        this.material = toolMaterial;
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

    /**
     * WeaponTypesME.TROLL_WEAPON carries attackRange +1.0 (dagger -1.0, spear/longsword/troll +1.0);
     * the SwordItem base only emits the damage/speed modifiers, so the interaction-range one is
     * added here.
     */
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot != EquipmentSlot.MAINHAND) {
            return super.getAttributeModifiers(slot);
        }
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(super.getAttributeModifiers(slot));
        builder.put(EntityAttributesME.ENTITY_INTERACTION_RANGE.value(), new AttributeModifier(
                ItemSettingsME.ENTITY_INTERACTION_RANGE_MODIFIER_ID,
                WeaponTypesME.TROLL_WEAPON.attackRange,
                AttributeModifier.Operation.ADDITION));
        return builder.build();
    }
}
