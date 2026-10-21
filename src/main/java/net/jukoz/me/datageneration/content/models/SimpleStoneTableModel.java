package net.jukoz.me.datageneration.content.models;

import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleStoneTableModel {
    public record VanillaTable(Block base, Block origin) {}

    public static List<Block> tables = new ArrayList<>() {
        {
        }
    };

    public static List<VanillaTable> vanillaTables = new ArrayList<>() {
        {
        }
    };
}
