package net.jukoz.me.client.model.equipment.head.helmets.humans.hobbits;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.jukoz.me.client.model.equipment.head.helmets.HatHelmetModel;

public class ShirriffHatModel extends HatHelmetModel {

    public ShirriffHatModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(PartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData feathers = head.addChild("feathers", ModelPartBuilder.create(),
                ModelTransform.of(3.9823F, -10.002F, 0.7141F, -0.0393F, 0.0F, 0.0F));
        feathers.addChild("duck_feather", ModelPartBuilder.create()
                .uv(13, 32).cuboid(-0.3F, -3.85F, -6.5F, 0.0F, 8.0F, 10.0F, new Dilation(1.0F)),
                ModelTransform.of(0.3037F, 1.402F, 1.6859F, 0.1327F, 0.0379F, 0.0433F));

        modelPartData.addChild(PartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        return TexturedModelData.of(modelData, 64, 64);
    }
}