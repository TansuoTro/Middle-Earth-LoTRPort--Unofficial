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

public class SilvanLordHelmetModel extends HelmetAddonModel {
    private static final float Y_OFFSET = 0.75f;

    public SilvanLordHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition addon = head.addChild("addon", CubeListBuilder.create(), PartPose.pivot(0.0F, 1.75F, -0.25F));

        addon.addChild("central_spine", CubeListBuilder.create().uv(0, 45).cuboid(-5.0F, 2.0F + Y_OFFSET, 0.0F, 1.0F, 10.0F, 9.0F,
                new CubeDeformation(0.1F)), PartPose.of(4.5F, -3.25F, -7.5F, 1.5708F, 0.0F, 0.0F));

        PartDefinition crest = addon.addChild("crest", CubeListBuilder.create(), PartPose.pivot(0.0F, -0.5F, 1.08F));

        PartDefinition crest_left = crest.addChild("crest_left", CubeListBuilder.create().uv(46, 51)
                .cuboid(-3.0F, -6.0F + Y_OFFSET, 0.0F, 9.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.of(2.654F, -15.0819F, -2.0603F, -0.4363F, -0.6109F, 0.2094F));

        PartDefinition crest_right = crest.addChild("crest_right", CubeListBuilder.create().uv(46, 51)
                .mirrored().cuboid(-5.0F, -6.0F + Y_OFFSET, 0.0F, 9.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(-3.404F, -14.8899F, -1.4963F, -0.4363F, 0.6109F, -0.2094F));

        PartDefinition fin = addon.addChild("fin", CubeListBuilder.create(), PartPose.pivot(0.0F, -0.5F, 1.08F));

        PartDefinition fin_1 = fin.addChild("fin_1", CubeListBuilder.create().uv(0, 0)
                .cuboid(-11.0F, -2.0F + Y_OFFSET, -3.75F, 21.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.5F, -7.25F, -0.968F, 0.6021F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
