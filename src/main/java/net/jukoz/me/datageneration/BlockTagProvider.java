package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.*;
import net.jukoz.me.datageneration.content.models.*;
import net.jukoz.me.datageneration.content.tags.*;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.RegistryAccess;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;

public class BlockTagProvider extends FabricTagProvider.BlockTagProvider {

    public BlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider arg) {
        var mineablePickaxe = tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "mineable/pickaxe")));
        var mineableAxe = tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "mineable/axe")));
        var mineableShovel = tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "mineable/shovel")));
        var mineableHoe = tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "mineable/hoe")));
        var swordEfficient = tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "sword_efficient")));

        var needsStoneTools = tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "needs_stone_tool")));
        var needsIronTools = tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "needs_iron_tool")));
        var needsDiamondTools = tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "needs_diamond_tool")));
        var needsNetheriteTools = tag(TagKey.create(Registries.BLOCK, new ResourceLocation("fabric", "needs_tool_level_4")));

        var baseStoneOverworld = tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "base_stone_overworld")));

        var seat = tag(TagKey.create(Registries.BLOCK, new ResourceLocation(MiddleEarth.MOD_ID, "seat")));
        var table = tag(TagKey.create(Registries.BLOCK, new ResourceLocation(MiddleEarth.MOD_ID, "table")));

        var leaves = tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "leaves")));

        var cobwebs = tag(TagKey.create(Registries.BLOCK, new ResourceLocation("me", "cobwebs")));

        mineableAxe.add(notNullBlocks(MineableAxe.blocks));
        mineablePickaxe.add(notNullBlocks(MineablePickaxe.blocks));

        leaves.add(notNullBlocks(LeavesSets.blocks));
        mineableHoe.add(notNullBlocks(MineableHoe.blocks));

        swordEfficient.add(notNullBlocks(LeavesSets.blocks));

        tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "saplings"))).add(notNullBlocks(Saplings.saplings));
        tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "doors"))).add(notNullBlocks(Doors.doors));
        tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "trapdoors"))).add(notNullBlocks(Trapdoors.trapdoors));
        tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "buttons"))).add(notNullBlocks(Buttons.buttons));
        tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "fences"))).add(notNullBlocks(Fences.fences));
        tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "wooden_fences"))).add(notNullBlocks(Fences.fences));
        tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "fence_gates"))).add(notNullBlocks(FenceGates.fenceGates));
        tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "logs"))).add(notNullBlocks(Logs.logs));
        tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "pressure_plates"))).add(notNullBlocks(PressurePlates.pressurePlates));
        tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "walls"))).add(notNullBlocks(Walls.walls));
        tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "planks"))).add(notNullBlocks(Planks.planks));
        tag(TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "crops"))).add(notNullBlocks(Crops.crops));

        //Ores
        TagKey<Block> iron_ores = TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "iron_ores"));
        TagKey<Block> gold_ores = TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "gold_ores"));
        TagKey<Block> copper_ores = TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "copper_ores"));
        TagKey<Block> coal_ores = TagKey.create(Registries.BLOCK, new ResourceLocation("minecraft", "coal_ores"));

        TagKey<Block> tin_ores = TagKey.create(Registries.BLOCK, new ResourceLocation("me", "tin_ores"));
        TagKey<Block> lead_ores = TagKey.create(Registries.BLOCK, new ResourceLocation("me", "lead_ores"));
        TagKey<Block> silver_ores = TagKey.create(Registries.BLOCK, new ResourceLocation("me", "silver_ores"));
        TagKey<Block> mithril_ores = TagKey.create(Registries.BLOCK, new ResourceLocation("me", "mithril_ores"));

        for (OreRockSets.OreRockSet set : OreRockSets.sets) {
            if(set.coal_ore() != null) {
                tag(coal_ores)
                        .add(set.coal_ore());
            }
            if(set.copper_ore() != null) {
                tag(copper_ores)
                        .add(set.copper_ore());
            }
            if(set.tin_ore() != null) {
                tag(tin_ores)
                        .add(set.tin_ore());
            }
            if(set.lead_ore() != null) {
                tag(lead_ores)
                        .add(set.lead_ore());
            }
            if(set.silver_ore() != null) {
                tag(silver_ores)
                        .add(set.silver_ore());
            }
            if(set.gold_ore() != null) {
                tag(gold_ores)
                        .add(set.gold_ore());
            }
            if(set.iron_ore() != null) {
                tag(iron_ores)
                        .add(set.iron_ore());
            }
            if(set.mithril_ore() != null) {
                tag(mithril_ores)
                        .add(set.mithril_ore());
            }
        }

        for (StoneBlockSets.SimpleBlockSet record : StoneBlockSets.sets) {
            if (Registries.BLOCK.getId(record.base()).getPath().contains("diftomin")){
                needsIronTools.add(record.base());
                needsIronTools.add(record.slab());
                needsIronTools.add(record.verticalSlab());
                needsIronTools.add(record.stairs());
                needsIronTools.add(record.wall());
            }else if (Registries.BLOCK.getId(record.base()).getPath().contains("epmosto")){
                needsDiamondTools.add(record.base());
                needsDiamondTools.add(record.slab());
                needsDiamondTools.add(record.verticalSlab());
                needsDiamondTools.add(record.stairs());
                needsDiamondTools.add(record.wall());
            } else {
                needsStoneTools.add(record.base());
                needsStoneTools.add(record.slab());
                needsStoneTools.add(record.verticalSlab());
                needsStoneTools.add(record.stairs());
                needsStoneTools.add(record.wall());
            }
        }

        baseStoneOverworld.add(Blocks.CALCITE);
        for (StoneBlockSets.SimpleBlockSetMain record : StoneBlockSets.setsMain) {
            if(record.base() != StoneBlockSets.ASHENSTONE_SET.base()) baseStoneOverworld.add(record.base());
            if (Registries.BLOCK.getId(record.base()).getPath().contains("diftomin")){
                needsIronTools.add(record.base());
                needsIronTools.add(record.slab());
                needsIronTools.add(record.verticalSlab());
                needsIronTools.add(record.stairs());
                needsIronTools.add(record.wall());
                needsIronTools.add(record.stool());
                needsIronTools.add(record.table());
            }else if (Registries.BLOCK.getId(record.base()).getPath().contains("epmosto")){
                needsDiamondTools.add(record.base());
                needsDiamondTools.add(record.slab());
                needsDiamondTools.add(record.verticalSlab());
                needsDiamondTools.add(record.stairs());
                needsDiamondTools.add(record.wall());
                needsDiamondTools.add(record.stool());
                needsDiamondTools.add(record.table());
            } else {
                needsStoneTools.add(record.base());
                needsStoneTools.add(record.slab());
                needsStoneTools.add(record.verticalSlab());
                needsStoneTools.add(record.stairs());
                needsStoneTools.add(record.wall());
                needsStoneTools.add(record.stool());
                needsStoneTools.add(record.table());
            }
        }

        cobwebs.add(Blocks.COBWEB);
        cobwebs.add(net.jukoz.me.compat.MEBlocksLegacy.HANGING_COBWEB);
        cobwebs.add(ModNatureBlocks.CORNER_COBWEB);

        java.util.Arrays.stream(notNullBlocks(SimpleStoneStoolModel.stools)).forEach(seat::add);
        SimpleStoneStoolModel.vanillaStools.forEach(block -> {
            if (block.base() != null) seat.add(block.base());
        });
        java.util.Arrays.stream(notNullBlocks(SimpleStoneChairModel.chairs)).forEach(seat::add);
        SimpleStoneChairModel.vanillaChairs.forEach(block -> {
            if (block.base() != null) seat.add(block.base());
        });
        java.util.Arrays.stream(notNullBlocks(SimpleStoneTableModel.tables)).forEach(table::add);
        SimpleStoneTableModel.vanillaTables.forEach(block -> {
            if (block.base() != null) table.add(block.base());
        });
        java.util.Arrays.stream(notNullBlocks(SimpleWoodStoolModel.stools)).forEach(seat::add);
        SimpleWoodStoolModel.vanillaStools.forEach(block -> {
            if (block.base() != null) seat.add(block.base());
        });
        java.util.Arrays.stream(notNullBlocks(SimpleWoodChairModel.chairs)).forEach(seat::add);
        SimpleWoodChairModel.vanillaChairs.forEach(block -> {
            if (block.base() != null) seat.add(block.base());
        });
        java.util.Arrays.stream(notNullBlocks(SimpleWoodTableModel.tables)).forEach(table::add);
        SimpleWoodTableModel.vanillaTables.forEach(block -> {
            if (block.base() != null) table.add(block.base());
        });

        needsStoneTools.add(OreRockSets.GONLUIN.copper_ore());
        needsStoneTools.add(OreRockSets.GONLUIN.tin_ore());

        needsStoneTools.add(OreRockSets.FROZEN.copper_ore());

        needsStoneTools.add(OreRockSets.ASHEN.copper_ore());
        needsStoneTools.add(OreRockSets.ASHEN.tin_ore());

        needsStoneTools.add(OreRockSets.LIMESTONE.copper_ore());
        needsStoneTools.add(OreRockSets.LIMESTONE.tin_ore());

        needsStoneTools.add(OreRockSets.CALCITE.copper_ore());
        needsStoneTools.add(OreRockSets.CALCITE.tin_ore());

        needsStoneTools.add(OreRockSets.STONE.tin_ore());

        needsStoneTools.add(OreRockSets.DEEPSLATE.tin_ore());
        needsStoneTools.add(OreRockSets.DEEPSLATE.lead_ore());

        needsStoneTools.add(OreRockSets.DEEPSLATE.lead_ore());

        needsIronTools.add(OreRockSets.DIFTOMIN.tin_ore());
        needsIronTools.add(OreRockSets.DIFTOMIN.lead_ore());
        needsIronTools.add(OreRockSets.DIFTOMIN.silver_ore());
        needsIronTools.add(OreRockSets.DIFTOMIN.gold_ore());
        needsIronTools.add(OreRockSets.DIFTOMIN.iron_ore());

        needsDiamondTools.add(OreRockSets.EPMOSTO.lead_ore());
        needsDiamondTools.add(OreRockSets.EPMOSTO.silver_ore());
        needsDiamondTools.add(OreRockSets.EPMOSTO.gold_ore());
        needsDiamondTools.add(OreRockSets.EPMOSTO.iron_ore());
        needsDiamondTools.add(OreRockSets.EPMOSTO.mithril_ore());

        needsStoneTools.add(ModBlocks.BRONZE_BLOCK);
        needsStoneTools.add(net.jukoz.me.compat.MEBlocksLegacy.ORC_STEEL_BLOCK);
        needsIronTools.add(ModBlocks.STEEL_BLOCK);
        needsIronTools.add(net.jukoz.me.compat.MEBlocksLegacy.DWARVEN_STEEL_BLOCK);
        needsIronTools.add(net.jukoz.me.compat.MEBlocksLegacy.ELVEN_STEEL_BLOCK);
        needsIronTools.add(net.jukoz.me.compat.MEBlocksLegacy.URUK_STEEL_BLOCK);

        mineablePickaxe.add(ModBlocks.STONE_MYCELIUM);
        mineableShovel.add(ModBlocks.ASH_BLOCK);
        mineableShovel.add(ModBlocks.RIVER_SAND);
        mineableShovel.add(ModBlocks.BLACK_SAND);
        mineableShovel.add(ModBlocks.WHITE_SAND);

        mineablePickaxe.add(ModDecorativeBlocks.DWARVEN_LANTERN);
        mineablePickaxe.add(ModDecorativeBlocks.SILVER_LANTERN);

        mineableShovel.add(ModBlocks.DRY_DIRT);
        mineableShovel.add(ModBlocks.DRY_DIRT_SLAB);
        mineableShovel.add(ModBlocks.DRY_DIRT_STAIRS);
        mineableShovel.add(ModBlocks.ASHEN_DIRT);
        mineableShovel.add(ModBlocks.ASHEN_DIRT_SLAB);
        mineableShovel.add(ModBlocks.ASHEN_DIRT_STAIRS);

        mineableHoe.add(net.jukoz.me.compat.MEBlocksLegacy.REED_BLOCK);
        mineableHoe.add(net.jukoz.me.compat.MEBlocksLegacy.REED_SLAB);
        mineableHoe.add(net.jukoz.me.compat.MEBlocksLegacy.REED_VERTICAL_SLAB);
        mineableHoe.add(net.jukoz.me.compat.MEBlocksLegacy.REED_STAIRS);
        mineableHoe.add(net.jukoz.me.compat.MEBlocksLegacy.REED_WALL);
        mineableHoe.add(net.jukoz.me.compat.MEBlocksLegacy.STRAW_BLOCK);
        mineableHoe.add(net.jukoz.me.compat.MEBlocksLegacy.STRAW_SLAB);
        mineableHoe.add(net.jukoz.me.compat.MEBlocksLegacy.STRAW_VERTICAL_SLAB);
        mineableHoe.add(net.jukoz.me.compat.MEBlocksLegacy.STRAW_STAIRS);
        mineableHoe.add(net.jukoz.me.compat.MEBlocksLegacy.STRAW_WALL);

        mineableAxe.add(ModDecorativeBlocks.WOOD_PILE);
        mineableAxe.add(ModDecorativeBlocks.ARTISAN_TABLE);

        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.STONE_VERTICAL_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.COBBLESTONE_VERTICAL_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.MOSSY_COBBLESTONE_VERTICAL_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.SMOOTH_STONE_VERTICAL_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.STONE_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.MOSSY_STONE_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.GRANITE_VERTICAL_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.POLISHED_GRANITE_VERTICAL_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.DIORITE_VERTICAL_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.POLISHED_DIORITE_VERTICAL_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.ANDESITE_VERTICAL_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.POLISHED_ANDESITE_VERTICAL_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.COBBLED_DEEPSLATE_VERTICAL_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.POLISHED_DEEPSLATE_VERTICAL_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.DEEPSLATE_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.DEEPSLATE_TILE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.MUD_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.SANDSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.SMOOTH_SANDSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.CUT_SANDSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.RED_SANDSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.SMOOTH_RED_SANDSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.CUT_RED_SANDSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.PRISMARINE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.PRISMARINE_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.DARK_PRISMARINE_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.NETHER_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.RED_NETHER_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(StoneBlockSets.BLACKSTONE_SET.baseBlocks.verticalSlab());
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.POLISHED_BLACKSTONE_VERTICAL_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.POLISHED_BLACKSTONE_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.END_STONE_BRICK_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.PURPUR_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.QUARTZ_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.SMOOTH_QUARTZ_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.EXPOSED_CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.WEATHERED_CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.OXIDIZED_CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.WAXED_CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB);

        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.OAK_WOOD_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.SPRUCE_WOOD_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.BIRCH_WOOD_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.JUNGLE_WOOD_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.ACACIA_WOOD_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.DARK_OAK_WOOD_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.MANGROVE_WOOD_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.CHERRY_WOOD_SLAB);

        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_OAK_WOOD_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_SPRUCE_WOOD_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_BIRCH_WOOD_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_JUNGLE_WOOD_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_ACACIA_WOOD_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_DARK_OAK_WOOD_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_MANGROVE_WOOD_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_CHERRY_WOOD_SLAB);

        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_OAK_WOOD_STAIRS);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_SPRUCE_WOOD_STAIRS);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_BIRCH_WOOD_STAIRS);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_JUNGLE_WOOD_STAIRS);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_ACACIA_WOOD_STAIRS);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_DARK_OAK_WOOD_STAIRS);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_MANGROVE_WOOD_STAIRS);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_CHERRY_WOOD_STAIRS);

        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.OAK_WOOD_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.SPRUCE_WOOD_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.BIRCH_WOOD_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.JUNGLE_WOOD_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.ACACIA_WOOD_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.DARK_OAK_WOOD_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.MANGROVE_WOOD_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.CHERRY_WOOD_VERTICAL_SLAB);

        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_OAK_WOOD_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_SPRUCE_WOOD_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_BIRCH_WOOD_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_JUNGLE_WOOD_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_ACACIA_WOOD_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_DARK_OAK_WOOD_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_MANGROVE_WOOD_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.STRIPPED_CHERRY_WOOD_VERTICAL_SLAB);

        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.OAK_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.SPRUCE_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.BIRCH_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.JUNGLE_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.ACACIA_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.DARK_OAK_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.MANGROVE_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.CHERRY_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.BAMBOO_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.CRIMSON_VERTICAL_SLAB);
        mineableAxe.add(net.jukoz.me.compat.MEBlocksLegacy.WARPED_VERTICAL_SLAB);

        mineableShovel.add(ModBlocks.GRAVEL_LAYER);
        mineableShovel.add(ModBlocks.SAND_LAYER);
        mineableShovel.add(ModBlocks.BLACK_SAND_LAYER);
        mineableShovel.add(ModBlocks.WHITE_SAND_LAYER);

        mineableShovel.add(ModBlocks.DIRT_SLAB);
        mineableShovel.add(ModBlocks.DIRT_STAIRS);
        mineableShovel.add(ModBlocks.MUD_SLAB);
        mineableShovel.add(ModBlocks.MUD_STAIRS);
        mineableShovel.add(ModBlocks.COARSE_DIRT_SLAB);
        mineableShovel.add(ModBlocks.COARSE_DIRT_STAIRS);
        mineableShovel.add(ModBlocks.ROOTED_DIRT_SLAB);

        mineableShovel.add(ModBlocks.ROOTED_DIRT_SLAB);
        
        mineablePickaxe.add(ModBlocks.PACKED_MUD_SLAB);
        mineablePickaxe.add(ModBlocks.PACKED_MUD_VERTICAL_SLAB);
        mineablePickaxe.add(ModBlocks.PACKED_MUD_STAIRS);
        mineablePickaxe.add(ModBlocks.PACKED_MUD_WALL);

        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.TUFF_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.TUFF_VERTICAL_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.TUFF_STAIRS);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.TUFF_WALL);

        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.CALCITE_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.CALCITE_VERTICAL_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.CALCITE_STAIRS);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.CALCITE_WALL);

        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.SMOOTH_BASALT_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.SMOOTH_BASALT_VERTICAL_SLAB);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.SMOOTH_BASALT_STAIRS);
        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.SMOOTH_BASALT_WALL);

        mineablePickaxe.add(ModBlocks.QUARTZ_BLOCK);
        mineablePickaxe.add(ModBlocks.BUDDING_QUARTZ);
        mineablePickaxe.add(ModBlocks.SMALL_QUARTZ_BUD);
        mineablePickaxe.add(ModBlocks.MEDIUM_QUARTZ_BUD);
        mineablePickaxe.add(ModBlocks.LARGE_QUARTZ_BUD);
        mineablePickaxe.add(ModBlocks.QUARTZ_CLUSTER);
        mineablePickaxe.add(ModBlocks.CITRINE_BLOCK);
        mineablePickaxe.add(ModBlocks.BUDDING_CITRINE);
        mineablePickaxe.add(ModBlocks.SMALL_CITRINE_BUD);
        mineablePickaxe.add(ModBlocks.MEDIUM_CITRINE_BUD);
        mineablePickaxe.add(ModBlocks.LARGE_CITRINE_BUD);
        mineablePickaxe.add(ModBlocks.CITRINE_CLUSTER);
        mineablePickaxe.add(ModBlocks.RED_AGATE_BLOCK);
        mineablePickaxe.add(ModBlocks.BUDDING_RED_AGATE);
        mineablePickaxe.add(ModBlocks.SMALL_RED_AGATE_BUD);
        mineablePickaxe.add(ModBlocks.MEDIUM_RED_AGATE_BUD);
        mineablePickaxe.add(ModBlocks.LARGE_RED_AGATE_BUD);
        mineablePickaxe.add(ModBlocks.RED_AGATE_CLUSTER);
        mineablePickaxe.add(ModBlocks.GLOWSTONE_BLOCK);
        mineablePickaxe.add(ModBlocks.BUDDING_GLOWSTONE);
        mineablePickaxe.add(ModBlocks.SMALL_GLOWSTONE_BUD);
        mineablePickaxe.add(ModBlocks.MEDIUM_GLOWSTONE_BUD);
        mineablePickaxe.add(ModBlocks.LARGE_GLOWSTONE_BUD);
        mineablePickaxe.add(ModBlocks.GLOWSTONE_CLUSTER);

        mineablePickaxe.add(net.jukoz.me.compat.MEBlocksLegacy.ALLOY_FURNACE);
    }
    private static Block[] notNullBlocks(java.util.List<Block> values) {
        return values.stream().filter(java.util.Objects::nonNull).toArray(Block[]::new);
    }
}
