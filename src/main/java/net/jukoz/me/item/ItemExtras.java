package net.jukoz.me.item;

import net.minecraft.item.Item;
import net.minecraft.text.Text;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

/**
 * Per-item defaults that 1.20.1 cannot express in {@code Item.Settings}.
 *
 * <p>1.21 declares these as data components on the settings ({@code LORE},
 * {@code PROVIDES_BANNER_PATTERNS}); 1.20.1 has neither, so the values are captured from
 * {@link MESettings} by the item mixin and consulted later - lore from the tooltip builder,
 * the banner-pattern tag when the loom integration is ported.
 */
public final class ItemExtras {

    private static final Map<Item, List<Text>> LORE =
            Collections.synchronizedMap(new IdentityHashMap<>());
    private static final Map<Item, String> BANNER_PATTERN_TAGS =
            Collections.synchronizedMap(new IdentityHashMap<>());

    private ItemExtras() {
    }

    public static void lore(Item item, List<Text> lines) {
        if (lines != null && !lines.isEmpty()) {
            LORE.put(item, lines);
        }
    }

    /** Lines declared on the item itself; shown before any component tooltip. */
    public static List<Text> lore(Item item) {
        return LORE.getOrDefault(item, List.of());
    }

    public static void bannerPatternTag(Item item, String tagId) {
        BANNER_PATTERN_TAGS.put(item, tagId);
    }

    public static String bannerPatternTag(Item item) {
        return BANNER_PATTERN_TAGS.get(item);
    }
}
