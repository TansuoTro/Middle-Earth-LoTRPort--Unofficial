package net.jukoz.me.item.utils;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.jukoz.me.entity.EntityAttributesME;
import net.jukoz.me.item.MESettings;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tier;
import net.jukoz.me.MiddleEarth;
import net.minecraft.resources.ResourceLocation;

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
 *
 * <p>The weapon half of the donor's settings is served from the weapon item classes instead:
 * {@link net.jukoz.me.item.items.weapons.ReachWeaponItem} overrides
 * {@code getAttributeModifiers(EquipmentSlot)} (built by {@link #createWeaponAttibutes}),
 * {@code canRepair(ItemStack, ItemStack)} ({@code repairable(material.repairItems())}) and
 * {@code getEnchantability()} ({@code enchantable(material.enchantmentValue())}).
 */
public interface ItemSettingsME {

    String ENTITY_INTERACTION_RANGE_MODIFIER_ID = "me.entity_interaction_range";

    static MESettings createWeaponSettings(Tier material, Item.Properties settings, WeaponTypesME type) {
        // Donor: maxDamage + repairable + enchantable + attributeModifiers + TOOL/WEAPON components.
        // 1.20.1's Item.Properties only holds maxDamage; the rest is served from the weapon classes
        // (see the interface javadoc).
        return settings instanceof MESettings meSettings ? meSettings.maxDamage(material.getUses()) : new MESettings().maxDamage(material.getUses());
    }

    static Multimap<Attribute, AttributeModifier> createWeaponAttibutes(
            Tier material, float attackDamage, float attackSpeed, float attackRange) {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                "me.weapon_damage", attackDamage + material.getAttackDamage(), AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.GENERIC_ATTACK_SPEED, new AttributeModifier(
                "me.weapon_speed", attackSpeed, AttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributesME.ENTITY_INTERACTION_RANGE.value(), new AttributeModifier(
                ENTITY_INTERACTION_RANGE_MODIFIER_ID, attackRange, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }

    /**
     * The donor's "blunted" swap: one point before breaking, an artefact weapon replaces its
     * modifiers with 0 damage, -3.0 speed and 0 range (see {@code MorgulKnifeItem} in the donor).
     * The modifier names match {@link #createWeaponAttibutes} so the equipped weapon's live
     * modifiers are replaced rather than left behind.
     */
    static Multimap<Attribute, AttributeModifier> createBluntedWeaponAttibutes() {
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(
                "me.weapon_damage", 0.0F, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.GENERIC_ATTACK_SPEED, new AttributeModifier(
                "me.weapon_speed", -3.0F, AttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributesME.ENTITY_INTERACTION_RANGE.value(), new AttributeModifier(
                ENTITY_INTERACTION_RANGE_MODIFIER_ID, 0.0F, AttributeModifier.Operation.ADDITION));
        return builder.build();
    }

    static MESettings goatArmor(net.minecraft.world.item.ArmorMaterial material) {
        return mountArmor(material, "broadhoof_goat", "beast_armor/");
    }

    static MESettings wargArmor(net.minecraft.world.item.ArmorMaterial material) {
        return mountArmor(material, "warg", "beast_armor/");
    }

    static MESettings greatHornArmor(net.minecraft.world.item.ArmorMaterial material) {
        return mountArmor(material, "great_horn", "");
    }

    /**
     * Mount armor is consumed by AbstractBeastEntity and its render features. It has no
     * damageable item setting, preserving the donor's no-damage-on-hurt equipment behavior.
     */
    private static MESettings mountArmor(net.minecraft.world.item.ArmorMaterial material, String entity, String assetPrefix) {
        int bodyDefense = switch (material.getProtection(net.minecraft.world.item.ArmorItem.Type.CHESTPLATE)) {
            case 4 -> 5;
            case 5 -> 6;
            case 6 -> 8;
            case 7 -> 10;
            default -> 4;
        };
        return new MESettings().maxCount(1)
                .component(net.jukoz.me.item.MEComponents.EQUIPPABLE_ASSET, MiddleEarth.of(assetPrefix + material.getName()))
                .component(net.jukoz.me.item.MEComponents.MOUNT_EQUIPPABLE,
                        new net.jukoz.me.item.dataComponents.MountEquippableComponent(MiddleEarth.of(entity), bodyDefense,
                                material.getToughness(), material.getKnockbackResistance(),
                                net.minecraft.core.registries.Registries.SOUND_EVENT.getId(material.getEquipSound())));
    }
}
