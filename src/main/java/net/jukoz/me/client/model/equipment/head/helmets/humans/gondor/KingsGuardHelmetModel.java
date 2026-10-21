package net.jukoz.me.client.model.equipment.head.helmets.humans.gondor;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.jukoz.me.client.model.equipment.head.helmets.EggHelmetModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public class KingsGuardHelmetModel extends EggHelmetModel {

    public KingsGuardHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        //+0.75
        modelData.getRoot().addChild(PartNames.HEAD, CubeListBuilder.create()
                        .uv(50, 0).mirrored().cuboid(2.0F, -19.55F, 0.0F, 7.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false)
                        .uv(50, 16).cuboid(-9.0F, -19.55F, 0.0F, 7.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
