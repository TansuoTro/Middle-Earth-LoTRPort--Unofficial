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

public class WargArmorTopAddonsModel extends WargArmorModel {

    private final ModelPart warg;
    public WargArmorTopAddonsModel(ModelPart root) {
        super(root);
        this.warg = root.getChild("root");
    }

    public static LayerDefinition getTexturedModelDataFront() {
        MeshDefinition modelData = getArmorModelData();
        PartDefinition body_armor = modelData.getRoot().getChild("root").getChild(PartNames.BODY).getChild("upper_body").getChild("body_no_legs").getChild("body_fur").getChild("body_armor");

        PartDefinition armor_addons = body_armor.addChild("armor_addons", CubeListBuilder.create(), PartPose.pivot(-5.0F, -5.0F, -4.0F));
        PartDefinition front_addons = armor_addons.addChild("front_addons", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        PartDefinition skull2 = front_addons.addChild("skull2", CubeListBuilder.create().uv(6, 43).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.1F))
                .uv(79, 48).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.2F))
                .uv(6, 30).cuboid(-3.0F, -4.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F))
                .uv(6, 60).cuboid(-1.0F, -6.0F, -1.0F, 2.0F, 11.0F, 2.0F, new CubeDeformation(-0.1F)), PartPose.of(13.0F, -4.0F, 3.0F, 0.0F, -1.5708F, 0.0F));

        return LayerDefinition.of(modelData, 128, 128);
    }

    public static LayerDefinition getTexturedModelDataBack() {
        MeshDefinition modelData = getArmorModelData();
        PartDefinition body_armor = modelData.getRoot().getChild("root").getChild(PartNames.BODY).getChild("upper_body").getChild("body_no_legs").getChild("body_fur").getChild("body_armor");

        PartDefinition armor_addons = body_armor.addChild("armor_addons", CubeListBuilder.create(), PartPose.pivot(-5.0F, -5.0F, -4.0F));

        PartDefinition back_addons = armor_addons.addChild("back_addons", CubeListBuilder.create().uv(0, 0).cuboid(-1.0F, -2.0F, -2.0F, 2.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                .uv(0, 12).cuboid(-1.0F, -9.0F, -1.0F, 2.0F, 7.0F, 8.0F, new CubeDeformation(0.0F))
                .uv(6, 60).cuboid(-1.0F, -23.0F, 2.0F, 2.0F, 23.0F, 2.0F, new CubeDeformation(-0.1F))
                .uv(20, 63).cuboid(-1.0F, -27.0F, 3.0F, 2.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = back_addons.addChild("cube_r1", CubeListBuilder.create().uv(4, 99).cuboid(-16.0F, -8.5F, 0.0F, 17.0F, 12.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.of(-2.0F, -21.5F, 3.0F, 0.0F, 0.0F, -1.4399F));

        PartDefinition cube_r2 = back_addons.addChild("cube_r2", CubeListBuilder.create().uv(19, 81).cuboid(-1.0F, -8.5F, -1.0F, 2.0F, 12.0F, 2.0F, new CubeDeformation(-0.3F)), PartPose.of(-2.0F, -21.5F, 3.0F, 0.0F, 0.0F, -1.5708F));

        PartDefinition skull = back_addons.addChild("skull", CubeListBuilder.create().uv(6, 43).cuboid(-3.0F, -3.0F, -3.0F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.1F))
                .uv(6, 30).cuboid(-3.0F, -3.0F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, -11.0F, 3.0F, 0.0F, -1.5708F, 0.0F));
        return LayerDefinition.of(modelData, 128, 128);
    }
}
