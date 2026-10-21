package net.jukoz.me.compat.jei;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.block.special.forge.MetalTypes;
import net.jukoz.me.gui.forge.ForgeAlloyingScreen;
import net.jukoz.me.recipe.AlloyingRecipe;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.List;

/** Molten-metal alloying in the forge block: up to four ingots in, one metal type out. */
public class ForgeAlloyingCategory implements IRecipeCategory<AlloyingRecipe> {
    public static final ResourceLocation TEXTURE = MiddleEarth.of("textures/gui/forge_rei.png");
    private static final int WIDTH = 175;
    private static final int HEIGHT = 82;
    private static final int FIRST_INPUT_X = 41;
    private static final int INPUT_Y = 16;
    private static final int SLOT_SPACING = 18;
    /** 576 mB fills the whole gauge, same scale as the REI category. */
    private static final float FULL_GAUGE = 576.0F;

    private final IDrawable background;
    private final IDrawable icon;

    public ForgeAlloyingCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createDrawable(TEXTURE, 0, 0, WIDTH, HEIGHT);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModDecorativeBlocks.FORGE));
    }

    @Override
    public RecipeType<AlloyingRecipe> getRecipeType() {
        return ModJEIPlugin.FORGE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("screen.me.forge");
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
    public void setRecipe(IRecipeLayoutBuilder builder, AlloyingRecipe recipe, IFocusGroup focuses) {
        List<Ingredient> inputs = recipe.getIngredients();
        for (int i = 0; i < inputs.size() && i < 4; i++) {
            builder.addSlot(RecipeIngredientRole.INPUT, FIRST_INPUT_X + SLOT_SPACING * i, INPUT_Y)
                    .addIngredients(inputs.get(i));
        }
        MetalTypes metal = MetalTypes.fromValue(recipe.output);
        if (metal != null) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 129, 35).addItemStack(new ItemStack(metal.getIngot()));
        }
    }

    @Override
    public void createRecipeExtras(IRecipeExtrasBuilder builder, AlloyingRecipe recipe, IFocusGroup focuses) {
        builder.addText(Component.translatable("trim_material.me." + recipe.output), 77, 5);
    }

    @Override
    public void draw(AlloyingRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        guiGraphics.blit(TEXTURE, 66, 44, 218, 14, 20, 15);
        int storedLiquid = (int) (Math.min(1.0F, (float) recipe.amount / FULL_GAUGE) * ForgeAlloyingScreen.LIQUID_HEIGHT);
        if (storedLiquid > 0) {
            guiGraphics.blit(TEXTURE, 106, 75 - storedLiquid, 211, 76 - storedLiquid, 20, storedLiquid);
        }
    }
}
