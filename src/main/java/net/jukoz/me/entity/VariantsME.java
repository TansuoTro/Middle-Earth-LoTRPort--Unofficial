package net.jukoz.me.entity;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.WorldAccess;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.compat.nbt.ReadView;
import net.jukoz.me.compat.nbt.WriteView;

import java.util.Optional;

/**
 * 1.20.1 stand-in for the vanilla 1.21.5 {@code net.minecraft.entity.Variants} helper.
 *
 * <p>The donor relied on vanilla's variant utility to (de)serialise a variant
 * {@link RegistryEntry} and to pick a random variant while spawning. 1.20.1 has no such
 * helper and no registry access inside {@code ReadView}, so the registry manager is passed in
 * explicitly at each call site.
 */
public final class VariantsME {

    private static final String VARIANT_KEY = "variant";

    private VariantsME() {
    }

    @SuppressWarnings("unchecked")
    public static <T> Registry<T> registry(DynamicRegistryManager manager, RegistryKey<? extends Registry<T>> registryKey) {
        return (Registry<T>) manager.get(registryKey);
    }

    public static <T> RegistryEntry<T> getOrDefaultOrThrow(DynamicRegistryManager manager, RegistryKey<T> key) {
        Registry<T> registry = registry(manager, RegistryKey.ofRegistry(key.getRegistry()));
        if (registry == null) {
            return null;
        }
        RegistryEntry<T> entry = registry.getEntry(key).orElse(null);
        if (entry != null) {
            return entry;
        }
        // A tracked data must never be initialised with null: DataTracker.Entry.isUnchanged()
        // dereferences the initial value the first time the entity is tracked, which takes the
        // whole server tick down. Fall back to whatever the registry does hold.
        Optional<RegistryEntry.Reference<T>> any = registry.getRandom(net.minecraft.util.math.random.Random.create());
        if (any.isEmpty()) {
            MiddleEarth.LOGGER.logError("VariantsME: registry " + key.getRegistry() + " holds no entry at all, "
                    + key.getValue() + " cannot be resolved");
            return null;
        }
        MiddleEarth.LOGGER.logError("VariantsME: " + key.getValue() + " is missing from " + key.getRegistry()
                + ", falling back to " + any.get().getKey().map(k -> k.getValue().toString()).orElse("?"));
        return any.get();
    }

    public static <T> Optional<RegistryEntry<T>> getEntry(DynamicRegistryManager manager, RegistryKey<T> key) {
        Registry<T> registry = registry(manager, RegistryKey.ofRegistry(key.getRegistry()));
        if (registry == null) {
            return Optional.empty();
        }
        return registry.getEntry(key).map(entry -> (RegistryEntry<T>) entry);
    }

    public static <T> void writeVariantToNbt(WriteView view, RegistryEntry<T> entry) {
        if (entry == null) {
            return;
        }
        entry.getKey().ifPresent(key -> view.put(VARIANT_KEY, Identifier.CODEC, key.getValue()));
    }

    public static <T> Optional<RegistryEntry<T>> readVariantFromNbt(ReadView view, DynamicRegistryManager manager,
                                                                    RegistryKey<? extends Registry<T>> registryKey) {
        Optional<Identifier> id = view.read(VARIANT_KEY, Identifier.CODEC);
        if (id.isEmpty()) {
            return Optional.empty();
        }
        Registry<T> registry = registry(manager, registryKey);
        if (registry == null) {
            return Optional.empty();
        }
        return registry.getEntry(RegistryKey.of(registryKey, id.get())).map(entry -> (RegistryEntry<T>) entry);
    }

    public static <T> Optional<RegistryEntry<T>> select(WorldAccess world, Registry<T> registry) {
        if (registry == null) {
            return Optional.empty();
        }
        return registry.getRandom(world.getRandom()).map(entry -> (RegistryEntry<T>) entry);
    }
}
