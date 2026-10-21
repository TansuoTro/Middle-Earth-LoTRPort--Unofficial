package net.jukoz.me.registries.content.races;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.Registry;
import net.minecraft.core.HolderGetter;
import net.minecraft.resources.ResourceKey;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.datageneration.content.TranslationEntries;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.resources.datas.races.Race;
import net.minecraft.core.HolderGetter;

public class RaceRegistry {
    private static final ResourceKey<Registry<Race>> RACE_KEY = DynamicRegistriesME.RACE;

    public final static ResourceKey<Race> DWARF = DynamicRegistriesME.of(RACE_KEY, RacePools.DWARF.getId());
    public final static ResourceKey<Race> ELF = DynamicRegistriesME.of(RACE_KEY, RacePools.ELF.getId());
    public final static ResourceKey<Race> HOBBIT = DynamicRegistriesME.of(RACE_KEY, RacePools.HOBBIT.getId());
    public final static ResourceKey<Race> HUMAN = DynamicRegistriesME.of(RACE_KEY, RacePools.HUMAN.getId());
    public final static ResourceKey<Race> ORC = DynamicRegistriesME.of(RACE_KEY, RacePools.ORC.getId());
    public final static ResourceKey<Race> URUK = DynamicRegistriesME.of(RACE_KEY, RacePools.URUK.getId());
    public final static ResourceKey<Race> GOBLIN = DynamicRegistriesME.of(RACE_KEY, RacePools.GOBLIN.getId());
    public final static ResourceKey<Race> SNAGA = DynamicRegistriesME.of(RACE_KEY, RacePools.SNAGA.getId());

    public static void bootstrap(BootstrapContext<Race> context) {
        HolderGetter<Race> registryEntryLookup = context.getRegistryLookup(RACE_KEY);

        register(context, registryEntryLookup, DWARF, RacePools.DWARF);
        register(context, registryEntryLookup, ELF, RacePools.ELF);
        register(context, registryEntryLookup, HOBBIT, RacePools.HOBBIT);
        register(context, registryEntryLookup, HUMAN, RacePools.HUMAN);
        register(context, registryEntryLookup, ORC, RacePools.ORC);
        register(context, registryEntryLookup, URUK, RacePools.URUK);
        register(context, registryEntryLookup, GOBLIN, RacePools.GOBLIN);
        register(context, registryEntryLookup, SNAGA, RacePools.SNAGA);
    }

    private static void register(BootstrapContext<Race> context, HolderGetter<Race> registryEntryLookup, ResourceKey<Race> registryKey, Race element){
        DynamicRegistriesME.register(context, registryEntryLookup, registryKey, element);
        // [LANG datagen]
        TranslationEntries.raceEntries.add(registryKey.location().getPath());
    }
}
