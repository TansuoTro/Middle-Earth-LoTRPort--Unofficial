package net.jukoz.me.client.model.equipment.chest;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class DGMarauderChestplateModel extends BlackCastellanChestplateModel {

    public DGMarauderChestplateModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();

        PartDefinition body = modelData.getRoot().addChild(PartNames.BODY, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        body.addChild("spike_right", CubeListBuilder.create()
                .uv(36, 0).cuboid(-10.25F, -14.5F, 0.252F, 11.0F, 19.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.of(-1.8F, 1.0F, 0.75F, 0.0F, 0.6545F, -0.3491F));

        body.addChild("spike_left", CubeListBuilder.create()
                .uv(36, 0).mirrored().cuboid(-0.5F, -14.5F, 0.284F, 11.0F, 19.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(1.764F, 1.0F, 0.5F, 0.0F, -0.6545F, 0.3491F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
