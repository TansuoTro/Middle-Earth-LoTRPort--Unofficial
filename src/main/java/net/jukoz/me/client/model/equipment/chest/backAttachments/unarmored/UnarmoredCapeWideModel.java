package net.jukoz.me.client.model.equipment.chest.backAttachments.unarmored;

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

public class UnarmoredCapeWideModel extends CloakCapeModel {

    public UnarmoredCapeWideModel(ModelPart root) {
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
        body.addChild("cape_shoulder", CubeListBuilder.create().uv(0, 16).cuboid(-4.0F, -23.5F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.55F)),
                PartPose.pivot(0.0F, 23.0F, 0.016F));

        PartDefinition cape = body.addChild("cape", CubeListBuilder.create()
                .uv(0, 32).mirrored().cuboid(-9F, -0.5F, -3F, 18.0F, 13.0F, 6.0F, new CubeDeformation(0.4F)).mirrored(false)
                .uv(0, 94).mirrored().cuboid(-9F, -0.5F, -3F, 18.0F, 13.0F, 6.0F, new CubeDeformation(0.39F)).mirrored(false),
                PartPose.pivot(0.0F, 0.0f, 0.0f));

        cape.addChild("cape_low", CubeListBuilder.create()
                .uv(0, 51).mirrored().cuboid(-9F, -0.3F, -3F, 18.0F, 13.0F, 6.0F, new CubeDeformation(0.4F)).mirrored(false)
                .uv(0, 113).mirrored().cuboid(-9F, -0.3F, -3F, 18.0F, 13.0F, 6.0F, new CubeDeformation(0.39F)).mirrored(false),
                PartPose.pivot(0.0f, 13.3158f, 0.0f));

        PartDefinition right_arm = modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));
        right_arm.addChild("right_arm_shoulder_cape", CubeListBuilder.create().uv(24, 16).cuboid(-4.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.35F)),
                PartPose.pivot(-0.0F, -0.0F, 0.0F));

        PartDefinition left_arm = modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));
        left_arm.addChild("left_arm_shoulder_cape", CubeListBuilder.create().uv(40, 16).mirrored().cuboid(0.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.35F)).mirrored(false),
                PartPose.pivot(0.0F, -0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 128, 128);
    }
}
