package net.jukoz.me.entity.model;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.world.entity.Entity;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class DwarfModel {
    public static final String NOSE = "nose";
    public static final String BEARD = "beard";
    public static final String BEARD2 = "beard2";
    public static final String BEARD_TIP = "beard_tip";
    public static final String NORI_BEARD_CENTER = "nori_beard_center";
    public static final String NORI_BEARD_LEFT = "nori_beard_left";
    public static final String NORI_BEARD_RIGHT = "nori_beard_right";
    private static final String LEFT_SLEEVE = "left_sleeve";
    private static final String RIGHT_SLEEVE = "right_sleeve";
    public static final float BEARD_PITCH_ANGLE = -0.174f;

    public static LayerDefinition getTexturedModelData(CubeDeformation dilation) {
        MeshDefinition modelData = new MeshDefinition();
            PartDefinition modelPartData = modelData.getRoot();
            PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder
                    .create().uv(0, 0)
                    .cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation), PartPose.pivot(0.0f, 0.0f, 0.0f));

            head.addChild(NOSE, CubeListBuilder.create().uv(0, 5).cuboid(-1f, 0, 0, 2.0f, 2.0f, 1.0f, dilation),
                    PartPose.of(0f, -4.0f, -5.0f, 0, 0, 0));

            head.addChild(BEARD, CubeListBuilder.create().uv(56, 25).cuboid(0, 0, -4f, 7.0f, 5.0f, 2.0f, dilation),
                    PartPose.of(-3.5f, 0.67f, 0.0f, BEARD_PITCH_ANGLE, 0, 0));
            head.addChild(BEARD2, CubeListBuilder.create().uv(57, 26).cuboid(0, 0, -6f, 5.0f, 3.0f, 1.0f, dilation),
                    PartPose.of(-2.5f, 1f, 1.05f, BEARD_PITCH_ANGLE, 0, 0));
            head.addChild(BEARD_TIP, CubeListBuilder.create().uv(57, 26).cuboid(0, 0, -5f, 5.0f, 2.0f, 1.0f, dilation),
                    PartPose.of(-2.5f, 5.99f, 1f, BEARD_PITCH_ANGLE, 0, 0));

            head.addChild(NORI_BEARD_CENTER, CubeListBuilder.create().uv(64, 0).cuboid(-1, 1.5f, -4.5f, 2.0f, 5.0f, 2.0f, dilation.add(0.15f)),
                    PartPose.of(0f, 0f, 0f, BEARD_PITCH_ANGLE - 0.2f, 0, 0));
            head.addChild(NORI_BEARD_LEFT, CubeListBuilder.create().uv(64, 0).cuboid(1f, 0.7f, -4f, 2.0f, 5.0f, 2.0f, dilation),
                    PartPose.of(0f, 0f, 0f, BEARD_PITCH_ANGLE, 0, -0.55f));
            head.addChild(NORI_BEARD_RIGHT, CubeListBuilder.create().uv(64, 0).cuboid(-3f, 0.7f, -4f, 2.0f, 5.0f, 2.0f, dilation),
                    PartPose.of(0f, 0f, 0f, BEARD_PITCH_ANGLE, 0, 0.55f));

            modelPartData.addChild(PartNames.HAT, CubeListBuilder.create().uv(32, 0).cuboid(-4.0f, -8.0f, -4.0f, 8.0f, 8.0f, 8.0f, dilation.add(0.5f)), PartPose.pivot(0.0f, 0.0f, 0.0f));
            modelPartData.addChild(PartNames.BODY, CubeListBuilder.create().uv(16, 16).cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, dilation), PartPose.pivot(0.0f, 0.0f, 0.0f));

            PartDefinition rightArm = modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create()
                    .uv(40, 16).cuboid(-3.0f, -2.5f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), PartPose.pivot(-5.0f, 2.0f, 0.0f));
            rightArm.addChild(RIGHT_SLEEVE, CubeListBuilder.create().uv(40, 32).cuboid(0f, 0f, 0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)), PartPose.pivot(-3f, -2f, -2f));

            PartDefinition leftArm = modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create()
                    .uv(40, 16).mirrored().cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), PartPose.pivot(5.0f, 2.0f, 0.0f));
            leftArm.addChild(LEFT_SLEEVE, CubeListBuilder.create().uv(48, 48).cuboid(0f, 0f, 0f, 4.0f, 12.0f, 4.0f, dilation.add(0.25f)), PartPose.pivot(-1f, -2f, -2f));

            modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create().uv(0, 16).cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), PartPose.pivot(-1.9f, 12.0f, 0.0f));
            modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create().uv(0, 16).mirrored().cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, dilation), PartPose.pivot(1.9f, 12.0f, 0.0f));

            return LayerDefinition.of(modelData, 82, 64);
    }
}
