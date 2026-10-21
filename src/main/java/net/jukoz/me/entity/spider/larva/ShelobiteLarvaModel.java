package net.jukoz.me.entity.spider.larva;

import net.minecraft.client.model.*;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ShelobiteLarvaModel extends HierarchicalModel<ShelobiteLarvaEntity> {
    private final ModelPart root;

    public ShelobiteLarvaModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addChild("root", CubeListBuilder.create(), PartPose.pivot(0.0F, 23.5F, 5.0F));

        PartDefinition abdomen = root.addChild("abdomen", CubeListBuilder.create(), PartPose.pivot(0.0F, -1.5F, -3.0F));

        PartDefinition cube_r1 = abdomen.addChild("cube_r1", CubeListBuilder.create().uv(0, 0).cuboid(-2.5F, -2.0F, -1.5F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, -0.2F, 0.5F, 0.1745F, 0.0F, 0.0F));

        PartDefinition core = root.addChild("core", CubeListBuilder.create().uv(0, 11).cuboid(-1.5F, -0.95F, -1.2F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, -1.05F, -5.8F));

        PartDefinition leg_left = core.addChild("leg_left", CubeListBuilder.create(), PartPose.pivot(1.5F, -0.45F, 0.3F));

        PartDefinition leg_left_front = leg_left.addChild("leg_left_front", CubeListBuilder.create(), PartPose.of(-0.2617F, 0.65F, 0.0804F, -1.5708F, 1.0472F, 0.0F));

        PartDefinition cube_r2 = leg_left_front.addChild("cube_r2", CubeListBuilder.create().uv(0, 17).cuboid(-0.1608F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.of(0.1608F, 0.0804F, 0.5F, -1.5708F, 0.2182F, 0.0F));

        PartDefinition leg_left_center_front = leg_left.addChild("leg_left_center_front", CubeListBuilder.create(), PartPose.of(-0.2617F, 0.65F, 0.0804F, -1.6411F, 0.6474F, -0.2534F));

        PartDefinition cube_r3 = leg_left_center_front.addChild("cube_r3", CubeListBuilder.create().uv(0, 17).cuboid(-0.1608F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.of(0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));

        PartDefinition leg_left_center_back = leg_left.addChild("leg_left_center_back", CubeListBuilder.create(), PartPose.of(-0.2617F, 0.65F, 0.0804F, -1.5327F, -0.1704F, -0.2214F));

        PartDefinition cube_r4 = leg_left_center_back.addChild("cube_r4", CubeListBuilder.create().uv(0, 17).cuboid(-0.1608F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.of(0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));

        PartDefinition leg_left_back = leg_left.addChild("leg_left_back", CubeListBuilder.create(), PartPose.of(-0.2617F, 0.65F, 0.0804F, -1.4338F, -0.5522F, -0.257F));

        PartDefinition cube_r5 = leg_left_back.addChild("cube_r5", CubeListBuilder.create().uv(0, 17).cuboid(-0.1608F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.of(0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));

        PartDefinition leg_right = core.addChild("leg_right", CubeListBuilder.create(), PartPose.pivot(-1.5F, -0.45F, 0.3F));

        PartDefinition leg_right_front = leg_right.addChild("leg_right_front", CubeListBuilder.create(), PartPose.of(0.2617F, 0.65F, 0.0804F, -1.5708F, -1.0472F, 0.0F));

        PartDefinition cube_r6 = leg_right_front.addChild("cube_r6", CubeListBuilder.create().uv(0, 17).mirrored().cuboid(-3.8392F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.of(-0.1608F, 0.0804F, 0.5F, -1.5708F, -0.2182F, 0.0F));

        PartDefinition leg_right_center_front = leg_right.addChild("leg_right_center_front", CubeListBuilder.create(), PartPose.of(0.2617F, 0.65F, 0.0804F, -1.6411F, -0.6474F, 0.2534F));

        PartDefinition cube_r7 = leg_right_center_front.addChild("cube_r7", CubeListBuilder.create().uv(0, 17).mirrored().cuboid(-3.8392F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.of(-0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));

        PartDefinition leg_right_center_back = leg_right.addChild("leg_right_center_back", CubeListBuilder.create(), PartPose.of(0.2617F, 0.65F, 0.0804F, -1.5327F, 0.1704F, 0.2214F));

        PartDefinition cube_r8 = leg_right_center_back.addChild("cube_r8", CubeListBuilder.create().uv(0, 17).mirrored().cuboid(-3.8392F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.of(-0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));

        PartDefinition leg_right_back = leg_right.addChild("leg_right_back", CubeListBuilder.create(), PartPose.of(0.2617F, 0.65F, 0.0804F, -1.4338F, 0.5522F, 0.257F));

        PartDefinition cube_r9 = leg_right_back.addChild("cube_r9", CubeListBuilder.create().uv(0, 17).mirrored().cuboid(-3.8392F, -1.5F, -0.0804F, 4.0F, 3.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.of(-0.1608F, 0.0804F, 0.5F, -1.5708F, 0.0F, 0.0F));
        return LayerDefinition.of(modelData, 32, 32);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(ShelobiteLarvaEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        this.animateMovement(ShelobiteLarvaAnimations.SHELOBITE_LARVA_WALK, limbSwing, limbSwingAmount, 3F, 2.5F);
        this.updateAnimation(entity.biteAnimation, ShelobiteLarvaAnimations.SHELOBITE_LARVA_BITE, ageInTicks, 1.25f);
    }
}
