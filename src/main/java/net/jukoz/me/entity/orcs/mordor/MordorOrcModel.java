package net.jukoz.me.entity.orcs.mordor;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.jukoz.me.entity.humans.gondor.GondorHumanEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.AnimationUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.InteractionHand;
import net.minecraft.util.Mth;
import net.minecraft.util.profiling.jfr.Environment;

@Environment(value= EnvType.CLIENT)
public class MordorOrcModel<T extends Mob>
        extends HumanoidModel<T> {

    public MordorOrcModel(ModelPart root) {
        super(root);
    }

    @Override
    public void animateModel(T mobEntity, float f, float g, float h) {
        this.rightArmPose = HumanoidModel.ArmPose.EMPTY;
        this.leftArmPose = HumanoidModel.ArmPose.EMPTY;
        ItemStack itemStack = ((LivingEntity)mobEntity).getItemInHand(InteractionHand.MAIN_HAND);
        if (itemStack.is(Items.BOW) && ((Mob)mobEntity).isAggressive()) {
            if (((Mob)mobEntity).getMainArm() == HumanoidArm.RIGHT) {
                this.rightArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
            } else {
                this.leftArmPose = HumanoidModel.ArmPose.BOW_AND_ARROW;
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
            this.leftArm.xRot = Mth.cos(f * 0.6662f) * 2.0f * g * 0.5f;
            this.leftArm.yRot = 0.0f;
            this.leftArm.roll = 0.0f;

            this.rightArm.xRot = -0.9f + Mth.cos(f * 0.6662f + (float)Math.PI) * 2.0f * g * 0.5f;
            this.rightArm.yRot = 0.0f;
            this.rightArm.roll = 0.0f;
        }
    }

    @Override
    public void setArmAngle(HumanoidArm arm, PoseStack matrices) {
        float f = arm == HumanoidArm.RIGHT ? 1.0f : -1.0f;
        ModelPart modelPart = this.getArm(arm);
        modelPart.pivotX += f;
        modelPart.rotate(matrices);
        modelPart.pivotX -= f;
    }*/
}
