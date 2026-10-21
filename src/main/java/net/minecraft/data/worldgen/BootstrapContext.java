package net.minecraft.data.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderOwner;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * Forge compat shim for the 1.21.4+ {@code BootstrapContext}. 1.20.1 has no such class, so this
 * minimal version records the bootstrap registrations the port's datagen providers make and
 * doubles as the {@link HolderGetter} those registries expect.
 *
 * @param <T> the value type being bootstrapped
 */
public final class BootstrapContext<T> implements HolderGetter<T>, HolderOwner<T> {
    private static final Map<ResourceKey<?>, BootstrapContext<?>> CONTEXTS = new HashMap<>();

    private final ResourceKey<? extends Registry<T>> key;
    private final Map<ResourceKey<T>, T> values = new HashMap<>();

    private BootstrapContext(ResourceKey<? extends Registry<T>> key) {
        this.key = key;
    }

    /** Returns the shared context for a dynamic registry. */
    @SuppressWarnings("unchecked")
    public static <T> BootstrapContext<T> of(ResourceKey<? extends Registry<T>> key) {
        BootstrapContext<?> existing = CONTEXTS.get(key);
        if (existing != null) {
            return (BootstrapContext<T>) existing;
        }
        BootstrapContext<T> created = new BootstrapContext<>(key);
        CONTEXTS.put(key, created);
        return created;
    }

    public ResourceKey<? extends Registry<T>> key() {
        return key;
    }

    /** 1.21's {@code register(key, value)}. */
    public void register(ResourceKey<T> key, T value) {
        values.put(key, value);
    }

    /** 1.21's {@code register(key, supplier)}. */
    public void register(ResourceKey<T> key, Supplier<T> value) {
        values.put(key, value.get());
    }

    /** 1.21's {@code lookup(registryKey)}. */
    public <R> HolderGetter<R> getRegistryLookup(ResourceKey<? extends Registry<R>> registryKey) {
        return BootstrapContext.of(registryKey);
    }

    public Map<ResourceKey<T>, T> values() {
        return values;
    }

    @Override
    public Optional<Holder.Reference<T>> get(ResourceKey<T> key) {
        T value = values.get(key);
        return value == null ? Optional.empty()
                : Optional.of(Holder.Reference.createStandAlone(this, key));
    }

    @Override
    public Optional<HolderSet.Named<T>> get(TagKey<T> key) {
        return Optional.empty();
    }

    @Override
    public boolean canSerializeIn(HolderOwner<T> owner) {
        return owner == this;
    }
}
