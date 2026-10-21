package net.jukoz.me.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.world.level.Level;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.ShapedRecipe;

public class ArtisanRecipe implements Recipe<Inventory> {
    public final ResourceLocation id;
    public final ItemStack output;
    public final NonNullList<Ingredient> inputs;
    /** Tab id this recipe is listed under (e.g. "sword"); null matches every tab. */
    public final String category;
    /** Faction disposition gate; null is treated as "neutral" (craftable by everyone). */
    public final String disposition;
    public final int xp;

    public ArtisanRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems) {
        this(id, output, recipeItems, null, null, 0);
    }

    public ArtisanRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> recipeItems, String category, String disposition, int xp) {
        this.id = id;
        this.output = output;
        this.inputs = recipeItems;
        this.category = category;
        this.disposition = disposition;
        this.xp = xp;
    }

    public ItemStack createIcon() {
        return new ItemStack(ModDecorativeBlocks.ARTISAN_TABLE);
    }

    @Override
    public boolean matches(Inventory inventory, Level world) {
        int i = 0;
        for (int j = 0; j < inventory.size(); ++j) {
            ItemStack itemStack = inventory.getStack(j);
            if (itemStack.isEmpty()) continue;
            ++i;
        }
        if(i != this.inputs.size()) return false;

        for (int j = 0; j < inputs.size(); j++) {
            if(!inputs.get(j).test(inventory.getStack(j))) return false;
        }
        return true;
    }

    @Override
    public ItemStack craft(Inventory inventory, RegistryAccess registryManager) {
        return this.output.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getOutput(RegistryAccess registryManager) {
        return output;
    }

    public NonNullList<Ingredient> getIngredients() {
        NonNullList<Ingredient> defaultedList = NonNullList.of();
        defaultedList.addAll(this.inputs);
        return defaultedList;
    }

    public String getCategory() {
        return category;
    }

    public String getDisposition() {
        return disposition;
    }

    public int getXp() {
        return xp;
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<ArtisanRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "artisan_table";
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<ArtisanRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "artisan_table";

        @Override
        public ArtisanRecipe read(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(GsonHelper.getObject(json, "output"));
            JsonArray ingredients = GsonHelper.getArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.ofSize(ingredients.size(), Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                Ingredient ingredient = Ingredient.fromJson(ingredients.get(i), true);
                if (ingredient.isEmpty()) continue;
                inputs.set(i, ingredient);
            }

            String category = GsonHelper.getString(json, "category", null);
            String disposition = GsonHelper.getString(json, "disposition", null);
            int xp = GsonHelper.getInt(json, "xp", 0);
            return new ArtisanRecipe(id, output, inputs, category, disposition, xp);
        }

        @Override
        public ArtisanRecipe read(ResourceLocation id, FriendlyByteBuf buf) {
            String category = readNullableString(buf);
            ItemStack output = buf.readItemStack();
            NonNullList<Ingredient> inputs = NonNullList.ofSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }
            String disposition = readNullableString(buf);
            int xp = buf.readVarInt();
            return new ArtisanRecipe(id, output, inputs, category, disposition, xp);
        }

        @Override
        public void write(FriendlyByteBuf buf, ArtisanRecipe recipe) {
            writeNullableString(buf, recipe.category);
            buf.writeItemStack(recipe.output);
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(buf);
            }
            writeNullableString(buf, recipe.disposition);
            buf.writeVarInt(recipe.xp);
        }

        private static String readNullableString(FriendlyByteBuf buf) {
            String value = buf.readString();
            return value.isEmpty() ? null : value;
        }

        private static void writeNullableString(FriendlyByteBuf buf, String value) {
            buf.writeString(value == null ? "" : value);
        }
    }
}
