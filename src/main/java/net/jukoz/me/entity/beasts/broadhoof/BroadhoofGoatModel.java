package net.jukoz.me.entity.beasts.broadhoof;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;

public class BroadhoofGoatModel extends SinglePartEntityModel<BroadhoofGoatEntity> {
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
    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = getModelData();
        ModelPartData root = modelData.getRoot();
        ModelPartData broadhoof_goat = root.getChild("broadhoof_goat");
        ModelPartData body = broadhoof_goat.getChild(PartNames.BODY);
        ModelPartData head = body.getChild(PartNames.HEAD);

        ModelPartData head_cube = head.addChild(PartNames.HEAD, ModelPartBuilder.create().uv(0, 0).mirrored().cuboid(2.9F, -11.0F, -6.0F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)).mirrored(false)
                .uv(0, 0).cuboid(-5.9F, -11.0F, -6.0F, 3.0F, 2.0F, 1.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));


        ModelPartData body_cube = body.addChild(PartNames.BODY, ModelPartBuilder.create().uv(2, 48).cuboid(-6.0F, -9.0F, -11.0F, 12.0F, 11.0F, 20.0F, new Dilation(0.0F))
                .uv(0, 18).cuboid(-7.0F, -11.0F, -12.0F, 14.0F, 16.0F, 13.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData tail_r1 = body.addChild("tail_r1", ModelPartBuilder.create().uv(86, 1).cuboid(-2.0F, -3.0F, 0.0F, 4.0F, 3.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -6.0F, 9.0F, 0.4363F, 0.0F, 0.0F));

        ModelPartData wild_beard = head.getChild("wild_beard");
        ModelPartData wild_beard_cube = wild_beard.addChild("wild_beard_cube", ModelPartBuilder.create().uv(3, 47).mirrored().cuboid(0.0F, -5.0F, -2.0F, 0.0F, 11.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 2.9232F, 0.0F, 3.1321F));

        ModelPartData brushed_beard = head.getChild("brushed_beard");
        ModelPartData brushed_beard_cube = brushed_beard.addChild("brushed_beard_cube", ModelPartBuilder.create().uv(54, 31).cuboid(-3.0F, -2.0F, -2.0F, 6.0F, 10.0F, 5.0F, new Dilation(-0.1F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.2182F, 0.0F, 0.0F));

        ModelPartData neck_r1 = head.addChild("neck_r1", ModelPartBuilder.create().uv(36, 2).cuboid(-5.0F, -6.0F, -1.0F, 6.0F, 6.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 2.1213F, -1.636F, 0.7854F, 0.0F, 0.0F));

        ModelPartData head_r1 = head.addChild("head_r1", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -7.0F, -1.0F, 6.0F, 7.0F, 11.0F, new Dilation(0.0F)), ModelTransform.of(2.0F, 0.0F, -8.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData horns = head.getChild("horns");

        ModelPartData long_left_horn = horns.getChild("long_left_horn");
        ModelPartData long_right_horn = horns.getChild("long_right_horn");
        ModelPartData long_left_horn_cube = long_left_horn.addChild("long_left_horn_cube", ModelPartBuilder.create().uv(0, 113).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 13.0F, new Dilation(0.0F)), ModelTransform.of(13.0F, -1.0F, 2.0F, 1.4443F, 0.0338F, 0.2597F));
        ModelPartData long_right_horn_cube = long_right_horn.addChild("long_right_horn_cube", ModelPartBuilder.create().uv(0, 113).mirrored().cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 13.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(9.0F, -1.0F, 2.0F, 1.4443F, -0.0338F, -0.2597F));

        ModelPartData normal_left_horn = horns.getChild("normal_left_horn");
        ModelPartData normal_right_horn = horns.getChild("normal_right_horn");
        ModelPartData normal_left_horn_cube = normal_left_horn.addChild("normal_left_horn_cube", ModelPartBuilder.create().uv(23, 0).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(13.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData normal_right_horn_cube = normal_right_horn.addChild("normal_right_horn_cube", ModelPartBuilder.create().uv(23, 0).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 8.0F, new Dilation(0.0F)), ModelTransform.of(9.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData tiny_left_horn = horns.getChild("tiny_left_horn");
        ModelPartData tiny_right_horn = horns.getChild("tiny_right_horn");
        ModelPartData tiny_left_horn_cube = tiny_left_horn.addChild("tiny_left_horn_cube", ModelPartBuilder.create().uv(28, 5).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(13.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData tiny_right_horn_cube = tiny_right_horn.addChild("tiny_right_horn_cube", ModelPartBuilder.create().uv(28, 5).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(9.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData curly_left_horn = horns.getChild("curly_left_horn");
        ModelPartData curly_right_horn = horns.getChild("curly_right_horn");
        ModelPartData curly_left_horn5_r1 = curly_left_horn.addChild("curly_left_horn5_r1", ModelPartBuilder.create().uv(58, 107).cuboid(3.0F, 2.0F, -3.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(57, 102).cuboid(3.0F, 4.0F, -1.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(59, 95).cuboid(3.0F, 1.0F, 2.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F))
                .uv(57, 90).cuboid(1.0F, -1.0F, 2.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(56, 82).cuboid(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(14.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData curly_right_horn5_r1 = curly_right_horn.addChild("curly_right_horn5_r1", ModelPartBuilder.create().uv(37, 108).cuboid(3.0F, 2.0F, -3.0F, 2.0F, 4.0F, 2.0F, new Dilation(0.0F))
                .uv(36, 102).cuboid(3.0F, 4.0F, -1.0F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F))
                .uv(37, 95).cuboid(3.0F, 1.0F, 2.0F, 2.0F, 5.0F, 2.0F, new Dilation(0.0F))
                .uv(35, 90).cuboid(3.0F, -1.0F, 2.0F, 4.0F, 2.0F, 2.0F, new Dilation(0.0F))
                .uv(37, 82).cuboid(7.0F, -1.0F, -1.0F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData swirly_left_horn = horns.getChild("swirly_left_horn");
        ModelPartData swirly_right_horn = horns.getChild("swirly_right_horn");
        ModelPartData swirly_left_horn_tip_r1 = swirly_left_horn.addChild("swirly_left_horn_tip_r1", ModelPartBuilder.create().uv(21, 80).cuboid(0.0F, 4.0F, -5.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(1, 81).cuboid(0.0F, 0.0F, -2.0F, 4.0F, 7.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(13.0F, -1.0F, 1.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData swirly_right_horn_tip_r1 = swirly_right_horn.addChild("swirly_right_horn_tip_r1", ModelPartBuilder.create().uv(21, 94).cuboid(-8.0F, 4.0F, -5.0F, 4.0F, 3.0F, 3.0F, new Dilation(0.0F))
                .uv(1, 95).cuboid(-8.0F, 0.0F, -2.0F, 4.0F, 7.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(13.0F, -1.0F, 1.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData wide_left_horn = horns.getChild("wide_left_horn");
        ModelPartData wide_right_horn = horns.getChild("wide_right_horn");
        ModelPartData wide_left_horn_cube = wide_left_horn.addChild("wide_left_horn_cube", ModelPartBuilder.create(), ModelTransform.of(13.0F, 0.6F, -0.1F, 0.2012F, -0.2955F, 0.2578F));
        ModelPartData wide_right_horn_cube = wide_right_horn.addChild("wide_right_horn_cube", ModelPartBuilder.create(), ModelTransform.of(9.0F, 0.6F, -0.1F, 0.2012F, 0.2955F, -0.2578F));
        ModelPartData wide_left_horn_tip_r1 = wide_left_horn_cube.addChild("wide_left_horn_tip_r1", ModelPartBuilder.create().uv(59, 117).mirrored().cuboid(-11.0F, -3.0F, 0.0F, 2.0F, 3.0F, 4.0F, new Dilation(0.0F)).mirrored(false)
                .uv(35, 119).mirrored().cuboid(-21.0F, -3.0F, 2.0F, 10.0F, 3.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(17, 115).mirrored().cuboid(-21.0F, -3.0F, -4.0F, 3.0F, 3.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(20.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData wide_right_horn_tip_r1 = wide_right_horn_cube.addChild("wide_right_horn_tip_r1", ModelPartBuilder.create().uv(59, 117).cuboid(9.0F, -3.0F, 0.0F, 2.0F, 3.0F, 4.0F, new Dilation(0.0F))
                .uv(35, 119).cuboid(11.0F, -3.0F, 2.0F, 10.0F, 3.0F, 2.0F, new Dilation(0.0F))
                .uv(17, 115).cuboid(18.0F, -3.0F, -4.0F, 3.0F, 3.0F, 6.0F, new Dilation(0.0F)), ModelTransform.of(-20.0F, -1.0F, 3.0F, 0.7854F, 0.0F, 0.0F));

        ModelPartData huge_left_horn = horns.getChild("huge_left_horn");
        ModelPartData huge_right_horn = horns.getChild("huge_right_horn");
        ModelPartData huge_left_horn_cube = huge_left_horn.addChild("huge_left_horn_cube", ModelPartBuilder.create(), ModelTransform.of(12.6F, 1.0F, -0.5F, 0.0576F, -0.2575F, 0.287F));
        ModelPartData huge_right_horn_cube = huge_right_horn.addChild("huge_right_horn_cube", ModelPartBuilder.create(), ModelTransform.of(9.4F, 1.0F, -0.5F, 0.0576F, 0.2575F, -0.287F));
        ModelPartData huge_left_horn4_r1 = huge_left_horn_cube.addChild("huge_left_horn4_r1", ModelPartBuilder.create().uv(85, 88).cuboid(-1.0F, -3.0F, 8.0F, 3.0F, 2.0F, 5.0F, new Dilation(0.0F))
                .uv(86, 95).cuboid(-1.0F, -12.0F, 11.0F, 3.0F, 9.0F, 2.0F, new Dilation(0.0F))
                .uv(67, 94).cuboid(-1.0F, -12.0F, -1.0F, 3.0F, 3.0F, 12.0F, new Dilation(0.0F))
                .uv(73, 109).cuboid(-1.0F, -12.0F, -5.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -2.0F, 3.0F, 0.7854F, 0.0F, 0.0F));
        ModelPartData huge_right_horn5_r1 = huge_right_horn_cube.addChild("huge_right_horn5_r1", ModelPartBuilder.create().uv(85, 88).mirrored().cuboid(-2.0F, -3.0F, 8.0F, 3.0F, 2.0F, 5.0F, new Dilation(0.0F)).mirrored(false)
                .uv(86, 95).mirrored().cuboid(-2.0F, -12.0F, 11.0F, 3.0F, 9.0F, 2.0F, new Dilation(0.0F)).mirrored(false)
                .uv(67, 94).mirrored().cuboid(-2.0F, -12.0F, -1.0F, 3.0F, 3.0F, 12.0F, new Dilation(0.0F)).mirrored(false)
                .uv(73, 109).mirrored().cuboid(-2.0F, -12.0F, -5.0F, 3.0F, 12.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.of(0.0F, -2.0F, 3.0F, 0.7854F, 0.0F, 0.0F));


        ModelPartData left_front_leg = broadhoof_goat.getChild("left_front_leg");
        ModelPartData left_hind_leg = broadhoof_goat.getChild("left_hind_leg");
        ModelPartData right_front_leg = broadhoof_goat.getChild("right_front_leg");
        ModelPartData right_hind_leg = broadhoof_goat.getChild("right_hind_leg");

        ModelPartData left_front_leg_cube = left_front_leg.addChild("left_front_leg", ModelPartBuilder.create().uv(43, 17).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData left_hind_leg_cube = left_hind_leg.addChild("left_hind_leg", ModelPartBuilder.create().uv(61, 17).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData right_front_leg_cube = right_front_leg.addChild("right_front_leg", ModelPartBuilder.create().uv(43, 17).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData right_hind_leg_cube = right_hind_leg.addChild("right_hind_leg", ModelPartBuilder.create().uv(61, 17).mirrored().cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 9.0F, 4.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 128, 128);
    }



    public static ModelData getModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        ModelPartData broadhoof_goat = modelPartData.addChild("broadhoof_goat", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        ModelPartData body = broadhoof_goat.addChild(PartNames.BODY, ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -11.0F, 2.0F));

        ModelPartData head = body.addChild(PartNames.HEAD, ModelPartBuilder.create(), ModelTransform.of(0.0F, -6.0F, -9.0F, -0.2182F, 0.0F, 0.0F));

        ModelPartData wild_beard = head.addChild("wild_beard", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 2.0F, -4.0F));

        ModelPartData brushed_beard = head.addChild("brushed_beard", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, -7.0F));

        ModelPartData hair = head.addChild("hair", ModelPartBuilder.create().uv(100, 91).cuboid(-4.0F, -2.0F, -1.0F, 8.0F, 7.0F, 6.0F, new Dilation(-0.1F)), ModelTransform.of(0.0F, -9.0F, -9.0F, 0.3054F, 0.0F, 0.0F));

        ModelPartData horns = head.addChild("horns", ModelPartBuilder.create(), ModelTransform.pivot(-11.0F, -11.0208F, -7.4645F));

        ModelPartData long_left_horn = horns.addChild("long_left_horn", ModelPartBuilder.create(), ModelTransform.of(0f, 0f, 0f, 0f, 0f, 0f));
        ModelPartData long_right_horn = horns.addChild("long_right_horn", ModelPartBuilder.create(), ModelTransform.of(0f, 0f, 0f, 0f, 0f, 0f));

        ModelPartData normal_left_horn = horns.addChild("normal_left_horn", ModelPartBuilder.create(), ModelTransform.of(0F, 0F, 0F, 0F, 0.0F, 0.0F));
        ModelPartData normal_right_horn = horns.addChild("normal_right_horn", ModelPartBuilder.create(), ModelTransform.of(0F, 0F, 0F, 0F, 0.0F, 0.0F));

        ModelPartData tiny_left_horn = horns.addChild("tiny_left_horn", ModelPartBuilder.create(), ModelTransform.of(0F, -0F, 0F, 0F, 0.0F, 0.0F));
        ModelPartData tiny_right_horn = horns.addChild("tiny_right_horn", ModelPartBuilder.create(), ModelTransform.of(0F, 0F, 0F, 0F, 0.0F, 0.0F));

        ModelPartData curly_left_horn = horns.addChild("curly_left_horn", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData curly_right_horn = horns.addChild("curly_right_horn", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData swirly_left_horn = horns.addChild("swirly_left_horn", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData swirly_right_horn = horns.addChild("swirly_right_horn", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData wide_left_horn = horns.addChild("wide_left_horn", ModelPartBuilder.create(), ModelTransform.of(0F, 0F, -0F, 0F, 0F, 0F));
        ModelPartData wide_right_horn = horns.addChild("wide_right_horn", ModelPartBuilder.create(), ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));

        ModelPartData huge_left_horn = horns.addChild("huge_left_horn", ModelPartBuilder.create(), ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));
        ModelPartData huge_right_horn = horns.addChild("huge_right_horn", ModelPartBuilder.create(), ModelTransform.of(0F, 0F, 0F, 0F, 0F, 0F));

        ModelPartData left_front_leg = broadhoof_goat.addChild("left_front_leg", ModelPartBuilder.create(), ModelTransform.pivot(4.0F, -9.0F, -6.0F));
        ModelPartData left_hind_leg = broadhoof_goat.addChild("left_hind_leg", ModelPartBuilder.create(), ModelTransform.pivot(3.0F, -9.0F, 7.0F));
        ModelPartData right_front_leg = broadhoof_goat.addChild("right_front_leg", ModelPartBuilder.create(), ModelTransform.pivot(-4.0F, -9.0F, -6.0F));
        ModelPartData right_hind_leg = broadhoof_goat.addChild("right_hind_leg", ModelPartBuilder.create(), ModelTransform.pivot(-3.0F, -9.0F, 7.0F));

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