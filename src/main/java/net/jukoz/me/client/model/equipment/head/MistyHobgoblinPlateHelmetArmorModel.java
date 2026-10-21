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

public class MistyHobgoblinPlateHelmetArmorModel<T extends LivingEntity> extends CustomHelmetModel<T> {

    public final ModelPart wingRight;
    public final ModelPart wingLeft;

    public MistyHobgoblinPlateHelmetArmorModel(ModelPart root) {
        super(root);
        wingRight = root.getChild("head").getChild("wing_right");
        wingLeft = root.getChild("head").getChild("wing_left");

        HELMET_ADDON_TEXTURE = new ResourceLocation(MiddleEarth.MOD_ID, "textures/models/armor/misty_hobgoblin_plate_helmet_addon.png");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        PartDefinition wing_right = head.addChild("wing_right", CubeListBuilder.create(), PartPose.pivot(-2.5F, -10.5976F, -0.7012F));
        PartDefinition wing_left = head.addChild("wing_left", CubeListBuilder.create(), PartPose.pivot(-2.5F, -10.5976F, -0.7012F));
        wing_right.addChild("wing_right1", CubeListBuilder.create().uv(6, 0).cuboid(-3.5F, 8.5F, 8.1426F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.of(-3.6462F, -13.4868F, -7.8413F, 0.0F, 0.3491F, 0.0F));
        wing_right.addChild("wing_right2", CubeListBuilder.create().uv(0, 0).cuboid(0.0F, -7.0F, -0.5F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.of(-0.5F, 3.1132F, -0.3666F, 0.0F, 0.3491F, 0.0F));
        wing_left.addChild("wing_left1", CubeListBuilder.create().uv(6, 0).mirrored().cuboid(-2.5F, 8.5F, 8.1426F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.of(8.6462F, -13.4868F, -7.8413F, 0.0F, -0.3491F, 0.0F));
        wing_left.addChild("wing_left2", CubeListBuilder.create().uv(0, 0).mirrored().cuboid(-2.0F, -7.0F, -0.5F, 2.0F, 14.0F, 1.0F, new CubeDeformation(0.1F)).mirrored(false), PartPose.of(5.5F, 3.1132F, -0.3666F, 0.0F, -0.3491F, 0.0F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
