package net.jukoz.me.client.model.equipment.head.helmets.orcs.isengard;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class IsenUrukReinforcedHelmetModel extends IsenUrukCrestHelmetModel {

    public IsenUrukReinforcedHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();

        PartDefinition head = modelData.getRoot().addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("flaps", CubeListBuilder.create()
                        .uv(35, 3).cuboid(-4.0F, 0.75F, -4.0F, 8.0F, 5.0F, 5.0F, new CubeDeformation(0.55F)),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("snout", CubeListBuilder.create()
                .uv(0, 13).cuboid(-4.0F, -0.25F, -1.5F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, -2.8F, -4.5F, 0.5236F, 0.0F, 0.0F));

        PartDefinition helmet_sides = head.addChild("helmet_sides", CubeListBuilder.create(),
                PartPose.pivot(0.0F, -1.0F, 0.0F));

        helmet_sides.addChild("side_right", CubeListBuilder.create()
                .uv(15, 53).mirrored().cuboid(-2.3F, 2.25F, -7.0F, 5.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(-6.75F, -1.65F, 1.0F, 0.0F, 0.0F, -0.5672F));

        helmet_sides.addChild("side_left", CubeListBuilder.create()
                .uv(15, 53).cuboid(-2.7F, 2.25F, -7.0F, 5.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)),
                PartPose.of(6.75F, -1.65F, 1.0F, 0.0F, 0.0F, 0.5672F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
