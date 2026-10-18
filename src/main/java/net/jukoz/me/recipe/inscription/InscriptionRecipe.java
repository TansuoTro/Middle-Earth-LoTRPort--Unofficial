package net.jukoz.me.recipe.inscription;
import net.jukoz.me.compat.NbtCompat;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.*;
import net.jukoz.me.compat.SingleStackRecipeInput;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.world.World;
import net.jukoz.me.block.registration.ModDecorativeBlocks;

import java.util.ArrayList;
import java.util.List;

public class InscriptionRecipe implements Recipe<SingleStackRecipeInput> {
    public final Identifier id;
    public final RegistryEntry<Enchantment> enchant;
    public final int level;
    public final List<String> inputWords;
    public final Ingredient inputChisel;
    public final int levelCost;

    public InscriptionRecipe(Identifier id, RegistryEntry<Enchantment> enchant, int level, List<String> inputWords, Ingredient inputChisel, int levelCost) {
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
    public boolean matches(SingleStackRecipeInput input, World world) {
        return this.inputChisel.test(input.getStack(0));
    }

    @Override
    public ItemStack craft(SingleStackRecipeInput input, DynamicRegistryManager registries) {
        ItemStack result = input.getStack(0).copy();
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
    public ItemStack getOutput(DynamicRegistryManager registryManager) {
        return ItemStack.EMPTY;
    }

    @Override
    public Identifier getId() {
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
        public InscriptionRecipe read(Identifier id, JsonObject json) {
            Identifier enchantmentId = new Identifier(JsonHelper.getString(json, "enchantment"));
            RegistryEntry<Enchantment> enchant = RegistryEntry.of(Registries.ENCHANTMENT.get(enchantmentId));
            int level = JsonHelper.getInt(json, "level");
            JsonArray words = JsonHelper.getArray(json, "words");
            List<String> inputWords = new ArrayList<>(words.size());
            for (int i = 0; i < words.size(); i++) {
                inputWords.add(words.get(i).getAsString());
            }
            Ingredient chisel = Ingredient.fromJson(JsonHelper.getElement(json, "chisel"), true);
            int levelCost = JsonHelper.getInt(json, "level_cost");

            return new InscriptionRecipe(id, enchant, level, inputWords, chisel, levelCost);
        }

        @Override
        public InscriptionRecipe read(Identifier id, PacketByteBuf buf) {
            Identifier enchantmentId = buf.readIdentifier();
            RegistryEntry<Enchantment> enchant = RegistryEntry.of(Registries.ENCHANTMENT.get(enchantmentId));
            int level = buf.readVarInt();
            int wordCount = buf.readVarInt();
            List<String> inputWords = new ArrayList<>(wordCount);
            for (int i = 0; i < wordCount; i++) {
                inputWords.add(buf.readString());
            }
            Ingredient chisel = Ingredient.fromPacket(buf);
            int levelCost = buf.readVarInt();

            return new InscriptionRecipe(id, enchant, level, inputWords, chisel, levelCost);
        }

        @Override
        public void write(PacketByteBuf buf, InscriptionRecipe recipe) {
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
