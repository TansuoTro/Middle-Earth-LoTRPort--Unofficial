package net.jukoz.me.datageneration.custom;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.jukoz.me.recipe.ArtisanRecipe;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementRewards;
import net.minecraft.advancement.CriterionMerger;
import net.minecraft.advancement.criterion.CriterionConditions;
import net.minecraft.advancement.criterion.RecipeUnlockedCriterion;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemConvertible;
import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;

/**
 * Datagen builder for {@link ArtisanRecipe}.
 *
 * <p>Emits the JSON the runtime serializer parses: {@code output} (item stack,
 * NBT kept for trimmed outputs), {@code ingredients} and the donor's
 * {@code category}/{@code disposition}/{@code xp} fields. The serializer treats those
 * three as optional, so previously generated JSON stays loadable.
 */
public class ArtisanTableRecipeJsonBuilder {
    private final RecipeCategory category;
    private final String tab;
    private final List<JsonElement> inputs = new ArrayList<>();
    private final ItemStack output;
    private final DispositionType dispositionType;
    private final int xp;
    private final Map<String, CriterionConditions> criteria = new LinkedHashMap<>();

    private ArtisanTableRecipeJsonBuilder(RecipeCategory category, ItemStack output,
                                          String tab, DispositionType dispositionType, int xp) {
        this.category = category;
        this.output = output;
        this.tab = tab;
        this.dispositionType = dispositionType;
        this.xp = xp;
    }

    public static ArtisanTableRecipeJsonBuilder createArtisanRecipe(RecipeCategory category, ItemStack output,
                                                                    String tab, DispositionType dispositionType, int xp) {
        return new ArtisanTableRecipeJsonBuilder(category, output, tab, dispositionType, xp);
    }

    public static ArtisanTableRecipeJsonBuilder createArtisanRecipe(RecipeCategory category, ItemStack output,
                                                                    String tab, DispositionType dispositionType) {
        return new ArtisanTableRecipeJsonBuilder(category, output, tab, dispositionType, 0);
    }

    public static ArtisanTableRecipeJsonBuilder createArtisanRecipe(RecipeCategory category, ItemStack output, String tab) {
        return new ArtisanTableRecipeJsonBuilder(category, output, tab, null, 0);
    }

    public Item getOutputItem() {
        return this.output.getItem();
    }

    public ArtisanTableRecipeJsonBuilder input(TagKey<Item> tag) {
        return this.input(Ingredient.fromTag(tag));
    }

    public ArtisanTableRecipeJsonBuilder input(ItemConvertible itemProvider) {
        return this.input(itemProvider, 1);
    }

    public ArtisanTableRecipeJsonBuilder input(ItemConvertible itemProvider, int size) {
        for (int i = 0; i < size; ++i) {
            this.input(Ingredient.ofItems(itemProvider));
        }
        return this;
    }

    public ArtisanTableRecipeJsonBuilder input(Ingredient ingredient) {
        return this.input(ingredient, 1);
    }

    public ArtisanTableRecipeJsonBuilder input(Ingredient ingredient, int size) {
        for (int i = 0; i < size; ++i) {
            this.inputs.add(ingredient.toJson());
        }
        return this;
    }

    public ArtisanTableRecipeJsonBuilder input(JsonElement ingredientJson) {
        this.inputs.add(ingredientJson);
        return this;
    }

    /**
     * 1.20.1 replacement for the donor's {@code componentInput(ComponentsIngredient)}:
     * trims live in the stack NBT, so the component match becomes a
     * {@code fabric:nbt} custom ingredient over the stack's own NBT.
     */
    public ArtisanTableRecipeJsonBuilder componentInput(ItemStack trimmedStack) {
        JsonObject json = new JsonObject();
        json.addProperty("fabric:type", "fabric:nbt");
        JsonObject base = new JsonObject();
        base.addProperty("item", Registries.ITEM.getId(trimmedStack.getItem()).toString());
        json.add("base", base);
        if (trimmedStack.getNbt() != null) {
            json.addProperty("nbt", trimmedStack.getNbt().toString());
        }
        return this.input(json);
    }

    /**
     * Trim sub-NBT ({@code {material, pattern}}) as written by {@code MEArmorTrim.set}.
     * Written directly because the trim material/pattern dynamic registries are not
     * guaranteed to be bootstrapped during datagen.
     */
    public static CompoundTag trimNbt(ResourceLocation materialId) {
        CompoundTag trim = new CompoundTag();
        trim.putString("material", materialId.toString());
        trim.putString("pattern", net.jukoz.me.item.utils.SmithingTrimPatternsME.SMITHING_PART.getValue().toString());
        return trim;
    }

    public ArtisanTableRecipeJsonBuilder criterion(String name, CriterionConditions conditions) {
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
        exporter.accept(new CustomRecipeJsonProvider(Objects.requireNonNull(recipeId), ArtisanRecipe.Serializer.INSTANCE,
                this::writeRecipeJson, advancement, recipeId.withPrefixedPath("recipes/")));
    }

    private void writeRecipeJson(JsonObject json) {
        json.addProperty("category", this.tab);
        json.addProperty("disposition", this.dispositionType == null
                ? DispositionType.NEUTRAL.toString().toLowerCase()
                : this.dispositionType.toString().toLowerCase());
        json.addProperty("xp", this.xp);
        json.add("output", itemStackJson(this.output));
        JsonArray ingredients = new JsonArray();
        for (JsonElement input : this.inputs) {
            ingredients.add(input);
        }
        json.add("ingredients", ingredients);
    }

    static JsonObject itemStackJson(ItemStack stack) {
        JsonObject json = new JsonObject();
        json.addProperty("item", Registries.ITEM.getId(stack.getItem()).toString());
        if (stack.getCount() != 1) {
            json.addProperty("count", stack.getCount());
        }
        if (stack.getNbt() != null) {
            json.addProperty("nbt", stack.getNbt().toString());
        }
        return json;
    }

    private void validate(ResourceLocation recipeId) {
        if (this.criteria.isEmpty()) {
            throw new IllegalStateException("No way of obtaining recipe " + recipeId);
        }
    }
}
