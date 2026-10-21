package net.jukoz.me.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.level.Level;
import net.jukoz.me.block.special.forge.MultipleStackRecipeInput;

import java.util.List;

public class CrockpotRecipe implements Recipe<MultipleStackRecipeInput> {
    public final ResourceLocation id;
    public final int ingredientsAmount;
    public final List<Ingredient> inputs;
    public final ItemStack output;

    public CrockpotRecipe(ResourceLocation id, int ingredientsAmount, List<Ingredient> inputs, ItemStack output) {
        this.id = id;
        this.ingredientsAmount = ingredientsAmount;
        this.inputs = inputs;
        this.output = output;
    }

    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.addAll(this.inputs);
        return defaultedList;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    @Override
    public boolean matches(MultipleStackRecipeInput input, Level world) {
        if(world.isClient()) return false;
        int i = 0;
        for (int j = 0; j < input.size(); j++) {
            ItemStack itemStack = input.getStack(j);
            if (itemStack.isEmpty()) continue;
            i++;
        }
        if(i != this.inputs.size()) return false;

        for (int j = 0; j < inputs.size(); j++) {
            if(!inputs.get(j).test(input.getStack(j))) return false;
        }
        return true;
    }

    @Override
    public ItemStack craft(MultipleStackRecipeInput input, DynamicRegistryManager lookup) {
        return this.output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<? extends Recipe<MultipleStackRecipeInput>> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<? extends Recipe<MultipleStackRecipeInput>> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<CrockpotRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "crockpot";
    }

    public static class Serializer implements RecipeSerializer<CrockpotRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "crockpot";

        @Override
        public CrockpotRecipe read(ResourceLocation id, JsonObject json) {
            int ingredientsAmount = JsonHelper.getInt(json, "ingredients_amount");
            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(ingredients.size(), Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                Ingredient ingredient = Ingredient.fromJson(ingredients.get(i), true);
                if (ingredient.isEmpty()) continue;
                inputs.set(i, ingredient);
            }
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));

            return new CrockpotRecipe(id, ingredientsAmount, inputs, output);
        }

        @Override
        public CrockpotRecipe read(ResourceLocation id, FriendlyByteBuf buf) {
            int ingredientsAmount = buf.readInt();
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }
            ItemStack output = buf.readItemStack();

            return new CrockpotRecipe(id, ingredientsAmount, inputs, output);
        }

        @Override
        public void write(FriendlyByteBuf buf, CrockpotRecipe recipe) {
            buf.writeInt(recipe.ingredientsAmount);
            buf.writeInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                ingredient.write(buf);
            }
            buf.writeItemStack(recipe.output);
        }
    }
}
