package net.jukoz.me.compat.mcforge;

import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Forge compat layer for Fabric's {@code RecipeManager.CachedCheck} /
 * {@code createCachedMatchGetter}. Vanilla 1.20.1 has no cached recipe lookup, so this class
 * adds a small content-hash cache in front of {@link RecipeManager#getRecipeFor}.
 *
 * @param <C> the inventory type the recipe consumes
 * @param <T> the recipe type
 */
public final class MatchGetter<C extends Container, T extends Recipe<C>> {
    private final RecipeType<T> type;
    private final Map<Long, Optional<T>> cache = new HashMap<>();

    private MatchGetter(RecipeType<T> type) {
        this.type = type;
    }

    /** Fabric's {@code RecipeManager.createCachedMatchGetter(type)}. */
    public static <C extends Container, T extends Recipe<C>> MatchGetter<C, T> createCachedMatchGetter(
            RecipeType<T> type) {
        return new MatchGetter<>(type);
    }

    /** Fabric's {@code getFirstMatch(input, world)}. */
    public Optional<T> getFirstMatch(C input, Level level) {
        long key = hash(input);
        Optional<T> cached = cache.get(key);
        if (cached != null) {
            return cached;
        }
        Optional<T> result = level.getRecipeManager().getRecipeFor(type, input, level);
        if (cache.size() > 4096) {
            cache.clear();
        }
        cache.put(key, result);
        return result;
    }

    private static long hash(Container input) {
        long hash = 1469598103934665603L;
        for (int slot = 0; slot < input.getContainerSize(); slot++) {
            ItemStack stack = input.getItem(slot);
            hash ^= stack.getItem().hashCode() * 31L + stack.getCount();
            hash *= 1099511628211L;
        }
        return hash;
    }
}
