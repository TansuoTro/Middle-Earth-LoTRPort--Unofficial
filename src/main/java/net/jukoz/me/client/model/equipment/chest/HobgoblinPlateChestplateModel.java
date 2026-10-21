package net.jukoz.me.client.model.equipment.chest;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.world.entity.LivingEntity;

public class HobgoblinPlateChestplateModel extends ChestplateAddonModel {

    public HobgoblinPlateChestplateModel(ModelPart root) {
        super(root);
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData head = modelPartData.addChild(PartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData right_arm = modelPartData.addChild(PartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        right_arm.addChild("right_arm_spike", ModelPartBuilder.create().uv(0, 45).cuboid(0.0F, 14.3F, 0.0F, 4.0F, 19.0F, 0.0F, new Dilation(0.0F)), ModelTransform.pivot(-5.0F, -21.0F, 0.0F));
        ModelPartData left_arm = modelPartData.addChild(PartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        left_arm.addChild("left_arm_spike", ModelPartBuilder.create().uv(8, 45).mirrored().cuboid(-4.0F, 14.3F, 0.0F, 4.0F, 19.0F, 0.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(5.0F, -21.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

}