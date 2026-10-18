package net.jukoz.me.compat;

import net.minecraft.block.Block;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.item.Item;

/**
 * The 1.21.2 ToolMaterial record, in 1.20.1 terms.
 *
 * <p>1.21 replaced the numeric mining level with a tag of blocks the tool cannot
 * harvest, and made ToolMaterial a record that the mod instantiates directly. On
 * 1.20.1 ToolMaterial is an interface, so the tag is translated back into the level
 * it stands for.
 */
public record SimpleToolMaterial(TagKey<Block> incorrectBlocksForDrops, int durability, float speed,
                                 float attackDamageBonus, int enchantmentValue, TagKey<Item> repairItems)
        implements ToolMaterial {

    @Override
    public int getDurability() {
        return durability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return speed;
    }

    @Override
    public float getAttackDamage() {
        return attackDamageBonus;
    }

    @Override
    public int getMiningLevel() {
        if (incorrectBlocksForDrops == BlockTags.INCORRECT_FOR_NETHERITE_TOOL) return 4;
        if (incorrectBlocksForDrops == BlockTags.INCORRECT_FOR_DIAMOND_TOOL) return 3;
        if (incorrectBlocksForDrops == BlockTags.INCORRECT_FOR_IRON_TOOL) return 2;
        if (incorrectBlocksForDrops == BlockTags.INCORRECT_FOR_STONE_TOOL) return 1;
        return 0;
    }

    @Override
    public int getEnchantability() {
        return enchantmentValue;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.fromTag(repairItems);
    }
}
