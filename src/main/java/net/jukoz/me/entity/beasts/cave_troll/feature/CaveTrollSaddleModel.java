package net.jukoz.me.entity.beasts.cave_troll.feature;

import net.minecraft.client.model.*;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntity;
import net.jukoz.me.entity.beasts.cave_troll.CaveTrollEntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class CaveTrollSaddleModel extends CaveTrollEntityModel {
    private final ModelPart reins;

    protected CaveTrollSaddleModel(ModelPart root) {
        super(root);

        reins = root.getChild("root").getChild("body_no_legs").getChild("body_no_limbs").getChild("Head").getChild("Saddle_Reigns");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        PartDefinition modelPartData = modelData.getRoot().getChild("root").getChild("body_no_legs").getChild("body_no_limbs");

        PartDefinition body = modelPartData.addChild("body", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition armor = body.addChild("armor", CubeListBuilder.create().uv(91, 51)
                .cuboid(-16.0F, -40.0F, -9.0F, 33.0F, 40.0F, 17.0F, new CubeDeformation(0.2F)), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition saddle_platform = modelPartData.addChild("saddle_platform", CubeListBuilder.create().uv(0, 0).cuboid(-7.5F, 1.0F, -7.5F, 15.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .uv(0, 77).cuboid(-7.5F, -17.0F, -7.5F, 15.0F, 18.0F, 16.0F,
                        new CubeDeformation(0.0F)), PartPose.pivot(0.5F, -47.0F, 6.5F));

        PartDefinition log2_r1 = saddle_platform.addChild("log2_r1", CubeListBuilder.create().uv(67, 25).cuboid(-1.5F, -1.5F, 3.0F, 4.0F, 4.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.of(0.4F, 4.5F, 9.0F, 3.1416F, 0.0F, 3.1416F));

        PartDefinition log1_r1 = saddle_platform.addChild("log1_r1", CubeListBuilder.create().uv(53, 0).cuboid(-1.5F, -1.5F, -10.0F, 4.0F, 4.0F, 20.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, 4.5F, -2.5F, 0.0F, -1.5708F, 0.0F));

        PartDefinition platform = modelPartData.addChild("platform", CubeListBuilder.create().uv(106, 15).cuboid(-15.5F, 12.0F, 1.5F, 31.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
                .uv(89, 28).cuboid(-19.0F, 10.0F, 1.6F, 38.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                .uv(77, 114).cuboid(-19.0F, 1.0F, 1.6F, 38.0F, 9.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.5F, -48.0F, 6.5F));

        PartDefinition log2_r2 = platform.addChild("log2_r2", CubeListBuilder.create().uv(111, 0).cuboid(-1.5F, -1.5F, -1.0F, 4.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.of(-7.5F, 13.5F, 14.5F, 3.1416F, 0.0F, 3.1416F));

        PartDefinition log1_r2 = platform.addChild("log1_r2", CubeListBuilder.create().uv(142, 0).cuboid(-1.5F, -1.5F, -1.0F, 4.0F, 4.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.of(8.5F, 13.5F, 14.5F, 3.1416F, 0.0F, 3.1416F));

        PartDefinition Head = modelPartData.addChild("Head", CubeListBuilder.create(), PartPose.pivot(0.5F, -34.5F, -8.5F));

        PartDefinition Saddle_Reigns = Head.addChild("Saddle_Reigns", CubeListBuilder.create(), PartPose.of(0.0F, 0.0F, 0.0F, -0.0873F, 0.0F, 0.0F));

        PartDefinition cube_r1 = Saddle_Reigns.addChild("cube_r1", CubeListBuilder.create().uv(0, 43).cuboid(-5.0F, -22.0F, -2.0F, 10.0F, 22.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, -5.0F, -4.0F, -0.6981F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 256, 256);
    }

    @Override
    public void setAngles(CaveTrollEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        reins.visible = entity.getControllingPassenger() != null;
    }
}
