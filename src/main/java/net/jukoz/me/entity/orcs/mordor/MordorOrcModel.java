package net.jukoz.me.entity.orcs.mordor;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.CrossbowPosing;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.util.Arm;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.Mth;
@Environment(value= EnvType.CLIENT)
public class MordorOrcModel<T extends Mob>
        extends BipedEntityModel<T> {

    public MordorOrcModel(ModelPart root) {
        super(root);
    }

    @Override
    public void animateModel(T mobEntity, float f, float g, float h) {
        this.rightArmPose = BipedEntityModel.ArmPose.EMPTY;
        this.leftArmPose = BipedEntityModel.ArmPose.EMPTY;
        ItemStack itemStack = ((LivingEntity)mobEntity).getStackInHand(InteractionHand.MAIN_HAND);
        if (itemStack.isOf(Items.BOW) && ((Mob)mobEntity).isAttacking()) {
            if (((Mob)mobEntity).getMainArm() == Arm.RIGHT) {
                this.rightArmPose = BipedEntityModel.ArmPose.BOW_AND_ARROW;
            } else {
                this.leftArmPose = BipedEntityModel.ArmPose.BOW_AND_ARROW;
            }
        }
        super.animateModel(mobEntity, f, g, h);
    }

    /*@Override
    public void setAngles(T mobEntity, float f, float g, float h, float i, float j) {
        super.setAngles(mobEntity, f, g, h, i, j);
        ItemStack itemStack = ((LivingEntity)mobEntity).getMainHandStack();
        MordorOrcEntity.State state = ((MordorOrcEntity)mobEntity).getState();

        if(state == MordorOrcEntity.State.ATTACKING) {
            // Walk added
            this.leftArm.pitch = Mth.cos(f * 0.6662f) * 2.0f * g * 0.5f;
            this.leftArm.yaw = 0.0f;
            this.leftArm.roll = 0.0f;

            this.rightArm.pitch = -0.9f + Mth.cos(f * 0.6662f + (float)Math.PI) * 2.0f * g * 0.5f;
            this.rightArm.yaw = 0.0f;
            this.rightArm.roll = 0.0f;
        }
    }

    @Override
    public void setArmAngle(Arm arm, PoseStack matrices) {
        float f = arm == Arm.RIGHT ? 1.0f : -1.0f;
        ModelPart modelPart = this.getArm(arm);
        modelPart.pivotX += f;
        modelPart.rotate(matrices);
        modelPart.pivotX -= f;
    }*/
}
