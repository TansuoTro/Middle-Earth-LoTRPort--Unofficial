package net.jukoz.me.client.model.equipment.chest;

import net.jukoz.me.MiddleEarth;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;

public class EreborPouchChestplateArmorModel<T extends LivingEntity> extends CustomChestplateModel<T> {

    public final ModelPart pouch;

    public EreborPouchChestplateArmorModel(ModelPart root) {
        super(root);
        pouch = root.getChild("body").getChild("pouch");

        CHESTPLATE_ADDON_TEXTURE = new ResourceLocation(MiddleEarth.MOD_ID, "textures/models/armor/erebor_pouch.png");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(PartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData body = modelPartData.addChild(PartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData pouch = body.addChild("pouch", ModelPartBuilder.create().uv(0, 6).cuboid(0.4F, 8.5F, 2.0F, 3.0F, 4.0F, 1.0F, new Dilation(0.5F)), ModelTransform.pivot(0.0F, 0.0F, 1.0F));
        pouch.addChild("pouch_lid", ModelPartBuilder.create().uv(1, 13).cuboid(-1.5F, 0.5F, 0.6F, 3.0F, 3.0F, -1.0F, new Dilation(0.5F)), ModelTransform.of(1.9F, 8.0F, 3.4F, 0.0873F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 32);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}