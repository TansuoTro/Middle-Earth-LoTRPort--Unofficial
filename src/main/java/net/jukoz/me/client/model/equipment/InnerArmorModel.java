package net.jukoz.me.client.model.equipment;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.world.entity.LivingEntity;

public class InnerArmorModel<T extends LivingEntity> extends BipedEntityModel<T> {

    public InnerArmorModel(ModelPart root) {
        super(root, RenderLayer::getArmorCutoutNoCull);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(PartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.BODY, ModelPartBuilder.create().uv(16, 16)
                        .cuboid(-4.0f, 0.0f, -2.0f, 8.0f, 12.0f, 4.0f, new Dilation(0.8f)),
                ModelTransform.pivot(0.0f, 0.0f, 0.0f));
        modelPartData.addChild(PartNames.RIGHT_ARM, ModelPartBuilder.create().uv(40, 16)
                        .cuboid(-3.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.5F)),
                ModelTransform.pivot(-5.0f, 2.0f, 0.0f));
        modelPartData.addChild(PartNames.LEFT_ARM, ModelPartBuilder.create().uv(40, 16).mirrored()
                        .cuboid(-1.0f, -2.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.5F)),
                ModelTransform.pivot(5.0f, 2.0f, 0.0f));

        modelPartData.addChild(PartNames.RIGHT_LEG, ModelPartBuilder.create().uv(0, 16)
                .cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.75F)),
                ModelTransform.pivot(-1.9f, 12.0f, 0.0f));
        modelPartData.addChild(PartNames.LEFT_LEG, ModelPartBuilder.create().uv(0, 16).mirrored().
                cuboid(-2.0f, 0.0f, -2.0f, 4.0f, 12.0f, 4.0f, new Dilation(0.75F)),
                ModelTransform.pivot(1.9f, 12.0f, 0.0f));

        return TexturedModelData.of(modelData, 64, 32);
    }
}