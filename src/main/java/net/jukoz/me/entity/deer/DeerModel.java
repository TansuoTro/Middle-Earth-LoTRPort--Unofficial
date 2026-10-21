package net.jukoz.me.entity.deer;

import net.jukoz.me.entity.beasts.trolls.TrollAnimations;
import net.minecraft.client.model.*;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class DeerModel extends HierarchicalModel<DeerEntity> {
    private final ModelPart deer;
    public DeerModel(ModelPart root) {
        this.deer = root.getChild("deer");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition deer = modelPartData.addChild("deer", CubeListBuilder.create(), PartPose.pivot(0.0F, 24.0F, 0.0F));

        PartDefinition upperBody = deer.addChild("upper_body", CubeListBuilder.create(), PartPose.pivot(0.0F, -15.5F, -3.5F));

        upperBody.addChild("chest", CubeListBuilder.create().uv(35, 15).cuboid(-5.5F, -5.5F, -8.5F, 11.0F, 11.0F, 9.0F, new CubeDeformation(0.0F))
                .uv(0, 24).cuboid(-5.0F, -4.5F, 0.5F, 10.0F, 10.0F, 11.0F, new CubeDeformation(0.0F))
                .uv(72, 49).cuboid(-2.0F, -2.5F, 6.5F, 4.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, -1.0F, -2.0F));

        PartDefinition head = upperBody.addChild("head", CubeListBuilder.create(), PartPose.of(0.0F, -0.9137F, -8.8708F, -0.5672F, 0.0F, 0.0F));
        PartDefinition neck = head.addChild("neck", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.7641F, 0.6357F));
        neck.addChild("neck_r1", CubeListBuilder.create().uv(0, 0).cuboid(-2.5F, -3.3504F, -8.5F, 5.0F, 7.0F, 17.0F, new CubeDeformation(-0.1F)), PartPose.of(0.0F, -5.0F, -3.0F, -1.0036F, 0.0F, 0.0F));

        PartDefinition mainHead = neck.addChild("main_head", CubeListBuilder.create(), PartPose.of(0.0F, -9.5365F, -6.0671F, 0.2182F, 0.0F, 0.0F));
        mainHead.addChild("ear_left", CubeListBuilder.create().uv(27, 0).cuboid(-1.0F, -2.5F, -0.5F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(3.0F, -2.5889F, -0.3614F, 0.1678F, -0.2564F, 0.9819F));
        mainHead.addChild("ear_right", CubeListBuilder.create().uv(36, 45).cuboid(-1.0F, -2.5F, -0.5F, 2.0F, 5.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(-3.0F, -2.5889F, -0.3614F, 0.1678F, 0.2564F, -0.9819F));
        mainHead.addChild("skull", CubeListBuilder.create().uv(33, 3).cuboid(-3.0F, -3.0F, -2.4569F, 6.0F, 5.0F, 7.0F, new CubeDeformation(0.1F)), PartPose.of(0.0F, -0.7072F, -1.3409F, 0.4363F, 0.0F, 0.0F));
        mainHead.addChild("muzzle", CubeListBuilder.create().uv(62, 5).cuboid(-3.0F, -1.0F, -6.8F, 4.0F, 4.0F, 4.0F, new CubeDeformation(0.1F)), PartPose.of(1.0F, -1.7163F, -1.5432F, 0.4363F, 0.0F, 0.0F));

        PartDefinition antlers = head.addChild("antlers", CubeListBuilder.create(), PartPose.of(-3.0F, -20.0863F, -8.1292F, 0.0873F, 0.0F, 0.0F));
        antlers.addChild("antler_left", CubeListBuilder.create().uv(2, 59).mirrored().cuboid(-2.0F, -7.5F, -6.2F, 0.0F, 15.0F, 15.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.of(0.0F, 0.0F, 0.0F, 0.3309F, -0.4862F, -0.3936F));
        antlers.addChild("antler_right", CubeListBuilder.create().uv(2, 59).cuboid(2.0F, -7.5F, -6.2F, 0.0F, 15.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.of(6.0F, 0.0F, 0.0F, 0.3309F, 0.4862F, 0.3936F));

        deer.addChild("leg_back_left", CubeListBuilder.create().uv(0, 57).cuboid(-1.5F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.pivot(3.49F, -11.0F, 4.0F));
        deer.addChild("leg_back_right", CubeListBuilder.create().uv(38, 57).cuboid(-1.5F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.pivot(-3.49F, -11.0F, 4.0F));
        deer.addChild("leg_front_left", CubeListBuilder.create().uv(12, 57).cuboid(-1.5F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.pivot(3.5F, -11.0F, -11.0F));
        deer.addChild("leg_front_right", CubeListBuilder.create().uv(24, 57).cuboid(-1.5F, -1.0F, -2.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.pivot(-3.5F, -11.0F, -11.0F));
        return LayerDefinition.of(modelData, 128, 128);
    }

    @Override
    public void render(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        this.deer.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return this.deer;
    }

    @Override
    public void setAngles(DeerEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        this.animateMovement(DeerAnimations.WALK, limbAngle, limbDistance, 1f, 1f);
        this.updateAnimation(entity.idleAnimationState, DeerAnimations.IDLE, animationProgress, 1f);
    }
}
