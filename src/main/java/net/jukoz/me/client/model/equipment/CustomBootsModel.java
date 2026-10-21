package net.jukoz.me.client.model.equipment;
import net.jukoz.me.compat.client.render.MEBipedEntityModel;

import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.model.geom.PartNames;
import net.jukoz.me.compat.client.render.state.BipedEntityRenderState;
import net.minecraft.world.entity.LivingEntity;

public class CustomBootsModel extends MEBipedEntityModel<LivingEntity, BipedEntityRenderState> {

    public CustomBootsModel(ModelPart root) {
        super(root, RenderLayer::getArmorCutoutNoCull);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();


        ModelPartData head = modelPartData.addChild(PartNames.HEAD, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.BODY, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData rightLeg = modelPartData.addChild(PartNames.RIGHT_LEG, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        rightLeg.addChild("rightBoot", ModelPartBuilder.create()
                        .uv(0, 48).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.9F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        rightLeg.addChild("rightInnerBoot", ModelPartBuilder.create()
                        .uv(0, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.7F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData leftLeg = modelPartData.addChild(PartNames.LEFT_LEG, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        leftLeg.addChild("leftBoot", ModelPartBuilder.create()
                        .uv(16, 48).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.9F)).mirrored(false),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        leftLeg.addChild("leftInnerBoot", ModelPartBuilder.create()
                        .uv(0, 32).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.7F)).mirrored(false),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}
