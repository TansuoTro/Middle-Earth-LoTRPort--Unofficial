package net.jukoz.me.client.model.equipment.head.helmets.humans.dale;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.jukoz.me.client.model.equipment.head.helmets.HelmetAddonModel;

public class DaleSpikeHelmetModel extends HelmetAddonModel {

    public DaleSpikeHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        return TexturedModelData.of(modelData, 64, 64);
    }

    public static ModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(PartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData addon = head.addChild("addon", ModelPartBuilder.create()
                .uv(37, 0).cuboid(-2.5F, -11.75F, -3.0F, 5.0F, 3.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        addon.addChild("tall_spike", ModelPartBuilder.create()
                .uv(6, 0).cuboid(-0.5F, -3.75F, -0.5F, 1.0F, 8.0F, 1.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, -12.5F, 0.0F, 0.0F, -0.7854F, 0.0F));

        modelPartData.addChild(PartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return modelData;
    }
}