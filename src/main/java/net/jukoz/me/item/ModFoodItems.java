package net.jukoz.me.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModNatureBlocks;
import net.jukoz.me.datageneration.content.models.SimpleItemModel;
import net.jukoz.me.item.items.SkewerItem;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.Items;

public class ModFoodItems {

    public static final Item LEMBAS = FoodItemsME.LEMBAS;

    public static final Item RAW_CRAB_CLAW = registerItem("raw_crab_claw",
            new Item(new FabricItemSettings().food(
                    new FoodProperties.Builder().hunger(2).saturationModifier(0.2F).meat().build())));
    public static final Item COOKED_CRAB_CLAW = registerItem("cooked_crab_claw",
            new Item(new FabricItemSettings().food(
                    new FoodProperties.Builder().hunger(5).saturationModifier(0.6F).meat().build())));

    public static final Item RAW_GOOSE = registerItem("raw_goose",
            new Item(new FabricItemSettings().food(
                    new FoodProperties.Builder().hunger(3).saturationModifier(0.3F).meat().build())));
    public static final Item COOKED_GOOSE = registerItem("cooked_goose",
            new Item(new FabricItemSettings().food(
                    new FoodProperties.Builder().hunger(7).saturationModifier(0.8F).meat().build())));

    public static final Item RAW_DUCK = registerItem("raw_duck",
            new Item(new FabricItemSettings().food(
                    new FoodProperties.Builder().hunger(2).saturationModifier(0.3F).meat().build())));
    public static final Item COOKED_DUCK = registerItem("cooked_duck",
            new Item(new FabricItemSettings().food(
                    new FoodProperties.Builder().hunger(6).saturationModifier(0.7F).meat().build())));

    public static final Item RAW_SWAN = registerItem("raw_swan",
            new Item(new FabricItemSettings().food(
                    new FoodProperties.Builder().hunger(3).saturationModifier(0.4F).meat().build())));
    public static final Item COOKED_SWAN = registerItem("cooked_swan",
            new Item(new FabricItemSettings().food(
                    new FoodProperties.Builder().hunger(8).saturationModifier(0.8F).meat().build())));

    public static final Item RAW_RAT = registerItem("raw_rat",
            new Item(new FabricItemSettings().food(
                    new FoodProperties.Builder().hunger(2).saturationModifier(0.3F)
                            .statusEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build())));
    public static final Item COOKED_RAT = registerItem("cooked_rat",
            new Item(new FabricItemSettings().food(
                    new FoodProperties.Builder().hunger(5).saturationModifier(0.6F).meat().build())));

    public static final Item RAW_HORSE = FoodItemsME.RAW_HORSE;
    public static final Item COOKED_HORSE = FoodItemsME.COOKED_HORSE;

    public static final Item MAGGOTY_BREAD = FoodItemsME.MAGGOTY_BREAD;
    public static final Item TOUGH_BERRIES = FoodItemsME.TOUGH_BERRIES;
    public static final Item STRAWBERRY = registerItem("strawberry",
            new ItemNameBlockItem(ModNatureBlocks.STRAWBERRY_BUSH,
                    new FabricItemSettings().food(
                            new FoodProperties.Builder().hunger(2).saturationModifier(0.1f).build())));

    public static final Item TOMATO = FoodItemsME.TOMATO;
    public static final Item BELL_PEPPER = FoodItemsME.BELL_PEPPER;
    public static final Item CUCUMBER = FoodItemsME.CUCUMBER;
    public static final Item GARLIC = FoodItemsME.GARLIC;
    public static final Item LEEK = FoodItemsME.LEEK;
    public static final Item LETTUCE = FoodItemsME.LETTUCE;
    public static final Item ONION = FoodItemsME.ONION;

    public static final Item BERRY_PIE = FoodItemsME.BERRY_PIE;
    public static final Item BOILED_EGG = FoodItemsME.BOILED_EGG;
    public static final Item CRAB_SOUP = registerItem("crab_soup",
            new BowlFoodItem(new FabricItemSettings().food(
                    new FoodProperties.Builder().hunger(8).saturationModifier(0.8f).build()).maxCount(1)));
    public static final Item FISH_STEW = FoodItemsME.FISH_STEW;
    public static final Item MEAT_BOWL = FoodItemsME.MEAT_BOWL;
    public static final Item MEAT_EGG_MEAL = FoodItemsME.MEAT_EGG_MEAL;
    public static final Item MEAT_SKEWER = FoodItemsME.MEAT_SKEWER;
    public static final Item COOKED_MEAT_SKEWER = FoodItemsME.COOKED_MEAT_SKEWER;
    public static final Item POULTRY_MEAL = FoodItemsME.POULTRY_MEAL;
    public static final Item RAT_SKEWER = registerItem("rat_skewer",
            new SkewerItem(new FabricItemSettings().food(
                    new FoodProperties.Builder().hunger(1).saturationModifier(0.1f)
                            .statusEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0), 0.3F).meat().build()).maxCount(16)));
    public static final Item COOKED_RAT_SKEWER = registerItem("cooked_rat_skewer",
            new SkewerItem(new FabricItemSettings().food(
                    new FoodProperties.Builder().hunger(7).saturationModifier(0.8f).meat().build()).maxCount(16)));
    public static final Item VEGETABLE_SKEWER = FoodItemsME.VEGETABLE_SKEWER;
    public static final Item COOKED_VEGETABLE_SKEWER = FoodItemsME.COOKED_VEGETABLE_SKEWER;
    public static final Item VEGETABLE_SOUP = FoodItemsME.VEGETABLE_SOUP;

    private static Item registerItem(String name, Item item) {
        ModItemGroups.FOOD_CONTENTS.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        LoggerUtil.getInstance().logDebugMsg("Registering Mod Food Items for " + MiddleEarth.MOD_ID);
    }
}
