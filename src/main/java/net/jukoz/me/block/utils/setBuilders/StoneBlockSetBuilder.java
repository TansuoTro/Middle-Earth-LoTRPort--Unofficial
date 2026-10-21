package net.jukoz.me.block.utils.setBuilders;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.SoundType;
import net.jukoz.me.block.utils.BlockRecordTypes;
import net.jukoz.me.block.utils.StoneBlockTypes;
import java.util.ArrayList;
import java.util.List;

public class StoneBlockSetBuilder {

    public final List<StoneBlockTypes> existingList;

    public BlockRecordTypes.BaseStoneSet baseBlocks;

    public BlockRecordTypes.RegularSet cobblestoneBlocks;
    public BlockRecordTypes.RegularSet mossyCobblestoneBlocks;

    public BlockRecordTypes.RegularSet brickBlocks;
    public BlockRecordTypes.RegularSet mossyBrickBlocks;
    public BlockRecordTypes.RegularSet crackedBrickBlocks;

    public BlockRecordTypes.RegularSet tileBlocks;
    public BlockRecordTypes.RegularSet mossyTileBlocks;
    public BlockRecordTypes.RegularSet crackedTileBlocks;

    public BlockRecordTypes.RegularSet smoothBlocks;
    public BlockRecordTypes.RegularSet mossySmoothBlocks;
    public BlockRecordTypes.RegularSet crackedSmoothBlocks;

    public BlockRecordTypes.RegularSet polishedBlocks;
    public BlockRecordTypes.RegularSet mossyPolishedBlocks;
    public BlockRecordTypes.RegularSet crackedPolishedBlocks;

    public BlockRecordTypes.RegularSet brickworkBlocks;

    public BlockRecordTypes.PillarSet pillarBlocks;
    public BlockRecordTypes.PillarSet mossyPillarBlocks;
    public BlockRecordTypes.PillarSet crackedPillarBlocks;

    public BlockRecordTypes.PillarSet chiseledBlocks;
    public BlockRecordTypes.PillarSet chiseledBricksBlocks;
    public BlockRecordTypes.PillarSet chiseledPolishedBlocks;
    public BlockRecordTypes.PillarSet chiseledTilesBlocks;
    public BlockRecordTypes.PillarSet chiseledSmoothBlocks;

    public BlockRecordTypes.RegularSet oldBlocks;

    public BlockRecordTypes.CarvedWindow carvedWindows;

    public String setName;
    public float hardness;
    public float blastResistance;
    public MapColor mapColor;
    public NoteBlockInstrument instrument;
    public SoundType soundGroup;
    public boolean hasMossy;
    public boolean hasCracked;
    public boolean isVanilla;
    public boolean hasVanillaCobble;
    public boolean hasVanillaPolished;

    public StoneBlockSetBuilder(String name, float hardness, float blastResistance,
                                MapColor mapColor, NoteBlockInstrument instrument, SoundType soundGroup, boolean hasMossy, boolean hasCracked){
        this(name, hardness,blastResistance, mapColor, instrument, soundGroup, hasMossy, hasCracked, false, false, false);
    }

    public StoneBlockSetBuilder(String name, float hardness, float blastResistance,
                                MapColor mapColor, NoteBlockInstrument instrument, SoundType soundGroup, boolean hasMossy,
                                boolean hasCracked, boolean isVanilla, boolean hasVanillaCobble, boolean hasVanillaPolished){
        this.setName = name;
        this.hardness = hardness;
        this.blastResistance = blastResistance;
        this.mapColor = mapColor;
        this.instrument = instrument;
        this.soundGroup = soundGroup;
        this.hasMossy = hasMossy;
        this.hasCracked = hasCracked;
        this.isVanilla = isVanilla;
        this.hasVanillaCobble = hasVanillaCobble;
        this.hasVanillaPolished = hasVanillaPolished;

        this.existingList = new ArrayList<>();
    }

    public StoneBlockSetBuilder addToSet(StoneBlockTypes type) {
        this.existingList.add(type);
        return this;
    }

    // --- 1.20.1 port compat: legacy flat accessors ---
    public Block base() { return this.baseBlocks == null ? null : this.baseBlocks.base(); }
    public Block slab() { return this.baseBlocks == null ? null : this.baseBlocks.slab(); }
    public Block verticalSlab() { return this.baseBlocks == null ? null : this.baseBlocks.verticalSlab(); }
    public Block stairs() { return this.baseBlocks == null ? null : this.baseBlocks.stairs(); }
    public Block wall() { return this.baseBlocks == null ? null : this.baseBlocks.wall(); }
    public Block cobblestone() { return this.cobblestoneBlocks == null ? null : this.cobblestoneBlocks.base(); }
    public Block bricks() { return this.brickBlocks == null ? null : this.brickBlocks.base(); }
    public Block tiles() { return this.tileBlocks == null ? null : this.tileBlocks.base(); }
    public Block smooth() { return this.smoothBlocks == null ? null : this.smoothBlocks.base(); }
    public Block polished() { return this.polishedBlocks == null ? null : this.polishedBlocks.base(); }
}
