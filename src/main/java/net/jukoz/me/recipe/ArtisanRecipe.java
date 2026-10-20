package net.jukoz.me.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class ArtisanRecipe implements Recipe<Inventory> {
    public final Identifier id;
    public final ItemStack output;
    public final DefaultedList<Ingredient> inputs;
    /** Tab id this recipe is listed under (e.g. "sword"); null matches every tab. */
    public final String category;
    /** Faction disposition gate; null is treated as "neutral" (craftable by everyone). */
    public final String disposition;
    public final int xp;

    public ArtisanRecipe(Identifier id, ItemStack output, DefaultedList<Ingredient> recipeItems) {
        this(id, output, recipeItems, null, null, 0);
    }

    public ArtisanRecipe(Identifier id, ItemStack output, DefaultedList<Ingredient> recipeItems, String category, String disposition, int xp) {
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
    public boolean matches(Inventory inventory, World world) {
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
    public ItemStack craft(Inventory inventory, DynamicRegistryManager registryManager) {
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

    public DefaultedList<Ingredient> getIngredients() {
        DefaultedList<Ingredient> defaultedList = DefaultedList.of();
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
    public Identifier getId() {
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
        public ArtisanRecipe read(Identifier id, JsonObject json) {
            ItemStack output = ShapedRecipe.outputFromJson(JsonHelper.getObject(json, "output"));
            JsonArray ingredients = JsonHelper.getArray(json, "ingredients");
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(ingredients.size(), Ingredient.EMPTY);
            for (int i = 0; i < ingredients.size(); i++) {
                Ingredient ingredient = Ingredient.fromJson(ingredients.get(i), true);
                if (ingredient.isEmpty()) continue;
                inputs.set(i, ingredient);
            }

            String category = JsonHelper.getString(json, "category", null);
            String disposition = JsonHelper.getString(json, "disposition", null);
            int xp = JsonHelper.getInt(json, "xp", 0);
            return new ArtisanRecipe(id, output, inputs, category, disposition, xp);
        }

        @Override
        public ArtisanRecipe read(Identifier id, PacketByteBuf buf) {
            String category = readNullableString(buf);
            ItemStack output = buf.readItemStack();
            DefaultedList<Ingredient> inputs = DefaultedList.ofSize(buf.readInt(), Ingredient.EMPTY);
            for (int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromPacket(buf));
            }
            String disposition = readNullableString(buf);
            int xp = buf.readVarInt();
            return new ArtisanRecipe(id, output, inputs, category, disposition, xp);
        }

        @Override
        public void write(PacketByteBuf buf, ArtisanRecipe recipe) {
            writeNullableString(buf, recipe.category);
            buf.writeItemStack(recipe.output);
            buf.writeInt(recipe.getIngredients().size());
            for (Ingredient ingredient : recipe.getIngredients()) {
                ingredient.write(buf);
            }
            writeNullableString(buf, recipe.disposition);
            buf.writeVarInt(recipe.xp);
        }

        private static String readNullableString(PacketByteBuf buf) {
            String value = buf.readString();
            return value.isEmpty() ? null : value;
        }

        private static void writeNullableString(PacketByteBuf buf, String value) {
            buf.writeString(value == null ? "" : value);
        }
    }
}
