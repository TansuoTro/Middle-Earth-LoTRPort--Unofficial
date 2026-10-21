package net.jukoz.me.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModNatureBlocks;
import net.jukoz.me.block.special.plants.CustomTallPlantBlock;
import net.jukoz.me.item.items.*;
import net.jukoz.me.item.utils.ModBannerPatternTags;
import net.jukoz.me.item.utils.ModItemGroups;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.item.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;

public class ModResourceItems {
    public static final Item MIDDLE_EARTH_MAP = ResourceItemsME.MIDDLE_EARTH_MAP;

    public static final Item EMPTY_PHIAL = ResourceItemsME.EMPTY_PHIAL;
    public static final Item WATER_PHIAL = ResourceItemsME.WATER_PHIAL;
    public static final Item STARLIGHT_PHIAL = ResourceItemsME.STARLIGHT_PHIAL;

    public static final Item RAW_TIN = ResourceItemsME.RAW_TIN;
    public static final Item TIN_INGOT = ResourceItemsME.TIN_INGOT;
    public static final Item TIN_NUGGET = ResourceItemsME.TIN_NUGGET;

    public static final Item RAW_LEAD = ResourceItemsME.RAW_LEAD;
    public static final Item LEAD_INGOT = ResourceItemsME.LEAD_INGOT;
    public static final Item LEAD_NUGGET = ResourceItemsME.LEAD_NUGGET;

    public static final Item BRONZE_INGOT = ResourceItemsME.BRONZE_INGOT;
    public static final Item BRONZE_NUGGET = ResourceItemsME.BRONZE_NUGGET;

    public static final Item RAW_SILVER = ResourceItemsME.RAW_SILVER;
    public static final Item SILVER_INGOT = ResourceItemsME.SILVER_INGOT;
    public static final Item SILVER_NUGGET = ResourceItemsME.SILVER_NUGGET;

    public static final Item ORC_STEEL_INGOT = registerItem("orc_steel_ingot",
            new Item(new FabricItemSettings()));
    public static final Item ORC_STEEL_NUGGET = registerItem("orc_steel_nugget",
            new Item(new FabricItemSettings()));

    public static final Item URUK_STEEL_INGOT = registerItem("uruk_steel_ingot",
            new Item(new FabricItemSettings()));
    public static final Item URUK_STEEL_NUGGET = registerItem("uruk_steel_nugget",
            new Item(new FabricItemSettings()));

    public static final Item STEEL_INGOT = ResourceItemsME.STEEL_INGOT;
    public static final Item STEEL_NUGGET = ResourceItemsME.STEEL_NUGGET;

    public static final Item ELVEN_STEEL_INGOT = registerItem("elven_steel_ingot",
            new Item(new FabricItemSettings()));
    public static final Item ELVEN_STEEL_NUGGET = registerItem("elven_steel_nugget",
            new Item(new FabricItemSettings()));

    public static final Item DWARVEN_STEEL_INGOT = registerItem("dwarven_steel_ingot",
            new Item(new FabricItemSettings()));
    public static final Item DWARVEN_STEEL_NUGGET = registerItem("dwarven_steel_nugget",
            new Item(new FabricItemSettings()));

    public static final Item MORGUL_STEEL_INGOT = registerItem("morgul_steel_ingot",
            new Item(new FabricItemSettings()));
    public static final Item MORGUL_STEEL_NUGGET = registerItem("morgul_steel_nugget",
            new Item(new FabricItemSettings()));


    public static final Item RAW_MITHRIL = ResourceItemsME.RAW_MITHRIL;
    public static final Item MITHRIL_INGOT = ResourceItemsME.MITHRIL_INGOT;
    public static final Item MITHRIL_NUGGET = ResourceItemsME.MITHRIL_NUGGET;

    public static final Item COPPER_ROD = registerItem("copper_rod",
            new Item(new FabricItemSettings()));
    public static final Item TIN_ROD = registerItem("tin_rod",
            new Item(new FabricItemSettings()));
    public static final Item LEAD_ROD = registerItem("lead_rod",
            new Item(new FabricItemSettings()));
    public static final Item BRONZE_ROD = registerItem("bronze_rod",
            new Item(new FabricItemSettings()));
    public static final Item SILVER_ROD = registerItem("silver_rod",
            new Item(new FabricItemSettings()));
    public static final Item GOLD_ROD = registerItem("gold_rod",
            new Item(new FabricItemSettings()));
    public static final Item IRON_ROD = registerItem("iron_rod",
            new Item(new FabricItemSettings()));
    public static final Item ORC_STEEL_ROD = registerItem("orc_steel_rod",
            new Item(new FabricItemSettings()));
    public static final Item URUK_STEEL_ROD = registerItem("uruk_steel_rod",
            new Item(new FabricItemSettings()));
    public static final Item STEEL_ROD = registerItem("steel_rod",
            new Item(new FabricItemSettings()));
    public static final Item ELVEN_STEEL_ROD = registerItem("elven_steel_rod",
            new Item(new FabricItemSettings()));
    public static final Item DWARVEN_STEEL_ROD = registerItem("dwarven_steel_rod",
            new Item(new FabricItemSettings()));
    public static final Item MORGUL_STEEL_ROD = registerItem("morgul_steel_rod",
            new Item(new FabricItemSettings()));
    public static final Item MITHRIL_ROD = registerItem("mithril_rod",
            new Item(new FabricItemSettings().fireproof()));

    public static final Item IRON_CHAINMAIL = registerItem("iron_chainmail",
            new Item(new FabricItemSettings()));

    public static final Item RED_AGATE_SHARD = ResourceItemsME.RED_AGATE_SHARD;
    public static final Item CITRINE_SHARD = ResourceItemsME.CITRINE_SHARD;
    public static final Item QUARTZ_SHARD = ResourceItemsME.QUARTZ_SHARD;

    public static final Item ORC_BONE = registerItem("orc_bone",
            new Item(new FabricItemSettings()));
    public static final Item WARG_BONE = registerItem("warg_bone",
            new Item(new FabricItemSettings()));

    public static final Item ASH = ResourceItemsME.ASH;

    public static final Item PEBBLE = ResourceItemsME.PEBBLE;
    public static final Item PINECONE = ResourceItemsME.PINECONE;
    public static final Item LIT_PINECONE = ResourceItemsME.LIT_PINECONE;

    public static final Item STRAW = ResourceItemsME.STRAW;
    public static final Block REEDS = ResourceItemsME.REEDS;

    public static final Item DUCK_FEATHER = registerItem("duck_feather",
            new Item(new FabricItemSettings()));
    public static final Item SWAN_FEATHER = ResourceItemsME.SWAN_FEATHER;
            
    public static final Item GLOWWORM_BOTTLE = ResourceItemsME.GLOWWORM_BOTTLE;

    public static final Item COPPER_COIN = ResourceItemsME.COPPER_COIN;
    public static final Item SILVER_COIN = ResourceItemsME.SILVER_COIN;


    public static final Item FLAX = ResourceItemsME.FLAX;
    public static final Item FLAX_SEEDS = ResourceItemsME.FLAX_SEEDS;
    public static final Item PIPEWEED = ResourceItemsME.PIPEWEED;
    public static final Item PIPEWEED_SEEDS = ResourceItemsME.PIPEWEED_SEEDS;
    public static final Item TOMATO_SEEDS = ResourceItemsME.TOMATO_SEEDS;
    public static final Item BELL_PEPPER_SEEDS = ResourceItemsME.BELL_PEPPER_SEEDS;
    public static final Item CUCUMBER_SEEDS = ResourceItemsME.CUCUMBER_SEEDS;
    public static final Item LETTUCE_SEEDS = ResourceItemsME.LETTUCE_SEEDS;

    public static final Item GONDOR_BANNER_PATTERN = ResourceItemsME.GONDOR_BANNER_PATTERN;
    public static final Item ROHAN_BANNER_PATTERN = ResourceItemsME.ROHAN_BANNER_PATTERN;
    public static final Item LONGBEARD_BANNER_PATTERN = registerItem("longbeard_banner_pattern",
            new BannerPatternItem(ModBannerPatternTags.LONGBEARD_PATTERN_ITEM, new MESettings().maxCount(1).rarity(Rarity.RARE)));
    public static final Item LOTHLORIEN_BANNER_PATTERN = ResourceItemsME.LOTHLORIEN_BANNER_PATTERN;
    public static final Item MORDOR_GREAT_EYE_BANNER_PATTERN = registerItem("mordor_great_eye_banner_pattern",
            new BannerPatternItem(ModBannerPatternTags.MORDOR_GREAT_EYE_PATTERN_ITEM, new MESettings().maxCount(1).rarity(Rarity.RARE)));
    public static final Item MORDOR_EYE_BANNER_PATTERN = registerItem("mordor_eye_banner_pattern",
            new BannerPatternItem(ModBannerPatternTags.MORDOR_EYE_PATTERN_ITEM, new MESettings().maxCount(1).rarity(Rarity.RARE)));
    public static final Item MISTY_ORCS_EYE_BANNER_PATTERN = registerItem("misty_orcs_eye_banner_pattern",
            new BannerPatternItem(ModBannerPatternTags.MISTY_ORCS_EYE_PATTERN_ITEM, new MESettings().maxCount(1).rarity(Rarity.RARE)));
    public static final Item MISTY_ORCS_PEAKS_BANNER_PATTERN = registerItem("misty_orcs_peaks_banner_pattern",
            new BannerPatternItem(ModBannerPatternTags.MISTY_ORCS_PEAKS_PATTERN_ITEM, new MESettings().maxCount(1).rarity(Rarity.RARE)));

    public static Block registerBlock(String name, Block block, boolean absent) {
        if(!absent) registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new ResourceLocation(MiddleEarth.MOD_ID, name), block);
    }
    static Item registerBlockItem(String name, Block block) {
        var item =  Registry.register(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));

        Item.BLOCK_ITEMS.put(block, item);
        ModItemGroups.RESOURCES_CONTENTS.add(item.getDefaultStack());
        return item;
    }

    private static Item registerItem(String name, Item item) {
        ModItemGroups.RESOURCES_CONTENTS.add(item.getDefaultStack());
        return Registry.register(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, name), item);
    }

    public static void registerModItems() {
        LoggerUtil.getInstance().logDebugMsg("Registering Mod Resource Items for " + MiddleEarth.MOD_ID);
    }
}
