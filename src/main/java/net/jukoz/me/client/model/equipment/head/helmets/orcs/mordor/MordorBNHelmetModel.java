package net.jukoz.me.client.model.equipment.head.helmets.orcs.mordor;

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

public class MordorBNHelmetModel extends HelmetAddonModel {

    public final ModelPart addons;

    public MordorBNHelmetModel(ModelPart root) {
        super(root);
        addons = root.getChild("head").getChild("addons");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition addons = head.addChild("addons", CubeListBuilder.create().
                        uv(0, 0).cuboid(-3.5F, -11.75F, -3.5F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F))
                        .uv(0, 10).cuboid(-1.5F, -13.25F, -0.5F, 3.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                        .uv(40, 3).cuboid(0.0F, -17.10F, -6.1F, 0.0F, 17.0F, 12.0F, new CubeDeformation(0.0F))
                        .uv(46, 63).cuboid(-4.5F, -3.55F, -4.475F, 9.0F, 1.0F, 0.0F, new CubeDeformation(0.0F))
                        .uv(0, 15).cuboid(-4.0F, -9.25F, -4.0F, 8.0F, 9.0F, 8.0F, new CubeDeformation(0.5F)),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        addons.addChild("side_left", CubeListBuilder.create()
                        .uv(40, 0).mirrored().cuboid(-0.5F, -9.75F, 0.284F, 6.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(4.814F, -7.0F, -1.5F, 0.0F, -0.6545F, 0.0F));

        addons.addChild("side_right", CubeListBuilder.create()
                        .uv(52, 0).cuboid(-5.25F, -9.75F, 0.252F, 6.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.of(-5.0F, -7.0F, -1.25F, 0.0F, 0.6545F, 0.0F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        return LayerDefinition.of(modelData, 64, 64);
    }
}
