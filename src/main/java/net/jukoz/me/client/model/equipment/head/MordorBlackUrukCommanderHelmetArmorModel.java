package net.jukoz.me.client.model.equipment.head;

import net.jukoz.me.MiddleEarth;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;

public class MordorBlackUrukCommanderHelmetArmorModel<T extends LivingEntity> extends CustomHelmetModel<T> {

    public final ModelPart skull;

    public MordorBlackUrukCommanderHelmetArmorModel(ModelPart root) {
        super(root);
        skull = root.getChild("head").getChild("skull_parts");

        HELMET_ADDON_TEXTURE = new ResourceLocation(MiddleEarth.MOD_ID, "textures/models/armor/mordor_black_uruk_commander_helmet_addon.png");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        modelPartData.addChild(PartNames.HAT, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData head = modelPartData.addChild(PartNames.HEAD, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData skull_parts = head.addChild("skull_parts", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        skull_parts.addChild("skull", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -41.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(-0.8F))
                .uv(0, 16).mirrored().cuboid(-4.0F, -40.3F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(-0.6F)).mirrored(false), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        modelPartData.addChild(PartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}