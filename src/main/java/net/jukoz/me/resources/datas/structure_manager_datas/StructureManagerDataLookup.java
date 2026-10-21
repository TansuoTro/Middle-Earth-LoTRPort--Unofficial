package net.jukoz.me.resources.datas.structure_manager_datas;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.jukoz.me.registries.DynamicRegistriesME;

import java.util.Optional;

public class StructureManagerDataLookup {
    public static Optional<StructureManagerData> getStructureManagerData(Level world, ResourceLocation id) {
        return world.getRegistryManager().get(DynamicRegistriesME.STRUCTURE_MANAGER_DATA).getOrEmpty(id);
    }
}
