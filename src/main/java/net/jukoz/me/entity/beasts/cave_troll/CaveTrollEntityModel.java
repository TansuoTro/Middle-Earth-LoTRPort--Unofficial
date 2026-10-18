package net.jukoz.me.entity.beasts.cave_troll;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;

public class CaveTrollEntityModel extends SinglePartEntityModel<CaveTrollEntity> {
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

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();

        return TexturedModelData.of(modelData, 256, 256);
    }

    public static ModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body_no_legs = root.addChild("body_no_legs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -15.3333F, -0.5F));

        ModelPartData body_no_limbs = body_no_legs.addChild("body_no_limbs", ModelPartBuilder.create(), ModelTransform.pivot(-0.5F, 0.3333F, 0.5F));

        ModelPartData body = body_no_limbs.addChild("body", ModelPartBuilder.create().uv(156, 199).cuboid(-16.0F, -40.0F, -9.0F, 33.0F, 40.0F, 17.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData Loincloth_Front = body.addChild("Loincloth_Front", ModelPartBuilder.create().uv(190, 122).cuboid(-17.0F, 0.0F, 0.0F, 33.0F, 7.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, 0.0F, -9.0F));

        ModelPartData Loincloth_Back = body_no_limbs.addChild("Loincloth_Back", ModelPartBuilder.create().uv(190, 134).cuboid(-17.0F, 0.0F, 0.0F, 33.0F, 7.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, 0.0F, 8.0F));

        ModelPartData Head = body_no_limbs.addChild("Head", ModelPartBuilder.create().uv(172, 161).cuboid(-8.5F, -8.5F, -8.5F, 17.0F, 17.0F, 17.0F, new Dilation(0.0F)), ModelTransform.pivot(0.5F, -34.5F, -8.5F));

        ModelPartData chain = Head.addChild("chain", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData head_chain = chain.addChild("head_chain", ModelPartBuilder.create().uv(0, 21).cuboid(0.0F, -14.0F, -2.0F, 18.0F, 17.0F, 3.0F, new Dilation(0.01F)), ModelTransform.pivot(-9.0F, 5.5F, -1.5F));

        ModelPartData chain_hanging = chain.addChild("chain_hanging", ModelPartBuilder.create().uv(45, 30).cuboid(-1.5F, -0.5F, 0.0F, 3.0F, 11.0F, 0.0F, new Dilation(0.01F)), ModelTransform.pivot(0.0F, 9.0F, -2.0F));

        ModelPartData jaw = Head.addChild("jaw", ModelPartBuilder.create().uv(143, 144).cuboid(-8.5F, -1.5F, -5.5F, 17.0F, 4.0F, 6.0F, new Dilation(0.01F))
                .uv(201, 150).cuboid(-4.5F, -4.5F, -5.5F, 9.0F, 3.0F, 1.0F, new Dilation(0.01F))
                .uv(166, 162).cuboid(4.5F, -5.5F, -5.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.01F))
                .uv(166, 162).mirrored().cuboid(-6.5F, -5.5F, -5.5F, 2.0F, 4.0F, 2.0F, new Dilation(0.01F)).mirrored(false), ModelTransform.of(0.0F, 6.0F, -4.0F, 0.0436F, 0.0F, 0.0F));

        ModelPartData ArmRight = body_no_legs.addChild("ArmRight", ModelPartBuilder.create().uv(62, 171).cuboid(-10.0F, -3.5F, -5.0F, 10.0F, 41.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(-16.5F, -34.1667F, 0.5F));

        ModelPartData HandRight = ArmRight.addChild("HandRight", ModelPartBuilder.create(), ModelTransform.pivot(-9.0F, 39.5F, -3.0F));

        ModelPartData HandRight_FingerBase_1 = HandRight.addChild("HandRight_FingerBase_1", ModelPartBuilder.create().uv(117, 154).cuboid(-2.5F, 3.0F, -1.5F, 5.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, -1.0F, -0.5F));

        ModelPartData HandRight_Finger_1 = HandRight_FingerBase_1.addChild("HandRight_Finger_1", ModelPartBuilder.create().uv(119, 162).cuboid(-4.0F, -5.9F, -2.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(122, 149).cuboid(1.0F, -1.6F, -1.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, 4.9F, 0.5F));

        ModelPartData HandRight_FingerBase_2 = HandRight.addChild("HandRight_FingerBase_2", ModelPartBuilder.create(), ModelTransform.pivot(48.9F, 0.5F, 8.1F));

        ModelPartData HandRight_Finger_2 = HandRight_FingerBase_2.addChild("HandRight_Finger_2", ModelPartBuilder.create(), ModelTransform.pivot(-45.9F, 3.4F, -4.1F));

        ModelPartData HandRight_FingerBase_3 = HandRight.addChild("HandRight_FingerBase_3", ModelPartBuilder.create().uv(117, 154).cuboid(-2.5F, 3.0F, -1.5F, 5.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, -1.0F, 6.5F));

        ModelPartData HandRight_Finger_3 = HandRight_FingerBase_3.addChild("HandRight_Finger_3", ModelPartBuilder.create().uv(119, 162).cuboid(-4.0F, -5.9F, -2.0F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F))
                .uv(122, 149).cuboid(1.0F, -1.6F, -1.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(1.5F, 4.9F, 0.5F));

        ModelPartData HandRight_Thumb = HandRight.addChild("HandRight_Thumb", ModelPartBuilder.create().uv(95, 163).cuboid(-1.75F, -1.5F, -1.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(97, 157).cuboid(-0.75F, 1.5F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(-0.02F)), ModelTransform.pivot(7.15F, -1.0F, 0.6F));

        ModelPartData ArmLeft = body_no_legs.addChild("ArmLeft", ModelPartBuilder.create().uv(105, 171).cuboid(0.0F, -3.5F, -5.0F, 10.0F, 41.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(16.5F, -34.1667F, 0.5F));

        ModelPartData HandLeft = ArmLeft.addChild("HandLeft", ModelPartBuilder.create(), ModelTransform.pivot(-42.0F, 39.5F, -4.0F));

        ModelPartData HandLeft_FingerBase_1 = HandLeft.addChild("HandLeft_FingerBase_1", ModelPartBuilder.create().uv(119, 162).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(50.5F, -2.0F, 0.5F));

        ModelPartData HandLeft_Finger_1 = HandLeft_FingerBase_1.addChild("HandLeft_Finger_1", ModelPartBuilder.create().uv(117, 154).cuboid(44.0F, -1.9F, -1.0F, 5.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(122, 149).cuboid(43.0F, -1.6F, -0.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-47.5F, 5.9F, -0.5F));

        ModelPartData HandLeft_FingerBase_2 = HandLeft.addChild("HandLeft_FingerBase_2", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 3.5F));

        ModelPartData HandLeft_Finger_2 = HandLeft_FingerBase_2.addChild("HandLeft_Finger_2", ModelPartBuilder.create(), ModelTransform.pivot(3.0F, 3.9F, 0.0F));

        ModelPartData HandLeft_FingerBase_3 = HandLeft.addChild("HandLeft_FingerBase_3", ModelPartBuilder.create().uv(119, 162).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 4.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(50.5F, -2.0F, 7.5F));

        ModelPartData HandLeft_Finger_3 = HandLeft_FingerBase_3.addChild("HandLeft_Finger_3", ModelPartBuilder.create().uv(117, 154).cuboid(44.0F, -1.9F, -1.0F, 5.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(122, 149).cuboid(43.0F, -1.6F, -0.5F, 1.0F, 1.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-47.5F, 5.9F, -0.5F));

        ModelPartData HandLeft_Thumb = HandLeft.addChild("HandLeft_Thumb", ModelPartBuilder.create().uv(95, 163).cuboid(-1.25F, -0.5F, -1.5F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(97, 157).cuboid(-1.25F, 2.5F, -1.0F, 2.0F, 1.0F, 2.0F, new Dilation(-0.02F)), ModelTransform.pivot(43.65F, -2.0F, 1.6F));

        ModelPartData LegRight = root.addChild("LegRight", ModelPartBuilder.create().uv(44, 228).cuboid(-6.5F, -0.5F, -6.5F, 13.0F, 15.0F, 13.0F, new Dilation(0.0F)), ModelTransform.pivot(-9.0F, -14.5F, -1.5F));

        ModelPartData LegLeft = root.addChild("LegLeft", ModelPartBuilder.create().uv(100, 228).cuboid(-6.5F, -0.5F, -6.5F, 13.0F, 15.0F, 13.0F, new Dilation(0.0F)), ModelTransform.pivot(9.0F, -14.5F, -1.5F));

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

    public void setArmAngle(MatrixStack matrices) {
        this.getPart().rotate(matrices);
        this.upperBody.rotate(matrices);
        this.rightArm.rotate(matrices);
    }
}
