package net.jukoz.me.datageneration;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModBlocks;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.block.registration.ModNatureBlocks;
import net.jukoz.me.block.MushroomBlockSets;
import net.jukoz.me.block.special.crop.*;
import net.jukoz.me.block.special.verticalSlabs.VerticalSlabBlock;
import net.jukoz.me.datageneration.content.CustomItemModels;
import net.jukoz.me.datageneration.content.MEModels;
import net.jukoz.me.datageneration.content.models.*;
import net.jukoz.me.datageneration.content.tags.LeavesSets;
import net.jukoz.me.item.ModEquipmentItems;
import net.jukoz.me.item.ResourceItemsME;
import net.minecraft.world.level.block.state.properties.DripstoneThickness;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.jukoz.me.block.special.CustomWaterloggableTallPlantBlock;
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
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.DoorHingeSide;
import net.jukoz.me.block.special.hangingstuff.CustomHangingBlock;
import java.util.Objects;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import java.util.Optional;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.ModelProvider;
import net.minecraft.data.models.blockstates.Condition;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.blockstates.VariantProperty;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FaceAttachedHorizontalDirectionalBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.item.Items;
import net.minecraft.client.model.Model;
import net.minecraft.world.level.block.Rotation;

public class ModelProvider extends FabricModelProvider {
    /**
     * Every model id the block phase uploaded. 1.20.1's {@link net.minecraft.data.models.model.ModelTemplate}
     * has no "does this exist" query, and the item fallback needs one to tell a block whose shape
     * is a single {@code <name>} model from one that only exists as sub-models.
     */
    public static final java.util.Set<ResourceLocation> UPLOADED_BLOCK_MODELS = new java.util.HashSet<>();

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
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
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
    /**
     * The model a hand-written (non-datagen) blockstate uses for its first variant, or null when
     * the block has no hand-written blockstate. Used to give block items whose shape is modelled by
     * several differently named sub-models an icon that actually exists.
     */
    /** Project root, found by walking up from the datagen run directory (build/datagen). */
    private static java.io.File projectRoot() {
        java.io.File dir = new java.io.File("").getAbsoluteFile();
        for (int i = 0; i < 6 && dir != null; i++) {
            if (new java.io.File(dir, "build.gradle").isFile()) {
                return dir;
            }
            dir = dir.getParentFile();
        }
        return null;
    }

    private static boolean hasBlockTexture(String name) {
        java.io.File root = projectRoot();
        return root != null && new java.io.File(root, "src/main/resources/assets/me/textures/block/" + name + ".png").isFile();
    }

    /**
     * The model a hand-written (non-datagen) blockstate uses for its first entry, or null when the
     * block has no hand-written blockstate. Gives block items whose shape is modelled by several
     * differently named sub-models (plates, the skeleton decorations, the vaults) an icon that
     * actually exists.
     */
    private static ResourceLocation handWrittenBlockStateModel(String path) {
        java.io.File root = projectRoot();
        if (root == null) {
            return null;
        }
        java.io.File file = new java.io.File(root, "src/main/resources/assets/me/blockstates/" + path + ".json");
        if (!file.isFile()) {
            return null;
        }
        try (java.io.Reader reader = new java.io.FileReader(file, java.nio.charset.StandardCharsets.UTF_8)) {
            com.google.gson.JsonObject json = com.google.gson.JsonParser.parseReader(reader).getAsJsonObject();
            java.util.List<com.google.gson.JsonElement> entries = new java.util.ArrayList<>();
            com.google.gson.JsonObject variants = json.getAsJsonObject("variants");
            if (variants != null) {
                for (String key : variants.keySet()) {
                    entries.add(variants.get(key));
                }
            }
            com.google.gson.JsonArray multipart = json.getAsJsonArray("multipart");
            if (multipart != null) {
                for (com.google.gson.JsonElement element : multipart) {
                    entries.add(element.getAsJsonObject().get("apply"));
                }
            }
            for (com.google.gson.JsonElement element : entries) {
                if (element == null || !element.isJsonObject()) { continue; }
                com.google.gson.JsonElement model = element.getAsJsonObject().get("model");
                if (model == null || !model.isJsonPrimitive()) { continue; }
                String id = model.getAsString();
                if (!id.isEmpty()) {
                    return id.contains(":") ? new ResourceLocation(id) : new ResourceLocation(net.jukoz.me.MiddleEarth.MOD_ID, id);
                }
            }
        } catch (Exception ignored) {
            return null;
        }
        return null;
    }

    /**
     * Which model a block item's icon should parent to. Walls/fences/doors/panes/slabs-in-layers
     * have a canonical part; everything else parents to its own {@code <name>} model when the block
     * phase uploaded one, and otherwise to the most representative sub-model it did upload (the
     * upward tip of a pointed dripstone, the top half of a double plant).
     */
    private static ResourceLocation itemIconParent(Block block, ResourceLocation blockId) {
        String path = blockId.getPath();
        if (block instanceof WallBlock || block instanceof FenceBlock) {
            return blockId.withPath("block/" + path + "_inventory");
        }
        if (block instanceof LargeDoorBlock) {
            return blockId.withPath("block/" + path + "_left_0");
        }
        if (block instanceof DoorBlock) {
            return blockId.withPath("block/" + path + "_bottom_left");
        }
        if (block instanceof TrapDoorBlock) {
            return blockId.withPath("block/" + path + "_bottom");
        }
        if (block instanceof IronBarsBlock) {
            return blockId.withPath("block/" + path + "_post");
        }
        if (block.getStateManager().getProperty("layers") != null) {
            return blockId.withPath("block/" + path + "_height2");
        }
        ResourceLocation plain = blockId.withPath("block/" + path);
        if (UPLOADED_BLOCK_MODELS.contains(plain)) {
            return plain;
        }
        String prefix = "block/" + path + "_";
        ResourceLocation best = null;
        for (ResourceLocation uploaded : UPLOADED_BLOCK_MODELS) {
            if (!uploaded.getNamespace().equals(blockId.getNamespace())) { continue; }
            if (!uploaded.getPath().startsWith(prefix)) { continue; }
            String suffix = uploaded.getPath().substring(prefix.length());
            if (suffix.equals("up_tip") || suffix.equals("top")) {
                return uploaded;
            }
            if (best == null || uploaded.getPath().compareTo(best.getPath()) < 0) {
                best = uploaded;
            }
        }
        if (best != null) {
            return best;
        }
        return handWrittenBlockStateModel(path);
    }

    private static void logSkipped(RuntimeException e) {
        // A duplicate means an explicit registration already claimed the id, which is the
        // intended outcome of the fallbacks below - not a missing model.
        if (e.getMessage() != null && e.getMessage().startsWith("Duplicate model definition")) {
            return;
        }
        skippedModels++;
        String msg = e.toString();
        Throwable c = e.getCause();
        while (c != null) { msg += " | caused by " + c; c = c.getCause(); }
        if (loggedSkips.add(msg)) {
            System.out.println("[datagen] model skip: " + msg);
        }
    }

    private void generateBlockStateModelsUnchecked(BlockModelGenerators blockStateModelGenerator) {
        DedupingModelCollector.install(blockStateModelGenerator);

        for (Block block : SimpleBlockModel.blocks) {
            try {
            blockStateModelGenerator.registerSimpleCubeAll(block);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Block block : SimpleBlockModel.cobbleableStoneBlocks) {
            try {
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block);
            ResourceLocation identifier = ModelTemplates.CUBE_MIRRORED_ALL.upload(block, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation identifier2 = ModelTemplates.CUBE_ALL.upload(block, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
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
            TextureMapping textureMap = new TextureMapping().put(TextureSlot.ALL, new ResourceLocation(MiddleEarth.MOD_ID,"block/" + Registries.BLOCK.getId(wood).getPath().replaceAll("_wood", "_log")));
            ResourceLocation identifier = ModelTemplates.CUBE_COLUMN.upload(wood, textureMap, blockStateModelGenerator.modelCollector);
            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators.createAxisRotatedBlockState(wood, identifier));} catch (RuntimeException __modelError) { logSkipped(__modelError); }
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
            ResourceLocation id = ModelLocationUtils.getBlockModelId(block.origin());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.origin());
            ResourceLocation bottom = ModelTemplates.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation top = ModelTemplates.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createSlabBlockState(slab, bottom, top, id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.woodSlabs) {
            try {
            if (block.slab() == null) { continue; }
            if (block.origin() == null) { continue; }
            ResourceLocation id = ModelLocationUtils.getBlockModelId(block.origin());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            ResourceLocation bottom = ModelTemplates.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation top = ModelTemplates.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createSlabBlockState(slab, bottom, top, id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.strippedSlabs) {
            try {
            if (block.slab() == null) { continue; }
            if (block.origin() == null) { continue; }
            ResourceLocation id = ModelLocationUtils.getBlockModelId(block.origin());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            ResourceLocation bottom = ModelTemplates.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation top = ModelTemplates.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createSlabBlockState(slab, bottom, top, id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.vanillaSlabs) {
            try {
            if (block.slab() == null) { continue; }
            if (block.origin() == null) { continue; }
            ResourceLocation id = ModelLocationUtils.getBlockModelId(block.origin());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(new ResourceLocation("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            ResourceLocation bottom = ModelTemplates.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation top = ModelTemplates.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createSlabBlockState(slab, bottom, top, id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.vanillaWoodSlabs) {
            try {
            if (block.slab() == null) { continue; }
            if (block.origin() == null) { continue; }
            ResourceLocation id = ModelLocationUtils.getBlockModelId(block.origin());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(new ResourceLocation("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            ResourceLocation bottom = ModelTemplates.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation top = ModelTemplates.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createSlabBlockState(slab, bottom, top, id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleSlabModel.Slab block : SimpleSlabModel.vanillaStrippedSlab) {
            try {
            if (block.slab() == null) { continue; }
            if (block.origin() == null) { continue; }
            ResourceLocation id = ModelLocationUtils.getBlockModelId(block.origin());
            Block slab = block.slab();

            TexturedModel texturedModel = TexturedModel.getCubeAll(new ResourceLocation("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            ResourceLocation bottom = ModelTemplates.SLAB.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation top = ModelTemplates.SLAB_TOP.upload(slab, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createSlabBlockState(slab, bottom, top, id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.stairs) {
            try {
            if (block.stairs() == null) { continue; }
            if (block.origin() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.origin());
            Block stairs = block.stairs();

            ResourceLocation inner = ModelTemplates.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation regular = ModelTemplates.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation outer = ModelTemplates.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createStairsBlockState(stairs, inner, regular, outer));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.woodStairs) {
            try {
            if (block.stairs() == null) { continue; }
            if (block.origin() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Block stairs = block.stairs();

            ResourceLocation inner = ModelTemplates.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation regular = ModelTemplates.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation outer = ModelTemplates.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createStairsBlockState(stairs, inner, regular, outer));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.strippedStairs) {
            try {
            if (block.stairs() == null) { continue; }
            if (block.origin() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Block stairs = block.stairs();

            ResourceLocation inner = ModelTemplates.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation regular = ModelTemplates.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation outer = ModelTemplates.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createStairsBlockState(stairs, inner, regular, outer));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.vanillaWoodStairs) {
            try {
            if (block.stairs() == null) { continue; }
            if (block.origin() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new ResourceLocation("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Block stairs = block.stairs();

            ResourceLocation inner = ModelTemplates.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation regular = ModelTemplates.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation outer = ModelTemplates.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createStairsBlockState(stairs, inner, regular, outer));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.vanillaStrippedStairs) {
            try {
            if (block.stairs() == null) { continue; }
            if (block.origin() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new ResourceLocation("minecraft", "block/" + Registries.BLOCK.getId(block.origin()).getPath().replaceAll("_wood", "_log")));
            Block stairs = block.stairs();

            ResourceLocation inner = ModelTemplates.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation regular = ModelTemplates.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation outer = ModelTemplates.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createStairsBlockState(stairs, inner, regular, outer));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleStairModel.Stair block : SimpleStairModel.vanillaStairs) {
            try {
            if (block.stairs() == null) { continue; }
            if (block.origin() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.origin());
            Block stairs = block.stairs();

            ResourceLocation inner = ModelTemplates.INNER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation regular = ModelTemplates.STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation outer = ModelTemplates.OUTER_STAIRS.upload(stairs, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createStairsBlockState(stairs, inner, regular, outer));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.blocks) {
            try {
            if (block.wall() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Block wall = block.wall();

            ModelTemplates.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            ResourceLocation post = ModelTemplates.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation low = ModelTemplates.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation tall = ModelTemplates.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createWallBlockState(wall, post, low, tall));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.vanillaWalls) {
            try {
            if (block.wall() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new ResourceLocation("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath()));
            Block wall = block.wall();

            ModelTemplates.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            ResourceLocation post = ModelTemplates.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation low = ModelTemplates.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation tall = ModelTemplates.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createWallBlockState(wall, post, low, tall));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.strippedWalls) {
            try {
            if (block.wall() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block wall = block.wall();

            ResourceLocation inventory = ModelTemplates.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.registerParentedItemModel(wall, inventory);

            ResourceLocation post = ModelTemplates.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation low = ModelTemplates.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation tall = ModelTemplates.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createWallBlockState(wall, post, low, tall));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.vanillaStrippedWalls) {
            try {
            if (block.wall() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new ResourceLocation("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block wall = block.wall();

            ResourceLocation inventory = ModelTemplates.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.registerParentedItemModel(wall, inventory);

            ResourceLocation post = ModelTemplates.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation low = ModelTemplates.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation tall = ModelTemplates.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createWallBlockState(wall, post, low, tall));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleWallModel.Wall block : SimpleWallModel.vanillaWoodWalls) {
            try {
            if (block.wall() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new ResourceLocation("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block wall = block.wall();

            ResourceLocation inventory = ModelTemplates.WALL_INVENTORY.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.registerParentedItemModel(wall, inventory);

            ResourceLocation post = ModelTemplates.TEMPLATE_WALL_POST.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation low = ModelTemplates.TEMPLATE_WALL_SIDE.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation tall = ModelTemplates.TEMPLATE_WALL_SIDE_TALL.upload(wall, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createWallBlockState(wall, post, low, tall));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.blocks) {
            try {
            if (block.fence() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Block fence = block.fence();

            ResourceLocation post = ModelTemplates.FENCE_POST.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation side = ModelTemplates.FENCE_SIDE.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation inventory = ModelTemplates.FENCE_INVENTORY.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createFenceBlockState(fence, post, side));

            blockStateModelGenerator.registerParentedItemModel(fence, inventory);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.strippedFences) {
            try {
            if (block.fence() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block fence = block.fence();

            ResourceLocation post = ModelTemplates.FENCE_POST.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation side = ModelTemplates.FENCE_SIDE.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation inventory = ModelTemplates.FENCE_INVENTORY.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createFenceBlockState(fence, post, side));

            blockStateModelGenerator.registerParentedItemModel(fence, inventory);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.vanillaStrippedFences) {
            try {
            if (block.fence() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new ResourceLocation("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block fence = block.fence();

            ResourceLocation post = ModelTemplates.FENCE_POST.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation side = ModelTemplates.FENCE_SIDE.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation inventory = ModelTemplates.FENCE_INVENTORY.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createFenceBlockState(fence, post, side));

            blockStateModelGenerator.registerParentedItemModel(fence, inventory);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleFenceModel.Fence block : SimpleFenceModel.vanillaWoodFences) {
            try {
            if (block.fence() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.getCubeAll(new ResourceLocation("minecraft", "block/" + Registries.BLOCK.getId(block.block()).getPath().replaceAll("_wood", "_log")));
            Block fence = block.fence();

            ResourceLocation post = ModelTemplates.FENCE_POST.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation side = ModelTemplates.FENCE_SIDE.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation inventory = ModelTemplates.FENCE_INVENTORY.upload(fence, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
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

            ResourceLocation open = ModelTemplates.TEMPLATE_CUSTOM_FENCE_GATE_OPEN.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation closed = ModelTemplates.TEMPLATE_CUSTOM_FENCE_GATE.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation openWall = ModelTemplates.TEMPLATE_CUSTOM_FENCE_GATE_WALL_OPEN.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation closedWall = ModelTemplates.TEMPLATE_CUSTOM_FENCE_GATE_WALL.upload(fenceGate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                    .createFenceGateBlockState(fenceGate, open, closed, openWall, closedWall, false));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleButtonModel.Button block : SimpleButtonModel.buttons) {
            try {
            if (block.button() == null) { continue; }
            if (block.block() == null) { continue; }
            TexturedModel texturedModel = TexturedModel.CUBE_ALL.get(block.block());
            Block button = block.button();

            ResourceLocation unpressed = ModelTemplates.BUTTON.upload(button, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation pressed = ModelTemplates.BUTTON_PRESSED.upload(button, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation inventory = ModelTemplates.BUTTON_INVENTORY.upload(button, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
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

            ResourceLocation up = ModelTemplates.PRESSURE_PLATE_UP.upload(pressurePlate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);
            ResourceLocation down = ModelTemplates.PRESSURE_PLATE_DOWN.upload(pressurePlate, texturedModel.getTextures(), blockStateModelGenerator.modelCollector);

            blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
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
            if(block != null) blockStateModelGenerator.registerTintableCross(block, BlockModelGenerators.TintState.NOT_TINTED);

        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : TintableCrossModel.tintedBlocks) {
            try {
            blockStateModelGenerator.registerTintableCross(block, BlockModelGenerators.TintState.TINTED);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : TintableCrossModel.grassLikeBlocks) {
            try {
            blockStateModelGenerator.registerTintableCross(block, BlockModelGenerators.TintState.NOT_TINTED);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : SimpleFlowerBedModel.flowerBeds) {
            try {
            blockStateModelGenerator.registerFlowerbed(block);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : SimpleDoubleBlockModel.doubleBlocks){
            try {
            blockStateModelGenerator.registerDoubleBlock(block, BlockModelGenerators.TintState.NOT_TINTED);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : SimpleDoubleBlockModel.doubleBlocksItems){
            try {
            registerDoubleBlock(blockStateModelGenerator, block, BlockModelGenerators.TintState.NOT_TINTED);
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
                ResourceLocation columnId = Registries.BLOCK.getId(verticalSlab.verticalSlab());
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
                TextureMapping textureMap = (new TextureMapping())
                        .put(TextureSlot.SIDE, TextureMapping.getId(block.base()))
                        .put(TextureSlot.END, block.origin() == Blocks.BASALT || block.origin() == Blocks.DEEPSLATE
                                ? TextureMapping.getSubId(block.origin(), "_top") : TextureMapping.getId(block.origin()));
                ResourceLocation model = ModelTemplates.CUBE_COLUMN.upload(block.base(), textureMap, blockStateModelGenerator.modelCollector);
                blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators.createSingletonBlockState(block.base(), model));
            } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleFlowerPotModel.FlowerPot flowerPot : SimpleFlowerPotModel.pots) {
            try {
                if (flowerPot.plant() == null || flowerPot.pottedPlant() == null) { continue; }
                blockStateModelGenerator.registerFlowerPotPlant(flowerPot.plant(), flowerPot.pottedPlant(), BlockModelGenerators.TintState.NOT_TINTED);
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
                    BlockModelGenerators.TintState.NOT_TINTED);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }

        try {
            if (ModNatureBlocks.FLOATING_ICE != null) {
                blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators.createSingletonBlockState(
                        ModNatureBlocks.FLOATING_ICE, ModelLocationUtils.getBlockModelId(ModNatureBlocks.FLOATING_ICE)));
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
            ResourceLocation identifier = Registries.BLOCK.getId(wall.wall());
            String sidePath = identifier.getPath().replaceAll("_wall", "");
            String topBottomPath = identifier.withSuffixedPath("_top").getPath().replaceAll("_wall", "");
            registerColumnWallModelBlockStates(blockStateModelGenerator, wall.wall(), wall.block(),
                    identifier.getNamespace(), topBottomPath, topBottomPath, sidePath);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : SimpleWoodStoolModel.stools){
            try {
            registerWoodStoolModelBlockStates(blockStateModelGenerator, block,
                    new ResourceLocation(MiddleEarth.MOD_ID, "block/stripped_" + Registries.BLOCK.getId(block).getPath().replaceAll("_stool", "_log")),
                    new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_stool", "_planks")));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : SimpleStoneStoolModel.stools){
            try {
            registerStoneStoolModelBlockStates(blockStateModelGenerator, block,
                    new ResourceLocation(MiddleEarth.MOD_ID, "block/" +
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
                    new ResourceLocation("minecraft", id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : SimpleStoneTableModel.tables){
            try {
            registerStoneTableModelBlockStates(blockStateModelGenerator, block, new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_table", "")));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }
        for(SimpleStoneTableModel.VanillaTable stool : SimpleStoneTableModel.vanillaTables) {
            try {
            if (stool.origin() == null) { continue; }
            if (stool.base() == null) { continue; }
            String id = "block/" + Registries.BLOCK.getId(stool.origin()).getPath();
            if(stool.origin() == Blocks.BASALT) id += "_side";
            registerStoneTableModelBlockStates(blockStateModelGenerator, stool.base(), new ResourceLocation("minecraft", id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : SimpleStoneChairModel.chairs){
            try {
            registerStoneChairModelBlockStates(blockStateModelGenerator, block, new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_chair", "")));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }
        for(SimpleStoneChairModel.VanillaChair stool : SimpleStoneChairModel.vanillaChairs){
            try {
            if (stool.origin() == null) { continue; }
            if (stool.base() == null) { continue; }
            String id = "block/" + Registries.BLOCK.getId(stool.origin()).getPath();
            if(stool.origin() == Blocks.BASALT) id += "_side";
            registerStoneChairModelBlockStates(blockStateModelGenerator, stool.base(), new ResourceLocation("minecraft", id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : SimpleWoodTableModel.tables){
            try {
            registerWoodTableModelBlockStates(blockStateModelGenerator, block,
                    new ResourceLocation(MiddleEarth.MOD_ID, "block/stripped_" + Registries.BLOCK.getId(block).getPath().replaceAll("_table", "_log")),
                    new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_table", "_planks")));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : SimpleWoodChairModel.chairs){
            try {
            registerWoodChairModelBlockStates(blockStateModelGenerator, block,
                    new ResourceLocation(MiddleEarth.MOD_ID, "block/stripped_" + Registries.BLOCK.getId(block).getPath().replaceAll("_chair", "_log")),
                    new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath().replaceAll("_chair", "_planks")));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for(Block block : SimpleFanModel.grassLikeFans){
            try {
            registerFanModel(blockStateModelGenerator, block);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        registerWoodStoolModelBlockStates(blockStateModelGenerator, MushroomBlockSets.MUSHROOM.stool(),
                new ResourceLocation("minecraft", "block/mushroom_stem"),
                new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.MUSHROOM.stool()).getPath().replaceAll("_stool", "_planks")));
        registerWoodStoolModelBlockStates(blockStateModelGenerator, MushroomBlockSets.DARK_MUSHROOM.stool(),
                new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.DARK_MUSHROOM.stool()).getPath().replaceAll("_stool", "_stem")),
                new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.DARK_MUSHROOM.stool()).getPath().replaceAll("_stool", "_planks")));
        registerWoodStoolModelBlockStates(blockStateModelGenerator, MushroomBlockSets.GRAY_MUSHROOM.stool(),
                new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.GRAY_MUSHROOM.stool()).getPath().replaceAll("_stool", "_stem")),
                new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.GRAY_MUSHROOM.stool()).getPath().replaceAll("_stool", "_planks")));

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
                    new ResourceLocation("minecraft", "block/" + stripped),
                    new ResourceLocation("minecraft", "block/" + id));
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
                    new ResourceLocation("minecraft", "block/" + stripped),
                    new ResourceLocation("minecraft", "block/" + id));
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
                    new ResourceLocation("minecraft", "block/" + stripped),
                    new ResourceLocation("minecraft", "block/" + id));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        registerWoodChairModelBlockStates(blockStateModelGenerator, MushroomBlockSets.MUSHROOM.chair(),
                new ResourceLocation("minecraft", "block/mushroom_stem"),
                new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.MUSHROOM.chair()).getPath().replaceAll("_chair", "_planks")));
        registerWoodChairModelBlockStates(blockStateModelGenerator, MushroomBlockSets.DARK_MUSHROOM.chair(),
                new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.DARK_MUSHROOM.chair()).getPath().replaceAll("_chair", "_stem")),
                new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.DARK_MUSHROOM.chair()).getPath().replaceAll("_chair", "_planks")));
        registerWoodChairModelBlockStates(blockStateModelGenerator, MushroomBlockSets.GRAY_MUSHROOM.chair(),
                new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.GRAY_MUSHROOM.chair()).getPath().replaceAll("_chair", "_stem")),
                new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.GRAY_MUSHROOM.chair()).getPath().replaceAll("_chair", "_planks")));

        registerWoodTableModelBlockStates(blockStateModelGenerator, MushroomBlockSets.MUSHROOM.table(),
                new ResourceLocation("minecraft", "block/mushroom_stem"),
                new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.MUSHROOM.table()).getPath().replaceAll("_table", "_planks")));
        registerWoodTableModelBlockStates(blockStateModelGenerator, MushroomBlockSets.DARK_MUSHROOM.table(),
                new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.DARK_MUSHROOM.table()).getPath().replaceAll("_table", "_stem")),
                new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.DARK_MUSHROOM.table()).getPath().replaceAll("_table", "_planks")));
        registerWoodTableModelBlockStates(blockStateModelGenerator, MushroomBlockSets.GRAY_MUSHROOM.table(),
                new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.GRAY_MUSHROOM.table()).getPath().replaceAll("_table", "_stem")),
                new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(MushroomBlockSets.GRAY_MUSHROOM.table()).getPath().replaceAll("_table", "_planks")));

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

    public final void registerFanModel(BlockModelGenerators blockStateCollector, Block coralFanBlock) {
        TexturedModel texturedModel = TexturedModel.CORAL_FAN.get(coralFanBlock);
        ResourceLocation identifier = texturedModel.upload(coralFanBlock, blockStateCollector.modelCollector);
        blockStateCollector.blockStateCollector.accept(blockStateCollector.createSingletonBlockState(coralFanBlock, identifier));
        blockStateCollector.registerItemModel(coralFanBlock);
    }

    public void registerVanillaVerticalSlabModelBlockStates(BlockModelGenerators blockStateModelGenerator, Block block, Block origin, String slabPath){
        ResourceLocation fullBlockId = ModelLocationUtils.getBlockModelId(origin);
        // Vanilla ships no waxed_* block models: a waxed copper block points at its unwaxed
        // counterpart, and so must the slab's double variant.
        ResourceLocation vanillaOriginId = Registries.BLOCK.getId(origin);
        if (vanillaOriginId.getPath().startsWith("waxed_")) {
            fullBlockId = vanillaOriginId.withPath("block/" + vanillaOriginId.getPath().substring("waxed_".length()));
        }
        ResourceLocation variantId = MEModels.VERTICAL_SLAB.upload(block,
                TextureMapping.of(TextureSlot.ALL,new ResourceLocation("minecraft","block/" + slabPath)),
                blockStateModelGenerator.modelCollector);
        registerVerticalSlab(blockStateModelGenerator, block, fullBlockId, variantId);
    }

    public void registerVerticalSlabModelBlockStates(BlockModelGenerators blockStateModelGenerator, Block block, Block origin, String slabPath){
        ResourceLocation fullBlockId = ModelLocationUtils.getBlockModelId(origin);
        // Vanilla ships no waxed_* block models or textures at all: a waxed copper block simply
        // points at its unwaxed counterpart, so the slab has to do the same or it renders as a
        // missing model over a missing texture.
        ResourceLocation originId = Registries.BLOCK.getId(origin);
        if (originId.getNamespace().equals("minecraft") && originId.getPath().startsWith("waxed_")) {
            String unwaxed = originId.getPath().substring("waxed_".length());
            fullBlockId = originId.withPath("block/" + unwaxed);
            slabPath = unwaxed;
        }
        ResourceLocation variantId = MEModels.VERTICAL_SLAB.upload(block,
                TextureMapping.of(TextureSlot.ALL, new ResourceLocation(MiddleEarth.MOD_ID, "block/" + slabPath)),
                blockStateModelGenerator.modelCollector);
        registerVerticalSlab(blockStateModelGenerator, block, fullBlockId, variantId);
    }

    public void registerColumnVerticalSlabModelBlockStates(BlockModelGenerators blockStateModelGenerator, Block block, Block origin,
                                                           String modId, String topTexturePath, String bottomTexturePath, String sideTexturePath){
        ResourceLocation fullBlockId = ModelLocationUtils.getBlockModelId(origin);
        ResourceLocation sideTexture = new ResourceLocation(modId, "block/" + sideTexturePath);

        ResourceLocation variantId = MEModels.VERTICAL_COLUMN_SLAB.upload(block, (new TextureMapping())
                        .put(TextureSlot.TOP, new ResourceLocation(modId, "block/" + topTexturePath))
                        .put(TextureSlot.BOTTOM, new ResourceLocation(modId, "block/" + bottomTexturePath))
                        .put(TextureSlot.SIDE, sideTexture)
                        .put(TextureSlot.PARTICLE, sideTexture),
                blockStateModelGenerator.modelCollector);

        registerVerticalSlab(blockStateModelGenerator, block, fullBlockId, variantId);
    }

    private void registerVerticalSlab(BlockModelGenerators blockStateModelGenerator, Block block, ResourceLocation full, ResourceLocation variant) {
        // Two VerticalSlabBlock classes exist in the port (block.special and block.special.verticalSlabs)
        // and Property equality is identity-based, so a block built with the other class fails the
        // variant validation below even though the state names look identical.
        try {
            registerVerticalSlabUnchecked(blockStateModelGenerator, block, full, variant);
        } catch (IllegalStateException e) {
            System.out.println("[datagen] skipping vertical slab model for " + block);
        }
    }

    private void registerVerticalSlabUnchecked(BlockModelGenerators blockStateModelGenerator, Block block, ResourceLocation full, ResourceLocation variant) {
        blockStateModelGenerator.blockStateCollector.accept(MultiVariantGenerator.create(block)
                .coordinate(PropertyDispatch.create(BlockStateProperties.HORIZONTAL_FACING, VerticalSlabBlock.DOUBLE)
                        .register(Direction.NORTH, false, Variant.create()
                                .put(VariantProperties.MODEL, variant).put(VariantProperties.UVLOCK, true))
                        .register(Direction.EAST, false, Variant.create()
                                .put(VariantProperties.MODEL, variant).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R90))
                        .register(Direction.SOUTH, false, Variant.create()
                                .put(VariantProperties.MODEL, variant).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R180))
                        .register(Direction.WEST, false, Variant.create()
                                .put(VariantProperties.MODEL, variant).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R270))
                        .register(Direction.NORTH, true, Variant.create()
                                .put(VariantProperties.MODEL, full).put(VariantProperties.UVLOCK, true))
                        .register(Direction.EAST, true, Variant.create()
                                .put(VariantProperties.MODEL, full).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R90))
                        .register(Direction.SOUTH, true, Variant.create()
                                .put(VariantProperties.MODEL, full).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R180))
                        .register(Direction.WEST, true, Variant.create()
                                .put(VariantProperties.MODEL, full).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R270))
                ));
    }

    public void registerWoodStoolModelBlockStates(BlockModelGenerators blockStateModelGenerator, Block block, ResourceLocation logTexture, ResourceLocation plankTexture){
        blockStateModelGenerator.blockStateCollector.accept(MultiVariantGenerator.create(block,
                Variant.create().put(VariantProperties.MODEL, MEModels.WOOD_STOOL.upload(block,
                        (new TextureMapping()).put(TextureSlot.TOP, plankTexture)
                                .put(TextureSlot.SIDE, logTexture)
                                .put(TextureSlot.PARTICLE, logTexture),
                        blockStateModelGenerator.modelCollector)).put(VariantProperties.UVLOCK, false))
                .coordinate(BlockModelGenerators.createNorthDefaultHorizontalRotationStates()));
    }

    public void registerWoodTableModelBlockStates(BlockModelGenerators blockStateModelGenerator, Block block, ResourceLocation logTexture, ResourceLocation plankTexture){
        blockStateModelGenerator.blockStateCollector.accept(MultiVariantGenerator.create(block,
                        Variant.create().put(VariantProperties.MODEL, MEModels.WOOD_TABLE.upload(block,
                                (new TextureMapping()).put(TextureSlot.TOP, plankTexture)
                                        .put(TextureSlot.SIDE, logTexture)
                                        .put(TextureSlot.PARTICLE, logTexture),
                                blockStateModelGenerator.modelCollector)).put(VariantProperties.UVLOCK, false)));
    }

    public void registerWoodChairModelBlockStates(BlockModelGenerators blockStateModelGenerator, Block block, ResourceLocation logTexture, ResourceLocation plankTexture){
        blockStateModelGenerator.blockStateCollector.accept(MultiVariantGenerator.create(block,
                        Variant.create().put(VariantProperties.MODEL, MEModels.WOOD_CHAIR.upload(block,
                                (new TextureMapping()).put(TextureSlot.TOP, plankTexture)
                                        .put(TextureSlot.SIDE, logTexture)
                                        .put(TextureSlot.PARTICLE, logTexture),
                                blockStateModelGenerator.modelCollector)).put(VariantProperties.UVLOCK, false))
                .coordinate(BlockModelGenerators.createNorthDefaultHorizontalRotationStates()));
    }

    public void registerStoneStoolModelBlockStates(BlockModelGenerators blockStateModelGenerator, Block block, ResourceLocation texture){
        blockStateModelGenerator.blockStateCollector.accept(MultiVariantGenerator.create(block,
                        Variant.create().put(VariantProperties.MODEL, MEModels.STONE_STOOL.upload(block,
                                (new TextureMapping()).put(TextureSlot.ALL, texture)
                                        .put(TextureSlot.PARTICLE, texture),
                                blockStateModelGenerator.modelCollector)).put(VariantProperties.UVLOCK, false))
                .coordinate(BlockModelGenerators.createNorthDefaultHorizontalRotationStates()));
    }

    public void registerStoneTableModelBlockStates(BlockModelGenerators blockStateModelGenerator, Block block, ResourceLocation texture){
        blockStateModelGenerator.blockStateCollector.accept(MultiVariantGenerator.create(block,
                Variant.create().put(VariantProperties.MODEL, MEModels.STONE_TABLE.upload(block,
                        (new TextureMapping()).put(TextureSlot.ALL, texture)
                                .put(TextureSlot.PARTICLE, texture),
                        blockStateModelGenerator.modelCollector)).put(VariantProperties.UVLOCK, false)));
    }

    public void registerStoneChairModelBlockStates(BlockModelGenerators blockStateModelGenerator, Block block, ResourceLocation texture){
        blockStateModelGenerator.blockStateCollector.accept(MultiVariantGenerator.create(block,
                        Variant.create().put(VariantProperties.MODEL, MEModels.STONE_CHAIR.upload(block,
                                (new TextureMapping()).put(TextureSlot.ALL, texture)
                                        .put(TextureSlot.PARTICLE, texture),
                                blockStateModelGenerator.modelCollector)).put(VariantProperties.UVLOCK, false))
                .coordinate(BlockModelGenerators.createNorthDefaultHorizontalRotationStates()));
    }

    /** 1.20.1 port of the donor's registerPaneModel: single-texture pane (bars / net). */
    public final void registerPaneModel(BlockModelGenerators blockStateModelGenerator, Block pane) {
        TextureMapping textureMap = TextureMapping.paneAndTopForEdge(pane, pane);
        ResourceLocation post = ModelTemplates.TEMPLATE_GLASS_PANE_POST.upload(pane, textureMap, blockStateModelGenerator.modelCollector);
        ResourceLocation side = ModelTemplates.TEMPLATE_GLASS_PANE_SIDE.upload(pane, textureMap, blockStateModelGenerator.modelCollector);
        ResourceLocation sideAlt = ModelTemplates.TEMPLATE_GLASS_PANE_SIDE_ALT.upload(pane, textureMap, blockStateModelGenerator.modelCollector);
        ResourceLocation noSide = ModelTemplates.TEMPLATE_GLASS_PANE_NOSIDE.upload(pane, textureMap, blockStateModelGenerator.modelCollector);
        ResourceLocation noSideAlt = ModelTemplates.TEMPLATE_GLASS_PANE_NOSIDE_ALT.upload(pane, textureMap, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(MultiPartGenerator.create(pane)
                .with(Variant.create().put(VariantProperties.MODEL, post))
                .with(Condition.create().set(BlockStateProperties.NORTH, true), Variant.create().put(VariantProperties.MODEL, side))
                .with(Condition.create().set(BlockStateProperties.EAST, true), Variant.create().put(VariantProperties.MODEL, side).put(VariantProperties.Y, VariantProperties.Rotation.R90))
                .with(Condition.create().set(BlockStateProperties.SOUTH, true), Variant.create().put(VariantProperties.MODEL, sideAlt))
                .with(Condition.create().set(BlockStateProperties.WEST, true), Variant.create().put(VariantProperties.MODEL, sideAlt).put(VariantProperties.Y, VariantProperties.Rotation.R90))
                .with(Condition.create().set(BlockStateProperties.NORTH, false), Variant.create().put(VariantProperties.MODEL, noSide))
                .with(Condition.create().set(BlockStateProperties.EAST, false), Variant.create().put(VariantProperties.MODEL, noSideAlt))
                .with(Condition.create().set(BlockStateProperties.SOUTH, false), Variant.create().put(VariantProperties.MODEL, noSideAlt).put(VariantProperties.Y, VariantProperties.Rotation.R90))
                .with(Condition.create().set(BlockStateProperties.WEST, false), Variant.create().put(VariantProperties.MODEL, noSide).put(VariantProperties.Y, VariantProperties.Rotation.R270)));
        ModelTemplates.GENERATED.upload(ModelLocationUtils.getItemModelId(pane.asItem()), TextureMapping.layer0(pane), blockStateModelGenerator.modelCollector);
    }

    /** 1.20.1 port of the donor's registerPointedBlock. Vanilla's
     *  BlockModelGenerators#registerPointedDripstone is private and hard-wired to
     *  Blocks.POINTED_DRIPSTONE, so the 10 variants are built here instead. */
    public void registerPointedBlock(BlockModelGenerators blockStateModelGenerator, Block pointedBlock) {
        ModelTemplates.GENERATED.upload(ModelLocationUtils.getItemModelId(pointedBlock.asItem()),
                TextureMapping.layer0(pointedBlock.asItem()), blockStateModelGenerator.modelCollector);

        PropertyDispatch.C2<Direction, DripstoneThickness> variants =
                PropertyDispatch.create(BlockStateProperties.VERTICAL_DIRECTION, BlockStateProperties.THICKNESS);
        for (DripstoneThickness thickness : DripstoneThickness.values()) {
            for (Direction direction : new Direction[]{Direction.UP, Direction.DOWN}) {
                String suffix = "_" + direction.asString() + "_" + thickness.asString();
                TextureMapping textures = TextureMapping.cross(TextureMapping.getSubId(pointedBlock, suffix));
                ResourceLocation model = ModelTemplates.POINTED_DRIPSTONE.upload(pointedBlock, suffix, textures,
                        blockStateModelGenerator.modelCollector);
                variants.register(direction, thickness,
                        Variant.create().put(VariantProperties.MODEL, model));
            }
        }

        blockStateModelGenerator.blockStateCollector.accept(
                MultiVariantGenerator.create(pointedBlock).coordinate(variants));
    }

    /** 1.20.1 stand-in for 1.21's BlockModelGenerators#registerMultifaceBlock(ModelTemplate):
     *  emits the vanilla glow_lichen multipart blockstate against the block's own
     *  (hand-written) flat multiface model. */
    public void registerMultifaceBlock(BlockModelGenerators blockStateModelGenerator, Block block) {
        ResourceLocation model = ModelLocationUtils.getBlockModelId(block);

        Variant north = Variant.create().put(VariantProperties.MODEL, model);
        Variant east = Variant.create().put(VariantProperties.MODEL, model).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R90);
        Variant south = Variant.create().put(VariantProperties.MODEL, model).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R180);
        Variant west = Variant.create().put(VariantProperties.MODEL, model).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R270);
        Variant up = Variant.create().put(VariantProperties.MODEL, model).put(VariantProperties.UVLOCK, true).put(VariantProperties.X, VariantProperties.Rotation.R270);
        Variant down = Variant.create().put(VariantProperties.MODEL, model).put(VariantProperties.UVLOCK, true).put(VariantProperties.X, VariantProperties.Rotation.R90);

        Condition noFace = Condition.create()
                .set(BlockStateProperties.NORTH, false).set(BlockStateProperties.EAST, false).set(BlockStateProperties.SOUTH, false)
                .set(BlockStateProperties.WEST, false).set(BlockStateProperties.UP, false).set(BlockStateProperties.DOWN, false);

        blockStateModelGenerator.blockStateCollector.accept(MultiPartGenerator.create(block)
                .with(Condition.create().set(BlockStateProperties.NORTH, true), north).with(noFace, north)
                .with(Condition.create().set(BlockStateProperties.EAST, true), east).with(noFace, east)
                .with(Condition.create().set(BlockStateProperties.SOUTH, true), south).with(noFace, south)
                .with(Condition.create().set(BlockStateProperties.WEST, true), west).with(noFace, west)
                .with(Condition.create().set(BlockStateProperties.UP, true), up).with(noFace, up)
                .with(Condition.create().set(BlockStateProperties.DOWN, true), down).with(noFace, down));
    }

    private void registerFarmland(BlockModelGenerators blockStateModelGenerator, Block dirtBlock, Block farmland) {
        ResourceLocation dirtTexture = TextureMapping.getId(dirtBlock);
        ResourceLocation dryModel = ModelTemplates.TEMPLATE_FARMLAND.upload(farmland, (new TextureMapping())
                        .put(TextureSlot.DIRT, dirtTexture)
                        .put(TextureSlot.TOP, TextureMapping.getId(farmland)),
                blockStateModelGenerator.modelCollector);
        ResourceLocation moistModel = ModelTemplates.TEMPLATE_FARMLAND.upload(TextureMapping.getSubId(farmland, "_moist"), (new TextureMapping())
                        .put(TextureSlot.DIRT, dirtTexture)
                        .put(TextureSlot.TOP, TextureMapping.getSubId(farmland, "_moist")),
                blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(MultiVariantGenerator.create(farmland)
                .coordinate(BlockModelGenerators.createValueFencedModelMap(BlockStateProperties.MOISTURE, 7, moistModel, dryModel)));
    }

    private void registerDirtPath(BlockModelGenerators blockStateModelGenerator, Block dirtBlock, Block pathBlock) {
        ResourceLocation dirtTexture = TextureMapping.getId(dirtBlock);
        ResourceLocation model = MEModels.PATH_BLOCK.upload(pathBlock, (new TextureMapping())
                        .put(TextureSlot.PARTICLE, dirtTexture)
                        .put(TextureSlot.TOP, TextureMapping.getId(pathBlock).withSuffixedPath("_top"))
                        .put(TextureSlot.SIDE, TextureMapping.getId(pathBlock).withSuffixedPath("_side"))
                        .put(TextureSlot.BOTTOM, dirtTexture),
                blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators.createSingletonBlockState(pathBlock, model));
    }

    public final void registerLargePlant(BlockModelGenerators blockStateModelGenerator, Block plantBlock) {
        ResourceLocation id = Registries.BLOCK.getId(plantBlock);
        ResourceLocation texture = new ResourceLocation(id.getNamespace(), "block/" + id.getPath());
        ResourceLocation model = MEModels.LARGE_PLANT.upload(plantBlock, (new TextureMapping())
                        .put(TextureSlot.ALL, texture)
                        .put(TextureSlot.PARTICLE, texture),
                blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators.createSingletonBlockState(plantBlock, model));
    }

    public final void registerTintableLargePlant(BlockModelGenerators blockStateModelGenerator, Block plantBlock) {
        ResourceLocation id = Registries.BLOCK.getId(plantBlock);
        ResourceLocation texture = new ResourceLocation(id.getNamespace(), "block/" + id.getPath());
        ResourceLocation model = MEModels.TINTED_LARGE_PLANT.upload(plantBlock, (new TextureMapping())
                        .put(TextureSlot.ALL, texture)
                        .put(TextureSlot.PARTICLE, texture),
                blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators.createSingletonBlockState(plantBlock, model));
    }

    public void registerHangingVine(BlockModelGenerators blockStateModelGenerator, Block block) {
        ResourceLocation base = MEModels.CROP_VINE.upload(block, TextureMapping.crop(TextureMapping.getId(block)),
                blockStateModelGenerator.modelCollector);
        ResourceLocation tip = MEModels.CROP_VINE.upload(block, "_tip", TextureMapping.crop(TextureMapping.getSubId(block, "_tip")),
                blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(MultiVariantGenerator.create(block)
                .coordinate(PropertyDispatch.create(CustomHangingBlock.TIP)
                        .register(false, Variant.create().put(VariantProperties.MODEL, base))
                        .register(true, Variant.create().put(VariantProperties.MODEL, tip))));
    }

    public final void registerLargeDoor(BlockModelGenerators blockStateModelGenerator, LargeDoorBlock largeDoor, IntegerProperty part) {
        PropertyDispatch.C4<Direction, Boolean, DoorHingeSide, Integer> statesMap =
                PropertyDispatch.create(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.OPEN, BlockStateProperties.DOOR_HINGE, part);
        for (int i = 0; i < largeDoor.getDoorWidth() * largeDoor.getDoorHeight(); i++) {
            for (int k = 2; k < 6; k++) {
                int rot = switch (k) {
                    case 3 -> 180;
                    case 4 -> 270;
                    case 5 -> 90;
                    default -> 0;
                };
                ResourceLocation left = new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_left_" + i);
                ResourceLocation leftOpen = new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_left_open_" + i);
                ResourceLocation right = new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_right_" + i);
                ResourceLocation rightOpen = new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_right_open_" + i);

                statesMap.register(Direction.values()[k], false, DoorHingeSide.LEFT, i, rotateY(left, rot));
                statesMap.register(Direction.values()[k], true, DoorHingeSide.LEFT, i, rotateY(leftOpen, rot));
                statesMap.register(Direction.values()[k], false, DoorHingeSide.RIGHT, i, rotateY(right, rot));
                statesMap.register(Direction.values()[k], true, DoorHingeSide.RIGHT, i, rotateY(rightOpen, rot));

                if (k == 2) {
                    TextureMapping textures = (new TextureMapping())
                            .put(TextureSlot.ALL, new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i))
                            .put(TextureSlot.PARTICLE, new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i));
                    MEModels.LARGE_DOOR_LEFT.upload(largeDoor, "_left_" + i, textures, blockStateModelGenerator.modelCollector);
                    MEModels.LARGE_DOOR_LEFT_OPEN.upload(largeDoor, "_left_open_" + i, textures, blockStateModelGenerator.modelCollector);
                    MEModels.LARGE_DOOR_RIGHT.upload(largeDoor, "_right_" + i, textures, blockStateModelGenerator.modelCollector);
                    MEModels.LARGE_DOOR_RIGHT_OPEN.upload(largeDoor, "_right_open_" + i, textures, blockStateModelGenerator.modelCollector);
                }
            }
        }
        blockStateModelGenerator.blockStateCollector.accept(MultiVariantGenerator.create(largeDoor).coordinate(statesMap));
        blockStateModelGenerator.registerParentedItemModel(largeDoor, ModelLocationUtils.getBlockSubModelId(largeDoor, "_left_0"));
    }

    public final void registerThickLargeDoor(BlockModelGenerators blockStateModelGenerator, LargeDoorBlock largeDoor, IntegerProperty part) {
        PropertyDispatch.C4<Direction, Boolean, DoorHingeSide, Integer> statesMap =
                PropertyDispatch.create(BlockStateProperties.HORIZONTAL_FACING, BlockStateProperties.OPEN, BlockStateProperties.DOOR_HINGE, part);
        for (int i = 0; i < largeDoor.getDoorWidth() * largeDoor.getDoorHeight(); i++) {
            for (int k = 2; k < 6; k++) {
                int rot = switch (k) {
                    case 3 -> 180;
                    case 4 -> 270;
                    case 5 -> 90;
                    default -> 0;
                };
                ResourceLocation left = new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_left_" + i);
                ResourceLocation leftOpen = new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_left_open_" + i);
                ResourceLocation right = new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_right_" + i);
                ResourceLocation rightOpen = new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_right_open_" + i);

                statesMap.register(Direction.values()[k], false, DoorHingeSide.LEFT, i, rotateY(left, rot));
                statesMap.register(Direction.values()[k], true, DoorHingeSide.LEFT, i, rotateY(leftOpen, rot));
                statesMap.register(Direction.values()[k], false, DoorHingeSide.RIGHT, i, rotateY(right, rot));
                statesMap.register(Direction.values()[k], true, DoorHingeSide.RIGHT, i, rotateY(rightOpen, rot));

                if (k == 2) {
                    TextureMapping textures = (new TextureMapping())
                            .put(TextureSlot.ALL, new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i))
                            .put(TextureSlot.PARTICLE, new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(largeDoor).getPath() + "_" + i));
                    MEModels.LARGE_THICK_DOOR_LEFT.upload(largeDoor, "_left_" + i, textures, blockStateModelGenerator.modelCollector);
                    MEModels.LARGE_THICK_DOOR_LEFT_OPEN.upload(largeDoor, "_left_open_" + i, textures, blockStateModelGenerator.modelCollector);
                    MEModels.LARGE_THICK_DOOR_RIGHT.upload(largeDoor, "_right_" + i, textures, blockStateModelGenerator.modelCollector);
                    MEModels.LARGE_THICK_DOOR_RIGHT_OPEN.upload(largeDoor, "_right_open_" + i, textures, blockStateModelGenerator.modelCollector);
                }
            }
        }
        blockStateModelGenerator.blockStateCollector.accept(MultiVariantGenerator.create(largeDoor).coordinate(statesMap));
        blockStateModelGenerator.registerParentedItemModel(largeDoor, ModelLocationUtils.getBlockSubModelId(largeDoor, "_left_0"));
    }

    private static Variant rotateY(ResourceLocation model, int rot) {
        Variant variant = Variant.create().put(VariantProperties.MODEL, model);
        return switch (rot) {
            case 90 -> variant.put(VariantProperties.Y, VariantProperties.Rotation.R90);
            case 180 -> variant.put(VariantProperties.Y, VariantProperties.Rotation.R180);
            case 270 -> variant.put(VariantProperties.Y, VariantProperties.Rotation.R270);
            default -> variant;
        };
    }

    public void registerWoodBenchModelBlockStates(BlockModelGenerators blockStateModelGenerator, Block block){
        ResourceLocation texture = new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(block).getPath());
        ResourceLocation model = MEModels.WOOD_BENCH.upload(block,
                (new TextureMapping()).put(TextureSlot.ALL, texture).put(TextureSlot.PARTICLE, texture),
                blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(MultiVariantGenerator.create(block,
                        Variant.create().put(VariantProperties.MODEL, model).put(VariantProperties.UVLOCK, false))
                .coordinate(BlockModelGenerators.createNorthDefaultHorizontalRotationStates()));
        blockStateModelGenerator.registerParentedItemModel(block, ModelLocationUtils.getBlockModelId(block));
    }

    public void registerRocksBlock(BlockModelGenerators blockStateModelGenerator, Block rocksBlock, Block origin){
        ResourceLocation id = new ResourceLocation(Registries.BLOCK.getId(origin).getNamespace(), "block/" + Registries.BLOCK.getId(origin).getPath());
        if (origin == Blocks.BASALT || origin == Blocks.POLISHED_BASALT) {
            id = id.withSuffixedPath("_side");
        }
        TextureMapping textures = TextureMapping.of(TextureSlot.ALL, id);
        ResourceLocation stage0 = MEModels.ROCKS_STAGE_0.upload(rocksBlock, textures, blockStateModelGenerator.modelCollector);
        ResourceLocation stage1 = MEModels.ROCKS_STAGE_1.upload(rocksBlock, textures, blockStateModelGenerator.modelCollector);
        ResourceLocation stage2 = MEModels.ROCKS_STAGE_2.upload(rocksBlock, textures, blockStateModelGenerator.modelCollector);
        ResourceLocation stage3 = MEModels.ROCKS_STAGE_3.upload(rocksBlock, textures, blockStateModelGenerator.modelCollector);

        blockStateModelGenerator.blockStateCollector.accept(MultiVariantGenerator.create(rocksBlock)
                .coordinate(PropertyDispatch.create(BlockStateProperties.HORIZONTAL_FACING, RocksBlock.STAGE)
                        .register(Direction.NORTH, 0, Variant.create().put(VariantProperties.MODEL, stage0).put(VariantProperties.UVLOCK, true))
                        .register(Direction.EAST, 0, Variant.create().put(VariantProperties.MODEL, stage0).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R90))
                        .register(Direction.SOUTH, 0, Variant.create().put(VariantProperties.MODEL, stage0).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R180))
                        .register(Direction.WEST, 0, Variant.create().put(VariantProperties.MODEL, stage0).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R270))
                        .register(Direction.NORTH, 1, Variant.create().put(VariantProperties.MODEL, stage1).put(VariantProperties.UVLOCK, true))
                        .register(Direction.EAST, 1, Variant.create().put(VariantProperties.MODEL, stage1).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R90))
                        .register(Direction.SOUTH, 1, Variant.create().put(VariantProperties.MODEL, stage1).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R180))
                        .register(Direction.WEST, 1, Variant.create().put(VariantProperties.MODEL, stage1).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R270))
                        .register(Direction.NORTH, 2, Variant.create().put(VariantProperties.MODEL, stage2).put(VariantProperties.UVLOCK, true))
                        .register(Direction.EAST, 2, Variant.create().put(VariantProperties.MODEL, stage2).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R90))
                        .register(Direction.SOUTH, 2, Variant.create().put(VariantProperties.MODEL, stage2).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R180))
                        .register(Direction.WEST, 2, Variant.create().put(VariantProperties.MODEL, stage2).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R270))
                        .register(Direction.NORTH, 3, Variant.create().put(VariantProperties.MODEL, stage3).put(VariantProperties.UVLOCK, true))
                        .register(Direction.EAST, 3, Variant.create().put(VariantProperties.MODEL, stage3).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R90))
                        .register(Direction.SOUTH, 3, Variant.create().put(VariantProperties.MODEL, stage3).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R180))
                        .register(Direction.WEST, 3, Variant.create().put(VariantProperties.MODEL, stage3).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R270))
                ));
        blockStateModelGenerator.registerParentedItemModel(rocksBlock, ModelLocationUtils.getBlockModelId(rocksBlock));
    }

    public void registerOrientableThickLadder(BlockModelGenerators blockStateModelGenerator, Block ladderBlock){
        ResourceLocation texture = new ResourceLocation(MiddleEarth.MOD_ID, "block/" + Registries.BLOCK.getId(ladderBlock).getPath());
        ResourceLocation model = MEModels.THICK_LADDER.upload(ladderBlock, TextureMapping.of(TextureSlot.TEXTURE, texture), blockStateModelGenerator.modelCollector);

        blockStateModelGenerator.blockStateCollector.accept(MultiVariantGenerator.create(ladderBlock)
                .coordinate(PropertyDispatch.create(FaceAttachedHorizontalDirectionalBlock.FACE, BlockStateProperties.HORIZONTAL_FACING)
                        .register(AttachFace.FLOOR, Direction.NORTH, Variant.create().put(VariantProperties.MODEL, model).put(VariantProperties.UVLOCK, true))
                        .register(AttachFace.FLOOR, Direction.EAST, Variant.create().put(VariantProperties.MODEL, model).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R90))
                        .register(AttachFace.FLOOR, Direction.SOUTH, Variant.create().put(VariantProperties.MODEL, model).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R180))
                        .register(AttachFace.FLOOR, Direction.WEST, Variant.create().put(VariantProperties.MODEL, model).put(VariantProperties.UVLOCK, true).put(VariantProperties.Y, VariantProperties.Rotation.R270))
                        .register(AttachFace.WALL, Direction.NORTH, Variant.create().put(VariantProperties.MODEL, model).put(VariantProperties.UVLOCK, true).put(VariantProperties.X, VariantProperties.Rotation.R90))
                        .register(AttachFace.WALL, Direction.EAST, Variant.create().put(VariantProperties.MODEL, model).put(VariantProperties.UVLOCK, true).put(VariantProperties.X, VariantProperties.Rotation.R90).put(VariantProperties.Y, VariantProperties.Rotation.R90))
                        .register(AttachFace.WALL, Direction.SOUTH, Variant.create().put(VariantProperties.MODEL, model).put(VariantProperties.UVLOCK, true).put(VariantProperties.X, VariantProperties.Rotation.R90).put(VariantProperties.Y, VariantProperties.Rotation.R180))
                        .register(AttachFace.WALL, Direction.WEST, Variant.create().put(VariantProperties.MODEL, model).put(VariantProperties.UVLOCK, true).put(VariantProperties.X, VariantProperties.Rotation.R90).put(VariantProperties.Y, VariantProperties.Rotation.R270))
                        .register(AttachFace.CEILING, Direction.SOUTH, Variant.create().put(VariantProperties.MODEL, model).put(VariantProperties.UVLOCK, true).put(VariantProperties.X, VariantProperties.Rotation.R180))
                        .register(AttachFace.CEILING, Direction.WEST, Variant.create().put(VariantProperties.MODEL, model).put(VariantProperties.UVLOCK, true).put(VariantProperties.X, VariantProperties.Rotation.R180).put(VariantProperties.Y, VariantProperties.Rotation.R90))
                        .register(AttachFace.CEILING, Direction.NORTH, Variant.create().put(VariantProperties.MODEL, model).put(VariantProperties.UVLOCK, true).put(VariantProperties.X, VariantProperties.Rotation.R180).put(VariantProperties.Y, VariantProperties.Rotation.R180))
                        .register(AttachFace.CEILING, Direction.EAST, Variant.create().put(VariantProperties.MODEL, model).put(VariantProperties.UVLOCK, true).put(VariantProperties.X, VariantProperties.Rotation.R180).put(VariantProperties.Y, VariantProperties.Rotation.R270))
                ));
        blockStateModelGenerator.registerParentedItemModel(ladderBlock, ModelLocationUtils.getBlockModelId(ladderBlock));
    }

    public void registerColumnWallModelBlockStates(BlockModelGenerators blockStateModelGenerator, Block block, Block origin,
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
        ResourceLocation sideTexture = new ResourceLocation(modId, "block/" + sideTexturePath);
        TextureMapping textures = (new TextureMapping())
                .put(TextureSlot.TOP, new ResourceLocation(modIdTopBottom, "block/" + topTexturePath))
                .put(TextureSlot.BOTTOM, new ResourceLocation(modIdTopBottom, "block/" + bottomTexturePath))
                .put(TextureSlot.WALL, sideTexture)
                .put(TextureSlot.PARTICLE, sideTexture);

        ResourceLocation post = MEModels.COLUMN_WALL_POST.upload(block, textures, blockStateModelGenerator.modelCollector);
        ResourceLocation low = MEModels.COLUMN_WALL_SIDE.upload(block, textures, blockStateModelGenerator.modelCollector);
        ResourceLocation tall = MEModels.COLUMN_WALL_SIDE_TALL.upload(block, textures, blockStateModelGenerator.modelCollector);

        blockStateModelGenerator.blockStateCollector.accept(BlockModelGenerators
                .createWallBlockState(block, post, low, tall));

        ResourceLocation inventory = MEModels.COLUMN_WALL_INVENTORY.upload(block, textures, blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.registerParentedItemModel(block, inventory);
    }

    public final void registerDoubleBlock(BlockModelGenerators blockStateModelGenerator, Block doubleBlock, BlockModelGenerators.TintState tintType) {
        blockStateModelGenerator.registerItemModel(doubleBlock.asItem());
        ResourceLocation identifier = blockStateModelGenerator.createSubModel(doubleBlock, "_top", tintType.getCross(), TextureMapping::cross);
        ResourceLocation identifier2 = blockStateModelGenerator.createSubModel(doubleBlock, "_bottom", tintType.getCross(), TextureMapping::cross);
        blockStateModelGenerator.registerDoubleBlock(doubleBlock, identifier, identifier2);
    }

    public final void registerLeadGlassPane(BlockModelGenerators blockStateModelGenerator, Block glass, Block glassPane) {
        blockStateModelGenerator.registerSimpleCubeAll(glass);
        TextureMapping textureMap = TextureMapping.paneAndTopForEdge(glass, ModDecorativeBlocks.LEAD_GLASS_PANE);
        ResourceLocation identifier = ModelTemplates.TEMPLATE_GLASS_PANE_POST.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector);
        ResourceLocation identifier2 = ModelTemplates.TEMPLATE_GLASS_PANE_SIDE.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector);
        ResourceLocation identifier3 = ModelTemplates.TEMPLATE_GLASS_PANE_SIDE_ALT.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector);
        ResourceLocation identifier4 = ModelTemplates.TEMPLATE_GLASS_PANE_NOSIDE.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector);
        ResourceLocation identifier5 = ModelTemplates.TEMPLATE_GLASS_PANE_NOSIDE_ALT.upload(glassPane, textureMap, blockStateModelGenerator.modelCollector);
        Item item = glassPane.asItem();
        ModelTemplates.GENERATED.upload(ModelLocationUtils.getItemModelId(item), TextureMapping.layer0(glass), blockStateModelGenerator.modelCollector);
        blockStateModelGenerator.blockStateCollector.accept(MultiPartGenerator.create(glassPane).with(Variant.create().put(VariantProperties.MODEL, identifier)).with(Condition.create().set(BlockStateProperties.NORTH, true), Variant.create().put(VariantProperties.MODEL, identifier2)).with(Condition.create().set(BlockStateProperties.EAST, true), Variant.create().put(VariantProperties.MODEL, identifier2).put(VariantProperties.Y, VariantProperties.Rotation.R90)).with(Condition.create().set(BlockStateProperties.SOUTH, true), Variant.create().put(VariantProperties.MODEL, identifier3)).with(Condition.create().set(BlockStateProperties.WEST, true), Variant.create().put(VariantProperties.MODEL, identifier3).put(VariantProperties.Y, VariantProperties.Rotation.R90)).with(Condition.create().set(BlockStateProperties.NORTH, false), Variant.create().put(VariantProperties.MODEL, identifier4)).with(Condition.create().set(BlockStateProperties.EAST, false), Variant.create().put(VariantProperties.MODEL, identifier5)).with(Condition.create().set(BlockStateProperties.SOUTH, false), Variant.create().put(VariantProperties.MODEL, identifier5).put(VariantProperties.Y, VariantProperties.Rotation.R90)).with(Condition.create().set(BlockStateProperties.WEST, false), Variant.create().put(VariantProperties.MODEL, identifier4).put(VariantProperties.Y, VariantProperties.Rotation.R270)));
    }

    private void registerLayers(BlockModelGenerators blockStateModelGenerator, Block layers, Block origin) {
        ResourceLocation originId = Registries.BLOCK.getId(origin);
        TextureMapping textureMap = TextureMapping.all(originId);
        blockStateModelGenerator.blockStateCollector.accept(MultiVariantGenerator.create(layers).coordinate(PropertyDispatch.create(BlockStateProperties.LAYERS).register((height) -> {
            Variant var10000 = Variant.create();
            VariantProperty var10001 = VariantProperties.MODEL;
            ResourceLocation var2;
            if (height < 8) {
                Block var10002 = layers;
                int var10003 = height;
                var2 = ModelLocationUtils.getBlockSubModelId(var10002, "_height" + var10003 * 2);
            } else {
                var2 = originId.withPath("block/" + originId.getPath());
            }
            return var10000.put(var10001, var2);
        })));
        blockStateModelGenerator.registerParentedItemModel(layers, ModelLocationUtils.getBlockSubModelId(layers, "_height2"));
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {
        try {
            generateItemModelsUnchecked(itemModelGenerator);
        } catch (RuntimeException e) {
            System.out.println("[datagen] item models aborted: " + e);
        }
    }

    private void generateItemModelsUnchecked(ItemModelGenerators itemModelGenerator) {
        DedupingModelCollector.install(itemModelGenerator);
        // Hot metals have to be registered before the generic loops: the writer rejects a second
        // registration of the same id, and the override-bearing base model has to win.
        registerHotMetalModels(itemModelGenerator);
        for (SimpleWallModel.Wall wall : SimpleWallModel.blocks) {
            try {
            if (wall.wall() == null) { continue; }
            ResourceLocation id = Registries.BLOCK.getId(wall.wall());
            itemModelGenerator.register(wall.wall().asItem(), new ModelTemplate(Optional.of(id.withPath("block/" + id.getPath() + "_inventory")), Optional.empty()));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (SimpleWallModel.Wall wall : SimpleWallModel.vanillaWalls) {
            try {
            if (wall.wall() == null) { continue; }
            ResourceLocation id = Registries.BLOCK.getId(wall.wall());
            itemModelGenerator.register(wall.wall().asItem(), new ModelTemplate(Optional.of(id.withPath("block/" + id.getPath() + "_inventory")), Optional.empty()));
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Item item : SimpleItemModel.items) {
            try {
            itemModelGenerator.register(item, ModelTemplates.GENERATED);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Item item : SimpleHandheldItemModel.items) {
            try {
            itemModelGenerator.register(item, ModelTemplates.HANDHELD);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Item item : SimpleBigItemModel.items) {
            try {
            itemModelGenerator.register(item, CustomItemModels.BIG_WEAPON);
            itemModelGenerator.register(item, "_inventory", ModelTemplates.HANDHELD);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        for (Item item : SimpleBowItemModel.items) {
            try {
            registerBowModels(itemModelGenerator, item);
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
        itemModelGenerator.register(ModBlocks.QUARTZ_CLUSTER.asItem(), ModelTemplates.GENERATED);
        itemModelGenerator.register(ModBlocks.SMALL_QUARTZ_BUD.asItem(), ModelTemplates.GENERATED);
        itemModelGenerator.register(ModBlocks.MEDIUM_QUARTZ_BUD.asItem(), ModelTemplates.GENERATED);
        itemModelGenerator.register(ModBlocks.LARGE_QUARTZ_BUD.asItem(), ModelTemplates.GENERATED);
        itemModelGenerator.register(ModBlocks.RED_AGATE_CLUSTER.asItem(), ModelTemplates.GENERATED);
        itemModelGenerator.register(ModBlocks.SMALL_RED_AGATE_BUD.asItem(), ModelTemplates.GENERATED);
        itemModelGenerator.register(ModBlocks.MEDIUM_RED_AGATE_BUD.asItem(), ModelTemplates.GENERATED);
        itemModelGenerator.register(ModBlocks.LARGE_RED_AGATE_BUD.asItem(), ModelTemplates.GENERATED);
        itemModelGenerator.register(ModBlocks.CITRINE_CLUSTER.asItem(), ModelTemplates.GENERATED);
        itemModelGenerator.register(ModBlocks.SMALL_CITRINE_BUD.asItem(), ModelTemplates.GENERATED);
        itemModelGenerator.register(ModBlocks.MEDIUM_CITRINE_BUD.asItem(), ModelTemplates.GENERATED);
        itemModelGenerator.register(ModBlocks.LARGE_CITRINE_BUD.asItem(), ModelTemplates.GENERATED);
        itemModelGenerator.register(ModBlocks.GLOWSTONE_CLUSTER.asItem(), ModelTemplates.GENERATED);
        itemModelGenerator.register(ModBlocks.SMALL_GLOWSTONE_BUD.asItem(), ModelTemplates.GENERATED);
        itemModelGenerator.register(ModBlocks.MEDIUM_GLOWSTONE_BUD.asItem(), ModelTemplates.GENERATED);
        itemModelGenerator.register(ModBlocks.LARGE_GLOWSTONE_BUD.asItem(), ModelTemplates.GENERATED);
    
        // Fallback: every block item that no explicit list covered still needs an inventory model.
        for (Block block : Registries.BLOCK) {
            ResourceLocation blockId = Registries.BLOCK.getId(block);
            if (!blockId.getNamespace().equals(net.jukoz.me.MiddleEarth.MOD_ID)) { continue; }
            Item blockItem = block.asItem();
            if (blockItem == net.minecraft.world.item.Items.AIR) { continue; }
            try {
                // Composite shapes have no plain <block> model: point the item at the shape's
                // own inventory model, otherwise the icon references a model nobody uploaded.
                ResourceLocation parent = itemIconParent(block, blockId);
                if (parent != null) {
                    itemModelGenerator.register(blockItem, new ModelTemplate(java.util.Optional.of(parent), java.util.Optional.empty()));
                    continue;
                }
                // Nothing to parent to: a flat icon over the block's own texture, which for a
                // double-height plant is the texture of its top half.
                String texturePath = hasBlockTexture(blockId.getPath() + "_top")
                        ? blockId.getPath() + "_top" : blockId.getPath();
                ModelTemplates.GENERATED.upload(ModelLocationUtils.getItemModelId(blockItem),
                        TextureMapping.layer0(blockId.withPath("block/" + texturePath)), itemModelGenerator.writer);
            } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        // Fallback 2: every mod item that no explicit list covered still needs an inventory icon.
        for (Item item : Registries.ITEM) {
            ResourceLocation itemId = Registries.ITEM.getId(item);
            if (!itemId.getNamespace().equals(net.jukoz.me.MiddleEarth.MOD_ID)) { continue; }
            try {
                itemModelGenerator.register(item, ModelTemplates.GENERATED);
            } catch (RuntimeException __modelError) { logSkipped(__modelError); }
        }

        if (skippedModels > 0) { System.out.println("[datagen] skipped " + skippedModels + " model registrations"); }
    }

    /**
     * The donor swapped hot metals to a glowing model with 1.21.4's item-model condition API
     * ({@code ItemModelShaper.condition(new HotComponentProperty(), ...)}). 1.20.1 has no such API, so
     * the same effect is done with a {@code custom_model_data} override: MEComponents.set mirrors
     * the temperature into that tag, and the base model switches to the {@code _hot} variant.
     */
    private void registerHotMetalModels(ItemModelGenerators itemModelGenerator) {
        for (Item item : HotMetalsModel.ingots) {
            registerHotModel(itemModelGenerator, item, hotIngotTexture(item));
        }
        for (Item item : HotMetalsModel.nuggets) {
            registerHotModel(itemModelGenerator, item, "nugget_hot");
        }
        for (Item item : HotMetalsModel.nuggies) {
            registerHotModel(itemModelGenerator, item, Registries.ITEM.getId(item).getPath() + "_hot");
        }
    }

    private static String hotIngotTexture(Item item) {
        if (item == ResourceItemsME.BRONZE_INGOT) { return "medium_ingot_hot"; }
        if (item == ResourceItemsME.TIN_INGOT) { return "cube_ingot_hot"; }
        if (item == ResourceItemsME.LEAD_INGOT) { return "tall_small_ingot_hot"; }
        if (item == ResourceItemsME.EDHEL_STEEL_INGOT || item == ResourceItemsME.MITHRIL_INGOT) { return "small_ingot_hot"; }
        if (item == ResourceItemsME.KHAZAD_STEEL_INGOT) { return "tall_ingot_hot"; }
        if (item == ResourceItemsME.BURZUM_STEEL_INGOT) { return "thick_ingot_hot"; }
        return "ingot_hot";
    }

    private void registerHotModel(ItemModelGenerators itemModelGenerator, Item item, String hotTexture) {
        ResourceLocation base = ModelLocationUtils.getItemModelId(item);
        ResourceLocation hot = base.withSuffixedPath("_hot");
        try {
            ModelTemplates.GENERATED.upload(hot, TextureMapping.layer0(new ResourceLocation(MiddleEarth.MOD_ID, "item/" + hotTexture)), itemModelGenerator.writer);
            JsonObject textures = new JsonObject();
            textures.addProperty("layer0", base.toString());
            JsonObject json = new JsonObject();
            json.addProperty("parent", "minecraft:item/generated");
            json.add("textures", textures);
            JsonObject predicate = new JsonObject();
            predicate.addProperty("custom_model_data", 1);
            JsonObject override = new JsonObject();
            override.add("predicate", predicate);
            override.addProperty("model", hot.toString());
            JsonArray overrides = new JsonArray();
            overrides.add(override);
            json.add("overrides", overrides);
            itemModelGenerator.writer.accept(base, () -> json);
        } catch (RuntimeException __modelError) { logSkipped(__modelError); }
    }

    /**
     * Bows need three draw stages plus a base model that points at them. The donor got this from
     * 1.21's item-model API; here the overrides have to be written by hand, and each stage needs
     * its own *_pulling_N texture rather than the undrawn one.
     */
    private static void registerBowModels(ItemModelGenerators itemModelGenerator, Item item) {
        ResourceLocation base = ModelLocationUtils.getItemModelId(item);
        float[] pull = {0.0F, 0.65F, 0.9F};
        JsonArray overrides = new JsonArray();
        for (int i = 0; i < 3; i++) {
            ResourceLocation stage = base.withSuffixedPath("_pulling_" + i);
            CustomItemModels.BOW.upload(stage, (new TextureMapping()).put(TextureSlot.LAYER0, stage), itemModelGenerator.writer);

            JsonObject predicate = new JsonObject();
            predicate.addProperty("pulling", 1);
            if (i > 0) {
                predicate.addProperty("pull", pull[i]);
            }
            JsonObject override = new JsonObject();
            override.add("predicate", predicate);
            override.addProperty("model", stage.toString());
            overrides.add(override);
        }
        JsonObject textures = new JsonObject();
        textures.addProperty("layer0", base.toString());
        JsonObject json = new JsonObject();
        json.addProperty("parent", MiddleEarth.of("item/bow").toString());
        json.add("textures", textures);
        json.add("overrides", overrides);
        itemModelGenerator.writer.accept(base, () -> json);
    }

    public final void registerDyeableArmor(ArmorItem armor, ItemModelGenerators itemModelGenerator) {
        ResourceLocation identifier = ModelLocationUtils.getItemModelId(armor);
        ResourceLocation identifier2 = TextureMapping.getId(armor);
        ResourceLocation identifier3 = TextureMapping.getSubId(armor, "_overlay");
        // createArmorJson would append ten trim overrides (tunic_cloak_quartz_trim and friends),
        // but the mod ships no trim textures, so those models can never be generated and the
        // inventory icon would fall back to the missing-model placeholder.
        ModelTemplates.GENERATED_TWO_LAYERS.upload(identifier, TextureMapping.layered(identifier2, identifier3), itemModelGenerator.writer);
    }
}
