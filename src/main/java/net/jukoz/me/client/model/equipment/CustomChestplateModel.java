package net.jukoz.me.client.model.equipment;
import net.jukoz.me.compat.client.render.MEBipedEntityModel;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.model.geom.PartNames;
import net.jukoz.me.compat.client.render.state.BipedEntityRenderState;
import net.minecraft.world.entity.LivingEntity;

public class CustomChestplateModel extends MEBipedEntityModel<LivingEntity, BipedEntityRenderState> {

    public CustomChestplateModel(ModelPart root) {
        super(root, RenderLayer::getArmorCutoutNoCull);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(PartNames.HEAD, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild(PartNames.BODY, ModelPartBuilder.create()
                        .uv(16, 32).cuboid(-4.0F, 0.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.8F)),
                ModelTransform.pivot(0.0F, -23.0F, 0.0F));

        body.addChild("innerChest", ModelPartBuilder.create()
                        .uv(16, 16).cuboid(-4.0F, -0.0F, -2.068F, 8.0F, 12.0F, 4.0F, new Dilation(0.5F)),
                ModelTransform.pivot(0.0F, -0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_ARM, ModelPartBuilder.create()
                        .uv(32, 48).cuboid(-3.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.9F))
                        .uv(40, 16).cuboid(-3.0F, -2.3F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.6F)),
                ModelTransform.pivot(-5.0F, -21.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, ModelPartBuilder.create()
                        .uv(48, 48).mirrored().cuboid(-1.0F, -2.5F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.9F)).mirrored(false)
                        .uv(40, 32).mirrored().cuboid(-1.0F, -2.3F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.6F)).mirrored(false),
                ModelTransform.pivot(5.0F, -21.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, ModelPartBuilder.create()
                .uv(0, 16).cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F)),
                ModelTransform.pivot(-1.9F, -11.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, ModelPartBuilder.create()
                .uv(0, 32).mirrored().cuboid(-2.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.5F)).mirrored(false),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}
