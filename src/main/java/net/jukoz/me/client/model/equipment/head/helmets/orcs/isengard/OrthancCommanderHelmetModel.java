package net.jukoz.me.client.model.equipment.head.helmets.orcs.isengard;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class OrthancCommanderHelmetModel extends OrthancGuardHelmetModel {

    public OrthancCommanderHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();

        PartDefinition head = modelData.getRoot().addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("addons_commander", CubeListBuilder.create()
                .uv(1, 16).cuboid(-11.0F, -18.625F, -0.7F, 22.0F, 12.0F, 1.0F, new CubeDeformation(0.0F))
                .uv(-9, 30).cuboid(-7.5F, -1.85F, -1.0F, 15.0F, 0.0F, 9.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
