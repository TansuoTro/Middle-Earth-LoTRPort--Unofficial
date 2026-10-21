package net.jukoz.me.entity.balrog;

import net.minecraft.client.model.*;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HierarchicalModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.util.Mth;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class BalrogModel<T extends BalrogEntity> extends HierarchicalModel<BalrogEntity> {
    private final ModelPart balrog;
    private final ModelPart head;
    private final ModelPart body;
    private final ModelPart tail;
    private final ModelPart leftwing;
    private final ModelPart rightwing;
    private final ModelPart leftarm;
    private final ModelPart rightarm;
    private final ModelPart leftleg;
    private final ModelPart rightleg;
    private static final float ROTATION_SPEED = 0.5f;

    public BalrogModel(ModelPart root) {
        this.balrog = root.getChild("balrog");
        this.body = balrog.getChild("body");
        this.head = balrog.getChild("head");
        this.tail = balrog.getChild("tail");
        this.leftwing = balrog.getChild("leftwing");
        this.rightwing = balrog.getChild("rightwing");
        this.leftarm = balrog.getChild("leftarm");
        this.rightarm = balrog.getChild("rightarm");
        this.leftleg = balrog.getChild("leftleg");
        this.rightleg = balrog.getChild("rightleg");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition balrog = modelPartData.addChild("balrog", CubeListBuilder.create(), PartPose.pivot(0.0F, 24.0F, 0.0F));

        PartDefinition head = balrog.addChild("head", CubeListBuilder.create(), PartPose.of(0.0338F, -33.7313F, -1.9332F, -0.0436F, 0.0F, 0.0F));

        PartDefinition lowerjaw = head.addChild("lowerjaw", CubeListBuilder.create().uv(29, 57).cuboid(-3.6512F, -0.4959F, -7.4777F, 7.0F, 4.0F, 8.0F, new CubeDeformation(0.0F))
                .uv(60, 37).cuboid(-3.6512F, -2.4959F, -7.4777F, 7.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.1512F, -1.5041F, 0.7277F));

        PartDefinition cube_r1 = lowerjaw.addChild("cube_r1", CubeListBuilder.create().uv(84, 20).cuboid(-3.4995F, -1.9887F, -1.971F, 5.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(0.8488F, 2.5367F, -4.8198F, 1.1345F, 0.0F, 0.0F));

        PartDefinition upperjaw = head.addChild("upperjaw", CubeListBuilder.create().uv(0, 63).cuboid(-4.741F, -3.9783F, -6.4895F, 6.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.pivot(1.741F, -2.0217F, 0.4895F));

        PartDefinition lefthorn = upperjaw.addChild("lefthorn", CubeListBuilder.create().uv(83, 71).cuboid(-1.0F, -2.0F, -1.5007F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.of(-0.0227F, -3.0227F, -3.8955F, -0.5025F, -0.4661F, -0.0836F));

        PartDefinition cube_r2 = lefthorn.addChild("cube_r2", CubeListBuilder.create().uv(51, 70).cuboid(1.0F, -2.0F, -0.003F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(2.8739F, 6.1206F, -0.5378F, -0.0385F, -0.0378F, 2.6273F));

        PartDefinition cube_r3 = lefthorn.addChild("cube_r3", CubeListBuilder.create().uv(62, 74).cuboid(-3.0F, -2.0F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(2.0039F, 6.6122F, -0.5F, 0.0F, 0.0F, 3.0543F));

        PartDefinition cube_r4 = lefthorn.addChild("cube_r4", CubeListBuilder.create().uv(91, 45).cuboid(-3.0F, -2.0F, -0.5F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(3.8812F, 6.811F, -0.5F, 0.0F, 0.0F, 2.4435F));

        PartDefinition cube_r5 = lefthorn.addChild("cube_r5", CubeListBuilder.create().uv(16, 93).cuboid(-3.0F, -2.0F, -0.5007F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(5.5855F, 5.7307F, -0.5F, 0.0F, 0.0F, 1.9199F));

        PartDefinition cube_r6 = lefthorn.addChild("cube_r6", CubeListBuilder.create().uv(52, 57).cuboid(-3.0F, -2.0F, -0.5F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(6.322F, 3.0522F, -0.5F, 0.0F, 0.0F, 1.3526F));

        PartDefinition cube_r7 = lefthorn.addChild("cube_r7", CubeListBuilder.create().uv(85, 59).cuboid(-3.0F, -2.0F, -1.5F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.of(5.8737F, 1.0805F, 0.0F, 0.0F, 0.0F, 0.4363F));

        PartDefinition righthorn = upperjaw.addChild("righthorn", CubeListBuilder.create().uv(83, 71).cuboid(-4.0F, -2.0F, -1.5007F, 5.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.of(-3.5269F, -3.0227F, -3.8955F, -0.5025F, 0.4661F, 0.0836F));

        PartDefinition cube_r8 = righthorn.addChild("cube_r8", CubeListBuilder.create().uv(51, 70).cuboid(-4.0F, -2.0F, -0.003F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(-2.8739F, 6.1206F, -0.5378F, -0.0385F, 0.0378F, -2.6273F));

        PartDefinition cube_r9 = righthorn.addChild("cube_r9", CubeListBuilder.create().uv(62, 74).cuboid(0.0F, -2.0F, 0.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(-2.0039F, 6.6122F, -0.5F, 0.0F, 0.0F, -3.0543F));

        PartDefinition cube_r10 = righthorn.addChild("cube_r10", CubeListBuilder.create().uv(91, 45).cuboid(0.0F, -2.0F, -0.5F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(-3.8812F, 6.811F, -0.5F, 0.0F, 0.0F, -2.4435F));

        PartDefinition cube_r11 = righthorn.addChild("cube_r11", CubeListBuilder.create().uv(16, 93).cuboid(0.0F, -2.0F, -0.5007F, 3.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(-5.5855F, 5.7307F, -0.5F, 0.0F, 0.0F, -1.9199F));

        PartDefinition cube_r12 = righthorn.addChild("cube_r12", CubeListBuilder.create().uv(52, 57).cuboid(-1.0F, -2.0F, -0.5F, 4.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(-6.322F, 3.0522F, -0.5F, 0.0F, 0.0F, -1.3526F));

        PartDefinition cube_r13 = righthorn.addChild("cube_r13", CubeListBuilder.create().uv(85, 59).cuboid(-1.0F, -2.0F, -1.5F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.of(-5.8737F, 1.0805F, 0.0F, 0.0F, 0.0F, -0.4363F));

        PartDefinition body = balrog.addChild("body", CubeListBuilder.create().uv(0, 51).cuboid(-5.9697F, -4.7313F, -2.2867F, 12.0F, 5.0F, 6.0F, new CubeDeformation(0.0F))
                .uv(0, 27).cuboid(-6.9697F, -16.7313F, -2.7867F, 14.0F, 12.0F, 7.0F, new CubeDeformation(0.0F))
                .uv(46, 20).cuboid(-8.4697F, -17.2313F, -3.2867F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
                .uv(35, 40).cuboid(0.5303F, -17.2313F, -3.2867F, 8.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, -18.0F, 0.0F, 0.172F, -0.0196F, -0.0242F));

        PartDefinition tail = balrog.addChild("tail", CubeListBuilder.create().uv(70, 17).cuboid(-2.0F, -2.5F, -2.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.of(-0.4576F, -16.7238F, 2.7986F, 0.6545F, 0.0F, 0.0F));

        PartDefinition tail2 = tail.addChild("tail2", CubeListBuilder.create().uv(82, 0).cuboid(-2.0F, -2.5F, -1.0F, 4.0F, 6.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.of(0.5F, 4.0F, 0.25F, 0.48F, 0.0F, 0.0F));

        PartDefinition tail3 = tail2.addChild("tail3", CubeListBuilder.create().uv(32, 83).cuboid(-1.5F, 2.6428F, -2.2531F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, 0.0F, 0.0F, 0.48F, 0.0F, 0.0F));

        PartDefinition tail4 = tail3.addChild("tail4", CubeListBuilder.create().uv(54, 88).cuboid(-1.0F, -1.3572F, -1.3336F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, 10.0F, 0.0F, 0.48F, 0.0F, 0.0F));

        PartDefinition leftwing = balrog.addChild("leftwing", CubeListBuilder.create().uv(68, 48).cuboid(-1.0F, -5.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(0, 0).cuboid(-2.7673F, -16.8742F, 0.0F, 26.0F, 27.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.of(6.5303F, -32.2313F, 4.2133F, -0.3151F, -0.3154F, 0.8754F));

        PartDefinition cube_r14 = leftwing.addChild("cube_r14", CubeListBuilder.create().uv(11, 92).cuboid(7.0F, 9.0F, -0.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(2.1964F, -3.4436F, 0.0F, 0.0F, 0.0F, -0.5236F));

        PartDefinition cube_r15 = leftwing.addChild("cube_r15", CubeListBuilder.create().uv(92, 21).cuboid(7.0F, 2.0F, -0.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(-0.5176F, -2.4778F, 0.0F, 0.0F, 0.0F, -0.7854F));

        PartDefinition cube_r16 = leftwing.addChild("cube_r16", CubeListBuilder.create().uv(45, 88).cuboid(5.0F, -10.0F, -1.4933F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(5.3106F, -0.2488F, 0.5F, 0.0F, 0.0F, -1.2217F));

        PartDefinition cube_r17 = leftwing.addChild("cube_r17", CubeListBuilder.create().uv(80, 87).cuboid(7.0F, 2.0F, 0.0F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(8.0868F, -6.2611F, -0.5F, 0.0F, 0.0F, -0.6545F));

        PartDefinition cube_r18 = leftwing.addChild("cube_r18", CubeListBuilder.create().uv(92, 33).cuboid(7.0F, 2.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(1.2327F, -9.8742F, -0.5F, 0.0F, 0.0F, -0.9599F));

        PartDefinition cube_r19 = leftwing.addChild("cube_r19", CubeListBuilder.create().uv(0, 75).cuboid(-1.0F, -6.5F, -6.5F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.of(-2.0896F, -16.0288F, 0.0F, 0.0F, 0.0F, -0.8727F));

        PartDefinition cube_r20 = leftwing.addChild("cube_r20", CubeListBuilder.create().uv(86, 85).cuboid(7.0F, -8.0F, -1.4933F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(3.3106F, -7.2488F, 0.5F, 0.0F, 0.0F, -1.4399F));

        PartDefinition cube_r21 = leftwing.addChild("cube_r21", CubeListBuilder.create().uv(83, 28).cuboid(-1.0F, -10.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(-0.9658F, -7.4771F, 0.0F, 0.0F, 0.0F, -0.3491F));

        PartDefinition rightwing = balrog.addChild("rightwing", CubeListBuilder.create().uv(68, 48).cuboid(-1.0F, -5.0F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(0, 0).mirrored().cuboid(-23.2327F, -16.8742F, 0.0F, 26.0F, 27.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.of(-6.5303F, -32.2313F, 4.2133F, -0.3151F, 0.3154F, -0.8754F));

        PartDefinition cube_r22 = rightwing.addChild("cube_r22", CubeListBuilder.create().uv(11, 92).cuboid(-8.0F, 9.0F, -0.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(-2.1964F, -3.4436F, 0.0F, 0.0F, 0.0F, 0.5236F));

        PartDefinition cube_r23 = rightwing.addChild("cube_r23", CubeListBuilder.create().uv(92, 21).cuboid(-8.0F, 2.0F, -0.5F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(0.5176F, -2.4778F, 0.0F, 0.0F, 0.0F, 0.7854F));

        PartDefinition cube_r24 = rightwing.addChild("cube_r24", CubeListBuilder.create().uv(45, 88).cuboid(-7.0F, -10.0F, -1.5F, 2.0F, 9.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(-5.3106F, -0.2488F, 0.5F, 0.0F, 0.0F, 1.2217F));

        PartDefinition cube_r25 = rightwing.addChild("cube_r25", CubeListBuilder.create().uv(80, 87).cuboid(-8.0F, 2.0F, 0.0F, 1.0F, 13.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(-8.0868F, -6.2611F, -0.5F, 0.0F, 0.0F, 0.6545F));

        PartDefinition cube_r26 = rightwing.addChild("cube_r26", CubeListBuilder.create().uv(92, 33).cuboid(-8.0F, 2.0F, 0.0F, 1.0F, 10.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(-1.2327F, -9.8742F, -0.5F, 0.0F, 0.0F, 0.9599F));

        PartDefinition cube_r27 = rightwing.addChild("cube_r27", CubeListBuilder.create().uv(0, 75).cuboid(1.0F, -6.5F, -6.5F, 0.0F, 7.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.of(2.0896F, -16.0288F, 0.0F, 0.0F, 0.0F, 0.8727F));

        PartDefinition cube_r28 = rightwing.addChild("cube_r28", CubeListBuilder.create().uv(86, 85).cuboid(-9.0F, -8.0F, -1.5F, 2.0F, 11.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(-3.3106F, -7.2488F, 0.5F, 0.0F, 0.0F, 1.4399F));

        PartDefinition cube_r29 = rightwing.addChild("cube_r29", CubeListBuilder.create().uv(83, 28).cuboid(-1.0F, -10.0F, -1.0F, 2.0F, 13.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(0.9658F, -7.4771F, 0.0F, 0.0F, 0.0F, 0.3491F));

        PartDefinition leftarm = balrog.addChild("leftarm", CubeListBuilder.create().uv(67, 74).cuboid(-1.5F, -2.0F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F))
                .uv(53, 0).cuboid(-2.25F, 4.0F, -3.5F, 7.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.of(8.0303F, -32.2313F, 0.2133F, -0.0436F, 0.0F, -0.2182F));

        PartDefinition leftarmseg = leftarm.addChild("leftarmseg", CubeListBuilder.create().uv(21, 70).cuboid(-3.0F, -1.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.of(1.25F, 13.0F, 0.0F, 0.0F, 0.0F, 0.3927F));

        PartDefinition lefthand = leftarmseg.addChild("lefthand", CubeListBuilder.create().uv(79, 47).cuboid(-2.0F, 0.0F, -2.5F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.of(2.0F, 5.0F, 0.0F, 0.0F, 0.0F, 0.6981F));

        PartDefinition leftfing = lefthand.addChild("leftfing", CubeListBuilder.create(), PartPose.pivot(-4.2803F, -0.7687F, -3.2133F));

        PartDefinition cube_r30 = leftfing.addChild("cube_r30", CubeListBuilder.create().uv(70, 17).cuboid(-0.25F, -1.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(4.9917F, 6.5386F, 0.3357F, -0.4531F, 0.272F, 0.5042F));

        PartDefinition cube_r31 = leftfing.addChild("cube_r31", CubeListBuilder.create().uv(27, 94).cuboid(-1.0F, -1.25F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(4.7803F, 5.0187F, 1.2133F, -0.5087F, -0.1298F, -0.228F));

        PartDefinition leftfing2 = lefthand.addChild("leftfing2", CubeListBuilder.create(), PartPose.of(0.6092F, 6.1053F, -0.1381F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r32 = leftfing2.addChild("cube_r32", CubeListBuilder.create().uv(0, 63).cuboid(0.5F, -0.25F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(0.1023F, 0.4146F, -0.2394F, -0.4531F, 0.272F, 0.5042F));

        PartDefinition cube_r33 = leftfing2.addChild("cube_r33", CubeListBuilder.create().uv(36, 28).cuboid(-1.0F, -1.25F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(-0.1092F, -1.1053F, 0.6381F, -0.5087F, -0.1298F, -0.228F));

        PartDefinition leftfing3 = lefthand.addChild("leftfing3", CubeListBuilder.create(), PartPose.of(0.6092F, 5.8553F, 2.3619F, 0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r34 = leftfing3.addChild("cube_r34", CubeListBuilder.create().uv(0, 48).cuboid(-0.25F, -1.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(0.1023F, 0.4146F, -0.2394F, -0.4531F, 0.272F, 0.5042F));

        PartDefinition cube_r35 = leftfing3.addChild("cube_r35", CubeListBuilder.create().uv(20, 63).cuboid(-1.0F, -1.25F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(-0.1092F, -1.1053F, 0.6381F, -0.5087F, -0.1298F, -0.228F));

        PartDefinition rightarm = balrog.addChild("rightarm", CubeListBuilder.create().uv(67, 74).mirrored().cuboid(-3.5F, -2.0F, -2.5F, 5.0F, 7.0F, 5.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(53, 0).mirrored().cuboid(-4.75F, 4.0F, -3.5F, 7.0F, 9.0F, 7.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.of(-8.0303F, -32.2313F, 0.2133F, -0.0436F, 0.0F, 0.2182F));

        PartDefinition rightarmseg = rightarm.addChild("rightarmseg", CubeListBuilder.create().uv(21, 69).mirrored().cuboid(-3.0F, -1.0F, -3.0F, 6.0F, 6.0F, 6.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.of(-1.25F, 13.0F, 0.0F, 0.0F, 0.0F, -0.3927F));

        PartDefinition righthand = rightarmseg.addChild("righthand", CubeListBuilder.create().uv(79, 48).cuboid(-1.0F, 0.0F, -2.5F, 3.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.of(-2.0F, 5.0F, 0.0F, 0.0F, 0.0F, -0.6981F));

        PartDefinition rightfing = righthand.addChild("rightfing", CubeListBuilder.create(), PartPose.pivot(4.2803F, -0.7687F, -3.2133F));

        PartDefinition cube_r36 = rightfing.addChild("cube_r36", CubeListBuilder.create().uv(70, 17).cuboid(-0.75F, -1.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(-4.9917F, 6.5386F, 0.3357F, -0.4531F, -0.272F, -0.5042F));

        PartDefinition cube_r37 = rightfing.addChild("cube_r37", CubeListBuilder.create().uv(27, 94).cuboid(-1.0F, -1.25F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(-4.7803F, 5.0187F, 1.2133F, -0.5087F, 0.1298F, 0.228F));

        PartDefinition rightfing2 = righthand.addChild("rightfing2", CubeListBuilder.create(), PartPose.of(-0.6092F, 6.1053F, -0.1381F, 0.3927F, 0.0F, 0.0F));

        PartDefinition cube_r38 = rightfing2.addChild("cube_r38", CubeListBuilder.create().uv(0, 63).cuboid(-1.5F, -0.25F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(-0.1023F, 0.4146F, -0.2394F, -0.4531F, -0.272F, -0.5042F));

        PartDefinition cube_r39 = rightfing2.addChild("cube_r39", CubeListBuilder.create().uv(36, 28).cuboid(-1.0F, -1.25F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(0.1092F, -1.1053F, 0.6381F, -0.5087F, 0.1298F, 0.228F));

        PartDefinition rightfing3 = righthand.addChild("rightfing3", CubeListBuilder.create(), PartPose.of(-0.6092F, 5.8553F, 2.3619F, 0.6981F, 0.0F, 0.0F));

        PartDefinition cube_r40 = rightfing3.addChild("cube_r40", CubeListBuilder.create().uv(0, 48).cuboid(-0.75F, -1.0F, -0.5F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(-0.1023F, 0.4146F, -0.2394F, -0.4531F, -0.272F, -0.5042F));

        PartDefinition cube_r41 = rightfing3.addChild("cube_r41", CubeListBuilder.create().uv(20, 63).cuboid(-1.0F, -1.25F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(0.1092F, -1.1053F, 0.6381F, -0.5087F, 0.1298F, 0.228F));

        PartDefinition leftleg = balrog.addChild("leftleg", CubeListBuilder.create().uv(60, 57).cuboid(-3.0F, -2.7642F, -2.9635F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.of(4.5F, -16.8371F, 0.6744F, -0.4899F, 0.1932F, -0.102F));

        PartDefinition leftlegseg = leftleg.addChild("leftlegseg", CubeListBuilder.create().uv(46, 74).cuboid(-2.5F, -2.7701F, -0.9363F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.of(0.0F, 6.9659F, 0.735F, 1.3126F, -0.1798F, 0.0028F));

        PartDefinition leftlegseg2 = leftlegseg.addChild("leftlegseg2", CubeListBuilder.create().uv(86, 11).cuboid(-2.0F, -3.1567F, -1.0749F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.of(0.5F, 5.0F, 0.0F, -1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r42 = leftlegseg2.addChild("cube_r42", CubeListBuilder.create().uv(8, 75).cuboid(-1.9989F, -1.5F, -1.0F, 3.998F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(-0.5F, 4.4091F, -0.8925F, -0.3054F, 0.0F, 0.0F));

        PartDefinition cube_r43 = leftlegseg2.addChild("cube_r43", CubeListBuilder.create().uv(15, 83).cuboid(-2.0F, -2.75F, -1.75F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.of(-0.5F, 4.5744F, 0.6421F, 0.1745F, 0.0F, 0.0F));

        PartDefinition toe = leftlegseg2.addChild("toe", CubeListBuilder.create(), PartPose.pivot(0.5668F, 6.2594F, -1.4137F));

        PartDefinition cube_r44 = toe.addChild("cube_r44", CubeListBuilder.create().uv(60, 37).cuboid(-1.0F, -0.5F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(1.4714F, 0.8289F, -2.1236F, 0.7091F, -0.3435F, -0.0631F));

        PartDefinition cube_r45 = toe.addChild("cube_r45", CubeListBuilder.create().uv(0, 90).cuboid(-0.5F, -1.0F, -0.75F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.of(-0.0668F, 0.1103F, -0.9278F, 0.1855F, -0.3435F, -0.0631F));

        PartDefinition toe2 = leftlegseg2.addChild("toe2", CubeListBuilder.create(), PartPose.of(-2.4332F, 6.2594F, -1.4137F, 0.0733F, 0.8569F, 0.191F));

        PartDefinition cube_r46 = toe2.addChild("cube_r46", CubeListBuilder.create().uv(53, 0).cuboid(-1.0F, -0.5F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(1.4714F, 0.8289F, -2.1236F, 0.7091F, -0.3435F, -0.0631F));

        PartDefinition cube_r47 = toe2.addChild("cube_r47", CubeListBuilder.create().uv(88, 79).cuboid(-0.5F, -1.0F, -0.75F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.of(-0.0668F, 0.1103F, -0.9278F, 0.1855F, -0.3435F, -0.0631F));

        PartDefinition toe3 = leftlegseg2.addChild("toe3", CubeListBuilder.create(), PartPose.of(-1.1481F, 6.4314F, -2.3892F, 0.0052F, 0.3439F, 0.0606F));

        PartDefinition cube_r48 = toe3.addChild("cube_r48", CubeListBuilder.create().uv(0, 28).cuboid(-1.0F, -0.5F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(1.4714F, 0.8289F, -2.1236F, 0.7091F, -0.3435F, -0.0631F));

        PartDefinition cube_r49 = toe3.addChild("cube_r49", CubeListBuilder.create().uv(40, 70).cuboid(-0.5F, -1.0F, -0.75F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.of(-0.0668F, 0.1103F, -0.9278F, 0.1855F, -0.3435F, -0.0631F));

        PartDefinition rightleg = balrog.addChild("rightleg", CubeListBuilder.create().uv(60, 57).mirrored().cuboid(-3.0F, -2.7642F, -2.9635F, 6.0F, 10.0F, 6.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.of(-4.5F, -16.8371F, 0.6744F, -0.4899F, -0.1932F, 0.102F));

        PartDefinition rightlegseg = rightleg.addChild("rightlegseg", CubeListBuilder.create().uv(46, 74).mirrored().cuboid(-2.5F, -2.7701F, -0.9363F, 5.0F, 8.0F, 5.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.of(0.0F, 6.9659F, 0.735F, 1.3126F, 0.1798F, -0.0028F));

        PartDefinition rightlegseg2 = rightlegseg.addChild("rightlegseg2", CubeListBuilder.create().uv(86, 11).cuboid(-1.0F, -3.1567F, -1.0749F, 3.0F, 6.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.of(-0.5F, 5.0F, 0.0F, -1.0036F, 0.0F, 0.0F));

        PartDefinition cube_r50 = rightlegseg2.addChild("cube_r50", CubeListBuilder.create().uv(8, 75).cuboid(-2.0F, -1.5F, -1.0F, 4.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(0.5F, 4.4091F, -0.8925F, -0.3054F, 0.0F, 0.0F));

        PartDefinition cube_r51 = rightlegseg2.addChild("cube_r51", CubeListBuilder.create().uv(15, 83).cuboid(-2.0F, -2.75F, -1.75F, 4.0F, 5.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.of(0.5F, 4.5744F, 0.6421F, 0.1745F, 0.0F, 0.0F));

        PartDefinition toe4 = rightlegseg2.addChild("toe4", CubeListBuilder.create(), PartPose.pivot(-0.5668F, 6.2594F, -1.4137F));

        PartDefinition cube_r52 = toe4.addChild("cube_r52", CubeListBuilder.create().uv(60, 37).cuboid(0.0F, -0.5F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(-1.4714F, 0.8289F, -2.1236F, 0.7091F, 0.3435F, 0.0631F));

        PartDefinition cube_r53 = toe4.addChild("cube_r53", CubeListBuilder.create().uv(0, 90).cuboid(-1.5F, -1.0F, -0.75F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.of(0.0668F, 0.1103F, -0.9278F, 0.1855F, 0.3435F, 0.0631F));

        PartDefinition toe5 = rightlegseg2.addChild("toe5", CubeListBuilder.create(), PartPose.of(2.4332F, 6.2594F, -1.4137F, 0.0733F, -0.8569F, -0.191F));

        PartDefinition cube_r54 = toe5.addChild("cube_r54", CubeListBuilder.create().uv(53, 0).cuboid(0.0F, -0.5F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(-1.4714F, 0.8289F, -2.1236F, 0.7091F, 0.3435F, 0.0631F));

        PartDefinition cube_r55 = toe5.addChild("cube_r55", CubeListBuilder.create().uv(88, 79).cuboid(-1.5F, -1.0F, -0.75F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.of(0.0668F, 0.1103F, -0.9278F, 0.1855F, 0.3435F, 0.0631F));

        PartDefinition toe6 = rightlegseg2.addChild("toe6", CubeListBuilder.create(), PartPose.of(1.1481F, 6.4314F, -2.3892F, 0.0052F, -0.3439F, -0.0606F));

        PartDefinition cube_r56 = toe6.addChild("cube_r56", CubeListBuilder.create().uv(0, 28).cuboid(0.0F, -0.5F, -0.5F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.of(-1.4714F, 0.8289F, -2.1236F, 0.7091F, 0.3435F, 0.0631F));

        PartDefinition cube_r57 = toe6.addChild("cube_r57", CubeListBuilder.create().uv(40, 70).cuboid(-1.5F, -1.0F, -0.75F, 2.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.of(0.0668F, 0.1103F, -0.9278F, 0.1855F, 0.3435F, 0.0631F));
        return LayerDefinition.of(modelData, 128, 128);
    }

    @Override
    public ModelPart getPart() {
        return balrog;
    }

    @Override
    public void setAngles(BalrogEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(headYaw, headPitch);

        this.animateMovement(BalrogAnimations.WALK, limbAngle, limbDistance, 2f, 2.5f);
        this.updateAnimation(entity.idleAnimationState, BalrogAnimations.IDLE, animationProgress, 1f);
        this.updateAnimation(entity.attackAnimationState, BalrogAnimations.ATTACK, animationProgress, 1f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = Mth.clamp(headYaw, -30.0F, 30.0F);
        headPitch = Mth.clamp(headPitch, -25.0F, 40.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

    @Override
    public void render(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        balrog.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}
