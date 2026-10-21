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
                .addOrReplaceChild(ARM_RIGHT, CubeListBuilder.create().texOffs(20, 13).addBox(-2.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 20.4F, -3.0F));

        PartDefinition clawTopRight = modelPartData.getChild(ARM_RIGHT)
                .addOrReplaceChild(CLAW_TOP_RIGHT, CubeListBuilder.create().texOffs(16, 11).addBox(-3F, 0.5F, -0.5F, 3.0F, 1.0F, 1.0F,  new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-1.5F, -0.5F, 0,0, 0, 0));

        PartDefinition clawBotRight = modelPartData.getChild(ARM_RIGHT)
                .addOrReplaceChild(CLAW_BOT_RIGHT, CubeListBuilder.create().texOffs(0, 16).addBox(-3F, -1.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(-1.5F, 0.5F, 0,0, 0, 0));

        PartDefinition armLeft = modelPartData
                .addOrReplaceChild(ARM_LEFT, CubeListBuilder.create().texOffs(20, 16).addBox(.0F, -0.5F, -0.5F, 2.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(3.5F, 20.4F, -3.0F));

        PartDefinition clawTopLeft = modelPartData.getChild(ARM_LEFT)
                .addOrReplaceChild(CLAW_TOP_LEFT, CubeListBuilder.create().texOffs(16, 9).addBox(.5F, 0.5F, -0.5F, 3.0F, 1.0F, 1.0F,  new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(1.5F, -0.5F, 0,0, 0, 0));

        PartDefinition clawBotLeft = modelPartData.getChild(ARM_LEFT)
                .addOrReplaceChild(CLAW_BOT_LEFT, CubeListBuilder.create().texOffs(8, 16).addBox(.5F, -1.5F, -0.5F, 3.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                        PartPose.offsetAndRotation(1.5F, 0.5F, 0,0, 0, 0));

        PartDefinition bone_leg_swimmer_left = modelPartData.addOrReplaceChild(LEG_SWIMMER_LEFT, CubeListBuilder.create().texOffs(0, 3).addBox(-0.5F, -0.5f, 0.5F, 1, 1, 1, new CubeDeformation(0.0F)), PartPose.offset(2F, 21.8F, 2.8F));

        PartDefinition swimmerLeft = modelPartData.getChild(LEG_SWIMMER_LEFT)
                .addOrReplaceChild(SWIMMER_LEFT, CubeListBuilder.create().texOffs(8, 16).addBox(0, -0.5f, 1.5F, 0, 1, 1,  new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0, 0, 0,0, 0, 0));

        PartDefinition bone_leg_swimmer_right = modelPartData.addOrReplaceChild(LEG_SWIMMER_RIGHT, CubeListBuilder.create().texOffs(16, 11).addBox(-0.5F,  -0.5f, 0.5F, 1, 1, 1, new CubeDeformation(0.0F)), PartPose.offset(-2F, 21.8F, 2.8F));

        PartDefinition swimmerRight = modelPartData.getChild(LEG_SWIMMER_RIGHT)
                .addOrReplaceChild(SWIMMER_RIGHT, CubeListBuilder.create().texOffs(8, 16).addBox(0, -0.5f, 1.5F, 0, 1, 1,  new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(0, 0, 0,0, 0, 0));

        PartDefinition bone_leg_right_first = modelPartData.addOrReplaceChild(LEG_RIGHT_FIRST, CubeListBuilder.create().texOffs(12, 18).addBox(-1.0F, -0.1F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 22.4F, -2.0F));

        PartDefinition bone_leg_right_second = modelPartData.addOrReplaceChild(LEG_RIGHT_SECOND, CubeListBuilder.create().texOffs(8, 18).addBox(-1.0F, -0.1F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 22.4F, 0.0F));

        PartDefinition bone_leg_right_third = modelPartData.addOrReplaceChild(LEG_RIGHT_THIRD, CubeListBuilder.create().texOffs(4, 18).addBox(-1.0F, -0.1F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.5F, 22.4F, 2.0F));

        PartDefinition bone_leg_left_first = modelPartData.addOrReplaceChild(LEG_LEFT_FIRST, CubeListBuilder.create().texOffs(0, 9).addBox(0.0F, -0.1F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 22.4F, -2.0F));

        PartDefinition bone_leg_left_second = modelPartData.addOrReplaceChild(LEG_LEFT_SECOND, CubeListBuilder.create().texOffs(16, 16).addBox(0.0F, -0.1F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 22.4F, 0.0F));

        PartDefinition bone_leg_left_third = modelPartData.addOrReplaceChild(LEG_LEFT_THIRD, CubeListBuilder.create().texOffs(0, 18).addBox(0.0F, -0.1F, -0.5F, 1.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.5F, 22.4F, 2.0F));

        PartDefinition bone_eye_right = modelPartData.addOrReplaceChild(EYE_RIGHT, CubeListBuilder.create().texOffs(21, 0).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.5F, 19.3F, -2.0F));

        PartDefinition bone_eye_left = modelPartData.addOrReplaceChild(EYE_LEFT, CubeListBuilder.create().texOffs(20, 18).addBox(-0.5F, -2.0F, -0.5F, 1.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 19.3F, -2.0F));

        PartDefinition bb_main = modelPartData.addOrReplaceChild(BODY, CubeListBuilder.create().texOffs(0, 0).addBox(-3.5F, -4.7F, -3.5F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                .texOffs(0, 9).addBox(-2.5F, -2.7F, -2.7F, 5.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
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

        legRightFirst.xRot = -cosVal;
        legRightSecond.xRot = -cosVal * 0.25f;
        legRightThird.xRot = cosVal;

        legLeftFirst.xRot = cosVal;
        legLeftSecond.xRot = cosVal * 0.25f;
        legLeftThird.xRot = -cosVal;

        float armRollMovementModifier = 0.5F;
        armRight.roll = armAngle ;
        armLeft.roll = -armAngle;

        float clawPitchMovement = Mth.abs(Mth.cos(limbSwing * 1.3333F) * 0.5F) * limbSwingAmount;
        clawTopRight.roll = -clawPitchMovement;
        clawBotRight.roll = clawPitchMovement;

        clawTopLeft.roll = clawPitchMovement;
        clawBotLeft.roll = -clawPitchMovement;

        legSwimmerLeft.yRot = clawPitchMovement;
        legSwimmerRight.yRot = -clawPitchMovement;

        swimmerLeft.yRot = clawPitchMovement * 2;
        swimmerRight.yRot = -clawPitchMovement * 2;
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
