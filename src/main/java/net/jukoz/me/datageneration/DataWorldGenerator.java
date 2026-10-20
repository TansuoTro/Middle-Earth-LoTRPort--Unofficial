package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.jukoz.me.MiddleEarth;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class DataWorldGenerator extends FabricDynamicRegistryProvider {
    public DataWorldGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registries, Entries entries) {
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.BIOME));
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.CONFIGURED_FEATURE));
        entries.addAll(registries.getWrapperOrThrow(RegistryKeys.PLACED_FEATURE));

        entries.addAll(registries.getWrapperOrThrow(net.jukoz.me.registries.DynamicRegistriesME.RACE));
        entries.addAll(registries.getWrapperOrThrow(net.jukoz.me.registries.DynamicRegistriesME.NPC_TYPE));
        entries.addAll(registries.getWrapperOrThrow(net.jukoz.me.registries.DynamicRegistriesME.FACTION));
        entries.addAll(registries.getWrapperOrThrow(net.jukoz.me.registries.DynamicRegistriesME.BIOME_EVENT));
        entries.addAll(registries.getWrapperOrThrow(net.jukoz.me.registries.DynamicRegistriesME.STRUCTURE_EVENT));
        entries.addAll(registries.getWrapperOrThrow(net.jukoz.me.registries.DynamicRegistriesME.STRUCTURE_MANAGER_DATA));
        entries.addAll(registries.getWrapperOrThrow(net.jukoz.me.registries.DynamicRegistriesME.TEXTURE_PRESETS));
        entries.addAll(registries.getWrapperOrThrow(net.jukoz.me.registries.DynamicRegistriesME.SPIDER_VARIANTS));
        entries.addAll(registries.getWrapperOrThrow(net.jukoz.me.registries.DynamicRegistriesME.GREAT_HORN_VARIANTS));
    }

    @Override
    public String getName() {
        return MiddleEarth.MOD_ID;
    }
}
