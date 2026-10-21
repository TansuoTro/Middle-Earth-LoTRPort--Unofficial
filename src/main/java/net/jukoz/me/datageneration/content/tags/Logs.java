package net.jukoz.me.datageneration.content.tags;

import net.jukoz.me.block.registration.ModNatureBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;
import java.util.ArrayList;
import java.util.List;

public class Logs {
    public static List<Block> logs = new ArrayList<>() {
        {
            add(ModNatureBlocks.PINE_BRANCHES);
        }
    };

    public static List<Item> getItemLogs() {
        ArrayList<Item> newList = new ArrayList<>();
        for (Block block : logs) {
            if (block != null) {
                newList.add(block.asItem());
            }
        }
        return newList;
    }
}
