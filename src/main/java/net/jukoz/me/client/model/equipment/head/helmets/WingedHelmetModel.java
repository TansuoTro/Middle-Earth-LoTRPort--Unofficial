package net.jukoz.me.client.model.equipment.head.helmets;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class WingedHelmetModel extends HelmetAddonModel {

    public WingedHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        return LayerDefinition.of(modelData, 64, 64);
    }

    protected static MeshDefinition getModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition addon = head.addChild("addon", CubeListBuilder.create()
                .uv(0, 0).cuboid(-1.0F, -10.65F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        addon.addChild("wing_right", CubeListBuilder.create()
                .uv(0, 0).mirrored().cuboid(0.0F, -9.25F, -1.0F, 0.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(-5.0F, -6.0F, 0.0F, 0.0117F, -0.3229F, 0.0047F));

        addon.addChild("wing_left", CubeListBuilder.create()
                .uv(0, 0).cuboid(0.0F, -9.25F, -1.0F, 0.0F, 12.0F, 8.0F, new CubeDeformation(0.0F)),
                PartPose.of(5.0F, -6.0F, 0.0F, 0.0117F, 0.3229F, -0.0047F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return modelData;
    }
}
