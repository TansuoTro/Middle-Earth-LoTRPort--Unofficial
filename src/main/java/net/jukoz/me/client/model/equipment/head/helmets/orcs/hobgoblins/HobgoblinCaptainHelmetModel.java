package net.jukoz.me.client.model.equipment.head.helmets.orcs.hobgoblins;

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

public class HobgoblinCaptainHelmetModel extends HelmetAddonModel {

    public HobgoblinCaptainHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition crest = head.addChild("crest", CubeListBuilder.create(),
                PartPose.pivot(-1.5F, -11.5976F, -4.4512F));

        crest.addChild("crest_left", CubeListBuilder.create()
                .uv(38, 53).mirrored().cuboid(-4.0F, -4.75F, 0.0F, 8.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(5.2366F, 0.5132F, 0.3928F, 0.0F, -0.3491F, 0.0F));

        crest.addChild("crest_right", CubeListBuilder.create()
                .uv(38, 53).cuboid(-5.0F, -3.25F, 0.0F, 8.0F, 11.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.of(-1.3312F, -0.9868F, 0.0562F, 0.0F, 0.3491F, 0.0F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
