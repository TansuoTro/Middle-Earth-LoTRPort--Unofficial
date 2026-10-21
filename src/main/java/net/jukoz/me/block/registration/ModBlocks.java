package net.jukoz.me.block.registration;

import net.jukoz.me.item.MESettings;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.*;
import net.jukoz.me.block.special.dirts.CustomFarmlandBlock;
import net.jukoz.me.block.special.dirts.CustomGrassBlock;
import net.jukoz.me.block.special.dirts.CustomPathBlock;
import net.jukoz.me.block.special.gemstones.CustomBuddingGemBlock;
import net.jukoz.me.block.special.pointedBlocks.PointedDolomiteBlock;
import net.jukoz.me.block.special.pointedBlocks.PointedGalonnBlock;
import net.jukoz.me.block.special.pointedBlocks.PointedIzherabanBlock;
import net.jukoz.me.block.special.pointedBlocks.PointedLimestoneBlock;
import net.jukoz.me.block.special.verticalSlabs.VerticalSlabBlock;
import net.jukoz.me.datageneration.content.TranslationEntries;
import net.jukoz.me.datageneration.content.loot_tables.BlockDrops;
import net.jukoz.me.item.utils.ModItemGroups;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.registries.RegistryAliasesME;
import java.util.List;
import java.util.function.Function;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.AmethystBlock;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.IronBarsBlock;
import net.minecraft.world.level.block.MudBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class ModBlocks {
    public static final float DIRT_STRENGTH = 0.6f;
    public static final float SLAB_RESISTANCE = 6.0f; // From explosions

    public static final Block POINTED_DOLOMITE = registerStoneBlock("pointed_dolomite",
            PointedDolomiteBlock::new,BlockBehaviour.Properties.copy(Blocks.POINTED_DRIPSTONE).ticksRandomly().strength(1.5F, 3.0F).dynamicBounds().offset(BlockBehaviour.OffsetType.XZ).pistonBehavior(PushReaction.DESTROY).solidBlock(Blocks::never), false);
    public static final Block POINTED_LIMESTONE = registerStoneBlock("pointed_limestone",
            PointedLimestoneBlock::new,BlockBehaviour.Properties.copy(Blocks.POINTED_DRIPSTONE).ticksRandomly().strength(1.5F, 3.0F).dynamicBounds().offset(BlockBehaviour.OffsetType.XZ).pistonBehavior(PushReaction.DESTROY).solidBlock(Blocks::never), false);
    public static final Block POINTED_IZHERABAN = registerStoneBlock("pointed_izheraban",
            PointedIzherabanBlock::new,BlockBehaviour.Properties.copy(Blocks.POINTED_DRIPSTONE).ticksRandomly().strength(1.5F, 3.0F).dynamicBounds().offset(BlockBehaviour.OffsetType.XZ).pistonBehavior(PushReaction.DESTROY).solidBlock(Blocks::never), false);
    public static final Block POINTED_GALONN = registerStoneBlock("pointed_galonn",
            PointedGalonnBlock::new,BlockBehaviour.Properties.copy(Blocks.POINTED_DRIPSTONE).ticksRandomly().strength(1.5F, 3.0F).dynamicBounds().offset(BlockBehaviour.OffsetType.XZ).pistonBehavior(PushReaction.DESTROY).solidBlock(Blocks::never), false);

    public static final Block BLACK_WATTLE_TRAPDOOR = registerWoodBlock("black_wattle_trapdoor",
            (settings) -> new TrapDoorBlock(settings, BlockSetType.OAK), BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), true);
    public static final Block DARK_WATTLE_TRAPDOOR = registerWoodBlock("dark_wattle_trapdoor",
            (settings) -> new TrapDoorBlock(settings, BlockSetType.OAK), BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), true);
    public static final Block GREEN_WATTLE_TRAPDOOR = registerWoodBlock("green_wattle_trapdoor",
            (settings) -> new TrapDoorBlock(settings, BlockSetType.OAK), BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), true);
    public static final Block RED_WATTLE_TRAPDOOR = registerWoodBlock("red_wattle_trapdoor",
            (settings) -> new TrapDoorBlock(settings, BlockSetType.OAK), BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), true);
    public static final Block WATTLE_TRAPDOOR = registerWoodBlock("wattle_trapdoor",
            (settings) -> new TrapDoorBlock(settings, BlockSetType.OAK), BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), true);

    public static final Block TREATED_WOOD_ROPE_FENCE = registerWoodBlock("treated_wood_rope_fence",
            FenceBlock::new, BlockBehaviour.Properties.copy(Blocks.OAK_FENCE), true);

    public static final Block SNOWY_GRASS_BLOCK = registerMiscBlock("snowy_grass_block",
            Block::new,BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK),false);

    public static final Block SNOWY_DIRT = registerMiscBlock("snowy_dirt",
            Block::new,BlockBehaviour.Properties.copy(Blocks.COARSE_DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block SNOWY_DIRT_SLAB = registerMiscBlock("snowy_dirt_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(SNOWY_DIRT),true);
    public static final Block SNOWY_DIRT_STAIRS = registerMiscBlock("snowy_dirt_stairs",
            (settings) -> new StairBlock(SNOWY_DIRT.defaultBlockState(), settings), BlockBehaviour.Properties.copy(SNOWY_DIRT),true);

    public static final Block DRY_DIRT = registerMiscBlock("dry_dirt",
            Block::new,BlockBehaviour.Properties.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block DRY_DIRT_SLAB = registerMiscBlock("dry_dirt_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(DRY_DIRT),true);
    public static final Block DRY_DIRT_STAIRS = registerMiscBlock("dry_dirt_stairs",
            (settings) -> new StairBlock(DRY_DIRT.defaultBlockState(), settings), BlockBehaviour.Properties.copy(DRY_DIRT),true);

    public static final Block GRASSY_DIRT = registerMiscBlock("grassy_dirt",
            Block::new,BlockBehaviour.Properties.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block GRASSY_DIRT_SLAB = registerMiscBlock("grassy_dirt_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(GRASSY_DIRT),true);
    public static final Block GRASSY_DIRT_STAIRS = registerMiscBlock("grassy_dirt_stairs",
            (settings) -> new StairBlock(GRASSY_DIRT.defaultBlockState(), settings), BlockBehaviour.Properties.copy(GRASSY_DIRT),true);

    public static final Block PEBBLED_GRASS = registerMiscBlock("pebbled_grass",
            Block::new,BlockBehaviour.Properties.copy(Blocks.COBBLESTONE).sound(SoundType.STONE),true);
    public static final Block PEBBLED_GRASS_SLAB = registerMiscBlock("pebbled_grass_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(PEBBLED_GRASS),true);
    public static final Block PEBBLED_GRASS_STAIRS = registerMiscBlock("pebbled_grass_stairs",
            (settings) -> new StairBlock(PEBBLED_GRASS.defaultBlockState(), settings), BlockBehaviour.Properties.copy(PEBBLED_GRASS),true);

    public static final Block COBBLY_DIRT = registerMiscBlock("cobbly_dirt",
            Block::new,BlockBehaviour.Properties.copy(Blocks.COARSE_DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block COBBLY_DIRT_SLAB = registerMiscBlock("cobbly_dirt_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(COBBLY_DIRT),true);
    public static final Block COBBLY_DIRT_STAIRS = registerMiscBlock("cobbly_dirt_stairs",
            (settings) -> new StairBlock(COBBLY_DIRT.defaultBlockState(), settings), BlockBehaviour.Properties.copy(COBBLY_DIRT),true);

    public static final Block CHALKSOIL = registerMiscBlock("chalksoil",
            Block::new,BlockBehaviour.Properties.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block CHALKSOIL_SLAB = registerMiscBlock("chalksoil_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(CHALKSOIL),true);
    public static final Block CHALKSOIL_STAIRS = registerMiscBlock("chalksoil_stairs",
            (settings) -> new StairBlock(CHALKSOIL.defaultBlockState(), settings), BlockBehaviour.Properties.copy(CHALKSOIL),true);

    public static final Block CHALKSOIL_GRASS_BLOCK = registerMiscBlock("chalksoil_grass_block",
            (settings) -> new CustomGrassBlock(settings, CHALKSOIL), BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK),false);

    public static final Block GRASSY_CHALKSOIL = registerMiscBlock("grassy_chalksoil",
            Block::new,BlockBehaviour.Properties.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block GRASSY_CHALKSOIL_SLAB = registerMiscBlock("grassy_chalksoil_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(GRASSY_CHALKSOIL),true);
    public static final Block GRASSY_CHALKSOIL_STAIRS = registerMiscBlock("grassy_chalksoil_stairs",
            (settings) -> new StairBlock(GRASSY_CHALKSOIL.defaultBlockState(), settings), BlockBehaviour.Properties.copy(GRASSY_CHALKSOIL),true);

    public static final Block COARSE_CHALKSOIL = registerMiscBlock("coarse_chalksoil",
            Block::new,BlockBehaviour.Properties.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block COARSE_CHALKSOIL_SLAB = registerMiscBlock("coarse_chalksoil_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(DRY_DIRT),true);
    public static final Block COARSE_CHALKSOIL_STAIRS = registerMiscBlock("coarse_chalksoil_stairs",
            (settings) -> new StairBlock(COARSE_CHALKSOIL.defaultBlockState(), settings), BlockBehaviour.Properties.copy(DRY_DIRT),true);

    public static final Block CHALKSOIL_PATH = registerMiscBlock("chalksoil_path",
            (settings) -> new CustomPathBlock(settings, CHALKSOIL), BlockBehaviour.Properties.copy(Blocks.DIRT_PATH),false);
    public static final Block CHALKSOIL_FARMLAND = registerMiscBlock("chalksoil_farmland",
            (settings) -> new CustomFarmlandBlock(settings, CHALKSOIL), BlockBehaviour.Properties.copy(Blocks.FARMLAND),false);

    public static final Block LOAM = registerMiscBlock("loam",
            Block::new,BlockBehaviour.Properties.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block LOAM_SLAB = registerMiscBlock("loam_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(LOAM),true);
    public static final Block LOAM_STAIRS = registerMiscBlock("loam_stairs",
            (settings) -> new StairBlock(LOAM.defaultBlockState(), settings), BlockBehaviour.Properties.copy(LOAM),true);

    public static final Block LOAM_GRASS_BLOCK = registerMiscBlock("loam_grass_block",
            (settings) -> new CustomGrassBlock(settings, LOAM), BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK),false);
    
    public static final Block GRASSY_LOAM = registerMiscBlock("grassy_loam",
            Block::new,BlockBehaviour.Properties.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block GRASSY_LOAM_SLAB = registerMiscBlock("grassy_loam_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(GRASSY_LOAM),true);
    public static final Block GRASSY_LOAM_STAIRS = registerMiscBlock("grassy_loam_stairs",
            (settings) -> new StairBlock(GRASSY_LOAM.defaultBlockState(), settings), BlockBehaviour.Properties.copy(GRASSY_LOAM),true);

    public static final Block COARSE_LOAM = registerMiscBlock("coarse_loam",
            Block::new,BlockBehaviour.Properties.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block COARSE_LOAM_SLAB = registerMiscBlock("coarse_loam_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(DRY_DIRT),true);
    public static final Block COARSE_LOAM_STAIRS = registerMiscBlock("coarse_loam_stairs",
            (settings) -> new StairBlock(COARSE_LOAM.defaultBlockState(), settings), BlockBehaviour.Properties.copy(DRY_DIRT),true);

    public static final Block LOAM_PATH = registerMiscBlock("loam_path",
            (settings) -> new CustomPathBlock(settings, LOAM), BlockBehaviour.Properties.copy(Blocks.DIRT_PATH),false);
    public static final Block LOAM_FARMLAND = registerMiscBlock("loam_farmland",
            (settings) -> new CustomFarmlandBlock(settings, LOAM), BlockBehaviour.Properties.copy(Blocks.FARMLAND),false);

    public static final Block PEAT = registerMiscBlock("peat",
            Block::new,BlockBehaviour.Properties.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block PEAT_SLAB = registerMiscBlock("peat_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(PEAT),true);
    public static final Block PEAT_STAIRS = registerMiscBlock("peat_stairs",
            (settings) -> new StairBlock(PEAT.defaultBlockState(), settings), BlockBehaviour.Properties.copy(PEAT),true);

    public static final Block PEAT_GRASS_BLOCK = registerMiscBlock("peat_grass_block",
            (settings) -> new CustomGrassBlock(settings, PEAT), BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK),false);

    public static final Block GRASSY_PEAT = registerMiscBlock("grassy_peat",
            Block::new,BlockBehaviour.Properties.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block GRASSY_PEAT_SLAB = registerMiscBlock("grassy_peat_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(GRASSY_PEAT),true);
    public static final Block GRASSY_PEAT_STAIRS = registerMiscBlock("grassy_peat_stairs",
            (settings) -> new StairBlock(GRASSY_PEAT.defaultBlockState(), settings), BlockBehaviour.Properties.copy(GRASSY_PEAT),true);

    public static final Block COARSE_PEAT = registerMiscBlock("coarse_peat",
            Block::new,BlockBehaviour.Properties.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block COARSE_PEAT_SLAB = registerMiscBlock("coarse_peat_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(DRY_DIRT),true);
    public static final Block COARSE_PEAT_STAIRS = registerMiscBlock("coarse_peat_stairs",
            (settings) -> new StairBlock(COARSE_PEAT.defaultBlockState(), settings), BlockBehaviour.Properties.copy(DRY_DIRT),true);

    public static final Block PEAT_PATH = registerMiscBlock("peat_path",
            (settings) -> new CustomPathBlock(settings, PEAT), BlockBehaviour.Properties.copy(Blocks.DIRT_PATH),false);
    public static final Block PEAT_FARMLAND = registerMiscBlock("peat_farmland",
            (settings) -> new CustomFarmlandBlock(settings, PEAT), BlockBehaviour.Properties.copy(Blocks.FARMLAND),false);

    public static final Block SILT = registerMiscBlock("silt",
            Block::new,BlockBehaviour.Properties.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block SILT_SLAB = registerMiscBlock("silt_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(SILT),true);
    public static final Block SILT_STAIRS = registerMiscBlock("silt_stairs",
            (settings) -> new StairBlock(SILT.defaultBlockState(), settings), BlockBehaviour.Properties.copy(SILT),true);

    public static final Block SILT_GRASS_BLOCK = registerMiscBlock("silt_grass_block",
            (settings) -> new CustomGrassBlock(settings, SILT), BlockBehaviour.Properties.copy(Blocks.GRASS_BLOCK),false);

    public static final Block GRASSY_SILT = registerMiscBlock("grassy_silt",
            Block::new,BlockBehaviour.Properties.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block GRASSY_SILT_SLAB = registerMiscBlock("grassy_silt_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(GRASSY_SILT),true);
    public static final Block GRASSY_SILT_STAIRS = registerMiscBlock("grassy_silt_stairs",
            (settings) -> new StairBlock(GRASSY_SILT.defaultBlockState(), settings), BlockBehaviour.Properties.copy(GRASSY_SILT),true);

    public static final Block COARSE_SILT = registerMiscBlock("coarse_silt",
            Block::new,BlockBehaviour.Properties.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block COARSE_SILT_SLAB = registerMiscBlock("coarse_silt_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(DRY_DIRT),true);
    public static final Block COARSE_SILT_STAIRS = registerMiscBlock("coarse_silt_stairs",
            (settings) -> new StairBlock(COARSE_SILT.defaultBlockState(), settings), BlockBehaviour.Properties.copy(DRY_DIRT),true);

    public static final Block SILT_PATH = registerMiscBlock("silt_path",
            (settings) -> new CustomPathBlock(settings, SILT), BlockBehaviour.Properties.copy(Blocks.DIRT_PATH),false);
    public static final Block SILT_FARMLAND = registerMiscBlock("silt_farmland",
            (settings) -> new CustomFarmlandBlock(settings, SILT), BlockBehaviour.Properties.copy(Blocks.FARMLAND),false);

    public static final Block MIRE = registerMiscBlock("mire",
            MudBlock::new, BlockBehaviour.Properties.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sound(SoundType.MUD),true);
    public static final Block MIRE_SLAB = registerMiscBlock("mire_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(MIRE),true);
    public static final Block MIRE_STAIRS = registerMiscBlock("mire_stairs",
            (settings) -> new StairBlock(MIRE.defaultBlockState(), settings), BlockBehaviour.Properties.copy(MIRE),true);

    public static final Block TURF = registerMiscBlock("turf",
            Block::new,BlockBehaviour.Properties.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRASS),true);
    public static final Block TURF_SLAB = registerMiscBlock("turf_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(TURF),true);
    public static final Block TURF_STAIRS = registerMiscBlock("turf_stairs",
            (settings) -> new StairBlock(TURF.defaultBlockState(), settings), BlockBehaviour.Properties.copy(TURF),true);
    public static final Block TURF_VERTICAL_SLAB = registerMiscBlock("turf_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(TURF),true);

    public static final Block DIRTY_ROOTS = registerMiscBlock("dirty_roots",
            Block::new,BlockBehaviour.Properties.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block DIRTY_ROOTS_SLAB = registerMiscBlock("dirty_roots_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(DIRTY_ROOTS),true);
    public static final Block DIRTY_ROOTS_STAIRS = registerMiscBlock("dirty_roots_stairs",
            (settings) -> new StairBlock(DIRTY_ROOTS.defaultBlockState(), settings), BlockBehaviour.Properties.copy(DIRTY_ROOTS),true);

    public static final Block FOUL_DIRT = registerMiscBlock("foul_dirt",
            Block::new,BlockBehaviour.Properties.copy(Blocks.COARSE_DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block FOUL_DIRT_SLAB = registerMiscBlock("foul_dirt_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(FOUL_DIRT),true);
    public static final Block FOUL_DIRT_STAIRS = registerMiscBlock("foul_dirt_stairs",
            (settings) -> new StairBlock(FOUL_DIRT.defaultBlockState(), settings), BlockBehaviour.Properties.copy(FOUL_DIRT),true);

    public static final Block ASHEN_DIRT = registerMiscBlock("ashen_dirt",
            Block::new,BlockBehaviour.Properties.copy(Blocks.DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block ASHEN_DIRT_SLAB = registerMiscBlock("ashen_dirt_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(ASHEN_DIRT),true);
    public static final Block ASHEN_DIRT_STAIRS = registerMiscBlock("ashen_dirt_stairs",
            (settings) -> new StairBlock(ASHEN_DIRT.defaultBlockState(), settings), BlockBehaviour.Properties.copy(ASHEN_DIRT),true);

    public static final Block COBBLY_ASHEN_DIRT = registerMiscBlock("cobbly_ashen_dirt",
            Block::new,BlockBehaviour.Properties.copy(Blocks.COARSE_DIRT).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block COBBLY_ASHEN_DIRT_SLAB = registerMiscBlock("cobbly_ashen_dirt_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(COBBLY_ASHEN_DIRT),true);
    public static final Block COBBLY_ASHEN_DIRT_STAIRS = registerMiscBlock("cobbly_ashen_dirt_stairs",
            (settings) -> new StairBlock(COBBLY_ASHEN_DIRT.defaultBlockState(), settings), BlockBehaviour.Properties.copy(ASHEN_DIRT),true);

    public static final Block ASHEN_SAND = registerMiscBlock("ashen_sand",
            (settings) -> new ColoredFallingBlock(14406560, settings), BlockBehaviour.Properties.copy(Blocks.SAND).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block ASHEN_SAND_LAYER = registerMiscBlock("ashen_sand_layer",
            (settings) -> new LayersBlock(settings, ASHEN_SAND),BlockBehaviour.Properties.copy(ASHEN_SAND), false);

    public static final Block ASHEN_GRAVEL = registerMiscBlock("ashen_gravel",
            (settings) -> new ColoredFallingBlock(14406560, settings), BlockBehaviour.Properties.copy(Blocks.GRAVEL).strength(DIRT_STRENGTH).sound(SoundType.GRAVEL),true);
    public static final Block ASHEN_GRAVEL_LAYER = registerMiscBlock("ashen_gravel_layer",
            (settings) -> new LayersBlock(settings, ASHEN_GRAVEL), BlockBehaviour.Properties.copy(ASHEN_GRAVEL), false);

    public static final Block EMBERS = registerMiscBlock("embers",
            Block::new, BlockBehaviour.Properties.copy(Blocks.NETHERRACK),true);

    public static final Block SKELETAL_PILE = registerMiscBlock("skeletal_pile",
            Block::new, BlockBehaviour.Properties.copy(Blocks.BONE_BLOCK).strength(DIRT_STRENGTH).sound(SoundType.BONE),true);
    public static final Block SKELETAL_PILE_LAYER = registerMiscBlock("skeletal_pile_layer",
            (settings) -> new LayersBlock(settings, SKELETAL_PILE),BlockBehaviour.Properties.copy(SKELETAL_PILE), false);

    public static final Block WASTE_PILE = registerMiscBlock("waste_pile",
            Block::new, BlockBehaviour.Properties.copy(Blocks.MUD).strength(DIRT_STRENGTH).sound(SoundType.MUD),true);
    public static final Block WASTE_PILE_LAYER = registerMiscBlock("waste_pile_layer",
            (settings) -> new LayersBlock(settings, WASTE_PILE),BlockBehaviour.Properties.copy(WASTE_PILE), false);

    public static final Block WATTLE_AND_BRICK = registerMiscBlock("wattle_and_brick",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_BRICK_CROSS = registerMiscBlock("wattle_and_brick_cross",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_BRICK_RIGHT = registerMiscBlock("wattle_and_brick_right",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_BRICK_LEFT = registerMiscBlock("wattle_and_brick_left",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_BRICK_PILLAR = registerMiscBlock("wattle_and_brick_pillar",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_BRICK_DIAMOND = registerMiscBlock("wattle_and_brick_diamond",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);

    public static final Block WATTLE_AND_WHITE_DAUB = registerMiscBlock("wattle_and_white_daub",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_WHITE_DAUB_CROSS = registerMiscBlock("wattle_and_white_daub_cross",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_WHITE_DAUB_RIGHT = registerMiscBlock("wattle_and_white_daub_right",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_WHITE_DAUB_LEFT = registerMiscBlock("wattle_and_white_daub_left",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_WHITE_DAUB_PILLAR = registerMiscBlock("wattle_and_white_daub_pillar",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_WHITE_DAUB_DIAMOND = registerMiscBlock("wattle_and_white_daub_diamond",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);

    public static final Block BLACK_WATTLE_AND_WHITE_DAUB = registerMiscBlock("black_wattle_and_white_daub",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block BLACK_WATTLE_AND_WHITE_DAUB_CROSS = registerMiscBlock("black_wattle_and_white_daub_cross",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block BLACK_WATTLE_AND_WHITE_DAUB_RIGHT = registerMiscBlock("black_wattle_and_white_daub_right",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block BLACK_WATTLE_AND_WHITE_DAUB_LEFT = registerMiscBlock("black_wattle_and_white_daub_left",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block BLACK_WATTLE_AND_WHITE_DAUB_PILLAR = registerMiscBlock("black_wattle_and_white_daub_pillar",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block BLACK_WATTLE_AND_WHITE_DAUB_DIAMOND = registerMiscBlock("black_wattle_and_white_daub_diamond",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);

    public static final Block GREEN_WATTLE_AND_WHITE_DAUB = registerMiscBlock("green_wattle_and_white_daub",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block GREEN_WATTLE_AND_WHITE_DAUB_CROSS = registerMiscBlock("green_wattle_and_white_daub_cross",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block GREEN_WATTLE_AND_WHITE_DAUB_RIGHT = registerMiscBlock("green_wattle_and_white_daub_right",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block GREEN_WATTLE_AND_WHITE_DAUB_LEFT = registerMiscBlock("green_wattle_and_white_daub_left",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block GREEN_WATTLE_AND_WHITE_DAUB_PILLAR = registerMiscBlock("green_wattle_and_white_daub_pillar",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block GREEN_WATTLE_AND_WHITE_DAUB_DIAMOND = registerMiscBlock("green_wattle_and_white_daub_diamond",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);

    public static final Block RED_WATTLE_AND_WHITE_DAUB = registerMiscBlock("red_wattle_and_white_daub",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block RED_WATTLE_AND_WHITE_DAUB_CROSS = registerMiscBlock("red_wattle_and_white_daub_cross",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block RED_WATTLE_AND_WHITE_DAUB_RIGHT = registerMiscBlock("red_wattle_and_white_daub_right",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block RED_WATTLE_AND_WHITE_DAUB_LEFT = registerMiscBlock("red_wattle_and_white_daub_left",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block RED_WATTLE_AND_WHITE_DAUB_PILLAR = registerMiscBlock("red_wattle_and_white_daub_pillar",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block RED_WATTLE_AND_WHITE_DAUB_DIAMOND = registerMiscBlock("red_wattle_and_white_daub_diamond",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);

    public static final Block DARK_WATTLE_AND_DARK_DAUB = registerMiscBlock("dark_wattle_and_dark_daub",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block DARK_WATTLE_AND_DARK_DAUB_CROSS = registerMiscBlock("dark_wattle_and_dark_daub_cross",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block DARK_WATTLE_AND_DARK_DAUB_RIGHT = registerMiscBlock("dark_wattle_and_dark_daub_right",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block DARK_WATTLE_AND_DARK_DAUB_LEFT = registerMiscBlock("dark_wattle_and_dark_daub_left",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block DARK_WATTLE_AND_DARK_DAUB_PILLAR = registerMiscBlock("dark_wattle_and_dark_daub_pillar",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block DARK_WATTLE_AND_DARK_DAUB_DIAMOND = registerMiscBlock("dark_wattle_and_dark_daub_diamond",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);

    public static final Block WATTLE_AND_YELLOW_DAUB = registerMiscBlock("wattle_and_yellow_daub",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_YELLOW_DAUB_CROSS = registerMiscBlock("wattle_and_yellow_daub_cross",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_YELLOW_DAUB_RIGHT = registerMiscBlock("wattle_and_yellow_daub_right",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_YELLOW_DAUB_LEFT = registerMiscBlock("wattle_and_yellow_daub_left",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_YELLOW_DAUB_PILLAR = registerMiscBlock("wattle_and_yellow_daub_pillar",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);
    public static final Block WATTLE_AND_YELLOW_DAUB_DIAMOND = registerMiscBlock("wattle_and_yellow_daub_diamond",
            Block::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD),true);

    //region METAL AND GEMS
    public static final Block RAW_MITHRIL_BLOCK = registerMiscBlock("raw_mithril_block",
            Block::new,BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK).strength(4f).requiresCorrectToolForDrops(),true);
    public static final Block MITHRIL_BLOCK = registerMiscBlock("mithril_block",
            Block::new,BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(6f).requiresCorrectToolForDrops(),true);
    public static final Block RAW_TIN_BLOCK = registerMiscBlock("raw_tin_block",
            Block::new,BlockBehaviour.Properties.copy(Blocks.STONE).strength(2f).requiresCorrectToolForDrops(),true);
    public static final Block TIN_BLOCK = registerMiscBlock("tin_block",
            Block::new,BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(3f).requiresCorrectToolForDrops(),true);
    public static final Block RAW_LEAD_BLOCK = registerMiscBlock("raw_lead_block",
            Block::new,BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK).strength(3f).requiresCorrectToolForDrops(),true);
    public static final Block LEAD_BLOCK = registerMiscBlock("lead_block",
            Block::new,BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(2f).requiresCorrectToolForDrops(),true);
    public static final Block RAW_SILVER_BLOCK = registerMiscBlock("raw_silver_block",
            Block::new,BlockBehaviour.Properties.copy(Blocks.RAW_IRON_BLOCK).strength(3f).requiresCorrectToolForDrops(),true);
    public static final Block SILVER_BLOCK = registerMiscBlock("silver_block",
            Block::new,BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(4f).requiresCorrectToolForDrops(),true);

    public static final Block BRONZE_BLOCK = registerMiscBlock("bronze_block",
            Block::new,BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5f).requiresCorrectToolForDrops(),true);
    public static final Block CRUDE_BLOCK = registerMiscBlock("crude_block",
            Block::new,BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(5.5f).requiresCorrectToolForDrops(),true);
    public static final Block STEEL_BLOCK = registerMiscBlock("steel_block",
            Block::new,BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(6f).requiresCorrectToolForDrops(),true);
    public static final Block KHAZAD_STEEL_BLOCK = registerMiscBlock("khazad_steel_block",
            Block::new,BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(7f).requiresCorrectToolForDrops(),true);
    public static final Block EDHEL_STEEL_BLOCK = registerMiscBlock("edhel_steel_block",
            Block::new,BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(6f).requiresCorrectToolForDrops(),true);
    public static final Block BURZUM_STEEL_BLOCK = registerMiscBlock("burzum_steel_block",
            Block::new,BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).strength(6f).requiresCorrectToolForDrops(),true);

    public static final Block ADAMANT_BLOCK = registerMiscBlock("adamant_block",
            Block::new,BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).requiresCorrectToolForDrops(),true);
    public static final Block RUBY_BLOCK = registerMiscBlock("ruby_block",
            Block::new,BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).requiresCorrectToolForDrops(),true);
    public static final Block SAPPHIRE_BLOCK = registerMiscBlock("sapphire_block",
            Block::new,BlockBehaviour.Properties.copy(Blocks.DIAMOND_BLOCK).requiresCorrectToolForDrops(),true);

    public static final Block QUARTZ_BLOCK = registerMiscBlock("quartz_block",
            AmethystBlock::new,BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK), true);
    public static final Block QUARTZ_CLUSTER = registerMiscBlock("quartz_cluster",
            (settings) -> new AmethystClusterBlock(7,3, settings), BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER), false);
    public static final Block LARGE_QUARTZ_BUD = registerMiscBlock("large_quartz_bud",
            (settings) -> new AmethystClusterBlock(5,3, settings), BlockBehaviour.Properties.copy(ModBlocks.QUARTZ_CLUSTER).sound(SoundType.LARGE_AMETHYST_BUD), false);
    public static final Block MEDIUM_QUARTZ_BUD = registerMiscBlock("medium_quartz_bud",
            (settings) -> new AmethystClusterBlock(4,3, settings), BlockBehaviour.Properties.copy(ModBlocks.QUARTZ_CLUSTER).sound(SoundType.MEDIUM_AMETHYST_BUD), false);
    public static final Block SMALL_QUARTZ_BUD = registerMiscBlock("small_quartz_bud",
            (settings) -> new AmethystClusterBlock(3,4, settings), BlockBehaviour.Properties.copy(ModBlocks.QUARTZ_CLUSTER).sound(SoundType.SMALL_AMETHYST_BUD), false);
    public static final Block BUDDING_QUARTZ = registerMiscBlock("budding_quartz",
            (settings) -> new CustomBuddingGemBlock(settings, List.of(SMALL_QUARTZ_BUD,MEDIUM_QUARTZ_BUD,LARGE_QUARTZ_BUD, QUARTZ_CLUSTER)), BlockBehaviour.Properties.copy(Blocks.BUDDING_AMETHYST), false);

    public static final Block RED_AGATE_BLOCK = registerMiscBlock("red_agate_block",
            AmethystBlock::new,BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK), true);
    public static final Block RED_AGATE_CLUSTER = registerMiscBlock("red_agate_cluster",
            (settings) -> new AmethystClusterBlock(7,3, settings), BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER), false);
    public static final Block LARGE_RED_AGATE_BUD = registerMiscBlock("large_red_agate_bud",
            (settings) -> new AmethystClusterBlock(5,3, settings), BlockBehaviour.Properties.copy(ModBlocks.RED_AGATE_CLUSTER).sound(SoundType.LARGE_AMETHYST_BUD), false);
    public static final Block MEDIUM_RED_AGATE_BUD = registerMiscBlock("medium_red_agate_bud",
            (settings) -> new AmethystClusterBlock(4,3, settings), BlockBehaviour.Properties.copy(ModBlocks.RED_AGATE_CLUSTER).sound(SoundType.MEDIUM_AMETHYST_BUD), false);
    public static final Block SMALL_RED_AGATE_BUD = registerMiscBlock("small_red_agate_bud",
            (settings) -> new AmethystClusterBlock(3,4, settings), BlockBehaviour.Properties.copy(ModBlocks.RED_AGATE_CLUSTER).sound(SoundType.SMALL_AMETHYST_BUD), false);
    public static final Block BUDDING_RED_AGATE = registerMiscBlock("budding_red_agate",
            (settings) -> new CustomBuddingGemBlock(settings, List.of(SMALL_RED_AGATE_BUD,MEDIUM_RED_AGATE_BUD,LARGE_RED_AGATE_BUD, RED_AGATE_CLUSTER)),BlockBehaviour.Properties.copy(Blocks.BUDDING_AMETHYST),  false);

    public static final Block CITRINE_BLOCK = registerMiscBlock("citrine_block",
            AmethystBlock::new,BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK), true);
    public static final Block CITRINE_CLUSTER = registerMiscBlock("citrine_cluster",
            (settings) -> new AmethystClusterBlock(7,3, settings), BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER), false);
    public static final Block LARGE_CITRINE_BUD = registerMiscBlock("large_citrine_bud",
            (settings) -> new AmethystClusterBlock(5,3, settings), BlockBehaviour.Properties.copy(ModBlocks.CITRINE_CLUSTER).sound(SoundType.LARGE_AMETHYST_BUD), false);
    public static final Block MEDIUM_CITRINE_BUD = registerMiscBlock("medium_citrine_bud",
            (settings) -> new AmethystClusterBlock(4,3, settings), BlockBehaviour.Properties.copy(ModBlocks.CITRINE_CLUSTER).sound(SoundType.MEDIUM_AMETHYST_BUD), false);
    public static final Block SMALL_CITRINE_BUD = registerMiscBlock("small_citrine_bud",
            (settings) -> new AmethystClusterBlock(3,4, settings), BlockBehaviour.Properties.copy(ModBlocks.CITRINE_CLUSTER).sound(SoundType.SMALL_AMETHYST_BUD), false);
    public static final Block BUDDING_CITRINE = registerMiscBlock("budding_citrine",
            (settings) -> new CustomBuddingGemBlock(settings, List.of(SMALL_CITRINE_BUD,MEDIUM_CITRINE_BUD,LARGE_CITRINE_BUD, CITRINE_CLUSTER)), BlockBehaviour.Properties.copy(Blocks.BUDDING_AMETHYST),false);

    public static final Block GLOWSTONE_BLOCK = registerMiscBlock("glowstone_block",
            AmethystBlock::new,BlockBehaviour.Properties.copy(Blocks.AMETHYST_BLOCK).luminance(state -> 12), true);
    public static final Block GLOWSTONE_CLUSTER = registerMiscBlock("glowstone_cluster",
            (settings) -> new AmethystClusterBlock(7,3, settings), BlockBehaviour.Properties.copy(Blocks.AMETHYST_CLUSTER).luminance(state -> 10), false);
    public static final Block LARGE_GLOWSTONE_BUD = registerMiscBlock("large_glowstone_bud",
            (settings) -> new AmethystClusterBlock(5,3, settings), BlockBehaviour.Properties.copy(ModBlocks.GLOWSTONE_CLUSTER).sound(SoundType.LARGE_AMETHYST_BUD).luminance(state -> 9), false);
    public static final Block MEDIUM_GLOWSTONE_BUD = registerMiscBlock("medium_glowstone_bud",
            (settings) -> new AmethystClusterBlock(4,3, settings), BlockBehaviour.Properties.copy(ModBlocks.GLOWSTONE_CLUSTER).sound(SoundType.MEDIUM_AMETHYST_BUD).luminance(state -> 7), false);
    public static final Block SMALL_GLOWSTONE_BUD = registerMiscBlock("small_glowstone_bud",
            (settings) -> new AmethystClusterBlock(3,4, settings), BlockBehaviour.Properties.copy(ModBlocks.GLOWSTONE_CLUSTER).sound(SoundType.SMALL_AMETHYST_BUD).luminance(state -> 5), false);
    public static final Block BUDDING_GLOWSTONE = registerMiscBlock("budding_glowstone",
            (settings) -> new CustomBuddingGemBlock(settings, List.of(SMALL_GLOWSTONE_BUD,MEDIUM_GLOWSTONE_BUD,LARGE_GLOWSTONE_BUD, GLOWSTONE_CLUSTER)), BlockBehaviour.Properties.copy(Blocks.BUDDING_AMETHYST),false);

    public static final Block NET = registerMiscBlock("net",
            IronBarsBlock::new, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).sound(SoundType.WOOL), true);

    public static final Block COPPER_BARS = registerMiscBlock("copper_bars",
            (settings) -> new OxidizablePaneBlock(WeatheringCopper.WeatherState.UNAFFECTED, settings), BlockBehaviour.Properties.copy(Blocks.CUT_COPPER_SLAB), true);
    public static final Block EXPOSED_COPPER_BARS = registerMiscBlock("exposed_copper_bars",
            (settings) -> new OxidizablePaneBlock(WeatheringCopper.WeatherState.EXPOSED, settings), BlockBehaviour.Properties.copy(Blocks.EXPOSED_CUT_COPPER_SLAB), true);
    public static final Block WEATHERED_COPPER_BARS = registerMiscBlock("weathered_copper_bars",
            (settings) -> new OxidizablePaneBlock(WeatheringCopper.WeatherState.WEATHERED, settings), BlockBehaviour.Properties.copy(Blocks.WEATHERED_CUT_COPPER_SLAB), true);
    public static final Block OXIDIZED_COPPER_BARS = registerMiscBlock("oxidized_copper_bars",
            (settings) -> new OxidizablePaneBlock(WeatheringCopper.WeatherState.OXIDIZED, settings), BlockBehaviour.Properties.copy(Blocks.OXIDIZED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_COPPER_BARS = registerMiscBlock("waxed_copper_bars",
            IronBarsBlock::new, BlockBehaviour.Properties.copy(Blocks.WAXED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_EXPOSED_COPPER_BARS = registerMiscBlock("waxed_exposed_copper_bars",
            IronBarsBlock::new, BlockBehaviour.Properties.copy(Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_WEATHERED_COPPER_BARS = registerMiscBlock("waxed_weathered_copper_bars",
            IronBarsBlock::new, BlockBehaviour.Properties.copy(Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_OXIDIZED_COPPER_BARS = registerMiscBlock("waxed_oxidized_copper_bars",
            IronBarsBlock::new, BlockBehaviour.Properties.copy(Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB), true);

    public static final Block BRONZE_BARS = registerMiscBlock("bronze_bars",
            IronBarsBlock::new, BlockBehaviour.Properties.copy(Blocks.IRON_BARS), true);
    public static final Block CRUDE_BARS = registerMiscBlock("crude_bars",
            IronBarsBlock::new, BlockBehaviour.Properties.copy(Blocks.IRON_BARS), true);
    public static final Block TREATED_STEEL_BARS = registerMiscBlock("treated_steel_bars",
            IronBarsBlock::new, BlockBehaviour.Properties.copy(Blocks.IRON_BARS), true);
    public static final Block BURZUM_BARS = registerMiscBlock("burzum_bars",
            IronBarsBlock::new, BlockBehaviour.Properties.copy(Blocks.IRON_BARS), true);
    public static final Block SILVER_BARS = registerMiscBlock("silver_bars",
            IronBarsBlock::new, BlockBehaviour.Properties.copy(Blocks.IRON_BARS).sound(SoundType.METAL), true);
    public static final Block GILDED_BARS = registerMiscBlock("gilded_bars",
            IronBarsBlock::new, BlockBehaviour.Properties.copy(Blocks.IRON_BARS).sound(SoundType.COPPER), true);

    public static final Block AGED_WOOD_DOOR = registerMiscBlock("aged_wood_door",
            (settings) -> new DoorBlock(settings, BlockSetType.DARK_OAK), BlockBehaviour.Properties.copy(Blocks.DARK_OAK_DOOR), true);
    public static final Block BRONZE_DOOR = registerMiscBlock("bronze_door",
            (settings) -> new DoorBlock(settings, BlockSetType.OAK), BlockBehaviour.Properties.copy(Blocks.IRON_DOOR), true);
    public static final Block CRUDE_DOOR = registerMiscBlock("crude_door",
            (settings) -> new DoorBlock(settings, BlockSetType.OAK), BlockBehaviour.Properties.copy(Blocks.IRON_DOOR), true);
    public static final Block TREATED_STEEL_DOOR = registerMiscBlock("treated_steel_door",
            (settings) -> new DoorBlock(settings, BlockSetType.OAK), BlockBehaviour.Properties.copy(Blocks.IRON_DOOR), true);
    public static final Block AGED_WOOD_TRAPDOOR = registerMiscBlock("aged_wood_trapdoor",
            (settings) -> new TrapDoorBlock(settings, BlockSetType.DARK_OAK), BlockBehaviour.Properties.copy(Blocks.DARK_OAK_TRAPDOOR), true);
    public static final Block BRONZE_TRAPDOOR = registerMiscBlock("bronze_trapdoor",
            (settings) -> new TrapDoorBlock(settings, BlockSetType.IRON), BlockBehaviour.Properties.copy(Blocks.IRON_TRAPDOOR), true);
    public static final Block CRUDE_TRAPDOOR = registerMiscBlock("crude_trapdoor",
            (settings) -> new TrapDoorBlock(settings, BlockSetType.IRON), BlockBehaviour.Properties.copy(Blocks.IRON_TRAPDOOR), true);
    public static final Block TREATED_STEEL_TRAPDOOR = registerMiscBlock("treated_steel_trapdoor",
            (settings) -> new TrapDoorBlock(settings, BlockSetType.IRON), BlockBehaviour.Properties.copy(Blocks.IRON_TRAPDOOR), true);

    public static final Block BURZUM_SPIKES = registerMiscBlock("burzum_spikes",
            SpikesBlock::new, BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion(), true);
    //endregion

    public static final Block RIVER_SAND = registerMiscBlock("river_sand",
            (settings) -> new ColoredFallingBlock(-8356741, settings), BlockBehaviour.Properties.copy(Blocks.SAND).sound(SoundType.SAND), true);
    public static final Block BLACK_SAND = registerMiscBlock("black_sand",
            (settings) -> new ColoredFallingBlock(-8356741, settings), BlockBehaviour.Properties.copy(Blocks.SAND).sound(SoundType.SAND), true);
    public static final Block WHITE_SAND = registerMiscBlock("white_sand",
            (settings) -> new ColoredFallingBlock(14406560, settings), BlockBehaviour.Properties.copy(Blocks.SAND).sound(SoundType.SAND), true);

    public static final Block STONE_MYCELIUM = registerMiscBlock("stone_mycelium",
            StoneMyceliumBlock::new, BlockBehaviour.Properties.copy(Blocks.STONE).ticksRandomly(), false);

    public static final Block ASH_BLOCK = registerMiscBlock("ash_block",
            (settings) -> new ColoredFallingBlock(-8356741, settings), BlockBehaviour.Properties.copy(Blocks.SAND).strength(DIRT_STRENGTH).sound(SoundType.SAND), true);

    //region VANILLA SLABS
    public static final Block DIRT_SLAB = registerMiscBlock("dirt_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.DIRT), true);
    public static final Block COARSE_DIRT_SLAB = registerMiscBlock("coarse_dirt_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.COARSE_DIRT), true);
    public static final Block ROOTED_DIRT_SLAB = registerMiscBlock("rooted_dirt_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.ROOTED_DIRT), true);
    public static final Block MUD_SLAB = registerMiscBlock("mud_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.MUD), true);
    public static final Block MOSS_SLAB = registerMiscBlock("moss_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.MOSS_BLOCK), true);

    public static final Block PACKED_MUD_SLAB = registerStoneBlock("packed_mud_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD), true);
    public static final Block PACKED_MUD_VERTICAL_SLAB = registerStoneBlock("packed_mud_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.PACKED_MUD), true);
    public static final Block PACKED_MUD_STAIRS = registerStoneBlock("packed_mud_stairs",
            (settings) -> new StairBlock(Blocks.PACKED_MUD.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.PACKED_MUD), true);
    public static final Block PACKED_MUD_WALL = registerStoneBlock("packed_mud_wall",
            WallBlock::new,BlockBehaviour.Properties.copy(Blocks.PACKED_MUD), true);

    public static final Block CUT_COPPER_WALL = registerMiscBlock("cut_copper_wall",
            (settings) -> new OxidizableWallBlock(WeatheringCopper.WeatherState.UNAFFECTED, settings), BlockBehaviour.Properties.copy(Blocks.CUT_COPPER_SLAB), true);
    public static final Block EXPOSED_CUT_COPPER_WALL = registerMiscBlock("exposed_cut_copper_wall",
            (settings) -> new OxidizableWallBlock(WeatheringCopper.WeatherState.EXPOSED, settings), BlockBehaviour.Properties.copy(Blocks.EXPOSED_CUT_COPPER_SLAB), true);
    public static final Block WEATHERED_CUT_COPPER_WALL = registerMiscBlock("weathered_cut_copper_wall",
            (settings) -> new OxidizableWallBlock(WeatheringCopper.WeatherState.WEATHERED, settings), BlockBehaviour.Properties.copy(Blocks.WEATHERED_CUT_COPPER_SLAB), true);
    public static final Block OXIDIZED_CUT_COPPER_WALL = registerMiscBlock("oxidized_cut_copper_wall",
            (settings) -> new OxidizableWallBlock(WeatheringCopper.WeatherState.OXIDIZED, settings), BlockBehaviour.Properties.copy(Blocks.OXIDIZED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_CUT_COPPER_WALL = registerMiscBlock("waxed_cut_copper_wall",
            WallBlock::new,BlockBehaviour.Properties.copy(Blocks.WAXED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_EXPOSED_CUT_COPPER_WALL = registerMiscBlock("waxed_exposed_cut_copper_wall",
            WallBlock::new,BlockBehaviour.Properties.copy(Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_WEATHERED_CUT_COPPER_WALL = registerMiscBlock("waxed_weathered_cut_copper_wall",
            WallBlock::new,BlockBehaviour.Properties.copy(Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_OXIDIZED_CUT_COPPER_WALL = registerMiscBlock("waxed_oxidized_cut_copper_wall",
            WallBlock::new,BlockBehaviour.Properties.copy(Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB), true);

    public static final Block BLACK_WOOL_SLAB = registerMiscBlock("black_wool_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block BLUE_WOOL_SLAB = registerMiscBlock("blue_wool_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block BROWN_WOOL_SLAB = registerMiscBlock("brown_wool_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block CYAN_WOOL_SLAB = registerMiscBlock("cyan_wool_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block GRAY_WOOL_SLAB = registerMiscBlock("gray_wool_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block GREEN_WOOL_SLAB = registerMiscBlock("green_wool_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block LIGHT_BLUE_WOOL_SLAB = registerMiscBlock("light_blue_wool_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block LIGHT_GRAY_WOOL_SLAB = registerMiscBlock("light_gray_wool_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block LIME_WOOL_SLAB = registerMiscBlock("lime_wool_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block MAGENTA_WOOL_SLAB = registerMiscBlock("magenta_wool_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block ORANGE_WOOL_SLAB = registerMiscBlock("orange_wool_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block PINK_WOOL_SLAB = registerMiscBlock("pink_wool_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block PURPLE_WOOL_SLAB = registerMiscBlock("purple_wool_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block RED_WOOL_SLAB = registerMiscBlock("red_wool_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block WHITE_WOOL_SLAB = registerMiscBlock("white_wool_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block YELLOW_WOOL_SLAB = registerMiscBlock("yellow_wool_slab",
            SlabBlock::new,BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    //endregion

    //region VANILLA VERTICAL SLABS
    public static final Block BRICK_VERTICAL_SLAB = registerStoneBlock("brick_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.BRICK_SLAB), true);
    public static final Block MUD_BRICK_VERTICAL_SLAB = registerStoneBlock("mud_brick_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.MUD_BRICK_SLAB), true);
    public static final Block SANDSTONE_VERTICAL_SLAB = registerStoneBlock("sandstone_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.SANDSTONE_SLAB), true);
    public static final Block SMOOTH_SANDSTONE_VERTICAL_SLAB = registerStoneBlock("smooth_sandstone_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.SMOOTH_SANDSTONE_SLAB), true);
    public static final Block CUT_SANDSTONE_VERTICAL_SLAB = registerStoneBlock("cut_sandstone_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.CUT_SANDSTONE_SLAB), true);
    public static final Block RED_SANDSTONE_VERTICAL_SLAB = registerStoneBlock("red_sandstone_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.RED_SANDSTONE_SLAB), true);
    public static final Block SMOOTH_RED_SANDSTONE_VERTICAL_SLAB = registerStoneBlock("smooth_red_sandstone_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.SMOOTH_RED_SANDSTONE_SLAB), true);
    public static final Block CUT_RED_SANDSTONE_VERTICAL_SLAB = registerStoneBlock("cut_red_sandstone_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.CUT_RED_SANDSTONE_SLAB), true);
    public static final Block PRISMARINE_VERTICAL_SLAB = registerStoneBlock("prismarine_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.PRISMARINE_SLAB), true);
    public static final Block PRISMARINE_BRICK_VERTICAL_SLAB = registerStoneBlock("prismarine_brick_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.PRISMARINE_BRICK_SLAB), true);
    public static final Block DARK_PRISMARINE_VERTICAL_SLAB = registerStoneBlock("dark_prismarine_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.DARK_PRISMARINE_SLAB), true);
    public static final Block NETHER_BRICK_VERTICAL_SLAB = registerStoneBlock("nether_brick_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.NETHER_BRICK_SLAB), true);
    public static final Block RED_NETHER_BRICK_VERTICAL_SLAB = registerStoneBlock("red_nether_brick_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.RED_NETHER_BRICK_SLAB), true);
    public static final Block END_STONE_BRICK_VERTICAL_SLAB = registerStoneBlock("end_stone_brick_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.END_STONE_BRICK_SLAB), true);
    public static final Block PURPUR_VERTICAL_SLAB = registerStoneBlock("purpur_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.PURPUR_SLAB), true);
    public static final Block QUARTZ_VERTICAL_SLAB = registerStoneBlock("quartz_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.QUARTZ_SLAB), true);
    public static final Block SMOOTH_QUARTZ_VERTICAL_SLAB = registerStoneBlock("smooth_quartz_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.SMOOTH_QUARTZ_SLAB), true);
    public static final Block CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("cut_copper_vertical_slab",
            (settings) -> new OxidizableVerticalSlabBlock(WeatheringCopper.WeatherState.UNAFFECTED, settings), BlockBehaviour.Properties.copy(Blocks.CUT_COPPER_SLAB), true);
    public static final Block EXPOSED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("exposed_cut_copper_vertical_slab",
            (settings) -> new OxidizableVerticalSlabBlock(WeatheringCopper.WeatherState.EXPOSED, settings), BlockBehaviour.Properties.copy(Blocks.EXPOSED_CUT_COPPER_SLAB), true);
    public static final Block WEATHERED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("weathered_cut_copper_vertical_slab",
            (settings) -> new OxidizableVerticalSlabBlock(WeatheringCopper.WeatherState.WEATHERED, settings), BlockBehaviour.Properties.copy(Blocks.WEATHERED_CUT_COPPER_SLAB), true);
    public static final Block OXIDIZED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("oxidized_cut_copper_vertical_slab",
            (settings) -> new OxidizableVerticalSlabBlock(WeatheringCopper.WeatherState.OXIDIZED, settings), BlockBehaviour.Properties.copy(Blocks.OXIDIZED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("waxed_cut_copper_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.WAXED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_EXPOSED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("waxed_exposed_cut_copper_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.WAXED_EXPOSED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_WEATHERED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("waxed_weathered_cut_copper_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.WAXED_WEATHERED_CUT_COPPER_SLAB), true);
    public static final Block WAXED_OXIDIZED_CUT_COPPER_VERTICAL_SLAB = registerMiscBlock("waxed_oxidized_cut_copper_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.WAXED_OXIDIZED_CUT_COPPER_SLAB), true);

    public static final Block BLACK_WOOL_VERTICAL_SLAB = registerMiscBlock("black_wool_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block BLUE_WOOL_VERTICAL_SLAB = registerMiscBlock("blue_wool_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block BROWN_WOOL_VERTICAL_SLAB = registerMiscBlock("brown_wool_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block CYAN_WOOL_VERTICAL_SLAB = registerMiscBlock("cyan_wool_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block GRAY_WOOL_VERTICAL_SLAB = registerMiscBlock("gray_wool_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block GREEN_WOOL_VERTICAL_SLAB = registerMiscBlock("green_wool_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block LIGHT_BLUE_WOOL_VERTICAL_SLAB = registerMiscBlock("light_blue_wool_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block LIGHT_GRAY_WOOL_VERTICAL_SLAB = registerMiscBlock("light_gray_wool_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block LIME_WOOL_VERTICAL_SLAB = registerMiscBlock("lime_wool_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block MAGENTA_WOOL_VERTICAL_SLAB = registerMiscBlock("magenta_wool_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block ORANGE_WOOL_VERTICAL_SLAB = registerMiscBlock("orange_wool_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block PINK_WOOL_VERTICAL_SLAB = registerMiscBlock("pink_wool_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block PURPLE_WOOL_VERTICAL_SLAB = registerMiscBlock("purple_wool_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block RED_WOOL_VERTICAL_SLAB = registerMiscBlock("red_wool_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block WHITE_WOOL_VERTICAL_SLAB = registerMiscBlock("white_wool_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block YELLOW_WOOL_VERTICAL_SLAB = registerMiscBlock("yellow_wool_vertical_slab",
            VerticalSlabBlock::new, BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    //endregion

    //region VANILLA STAIRS
    public static final Block DIRT_STAIRS = registerMiscBlock("dirt_stairs",
            (settings) -> new StairBlock(Blocks.DIRT.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.DIRT), true);
    public static final Block COARSE_DIRT_STAIRS = registerMiscBlock("coarse_dirt_stairs",
            (settings) -> new StairBlock(Blocks.COARSE_DIRT.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.COARSE_DIRT), true);
    public static final Block ROOTED_DIRT_STAIRS = registerMiscBlock("rooted_dirt_stairs",
            (settings) -> new StairBlock(Blocks.ROOTED_DIRT.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.ROOTED_DIRT), true);
    public static final Block MUD_STAIRS = registerMiscBlock("mud_stairs",
            (settings) -> new StairBlock(Blocks.MUD.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.DIRT).sound(SoundType.MUD), true);
    public static final Block MOSS_STAIRS = registerMiscBlock("moss_stairs",
            (settings) -> new StairBlock(Blocks.MOSS_BLOCK.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.MOSS_BLOCK), true);

    public static final Block BLACK_WOOL_STAIRS = registerMiscBlock("black_wool_stairs",
            (settings) -> new StairBlock(Blocks.BLACK_WOOL.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block BLUE_WOOL_STAIRS = registerMiscBlock("blue_wool_stairs",
            (settings) -> new StairBlock(Blocks.BLACK_WOOL.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block BROWN_WOOL_STAIRS = registerMiscBlock("brown_wool_stairs",
            (settings) -> new StairBlock(Blocks.BLACK_WOOL.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block CYAN_WOOL_STAIRS = registerMiscBlock("cyan_wool_stairs",
            (settings) -> new StairBlock(Blocks.BLACK_WOOL.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block GRAY_WOOL_STAIRS = registerMiscBlock("gray_wool_stairs",
            (settings) -> new StairBlock(Blocks.BLACK_WOOL.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block GREEN_WOOL_STAIRS = registerMiscBlock("green_wool_stairs",
            (settings) -> new StairBlock(Blocks.BLACK_WOOL.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block LIGHT_BLUE_WOOL_STAIRS = registerMiscBlock("light_blue_wool_stairs",
            (settings) -> new StairBlock(Blocks.BLACK_WOOL.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block LIGHT_GRAY_WOOL_STAIRS = registerMiscBlock("light_gray_wool_stairs",
            (settings) -> new StairBlock(Blocks.BLACK_WOOL.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block LIME_WOOL_STAIRS = registerMiscBlock("lime_wool_stairs",
            (settings) -> new StairBlock(Blocks.BLACK_WOOL.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block MAGENTA_WOOL_STAIRS = registerMiscBlock("magenta_wool_stairs",
            (settings) -> new StairBlock(Blocks.BLACK_WOOL.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block ORANGE_WOOL_STAIRS = registerMiscBlock("orange_wool_stairs",
            (settings) -> new StairBlock(Blocks.BLACK_WOOL.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block PINK_WOOL_STAIRS = registerMiscBlock("pink_wool_stairs",
            (settings) -> new StairBlock(Blocks.BLACK_WOOL.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block PURPLE_WOOL_STAIRS = registerMiscBlock("purple_wool_stairs",
            (settings) -> new StairBlock(Blocks.BLACK_WOOL.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block RED_WOOL_STAIRS = registerMiscBlock("red_wool_stairs",
            (settings) -> new StairBlock(Blocks.BLACK_WOOL.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block WHITE_WOOL_STAIRS = registerMiscBlock("white_wool_stairs",
            (settings) -> new StairBlock(Blocks.BLACK_WOOL.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    public static final Block YELLOW_WOOL_STAIRS = registerMiscBlock("yellow_wool_stairs",
            (settings) -> new StairBlock(Blocks.BLACK_WOOL.defaultBlockState(), settings), BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).ignitedByLava(), true);
    //endregion

    //region VANILLA LAYERS
    public static final Block GRAVEL_LAYER = registerMiscBlock("gravel_layer",
            (settings) -> new LayersBlock(settings, Blocks.GRAVEL), BlockBehaviour.Properties.copy(Blocks.GRAVEL), false);
    public static final Block SAND_LAYER = registerMiscBlock("sand_layer",
            (settings) -> new LayersBlock(settings, Blocks.SAND), BlockBehaviour.Properties.copy(Blocks.SAND), false);
    public static final Block BLACK_SAND_LAYER = registerMiscBlock("black_sand_layer",
            (settings) -> new LayersBlock(settings, BLACK_SAND), BlockBehaviour.Properties.copy(Blocks.SAND), false);
    public static final Block WHITE_SAND_LAYER = registerMiscBlock("white_sand_layer",
            (settings) -> new LayersBlock(settings, WHITE_SAND), BlockBehaviour.Properties.copy(Blocks.SAND), false);
    //endregion

    //region FOOD
    public static final Block LAYERED_CAKE = registerTablessBlock("layered_cake",
            LayeredCakeBlock::new, BlockBehaviour.Properties.copy(Blocks.CAKE), false);
    public static final Block CANDLES_LAYERED_CAKE = registerTablessBlock("candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.CANDLE, settings), BlockBehaviour.Properties.copy(Blocks.BLACK_CANDLE_CAKE), false);
    public static final Block WHITE_CANDLES_LAYERED_CAKE = registerTablessBlock("white_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.WHITE_CANDLE, settings), BlockBehaviour.Properties.copy(Blocks.BLACK_CANDLE_CAKE), false);
    public static final Block ORANGE_CANDLES_LAYERED_CAKE = registerTablessBlock("orange_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.ORANGE_CANDLE, settings), BlockBehaviour.Properties.copy(Blocks.BLACK_CANDLE_CAKE), false);
    public static final Block MAGENTA_CANDLES_LAYERED_CAKE = registerTablessBlock("magenta_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.MAGENTA_CANDLE, settings), BlockBehaviour.Properties.copy(Blocks.BLACK_CANDLE_CAKE), false);
    public static final Block LIGHT_BLUE_CANDLES_LAYERED_CAKE = registerTablessBlock("light_blue_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.LIGHT_BLUE_CANDLE, settings), BlockBehaviour.Properties.copy(Blocks.BLACK_CANDLE_CAKE), false);
    public static final Block YELLOW_CANDLES_LAYERED_CAKE = registerTablessBlock("yellow_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.YELLOW_CANDLE, settings), BlockBehaviour.Properties.copy(Blocks.BLACK_CANDLE_CAKE), false);
    public static final Block LIME_CANDLES_LAYERED_CAKE = registerTablessBlock("lime_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.LIME_CANDLE, settings), BlockBehaviour.Properties.copy(Blocks.BLACK_CANDLE_CAKE), false);
    public static final Block PINK_CANDLES_LAYERED_CAKE = registerTablessBlock("pink_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.PINK_CANDLE, settings), BlockBehaviour.Properties.copy(Blocks.BLACK_CANDLE_CAKE), false);
    public static final Block GRAY_CANDLES_LAYERED_CAKE = registerTablessBlock("gray_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.GRAY_CANDLE, settings), BlockBehaviour.Properties.copy(Blocks.BLACK_CANDLE_CAKE), false);
    public static final Block LIGHT_GRAY_CANDLES_LAYERED_CAKE = registerTablessBlock("light_gray_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.LIGHT_GRAY_CANDLE, settings), BlockBehaviour.Properties.copy(Blocks.BLACK_CANDLE_CAKE), false);
    public static final Block CYAN_CANDLES_LAYERED_CAKE = registerTablessBlock("cyan_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.CYAN_CANDLE, settings), BlockBehaviour.Properties.copy(Blocks.BLACK_CANDLE_CAKE), false);
    public static final Block PURPLE_CANDLES_LAYERED_CAKE = registerTablessBlock("purple_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.PURPLE_CANDLE, settings), BlockBehaviour.Properties.copy(Blocks.BLACK_CANDLE_CAKE), false);
    public static final Block BLUE_CANDLES_LAYERED_CAKE = registerTablessBlock("blue_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.BLUE_CANDLE, settings), BlockBehaviour.Properties.copy(Blocks.BLACK_CANDLE_CAKE), false);
    public static final Block BROWN_CANDLES_LAYERED_CAKE = registerTablessBlock("brown_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.BROWN_CANDLE, settings), BlockBehaviour.Properties.copy(Blocks.BLACK_CANDLE_CAKE), false);
    public static final Block GREEN_CANDLES_LAYERED_CAKE = registerTablessBlock("green_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.GREEN_CANDLE, settings), BlockBehaviour.Properties.copy(Blocks.BLACK_CANDLE_CAKE), false);
    public static final Block RED_CANDLES_LAYERED_CAKE = registerTablessBlock("red_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.RED_CANDLE, settings), BlockBehaviour.Properties.copy(Blocks.BLACK_CANDLE_CAKE), false);
    public static final Block BLACK_CANDLES_LAYERED_CAKE = registerTablessBlock("black_candles_layered_cake",
            (settings) -> new CandleLayeredCakeBlock(Blocks.BLACK_CANDLE, settings), BlockBehaviour.Properties.copy(Blocks.BLACK_CANDLE_CAKE), false);

    //

    public static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings, boolean drop, List<ItemStack> group){
        // Port shim: the port-only set classes (RoofBlockSets, MushroomBlockSets, ...) re-declare
        // blocks that WoodBlockSets / GenericBlockSets already registered.  Reuse the existing
        // entry instead of aborting the whole registry with a duplicate-id error.
        Block existing = Registries.BLOCK.get(new ResourceLocation(MiddleEarth.MOD_ID, name));
        if (existing != Blocks.AIR) {
            return existing;
        }
        Block block = (Block)factory.apply(settings);
        registerBlockItem(name, block);
        if(drop){
            BlockDrops.blocks.add(block);
        }
        group.add(block.asItem().getDefaultInstance());
        TranslationEntries.blockEntries.add(block);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.BLOCK, name));
        return Registry.register(Registries.BLOCK, keyOfBlock(name), block);
    }

    public static Block registerTablessBlock(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings, boolean drop) {
        Block block = factory.apply(settings);
        if(drop){
            BlockDrops.blocks.add(block);
        }
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.BLOCK, name));

        return Registry.register(Registries.BLOCK, ModBlocks.keyOfBlock(name), block);
    }

    public static Block registerStoneBlock(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings, boolean drop) {
        return registerBlock(name, factory, settings, drop, ModItemGroups.STONE_BLOCKS_CONTENTS);
    }

    public static Block registerWoodBlock(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings, boolean drop) {
        return registerBlock(name, factory, settings, drop, ModItemGroups.WOOD_BLOCKS_CONTENTS);
    }

    public static Block registerMiscBlock(String name, Function<BlockBehaviour.Properties, Block> factory, BlockBehaviour.Properties settings, boolean drop) {
        return registerBlock(name, factory, settings, drop, ModItemGroups.MISC_BLOCKS_CONTENTS);
    }

    // Legacy (String, Block, boolean) overloads used by tree-local set classes (MushroomBlockSets,
    // RoofBlockSets) that pre-build blocks with their own settings; the factory ignores the passed settings.
    public static Block registerBlock(String name, Block block, boolean drop) {
        return registerBlock(name, (settings) -> block, BlockBehaviour.Properties.create(), drop, ModItemGroups.MISC_BLOCKS_CONTENTS);
    }

    public static Block registerStoneBlock(String name, Block block, boolean drop) {
        return registerBlock(name, (settings) -> block, BlockBehaviour.Properties.create(), drop, ModItemGroups.STONE_BLOCKS_CONTENTS);
    }

    public static Block registerWoodBlock(String name, Block block, boolean drop) {
        return registerBlock(name, (settings) -> block, BlockBehaviour.Properties.create(), drop, ModItemGroups.WOOD_BLOCKS_CONTENTS);
    }

    public static Block registerMiscBlock(String name, Block block, boolean drop) {
        return registerBlock(name, (settings) -> block, BlockBehaviour.Properties.create(), drop, ModItemGroups.MISC_BLOCKS_CONTENTS);
    }

    static void registerBlockItem(String name, Block block) {
        var item = Registry.register(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, name),
                new BlockItem(block, new MESettings()));
        Item.BLOCK_ITEMS.put(block, item);
        RegistryAliasesME.aliases.add(new RegistryAliasesME.Alias(Registries.ITEM, name));
    }

    public static ResourceLocation keyOfBlock(String id) {
        return new ResourceLocation(MiddleEarth.MOD_ID, id);
    }

    public static ResourceLocation keyOfItem(String id) {
        return new ResourceLocation(MiddleEarth.MOD_ID, id);
    }

    public static void registerModBlocks() {
        MiddleEarth.LOGGER.logDebugMsg("Registering ModBlocks for " + MiddleEarth.MOD_ID);
    }

    /** 1.20.1 replacement for donor ColoredFallingBlock/ColorCode (1.21.x vanilla). */
    private static class ColoredFallingBlock extends FallingBlock {
        private final int dustColor;

        public ColoredFallingBlock(int dustColor, BlockBehaviour.Properties settings) {
            super(settings);
            this.dustColor = dustColor;
        }

        @Override
        public int getColor(BlockState state, BlockGetter world, BlockPos pos) {
            return dustColor;
        }
    }

}
