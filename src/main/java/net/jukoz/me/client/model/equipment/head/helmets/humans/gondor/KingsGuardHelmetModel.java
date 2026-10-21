package net.jukoz.me.client.model.equipment.head.helmets.humans.gondor;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.jukoz.me.client.model.equipment.head.helmets.EggHelmetModel;

public class KingsGuardHelmetModel extends EggHelmetModel {

    public KingsGuardHelmetModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        //+0.75
        modelData.getRoot().addChild(PartNames.HEAD, ModelPartBuilder.create()
                        .uv(50, 0).mirrored().cuboid(2.0F, -19.55F, 0.0F, 7.0F, 16.0F, 0.0F, new Dilation(0.0F)).mirrored(false)
                        .uv(50, 16).cuboid(-9.0F, -19.55F, 0.0F, 7.0F, 16.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));


        return TexturedModelData.of(modelData, 64, 64);
    }
}