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

public class WoodlandRealmRoyalGuardHelmetModel extends HelmetAddonModel {
    private static final float Y_OFFSET = 0.75f;

    public WoodlandRealmRoyalGuardHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition addon = head.addChild("addon", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition centralSpine = addon.addChild("central_spine", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = centralSpine.addChild("cube_r1", CubeListBuilder.create().uv(0, 45)
                .cuboid(-5.0F, 2.0F, 0.0F, 1.0F, 10.0F, 9.0F, new CubeDeformation(0.1F)),
                PartPose.of(4.5F, -2.25F, -7.0F, 1.5708F, 0.0F, 0.0F));

        PartDefinition fin = addon.addChild("fin", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition fin1 = fin.addChild("fin_1", CubeListBuilder.create().uv(0, 0)
                .cuboid(-11.0F, -2.0F + Y_OFFSET, -4.0F, 21.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.5F, -6.25F, -0.388F, 0.6021F, 0.0F, 0.0F));

        PartDefinition lowerExtension = addon.addChild("lower_extension", CubeListBuilder.create().uv(0, 11)
                .cuboid(-4.0F, -13.8F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.5F)).uv(32, 11)
                .cuboid(-4.0F, -10.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.9F)), PartPose.pivot(0.0F, 10.8F, 0.0F));

        head.addChild("addon_eye", CubeListBuilder.create()
                        .uv(46, 63).cuboid(-4.5F, -3.55F, -4.475F, 9.0F, 1.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
