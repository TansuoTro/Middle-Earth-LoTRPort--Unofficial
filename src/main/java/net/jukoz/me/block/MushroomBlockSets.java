package net.jukoz.me.block;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.WoodBlockSets;
import net.jukoz.me.block.utils.setBuilders.WoodBlockSetBuilder;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.block.Block;

public class MushroomBlockSets {
    public static final float MUSHROOM_STRENGTH = 2f;
    public static final float PLATE_BUTTON_STRENGTH = 0.1f;

    public static MushroomBlockSets.MushroomBlockSet DARK_MUSHROOM =
            fromWoodSet(WoodBlockSets.DARK_MUSHROOM_SET);
    public static MushroomBlockSets.MushroomBlockSet GRAY_MUSHROOM =
            fromWoodSet(WoodBlockSets.GRAY_MUSHROOM_SET);
    public static MushroomBlockSets.MushroomBlockSet MUSHROOM =
            fromWoodSet(WoodBlockSets.MUSHROOM_SET);

    public record MushroomBlockSet(Block stem, Block stemWall, Block stemFence,
                                   Block planks, Block planksSlab, Block planksVerticalSlab, Block planksStairs,
                                   Block planksFence, Block planksGate,
                                   Block pressurePlate, Block button, Block door, Block trapdoor,
                                   Block stool, Block table, Block chair) {
    }

    public static MushroomBlockSets.MushroomBlockSet[] sets = new MushroomBlockSets.MushroomBlockSet[] {
            DARK_MUSHROOM,
            GRAY_MUSHROOM,
            MUSHROOM,
    };

    /**
     * 1.20.1 port note: the donor registers these blocks through {@link WoodBlockSets}
     * (MUSHROOM_STEM_BLOCKS + PLANK_BLOCKS + REDSTONE_BLOCKS + FURNITURE_BLOCKS).
     * This legacy class used to register a second, identical set, which collided at boot
     * (e.g. {@code me:dark_mushroom_stem} at two raw ids). Derive the record from the
     * canonical {@link WoodBlockSets} entry instead of registering anything.
     */
    private static MushroomBlockSets.MushroomBlockSet fromWoodSet(WoodBlockSetBuilder set) {
        return new MushroomBlockSet(
                set.mushroomStemBlocks.stem(),
                set.mushroomStemBlocks.wall(),
                set.mushroomStemBlocks.fence(),
                set.planks(),
                set.slab(),
                set.verticalSlab(),
                set.stairs(),
                set.fence(),
                set.gate(),
                set.pressurePlate(),
                set.button(),
                set.door(),
                set.trapdoor(),
                set.stool(),
                set.table(),
                set.chair());
    }

    public static void registerModBlockSets() {
        LoggerUtil.getInstance().logDebugMsg("MushroomBlockSets aliased to WoodBlockSets for " + MiddleEarth.MOD_ID);
    }
}
