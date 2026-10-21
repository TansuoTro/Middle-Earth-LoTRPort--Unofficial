package net.jukoz.me.block.registration;

import net.minecraft.block.enums.Instrument;
import net.minecraft.item.ItemStack;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.utils.BlockSetRegistration;
import net.jukoz.me.block.utils.BlockRecordTypes;
import net.jukoz.me.block.utils.setBuilders.StoneBlockSetBuilder;
import net.jukoz.me.block.utils.StoneBlockTypes;
import net.minecraft.block.*;
import net.minecraft.sound.BlockSoundGroup;
import net.jukoz.me.item.utils.ItemGroupsME;

import java.util.ArrayList;
import java.util.List;

public class StoneBlockSets {
    public static final float STONE_HARDNESS = Blocks.STONE.getHardness(); //1.5f
    public static final float STONE_BLAST_RESISTANCE = Blocks.STONE.getBlastResistance(); //6.0f

    public static final float DEEPSLATE_HARDNESS = Blocks.DEEPSLATE.getHardness();
    public static final float DEEPSLATE_BLAST_RESISTANCE = Blocks.DEEPSLATE.getBlastResistance();

    public static final float NURGON_HARDNESS = 5.0F;
    public static final float NURGON_BLAST_RESISTANCE = 7.0F;

    public static final float MEDGON_HARDNESS = 7.0F;
    public static final float MEDGON_BLAST_RESISTANCE = 9.0F;

    public static List<StoneBlockSetBuilder> stoneSetsList = new ArrayList<>();

    /**
     * Legacy flat view of the stone sets. The datagen providers were written against the
     * old {@code SimpleBlockSet}/{@code SimpleBlockSetMain} records; the registration
     * itself now uses {@link StoneBlockSetBuilder}. These adapters keep the old call sites
     * working (see {@link #registerStoneSet}).
     */
    public static final List<SimpleBlockSetMain> setsMain = new ArrayList<>();
    public static final List<SimpleBlockSet> sets = new ArrayList<>();

    public static class SimpleBlockSet {
        private final BlockRecordTypes.RegularSet delegate;
        private final Block source;

        public SimpleBlockSet(BlockRecordTypes.RegularSet delegate) {
            this(delegate, null);
        }

        public SimpleBlockSet(BlockRecordTypes.RegularSet delegate, Block source) {
            this.delegate = delegate;
            this.source = source;
        }

        public Block source() { return source; }

        public Block base() { return delegate == null ? null : delegate.base(); }
        public Block slab() { return delegate == null ? null : delegate.slab(); }
        public Block verticalSlab() { return delegate == null ? null : delegate.verticalSlab(); }
        public Block stairs() { return delegate == null ? null : delegate.stairs(); }
        public Block wall() { return delegate == null ? null : delegate.wall(); }
    }

    public static class SimpleBlockSetMain {
        private final BlockRecordTypes.BaseStoneSet delegate;

        public SimpleBlockSetMain(BlockRecordTypes.BaseStoneSet delegate) {
            this.delegate = delegate;
        }

        public Block source() { return delegate == null ? null : delegate.base(); }

        public Block base() { return delegate == null ? null : delegate.base(); }
        public Block slab() { return delegate == null ? null : delegate.slab(); }
        public Block verticalSlab() { return delegate == null ? null : delegate.verticalSlab(); }
        public Block stairs() { return delegate == null ? null : delegate.stairs(); }
        public Block wall() { return delegate == null ? null : delegate.wall(); }
        public Block pressurePlate() { return delegate == null ? null : delegate.pressurePlate(); }
        public Block button() { return delegate == null ? null : delegate.button(); }
        public Block stool() { return delegate == null ? null : delegate.stool(); }
        public Block table() { return delegate == null ? null : delegate.table(); }
        public Block chair() { return delegate == null ? null : delegate.chair(); }
    }

    public static StoneBlockSetBuilder STONE_SET = registerStoneSet(new StoneBlockSetBuilder("stone",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.STONE_GRAY, Instrument.BASEDRUM, BlockSoundGroup.STONE,
            true, true, true, true, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLESTONE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder GRANITE_SET = registerStoneSet(new StoneBlockSetBuilder("granite",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.ORANGE, Instrument.BASEDRUM, BlockSoundGroup.STONE,
            true, true, true, false, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS));

    public static StoneBlockSetBuilder DIORITE_SET = registerStoneSet(new StoneBlockSetBuilder("diorite",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE, Instrument.BASEDRUM, BlockSoundGroup.STONE,
            true, true, true, false, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS));

    public static StoneBlockSetBuilder ANDESITE_SET = registerStoneSet(new StoneBlockSetBuilder("andesite",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE_GRAY, Instrument.BASEDRUM, BlockSoundGroup.STONE,
            true, true, true, false, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS));

    public static StoneBlockSetBuilder CALCITE_SET = registerStoneSet(new StoneBlockSetBuilder("calcite",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE, Instrument.BASEDRUM, BlockSoundGroup.CALCITE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder DRIPSTONE_SET = registerStoneSet(new StoneBlockSetBuilder("dripstone",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE, Instrument.BASEDRUM, BlockSoundGroup.DRIPSTONE_BLOCK, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS));

    public static StoneBlockSetBuilder DEEPSLATE_SET = registerStoneSet(new StoneBlockSetBuilder("deepslate",
            DEEPSLATE_HARDNESS, DEEPSLATE_BLAST_RESISTANCE, MapColor.DEEPSLATE_GRAY, Instrument.BASEDRUM, BlockSoundGroup.DEEPSLATE,
            true, true, true, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder TUFF_SET = registerStoneSet(new StoneBlockSetBuilder("tuff",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE, Instrument.BASEDRUM, BlockSoundGroup.TUFF, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder BASALT_SET = registerStoneSet(new StoneBlockSetBuilder("basalt",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.GRAY, Instrument.BASEDRUM, BlockSoundGroup.BASALT, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS_PILLAR)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder BLACKSTONE_SET = registerStoneSet(new StoneBlockSetBuilder("blackstone",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.BLACK, Instrument.BASEDRUM, BlockSoundGroup.STONE,
            true, true, true, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder DOLOMITE_SET = registerStoneSet(new StoneBlockSetBuilder("dolomite",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.LIGHT_GRAY, Instrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS_PILLAR)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder SLATE_SET = registerStoneSet(new StoneBlockSetBuilder("slate",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.STONE_GRAY, Instrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder NURGON_SET = registerStoneSet(new StoneBlockSetBuilder("nurgon",
            NURGON_HARDNESS, NURGON_BLAST_RESISTANCE, MapColor.TERRACOTTA_GRAY, Instrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder MEDGON_SET = registerStoneSet(new StoneBlockSetBuilder("medgon",
            MEDGON_HARDNESS, MEDGON_BLAST_RESISTANCE, MapColor.TERRACOTTA_GRAY, Instrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder CHALK_SET = registerStoneSet(new StoneBlockSetBuilder("chalk",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE_GRAY, Instrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS));

    public static StoneBlockSetBuilder GNEISS_SET = registerStoneSet(new StoneBlockSetBuilder("gneiss",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE_GRAY, Instrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder ZIGILABAN_SET = registerStoneSet(new StoneBlockSetBuilder("zigilaban",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.WHITE, Instrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder GALONN_SET = registerStoneSet(new StoneBlockSetBuilder("galonn",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.PALE_YELLOW, Instrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS_PILLAR)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder LIMESTONE_SET = registerStoneSet(new StoneBlockSetBuilder("limestone",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.YELLOW, Instrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS_PILLAR)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder IZHERABAN_SET = registerStoneSet(new StoneBlockSetBuilder("izheraban",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.PALE_YELLOW, Instrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS_PILLAR)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder TRAVERTINE_SET = registerStoneSet(new StoneBlockSetBuilder("travertine",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.PALE_YELLOW, Instrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS_PILLAR));

    public static StoneBlockSetBuilder GABBRO_SET = registerStoneSet(new StoneBlockSetBuilder("gabbro",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.BROWN, Instrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder BURZUM_GABBRO_SET = registerStoneSet(new StoneBlockSetBuilder("burzum_gabbro",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.BROWN, Instrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS_NO_RESTRICTION));

    public static StoneBlockSetBuilder ASHENSTONE_SET = registerStoneSet(new StoneBlockSetBuilder("ashenstone",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.BLACK, Instrument.BASEDRUM, BlockSoundGroup.DEEPSLATE, false, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder PUMICE_SET = registerStoneSet(new StoneBlockSetBuilder("pumice",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.BLACK, Instrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder KHAGALABAN_SET = registerStoneSet(new StoneBlockSetBuilder("khagalaban",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.BLUE, Instrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder BLUE_TUFF_SET = registerStoneSet(new StoneBlockSetBuilder("blue_tuff",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.BLUE, Instrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder SCHIST_SET = registerStoneSet(new StoneBlockSetBuilder("schist",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.LAPIS_BLUE, Instrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS));

    public static StoneBlockSetBuilder IRONSTONE_SET = registerStoneSet(new StoneBlockSetBuilder("ironstone",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.DARK_RED, Instrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder HEMATITE_SET = registerStoneSet(new StoneBlockSetBuilder("hematite",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.RED, Instrument.BASEDRUM, BlockSoundGroup.STONE, true, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder GREEN_TUFF_SET = registerStoneSet(new StoneBlockSetBuilder("green_tuff",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.GREEN, Instrument.BASEDRUM, BlockSoundGroup.STONE, false, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.SMOOTH_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS)
            .addToSet(StoneBlockTypes.OLD_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    public static StoneBlockSetBuilder GILDED_GREEN_TUFF_SET = registerStoneSet(new StoneBlockSetBuilder("gilded_green_tuff",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.GREEN, Instrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.CHISELED_BLOCKS_NO_RESTRICTION));

    public static StoneBlockSetBuilder JADEITE_SET = registerStoneSet(new StoneBlockSetBuilder("jadeite",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.EMERALD_GREEN, Instrument.BASEDRUM, BlockSoundGroup.STONE, false, true)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.PILLAR_BLOCKS));

    public static StoneBlockSetBuilder QUARTZITE_SET = registerStoneSet(new StoneBlockSetBuilder("quartzite",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.RAW_IRON_PINK, Instrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BASE_BLOCKS)
            .addToSet(StoneBlockTypes.COBBLED_BLOCKS)
            .addToSet(StoneBlockTypes.POLISHED_BLOCKS)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS));

    public static StoneBlockSetBuilder TAN_CLAY = registerStoneSet(new StoneBlockSetBuilder("tan_clay",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.RAW_IRON_PINK, Instrument.BASEDRUM, BlockSoundGroup.STONE, false, false)
            .addToSet(StoneBlockTypes.BRICK_BLOCKS)
            .addToSet(StoneBlockTypes.TILE_BLOCKS)
            .addToSet(StoneBlockTypes.BRICKWORK_BLOCKS));

    public static StoneBlockSetBuilder DRYSTONE_SET = registerStoneSet(new StoneBlockSetBuilder("drystone",
            STONE_HARDNESS, STONE_BLAST_RESISTANCE, MapColor.DEEPSLATE_GRAY, Instrument.BASEDRUM,
            BlockSoundGroup.STONE, true, false)
            .addToSet(StoneBlockTypes.PILLAR_BASE_BLOCKS)
            .addToSet(StoneBlockTypes.CARVED_WINDOW));

    private static StoneBlockSetBuilder registerStoneSet(StoneBlockSetBuilder set) {

        List<ItemStack> itemGroup = ItemGroupsME.STONE_BLOCKS_CONTENTS;

        set.existingList.forEach((stoneBlockTypes) -> {
            switch (stoneBlockTypes){
                case BASE_BLOCKS -> set.baseBlocks = BlockSetRegistration.createMainStoneSet(set.setName, set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                case PILLAR_BASE_BLOCKS -> {
                    set.cobblestoneBlocks = BlockSetRegistration.createRegularSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix() , set.hardness + 0.5f, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, true, itemGroup, true);
                    if(set.hasMossy) set.mossyCobblestoneBlocks = BlockSetRegistration.createRegularSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness+ 0.5f, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, true, itemGroup, true);
                }
                case COBBLED_BLOCKS -> {
                    set.cobblestoneBlocks = BlockSetRegistration.createRegularSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix() , set.hardness + 0.5f, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, true);
                    if(set.hasMossy) set.mossyCobblestoneBlocks = BlockSetRegistration.createRegularSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness+ 0.5f, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, true);
                }
                case COBBLESTONE_BLOCKS -> {
                    set.cobblestoneBlocks = BlockSetRegistration.createRegularSet("cobblestone", set.hardness + 0.5f, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, true);
                    if(set.hasMossy) set.mossyCobblestoneBlocks = BlockSetRegistration.createRegularSet("mossy_cobblestone", set.hardness+ 0.5f, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, true);
                }
                case BRICK_BLOCKS, POLISHED_BRICK_BLOCKS -> {
                    set.brickBlocks = BlockSetRegistration.createRegularSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, true);
                    if(set.hasMossy) set.mossyBrickBlocks = BlockSetRegistration.createRegularSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, true);
                    if(set.hasCracked) set.crackedBrickBlocks = BlockSetRegistration.createRegularSet("cracked_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, true);
                }
                case TILE_BLOCKS -> {
                    set.tileBlocks = BlockSetRegistration.createRegularSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, true);
                    if(set.hasMossy) set.mossyTileBlocks = BlockSetRegistration.createRegularSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, true);
                    if(set.hasCracked) set.crackedTileBlocks = BlockSetRegistration.createRegularSet("cracked_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, true);
                }
                case SMOOTH_BLOCKS -> {
                    set.smoothBlocks = BlockSetRegistration.createRegularSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, true);
                    if(set.hasMossy) set.mossySmoothBlocks = BlockSetRegistration.createRegularSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, true);
                    if(set.hasCracked) set.crackedSmoothBlocks = BlockSetRegistration.createRegularSet("cracked_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, true);
                }
                case POLISHED_BLOCKS -> {
                    set.polishedBlocks = BlockSetRegistration.createRegularSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, true);
                    if(set.hasMossy) set.mossyPolishedBlocks = BlockSetRegistration.createRegularSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, true);
                    if(set.hasCracked) set.crackedPolishedBlocks = BlockSetRegistration.createRegularSet("cracked_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, true);
                }
                case POLISHED_BLOCKS_PILLAR -> {
                    set.polishedBlocks = BlockSetRegistration.createRegularSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, true, itemGroup, true);
                    if(set.hasMossy) set.mossyPolishedBlocks = BlockSetRegistration.createRegularSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, true, itemGroup, true);
                    if(set.hasCracked) set.crackedPolishedBlocks = BlockSetRegistration.createRegularSet("cracked_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, true, itemGroup, true);
                }
                case BRICKWORK_BLOCKS -> set.brickworkBlocks = BlockSetRegistration.createRegularSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, true);
                case OLD_BLOCKS -> set.oldBlocks = BlockSetRegistration.createRegularSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, false, itemGroup, true);
                case OLD_BLOCKS_PILLAR -> set.oldBlocks = BlockSetRegistration.createRegularSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, true, itemGroup, true);
                case PILLAR_BLOCKS -> {
                    set.pillarBlocks = BlockSetRegistration.createStonePillarSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    if(set.hasMossy) set.mossyPillarBlocks = BlockSetRegistration.createStonePillarSet("mossy_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    if(set.hasCracked) set.crackedPillarBlocks = BlockSetRegistration.createStonePillarSet("cracked_" + stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                }
                case CHISELED_BLOCKS -> {
                    if (set.existingList.contains(StoneBlockTypes.BASE_BLOCKS)) set.chiseledBlocks = BlockSetRegistration.createStoneChiseledSet(set.setName, set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    if (set.existingList.contains(StoneBlockTypes.BRICK_BLOCKS)) set.chiseledBricksBlocks = BlockSetRegistration.createStoneChiseledSet(set.setName + "_bricks", set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    if (set.existingList.contains(StoneBlockTypes.POLISHED_BRICK_BLOCKS)) set.chiseledBricksBlocks = BlockSetRegistration.createStoneChiseledSet( "polished_" + set.setName + "_bricks", set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    if (set.existingList.contains(StoneBlockTypes.POLISHED_BLOCKS) || set.existingList.contains(StoneBlockTypes.POLISHED_BLOCKS_PILLAR)) set.chiseledPolishedBlocks = BlockSetRegistration.createStoneChiseledSet("polished_" + set.setName, set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    if (set.existingList.contains(StoneBlockTypes.TILE_BLOCKS)) set.chiseledTilesBlocks = BlockSetRegistration.createStoneChiseledSet(set.setName + "_tiles", set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    if (set.existingList.contains(StoneBlockTypes.SMOOTH_BLOCKS)) set.chiseledSmoothBlocks = BlockSetRegistration.createStoneChiseledSet("smooth_" + set.setName, set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                }
                case CHISELED_BLOCKS_NO_RESTRICTION -> {
                    set.chiseledBlocks = BlockSetRegistration.createStoneChiseledSet(set.setName, set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    set.chiseledBricksBlocks = BlockSetRegistration.createStoneChiseledSet(set.setName + "_bricks", set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    set.chiseledPolishedBlocks = BlockSetRegistration.createStoneChiseledSet("polished_" + set.setName, set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    set.chiseledTilesBlocks = BlockSetRegistration.createStoneChiseledSet(set.setName + "_tiles", set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                    set.chiseledSmoothBlocks = BlockSetRegistration.createStoneChiseledSet("smooth_" + set.setName, set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
                }
                case CARVED_WINDOW -> set.carvedWindows = BlockSetRegistration.createCarvedWindowSet(stoneBlockTypes.getPrefix() + set.setName + stoneBlockTypes.getSuffix(), set.hardness, set.blastResistance, set.mapColor, set.instrument, set.soundGroup, itemGroup);
            }
        });

        stoneSetsList.add(set);

        // Populate the legacy views used by the datagen providers.
        if (set.baseBlocks != null) setsMain.add(new SimpleBlockSetMain(set.baseBlocks));
        Block source = set.baseBlocks == null ? null : set.baseBlocks.base();
        addSimpleSet(set.cobblestoneBlocks, source);
        addSimpleSet(set.mossyCobblestoneBlocks, source);
        addSimpleSet(set.crackedBrickBlocks, source);
        addSimpleSet(set.mossyBrickBlocks, source);
        addSimpleSet(set.brickBlocks, source);
        addSimpleSet(set.crackedTileBlocks, source);
        addSimpleSet(set.mossyTileBlocks, source);
        addSimpleSet(set.tileBlocks, source);
        addSimpleSet(set.crackedSmoothBlocks, source);
        addSimpleSet(set.mossySmoothBlocks, source);
        addSimpleSet(set.smoothBlocks, source);
        addSimpleSet(set.crackedPolishedBlocks, source);
        addSimpleSet(set.mossyPolishedBlocks, source);
        addSimpleSet(set.polishedBlocks, source);
        addSimpleSet(set.brickworkBlocks, source);
        addSimpleSet(set.oldBlocks, source);

        return set;
    }

    private static void addSimpleSet(BlockRecordTypes.RegularSet regularSet, Block source) {
        if (regularSet != null) sets.add(new SimpleBlockSet(regularSet, source));
    }

    public static void registerModBlockSets() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Stone Block Sets for " + MiddleEarth.MOD_ID);
    }

    // Aliases kept so the older worldgen/datagen call sites still resolve.
    public static final StoneBlockSetBuilder FROZEN_STONE = STONE_SET;
    public static final StoneBlockSetBuilder COBBLED_DIFTOMIN = STONE_SET;
    public static final StoneBlockSetBuilder COBBLED_EPMOSTO = STONE_SET;
    public static final StoneBlockSetBuilder COBBLED_GONLUIN = STONE_SET;
    public static final StoneBlockSetBuilder DOLOMITE = DOLOMITE_SET;
    public static final StoneBlockSetBuilder JADEITE = JADEITE_SET;
    public static final StoneBlockSetBuilder BLUE_TUFF = BLUE_TUFF_SET;

    // Legacy flat part references used by datagen (they used to be standalone fields).
    public static final SimpleBlockSet COBBLED_LIMESTONE = new SimpleBlockSet(LIMESTONE_SET.cobblestoneBlocks);
    public static final SimpleBlockSet COBBLED_DOLOMITE = new SimpleBlockSet(DOLOMITE_SET.cobblestoneBlocks);
    public static final SimpleBlockSet COBBLED_QUARTZITE = new SimpleBlockSet(QUARTZITE_SET.cobblestoneBlocks);
    public static final SimpleBlockSet FROZEN_COBBLESTONE = new SimpleBlockSet(STONE_SET.cobblestoneBlocks);
    public static final SimpleBlockSet ASHEN_COBBLESTONE = new SimpleBlockSet(ASHENSTONE_SET.cobblestoneBlocks);
    public static final SimpleBlockSet FROZEN_BRICKS = new SimpleBlockSet(STONE_SET.brickBlocks);
    public static final SimpleBlockSet STONE_TILES = new SimpleBlockSet(STONE_SET.tileBlocks);
    public static final SimpleBlockSet FROZEN_TILES = new SimpleBlockSet(STONE_SET.tileBlocks);
    public static final SimpleBlockSet CALCITE_BRICKS = new SimpleBlockSet(CALCITE_SET.brickBlocks);
}
