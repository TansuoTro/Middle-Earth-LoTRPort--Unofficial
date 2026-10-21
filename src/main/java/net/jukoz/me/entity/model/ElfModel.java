package net.jukoz.me.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ElfModel {
    private static final String LEFT_EAR = "left_ear";
    private static final String RIGHT_EAR = "right_ear";
    private static final String LEFT_SLEEVE = "left_sleeve";
    private static final String RIGHT_SLEEVE = "right_sleeve";
    private static final String LEFT_PANTS = "left_leg";
    private static final String RIGHT_PANTS = "right_leg";

    public static LayerDefinition getTexturedModelData(CubeDeformation dilation) {
        MeshDefinition modelData = HumanoidModel.getModelData(dilation, 0.0f);
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addChild(PartNames.HAT, CubeListBuilder.create().uv(54, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 16.0f, 8.0f, dilation.add(0.5f)),
                PartPose.pivot(0.0f, 0.0f, 0.0f));

        PartDefinition rightEar = modelPartData.getChild(PartNames.HEAD).addChild(RIGHT_EAR, CubeListBuilder.create().uv(2, 2).cuboid(0, -0, -1f, 1.0f, 2.0f, 2.0f, dilation),
                PartPose.of(-5f, -4f, -0.25f,-0.7f, 0, -0.35f));
        PartDefinition leftEar = modelPartData.getChild(PartNames.HEAD).addChild(LEFT_EAR, CubeListBuilder.create().uv(2, 2).cuboid(0, -0, -1f, 1.0f, 2.0f, 2.0f, dilation),
                PartPose.of(4f, -4f, -0.25f,-0.7f, 0, 0.35f));
        rightEar.addChild(RIGHT_EAR, CubeListBuilder.create().uv(3, 3).cuboid(0, -2, -1f, 1.0f, 2.0f, 1.0f, dilation),
                PartPose.of(0f, 0, 0f,0f, 0, 0f));
        leftEar.addChild(LEFT_EAR, CubeListBuilder.create().uv(3, 4).cuboid(0, -2, -1f, 1.0f, 2.0f, 1.0f, dilation),
                PartPose.of(0f, 0, 0f,0f, 0, 0f));

        PartDefinition leftArm = modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create()
                        .uv(32, 48).cuboid(-1.0f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, dilation),
                PartPose.pivot(5.0f, 2.5f, 0.0f));
        PartDefinition rightArm = modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create()
                        .uv(40, 16).cuboid(-2.5f, -2.0f, -2.0f, 3.0f, 12.0f, 4.0f, dilation),
                PartPose.pivot(-5.0f, 2.5f, 0.0f));

        rightArm.addChild(RIGHT_SLEEVE, CubeListBuilder.create().uv(40, 32).cuboid(0f, 0f, 0f, 3.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                PartPose.pivot(-3f, -2f, -2f));
        leftArm.addChild(LEFT_SLEEVE, CubeListBuilder.create().uv(48, 48).cuboid(0f, 0f, 0f, 3.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                PartPose.pivot(-1f, -2f, -2f));

        modelPartData.getChild(PartNames.BODY).addChild(PartNames.JACKET, CubeListBuilder.create().uv(16, 32).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, dilation.add(0.25f)), PartPose.NONE);
        modelPartData.getChild(PartNames.LEFT_LEG).addChild(LEFT_PANTS, CubeListBuilder.create().uv(0, 48).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                PartPose.pivot(0, 0.0f, 0.0f));
        modelPartData.getChild(PartNames.RIGHT_LEG).addChild(RIGHT_PANTS, CubeListBuilder.create().uv(0, 32).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                PartPose.pivot(0, 0.0f, 0.0f));

        //modelPartData.addChild(PartNames.HAT, CubeListBuilder.create().uv(32, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation.add(0.5f)),
        //        PartPose.pivot(0.0f, 0.0f, 0.0f));                // 54

        return LayerDefinition.of(modelData, 86, 64);
    }
}
