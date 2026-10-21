package net.jukoz.me.client.model.equipment.head;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.ToRad;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class RohirricHelmetArmorModel<T extends LivingEntity> extends CustomHelmetModel<T> {
    private static final float MAX_ANGLE_HAIR = 75f;

    public final ModelPart hair;

    public RohirricHelmetArmorModel(ModelPart root, String name) {
        super(root);
        hair = root.getChild("head").getChild("hair");

        HELMET_ADDON_TEXTURE = new ResourceLocation(MiddleEarth.MOD_ID, "textures/models/armor/" + name + "_helmet_addon.png");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        modelPartData.addChild(PartNames.HAT, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        PartDefinition head = modelPartData.addChild(PartNames.HEAD, CubeListBuilder.create().uv(0, -1)
                .cuboid(0.0F, -16.0F, -7.0F, 0.0F, 11.0F, 17.0F, new CubeDeformation(0.0F)),
                PartPose.pivot(0.0F, 0.0F, 0.0F));

        head.addChild("hair", CubeListBuilder.create().uv(0, 27)
                .cuboid(-1.0F, -2.0F, 0.0F, 2.0F, 3.0F, 18.0F, new CubeDeformation(0.0F)),
                PartPose.of(0.0F, -9.0F, 5.0F, -1.5708F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.BODY, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.RIGHT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_ARM, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        modelPartData.addChild(PartNames.RIGHT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));
        modelPartData.addChild(PartNames.LEFT_LEG, CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 64, 48);
    }

    @Override
    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        Vec3 velocity = entity.getVelocity();
        double sqrVel = velocity.lengthSquared();
        double speed = (sqrVel * 0.35f) + Math.sqrt(Math.abs(limbDistance)) * 0.4f;
        double degree;

        degree = 5 + (MAX_ANGLE_HAIR * speed);
        degree = Math.max(2.5f, degree);
        degree = Math.min(MAX_ANGLE_HAIR, degree);

        if(headPitch < -0.75){
            degree -= 45;
        }else if(headPitch < -1.4){
            degree -= 5;
        }else {
            degree -= 90;
        }

        this.hair.pitch = ToRad.ex(degree);
    }
}
