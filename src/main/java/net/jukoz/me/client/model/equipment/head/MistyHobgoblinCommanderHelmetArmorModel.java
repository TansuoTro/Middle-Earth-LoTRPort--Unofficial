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

public class MistyHobgoblinCommanderHelmetArmorModel<T extends LivingEntity> extends CustomHelmetModel<T> {

    public final ModelPart wingMiddle;
    public final ModelPart wingRight;
    public final ModelPart wingLeft;

    public MistyHobgoblinCommanderHelmetArmorModel(ModelPart root) {
        super(root);
        wingMiddle = root.getChild("head").getChild("addon").getChild("wing_middle");
        wingRight = root.getChild("head").getChild("addon").getChild("wing_right");
        wingLeft = root.getChild("head").getChild("addon").getChild("wing_left");

        HELMET_ADDON_TEXTURE = new ResourceLocation(MiddleEarth.MOD_ID, "textures/models/armor/misty_hobgoblin_commander_helmet_addon.png");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition head = modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition addon = head.addOrReplaceChild("addon", CubeListBuilder.create(), PartPose.offsetAndRotation(-1.5F, -10.5976F, -3.7012F, 0.0F, -0.3491F, 0.0F));
        addon.addOrReplaceChild("wing_middle", CubeListBuilder.create().texOffs(17, 2).mirror().addBox(-3.0F, -4.0F, -2.0F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(4.3312F, -0.9868F, -0.0188F, 0.0F, 0.1309F, 0.0F));
        addon.addOrReplaceChild("wing_right", CubeListBuilder.create().texOffs(17, 2).addBox(-3.0F, -4.0F, 0.0F, 6.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.3312F, -0.9868F, -0.0188F, 0.0F, 0.5672F, 0.0F));
        addon.addOrReplaceChild("wing_left", CubeListBuilder.create().texOffs(0, 0).addBox(0.0F, -7.0F, -0.5F, 4.0F, 14.0F, 1.0F, new CubeDeformation(0.1F)), PartPose.offsetAndRotation(-0.5F, 3.1132F, -0.3666F, 0.0F, 0.3491F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
