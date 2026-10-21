package net.jukoz.me.datageneration.content.tags;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.item.Item;

import java.util.ArrayList;
import java.util.List;

public class Planks {
    public static List<Block> planks = new ArrayList<>() {
        {

        }
    };

    public static List<Block> moddedPlanks = new ArrayList<>() {
        {

        }
    };

    public static List<Item> getItemPlanks() {
        ArrayList<Item> newList = new ArrayList<>();
        for(Block block : planks) {
            newList.add(block.asItem());
        }
        return newList;
    }

    public static List<Item> getItemPlanksWithoutVanilla() {
        ArrayList<Item> newList = new ArrayList<>();
        for(Block block : moddedPlanks) {
            newList.add(block.asItem());
        }
        return newList;
    }

    public static List<Block> planksSlabs = new ArrayList<>() {
        {

        }
    };

    public static List<Item> getItemPlanksSlabs() {
        ArrayList<Item> newList = new ArrayList<>();
        for(Block block : planksSlabs) {
            newList.add(block.asItem());
        }
        return newList;
    }
}
