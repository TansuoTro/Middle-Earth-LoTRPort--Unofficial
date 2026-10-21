package net.jukoz.me.datageneration.custom;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.google.gson.JsonObject;
import net.jukoz.me.recipe.AnvilShapingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemConvertible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;

/**
 * Datagen builder for {@link AnvilShapingRecipe}.
 *
 * <p>Emits exactly the JSON the runtime serializer parses: {@code ingredient},
 * {@code output} (item stack) and {@code amount}.
 */
public class AnvilShapingRecipeJsonBuilder {
    private final RecipeCategory category;
    private Ingredient input;
    private final Item output;
    private final int amount;
    private final Map<String, CriterionConditions> criteria = new LinkedHashMap<>();

    private AnvilShapingRecipeJsonBuilder(RecipeCategory category, Item output, int amount) {
        this.category = category;
        this.output = output;
        this.amount = amount;
    }

    public static AnvilShapingRecipeJsonBuilder createAnvilShapingRecipe(RecipeCategory category, Item output, int amount) {
        return new AnvilShapingRecipeJsonBuilder(category, output, amount);
    }

    public Item getOutputItem() {
        return this.output;
    }

    public AnvilShapingRecipeJsonBuilder group(String group) {
        return this;
    }

    public AnvilShapingRecipeJsonBuilder input(TagKey<Item> tag) {
        return this.input(Ingredient.fromTag(tag));
    }

    public AnvilShapingRecipeJsonBuilder input(ItemConvertible itemProvider) {
        return this.input(Ingredient.ofItems(itemProvider));
    }

    public AnvilShapingRecipeJsonBuilder input(Ingredient ingredient) {
        this.input = ingredient;
        return this;
    }

    public AnvilShapingRecipeJsonBuilder criterion(String name, CriterionConditions conditions) {
        this.criteria.put(name, conditions);
        return this;
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter) {
        this.offerTo(exporter, Registries.ITEM.getId(this.getOutputItem()).getPath());
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter, String path) {
        this.offerTo(exporter, new ResourceLocation(net.jukoz.me.MiddleEarth.MOD_ID, path));
    }

    public void offerTo(Consumer<RecipeJsonProvider> exporter, ResourceLocation recipeId) {
        this.validate(recipeId);
        Advancement.Builder advancement = Advancement.Builder.createUntelemetered()
                .criterion("has_the_recipe", RecipeUnlockedCriterion.create(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .criteriaMerger(CriterionMerger.OR);
        this.criteria.forEach(advancement::criterion);
        exporter.accept(new CustomRecipeJsonProvider(recipeId, AnvilShapingRecipe.Serializer.INSTANCE,
                this::writeRecipeJson, advancement, recipeId.withPrefixedPath("recipes/")));
    }

    private void writeRecipeJson(JsonObject json) {
        json.add("ingredient", this.input.toJson());
        json.add("output", ArtisanTableRecipeJsonBuilder.itemStackJson(new ItemStack(this.output)));
        json.addProperty("amount", this.amount);
    }

    private void validate(ResourceLocation recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        }
    }
}
