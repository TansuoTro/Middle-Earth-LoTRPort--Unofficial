package net.jukoz.me.datageneration.custom;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.jukoz.me.recipe.AlloyingRecipe;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.RequirementsStrategy;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.NonNullList;

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
    private final net.minecraft.core.NonNullList<Ingredient> inputs = net.minecraft.util.collection.NonNullList.of();
    private final Map<String, CriterionTriggerInstance> criteria = new LinkedHashMap<>();
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

    public AlloyRecipeJsonBuilder input(ItemLike itemProvider) {
        return this.input(itemProvider, 1);
    }

    public AlloyRecipeJsonBuilder input(ItemLike itemProvider, int size) {
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

    public AlloyRecipeJsonBuilder criterion(String name, CriterionTriggerInstance conditions) {
        this.criteria.put(name, conditions);
        return this;
    }

    public void offerTo(Consumer<FinishedRecipe> exporter) {
        this.offerTo(exporter, Registries.ITEM.getId(this.getOutputItem()).getPath());
    }

    public void offerTo(Consumer<FinishedRecipe> exporter, String path) {
        this.offerTo(exporter, new ResourceLocation(net.jukoz.me.MiddleEarth.MOD_ID, path));
    }

    public void offerTo(Consumer<FinishedRecipe> exporter, ResourceLocation recipeId) {
        this.validate(recipeId);
        Advancement.Builder advancement = Advancement.Builder.createUntelemetered()
                .criterion("has_the_recipe", RecipeUnlockedTrigger.create(recipeId))
                .rewards(AdvancementRewards.Builder.recipe(recipeId))
                .criteriaMerger(RequirementsStrategy.OR);
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

    private static CraftingBookCategory craftingCategory(RecipeCategory category) {
        return switch (category) {
            case BUILDING_BLOCKS -> CraftingBookCategory.BUILDING;
            case REDSTONE -> CraftingBookCategory.REDSTONE;
            case TOOLS, COMBAT -> CraftingBookCategory.EQUIPMENT;
            default -> CraftingBookCategory.MISC;
        };
    }

    private void validate(ResourceLocation recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        }
    }
}
