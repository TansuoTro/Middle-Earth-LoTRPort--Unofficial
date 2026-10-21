package net.jukoz.me.client.model.equipment.head.helmets.orcs.goblintown;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.jukoz.me.client.model.equipment.head.helmets.FlapHelmetModel;

public class GoblinTownSkullHelmetModel extends FlapHelmetModel {

    public GoblinTownSkullHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(PartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("bottom_jaw", ModelPartBuilder.create()
                        .uv(0, 31).cuboid(-8.4497F, -9.75F, 2.0F, 6.0F, 10.0F, 6.0F, new Dilation(0.3F)),
                ModelTransform.of(0.3498F, 4.5F, -8.9142F, 0.6603F, 0.6603F, 0.4439F));

        modelPartData.addChild(PartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}