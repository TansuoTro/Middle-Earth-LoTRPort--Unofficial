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

public class MoriaScreecherHelmetModel extends HelmetAddonModel {

    public MoriaScreecherHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        return LayerDefinition.of(modelData, 64, 64);
    }

    public static MeshDefinition getModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("large_crest", CubeListBuilder.create()
                        .uv(28, -18).cuboid(0.0F, -15.25F, -7.0F, 0.0F, 14.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("upper_jaw", CubeListBuilder.create()
                        .uv(0, 17).cuboid(-5.3268F, -7.5878F, -1.8578F, 6.0F, 8.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.8268F, -5.3903F, -7.9422F, -0.5025F, 0.7273F, -0.357F));

        head.addChild("bottom_jaw", CubeListBuilder.create()
                .uv(0, 31).cuboid(-8.4497F, -9.75F, 2.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.3F)),
                PartPose.of(0.3498F, 4.5F, -8.9142F, 0.6603F, 0.6603F, 0.4439F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return modelData;
    }
}
