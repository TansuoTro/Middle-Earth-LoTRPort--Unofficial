package net.jukoz.me.utils;

import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.jukoz.me.MiddleEarth;

public class EntityTypeTagsME {
    public static TagKey<EntityType<?>> CAN_WEAR_GOAT_ARMOR = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(MiddleEarth.MOD_ID, "can_wear_goat_armor"));
    public static TagKey<EntityType<?>> CAN_WEAR_WARG_ARMOR = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(MiddleEarth.MOD_ID, "can_wear_warg_armor"));
    public static TagKey<EntityType<?>> CAN_WEAR_GREAT_HORN_ARMOR = TagKey.of(RegistryKeys.ENTITY_TYPE, new Identifier(MiddleEarth.MOD_ID, "can_wear_great_horn_armor"));
}
