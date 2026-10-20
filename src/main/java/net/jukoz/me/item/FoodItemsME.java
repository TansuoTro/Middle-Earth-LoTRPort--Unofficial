package net.jukoz.me.item;

import net.minecraft.block.Block;

import net.minecraft.item.FoodComponents;
import net.minecraft.item.BlockItem;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlocks;
import net.jukoz.me.block.registration.ModNatureBlocks;
import net.jukoz.me.datageneration.content.TranslationEntries;
import net.jukoz.me.item.items.BottledDrinkItem;
import net.jukoz.me.item.items.OrcishFoodItem;
import net.jukoz.me.item.utils.ItemGroupsME;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.jukoz.me.registries.RegistryAliasesME;

import java.util.function.Function;

/**
 * Middle-earth mod Food Items registry
 */
public class FoodItemsME {

    public static final Item LEMBAS = registerItem("lembas",
            Item::new,new MESettings()
                    .food(new FoodComponent.Builder().hunger(20).saturationModifier(1).build()));
    public static final Item CRAM = registerItem("cram",
            Item::new,new MESettings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.2F).build()));

    public static final Item RAW_HORSE = registerItem("raw_horse",
            Item::new,new MESettings()
                    .food(new FoodComponent.Builder().hunger(3).saturationModifier(0.4F).build()));
    public static final Item COOKED_HORSE = registerItem("cooked_horse",
            Item::new,new MESettings()
                    .food(new FoodComponent.Builder().hunger(8).saturationModifier(0.8F).build()));

    public static final Item MAGGOTY_BREAD = registerItem("maggoty_bread",
            OrcishFoodItem::new,new MESettings()
                    .food(new FoodComponent.Builder().hunger(5).saturationModifier(1.2f).build()));
    public static final Item TOUGH_BERRIES = registerItem("tough_berries",
            (settings) -> new BlockItem(ModNatureBlocks.TOUGH_BERRY_BUSH, settings), new MESettings());
    public static final Item STRAWBERRIES = registerItem("strawberries",
            (settings) -> new BlockItem(ModNatureBlocks.STRAWBERRY_BUSH, settings), new MESettings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1f).build()));

    // Donor: drink consumable (2s), useRemainder(GLASS_BOTTLE); maple syrup drinks with the
    // honey-bottle sound, birch water cures poison (RemoveEffectsConsumeEffect(POISON)).
    public static final Item MAPLE_SYRUP = registerItem("maple_syrup",
            (settings) -> new BottledDrinkItem(settings, false, SoundEvents.ITEM_HONEY_BOTTLE_DRINK),
            new MESettings()
                    .recipeRemainder(Items.GLASS_BOTTLE)
                    .food(FoodComponents.HONEY_BOTTLE).maxCount(16));
    public static final Item BIRCH_WATER = registerItem("birch_water",
            (settings) -> new BottledDrinkItem(settings, true, null),
            new MESettings()
                    .recipeRemainder(Items.GLASS_BOTTLE)
                    .food(FoodComponents.HONEY_BOTTLE).maxCount(16));

    public static final Item TOMATO = registerItem("tomato",
            Item::new,new MESettings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.3f).build()));
    public static final Item BELL_PEPPER = registerItem("bell_pepper",
            Item::new,new MESettings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.3f).build()));
    public static final Item CUCUMBER = registerItem("cucumber",
            Item::new,new MESettings()
                    .food(new FoodComponent.Builder().hunger(4).saturationModifier(0.3f).build()));
    public static final Item GARLIC = registerItem("garlic",
            (settings) -> new BlockItem(ModNatureBlocks.GARLIC_CROP, settings),new MESettings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.2f).build()));
    public static final Item LEEK = registerItem("leek",
            (settings) -> new BlockItem(ModNatureBlocks.LEEK_CROP, settings),new MESettings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.2f).build()));
    public static final Item LETTUCE = registerItem("lettuce",
            Item::new,new MESettings()
                    .food(new FoodComponent.Builder().hunger(3).saturationModifier(0.3f).build()));
    public static final Item ONION = registerItem("onion",
            (settings) -> new BlockItem(ModNatureBlocks.ONION_CROP, settings),new MESettings()
                    .food(new FoodComponent.Builder().hunger(2).saturationModifier(0.2f).build()));

    public static final Item LAYERED_CAKE = registerItem("layered_cake",
            (settings) -> new BlockItem(ModBlocks.LAYERED_CAKE, settings),new MESettings().maxCount(1));
    public static final Item BERRY_PIE = registerItem("berry_pie",
            Item::new,new MESettings().food(
                    new FoodComponent.Builder().hunger(8).saturationModifier(0.5f).build()));
    public static final Item BOILED_EGG = registerItem("boiled_egg",
            Item::new,new MESettings().food(
                    new FoodComponent.Builder().hunger(3).saturationModifier(0.6f).build()));
    public static final Item FISH_STEW = registerItem("fish_stew",
            Item::new,new MESettings().food(
                    new FoodComponent.Builder().hunger(8).saturationModifier(1.0f).build()).maxCount(1).recipeRemainder(Items.BOWL));
    public static final Item MEAT_BOWL = registerItem("meat_bowl",
            Item::new,new MESettings().food(
                    new FoodComponent.Builder().hunger(12).saturationModifier(1.0f).build()).maxCount(1).recipeRemainder(Items.BOWL));
    public static final Item MEAT_EGG_MEAL = registerItem("meat_egg_meal",
            Item::new,new MESettings().food(
                    new FoodComponent.Builder().hunger(9).saturationModifier(0.9f).build()).maxCount(1).recipeRemainder(Items.BOWL));
    public static final Item MEAT_SKEWER = registerItem("meat_skewer",
            Item::new,new MESettings().food(
                    new FoodComponent.Builder().hunger(5).saturationModifier(0.6f).build()).maxCount(8).recipeRemainder(Items.STICK));
    public static final Item COOKED_MEAT_SKEWER = registerItem("cooked_meat_skewer",
            Item::new,new MESettings().food(
                    new FoodComponent.Builder().hunger(10).saturationModifier(0.8f).build()).maxCount(8).recipeRemainder(Items.STICK));
    public static final Item POULTRY_MEAL = registerItem("poultry_meal",
            Item::new,new MESettings().food(
                    new FoodComponent.Builder().hunger(8).saturationModifier(0.8f).build()).maxCount(8).recipeRemainder(Items.BOWL));
    public static final Item VEGETABLE_SKEWER = registerItem("vegetable_skewer",
            Item::new,new MESettings().food(
                    new FoodComponent.Builder().hunger(3).saturationModifier(0.3f).build()).maxCount(8).recipeRemainder(Items.STICK));
    public static final Item COOKED_VEGETABLE_SKEWER = registerItem("cooked_vegetable_skewer",
            Item::new,new MESettings().food(
                    new FoodComponent.Builder().hunger(6).saturationModifier(0.6f).build()).maxCount(8).recipeRemainder(Items.STICK));
    public static final Item VEGETABLE_SOUP = registerItem("vegetable_soup",
            Item::new,new MESettings().food(
                    new FoodComponent.Builder().hunger(7).saturationModifier(0.6f).build()).maxCount(1).recipeRemainder(Items.BOWL));

    public static final Item SACK_OF_HORSEFEED = registerItem("sack_of_horsefeed",
            Item::new,new MESettings().food(
                    new FoodComponent.Builder().hunger(7).saturationModifier(0.4f).build()));


    private static Item registerItem(String name, Function<Item.Settings, Item> factory, Item.Settings settings) {
        Item item = (Item)factory.apply(settings);
        ItemGroupsME.FOOD_CONTENTS.add(item.getDefaultStack());
        TranslationEntries.itemEntries.add(item);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.ITEM, name));
        return Registry.register(Registries.ITEM, new Identifier(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod Food Items for " + MiddleEarth.MOD_ID);
    }
}
