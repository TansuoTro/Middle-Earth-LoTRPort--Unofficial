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

public class CookingPotHelmetModel extends HelmetAddonModel {

    public CookingPotHelmetModel(ModelPart root) {
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

        head.addChild("Hat2", CubeListBuilder.create()
                .uv(0, 0).cuboid(-3.5F, -33.75F, -3.5F, 7.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(0.0F, 23.0F, 0.0F));

        head.addChild("handle2_r1", CubeListBuilder.create()
                .uv(1, -1).cuboid(0.0F, 0.75F, -1.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.of(5.0F, -6.3F, -0.4F, 0.0F, 0.0F, -0.3491F));

        head.addChild("handle1_r1", CubeListBuilder.create()
                .uv(1, -1).cuboid(0.0F, 0.75F, -1.0F, 0.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)),
                PartPose.of(-4.9F, -6.3F, -0.4F, 0.0F, 0.0F, 0.3491F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return modelData;
    }
}
