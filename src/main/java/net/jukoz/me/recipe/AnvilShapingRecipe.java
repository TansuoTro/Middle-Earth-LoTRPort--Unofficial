package net.jukoz.me.recipe;
import net.jukoz.me.item.MEComponents;

import com.google.gson.JsonObject;
import net.minecraft.recipe.*;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.jukoz.me.compat.SingleStackRecipeInput;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;

public class AnvilShapingRecipe implements Recipe<SingleStackRecipeInput> {
    public final Identifier id;
    protected final Ingredient input;
    protected final ItemStack output;
    protected final int amount;

    public AnvilShapingRecipe(Identifier id, Ingredient input, ItemStack output, int amount) {
        this.id = id;
        this.output = output;
        this.input = input;
        this.amount = amount;
    }

    public ItemStack createIcon() {
        return new ItemStack(ModDecorativeBlocks.TREATED_ANVIL);
    }

    @Override
    public boolean matches(SingleStackRecipeInput input, World world) {
        if(input.item().isEmpty()) return false;
        //if(input.item().get(MEComponents.TEMPERATURE_DATA) == null) return false;
        return this.input.test(input.item());
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput input, DynamicRegistryManager lookup) {
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
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return output;
    }

    @Override
    public Identifier getId() {
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
        public AnvilShapingRecipe read(Identifier id, JsonObject json) {
            Ingredient input = Ingredient.fromJson(JsonHelper.getElement(json, "ingredient"), true);
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));
            int amount = JsonHelper.getInt(json, "amount");
            return new AnvilShapingRecipe(id, input, output, amount);
        }

        @Override
        public AnvilShapingRecipe read(Identifier id, PacketByteBuf buf) {
            Ingredient input = Ingredient.fromPacket(buf);
            ItemStack output = buf.readItemStack();
            int amount = buf.readInt();
            return new AnvilShapingRecipe(id, input, output, amount);
        }

        @Override
        public void write(PacketByteBuf buf, AnvilShapingRecipe recipe) {
            recipe.input.write(buf);
            buf.writeItemStack(recipe.output);
            buf.writeInt(recipe.amount);
        }
    }
}
