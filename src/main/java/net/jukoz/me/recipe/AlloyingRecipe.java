package net.jukoz.me.recipe;
import net.jukoz.me.compat.NbtCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.jukoz.me.block.special.forge.MultipleStackRecipeInput;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.List;
import java.util.Locale;

public class AlloyingRecipe implements Recipe<MultipleStackRecipeInput> {
    public final Identifier id;
    public final String output;
    public final int amount;
    public final List<Ingredient> inputs;
    private final CraftingRecipeCategory category;
    private final String group;
    private final int xp;

    public AlloyingRecipe(Identifier id, String group, CraftingRecipeCategory category, String output, List<Ingredient> recipeItems, int amount, int xp) {
        this.id = id;
        this.output = output;
        this.group = group;
        this.inputs = recipeItems;
        this.amount = amount;
        this.category = category;
        this.xp = xp;
    }

    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
        defaultedList.addAll(this.inputs);
        return defaultedList;
    }

    public String getGroup() {
        return this.group;
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    public CraftingRecipeCategory getCategory() {
        return this.category;
    }

    @Override
    public boolean matches(MultipleStackRecipeInput input, World world) {
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
        return null;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return ItemStack.EMPTY;
    }

    @Override
    public Identifier getId() {
        return id;
    }

    public String craftAlloy(MultipleStackRecipeInput input, DynamicRegistryManager lookup) {
        return output;
    }

    public String getAlloyResult() {
        return output;
    }

    public int getAmount() {
        return amount;
    }

    public int getXp() {
        return xp;
    }

    @Override
    public RecipeSerializer<? extends Recipe<MultipleStackRecipeInput>> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<? extends Recipe<MultipleStackRecipeInput>> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<AlloyingRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "alloying";
    }

    public static class Serializer implements RecipeSerializer<AlloyingRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "alloying";

        @Override
        public AlloyingRecipe read(Identifier id, JsonObject json) {
            String group = JsonHelper.getString(json, "group", "");
            CraftingRecipeCategory category = CraftingRecipeCategory.MISC;
            if (JsonHelper.hasString(json, "category")) {
                try {
                    category = CraftingRecipeCategory.valueOf(JsonHelper.getString(json, "category").toUpperCase(Locale.ROOT));
                } catch (IllegalArgumentException ignored) {}
            }
            String output = JsonHelper.getString(json, "output");
            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(ingredients.size(), Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                Ingredient ingredient = Ingredient.fromJson(ingredients.get(i), true);
                if (ingredient.isEmpty()) continue;
                inputs.set(i, ingredient);
            }
            int amount = JsonHelper.getInt(json, "amount");
            int xp = JsonHelper.getInt(json, "xp");

            return new AlloyingRecipe(id, group, category, output, inputs, amount, xp);
        }

        @Override
        public AlloyingRecipe read(Identifier id, PacketByteBuf buf) {
            String group = buf.readString();
            CraftingRecipeCategory category = buf.readEnumConstant(CraftingRecipeCategory.class);
            String output = buf.readString();
            int amount = buf.readInt();
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readVarInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }
            int xp = buf.readVarInt();

            return new AlloyingRecipe(id, group, category, output, inputs, amount, xp);
        }

        @Override
        public void write(PacketByteBuf buf, AlloyingRecipe recipe) {
            buf.writeString(recipe.group);
            buf.writeEnumConstant(recipe.category);
            buf.writeString(recipe.output);
            buf.writeInt(recipe.amount);
            buf.writeVarInt(recipe.inputs.size());
            for (Ingredient ingredient : recipe.inputs) {
                ingredient.write(buf);
            }
            buf.writeVarInt(recipe.xp);
        }
    }
}
