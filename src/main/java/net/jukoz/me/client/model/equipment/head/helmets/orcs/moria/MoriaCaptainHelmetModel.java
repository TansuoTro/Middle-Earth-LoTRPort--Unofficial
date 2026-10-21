package net.jukoz.me.client.model.equipment.head.helmets.orcs.moria;

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

public class MoriaCaptainHelmetModel extends MoriaScreecherHelmetModel {

    public MoriaCaptainHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("side_spikes", CubeListBuilder.create()
                .uv(48, 13).cuboid(-4.0F, -3.25F, -1.0F, 8.0F, 10.0F, 0.0F, new CubeDeformation(0.0F))
                .uv(47, 25).cuboid(-21.0F, -3.25F, -1.0F, 8.0F, 10.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.of(8.5F, -7.2F, -1.0F, -0.4363F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
