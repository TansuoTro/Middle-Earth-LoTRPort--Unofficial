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

public class WargArmorBaseAddonsModel extends WargArmorModel {
    private final ModelPart warg;
    public WargArmorBaseAddonsModel(ModelPart root) {
        super(root);
        this.warg = root.getChild("root");
    }

    public static LayerDefinition getTexturedModelDataSpine() {
        MeshDefinition modelData = getArmorModelData();
        PartDefinition front_armor = modelData.getRoot().getChild("root").getChild(PartNames.BODY).getChild("upper_body").getChild("body_no_legs").getChild("body_fur").getChild("body_armor").getChild("front_armor");

        PartDefinition addons_front_armor = front_armor.addChild("addons_front_armor", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition spine = addons_front_armor.addChild("spine", CubeListBuilder.create().uv(8, 0).cuboid(2.0F, -2.4F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(16, 0).cuboid(-1.0F, -2.1F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, -6.0F, 0.0F));
        PartDefinition spine_04_r1 = spine.addChild("spine_04_r1", CubeListBuilder.create().uv(24, 0).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(-3.0F, -0.7F, 0.0F, 0.0F, 0.0F, 0.0873F));
        PartDefinition spine_01_r1 = spine.addChild("spine_01_r1", CubeListBuilder.create().uv(0, 0).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(6.0F, -1.2F, 0.0F, 0.0F, 0.0F, 0.1309F));

        return LayerDefinition.of(modelData, 128, 128);
    }
}
