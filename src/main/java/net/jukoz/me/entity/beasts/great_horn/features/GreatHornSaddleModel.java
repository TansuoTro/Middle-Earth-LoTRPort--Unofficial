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

public class GreatHornSaddleModel extends GreatHornModel {
    private final ModelPart root;
    private final ModelPart body;
    private final ModelPart frontHalf;
    private final ModelPart frontBody;
    private final ModelPart saddle;
    private final ModelPart headNeck;
    private final ModelPart topHead;
    private final ModelPart reinsHead;
    private final ModelPart reins;

    public GreatHornSaddleModel(ModelPart root) {
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

        PartDefinition front_body = front_half.addChild("front_body", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.5F, 2.5F));

        PartDefinition saddle = front_body.addChild("saddle", CubeListBuilder.create().uv(88, 50)
                .cuboid(-7.0F, -1.0F, 2.0F, 14.0F, 22.0F, 6.0F, new CubeDeformation(0.3F))
                .uv(84, 18).cuboid(-7.0F, -2.0F, -6.6F, 14.0F, 23.0F, 8.0F,
                        new CubeDeformation(0.3F)), PartPose.pivot(0.0F, -6.5F, 6.5F));

        PartDefinition seat_back_r1 = saddle.addChild("seat_back_r1", CubeListBuilder.create().uv(49, 78)
                .cuboid(-4.5F, -4.5F, -1.0F, 9.0F, 5.0F, 2.0F, new CubeDeformation(0.3F)), PartPose.of(0.0F, 1.0F, 8.4F, -0.2182F, 0.0F, 0.0F));

        PartDefinition head_neck = front_half.addChild("head_neck", CubeListBuilder.create(), PartPose.pivot(0.0F, -6.0F, -3.0F));

        PartDefinition top_head = head_neck.addChild("top_head", CubeListBuilder.create(), PartPose.pivot(0.0F, -7.5F, 0.0F));

        root.addChild("front_left_leg", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        root.addChild("front_right_leg", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        root.addChild("back_left_leg", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        root.addChild("back_right_leg", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition back_body = front_half.addChild("back_body", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        back_body.addChild("tail", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        top_head.addChild("right_antler", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        top_head.addChild("left_antler", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        top_head.addChild("ear_left", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        top_head.addChild("ear_right", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        top_head.addChild("beard", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition reins_head = top_head.addChild("reins_head", CubeListBuilder.create().uv(51, 35)
                .cuboid(-3.5F, -7.0F, -6.0F, 7.0F, 7.0F, 9.0F, new CubeDeformation(0.3F))
                .uv(52, 26).cuboid(-2.5F, -4.0F, -11.0F, 5.0F, 4.0F, 5.0F, new CubeDeformation(0.3F))
                .uv(53, 53).cuboid(-3.5F, 0.0F, -4.0F, 7.0F, 18.0F, 7.0F,
                        new CubeDeformation(0.3F)), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition reins = top_head.addChild("reins", CubeListBuilder.create().uv(86, 99)
                .cuboid(3.9F, 0.0F, 0.0F, 0.0F, 7.0F, 21.0F, new CubeDeformation(0.0F))
                .uv(86, 91).cuboid(-3.9F, 0.0F, 0.0F, 0.0F, 7.0F, 21.0F,
                        new CubeDeformation(0.0F)), PartPose.pivot(0.0F, -2.0F, -7.0F));

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
