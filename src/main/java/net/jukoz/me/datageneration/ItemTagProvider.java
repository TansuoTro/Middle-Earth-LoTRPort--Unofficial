package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.OreRockSets;
import net.jukoz.me.datageneration.content.tags.*;
import net.jukoz.me.item.*;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.datageneration.content.models.HotMetalsModel;
import net.jukoz.me.datageneration.content.models.SimpleDyeableItemModel;
import net.jukoz.me.utils.ItemTagsME;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.RegistryWrapper;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;

public class ItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        populatePortedTags();
        var bones = getOrCreateTagBuilder(TagKey.of(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "bones")));
        var feathers = getOrCreateTagBuilder(TagKey.of(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "feathers")));
        var cloaks = getOrCreateTagBuilder(TagKey.of(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "cloaks")));

        TagKey<Item> iron_ores = TagKey.of(Registries.ITEM, new ResourceLocation("minecraft", "iron_ores"));
        TagKey<Item> gold_ores = TagKey.of(Registries.ITEM, new ResourceLocation("minecraft", "gold_ores"));
        TagKey<Item> copper_ores = TagKey.of(Registries.ITEM, new ResourceLocation("minecraft", "copper_ores"));
        TagKey<Item> coal_ores = TagKey.of(Registries.ITEM, new ResourceLocation("minecraft", "coal_ores"));

        TagKey<Item> saplings = TagKey.of(Registries.ITEM, new ResourceLocation("minecraft", "saplings"));
        TagKey<Item> wooden_slabs = TagKey.of(Registries.ITEM, new ResourceLocation("minecraft", "wooden_slabs"));
        TagKey<Item> logs_that_burn = TagKey.of(Registries.ITEM, new ResourceLocation("minecraft", "logs_that_burn"));
        TagKey<Item> stone_crafting_materials = TagKey.of(Registries.ITEM, new ResourceLocation("minecraft", "stone_crafting_materials"));
        TagKey<Item> stone_tool_materials = TagKey.of(Registries.ITEM, new ResourceLocation("minecraft", "stone_tool_materials"));

        TagKey<Item> tin_ores = TagKey.of(Registries.ITEM, new ResourceLocation("me", "tin_ores"));
        TagKey<Item> lead_ores = TagKey.of(Registries.ITEM, new ResourceLocation("me", "lead_ores"));
        TagKey<Item> silver_ores = TagKey.of(Registries.ITEM, new ResourceLocation("me", "silver_ores"));
        TagKey<Item> mithril_ores = TagKey.of(Registries.ITEM, new ResourceLocation("me", "mithril_ores"));
        TagKey<Item> shingles = TagKey.of(Registries.ITEM, new ResourceLocation("me", "shingles"));

        TagKey<Item> mod_stripped_logs = TagKey.of(Registries.ITEM, new ResourceLocation("me", "mod_stripped_logs"));
        getOrCreateTagBuilder(TagKey.of(Registries.ITEM, new ResourceLocation("me", "mod_planks"))).add(notNullItems(Planks.getItemPlanks()));


        getOrCreateTagBuilder(TagKey.of(Registries.ITEM, new ResourceLocation("minecraft", "planks"))).add(notNullItems(Planks.getItemPlanks()));
        getOrCreateTagBuilder(TagKey.of(Registries.ITEM, new ResourceLocation("minecraft", "logs"))).add(notNullItems(Logs.getItemLogs()));


        bones.add(Items.BONE);
        bones.add(ModResourceItems.ORC_BONE);
        bones.add(ModResourceItems.WARG_BONE);

        feathers.add(ModResourceItems.DUCK_FEATHER);
        feathers.add(ModResourceItems.SWAN_FEATHER);
        feathers.add(Items.FEATHER);

        cloaks.add(ModEquipmentItems.CLOAK);
        cloaks.add(ModEquipmentItems.TUNIC_CLOAK);
        cloaks.add(ModEquipmentItems.FUR_CLOAK);
        cloaks.add(ModEquipmentItems.CHAINMAIL_FUR_CLOAK);
        cloaks.add(ModEquipmentItems.NAZGUL_CLOAK);
        cloaks.add(ModEquipmentItems.CLOAK_HOOD);
        cloaks.add(ModEquipmentItems.FUR_CLOAK_HOOD);
        cloaks.add(ModEquipmentItems.NAZGUL_CLOAK_HOOD);

        for (OreRockSets.OreRockSet set : OreRockSets.sets) {
            if(set.coal_ore() != null) {
                getOrCreateTagBuilder(coal_ores)
                        .add(set.coal_ore().asItem());
            }
            if(set.copper_ore() != null) {
                getOrCreateTagBuilder(copper_ores)
                        .add(set.copper_ore().asItem());
            }
            if(set.tin_ore() != null) {
                getOrCreateTagBuilder(tin_ores)
                        .add(set.tin_ore().asItem());
            }
            if(set.lead_ore() != null) {
                getOrCreateTagBuilder(lead_ores)
                        .add(set.lead_ore().asItem());
            }
            if(set.silver_ore() != null) {
                getOrCreateTagBuilder(silver_ores)
                        .add(set.silver_ore().asItem());
            }
            if(set.gold_ore() != null) {
                getOrCreateTagBuilder(gold_ores)
                        .add(set.gold_ore().asItem());
            }
            if(set.iron_ore() != null) {
                getOrCreateTagBuilder(iron_ores)
                        .add(set.iron_ore().asItem());
            }
            if(set.mithril_ore() != null) {
                getOrCreateTagBuilder(mithril_ores)
                        .add(set.mithril_ore().asItem());
            }
        }

        WoodenSlabs.woodenSlabs.forEach(block -> {
            if (block == null) { return; }
            getOrCreateTagBuilder(wooden_slabs).add(block.asItem());
        });

        ModdedStrippedLogs.strippedLogs.forEach(block -> {
            if (block == null) { return; }
            getOrCreateTagBuilder(mod_stripped_logs).add(block.asItem());
        });

        Shingles.shingles.forEach(block -> {
            if (block == null) { return; }
            getOrCreateTagBuilder(shingles).add(block.asItem());
        });

        Saplings.saplings.forEach(sapling -> {
            if (sapling == null) { return; }
            getOrCreateTagBuilder(saplings).add(sapling.asItem());
        });

        LogsThatBurn.logsThatBurn.forEach(log -> {
            if (log == null) { return; }
            getOrCreateTagBuilder(logs_that_burn).add(log.asItem());
        });

        Stones.stones.forEach(stone -> {
            if (stone == null) { return; }
            getOrCreateTagBuilder(stone_crafting_materials).add(stone.asItem());
            if(!stone.asItem().toString().contains("jadeite")){
                getOrCreateTagBuilder(stone_tool_materials).add(stone.asItem());
            }
        });
    }

    /** Restore lists maintained by donor registration; static tags can reference these lists. */
    private void populatePortedTags() {
        addToTag("me:repairs_leather_armor", Items.LEATHER);
        addToTag("me:repairs_iron_armor", Items.IRON_INGOT);
        addToTag("me:repairs_fur_armor", ResourceItemsME.FUR);
        addToTag("me:repairs_fabric_armor", ResourceItemsME.FABRIC);
        addToTag("me:spider_tool_materials", ResourceItemsME.SPIDER_STINGER);
        addToTag("minecraft:cow_food", Items.WHEAT);
        addToTag("minecraft:goat_food", Items.WHEAT);
        addToTag("me:bones", ResourceItemsME.DIRTY_BONE, ResourceItemsME.FANG);
        addToTag("me:cloaks", notNullItems(EquipmentItemsME.helmetAttachments));
        addToTag("me:cloaks", notNullItems(EquipmentItemsME.backAttachments));

        addToTag("me:ported_axes", notNullItems(WeaponEnchants.axes));
        addToTag("me:daggers", notNullItems(WeaponEnchants.daggers));
        addToTag("minecraft:swords", notNullItems(WeaponEnchants.swords));
        addToTag("me:spears", notNullItems(WeaponItemsME.spears));
        addToTag("minecraft:enchantable/weapon", notNullItems(WeaponEnchants.weapons));
        addToTag("minecraft:enchantable/sword", notNullItems(WeaponEnchants.sharpWeapons));
        addToTag("me:ported_bows", notNullItems(Bows.bows));
        addToTag("minecraft:enchantable/bow", Items.BOW);
        addToTag("minecraft:enchantable/bow", notNullItems(Bows.bows));
        addToTag("minecraft:enchantable/crossbow", Items.CROSSBOW);
        addToTag("minecraft:enchantable/crossbow", notNullItems(Crossbows.crossbows));
        addToTag("minecraft:crossbows", Items.CROSSBOW);
        addToTag("minecraft:crossbows", notNullItems(Crossbows.crossbows));
        addToTag("c:tools/ranged_weapon", notNullItems(Bows.bows));
        addToTag("c:tools/bow", notNullItems(Bows.bows));
        addToTag("c:tools/bows", notNullItems(Bows.bows));
        addToTag("c:tools/shields", notNullItems(WeaponItemsME.shields));
        addToTag("c:tools/shield", notNullItems(WeaponItemsME.shields));
        for (String tag : new String[]{"durability", "vanishing", "equippable", "armor"}) {
            addToTag("minecraft:enchantable/" + tag, notNullItems(ArmorTags.armors));
        }
        for (String tag : new String[]{"durability", "vanishing"}) {
            addToTag("minecraft:enchantable/" + tag, notNullItems(WeaponEnchants.weapons));
            addToTag("minecraft:enchantable/" + tag, notNullItems(ToolItemsME.smithingHammers));
        }
        addToTag("minecraft:enchantable/durability", notNullItems(Crossbows.crossbows));
        addToTag("minecraft:enchantable/head_armor", notNullItems(ArmorTags.headArmors));
        addToTag("minecraft:enchantable/chest_armor", notNullItems(ArmorTags.chestArmors));
        addToTag("minecraft:enchantable/leg_armor", notNullItems(ArmorTags.legArmors));
        addToTag("minecraft:enchantable/foot_armor", notNullItems(ArmorTags.footArmors));
        addToTag("me:enchantable/basic_armor", notNullItems(ArmorTags.basicArmors));
        addToTag("me:enchantable/light_armor", notNullItems(ArmorTags.lightArmors));
        addToTag("me:enchantable/medium_armor", notNullItems(ArmorTags.mediumArmors));
        addToTag("me:enchantable/sturdy_armor", notNullItems(ArmorTags.sturdyArmors));
        addToTag("me:enchantable/heavy_armor", notNullItems(ArmorTags.heavyArmors));
        addToTag("me:enchantable/incomplete_armors", notNullItems(ArmorTags.incompleteArmors));
        addToTag("me:ported_ingot_shaping", notNullItems(HotMetalsModel.ingots));
        addToTag("me:ported_nugget_shaping", notNullItems(HotMetalsModel.nuggets));
        addToTag("minecraft:leaves", notNullItems(LeavesSets.getItemLeaves()));
        for (Block block : WoodenVerticalSlabs.woodenVericalSlabs) {
            if (block != null) addToTag("me:wooden_vertical_slabs", block.asItem());
        }
        for (Block block : Fences.fences) {
            if (block != null) addToTag("minecraft:wooden_fences", block.asItem());
        }
        for (Block block : ModdedStrippedLogs.strippedLogs) {
            if (block != null) addToTag("c:stripped_logs", block.asItem());
        }
        addToTag("minecraft:dyeable", notNullItems(SimpleDyeableItemModel.items));
        addToTag("me:horse_armor", Items.LEATHER_HORSE_ARMOR, Items.IRON_HORSE_ARMOR,
                Items.GOLDEN_HORSE_ARMOR, Items.DIAMOND_HORSE_ARMOR,
                EquipmentItemsME.GONDORIAN_HORSE_ARMOR, EquipmentItemsME.ROHIRRIC_HORSE_ARMOR,
                EquipmentItemsME.DALISH_HORSE_ARMOR, EquipmentItemsME.LORIEN_HORSE_ARMOR);

        addToTag("me:warg_food",
                Items.RABBIT,
                Items.CHICKEN,
                Items.PORKCHOP,
                Items.BEEF,
                Items.MUTTON,
                FoodItemsME.RAW_HORSE);

        addToTag("me:warg_armor",
                EquipmentItemsME.WARG_MORDOR_PLATE_ARMOR,
                EquipmentItemsME.WARG_GUNDABAD_PLATE_ARMOR,
                EquipmentItemsME.WARG_ISENGARD_PLATE_ARMOR,
                EquipmentItemsME.WARG_MORDOR_MAIL_ARMOR,
                EquipmentItemsME.WARG_LEATHER_ARMOR,
                EquipmentItemsME.WARG_REINFORCED_LEATHER_ARMOR);

        addToTag("me:broadhoof_goat_armor",
                EquipmentItemsME.BROADHOOF_GOAT_PLATE_ARMOR,
                EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR,
                EquipmentItemsME.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR);

        addToTag("me:great_horn_armor",
                EquipmentItemsME.GREAT_HORN_LIGHT_ARMOR,
                EquipmentItemsME.GREAT_HORN_LIGHT_GRAY_ARMOR,
                EquipmentItemsME.GREAT_HORN_LIGHT_GREEN_ARMOR,
                EquipmentItemsME.GREAT_HORN_PLATE_ARMOR,
                EquipmentItemsME.GREAT_HORN_ORNAMENTED_PLATE_ARMOR,
                EquipmentItemsME.GREAT_HORN_GREEN_PLATE_ARMOR);

        addToTag("minecraft:dyeable",
                EquipmentItemsME.BROADHOOF_GOAT_PADDED_ARMOR,
                EquipmentItemsME.BROADHOOF_GOAT_ORNAMENTED_PADDED_ARMOR,
                EquipmentItemsME.WARG_LEATHER_ARMOR,
                EquipmentItemsME.WARG_REINFORCED_LEATHER_ARMOR,
                EquipmentItemsME.GREAT_HORN_LIGHT_ARMOR,
                EquipmentItemsME.GREAT_HORN_LIGHT_GRAY_ARMOR,
                EquipmentItemsME.GREAT_HORN_LIGHT_GREEN_ARMOR);

        addToTag("me:chains",
                Items.CHAIN,
                ModDecorativeBlocks.BRONZE_CHAIN.asItem(),
                ModDecorativeBlocks.BRONZE_BROAD_CHAIN.asItem(),
                ModDecorativeBlocks.CRUDE_CHAIN.asItem(),
                ModDecorativeBlocks.CRUDE_BROAD_CHAIN.asItem(),
                ModDecorativeBlocks.SPIKY_CHAIN.asItem());

        addToTag("me:troll_weapons",
                WeaponItemsME.TROLL_MACE,
                WeaponItemsME.MACE_OF_SAURON);

        addToTag("me:troll_food",
                FoodItemsME.RAW_HORSE,
                FoodItemsME.COOKED_HORSE,
                FoodItemsME.COOKED_MEAT_SKEWER,
                Items.PORKCHOP,
                Items.COOKED_PORKCHOP,
                Items.MUTTON,
                Items.COOKED_MUTTON,
                Items.BEEF,
                Items.COOKED_BEEF,
                Items.CHICKEN,
                Items.COOKED_CHICKEN,
                Items.ROTTEN_FLESH,
                Items.MUSHROOM_STEW,
                Items.BROWN_MUSHROOM,
                Items.RED_MUSHROOM);

        addToTag("me:character_helmet_should_show_ears",
                EquipmentItemsME.LORIEN_DIADEM,
                EquipmentItemsME.KETTLE_HAT,
                EquipmentItemsME.MORDOR_KETTLE_HAT,
                EquipmentItemsME.RUSTED_MORDOR_KETTLE_HAT,
                EquipmentItemsME.DOL_GULDUR_JAILER_COLLAR,
                EquipmentItemsME.WEATHERED_DOL_GULDUR_JAILER_COLLAR,
                EquipmentItemsME.MORIA_GOBLIN_MANDIBLE_HELMET,
                EquipmentItemsME.MORIA_GOBLIN_SCREECHER_HELMET,
                EquipmentItemsME.MORIA_GOBLIN_CAPTAIN_HELMET);

        addToTag("me:character_helmet_should_hide_hair",
                EquipmentItemsME.MAIL_COIF,
                EquipmentItemsME.CLOSED_MAIL_COIF,
                EquipmentItemsME.HOOD,
                EquipmentItemsME.TALL_HOOD,
                EquipmentItemsME.TAN_FUR_HOOD,
                EquipmentItemsME.BLACK_FUR_HOOD,
                EquipmentItemsME.GRAY_FUR_HOOD,
                EquipmentItemsME.BROWN_FUR_HOOD,
                EquipmentItemsME.WHITE_FUR_HOOD,
                EquipmentItemsME.ELVEN_MAIL_COIF,
                EquipmentItemsME.LORIEN_MAIL_COIF_DIADEM,
                EquipmentItemsME.DWARVEN_MAIL_COIF,
                EquipmentItemsME.EREBOR_MAIL_COIF,
                EquipmentItemsME.EREBOR_GILDED_MAIL_COIF,
                EquipmentItemsME.ORCISH_MAIL_COIF,
                EquipmentItemsME.RUSTED_ORCISH_MAIL_COIF);

        addToTag("me:character_helmet_should_hide_beard",
                EquipmentItemsME.CLOSED_MAIL_COIF,
                EquipmentItemsME.KETTLE_HAT_WITH_CLOSED_COIF);
    }

    private void addToTag(String id, Item... items) {
        getOrCreateTagBuilder(TagKey.of(Registries.ITEM, new ResourceLocation(id))).add(items);
    }

    private static Item[] notNullItems(java.util.List<Item> values) {
        return values.stream().filter(java.util.Objects::nonNull).toArray(Item[]::new);
    }
}

