package net.jukoz.me.compat.client.render.state;

import java.util.UUID;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EntityPose;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.jukoz.me.client.renderer.ArmedEntityRenderStateAccess;
import net.jukoz.me.client.renderer.BipedEntityRenderStateAccess;

/**
 * R2 field carrier for the biped render state family. The donor-named
 * {@link BipedEntityRenderState} simply extends this class.
 *
 * <p>R3: also implements the donor's state-access interfaces directly, so the
 * 1.21.8 state mixins (which used to add these fields) could be deleted on 1.20.1.
 */
public class BipedEntityRenderStateCompat extends LivingEntityRenderStateCompat
        implements ArmedEntityRenderStateAccess, BipedEntityRenderStateAccess {
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

    private ItemStack mainHandStack = ItemStack.EMPTY;
    private ItemStack offHandStack = ItemStack.EMPTY;
    private boolean restrained;
    private float tickProgress;
    private Vec3d velocity = Vec3d.ZERO;
    private Vec3d previousVelocity = Vec3d.ZERO;

    public boolean isInPose(EntityPose pose) {
        return this.pose == pose;
    }

    @Override
    public ItemStack getMainHandStack() {
        return this.mainHandStack;
    }

    @Override
    public ItemStack getOffHandStack() {
        return this.offHandStack;
    }

    @Override
    public boolean isRestrained() {
        return this.restrained;
    }

    @Override
    public void setMainHandStack(ItemStack mainHandStack) {
        this.mainHandStack = mainHandStack;
    }

    @Override
    public void setOffHandStack(ItemStack offHandStack) {
        this.offHandStack = offHandStack;
    }

    @Override
    public void setRestrained(boolean restrained) {
        this.restrained = restrained;
    }

    @Override
    public float getTickProgress() {
        return this.tickProgress;
    }

    @Override
    public Vec3d getPreviousVelocity() {
        return this.previousVelocity;
    }

    @Override
    public Vec3d getVelocity() {
        return this.velocity;
    }

    @Override
    public void setTickProgress(float tickProgress) {
        this.tickProgress = tickProgress;
    }

    @Override
    public void setPreviousVelocity(Vec3d velocity) {
        this.previousVelocity = velocity;
    }

    @Override
    public void setVelocity(Vec3d velocity) {
        this.velocity = velocity;
    }
}
