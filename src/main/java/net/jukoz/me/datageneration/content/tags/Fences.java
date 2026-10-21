package net.jukoz.me.datageneration.content.tags;

import net.minecraft.world.level.block.Block;
import net.jukoz.me.block.registration.ModBlocks;
import java.util.ArrayList;
import java.util.List;

public class Fences {
    public static List<Block> fences = new ArrayList<>() {
        {
            add(ModBlocks.TREATED_WOOD_ROPE_FENCE);
        }
    };
}
