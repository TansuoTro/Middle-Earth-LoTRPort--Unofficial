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

public class MistyHobgoblinScaleHelmetArmorModel<T extends LivingEntity> extends CustomHelmetModel<T> {

    public final ModelPart wingRight;
    public final ModelPart wingLeft;

    public MistyHobgoblinScaleHelmetArmorModel(ModelPart root) {
        super(root);
        wingRight = root.getChild("head").getChild("wing_right");
        wingLeft = root.getChild("head").getChild("wing_left");

        HELMET_ADDON_TEXTURE = new ResourceLocation(MiddleEarth.MOD_ID, "textures/models/armor/misty_hobgoblin_scale_helmet_addon.png");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild("wing_right", CubeListBuilder.create().uv(0, 0).mirrored().cuboid(-2.5F, -2.5F, -1.0F, 4.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.of(3.5F, -11.5F, 0.0F, 0.0F, -0.2618F, 0.0F));
        head.addChild("wing_left", CubeListBuilder.create().uv(0, 0).cuboid(-1.5F, -2.5F, -1.0F, 4.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.of(-3.5F, -11.5F, 0.0F, 0.0F, 0.2618F, 0.0F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 16, 16);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
