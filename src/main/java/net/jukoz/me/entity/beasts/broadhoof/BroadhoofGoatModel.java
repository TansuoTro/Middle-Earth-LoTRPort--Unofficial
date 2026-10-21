package net.jukoz.me.entity.beasts.broadhoof;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BroadhoofGoatModel extends HierarchicalModel<BroadhoofGoatEntity> {
    private final ModelPart broadhoofGoat;
    private final ModelPart head;
    private final ModelPart hair;
    private final ModelPart horns;
    private final ModelPart wildBeard;
    private final ModelPart brushedBeard;
    private final ModelPart[] leftHorns = new ModelPart[BroadhoofGoatHorns.values().length];
    private final ModelPart[] rightHorns = new ModelPart[BroadhoofGoatHorns.values().length];

    public BroadhoofGoatModel(ModelPart root) {
        this.broadhoofGoat = root.getChild("broadhoof_goat");
        this.head = broadhoofGoat.getChild(PartNames.BODY).getChild(PartNames.HEAD);

        this.wildBeard = this.head.getChild("wild_beard");
        this.brushedBeard = this.head.getChild("brushed_beard");

        this.hair = this.head.getChild("hair");
        this.horns = this.head.getChild("horns");

        this.leftHorns[0] = horns.getChild("tiny_left_horn");
        this.rightHorns[0] = horns.getChild("tiny_right_horn");

        this.leftHorns[1] = horns.getChild("normal_left_horn");
        this.rightHorns[1] = horns.getChild("normal_right_horn");

        this.leftHorns[2] = horns.getChild("long_left_horn");
        this.rightHorns[2] = horns.getChild("long_right_horn");

        this.leftHorns[3] = horns.getChild("curly_left_horn");
        this.rightHorns[3] = horns.getChild("curly_right_horn");

        this.leftHorns[4] = horns.getChild("swirly_left_horn");
        this.rightHorns[4] = horns.getChild("swirly_right_horn");

        this.leftHorns[5] = horns.getChild("wide_left_horn");
        this.rightHorns[5] = horns.getChild("wide_right_horn");

        this.leftHorns[6] = horns.getChild("huge_left_horn");
        this.rightHorns[6] = horns.getChild("huge_right_horn");

    }
    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        PartDefinition root = modelData.getRoot();
        PartDefinition broadhoof_goat = root.getChild("broadhoof_goat");
        PartDefinition body = broadhoof_goat.getChild(PartNames.BODY);
        PartDefinition head = body.getChild(PartNames.HEAD);

        PartDefinition head_cube = head.addChild(PartNames.HEAD, CubeListBuilder.create().uv(0, 0).mirrored().cuboid(2.9F, -11.0F, -6.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(0, 0).cuboid(-5.9F, -11.0F, -6.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

        PartDefinition body_cube = body.addChild(PartNames.BODY, CubeListBuilder.create().uv(2, 48).cuboid(-6.0F, -9.0F, -11.0F, 12.0F, 11.0F, 20.0F, new CubeDeformation(0.0F))
                .uv(0, 18).cuboid(-7.0F, -11.0F, -12.0F, 14.0F, 16.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition tail_r1 = body.addChild("tail_r1", CubeListBuilder.create().uv(86, 1).cuboid(-2.0F, -3.0F, 0.0F, 4.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, -6.0F, 9.0F, 0.4363F, 0.0F, 0.0F));

        PartDefinition wild_beard = head.getChild("wild_beard");
        PartDefinition wild_beard_cube = wild_beard.addChild("wild_beard_cube", CubeListBuilder.create().uv(3, 47).mirrored().cuboid(0.0F, -5.0F, -2.0F, 0.0F, 11.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, 0.0F, 0.0F, 2.9232F, 0.0F, 3.1321F));

        PartDefinition brushed_beard = head.getChild("brushed_beard");
        PartDefinition brushed_beard_cube = brushed_beard.addChild("brushed_beard_cube", CubeListBuilder.create().uv(54, 31).cuboid(-3.0F, -2.0F, -2.0F, 6.0F, 10.0F, 5.0F, new CubeDeformation(-0.1F)), PartPose.of(0.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

        PartDefinition neck_r1 = head.addChild("neck_r1", CubeListBuilder.create().uv(36, 2).cuboid(-5.0F, -6.0F, -1.0F, 6.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.of(2.0F, 2.1213F, -1.636F, 0.7854F, 0.0F, 0.0F));

        PartDefinition head_r1 = head.addChild("head_r1", CubeListBuilder.create().uv(0, 0).cuboid(-5.0F, -7.0F, -1.0F, 6.0F, 7.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.of(2.0F, 0.0F, -8.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition horns = head.getChild("horns");

        PartDefinition long_left_horn = horns.getChild("long_left_horn");
        PartDefinition long_right_horn = horns.getChild("long_right_horn");
        PartDefinition long_left_horn_cube = long_left_horn.addChild("long_left_horn_cube", CubeListBuilder.create().uv(0, 113).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)), PartPose.of(13.0F, -1.0F, 2.0F, 1.4443F, 0.0338F, 0.2597F));
        PartDefinition long_right_horn_cube = long_right_horn.addChild("long_right_horn_cube", CubeListBuilder.create().uv(0, 113).mirrored().cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 13.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.of(9.0F, -1.0F, 2.0F, 1.4443F, -0.0338F, -0.2597F));

        PartDefinition normal_left_horn = horns.getChild("normal_left_horn");
        PartDefinition normal_right_horn = horns.getChild("normal_right_horn");
        PartDefinition normal_left_horn_cube = normal_left_horn.addChild("normal_left_horn_cube", CubeListBuilder.create().uv(23, 0).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.of(13.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));
        PartDefinition normal_right_horn_cube = normal_right_horn.addChild("normal_right_horn_cube", CubeListBuilder.create().uv(23, 0).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.of(9.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition tiny_left_horn = horns.getChild("tiny_left_horn");
        PartDefinition tiny_right_horn = horns.getChild("tiny_right_horn");
        PartDefinition tiny_left_horn_cube = tiny_left_horn.addChild("tiny_left_horn_cube", CubeListBuilder.create().uv(28, 5).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.of(13.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));
        PartDefinition tiny_right_horn_cube = tiny_right_horn.addChild("tiny_right_horn_cube", CubeListBuilder.create().uv(28, 5).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.of(9.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition curly_left_horn = horns.getChild("curly_left_horn");
        PartDefinition curly_right_horn = horns.getChild("curly_right_horn");
        PartDefinition curly_left_horn5_r1 = curly_left_horn.addChild("curly_left_horn5_r1", CubeListBuilder.create().uv(58, 107).cuboid(3.0F, 2.0F, -3.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(57, 102).cuboid(3.0F, 4.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .uv(59, 95).cuboid(3.0F, 1.0F, 2.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(57, 90).cuboid(1.0F, -1.0F, 2.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(56, 82).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.of(14.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        PartDefinition curly_right_horn5_r1 = curly_right_horn.addChild("curly_right_horn5_r1", CubeListBuilder.create().uv(37, 108).cuboid(3.0F, 2.0F, -3.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(36, 102).cuboid(3.0F, 4.0F, -1.0F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .uv(37, 95).cuboid(3.0F, 1.0F, 2.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(35, 90).cuboid(3.0F, -1.0F, 2.0F, 4.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(37, 82).cuboid(7.0F, -1.0F, -1.0F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition swirly_left_horn = horns.getChild("swirly_left_horn");
        PartDefinition swirly_right_horn = horns.getChild("swirly_right_horn");
        PartDefinition swirly_left_horn_tip_r1 = swirly_left_horn.addChild("swirly_left_horn_tip_r1", CubeListBuilder.create().uv(21, 80).cuboid(0.0F, 4.0F, -5.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .uv(1, 81).cuboid(0.0F, 0.0F, -2.0F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.of(13.0F, -1.0F, 1.0F, 0.7854F, 0.0F, 0.0F));
        PartDefinition swirly_right_horn_tip_r1 = swirly_right_horn.addChild("swirly_right_horn_tip_r1", CubeListBuilder.create().uv(21, 94).cuboid(-8.0F, 4.0F, -5.0F, 4.0F, 3.0F, 3.0F, new CubeDeformation(0.0F))
                .uv(1, 95).cuboid(-8.0F, 0.0F, -2.0F, 4.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.of(13.0F, -1.0F, 1.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition wide_left_horn = horns.getChild("wide_left_horn");
        PartDefinition wide_right_horn = horns.getChild("wide_right_horn");
        PartDefinition wide_left_horn_cube = wide_left_horn.addChild("wide_left_horn_cube", CubeListBuilder.create(), PartPose.of(13.0F, 0.6F, -0.1F, 0.2012F, -0.2955F, 0.2578F));
        PartDefinition wide_right_horn_cube = wide_right_horn.addChild("wide_right_horn_cube", CubeListBuilder.create(), PartPose.of(9.0F, 0.6F, -0.1F, 0.2012F, 0.2955F, -0.2578F));
        PartDefinition wide_left_horn_tip_r1 = wide_left_horn_cube.addChild("wide_left_horn_tip_r1", CubeListBuilder.create().uv(59, 117).mirrored().cuboid(-11.0F, -3.0F, 0.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(35, 119).mirrored().cuboid(-21.0F, -3.0F, 2.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(17, 115).mirrored().cuboid(-21.0F, -3.0F, -4.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.of(20.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));
        PartDefinition wide_right_horn_tip_r1 = wide_right_horn_cube.addChild("wide_right_horn_tip_r1", CubeListBuilder.create().uv(59, 117).cuboid(9.0F, -3.0F, 0.0F, 2.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
                .uv(35, 119).cuboid(11.0F, -3.0F, 2.0F, 10.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(17, 115).cuboid(18.0F, -3.0F, -4.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.of(-20.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition huge_left_horn = horns.getChild("huge_left_horn");
        PartDefinition huge_right_horn = horns.getChild("huge_right_horn");
        PartDefinition huge_left_horn_cube = huge_left_horn.addChild("huge_left_horn_cube", CubeListBuilder.create(), PartPose.of(12.6F, 1.0F, -0.5F, 0.0576F, -0.2575F, 0.287F));
        PartDefinition huge_right_horn_cube = huge_right_horn.addChild("huge_right_horn_cube", CubeListBuilder.create(), PartPose.of(9.4F, 1.0F, -0.5F, 0.0576F, 0.2575F, -0.287F));
        PartDefinition huge_left_horn4_r1 = huge_left_horn_cube.addChild("huge_left_horn4_r1", CubeListBuilder.create().uv(85, 88).cuboid(-1.0F, -3.0F, 8.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F))
                .uv(86, 95).cuboid(-1.0F, -12.0F, 11.0F, 3.0F, 9.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(67, 94).cuboid(-1.0F, -12.0F, -1.0F, 3.0F, 3.0F, 12.0F, new CubeDeformation(0.0F))
                .uv(73, 109).cuboid(-1.0F, -12.0F, -5.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, -2.0F, 3.0F, 0.7854F, 0.0F, 0.0F));
        PartDefinition huge_right_horn5_r1 = huge_right_horn_cube.addChild("huge_right_horn5_r1", CubeListBuilder.create().uv(85, 88).mirrored().cuboid(-2.0F, -3.0F, 8.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(86, 95).mirrored().cuboid(-2.0F, -12.0F, 11.0F, 3.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(67, 94).mirrored().cuboid(-2.0F, -12.0F, -1.0F, 3.0F, 3.0F, 12.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(73, 109).mirrored().cuboid(-2.0F, -12.0F, -5.0F, 3.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.of(0.0F, -2.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        PartDefinition left_front_leg = broadhoof_goat.getChild("left_front_leg");
        PartDefinition left_hind_leg = broadhoof_goat.getChild("left_hind_leg");
        PartDefinition right_front_leg = broadhoof_goat.getChild("right_front_leg");
        PartDefinition right_hind_leg = broadhoof_goat.getChild("right_hind_leg");

        PartDefinition left_front_leg_cube = left_front_leg.addChild("left_front_leg", CubeListBuilder.create().uv(43, 17).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 0.0F, 0.0F));
        PartDefinition left_hind_leg_cube = left_hind_leg.addChild("left_hind_leg", CubeListBuilder.create().uv(61, 17).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 0.0F, 0.0F));
        PartDefinition right_front_leg_cube = right_front_leg.addChild("right_front_leg", CubeListBuilder.create().uv(43, 17).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(0.0F, 0.0F, 0.0F));
        PartDefinition right_hind_leg_cube = right_hind_leg.addChild("right_hind_leg", CubeListBuilder.create().uv(61, 17).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 128, 128);
    }

    public static MeshDefinition getModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition broadhoof_goat = modelPartData.addChild("broadhoof_goat", CubeListBuilder.create(), PartPose.pivot(0.0F, 24.0F, 0.0F));

        PartDefinition body = broadhoof_goat.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, -11.0F, 2.0F));

        PartDefinition head = body.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.of(0.0F, -6.0F, -9.0F, -0.2182F, 0.0F, 0.0F));

        PartDefinition wild_beard = head.addChild("wild_beard", CubeListBuilder.create(), PartPose.pivot(0.0F, 2.0F, -4.0F));

        PartDefinition brushed_beard = head.addChild("brushed_beard", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, -7.0F));

        PartDefinition hair = head.addChild("hair", CubeListBuilder.create().uv(100, 91).cuboid(-4.0F, -2.0F, -1.0F, 8.0F, 7.0F, 6.0F, new CubeDeformation(-0.1F)), PartPose.of(0.0F, -9.0F, -9.0F, 0.3054F, 0.0F, 0.0F));

        PartDefinition horns = head.addChild("horns", CubeListBuilder.create(), PartPose.pivot(-11.0F, -11.0208F, -7.4645F));

        PartDefinition long_left_horn = horns.addChild("long_left_horn", CubeListBuilder.create(), PartPose.of(0f, 0f, 0f, 0f, 0f, 0f));
        PartDefinition long_right_horn = horns.addChild("long_right_horn", CubeListBuilder.create(), PartPose.of(0f, 0f, 0f, 0f, 0f, 0f));

        PartDefinition normal_left_horn = horns.addChild("normal_left_horn", CubeListBuilder.create(), PartPose.of(0F, 0F, 0F, 0F, 0.0F, 0.0F));
        PartDefinition normal_right_horn = horns.addChild("normal_right_horn", CubeListBuilder.create(), PartPose.of(0F, 0F, 0F, 0F, 0.0F, 0.0F));

        PartDefinition tiny_left_horn = horns.addChild("tiny_left_horn", CubeListBuilder.create(), PartPose.of(0F, -0F, 0F, 0F, 0.0F, 0.0F));
        PartDefinition tiny_right_horn = horns.addChild("tiny_right_horn", CubeListBuilder.create(), PartPose.of(0F, 0F, 0F, 0F, 0.0F, 0.0F));

        PartDefinition curly_left_horn = horns.addChild("curly_left_horn", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        PartDefinition curly_right_horn = horns.addChild("curly_right_horn", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition swirly_left_horn = horns.addChild("swirly_left_horn", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        PartDefinition swirly_right_horn = horns.addChild("swirly_right_horn", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition wide_left_horn = horns.addChild("wide_left_horn", CubeListBuilder.create(), PartPose.of(0F, 0F, -0F, 0F, 0F, 0F));
        PartDefinition wide_right_horn = horns.addChild("wide_right_horn", CubeListBuilder.create(), PartPose.of(0F, 0F, 0F, 0F, 0F, 0F));

        PartDefinition huge_left_horn = horns.addChild("huge_left_horn", CubeListBuilder.create(), PartPose.of(0F, 0F, 0F, 0F, 0F, 0F));
        PartDefinition huge_right_horn = horns.addChild("huge_right_horn", CubeListBuilder.create(), PartPose.of(0F, 0F, 0F, 0F, 0F, 0F));

        PartDefinition left_front_leg = broadhoof_goat.addChild("left_front_leg", CubeListBuilder.create(), PartPose.pivot(4.0F, -9.0F, -6.0F));
        PartDefinition left_hind_leg = broadhoof_goat.addChild("left_hind_leg", CubeListBuilder.create(), PartPose.pivot(3.0F, -9.0F, 7.0F));
        PartDefinition right_front_leg = broadhoof_goat.addChild("right_front_leg", CubeListBuilder.create(), PartPose.pivot(-4.0F, -9.0F, -6.0F));
        PartDefinition right_hind_leg = broadhoof_goat.addChild("right_hind_leg", CubeListBuilder.create(), PartPose.pivot(-3.0F, -9.0F, 7.0F));

        return modelData;
    }

    @Override
    public ModelPart getPart() {
        return this.broadhoofGoat;
    }

    @Override
    public void setAngles(BroadhoofGoatEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        for(int i = 0 ; i < BroadhoofGoatHorns.values().length; i++) {
            this.leftHorns[i].visible = (entity.getHorns().getId() == i) && entity.hasLeftHorn() && !entity.isBaby();
            this.rightHorns[i].visible = (entity.getHorns().getId() == i) && entity.hasRightHorn() && !entity.isBaby();
        }

        this.hair.visible = entity.hasHair();

        this.wildBeard.visible = !entity.hasBrushedBeard();
        this.brushedBeard.visible = entity.hasBrushedBeard();

        if(!entity.isSprinting() && !(entity.getControllingPassenger() != null && entity.getControllingPassenger().isSprinting()) && !entity.isCharging()) {
            this.animateMovement(BroadhoofGoatAnimations.WALK, limbSwing, limbSwingAmount, 4f, 4f);
        }
        else {
            this.animateMovement(BroadhoofGoatAnimations.RUN, limbSwing, limbSwingAmount, 1.2f, 1.2f);
        }

        this.updateAnimation(entity.idleAnimationState, BroadhoofGoatAnimations.EAT, ageInTicks);
        this.updateAnimation(entity.attackAnimationState, BroadhoofGoatAnimations.RAM_ATTACK, ageInTicks);
        this.updateAnimation(entity.startSittingAnimationState, BroadhoofGoatAnimations.LAY_DOWN, ageInTicks);
        this.updateAnimation(entity.stopSittingAnimationState, BroadhoofGoatAnimations.STAND_UP, ageInTicks);
        this.updateAnimation(entity.sittingAnimationState, BroadhoofGoatAnimations.LYING, ageInTicks);
        this.updateAnimation(entity.chargeAnimationState, BroadhoofGoatAnimations.CHARGE_ATTACK, ageInTicks);
        this.updateAnimation(entity.jumpAnimationState, BroadhoofGoatAnimations.JUMP, ageInTicks);

    }

    public void setAngles(BroadhoofGoatEntityRenderState state) {
        if (state.entity instanceof BroadhoofGoatEntity entity) {
            this.setAngles(entity, state.limbSwingAnimationProgress, state.limbSwingAmplitude,
                    state.age, state.relativeHeadYaw, state.relativeHeadPitch);
        }
    }
}
