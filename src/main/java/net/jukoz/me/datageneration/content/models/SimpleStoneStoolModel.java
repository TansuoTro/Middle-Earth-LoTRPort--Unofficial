package net.jukoz.me.datageneration.content.models;

import net.minecraft.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleStoneStoolModel {

    public record VanillaStool(Block base, Block origin) {}

    public static List<Block> stools = new ArrayList<>() {
        {
        }
    };

    public static List<VanillaStool> vanillaStools = new ArrayList<>() {
        {
        }
    };
}
