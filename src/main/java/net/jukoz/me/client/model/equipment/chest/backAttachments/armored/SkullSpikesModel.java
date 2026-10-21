package net.jukoz.me.client.model.equipment.chest.backAttachments.armored;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.jukoz.me.client.model.equipment.chest.ChestplateAddonModel;
import net.jukoz.me.client.model.equipment.chest.backAttachments.CloakCapeModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class SkullSpikesModel extends ChestplateAddonModel {

    public SkullSpikesModel(ModelPart root) {
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

        PartDefinition spears = body.addChild("spears", CubeListBuilder.create(),
                PartPose.pivot(0.0F, 4.0F, -0.5F));

        PartDefinition rightspear = spears.addChild("right_spear_0", CubeListBuilder.create(),
                PartPose.of(-1.5F, 7.0F, 2.7F, -0.1745F, 0.0F, -0.3491F));
        rightspear.addChild("skull_right_0", CubeListBuilder.create().uv(40, 0).cuboid(-3.0F, -41.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
                .uv(40, 13).cuboid(-3.0F, -41.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.2F)),
                PartPose.pivot(0.0F, 10.0F, -0.2F));
        rightspear.addChild("skull_right_1", CubeListBuilder.create().uv(40, 0).cuboid(-3.0F, -3.5F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
                .uv(40, 13).cuboid(-3.0F, -3.5F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.2F)),
                PartPose.of(0.0F, -18.5F, -0.2F, 0.0F, 0.5236F, 0.0F));
        rightspear.addChild("cloth_right", CubeListBuilder.create().uv(24, 0).cuboid(-1.0F, -1.5F, 0.0F, 2.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.of(-0.3181F, -14.5411F, 1.0122F, 0.1745F, 0.0F, 0.3491F));

        PartDefinition spears2 = rightspear.addChild("right_spear_1", CubeListBuilder.create(),
                PartPose.pivot(-2.5F, -0.5F, -3.7F));
        spears2.addChild("right_spear_2", CubeListBuilder.create().uv(16, 0).mirrored().cuboid(1.4329F, -23.6652F, 2.6678F, 2.0F, 31.0F, 2.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(0.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition leftspear = spears.addChild("left_spear_0", CubeListBuilder.create(),
                PartPose.of(1.5F, 7.0F, 2.7F, -0.1745F, 0.0F, 0.3491F));
        leftspear.addChild("skull_left_0", CubeListBuilder.create().uv(40, 0).cuboid(-3.0F, -3.5F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
                .uv(40, 13).cuboid(-3.0F, -3.5F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.2F)),
                PartPose.of(0.0F, -27.5F, -0.2F, 0.0F, 0.5236F, 0.0F));
        leftspear.addChild("rib_left", CubeListBuilder.create().uv(39, 51).cuboid(-4.0F, -5.4F, -2.0F, 8.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(0.0F, -18.5F, -0.2F));
        leftspear.addChild("cloth_left", CubeListBuilder.create().uv(24, 0).mirrored().cuboid(-1.0F, -1.5F, 0.0F, 2.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(0.6819F, -19.5411F, 1.0122F, 0.1745F, 0.0F, -0.3491F));

        PartDefinition spears6 = leftspear.addChild("left_spear_1", CubeListBuilder.create(),
                PartPose.pivot(-2.5F, -0.5F, -3.7F));
        spears6.addChild("left_spear_2", CubeListBuilder.create().uv(8, 0).mirrored().cuboid(1.4329F, -23.6652F, 2.6678F, 2.0F, 31.0F, 2.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(0.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition centrespear = spears.addChild("center_spear_0", CubeListBuilder.create(),
                PartPose.of(0.0F, 3.0F, 2.7F, -0.1309F, 0.0F, 0.0F));
        centrespear.addChild("skull_center", CubeListBuilder.create().uv(40, 0).cuboid(-3.0F, -41.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
                .uv(40, 13).cuboid(-3.0F, -41.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.2F)),
                PartPose.pivot(0.0F, 10.0F, -0.2F));
        centrespear.addChild("rib_center", CubeListBuilder.create().uv(36, 38).cuboid(-5.0F, -5.4F, -1.0F, 10.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(0.0F, -18.5F, -0.2F));

        PartDefinition spears3 = centrespear.addChild("center_spear_1", CubeListBuilder.create(),
                PartPose.pivot(-2.5F, -0.5F, -3.7F));
        spears3.addChild("center_spear_2", CubeListBuilder.create().uv(0, 0).mirrored().cuboid(1.4329F, -23.6652F, 2.6678F, 2.0F, 35.0F, 2.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(0.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
