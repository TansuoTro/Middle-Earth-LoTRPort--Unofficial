package net.jukoz.me.compat.forge;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.world.item.ItemStack;
import net.jukoz.me.block.special.forge.MetalTypes;
import net.jukoz.me.compat.REICommonPluginME;
import net.jukoz.me.recipe.AlloyingRecipe;
import java.util.ArrayList;
import java.util.List;

public class AlloyingDisplay extends BasicDisplay {
    protected String output;
    protected int amount;

    public AlloyingDisplay(List<EntryIngredient> inputs, String output, int amount) {
        super(inputs, List.of(EntryIngredients.of(new ItemStack(MetalTypes.fromValue(output).getIngot()))));
        this.output = output;
        this.amount = amount;
    }

    public AlloyingDisplay(AlloyingRecipe recipe) {
        this(getInputs(recipe), recipe.output, recipe.amount);
    }

    public static List<EntryIngredient> getInputs(AlloyingRecipe recipe) {
        return new ArrayList<>(EntryIngredients.ofIngredients(recipe.getIngredients()));
    }

    public String getOutput() {
        return this.output;
    }

    public int getAmount() {
        return this.amount;
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return REICommonPluginME.FORGE_CATEGORY;
    }
}
