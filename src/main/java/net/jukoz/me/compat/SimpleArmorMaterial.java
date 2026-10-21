package net.jukoz.me.compat;

import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.sounds.SoundEvent;

import java.util.EnumMap;

/**
 * The 1.21 ArmorMaterial record, in 1.20.1 terms.
 *
 * <p>1.21 made ArmorMaterial a record that is registered like any other item property and
 * resolved through a {@code RegistryEntry}. On 1.20.1 ArmorMaterial is still an interface
 * with per-slot durability and protection lookups, so the donor's per-type defence map and
 * its single durability multiplier are folded into the vanilla formulas.
 */
public record SimpleArmorMaterial(int durabilityMultiplier, EnumMap<ArmorItem.Type, Integer> defense,
                                  int enchantability, SoundEvent equipSound, float toughness,
                                  float knockbackResistance, TagKey<Item> repairItems, String name)
        implements ArmorMaterial {

    /** Vanilla's base durability per armour slot; the material multiplier scales it. */
    private static int baseDurability(ArmorItem.Type type) {
        return switch (type) {
            case HELMET -> 11;
            case CHESTPLATE -> 16;
            case LEGGINGS -> 15;
            case BOOTS -> 13;
        };
    }

    @Override
    public int getDurability(ArmorItem.Type type) {
        return baseDurability(type) * this.durabilityMultiplier;
    }

    @Override
    public int getProtection(ArmorItem.Type type) {
        return this.defense.getOrDefault(type, 0);
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public SoundEvent getEquipSound() {
        return this.equipSound;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.fromTag(this.repairItems);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public float getToughness() {
        return this.toughness;
    }

    @Override
    public float getKnockbackResistance() {
        return this.knockbackResistance;
    }
}
