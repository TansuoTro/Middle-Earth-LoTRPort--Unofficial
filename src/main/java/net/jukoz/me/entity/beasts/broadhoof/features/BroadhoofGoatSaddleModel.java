package net.jukoz.me.entity.beasts.broadhoof.features;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BroadhoofGoatSaddleModel extends BroadhoofGoatModel {
    private final ModelPart reins;
    public BroadhoofGoatSaddleModel(ModelPart root) {
        super(root);

        this.reins = root.getChild("broadhoof_goat").getChild(PartNames.BODY).getChild(PartNames.HEAD).getChild("saddle_things").getChild("reins");
    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        PartDefinition root = modelData.getRoot().getChild("broadhoof_goat").getChild(PartNames.BODY);
        PartDefinition head = root.getChild(PartNames.HEAD);

        PartDefinition saddle = root.addChild("saddle", CubeListBuilder.create().uv(74, 19).cuboid(-7.0F, -23.0F, 13.0F, 14.0F, 14.0F, 13.0F, new CubeDeformation(0.3F))
                .uv(92, 72).cuboid(-7.0F, -25.0F, 25.0F, 14.0F, 4.0F, 4.0F, new CubeDeformation(0.5F)), PartPose.pivot(0.0F, 12.0F, -21.0F));

        PartDefinition saddle_things = head.addChild("saddle_things", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition reins = saddle_things.addChild("reins", CubeListBuilder.create().uv(81, -3).mirrored().cuboid(-4.1F, -7.0F, -8.0F, 0.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(81, -3).cuboid(4.1F, -7.0F, -8.0F, 0.0F, 3.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, -2.0F, 1.0F, 0.48F, 0.0F, 0.0F));

        PartDefinition mouth_things = saddle_things.addChild("mouth_things", CubeListBuilder.create().uv(62, 50).mirrored().cuboid(-2.0F, -3.0F, 1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(62, 50).cuboid(5.0F, -3.0F, 1.0F, 1.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(66, 47).cuboid(-1.0F, -7.0F, -1.0F, 6.0F, 7.0F, 11.0F, new CubeDeformation(0.2F)), PartPose.of(-2.0F, 0.0F, -8.0F, 0.7854F, 0.0F, 0.0F));
        return LayerDefinition.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(BroadhoofGoatEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        this.reins.visible = entity.getControllingPassenger() != null;
    }
}
