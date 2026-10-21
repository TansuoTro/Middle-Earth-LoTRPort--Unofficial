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

public class BroadhoofGoatArmorModel extends BroadhoofGoatModel {

    private final ModelPart broadhoofGoat;
    private final ModelPart head;

    public BroadhoofGoatArmorModel(ModelPart root) {
        super(root);

        this.broadhoofGoat = root.getChild("broadhoof_goat");
        this.head = broadhoofGoat.getChild(PartNames.BODY).getChild(PartNames.HEAD);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        PartDefinition root = modelData.getRoot().getChild("broadhoof_goat").getChild(PartNames.BODY);
        PartDefinition head = root.getChild(PartNames.HEAD);

        PartDefinition head_armor = head.addChild("head_armor", CubeListBuilder.create().uv(0, 1).cuboid(0.9F, -11.0F, 2.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.1F))
                .uv(0, 1).mirrored().cuboid(-7.9F, -11.0F, 2.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.1F)).mirrored(false), PartPose.pivot(2.0F, 0.0F, -8.0F));

        PartDefinition neck_plate_r1 = head_armor.addChild("neck_plate_r1", CubeListBuilder.create().uv(85, 5).cuboid(-5.0F, -7.0F, 4.0F, 6.0F, 14.0F, 6.0F, new CubeDeformation(0.1F))
                .uv(44, 0).cuboid(-5.0F, -7.0F, -1.0F, 6.0F, 6.0F, 11.0F, new CubeDeformation(0.1F)), PartPose.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition side_hanging_plate_r1 = head_armor.addChild("side_hanging_plate_r1", CubeListBuilder.create().uv(77, 53).cuboid(-5.0F, -4.0F, -1.0F, 6.0F, 5.0F, 11.0F, new CubeDeformation(0.1F)), PartPose.of(0.0F, 2.0F, 2.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition body_armor = root.addChild("body_armor", CubeListBuilder.create().uv(0, 0).cuboid(-6.0F, -3.0F, -20.0F, 12.0F, 11.0F, 20.0F, new CubeDeformation(0.2F))
                .uv(0, 89).cuboid(-6.0F, 2.0F, -20.0F, 12.0F, 6.0F, 20.0F, new CubeDeformation(0.1F))
                .uv(0, 31).cuboid(-7.0F, -5.0F, -21.0F, 14.0F, 16.0F, 13.0F, new CubeDeformation(0.2F))
                .uv(0, 60).cuboid(-7.0F, -5.0F, -21.0F, 14.0F, 16.0F, 13.0F, new CubeDeformation(0.1F))
                .uv(108, 113).cuboid(6.0F, -2.0F, -7.0F, 3.0F, 8.0F, 7.0F, new CubeDeformation(0.0F))
                .uv(108, 98).cuboid(-9.0F, -2.0F, -7.0F, 3.0F, 8.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, -6.0F, 9.0F));
        return LayerDefinition.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(BroadhoofGoatEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
    }
}
