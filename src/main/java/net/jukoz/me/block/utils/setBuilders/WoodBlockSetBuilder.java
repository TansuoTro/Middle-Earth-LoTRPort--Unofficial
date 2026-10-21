package net.jukoz.me.block.utils.setBuilders;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.NoteBlockInstrument;
import net.minecraft.sound.BlockSoundGroup;
import net.jukoz.me.block.utils.BlockRecordTypes;
import net.jukoz.me.block.utils.WoodBlockTypes;

import java.util.ArrayList;
import java.util.List;

public class WoodBlockSetBuilder {
    public boolean vanilla = false;
    public final List<WoodBlockTypes> existingList;

    public BlockRecordTypes.WoodSet logBlocks;
    public BlockRecordTypes.WoodSet strippedLogBlocks;

    public BlockRecordTypes.MushroomStemSet mushroomStemBlocks;

    public BlockRecordTypes.PlanksSet planksBlocks;

    public BlockRecordTypes.WoodRedstoneBlocks redstoneBlocks;

    public BlockRecordTypes.WoodFurnitureBlocks furnitureBlocks;

    public BlockRecordTypes.RegularSet roofingBlocks;
    public BlockRecordTypes.RegularSet shinglesBlocks;

    public Block leaves;
    public Block sapling;

    public String setName;
    public float hardness;
    public float blastResistance;
    public MapColor mapColor;
    public MapColor leavesMapColor;
    public NoteBlockInstrument instrument;
    public BlockSoundGroup soundGroup;

    public WoodBlockSetBuilder(String name, float hardness, float blastResistance,
                               MapColor mapColor, NoteBlockInstrument instrument, BlockSoundGroup soundGroup, Block sapling){
        this.setName = name;
        this.hardness = hardness;
        this.blastResistance = blastResistance;
        this.mapColor = mapColor;
        this.instrument = instrument;
        this.soundGroup = soundGroup;
        this.sapling = sapling;

        this.existingList = new ArrayList<>();
    }

    public WoodBlockSetBuilder addToSet(WoodBlockTypes type) {
        this.existingList.add(type);
        return this;
    }

    public WoodBlockSetBuilder vanilla(boolean vanilla) {
        this.vanilla = vanilla;
        return this;
    }

    public WoodBlockSetBuilder leavesColor(MapColor leavesMapColor) {
        this.leavesMapColor = leavesMapColor;
        return this;
    }

    // --- 1.20.1 port compat: legacy flat accessors (pre-refactor call sites use set.log()/set.leaves()/...) ---
    public Block log() { return this.logBlocks == null ? null : this.logBlocks.log(); }
    public Block wood() { return this.logBlocks == null ? null : this.logBlocks.wood(); }
    public Block strippedLog() { return this.strippedLogBlocks == null ? null : this.strippedLogBlocks.log(); }
    public Block planks() { return this.planksBlocks == null ? null : this.planksBlocks.base(); }
    public Block slab() { return this.planksBlocks == null ? null : this.planksBlocks.slab(); }
    public Block verticalSlab() { return this.planksBlocks == null ? null : this.planksBlocks.verticalSlab(); }
    public Block stairs() { return this.planksBlocks == null ? null : this.planksBlocks.stairs(); }
    public Block fence() { return this.planksBlocks == null ? null : this.planksBlocks.fence(); }
    public Block gate() { return this.planksBlocks == null ? null : this.planksBlocks.gate(); }
    public Block wall() { return this.logBlocks == null ? null : this.logBlocks.wall(); }
    public Block leaves() { return this.leaves; }
    public Block sapling() { return this.sapling; }
    public Block trapdoor() { return this.redstoneBlocks == null ? null : this.redstoneBlocks.trapdoor(); }
    public Block door() { return this.redstoneBlocks == null ? null : this.redstoneBlocks.door(); }
    public Block button() { return this.redstoneBlocks == null ? null : this.redstoneBlocks.button(); }
    public Block pressurePlate() { return this.redstoneBlocks == null ? null : this.redstoneBlocks.pressurePlate(); }
    public Block stool() { return this.furnitureBlocks == null ? null : this.furnitureBlocks.stool(); }
    public Block table() { return this.furnitureBlocks == null ? null : this.furnitureBlocks.table(); }
    public Block chair() { return this.furnitureBlocks == null ? null : this.furnitureBlocks.chair(); }
    public Block bench() { return this.furnitureBlocks == null ? null : this.furnitureBlocks.bench(); }
    public Block ladder() { return this.furnitureBlocks == null ? null : this.furnitureBlocks.ladder(); }
    public Block roofing() { return this.roofingBlocks == null ? null : this.roofingBlocks.base(); }
    public Block shingles() { return this.shinglesBlocks == null ? null : this.shinglesBlocks.base(); }
}
