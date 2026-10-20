package net.jukoz.me.datageneration.custom;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.jukoz.me.recipe.AlloyingRecipe;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

/**
 * Datagen builder for {@link AlloyingRecipe}.
 *
 * <p>Emits exactly the JSON the runtime serializer parses: {@code group} (optional),
 * {@code category} (optional), {@code output} (alloy id string), {@code ingredients},
 * {@code amount} and {@code xp}.
 */
public class AlloyRecipeJsonBuilder {
    private final RecipeCategory category;
    private final String metalOutput;
    private final int metalAmount;
    private final int xp;
    private final net.minecraft.util.collection.DefaultedList<Ingredient> inputs = net.minecraft.util.collection.DefaultedList.of();
    private final Map<String, CriterionConditions> criteria = new LinkedHashMap<>();
    private String group;

    private AlloyRecipeJsonBuilder(RecipeCategory category, String metalOutput, int metalAmount, int xp) {
        this.category = category;
        this.metalOutput = metalOutput;
        this.metalAmount = metalAmount;
        this.xp = xp;
    }

    public static AlloyRecipeJsonBuilder createAlloyRecipe(RecipeCategory category, String output, int amount, int xp) {
        return new AlloyRecipeJsonBuilder(category, output, amount, xp);
    }

    public Item getOutputItem() {
        return net.jukoz.me.item.ResourceItemsME.ROD;
    }

    public String getOutputMetal() {
        return this.metalOutput;
    }

    public int getMetalAmount() {
        return this.metalAmount;
    }

    public AlloyRecipeJsonBuilder group(String group) {
        this.group = group;
        return this;
    }

    public AlloyRecipeJsonBuilder input(TagKey<Item> tag) {
        return this.input(Ingredient.fromTag(tag));
    }

    public AlloyRecipeJsonBuilder input(ItemConvertible itemProvider) {
        return this.input(itemProvider, 1);
    }

    public AlloyRecipeJsonBuilder input(ItemConvertible itemProvider, int size) {
        for (int i = 0; i < size; ++i) {
            this.input(Ingredient.ofItems(itemProvider));
        }
        return this;
    }

    public AlloyRecipeJsonBuilder input(Ingredient ingredient) {
        return this.input(ingredient, 1);
    }

    public AlloyRecipeJsonBuilder input(Ingredient ingredient, int size) {
        for (int i = 0; i < size; ++i) {
            this.inputs.add(ingredient);
        }
        return this;
    }

    public AlloyRecipeJsonBuilder criterion(String name, CriterionConditions conditions) {
        this.criteria.put(name, conditions);
        return this;
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter) {
        this.offerTo(exporter, Registries.ITEM.getId(this.getOutputItem()).getPath());
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter, String path) {
        this.offerTo(exporter, new Identifier(net.jukoz.me.MiddleEarth.MOD_ID, path));
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter, Identifier recipeId) {
        this.validate(recipeId);
        Advancement.Builder advancement = Advancement.Builder.createUntelemetered()
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .criteriaMerger(CriterionMerger.OR);
        this.criteria.forEach(advancement::criterion);
        exporter.accept(new CustomRecipeJsonProvider(recipeId, AlloyingRecipe.Serializer.INSTANCE,
                this::writeRecipeJson, advancement, recipeId.withPrefixedPath("recipes/")));
    }

    private void writeRecipeJson(JsonObject json) {
        if (this.group != null && !this.group.isEmpty()) {
            json.addProperty("group", this.group);
        }
        json.addProperty("category", craftingCategory(this.category).asString());
        json.addProperty("output", this.metalOutput);
        JsonArray ingredients = new JsonArray();
        for (Ingredient input : this.inputs) {
            ingredients.add(input.toJson());
        }
        json.add("ingredients", ingredients);
        json.addProperty("amount", this.metalAmount);
        json.addProperty("xp", this.xp);
    }

    private static CraftingRecipeCategory craftingCategory(RecipeCategory category) {
        return switch (category) {
            case BUILDING_BLOCKS -> CraftingRecipeCategory.BUILDING;
            case REDSTONE -> CraftingRecipeCategory.REDSTONE;
            case TOOLS, COMBAT -> CraftingRecipeCategory.EQUIPMENT;
            default -> CraftingRecipeCategory.MISC;
        };
    }

    private void validate(Identifier recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        }
    }
}
