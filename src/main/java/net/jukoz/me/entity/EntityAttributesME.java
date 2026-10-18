package net.jukoz.me.entity;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.jukoz.me.MiddleEarth;

/**
 * Attributes the 1.21 tree relies on.
 *
 * <p>Most of them moved out of vanilla in the 1.20.5 attribute rework, where
 * {@code EntityAttributes.GENERIC_MAX_HEALTH} lost its {@code GENERIC_} prefix and a
 * batch of new attributes appeared. On 1.20.1 the renames are handled by the importer;
 * the ones that simply did not exist yet are registered here under the mod namespace so
 * the 1.21 data (race pools, npc type pools, weapon modifiers) still loads.
 *
 * <p>Registering them is not the same as honouring them: 1.20.1 has no engine code that
 * reads {@code entity_interaction_range} or {@code scale}, so those keep their value but
 * do nothing until a mixin consumes them. See PORTING_NOTES.md.
 */
public class EntityAttributesME {
    public static final RegistryEntry<EntityAttribute> POWDERED_SNOW_IMMUNITY;
    public static final RegistryEntry<EntityAttribute> DELVERS_FEAR_STRENGTH;
    public static final RegistryEntry<EntityAttribute> CLIMBING_STRENGTH;
    public static final RegistryEntry<EntityAttribute> DETECTION_RANGE;
    public static final RegistryEntry<EntityAttribute> WIDTH_SCALE;

    // 1.20.5+ vanilla attributes, registered locally so the ported data still resolves.
    public static final RegistryEntry<EntityAttribute> SCALE;
    public static final RegistryEntry<EntityAttribute> ENTITY_INTERACTION_RANGE;
    public static final RegistryEntry<EntityAttribute> BLOCK_INTERACTION_RANGE;
    public static final RegistryEntry<EntityAttribute> SAFE_FALL_DISTANCE;
    public static final RegistryEntry<EntityAttribute> SNEAKING_SPEED;
    public static final RegistryEntry<EntityAttribute> MINING_EFFICIENCY;
    public static final RegistryEntry<EntityAttribute> FALL_DAMAGE_MULTIPLIER;
    public static final RegistryEntry<EntityAttribute> WATER_MOVEMENT_EFFICIENCY;
    public static final RegistryEntry<EntityAttribute> MOVEMENT_EFFICIENCY;
    public static final RegistryEntry<EntityAttribute> BURNING_TIME;

    private static RegistryEntry<EntityAttribute> register(String name, double defaultValue, double minValue, double maxValue, boolean tracked) {
        Identifier id = MiddleEarth.of(name);
        EntityAttribute attribute = new ClampedEntityAttribute(id.toTranslationKey(), defaultValue, minValue, maxValue).setTracked(tracked);
        return Registry.registerReference(Registries.ATTRIBUTE, id, attribute);
    }

    public static void register() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod attributes");
    }

    static {
        POWDERED_SNOW_IMMUNITY = register("powdered_snow_immunity", 0.0, 0.0, 1.0, true);
        DELVERS_FEAR_STRENGTH = register("delvers_fear_strength", 0.0, 0.0, Double.MAX_VALUE, true);
        CLIMBING_STRENGTH = register("climbing_strength", 0.0, 0.0, Double.MAX_VALUE, true);
        DETECTION_RANGE = register("detection_range", 1.0, 0.1, 1.0, true);
        WIDTH_SCALE = register("width_scale", 1.0, 0.1, 2.0, true);

        SCALE = register("scale", 1.0, 0.0625, 16.0, true);
        ENTITY_INTERACTION_RANGE = register("entity_interaction_range", 3.0, 0.0, 64.0, true);
        BLOCK_INTERACTION_RANGE = register("block_interaction_range", 4.5, 0.0, 64.0, true);
        SAFE_FALL_DISTANCE = register("safe_fall_distance", 3.0, -1024.0, 1024.0, true);
        SNEAKING_SPEED = register("sneaking_speed", 0.3, 0.0, 1.0, true);
        MINING_EFFICIENCY = register("mining_efficiency", 0.0, 0.0, 1024.0, true);
        FALL_DAMAGE_MULTIPLIER = register("fall_damage_multiplier", 1.0, 0.0, 100.0, true);
        WATER_MOVEMENT_EFFICIENCY = register("water_movement_efficiency", 0.0, 0.0, 1.0, true);
        MOVEMENT_EFFICIENCY = register("movement_efficiency", 0.0, 0.0, 1.0, true);
        BURNING_TIME = register("burning_time", 1.0, 0.0, 1024.0, true);
    }
}
