package net.jukoz.me.client.model.equipment.head.helmets.elves.woodlandrealm;

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

public class WoodlandRealmCrownModel extends HelmetAddonModel {
    ModelPart crown;

    public WoodlandRealmCrownModel(ModelPart root) {
        super(root);

        ModelPart helmet = root.getChild("head");
        ModelPart addon = helmet.getChild("addon");
        this.crown = addon.getChild("crown");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition addon = head.addChild("addon", CubeListBuilder.create(), PartPose.pivot(0.0F, 1.75F, -0.25F));

        PartDefinition crown = addon.addChild("crown", CubeListBuilder.create().uv(41, 28)
                .cuboid(-1.9077F, -8.5F, 3.3333F, 4.0F, 15.0F, 0.0F,
                        new CubeDeformation(0.0F)), PartPose.of(-0.0909F, -11.0F, 2.4137F, -0.3491F, 0.0F, 0.0F));

        PartDefinition crown_side_right = crown.addChild("crown_side_right", CubeListBuilder.create().uv(26, 28)
                .cuboid(-4.0F, -7.5F, 0.0F, 7.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.of(-3.9077F, -1.0F, 1.3333F, 0.0F, -0.7418F, 0.0F));

        PartDefinition crown_side_left = crown.addChild("crown_side_left", CubeListBuilder.create().uv(50, 28)
                .mirrored().cuboid(-3.0F, -7.5F, 0.0F, 7.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(4.1154F, -1.0F, 1.3335F, 0.0F, 0.7418F, 0.0F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
