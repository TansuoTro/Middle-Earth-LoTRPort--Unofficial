package net.fabricmc.fabric.api.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WeatheringCopper;

/**
 * Forge compat shim for Fabric's {@code OxidizableBlocksRegistry}. Vanilla keeps the oxidising
 * pairs in the static {@code WeatheringCopper} maps; this shim just delegates.
 */
public final class OxidizableBlocksRegistry {
    private OxidizableBlocksRegistry() {}

    public static void registerOxidizableBlockPair(Block unaffected, Block exposed) {
        WeatheringCopper.NEXT_BY_BLOCK.get().put(unaffected, exposed);
    }

    public static void registerWaxableBlockPair(Block waxed, Block unwaxed) {
        WeatheringCopper.PREVIOUS_BY_BLOCK.get().put(unwaxed, waxed);
    }
}
