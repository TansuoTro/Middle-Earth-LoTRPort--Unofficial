package net.jukoz.me.entity.beasts.great_horn;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

import java.util.Set;

public class GreatHornModel extends SinglePartEntityModel<GreatHornEntity> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart frontHalf;
    private final ModelPart headNeck;
    private final ModelPart topHead;
    private final ModelPart rightAntler;
    private final ModelPart leftAntler;
    private final ModelPart beard;

    public GreatHornModel(ModelPart root) {
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.frontHalf = this.body.getChild("front_half");
        this.headNeck = this.frontHalf.getChild("head_neck");
        this.topHead = this.headNeck.getChild("top_head");
        this.rightAntler = this.topHead.getChild("right_antler");
        this.leftAntler = this.topHead.getChild("left_antler");
        this.beard = this.topHead.getChild("beard");
    }
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData root = modelPartData.addChild("root", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 8.0F, -10.0F));

        ModelPartData body = root.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -8.5F, 12.0F));

        ModelPartData front_half = body.addChild("front_half", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, -13.0F));

        ModelPartData front_body = front_half.addChild("front_body", ModelPartBuilder.create().uv(0, 15).cuboid(-6.5F, -8.0F, -6.5F, 13.0F, 16.0F, 13.0F, new Dilation(0.0F))
                .uv(0, 15).cuboid(-6.5F, -8.0F, -6.5F, 13.0F, 16.0F, 13.0F, new Dilation(0.2F))
                .uv(37, 133).cuboid(-6.5F, 8.4F, -6.5F, 13.0F, 4.0F, 13.0F, new Dilation(0.2F)), ModelTransform.pivot(0.0F, 0.5F, 2.5F));

        ModelPartData saddle = front_body.addChild("saddle", ModelPartBuilder.create().uv(88, 50).cuboid(-7.0F, -1.0F, 2.0F, 14.0F, 22.0F, 6.0F, new Dilation(0.3F))
                .uv(84, 18).cuboid(-7.0F, -2.0F, -6.6F, 14.0F, 23.0F, 8.0F, new Dilation(0.3F)), ModelTransform.pivot(0.0F, -6.5F, 6.5F));

        ModelPartData seat_back_r1 = saddle.addChild("seat_back_r1", ModelPartBuilder.create().uv(49, 78).cuboid(-4.5F, -4.5F, -1.0F, 9.0F, 5.0F, 2.0F, new Dilation(0.3F)), ModelTransform.of(0.0F, 1.0F, 8.4F, -0.2182F, 0.0F, 0.0F));

        ModelPartData frontLeftLeg = front_half.addChild("front_left_leg", ModelPartBuilder.create().uv(0, 108).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F, new Dilation(0.0F))
                .uv(0, 108).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F, new Dilation(0.2F)), ModelTransform.pivot(4.5F, 8.5F, -1.0F));

        ModelPartData frontRightLeg = front_half.addChild("front_right_leg", ModelPartBuilder.create().uv(16, 108).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F, new Dilation(0.0F))
                .uv(16, 108).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F, new Dilation(0.2F)), ModelTransform.pivot(-4.5F, 8.5F, -1.0F));

        ModelPartData head_neck = front_half.addChild("head_neck", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -6.0F, -3.0F));

        ModelPartData neck = head_neck.addChild("neck", ModelPartBuilder.create().uv(40, 0).cuboid(-3.5F, -6.0F, -2.0F, 7.0F, 18.0F, 7.0F, new Dilation(0.0F))
                .uv(40, 0).cuboid(-3.5F, -6.0F, -2.0F, 7.0F, 18.0F, 7.0F, new Dilation(0.0F))
                .uv(53, 53).cuboid(-3.5F, -6.0F, -2.0F, 7.0F, 18.0F, 7.0F, new Dilation(0.3F)), ModelTransform.pivot(0.0F, -1.5F, -2.0F));

        ModelPartData top_head = head_neck.addChild("top_head", ModelPartBuilder.create().uv(96, 0).cuboid(-3.5F, -7.0F, -6.0F, 7.0F, 7.0F, 9.0F, new Dilation(0.0F))
                .uv(96, 0).cuboid(-3.5F, -7.0F, -6.0F, 7.0F, 7.0F, 9.0F, new Dilation(0.2F))
                .uv(51, 35).cuboid(-3.5F, -7.0F, -6.0F, 7.0F, 7.0F, 9.0F, new Dilation(0.3F))
                .uv(69, 0).cuboid(-2.5F, -4.0F, -11.0F, 5.0F, 4.0F, 5.0F, new Dilation(0.0F))
                .uv(69, 0).cuboid(-2.5F, -4.0F, -11.0F, 5.0F, 4.0F, 5.0F, new Dilation(0.2F))
                .uv(52, 26).cuboid(-2.5F, -4.0F, -11.0F, 5.0F, 4.0F, 5.0F, new Dilation(0.3F))
                .uv(86, 99).cuboid(3.9F, -2.0F, -7.0F, 0.0F, 7.0F, 21.0F, new Dilation(0.0F))
                .uv(86, 91).cuboid(-3.9F, -2.0F, -7.0F, 0.0F, 7.0F, 21.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -7.5F, 0.0F));

        ModelPartData earLeft = top_head.addChild("ear_left", ModelPartBuilder.create().uv(0, 0).cuboid(0.0F, -4.0F, -1.0F, 1.0F, 5.0F, 3.0F, new Dilation(0.0F))
                .uv(0, 0).cuboid(0.0F, -4.0F, -1.0F, 1.0F, 5.0F, 3.0F, new Dilation(0.2F)), ModelTransform.of(2.5F, -6.0F, 1.0F, -0.7854F, 0.4363F, 0.0F));

        ModelPartData earRight = top_head.addChild("ear_right", ModelPartBuilder.create().uv(9, 0).cuboid(-0.5F, -4.0F, -1.0F, 1.0F, 5.0F, 3.0F, new Dilation(0.0F))
                .uv(9, 0).cuboid(-0.5F, -4.0F, -1.0F, 1.0F, 5.0F, 3.0F, new Dilation(0.2F)), ModelTransform.of(-3.0F, -6.0F, 1.0F, -0.7854F, -0.4363F, 0.0F));

        ModelPartData right_antler = top_head.addChild("right_antler", ModelPartBuilder.create().uv(34, 120).mirrored().cuboid(-8.0F, 0.0F, -2.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(34, 120).mirrored().cuboid(-8.0F, 0.0F, -2.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.2F)).mirrored(false), ModelTransform.of(-3.0F, -7.0F, 0.0F, 0.1812F, -0.3808F, 0.4252F));

        ModelPartData tip_r_armor_r1 = right_antler.addChild("tip_r_armor_r1", ModelPartBuilder.create().uv(34, 124).mirrored().cuboid(-8.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.2F)).mirrored(false), ModelTransform.of(-7.0F, 1.0F, 0.425F, 0.0F, 1.5708F, 0.0F));

        ModelPartData tip_r_r1 = right_antler.addChild("tip_r_r1", ModelPartBuilder.create().uv(34, 124).mirrored().cuboid(-8.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-7.0F, 1.0F, 0.0F, 0.0F, 1.5708F, 0.0F));

        ModelPartData tip_r_horn_r1 = right_antler.addChild("tip_r_horn_r1", ModelPartBuilder.create().uv(27, 87).mirrored().cuboid(-16.0F, 0.0F, -12.0F, 16.0F, 0.0F, 23.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(-8.0F, 0.0F, 7.0F, 0.0F, 0.0F, 0.6109F));

        ModelPartData base_r_horn_r1 = right_antler.addChild("base_r_horn_r1", ModelPartBuilder.create().uv(27, 112).cuboid(-5.0F, 0.0F, -7.0F, 11.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(-7.0F, 0.0F, -2.0F, -1.1781F, 0.0F, 0.0F));

        ModelPartData left_antler = top_head.addChild("left_antler", ModelPartBuilder.create().uv(54, 120).cuboid(0.0F, 0.0F, -2.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(54, 120).cuboid(0.0F, 0.0F, -2.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.2F)), ModelTransform.of(3.0F, -7.0F, 0.0F, 0.1812F, 0.3808F, -0.4252F));

        ModelPartData base_l_horn_r1 = left_antler.addChild("base_l_horn_r1", ModelPartBuilder.create().uv(50, 112).cuboid(-6.0F, 0.0F, -7.0F, 11.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 0.0F, -2.0F, -1.1781F, 0.0F, 0.0F));

        ModelPartData tip_l_horn_r1 = left_antler.addChild("tip_l_horn_r1", ModelPartBuilder.create().uv(62, 87).cuboid(0.0F, 0.0F, -12.0F, 16.0F, 0.0F, 23.0F, new Dilation(0.0F)), ModelTransform.of(8.0F, 0.0F, 7.0F, 0.0F, 0.0F, -0.6109F));

        ModelPartData tip_l_armor_r1 = left_antler.addChild("tip_l_armor_r1", ModelPartBuilder.create().uv(54, 124).cuboid(0.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.2F)), ModelTransform.of(7.0F, 1.0F, 0.375F, 0.0F, -1.5708F, 0.0F));

        ModelPartData tip_l_r1 = left_antler.addChild("tip_l_r1", ModelPartBuilder.create().uv(54, 124).cuboid(0.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(7.0F, 1.0F, 0.0F, 0.0F, -1.5708F, 0.0F));

        ModelPartData beard = top_head.addChild("beard", ModelPartBuilder.create().uv(69, 3).cuboid(0.0F, -1.0F, -6.0F, 0.0F, 18.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.5F, -5.0F));

        ModelPartData back_body = front_half.addChild("back_body", ModelPartBuilder.create().uv(0, 44).cuboid(-5.5F, -4.55F, -3.6F, 11.0F, 13.0F, 14.0F, new Dilation(0.2F))
                .uv(0, 44).cuboid(-5.5F, -4.5F, -4.0F, 11.0F, 13.0F, 14.0F, new Dilation(0.0F))
                .uv(0, 128).cuboid(-5.5F, 8.825F, -3.6F, 11.0F, 4.0F, 14.0F, new Dilation(0.2F)), ModelTransform.pivot(0.0F, 0.0F, 13.0F));

        ModelPartData tail = back_body.addChild("tail", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -4.5F, 10.0F));

        ModelPartData tail_armor_r1 = tail.addChild("tail_armor_r1", ModelPartBuilder.create().uv(39, 47).cuboid(-1.5F, -2.0F, 0.0F, 3.0F, 2.0F, 4.0F, new Dilation(0.2F))
                .uv(39, 47).cuboid(-1.5F, -2.0F, 0.0F, 3.0F, 2.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 2.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        ModelPartData backLeftLeg = root.addChild("back_left_leg", ModelPartBuilder.create().uv(0, 72).cuboid(-2.25F, -4.25F, -3.75F, 5.0F, 10.0F, 7.0F, new Dilation(0.0F))
                .uv(0, 72).cuboid(-2.25F, -4.25F, -3.75F, 5.0F, 10.0F, 7.0F, new Dilation(0.2F))
                .uv(4, 89).cuboid(-2.25F, 5.75F, 0.25F, 4.0F, 15.0F, 4.0F, new Dilation(0.0F))
                .uv(4, 89).cuboid(-2.25F, 5.75F, 0.25F, 4.0F, 15.0F, 4.0F, new Dilation(0.2F)), ModelTransform.pivot(4.75F, -4.75F, 19.75F));

        ModelPartData backRightLeg = root.addChild("back_right_leg", ModelPartBuilder.create().uv(24, 72).cuboid(-2.75F, -4.25F, -3.75F, 5.0F, 10.0F, 7.0F, new Dilation(0.0F))
                .uv(24, 72).cuboid(-2.75F, -4.25F, -3.75F, 5.0F, 10.0F, 7.0F, new Dilation(0.2F))
                .uv(28, 89).cuboid(-1.75F, 5.75F, 0.25F, 4.0F, 15.0F, 4.0F, new Dilation(0.0F))
                .uv(28, 89).cuboid(-1.75F, 5.75F, 0.25F, 4.0F, 15.0F, 4.0F, new Dilation(0.2F)), ModelTransform.pivot(-4.75F, -4.75F, 19.75F));
        return TexturedModelData.of(modelData, 128, 160);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(GreatHornEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        this.updateAnimation(entity.idleAnimationState, GreatHornAnimations.IDLE, ageInTicks);
        this.updateAnimation(entity.earWigglingAnimationState, GreatHornAnimations.EAR_WIGGLE, ageInTicks);

        if(entity.attackAnimationState.isRunning()) {
            this.updateAnimation(entity.attackAnimationState, GreatHornAnimations.ATTACK, ageInTicks);
        }

        if(entity.bowAnimationState.isRunning()) {
            this.updateAnimation(entity.bowAnimationState, GreatHornAnimations.BOW, ageInTicks);
        } else {
            if(entity.gallopAnimationState.isRunning()) {
                this.animateMovement(GreatHornAnimations.GALLOP, limbSwing, limbSwingAmount, 1.0F, 2.5F);
            } else {
                this.animateMovement(GreatHornAnimations.WALK, limbSwing, limbSwingAmount, 2.75F, 2.5F);
            }
        }

        leftAntler.visible = true;
        rightAntler.visible = true;
        if(entity.isBaby()) {
            leftAntler.visible = false;
            rightAntler.visible = false;
            beard.hidden = true;
        } else if(entity.isSaddled()) {
            leftAntler.visible = false;
            rightAntler.visible = false;
        }
    }

    public void setAngles(GreatHornEntityRenderState state) {
        if (state.entity instanceof GreatHornEntity entity) {
            this.setAngles(entity, state.limbSwingAnimationProgress, state.limbSwingAmplitude,
                    state.age, state.relativeHeadYaw, state.relativeHeadPitch);
        }
    }
}