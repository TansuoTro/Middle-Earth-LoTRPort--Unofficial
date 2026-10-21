package net.jukoz.me.resources.datas.races;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.jukoz.me.registries.DynamicRegistriesME;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RaceLookup {
    public static List<Race> getAllRaces(Level world) {
        return world.getRegistryManager().get(DynamicRegistriesME.RACE).stream().toList();
    }

    public static List<Race> getAllRaces(Level world, List<ResourceLocation> ids) {
        Registry<Race> registry = world.getRegistryManager().get(DynamicRegistriesME.RACE);
        List<Race> list = new ArrayList<>();
        for(ResourceLocation id : ids){
            Race race = registry.get(id);
            if(!list.contains(race))
                list.add(race);
        }
        return list;
    }

    public static Race getRace(Level world, ResourceLocation identifier) {
        Optional<Race> race = world.getRegistryManager().get(DynamicRegistriesME.RACE).getOrEmpty(identifier);
        return race.orElse(null);
    }
}
