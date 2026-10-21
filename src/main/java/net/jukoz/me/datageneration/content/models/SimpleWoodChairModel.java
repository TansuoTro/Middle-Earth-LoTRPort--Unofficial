package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import java.util.ArrayList;
import java.util.List;

public class SimpleWoodChairModel {
    public record VanillaChair(Block base, Block planks, Block origin) {}

    public static List<Block> chairs = new ArrayList<>() {
        {
            add(ModDecorativeBlocks.TREATED_WOOD_CHAIR);
        }
    };

    public static List<VanillaChair> vanillaChairs = new ArrayList<>() {
        {
        }
    };
}
