package net.fabricmc.fabric.api.event.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;

import java.util.HashMap;
import java.util.Map;

/**
 * Forge compat shim for Fabric's {@code DynamicRegistries}. It records the synced dynamic
 * registries (and their codecs) so the port can keep its registration calls; Forge's own
 * datapack registries already sync these values to the client.
 */
public final class DynamicRegistries {
    private record Entry(ResourceKey<?> key, com.mojang.serializations.Codec<?> codec) {}

    private static final Map<ResourceKey<?>, Entry> SYNCED = new HashMap<>();

    private DynamicRegistries() {}

    public static <T> void registerSynced(ResourceKey<? extends Registry<T>> key,
                                          com.mojang.serializations.Codec<T> codec) {
        SYNCED.put(key, new Entry(key, codec));
    }

    public static <T> void register(ResourceKey<? extends Registry<T>> key,
                                    com.mojang.serializations.Codec<T> codec) {
        SYNCED.put(key, new Entry(key, codec));
    }

    public static boolean isSynced(ResourceKey<?> key) {
        return SYNCED.containsKey(key);
    }

    public static int count() {
        return SYNCED.size();
    }
}
