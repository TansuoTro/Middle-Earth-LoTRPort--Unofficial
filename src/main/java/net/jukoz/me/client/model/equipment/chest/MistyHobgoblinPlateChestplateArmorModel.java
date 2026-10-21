package net.jukoz.me.client.model.equipment.chest;

import net.jukoz.me.MiddleEarth;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;

public class MistyHobgoblinPlateChestplateArmorModel<T extends LivingEntity> extends CustomChestplateModel<T> {
    private static final float MAX_ANGLE_HAIR = 75f;

    public final ModelPart shoulderAddon;

    public MistyHobgoblinPlateChestplateArmorModel(ModelPart root) {
        super(root);
        shoulderAddon = root.getChild("right_arm").getChild("shoulder_addon");

        CHESTPLATE_ADDON_TEXTURE = new ResourceLocation(MiddleEarth.MOD_ID, "textures/models/armor/misty_hobgoblin_plate_chestplate_addon.png");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(PartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData right_arm = modelPartData.addChild(PartNames.RIGHT_ARM, ModelPartBuilder.create(),
                ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        right_arm.addChild("shoulder_addon", ModelPartBuilder.create()
                .uv(0, 0).cuboid(-3.0F, -2.0F, -2.0F, 3.0F, 5.0F, 0, new Dilation(0.0F)),
                ModelTransform.pivot(-3.0F, -4.0F, 2.0F));

        modelPartData.addChild(PartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 16, 16);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}