package net.jukoz.me.datageneration;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.jukoz.me.MiddleEarth;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.RegistryAccess;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;

public class DataWorldGenerator extends FabricDynamicRegistryProvider {
    public DataWorldGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registries, Entries entries) {
        entries.addAll(registries.lookupOrThrow(Registries.BIOME));
        entries.addAll(registries.lookupOrThrow(Registries.CONFIGURED_FEATURE));
        entries.addAll(registries.lookupOrThrow(Registries.PLACED_FEATURE));

        entries.addAll(registries.lookupOrThrow(net.jukoz.me.registries.DynamicRegistriesME.RACE));
        entries.addAll(registries.lookupOrThrow(net.jukoz.me.registries.DynamicRegistriesME.NPC_TYPE));
        entries.addAll(registries.lookupOrThrow(net.jukoz.me.registries.DynamicRegistriesME.FACTION));
        entries.addAll(registries.lookupOrThrow(net.jukoz.me.registries.DynamicRegistriesME.BIOME_EVENT));
        entries.addAll(registries.lookupOrThrow(net.jukoz.me.registries.DynamicRegistriesME.STRUCTURE_EVENT));
        entries.addAll(registries.lookupOrThrow(net.jukoz.me.registries.DynamicRegistriesME.STRUCTURE_MANAGER_DATA));
        entries.addAll(registries.lookupOrThrow(net.jukoz.me.registries.DynamicRegistriesME.TEXTURE_PRESETS));
        entries.addAll(registries.lookupOrThrow(net.jukoz.me.registries.DynamicRegistriesME.SPIDER_VARIANTS));
        entries.addAll(registries.lookupOrThrow(net.jukoz.me.registries.DynamicRegistriesME.GREAT_HORN_VARIANTS));
        entries.addAll(registries.lookupOrThrow(net.jukoz.me.registries.DynamicRegistriesME.SKIN_MATERIAL));
        entries.addAll(registries.lookupOrThrow(net.jukoz.me.registries.DynamicRegistriesME.EYE_MATERIAL));
        entries.addAll(registries.lookupOrThrow(net.jukoz.me.registries.DynamicRegistriesME.HAIR_MATERIAL));
        entries.addAll(registries.lookupOrThrow(net.jukoz.me.registries.DynamicRegistriesME.SKIN_PATTERN));
        entries.addAll(registries.lookupOrThrow(net.jukoz.me.registries.DynamicRegistriesME.EYE_PATTERN));
        entries.addAll(registries.lookupOrThrow(net.jukoz.me.registries.DynamicRegistriesME.HAIR_PATTERN));
    }

    @Override
    public String getName() {
        return MiddleEarth.MOD_ID;
    }
}
