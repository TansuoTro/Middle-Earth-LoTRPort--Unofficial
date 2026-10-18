package net.jukoz.me.item.utils;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.jukoz.me.entity.EntityAttributesME;
import net.jukoz.me.item.MESettings;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.ToolMaterial;
import net.jukoz.me.MiddleEarth;
import net.minecraft.util.Identifier;

/**
 * Shared item settings helpers.
 *
 * <p>The 1.21.5 donor builds weapon and mount-armour settings out of data components:
 * {@code TOOL} / {@code WEAPON} on every weapon, and {@code EQUIPPABLE} (with a
 * {@code EquipmentSlot.CHEST} slot and an allowed-entities tag) on goat / warg / great-horn
 * armour. 1.20.1 has neither component: weapon behaviour comes from the item class itself
 * ({@code SwordItem}, {@code AxeItem}, ...), and mount armour is a {@code HorseArmorItem},
 * which has no allowed-entities filter.
 *
 * <p>So the component parts are dropped and only what 1.20.1 can express is kept. The mount
 * armours are inert here; equipping them properly needs a follow-up port of the armour items.
 */
public interface ItemSettingsME {

    String ENTITY_INTERACTION_RANGE_MODIFIER_ID = "me.entity_interaction_range";

    static MESettings createWeaponSettings(ToolMaterial material, Item.Settings settings, WeaponTypesME type) {
        return settings instanceof MESettings meSettings ? meSettings.maxDamage(material.getDurability()) : new MESettings().maxDamage(material.getDurability());
    }

    static Multimap<EntityAttribute, EntityAttributeModifier> createWeaponAttibutes(
            ToolMaterial material, float attackDamage, float attackSpeed, float attackRange) {
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(
                "me.weapon_damage", attackDamage + material.getAttackDamage(), EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(
                "me.weapon_speed", attackSpeed, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributesME.ENTITY_INTERACTION_RANGE.value(), new EntityAttributeModifier(
                ENTITY_INTERACTION_RANGE_MODIFIER_ID, attackRange, EntityAttributeModifier.Operation.ADDITION));
        return builder.build();
    }

    static MESettings goatArmor(net.minecraft.item.ArmorMaterial material) {
        return mountArmor();
    }

    static MESettings wargArmor(net.minecraft.item.ArmorMaterial material) {
        return mountArmor();
    }

    static MESettings greatHornArmor(net.minecraft.item.ArmorMaterial material) {
        return mountArmor();
    }

    /**
     * TODO(port): the donor's EQUIPPABLE component carried the equip sound, the armour model,
     * the allowed-entity tag and the "no damage on hurt" flag. None of that exists on 1.20.1,
     * where mount armour is a HorseArmorItem with a hard-coded texture and a fixed bonus.
     */
    private static MESettings mountArmor() {
        return new MESettings().maxCount(1);
    }
}
