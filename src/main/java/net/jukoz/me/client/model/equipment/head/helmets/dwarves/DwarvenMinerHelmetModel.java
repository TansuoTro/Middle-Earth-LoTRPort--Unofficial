package net.jukoz.me.client.model.equipment.head.helmets.dwarves;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.jukoz.me.client.model.equipment.head.helmets.HelmetAddonModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class DwarvenMinerHelmetModel extends HelmetAddonModel {

    public DwarvenMinerHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("flaps", CubeListBuilder.create()
                .uv(0, 20).cuboid(-4.0F, 1.55F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.9F)),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition right_candle = head.addChild("right_candle", CubeListBuilder.create()
                .uv(0, 0).cuboid(-1.0F, -1.25F, 1.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
                .uv(20, 12).cuboid(-1.0F, -1.25F, 5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
                .uv(0, 14).cuboid(-1.0F, -1.25F, -3.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
                .uv(8, 0).cuboid(-2.0F, -1.25F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F))
                .uv(0, 22).cuboid(-1.0F, -5.25F, -3.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(-5.5F, -4.5F, -4.5F));
        right_candle.addChild("flame_right_0", CubeListBuilder.create()
                .uv(0, 21).cuboid(0.0F, 0.75F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, -7.0F, -2.0F, 0.0F, 0.7854F, 0.0F));
        right_candle.addChild("flame_right_1", CubeListBuilder.create()
                .uv(0, 21).cuboid(0.0F, 0.75F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, -7.0F, -2.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition left_candle = head.addChild("left_candle", CubeListBuilder.create()
                        .uv(0, 0).mirrored().cuboid(-1.0F, -1.25F, 1.0F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(20, 12).mirrored().cuboid(-1.0F, -1.25F, 5.0F, 2.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirrored(false)
                .uv(0, 14).mirrored().cuboid(-1.0F, -1.25F, -3.0F, 2.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(8, 0).mirrored().cuboid(-2.0F, -1.25F, -4.0F, 4.0F, 0.0F, 4.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(0, 22).mirrored().cuboid(-1.0F, -5.25F, -3.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.pivot(5.5F, -4.5F, -4.5F));
        left_candle.addChild("flame_left_0", CubeListBuilder.create()
                .uv(0, 21).mirrored().cuboid(0.0F, 0.75F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(0.0F, -7.0F, -2.0F, 0.0F, -0.7854F, 0.0F));
        left_candle.addChild("flame_left_1", CubeListBuilder.create()
                .uv(0, 21).mirrored().cuboid(0.0F, 0.75F, -0.5F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(0.0F, -7.0F, -2.0F, 0.0F, 0.7854F, 0.0F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
