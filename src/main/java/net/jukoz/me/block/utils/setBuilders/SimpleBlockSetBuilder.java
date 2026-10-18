package net.jukoz.me.block.utils.setBuilders;

import net.minecraft.block.MapColor;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.BlockSoundGroup;
import net.jukoz.me.block.utils.BlockRecordTypes;

import java.util.List;

public class SimpleBlockSetBuilder {

    public BlockRecordTypes.SimpleBlocks blockSet;

    public String setName;
    public float hardness;
    public float blastResistance;
    public MapColor mapColor;
    public Instrument instrument;
    public BlockSoundGroup soundGroup;
    public boolean requiresTool;
    public  List<ItemStack> group;

    public SimpleBlockSetBuilder(String name, float hardness, float blastResistance,
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
}
