package net.jukoz.me.client.model.equipment.head.helmets.humans.dale;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class DaleFeatherHelmetModel extends DaleHelmetModel {

    public DaleFeatherHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("spike", CubeListBuilder.create()
                .uv(0, 0).cuboid(-0.5F, -0.75F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, -10.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

        PartDefinition feather_duster = head.addChild("feather_duster", CubeListBuilder.create(),
                PartPose.pivot(0.0F, -11.0F, 0.0F));

        PartDefinition plumes = feather_duster.addChild("plumes", CubeListBuilder.create(),
                PartPose.of(0.0F, 0.0F, 0.0F, -0.1309F, 0.0F, 0.0F));

        PartDefinition plume_0 = plumes.addChild("plumes_0", CubeListBuilder.create()
                .uv(0, 51).cuboid(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, 0.0F, 0.0F, -2.1369F, -0.5338F, 1.8835F));
        plume_0.addChild("plume_0", CubeListBuilder.create()
                .uv(0, 45).cuboid(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition plume_1 = plumes.addChild("plumes_1", CubeListBuilder.create()
                .uv(14, 51).cuboid(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, 0.0F, 0.0F, -0.3841F, -0.761F, 0.4898F));
        plume_1.addChild("plume_1", CubeListBuilder.create()
                .uv(14, 45).cuboid(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition plume_2 = plumes.addChild("plumes_2", CubeListBuilder.create()
                .uv(28, 51).cuboid(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, 0.0F, 0.0F, -2.8254F, -0.5049F, 2.4806F));
        plume_2.addChild("plume_2", CubeListBuilder.create()
                .uv(28, 45).cuboid(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition plume_3 = plumes.addChild("plumes_3", CubeListBuilder.create()
                .uv(42, 51).cuboid(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, 0.0F, 0.0F, -2.7925F, 0.5672F, 3.1416F));
        plume_3.addChild("plume_3", CubeListBuilder.create()
                .uv(42, 45).cuboid(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition plume_4 = plumes.addChild("plumes_4", CubeListBuilder.create()
                .uv(42, 39).cuboid(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, 0.0F, 0.0F, -1.7964F, -0.9478F, 1.7551F));
        plume_4.addChild("plume_4", CubeListBuilder.create()
                .uv(42, 33).cuboid(0.0F, -5.25F, -3.0F, 0.0F, 6.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, -6.0F, 0.0F, 0.0F, 0.0F, 0.5236F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
