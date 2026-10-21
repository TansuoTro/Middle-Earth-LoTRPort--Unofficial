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
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.Item;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

/**
 * Datagen builder for {@link InscriptionRecipe}.
 *
 * <p>Emits exactly the JSON the runtime serializer parses: {@code enchantment},
 * {@code level}, {@code words}, {@code chisel} and {@code level_cost}.
 */
public class InscriptionRecipeJsonBuilder {
    private final RecipeCategory category;
    public Holder<Enchantment> enchant;
    public int level;
    private final List<String> inputWords = new ArrayList<>();
    private net.minecraft.world.item.crafting.Ingredient chiselInput;
    private int levelCost;
    private final Map<String, CriterionTriggerInstance> criteria = new LinkedHashMap<>();

    private InscriptionRecipeJsonBuilder(RecipeCategory category, Holder<Enchantment> enchant, int level, int levelCost) {
        this.category = category;
        this.enchant = enchant;
        this.level = level;
        this.levelCost = levelCost;
    }

    public static InscriptionRecipeJsonBuilder createInscriptionRecipe(RecipeCategory category, Holder<Enchantment> enchant, int level, int levelCost) {
        return new InscriptionRecipeJsonBuilder(category, enchant, level, levelCost);
    }

    public Item getOutputItem() {
        return net.jukoz.me.item.ToolItemsME.MITHRIL_CHISEL;
    }

    public InscriptionRecipeJsonBuilder chisel(TagKey<Item> tag) {
        this.chiselInput = net.minecraft.world.item.crafting.Ingredient.fromTag(tag);
        return this;
    }

    public InscriptionRecipeJsonBuilder chisel(net.minecraft.world.item.crafting.Ingredient chisel) {
        this.chiselInput = chisel;
        return this;
    }

    public InscriptionRecipeJsonBuilder addWord(String word) {
        this.inputWords.add(word);
        return this;
    }

    public InscriptionRecipeJsonBuilder criterion(String name, CriterionTriggerInstance conditions) {
        this.criteria.put(name, conditions);
        return this;
    }

    public void offerTo(Consumer<FinishedRecipe> exporter) {
        this.offerTo(exporter, Registries.ITEM.getId(this.getOutputItem()).getPath());
    }

    public void offerTo(Consumer<FinishedRecipe> exporter, String path) {
        this.offerTo(exporter, new ResourceLocation(MiddleEarth.MOD_ID, path));
    }

    public void offerTo(Consumer<FinishedRecipe> exporter, ResourceLocation recipeId) {
        this.validate(recipeId);
        Advancement.Builder advancement = Advancement.Builder.createUntelemetered()
                .criterion("has_the_recipe", RecipeUnlockedTrigger.create(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .criteriaMerger(RequirementsStrategy.OR);
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

    private void validate(ResourceLocation recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        }
    }
}
