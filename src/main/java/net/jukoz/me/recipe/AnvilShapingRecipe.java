package net.jukoz.me.recipe;

import net.jukoz.me.item.MEComponents;
import com.google.gson.JsonObject;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.compat.SingleStackRecipeInput;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class AnvilShapingRecipe implements Recipe<SingleStackRecipeInput> {
    public final ResourceLocation id;
    protected final Ingredient input;
    protected final ItemStack output;
    protected final int amount;

    public AnvilShapingRecipe(ResourceLocation id, Ingredient input, ItemStack output, int amount) {
        this.id = id;
        this.output = output;
        this.input = input;
        this.amount = amount;
    }

    public ItemStack createIcon() {
        return new ItemStack(ModDecorativeBlocks.TREATED_ANVIL);
    }

    @Override
    public boolean matches(SingleStackRecipeInput input, Level world) {
        if(input.item().isEmpty()) return false;
        //if(input.item().get(MEComponents.TEMPERATURE_DATA) == null) return false;
        return this.input.test(input.item());
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput input, RegistryAccess lookup) {
        return this.output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    public ItemStack getOutput() {
        return output;
    }

    @Override
    public ItemStack getOutput(RegistryAccess registryManager) {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public Ingredient getIngredient() {
        return this.input;
    }

    public int getAmount() {
        return this.amount;
    }

    @Override
    public RecipeSerializer<? extends Recipe<SingleStackRecipeInput>> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<? extends Recipe<SingleStackRecipeInput>> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<AnvilShapingRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "anvil_shaping";
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<AnvilShapingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "anvil_shaping";

        @Override
        public AnvilShapingRecipe read(ResourceLocation id, JsonObject json) {
            Ingredient input = Ingredient.fromJson(GsonHelper.getElement(json, "ingredient"), true);
            ItemStack output = ShapedRecipe.outputFromJson(GsonHelper.getObject(json, "output"));
            int amount = GsonHelper.getInt(json, "amount");
            return new AnvilShapingRecipe(id, input, output, amount);
        }

        @Override
        public AnvilShapingRecipe read(ResourceLocation id, FriendlyByteBuf buf) {
            Ingredient input = Ingredient.fromPacket(buf);
            ItemStack output = buf.readItemStack();
            int amount = buf.readInt();
            return new AnvilShapingRecipe(id, input, output, amount);
        }

        @Override
        public void write(FriendlyByteBuf buf, AnvilShapingRecipe recipe) {
            recipe.input.write(buf);
            buf.writeItemStack(recipe.output);
            buf.writeInt(recipe.amount);
        }
    }
}
