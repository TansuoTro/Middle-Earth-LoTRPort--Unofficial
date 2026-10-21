package net.jukoz.me.client.model.equipment.chest.backAttachments.armored;

import net.jukoz.me.client.model.equipment.chest.backAttachments.CloakCapeModel;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class CapeSlimModel extends CloakCapeModel {

    public CapeSlimModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition body = modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));
        body.addChild("cape_shoulder", CubeListBuilder.create().uv(0, 16).cuboid(-4.0F, -23.5F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(1.1F)),
                PartPose.pivot(0.0F, 23.0F, 0.016F));

        PartDefinition cape = body.addChild("cape", CubeListBuilder.create()
                .uv(92, 32).mirrored().cuboid(-5.5F, -3.0F, -1F, 11.0F, 13.0F, 5.0F, new CubeDeformation(0.2F)).mirrored(false)
                .uv(92, 94).mirrored().cuboid(-5.5F, -3.0F, -1F, 11.0F, 13.0F, 5.0F, new CubeDeformation(0.19F)).mirrored(false),
                PartPose.pivot(0.0F, 3.0F, 0.0F));

        cape.addChild("cape_low", CubeListBuilder.create()
                .uv(92, 50).mirrored().cuboid(-5.5F, -2.8F, -1F, 11.0F, 13.0F, 5.0F, new CubeDeformation(0.2F)).mirrored(false)
                .uv(92, 112).mirrored().cuboid(-5.5F, -2.8F, -1F, 11.0F, 13.0F, 5.0F, new CubeDeformation(0.19F)).mirrored(false),
                PartPose.pivot(0.0F, 13.1558F, 0.0F));

        PartDefinition right_arm = modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));
        right_arm.addChild("right_arm_shoulder_cape", CubeListBuilder.create().uv(24, 16).cuboid(-4.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.1F)),
                PartPose.pivot(-0.0F, -0.0F, 0.0F));

        PartDefinition left_arm = modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));
        left_arm.addChild("left_arm_shoulder_cape", CubeListBuilder.create().uv(40, 16).mirrored().cuboid(0.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.1F)).mirrored(false),
                PartPose.pivot(0.0F, -0.0F, 0.0F));

        PartDefinition right_leg = modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));
        right_leg.addChild("right_leg", CubeListBuilder.create().uv(72, 16).cuboid(-2.1F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.1F)),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition left_leg = modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));
        left_leg.addChild("left_leg", CubeListBuilder.create().uv(56, 16).mirrored().cuboid(-1.968F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(1.1F)).mirrored(false),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 128, 128);
    }
}
