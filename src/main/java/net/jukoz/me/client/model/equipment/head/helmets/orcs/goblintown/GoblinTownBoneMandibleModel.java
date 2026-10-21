package net.jukoz.me.client.model.equipment.head.helmets.orcs.goblintown;

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

public class GoblinTownBoneMandibleModel extends HelmetAddonModel {

    public GoblinTownBoneMandibleModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("mandible_left", CubeListBuilder.create()
                .uv(2, 15).cuboid(0.0F, -8.25F, -6.0F, 0.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.of(4.0F, 2.0F, -4.0F, 0.0F, -0.4363F, 0.0F));
        head.addChild("mandible_right", CubeListBuilder.create()
                .uv(2, 15).cuboid(0.0F, -8.25F, -6.0F, 0.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.of(-4.0F, 2.0F, -4.0F, 0.0F, 0.4363F, 0.0F));
        head.addChild("bottom_jaw", CubeListBuilder.create()
                .uv(0, 36).cuboid(-8.4497F, -9.75F, 2.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.3F)),
                PartPose.of(0.3498F, 4.5F, -8.9142F, 0.6603F, 0.6603F, 0.4439F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
