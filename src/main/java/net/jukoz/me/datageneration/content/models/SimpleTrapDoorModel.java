package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.registration.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import java.util.ArrayList;
import java.util.List;

public class SimpleTrapDoorModel {
    public record Trapdoor(Block block, Block trapdoor, boolean orientable) {
        public Trapdoor(Block block, Block trapdoor) {
            this(block, trapdoor, false);
        }
    }

    public static List<Trapdoor> trapdoors = new ArrayList<>() {
        {
            add(new Trapdoor(ModBlocks.BLACK_WATTLE_AND_WHITE_DAUB, ModBlocks.BLACK_WATTLE_TRAPDOOR, true));
            add(new Trapdoor(ModBlocks.DARK_WATTLE_AND_DARK_DAUB, ModBlocks.DARK_WATTLE_TRAPDOOR, true));
            add(new Trapdoor(ModBlocks.GREEN_WATTLE_AND_WHITE_DAUB, ModBlocks.GREEN_WATTLE_TRAPDOOR, true));
            add(new Trapdoor(ModBlocks.RED_WATTLE_AND_WHITE_DAUB, ModBlocks.RED_WATTLE_TRAPDOOR, true));
            add(new Trapdoor(ModBlocks.WATTLE_AND_WHITE_DAUB, ModBlocks.WATTLE_TRAPDOOR, true));
            add(new Trapdoor(ModBlocks.AGED_WOOD_TRAPDOOR, ModBlocks.AGED_WOOD_TRAPDOOR, true));
            add(new Trapdoor(ModBlocks.BRONZE_BLOCK, ModBlocks.BRONZE_TRAPDOOR, true));
            add(new Trapdoor(ModBlocks.CRUDE_BLOCK, ModBlocks.CRUDE_TRAPDOOR, true));
            add(new Trapdoor(ModBlocks.STEEL_BLOCK, ModBlocks.TREATED_STEEL_TRAPDOOR, true));
        }
    };
}
