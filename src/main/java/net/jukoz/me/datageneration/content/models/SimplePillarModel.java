package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.registration.ModBlocks;
import net.jukoz.me.block.registration.StoneBlockSets;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimplePillarModel {
    public record Pillar(Block base) {}

    public record StonePillar(Block base, Block origin) {}

    public static List<Pillar> blocks = new ArrayList<>() {
        {
        }
    };

    public static List<StonePillar> stonePillars = new ArrayList<>() {
        {

        }
    };

    public static List<StonePillar> carvedWindows = new ArrayList<>() {
        {

        }
    };
}
