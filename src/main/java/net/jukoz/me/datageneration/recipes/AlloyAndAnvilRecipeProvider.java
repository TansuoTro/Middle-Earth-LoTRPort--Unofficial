package net.jukoz.me.datageneration.recipes;

import java.util.function.Consumer;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlocks;
import net.jukoz.me.datageneration.content.models.HotMetalsModel;
import net.jukoz.me.datageneration.custom.AlloyRecipeJsonBuilder;
import net.jukoz.me.datageneration.custom.AnvilShapingRecipeJsonBuilder;
import net.jukoz.me.item.DecorativeItemsME;
import net.jukoz.me.item.FoodItemsME;
import net.jukoz.me.item.ResourceItemsME;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;

/**
 * Ported from the 1.21.8 donor package
 * net.sevenstars.middleearth.datageneration.providers.recipes (RecipeGenerator API rewritten as a plain 1.20.1 FabricRecipeProvider).
 *
 * <p>Covers the donor RecipeProvider "Alloying" region: the alloy-forge recipes
 * (alloying + bulk melting, both via {@link AlloyRecipeJsonBuilder}) and the
 * shaping-anvil recipes (via {@link AnvilShapingRecipeJsonBuilder}), plus the
 * crafting-table recipes for the treated anvil blocks.
 */
public class AlloyAndAnvilRecipeProvider extends FabricRecipeProvider {

    private static final int INGOT_LIQUID_VALUE = 144;

    public AlloyAndAnvilRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public String getName() {
        return "AlloyAndAnvilRecipes";
    }

    private static TagKey<Item> modTag(String path) {
        return TagKey.of(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, path));
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {

        //region ALLOYING (alloy forge)
        AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, "bronze", INGOT_LIQUID_VALUE * 4, 4)
                .input(modTag("copper"))
                .input(modTag("copper"))
                .input(modTag("copper"))
                .input(modTag("tin"))
                .criterion(hasItem(Items.COPPER_INGOT),
                        conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter, "bronze_from_alloying");

        AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, "crude", INGOT_LIQUID_VALUE * 3, 3)
                .input(modTag("copper"))
                .input(modTag("copper"))
                .input(modTag("tin"))
                .input(ResourceItemsME.ASH)
                .criterion(hasItem(Items.COPPER_INGOT),
                        conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter, "crude_from_alloying");

        AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, "steel", INGOT_LIQUID_VALUE * 3, 3)
                .input(modTag("iron"))
                .input(modTag("iron"))
                .input(modTag("iron"))
                .input(Items.COAL)
                .criterion(hasItem(Items.IRON_INGOT),
                        conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, "steel_from_alloying_tags");

        AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, "khazad_steel", INGOT_LIQUID_VALUE * 3, 3)
                .input(modTag("iron"))
                .input(modTag("iron"))
                .input(modTag("lead"))
                .input(Items.COAL)
                .criterion(hasItem(Items.IRON_INGOT),
                        conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, "khazad_steel_from_alloying_tags");

        AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, "edhel_steel", INGOT_LIQUID_VALUE * 3, 3)
                .input(modTag("iron"))
                .input(modTag("iron"))
                .input(modTag("iron"))
                .input(ResourceItemsME.SILVER_NUGGET)
                .criterion(hasItem(Items.IRON_INGOT),
                        conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, "edhel_steel_from_alloying_tags");

        AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, "burzum_steel", INGOT_LIQUID_VALUE * 3, 3)
                .input(modTag("iron"))
                .input(modTag("iron"))
                .input(modTag("lead"))
                .input(ResourceItemsME.ASH)
                .criterion(hasItem(Items.IRON_INGOT),
                        conditionsFromItem(Items.IRON_INGOT))
                .offerTo(exporter, "burzum_steel_from_alloying_tags");

        AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, "chicken_nugget", INGOT_LIQUID_VALUE, 1)
                .input(Items.CHICKEN)
                .input(Items.WHEAT)
                .input(Items.EGG)
                .input(FoodItemsME.GARLIC)
                .criterion(hasItem(ResourceItemsME.PTEROSAUR_NUGGET),
                        conditionsFromItem(ResourceItemsME.PTEROSAUR_NUGGET))
                .offerTo(exporter, "chicken_nugget_from_alloying");
        //endregion

        //region MELTING (alloy forge bulk melt, one recipe per ingot count 1..4)
        createMeltBulkRecipeTag(exporter, modTag("copper"), "copper");
        createMeltBulkRecipeTag(exporter, modTag("tin"), "tin");

        createMeltBulkRecipe(exporter, ResourceItemsME.BRONZE_INGOT, "bronze");
        createMeltBulkRecipe(exporter, ResourceItemsME.CRUDE_INGOT, "crude");

        createMeltBulkRecipeTag(exporter, modTag("lead"), "lead");
        createMeltBulkRecipeTag(exporter, modTag("silver"), "silver");
        createMeltBulkRecipeTag(exporter, modTag("iron"), "iron");
        createMeltBulkRecipeTag(exporter, modTag("gold"), "gold");

        createMeltBulkRecipe(exporter, ResourceItemsME.STEEL_INGOT, "steel");
        createMeltBulkRecipe(exporter, ResourceItemsME.KHAZAD_STEEL_INGOT, "khazad_steel");
        createMeltBulkRecipe(exporter, ResourceItemsME.EDHEL_STEEL_INGOT, "edhel_steel");
        createMeltBulkRecipe(exporter, ResourceItemsME.BURZUM_STEEL_INGOT, "burzum_steel");

        createMeltBulkRecipeTag(exporter, modTag("mithril"), "mithril");

        createMeltBulkRecipe(exporter, Items.NETHERITE_INGOT, "netherite");
        //endregion

        //region ANVIL SHAPING (shaping anvil)
        HotMetalsModel.shapesTag.forEach(shape -> {
            createAnvilShapingRecipeTag(exporter, shape.tagKey(), shape.output(), shape.amount());
        });
        HotMetalsModel.shapesItem.forEach(shape -> {
            createAnvilShapingRecipeItem(exporter, shape.item(), shape.output(), shape.amount());
        });
        //endregion

        //region ANVIL BLOCKS (crafting-table recipes for the treated anvils)
        createAnvilRecipe(exporter, ModBlocks.STEEL_BLOCK.asItem(), ResourceItemsME.STEEL_INGOT, DecorativeItemsME.TREATED_ANVIL);
        createAnvilRecipe(exporter, ModBlocks.KHAZAD_STEEL_BLOCK.asItem(), ResourceItemsME.KHAZAD_STEEL_INGOT, DecorativeItemsME.DWARVEN_TREATED_ANVIL);
        createAnvilRecipe(exporter, ModBlocks.EDHEL_STEEL_BLOCK.asItem(), ResourceItemsME.EDHEL_STEEL_INGOT, DecorativeItemsME.ELVEN_TREATED_ANVIL);
        createAnvilRecipe(exporter, ModBlocks.BURZUM_STEEL_BLOCK.asItem(), ResourceItemsME.BURZUM_STEEL_INGOT, DecorativeItemsME.ORCISH_TREATED_ANVIL);
        //endregion
    }

    private void createMeltBulkRecipe(Consumer<RecipeJsonProvider> exporter, Item input, String output) {
        createMeltRecipe(exporter, input, output, 1, INGOT_LIQUID_VALUE);
        createMeltRecipe(exporter, input, output, 2, INGOT_LIQUID_VALUE);
        createMeltRecipe(exporter, input, output, 3, INGOT_LIQUID_VALUE);
        createMeltRecipe(exporter, input, output, 4, INGOT_LIQUID_VALUE);
    }

    private void createMeltRecipe(Consumer<RecipeJsonProvider> exporter, Item input, String output, int ingots, int amount) {
        switch (ingots) {
            case 1 -> AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, output, amount, 0)
                    .input(input)
                    .criterion(hasItem(input),
                            conditionsFromItem(input))
                    .offerTo(exporter, output + "_from_melting_1_" + Registries.ITEM.getId(input).getPath());
            case 2 -> AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, output, amount * 2, 0)
                    .input(input)
                    .input(input)
                    .criterion(hasItem(input),
                            conditionsFromItem(input))
                    .offerTo(exporter, output + "_from_melting_2_" + Registries.ITEM.getId(input).getPath());
            case 3 -> AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, output, amount * 3, 0)
                    .input(input)
                    .input(input)
                    .input(input)
                    .criterion(hasItem(input),
                            conditionsFromItem(input))
                    .offerTo(exporter, output + "_from_melting_3_" + Registries.ITEM.getId(input).getPath());
            case 4 -> AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, output, amount * 4, 0)
                    .input(input)
                    .input(input)
                    .input(input)
                    .input(input)
                    .criterion(hasItem(input),
                            conditionsFromItem(input))
                    .offerTo(exporter, output + "_from_melting_4_" + Registries.ITEM.getId(input).getPath());
        }
    }

    private void createMeltBulkRecipeTag(Consumer<RecipeJsonProvider> exporter, TagKey<Item> input, String output) {
        createMeltRecipeTag(exporter, input, output, 1, INGOT_LIQUID_VALUE);
        createMeltRecipeTag(exporter, input, output, 2, INGOT_LIQUID_VALUE);
        createMeltRecipeTag(exporter, input, output, 3, INGOT_LIQUID_VALUE);
        createMeltRecipeTag(exporter, input, output, 4, INGOT_LIQUID_VALUE);
    }

    private void createMeltRecipeTag(Consumer<RecipeJsonProvider> exporter, TagKey<Item> input, String output, int ingots, int amount) {
        switch (ingots) {
            case 1 -> AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, output, amount, 0)
                    .input(input)
                    .criterion(hasItem(DecorativeItemsME.FORGE),
                            conditionsFromItem(DecorativeItemsME.FORGE))
                    .offerTo(exporter, output + "_from_melting_1_" + input.id().getPath());
            case 2 -> AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, output, amount * 2, 0)
                    .input(input)
                    .input(input)
                    .criterion(hasItem(DecorativeItemsME.FORGE),
                            conditionsFromItem(DecorativeItemsME.FORGE))
                    .offerTo(exporter, output + "_from_melting_2_" + input.id().getPath());
            case 3 -> AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, output, amount * 3, 0)
                    .input(input)
                    .input(input)
                    .input(input)
                    .criterion(hasItem(DecorativeItemsME.FORGE),
                            conditionsFromItem(DecorativeItemsME.FORGE))
                    .offerTo(exporter, output + "_from_melting_3_" + input.id().getPath());
            case 4 -> AlloyRecipeJsonBuilder.createAlloyRecipe(RecipeCategory.MISC, output, amount * 4, 0)
                    .input(input)
                    .input(input)
                    .input(input)
                    .input(input)
                    .criterion(hasItem(DecorativeItemsME.FORGE),
                            conditionsFromItem(DecorativeItemsME.FORGE))
                    .offerTo(exporter, output + "_from_melting_4_" + input.id().getPath());
        }
    }

    private void createAnvilShapingRecipeTag(Consumer<RecipeJsonProvider> exporter, TagKey<Item> input, Item output, int amount) {
        AnvilShapingRecipeJsonBuilder.createAnvilShapingRecipe(RecipeCategory.MISC, output, amount)
                .input(input)
                .criterion(hasItem(Items.COPPER_INGOT),
                        conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter);
    }

    private void createAnvilShapingRecipeItem(Consumer<RecipeJsonProvider> exporter, Item input, Item output, int amount) {
        AnvilShapingRecipeJsonBuilder.createAnvilShapingRecipe(RecipeCategory.MISC, output, amount)
                .input(input)
                .criterion(hasItem(Items.COPPER_INGOT),
                        conditionsFromItem(Items.COPPER_INGOT))
                .offerTo(exporter);
    }

    private void createAnvilRecipe(Consumer<RecipeJsonProvider> exporter, Item inputBlock, Item inputIngot, Item output) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, output, 1)
                .pattern("IBI")
                .pattern(" I ")
                .pattern("LLL")
                .input('I', inputIngot)
                .input('B', inputBlock)
                .input('L', TagKey.of(Registries.ITEM, new ResourceLocation("logs")))
                .criterion(hasItem(inputIngot),
                        conditionsFromItem(inputIngot))
                .offerTo(exporter);
    }
}
