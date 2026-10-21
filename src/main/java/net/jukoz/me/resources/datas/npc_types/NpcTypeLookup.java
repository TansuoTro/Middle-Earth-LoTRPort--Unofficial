package net.jukoz.me.resources.datas.npc_types;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.jukoz.me.registries.DynamicRegistriesME;

import java.util.ArrayList;
import java.util.List;

public class NpcTypeLookup {
    public static List<NpcType> getAllNpcTypes(Level world, List<ResourceLocation> ids) {
        Registry<NpcType> registry = world.getRegistryManager().get(DynamicRegistriesME.NPC_TYPE);
        List<NpcType> list = new ArrayList<>();
        for(ResourceLocation id : ids){
            list.add(registry.get(id));
        }
        return list;
    }

    public static List<NpcType> getAllNpcTypesFromRace(Level world, List<ResourceLocation> ids, ResourceLocation race){
        List<NpcType> unsortedList = getAllNpcTypes(world, ids);
        List<NpcType> list = new ArrayList<>();
        for(NpcType npcType : unsortedList){
            if(npcType.getRace().equals(race))
                list.add(npcType);
        }
        return list;
    }
    public static NpcType getNpcType(Level world, ResourceLocation id) {
        return world.getRegistryManager().get(DynamicRegistriesME.NPC_TYPE).get(id);
    }
}
