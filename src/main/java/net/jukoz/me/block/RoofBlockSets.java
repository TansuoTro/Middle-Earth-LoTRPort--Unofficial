package net.jukoz.me.block;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.fabricmc.fabric.api.registry.OxidizableBlocksRegistry;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlocks;
import net.jukoz.me.block.registration.StoneBlockSets;
import net.jukoz.me.block.registration.WoodBlockSets;
import net.jukoz.me.block.special.OxidizableVerticalSlabBlock;
import net.jukoz.me.block.special.OxidizableWallBlock;
import net.jukoz.me.block.special.verticalSlabs.VerticalSlabBlock;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.block.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class RoofBlockSets {

    public static RoofBlockSet OAK_SHINGLES = registerWoodSet("oak_shingles", Blocks.OAK_PLANKS);
    public static RoofBlockSet SPRUCE_SHINGLES = registerWoodSet("spruce_shingles", Blocks.SPRUCE_PLANKS);
    public static RoofBlockSet BIRCH_SHINGLES = registerWoodSet("birch_shingles", Blocks.BIRCH_PLANKS);
    public static RoofBlockSet JUNGLE_SHINGLES = registerWoodSet("jungle_shingles", Blocks.JUNGLE_PLANKS);
    public static RoofBlockSet ACACIA_SHINGLES = registerWoodSet("acacia_shingles", Blocks.ACACIA_PLANKS);
    public static RoofBlockSet DARK_OAK_SHINGLES = registerWoodSet("dark_oak_shingles", Blocks.ACACIA_PLANKS);
    public static RoofBlockSet MANGROVE_SHINGLES = registerWoodSet("mangrove_shingles", Blocks.MANGROVE_PLANKS);
    public static RoofBlockSet CHERRY_SHINGLES = registerWoodSet("cherry_shingles", Blocks.CHERRY_PLANKS);
    public static RoofBlockSet BAMBOO_SHINGLES = registerWoodSet("bamboo_shingles", Blocks.BAMBOO_PLANKS);
    public static RoofBlockSet CRIMSON_SHINGLES = registerWoodSet("crimson_shingles", Blocks.CRIMSON_PLANKS);
    public static RoofBlockSet WARPED_SHINGLES = registerWoodSet("warped_shingles", Blocks.WARPED_PLANKS);

    public static RoofBlockSet BEECH_SHINGLES = registerWoodSet("beech_shingles", WoodBlockSets.BEECH_SET.planksBlocks.base());
    public static RoofBlockSet LARCH_SHINGLES = registerWoodSet("larch_shingles", WoodBlockSets.LARCH_SET.planksBlocks.base());
    public static RoofBlockSet BLACK_LEBETHRON_SHINGLES = registerWoodSet("black_lebethron_shingles", WoodBlockSets.BLACK_LEBETHRON_SET.planksBlocks.base());
    public static RoofBlockSet WHITE_LEBETHRON_SHINGLES = registerWoodSet("white_lebethron_shingles", WoodBlockSets.WHITE_LEBETHRON_SET.planksBlocks.base());
    public static RoofBlockSet MALLORN_SHINGLES = registerWoodSet("mallorn_shingles", WoodBlockSets.MALLORN_SET.planksBlocks.base());
    public static RoofBlockSet MAPLE_SHINGLES = registerWoodSet("maple_shingles", WoodBlockSets.MAPLE_SET.planksBlocks.base());
    public static RoofBlockSet SILVER_MAPLE_SHINGLES = registerWoodSet("silver_maple_shingles", WoodBlockSets.SILVER_MAPLE_SET.planksBlocks.base());
    public static RoofBlockSet MIRKWOOD_SHINGLES = registerWoodSet("mirkwood_shingles", WoodBlockSets.MIRKWOOD_SET.planksBlocks.base());
    public static RoofBlockSet PALM_SHINGLES = registerWoodSet("palm_shingles", WoodBlockSets.PALM_SET.planksBlocks.base());
    public static RoofBlockSet PINE_SHINGLES = registerWoodSet("pine_shingles", WoodBlockSets.PINE_SET.planksBlocks.base());
    public static RoofBlockSet WILLOW_SHINGLES = registerWoodSet("willow_shingles", WoodBlockSets.WILLOW_SET.planksBlocks.base());
    public static RoofBlockSet GRAY_MUSHROOM_SHINGLES = registerWoodSet("gray_mushroom_shingles", MushroomBlockSets.GRAY_MUSHROOM.planks());
    public static RoofBlockSet DARK_MUSHROOM_SHINGLES = registerWoodSet("dark_mushroom_shingles", MushroomBlockSets.DARK_MUSHROOM.planks());
    public static RoofBlockSet MUSHROOM_SHINGLES = registerWoodSet("mushroom_shingles", MushroomBlockSets.MUSHROOM.planks());

    public static RoofBlockSet CHARRED_SHINGLES = registerWoodSet("charred_shingles", null);
    public static RoofBlockSet WEATHERED_SHINGLES = registerWoodSet("weathered_shingles", null);

    public static RoofBlockSet BRICK_CLAY_TILING = registerClaySet("brick_clay_tiling", Blocks.BRICKS);

    public static RoofBlockSet BLACK_CLAY_TILING = registerClaySet("black_clay_tiling", Blocks.BLACK_TERRACOTTA);
    public static RoofBlockSet BLUE_CLAY_TILING = registerClaySet("blue_clay_tiling", Blocks.BLUE_TERRACOTTA);
    public static RoofBlockSet BROWN_CLAY_TILING = registerClaySet("brown_clay_tiling", Blocks.BROWN_TERRACOTTA);
    public static RoofBlockSet CYAN_CLAY_TILING = registerClaySet("cyan_clay_tiling", Blocks.CYAN_TERRACOTTA);
    public static RoofBlockSet GRAY_CLAY_TILING = registerClaySet("gray_clay_tiling", Blocks.GRAY_TERRACOTTA);
    public static RoofBlockSet GREEN_CLAY_TILING = registerClaySet("green_clay_tiling", Blocks.GREEN_TERRACOTTA);
    public static RoofBlockSet LIGHT_BLUE_CLAY_TILING = registerClaySet("light_blue_clay_tiling", Blocks.LIGHT_BLUE_TERRACOTTA);
    public static RoofBlockSet LIGHT_GRAY_CLAY_TILING = registerClaySet("light_gray_clay_tiling", Blocks.LIGHT_GRAY_TERRACOTTA);
    public static RoofBlockSet LIME_TILING = registerClaySet("lime_clay_tiling", Blocks.LIME_TERRACOTTA);
    public static RoofBlockSet MAGENTA_CLAY_TILING = registerClaySet("magenta_clay_tiling", Blocks.MAGENTA_TERRACOTTA);
    public static RoofBlockSet ORANGE_CLAY_TILING = registerClaySet("orange_clay_tiling", Blocks.ORANGE_TERRACOTTA);
    public static RoofBlockSet PINK_CLAY_TILING = registerClaySet("pink_clay_tiling", Blocks.PINK_TERRACOTTA);
    public static RoofBlockSet PURPLE_CLAY_TILING = registerClaySet("purple_clay_tiling", Blocks.PURPLE_TERRACOTTA);
    public static RoofBlockSet RED_CLAY_TILING = registerClaySet("red_clay_tiling", Blocks.RED_TERRACOTTA);
    public static RoofBlockSet WHITE_CLAY_TILING = registerClaySet("white_clay_tiling", Blocks.WHITE_TERRACOTTA);
    public static RoofBlockSet YELLOW_CLAY_TILING = registerClaySet("yellow_clay_tiling", Blocks.YELLOW_TERRACOTTA);

    public static RoofBlockSet THATCH = registerThatchSet("thatch", Oxidizable.OxidationLevel.UNAFFECTED);
    public static RoofBlockSet WEATHERED_THATCH = registerThatchSet("weathered_thatch", Oxidizable.OxidationLevel.EXPOSED);
    public static RoofBlockSet AGED_THATCH = registerThatchSet("aged_thatch", Oxidizable.OxidationLevel.WEATHERED);
    public static RoofBlockSet OLD_THATCH = registerThatchSet("old_thatch", Oxidizable.OxidationLevel.OXIDIZED);

    public static RoofBlockSet WAXED_THATCH = registerWaxedThatchSet("waxed_thatch");
    public static RoofBlockSet WAXED_WEATHERED_THATCH = registerWaxedThatchSet("waxed_weathered_thatch");
    public static RoofBlockSet WAXED_AGED_THATCH = registerWaxedThatchSet("waxed_aged_thatch");
    public static RoofBlockSet WAXED_OLD_THATCH = registerWaxedThatchSet("waxed_old_thatch");

    public static RoofBlockSet[] sets = new RoofBlockSet[] {
            THATCH,
            WEATHERED_THATCH,
            AGED_THATCH,
            OLD_THATCH,

            WAXED_THATCH,
            WAXED_WEATHERED_THATCH,
            WAXED_AGED_THATCH,
            WAXED_OLD_THATCH,

            OAK_SHINGLES,
            SPRUCE_SHINGLES,
            BIRCH_SHINGLES,
            JUNGLE_SHINGLES,
            ACACIA_SHINGLES,
            DARK_OAK_SHINGLES,
            MANGROVE_SHINGLES,
            CHERRY_SHINGLES,
            BAMBOO_SHINGLES,
            CRIMSON_SHINGLES,
            WARPED_SHINGLES,

            BEECH_SHINGLES,
            LARCH_SHINGLES,
            BLACK_LEBETHRON_SHINGLES,
            WHITE_LEBETHRON_SHINGLES,
            MALLORN_SHINGLES,
            MAPLE_SHINGLES,
            SILVER_MAPLE_SHINGLES,
            MIRKWOOD_SHINGLES,
            PALM_SHINGLES,
            PINE_SHINGLES,
            WILLOW_SHINGLES,
            DARK_MUSHROOM_SHINGLES,
            GRAY_MUSHROOM_SHINGLES,
            MUSHROOM_SHINGLES,

            CHARRED_SHINGLES,
            WEATHERED_SHINGLES,

            BRICK_CLAY_TILING,

            BLACK_CLAY_TILING,
            BLUE_CLAY_TILING,
            BROWN_CLAY_TILING,
            CYAN_CLAY_TILING,
            GRAY_CLAY_TILING,
            GREEN_CLAY_TILING,
            LIGHT_BLUE_CLAY_TILING,
            LIGHT_GRAY_CLAY_TILING,
            LIME_TILING,
            MAGENTA_CLAY_TILING,
            ORANGE_CLAY_TILING,
            PINK_CLAY_TILING,
            PURPLE_CLAY_TILING,
            RED_CLAY_TILING,
            WHITE_CLAY_TILING,
            YELLOW_CLAY_TILING,
    };

    public record RoofBlockSet(Block block, Block slab, Block verticalSlab, Block stairs, Block wall, Block origin) {
    }

    /** RoofBlockSets is a port-only duplicate of WoodBlockSets / GenericBlockSets: most of its ids
     *  are already registered. Look them up instead of building throwaway Block instances that never
     *  reach the registry (those leave unbound intrusive holders behind). */
    private static Block existingBlock(String path) {
        Block block = Registries.BLOCK.get(new Identifier(MiddleEarth.MOD_ID, path));
        return block == Blocks.AIR ? null : block;
    }

    private static RoofBlockSet registerWoodSet(String name, Block origin) {
        Block block = existingBlock(name);
        if (block == null) {
            block = ModBlocks.registerWoodBlock(name,
                    new Block(AbstractBlock.Settings.copy(origin == null ? Blocks.OAK_PLANKS : origin)), origin == null);
        }

        Block slab = existingBlock(name + "_slab");
        if (slab == null) {
            slab = ModBlocks.registerWoodBlock(name + "_slab", new SlabBlock(FabricBlockSettings.copyOf(block)), true);
        }

        Block verticalSlab = existingBlock(name + "_vertical_slab");
        if (verticalSlab == null) {
            verticalSlab = ModBlocks.registerWoodBlock(name + "_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(block)), true);
        }

        Block stairs = existingBlock(name + "_stairs");
        if (stairs == null) {
            stairs = ModBlocks.registerWoodBlock(name + "_stairs", new StairsBlock(block.getDefaultState(),
                    FabricBlockSettings.copyOf(block)), true);
        }

        Block wall = existingBlock(name + "_wall");
        if (wall == null) {
            wall = ModBlocks.registerWoodBlock(name + "_wall", new WallBlock(AbstractBlock.Settings.copy(block)), true);
        }

        FlammableBlockRegistry.getDefaultInstance().add(block, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(slab, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(verticalSlab, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(stairs, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(wall, 5, 20);

        FuelRegistry registry = FuelRegistry.INSTANCE;
        registry.add(block, 300);
        registry.add(slab, 150);
        registry.add(verticalSlab, 150);
        registry.add(stairs, 300);
        registry.add(wall, 300);

        return new RoofBlockSet(block, slab, verticalSlab, stairs, wall, origin);
    }

    private static RoofBlockSet registerClaySet(String name, Block origin) {
        Block block = existingBlock(name);
        if (block == null) {
            block = ModBlocks.registerStoneBlock(name,
                    new Block(AbstractBlock.Settings.copy(origin == null ? Blocks.TERRACOTTA : origin).requiresTool()), true);
        }

        Block slab = existingBlock(name + "_slab");
        if (slab == null) {
            slab = ModBlocks.registerStoneBlock(name + "_slab", new SlabBlock(FabricBlockSettings.copyOf(block).requiresTool()), true);
        }

        Block verticalSlab = existingBlock(name + "_vertical_slab");
        if (verticalSlab == null) {
            verticalSlab = ModBlocks.registerStoneBlock(name + "_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(block).requiresTool()), true);
        }

        Block stairs = existingBlock(name + "_stairs");
        if (stairs == null) {
            stairs = ModBlocks.registerStoneBlock(name + "_stairs", new StairsBlock(block.getDefaultState(),
                    FabricBlockSettings.copyOf(block).requiresTool()), true);
        }

        Block wall = existingBlock(name + "_wall");
        if (wall == null) {
            wall = ModBlocks.registerStoneBlock(name + "_wall", new WallBlock(AbstractBlock.Settings.copy(block).requiresTool()), true);
        }

        return new RoofBlockSet(block, slab, verticalSlab, stairs, wall, origin);
    }

    private static RoofBlockSet registerThatchSet(String name, Oxidizable.OxidationLevel level) {
        Block block = existingBlock(name);
        if (block == null) {
            block = ModBlocks.registerMiscBlock(name, new OxidizableBlock(level, AbstractBlock.Settings.copy(Blocks.HAY_BLOCK)), true);
        }

        Block slab = existingBlock(name + "_slab");
        if (slab == null) {
            slab = ModBlocks.registerMiscBlock(name + "_slab", new OxidizableSlabBlock(level, FabricBlockSettings.copyOf(block)), true);
        }

        Block verticalSlab = existingBlock(name + "_vertical_slab");
        if (verticalSlab == null) {
            verticalSlab = ModBlocks.registerMiscBlock(name + "_vertical_slab", new OxidizableVerticalSlabBlock(level, AbstractBlock.Settings.copy(block)), true);
        }

        Block stairs = existingBlock(name + "_stairs");
        if (stairs == null) {
            stairs = ModBlocks.registerMiscBlock(name + "_stairs", new OxidizableStairsBlock(level, block.getDefaultState(),
                    FabricBlockSettings.copyOf(block)), true);
        }

        Block wall = existingBlock(name + "_wall");
        if (wall == null) {
            wall = ModBlocks.registerMiscBlock(name + "_wall", new OxidizableWallBlock(level, AbstractBlock.Settings.copy(block)), true);
        }

        FlammableBlockRegistry.getDefaultInstance().add(block, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(slab, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(verticalSlab, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(stairs, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(wall, 5, 60);

        return new RoofBlockSet(block, slab, verticalSlab, stairs, wall, null);
    }

    private static RoofBlockSet registerWaxedThatchSet(String name) {
        Block block = existingBlock(name);
        if (block == null) {
            block = ModBlocks.registerMiscBlock(name, new Block(AbstractBlock.Settings.copy(Blocks.HAY_BLOCK)), true);
        }

        Block slab = existingBlock(name + "_slab");
        if (slab == null) {
            slab = ModBlocks.registerMiscBlock(name + "_slab", new SlabBlock(FabricBlockSettings.copyOf(block)), true);
        }

        Block verticalSlab = existingBlock(name + "_vertical_slab");
        if (verticalSlab == null) {
            verticalSlab = ModBlocks.registerMiscBlock(name + "_vertical_slab", new VerticalSlabBlock(AbstractBlock.Settings.copy(block)), true);
        }

        Block stairs = existingBlock(name + "_stairs");
        if (stairs == null) {
            stairs = ModBlocks.registerMiscBlock(name + "_stairs", new StairsBlock(block.getDefaultState(),
                    FabricBlockSettings.copyOf(block)), true);
        }

        Block wall = existingBlock(name + "_wall");
        if (wall == null) {
            wall = ModBlocks.registerMiscBlock(name + "_wall", new WallBlock(AbstractBlock.Settings.copy(block)), true);
        }

        FlammableBlockRegistry.getDefaultInstance().add(block, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(slab, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(verticalSlab, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(stairs, 5, 60);
        FlammableBlockRegistry.getDefaultInstance().add(wall, 5, 60);

        return new RoofBlockSet(block, slab, verticalSlab, stairs, wall, null);
    }

    public static void registerModBlockSets() {
        LoggerUtil.getInstance().logDebugMsg("Registering WoodBlockSets for " + MiddleEarth.MOD_ID);
    }
}
