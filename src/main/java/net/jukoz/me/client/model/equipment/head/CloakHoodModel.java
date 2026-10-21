package net.jukoz.me.client.model.equipment.head;

import com.google.common.collect.ImmutableList;
import net.jukoz.me.utils.ToRad;
import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.AnimalModel;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.world.entity.LivingEntity;

public class CloakHoodModel<T extends LivingEntity> extends BipedEntityModel<T> {
    private final ModelPart hood;

    public CloakHoodModel(ModelPart root) {
        super(root);
        hood = root.getChild("hat").getChild("hood");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        Dilation dilation = new Dilation(0.0F);

        ModelPartData hat = modelPartData.addChild(PartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        hat.addChild("hood", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-4.0F, -7.5F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(1.6F))
                .uv(32, 0).cuboid(-4.0F, -7.5F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(2.0F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        /*modelPartData
                .addChild("hood", ModelPartBuilder.create()
                                .uv(32, 0)
                                .cuboid(-3, -12, 2, 6, 6, 1.0F, dilation)
                , ModelTransform.of(0, 0, 0, 0, 0.0F, 0F))
                .addChild("back_hood", ModelPartBuilder.create().uv(32, 7)
                        .cuboid(-3, -13.435f, 2.08f, 6,6,2, dilation)
                , ModelTransform.of(0, 1, -1, -ToRad.ex(7.5), 0.0F, 0F));*/
        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
