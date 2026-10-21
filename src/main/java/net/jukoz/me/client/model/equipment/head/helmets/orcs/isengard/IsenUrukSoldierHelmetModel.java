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

public class IsenUrukSoldierHelmetModel extends IsenUrukCrestHelmetModel {

    public IsenUrukSoldierHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();

        PartDefinition head = modelData.getRoot().addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition side_spikes = head.addChild("side_spikes", CubeListBuilder.create(),
                PartPose.pivot(6.75F, -1.65F, -1.0F));

        side_spikes.addChild("spikes_right", CubeListBuilder.create()
                        .uv(43, 53).cuboid(-2.7F, 2.25F, -7.0F, 5.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.5672F));
        side_spikes.addChild("spikes_left", CubeListBuilder.create()
                        .uv(43, 53).mirrored().cuboid(-2.3F, 2.25F, -7.0F, 5.0F, 0.0F, 11.0F, new CubeDeformation(0.0F)).mirrored(false),
                PartPose.of(-13.5F, 0.0F, 0.0F, 0.0F, 0.0F, -0.5672F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
