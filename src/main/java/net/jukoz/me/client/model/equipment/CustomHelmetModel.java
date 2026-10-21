package net.jukoz.me.client.model.equipment;
import net.jukoz.me.compat.client.render.MEBipedEntityModel;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.model.geom.PartNames;
import net.jukoz.me.compat.client.render.state.BipedEntityRenderState;
import net.minecraft.world.entity.LivingEntity;

public class CustomHelmetModel extends MEBipedEntityModel<LivingEntity, BipedEntityRenderState> {

    public CustomHelmetModel(ModelPart root) {
        super(root, RenderLayer::getArmorCutoutNoCull);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(PartNames.HEAD, ModelPartBuilder.create()
                .uv(0, 0).cuboid(-4.0F, -8.2F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.55F)),
                ModelTransform.pivot(0.0F, -23.0F, 0.0F));
        head.addChild(PartNames.HAT, ModelPartBuilder.create()
                .uv(32, 0).cuboid(-4.0F, -8.2F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.9F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild(PartNames.BODY, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        body.addChild("chestHeadExtension",  ModelPartBuilder.create()
                .uv(16, 32).cuboid(-4.0F, -1.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.82F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData rightArm = modelPartData.addChild(PartNames.RIGHT_ARM, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        rightArm.addChild("rightArmHeadExtension",  ModelPartBuilder.create()
                .uv(32, 48).cuboid(-3.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.92F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leftArm = modelPartData.addChild(PartNames.LEFT_ARM, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        leftArm.addChild("leftArmHeadExtension",  ModelPartBuilder.create()
                .uv(48, 48).mirrored().cuboid(-1.0F, -3.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.92F)).mirrored(false),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}
