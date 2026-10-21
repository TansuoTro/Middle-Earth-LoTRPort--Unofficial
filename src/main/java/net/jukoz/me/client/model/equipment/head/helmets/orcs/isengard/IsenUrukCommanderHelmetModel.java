package net.jukoz.me.client.model.equipment.head.helmets.orcs.isengard;

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

public class IsenUrukCommanderHelmetModel extends IsenUrukHelmetModel {

    public IsenUrukCommanderHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();

        PartDefinition head = modelData.getRoot().addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("large_fan", CubeListBuilder.create()
                        .uv(0, 17).cuboid(-13.0F, -6.75F, 0.0F, 26.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, -10.875F, 2.3F, -0.4363F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
