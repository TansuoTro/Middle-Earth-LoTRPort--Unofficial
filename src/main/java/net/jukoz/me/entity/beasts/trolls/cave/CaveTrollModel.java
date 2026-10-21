package net.jukoz.me.entity.beasts.trolls.cave;

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
public class CaveTrollModel extends HierarchicalModel<CaveTrollEntity> {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart head;
    private final ModelPart boneJaw;
    private final ModelPart legRight;
    private final ModelPart legLeft;
    private final ModelPart armLeft;
    private final ModelPart armRight;

    private static final float ROTATION_SPEED = 0.8f;

    public CaveTrollModel(ModelPart root) {
        this.root = root;
        this.body = root.getChild("body");
        this.head = root.getChild("head");
        this.boneJaw = head.getChild("boneJaw");
        this.legRight = root.getChild("legRight");
        this.legLeft = root.getChild("legLeft");
        this.armLeft = root.getChild("armLeft");
        this.armRight = root.getChild("armRight");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition body = modelPartData.addChild("body", CubeListBuilder.create().uv(66, 33).cuboid(-9.0F, -35.0F, -7.0F, 18.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
                .uv(0, 0).cuboid(-14.0F, -32.0F, -7.0F, 28.0F, 16.0F, 17.0F, new CubeDeformation(0.0F))
                .uv(0, 33).cuboid(-12.0F, -16.0F, -9.0F, 24.0F, 16.0F, 18.0F, new CubeDeformation(0.0F))
                .uv(84, 122).cuboid(-10.0F, -29.0F, -10.0F, 20.0F, 13.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 1.0F, 0.0F));

        PartDefinition head = modelPartData.addChild("head", CubeListBuilder.create().uv(0, 67).cuboid(-7.5F, -7.8F, -16.0F, 15.0F, 8.0F, 15.0F, new CubeDeformation(0.0F))
                .uv(118, 0).cuboid(-6.5F, -7.8F, -18.0F, 13.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(0, 0).cuboid(-2.5F, -2.8F, -17.0F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .uv(116, 107).cuboid(-6.0F, 0.2F, -16.0F, 12.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, -26.0F, -6.0F));

        PartDefinition boneJaw = head.addChild("boneJaw", CubeListBuilder.create().uv(84, 50).cuboid(-6.0F, 0.75F, -13.0F, 12.0F, 3.0F, 14.0F, new CubeDeformation(0.0F))
                .uv(117, 91).cuboid(-5.5F, -1.25F, -12.5F, 11.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 2.45F, -5.0F));

        PartDefinition legLeft = modelPartData.addChild("legLeft", CubeListBuilder.create().uv(106, 67).cuboid(-5.5F, -2.0F, -5.5F, 11.0F, 13.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.pivot(5.5F, 0.0F, 0.0F));

        PartDefinition kneeLeft = legLeft.addChild("kneeLeft", CubeListBuilder.create().uv(0, 118).cuboid(3.6F, -13.0F, -5.0F, 10.0F, 13.0F, 10.0F, new CubeDeformation(0.0F))
                .uv(73, 0).cuboid(3.6F, -5.0F, -7.0F, 10.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.pivot(-8.6F, 24.0F, 0.0F));

        PartDefinition legRight = modelPartData.addChild("legRight", CubeListBuilder.create().uv(84, 95).cuboid(-5.5F, -2.0F, -5.5F, 11.0F, 13.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.pivot(-5.5F, 0.0F, 0.0F));

        PartDefinition kneeRight = legRight.addChild("kneeRight", CubeListBuilder.create().uv(45, 67).cuboid(-5.0F, 19.0F, -7.0F, 10.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(118, 23).cuboid(-5.0F, 11.0F, -5.0F, 10.0F, 13.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition armRight = modelPartData.addChild("armRight", CubeListBuilder.create().uv(60, 67).cuboid(-11.0F, -4.0F, -6.0F, 11.0F, 16.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.pivot(-14.0F, -26.0F, 1.0F));

        PartDefinition forearmRight = armRight.addChild("forearmRight", CubeListBuilder.create().uv(90, 0).cuboid(-11.5F, 9.2F, -5.5F, 9.0F, 23.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.pivot(1.5F, 2.8F, 0.5F));

        PartDefinition armLeft = modelPartData.addChild("armLeft", CubeListBuilder.create().uv(0, 90).cuboid(0.0F, -4.0F, -6.0F, 11.0F, 16.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.pivot(14.0F, -26.0F, 1.0F));

        PartDefinition forearmLeft = armLeft.addChild("forearmLeft", CubeListBuilder.create().uv(46, 95).cuboid(-4.5F, 0.2F, -5.5F, 9.0F, 23.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.pivot(5.5F, 11.8F, 0.5F));
        return LayerDefinition.of(modelData, 256, 256);
    }

    @Override
    public void setAngles(CaveTrollEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(netHeadYaw, headPitch);

        this.animateMovement(TrollAnimations.MOVING, limbSwing, limbSwingAmount, 2f, 2.5f);
        this.updateAnimation(entity.idleAnimationState, TrollAnimations.IDLE, ageInTicks, 1f);
        this.updateAnimation(entity.attackAnimationState, TrollAnimations.ATTACK, ageInTicks, 1.5f);
        this.updateAnimation(entity.chargeAnimationState, TrollAnimations.CHARGING, ageInTicks, 1f);
        this.updateAnimation(entity.throwingAnimationState, TrollAnimations.THROWING, ageInTicks, 1.1f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30.0F, 30.0F);
        headPitch = Mth.clamp(headPitch, -25.0F, 40.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

    @Override
    public void render(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        root.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }
}
