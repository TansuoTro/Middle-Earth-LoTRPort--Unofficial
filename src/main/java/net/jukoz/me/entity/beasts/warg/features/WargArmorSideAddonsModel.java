package net.jukoz.me.entity.beasts.warg.features;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.jukoz.me.entity.beasts.warg.WargEntityRenderState;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class WargArmorSideAddonsModel extends WargArmorModel {

    private final ModelPart warg;
    public WargArmorSideAddonsModel(ModelPart root) {
        super(root);

        this.warg = root.getChild("root");
    }

    public static LayerDefinition getTexturedModelDataSideSkulls() {
        MeshDefinition modelData = getArmorModelData();
        PartDefinition front_armor = modelData.getRoot().getChild("root").getChild(PartNames.BODY).getChild("upper_body").getChild("body_no_legs").getChild("body_fur").getChild("body_armor").getChild("front_armor");

        PartDefinition addons_front_armor = front_armor.addChild("addons_front_armor", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        PartDefinition skulls = addons_front_armor.addChild("skulls", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 1.0F));
        PartDefinition skull_01 = skulls.addChild("skull_01", CubeListBuilder.create(), PartPose.of(0.092F, 2.1315F, 6.8137F, -2.7489F, -0.3054F, 3.1416F));
        PartDefinition cube_r1 = skull_01.addChild("cube_r1", CubeListBuilder.create().uv(0, 0).cuboid(-1.0F, -6.0F, 0.0F, 2.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.of(-2.0F, -2.0F, 0.0F, -0.7969F, -0.0393F, 0.3624F));
        PartDefinition skull_hat_r1 = skull_01.addChild("skull_hat_r1", CubeListBuilder.create().uv(24, 0).cuboid(-3.0F, -3.5F, -3.0F, 6.0F, 8.0F, 6.0F, new CubeDeformation(-0.75F)), PartPose.of(-2.0F, 0.0F, 0.0F, -0.2182F, 0.2182F, 0.0F));
        PartDefinition skull_r1 = skull_01.addChild("skull_r1", CubeListBuilder.create().uv(0, 0).cuboid(-3.0F, -3.5F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(-1.0F)), PartPose.of(-2.0F, 0.0F, 0.0F, -0.2182F, 0.2182F, 0.0F));
        PartDefinition skull_02 = skulls.addChild("skull_02", CubeListBuilder.create(), PartPose.of(-0.6F, 3.5F, 5.0F, 0.0F, 3.098F, 0.0F));
        PartDefinition cube_r2 = skull_02.addChild("cube_r2", CubeListBuilder.create().uv(0, 32).cuboid(-1.0F, -6.0F, 0.0F, 2.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.of(1.692F, -2.8685F, -1.8863F, -0.4037F, -0.2145F, -0.1479F));
        PartDefinition skull_hat_r2 = skull_02.addChild("skull_hat_r2", CubeListBuilder.create().uv(24, 32).cuboid(-3.0F, -3.5F, -3.0F, 6.0F, 9.0F, 6.0F, new CubeDeformation(-0.75F))
                .uv(0, 32).cuboid(-3.0F, -3.5F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(-1.0F)), PartPose.of(1.5129F, -0.8639F, -1.0609F, 0.1309F, -0.1745F, 0.0F));
        skull_02.addChild("back_body_no_legs", CubeListBuilder.create(), PartPose.pivot(-4.5F, -3.0F, -6.0F));

        return LayerDefinition.of(modelData, 128, 128);
    }
}
