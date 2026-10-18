package net.jukoz.me.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.jukoz.me.MiddleEarth;

public class EntityTypeTagsME {
    public static TagKey<EntityType<?>> UNGOLIENI = of("ungolieni");

    private static TagKey<EntityType<?>> of(String id) {
        return TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(MiddleEarth.MOD_ID, id));
    }
}
