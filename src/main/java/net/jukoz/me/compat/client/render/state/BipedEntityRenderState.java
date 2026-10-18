package net.jukoz.me.compat.client.render.state;

import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EntityPose;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;

/**
 * 1.20.1 stand-in for 1.21.8
 * {@code net.jukoz.me.compat.client.render.state.BipedEntityRenderState}. Only the
 * members the donor code reads are declared; R2 supplies the real behaviour.
 */
public class BipedEntityRenderState extends ArmedEntityRenderState {
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

    public boolean isInPose(EntityPose pose) {
        return this.pose == pose;
    }
}
