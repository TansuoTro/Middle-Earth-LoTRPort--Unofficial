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

public class HobbitModel {
    private static final String LEFT_SLEEVE = "left_sleeve";
    private static final String RIGHT_SLEEVE = "right_sleeve";
    private static final String LEFT_PANTS = "left_leg";
    private static final String RIGHT_PANTS = "right_leg";

    public static LayerDefinition getTexturedModelData(CubeDeformation dilation) {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition root = modelData.getRoot();

        root.addChild(PartNames.BODY, CubeListBuilder.create().uv(8, 8)
                .cuboid(-1.0f, 0f, -1.0f, 2.0f, 2.0f, 2.0f, dilation),
                PartPose.pivot(0.0f, 0.0f, 0.0f));

        root.addChild(PartNames.HEAD, CubeListBuilder.create().uv(0, 0)
                .cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation),
                PartPose.pivot(0.0f, 0.0f, 0.0f));

        PartDefinition leftArm = root.addChild(PartNames.LEFT_ARM, CubeListBuilder.create().uv(32, 48)
                        .cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                PartPose.pivot(17.0f, 2.5f, 0.0f));
        PartDefinition rightArm = root.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create().uv(40, 16)
                        .cuboid(-2.5f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                PartPose.pivot(-7.0f, 2.5f, 0.0f));

        rightArm.addChild(RIGHT_SLEEVE, CubeListBuilder.create().uv(40, 32).cuboid(0f, 0f, 0f, 3.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                PartPose.pivot(-2f, -2f, -2f));
        leftArm.addChild(LEFT_SLEEVE, CubeListBuilder.create().uv(48, 48).cuboid(0f, 0f, 0f, 3.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                PartPose.pivot(-1f, -2f, -2f));

        root.addChild(PartNames.BODY, CubeListBuilder.create().uv(16, 16)
                .cuboid(-4.0f, 0.0f, -2f, 8.0f, 12.0f, 4.0f, dilation),
                PartPose.pivot(0.0f, 0f, 0.0f));

        root.getChild(PartNames.BODY).addChild(PartNames.JACKET, CubeListBuilder.create().uv(16, 32)
                .cuboid(-4.0f, 0.0f, -2f, 8.0f, 12.0f, 4.0f, dilation.add(0.25f)), PartPose.NONE);

        root.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create().uv(0, 16)
                .cuboid(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                PartPose.pivot(-1.9f, 0.0f, 0.0f));
        root.addChild(PartNames.LEFT_LEG, CubeListBuilder.create().uv(0, 16).mirrored()
                .cuboid(-2.0f, 0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation),
                PartPose.pivot(1.9f, 0.0f, 0.0f));

        root.getChild(PartNames.LEFT_LEG).addChild(LEFT_PANTS, CubeListBuilder.create().uv(0, 48)
                        .cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                PartPose.pivot(0, 0.0f, 0.0f));
        root.getChild(PartNames.RIGHT_LEG).addChild(RIGHT_PANTS, CubeListBuilder.create().uv(0, 32)
                        .cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)),
                PartPose.pivot(0, 0.0f, 0.0f));

        root.addChild(PartNames.HAT, CubeListBuilder.create().uv(32, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation.add(0.5f)),
                PartPose.pivot(0.0f, 0.0f, 0.0f));                // 54

        return LayerDefinition.of(modelData, 64, 64);
    }
}
