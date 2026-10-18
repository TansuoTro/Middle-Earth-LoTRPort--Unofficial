package net.jukoz.me.compat;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

/**
 * NBT-backed replacement for 1.21's {@code CONTAINER_LOOT} component.
 *
 * <p>Reads the {@code LootTable}/{@code LootTableSeed} pair from the stack's
 * {@code BlockEntityTag}, matching vanilla 1.20.1 container block entities.
 */
public final class MELootTable {

    public static final String LOOT_TABLE_KEY = "LootTable";
    public static final String LOOT_TABLE_SEED_KEY = "LootTableSeed";

    private MELootTable() {
    }

    /** A loot table id and its generation seed. */
    public record Loot(Identifier table, long seed) {
    }

    /** The container's loot table, or null when the stack carries none. */
    @Nullable
    public static Loot get(ItemStack stack) {
        NbtCompound blockEntityTag = stack.getSubNbt("BlockEntityTag");
        if (blockEntityTag == null || !blockEntityTag.contains(LOOT_TABLE_KEY, NbtElement.STRING_TYPE)) {
            return null;
        }
        Identifier table = Identifier.tryParse(blockEntityTag.getString(LOOT_TABLE_KEY));
        if (table == null) {
            return null;
        }
        return new Loot(table, blockEntityTag.getLong(LOOT_TABLE_SEED_KEY));
    }
}
