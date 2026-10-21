package net.jukoz.me.client.model.equipment.head.helmets.orcs.mordor.dolguldur;

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

public class DGMarauderHelmetModel extends HelmetAddonModel {

    public DGMarauderHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("horn_left", CubeListBuilder.create()
                .uv(0, -8).mirrored().cuboid(0.0F, -14.25F, -9.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(3.0F, 1.0F, -5.0F, -0.4363F, -0.6981F, 0.0F));

        head.addChild("horn_right", CubeListBuilder.create()
                .uv(0, -8).cuboid(0.0F, -14.25F, -9.0F, 0.0F, 10.0F, 10.0F, new CubeDeformation(0.0F)),
                PartPose.of(-3.0F, 1.0F, -5.0F, -0.4363F, 0.6981F, 0.0F));

        head.addChild("side_right", CubeListBuilder.create()
                        .uv(24, 15).cuboid(-5.25F, -9.75F, 0.252F, 6.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.of(-5.0F, -7.0F, -1.25F, 0.0F, 0.6545F, 0.0F));

        head.addChild("side_left", CubeListBuilder.create()
                        .uv(24, 0).mirrored().cuboid(-0.5F, -9.75F, 0.284F, 6.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(4.814F, -7.0F, -1.5F, 0.0F, -0.6545F, 0.0F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
