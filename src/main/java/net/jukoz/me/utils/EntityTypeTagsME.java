package net.jukoz.me.utils;

import net.minecraft.world.entity.EntityType;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;

public class EntityTypeTagsME {
    public static TagKey<EntityType<?>> CAN_WEAR_GOAT_ARMOR = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(MiddleEarth.MOD_ID, "can_wear_goat_armor"));
    public static TagKey<EntityType<?>> CAN_WEAR_WARG_ARMOR = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(MiddleEarth.MOD_ID, "can_wear_warg_armor"));
    public static TagKey<EntityType<?>> CAN_WEAR_GREAT_HORN_ARMOR = TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(MiddleEarth.MOD_ID, "can_wear_great_horn_armor"));
}
