package net.jukoz.me.entity;

import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
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
 * Attribute compatibility mixins consume the values in movement, reach, damage and rendering.
 */
public class EntityAttributesME {
    public static final Holder<EntityAttribute> POWDERED_SNOW_IMMUNITY;
    public static final Holder<EntityAttribute> DELVERS_FEAR_STRENGTH;
    public static final Holder<EntityAttribute> CLIMBING_STRENGTH;
    public static final Holder<EntityAttribute> DETECTION_RANGE;
    public static final Holder<EntityAttribute> WIDTH_SCALE;

    // 1.20.5+ vanilla attributes, registered locally so the ported data still resolves.
    public static final Holder<EntityAttribute> SCALE;
    public static final Holder<EntityAttribute> ENTITY_INTERACTION_RANGE;
    public static final Holder<EntityAttribute> BLOCK_INTERACTION_RANGE;
    public static final Holder<EntityAttribute> SAFE_FALL_DISTANCE;
    public static final Holder<EntityAttribute> SNEAKING_SPEED;
    public static final Holder<EntityAttribute> MINING_EFFICIENCY;
    public static final Holder<EntityAttribute> FALL_DAMAGE_MULTIPLIER;
    public static final Holder<EntityAttribute> WATER_MOVEMENT_EFFICIENCY;
    public static final Holder<EntityAttribute> MOVEMENT_EFFICIENCY;
    public static final Holder<EntityAttribute> BURNING_TIME;

    private static Holder<EntityAttribute> register(String name, double defaultValue, double minValue, double maxValue, boolean tracked) {
        ResourceLocation id = MiddleEarth.of(name);
        EntityAttribute attribute = new ClampedEntityAttribute(id.toTranslationKey(), defaultValue, minValue, maxValue).setTracked(tracked);
        return Registry.registerReference(Registries.ATTRIBUTE, id, attribute);
    }

    public static void register() {
        MiddleEarth.LOGGER.logDebugMsg("Registering Mod attributes");
        net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry.register(
                net.minecraft.world.entity.EntityType.PLAYER,
                addDefaults(net.minecraft.world.entity.player.Player.createPlayerAttributes()));
    }

    public static net.minecraft.entity.attribute.DefaultAttributeContainer.Builder addDefaults(
            net.minecraft.entity.attribute.DefaultAttributeContainer.Builder builder) {
        for (Holder<EntityAttribute> attribute : java.util.List.of(POWDERED_SNOW_IMMUNITY,
                DELVERS_FEAR_STRENGTH, CLIMBING_STRENGTH, DETECTION_RANGE, WIDTH_SCALE, SCALE,
                ENTITY_INTERACTION_RANGE, BLOCK_INTERACTION_RANGE, SAFE_FALL_DISTANCE, SNEAKING_SPEED,
                MINING_EFFICIENCY, FALL_DAMAGE_MULTIPLIER, WATER_MOVEMENT_EFFICIENCY,
                MOVEMENT_EFFICIENCY, BURNING_TIME)) {
            builder.add(attribute.value());
        }
        return builder;
    }

    public static double value(net.minecraft.world.entity.LivingEntity entity, Holder<EntityAttribute> attribute) {
        var attributes = entity.getAttributes();
        return attributes != null && attributes.hasAttribute(attribute.value())
                ? entity.getAttributeValue(attribute.value()) : attribute.value().getDefaultValue();
    }

    public static float scale(net.minecraft.world.entity.LivingEntity entity) {
        return (float) value(entity, SCALE);
    }

    public static double entityReach(net.minecraft.world.entity.player.Player player) {
        return value(player, ENTITY_INTERACTION_RANGE) + (player.getAbilities().creativeMode ? 2.0 : 0.0);
    }

    public static double blockReach(net.minecraft.world.entity.player.Player player) {
        return value(player, BLOCK_INTERACTION_RANGE) + (player.getAbilities().creativeMode ? 0.5 : 0.0);
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
