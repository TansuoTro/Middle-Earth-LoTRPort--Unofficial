package net.jukoz.me.client.model.equipment.head.helmets.humans.rohan;

import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.jukoz.me.compat.client.render.state.BipedEntityRenderState;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class RohanHairHelmetModel extends RohanHelmetModel {
    private static final float MAX_ANGLE_HAIR = 75f;

    public final ModelPart hair;

    public RohanHairHelmetModel(ModelPart root) {
        super(root);
        hair = root.getChild("head").getChild("hair_bone").getChild("hair");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = getModelData();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        head.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition hair_bone = head.addChild("hair_bone", CubeListBuilder.create(),
                PartPose.of(0.0F, -9.5F, 5.5F, 0.48F, 0.0F, 0.0F));
        hair_bone.addChild("hair", CubeListBuilder.create()
                .uv(0, 27).cuboid(-1.0F, -2.25F, 0.0F, 2.0F, 3.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, 1.0F, -1.0F, -2.0508F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 64);
    }

    @Override
    public void setAngles(BipedEntityRenderState bipedEntityRenderState) {
        /*Vec3 velocity = bipedEntityRenderState.positionOffset;
        double sqrVel = velocity.lengthSquared();
        double speed = (sqrVel * 0.35f) + Math.sqrt(Math.abs(bipedEntityRenderState.limbFrequency)) * 0.4f;
        double degree;

        degree = 5 + (MAX_ANGLE_HAIR * speed);
        degree = Math.max(2.5f, degree);
        degree = Math.min(MAX_ANGLE_HAIR, degree);

        if(bipedEntityRenderState.pitch < -0.75){
            degree -= 45;
        }else if(bipedEntityRenderState.pitch < -1.4){
            degree -= 5;
        }else {
            degree -= 90;
        }

        this.hair.pitch = ToRad.ex(degree);*/
    }
}
