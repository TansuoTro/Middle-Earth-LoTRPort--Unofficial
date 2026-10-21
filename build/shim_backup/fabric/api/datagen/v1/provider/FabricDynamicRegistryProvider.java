package net.fabricmc.fabric.api.datagen.v1.provider;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;

import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.resources.ResourceKey;

/**
 * Forge compat shim for Fabric's {@code FabricDynamicRegistryProvider}. Fabric's variant emits
 * the dynamic registries with a {@code configure(registries, entries)} entry point; Forge 1.20.1
 * does the same through {@code RegistryDataGenerator} / the mod's datagen pack.
 */
public abstract class FabricDynamicRegistryProvider {
    private final PackOutput output;
    private final CompletableFuture<HolderLookup.Provider> registries;

    protected FabricDynamicRegistryProvider(net.fabricmc.fabric.api.datagen.v1.FabricDataOutput output,
                                            CompletableFuture<HolderLookup.Provider> registries) {
        this.output = output.getOutput();
        this.registries = registries;
    }

    protected FabricDynamicRegistryProvider(PackOutput output,
                                            CompletableFuture<HolderLookup.Provider> registries) {
        this.output = output;
        this.registries = registries;
    }

    /** Fabric's entry point; ported providers override this. */
    public abstract void configure(HolderLookup.Provider registries, Entries entries);

    public String getName() {
        return "DynamicRegistries";
    }

    public final PackOutput getPackOutput() {
        return output;
    }

    public final CompletableFuture<HolderLookup.Provider> getRegistries() {
        return registries;
    }

    /** Fabric's per-registry entry collector. */
    public interface Entries {
        <T> void add(net.minecraft.resources.ResourceKey<T> key, T value);

        <T> void add(net.minecraft.resources.ResourceKey<T> key, java.util.function.Supplier<T> value);
    }
}
