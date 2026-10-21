package net.jukoz.me.datageneration.content.models;

import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class SimplePressurePlateModel {
    public record PressurePlate(Block block, Block pressurePlate) {}
    public static List<PressurePlate> pressurePlates = new ArrayList<>() {
        {
        }
    };
}
