package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.registration.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import java.util.ArrayList;
import java.util.List;

public class SimpleRocksModel {
    public record Rocks(Block block, Block rocks) {}
    public static List<Rocks> rocks = new ArrayList<>() {
        {
        }
    };

    public static List<Rocks> vanillaRocks = new ArrayList<>() {
        {

        }
    };
}
