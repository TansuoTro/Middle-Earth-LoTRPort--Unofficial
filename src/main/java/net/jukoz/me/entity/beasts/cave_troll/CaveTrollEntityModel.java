package net.jukoz.me.entity.beasts.cave_troll;

import net.minecraft.client.model.*;
import net.minecraft.client.model.HierarchicalModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class CaveTrollEntityModel extends HierarchicalModel<CaveTrollEntity> {
    private final ModelPart rightArm;
    private final ModelPart upperBody;
    private final ModelPart rootChild;
    private final ModelPart chain;

    protected CaveTrollEntityModel(ModelPart root) {
        rootChild = root.getChild("root");
        upperBody = rootChild.getChild("body_no_legs");
        rightArm = upperBody.getChild("ArmRight");
        chain = upperBody.getChild("body_no_limbs").getChild("Head").getChild("chain");

    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();

        return LayerDefinition.of(modelData, 256, 256);
    }

    public static MeshDefinition getModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition root = modelPartData.addChild("root", CubeListBuilder.create(), PartPose.pivot(0.0F, 24.0F, 0.0F));

        PartDefinition body_no_legs = root.addChild("body_no_legs", CubeListBuilder.create(), PartPose.pivot(0.0F, -15.3333F, -0.5F));

        PartDefinition body_no_limbs = body_no_legs.addChild("body_no_limbs", CubeListBuilder.create(), PartPose.pivot(-0.5F, 0.3333F, 0.5F));

        PartDefinition body = body_no_limbs.addChild("body", CubeListBuilder.create().uv(156, 199).cuboid(-16.0F, -40.0F, -9.0F, 33.0F, 40.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition Loincloth_Front = body.addChild("Loincloth_Front", CubeListBuilder.create().uv(190, 122).cuboid(-17.0F, 0.0F, 0.0F, 33.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.pivot(1.0F, 0.0F, -9.0F));

        PartDefinition Loincloth_Back = body_no_limbs.addChild("Loincloth_Back", CubeListBuilder.create().uv(190, 134).cuboid(-17.0F, 0.0F, 0.0F, 33.0F, 7.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.pivot(1.0F, 0.0F, 8.0F));

        PartDefinition Head = body_no_limbs.addChild("Head", CubeListBuilder.create().uv(172, 161).cuboid(-8.5F, -8.5F, -8.5F, 17.0F, 17.0F, 17.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.5F, -34.5F, -8.5F));

        PartDefinition chain = Head.addChild("chain", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        PartDefinition head_chain = chain.addChild("head_chain", CubeListBuilder.create().uv(0, 21).cuboid(0.0F, -14.0F, -2.0F, 18.0F, 17.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.pivot(-9.0F, 5.5F, -1.5F));

        PartDefinition chain_hanging = chain.addChild("chain_hanging", CubeListBuilder.create().uv(45, 30).cuboid(-1.5F, -0.5F, 0.0F, 3.0F, 11.0F, 0.0F, new CubeDeformation(0.01F)), PartPose.pivot(0.0F, 9.0F, -2.0F));

        PartDefinition jaw = Head.addChild("jaw", CubeListBuilder.create().uv(143, 144).cuboid(-8.5F, -1.5F, -5.5F, 17.0F, 4.0F, 6.0F, new CubeDeformation(0.01F))
                .uv(201, 150).cuboid(-4.5F, -4.5F, -5.5F, 9.0F, 3.0F, 1.0F, new CubeDeformation(0.01F))
                .uv(166, 162).cuboid(4.5F, -5.5F, -5.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.01F))
                .uv(166, 162).mirrored().cuboid(-6.5F, -5.5F, -5.5F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.01F)).mirrored(false), PartPose.of(0.0F, 6.0F, -4.0F, 0.0436F, 0.0F, 0.0F));

        PartDefinition ArmRight = body_no_legs.addChild("ArmRight", CubeListBuilder.create().uv(62, 171).cuboid(-10.0F, -3.5F, -5.0F, 10.0F, 41.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.pivot(-16.5F, -34.1667F, 0.5F));

        PartDefinition HandRight = ArmRight.addChild("HandRight", CubeListBuilder.create(), PartPose.pivot(-9.0F, 39.5F, -3.0F));

        PartDefinition HandRight_FingerBase_1 = HandRight.addChild("HandRight_FingerBase_1", CubeListBuilder.create().uv(117, 154).cuboid(-2.5F, 3.0F, -1.5F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.pivot(1.5F, -1.0F, -0.5F));

        PartDefinition HandRight_Finger_1 = HandRight_FingerBase_1.addChild("HandRight_Finger_1", CubeListBuilder.create().uv(119, 162).cuboid(-4.0F, -5.9F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .uv(122, 149).cuboid(1.0F, -1.6F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.pivot(1.5F, 4.9F, 0.5F));

        PartDefinition HandRight_FingerBase_2 = HandRight.addChild("HandRight_FingerBase_2", CubeListBuilder.create(), PartPose.pivot(48.9F, 0.5F, 8.1F));

        PartDefinition HandRight_Finger_2 = HandRight_FingerBase_2.addChild("HandRight_Finger_2", CubeListBuilder.create(), PartPose.pivot(-45.9F, 3.4F, -4.1F));

        PartDefinition HandRight_FingerBase_3 = HandRight.addChild("HandRight_FingerBase_3", CubeListBuilder.create().uv(117, 154).cuboid(-2.5F, 3.0F, -1.5F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.pivot(1.5F, -1.0F, 6.5F));

        PartDefinition HandRight_Finger_3 = HandRight_FingerBase_3.addChild("HandRight_Finger_3", CubeListBuilder.create().uv(119, 162).cuboid(-4.0F, -5.9F, -2.0F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F))
                .uv(122, 149).cuboid(1.0F, -1.6F, -1.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.pivot(1.5F, 4.9F, 0.5F));

        PartDefinition HandRight_Thumb = HandRight.addChild("HandRight_Thumb", CubeListBuilder.create().uv(95, 163).cuboid(-1.75F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .uv(97, 157).cuboid(-0.75F, 1.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.02F)), PartPose.pivot(7.15F, -1.0F, 0.6F));

        PartDefinition ArmLeft = body_no_legs.addChild("ArmLeft", CubeListBuilder.create().uv(105, 171).cuboid(0.0F, -3.5F, -5.0F, 10.0F, 41.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.pivot(16.5F, -34.1667F, 0.5F));

        PartDefinition HandLeft = ArmLeft.addChild("HandLeft", CubeListBuilder.create(), PartPose.pivot(-42.0F, 39.5F, -4.0F));

        PartDefinition HandLeft_FingerBase_1 = HandLeft.addChild("HandLeft_FingerBase_1", CubeListBuilder.create().uv(119, 162).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.pivot(50.5F, -2.0F, 0.5F));

        PartDefinition HandLeft_Finger_1 = HandLeft_FingerBase_1.addChild("HandLeft_Finger_1", CubeListBuilder.create().uv(117, 154).cuboid(44.0F, -1.9F, -1.0F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .uv(122, 149).cuboid(43.0F, -1.6F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.pivot(-47.5F, 5.9F, -0.5F));

        PartDefinition HandLeft_FingerBase_2 = HandLeft.addChild("HandLeft_FingerBase_2", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 3.5F));

        PartDefinition HandLeft_Finger_2 = HandLeft_FingerBase_2.addChild("HandLeft_Finger_2", CubeListBuilder.create(), PartPose.pivot(3.0F, 3.9F, 0.0F));

        PartDefinition HandLeft_FingerBase_3 = HandLeft.addChild("HandLeft_FingerBase_3", CubeListBuilder.create().uv(119, 162).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.pivot(50.5F, -2.0F, 7.5F));

        PartDefinition HandLeft_Finger_3 = HandLeft_FingerBase_3.addChild("HandLeft_Finger_3", CubeListBuilder.create().uv(117, 154).cuboid(44.0F, -1.9F, -1.0F, 5.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .uv(122, 149).cuboid(43.0F, -1.6F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.pivot(-47.5F, 5.9F, -0.5F));

        PartDefinition HandLeft_Thumb = HandLeft.addChild("HandLeft_Thumb", CubeListBuilder.create().uv(95, 163).cuboid(-1.25F, -0.5F, -1.5F, 3.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .uv(97, 157).cuboid(-1.25F, 2.5F, -1.0F, 2.0F, 1.0F, 2.0F, new CubeDeformation(-0.02F)), PartPose.pivot(43.65F, -2.0F, 1.6F));

        PartDefinition LegRight = root.addChild("LegRight", CubeListBuilder.create().uv(44, 228).cuboid(-6.5F, -0.5F, -6.5F, 13.0F, 15.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.pivot(-9.0F, -14.5F, -1.5F));

        PartDefinition LegLeft = root.addChild("LegLeft", CubeListBuilder.create().uv(100, 228).cuboid(-6.5F, -0.5F, -6.5F, 13.0F, 15.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.pivot(9.0F, -14.5F, -1.5F));

        return modelData;
    }

    @Override
    public ModelPart getPart() {
        return this.rootChild;
    }

    @Override
    public void setAngles(CaveTrollEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        chain.visible = entity.isTame();

        if(!entity.isSprinting() && !(entity.getControllingPassenger() != null && entity.getControllingPassenger().isSprinting()) && !entity.isCharging()) {
            this.animateMovement(CaveTrollAnimations.PASSIVE_WALK, limbSwing, limbSwingAmount, 10.0f, 10.0f);
        }
        else {
            this.animateMovement(CaveTrollAnimations.RUN, limbSwing, limbSwingAmount, 2.0f, 2.0f);
        }

        this.updateAnimation(entity.scavengingAnimationState, CaveTrollAnimations.INSPECT, ageInTicks);
        this.updateAnimation(entity.startSittingAnimationState, CaveTrollAnimations.STANDING_TO_SITTING, ageInTicks);
        this.updateAnimation(entity.stopSittingAnimationState, CaveTrollAnimations.STANDING_FROM_SITTING, ageInTicks);
        this.updateAnimation(entity.startSleepingAnimationState, CaveTrollAnimations.SLEEP_LAYING_DOWN, ageInTicks);
        this.updateAnimation(entity.sleepingAnimationState, CaveTrollAnimations.SLEEP_LAYING_DOWN, ageInTicks);
        this.updateAnimation(entity.stopSleepingAnimationState, CaveTrollAnimations.SLEEP_LAYING_DOWN_STANDING_UP, ageInTicks);
        this.updateAnimation(entity.roaringAnimationState, CaveTrollAnimations.SCREAM, ageInTicks);
        this.updateAnimation(entity.smashingAnimationState, CaveTrollAnimations.OVERHAND_SMASH, ageInTicks);
    }

    public void setArmAngle(PoseStack matrices) {
        this.getPart().rotate(matrices);
        this.upperBody.rotate(matrices);
        this.rightArm.rotate(matrices);
    }

    public void setAngles(CaveTrollEntityRenderState state) {
        if (state.entity instanceof CaveTrollEntity entity) {
            this.setAngles(entity, state.limbSwingAnimationProgress, state.limbSwingAmplitude,
                    state.age, state.relativeHeadYaw, state.relativeHeadPitch);
        }
    }
}
