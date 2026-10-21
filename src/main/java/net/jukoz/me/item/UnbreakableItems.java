package net.jukoz.me.item;

import net.minecraft.world.item.Item;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

/**
 * Items that are unbreakable for their whole lifetime.
 *
 * <p>1.21 expresses this with the {@code UNBREAKABLE} data component declared on the item.
 * 1.20.1 only has the per-stack {@code Unbreakable} NBT flag, which cannot be set from
 * {@code Item.Settings}, so the item itself is recorded here and
 * {@code ItemStackUnbreakableMixin} reports it.
 */
public final class UnbreakableItems {

    private static final Set<Item> ITEMS =
            Collections.newSetFromMap(new IdentityHashMap<>());

    private UnbreakableItems() {
    }

    public static void add(Item item) {
        ITEMS.add(item);
    }

    public static boolean contains(Item item) {
        return ITEMS.contains(item);
    }
}
