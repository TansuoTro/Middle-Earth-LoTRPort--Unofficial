package net.jukoz.me.client.model.equipment.head.helmets.elves.woodlandrealm;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.jukoz.me.client.model.equipment.head.helmets.HelmetAddonModel;

public class WoodlandRealmCrownModel extends HelmetAddonModel {
    ModelPart crown;

    public WoodlandRealmCrownModel(ModelPart root) {
        super(root);

        ModelPart helmet = root.getChild("head");
        ModelPart addon = helmet.getChild("addon");
        this.crown = addon.getChild("crown");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(PartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData addon = head.addChild("addon", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 1.75F, -0.25F));

        ModelPartData crown = addon.addChild("crown", ModelPartBuilder.create().uv(41, 28)
                .cuboid(-1.9077F, -8.5F, 3.3333F, 4.0F, 15.0F, 0.0F,
                        new Dilation(0.0F)), ModelTransform.of(-0.0909F, -11.0F, 2.4137F, -0.3491F, 0.0F, 0.0F));

        ModelPartData crown_side_right = crown.addChild("crown_side_right", ModelPartBuilder.create().uv(26, 28)
                .cuboid(-4.0F, -7.5F, 0.0F, 7.0F, 15.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.of(-3.9077F, -1.0F, 1.3333F, 0.0F, -0.7418F, 0.0F));

        ModelPartData crown_side_left = crown.addChild("crown_side_left", ModelPartBuilder.create().uv(50, 28)
                .mirrored().cuboid(-3.0F, -7.5F, 0.0F, 7.0F, 15.0F, 0.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(4.1154F, -1.0F, 1.3335F, 0.0F, 0.7418F, 0.0F));

        modelPartData.addChild(PartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}