package net.jukoz.me.api.registries;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryEntryLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public class DynamicRegistries {

    public static <T> T register(BootstrapContext<T> context, RegistryEntryLookup<T> registryEntryLookup, ResourceKey<T> registryKey, T element) {
        Optional<Holder.Reference<T>> optionalRegistryEntry = registryEntryLookup.getOptional(registryKey);
        optionalRegistryEntry.ifPresent(reference -> context.register(registryKey, element));
        return element;
    }
    public static <T> ResourceKey<T> of(ResourceKey<Registry<T>> key, ResourceLocation id) {
        return ResourceKey.of(key, id);
    }
}
