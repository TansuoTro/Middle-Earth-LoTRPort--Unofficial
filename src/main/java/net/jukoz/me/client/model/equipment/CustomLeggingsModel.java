package net.jukoz.me.client.model.equipment;
import net.jukoz.me.compat.client.render.MEBipedEntityModel;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.model.geom.PartNames;
import net.jukoz.me.compat.client.render.state.BipedEntityRenderState;
import net.minecraft.world.entity.LivingEntity;

public class CustomLeggingsModel extends MEBipedEntityModel<LivingEntity, BipedEntityRenderState> {

    public CustomLeggingsModel(ModelPart root) {
        super(root, RenderLayer::getArmorCutoutNoCull);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(PartNames.HEAD, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild(PartNames.BODY, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        body.addChild("waist", ModelPartBuilder.create()
                        .uv(16, 16).cuboid(-4.0F, -0.0F, -2.1F, 8.0F, 12.0F, 4.0F, new Dilation(0.3F)),
                ModelTransform.pivot(0.0F, -0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_ARM, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, ModelPartBuilder.create()
                .uv(0, 16).cuboid(-2.1F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.3F)),
                ModelTransform.pivot(-1.9F, -11.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, ModelPartBuilder.create()
                .uv(0, 32).mirrored().cuboid(-1.968F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.3F)).mirrored(false),
                ModelTransform.pivot(1.9F, -11.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}
