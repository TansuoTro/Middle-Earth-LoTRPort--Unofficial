package net.jukoz.me.compat;import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.item.Item;/**
 * The 1.21.2 ToolMaterial record, in 1.20.1 terms.
 *
 * <p>1.21 replaced the numeric mining level with a tag of blocks the tool cannot
 * harvest, and made ToolMaterial a record that the mod instantiates directly. On
 * 1.20.1 ToolMaterial is an interface driven by the numeric mining level, so the
 * donor's incorrect-blocks tag is translated back into the level it stands for
 * at the construction site (see ToolMaterialsME).
 */
public record SimpleToolMaterial(int miningLevel, int durability, float speed,
                                 float attackDamageBonus, int enchantmentValue, TagKey<Item> repairItems)
        implements ToolMaterial {    @Override
    public int getDurability() {
        return durability;
    }    @Override
    public float getMiningSpeedMultiplier() {
        return speed;
    }    @Override
    public float getAttackDamage() {
        return attackDamageBonus;
    }    @Override
    public int getMiningLevel() {
        return miningLevel;
    }    @Override
    public int getEnchantability() {
        return enchantmentValue;
    }    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.fromTag(repairItems);
    }
}
