package net.jukoz.me.datageneration.content.models;

import net.minecraft.world.level.block.Block;
import java.util.ArrayList;
import java.util.List;

public class SimpleStoneChairModel {

    public record VanillaChair(Block base, Block origin) {}

    public static List<Block> chairs = new ArrayList<>() {
        {
        }
    };

    public static List<VanillaChair> vanillaChairs = new ArrayList<>() {
        {
        }
    };
}
