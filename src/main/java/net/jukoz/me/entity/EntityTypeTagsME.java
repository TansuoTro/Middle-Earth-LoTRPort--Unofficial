package net.jukoz.me.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;

public class EntityTypeTagsME {
    public static TagKey<EntityType<?>> UNGOLIENI = of("ungolieni");

    private static TagKey<EntityType<?>> of(String id) {
        return TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(MiddleEarth.MOD_ID, id));
    }
}
