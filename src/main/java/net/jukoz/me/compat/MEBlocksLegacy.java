package net.jukoz.me.compat;

import net.minecraft.world.level.block.Block;
import net.jukoz.me.block.registration.GenericBlockSets;
import net.jukoz.me.block.registration.ModBlocks;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.block.registration.ModNatureBlocks;
import net.jukoz.me.block.registration.StoneBlockSets;
import net.jukoz.me.block.registration.WoodBlockSets;
import net.jukoz.me.block.utils.setBuilders.WoodBlockSetBuilder;
import net.minecraft.world.level.block.Blocks;

/**
 * Aliases for block names the old 1.20.1 datagen still references after the donor
 * moved them into the block-set builders. Datagen runs after registration, so the
 * set fields are populated by the time these are read.
 */
public final class MEBlocksLegacy {
    private MEBlocksLegacy() {
    }

    public static final Block ACACIA_CHAIR = WoodBlockSets.ACACIA_SET.chair();
    public static final Block ACACIA_STOOL = WoodBlockSets.ACACIA_SET.stool();
    public static final Block ACACIA_TABLE = WoodBlockSets.ACACIA_SET.table();
    public static final Block ACACIA_VERTICAL_SLAB = WoodBlockSets.ACACIA_SET.verticalSlab();
    public static final Block ACACIA_WOOD_SLAB = woodSlab(WoodBlockSets.ACACIA_SET);
    public static final Block ACACIA_WOOD_VERTICAL_SLAB = woodVerticalSlab(WoodBlockSets.ACACIA_SET);
    public static final Block ALLOY_FURNACE = ModDecorativeBlocks.FORGE;
    public static final Block ANDESITE_VERTICAL_SLAB = StoneBlockSets.ANDESITE_SET.baseBlocks.verticalSlab();
    public static final Block BAMBOO_CHAIR = WoodBlockSets.BAMBOO.chair();
    public static final Block BAMBOO_STOOL = WoodBlockSets.BAMBOO.stool();
    public static final Block BAMBOO_TABLE = WoodBlockSets.BAMBOO.table();
    public static final Block BAMBOO_VERTICAL_SLAB = WoodBlockSets.BAMBOO.verticalSlab();
    public static final Block BAMBOO_WOOD_SLAB = woodSlab(WoodBlockSets.BAMBOO);
    public static final Block BAMBOO_WOOD_VERTICAL_SLAB = woodVerticalSlab(WoodBlockSets.BAMBOO);
    public static final Block BIRCH_CHAIR = WoodBlockSets.BIRCH_SET.chair();
    public static final Block BIRCH_STOOL = WoodBlockSets.BIRCH_SET.stool();
    public static final Block BIRCH_TABLE = WoodBlockSets.BIRCH_SET.table();
    public static final Block BIRCH_VERTICAL_SLAB = WoodBlockSets.BIRCH_SET.verticalSlab();
    public static final Block BIRCH_WOOD_SLAB = woodSlab(WoodBlockSets.BIRCH_SET);
    public static final Block BIRCH_WOOD_VERTICAL_SLAB = woodVerticalSlab(WoodBlockSets.BIRCH_SET);
    public static final Block BLACKSTONE_TRAPDOOR = StoneBlockSets.BLACKSTONE_SET.baseBlocks.trapdoor();
    public static final Block CALCITE_SLAB = StoneBlockSets.CALCITE_SET.baseBlocks.slab();
    public static final Block CALCITE_STAIRS = StoneBlockSets.CALCITE_SET.baseBlocks.stairs();
    public static final Block CALCITE_VERTICAL_SLAB = StoneBlockSets.CALCITE_SET.baseBlocks.verticalSlab();
    public static final Block CALCITE_WALL = StoneBlockSets.CALCITE_SET.baseBlocks.wall();
    public static final Block CHERRY_CHAIR = WoodBlockSets.CHERRY_SET.chair();
    public static final Block CHERRY_STOOL = WoodBlockSets.CHERRY_SET.stool();
    public static final Block CHERRY_TABLE = WoodBlockSets.CHERRY_SET.table();
    public static final Block CHERRY_VERTICAL_SLAB = WoodBlockSets.CHERRY_SET.verticalSlab();
    public static final Block CHERRY_WOOD_SLAB = woodSlab(WoodBlockSets.CHERRY_SET);
    public static final Block CHERRY_WOOD_VERTICAL_SLAB = woodVerticalSlab(WoodBlockSets.CHERRY_SET);
    public static final Block COBBLED_DEEPSLATE_VERTICAL_SLAB = StoneBlockSets.DEEPSLATE_SET.cobblestoneBlocks.verticalSlab();
    public static final Block COBBLESTONE_VERTICAL_SLAB = StoneBlockSets.STONE_SET.cobblestoneBlocks.verticalSlab();
    public static final Block CRIMSON_VERTICAL_SLAB = WoodBlockSets.CRIMSON_SET.verticalSlab();
    public static final Block CRIMSON_WOOD_SLAB = woodSlab(WoodBlockSets.CRIMSON_SET);
    public static final Block CRIMSON_WOOD_VERTICAL_SLAB = woodVerticalSlab(WoodBlockSets.CRIMSON_SET);
    public static final Block CUT_LEAD = GenericBlockSets.CUT_LEAD.blockSet.base();
    public static final Block CUT_LEAD_SLAB = GenericBlockSets.CUT_LEAD.blockSet.slab();
    public static final Block CUT_LEAD_STAIRS = GenericBlockSets.CUT_LEAD.blockSet.stairs();
    public static final Block CUT_SILVER = GenericBlockSets.CUT_SILVER.blockSet.base();
    public static final Block CUT_SILVER_SLAB = GenericBlockSets.CUT_SILVER.blockSet.slab();
    public static final Block CUT_SILVER_STAIRS = GenericBlockSets.CUT_SILVER.blockSet.stairs();
    public static final Block DARK_OAK_CHAIR = WoodBlockSets.DAK_OAK_SET.chair();
    public static final Block DARK_OAK_STOOL = WoodBlockSets.DAK_OAK_SET.stool();
    public static final Block DARK_OAK_TABLE = WoodBlockSets.DAK_OAK_SET.table();
    public static final Block DARK_OAK_VERTICAL_SLAB = WoodBlockSets.DAK_OAK_SET.verticalSlab();
    public static final Block DARK_OAK_WOOD_SLAB = woodSlab(WoodBlockSets.DAK_OAK_SET);
    public static final Block DARK_OAK_WOOD_VERTICAL_SLAB = woodVerticalSlab(WoodBlockSets.DAK_OAK_SET);
    public static final Block DEEPSLATE_BRICK_VERTICAL_SLAB = StoneBlockSets.DEEPSLATE_SET.brickBlocks.verticalSlab();
    public static final Block DEEPSLATE_TILE_VERTICAL_SLAB = StoneBlockSets.DEEPSLATE_SET.tileBlocks.verticalSlab();
    public static final Block DIORITE_VERTICAL_SLAB = StoneBlockSets.DIORITE_SET.baseBlocks.verticalSlab();
    public static final Block DRY_GRASS = ModNatureBlocks.DYING_GRASS;
    public static final Block DWARVEN_STEEL_BLOCK = ModBlocks.KHAZAD_STEEL_BLOCK;
    public static final Block ELVEN_STEEL_BLOCK = ModBlocks.EDHEL_STEEL_BLOCK;
    public static final Block EPMOSTO_CARVED_WINDOW = ModDecorativeBlocks.WOOD_FRAMED_WINDOW;
    public static final Block EPMOSTO_CARVED_WINDOW_PANE = ModDecorativeBlocks.WOOD_FRAMED_WINDOW_PANE;
    public static final Block GIANT_HOROKAKA = ModNatureBlocks.GRASS_TUFT;
    public static final Block GRANITE_VERTICAL_SLAB = StoneBlockSets.GRANITE_SET.baseBlocks.verticalSlab();
    public static final Block HANGING_COBWEB = ModNatureBlocks.HANGING_WEBS;
    public static final Block HOROKAKA = ModNatureBlocks.SPARSE_GRASS;
    public static final Block JUNGLE_CHAIR = WoodBlockSets.JUNGLE_SET.chair();
    public static final Block JUNGLE_STOOL = WoodBlockSets.JUNGLE_SET.stool();
    public static final Block JUNGLE_TABLE = WoodBlockSets.JUNGLE_SET.table();
    public static final Block JUNGLE_VERTICAL_SLAB = WoodBlockSets.JUNGLE_SET.verticalSlab();
    public static final Block JUNGLE_WOOD_SLAB = woodSlab(WoodBlockSets.JUNGLE_SET);
    public static final Block JUNGLE_WOOD_VERTICAL_SLAB = woodVerticalSlab(WoodBlockSets.JUNGLE_SET);
    public static final Block MANGROVE_CHAIR = WoodBlockSets.MANGROVE_SET.chair();
    public static final Block MANGROVE_STOOL = WoodBlockSets.MANGROVE_SET.stool();
    public static final Block MANGROVE_TABLE = WoodBlockSets.MANGROVE_SET.table();
    public static final Block MANGROVE_VERTICAL_SLAB = WoodBlockSets.MANGROVE_SET.verticalSlab();
    public static final Block MANGROVE_WOOD_SLAB = woodSlab(WoodBlockSets.MANGROVE_SET);
    public static final Block MANGROVE_WOOD_VERTICAL_SLAB = woodVerticalSlab(WoodBlockSets.MANGROVE_SET);
    public static final Block MAPLE_LEAVES = WoodBlockSets.MAPLE_SET.leaves();
    public static final Block MIRKWOOD_SPIDER_EGG = ModNatureBlocks.SHELOBITE_LARVA_EGG;
    public static final Block MORDOR_LICHEN = ModNatureBlocks.CORRUPTED_MOSS;
    public static final Block MOSSY_COBBLESTONE_VERTICAL_SLAB = StoneBlockSets.STONE_SET.mossyCobblestoneBlocks.verticalSlab();
    public static final Block MOSSY_STONE_BRICK_VERTICAL_SLAB = StoneBlockSets.STONE_SET.mossyBrickBlocks.verticalSlab();
    public static final Block OAK_CHAIR = WoodBlockSets.OAK_SET.chair();
    public static final Block OAK_STOOL = WoodBlockSets.OAK_SET.stool();
    public static final Block OAK_TABLE = WoodBlockSets.OAK_SET.table();
    public static final Block OAK_VERTICAL_SLAB = WoodBlockSets.OAK_SET.verticalSlab();
    public static final Block OAK_WOOD_SLAB = woodSlab(WoodBlockSets.OAK_SET);
    public static final Block OAK_WOOD_VERTICAL_SLAB = woodVerticalSlab(WoodBlockSets.OAK_SET);
    public static final Block ORC_STEEL_BLOCK = ModBlocks.BURZUM_STEEL_BLOCK;
    public static final Block POLISHED_ANDESITE_VERTICAL_SLAB = StoneBlockSets.ANDESITE_SET.polishedBlocks.verticalSlab();
    public static final Block POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB = StoneBlockSets.BLACKSTONE_SET.brickBlocks.verticalSlab();
    public static final Block POLISHED_BLACKSTONE_VERTICAL_SLAB = StoneBlockSets.BLACKSTONE_SET.polishedBlocks.verticalSlab();
    public static final Block POLISHED_DEEPSLATE_VERTICAL_SLAB = StoneBlockSets.DEEPSLATE_SET.polishedBlocks.verticalSlab();
    public static final Block POLISHED_DIORITE_VERTICAL_SLAB = StoneBlockSets.DIORITE_SET.polishedBlocks.verticalSlab();
    public static final Block POLISHED_GRANITE_VERTICAL_SLAB = StoneBlockSets.GRANITE_SET.polishedBlocks.verticalSlab();
    public static final Block REED_BLOCK = GenericBlockSets.REED.blockSet.base();
    public static final Block REED_SLAB = GenericBlockSets.REED.blockSet.slab();
    public static final Block REED_STAIRS = GenericBlockSets.REED.blockSet.stairs();
    public static final Block REED_VERTICAL_SLAB = GenericBlockSets.REED.blockSet.verticalSlab();
    public static final Block REED_WALL = GenericBlockSets.REED.blockSet.wall();
    public static final Block SILVERS_BARS = ModBlocks.BRONZE_BARS;
    public static final Block SMOOTH_BASALT_SLAB = StoneBlockSets.BASALT_SET.smoothBlocks.slab();
    public static final Block SMOOTH_BASALT_STAIRS = StoneBlockSets.BASALT_SET.smoothBlocks.stairs();
    public static final Block SMOOTH_BASALT_VERTICAL_SLAB = StoneBlockSets.BASALT_SET.smoothBlocks.verticalSlab();
    public static final Block SMOOTH_BASALT_WALL = StoneBlockSets.BASALT_SET.smoothBlocks.wall();
    public static final Block SMOOTH_STONE_VERTICAL_SLAB = StoneBlockSets.STONE_SET.smoothBlocks.verticalSlab();
    public static final Block SPRUCE_CHAIR = WoodBlockSets.SPRUCE_SET.chair();
    public static final Block SPRUCE_STOOL = WoodBlockSets.SPRUCE_SET.stool();
    public static final Block SPRUCE_TABLE = WoodBlockSets.SPRUCE_SET.table();
    public static final Block SPRUCE_VERTICAL_SLAB = WoodBlockSets.SPRUCE_SET.verticalSlab();
    public static final Block SPRUCE_WOOD_SLAB = woodSlab(WoodBlockSets.SPRUCE_SET);
    public static final Block SPRUCE_WOOD_VERTICAL_SLAB = woodVerticalSlab(WoodBlockSets.SPRUCE_SET);
    public static final Block STONE_BRICK_VERTICAL_SLAB = StoneBlockSets.STONE_SET.brickBlocks.verticalSlab();
    public static final Block STONE_TRAPDOOR = StoneBlockSets.STONE_SET.baseBlocks.trapdoor();
    public static final Block STONE_VERTICAL_SLAB = StoneBlockSets.STONE_SET.baseBlocks.verticalSlab();
    public static final Block STRAW_BLOCK = GenericBlockSets.STRAW.blockSet.base();
    public static final Block STRAW_SLAB = GenericBlockSets.STRAW.blockSet.slab();
    public static final Block STRAW_STAIRS = GenericBlockSets.STRAW.blockSet.stairs();
    public static final Block STRAW_VERTICAL_SLAB = GenericBlockSets.STRAW.blockSet.verticalSlab();
    public static final Block STRAW_WALL = GenericBlockSets.STRAW.blockSet.wall();
    public static final Block STRIPPED_ACACIA_WOOD_SLAB = strippedSlab(WoodBlockSets.ACACIA_SET);
    public static final Block STRIPPED_ACACIA_WOOD_STAIRS = strippedStairs(WoodBlockSets.ACACIA_SET);
    public static final Block STRIPPED_ACACIA_WOOD_VERTICAL_SLAB = strippedVerticalSlab(WoodBlockSets.ACACIA_SET);
    public static final Block STRIPPED_BAMBOO_WOOD_SLAB = strippedSlab(WoodBlockSets.BAMBOO);
    public static final Block STRIPPED_BAMBOO_WOOD_STAIRS = strippedStairs(WoodBlockSets.BAMBOO);
    public static final Block STRIPPED_BAMBOO_WOOD_VERTICAL_SLAB = strippedVerticalSlab(WoodBlockSets.BAMBOO);
    public static final Block STRIPPED_BIRCH_WOOD_SLAB = strippedSlab(WoodBlockSets.BIRCH_SET);
    public static final Block STRIPPED_BIRCH_WOOD_STAIRS = strippedStairs(WoodBlockSets.BIRCH_SET);
    public static final Block STRIPPED_BIRCH_WOOD_VERTICAL_SLAB = strippedVerticalSlab(WoodBlockSets.BIRCH_SET);
    public static final Block STRIPPED_CHERRY_WOOD_SLAB = strippedSlab(WoodBlockSets.CHERRY_SET);
    public static final Block STRIPPED_CHERRY_WOOD_STAIRS = strippedStairs(WoodBlockSets.CHERRY_SET);
    public static final Block STRIPPED_CHERRY_WOOD_VERTICAL_SLAB = strippedVerticalSlab(WoodBlockSets.CHERRY_SET);
    public static final Block STRIPPED_CRIMSON_WOOD_SLAB = strippedSlab(WoodBlockSets.CRIMSON_SET);
    public static final Block STRIPPED_CRIMSON_WOOD_STAIRS = strippedStairs(WoodBlockSets.CRIMSON_SET);
    public static final Block STRIPPED_CRIMSON_WOOD_VERTICAL_SLAB = strippedVerticalSlab(WoodBlockSets.CRIMSON_SET);
    public static final Block STRIPPED_DARK_OAK_WOOD_SLAB = strippedSlab(WoodBlockSets.DAK_OAK_SET);
    public static final Block STRIPPED_DARK_OAK_WOOD_STAIRS = strippedStairs(WoodBlockSets.DAK_OAK_SET);
    public static final Block STRIPPED_DARK_OAK_WOOD_VERTICAL_SLAB = strippedVerticalSlab(WoodBlockSets.DAK_OAK_SET);
    public static final Block STRIPPED_JUNGLE_WOOD_SLAB = strippedSlab(WoodBlockSets.JUNGLE_SET);
    public static final Block STRIPPED_JUNGLE_WOOD_STAIRS = strippedStairs(WoodBlockSets.JUNGLE_SET);
    public static final Block STRIPPED_JUNGLE_WOOD_VERTICAL_SLAB = strippedVerticalSlab(WoodBlockSets.JUNGLE_SET);
    public static final Block STRIPPED_MANGROVE_WOOD_SLAB = strippedSlab(WoodBlockSets.MANGROVE_SET);
    public static final Block STRIPPED_MANGROVE_WOOD_STAIRS = strippedStairs(WoodBlockSets.MANGROVE_SET);
    public static final Block STRIPPED_MANGROVE_WOOD_VERTICAL_SLAB = strippedVerticalSlab(WoodBlockSets.MANGROVE_SET);
    public static final Block STRIPPED_OAK_WOOD_SLAB = strippedSlab(WoodBlockSets.OAK_SET);
    public static final Block STRIPPED_OAK_WOOD_STAIRS = strippedStairs(WoodBlockSets.OAK_SET);
    public static final Block STRIPPED_OAK_WOOD_VERTICAL_SLAB = strippedVerticalSlab(WoodBlockSets.OAK_SET);
    public static final Block STRIPPED_SPRUCE_WOOD_SLAB = strippedSlab(WoodBlockSets.SPRUCE_SET);
    public static final Block STRIPPED_SPRUCE_WOOD_STAIRS = strippedStairs(WoodBlockSets.SPRUCE_SET);
    public static final Block STRIPPED_SPRUCE_WOOD_VERTICAL_SLAB = strippedVerticalSlab(WoodBlockSets.SPRUCE_SET);
    public static final Block STRIPPED_WARPED_WOOD_SLAB = strippedSlab(WoodBlockSets.WARPED_SET);
    public static final Block STRIPPED_WARPED_WOOD_STAIRS = strippedStairs(WoodBlockSets.WARPED_SET);
    public static final Block STRIPPED_WARPED_WOOD_VERTICAL_SLAB = strippedVerticalSlab(WoodBlockSets.WARPED_SET);
    public static final Block TUFF_SLAB = StoneBlockSets.TUFF_SET.baseBlocks.slab();
    public static final Block TUFF_STAIRS = StoneBlockSets.TUFF_SET.baseBlocks.stairs();
    public static final Block TUFF_VERTICAL_SLAB = StoneBlockSets.TUFF_SET.baseBlocks.verticalSlab();
    public static final Block TUFF_WALL = StoneBlockSets.TUFF_SET.baseBlocks.wall();
    public static final Block URUK_STEEL_BLOCK = ModBlocks.BURZUM_STEEL_BLOCK;
    public static final Block WARPED_VERTICAL_SLAB = WoodBlockSets.WARPED_SET.verticalSlab();
    public static final Block WARPED_WOOD_SLAB = woodSlab(WoodBlockSets.WARPED_SET);
    public static final Block WARPED_WOOD_VERTICAL_SLAB = woodVerticalSlab(WoodBlockSets.WARPED_SET);

    // Some wood sets (bamboo) have no log/stripped-log blocks; fall back to planks so
    // these legacy datagen aliases never NPE during class init.
    private static Block woodSlab(WoodBlockSetBuilder set) {
        if (set.logBlocks != null) return set.logBlocks.slab();
        return set.planksBlocks != null ? set.planksBlocks.slab() : net.minecraft.world.level.block.Blocks.AIR;
    }

    private static Block woodVerticalSlab(WoodBlockSetBuilder set) {
        if (set.logBlocks != null) return set.logBlocks.verticalSlab();
        return set.planksBlocks != null ? set.planksBlocks.verticalSlab() : net.minecraft.world.level.block.Blocks.AIR;
    }

    private static Block strippedSlab(WoodBlockSetBuilder set) {
        if (set.strippedLogBlocks != null) return set.strippedLogBlocks.slab();
        return set.planksBlocks != null ? set.planksBlocks.slab() : net.minecraft.world.level.block.Blocks.AIR;
    }

    private static Block strippedVerticalSlab(WoodBlockSetBuilder set) {
        if (set.strippedLogBlocks != null) return set.strippedLogBlocks.verticalSlab();
        return set.planksBlocks != null ? set.planksBlocks.verticalSlab() : net.minecraft.world.level.block.Blocks.AIR;
    }

    private static Block strippedStairs(WoodBlockSetBuilder set) {
        if (set.strippedLogBlocks != null) return set.strippedLogBlocks.stairs();
        return set.planksBlocks != null ? set.planksBlocks.stairs() : net.minecraft.world.level.block.Blocks.AIR;
    }

}
