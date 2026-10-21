package net.jukoz.me.entity.beasts.great_horn.features;

import net.minecraft.client.model.*;
import net.jukoz.me.entity.beasts.great_horn.GreatHornEntity;
import net.jukoz.me.entity.beasts.great_horn.GreatHornModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class GreatHornArmorModel extends GreatHornModel {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart frontHalf;
    private final ModelPart frontBody;
    private final ModelPart saddle;
    private final ModelPart headNeck;
    private final ModelPart topHead;
    private final ModelPart reinsHead;
    private final ModelPart reins;

    public GreatHornArmorModel(ModelPart root) {
        super(root);
        this.root = root.getChild("root");
        this.body = this.root.getChild("body");
        this.frontHalf = this.body.getChild("front_half");
        this.frontBody = this.frontHalf.getChild("front_body");
        this.saddle = this.frontBody.getChild("saddle");
        this.headNeck = this.frontHalf.getChild("head_neck");
        this.topHead = headNeck.getChild("top_head");
        this.reinsHead = this.topHead.getChild("reins_head");
        this.reins = this.topHead.getChild("reins");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addChild("root", CubeListBuilder.create(), PartPose.pivot(0.0F, 8.0F, -10.0F));

        PartDefinition body = root.addChild("body", CubeListBuilder.create(), PartPose.pivot(0.0F, -8.5F, 12.0F));

        PartDefinition front_half = body.addChild("front_half", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, -13.0F));

        PartDefinition front_body = front_half.addChild("front_body", CubeListBuilder.create().uv(0, 15)
                .cuboid(-6.5F, -8.0F, -6.5F, 13.0F, 16.0F, 13.0F, new CubeDeformation(0.2F))
                .uv(37, 133).cuboid(-6.5F, 8.4F, -6.5F, 13.0F, 4.0F, 13.0F,
                        new CubeDeformation(0.2F)), PartPose.pivot(0.0F, 0.5F, 2.5F));

        PartDefinition front_left_leg = front_half.addChild("front_left_leg", CubeListBuilder.create().uv(0, 108)
                .cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.pivot(4.5F, 8.5F, -1.0F));

        PartDefinition front_right_leg = front_half.addChild("front_right_leg", CubeListBuilder.create().uv(16, 108)
                .cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 16.0F, 4.0F, new CubeDeformation(0.2F)), PartPose.pivot(-4.5F, 8.5F, -1.0F));

        PartDefinition head_neck = front_half.addChild("head_neck", CubeListBuilder.create(), PartPose.pivot(0.0F, -6.0F, -3.0F));

        PartDefinition neck = head_neck.addChild("neck", CubeListBuilder.create().uv(40, 0)
                .cuboid(-3.5F, -6.0F, -2.0F, 7.0F, 18.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, -1.5F, -2.0F));

        PartDefinition top_head = head_neck.addChild("top_head", CubeListBuilder.create().uv(96, 0)
                .cuboid(-3.5F, -7.0F, -6.0F, 7.0F, 7.0F, 9.0F, new CubeDeformation(0.2F))
                .uv(69, 0).cuboid(-2.5F, -4.0F, -11.0F, 5.0F, 4.0F, 5.0F,
                        new CubeDeformation(0.2F)), PartPose.pivot(0.0F, -7.5F, 0.0F));

        PartDefinition ear_left = top_head.addChild("ear_left", CubeListBuilder.create().uv(0, 0)
                .cuboid(0.0F, -4.0F, -1.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.2F)),
                PartPose.of(2.5F, -6.0F, 1.0F, -0.7854F, 0.4363F, 0.0F));

        PartDefinition ear_right = top_head.addChild("ear_right", CubeListBuilder.create().uv(9, 0)
                .cuboid(-0.5F, -4.0F, -1.0F, 1.0F, 5.0F, 3.0F, new CubeDeformation(0.2F)),
                PartPose.of(-3.0F, -6.0F, 1.0F, -0.7854F, -0.4363F, 0.0F));

        PartDefinition right_antler = top_head.addChild("right_antler", CubeListBuilder.create().uv(34, 120)
                .mirrored().cuboid(-8.0F, 0.0F, -2.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.2F))
                .mirrored(false), PartPose.of(-3.0F, -7.0F, 0.0F, 0.1812F, -0.3808F, 0.4252F));

        PartDefinition tip_r_armor_r1 = right_antler.addChild("tip_r_armor_r1", CubeListBuilder.create().uv(34, 124)
                .mirrored().cuboid(-8.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.2F)).mirrored(false),
                PartPose.of(-7.0F, 1.0F, 0.425F, 0.0F, 1.5708F, 0.0F));

        PartDefinition left_antler = top_head.addChild("left_antler", CubeListBuilder.create().uv(54, 120)
                .cuboid(0.0F, 0.0F, -2.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.of(3.0F, -7.0F, 0.0F, 0.1812F, 0.3808F, -0.4252F));

        PartDefinition tip_l_armor_r1 = left_antler.addChild("tip_l_armor_r1", CubeListBuilder.create().uv(54, 124)
                .cuboid(0.0F, -1.0F, -1.0F, 8.0F, 2.0F, 2.0F, new CubeDeformation(0.2F)),
                PartPose.of(7.0F, 1.0F, 0.375F, 0.0F, -1.5708F, 0.0F));

        PartDefinition reins_head = top_head.addChild("reins_head", CubeListBuilder.create().uv(51, 35)
                .cuboid(-3.5F, -7.0F, -6.0F, 7.0F, 7.0F, 9.0F, new CubeDeformation(0.3F))
                .uv(52, 26).cuboid(-2.5F, -4.0F, -11.0F, 5.0F, 4.0F, 5.0F,
                        new CubeDeformation(0.3F)), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition reins = top_head.addChild("reins", CubeListBuilder.create().uv(86, 99)
                .cuboid(3.9F, 0.0F, 0.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
                .uv(86, 91).cuboid(-3.9F, 0.0F, 0.0F, 0.0F, 7.0F, 21.0F,
                        new CubeDeformation(0.0F)), PartPose.pivot(0.0F, -2.0F, -7.0F));

        PartDefinition back_body = front_half.addChild("back_body", CubeListBuilder.create().uv(0, 44)
                .cuboid(-5.5F, -4.55F, -3.6F, 11.0F, 13.0F, 14.0F, new CubeDeformation(0.2F))
                .uv(0, 128).cuboid(-5.5F, 8.825F, -3.6F, 11.0F, 4.0F, 14.0F,
                        new CubeDeformation(0.2F)), PartPose.pivot(0.0F, 0.0F, 13.0F));

        PartDefinition tail = back_body.addChild("tail", CubeListBuilder.create(), PartPose.pivot(0.0F, -4.5F, 10.0F));

        PartDefinition tail_armor_r1 = tail.addChild("tail_armor_r1", CubeListBuilder.create().uv(39, 47)
                .cuboid(-1.5F, -2.0F, 0.0F, 3.0F, 2.0F, 4.0F, new CubeDeformation(0.2F)),
                PartPose.of(0.0F, 2.0F, 0.0F, 0.5236F, 0.0F, 0.0F));

        PartDefinition back_left_leg = root.addChild("back_left_leg", CubeListBuilder.create().uv(0, 72)
                .cuboid(-2.25F, -4.25F, -3.75F, 5.0F, 10.0F, 7.0F, new CubeDeformation(0.2F))
                .uv(4, 89).cuboid(-2.25F, 5.75F, 0.25F, 4.0F, 15.0F, 4.0F,
                        new CubeDeformation(0.2F)), PartPose.pivot(4.75F, -4.75F, 19.75F));

        PartDefinition back_right_leg = root.addChild("back_right_leg", CubeListBuilder.create().uv(24, 72)
                .cuboid(-2.75F, -4.25F, -3.75F, 5.0F, 10.0F, 7.0F, new CubeDeformation(0.2F))
                .uv(28, 89).cuboid(-1.75F, 5.75F, 0.25F, 4.0F, 15.0F, 4.0F,
                        new CubeDeformation(0.2F)), PartPose.pivot(-4.75F, -4.75F, 19.75F));

        top_head.addChild("beard", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        front_body.addChild("saddle", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 128, 160);
    }

    @Override
    public void setAngles(GreatHornEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setAngles(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        boolean showSaddle = entity.isSaddled();
        saddle.hidden = !showSaddle;
        reinsHead.hidden = !showSaddle;
        reins.hidden = !showSaddle;

        if(!entity.hasPlayerRider()) {
            reins.pitch = -12.5f * 0.017453292F;
        } else {
            reins.pitch = 0f;
        }
    }
}
