package net.jukoz.me.item.items.weapons;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.jukoz.me.entity.EntityAttributesME;
import net.jukoz.me.item.MEComponents;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.jukoz.me.item.dataComponents.WeaponTypeDataComponent;
import net.jukoz.me.item.utils.ItemSettingsME;
import net.jukoz.me.item.utils.WeaponTypesME;

public class TrollWeaponItem extends SwordItem {

    private final ToolMaterial material;

    public TrollWeaponItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, (int) WeaponTypesME.TROLL_WEAPON.attack, WeaponTypesME.TROLL_WEAPON.attackSpeed,
                ((net.jukoz.me.item.MESettings) settings).maxDamage(toolMaterial.getDurability()).component(MEComponents.WEAPON_TYPE_DATA, new WeaponTypeDataComponent(WeaponTypesME.TROLL_WEAPON.name)));
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
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot != EquipmentSlot.MAINHAND) {
            return super.getAttributeModifiers(slot);
        }
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.putAll(super.getAttributeModifiers(slot));
        builder.put(EntityAttributesME.ENTITY_INTERACTION_RANGE.value(), new EntityAttributeModifier(
                ItemSettingsME.ENTITY_INTERACTION_RANGE_MODIFIER_ID,
                WeaponTypesME.TROLL_WEAPON.attackRange,
                EntityAttributeModifier.Operation.ADDITION));
        return builder.build();
    }
}
