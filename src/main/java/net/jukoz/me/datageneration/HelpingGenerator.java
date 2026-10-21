package net.jukoz.me.datageneration;

import net.jukoz.me.block.RoofBlockSets;
import net.jukoz.me.block.registration.*;
import net.jukoz.me.block.special.*;
import net.jukoz.me.block.special.verticalSlabs.VerticalSlabBlock;
import net.jukoz.me.block.utils.BlockRecordTypes;
import net.jukoz.me.block.utils.StoneBlockTypes;
import net.jukoz.me.block.utils.setBuilders.GenericBlockSetBuilder;
import net.jukoz.me.block.utils.setBuilders.SimpleBlockSetBuilder;
import net.jukoz.me.block.utils.setBuilders.StoneBlockSetBuilder;
import net.jukoz.me.block.utils.setBuilders.WoodBlockSetBuilder;
import net.jukoz.me.datageneration.content.loot_tables.BlockDrops;
import net.jukoz.me.datageneration.content.models.*;
import net.jukoz.me.datageneration.content.tags.*;
import net.minecraft.core.registries.Registries;
import java.util.Objects;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;

/**
 * Port of the upstream (1.21.x) HelpingGenerator: every registered block set builder is
 * walked and every block is routed into the model / loot / tag / mineable lists through
 * {@link #addBlocksToLists}. The legacy per-adapter loops this replaces only covered the
 * base subset of each set, which left ~2300 registered blocks without blockstates.
 */
public class HelpingGenerator {

    public static void generateFiles() {
        for (StoneBlockSetBuilder set : StoneBlockSets.stoneSetsList) {
            set.existingList.forEach(stoneBlockTypes -> {
                switch (stoneBlockTypes) {
                    case BASE_BLOCKS -> {
                        mainStoneBlocks(set.baseBlocks);
                        MineablePickaxe.baseStoneOverworld.add(set.baseBlocks.base());
                    }
                    case COBBLED_BLOCKS, COBBLESTONE_BLOCKS, PILLAR_BASE_BLOCKS -> {
                        regularBlocks(set.cobblestoneBlocks, 1);
                        if (set.hasMossy) regularBlocks(set.mossyCobblestoneBlocks, 1);
                        if (set.cobblestoneBlocks != null && set.cobblestoneBlocks.base() != null
                                && path(set.cobblestoneBlocks.base()).contains("cobble")
                                && !path(set.cobblestoneBlocks.base()).contains("mossy")) {
                            Stones.stones.add(set.cobblestoneBlocks.base());
                        }
                    }
                    case BRICK_BLOCKS, POLISHED_BRICK_BLOCKS -> {
                        regularBlocks(set.brickBlocks, 1);
                        if (set.hasMossy) regularBlocks(set.mossyBrickBlocks, 1);
                        if (set.hasCracked) regularBlocks(set.crackedBrickBlocks, 1);
                    }
                    case TILE_BLOCKS -> {
                        regularBlocks(set.tileBlocks, 1);
                        if (set.hasMossy) regularBlocks(set.mossyTileBlocks, 1);
                        if (set.hasCracked) regularBlocks(set.crackedTileBlocks, 1);
                    }
                    case SMOOTH_BLOCKS -> {
                        regularBlocks(set.smoothBlocks, 1);
                        if (set.hasMossy) regularBlocks(set.mossySmoothBlocks, 1);
                        if (set.hasCracked) regularBlocks(set.crackedSmoothBlocks, 1);
                    }
                    case POLISHED_BLOCKS, POLISHED_BLOCKS_PILLAR -> {
                        regularBlocks(set.polishedBlocks, 1);
                        if (set.hasMossy) regularBlocks(set.mossyPolishedBlocks, 1);
                        if (set.hasCracked) regularBlocks(set.crackedPolishedBlocks, 1);
                    }
                    case BRICKWORK_BLOCKS -> regularBlocks(set.brickworkBlocks, 1);
                    case OLD_BLOCKS, OLD_BLOCKS_PILLAR -> {
                        regularBlocks(set.oldBlocks, 1);
                        MineablePickaxe.baseStoneOverworld.add(set.oldBlocks.base());
                    }
                    case PILLAR_BLOCKS -> {
                        pillarBlocks(set.pillarBlocks, set.brickBlocks == null ? null : set.brickBlocks.base(), 1);
                        if (set.hasMossy) pillarBlocks(set.mossyPillarBlocks, set.pillarBlocks.base(), 1);
                        if (set.hasCracked) pillarBlocks(set.crackedPillarBlocks, set.pillarBlocks.base(), 1);
                    }
                    case CHISELED_BLOCKS -> {
                        if (set.existingList.contains(StoneBlockTypes.BASE_BLOCKS)) pillarBlocks(set.chiseledBlocks, set.baseBlocks.base(), 1);
                        if (set.existingList.contains(StoneBlockTypes.BRICK_BLOCKS)) pillarBlocks(set.chiseledBricksBlocks, set.brickBlocks.base(), 1);
                        if (set.existingList.contains(StoneBlockTypes.POLISHED_BRICK_BLOCKS)) pillarBlocks(set.chiseledBricksBlocks, set.brickBlocks.base(), 1);
                        if (set.existingList.contains(StoneBlockTypes.POLISHED_BLOCKS) || set.existingList.contains(StoneBlockTypes.POLISHED_BLOCKS_PILLAR))
                            pillarBlocks(set.chiseledPolishedBlocks, set.polishedBlocks.base(), 1);
                        if (set.existingList.contains(StoneBlockTypes.TILE_BLOCKS)) pillarBlocks(set.chiseledTilesBlocks, set.tileBlocks.base(), 1);
                        if (set.existingList.contains(StoneBlockTypes.SMOOTH_BLOCKS)) pillarBlocks(set.chiseledSmoothBlocks, set.smoothBlocks.base(), 1);
                    }
                    case CHISELED_BLOCKS_NO_RESTRICTION -> {
                        pillarBlocks(set.chiseledBlocks, null, 1);
                        pillarBlocks(set.chiseledBricksBlocks, null, 1);
                        pillarBlocks(set.chiseledPolishedBlocks, null, 1);
                        pillarBlocks(set.chiseledTilesBlocks, null, 1);
                        pillarBlocks(set.chiseledSmoothBlocks, null, 1);
                    }
                    case CARVED_WINDOW -> {
                        if (set.baseBlocks == null) carvedWindowBlocks(set.carvedWindows, set.cobblestoneBlocks.base());
                        else carvedWindowBlocks(set.carvedWindows, set.baseBlocks.base());
                    }
                    default -> { }
                }
            });
        }

        for (WoodBlockSetBuilder set : WoodBlockSets.woodSetsList) {
            set.existingList.forEach(woodBlockTypes -> {
                switch (woodBlockTypes) {
                    case LOG_BLOCKS, NETHER_STEM_BLOCKS -> woodBlocks(set.logBlocks);
                    case MUSHROOM_STEM_BLOCKS -> mushroomStemBlocks(set.mushroomStemBlocks);
                    case STRIPPED_LOG_BLOCKS, STRIPPED_STEM_BLOCKS -> {
                        ModdedStrippedLogs.strippedLogs.add(set.strippedLogBlocks.log());
                        woodBlocks(set.strippedLogBlocks);
                    }
                    case PLANK_BLOCKS -> plankBlocks(set.planksBlocks, set.vanilla);
                    case SHINGLE_BLOCKS -> {
                        Shingles.shingles.add(set.shinglesBlocks.base());
                        regularBlocks(set.shinglesBlocks, 0);
                    }
                    case ROOFING_BLOCKS -> regularBlocks(set.roofingBlocks, 0);
                    case FURNITURE_BLOCKS -> furnitureBlocks(set.furnitureBlocks, set.planksBlocks.base());
                    case REDSTONE_BLOCKS -> woodRedstoneBlocks(set.redstoneBlocks, set.planksBlocks.base());
                    case LEAVES -> {
                        if (set.leaves != null && !Objects.equals(Registries.BLOCK.getId(set.leaves).getNamespace(), "minecraft")) {
                            LeavesSets.blocks.add(set.leaves);
                            if (set.setName.contains("beech")) {
                                LeavesSets.grayscaleLeaves.add(set.leaves);
                            } else {
                                LeavesSets.leaves.add(set.leaves);
                            }
                        }
                    }
                    default -> { }
                }
            });
        }

        for (GenericBlockSetBuilder set : GenericBlockSets.genericSetsList) {
            int tool = -1;
            if (set.requiresTool) {
                if (set.setName.contains("wood")) tool = 0;
                else if (set.setName.contains("clay") || set.setName.contains("stone") || set.setName.contains("brick")
                        || set.setName.contains("tiles") || set.setName.contains("stucco")) tool = 1;
                else if (set.setName.contains("plaster")) tool = 2;
            }
            regularBlocks(set.blockSet, tool);
        }
        for (SimpleBlockSetBuilder set : GenericBlockSets.simpleSetsList) {
            regularBlocks(set.blockSet, -1);
        }

        // The roof / shingle sets are a 1.20.1-port registration path that upstream keeps
        // inside the wood sets; route them through the same list machinery.
        for (RoofBlockSets.RoofBlockSet set : RoofBlockSets.sets) {
            addBlocksToLists(set.block(), set.block(), set.origin(), roofTool(set));
            addBlocksToLists(set.slab(), set.block(), set.slab(), roofTool(set));
            addBlocksToLists(set.verticalSlab(), set.block(), set.slab(), roofTool(set));
            addBlocksToLists(set.stairs(), set.block(), set.stairs(), roofTool(set));
            addBlocksToLists(set.wall(), set.block(), set.wall(), roofTool(set));
            if (set.block().toString().contains("shingles")) {
                Shingles.shingles.add(set.block());
            }
        }

        SimpleBlockModel.blocks.addAll(LeavesSets.leaves);

        for (SimpleVerticalSlabModel.VerticalSlab set : SimpleVerticalSlabModel.vanillaVerticalSlabs) {
            BlockDrops.blocks.add(set.verticalSlab());
        }

        for (SimpleWoodStoolModel.VanillaStool stool : SimpleWoodStoolModel.vanillaStools) {
            BlockDrops.blocks.add(stool.base());
            MineableAxe.blocks.add(stool.base());
        }

        for (SimpleWoodTableModel.VanillaTable table : SimpleWoodTableModel.vanillaTables) {
            BlockDrops.blocks.add(table.base());
            MineableAxe.blocks.add(table.base());
        }

        for (SimpleWoodChairModel.VanillaChair chair : SimpleWoodChairModel.vanillaChairs) {
            BlockDrops.blocks.add(chair.base());
            MineableAxe.blocks.add(chair.base());
        }

        SimpleWallModel.vanillaWalls.forEach(block -> Walls.walls.add(block.wall()));
        SimpleWallModel.vanillaWoodWalls.forEach(block -> {
            Walls.walls.add(block.wall());
            MineableAxe.blocks.add(block.wall());
        });
        SimpleWallModel.vanillaStrippedWalls.forEach(block -> {
            Walls.walls.add(block.wall());
            MineableAxe.blocks.add(block.wall());
        });

        SimpleFenceModel.vanillaWoodFences.forEach(block -> {
            Fences.fences.add(block.fence());
            MineableAxe.blocks.add(block.fence());
        });
        SimpleFenceModel.vanillaStrippedFences.forEach(block -> {
            Fences.fences.add(block.fence());
            MineableAxe.blocks.add(block.fence());
        });

        for (OreRockSets.OreRockSet set : OreRockSets.sets) {
            addOre(set.coal_ore());
            addOre(set.copper_ore());
            addOre(set.tin_ore());
            addOre(set.lead_ore());
            addOre(set.silver_ore());
            addOre(set.gold_ore());
            addOre(set.iron_ore());
            addOre(set.mithril_ore());
            addOre(set.adamant_ore());
            addOre(set.emerald_ore());
            addOre(set.ruby_ore());
            addOre(set.sapphire_ore());
        }
    }

    // -1 == nothing, 0 == axe, 1 == pickaxe, 2 == shovel, 3 == hoe
    public static void addBlocksToLists(Block block, Block base, Block origin, int tool) {
        if (block == null) return;
        switch (tool) {
            case 0 -> MineableAxe.blocks.add(block);
            case 1 -> MineablePickaxe.blocks.add(block);
            case 2 -> MineableShovel.blocks.add(block);
            case 3 -> MineableHoe.blocks.add(block);
            default -> { }
        }

        BlockDrops.blocks.add(block);

        if (Objects.equals(Registries.BLOCK.getId(block).getNamespace(), "minecraft")) {
            return;
        }

        String blockName = path(block);
        String baseName = base == null ? "" : path(base);

        final boolean isPillar = blockName.contains("carved_window")
                || blockName.contains("chiseled")
                || blockName.contains("drystone")
                || blockName.contains("pillar");
        final boolean woodModel = (blockName.contains("_wood") && !(blockName.contains("gilded") || blockName.contains("beam"))) || blockName.contains("hyphae");

        if (block instanceof RotatedPillarBlock) {
            if (woodModel) {
                SimpleBlockModel.woodBlocks.add(block);
            } else {
                SimplePillarModel.blocks.add(new SimplePillarModel.Pillar(block));
            }
        } else if (block instanceof VerticalSlabBlock verticalSlabBlock) {
            if (isPillar) {
                SimpleVerticalSlabModel.columnVerticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(base, origin, verticalSlabBlock));
            } else if (blockName.contains("stripped")) {
                SimpleVerticalSlabModel.strippedVerticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(base, origin, verticalSlabBlock));
            } else if (woodModel) {
                SimpleVerticalSlabModel.woodVerticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(base, origin, verticalSlabBlock));
            } else if (baseName.contains("planks")) {
                SimpleVerticalSlabModel.plansVerticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(base, origin, verticalSlabBlock));
            } else {
                SimpleVerticalSlabModel.verticalSlabs.add(new SimpleVerticalSlabModel.VerticalSlab(base, origin, verticalSlabBlock));
            }
        } else if (block instanceof SlabBlock) {
            if (blockName.contains("stripped")) {
                SimpleSlabModel.strippedSlabs.add(new SimpleSlabModel.Slab(base, origin));
            } else if (woodModel) {
                SimpleSlabModel.woodSlabs.add(new SimpleSlabModel.Slab(base, origin));
            } else {
                SimpleSlabModel.slabs.add(new SimpleSlabModel.Slab(base, block));
            }
        } else if (block instanceof StairBlock) {
            if (blockName.contains("stripped")) {
                SimpleStairModel.strippedStairs.add(new SimpleStairModel.Stair(base, block));
            } else if (woodModel) {
                SimpleStairModel.woodStairs.add(new SimpleStairModel.Stair(base, block));
            } else {
                SimpleStairModel.stairs.add(new SimpleStairModel.Stair(base, block));
            }
        } else if (block instanceof WallBlock wallBlock) {
            Walls.walls.add(wallBlock);
            if (isPillar) {
                SimpleWallModel.columnWalls.add(new SimpleWallModel.Wall(base, wallBlock));
            } else if (blockName.contains("stripped")) {
                SimpleWallModel.strippedWalls.add(new SimpleWallModel.Wall(base, wallBlock));
            } else {
                SimpleWallModel.blocks.add(new SimpleWallModel.Wall(base, wallBlock));
            }
        } else if (block instanceof FenceBlock fenceBlock) {
            Fences.fences.add(fenceBlock);
            if (blockName.contains("stripped")) {
                SimpleFenceModel.strippedFences.add(new SimpleFenceModel.Fence(base, fenceBlock));
            } else {
                SimpleFenceModel.blocks.add(new SimpleFenceModel.Fence(base, fenceBlock));
            }
        } else if (block instanceof FenceGateBlock fenceGateBlock) {
            FenceGates.fenceGates.add(fenceGateBlock);
            SimpleFenceGateModel.blocks.add(new SimpleFenceGateModel.FenceGate(base, fenceGateBlock));
        } else if (block instanceof IronBarsBlock paneBlock) {
            SimplePaneModel.panes.add(new SimplePaneModel.Pane(base, paneBlock));
        } else if (block instanceof PressurePlateBlock pressurePlateBlock) {
            PressurePlates.pressurePlates.add(pressurePlateBlock);
            SimplePressurePlateModel.pressurePlates.add(new SimplePressurePlateModel.PressurePlate(base, pressurePlateBlock));
        } else if (block instanceof ButtonBlock buttonBlock) {
            if (woodModel) {
                Buttons.woodButtons.add(buttonBlock);
            } else {
                Buttons.stoneButtons.add(buttonBlock);
            }
            Buttons.buttons.add(buttonBlock);
            SimpleButtonModel.buttons.add(new SimpleButtonModel.Button(base, buttonBlock));
        } else if (block instanceof TrapDoorBlock trapdoorBlock) {
            Trapdoors.trapdoors.add(trapdoorBlock);
            SimpleTrapDoorModel.trapdoors.add(new SimpleTrapDoorModel.Trapdoor(base, trapdoorBlock, baseName.contains("planks")));
        } else if (block instanceof DoorBlock doorBlock) {
            Doors.doors.add(doorBlock);
            SimpleDoorModel.doors.add(new SimpleDoorModel.Door(base, doorBlock));
        } else if (block instanceof StoolBlock) {
            SimpleStoneStoolModel.stools.add(block);
        } else if (block instanceof WoodStoolBlock) {
            SimpleWoodStoolModel.stools.add(block);
        } else if (block instanceof StoneTableBlock) {
            SimpleStoneTableModel.tables.add(block);
        } else if (block instanceof WoodTableBlock) {
            SimpleWoodTableModel.tables.add(block);
        } else if (block instanceof StoneChairBlock) {
            SimpleStoneChairModel.chairs.add(block);
        } else if (block instanceof WoodChairBlock) {
            SimpleWoodChairModel.chairs.add(block);
        } else if (block instanceof WoodBenchBlock) {
            SimpleWoodBenchModel.benchs.add(block);
        } else if (block instanceof ThickLadderBlock ladderBlock) {
            SimpleLadderModel.ladders.add(new SimpleLadderModel.Ladder(base, ladderBlock));
        } else if (block instanceof RocksBlock rocksBlock) {
            SimpleRocksModel.rocks.add(new SimpleRocksModel.Rocks(base, rocksBlock));
        } else if (blockName.contains("carved_window")) {
            SimplePillarModel.carvedWindows.add(new SimplePillarModel.StonePillar(block, origin));
        } else {
            SimpleBlockModel.blocks.add(block);
        }
    }

    public static void regularBlocks(BlockRecordTypes.RegularSet set, int tool) {
        if (set == null) return;
        BlockRecordTypes.RegularSet.getAllBlocks(set).forEach(block -> addBlocksToLists(block, set.base(), set.slab(), tool));
    }

    public static void regularBlocks(BlockRecordTypes.SimpleBlocks set, int tool) {
        if (set == null) return;
        BlockRecordTypes.SimpleBlocks.getAllBlocks(set).forEach(block -> addBlocksToLists(block, set.base(), set.slab(), tool));
    }

    public static void pillarBlocks(BlockRecordTypes.PillarSet set, Block origin, int tool) {
        if (set == null) return;
        BlockRecordTypes.PillarSet.getAllBlocks(set).forEach(block -> addBlocksToLists(block, set.base(), set.verticalSlab(), tool));
    }

    public static void carvedWindowBlocks(BlockRecordTypes.CarvedWindow set, Block origin) {
        if (set == null) return;
        BlockRecordTypes.CarvedWindow.getAllBlocks(set).forEach(block -> addBlocksToLists(block, set.block(), origin, 1));
    }

    public static void mainStoneBlocks(BlockRecordTypes.BaseStoneSet set) {
        if (set == null) return;
        BlockRecordTypes.BaseStoneSet.getAllBlocks(set).forEach(block -> addBlocksToLists(block, set.base(), set.slab(), 1));
    }

    public static void plankBlocks(BlockRecordTypes.PlanksSet set, boolean vanilla) {
        if (set == null) return;
        BlockRecordTypes.PlanksSet.getAllBlocks(set).forEach(block -> addBlocksToLists(block, set.base(), set.slab(), 0));
        WoodenVerticalSlabs.woodenVericalSlabs.add(set.verticalSlab());
        Planks.planks.add(set.base());
        if (!vanilla) Planks.moddedPlanks.add(set.base());
        Planks.planksSlabs.add(set.slab());
        WoodenSlabs.woodenSlabs.add(set.slab());
    }

    public static void woodBlocks(BlockRecordTypes.WoodSet set) {
        if (set == null) return;
        BlockRecordTypes.WoodSet.getAllBlocks(set).forEach(block -> addBlocksToLists(block, set.wood(), set.slab(), 0));
        Logs.logs.add(set.log());
        Logs.logs.add(set.wood());
        LogsThatBurn.logsThatBurn.add(set.log());
        LogsThatBurn.logsThatBurn.add(set.wood());
    }

    public static void mushroomStemBlocks(BlockRecordTypes.MushroomStemSet set) {
        if (set == null) return;
        BlockRecordTypes.MushroomStemSet.getAllBlocks(set).forEach(block -> addBlocksToLists(block, set.stem(), set.slab(), 0));
    }

    public static void furnitureBlocks(BlockRecordTypes.WoodFurnitureBlocks set, Block base) {
        if (set == null) return;
        BlockRecordTypes.WoodFurnitureBlocks.getAllBlocks(set).forEach(block -> addBlocksToLists(block, base, null, 0));
    }

    public static void woodRedstoneBlocks(BlockRecordTypes.WoodRedstoneBlocks set, Block base) {
        if (set == null) return;
        BlockRecordTypes.WoodRedstoneBlocks.getAllBlocks(set).forEach(block -> addBlocksToLists(block, base, null, 0));
    }

    private static void addOre(Block ore) {
        if (ore == null) return;
        SimpleBlockModel.blocks.add(ore);
        MineablePickaxe.blocks.add(ore);
    }

    private static int roofTool(RoofBlockSets.RoofBlockSet set) {
        Block origin = set.origin();
        if (origin != null && path(origin).contains("planks")) return 0;
        if (path(set.block()).contains("thatch")) return 3;
        return 1;
    }

    private static String path(Block block) {
        return Registries.BLOCK.getId(block).getPath();
    }
}
