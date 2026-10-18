package net.jukoz.me.compat;

import dev.architectury.utils.GameInstance;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.plugins.REICommonPlugin;
import me.shedaniel.rei.api.common.registry.display.ServerDisplayRegistry;
import me.shedaniel.rei.api.common.util.EntryIngredients;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.compat.artisantable.ArtisanTableDisplay;
import net.jukoz.me.compat.forge.AlloyingDisplay;
import net.jukoz.me.recipe.AlloyingRecipe;
import net.jukoz.me.recipe.ArtisanRecipe;
import net.jukoz.me.recipe.RecipesME;

import java.util.Collection;
import java.util.List;

public class REICommonPluginME implements REICommonPlugin {
    public static final CategoryIdentifier<ArtisanTableDisplay> ARTISAN_TABLE_CATEGORY = CategoryIdentifier.of(MiddleEarth.MOD_ID, "artisan_table");
    public static final CategoryIdentifier<AlloyingDisplay> FORGE_CATEGORY = CategoryIdentifier.of(MiddleEarth.MOD_ID, "forge");

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
        REICommonPlugin.super.registerDisplaySerializer(registry);
        registry.register(MiddleEarth.of("artisan_table"), ArtisanTableDisplay.SERIALIZER);
        registry.register(MiddleEarth.of("forge"), AlloyingDisplay.SERIALIZER);
    }

    @Override
    public void registerDisplays(ServerDisplayRegistry registry) {
        REICommonPlugin.super.registerDisplays(registry);

        Collection<ArtisanRecipe> recipeEntryList = GameInstance.getServer().getRecipeManager().listAllOfType(RecipesME.ARTISAN_TABLE_SUPPLIER.get());
        for(ArtisanRecipe recipeEntry : recipeEntryList) {
            ArtisanRecipe artisanRecipe = recipeEntry;
            List<EntryIngredient> inputs = ArtisanTableDisplay.getInputs(artisanRecipe);
            registry.add(new ArtisanTableDisplay(inputs, List.of(EntryIngredients.of(artisanRecipe.getOutput(GameInstance.getServer().getRegistryManager()))), artisanRecipe.category));
        }

        Collection<AlloyingRecipe> alloyRecipeEntryList = GameInstance.getServer().getRecipeManager().listAllOfType(RecipesME.FORGE);
        for(AlloyingRecipe recipeEntry : alloyRecipeEntryList) {
            AlloyingRecipe alloyRecipe = recipeEntry;
            if(!alloyRecipe.output.contains("nugget")) {
                List<EntryIngredient> inputs = AlloyingDisplay.getInputs(alloyRecipe);
                registry.add(new AlloyingDisplay(inputs, alloyRecipe.output, alloyRecipe.amount));
            }
        }
    }
}
