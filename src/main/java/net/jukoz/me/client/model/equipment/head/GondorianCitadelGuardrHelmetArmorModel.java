package net.jukoz.me.client.model.equipment.head;

import net.jukoz.me.MiddleEarth;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class GondorianCitadelGuardrHelmetArmorModel<T extends LivingEntity> extends CustomHelmetModel<T> {

    public final ModelPart addons;

    public GondorianCitadelGuardrHelmetArmorModel(ModelPart root) {
        super(root);
        addons = root.getChild("head").getChild("addons");

        HELMET_ADDON_TEXTURE = new ResourceLocation(MiddleEarth.MOD_ID, "textures/models/armor/gondorian_citadel_guard_helmet_addon.png");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild("addons", CubeListBuilder.create()
                .uv(0, 10).cuboid(-3.0F, -13.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .uv(0, 0).cuboid(-4.0F, -11.0F, -4.0F, 8.0F, 2.0F, 8.0F, new CubeDeformation(0.0F))
                .uv(32, 0).mirrored().cuboid(-3.0F, -15.6F, -4.6F, 6.0F, 12.0F, 0.0F, new CubeDeformation(0.5F)).mirrored(false)
                .uv(32, 16).mirrored().cuboid(-4.0F, -8.0F, -4.4F, 8.0F, 8.0F, 2.0F, new CubeDeformation(0.5F)).mirrored(false), PartPose.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("wing_right",  CubeListBuilder.create().uv(49, 0).cuboid(-6.0F, -9.5F, -0.1F, 6.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.of(-5.0F, -6.5F, -2.5F, 0.0F, 1.3963F, 0.0F));
        head.addChild("wing_left",  CubeListBuilder.create().uv(43, 0).cuboid(0.0F, -9.5F, -0.1F, 6.0F, 15.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.of(5.0F, -6.5F, -2.5F, 0.0F, -1.3963F, 0.0F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 32);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
