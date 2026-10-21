package net.jukoz.me.entity.goose;

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

public class GooseModel<T extends GooseEntity> extends EntityModel<GooseEntity> {
    public final ModelPart neck;
    public final ModelPart head;
    private final ModelPart body;
    private final ModelPart right_wing;
    private final ModelPart left_wing;
    private final ModelPart right_leg;
    private final ModelPart left_leg;
    public GooseModel(ModelPart root) {
        this.neck = root.getChild("head");
        this.head = neck.getChild("head2");
        this.body = root.getChild("body");
        this.right_wing = root.getChild("right_wing");
        this.left_wing = root.getChild("left_wing");
        this.right_leg = root.getChild("right_leg");
        this.left_leg = root.getChild("left_leg");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition neck = modelPartData.addOrReplaceChild("head", CubeListBuilder.create()
                .texOffs(0, 0).addBox(-1.025F, -6.0F, -1.0F, 2.05F, 8.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 12.0F, -3.5F));
        PartDefinition head = neck.addOrReplaceChild("head2", CubeListBuilder.create()
                .texOffs(25, 0).addBox(-1.5F, -2.75F, -3.25F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -6.0F, 0.0F));
        head.addOrReplaceChild("peck", CubeListBuilder.create()
                .texOffs(0, 19).addBox(-1.0F, 0.0F, -6.25F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, -1, 0.0F));

        PartDefinition body = modelPartData.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(0.0F, 17.0F, 1.5F));

        PartDefinition cube_r1 = body.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -9.0F, -6.0F, 6.0F, 6.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 5.0F, -2.5F, -0.2618F, 0.0F, 0.0F));

        PartDefinition right_wing = modelPartData.addOrReplaceChild("right_wing", CubeListBuilder.create(), PartPose.offset(3.0F, 13.0F, 0.5F));

        PartDefinition cube_r2 = right_wing.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 19).addBox(3.0F, -10.0F, -2.0F, 1.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.0F, 9.0F, -1.5F, -0.2618F, 0.0F, 0.0F));

        PartDefinition left_wing = modelPartData.addOrReplaceChild("left_wing", CubeListBuilder.create(), PartPose.offset(-3.0F, 13.0F, 0.5F));

        PartDefinition cube_r3 = left_wing.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(13, 24).addBox(-4.0F, -10.0F, -2.0F, 1.0F, 5.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(3.0F, 9.0F, -1.5F, -0.2618F, 0.0F, 0.0F));

        PartDefinition right_leg = modelPartData.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(13, 19).addBox(-0.5F, -0.75F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(26, 19).addBox(-1.0F, 4.25F, -3.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 19.75F, 2.0F));

        PartDefinition left_leg = modelPartData.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(16, 19).addBox(-0.5F, -0.75F, 0.0F, 1.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(19, 19).addBox(-1.0F, 4.25F, -3.0F, 2.0F, 0.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 19.75F, 2.0F));
        return LayerDefinition.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(GooseEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.right_leg.xRot = Mth.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
        this.left_leg.xRot = Mth.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
        this.right_wing.roll = 0;
        this.left_wing.roll = -0;
        this.neck.xRot = headPitch * 0.017453292F;
        this.neck.yRot = netHeadYaw * 0.017453292F;
        this.head.xRot = 0;
        if(!entity.isOnGround()) {
            float angle = Mth.cos(Mth.cos(ageInTicks * 0.5f));
            this.right_wing.roll = -4 + (4 * angle);
            this.left_wing.roll = 4 - (4 * angle);
        } else if(entity.isAggressive()) {
            float angle = Mth.cos(Mth.cos(ageInTicks * 0.4f));
            this.right_wing.roll = -3 + (2 * angle);
            this.left_wing.roll = 3 - (2 * angle);
            this.neck.xRot = (float) Math.toRadians(72.5f);
            this.neck.yRot = 0; //netHeadYaw * 0.017453292F;
            this.head.xRot = (float) Math.toRadians(-70);
        }
    }

    @Override
    public void render(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        neck.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        right_wing.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        left_wing.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        right_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
        left_leg.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);

    }

}
