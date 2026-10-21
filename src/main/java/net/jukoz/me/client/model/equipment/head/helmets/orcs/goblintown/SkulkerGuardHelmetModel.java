package net.jukoz.me.client.model.equipment.head.helmets.orcs.goblintown;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.jukoz.me.client.model.equipment.head.helmets.EggHelmetModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class SkulkerGuardHelmetModel extends EggHelmetModel {

    public SkulkerGuardHelmetModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        PartDefinition head = modelData.getRoot().addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("large_crest", CubeListBuilder.create()
                        .texOffs(28, -18).addBox(0.0F, -15.25F, -7.0F, 0.0F, 14.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        head.addOrReplaceChild("mandible_left", CubeListBuilder.create()
                        .texOffs(2, 15).addBox(0.0F, -8.25F, -6.0F, 0.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(4.0F, 2.0F, -4.0F, 0.0F, -0.4363F, 0.0F));
        head.addOrReplaceChild("mandible_right", CubeListBuilder.create()
                        .texOffs(2, 15).addBox(0.0F, -8.25F, -6.0F, 0.0F, 9.0F, 6.0F, new CubeDeformation(0.0F)),
                PartPose.offsetAndRotation(-4.0F, 2.0F, -4.0F, 0.0F, 0.4363F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
