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
import net.jukoz.me.recipe.AlloyRecipe;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

public class AlloyFurnaceCategory implements IRecipeCategory<AlloyRecipe> {
    public static final ResourceLocation TEXTURE = MiddleEarth.of("textures/gui/alloy_furnace_rei.png");
    private static final int WIDTH = 175;
    private static final int HEIGHT = 82;
    private static final int[][] INPUT_POSITIONS = {{26, 17}, {44, 17}, {62, 17}, {80, 17}};

    private final IDrawable background;
    private final IDrawable icon;

    public AlloyFurnaceCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, WIDTH, HEIGHT);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModDecorativeBlocks.FORGE));
    }

    @Override
    public RecipeType<AlloyRecipe> getRecipeType() {
        return ModJEIPlugin.ALLOY_FURNACE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("item.me.alloy_furnace");
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
    public void setRecipe(IRecipeLayoutBuilder builder, AlloyRecipe recipe, IFocusGroup focuses) {
        List<Ingredient> inputs = recipe.getIngredients();
        for (int i = 0; i < inputs.size() && i < INPUT_POSITIONS.length; i++) {
            builder.addSlot(RecipeIngredientRole.INPUT, INPUT_POSITIONS[i][0], INPUT_POSITIONS[i][1])
                    .addIngredients(inputs.get(i));
        }
        builder.addSlot(RecipeIngredientRole.OUTPUT, 129, 35).addItemStack(recipe.getOutput(null));
    }
}
