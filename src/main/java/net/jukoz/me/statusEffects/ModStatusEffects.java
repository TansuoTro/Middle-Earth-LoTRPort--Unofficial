package net.jukoz.me.statusEffects;

import net.jukoz.me.MiddleEarth;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

public class ModStatusEffects {
    public static final MobEffect HALLUCINATION = new Hallucination();

    /** Donor 1.21.8 references this effect but its registration file is missing from the snapshot. */
    public static final MobEffect RESTRAINED = new MobEffect(MobEffectCategory.HARMFUL, 0x808080) {};
    public static final MobEffect ENSHROUDED = new EnshroudedStatusEffect(MobEffectCategory.HARMFUL, 0x2B2B3A);

    public static void registerStatusEffects(){
        Registry.register(Registries.STATUS_EFFECT, new ResourceLocation(MiddleEarth.MOD_ID, "hallucination"), HALLUCINATION).addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15000000596046448, AttributeModifier.Operation.MULTIPLY_TOTAL);
        Registry.register(Registries.STATUS_EFFECT, new ResourceLocation(MiddleEarth.MOD_ID, "enshrouded"), ENSHROUDED);
        Registry.register(Registries.STATUS_EFFECT, new ResourceLocation(MiddleEarth.MOD_ID, "restrained"), RESTRAINED);
    }
}
