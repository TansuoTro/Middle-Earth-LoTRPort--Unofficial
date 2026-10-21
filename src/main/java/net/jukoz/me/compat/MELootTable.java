package net.jukoz.me.compat;

import net.minecraft.world.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
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
    public record Loot(ResourceLocation table, long seed) {
    }

    /** The container's loot table, or null when the stack carries none. */
    @Nullable
    public static Loot get(ItemStack stack) {
        CompoundTag blockEntityTag = stack.getSubNbt("BlockEntityTag");
        if (blockEntityTag == null || !blockEntityTag.contains(LOOT_TABLE_KEY, Tag.STRING_TYPE)) {
            return null;
        }
        ResourceLocation table = ResourceLocation.tryParse(blockEntityTag.getString(LOOT_TABLE_KEY));
        if (table == null) {
            return null;
        }
        return new Loot(table, blockEntityTag.getLong(LOOT_TABLE_SEED_KEY));
    }
}
