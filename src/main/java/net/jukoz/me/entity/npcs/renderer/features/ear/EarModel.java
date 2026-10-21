package net.jukoz.me.entity.npcs.renderer.features.ear;

import net.minecraft.client.model.*;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.HierarchicalModel;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.minecraft.util.Mth;
import net.jukoz.me.entity.npcs.renderer.NpcEntityRenderState;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class EarModel extends HierarchicalModel<NpcEntity> {
    private final ModelPart root;
    public final ModelPart ears;
    public final ModelPart planeFlatLeft;
    public final ModelPart planeFlatRight;

    public EarModel(ModelPart modelPart) {
        this.root = modelPart;

        this.ears = modelPart.getChild("ears");

        this.planeFlatLeft = this.ears.getChild("ear_left");
        this.planeFlatRight = this.ears.getChild("ear_right");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition root = modelData.getRoot();

        PartDefinition ears = root.addChild("ears", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, 0.0F));

        ears.addChild("ear_right",
                CubeListBuilder.create().uv(0, 6).cuboid(3.5F, -8.0F, 2F, 7.0F, 6.0F, 0.0F, CubeDeformation.NONE),
                PartPose.NONE);

        ears.addChild("ear_left",
                CubeListBuilder.create().uv(0, 6).cuboid(3.5F, -8.0F, -2F, 7.0F, 6.0F, 0.0F, CubeDeformation.NONE),
                PartPose.NONE);

        return LayerDefinition.of(modelData, 16, 16);
    }

    public void setAngles(NpcEntityRenderState state) {

        // Taken from HumanoidModel.class
        float f = state.leaningPitch;
        boolean bl = state.isGliding;
        this.ears.pitch = state.pitch * 0.017453292F;
        this.ears.yaw = state.relativeHeadYaw * 0.017453292F;

        if (bl) {
            this.ears.pitch = -0.7853982F;
        } else if (f > 0.0F) {
            this.ears.pitch = net.jukoz.me.compat.MEAngles.lerpAngleRadians(f, this.ears.pitch, -0.7853982F);
        }

        this.planeFlatLeft.yaw = (float)Math.toRadians(-20);
        this.planeFlatRight.yaw = (float)Math.toRadians(-160);
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(NpcEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
}
