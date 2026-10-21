package net.jukoz.me.client.model.equipment.chest;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.world.entity.LivingEntity;

public class MordorBNChestplateModel extends HaltChestplateModel {

    public MordorBNChestplateModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();

        ModelPartData body = modelData.getRoot().addChild(PartNames.BODY, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        body.addChild("chest_addon", ModelPartBuilder.create()
                .uv(0, 41).cuboid(-4.5F, -1.0F, -2.3F, 9.0F, 12.0F, 4.0F, new Dilation(0.51F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}