package net.jukoz.me.client.model.equipment.chest;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class HaltChestplateModel extends ChestplateAddonModel {

    public HaltChestplateModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        return LayerDefinition.of(modelData, 64, 64);
    }

    public static MeshDefinition getModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition right_arm = modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        right_arm.addChild("right_halt", CubeListBuilder.create()
                .uv(0, 50).cuboid(-1.0F, -6.25F, -3.5F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition left_arm = modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        left_arm.addChild("left_halt", CubeListBuilder.create()
                .uv(17, 50).cuboid(11.0F, -6.25F, -3.5F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(-10.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return modelData;
    }
}
