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

public class EreborHornsHelmetModel extends HelmetAddonModel {

    public EreborHornsHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition addon_horns = head.addChild("addon_horns", CubeListBuilder.create(),
                PartPose.pivot(-11.0F, -8.0208F, -3.4645F));

        PartDefinition right_addon_horn = addon_horns.addChild("right_addon_horn", CubeListBuilder.create(),
                PartPose.of(-1.0F, 3.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition right_horn = right_addon_horn.addChild("right_horn", CubeListBuilder.create(),
                PartPose.pivot(13.0F, -3.8284F, -0.4142F));

        right_horn.addChild("right_horn_0", CubeListBuilder.create()
                .uv(21, 59).cuboid(-8.0F, 4.75F, -5.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        right_horn.addChild("right_horn_1", CubeListBuilder.create()
                .uv(20, 50).cuboid(-8.0F, 4.75F, -5.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, 2.1213F, 2.1213F, 0.7854F, 0.0F, 0.0F));
        right_horn.addChild("right_horn_2", CubeListBuilder.create()
                .uv(1, 52).cuboid(-8.0F, 0.75F, -1.0F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, 2.8284F, 1.4142F, 0.7854F, 0.0F, 0.0F));

        PartDefinition left_horn = right_addon_horn.addChild("left_horn", CubeListBuilder.create(),
                PartPose.pivot(11.0F, -3.8284F, -0.4142F));

        left_horn.addChild("left_horn_0", CubeListBuilder.create()
                        .uv(21, 59).mirrored().cuboid(5.0F, 4.75F, -5.0F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        left_horn.addChild("left_horn_1", CubeListBuilder.create()
                        .uv(20, 50).mirrored().cuboid(4.0F, 4.75F, -5.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(0.0F, 2.1213F, 2.1213F, 0.7854F, 0.0F, 0.0F));
        left_horn.addChild("left_horn_2", CubeListBuilder.create()
                        .uv(1, 52).mirrored().cuboid(4.0F, 0.75F, -1.0F, 4.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(0.0F, 2.8284F, 1.4142F, 0.7854F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
