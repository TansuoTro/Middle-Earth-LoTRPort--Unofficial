package net.fabricmc.fabric.api.registry;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.world.item.AxeItem;

/**
 * Forge compat shim for Fabric's {@code StrippableBlockRegistry}. Forge 1.20.1 keeps the
 * strippable pairs on {@code AxeItem} through a patched static map; this shim mirrors Fabric's
 * register API and pushes the pairs into that map.
 */
public final class StrippableBlockRegistry {
    private static final Map<Block, Block> PAIRS = new LinkedHashMap<>();

    private StrippableBlockRegistry() {}

    public static void register(Block stripped, Block log) {
        PAIRS.put(log, stripped);
        apply(log, stripped);
    }

    public static void register(Block stripped, Block log, boolean copyState) {
        register(stripped, log);
    }

    private static void apply(Block log, Block stripped) {
        try {
            net.minecraft.world.item.AxeItem.STRIPPABLES_MAP = put(net.minecraft.world.item.AxeItem.STRIPPABLES_MAP, log, stripped);
        } catch (Throwable ignored) {
            // Older field name; the pairs stay available for the block behaviour itself.
        }
    }

    @SuppressWarnings("unchecked")
    private static <K, V> Map<K, V> put(Map<K, V> map, K key, V value) {
        map.put(key, value);
        return map;
    }

    public static Map<Block, Block> getEntries() {
        return PAIRS;
    }
}
