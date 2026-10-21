package net.jukoz.me.entity.spider.spawn;

import net.minecraft.client.model.*;
import net.minecraft.client.model.HierarchicalModel;
import net.jukoz.me.entity.spider.scuttler.ShelobiteScuttlerEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class SpawnOfShelobModel extends HierarchicalModel<SpawnOfShelobEntity> {
    private final ModelPart root;

    public SpawnOfShelobModel(ModelPart root) {
        this.root = root.getChild("root");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        PartDefinition root = modelPartData.addChild("root", CubeListBuilder.create(), PartPose.pivot(0.0F, 12.0F, 0.0F));

        PartDefinition legscore = root.addChild("legscore", CubeListBuilder.create(), PartPose.pivot(0.0F, 6.0F, -5.0F));

        PartDefinition rights = legscore.addChild("rights", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition rrotation = rights.addChild("rrotation", CubeListBuilder.create(), PartPose.pivot(-3.0F, 0.0F, -4.5F));

        PartDefinition rleg = rrotation.addChild("rleg", CubeListBuilder.create().uv(150, 129).cuboid(-4.0F, -1.0F, -1.5F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(146, 135).cuboid(-6.0F, -13.0F, -1.5F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition rleg2 = rleg.addChild("rleg2", CubeListBuilder.create().uv(50, 130).cuboid(-10.0F, -2.0F, -2.0F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.pivot(-5.0F, -13.0F, 0.0F));

        PartDefinition rleg3 = rleg2.addChild("rleg3", CubeListBuilder.create().uv(147, 96).cuboid(-7.0F, -2.0F, -1.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .uv(84, 83).cuboid(-15.0F, -12.0F, -0.5F, 27.0F, 25.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.pivot(-9.0F, 1.0F, 0.0F));

        PartDefinition rrotation1 = rights.addChild("rrotation1", CubeListBuilder.create(), PartPose.pivot(-3.0F, 0.0F, -1.0F));

        PartDefinition rleg4 = rrotation1.addChild("rleg4", CubeListBuilder.create().uv(52, 99).cuboid(-5.0F, -2.0F, -2.0F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition rleg5 = rleg4.addChild("rleg5", CubeListBuilder.create().uv(144, 110).cuboid(-1.0F, -15.0F, -2.0F, 3.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.pivot(-5.0F, 0.0F, 0.0F));

        PartDefinition rleg6 = rleg5.addChild("rleg6", CubeListBuilder.create().uv(84, 133).cuboid(-3.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, -15.0F, -0.5F));

        PartDefinition rleg7 = rleg6.addChild("rleg7", CubeListBuilder.create().uv(0, 167).cuboid(-1.0F, -1.0F, -1.0F, 3.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(112, 25).cuboid(-10.0F, -13.0F, 0.0F, 20.0F, 29.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.pivot(1.0F, 11.0F, 0.0F));

        PartDefinition rrotation2 = rights.addChild("rrotation2", CubeListBuilder.create(), PartPose.pivot(-3.0F, 0.0F, 3.0F));

        PartDefinition rleg8 = rrotation2.addChild("rleg8", CubeListBuilder.create().uv(52, 99).cuboid(-5.0F, -2.0F, -2.0F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.01F)), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition rleg9 = rleg8.addChild("rleg9", CubeListBuilder.create().uv(144, 110).cuboid(-1.0F, -15.0F, -2.0F, 3.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.pivot(-5.0F, 0.0F, 0.0F));

        PartDefinition rleg10 = rleg9.addChild("rleg10", CubeListBuilder.create().uv(84, 133).cuboid(-3.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, -15.0F, -0.5F));

        PartDefinition rleg11 = rleg10.addChild("rleg11", CubeListBuilder.create().uv(0, 167).cuboid(-1.0F, -1.0F, -1.0F, 3.0F, 10.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(112, 25).cuboid(-10.0F, -13.0F, 0.0F, 20.0F, 29.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.pivot(1.0F, 11.0F, 0.0F));

        PartDefinition rrotation3 = rights.addChild("rrotation3", CubeListBuilder.create(), PartPose.pivot(-3.0F, 0.0F, 6.5F));

        PartDefinition rleg12 = rrotation3.addChild("rleg12", CubeListBuilder.create().uv(150, 129).cuboid(-4.0F, -1.0F, -1.5F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .uv(146, 135).cuboid(-6.0F, -13.0F, -1.5F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition rleg13 = rleg12.addChild("rleg13", CubeListBuilder.create().uv(50, 130).cuboid(-10.0F, -2.0F, -2.0F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.pivot(-5.0F, -13.0F, 0.0F));

        PartDefinition rleg14 = rleg13.addChild("rleg14", CubeListBuilder.create().uv(147, 96).cuboid(-7.0F, -2.0F, -1.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
                .uv(84, 83).cuboid(-15.0F, -12.0F, -0.5F, 27.0F, 25.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.pivot(-9.0F, 1.0F, 0.0F));

        PartDefinition lefts = legscore.addChild("lefts", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition lrotation = lefts.addChild("lrotation", CubeListBuilder.create(), PartPose.pivot(3.0F, 0.0F, -4.5F));

        PartDefinition lleg = lrotation.addChild("lleg", CubeListBuilder.create().uv(150, 129).mirrored().cuboid(-1.0F, -1.0F, -1.5F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(146, 135).mirrored().cuboid(4.0F, -13.0F, -1.5F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition lleg2 = lleg.addChild("lleg2", CubeListBuilder.create().uv(50, 130).mirrored().cuboid(0.0F, -2.0F, -2.0F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(5.0F, -13.0F, 0.0F));

        PartDefinition lleg3 = lleg2.addChild("lleg3", CubeListBuilder.create().uv(147, 96).mirrored().cuboid(-1.0F, -2.0F, -1.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(84, 83).mirrored().cuboid(-12.0F, -12.0F, -0.5F, 27.0F, 25.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(9.0F, 1.0F, 0.0F));

        PartDefinition lrotation2 = lefts.addChild("lrotation2", CubeListBuilder.create(), PartPose.pivot(3.0F, 0.0F, -1.0F));

        PartDefinition lleg4 = lrotation2.addChild("lleg4", CubeListBuilder.create().uv(52, 99).mirrored().cuboid(-1.0F, -2.0F, -2.0F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition lleg5 = lleg4.addChild("lleg5", CubeListBuilder.create().uv(144, 110).mirrored().cuboid(-2.0F, -15.0F, -2.0F, 3.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(5.0F, 0.0F, 0.0F));

        PartDefinition lleg6 = lleg5.addChild("lleg6", CubeListBuilder.create().uv(84, 133).mirrored().cuboid(-1.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(0.0F, -15.0F, -0.5F));

        PartDefinition lleg7 = lleg6.addChild("lleg7", CubeListBuilder.create().uv(0, 167).mirrored().cuboid(-2.0F, -1.0F, -1.0F, 3.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(112, 25).mirrored().cuboid(-10.0F, -13.0F, 0.0F, 20.0F, 29.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(-1.0F, 11.0F, 0.0F));

        PartDefinition lrotation3 = lefts.addChild("lrotation3", CubeListBuilder.create(), PartPose.pivot(3.0F, 0.0F, 3.0F));

        PartDefinition lleg8 = lrotation3.addChild("lleg8", CubeListBuilder.create().uv(52, 99).mirrored().cuboid(-1.0F, -2.0F, -2.0F, 6.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition lleg9 = lleg8.addChild("lleg9", CubeListBuilder.create().uv(144, 110).mirrored().cuboid(-2.0F, -15.0F, -2.0F, 3.0F, 15.0F, 3.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(5.0F, 0.0F, 0.0F));

        PartDefinition lleg10 = lleg9.addChild("lleg10", CubeListBuilder.create().uv(84, 133).mirrored().cuboid(-1.0F, -1.0F, -2.0F, 4.0F, 12.0F, 4.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(0.0F, -15.0F, -0.5F));

        PartDefinition lleg11 = lleg10.addChild("lleg11", CubeListBuilder.create().uv(0, 167).mirrored().cuboid(-2.0F, -1.0F, -1.0F, 3.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(112, 25).mirrored().cuboid(-10.0F, -13.0F, 0.0F, 20.0F, 29.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(-1.0F, 11.0F, 0.0F));

        PartDefinition lrotation4 = lefts.addChild("lrotation4", CubeListBuilder.create(), PartPose.pivot(3.0F, 0.0F, 6.5F));

        PartDefinition lleg12 = lrotation4.addChild("lleg12", CubeListBuilder.create().uv(150, 129).mirrored().cuboid(-1.0F, -1.0F, -1.5F, 5.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(146, 135).mirrored().cuboid(4.0F, -13.0F, -1.5F, 2.0F, 14.0F, 2.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition lleg13 = lleg12.addChild("lleg13", CubeListBuilder.create().uv(50, 130).mirrored().cuboid(0.0F, -2.0F, -2.0F, 10.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(5.0F, -13.0F, 0.0F));

        PartDefinition lleg14 = lleg13.addChild("lleg14", CubeListBuilder.create().uv(147, 96).mirrored().cuboid(-1.0F, -2.0F, -1.0F, 8.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(84, 83).mirrored().cuboid(-12.0F, -12.0F, -0.5F, 27.0F, 25.0F, 0.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(9.0F, 1.0F, 0.0F));

        PartDefinition body = root.addChild("body", CubeListBuilder.create(), PartPose.pivot(0.0F, 6.0F, -1.0F));

        PartDefinition abdomen = body.addChild("abdomen", CubeListBuilder.create().uv(6, 63).cuboid(-8.0F, -6.0F, 0.0F, 16.0F, 9.0F, 19.0F, new CubeDeformation(0.0F))
                .uv(5, 96).cuboid(-3.0F, -8.0F, -2.0F, 6.0F, 7.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, -4.0F, 1.0F));

        PartDefinition stinger = abdomen.addChild("stinger", CubeListBuilder.create().uv(156, 134).cuboid(-1.0F, 3.0F, -5.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
                .uv(0, 30).cuboid(0.0F, -2.0F, -10.0F, 0.0F, 10.0F, 15.0F, new CubeDeformation(0.0F))
                .uv(156, 142).cuboid(-1.0F, -2.0F, -2.0F, 2.0F, 5.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 3.0F, 19.0F));

        PartDefinition thorns = abdomen.addChild("thorns", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition cube_r1 = thorns.addChild("cube_r1", CubeListBuilder.create().uv(21, 36).mirrored().cuboid(-6.0F, 0.0F, -11.0F, 9.0F, 0.0F, 22.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.of(-3.0F, -8.0F, 8.0F, 0.0F, 0.0F, 0.7854F));
        PartDefinition cube_r2 = thorns.addChild("cube_r2", CubeListBuilder.create().uv(21, 36).cuboid(-3.0F, 0.0F, -11.0F, 9.0F, 0.0F, 22.0F, new CubeDeformation(0.0F)), PartPose.of(3.0F, -8.0F, 8.0F, 0.0F, 0.0F, -0.7854F));
        PartDefinition cube_r3 = thorns.addChild("cube_r3", CubeListBuilder.create().uv(-16, 217).mirrored().cuboid(-14.0F, 0.0F, -19.5F, 23.0F, 0.0F, 39.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.of(-8.0F, -6.0F, 16.5F, 0.0F, 0.0F, 0.2618F));
        PartDefinition cube_r4 = thorns.addChild("cube_r4", CubeListBuilder.create().uv(-16, 217).cuboid(-9.0F, 0.0F, -19.5F, 23.0F, 0.0F, 39.0F, new CubeDeformation(0.0F)), PartPose.of(8.0F, -6.0F, 16.5F, 0.0F, 0.0F, -0.2618F));

        PartDefinition prosoma = body.addChild("prosoma", CubeListBuilder.create().uv(88, 62).cuboid(-4.0F, -2.0F, -15.0F, 8.0F, 6.0F, 15.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, -4.0F, 3.0F));

        PartDefinition chelicerae = prosoma.addChild("chelicerae", CubeListBuilder.create(), PartPose.pivot(0.0F, 1.0F, -13.0F));

        PartDefinition leftchel = chelicerae.addChild("leftchel", CubeListBuilder.create().uv(140, 80).mirrored().cuboid(-2.0F, -3.0F, -8.0F, 3.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(2.0F, -1.0F, 1.0F));

        PartDefinition leftooth = leftchel.addChild("leftooth", CubeListBuilder.create().uv(9, 130).cuboid(-1.0F, 0.0F, -9.0F, 2.0F, 4.0F, 13.0F, new CubeDeformation(0.0F))
                .uv(0, 154).mirrored().cuboid(-1.0F, -3.0F, -9.0F, 2.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(-0.5F, -1.0F, -7.0F));

        PartDefinition leftjaw = leftchel.addChild("leftjaw", CubeListBuilder.create().uv(0, 132).mirrored().cuboid(-1.0F, -1.0F, -7.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(26, 132).mirrored().cuboid(-1.0F, 1.0F, -7.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(-0.5F, 1.0F, -8.0F));

        PartDefinition rightchel = chelicerae.addChild("rightchel", CubeListBuilder.create().uv(140, 80).cuboid(-1.0F, -3.0F, -8.0F, 3.0F, 6.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.pivot(-2.0F, -1.0F, 1.0F));

        PartDefinition rightooth = rightchel.addChild("rightooth", CubeListBuilder.create().uv(9, 130).mirrored().cuboid(-1.0F, 0.0F, -9.0F, 2.0F, 4.0F, 13.0F, new CubeDeformation(0.0F)).mirrored(false)
                .uv(0, 154).cuboid(-1.0F, -3.0F, -9.0F, 2.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.5F, -1.0F, -7.0F));

        PartDefinition rightjaw = rightchel.addChild("rightjaw", CubeListBuilder.create().uv(0, 132).cuboid(-1.0F, -1.0F, -7.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F))
                .uv(26, 132).cuboid(-1.0F, 1.0F, -7.0F, 2.0F, 2.0F, 9.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.5F, 1.0F, -8.0F));
        return LayerDefinition.of(modelData, 256, 256);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(SpawnOfShelobEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);

        int croppedClimbingTicks = Math.min(ShelobiteScuttlerEntity.CLIMBING_TIME_TRANSITION, entity.getClimbingTicks());
        float climbingPercentage = (float) croppedClimbingTicks / SpawnOfShelobEntity.CLIMBING_TIME_TRANSITION;

        int croppedLeapingTicks = Math.min(ShelobiteScuttlerEntity.LEAPING_TIME_TRANSITION, entity.getLeapingTicks());
        float leapingPercentage = (float) croppedLeapingTicks / SpawnOfShelobEntity.LEAPING_TIME_TRANSITION;

        if(entity.getClimbingTicks() > 0 && climbingPercentage > leapingPercentage) {
            this.root.pitch = -1.5f * climbingPercentage;
            this.animateMovement(SpawnOfShelobAnimations.SPAWN_OF_SHELOB_WALK, ageInTicks, 0.4f, 1.75F, 2F);
            return;
        } else if(entity.getLeapingTicks() > 0 && leapingPercentage > climbingPercentage) {
            this.root.pitch = -0.8f * leapingPercentage;
            this.animateMovement(SpawnOfShelobAnimations.SPAWN_OF_SHELOB_WALK, (float)entity.getLeapingTicks() / 3.1f, 0.75f, 2.2F, 2.5F);
            return;
        }

        if(limbSwingAmount <= 0.4) {
            this.updateAnimation(entity.idleAnimation, SpawnOfShelobAnimations.SPAWN_OF_SHELOB_IDLE, ageInTicks, 0.75f);
        } else if(entity.blockAnimation.isRunning()) {
            this.animateMovement(SpawnOfShelobAnimations.SPAWN_OF_SHELOB_BLOCK, limbSwing, limbSwingAmount, 2.2F, 2.25F);
        } else {
            this.animateMovement(SpawnOfShelobAnimations.SPAWN_OF_SHELOB_WALK, limbSwing, limbSwingAmount, 2.25F, 2.5F);
        }

        if(entity.biteAnimation.isRunning()) {
            this.updateAnimation(entity.biteAnimation, SpawnOfShelobAnimations.SPAWN_OF_SHELOB_BITE, ageInTicks, 1.3f);
        }
        if(entity.pounceAnimation.isRunning()) {
            this.updateAnimation(entity.pounceAnimation, SpawnOfShelobAnimations.SPAWN_OF_SHELOB_POUNCE, ageInTicks, 1.0f);
        }
    }
}
