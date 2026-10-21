package net.jukoz.me.client.model.equipment.head.helmetAttachments.armored;

import net.jukoz.me.client.model.equipment.head.helmetAttachments.CloakHoodModel;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class FurHoodDownModel extends CloakHoodModel {

    public FurHoodDownModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        PartDefinition hat = head.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition hood = hat.addChild("helmetAttachment", CubeListBuilder.create().uv(0, 73).cuboid(-4.0F, -9.0F, -4.376F, 8.0F, 5.0F, 2.0F, new CubeDeformation(0.1F)), PartPose.pivot(0.0F, -0.0F, 0.0F));

        PartDefinition furHood = hood.addChild("fur_hood", CubeListBuilder.create(), PartPose.of(0.0F, -6.5F, -3.5F, 1.5708F, 0.0F, 0.0F));

        PartDefinition down = furHood.addChild("down", CubeListBuilder.create().uv(96, 0).cuboid(-4.0F, -6.0F, 0.75F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.3F))
                .uv(64, 0).cuboid(-4.0F, -5.91F, 0.75F, 8.0F, 8.0F, 8.0F, new CubeDeformation(1.0F)), PartPose.of(0.0F, 0.0F, -6.0F, -1.1345F, 0.0F, 0.0F));

        PartDefinition hoodDown = down.addChild("hood_down", CubeListBuilder.create(), PartPose.of(-0.144F, 4.2857F, -1.716F, -1.5708F, 0.0F, 0.0F));

        hoodDown.addChild("top", CubeListBuilder.create().uv(112, 29).cuboid(-2.872F, -11.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.5F))
                .uv(112, 26).cuboid(-2.856F, -11.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.6F)), PartPose.pivot(0.0F, 0.0F, 0.0F));
        hoodDown.addChild("bottom", CubeListBuilder.create().uv(112, 20).cuboid(-2.75F, -2.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.5F))
                .uv(112, 23).cuboid(-2.718F, -2.2857F, -5.3F, 6.0F, 1.0F, 2.0F, new CubeDeformation(0.6F)), PartPose.pivot(0.0F, 0.0F, 0.0F));
        hoodDown.addChild("left", CubeListBuilder.create().uv(100, 20).cuboid(3.6F, -11.2857F, -5.3F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.5F))
                .uv(94, 20).cuboid(3.6F, -11.2857F, -5.3F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.6F)), PartPose.pivot(0.25F, 0.0F, 0.0F));
        hoodDown.addChild("right", CubeListBuilder.create().uv(100, 20).mirrored().cuboid(-4.35F, -11.2857F, -5.3F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.5F)).mirrored(false)
                .uv(106, 20).cuboid(-4.35F, -11.2857F, -5.3F, 1.0F, 8.0F, 2.0F, new CubeDeformation(0.6F)), PartPose.pivot(-0.25F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 128, 128);
    }
}
