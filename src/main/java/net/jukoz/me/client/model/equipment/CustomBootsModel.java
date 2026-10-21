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

public class CustomBootsModel extends MEBipedEntityModel<LivingEntity, BipedEntityRenderState> {

    public CustomBootsModel(ModelPart root) {
        super(root, RenderLayer::getArmorCutoutNoCull);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition rightLeg = modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        rightLeg.addChild("rightBoot", CubeListBuilder.create()
                        .uv(0, 48).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.9F)),
                PartPose.pivot(0.0F, 0.0F, 0.0F));
        rightLeg.addChild("rightInnerBoot", CubeListBuilder.create()
                        .uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.7F)),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition leftLeg = modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        leftLeg.addChild("leftBoot", CubeListBuilder.create()
                        .uv(16, 48).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.9F)).mirrored(false),
                PartPose.pivot(0.0F, 0.0F, 0.0F));
        leftLeg.addChild("leftInnerBoot", CubeListBuilder.create()
                        .uv(0, 32).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.7F)).mirrored(false),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }
}
