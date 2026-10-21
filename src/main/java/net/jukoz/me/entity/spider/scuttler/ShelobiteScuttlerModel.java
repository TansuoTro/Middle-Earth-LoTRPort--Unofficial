package net.jukoz.me.entity.spider.scuttler;

import net.minecraft.client.model.*;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class ShelobiteScuttlerModel extends HierarchicalModel<ShelobiteScuttlerEntity> {
    private static final int CLIMBING_ROTATION_MAX = 90;
    private final ModelPart root;
    private final ModelPart legscore;
    private final ModelPart body;

    public ShelobiteScuttlerModel(ModelPart root) {
        this.root = root.getChild("root");
        this.legscore = this.root.getChild("legscore");
        this.body = this.root.getChild("body");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addChild("root", CubeListBuilder.create(), PartPose.pivot(0.0F, 18.0F, 0.0F));

        PartDefinition legscore = root.addChild("legscore", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, -5.0F));

        PartDefinition rights = legscore.addChild("rights", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition rrotation = rights.addChild("rrotation", CubeListBuilder.create(), PartPose.pivot(-2.0F, 1.0F, -3.0F));

        PartDefinition rleg = rrotation.addChild("rleg", CubeListBuilder.create().uv(146, 142).cuboid(-2.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition rleg2 = rleg.addChild("rleg2", CubeListBuilder.create().uv(56, 131).cuboid(-4.0F, -2.0F, -1.5F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.pivot(-1.0F, -3.0F, 0.0F));

        PartDefinition rleg3 = rleg2.addChild("rleg3", CubeListBuilder.create().uv(158, 96).cuboid(-2.0F, -2.0F, -0.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .uv(88, 83).cuboid(-11.0F, -6.0F, 0.0F, 15.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.pivot(-3.0F, 1.0F, 0.0F));

        PartDefinition rrotation2 = rights.addChild("rrotation2", CubeListBuilder.create(), PartPose.pivot(-2.0F, 1.0F, 0.0F));

        PartDefinition rleg8 = rrotation2.addChild("rleg8", CubeListBuilder.create().uv(146, 135).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.pivot(-1.0F, -1.0F, 0.0F));

        PartDefinition rleg9 = rleg8.addChild("rleg9", CubeListBuilder.create().uv(53, 114).cuboid(-6.0F, -1.0F, -1.5F, 6.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, -4.0F, 0.0F));

        PartDefinition rleg10 = rleg9.addChild("rleg10", CubeListBuilder.create().uv(152, 96).cuboid(-2.0F, -1.0F, -0.5F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .uv(4, 0).cuboid(-11.0F, -3.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.pivot(-5.0F, 1.0F, 0.0F));

        PartDefinition rrotation3 = rights.addChild("rrotation3", CubeListBuilder.create(), PartPose.pivot(-2.0F, 1.0F, 3.0F));

        PartDefinition rleg11 = rrotation3.addChild("rleg11", CubeListBuilder.create().uv(146, 135).cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.pivot(-1.0F, -1.0F, 0.0F));

        PartDefinition rleg12 = rleg11.addChild("rleg12", CubeListBuilder.create().uv(53, 114).cuboid(-6.0F, -1.0F, -1.5F, 6.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, -4.0F, 0.0F));

        PartDefinition rleg13 = rleg12.addChild("rleg13", CubeListBuilder.create().uv(152, 96).cuboid(-2.0F, -1.0F, -0.5F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                .uv(4, 0).cuboid(-11.0F, -3.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.pivot(-5.0F, 1.0F, 0.0F));

        PartDefinition rrotation4 = rights.addChild("rrotation4", CubeListBuilder.create(), PartPose.pivot(-2.0F, 1.0F, 6.0F));

        PartDefinition rleg4 = rrotation4.addChild("rleg4", CubeListBuilder.create().uv(146, 142).cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.pivot(-1.0F, -1.0F, 0.0F));

        PartDefinition rleg5 = rleg4.addChild("rleg5", CubeListBuilder.create().uv(56, 131).cuboid(-4.0F, -2.0F, -1.5F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, -2.0F, 0.0F));

        PartDefinition rleg6 = rleg5.addChild("rleg6", CubeListBuilder.create().uv(158, 96).cuboid(-2.0F, -2.0F, -0.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
                .uv(88, 83).cuboid(-11.0F, -6.0F, 0.0F, 15.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.pivot(-3.0F, 1.0F, 0.0F));

        PartDefinition lefts = legscore.addChild("lefts", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition rrotation5 = lefts.addChild("rrotation5", CubeListBuilder.create(), PartPose.pivot(2.0F, 1.0F, -3.0F));

        PartDefinition rleg7 = rrotation5.addChild("rleg7", CubeListBuilder.create().uv(146, 142).mirrored().cuboid(0.0F, -4.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition rleg14 = rleg7.addChild("rleg14", CubeListBuilder.create().uv(56, 131).mirrored().cuboid(0.0F, -2.0F, -1.5F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(1.0F, -3.0F, 0.0F));

        PartDefinition rleg15 = rleg14.addChild("rleg15", CubeListBuilder.create().uv(158, 96).mirrored().cuboid(0.0F, -2.0F, -0.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(88, 83).mirrored().cuboid(-4.0F, -6.0F, 0.0F, 15.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(3.0F, 1.0F, 0.0F));

        PartDefinition rrotation6 = lefts.addChild("rrotation6", CubeListBuilder.create(), PartPose.pivot(2.0F, 1.0F, 0.0F));

        PartDefinition rleg16 = rrotation6.addChild("rleg16", CubeListBuilder.create().uv(146, 135).mirrored().cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(1.0F, -1.0F, 0.0F));

        PartDefinition rleg17 = rleg16.addChild("rleg17", CubeListBuilder.create().uv(53, 114).mirrored().cuboid(0.0F, -1.0F, -1.5F, 6.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(0.0F, -4.0F, 0.0F));

        PartDefinition rleg18 = rleg17.addChild("rleg18", CubeListBuilder.create().uv(152, 96).mirrored().cuboid(0.0F, -1.0F, -0.5F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(4, 0).mirrored().cuboid(-5.0F, -3.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(5.0F, 1.0F, 0.0F));

        PartDefinition rrotation7 = lefts.addChild("rrotation7", CubeListBuilder.create(), PartPose.pivot(2.0F, 1.0F, 3.0F));

        PartDefinition rleg19 = rrotation7.addChild("rleg19", CubeListBuilder.create().uv(146, 135).mirrored().cuboid(-1.0F, -4.0F, -1.0F, 2.0F, 5.0F, 2.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(1.0F, -1.0F, 0.0F));

        PartDefinition rleg20 = rleg19.addChild("rleg20", CubeListBuilder.create().uv(53, 114).mirrored().cuboid(0.0F, -1.0F, -1.5F, 6.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(0.0F, -4.0F, 0.0F));

        PartDefinition rleg21 = rleg20.addChild("rleg21", CubeListBuilder.create().uv(152, 96).mirrored().cuboid(0.0F, -1.0F, -0.5F, 2.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(4, 0).mirrored().cuboid(-5.0F, -3.0F, 0.0F, 16.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(5.0F, 1.0F, 0.0F));

        PartDefinition rrotation8 = lefts.addChild("rrotation8", CubeListBuilder.create(), PartPose.pivot(2.0F, 1.0F, 6.0F));

        PartDefinition rleg22 = rrotation8.addChild("rleg22", CubeListBuilder.create().uv(146, 142).mirrored().cuboid(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(1.0F, -1.0F, 0.0F));

        PartDefinition rleg23 = rleg22.addChild("rleg23", CubeListBuilder.create().uv(56, 131).mirrored().cuboid(0.0F, -2.0F, -1.5F, 4.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(0.0F, -2.0F, 0.0F));

        PartDefinition rleg24 = rleg23.addChild("rleg24", CubeListBuilder.create().uv(158, 96).mirrored().cuboid(0.0F, -2.0F, -0.5F, 2.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(88, 83).mirrored().cuboid(-4.0F, -6.0F, 0.0F, 15.0F, 13.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(3.0F, 1.0F, 0.0F));

        PartDefinition body = root.addChild("body", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, -4.0F));

        PartDefinition abdomen = body.addChild("abdomen", CubeListBuilder.create().uv(19, 72).cuboid(-4.0F, -2.0F, 0.0F, 8.0F, 5.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, -3.0F, 2.0F));

        PartDefinition stinger = abdomen.addChild("stinger", CubeListBuilder.create().uv(156, 134).cuboid(-1.0F, 3.0F, -5.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .uv(0, 30).cuboid(0.0F, -2.0F, -10.0F, 0.0F, 10.0F, 15.0F, new CubeDeformation(0.0F))
                .uv(156, 142).cuboid(-1.0F, -2.0F, -2.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 3.0F, 11.0F));

        PartDefinition prosoma = body.addChild("prosoma", CubeListBuilder.create().uv(96, 68).cuboid(-2.0F, -1.0F, -7.0F, 4.0F, 4.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, -2.0F, 3.0F));

        PartDefinition chelicerae = prosoma.addChild("chelicerae", CubeListBuilder.create(), PartPose.pivot(0.0F, 2.0F, -7.0F));

        PartDefinition leftchel = chelicerae.addChild("leftchel", CubeListBuilder.create().uv(0, 22).mirrored().cuboid(-0.5F, -0.5F, -3.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(0.5F, -0.5F, 0.5F));

        PartDefinition fang = leftchel.addChild("fang", CubeListBuilder.create().uv(0, 32).mirrored().cuboid(0.0F, -3.0F, -6.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(-0.5F, 1.5F, -3.5F));

        PartDefinition rightchel = chelicerae.addChild("rightchel", CubeListBuilder.create().uv(0, 22).cuboid(-0.5F, -0.5F, -3.5F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.pivot(-0.5F, -0.5F, 0.5F));

        PartDefinition fang2 = rightchel.addChild("fang2", CubeListBuilder.create().uv(0, 32).cuboid(-3.0F, -3.0F, -6.0F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.5F, 1.5F, -3.5F));
        return LayerDefinition.of(modelData, 256, 256);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(ShelobiteScuttlerEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        int croppedClimbingTicks = Math.min(ShelobiteScuttlerEntity.CLIMBING_TIME_TRANSITION, entity.getClimbingTicks());
        float climbingPercentage = (float) croppedClimbingTicks / ShelobiteScuttlerEntity.CLIMBING_TIME_TRANSITION;

        int croppedLeapingTicks = Math.min(ShelobiteScuttlerEntity.LEAPING_TIME_TRANSITION, entity.getLeapingTicks());
        float leapingPercentage = (float) croppedLeapingTicks / ShelobiteScuttlerEntity.LEAPING_TIME_TRANSITION;

        if(entity.getClimbingTicks() > 0 && climbingPercentage > leapingPercentage) {
            this.root.pitch = -1.5f * climbingPercentage;
            this.animateMovement(ShelobiteScuttlerAnimations.SHELOBITE_SCUTTLER_WALK, ageInTicks, 0.4f, 1.75F, 2F);
            return;
        } else if(entity.getLeapingTicks() > 0 && leapingPercentage > climbingPercentage) {
            this.root.pitch = -0.7f * leapingPercentage;
            this.animateMovement(ShelobiteScuttlerAnimations.SHELOBITE_SCUTTLER_WALK, (float)entity.getLeapingTicks() / 3.7f, 0.7f, 2.2F, 2.5F);
            return;
        }

        if(limbSwingAmount <= 0.4) {
            this.updateAnimation(entity.idleAnimation, ShelobiteScuttlerAnimations.SHELOBITE_SCUTTLER_IDLE, ageInTicks, 0.75f);
        } else {
            this.animateMovement(ShelobiteScuttlerAnimations.SHELOBITE_SCUTTLER_WALK, limbSwing, limbSwingAmount, 2.25F, 2.5F);
        }

        if(entity.biteAnimation.isRunning()) {
            this.updateAnimation(entity.biteAnimation, ShelobiteScuttlerAnimations.SHELOBITE_SCUTTLER_BITE, ageInTicks, 1.5f);
        }
        if(entity.pounceAnimation.isRunning()) {
            this.updateAnimation(entity.pounceAnimation, ShelobiteScuttlerAnimations.SHELOBITE_SCUTTLER_POUNCE, ageInTicks, 1.1f);
        }
    }
}
