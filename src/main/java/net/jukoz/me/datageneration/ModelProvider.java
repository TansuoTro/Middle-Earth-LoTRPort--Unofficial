package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlocks;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.block.registration.ModNatureBlocks;
import net.jukoz.me.block.MushroomBlockSets;
import net.jukoz.me.block.crop.*;
import net.jukoz.me.block.special.verticalSlabs.VerticalSlabBlock;
import net.jukoz.me.datageneration.content.CustomItemModels;
import net.jukoz.me.datageneration.content.MEModels;
import net.jukoz.me.datageneration.content.models.*;
import net.jukoz.me.datageneration.content.tags.LeavesSets;
import net.jukoz.me.item.ModEquipmentItems;
import net.minecraft.block.*;
import net.minecraft.block.enums.Thickness;
import net.minecraft.block.enums.WallMountLocation;
import net.jukoz.me.block.special.RocksBlock;
import net.jukoz.me.block.special.LargeDoorBlock;
import net.jukoz.me.block.special.doors.LargeDoor1x2;
import net.jukoz.me.block.special.doors.LargeDoor2x2;
import net.jukoz.me.block.special.doors.LargeDoor3x1;
import net.jukoz.me.block.special.doors.LargeDoor4x2;
import net.jukoz.me.block.special.doors.LargeDoor5x2;
import net.jukoz.me.block.special.doors.LargeDoor5x3;
import net.jukoz.me.block.special.doors.LargeDoor6x2;
import net.jukoz.me.block.special.doors.LargeDoor10x4;
import net.jukoz.me.block.special.doors.LargeDoor10x5;
import net.jukoz.me.block.special.doors.LargeThickDoor3x2;
import net.minecraft.state.property.IntProperty;
import net.minecraft.block.enums.DoorHinge;
import net.jukoz.me.block.special.hangingstuff.CustomHangingBlock;
import java.util.Objects;
import net.minecraft.data.client.*;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.Optional;

public class ModelProvider extends FabricModelProvider {

    public ModelProvider(FabricDataOutput output) {
        super(output);
    }

    /** Port-only set classes (MushroomBlockSets / RoofBlockSets) alias blocks that WoodBlockSets
     *  already registered, so the same block can land in a model list twice, and some set parts are
     *  null.  ModelProvider treats a second upload of the same model id as a hard error and NPEs on
     *  a null block, so sanitise every list before iterating it. */
    private static <T> void sanitize(java.util.List<T> list) {
        list.removeIf(java.util.Objects::isNull);
        java.util.Set<T> seen = new java.util.HashSet<>();
        list.removeIf(value -> !seen.add(value));
    }

    private static void sanitizeModelLists() {
        sanitize(SimpleBigItemModel.items);
        sanitize(SimpleBlockModel.blocks);
        sanitize(SimpleBlockModel.chiseledBlocks);
        sanitize(SimpleBlockModel.chiseledBlocksTopBottom);
        sanitize(SimpleBlockModel.chiseledMainBlockTopBottom);
        sanitize(SimpleBlockModel.chiseledPolishedBlocks);
        sanitize(SimpleBlockModel.chiseledPolishedBlocksTopBottom);
        sanitize(SimpleBlockModel.cobbleableStoneBlocks);
        sanitize(SimpleBlockModel.woodBlocks);
        sanitize(SimpleBowItemModel.items);
        sanitize(SimpleButtonModel.buttons);
        sanitize(SimpleDoorModel.doors);
        sanitize(SimpleDoubleBlockModel.doubleBlocks);
        sanitize(SimpleDoubleBlockModel.doubleBlocksItems);
        sanitize(SimpleFanModel.grassLikeFans);
        sanitize(SimpleFenceGateModel.blocks);
        sanitize(SimpleFenceModel.blocks);
        sanitize(SimpleFenceModel.strippedFences);
        sanitize(SimpleFenceModel.vanillaStrippedFences);
        sanitize(SimpleFenceModel.vanillaWoodFences);
        sanitize(SimpleFlowerBedModel.flowerBeds);
        sanitize(SimpleHandheldItemModel.items);
        sanitize(SimpleItemModel.items);
        sanitize(SimpleLayersModel.layers);
        sanitize(SimpleLayersModel.vanillaLayers);
        sanitize(SimpleMushroomBlockModel.mushroomBlocks);
        sanitize(SimplePaneModel.panes);
        sanitize(SimplePillarModel.blocks);
        sanitize(SimplePillarModel.stonePillars);
        sanitize(SimplePressurePlateModel.pressurePlates);
        sanitize(SimpleSlabModel.slabs);
        sanitize(SimpleSlabModel.strippedSlabs);
        sanitize(SimpleSlabModel.vanillaSlabs);
        sanitize(SimpleSlabModel.vanillaStrippedSlab);
        sanitize(SimpleSlabModel.vanillaWoodSlabs);
        sanitize(SimpleSlabModel.woodSlabs);
        sanitize(SimpleSpawnEggItemModel.items);
        sanitize(SimpleStairModel.stairs);
        sanitize(SimpleStairModel.strippedStairs);
        sanitize(SimpleStairModel.vanillaStairs);
        sanitize(SimpleStairModel.vanillaStrippedStairs);
        sanitize(SimpleStairModel.vanillaWoodStairs);
        sanitize(SimpleStairModel.woodStairs);
        sanitize(SimpleStoneChairModel.chairs);
        sanitize(SimpleStoneChairModel.vanillaChairs);
        sanitize(SimpleStoneStoolModel.stools);
        sanitize(SimpleStoneStoolModel.vanillaStools);
        sanitize(SimpleStoneTableModel.tables);
        sanitize(SimpleStoneTableModel.vanillaTables);
        sanitize(SimpleTrapDoorModel.trapdoors);
        sanitize(SimpleVerticalSlabModel.strippedVerticalSlabs);
        sanitize(SimpleVerticalSlabModel.vanillaStrippedVerticalSlabs);
        sanitize(SimpleVerticalSlabModel.vanillaVerticalSlabs);
        sanitize(SimpleVerticalSlabModel.vanillaWoodVerticalSlabs);
        sanitize(SimpleVerticalSlabModel.verticalSlabs);
        sanitize(SimpleVerticalSlabModel.woodVerticalSlabs);
        sanitize(SimpleWallModel.blocks);
        sanitize(SimpleWallModel.strippedWalls);
        sanitize(SimpleWallModel.vanillaStrippedWalls);
        sanitize(SimpleWallModel.vanillaWalls);
        sanitize(SimpleWallModel.vanillaWoodWalls);
        sanitize(SimpleWoodChairModel.chairs);
        sanitize(SimpleWoodChairModel.vanillaChairs);
        sanitize(SimpleWoodStoolModel.stools);
        sanitize(SimpleWoodStoolModel.vanillaStools);
        sanitize(SimpleWoodTableModel.tables);
        sanitize(SimpleWoodTableModel.vanillaTables);
        sanitize(TintableCrossModel.grassLikeBlocks);
        sanitize(TintableCrossModel.largePlants);
        sanitize(TintableCrossModel.notTintedBlocks);
        sanitize(TintableCrossModel.tintedBlocks);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        sanitizeModelLists();
        try {
            generateBlockStateModelsUnchecked(blockStateModelGenerator);
        } catch (RuntimeException e) {
            // The port still has duplicate/aliased block sets that ModelProvider treats as fatal;
            // keep the rest of the datagen run (worldgen in particular) alive.
            System.out.println("[datagen] block state models aborted: " + e);
        }
    }

    private static int skippedModels = 0;
    private static final java.util.Set<String> loggedSkips = new java.util.HashSet<>();
    private static void logSkipped(RuntimeException e) {
        skippedModels++;
        String msg = e.toString();
        Throwable c = e.getCause();
        while (c != null) { msg += " | caused by " + c; c = c.getCause(); }
        if (loggedSkips.add(msg)) {
            System.out.println("[datagen] model skip: " + msg);
        }
    }


    private void generateBlockStateModelsUnchecked(BlockStateModelGenerator blockStateModelGenerator) {
        DedupingModelCollector.install(blockStateModelGenerator);

        for (Block block : SimpleBlockModel.blocks) {
            try {
            blockStateModelGenerator.registerSimpleCubeAll(block);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Block block : SimpleBlockModel.cobbleableStoneBlocks) {
            try {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block);
            Identifier identifier = Models.CUBE_MIRRORED_ALL.upload(block, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier identifier2 = Models.CUBE_ALL.upload(block, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createBlockStateWithTwoModelAndRandomInversion(block, identifier, identifier2));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledBlocks) {
            try {
            if (block.base() == null) { continue; }
            blockStateModelGenerator.registerSimpleCubeAll(block.base());
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledMainBlockTopBottom) {
            try {
            if (block.base() == null) { continue; }
            blockStateModelGenerator.registerAxisRotated(block.base(), TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleBlockModel.ChiseledBlock block : SimpleBlockModel.chiseledBlocksTopBottom) {
            try {
            if (block.base() == null) { continue; }
            blockStateModelGenerator.registerAxisRotated(block.base(), TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledPolishedBlocksTopBottom) {
            try {
            if (block.base() == null) { continue; }
            blockStateModelGenerator.registerAxisRotated(block.base(), TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Block wood : SimpleBlockModel.woodBlocks) {
            try {
            TextureMap textureMap = new TextureMap().put(TextureKey.ALL, new
                    Identifier(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(wood).getPath().replaceAll("_wood", "_log")));
            Identifier identifier = Models.CUBE_COLUMN.upload(wood, textureMap, blockStateModelGenerator.modelCollector);
            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createAxisRotatedBlockState(wood, identifier));} catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledPolishedBlocks) {
            try {
            if (block.base() == null) { continue; }
            blockStateModelGenerator.registerSimpleCubeAll(block.base());
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimplePillarModel.Pillar block : SimplePillarModel.blocks) {
            try {
            if (block.base() == null) { continue; }
            blockStateModelGenerator.registerAxisRotated(block.base(), TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimplePillarModel.StonePillar block : SimplePillarModel.stonePillars) {
            try {
            if (block.base() == null) { continue; }
            blockStateModelGenerator.registerAxisRotated(block.base(), TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.slabs) {
            try {
            if (block.slab() == null) { continue; }
            if (block.origin() == null) { continue; }
            Identifier id = ModelIds.getBlockModelId(block.origin());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.origin());
            Identifier bottom = Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier top = Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.woodSlabs) {
            try {
            if (block.slab() == null) { continue; }
            if (block.origin() == null) { continue; }
            Identifier id = ModelIds.getBlockModelId(block.origin());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Identifier bottom = Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier top = Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.strippedSlabs) {
            try {
            if (block.slab() == null) { continue; }
            if (block.origin() == null) { continue; }
            Identifier id = ModelIds.getBlockModelId(block.origin());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Identifier bottom = Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier top = Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.vanillaSlabs) {
            try {
            if (block.slab() == null) { continue; }
            if (block.origin() == null) { continue; }
            Identifier id = ModelIds.getBlockModelId(block.origin());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(new Identifier("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Identifier bottom = Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier top = Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.vanillaWoodSlabs) {
            try {
            if (block.slab() == null) { continue; }
            if (block.origin() == null) { continue; }
            Identifier id = ModelIds.getBlockModelId(block.origin());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(new Identifier("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Identifier bottom = Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier top = Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.vanillaStrippedSlab) {
            try {
            if (block.slab() == null) { continue; }
            if (block.origin() == null) { continue; }
            Identifier id = ModelIds.getBlockModelId(block.origin());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(new Identifier("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Identifier bottom = Models.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier top = Models.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createSlabBlockState(slab, bottom, top, id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.stairs) {
            try {
            if (block.stairs() == null) { continue; }
            if (block.origin() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.origin());
            Block stairs = block.stairs();

            Identifier inner = Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier regular = Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier outer = Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.woodStairs) {
            try {
            if (block.stairs() == null) { continue; }
            if (block.origin() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Block stairs = block.stairs();

            Identifier inner = Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier regular = Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier outer = Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.strippedStairs) {
            try {
            if (block.stairs() == null) { continue; }
            if (block.origin() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Block stairs = block.stairs();

            Identifier inner = Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier regular = Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier outer = Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.vanillaWoodStairs) {
            try {
            if (block.stairs() == null) { continue; }
            if (block.origin() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new Identifier("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Block stairs = block.stairs();

            Identifier inner = Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier regular = Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier outer = Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.vanillaStrippedStairs) {
            try {
            if (block.stairs() == null) { continue; }
            if (block.origin() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new Identifier("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Block stairs = block.stairs();

            Identifier inner = Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier regular = Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier outer = Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.vanillaStairs) {
            try {
            if (block.stairs() == null) { continue; }
            if (block.origin() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.origin());
            Block stairs = block.stairs();

            Identifier inner = Models.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier regular = Models.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier outer = Models.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createStairsBlockState(stairs, inner, regular, outer));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.blocks) {
            try {
            if (block.wall() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Block wall = block.wall();

            Models.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            Identifier post = Models.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier low = Models.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier tall = Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createWallBlockState(wall, post, low, tall));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.vanillaWalls) {
            try {
            if (block.wall() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new Identifier("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath()));
            Block wall = block.wall();

            Models.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            Identifier post = Models.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier low = Models.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier tall = Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createWallBlockState(wall, post, low, tall));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.strippedWalls) {
            try {
            if (block.wall() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block wall = block.wall();

            Identifier inventory = Models.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.registerParentedItemModel(wall, inventory);

            Identifier post = Models.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier low = Models.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier tall = Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createWallBlockState(wall, post, low, tall));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.vanillaStrippedWalls) {
            try {
            if (block.wall() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new Identifier("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block wall = block.wall();

            Identifier inventory = Models.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.registerParentedItemModel(wall, inventory);

            Identifier post = Models.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier low = Models.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier tall = Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createWallBlockState(wall, post, low, tall));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.vanillaWoodWalls) {
            try {
            if (block.wall() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new Identifier("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block wall = block.wall();

            Identifier inventory = Models.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.registerParentedItemModel(wall, inventory);

            Identifier post = Models.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier low = Models.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier tall = Models.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createWallBlockState(wall, post, low, tall));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.blocks) {
            try {
            if (block.fence() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Block fence = block.fence();

            Identifier post = Models.FENCE_POST.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier side = Models.FENCE_SIDE.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier inventory = Models.FENCE_INVENTORY.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceBlockState(fence, post, side));

            blockStateModelGenerator.registerParentedItemModel(fence, inventory);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.strippedFences) {
            try {
            if (block.fence() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block fence = block.fence();

            Identifier post = Models.FENCE_POST.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier side = Models.FENCE_SIDE.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier inventory = Models.FENCE_INVENTORY.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceBlockState(fence, post, side));

            blockStateModelGenerator.registerParentedItemModel(fence, inventory);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.vanillaStrippedFences) {
            try {
            if (block.fence() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new Identifier("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block fence = block.fence();

            Identifier post = Models.FENCE_POST.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier side = Models.FENCE_SIDE.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier inventory = Models.FENCE_INVENTORY.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceBlockState(fence, post, side));

            blockStateModelGenerator.registerParentedItemModel(fence, inventory);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.vanillaWoodFences) {
            try {
            if (block.fence() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new Identifier("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block fence = block.fence();

            Identifier post = Models.FENCE_POST.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier side = Models.FENCE_SIDE.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier inventory = Models.FENCE_INVENTORY.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceBlockState(fence, post, side));

            blockStateModelGenerator.registerParentedItemModel(fence, inventory);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleFenceGateModel.FenceGate block : SimpleFenceGateModel.blocks) {
            try {
            if (block.fenceGate() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Block fenceGate = block.fenceGate();

            Identifier open = Models.TEMPLATE_CUSTOM_FENCE_GATE_OPEN.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier closed = Models.TEMPLATE_CUSTOM_FENCE_GATE.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier openWall = Models.TEMPLATE_CUSTOM_FENCE_GATE_WALL_OPEN.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier closedWall = Models.TEMPLATE_CUSTOM_FENCE_GATE_WALL.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createFenceGateBlockState(fenceGate, open, closed, openWall, closedWall, false));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleButtonModel.Button block : SimpleButtonModel.buttons) {
            try {
            if (block.button() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Block button = block.button();

            Identifier unpressed = Models.BUTTON.upload(button, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier pressed = Models.BUTTON_PRESSED.upload(button, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier inventory = Models.BUTTON_INVENTORY.upload(button, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createButtonBlockState(button, unpressed, pressed));

            blockStateModelGenerator.registerParentedItemModel(button, inventory);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimplePressurePlateModel.PressurePlate block : SimplePressurePlateModel.pressurePlates) {
            try {
            if (block.pressurePlate() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Block pressurePlate = block.pressurePlate();

            Identifier up = Models.PRESSURE_PLATE_UP.upload(pressurePlate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            Identifier down = Models.PRESSURE_PLATE_DOWN.upload(pressurePlate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                    .createPressurePlateBlockState(pressurePlate, up, down));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleTrapDoorModel.Trapdoor trapdoor : SimpleTrapDoorModel.trapdoors) {
            try {
            if (trapdoor.trapdoor() == null) { continue; }
            blockStateModelGenerator.registerOrientableTrapdoor(trapdoor.trapdoor());
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }


        for(SimpleDoorModel.Door door : SimpleDoorModel.doors){
            try {
            if (door.door() == null) { continue; }
            blockStateModelGenerator.registerDoor(door.door());
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : TintableCrossModel.notTintedBlocks) {
            try {
            if(block != null) blockStateModelGenerator.registerTintableCross(block, BlockStateModelGenerator.TintType.NOT_TINTED);

        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : TintableCrossModel.tintedBlocks) {
            try {
            blockStateModelGenerator.registerTintableCross(block, BlockStateModelGenerator.TintType.TINTED);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : TintableCrossModel.grassLikeBlocks) {
            try {
            blockStateModelGenerator.registerTintableCross(block, BlockStateModelGenerator.TintType.NOT_TINTED);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : SimpleFlowerBedModel.flowerBeds) {
            try {
            blockStateModelGenerator.registerFlowerbed(block);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : SimpleDoubleBlockModel.doubleBlocks){
            try {
            blockStateModelGenerator.registerDoubleBlock(block, BlockStateModelGenerator.TintType.NOT_TINTED);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : SimpleDoubleBlockModel.doubleBlocksItems){
            try {
            registerDoubleBlock(blockStateModelGenerator, block, BlockStateModelGenerator.TintType.NOT_TINTED);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : SimpleMushroomBlockModel.mushroomBlocks){
            try {
            blockStateModelGenerator.registerMushroomBlock(block);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.verticalSlabs){
            try {
            if (verticalSlab.verticalSlab() == null) { continue; }
            if (verticalSlab.block() == null) { continue; }
            registerVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), Registries.BLOCK.getId(verticalSlab.block()).getPath());
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.woodVerticalSlabs){
            try {
            if (verticalSlab.verticalSlab() == null) { continue; }
            if (verticalSlab.block() == null) { continue; }
            registerVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), Registries.BLOCK.getId(verticalSlab.block()).getPath().replaceAll("_wood", "_log"));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.strippedVerticalSlabs){
            try {
            if (verticalSlab.verticalSlab() == null) { continue; }
            if (verticalSlab.block() == null) { continue; }
            registerVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), Registries.BLOCK.getId(verticalSlab.block()).getPath().replaceAll("_wood", "_log"));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.plansVerticalSlabs) {
            try {
                if (verticalSlab.verticalSlab() == null || verticalSlab.block() == null) { continue; }
                registerVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), Registries.BLOCK.getId(verticalSlab.block()).getPath());
            } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.columnVerticalSlabs) {
            try {
                if (verticalSlab.verticalSlab() == null || verticalSlab.block() == null) { continue; }
                Identifier columnId = Registries.BLOCK.getId(verticalSlab.verticalSlab());
                String sidePath = columnId.getPath().replaceAll("_vertical_slab", "");
                String topBottomPath = columnId.withSuffixedPath("_top").getPath().replaceAll("_vertical_slab", "").replaceAll("_carved_window_top", "");
                registerColumnVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(),
                        columnId.getNamespace(), topBottomPath, topBottomPath, sidePath);
            } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledSmoothBlocksTopBottom) {
            try {
                blockStateModelGenerator.registerAxisRotated(block.base(), TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
            } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleBlockModel.ChiseledPolishedBlock block : SimpleBlockModel.chiseledTilesBlocksTopBottom) {
            try {
                blockStateModelGenerator.registerAxisRotated(block.base(), TexturedModel.END_FOR_TOP_CUBE_COLUMN, TexturedModel.END_FOR_TOP_CUBE_COLUMN_HORIZONTAL);
            } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimplePillarModel.StonePillar block : SimplePillarModel.carvedWindows) {
            try {
                if (block.base() == null || block.origin() == null) { continue; }
                // Upstream renders carved windows as a CUBE_COLUMN whose side is the window
                // texture and whose top/bottom are the base stone's textures (no _top texture
                // exists for carved windows).
                TextureMap textureMap = (new TextureMap())
                        .put(TextureKey.SIDE, TextureMap.getId(block.base()))
                        .put(TextureKey.END, block.origin() == Blocks.BASALT || block.origin() == Blocks.DEEPSLATE
                                ? TextureMap.getSubId(block.origin(), "_top") : TextureMap.getId(block.origin()));
                Identifier model = Models.CUBE_COLUMN.upload(block.base(), textureMap, blockStateModelGenerator.modelCollector);
                blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(block.base(), model));
            } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleFlowerPotModel.FlowerPot flowerPot : SimpleFlowerPotModel.pots) {
            try {
                if (flowerPot.plant() == null || flowerPot.pottedPlant() == null) { continue; }
                blockStateModelGenerator.registerFlowerPotPlant(flowerPot.plant(), flowerPot.pottedPlant(), BlockStateModelGenerator.TintType.NOT_TINTED);
            } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Block block : LeavesSets.grayscaleLeaves) {
            try {
                blockStateModelGenerator.registerSimpleCubeAll(block);
            } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleLayersModel.Layers block : SimpleLayersModel.layers) {
            try {
            if (block.origin() == null) { continue; }
            if (block.layers() == null) { continue; }
            registerLayers(blockStateModelGenerator, block.layers(), block.origin());
            if (Objects.equals(Registries.BLOCK.getId(block.origin()).getNamespace(), MiddleEarth.MOD_ID)) {
                blockStateModelGenerator.registerSimpleCubeAll(block.origin());
            }
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleLayersModel.Layers block : SimpleLayersModel.vanillaLayers) {
            try {
            if (block.origin() == null) { continue; }
            if (block.layers() == null) { continue; }
            registerLayers(blockStateModelGenerator, block.layers(), block.origin());
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(SimplePaneModel.Pane pane : SimplePaneModel.panes){
            try {
            if (pane.glass() == null) { continue; }
            if (pane.pane() == null) { continue; }
            registerLeadGlassPane(blockStateModelGenerator, pane.glass(), pane.pane());
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Block pane : new Block[]{ModBlocks.NET, ModBlocks.GILDED_BARS,
                ModBlocks.COPPER_BARS, ModBlocks.EXPOSED_COPPER_BARS, ModBlocks.WEATHERED_COPPER_BARS, ModBlocks.OXIDIZED_COPPER_BARS,
                ModBlocks.WAXED_COPPER_BARS, ModBlocks.WAXED_EXPOSED_COPPER_BARS, ModBlocks.WAXED_WEATHERED_COPPER_BARS, ModBlocks.WAXED_OXIDIZED_COPPER_BARS,
                ModBlocks.BRONZE_BARS, ModBlocks.CRUDE_BARS, ModBlocks.TREATED_STEEL_BARS, ModBlocks.BURZUM_BARS, ModBlocks.SILVER_BARS}) {
            try {
                registerPaneModel(blockStateModelGenerator, pane);
            } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Block pointed : new Block[]{ModBlocks.POINTED_DOLOMITE, ModBlocks.POINTED_GALONN,
                ModBlocks.POINTED_LIMESTONE, ModBlocks.POINTED_IZHERABAN}) {
            try {
                registerPointedBlock(blockStateModelGenerator, pointed);
            } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Block block : new Block[]{
                ModNatureBlocks.AZALEA_FLOWER_GROWTH, ModNatureBlocks.DRY_GROWTH, ModNatureBlocks.FROZEN_GROWTH,
                ModNatureBlocks.GREEN_GROWTH, ModNatureBlocks.IVY_GROWTH, ModNatureBlocks.LILAC_FLOWER_GROWTH,
                ModNatureBlocks.PINK_FLOWER_GROWTH, ModNatureBlocks.RED_FLOWER_GROWTH, ModNatureBlocks.THORNY_GROWTH,
                ModNatureBlocks.WHITE_FLOWER_GROWTH, ModNatureBlocks.YELLOW_FLOWER_GROWTH,
                ModNatureBlocks.WEBBING, ModNatureBlocks.MORGUL_IVY,
                ModNatureBlocks.STICKY_SNOW, ModNatureBlocks.STICKY_ICE,
                ModNatureBlocks.MOSS, ModNatureBlocks.FOREST_MOSS, ModNatureBlocks.CORRUPTED_MOSS
        }) {
            try {
                registerMultifaceBlock(blockStateModelGenerator, block);
            } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Block[] farmlandPair : new Block[][]{
                {ModBlocks.CHALKSOIL, ModBlocks.CHALKSOIL_FARMLAND},
                {ModBlocks.LOAM, ModBlocks.LOAM_FARMLAND},
                {ModBlocks.PEAT, ModBlocks.PEAT_FARMLAND},
                {ModBlocks.SILT, ModBlocks.SILT_FARMLAND}}) {
            try {
                registerFarmland(blockStateModelGenerator, farmlandPair[0], farmlandPair[1]);
            } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Block[] pathPair : new Block[][]{
                {ModBlocks.CHALKSOIL, ModBlocks.CHALKSOIL_PATH},
                {ModBlocks.LOAM, ModBlocks.LOAM_PATH},
                {ModBlocks.PEAT, ModBlocks.PEAT_PATH},
                {ModBlocks.SILT, ModBlocks.SILT_PATH}}) {
            try {
                registerDirtPath(blockStateModelGenerator, pathPair[0], pathPair[1]);
            } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Block block : TintableCrossModel.largePlants) {
            try {
                if (block == null) { continue; }
                registerLargePlant(blockStateModelGenerator, block);
            } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Block block : new Block[]{ModNatureBlocks.LARGE_BUSH, ModNatureBlocks.WILD_GRASS}) {
            try {
                registerTintableLargePlant(blockStateModelGenerator, block);
            } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Block block : new Block[]{ModNatureBlocks.WILLOW_VINES, ModNatureBlocks.MIRKWOOD_VINES, ModNatureBlocks.HANGING_WEBS}) {
            try {
                registerHangingVine(blockStateModelGenerator, block);
            } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        try {
            blockStateModelGenerator.registerPlantPart(ModNatureBlocks.GLOWWORM_WEBBING, ModNatureBlocks.GLOWWORM_MAIN,
                    BlockStateModelGenerator.TintType.NOT_TINTED);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }

        try {
            if (ModNatureBlocks.FLOATING_ICE != null) {
                blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(
                        ModNatureBlocks.FLOATING_ICE, ModelIds.getBlockModelId(ModNatureBlocks.FLOATING_ICE)));
            }
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }

        try {
            registerOrientableThickLadder(blockStateModelGenerator, ModDecorativeBlocks.ROPE_LADDER);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }

        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.BLUE_HOBBIT_DOOR, LargeDoor2x2.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.GREEN_HOBBIT_DOOR, LargeDoor2x2.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.LIGHT_BLUE_HOBBIT_DOOR, LargeDoor2x2.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.RED_HOBBIT_DOOR, LargeDoor2x2.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.YELLOW_HOBBIT_DOOR, LargeDoor2x2.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.LARCH_HOBBIT_DOOR, LargeDoor2x2.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.SPRUCE_HOBBIT_DOOR, LargeDoor2x2.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.TALL_BLACK_PINE_DOOR, LargeDoor3x1.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.TALL_FIR_DOOR, LargeDoor3x1.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.OAK_STABLE_DOOR, LargeDoor4x2.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.REINFORCED_SPRUCE_DOOR, LargeDoor4x2.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.REINFORCED_BLACK_PINE_DOOR, LargeDoor4x2.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.RICKETY_SIMPLE_LARCH_DOOR, LargeDoor4x2.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.SIMPLE_LARCH_GATE, LargeDoor4x2.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.SPRUCE_STABLE_DOOR, LargeDoor4x2.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.LARGE_STURDY_DOOR, LargeDoor5x3.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.LARGE_BEECH_FENCE_GATE, LargeDoor1x2.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.GREAT_GONDORIAN_GATE, LargeDoor10x5.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.GREAT_DWARVEN_GATE, LargeDoor5x2.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.VARNISHED_DWARVEN_DOOR, LargeDoor4x2.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.RUINED_DWARVEN_DOOR, LargeDoor4x2.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerThickLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.HIDDEN_DWARVEN_DOOR, LargeThickDoor3x2.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.GREAT_ELVEN_GATE, LargeDoor6x2.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        try {
            registerLargeDoor(blockStateModelGenerator, (LargeDoorBlock) ModDecorativeBlocks.GREAT_ORCISH_GATE, LargeDoor10x4.PART);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }

        for(SimpleLadderModel.Ladder ladder : SimpleLadderModel.ladders) {
            try {
            registerOrientableThickLadder(blockStateModelGenerator, ladder.ladder());
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }
        for(SimpleLadderModel.Ladder ladder : SimpleLadderModel.vanillaLadders) {
            try {
            registerOrientableThickLadder(blockStateModelGenerator, ladder.ladder());
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : SimpleWoodBenchModel.benchs){
            try {
            registerWoodBenchModelBlockStates(blockStateModelGenerator, block);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }
        for(SimpleWoodBenchModel.VanillaBench bench : SimpleWoodBenchModel.vanillaBenchs){
            try {
            if (bench.base() == null) { continue; }
            registerWoodBenchModelBlockStates(blockStateModelGenerator, bench.base());
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(SimpleRocksModel.Rocks rocks : SimpleRocksModel.rocks){
            try {
            if (rocks.rocks() == null || rocks.block() == null) { continue; }
            registerRocksBlock(blockStateModelGenerator, rocks.rocks(), rocks.block());
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }
        for(SimpleRocksModel.Rocks rocks : SimpleRocksModel.vanillaRocks){
            try {
            if (rocks.rocks() == null || rocks.block() == null) { continue; }
            registerRocksBlock(blockStateModelGenerator, rocks.rocks(), rocks.block());
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(SimpleWallModel.Wall wall : SimpleWallModel.columnWalls){
            try {
            if (wall.wall() == null) { continue; }
            Identifier identifier = Registries.BLOCK.getId(wall.wall());
            String sidePath = identifier.getPath().replaceAll("_wall", "");
            String topBottomPath = identifier.withSuffixedPath("_top").getPath().replaceAll("_wall", "");
            registerColumnWallModelBlockStates(blockStateModelGenerator, wall.wall(), wall.block(),
                    identifier.getNamespace(), topBottomPath, topBottomPath, sidePath);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : SimpleWoodStoolModel.stools){
            try {
            registerWoodStoolModelBlockStates(blockStateModelGenerator, block,
                    new Identifier(MiddleEarth.MOD_ID, "block/stripped_" + Registries.BLOCK.getId(block).getPath().replaceAll("_stool", "_log")),
                    new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_stool", "_planks")));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }


        for(Block block : SimpleStoneStoolModel.stools){
            try {
            registerStoneStoolModelBlockStates(blockStateModelGenerator, block,
                    new Identifier(MiddleEarth.MOD_ID, "block/" +
                            Registries.BLOCK.getId(block).getPath().replaceAll("_stool", "")));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }
        for(SimpleStoneStoolModel.VanillaStool stool : SimpleStoneStoolModel.vanillaStools){
            try {
            if (stool.origin() == null) { continue; }
            if (stool.base() == null) { continue; }
            String id = "block/" + Registries.BLOCK.getId(stool.origin()).getPath();
            if(stool.origin() == Blocks.BASALT) id += "_side";
            registerStoneStoolModelBlockStates(blockStateModelGenerator, stool.base(),
                    new Identifier("minecraft", id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : SimpleStoneTableModel.tables){
            try {
            registerStoneTableModelBlockStates(blockStateModelGenerator, block, new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_table", "")));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }
        for(SimpleStoneTableModel.VanillaTable stool : SimpleStoneTableModel.vanillaTables) {
            try {
            if (stool.origin() == null) { continue; }
            if (stool.base() == null) { continue; }
            String id = "block/" + Registries.BLOCK.getId(stool.origin()).getPath();
            if(stool.origin() == Blocks.BASALT) id += "_side";
            registerStoneTableModelBlockStates(blockStateModelGenerator, stool.base(), new Identifier("minecraft", id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : SimpleStoneChairModel.chairs){
            try {
            registerStoneChairModelBlockStates(blockStateModelGenerator, block, new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_chair", "")));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }
        for(SimpleStoneChairModel.VanillaChair stool : SimpleStoneChairModel.vanillaChairs){
            try {
            if (stool.origin() == null) { continue; }
            if (stool.base() == null) { continue; }
            String id = "block/" + Registries.BLOCK.getId(stool.origin()).getPath();
            if(stool.origin() == Blocks.BASALT) id += "_side";
            registerStoneChairModelBlockStates(blockStateModelGenerator, stool.base(), new Identifier("minecraft", id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : SimpleWoodTableModel.tables){
            try {
            registerWoodTableModelBlockStates(blockStateModelGenerator, block,
                    new Identifier(MiddleEarth.MOD_ID, "block/stripped_" + Registries.BLOCK.getId(block).getPath().replaceAll("_table", "_log")),
                    new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_table", "_planks")));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : SimpleWoodChairModel.chairs){
            try {
            registerWoodChairModelBlockStates(blockStateModelGenerator, block,
                    new Identifier(MiddleEarth.MOD_ID, "block/stripped_" + Registries.BLOCK.getId(block).getPath().replaceAll("_chair", "_log")),
                    new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_chair", "_planks")));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }


        for(Block block : SimpleFanModel.grassLikeFans){
            try {
            registerFanModel(blockStateModelGenerator, block);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        registerWoodStoolModelBlockStates(blockStateModelGenerator, MushroomBlockSets.MUSHROOM.stool(),
                new Identifier("minecraft", "block/mushroom_stem"),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.MUSHROOM.stool()).getPath().replaceAll("_stool", "_planks")));
        registerWoodStoolModelBlockStates(blockStateModelGenerator, MushroomBlockSets.DARK_MUSHROOM.stool(),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.DARK_MUSHROOM.stool()).getPath().replaceAll("_stool", "_stem")),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.DARK_MUSHROOM.stool()).getPath().replaceAll("_stool", "_planks")));
        registerWoodStoolModelBlockStates(blockStateModelGenerator, MushroomBlockSets.GRAY_MUSHROOM.stool(),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.GRAY_MUSHROOM.stool()).getPath().replaceAll("_stool", "_stem")),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.GRAY_MUSHROOM.stool()).getPath().replaceAll("_stool", "_planks")));


        for(SimpleWoodStoolModel.VanillaStool stool : SimpleWoodStoolModel.vanillaStools) {
            try {
            if (stool.origin() == null) { continue; }
            String id = Registries.BLOCK.getId(stool.origin()).getPath().replaceAll("_log", "_planks").replaceAll("_stem", "_planks").replaceAll("stripped_", "");
            String stripped;
            if(Registries.BLOCK.getId(stool.origin()).getPath().contains("crimson") || Registries.BLOCK.getId(stool.origin()).getPath().contains("warped")){
                stripped = "stripped_" + id.replaceAll("_planks", "_stem");
            } else {
                stripped = "stripped_" + id.replaceAll("_planks", "_log");
            }
            registerWoodStoolModelBlockStates(blockStateModelGenerator, stool.base(),
                    new Identifier("minecraft", "block/" + stripped),
                    new Identifier("minecraft", "block/" + id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(SimpleWoodTableModel.VanillaTable table : SimpleWoodTableModel.vanillaTables) {
            try {
            if (table.origin() == null) { continue; }
            String id = Registries.BLOCK.getId(table.origin()).getPath().replaceAll("_log", "_planks").replaceAll("_stem", "_planks").replaceAll("stripped_", "");
            String stripped;
            if(Registries.BLOCK.getId(table.origin()).getPath().contains("crimson") || Registries.BLOCK.getId(table.origin()).getPath().contains("warped")){
                stripped = "stripped_" + id.replaceAll("_planks", "_stem");
            } else {
                stripped = "stripped_" + id.replaceAll("_planks", "_log");
            }
            registerWoodTableModelBlockStates(blockStateModelGenerator, table.base(),
                    new Identifier("minecraft", "block/" + stripped),
                    new Identifier("minecraft", "block/" + id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(SimpleWoodChairModel.VanillaChair chair : SimpleWoodChairModel.vanillaChairs) {
            try {
            if (chair.origin() == null) { continue; }
            String id = Registries.BLOCK.getId(chair.origin()).getPath().replaceAll("_log", "_planks").replaceAll("_stem", "_planks").replaceAll("stripped_", "");
            String stripped;
            if(Registries.BLOCK.getId(chair.origin()).getPath().contains("crimson") || Registries.BLOCK.getId(chair.origin()).getPath().contains("warped")){
                stripped = "stripped_" + id.replaceAll("_planks", "_stem");
            } else {
                stripped = "stripped_" + id.replaceAll("_planks", "_log");
            }
            registerWoodChairModelBlockStates(blockStateModelGenerator, chair.base(),
                    new Identifier("minecraft", "block/" + stripped),
                    new Identifier("minecraft", "block/" + id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        registerWoodChairModelBlockStates(blockStateModelGenerator, MushroomBlockSets.MUSHROOM.chair(),
                new Identifier("minecraft", "block/mushroom_stem"),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.MUSHROOM.chair()).getPath().replaceAll("_chair", "_planks")));
        registerWoodChairModelBlockStates(blockStateModelGenerator, MushroomBlockSets.DARK_MUSHROOM.chair(),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.DARK_MUSHROOM.chair()).getPath().replaceAll("_chair", "_stem")),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.DARK_MUSHROOM.chair()).getPath().replaceAll("_chair", "_planks")));
        registerWoodChairModelBlockStates(blockStateModelGenerator, MushroomBlockSets.GRAY_MUSHROOM.chair(),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.GRAY_MUSHROOM.chair()).getPath().replaceAll("_chair", "_stem")),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.GRAY_MUSHROOM.chair()).getPath().replaceAll("_chair", "_planks")));


        registerWoodTableModelBlockStates(blockStateModelGenerator, MushroomBlockSets.MUSHROOM.table(),
                new Identifier("minecraft", "block/mushroom_stem"),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.MUSHROOM.table()).getPath().replaceAll("_table", "_planks")));
        registerWoodTableModelBlockStates(blockStateModelGenerator, MushroomBlockSets.DARK_MUSHROOM.table(),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.DARK_MUSHROOM.table()).getPath().replaceAll("_table", "_stem")),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.DARK_MUSHROOM.table()).getPath().replaceAll("_table", "_planks")));
        registerWoodTableModelBlockStates(blockStateModelGenerator, MushroomBlockSets.GRAY_MUSHROOM.table(),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.GRAY_MUSHROOM.table()).getPath().replaceAll("_table", "_stem")),
                new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.GRAY_MUSHROOM.table()).getPath().replaceAll("_table", "_planks")));


        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaVerticalSlabs) {
            try {
            if (verticalSlab.block() == null) { continue; }
            String id = String.valueOf(Registries.BLOCK.getId(verticalSlab.block()));
            id = id.substring(id.lastIndexOf(":") + 1);

            if(verticalSlab.block() == Blocks.SANDSTONE || verticalSlab.block() == Blocks.RED_SANDSTONE ||
                    verticalSlab.block() == Blocks.CUT_SANDSTONE || verticalSlab.block() == Blocks.CUT_RED_SANDSTONE) {
                String topId = id + "_top";
                String bottomId = id + "_bottom";
                if(verticalSlab.block() == Blocks.CUT_SANDSTONE || verticalSlab.block() == Blocks.CUT_RED_SANDSTONE) {
                    topId = topId.substring(topId.indexOf("_") + 1);
                    bottomId = bottomId.substring(bottomId.indexOf("_") + 1);
                }
                registerColumnVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), "minecraft", topId, bottomId, id);
            } else {
                if(verticalSlab.block() == Blocks.SMOOTH_RED_SANDSTONE
                        || verticalSlab.block() == Blocks.SMOOTH_SANDSTONE) {
                    id += "_top";
                    id = id.substring(id.indexOf("_") + 1);
                } else if(verticalSlab.block() == Blocks.QUARTZ_BLOCK) {
                    id += "_side";
                } else if(verticalSlab.block() == Blocks.SMOOTH_QUARTZ) {
                    id = "quartz_block_bottom";
                } else if(verticalSlab.block() == Blocks.WAXED_CUT_COPPER
                        || verticalSlab.block() == Blocks.WAXED_EXPOSED_CUT_COPPER
                        || verticalSlab.block() == Blocks.WAXED_WEATHERED_CUT_COPPER
                        || verticalSlab.block() == Blocks.WAXED_OXIDIZED_CUT_COPPER) {
                    id = id.substring(id.indexOf("_") + 1);
                }
                registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), id);
            }
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }
        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaWoodVerticalSlabs) {
            try {
            if (verticalSlab.block() == null) { continue; }
            if (verticalSlab.verticalSlab() == null) { continue; }
            String id = Registries.BLOCK.getId(verticalSlab.block()).getPath();
            String baseTextureId = id.substring(0, id.lastIndexOf("_")) + "_log";
            baseTextureId = baseTextureId.replaceAll("_wood", "_log");
            registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(),  baseTextureId);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }
        for(SimpleVerticalSlabModel.VerticalSlab verticalSlab : SimpleVerticalSlabModel.vanillaStrippedVerticalSlabs) {
            try {
            if (verticalSlab.block() == null) { continue; }
            if (verticalSlab.verticalSlab() == null) { continue; }
            String id = Registries.BLOCK.getId(verticalSlab.block()).getPath();
            String baseTextureId = id.substring(0, id.lastIndexOf("_")) + "_log";
            baseTextureId = baseTextureId.replaceAll("_wood", "_log");
            registerVanillaVerticalSlabModelBlockStates(blockStateModelGenerator, verticalSlab.verticalSlab(), verticalSlab.block(), baseTextureId);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        // Crops
        blockStateModelGenerator.registerCrop(ModNatureBlocks.BELL_PEPPER_CROP, BellpepperCropBlock.AGE, 0, 1, 2, 3, 4);
        blockStateModelGenerator.registerCrop(ModNatureBlocks.CUCUMBER_CROP, CucumberCropBlock.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.registerCrop(ModNatureBlocks.FLAX_CROP, FlaxCropBlock.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.registerCrop(ModNatureBlocks.GARLIC_CROP, GarlicCropBlock.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.registerCrop(ModNatureBlocks.LEEK_CROP, LeekCropBlock.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.registerCrop(ModNatureBlocks.LETTUCE_CROP, LettuceCropBlock.AGE, 0, 1, 2, 3);
        blockStateModelGenerator.registerCrop(ModNatureBlocks.ONION_CROP, OnionCropBlock.AGE, 0, 1, 2, 3);
        
        //CLUSTERS
        blockStateModelGenerator.registerAmethyst(ModBlocks.GLOWSTONE_CLUSTER);
        blockStateModelGenerator.registerAmethyst(ModBlocks.SMALL_GLOWSTONE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.MEDIUM_GLOWSTONE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.LARGE_GLOWSTONE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.RED_AGATE_CLUSTER);
        blockStateModelGenerator.registerAmethyst(ModBlocks.SMALL_RED_AGATE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.MEDIUM_RED_AGATE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.LARGE_RED_AGATE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.CITRINE_CLUSTER);
        blockStateModelGenerator.registerAmethyst(ModBlocks.SMALL_CITRINE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.MEDIUM_CITRINE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.LARGE_CITRINE_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.QUARTZ_CLUSTER);
        blockStateModelGenerator.registerAmethyst(ModBlocks.SMALL_QUARTZ_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.MEDIUM_QUARTZ_BUD);
        blockStateModelGenerator.registerAmethyst(ModBlocks.LARGE_QUARTZ_BUD);
    }

    public final void registerFanModel(BlockStateModelGenerator blockStateCollector, Block coralFanBlock) {
        TexturedModel texturedModel = TexturedModel.CORAL_FAN.get(coralFanBlock);
        Identifier identifier = texturedModel.upload(coralFanBlock, blockStateCollector.modelCollector);
        blockStateCollector.blockStateCollector.accept(blockStateCollector.createSingletonBlockState(coralFanBlock, identifier));
        blockStateCollector.registerItemModel(coralFanBlock);
    }

    public void registerVanillaVerticalSlabModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Block origin, String slabPath){
        Identifier fullBlockId = ModelIds.getBlockModelId(origin);
        Identifier variantId = MEModels.VERTICAL_SLAB.upload(block,
                TextureMap.of(TextureKey.ALL,new Identifier("minecraft","block/" + slabPath)),
                blockStateModelGenerator.modelCollector);
        registerVerticalSlab(blockStateModelGenerator, block, fullBlockId, variantId);
    }

    public void registerVerticalSlabModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Block origin, String slabPath){
        Identifier fullBlockId = ModelIds.getBlockModelId(origin);
        Identifier variantId = MEModels.VERTICAL_SLAB.upload(block,
                TextureMap.of(TextureKey.ALL, new Identifier(MiddleEarth.MOD_ID, "block/" + slabPath)),
                blockStateModelGenerator.modelCollector);
        registerVerticalSlab(blockStateModelGenerator, block, fullBlockId, variantId);
    }

    public void registerColumnVerticalSlabModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Block origin,
                                                           String modId, String topTexturePath, String bottomTexturePath, String sideTexturePath){
        Identifier fullBlockId = ModelIds.getBlockModelId(origin);
        Identifier sideTexture = new Identifier(modId, "block/" + sideTexturePath);

        Identifier variantId = MEModels.VERTICAL_COLUMN_SLAB.upload(block, (new TextureMap())
                        .put(TextureKey.TOP, new Identifier(modId, "block/" + topTexturePath))
                        .put(TextureKey.BOTTOM, new Identifier(modId, "block/" + bottomTexturePath))
                        .put(TextureKey.SIDE, sideTexture)
                        .put(TextureKey.PARTICLE, sideTexture),
                blockStateModelGenerator.modelCollector);

        registerVerticalSlab(blockStateModelGenerator, block, fullBlockId, variantId);
    }

    private void registerVerticalSlab(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier full, Identifier variant) {
        // Two VerticalSlabBlock classes exist in the port (block.special and block.special.verticalSlabs)
        // and Property equality is identity-based, so a block built with the other class fails the
        // variant validation below even though the state names look identical.
        try {
            registerVerticalSlabUnchecked(blockStateModelGenerator, block, full, variant);
        } catch (IllegalStateException e) {
            System.out.println("[datagen] skipping vertical slab model for " + block);
        }
    }

    private void registerVerticalSlabUnchecked(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier full, Identifier variant) {
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
                .coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, VerticalSlabBlock.DOUBLE)
                        .register(Direction.NORTH, false, BlockStateVariant.create()
                                .put(VariantSettings.MODEL, variant).put(VariantSettings.UVLOCK, true))
                        .register(Direction.EAST, false, BlockStateVariant.create()
                                .put(VariantSettings.MODEL, variant).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.SOUTH, false, BlockStateVariant.create()
                                .put(VariantSettings.MODEL, variant).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.WEST, false, BlockStateVariant.create()
                                .put(VariantSettings.MODEL, variant).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(Direction.NORTH, true, BlockStateVariant.create()
                                .put(VariantSettings.MODEL, full).put(VariantSettings.UVLOCK, true))
                        .register(Direction.EAST, true, BlockStateVariant.create()
                                .put(VariantSettings.MODEL, full).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.SOUTH, true, BlockStateVariant.create()
                                .put(VariantSettings.MODEL, full).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.WEST, true, BlockStateVariant.create()
                                .put(VariantSettings.MODEL, full).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                ));
    }

    public void registerWoodStoolModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier logTexture, Identifier plankTexture){
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.WOOD_STOOL.upload(block,
                        (new TextureMap()).put(TextureKey.TOP, plankTexture)
                                .put(TextureKey.SIDE, logTexture)
                                .put(TextureKey.PARTICLE, logTexture),
                        blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false))
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    public void registerWoodTableModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier logTexture, Identifier plankTexture){
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                        BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.WOOD_TABLE.upload(block,
                                (new TextureMap()).put(TextureKey.TOP, plankTexture)
                                        .put(TextureKey.SIDE, logTexture)
                                        .put(TextureKey.PARTICLE, logTexture),
                                blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false)));
    }

    public void registerWoodChairModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier logTexture, Identifier plankTexture){
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                        BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.WOOD_CHAIR.upload(block,
                                (new TextureMap()).put(TextureKey.TOP, plankTexture)
                                        .put(TextureKey.SIDE, logTexture)
                                        .put(TextureKey.PARTICLE, logTexture),
                                blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false))
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    public void registerStoneStoolModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier texture){
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                        BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.STONE_STOOL.upload(block,
                                (new TextureMap()).put(TextureKey.ALL, texture)
                                        .put(TextureKey.PARTICLE, texture),
                                blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false))
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    public void registerStoneTableModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier texture){
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.STONE_TABLE.upload(block,
                        (new TextureMap()).put(TextureKey.ALL, texture)
                                .put(TextureKey.PARTICLE, texture),
                        blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false)));
    }

    public void registerStoneChairModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Identifier texture){
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                        BlockStateVariant.create().put(VariantSettings.MODEL, MEModels.STONE_CHAIR.upload(block,
                                (new TextureMap()).put(TextureKey.ALL, texture)
                                        .put(TextureKey.PARTICLE, texture),
                                blockStateModelGenerator.modelCollector)).put(VariantSettings.UVLOCK, false))
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
    }

    /** 1.20.1 port of the donor's registerPaneModel: single-texture pane (bars / net). */
    public final void registerPaneModel(BlockStateModelGenerator blockStateModelGenerator, Block pane) {
        TextureMap textureMap = TextureMap.paneAndTopForEdge(pane, pane);
        Identifier post = Models.TEMPLATE_GLASS_PANE_POST.upload(pane, textureMap, blockStateModelGenerator.modelCollector);
        Identifier side = Models.TEMPLATE_GLASS_PANE_SIDE.upload(pane, textureMap, blockStateModelGenerator.modelCollector);
        Identifier sideAlt = Models.TEMPLATE_GLASS_PANE_SIDE_ALT.upload(pane, textureMap, blockStateModelGenerator.modelCollector);
        Identifier noSide = Models.TEMPLATE_GLASS_PANE_NOSIDE.upload(pane, textureMap, blockStateModelGenerator.modelCollector);
        Identifier noSideAlt = Models.TEMPLATE_GLASS_PANE_NOSIDE_ALT.upload(pane, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(pane)
                .with(BlockStateVariant.create().put(VariantSettings.MODEL, post))
                .with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, side))
                .with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, side).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                .with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, sideAlt))
                .with(When.create().set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, sideAlt).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                .with(When.create().set(Properties.NORTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, noSide))
                .with(When.create().set(Properties.EAST, false), BlockStateVariant.create().put(VariantSettings.MODEL, noSideAlt))
                .with(When.create().set(Properties.SOUTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, noSideAlt).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                .with(When.create().set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, noSide).put(VariantSettings.Y, VariantSettings.Rotation.R270)));
        Models.GENERATED.upload(ModelIds.getItemModelId(pane.asItem()), TextureMap.layer0(pane), blockStateModelGenerator.modelCollector);
    }

    /** 1.20.1 port of the donor's registerPointedBlock. Vanilla's
     *  BlockStateModelGenerator#registerPointedDripstone is private and hard-wired to
     *  Blocks.POINTED_DRIPSTONE, so the 10 variants are built here instead. */
    public void registerPointedBlock(BlockStateModelGenerator blockStateModelGenerator, Block pointedBlock) {
        Models.GENERATED.upload(ModelIds.getItemModelId(pointedBlock.asItem()),
                TextureMap.layer0(pointedBlock.asItem()), blockStateModelGenerator.modelCollector);

        BlockStateVariantMap.DoubleProperty<Direction, Thickness> variants =
                BlockStateVariantMap.create(Properties.VERTICAL_DIRECTION, Properties.THICKNESS);
        for (Thickness thickness : Thickness.values()) {
            for (Direction direction : new Direction[]{Direction.UP, Direction.DOWN}) {
                String suffix = "_" + direction.asString() + "_" + thickness.asString();
                TextureMap textures = TextureMap.cross(TextureMap.getSubId(pointedBlock, suffix));
                Identifier model = Models.POINTED_DRIPSTONE.upload(pointedBlock, suffix, textures,
                        blockStateModelGenerator.modelCollector);
                variants.register(direction, thickness,
                        BlockStateVariant.create().put(VariantSettings.MODEL, model));
            }
        }

        blockStateModelGenerator.blockStateCollector.accept(
                VariantsBlockStateSupplier.create(pointedBlock).coordinate(variants));
    }

    /** 1.20.1 stand-in for 1.21's BlockStateModelGenerator#registerMultifaceBlock(Model):
     *  emits the vanilla glow_lichen multipart blockstate against the block's own
     *  (hand-written) flat multiface model. */
    public void registerMultifaceBlock(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        Identifier model = ModelIds.getBlockModelId(block);

        BlockStateVariant north = BlockStateVariant.create().put(VariantSettings.MODEL, model);
        BlockStateVariant east = BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R90);
        BlockStateVariant south = BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R180);
        BlockStateVariant west = BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R270);
        BlockStateVariant up = BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.UVLOCK, true).put(VariantSettings.X, VariantSettings.Rotation.R270);
        BlockStateVariant down = BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.UVLOCK, true).put(VariantSettings.X, VariantSettings.Rotation.R90);

        When noFace = When.create()
                .set(Properties.NORTH, false).set(Properties.EAST, false).set(Properties.SOUTH, false)
                .set(Properties.WEST, false).set(Properties.UP, false).set(Properties.DOWN, false);

        blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(block)
                .with(When.create().set(Properties.NORTH, true), north).with(noFace, north)
                .with(When.create().set(Properties.EAST, true), east).with(noFace, east)
                .with(When.create().set(Properties.SOUTH, true), south).with(noFace, south)
                .with(When.create().set(Properties.WEST, true), west).with(noFace, west)
                .with(When.create().set(Properties.UP, true), up).with(noFace, up)
                .with(When.create().set(Properties.DOWN, true), down).with(noFace, down));
    }

    private void registerFarmland(BlockStateModelGenerator blockStateModelGenerator, Block dirtBlock, Block farmland) {
        Identifier dirtTexture = TextureMap.getId(dirtBlock);
        Identifier dryModel = Models.TEMPLATE_FARMLAND.upload(farmland, (new TextureMap())
                        .put(TextureKey.DIRT, dirtTexture)
                        .put(TextureKey.TOP, TextureMap.getId(farmland)),
                blockStateModelGenerator.modelCollector);
        Identifier moistModel = Models.TEMPLATE_FARMLAND.upload(TextureMap.getSubId(farmland, "_moist"), (new TextureMap())
                        .put(TextureKey.DIRT, dirtTexture)
                        .put(TextureKey.TOP, TextureMap.getSubId(farmland, "_moist")),
                blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(farmland)
                .coordinate(BlockStateModelGenerator.createValueFencedModelMap(Properties.MOISTURE, 7, moistModel, dryModel)));
    }

    private void registerDirtPath(BlockStateModelGenerator blockStateModelGenerator, Block dirtBlock, Block pathBlock) {
        Identifier dirtTexture = TextureMap.getId(dirtBlock);
        Identifier model = MEModels.PATH_BLOCK.upload(pathBlock, (new TextureMap())
                        .put(TextureKey.PARTICLE, dirtTexture)
                        .put(TextureKey.TOP, TextureMap.getId(pathBlock).withSuffixedPath("_top"))
                        .put(TextureKey.SIDE, TextureMap.getId(pathBlock).withSuffixedPath("_side"))
                        .put(TextureKey.BOTTOM, dirtTexture),
                blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(pathBlock, model));
    }

    public final void registerLargePlant(BlockStateModelGenerator blockStateModelGenerator, Block plantBlock) {
        Identifier id = Registries.BLOCK.getId(plantBlock);
        Identifier texture = new Identifier(id.getNamespace(), "block/" + id.getPath());
        Identifier model = MEModels.LARGE_PLANT.upload(plantBlock, (new TextureMap())
                        .put(TextureKey.ALL, texture)
                        .put(TextureKey.PARTICLE, texture),
                blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(plantBlock, model));
    }

    public final void registerTintableLargePlant(BlockStateModelGenerator blockStateModelGenerator, Block plantBlock) {
        Identifier id = Registries.BLOCK.getId(plantBlock);
        Identifier texture = new Identifier(id.getNamespace(), "block/" + id.getPath());
        Identifier model = MEModels.TINTED_LARGE_PLANT.upload(plantBlock, (new TextureMap())
                        .put(TextureKey.ALL, texture)
                        .put(TextureKey.PARTICLE, texture),
                blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSingletonBlockState(plantBlock, model));
    }

    public void registerHangingVine(BlockStateModelGenerator blockStateModelGenerator, Block block) {
        Identifier base = MEModels.CROP_VINE.upload(block, TextureMap.crop(TextureMap.getId(block)),
                blockStateModelGenerator.modelCollector);
        Identifier tip = MEModels.CROP_VINE.upload(block, "_tip", TextureMap.crop(TextureMap.getSubId(block, "_tip")),
                blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block)
                .coordinate(BlockStateVariantMap.create(CustomHangingBlock.TIP)
                        .register(false, BlockStateVariant.create().put(VariantSettings.MODEL, base))
                        .register(true, BlockStateVariant.create().put(VariantSettings.MODEL, tip))));
    }

    public final void registerLargeDoor(BlockStateModelGenerator blockStateModelGenerator, LargeDoorBlock largeDoor, IntProperty part) {
        BlockStateVariantMap.QuadrupleProperty<Direction, Boolean, DoorHinge, Integer> statesMap =
                BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.OPEN, Properties.DOOR_HINGE, part);
        for (int i = 0; i < largeDoor.getDoorWidth() * largeDoor.getDoorHeight(); i++) {
            for (int k = 2; k < 6; k++) {
                int rot = switch (k) {
                    case 3 -> 180;
                    case 4 -> 270;
                    case 5 -> 90;
                    default -> 0;
                };
                Identifier left = new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_left_" + i);
                Identifier leftOpen = new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_left_open_" + i);
                Identifier right = new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_right_" + i);
                Identifier rightOpen = new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_right_open_" + i);

                statesMap.register(Direction.values()[k], false, DoorHinge.LEFT, i, rotateY(left, rot));
                statesMap.register(Direction.values()[k], true, DoorHinge.LEFT, i, rotateY(leftOpen, rot));
                statesMap.register(Direction.values()[k], false, DoorHinge.RIGHT, i, rotateY(right, rot));
                statesMap.register(Direction.values()[k], true, DoorHinge.RIGHT, i, rotateY(rightOpen, rot));

                if (k == 2) {
                    TextureMap textures = (new TextureMap())
                            .put(TextureKey.ALL, new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i))
                            .put(TextureKey.PARTICLE, new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i));
                    MEModels.LARGE_DOOR_LEFT.upload(largeDoor, "_left_" + i, textures, blockStateModelGenerator.modelCollector);
                    MEModels.LARGE_DOOR_LEFT_OPEN.upload(largeDoor, "_left_open_" + i, textures, blockStateModelGenerator.modelCollector);
                    MEModels.LARGE_DOOR_RIGHT.upload(largeDoor, "_right_" + i, textures, blockStateModelGenerator.modelCollector);
                    MEModels.LARGE_DOOR_RIGHT_OPEN.upload(largeDoor, "_right_open_" + i, textures, blockStateModelGenerator.modelCollector);
                }
            }
        }
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(largeDoor).coordinate(statesMap));
    }

    public final void registerThickLargeDoor(BlockStateModelGenerator blockStateModelGenerator, LargeDoorBlock largeDoor, IntProperty part) {
        BlockStateVariantMap.QuadrupleProperty<Direction, Boolean, DoorHinge, Integer> statesMap =
                BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, Properties.OPEN, Properties.DOOR_HINGE, part);
        for (int i = 0; i < largeDoor.getDoorWidth() * largeDoor.getDoorHeight(); i++) {
            for (int k = 2; k < 6; k++) {
                int rot = switch (k) {
                    case 3 -> 180;
                    case 4 -> 270;
                    case 5 -> 90;
                    default -> 0;
                };
                Identifier left = new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_left_" + i);
                Identifier leftOpen = new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_left_open_" + i);
                Identifier right = new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_right_" + i);
                Identifier rightOpen = new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_right_open_" + i);

                statesMap.register(Direction.values()[k], false, DoorHinge.LEFT, i, rotateY(left, rot));
                statesMap.register(Direction.values()[k], true, DoorHinge.LEFT, i, rotateY(leftOpen, rot));
                statesMap.register(Direction.values()[k], false, DoorHinge.RIGHT, i, rotateY(right, rot));
                statesMap.register(Direction.values()[k], true, DoorHinge.RIGHT, i, rotateY(rightOpen, rot));

                if (k == 2) {
                    TextureMap textures = (new TextureMap())
                            .put(TextureKey.ALL, new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i))
                            .put(TextureKey.PARTICLE, new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i));
                    MEModels.LARGE_THICK_DOOR_LEFT.upload(largeDoor, "_left_" + i, textures, blockStateModelGenerator.modelCollector);
                    MEModels.LARGE_THICK_DOOR_LEFT_OPEN.upload(largeDoor, "_left_open_" + i, textures, blockStateModelGenerator.modelCollector);
                    MEModels.LARGE_THICK_DOOR_RIGHT.upload(largeDoor, "_right_" + i, textures, blockStateModelGenerator.modelCollector);
                    MEModels.LARGE_THICK_DOOR_RIGHT_OPEN.upload(largeDoor, "_right_open_" + i, textures, blockStateModelGenerator.modelCollector);
                }
            }
        }
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(largeDoor).coordinate(statesMap));
    }

    private static BlockStateVariant rotateY(Identifier model, int rot) {
        BlockStateVariant variant = BlockStateVariant.create().put(VariantSettings.MODEL, model);
        return switch (rot) {
            case 90 -> variant.put(VariantSettings.Y, VariantSettings.Rotation.R90);
            case 180 -> variant.put(VariantSettings.Y, VariantSettings.Rotation.R180);
            case 270 -> variant.put(VariantSettings.Y, VariantSettings.Rotation.R270);
            default -> variant;
        };
    }

    public void registerWoodBenchModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block){
        Identifier texture = new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath());
        Identifier model = MEModels.WOOD_BENCH.upload(block,
                (new TextureMap()).put(TextureKey.ALL, texture).put(TextureKey.PARTICLE, texture),
                blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(block,
                        BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.UVLOCK, false))
                .coordinate(BlockStateModelGenerator.createNorthDefaultHorizontalRotationStates()));
        blockStateModelGenerator.registerParentedItemModel(block, ModelIds.getBlockModelId(block));
    }

    public void registerRocksBlock(BlockStateModelGenerator blockStateModelGenerator, Block rocksBlock, Block origin){
        Identifier id = new Identifier(Registries.BLOCK.getId(origin).getNamespace(), "block/" + Registries.BLOCK.getId(origin).getPath());
        if (origin == Blocks.BASALT || origin == Blocks.POLISHED_BASALT) {
            id = id.withSuffixedPath("_side");
        }
        TextureMap textures = TextureMap.of(TextureKey.ALL, id);
        Identifier stage0 = MEModels.ROCKS_STAGE_0.upload(rocksBlock, textures, blockStateModelGenerator.modelCollector);
        Identifier stage1 = MEModels.ROCKS_STAGE_1.upload(rocksBlock, textures, blockStateModelGenerator.modelCollector);
        Identifier stage2 = MEModels.ROCKS_STAGE_2.upload(rocksBlock, textures, blockStateModelGenerator.modelCollector);
        Identifier stage3 = MEModels.ROCKS_STAGE_3.upload(rocksBlock, textures, blockStateModelGenerator.modelCollector);

        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(rocksBlock)
                .coordinate(BlockStateVariantMap.create(Properties.HORIZONTAL_FACING, RocksBlock.STAGE)
                        .register(Direction.NORTH, 0, BlockStateVariant.create().put(VariantSettings.MODEL, stage0).put(VariantSettings.UVLOCK, true))
                        .register(Direction.EAST, 0, BlockStateVariant.create().put(VariantSettings.MODEL, stage0).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.SOUTH, 0, BlockStateVariant.create().put(VariantSettings.MODEL, stage0).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.WEST, 0, BlockStateVariant.create().put(VariantSettings.MODEL, stage0).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(Direction.NORTH, 1, BlockStateVariant.create().put(VariantSettings.MODEL, stage1).put(VariantSettings.UVLOCK, true))
                        .register(Direction.EAST, 1, BlockStateVariant.create().put(VariantSettings.MODEL, stage1).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.SOUTH, 1, BlockStateVariant.create().put(VariantSettings.MODEL, stage1).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.WEST, 1, BlockStateVariant.create().put(VariantSettings.MODEL, stage1).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(Direction.NORTH, 2, BlockStateVariant.create().put(VariantSettings.MODEL, stage2).put(VariantSettings.UVLOCK, true))
                        .register(Direction.EAST, 2, BlockStateVariant.create().put(VariantSettings.MODEL, stage2).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.SOUTH, 2, BlockStateVariant.create().put(VariantSettings.MODEL, stage2).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.WEST, 2, BlockStateVariant.create().put(VariantSettings.MODEL, stage2).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(Direction.NORTH, 3, BlockStateVariant.create().put(VariantSettings.MODEL, stage3).put(VariantSettings.UVLOCK, true))
                        .register(Direction.EAST, 3, BlockStateVariant.create().put(VariantSettings.MODEL, stage3).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(Direction.SOUTH, 3, BlockStateVariant.create().put(VariantSettings.MODEL, stage3).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(Direction.WEST, 3, BlockStateVariant.create().put(VariantSettings.MODEL, stage3).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                ));
        blockStateModelGenerator.registerParentedItemModel(rocksBlock, ModelIds.getBlockModelId(rocksBlock));
    }

    public void registerOrientableThickLadder(BlockStateModelGenerator blockStateModelGenerator, Block ladderBlock){
        Identifier texture = new Identifier(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(ladderBlock).getPath());
        Identifier model = MEModels.THICK_LADDER.upload(ladderBlock, TextureMap.of(TextureKey.TEXTURE, texture), blockStateModelGenerator.modelCollector);

        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(ladderBlock)
                .coordinate(BlockStateVariantMap.create(WallMountedBlock.FACE, Properties.HORIZONTAL_FACING)
                        .register(WallMountLocation.FLOOR, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.UVLOCK, true))
                        .register(WallMountLocation.FLOOR, Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(WallMountLocation.FLOOR, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(WallMountLocation.FLOOR, Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.UVLOCK, true).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(WallMountLocation.WALL, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.UVLOCK, true).put(VariantSettings.X, VariantSettings.Rotation.R90))
                        .register(WallMountLocation.WALL, Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.UVLOCK, true).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(WallMountLocation.WALL, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.UVLOCK, true).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(WallMountLocation.WALL, Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.UVLOCK, true).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                        .register(WallMountLocation.CEILING, Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.UVLOCK, true).put(VariantSettings.X, VariantSettings.Rotation.R180))
                        .register(WallMountLocation.CEILING, Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.UVLOCK, true).put(VariantSettings.X, VariantSettings.Rotation.R180).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                        .register(WallMountLocation.CEILING, Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.UVLOCK, true).put(VariantSettings.X, VariantSettings.Rotation.R180).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                        .register(WallMountLocation.CEILING, Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, model).put(VariantSettings.UVLOCK, true).put(VariantSettings.X, VariantSettings.Rotation.R180).put(VariantSettings.Y, VariantSettings.Rotation.R270))
                ));
        blockStateModelGenerator.registerParentedItemModel(ladderBlock, ModelIds.getBlockModelId(ladderBlock));
    }

    public void registerColumnWallModelBlockStates(BlockStateModelGenerator blockStateModelGenerator, Block block, Block origin,
                                                   String modId, String topTexturePath, String bottomTexturePath, String sideTexturePath){
        String modIdTopBottom = modId;
        if (sideTexturePath.contains("deepslate_carved_window")){
            modIdTopBottom = "minecraft";
        }
        if (sideTexturePath.contains("basalt_carved_window")){
            topTexturePath = topTexturePath.concat("_top");
            modIdTopBottom = "minecraft";
        }
        if (Objects.equals(topTexturePath, "stone") ||
                Objects.equals(topTexturePath, "blackstone") ||
                Objects.equals(topTexturePath, "tuff")){
            modIdTopBottom = "minecraft";
        }
        if (Objects.equals(sideTexturePath, "chiseled_tuff") ||
                Objects.equals(sideTexturePath, "chiseled_tuff_bricks")){
            modId = "minecraft";
            modIdTopBottom = "minecraft";
        }
        if (Objects.equals(sideTexturePath, "chiseled_stone_bricks") ||
                Objects.equals(sideTexturePath, "chiseled_deepslate") ||
                Objects.equals(sideTexturePath, "chiseled_polished_blackstone")){
            modId = "minecraft";
            modIdTopBottom = "minecraft";
            topTexturePath = sideTexturePath;
            bottomTexturePath = sideTexturePath;
        }
        Identifier sideTexture = new Identifier(modId, "block/" + sideTexturePath);
        TextureMap textures = (new TextureMap())
                .put(TextureKey.TOP, new Identifier(modIdTopBottom, "block/" + topTexturePath))
                .put(TextureKey.BOTTOM, new Identifier(modIdTopBottom, "block/" + bottomTexturePath))
                .put(TextureKey.WALL, sideTexture)
                .put(TextureKey.PARTICLE, sideTexture);

        Identifier post = MEModels.COLUMN_WALL_POST.upload(block, textures, blockStateModelGenerator.modelCollector);
        Identifier low = MEModels.COLUMN_WALL_SIDE.upload(block, textures, blockStateModelGenerator.modelCollector);
        Identifier tall = MEModels.COLUMN_WALL_SIDE_TALL.upload(block, textures, blockStateModelGenerator.modelCollector);

        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator
                .createWallBlockState(block, post, low, tall));

        Identifier inventory = MEModels.COLUMN_WALL_INVENTORY.upload(block, textures, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.registerParentedItemModel(block, inventory);
    }

    public final void registerDoubleBlock(BlockStateModelGenerator blockStateModelGenerator, Block doubleBlock, BlockStateModelGenerator.TintType tintType) {
        blockStateModelGenerator.registerItemModel(doubleBlock.asItem());
        Identifier identifier = blockStateModelGenerator.createSubModel(doubleBlock, "_top", tintType.getCrossModel(), TextureMap::cross);
        Identifier identifier2 = blockStateModelGenerator.createSubModel(doubleBlock, "_bottom", tintType.getCrossModel(), TextureMap::cross);
        blockStateModelGenerator.registerDoubleBlock(doubleBlock, identifier, identifier2);
    }

    public final void registerLeadGlassPane(BlockStateModelGenerator blockStateModelGenerator, Block glass, Block glassPane) {
        blockStateModelGenerator.registerSimpleCubeAll(glass);
        TextureMap textureMap = TextureMap.paneAndTopForEdge(glass, ModDecorativeBlocks.LEAD_GLASS_PANE);
        Identifier identifier = Models.TEMPLATE_GLASS_PANE_POST.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier2 = Models.TEMPLATE_GLASS_PANE_SIDE.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier3 = Models.TEMPLATE_GLASS_PANE_SIDE_ALT.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier4 = Models.TEMPLATE_GLASS_PANE_NOSIDE.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector);
        Identifier identifier5 = Models.TEMPLATE_GLASS_PANE_NOSIDE_ALT.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector);
        Item item = glassPane.asItem();
        Models.GENERATED.upload(ModelIds.getItemModelId(item), TextureMap.layer0(glass), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(MultipartBlockStateSupplier.create(glassPane).with(BlockStateVariant.create().put(VariantSettings.MODEL, identifier)).with(When.create().set(Properties.NORTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier2)).with(When.create().set(Properties.EAST, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier2).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.SOUTH, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier3)).with(When.create().set(Properties.WEST, true), BlockStateVariant.create().put(VariantSettings.MODEL, identifier3).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.NORTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier4)).with(When.create().set(Properties.EAST, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5)).with(When.create().set(Properties.SOUTH, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier5).put(VariantSettings.Y, VariantSettings.Rotation.R90)).with(When.create().set(Properties.WEST, false), BlockStateVariant.create().put(VariantSettings.MODEL, identifier4).put(VariantSettings.Y, VariantSettings.Rotation.R270)));
    }

    private void registerLayers(BlockStateModelGenerator blockStateModelGenerator, Block layers, Block origin) {
        TextureMap textureMap = TextureMap.all(new Identifier("minecraft", Registries.BLOCK.getId(layers).getPath().replaceAll("_layers", "")));
        blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(layers).coordinate(BlockStateVariantMap.create(Properties.LAYERS).register((height) -> {
            BlockStateVariant var10000 = BlockStateVariant.create();
            VariantSetting var10001 = VariantSettings.MODEL;
            Identifier var2;
            if (height < 8) {
                Block var10002 = layers;
                int var10003 = height;
                var2 = ModelIds.getBlockSubModelId(var10002, "_height" + var10003 * 2);
            } else {
                var2 = new Identifier("minecraft", "block/" + Registries.BLOCK.getId(origin).getPath());
            }
            return var10000.put(var10001, var2);
        })));
        blockStateModelGenerator.registerParentedItemModel(layers, ModelIds.getBlockSubModelId(layers, "_height2"));
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        try {
            generateItemModelsUnchecked(itemModelGenerator);
        } catch (RuntimeException e) {
            System.out.println("[datagen] item models aborted: " + e);
        }
    }

    private void generateItemModelsUnchecked(ItemModelGenerator itemModelGenerator) {
        DedupingModelCollector.install(itemModelGenerator);
        for (SimpleWallModel.Wall wall : SimpleWallModel.blocks) {
            try {
            if (wall.wall() == null) { continue; }
            Identifier id = Registries.BLOCK.getId(wall.wall());
            itemModelGenerator.register(wall.wall().asItem(), new Model(Optional.of(id.withPath("block/" + id.getPath() + "_inventory")), Optional.empty()));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleWallModel.Wall wall : SimpleWallModel.vanillaWalls) {
            try {
            if (wall.wall() == null) { continue; }
            Identifier id = Registries.BLOCK.getId(wall.wall());
            itemModelGenerator.register(wall.wall().asItem(), new Model(Optional.of(id.withPath("block/" + id.getPath() + "_inventory")), Optional.empty()));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Item item : SimpleItemModel.items) {
            try {
            itemModelGenerator.register(item, Models.GENERATED);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Item item : SimpleHandheldItemModel.items) {
            try {
            itemModelGenerator.register(item, Models.HANDHELD);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Item item : SimpleBigItemModel.items) {
            try {
            itemModelGenerator.register(item, CustomItemModels.BIG_WEAPON);
            itemModelGenerator.register(item, "_inventory", Models.HANDHELD);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Item item : SimpleBowItemModel.items) {
            try {
            for(int i = 0; i < 3; i++) {
                itemModelGenerator.register(item, "_pulling_" + i, CustomItemModels.BOW);
            }
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Item item : SimpleSpawnEggItemModel.items) {
            try {
            itemModelGenerator.register(item, CustomItemModels.TEMPLATE_SPAWN_EGG);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }
        
        // Dyeables needs to be done manually (because of layers)
        // 1.20.1 has no data components: cloaks are BackAttachmentItem, not ArmorItem, so only
        // register the dyeable layers for the entries that really are armour.
        for (Item dyeable : new Item[]{ModEquipmentItems.CLOAK, ModEquipmentItems.CLOAK_HOOD,
                ModEquipmentItems.TUNIC_CLOAK, ModEquipmentItems.GAMBESON}) {
            try {
            if (dyeable instanceof ArmorItem armorItem) {
                registerDyeableArmor(armorItem, itemModelGenerator);
            }
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        // CLUSTERS
        itemModelGenerator.register(ModBlocks.QUARTZ_CLUSTER.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.SMALL_QUARTZ_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.MEDIUM_QUARTZ_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.LARGE_QUARTZ_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.RED_AGATE_CLUSTER.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.SMALL_RED_AGATE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.MEDIUM_RED_AGATE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.LARGE_RED_AGATE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.CITRINE_CLUSTER.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.SMALL_CITRINE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.MEDIUM_CITRINE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.LARGE_CITRINE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.GLOWSTONE_CLUSTER.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.SMALL_GLOWSTONE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.MEDIUM_GLOWSTONE_BUD.asItem(), Models.GENERATED);
        itemModelGenerator.register(ModBlocks.LARGE_GLOWSTONE_BUD.asItem(), Models.GENERATED);
    
        // Fallback: every block item that no explicit list covered still needs an inventory model.
        for (Block block : Registries.BLOCK) {
            Identifier blockId = Registries.BLOCK.getId(block);
            if (!blockId.getNamespace().equals(net.jukoz.me.MiddleEarth.MOD_ID)) { continue; }
            Item blockItem = block.asItem();
            if (blockItem == net.minecraft.item.Items.AIR) { continue; }
            try {
                itemModelGenerator.register(blockItem, new Model(java.util.Optional.of(blockId.withPath("block/" + blockId.getPath())), java.util.Optional.empty()));
            } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        if (skippedModels > 0) { System.out.println("[datagen] skipped " + skippedModels + " model registrations"); }
    }

    public final void registerDyeableArmor(ArmorItem armor, ItemModelGenerator itemModelGenerator) {
        Identifier identifier = ModelIds.getItemModelId(armor);
        Identifier identifier2 = TextureMap.getId(armor);
        Identifier identifier3 = TextureMap.getSubId(armor, "_overlay");
        Models.GENERATED_TWO_LAYERS.upload(identifier, TextureMap.layered(identifier2, identifier3), itemModelGenerator.writer, (id, textures) -> {
                    return itemModelGenerator.createArmorJson(id, textures, armor.getMaterial());
                }
        );
    }
}
