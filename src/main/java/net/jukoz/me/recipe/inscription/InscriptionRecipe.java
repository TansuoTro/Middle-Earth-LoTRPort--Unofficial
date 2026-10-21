package net.jukoz.me.recipe.inscription;

import net.jukoz.me.compat.NbtCompat;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.FriendlyByteBuf;
import net.jukoz.me.compat.SingleStackRecipeInput;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.Level;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

public class InscriptionRecipe implements Recipe<SingleStackRecipeInput> {
    public final ResourceLocation id;
    public final Holder<Enchantment> enchant;
    public final int level;
    public final List<String> inputWords;
    public final Ingredient inputChisel;
    public final int levelCost;

    public InscriptionRecipe(ResourceLocation id, Holder<Enchantment> enchant, int level, List<String> inputWords, Ingredient inputChisel, int levelCost) {
        this.id = id;
        this.enchant = enchant;
        this.level = level;
        this.inputWords = inputWords;
        this.inputChisel = inputChisel;
        this.levelCost = levelCost;
    }

    public ItemStack createIcon() {
        return new ItemStack(ModDecorativeBlocks.INSCRIPTION_TABLE);
    }

    @Override
    public boolean matches(SingleStackRecipeInput input, Level world) {
        return this.inputChisel.test(input.getItem(0));
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput input, RegistryAccess registries) {
        ItemStack result = input.getItem(0).copy();
        int currentLevel = EnchantmentHelper.getLevel(this.enchant.value(), result);
        if(currentLevel == this.level - 1) {
            result.addEnchantment(this.enchant.value(), this.level);
        }
        return result;
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

    @Override
    public RecipeSerializer<? extends Recipe<SingleStackRecipeInput>> getSerializer() {
        return Serializer.INSTANCE;
    }

    @Override
    public RecipeType<? extends Recipe<SingleStackRecipeInput>> getType() {
        return Type.INSTANCE;
    }

    public static class Type implements RecipeType<InscriptionRecipe> {
        private Type() {}
        public static final Type INSTANCE = new Type();
        public static final String ID = "inscription_table";
    }

    @Override
    public boolean isIgnoredInRecipeBook() {
        return true;
    }

    public static class Serializer implements RecipeSerializer<InscriptionRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final String ID = "inscription_table";

        @Override
        public InscriptionRecipe read(ResourceLocation id, JsonObject json) {
            ResourceLocation enchantmentId = new ResourceLocation(GsonHelper.getString(json, "enchantment"));
            Holder<Enchantment> enchant = Holder.of(Registries.ENCHANTMENT.get(enchantmentId));
            int level = GsonHelper.getInt(json, "level");
            JsonArray words = GsonHelper.getAsJsonArray(json, "words");
            List<String> inputWords = new ArrayList<>(words.size());
            for (int i = 0; i < words.size(); i++) {
                inputWords.add(words.get(i).getAsString());
            }
            Ingredient chisel = Ingredient.fromJson(GsonHelper.getElement(json, "chisel"), true);
            int levelCost = GsonHelper.getInt(json, "level_cost");

            return new InscriptionRecipe(id, enchant, level, inputWords, chisel, levelCost);
        }

        @Override
        public InscriptionRecipe read(ResourceLocation id, FriendlyByteBuf buf) {
            ResourceLocation enchantmentId = buf.readResourceLocation();
            Holder<Enchantment> enchant = Holder.of(Registries.ENCHANTMENT.get(enchantmentId));
            int level = buf.readVarInt();
            int wordCount = buf.readVarInt();
            List<String> inputWords = new ArrayList<>(wordCount);
            for (int i = 0; i < wordCount; i++) {
                inputWords.add(buf.readUtf());
            }
            Ingredient chisel = Ingredient.fromPacket(buf);
            int levelCost = buf.readVarInt();

            return new InscriptionRecipe(id, enchant, level, inputWords, chisel, levelCost);
        }

        @Override
        public void write(FriendlyByteBuf buf, InscriptionRecipe recipe) {
            buf.writeIdentifier(Registries.ENCHANTMENT.getId(recipe.enchant.value()));
            buf.writeVarInt(recipe.level);
            buf.writeVarInt(recipe.inputWords.size());
            for (String word : recipe.inputWords) {
                buf.writeString(word);
            }
            recipe.inputChisel.write(buf);
            buf.writeVarInt(recipe.levelCost);
        }
    }
}
