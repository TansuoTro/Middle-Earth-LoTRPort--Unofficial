package net.jukoz.me.client.model.equipment.head.helmets.orcs.mordor;

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

public class MordorSnoutHelmetModel extends HelmetAddonModel {

    public MordorSnoutHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition snout = head.addChild("snout", CubeListBuilder.create(),
                //edit -0.75 for height, nothing else
                PartPose.of(0.0F, -0.75F, -2.59F, 1.1345F, 0.0F, 0.0F));
        snout.addChild("snout_cube", CubeListBuilder.create()
                .uv(0, 16).cuboid(-3.5F, -3.5F, -7.5F, 7.0F, 7.0F, 12.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

        head.addChild("side_extension_left", CubeListBuilder.create()
                .uv(18, 0).cuboid(0.0F, -2.25F, 1.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.of(-4.0F, -6.0F, 0.2F, 0.0F, -0.4363F, 0.0F));

        head.addChild("side_extension_right", CubeListBuilder.create()
                .uv(29, 0).cuboid(-1.0F, -2.25F, 1.0F, 1.0F, 6.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.of(4.0F, -6.0F, 0.2F, 0.0F, 0.4363F, 0.0F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);

    }
}
