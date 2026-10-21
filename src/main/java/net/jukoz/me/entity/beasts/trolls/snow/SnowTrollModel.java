package net.jukoz.me.entity.beasts.trolls.snow;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.jukoz.me.entity.beasts.trolls.TrollAnimations;
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
import net.minecraft.util.profiling.jfr.Environment;

@Environment(value= EnvType.CLIENT)
public class SnowTrollModel extends HierarchicalModel<SnowTrollEntity> {
    private final ModelPart troll;
    private final ModelPart head;
    public SnowTrollModel(ModelPart root) {
        this.troll = root.getChild("roots");
        this.head = troll.getChild("torso").getChild("head");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition roots = modelPartData.addChild("roots", CubeListBuilder.create(), PartPose.pivot(0.0F, 26.0F, 0.0F));

        PartDefinition legs = roots.addChild("legs", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition right_leg = legs.addChild("right_leg", CubeListBuilder.create().uv(126, 98).mirrored().cuboid(-4.0F, 1.0F, -5.0F, 10.0F, 19.0F, 10.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(118, 69).cuboid(-4.0F, 1.0F, -5.0F, 10.0F, 19.0F, 10.0F, new CubeDeformation(0.2F)), PartPose.pivot(-8.0F, -22.0F, 5.0F));

        PartDefinition left_leg = legs.addChild("left_leg", CubeListBuilder.create().uv(122, 0).cuboid(-6.0F, 0.9848F, -4.8264F, 10.0F, 19.0F, 10.0F, new CubeDeformation(0.0F))
                .uv(100, 40).cuboid(-6.0F, 0.9848F, -4.8264F, 10.0F, 19.0F, 10.0F, new CubeDeformation(0.2F)), PartPose.pivot(7.0F, -22.0F, 5.0F));

        PartDefinition torso = roots.addChild("torso", CubeListBuilder.create().uv(0, 41).cuboid(-12.5F, -28.5F, -6.0865F, 25.0F, 29.0F, 12.0F, new CubeDeformation(0.0F))
                .uv(0, 0).cuboid(-12.5F, -28.5F, -6.0865F, 25.0F, 29.0F, 12.0F, new CubeDeformation(0.2F))
                .uv(74, 0).cuboid(-5.5F, -29.5F, -2.0905F, 11.0F, 27.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.of(-0.5F, -21.5F, 5.0865F, 1.0472F, 0.0F, 0.0F));

        PartDefinition head = torso.addChild("head", CubeListBuilder.create().uv(0, 82).cuboid(-7.0F, -11.0F, -12.0F, 13.0F, 14.0F, 12.0F, new CubeDeformation(0.0F))
                .uv(56, 64).cuboid(-7.0F, -11.0F, -12.0F, 13.0F, 24.0F, 18.0F, new CubeDeformation(0.2F)), PartPose.of(0.5F, -24.5F, -1.0865F, -1.0036F, 0.0F, 0.0F));

        PartDefinition right_ear = head.addChild("right_ear", CubeListBuilder.create().uv(0, 5).mirrored().cuboid(-4.0F, -2.0F, -0.5F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.of(-7.0F, -5.0F, -6.5F, 0.0F, 0.829F, 0.0F));

        PartDefinition left_ear = head.addChild("left_ear", CubeListBuilder.create().uv(0, 0).cuboid(0.0F, -2.0F, -0.5F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(6.0F, -5.0F, -6.5F, 0.0F, -0.7854F, 0.0F));

        PartDefinition nose = head.addChild("nose", CubeListBuilder.create().uv(62, 0).cuboid(-2.5F, -1.5F, -3.0F, 5.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.of(-0.5F, -4.5F, -10.0F, -0.3054F, 0.0F, 0.0F));

        PartDefinition jaw = head.addChild("jaw", CubeListBuilder.create().uv(66, 48).cuboid(-4.5F, -3.3653F, -4.8333F, 9.0F, 2.0F, 0.0F, new CubeDeformation(0.3F))
                .uv(62, 41).cuboid(-4.5F, -0.7693F, -4.8333F, 9.0F, 2.0F, 4.0F, new CubeDeformation(0.3F))
                .uv(126, 127).cuboid(-4.5F, -2.8653F, -4.8333F, 9.0F, 10.0F, 7.0F, new CubeDeformation(0.5F)), PartPose.pivot(-0.5F, 3.8653F, -7.1667F));

        PartDefinition arms = torso.addChild("arms", CubeListBuilder.create(), PartPose.of(0.5F, 7.5F, -20.0865F, -1.0472F, 0.0F, 0.0F));

        PartDefinition right_arm = arms.addChild("right_arm", CubeListBuilder.create().uv(70, 106).cuboid(-6.0F, -3.0F, -3.0F, 6.0F, 36.0F, 8.0F, new CubeDeformation(0.2F))
                .uv(0, 108).mirrored().cuboid(-6.0F, -3.0F, -3.0F, 6.0F, 36.0F, 8.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.of(-13.0F, -32.0F, -18.0F, -0.3927F, 0.0F, 0.0F));

        PartDefinition left_arm = arms.addChild("left_arm", CubeListBuilder.create().uv(42, 106).cuboid(-1.0F, -3.0F, -2.0F, 6.0F, 36.0F, 8.0F, new CubeDeformation(0.2F))
                .uv(98, 106).cuboid(-1.0F, -3.0F, -2.0F, 6.0F, 36.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.of(13.0F, -32.0F, -17.0F, -0.4363F, 0.0F, 0.0F));

        PartDefinition bone = torso.addChild("bone", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        return LayerDefinition.of(modelData, 256, 256);
    }

    @Override
    public void setAngles(SnowTrollEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(netHeadYaw, headPitch);

        this.animateMovement(SnowTrollAnimations.WALKING, limbSwing, limbSwingAmount, 1f, 1f);
        //this.updateAnimation(entity.idleAnimationState, SnowTrollAnimations.IDLE, ageInTicks, 1f);
        this.updateAnimation(entity.attackAnimationState, SnowTrollAnimations.ATTACK, ageInTicks, 1.4f);
        this.updateAnimation(entity.chargeAnimationState, SnowTrollAnimations.CHARGING, ageInTicks, 1f);
        this.updateAnimation(entity.throwingAnimationState, SnowTrollAnimations.THROWING, ageInTicks, 1.2f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30.0F, 30.0F);
        headPitch = Mth.clamp(headPitch, -25.0F, 40.0F) + -60.0F;

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

    @Override
    public void render(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        troll.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return this.troll;
    }

}
