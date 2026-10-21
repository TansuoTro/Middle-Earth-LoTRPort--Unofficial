package net.jukoz.me.client.model.equipment;

import net.jukoz.me.compat.client.render.MEBipedEntityModel;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.PartNames;
import net.jukoz.me.compat.client.render.state.BipedEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class CustomChestplateModel extends MEBipedEntityModel<LivingEntity, BipedEntityRenderState> {

    public CustomChestplateModel(ModelPart root) {
        super(root, RenderLayer::getArmorCutoutNoCull);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition body = modelPartData.addChild(PartNames.BODY, CubeListBuilder.create()
                        .uv(16, 32).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.8F)),
                PartPose.pivot(0.0F, -23.0F, 0.0F));

        body.addChild("innerChest", CubeListBuilder.create()
                        .uv(16, 16).cuboid(-4.0F, -0.0F, -2.068F, 8.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.pivot(0.0F, -0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create()
                        .uv(32, 48).cuboid(-3.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.9F))
                        .uv(40, 16).cuboid(-3.0F, -2.3F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.6F)),
                PartPose.pivot(-5.0F, -21.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create()
                        .uv(48, 48).mirrored().cuboid(-1.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.9F)).mirrored(false)
                        .uv(40, 32).mirrored().cuboid(-1.0F, -2.3F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.6F)).mirrored(false),
                PartPose.pivot(5.0F, -21.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create()
                .uv(0, 16).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)),
                PartPose.pivot(-1.9F, -11.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create()
                .uv(0, 32).mirrored().cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.5F)).mirrored(false),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
