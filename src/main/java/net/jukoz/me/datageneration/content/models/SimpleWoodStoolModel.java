package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import java.util.ArrayList;
import java.util.List;

public class SimpleWoodStoolModel {

    public record VanillaStool(Block base, Block planks, Block origin) {}

    public static List<Block> stools = new ArrayList<>() {
        {
            add(ModDecorativeBlocks.TREATED_WOOD_STOOL);
        }
    };

    public static List<VanillaStool> vanillaStools = new ArrayList<>() {
        {
        }
    };
}
