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

        root.addChild(PartNames.BODY, CubeListBuilder.create().uv(8, 8)
                        .cuboid(-1.0f, 0f, -1.0f, 2.0f, 2.0f, 2.0f, dilation),
                PartPose.pivot(0.0f, 0.0f, 0.0f));

        root.addChild(PartNames.HEAD, CubeListBuilder.create().uv(0, 0)
                        .cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation),
                PartPose.pivot(0.0f, 0.0f, 0.0f));

        PartDefinition leftArm = root.addChild(PartNames.LEFT_ARM, CubeListBuilder.create().uv(32, 48)
                        .cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                PartPose.pivot(LEFT_ARM_PIVOT_X, 2.5f, 0.0f));
        PartDefinition rightArm = root.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create().uv(40, 16)
                        .cuboid(-3f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                PartPose.pivot(RIGHT_ARM_PIVOT_X, 2.5f, 0.0f));

        rightArm.addChild(RIGHT_SLEEVE, CubeListBuilder.create().uv(40, 32).cuboid(0f, 0f, 0f, 3.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                PartPose.pivot(-2f, -2f, -2f));
        leftArm.addChild(LEFT_SLEEVE, CubeListBuilder.create().uv(48, 48).cuboid(0f, 0f, 0f, 3.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                PartPose.pivot(-1f, -2f, -2f));

        root.addChild(PartNames.BODY, CubeListBuilder.create().uv(16, 16)
                        .cuboid(-4.0f, 0.0f, -2f, 8.0f, 12.0f, 4.0f, dilation),
                PartPose.pivot(0.0f, 0f, 0.0f));

        root.getChild(PartNames.BODY).addChild(PartNames.JACKET, CubeListBuilder.create().uv(16, 32)
                .cuboid(-4.0f, 0.0f, 0f, 8.0f, 12.0f, 4.0f, dilation.add(0.25f)), PartPose.NONE);

        root.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create().uv(0, 16)
                        .cuboid(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                PartPose.pivot(-LEG_PIVOT_X, 0.0f, 0.0f));
        root.addChild(PartNames.LEFT_LEG, CubeListBuilder.create().uv(0, 16).mirrored()
                        .cuboid(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                PartPose.pivot(LEG_PIVOT_X, 0.0f, 0.0f));

        root.getChild(PartNames.LEFT_LEG).addChild(LEFT_PANTS, CubeListBuilder.create().uv(0, 48)
                        .cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                PartPose.pivot(0, 0.0f, 0.0f));
        root.getChild(PartNames.RIGHT_LEG).addChild(RIGHT_PANTS, CubeListBuilder.create().uv(0, 32)
                        .cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                PartPose.pivot(0, 0.0f, 0.0f));

        root.addChild(PartNames.HAT, CubeListBuilder.create().uv(32, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation.add(0.5f)),
                PartPose.pivot(0.0f, 0.0f, 0.0f));                // 54

        return LayerDefinition.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T mobEntity, float limbAngle, float limbDistance, float ageInTicks, float i, float j) {
        super.setAngles(mobEntity, limbAngle, limbDistance, ageInTicks, i, j);
        ItemStack itemStack = (mobEntity).getMainHandStack();
        NazgulEntity.State state = ((NazgulEntity)mobEntity).getState();

        if(state == NazgulEntity.State.ATTACKING) {
            this.leftArm.pitch = Mth.cos(limbAngle * 0.6662f) * 2.0f * limbDistance * 0.5f;
            this.leftArm.yaw = 0.0f;
            this.leftArm.roll = 0.0f;

            this.rightArm.pitch = -0.9f + Mth.cos(limbAngle * 0.6662f + (float)Math.PI) * 2.0f * limbDistance * 0.5f;
            this.rightArm.yaw = 0.0f;
            this.rightArm.roll = 0.0f;
        } else if(state == NazgulEntity.State.ON_FIRE || state == NazgulEntity.State.FADING) {
            float limbCosTime = (float) (1 + 0.25f * Math.cos(limbAngle));
            this.rightArm.pitch = -3.1f + Mth.cos(ageInTicks * 0.4f + (float)Math.PI) * 0.3f * limbCosTime;
            this.rightArm.roll = -0.6f + Mth.sin(ageInTicks * 0.666f + (float)Math.PI) * 0.2f;

            this.leftArm.pitch = -3.1f + Mth.sin(ageInTicks * 0.4f + (float)Math.PI) * 0.3f * limbCosTime;
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
