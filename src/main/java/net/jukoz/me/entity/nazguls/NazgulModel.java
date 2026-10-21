package net.jukoz.me.entity.nazguls;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.model.*;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.PartNames;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.util.Mth;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.profiling.jfr.Environment;

@Environment(value= EnvType.CLIENT)
public class NazgulModel<T extends Mob>
        extends HumanoidModel<T> {

    private static final String LEFT_SLEEVE = "left_sleeve";
    private static final String RIGHT_SLEEVE = "right_sleeve";
    private static final String LEFT_PANTS = "left_leg";
    private static final String RIGHT_PANTS = "right_leg";
    private static final float LEFT_ARM_PIVOT_X = 7.0f;
    private static final float RIGHT_ARM_PIVOT_X = -7.0f;
    private static final float LEG_PIVOT_X = 1.9f;

    public NazgulModel(ModelPart root) {
        super(root);
    }

    @Override
    public void animateModel(T mobEntity, float f, float g, float h) {
        this.rightArmPose = ArmPose.EMPTY;
        this.leftArmPose = ArmPose.EMPTY;
        super.animateModel(mobEntity, f, g, h);
    }

    public static LayerDefinition getTexturedModelData(CubeDeformation dilation) {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition root = modelData.getRoot();

        root.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(8, 8)
                        .addBox(-1.0f, 0f, -1.0f, 2.0f, 2.0f, 2.0f, dilation),
                PartPose.offset(0.0f, 0.0f, 0.0f));

        root.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create().texOffs(0, 0)
                        .addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation),
                PartPose.offset(0.0f, 0.0f, 0.0f));

        PartDefinition leftArm = root.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create().texOffs(32, 48)
                        .addBox(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                PartPose.offset(LEFT_ARM_PIVOT_X, 2.5f, 0.0f));
        PartDefinition rightArm = root.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create().texOffs(40, 16)
                        .addBox(-3f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                PartPose.offset(RIGHT_ARM_PIVOT_X, 2.5f, 0.0f));

        rightArm.addOrReplaceChild(RIGHT_SLEEVE, CubeListBuilder.create().texOffs(40, 32).addBox(0f, 0f, 0f, 3.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                PartPose.offset(-2f, -2f, -2f));
        leftArm.addOrReplaceChild(LEFT_SLEEVE, CubeListBuilder.create().texOffs(48, 48).addBox(0f, 0f, 0f, 3.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                PartPose.offset(-1f, -2f, -2f));

        root.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create().texOffs(16, 16)
                        .addBox(-4.0f, 0.0f, -2f, 8.0f, 12.0f, 4.0f, dilation),
                PartPose.offset(0.0f, 0f, 0.0f));

        root.getChild(PartNames.BODY).addOrReplaceChild(PartNames.JACKET, CubeListBuilder.create().texOffs(16, 32)
                .addBox(-4.0f, 0.0f, 0f, 8.0f, 12.0f, 4.0f, dilation.add(0.25f)), PartPose.NONE);

        root.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create().texOffs(0, 16)
                        .addBox(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                PartPose.offset(-LEG_PIVOT_X, 0.0f, 0.0f));
        root.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create().texOffs(0, 16).mirror()
                        .addBox(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                PartPose.offset(LEG_PIVOT_X, 0.0f, 0.0f));

        root.getChild(PartNames.LEFT_LEG).addOrReplaceChild(LEFT_PANTS, CubeListBuilder.create().texOffs(0, 48)
                        .addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                PartPose.offset(0, 0.0f, 0.0f));
        root.getChild(PartNames.RIGHT_LEG).addOrReplaceChild(RIGHT_PANTS, CubeListBuilder.create().texOffs(0, 32)
                        .addBox(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                PartPose.offset(0, 0.0f, 0.0f));

        root.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create().texOffs(32, 0).addBox(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation.add(0.5f)),
                PartPose.offset(0.0f, 0.0f, 0.0f));                // 54

        return LayerDefinition.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T mobEntity, float limbAngle, float limbDistance, float ageInTicks, float i, float j) {
        super.setAngles(mobEntity, limbAngle, limbDistance, ageInTicks, i, j);
        ItemStack itemStack = (mobEntity).getMainHandStack();
        NazgulEntity.State state = ((NazgulEntity)mobEntity).getState();

        if(state == NazgulEntity.State.ATTACKING) {
            this.leftArm.xRot = Mth.cos(limbAngle * 0.6662f) * 2.0f * limbDistance * 0.5f;
            this.leftArm.yRot = 0.0f;
            this.leftArm.roll = 0.0f;

            this.rightArm.xRot = -0.9f + Mth.cos(limbAngle * 0.6662f + (float)Math.PI) * 2.0f * limbDistance * 0.5f;
            this.rightArm.yRot = 0.0f;
            this.rightArm.roll = 0.0f;
        } else if(state == NazgulEntity.State.ON_FIRE || state == NazgulEntity.State.FADING) {
            float limbCosTime = (float) (1 + 0.25f * Math.cos(limbAngle));
            this.rightArm.xRot = -3.1f + Mth.cos(ageInTicks * 0.4f + (float)Math.PI) * 0.3f * limbCosTime;
            this.rightArm.roll = -0.6f + Mth.sin(ageInTicks * 0.666f + (float)Math.PI) * 0.2f;

            this.leftArm.xRot = -3.1f + Mth.sin(ageInTicks * 0.4f + (float)Math.PI) * 0.3f * limbCosTime;
            this.leftArm.roll = 0.6f + Mth.cos(ageInTicks * 0.666f + (float)Math.PI) * 0.2f;
        }
    }

    @Override
    public void setArmAngle(HumanoidArm arm, PoseStack matrices) {
        float f = arm == HumanoidArm.RIGHT ? 1.0f : -1.0f;
        ModelPart modelPart = this.getArm(arm);
        modelPart.pivotX += f;
        modelPart.rotate(matrices);
        modelPart.pivotX -= f;
    }

    @Override
    public void render(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float red, float green, float blue, float alpha) {
        super.render(matrices, vertices, light, overlay, red, green, blue, alpha * 0.5f);
    }
}
