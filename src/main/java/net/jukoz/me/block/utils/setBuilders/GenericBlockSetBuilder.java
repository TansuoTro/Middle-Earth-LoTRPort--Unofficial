package net.jukoz.me.block.utils.setBuilders;

import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.jukoz.me.block.utils.BlockRecordTypes;
import net.jukoz.me.block.utils.StoneBlockTypes;

import java.util.ArrayList;
import java.util.List;

public class GenericBlockSetBuilder {

    public BlockRecordTypes.RegularSet blockSet;

    public String setName;
    public float hardness;
    public float blastResistance;
    public MapColor mapColor;
    public Instrument instrument;
    public BlockSoundGroup soundGroup;
    public boolean requiresTool;
    public List<ItemStack> group;

    public GenericBlockSetBuilder(String name, float hardness, float blastResistance,
                                  MapColor mapColor, Instrument instrument, BlockSoundGroup soundGroup, boolean requiresTool,
                                  List<ItemStack> group){
        this.setName = name;
        this.hardness = hardness;
        this.blastResistance = blastResistance;
        this.mapColor = mapColor;
        this.instrument = instrument;
        this.soundGroup = soundGroup;
        this.requiresTool = requiresTool;
        this.group = group;
    }

    // Legacy datagen call sites used a flat base() accessor.
    public net.minecraft.block.Block base() {
        return this.blockSet == null ? null : this.blockSet.base();
    }
}
