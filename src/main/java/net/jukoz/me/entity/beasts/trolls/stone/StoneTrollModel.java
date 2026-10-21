package net.jukoz.me.entity.beasts.trolls.stone;

import net.minecraft.client.model.*;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.Mth;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class StoneTrollModel extends HierarchicalModel<StoneTrollEntity> {
    private final ModelPart r;
    private final ModelPart head;
    public StoneTrollModel(ModelPart root) {
        this.r = root.getChild("r");
        this.head = r.getChild("upperbody").getChild("upperbodynoarms").getChild("head");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition r = modelPartData.addChild("r", CubeListBuilder.create(), PartPose.pivot(0.0F, 26.0F, 0.0F));

        PartDefinition upperbody = r.addChild("upperbody", CubeListBuilder.create(), PartPose.pivot(0.0F, -20.0F, 1.0F));

        PartDefinition arms = upperbody.addChild("arms", CubeListBuilder.create(), PartPose.pivot(0.0F, 20.0F, -1.0F));

        PartDefinition right_arm = arms.addChild("right_arm", CubeListBuilder.create().uv(28, 82).cuboid(-5.0F, -2.0F, -4.0F, 6.0F, 36.0F, 8.0F, new CubeDeformation(0.2F))
                .uv(84, 96).cuboid(-5.0F, -2.0F, -4.0F, 6.0F, 36.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.pivot(-14.0F, -46.0F, 2.0F));

        PartDefinition left_arm = arms.addChild("left_arm", CubeListBuilder.create().uv(0, 82).cuboid(-3.0F, -2.0F, -4.0F, 6.0F, 36.0F, 8.0F, new CubeDeformation(0.2F))
                .uv(56, 96).mirrored().cuboid(-3.0F, -2.0F, -4.0F, 6.0F, 36.0F, 8.0F, new CubeDeformation(0.0F)).mirrored(), PartPose.pivot(15.0F, -46.0F, 2.0F));

        PartDefinition upperbodynoarms = upperbody.addChild("upperbodynoarms", CubeListBuilder.create(), PartPose.pivot(0.0F, -2.0F, -1.0F));

        PartDefinition head = upperbodynoarms.addChild("head", CubeListBuilder.create().uv(74, 0).cuboid(-6.5F, -9.6306F, -6.7491F, 13.0F, 14.0F, 12.0F, new CubeDeformation(0.0F))
                .uv(62, 70).cuboid(-6.5F, -9.6306F, -6.7491F, 13.0F, 14.0F, 12.0F, new CubeDeformation(0.2F))
                .uv(0, 0).mirrored().cuboid(6.4F, -5.6306F, -1.7491F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(0, 5).cuboid(-10.4F, -5.6306F, -1.7491F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.pivot(-0.5F, -27.3694F, -1.2509F));

        PartDefinition nose = head.addChild("nose", CubeListBuilder.create().uv(62, 0).cuboid(-2.5F, -1.5F, -3.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, -3.1306F, -4.7491F, -0.3054F, 0.0F, 0.0F));

        PartDefinition jaw = head.addChild("jaw", CubeListBuilder.create().uv(126, 29).cuboid(-4.5F, -2.0F, -4.0F, 9.0F, 4.0F, 4.0F, new CubeDeformation(0.3F)), PartPose.pivot(0.0F, 3.3694F, -2.7491F));

        PartDefinition hair = head.addChild("hair", CubeListBuilder.create().uv(192, 158).cuboid(-33.0F, -55.2728F, -0.4462F, 13.0F, 19.0F, 12.0F, new CubeDeformation(0.1F)), PartPose.pivot(26.5F, 51.3694F, -5.7491F));

        PartDefinition hair1 = hair.addChild("hair1", CubeListBuilder.create().uv(204, 105).cuboid(-6.3F, -22.2272F, -5.9945F, 13.0F, 19.0F, 12.0F, new CubeDeformation(0.1F)), PartPose.of(-26.7F, -37.5249F, 16.9945F, 0.5236F, 0.0F, 0.0F));

        PartDefinition torso = upperbodynoarms.addChild("torso", CubeListBuilder.create().uv(0, 41).cuboid(-13.0F, -50.0F, -4.0F, 25.0F, 29.0F, 12.0F, new CubeDeformation(0.0F))
                .uv(0, 0).cuboid(-13.0F, -50.0F, -4.0F, 25.0F, 29.0F, 12.0F, new CubeDeformation(0.2F)), PartPose.pivot(0.0F, 22.0F, 0.0F));

        PartDefinition frontloin = torso.addChild("frontloin", CubeListBuilder.create().uv(74, 48).cuboid(-12.5F, -1.5F, -0.5F, 25.0F, 21.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.pivot(-0.5F, -26.5F, -3.504F));

        PartDefinition backloin = torso.addChild("backloin", CubeListBuilder.create().uv(74, 26).cuboid(-12.5F, -1.5F, -0.5F, 25.0F, 21.0F, 1.0F, new CubeDeformation(0.3F)), PartPose.pivot(-0.5F, -26.5F, 7.85F));

        PartDefinition bodynoarmswithlegs = upperbodynoarms.addChild("bodynoarmswithlegs", CubeListBuilder.create(), PartPose.pivot(0.0F, -11.0F, 0.0F));

        PartDefinition legs = r.addChild("legs", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition right_leg = legs.addChild("right_leg", CubeListBuilder.create().uv(126, 0).cuboid(-5.0F, 0.5F, -5.0F, 10.0F, 19.0F, 10.0F, new CubeDeformation(0.0F))
                .uv(112, 99).cuboid(-5.0F, 0.5F, -5.0F, 10.0F, 19.0F, 10.0F, new CubeDeformation(0.2F)), PartPose.pivot(-7.0F, -21.5F, 2.0F));

        PartDefinition left_leg = legs.addChild("left_leg", CubeListBuilder.create().uv(0, 126).cuboid(-5.0F, 0.5F, -5.0F, 10.0F, 19.0F, 10.0F, new CubeDeformation(0.0F))
                .uv(112, 70).cuboid(-5.0F, 0.5F, -5.0F, 10.0F, 19.0F, 10.0F, new CubeDeformation(0.2F)), PartPose.pivot(6.0F, -21.5F, 2.0F));

        PartDefinition club = modelPartData.addChild("club", CubeListBuilder.create(), PartPose.of(-15.3115F, 10.3091F, -12.2077F, -0.1309F, 0.0F, 0.0873F));

        PartDefinition cube_r1 = club.addChild("cube_r1", CubeListBuilder.create().uv(16, 158).mirrored().cuboid(-4.5F, -4.5F, -22.0F, 9.0F, 9.0F, 22.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(17, 194).cuboid(-3.5F, -3.5F, 0.0F, 7.0F, 7.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, 0.0F, 0.0F, 0.1993F, 0.0145F, -0.0979F));
        return LayerDefinition.of(modelData, 256, 256);
    }
    @Override
    public void render(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        r.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return r;
    }

    @Override
    public void setAngles(StoneTrollEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(headYaw, headPitch);

        this.animateMovement(StoneTrollAnimations.WALK, limbAngle, limbDistance, 3f, 3f);
        this.updateAnimation(entity.attackAnimationState, StoneTrollAnimations.ATTACK, animationProgress, 1.3f);
        this.updateAnimation(entity.chargeAnimationState, StoneTrollAnimations.CHARGE, animationProgress, 1f);
        this.updateAnimation(entity.throwingAnimationState, StoneTrollAnimations.THROW, animationProgress, 1f);
        this.updateAnimation(entity.sittingAnimationState, StoneTrollAnimations.SITTING, animationProgress, 1f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30.0F, 30.0F);
        headPitch = Mth.clamp(headPitch, -25.0F, 40.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }
}
