package net.jukoz.me.compat.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.recipe.ArtisanRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class ArtisanTableCategory implements IRecipeCategory<ArtisanRecipe> {
    public static final ResourceLocation TEXTURE = MiddleEarth.of("textures/gui/artisan_table_rei.png");
    private static final int WIDTH = 175;
    private static final int HEIGHT = 82;
    /** Same slot anchors as the REI category: a 2x2 block plus two slots underneath. */
    private static final int[][] INPUT_POSITIONS = {{31, 14}, {12, 34}, {31, 34}, {50, 34}, {21, 53}, {41, 53}};

    private final IDrawable background;
    private final IDrawable icon;

    public ArtisanTableCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, WIDTH, HEIGHT);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModDecorativeBlocks.ARTISAN_TABLE));
    }

    @Override
    public RecipeType<ArtisanRecipe> getRecipeType() {
        return ModJEIPlugin.ARTISAN_TABLE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("container.me.artisan_table");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ArtisanRecipe recipe, IFocusGroup focuses) {
        List<Ingredient> inputs = recipe.getIngredients();
        for (int i = 0; i < inputs.size() && i < INPUT_POSITIONS.length; i++) {
            builder.addSlot(RecipeIngredientRole.INPUT, INPUT_POSITIONS[i][0], INPUT_POSITIONS[i][1])
                    .addIngredients(inputs.get(i));
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 128, 35).addItemStack(recipe.getOutput(null));
    }
}
