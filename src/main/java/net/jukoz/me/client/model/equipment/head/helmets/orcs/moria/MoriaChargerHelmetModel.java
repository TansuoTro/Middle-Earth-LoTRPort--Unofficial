package net.jukoz.me.client.model.equipment.head.helmets.orcs.moria;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.jukoz.me.client.model.equipment.head.helmets.HelmetAddonModel;

public class MoriaChargerHelmetModel extends HelmetAddonModel {

    public MoriaChargerHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(PartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("large_crest", ModelPartBuilder.create()
                        .uv(28, -18).cuboid(0.0F, -15.25F, -7.0F, 0.0F, 14.0F, 18.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("grate", ModelPartBuilder.create()
                .uv(22, 40).cuboid(-3.0F, -10.25F, -3.0F, 6.0F, 11.0F, 6.0F, new Dilation(0.0F)),
                ModelTransform.of(0.0F, 0.0F, -4.0F, 0.0F, 0.7854F, 0.0F));

        modelPartData.addChild(PartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}