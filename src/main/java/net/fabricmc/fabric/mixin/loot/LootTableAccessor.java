package net.fabricmc.fabric.mixin.loot;

import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.List;

/**
 * Forge compat shim for Fabric's {@code LootTableAccessor} mixin interface. Vanilla 1.20.1's
 * {@link LootTable} already exposes the pool list through {@code getPools()}, so the shim just
 * widens it back to the mutable list the port expects.
 */
public interface LootTableAccessor {
    static List<LootPool> getPools(LootTable table) {
        return table.pools;
    }
}
