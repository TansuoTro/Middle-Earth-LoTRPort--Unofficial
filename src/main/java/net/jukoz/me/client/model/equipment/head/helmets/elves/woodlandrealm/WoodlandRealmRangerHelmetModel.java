package net.jukoz.me.client.model.equipment.head.helmets.elves.woodlandrealm;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.jukoz.me.client.model.equipment.head.helmets.HelmetAddonModel;

public class WoodlandRealmRangerHelmetModel extends HelmetAddonModel {

    public WoodlandRealmRangerHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(PartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData addon = head.addChild("addon", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData centralSpine = addon.addChild("central_spine", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData cube_r1 = centralSpine.addChild("cube_r1", ModelPartBuilder.create().uv(0, 45)
                .cuboid(-5.0F, 2.0F, 0.0F, 1.0F, 10.0F, 9.0F, new Dilation(0.1F)),
                ModelTransform.of(4.5F, -2.25F, -7.0F, 1.5708F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}