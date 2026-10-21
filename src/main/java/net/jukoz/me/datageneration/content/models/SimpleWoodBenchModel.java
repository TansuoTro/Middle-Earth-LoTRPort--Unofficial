package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleWoodBenchModel {

    public record VanillaBench(Block base, Block planks) {}

    public static List<Block> benchs = new ArrayList<>() {
        {
            add(ModDecorativeBlocks.TREATED_WOOD_BENCH);
        }
    };

    public static List<VanillaBench> vanillaBenchs = new ArrayList<>() {
        {
        }
    };
}
