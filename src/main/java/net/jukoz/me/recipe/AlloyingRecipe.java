package net.jukoz.me.recipe;

import net.jukoz.me.compat.NbtCompat;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.jukoz.me.block.special.forge.MultipleStackRecipeInput;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.world.level.Level;
import java.util.List;
import java.util.Locale;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class AlloyingRecipe implements Recipe<MultipleStackRecipeInput> {
    public final ResourceLocation id;
    public final String output;
    public final int amount;
    public final List<Ingredient> inputs;
    private final CraftingBookCategory category;
    private final String group;
    private final int xp;

    public AlloyingRecipe(ResourceLocation id, String group, CraftingBookCategory category, String output, List<Ingredient> recipeItems, int amount, int xp) {
        this.id = id;
        this.output = output;
        this.group = group;
        this.inputs = recipeItems;
        this.amount = amount;
        this.category = category;
        this.xp = xp;
    }

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> defaultedList = NonNullList.of();
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

    public CraftingBookCategory getCategory() {
        return this.category;
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
    public ItemStack craft(MultipleStackRecipeInput input, RegistryAccess lookup) {
        return null;
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(RegistryAccess registryManager) {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    public String craftAlloy(MultipleStackRecipeInput input, RegistryAccess lookup) {
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
        public AlloyingRecipe read(ResourceLocation id, JsonObject json) {
            String group = GsonHelper.getString(json, "group", "");
            CraftingBookCategory category = CraftingBookCategory.MISC;
            if (GsonHelper.hasString(json, "category")) {
                try {
                    category = CraftingBookCategory.valueOf(GsonHelper.getString(json, "category").toUpperCase(Locale.ROOT));
                } catch (IllegalArgumentException ignored) {}
            }
            String output = GsonHelper.getString(json, "output");
            JsonArray ingredients = GsonHelper.getArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.ofSize(ingredients.size(), Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                Ingredient ingredient = Ingredient.fromJson(ingredients.get(i), true);
                if (ingredient.isEmpty()) continue;
                inputs.set(i, ingredient);
            }
            int amount = GsonHelper.getInt(json, "amount");
            int xp = GsonHelper.getInt(json, "xp");

            return new AlloyingRecipe(id, group, category, output, inputs, amount, xp);
        }

        @Override
        public AlloyingRecipe read(ResourceLocation id, FriendlyByteBuf buf) {
            String group = buf.readString();
            CraftingBookCategory category = buf.readEnumConstant(CraftingBookCategory.class);
            String output = buf.readString();
            int amount = buf.readInt();
            NonNullList<Ingredient> inputs = NonNullList.ofSize(buf.readVarInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }
            int xp = buf.readVarInt();

            return new AlloyingRecipe(id, group, category, output, inputs, amount, xp);
        }

        @Override
        public void write(FriendlyByteBuf buf, AlloyingRecipe recipe) {
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
