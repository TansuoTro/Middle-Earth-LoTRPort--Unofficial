package net.jukoz.me.mixin;

import net.jukoz.me.entity.EntityAttributesME;
import net.jukoz.me.enchantments.EnchantmentsME;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.world.entity.Pose;

/** 1.20.1 engine consumers for the later-version attributes used by race and NPC data. */
@Mixin(LivingEntity.class)
public abstract class LivingAttributeCompatMixin {
    @Unique private float me$lastScale = 1.0F;
    @Unique private float me$lastWidth = 1.0F;

    @Inject(method = "tick", at = @At("HEAD"))
    private void me$refreshDimensions(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        float scale = EntityAttributesME.scale(entity);
        float width = (float) EntityAttributesME.value(entity, EntityAttributesME.WIDTH_SCALE);
        if (scale != me$lastScale || width != me$lastWidth) {
            me$lastScale = scale;
            me$lastWidth = width;
            entity.refreshDimensions();
        }
        net.jukoz.me.enchantments.EnchantmentAttributeEffects.apply(entity);
    }

    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    private void me$dimensions(Pose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (pose != Pose.SLEEPING) {
            float scale = EntityAttributesME.scale(entity);
            cir.setReturnValue(cir.getReturnValue().scaled(scale * (float) EntityAttributesME.value(entity, EntityAttributesME.WIDTH_SCALE), scale));
        }
    }

    // Pass unscaled dimensions to species-specific eye-height calculations, then scale their result.
    @ModifyVariable(method = "getEyeHeight(Lnet/minecraft/entity/Pose;Lnet/minecraft/entity/EntityDimensions;)F",
            at = @At("HEAD"), argsOnly = true)
    private EntityDimensions me$eyeDimensions(EntityDimensions dimensions) {
        LivingEntity entity = (LivingEntity) (Object) this;
        float scale = EntityAttributesME.scale(entity);
        float width = (float) EntityAttributesME.value(entity, EntityAttributesME.WIDTH_SCALE);
        return dimensions.scaled(1.0F / (scale * width), 1.0F / scale);
    }

    @Inject(method = "getEyeHeight(Lnet/minecraft/entity/Pose;Lnet/minecraft/entity/EntityDimensions;)F",
            at = @At("RETURN"), cancellable = true)
    private void me$eyeHeight(Pose pose, EntityDimensions dimensions, CallbackInfoReturnable<Float> cir) {
        if (pose != Pose.SLEEPING) cir.setReturnValue(cir.getReturnValue() * EntityAttributesME.scale((LivingEntity) (Object) this));
    }

    @ModifyConstant(method = "computeFallDamage", constant = @Constant(floatValue = 3.0F))
    private float me$safeFallDistance(float original) {
        return (float) EntityAttributesME.value((LivingEntity) (Object) this, EntityAttributesME.SAFE_FALL_DISTANCE);
    }

    @ModifyVariable(method = "computeFallDamage", at = @At("HEAD"), argsOnly = true, ordinal = 1)
    private float me$fallMultiplier(float original) {
        return original * (float) EntityAttributesME.value((LivingEntity) (Object) this, EntityAttributesME.FALL_DAMAGE_MULTIPLIER);
    }

    @ModifyVariable(method = "travel", at = @At(value = "STORE", ordinal = 0), ordinal = 2)
    private float me$waterEfficiency(float depthStrider) {
        return Math.max(depthStrider, 3.0F * (float) EntityAttributesME.value((LivingEntity) (Object) this, EntityAttributesME.WATER_MOVEMENT_EFFICIENCY));
    }

    @ModifyVariable(method = "addStatusEffect(Lnet/minecraft/entity/effect/MobEffectInstance;Lnet/minecraft/entity/Entity;)Z",
            at = @At("HEAD"), argsOnly = true)
    private MobEffectInstance me$ailmentProtection(MobEffectInstance effect) {
        var enchantment = EnchantmentsME.get(EnchantmentsME.AILMENT_PROTECTION);
        if (enchantment == null || effect.getEffectType().getCategory() != MobEffectCategory.HARMFUL || effect.isInfinite()) return effect;
        int level = 0;
        for (var stack : ((LivingEntity) (Object) this).getArmorSlots()) level += EnchantmentHelper.getLevel(enchantment, stack);
        if (level == 0) return effect;
        int duration = Math.max(1, Math.round(effect.getDuration() * (1.0F - Math.min(0.8F, 0.08F * level))));
        return new MobEffectInstance(effect.getEffectType(), duration, effect.getAmplifier(), effect.isAmbient(), effect.shouldShowParticles(), effect.shouldShowIcon());
    }

    @Inject(method = "getAttackDistanceScalingFactor", at = @At("RETURN"), cancellable = true)
    private void me$stealth(Entity observer, CallbackInfoReturnable<Double> cir) {
        LivingEntity entity = (LivingEntity) (Object) this;
        int level = net.jukoz.me.enchantments.EnchantmentAttributeEffects.level(EnchantmentsME.STEALTHY_TRAIL, entity.getEquippedStack(EquipmentSlot.LEGS));
        double detection = EntityAttributesME.value(entity, EntityAttributesME.DETECTION_RANGE);
        cir.setReturnValue(Math.max(0.0, cir.getReturnValue() * detection + Math.max(-0.9, -0.2 * level)));
    }
}
