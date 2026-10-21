package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.block.registration.GenericBlockSets;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimpleLadderModel {

    public record Ladder(Block block, Block ladder) {}

    public static List<Ladder> ladders = new ArrayList<>() {
        {
            add(new Ladder(GenericBlockSets.TREATED_WOOD_PLANKS.blockSet.base(), ModDecorativeBlocks.TREATED_WOOD_LADDER));
        }
    };

    public static List<Ladder> vanillaLadders = new ArrayList<>() {
        {

        }
    };
}
