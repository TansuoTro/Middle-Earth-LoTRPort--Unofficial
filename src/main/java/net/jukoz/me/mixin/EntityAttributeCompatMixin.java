package net.jukoz.me.mixin;

import net.jukoz.me.entity.EntityAttributesME;
import net.jukoz.me.enchantments.EnchantmentAttributeEffects;
import net.jukoz.me.enchantments.EnchantmentsME;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityAttributeCompatMixin {
    @Inject(method = "getVelocityMultiplier", at = @At("RETURN"), cancellable = true)
    private void me$movementEfficiency(CallbackInfoReturnable<Float> cir) {
        if ((Object) this instanceof LivingEntity living && cir.getReturnValue() < 1.0F) {
            float efficiency = (float) EntityAttributesME.value(living, EntityAttributesME.MOVEMENT_EFFICIENCY);
            cir.setReturnValue(cir.getReturnValue() + (1.0F - cir.getReturnValue()) * efficiency);
        }
    }

    @ModifyVariable(method = "setOnFireFor", at = @At(value = "STORE", ordinal = 0), ordinal = 1)
    private int me$burningTime(int ticks) {
        return (Object) this instanceof LivingEntity living
                ? (int) Math.ceil(ticks * EntityAttributesME.value(living, EntityAttributesME.BURNING_TIME)) : ticks;
    }

    @Inject(method = "getStepHeight", at = @At("RETURN"), cancellable = true)
    private void me$highStep(CallbackInfoReturnable<Float> cir) {
        if ((Object) this instanceof LivingEntity living) {
            int level = EnchantmentAttributeEffects.level(EnchantmentsME.HIGH_STEP, living.getEquippedStack(EquipmentSlot.FEET));
            if (level > 0) cir.setReturnValue(cir.getReturnValue() + level * 0.5F);
        }
    }
}
