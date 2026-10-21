package net.jukoz.me.mixin.client;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.item.ItemStack;
import net.jukoz.me.item.DecorativeItemsME;
import net.jukoz.me.item.WeaponItemsME;
import net.jukoz.me.item.items.weapons.ReachWeaponItem;
import net.jukoz.me.statusEffects.ModStatusEffects;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * R3: 1.20.1 rewrite of the donor 1.21.8 render-state mixin. 1.20.1's
 * BipedEntityModel.positionRightArm/positionLeftArm take the entity directly,
 * so the state access interfaces are not needed here.
 */
@Mixin(BipedEntityModel.class)
public class BipedEntityModelMixin {

    @Shadow @Final
    public ModelPart rightArm;

    @Shadow @Final
    public ModelPart leftArm;

    private static final float VERTICAL_ANGLE = -1.4f;

    @Inject(at = @At("TAIL"), method = "positionRightArm(Lnet/minecraft/entity/LivingEntity;)V")
    private void me$positionRightArm(LivingEntity entity, CallbackInfo ci) {
        if (isRestrained(entity)) {
            restrainedAnimation();
        } else {
            tryItemAnimation(entity.getMainHandStack(), true);
        }
    }

    @Inject(at = @At("TAIL"), method = "positionLeftArm(Lnet/minecraft/entity/LivingEntity;)V")
    private void me$positionLeftArm(LivingEntity entity, CallbackInfo ci) {
        if (isRestrained(entity)) {
            restrainedAnimation();
        } else {
            tryItemAnimation(entity.getOffHandStack(), false);
        }
    }

    private static boolean isRestrained(LivingEntity entity) {
        StatusEffectInstance effect = entity.getStatusEffect(ModStatusEffects.RESTRAINED);
        return effect != null && effect.getDuration() > 0;
    }

    private void restrainedAnimation() {
        this.rightArm.pitch = 0.0F;
        this.rightArm.yaw = 0.0F;
        this.leftArm.pitch = 0.0F;
        this.leftArm.yaw = 0.0F;
    }

    private void tryItemAnimation(ItemStack itemStack, boolean rightHand) {
        if (itemStack.isEmpty()) {
            return;
        }
        if (itemStack.getItem().equals(DecorativeItemsME.FIRE_OF_ORTHANC)) {
            float pitch = this.rightArm.pitch * 0.25F - 0.5F;
            this.rightArm.pitch = pitch;
            this.rightArm.yaw = 0.0F;
            this.leftArm.pitch = pitch;
            this.leftArm.yaw = 0.0F;
        } else if (itemStack.getItem() instanceof ReachWeaponItem reachWeapon && reachWeapon.type.twoHanded) {
            float pitch = -1.15f;
            this.rightArm.pitch = pitch;
            this.leftArm.pitch = pitch - 0.2f;
            this.rightArm.yaw = -0.35f;
            this.leftArm.yaw = 0.8f;
        } else if (itemStack.getItem() == WeaponItemsME.HELD_BANNER) {
            if (rightHand) this.rightArm.pitch = VERTICAL_ANGLE;
            else this.leftArm.pitch = VERTICAL_ANGLE;
        } else if (itemStack.getItem() == DecorativeItemsME.TORCH_OF_ORTHANC) {
            if (rightHand) this.rightArm.pitch = VERTICAL_ANGLE;
            else this.leftArm.pitch = VERTICAL_ANGLE;
        }
    }
}
