package net.jukoz.me.client.model.equipment;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class InnerArmorModel<T extends LivingEntity> extends HumanoidModel<T> {

    public InnerArmorModel(ModelPart root) {
        super(root, RenderLayer::getArmorCutoutNoCull);
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create().uv(16, 16)
                        .cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new CubeDeformation(0.8f)),
                PartPose.pivot(0.0f, 0.0f, 0.0f));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create().uv(40, 16)
                        .cuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.5F)),
                PartPose.pivot(-5.0f, 2.0f, 0.0f));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create().uv(40, 16).mirrored()
                        .cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.5F)),
                PartPose.pivot(5.0f, 2.0f, 0.0f));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create().uv(0, 16)
                .cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.75F)),
                PartPose.pivot(-1.9f, 12.0f, 0.0f));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create().uv(0, 16).mirrored().
                cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new CubeDeformation(0.75F)),
                PartPose.pivot(1.9f, 12.0f, 0.0f));

        return LayerDefinition.of(modelData, 64, 32);
    }
}
