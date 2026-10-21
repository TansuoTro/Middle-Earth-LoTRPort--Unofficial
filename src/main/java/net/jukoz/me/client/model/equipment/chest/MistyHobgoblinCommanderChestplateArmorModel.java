package net.jukoz.me.client.model.equipment.chest;

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

public class MistyHobgoblinCommanderChestplateArmorModel<T extends LivingEntity> extends CustomChestplateModel<T> {

    public MistyHobgoblinCommanderChestplateArmorModel(ModelPart root) {
        super(root);

        CHESTPLATE_ADDON_TEXTURE = new ResourceLocation(MiddleEarth.MOD_ID, "textures/models/armor/misty_hobgoblin_commander_chestplate_addon.png");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addOrReplaceChild(PartNames.HAT, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition body = modelPartData.addOrReplaceChild(PartNames.BODY, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition rightskullspear = body.addOrReplaceChild("rightskullspear", CubeListBuilder.create(), PartPose.offsetAndRotation(-3.0F, 8.0F, 0.0F, 0.2986F, 0.0651F, -0.5138F));
        rightskullspear.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-2.5671F, -3.0474F, 2.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-1.2F)).mirror(false), PartPose.offsetAndRotation(8.0F, -28.0F, 7.0F, -0.3491F, 0.0F, 0.3054F));
        rightskullspear.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-4.0F, -4.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-1.0F)).mirror(false), PartPose.offsetAndRotation(6.332F, -18.3648F, 10.1039F, -0.4181F, 0.5692F, 0.0704F));

        PartDefinition cloth2 = rightskullspear.addOrReplaceChild("cloth2", CubeListBuilder.create(), PartPose.offsetAndRotation(-6.0F, 0.0F, 0.0F, 0.3011F, 0.0522F, 0.1388F));
        cloth2.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(0, 16).mirror().addBox(0.1819F, 2.4589F, 5.1122F, 2.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(8.0F, -18.5F, 12.0F, -0.6114F, 0.1727F, 0.3707F));

        PartDefinition spears2 = rightskullspear.addOrReplaceChild("spears2", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.5F, 3.0F, 0.0F, 0.0F, 0.1309F));
        spears2.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(32, 0).mirror().addBox(1.4329F, -23.6652F, 2.6678F, 2.0F, 35.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -10.0F, 0.0F, -0.3491F, 0.0F, 0.1745F));

        PartDefinition leftskullspear = body.addOrReplaceChild("leftskullspear", CubeListBuilder.create(), PartPose.offsetAndRotation(3.0F, 8.0F, 0.0F, 0.2986F, -0.0651F, 0.5138F));
        leftskullspear.addOrReplaceChild("ribcage_r1", CubeListBuilder.create().texOffs(4, 25).addBox(-6.0F, 6.0F, 6.3F, 10.0F, 12.0F, 4.0F, new CubeDeformation(-0.8F)), PartPose.offsetAndRotation(-8.5498F, -28.3041F, 4.7181F, -0.3491F, 0.0F, -0.3054F));
        leftskullspear.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(0, 0).addBox(-5.4329F, -0.0474F, 2.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-0.8F)), PartPose.offsetAndRotation(-8.0F, -28.0F, 7.0F, -0.3491F, 0.0F, -0.3054F));
        leftskullspear.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -14.0F, -4.0F, 8.0F, 8.0F, 8.0F, new CubeDeformation(-1.8F)), PartPose.offsetAndRotation(-6.332F, -18.3648F, 10.1039F, -0.4181F, -0.5692F, -0.0704F));

        PartDefinition cloth = leftskullspear.addOrReplaceChild("cloth", CubeListBuilder.create(), PartPose.offsetAndRotation(6.0F, 0.0F, 0.0F, 0.3011F, -0.0522F, -0.1388F));
        cloth.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(0, 16).addBox(-2.1819F, 1.4589F, 5.1122F, 2.0F, 9.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-8.0F, -18.5F, 12.0F, -0.6114F, -0.1727F, -0.3707F));

        PartDefinition spears3 = leftskullspear.addOrReplaceChild("spears3", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, -0.5F, 3.0F, 0.0F, 0.0F, -0.1309F));
        spears3.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(32, 0).addBox(-3.4329F, -23.6652F, 2.6678F, 2.0F, 35.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -10.0F, 0.0F, -0.3491F, 0.0F, -0.1745F));

        PartDefinition right_arm = modelPartData.addOrReplaceChild(PartNames.RIGHT_ARM, CubeListBuilder.create(),
                PartPose.offset(0.0F, 0.0F, 0.0F));
        right_arm.addOrReplaceChild("glove_addon", CubeListBuilder.create()
                .texOffs(40, 0).addBox(-7.0F, 4.0F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)),
                PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition left_arm =modelPartData.addOrReplaceChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        left_arm.addOrReplaceChild("shoulder_addon", CubeListBuilder.create()
                .texOffs(40, 0).mirror().addBox(3.0F, -6.0F, 0.0F, 3.0F, 5.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false),
                PartPose.offset(0.0F, 0.0F, 0.0F));;

        modelPartData.addOrReplaceChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        modelPartData.addOrReplaceChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {

    }
}
