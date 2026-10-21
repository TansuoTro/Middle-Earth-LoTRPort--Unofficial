package net.jukoz.me.entity.beasts.warg;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class WargModel extends HierarchicalModel<WargEntity> {
    private final ModelPart warg;
    private final ModelPart head;
    private final ModelPart mane;

    public WargModel(ModelPart root) {
        this.warg = root.getChild("root");
        ModelPart body = this.warg.getChild(PartNames.BODY);
        ModelPart upperBody = body.getChild("upper_body");
        ModelPart bodyNoLegs = upperBody.getChild("body_no_legs");
        this.head = bodyNoLegs.getChild(PartNames.HEAD);
        this.mane = bodyNoLegs.getChild("body_fur").getChild("mane");
    }

    public static LayerDefinition getTexturedModelData() {
        return LayerDefinition.of(getModelData(), 128, 128);
    }

    public static MeshDefinition getModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addChild("root", CubeListBuilder.create(), PartPose.of(-1.0F, 6.0F, -2.0F, 0.0F, 1.5708F, 0.0F));
        PartDefinition body = root.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(-2.6142F, 2.1138F, 1.5F));
        PartDefinition upperBody = body.addChild("upper_body", CubeListBuilder.create(), PartPose.pivot(-1.3858F, -1.1138F, -1.5F));
        PartDefinition bodyNoLegs = upperBody.addChild("body_no_legs", CubeListBuilder.create(), PartPose.pivot(7.5F, -0.25F, 0.5F));
        PartDefinition bodyFur = bodyNoLegs.addChild("body_fur", CubeListBuilder.create().uv(0, 0).cuboid(-6.5F, -7.5F, -6.0F, 14.0F, 15.0F, 13.0F, new CubeDeformation(-0.3F)), PartPose.pivot(0.0F, 0.0F, 0.0F));
        bodyFur.addChild("mane", CubeListBuilder.create().uv(54, 15).cuboid(-9.5F, -5.0F, 0.0F, 20.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.pivot(-2.0F, -6.75F, 0.5F));
        bodyNoLegs.addChild("main_body", CubeListBuilder.create().uv(0, 49).cuboid(-0.5F, -6.0F, -5.0F, 12.0F, 12.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.pivot(-5.3F, 0.25F, 0.0F));

        PartDefinition head = bodyNoLegs.addChild(PartNames.HEAD, CubeListBuilder.create()
                .uv(74, 0).cuboid(-1.5F, -3.25F, -5.0F, 10.0F, 5.0F, 10.0F, new CubeDeformation(0.0F))
                .uv(74, 0).cuboid(-1.5F, -3.25F, -5.0F, 10.0F, 5.0F, 10.0F, new CubeDeformation(0.025F))
                .uv(54, 31).cuboid(-2.1142F, 1.6138F, -4.0F, 10.0F, 3.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.pivot(7.25F, -2.0F, 0.5F));
        head.addChild("snout", CubeListBuilder.create()
                .uv(54, 23).cuboid(-9.5F, 0.5F, -2.5F, 15.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .uv(46, 82).cuboid(-0.644F, 4.522F, -2.5F, 6.0F, 1.0F, 4.0F, new CubeDeformation(0.05F)), PartPose.pivot(8.0F, -1.5F, 0.5F));
        head.addChild("ear_right", CubeListBuilder.create(), PartPose.of(4.0F, -2.25F, -4.478F, 0.0F, -0.8727F, 0.0F));
        head.addChild("ear_left", CubeListBuilder.create(), PartPose.of(4.0F, -2.25F, 4.522F, 0.0F, 0.8727F, 0.0F));
        PartDefinition earRight = head.getChild("ear_right");
        earRight.addChild("earR_r1", CubeListBuilder.create().uv(82, 72).cuboid(0.2802F, -4.8619F, -1.032F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(-1.0F, 1.0F, -0.022F, 0.0F, 0.0F, -1.0036F));
        PartDefinition earLeft = head.getChild("ear_left");
        earLeft.addChild("earL_r1", CubeListBuilder.create().uv(82, 79).cuboid(0.2802F, -4.8619F, 0.01F, 3.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(-1.0F, 1.0F, 0.0F, 0.0F, 0.0F, -1.0036F));
        PartDefinition jaw = head.addChild("jaw", CubeListBuilder.create()
                .uv(54, 42).cuboid(-5.5F, -0.25F, -2.0F, 15.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .uv(46, 60).cuboid(-5.564F, -1.75F, -2.0F, 15.0F, 2.0F, 4.0F, new CubeDeformation(-0.09F)), PartPose.of(3.75F, 3.25F, 0.0F, 0.0F, 0.0F, 0.2618F));
        jaw.addChild("tongue", CubeListBuilder.create().uv(8, 78).cuboid(-1.8F, 0.1F, -1.5F, 9.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.of(2.0F, -0.25F, 0.0F, 0.0F, 0.0F, -0.0349F));
        head.addChild("headmane", CubeListBuilder.create().uv(68, 66).cuboid(-7.5F, -5.0F, 0.0F, 15.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.pivot(-1.0F, -4.25F, 0.0F));

        PartDefinition waitTail = bodyNoLegs.addChild("wait_tail", CubeListBuilder.create().uv(0, 28).cuboid(-0.3F, -2.0F, -5.5F, 16.0F, 10.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.pivot(-20.5F, -3.75F, 0.5F));
        waitTail.addChild("tail", CubeListBuilder.create(), PartPose.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.829F));
        PartDefinition tail = waitTail.getChild("tail");
        tail.addChild("bone fluff_r1", CubeListBuilder.create()
                .uv(0, 108).cuboid(-13.0F, -5.0F, -0.75F, 15.0F, 2.0F, 1.0F, new CubeDeformation(0.4F))
                .uv(0, 72).cuboid(-13.0F, -5.0F, -0.75F, 15.0F, 2.0F, 1.0F, new CubeDeformation(0.0F))
                .uv(46, 49).cuboid(-18.0F, -6.0F, -2.75F, 20.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.of(-1.5979F, 4.1769F, 0.25F, 0.0F, 0.0F, 0.1745F));

        upperBody.addChild("right_front_leg", CubeListBuilder.create().uv(30, 72).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.pivot(9.0F, 4.0F, -2.5F));
        upperBody.addChild("left_front_leg", CubeListBuilder.create().uv(30, 89).cuboid(-2.0F, 0.0F, -2.5F, 4.0F, 13.0F, 4.0F, new CubeDeformation(0.01F)), PartPose.pivot(9.0F, 4.0F, 5.0F));

        PartDefinition backBody = body.addChild("back_body", CubeListBuilder.create(), PartPose.pivot(2.3142F, -2.1138F, -0.5F));
        PartDefinition leftBackLeg = backBody.addChild("left_back_leg", CubeListBuilder.create().uv(46, 66).cuboid(-3.5F, -1.0F, -1.0F, 7.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.pivot(-14.7F, -2.0F, 3.5F));
        leftBackLeg.addChild("left_back_foot", CubeListBuilder.create().uv(0, 78).cuboid(-2.0F, -2.0F, -1.5F, 4.0F, 12.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.pivot(-3.2F, 10.0F, 0.5F));
        PartDefinition rightBackLeg = backBody.addChild("right_back_leg", CubeListBuilder.create().uv(46, 66).cuboid(-4.5F, -1.0F, -4.0F, 7.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.pivot(-13.7F, -2.0F, -2.5F));
        rightBackLeg.addChild("right_back_foot", CubeListBuilder.create().uv(0, 93).cuboid(-2.0F, -2.0F, -1.5F, 4.0F, 12.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.pivot(-4.2F, 10.0F, -1.5F));

        return modelData;
    }

    public static MeshDefinition getEmptyModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addChild("root", CubeListBuilder.create(), PartPose.of(-1.0F, 6.0F, -2.0F, 0.0F, 1.5708F, 0.0F));
        PartDefinition body = root.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(-2.6142F, 2.1138F, 1.5F));
        PartDefinition upperBody = body.addChild("upper_body", CubeListBuilder.create(), PartPose.pivot(-1.3858F, -1.1138F, -1.5F));
        PartDefinition bodyNoLegs = upperBody.addChild("body_no_legs", CubeListBuilder.create(), PartPose.pivot(7.5F, -0.25F, 0.5F));
        PartDefinition bodyFur = bodyNoLegs.addChild("body_fur", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        bodyFur.addChild("mane", CubeListBuilder.create(), PartPose.pivot(-2.0F, -6.75F, 0.5F));
        bodyNoLegs.addChild("main_body", CubeListBuilder.create(), PartPose.pivot(-5.3F, 0.25F, 0.0F));

        PartDefinition head = bodyNoLegs.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(7.25F, -2.0F, 0.5F));
        head.addChild("snout", CubeListBuilder.create(), PartPose.pivot(8.0F, -1.5F, 0.5F));
        head.addChild("ear_right", CubeListBuilder.create(), PartPose.of(4.0F, -2.25F, -4.478F, 0.0F, -0.8727F, 0.0F));
        head.addChild("ear_left", CubeListBuilder.create(), PartPose.of(4.0F, -2.25F, 4.522F, 0.0F, 0.8727F, 0.0F));
        PartDefinition jaw = head.addChild("jaw", CubeListBuilder.create(), PartPose.of(3.75F, 3.25F, 0.0F, 0.0F, 0.0F, 0.2618F));
        jaw.addChild("tongue", CubeListBuilder.create(), PartPose.of(2.0F, -0.25F, 0.0F, 0.0F, 0.0F, -0.0349F));
        head.addChild("headmane", CubeListBuilder.create(), PartPose.pivot(-1.0F, -4.25F, 0.0F));

        PartDefinition waitTail = bodyNoLegs.addChild("wait_tail", CubeListBuilder.create(), PartPose.pivot(-20.5F, -3.75F, 0.5F));
        waitTail.addChild("tail", CubeListBuilder.create(), PartPose.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, -0.829F));

        upperBody.addChild("right_front_leg", CubeListBuilder.create(), PartPose.pivot(9.0F, 4.0F, -2.5F));
        upperBody.addChild("left_front_leg", CubeListBuilder.create(), PartPose.pivot(9.0F, 4.0F, 5.0F));

        PartDefinition backBody = body.addChild("back_body", CubeListBuilder.create(), PartPose.pivot(2.3142F, -2.1138F, -0.5F));
        PartDefinition leftBackLeg = backBody.addChild("left_back_leg", CubeListBuilder.create(), PartPose.pivot(-14.7F, -2.0F, 3.5F));
        leftBackLeg.addChild("left_back_foot", CubeListBuilder.create(), PartPose.pivot(-3.2F, 10.0F, 0.5F));
        PartDefinition rightBackLeg = backBody.addChild("right_back_leg", CubeListBuilder.create(), PartPose.pivot(-13.7F, -2.0F, -2.5F));
        rightBackLeg.addChild("right_back_foot", CubeListBuilder.create(), PartPose.pivot(-4.2F, 10.0F, -1.5F));

        return modelData;
    }

    @Override
    public ModelPart getPart() {
        return this.warg;
    }

    @Override
    public void setAngles(WargEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        boolean running = entity.isRunning() || entity.isSprinting()
                || (entity.getControllingPassenger() != null && entity.getControllingPassenger().isSprinting())
                || entity.isCharging();

        if(!running) {
            float speed = 3.5f;
            if(entity.getControllingPassenger() != null) {
                speed = 2f;
            }
            this.animateMovement(WargAnimations.WALK, limbSwing, limbSwingAmount, speed, 2f);
        }
        else {
            this.animateMovement(WargAnimations.RUN, limbSwing, limbSwingAmount, 1.4f, 1.2f);
        }

        this.updateAnimation(entity.idleAnimationState, WargAnimations.IDLE, ageInTicks);
        this.updateAnimation(entity.attackAnimationState, WargAnimations.BITE, ageInTicks);
        this.updateAnimation(entity.startSittingAnimationState, WargAnimations.STANDING_TO_SITTING, ageInTicks);
        this.updateAnimation(entity.stopSittingAnimationState, WargAnimations.SITTING_TO_STANDING, ageInTicks);
        this.updateAnimation(entity.sittingAnimationState, WargAnimations.SITTING, ageInTicks);
    }

    public void setAngles(WargEntityRenderState state) {
        if (state.entity instanceof WargEntity entity) {
            this.setAngles(entity, state.limbSwingAnimationProgress, state.limbSwingAmplitude,
                    state.age, state.relativeHeadYaw, state.relativeHeadPitch);
        }
    }
}
