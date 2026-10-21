package net.jukoz.me.entity.crab;

import net.minecraft.client.model.*;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.PartNames;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.Mth;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class CrabModel extends EntityModel<CrabEntity> {
    private static final String ARM_RIGHT = "bone_arm_right";
    private final ModelPart armRight;
    private static final String CLAW_TOP_RIGHT = "claw_top_right";
    private final ModelPart clawTopRight;
    private static final String CLAW_BOT_RIGHT = "claw_bot_right";
    private final ModelPart clawBotRight;

    private static final String ARM_LEFT = "bone_arm_left";
    private final ModelPart armLeft;

    private static final String CLAW_TOP_LEFT = "claw_top_left";
    private final ModelPart clawTopLeft;
    private static final String CLAW_BOT_LEFT = "claw_bot_left";
    private final ModelPart clawBotLeft;

    private static final String LEG_SWIMMER_LEFT = "bone_leg_swimmer_left";
    private final ModelPart legSwimmerLeft;

    private static final String SWIMMER_LEFT = "swimmer_left";
    private final ModelPart swimmerLeft;
    private static final String LEG_SWIMMER_RIGHT = "bone_leg_swimmer_right";
    private final ModelPart legSwimmerRight;

    private static final String SWIMMER_RIGHT = "swimmer_right";
    private final ModelPart swimmerRight;

    private static final String LEG_RIGHT_FIRST = "bone_leg_right_first";
    private final ModelPart legRightFirst;
    private static final String LEG_RIGHT_SECOND = "bone_leg_right_second";
    private final ModelPart legRightSecond;
    private static final String LEG_RIGHT_THIRD = "bone_leg_right_third";
    private final ModelPart legRightThird;
    private static final String LEG_LEFT_FIRST = "bone_leg_left_first";
    private final ModelPart legLeftFirst;
    private static final String LEG_LEFT_SECOND = "bone_leg_left_second";
    private final ModelPart legLeftSecond;
    private static final String LEG_LEFT_THIRD = "bone_leg_left_third";
    private final ModelPart legLeftThird;
    private static final String EYE_RIGHT = "bone_eye_right";
    private final ModelPart eyeRight;
    private static final String EYE_LEFT = "bone_eye_left";
    private final ModelPart eyeLeft;
    private static final String BODY = "bb_main";
    private final ModelPart body;
    public CrabModel(ModelPart root) {

        this.armRight = root.getChild(ARM_RIGHT);
        this.clawTopRight = this.armRight.getChild(CLAW_TOP_RIGHT);
        this.clawBotRight = this.armRight.getChild(CLAW_BOT_RIGHT);

        this.armLeft = root.getChild(ARM_LEFT);
        this.clawTopLeft = this.armLeft.getChild(CLAW_TOP_LEFT);
        this.clawBotLeft = this.armLeft.getChild(CLAW_BOT_LEFT);

        this.legSwimmerLeft = root.getChild(LEG_SWIMMER_LEFT);
        this.swimmerLeft = this.legSwimmerLeft.getChild(SWIMMER_LEFT);

        this.legSwimmerRight = root.getChild(LEG_SWIMMER_RIGHT);
        this.swimmerRight = this.legSwimmerRight.getChild(SWIMMER_RIGHT);

        this.legRightFirst = root.getChild(LEG_RIGHT_FIRST);
        this.legRightSecond = root.getChild(LEG_RIGHT_SECOND);
        this.legRightThird = root.getChild(LEG_RIGHT_THIRD);
        this.legLeftFirst = root.getChild(LEG_LEFT_FIRST);
        this.legLeftSecond = root.getChild(LEG_LEFT_SECOND);
        this.legLeftThird = root.getChild(LEG_LEFT_THIRD);
        this.eyeRight = root.getChild(EYE_RIGHT);
        this.eyeLeft = root.getChild(EYE_LEFT);
        this.body = root.getChild(BODY);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition armRight = modelPartData
                .addChild(ARM_RIGHT, CubeListBuilder.create().uv(20, 13).cuboid(-2.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.pivot(-3.5F, 20.4F, -3.0F));

        PartDefinition clawTopRight = modelPartData.getChild(ARM_RIGHT)
                .addChild(CLAW_TOP_RIGHT, CubeListBuilder.create().uv(16, 11).cuboid(-3F, 0.5F, -0.5F, 3.0F, 1.0F, 1.0F,  new CubeDeformation(0.0F)),
                PartPose.of(-1.5F, -0.5F, 0,0, 0, 0));

        PartDefinition clawBotRight = modelPartData.getChild(ARM_RIGHT)
                .addChild(CLAW_BOT_RIGHT, CubeListBuilder.create().uv(0, 16).cuboid(-3F, -1.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                        PartPose.of(-1.5F, 0.5F, 0,0, 0, 0));

        PartDefinition armLeft = modelPartData
                .addChild(ARM_LEFT, CubeListBuilder.create().uv(20, 16).cuboid(.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.pivot(3.5F, 20.4F, -3.0F));

        PartDefinition clawTopLeft = modelPartData.getChild(ARM_LEFT)
                .addChild(CLAW_TOP_LEFT, CubeListBuilder.create().uv(16, 9).cuboid(.5F, 0.5F, -0.5F, 3.0F, 1.0F, 1.0F,  new CubeDeformation(0.0F)),
                        PartPose.of(1.5F, -0.5F, 0,0, 0, 0));

        PartDefinition clawBotLeft = modelPartData.getChild(ARM_LEFT)
                .addChild(CLAW_BOT_LEFT, CubeListBuilder.create().uv(8, 16).cuboid(.5F, -1.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                        PartPose.of(1.5F, 0.5F, 0,0, 0, 0));

        PartDefinition bone_leg_swimmer_left = modelPartData.addChild(LEG_SWIMMER_LEFT, CubeListBuilder.create().uv(0, 3).cuboid(-0.5F, -0.5f, 0.5F, 1, 1, 1, new CubeDeformation(0.0F)), PartPose.pivot(2F, 21.8F, 2.8F));

        PartDefinition swimmerLeft = modelPartData.getChild(LEG_SWIMMER_LEFT)
                .addChild(SWIMMER_LEFT, CubeListBuilder.create().uv(8, 16).cuboid(0, -0.5f, 1.5F, 0, 1, 1,  new CubeDeformation(0.0F)),
                PartPose.of(0, 0, 0,0, 0, 0));

        PartDefinition bone_leg_swimmer_right = modelPartData.addChild(LEG_SWIMMER_RIGHT, CubeListBuilder.create().uv(16, 11).cuboid(-0.5F,  -0.5f, 0.5F, 1, 1, 1, new CubeDeformation(0.0F)), PartPose.pivot(-2F, 21.8F, 2.8F));

        PartDefinition swimmerRight = modelPartData.getChild(LEG_SWIMMER_RIGHT)
                .addChild(SWIMMER_RIGHT, CubeListBuilder.create().uv(8, 16).cuboid(0, -0.5f, 1.5F, 0, 1, 1,  new CubeDeformation(0.0F)),
                PartPose.of(0, 0, 0,0, 0, 0));

        PartDefinition bone_leg_right_first = modelPartData.addChild(LEG_RIGHT_FIRST, CubeListBuilder.create().uv(12, 18).cuboid(-1.0F, -0.1F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.pivot(-2.5F, 22.4F, -2.0F));

        PartDefinition bone_leg_right_second = modelPartData.addChild(LEG_RIGHT_SECOND, CubeListBuilder.create().uv(8, 18).cuboid(-1.0F, -0.1F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.pivot(-2.5F, 22.4F, 0.0F));

        PartDefinition bone_leg_right_third = modelPartData.addChild(LEG_RIGHT_THIRD, CubeListBuilder.create().uv(4, 18).cuboid(-1.0F, -0.1F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.pivot(-2.5F, 22.4F, 2.0F));

        PartDefinition bone_leg_left_first = modelPartData.addChild(LEG_LEFT_FIRST, CubeListBuilder.create().uv(0, 9).cuboid(0.0F, -0.1F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.pivot(2.5F, 22.4F, -2.0F));

        PartDefinition bone_leg_left_second = modelPartData.addChild(LEG_LEFT_SECOND, CubeListBuilder.create().uv(16, 16).cuboid(0.0F, -0.1F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.pivot(2.5F, 22.4F, 0.0F));

        PartDefinition bone_leg_left_third = modelPartData.addChild(LEG_LEFT_THIRD, CubeListBuilder.create().uv(0, 18).cuboid(0.0F, -0.1F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.pivot(2.5F, 22.4F, 2.0F));

        PartDefinition bone_eye_right = modelPartData.addChild(EYE_RIGHT, CubeListBuilder.create().uv(21, 0).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.pivot(-1.5F, 19.3F, -2.0F));

        PartDefinition bone_eye_left = modelPartData.addChild(EYE_LEFT, CubeListBuilder.create().uv(20, 18).cuboid(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.pivot(2.0F, 19.3F, -2.0F));

        PartDefinition bb_main = modelPartData.addChild(BODY, CubeListBuilder.create().uv(0, 0).cuboid(-3.5F, -4.7F, -3.5F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .uv(0, 9).cuboid(-2.5F, -2.7F, -2.7F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 24.0F, 0.0F));
        return LayerDefinition.of(modelData, 32, 32);
    }
    @Override
    public void setAngles(CrabEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

        float legAngle = 65F * Mth.RADIANS_PER_DEGREE;
        float armAngle = 30F * Mth.RADIANS_PER_DEGREE;
        float cosVal = (Mth.cos(limbSwing * 2.0F) * 0.6F) * limbSwingAmount;

        legRightFirst.roll = legAngle;
        legRightSecond.roll = legAngle;
        legRightThird.roll = legAngle;

        legLeftFirst.roll = -legAngle;
        legLeftSecond.roll = -legAngle;
        legLeftThird.roll = -legAngle;

        legRightFirst.pitch = -cosVal;
        legRightSecond.pitch = -cosVal * 0.25f;
        legRightThird.pitch = cosVal;

        legLeftFirst.pitch = cosVal;
        legLeftSecond.pitch = cosVal * 0.25f;
        legLeftThird.pitch = -cosVal;

        float armRollMovementModifier = 0.5F;
        armRight.roll = armAngle ;
        armLeft.roll = -armAngle;

        float clawPitchMovement = Mth.abs(Mth.cos(limbSwing * 1.3333F) * 0.5F) * limbSwingAmount;
        clawTopRight.roll = -clawPitchMovement;
        clawBotRight.roll = clawPitchMovement;

        clawTopLeft.roll = clawPitchMovement;
        clawBotLeft.roll = -clawPitchMovement;

        legSwimmerLeft.yaw = clawPitchMovement;
        legSwimmerRight.yaw = -clawPitchMovement;

        swimmerLeft.yaw = clawPitchMovement * 2;
        swimmerRight.yaw = -clawPitchMovement * 2;
    }

    @Override
    public void render(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        armRight.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        armLeft.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        legSwimmerLeft.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        legSwimmerRight.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        legRightFirst.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        legRightSecond.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        legRightThird.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        legLeftFirst.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        legLeftSecond.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        legLeftThird.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        eyeRight.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        eyeLeft.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
