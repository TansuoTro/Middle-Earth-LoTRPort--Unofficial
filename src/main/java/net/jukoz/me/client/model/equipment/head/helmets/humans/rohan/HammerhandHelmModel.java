package net.jukoz.me.client.model.equipment.head.helmets.humans.rohan;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class HammerhandHelmModel extends RohanHelmetModel {
    
    public HammerhandHelmModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition HornsR = head.addChild("horn_right_attachment", CubeListBuilder.create()
                .uv(0, 0).cuboid(-6.4F, -6.25F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(0.0F, 0.0F, 0.0F));
        HornsR.addChild("horn_right", CubeListBuilder.create()
                .uv(50, 0).cuboid(-3.5F, -11.25F, 0.0F, 7.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.of(-5.5F, -5.0F, 0.0F, 0.0F, 0.0F, 0.1396F));

        PartDefinition HornsR2 = head.addChild("horn_left_attachment", CubeListBuilder.create()
                .uv(0, 0).cuboid(4.6F, -6.25F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(0.0F, 0.0F, 0.0F));
        HornsR2.addChild("horn_left", CubeListBuilder.create()
                .uv(50, 13).mirrored().cuboid(-3.5F, -11.25F, 0.0F, 7.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(5.7F, -5.0F, 0.0F, 0.0F, 0.0F, -0.1396F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
