package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.registration.ModNatureBlocks;
import net.minecraft.world.level.block.Block;
import java.util.ArrayList;
import java.util.List;

public class SimpleTopWaterModel {
    public static List<Block> topWaterBlocks = new ArrayList<>() {
        {
            add(ModNatureBlocks.FLOATING_ICE);
        }
    };
}
