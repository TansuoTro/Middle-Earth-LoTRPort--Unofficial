package net.jukoz.me.entity.pheasant;

import net.minecraft.client.model.*;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.util.Mth;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class PheasantModel extends HierarchicalModel<PheasantEntity> {
    private final ModelPart pheasant;
    private final ModelPart rightleg;
    private final ModelPart leftleg;
    private final ModelPart rightwing;
    private final ModelPart leftwing;

    public PheasantModel(ModelPart root) {
        this.pheasant = root.getChild("pheasant");
        this.rightleg = this.pheasant.getChild("rightleg");
        this.leftleg = this.pheasant.getChild("leftleg");
        this.rightwing = this.pheasant.getChild("rightwing");
        this.leftwing = this.pheasant.getChild("leftwing");

    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition pheasant = modelPartData.addChild("pheasant", CubeListBuilder.create(), PartPose.pivot(2.4F, 24.0F, -3.5F));

        PartDefinition upperbody = pheasant.addChild("upperbody", CubeListBuilder.create(), PartPose.pivot(-2.5F, -4.0F, 3.0F));

        PartDefinition body = upperbody.addChild("body", CubeListBuilder.create(), PartPose.pivot(2.5F, 4.0F, -3.0F));

        PartDefinition body_r1 = body.addChild("body_r1", CubeListBuilder.create().uv(27, 0).cuboid(-2.5946F, -2.513F, -3.2795F, 5.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.of(-2.4F, -6.4F, 3.04F, -0.2618F, 0.0F, 0.0F));

        PartDefinition body_r2 = body.addChild("body_r2", CubeListBuilder.create().uv(27, 13).cuboid(-1.9591F, -2.5521F, 0.0991F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.of(-2.5F, -4.96F, 5.28F, 0.1745F, 0.0F, 0.0F));

        PartDefinition headandneck = upperbody.addChild("headandneck", CubeListBuilder.create(), PartPose.of(0.0F, -3.0F, -2.0F, 0.1309F, 0.0F, 0.0F));

        PartDefinition head = headandneck.addChild("head", CubeListBuilder.create().uv(26, 0).cuboid(-0.52F, -1.24F, -3.28F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(0, 0).cuboid(-1.56F, -2.92F, -2.4F, 3.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .uv(44, 0).cuboid(-1.56F, 0.08F, -2.4F, 3.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.of(0.1F, -5.0714F, -0.8695F, -0.1309F, 0.0F, 0.0F));

        PartDefinition head_r1 = head.addChild("head_r1", CubeListBuilder.create().uv(15, 0).cuboid(-1.0F, -1.16F, -0.64F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, -2.0F, 0.0F, 0.1305F, 0.0F, 0.0F));

        PartDefinition neck = headandneck.addChild("neck", CubeListBuilder.create().uv(0, 8).cuboid(-0.94F, -5.4F, -0.88F, 2.0F, 8.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, -0.9914F, -0.8695F));

        PartDefinition tail = upperbody.addChild("tail", CubeListBuilder.create(), PartPose.pivot(0.0F, -3.0F, 1.0F));

        PartDefinition tail_r1 = tail.addChild("tail_r1", CubeListBuilder.create().uv(1, 26).cuboid(-1.4653F, -2.2942F, -0.2503F, 3.0F, 3.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, 1.0F, 2.0025F, 0.3054F, 0.0F, 0.0F));

        PartDefinition tail_r2 = tail.addChild("tail_r2", CubeListBuilder.create().uv(2, 7).cuboid(-1.4743F, -0.9196F, 0.683F, 3.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, 1.0F, 2.0025F, 0.3053F, 0.0F, 0.0F));

        PartDefinition tail_r3 = tail.addChild("tail_r3", CubeListBuilder.create().uv(2, 7).cuboid(-1.5F, -0.52F, 1.2F, 3.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, 1.0F, 2.0025F, 0.1773F, 0.0F, 0.0F));

        PartDefinition tail_r4 = tail.addChild("tail_r4", CubeListBuilder.create().uv(2, 7).cuboid(-1.5001F, -0.9949F, 1.2189F, 3.0F, 0.0F, 18.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, 1.0F, 2.0025F, 0.2181F, 0.0F, 0.0F));

        PartDefinition leftwing = pheasant.addChild("leftwing", CubeListBuilder.create(), PartPose.pivot(0.0F, -8.0F, 2.5F));

        PartDefinition leftwing_r1 = leftwing.addChild("leftwing_r1", CubeListBuilder.create().uv(5, 16).mirrored().cuboid(4.3472F, -1.372F, -0.9923F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.of(-4.7425F, 1.1553F, 0.2354F, -0.4346F, 0.0F, 0.0F));

        PartDefinition rightwing = pheasant.addChild("rightwing", CubeListBuilder.create(), PartPose.pivot(-5.0F, -8.0F, 2.5F));

        PartDefinition rightwing_r1 = rightwing.addChild("rightwing_r1", CubeListBuilder.create().uv(5, 16).cuboid(-0.7728F, -1.372F, -0.9923F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.of(0.2575F, 1.1553F, 0.2354F, -0.4346F, 0.0F, 0.0F));

        PartDefinition rightleg = pheasant.addChild("rightleg", CubeListBuilder.create().uv(10, 8).cuboid(-0.54F, -1.0F, -1.46F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.pivot(-3.5F, -4.0F, 3.5F));

        PartDefinition leftleg = pheasant.addChild("leftleg", CubeListBuilder.create().uv(10, 8).cuboid(-0.55F, -1.0F, -1.62F, 1.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.pivot(-1.25F, -4.0F, 3.5F));
        return LayerDefinition.of(modelData, 64, 48);
    }

    @Override
    public void render(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        pheasant.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }

    @Override
    public ModelPart getPart() {
        return this.pheasant;
    }

    @Override
    public void setAngles(PheasantEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.rightleg.pitch = Mth.cos(limbAngle * 0.6662f) * 1.4f * limbDistance;
        this.leftleg.pitch = Mth.cos(limbAngle * 0.6662f + (float)Math.PI) * 1.4f * limbDistance;

        this.rightwing.roll = 0;
        this.leftwing.roll = 0;

        if(!entity.isOnGround()) {
            float angle = Mth.cos(Mth.cos(animationProgress * 0.5f));
            this.rightwing.roll = 4 + (4 * angle);
            this.leftwing.roll = -4 - (4 * angle);
        }
    }
}
