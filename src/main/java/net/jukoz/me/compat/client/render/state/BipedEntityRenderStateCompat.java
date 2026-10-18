package net.jukoz.me.compat.client.render.state;

import java.util.UUID;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EntityPose;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;

/**
 * R2 field carrier for the biped render state family. The donor-named
 * {@link BipedEntityRenderState} simply extends this class.
 */
public class BipedEntityRenderStateCompat extends LivingEntityRenderStateCompat {
    public boolean isSneaking;
    public boolean isInSneakingPose;
    public float leaningPitch;
    public float handSwingProgress;
    public float baseScale = 1.0F;
    public float standingEyeHeight;
    public EntityPose pose = EntityPose.STANDING;
    public Direction sleepingDirection;
    public BipedEntityModel.ArmPose leftArmPose = BipedEntityModel.ArmPose.EMPTY;
    public BipedEntityModel.ArmPose rightArmPose = BipedEntityModel.ArmPose.EMPTY;
    public ItemStack equippedChestStack = ItemStack.EMPTY;
    public UUID uuid;

    public boolean isInPose(EntityPose pose) {
        return this.pose == pose;
    }
}
