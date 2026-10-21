package net.jukoz.me.datageneration.content.models;

import net.jukoz.me.block.registration.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.ArrayList;
import java.util.List;

public class SimpleDoorModel {
    public record Door(Block block, Block door) {}

    public static List<Door> doors = new ArrayList<>() {
        {
            add(new Door(ModBlocks.AGED_WOOD_DOOR, ModBlocks.AGED_WOOD_DOOR));
            add(new Door(ModBlocks.BRONZE_BLOCK, ModBlocks.BRONZE_DOOR));
            add(new Door(ModBlocks.CRUDE_BLOCK, ModBlocks.CRUDE_DOOR));
            add(new Door(ModBlocks.STEEL_BLOCK, ModBlocks.TREATED_STEEL_DOOR));
        }
    };
}
