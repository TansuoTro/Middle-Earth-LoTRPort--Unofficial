package net.jukoz.me.statusEffects;

import net.jukoz.me.MiddleEarth;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class ModStatusEffects {
    public static final StatusEffect HALLUCINATION = new Hallucination();

    /** Donor 1.21.8 references this effect but its registration file is missing from the snapshot. */
    public static final StatusEffect RESTRAINED = new StatusEffect(StatusEffectCategory.HARMFUL, 0x808080) {};
    public static final StatusEffect ENSHROUDED = new EnshroudedStatusEffect(StatusEffectCategory.HARMFUL, 0x2B2B3A);

    public static void registerStatusEffects(){
        Registry.register(Registries.STATUS_EFFECT, new ResourceLocation(MiddleEarth.MOD_ID, "hallucination"), HALLUCINATION).addAttributeModifier(Attributes.GENERIC_MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15000000596046448, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        Registry.register(Registries.STATUS_EFFECT, new ResourceLocation(MiddleEarth.MOD_ID, "enshrouded"), ENSHROUDED);
        Registry.register(Registries.STATUS_EFFECT, new ResourceLocation(MiddleEarth.MOD_ID, "restrained"), RESTRAINED);
    }
}
