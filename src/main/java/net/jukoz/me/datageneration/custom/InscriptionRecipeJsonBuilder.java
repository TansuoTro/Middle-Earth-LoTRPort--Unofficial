package net.jukoz.me.datageneration.custom;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.recipe.inscription.InscriptionRecipe;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

/**
 * Datagen builder for {@link InscriptionRecipe}.
 *
 * <p>Emits exactly the JSON the runtime serializer parses: {@code enchantment},
 * {@code level}, {@code words}, {@code chisel} and {@code level_cost}.
 */
public class InscriptionRecipeJsonBuilder {
    private final RecipeCategory category;
    public RegistryEntry<Enchantment> enchant;
    public int level;
    private final List<String> inputWords = new ArrayList<>();
    private net.minecraft.recipe.Ingredient chiselInput;
    private int levelCost;
    private final Map<String, CriterionConditions> criteria = new LinkedHashMap<>();

    private InscriptionRecipeJsonBuilder(RecipeCategory category, RegistryEntry<Enchantment> enchant, int level, int levelCost) {
        this.category = category;
        this.enchant = enchant;
        this.level = level;
        this.levelCost = levelCost;
    }

    public static InscriptionRecipeJsonBuilder createInscriptionRecipe(RecipeCategory category, RegistryEntry<Enchantment> enchant, int level, int levelCost) {
        return new InscriptionRecipeJsonBuilder(category, enchant, level, levelCost);
    }

    public Item getOutputItem() {
        return net.jukoz.me.item.ToolItemsME.MITHRIL_CHISEL;
    }

    public InscriptionRecipeJsonBuilder chisel(TagKey<Item> tag) {
        this.chiselInput = net.minecraft.recipe.Ingredient.fromTag(tag);
        return this;
    }

    public InscriptionRecipeJsonBuilder chisel(net.minecraft.recipe.Ingredient chisel) {
        this.chiselInput = chisel;
        return this;
    }

    public InscriptionRecipeJsonBuilder addWord(String word) {
        this.inputWords.add(word);
        return this;
    }

    public InscriptionRecipeJsonBuilder criterion(String name, CriterionConditions conditions) {
        this.criteria.put(name, conditions);
        return this;
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter) {
        this.offerTo(exporter, Registries.ITEM.getId(this.getOutputItem()).getPath());
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter, String path) {
        this.offerTo(exporter, new Identifier(MiddleEarth.MOD_ID, path));
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId) {
        this.validate(recipeId);
        Advancement.Builder advancement = Advancement.Builder.createUntelemetered()
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .criteriaMerger(CriterionMerger.OR);
        this.criteria.forEach(advancement::criterion);
        exporter.accept(new CustomRecipeJsonProvider(recipeId, InscriptionRecipe.Serializer.INSTANCE,
                this::writeRecipeJson, advancement, recipeId.withPrefixedPath("recipes/")));
    }

    private void writeRecipeJson(JsonObject json) {
        json.addProperty("enchantment", Registries.ENCHANTMENT.getId(this.enchant.value()).toString());
        json.addProperty("level", this.level);
        JsonArray words = new JsonArray();
        for (String word : this.inputWords) {
            words.add(word);
        }
        json.add("words", words);
        json.add("chisel", this.chiselInput.toJson());
        json.addProperty("level_cost", this.levelCost);
    }

    private void validate(Identifier recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        }
    }
}
