package net.jukoz.me.entity.swan;

import net.minecraft.client.model.*;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.Mth;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class SwanModel<T extends SwanEntity> extends EntityModel<SwanEntity> {
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart right_wing;
    private final ModelPart left_wing;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    public SwanModel(ModelPart root) {
        this.head = root.getChild("head");
        this.body = root.getChild("body");
        this.right_wing = root.getChild("right_wing");
        this.left_wing = root.getChild("left_wing");
        this.right_leg = root.getChild("right_leg");
        this.left_leg = root.getChild("left_leg");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition head = modelPartData.addChild("head", CubeListBuilder.create(), PartPose.pivot(0.0F, 14.0F, -2.75F));

        PartDefinition cube_r1 = head.addChild("cube_r1", CubeListBuilder.create().uv(7, 8).cuboid(-1.0F, -6.0F, 17.25F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, 11.0F, 2.0F, 1.8762F, 0.0F, 0.0F));

        PartDefinition cube_r2 = head.addChild("cube_r2", CubeListBuilder.create().uv(0, 0).cuboid(6.5F, -14.75F, 10.75F, 3.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.of(-8.0F, 3.0F, 10.5F, 1.789F, 0.0F, 0.0F));

        PartDefinition cube_r3 = head.addChild("cube_r3", CubeListBuilder.create().uv(13, 19).cuboid(-1.0F, -15.0F, 10.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, 11.0F, 2.0F, 0.8727F, 0.0F, 0.0F));

        PartDefinition cube_r4 = head.addChild("cube_r4", CubeListBuilder.create().uv(0, 19).cuboid(-1.025F, -14.0F, -6.0F, 2.05F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, 10.0F, 2.25F, -0.2618F, 0.0F, 0.0F));

        PartDefinition body = modelPartData.addChild("body", CubeListBuilder.create().uv(0, 0).cuboid(-3.0F, -3.0F, -6.5F, 6.0F, 6.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 17.0F, 1.5F));

        PartDefinition right_wing = modelPartData.addChild("right_wing", CubeListBuilder.create(), PartPose.pivot(3.0F, 15.0F, 2.0F));

        PartDefinition cube_r5 = right_wing.addChild("cube_r5", CubeListBuilder.create().uv(0, 19).cuboid(3.0F, -10.0F, -2.0F, 1.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.of(-3.0F, 8.0F, -1.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition left_wing = modelPartData.addChild("left_wing", CubeListBuilder.create(), PartPose.pivot(-3.0F, 15.0F, 2.0F));

        PartDefinition cube_r6 = left_wing.addChild("cube_r6", CubeListBuilder.create().uv(13, 24).cuboid(-4.0F, -10.0F, -2.0F, 1.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.of(3.0F, 8.0F, -1.0F, 0.2618F, 0.0F, 0.0F));

        PartDefinition right_leg = modelPartData.addChild("right_leg", CubeListBuilder.create().uv(0, 8).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .uv(6, 0).cuboid(-1.0F, 4.0F, -3.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.pivot(-2.5F, 20.0F, 3.0F));

        PartDefinition left_leg = modelPartData.addChild("left_leg", CubeListBuilder.create().uv(9, 19).cuboid(-0.5F, 0.0F, 0.0F, 1.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .uv(0, 8).cuboid(-1.0F, 4.0F, -3.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.pivot(2.5F, 20.0F, 3.0F));
        return LayerDefinition.of(modelData, 64, 64);
    }
    @Override
    public void render(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        head.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        right_wing.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        left_wing.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        right_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        left_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public void setAngles(SwanEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.right_leg.pitch = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.left_leg.pitch = Mth.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
        this.right_wing.roll = 0;
        this.left_wing.roll = -0;
        this.head.pitch = 0;
        if(!entity.isOnGround()) {
            float angle = Mth.cos(Mth.cos(ageInTicks * 0.5f));
            this.right_wing.roll = -4 + (4 * angle);
            this.left_wing.roll = 4 - (4 * angle);
        } else if(entity.isAttacking()) {
            float angle = Mth.cos(Mth.cos(ageInTicks * 0.4f));
            this.right_wing.roll = -3 + (2 * angle);
            this.left_wing.roll = 3 - (2 * angle);
            this.head.pitch = (float) Math.toRadians(-70);
        }
    }
}
