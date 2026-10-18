package net.jukoz.me.entity.npcs.initializer;

import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.jukoz.me.registries.DynamicRegistriesME;
import net.jukoz.me.resources.datas.npc_types.NpcType;

public class NpcEntityInitializerUtil {
    public static boolean characterIdentifierExist(World world, Identifier typeId){
        if(typeId == null)
            return false;
        DynamicRegistryManager registryManager = world.getRegistryManager();
        NpcType type =  registryManager.get(DynamicRegistriesME.NPC_TYPE).get(typeId);
        return type != null;
    }
}
