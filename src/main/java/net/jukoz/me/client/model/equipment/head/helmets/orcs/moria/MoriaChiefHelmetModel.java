package net.jukoz.me.client.model.equipment.head.helmets.orcs.moria;

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

public class MoriaChiefHelmetModel extends HelmetAddonModel {

    public MoriaChiefHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("horizontal_crest", CubeListBuilder.create()
                .uv(31, 15).cuboid(-8.0F, -16.25F, 0.0F, 16.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("bottom_jaw", CubeListBuilder.create()
                .uv(0, 28).cuboid(-8.4497F, -9.75F, 2.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.3F)),
                PartPose.of(0.3498F, 4.5F, -8.9142F, 0.6603F, 0.6603F, 0.4439F));

        head.addChild("horns_right", CubeListBuilder.create()
                .uv(44, 32).cuboid(-7.0F, -10.25F, -4.0F, 3.0F, 5.0F, 7.0F, new CubeDeformation(0.0F))
                .uv(47, 50).cuboid(-7.0F, -5.25F, 0.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .uv(44, 55).mirrored().cuboid(-7.0F, -4.25F, -3.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(49, 45).mirrored().cuboid(-9.0F, -4.25F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("horns_left", CubeListBuilder.create()
                .uv(44, 32).mirrored().cuboid(4.0F, -10.25F, -4.0F, 3.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(47, 50).mirrored().cuboid(4.0F, -5.25F, 0.0F, 3.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(44, 55).cuboid(4.0F, -4.25F, -3.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .uv(49, 45).cuboid(7.0F, -4.25F, -3.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("top_spike", CubeListBuilder.create()
                .uv(0, 59).cuboid(-2.5F, -2.75F, 0.0F, 5.0F, 5.0F, 0.0F, new CubeDeformation(0.0F))
                .uv(0, 54).cuboid(0.0F, -2.75F, -2.5F, 0.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, -11.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

        head.addChild("eye_cover", CubeListBuilder.create()
                .uv(10, 60).cuboid(-4.5F, -4.55F, -4.5F, 9.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
