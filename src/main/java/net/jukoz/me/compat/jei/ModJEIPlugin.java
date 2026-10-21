package net.jukoz.me.compat.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.gui.artisantable.ArtisanTableScreen;
import net.jukoz.me.gui.forge.ForgeAlloyingScreen;
import net.jukoz.me.recipe.AlloyRecipe;
import net.jukoz.me.recipe.AlloyingRecipe;
import net.jukoz.me.recipe.ArtisanRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

/**
 * JEI counterpart of the old REI plugin ({@code ModREIClientPlugin} / {@code REIClientPluginME}).
 * Same three categories and the same recipe sources, only the viewer API differs.
 */
@JeiPlugin
public class ModJEIPlugin implements IModPlugin {
    public static final RecipeType<ArtisanRecipe> ARTISAN_TABLE =
            RecipeType.create(MiddleEarth.MOD_ID, "artisan_table", ArtisanRecipe.class);
    public static final RecipeType<AlloyRecipe> ALLOY_FURNACE =
            RecipeType.create(MiddleEarth.MOD_ID, "alloy_furnace", AlloyRecipe.class);
    public static final RecipeType<AlloyingRecipe> FORGE =
            RecipeType.create(MiddleEarth.MOD_ID, "forge", AlloyingRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return MiddleEarth.of("jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
                new ArtisanTableCategory(guiHelper),
                new AlloyFurnaceCategory(guiHelper),
                new ForgeAlloyingCategory(guiHelper));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        Level level = Minecraft.getInstance().level;
        if (level == null) return;
        RecipeManager recipeManager = level.getRecipeManager();
        registration.addRecipes(ARTISAN_TABLE, recipesOf(recipeManager, ArtisanRecipe.Type.INSTANCE, ArtisanRecipe.class));
        registration.addRecipes(ALLOY_FURNACE, recipesOf(recipeManager, AlloyRecipe.Type.INSTANCE, AlloyRecipe.class));
        registration.addRecipes(FORGE, recipesOf(recipeManager, AlloyingRecipe.Type.INSTANCE, AlloyingRecipe.class));
    }

    /**
     * {@code RecipeManager#getAllRecipesFor} only accepts recipes whose input type extends
     * {@code Container}, which AlloyingRecipe does not (its input is a plain MultipleStackRecipeInput).
     * Filter the whole recipe list by type instead so all three categories share one code path.
     */
    private static <T> List<T> recipesOf(RecipeManager recipeManager,
                                         net.minecraft.world.item.crafting.RecipeType<?> type,
                                         Class<T> recipeClass) {
        List<T> recipes = new ArrayList<>();
        for (Recipe<?> recipe : recipeManager.getRecipes()) {
            if (recipe.getType() == type) {
                recipes.add(recipeClass.cast(recipe));
            }
        }
        return recipes;
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModDecorativeBlocks.ARTISAN_TABLE), ARTISAN_TABLE);
        // The alloy furnace and the forge are the same block (MEBlocksLegacy.ALLOY_FURNACE == ModDecorativeBlocks.FORGE).
        registration.addRecipeCatalyst(new ItemStack(ModDecorativeBlocks.FORGE), ALLOY_FURNACE, FORGE);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(ArtisanTableScreen.class, 75, 30, 20, 30, ARTISAN_TABLE);
        registration.addRecipeClickArea(ForgeAlloyingScreen.class, 75, 30, 20, 30, FORGE);
    }
}
