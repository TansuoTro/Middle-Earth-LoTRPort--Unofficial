package net.jukoz.me.client.model.equipment.head.helmets.humans.rohan;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;

public class HammerhandHelmModel extends RohanHelmetModel {
    
    public HammerhandHelmModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(PartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData HornsR = head.addChild("horn_right_attachment", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-6.4F, -6.25F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        HornsR.addChild("horn_right", ModelPartBuilder.create()
                .uv(50, 0).cuboid(-3.5F, -11.25F, 0.0F, 7.0F, 13.0F, 0.0F, new Dilation(0.0F)),
                ModelTransform.of(-5.5F, -5.0F, 0.0F, 0.0F, 0.0F, 0.1396F));

        ModelPartData HornsR2 = head.addChild("horn_left_attachment", ModelPartBuilder.create()
                .uv(0, 0).cuboid(4.6F, -6.25F, -1.0F, 2.0F, 2.0F, 2.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        HornsR2.addChild("horn_left", ModelPartBuilder.create()
                .uv(50, 13).mirrored().cuboid(-3.5F, -11.25F, 0.0F, 7.0F, 13.0F, 0.0F, new Dilation(0.0F)).mirrored(false),
                ModelTransform.of(5.7F, -5.0F, 0.0F, 0.0F, 0.0F, -0.1396F));

        modelPartData.addChild(PartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }
}