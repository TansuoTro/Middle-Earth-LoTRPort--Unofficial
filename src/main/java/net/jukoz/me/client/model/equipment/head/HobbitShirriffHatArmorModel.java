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

public class HobbitShirriffHatArmorModel<T extends LivingEntity> extends CustomHelmetModel<T> {

    public final ModelPart shirriffHat;

    public HobbitShirriffHatArmorModel(ModelPart root, String colour) {
        super(root);
        shirriffHat = root.getChild("head").getChild("shirriff_hat");

        HELMET_ADDON_TEXTURE = new ResourceLocation(MiddleEarth.MOD_ID, "textures/models/armor/hobbit_shirriff_hat_" + colour + ".png");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild("shirriff_hat", CubeListBuilder.create()
                .uv(-6, 0).cuboid(-8.0F, -8.9F, -8.0F, 16.0F, 0.0F, 16.0F, new CubeDeformation(0.0F))
                .uv(0, 16).cuboid(-4.0F, -12.4F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.5F))
                .uv(32, 16).cuboid(-4.0F, -12.7F, -4.0F, 8.0F, 3.0F, 8.0F, new CubeDeformation(0.6F)),
                PartPose.pivot(0.0F, 4.0F, 0.0F));

        head.addChild("feather", CubeListBuilder.create().uv(0, 30).cuboid(-0.1F, -0.4F, -6.5F, 0.0F, 8.0F, 10.0F, new CubeDeformation(1.0F)), PartPose.of(4.1F, -13.6F, 4.4F, 0.1327F, 0.0379F, 0.0433F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
