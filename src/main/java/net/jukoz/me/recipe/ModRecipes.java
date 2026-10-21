package net.jukoz.me.recipe;

import net.jukoz.me.MiddleEarth;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.recipe.SmithingRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class ModRecipes {
    public static RecipeType<ArtisanRecipe> ARTISAN_TABLE;

    public static void registerRecipes() {
        Registry.register(Registries.RECIPE_SERIALIZER,
                new ResourceLocation(MiddleEarth.MOD_ID, AlloyRecipe.Serializer.ID),
                AlloyRecipe.Serializer.INSTANCE);
        Registry.register(Registries.RECIPE_TYPE,
                new ResourceLocation(MiddleEarth.MOD_ID, AlloyRecipe.Type.ID),
                AlloyRecipe.Type.INSTANCE);

        Registry.register(Registries.RECIPE_SERIALIZER,
                new ResourceLocation(MiddleEarth.MOD_ID, ArtisanRecipe.Serializer.ID),
                ArtisanRecipe.Serializer.INSTANCE);
        ARTISAN_TABLE = Registry.register(Registries.RECIPE_TYPE,
                new ResourceLocation(MiddleEarth.MOD_ID, ArtisanRecipe.Type.ID),
                ArtisanRecipe.Type.INSTANCE);
    }
}
