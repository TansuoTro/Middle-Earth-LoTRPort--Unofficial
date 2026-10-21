package net.jukoz.me.entity.npcs.renderer.features.feet;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.model.*;
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

@OnlyIn(Dist.CLIENT)
public class FeetModel extends HierarchicalModel<NpcEntity> {
    private final ModelPart root;
    public final ModelPart toeCubeLeft;
    public final ModelPart toeCubeRight;

    public final ModelPart rightLeg;
    public final ModelPart leftLeg;

    public FeetModel(ModelPart modelPart) {
        this.root = modelPart;

        this.leftLeg = modelPart.getChild("left_leg");
        this.rightLeg = modelPart.getChild("right_leg");

        this.toeCubeLeft = leftLeg.getChild("feet");
        this.toeCubeRight = rightLeg.getChild("feet");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        var rightLeg = modelPartData.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0,0).addBox(-2.0F, 0.0F, -2.0F, 0.0F, 12.0F, 0.0F, CubeDeformation.NONE), PartPose.offset(-1.9F, 12.0F, 0.0F));
        var leftLeg = modelPartData.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0,0).addBox(-2.0F, 0.0F, -2.0F, 0.0F, 0.0F, 0.0F,  CubeDeformation.NONE), PartPose.offset(1.9F, 12.0F, 0.0F));

        rightLeg.addOrReplaceChild("feet",
                                CubeListBuilder.create().texOffs(0, 4).mirror().addBox(0,0,0,4,2,2, CubeDeformation.NONE),
                                PartPose.offset(-2f,10f,-4f));

        leftLeg.addOrReplaceChild("feet",
                                CubeListBuilder.create().texOffs(0, 4).addBox(0,0,0,4,2,2, CubeDeformation.NONE),
                                PartPose.offset(-2f,10f,-4f));

        return LayerDefinition.of(modelData, 16, 16);
    }

    public void setAngles(NpcEntityRenderState state) {

        float g = state.limbSwingAnimationProgress;
        float h = state.limbSwingAmplitude;

        this.rightLeg.xRot = Mth.cos(g * 0.6662F) * 1.4F * h / state.limbAmplitudeInverse;
        this.leftLeg.xRot = Mth.cos(g * 0.6662F + (float)Math.PI) * 1.4F * h / state.limbAmplitudeInverse;
        this.rightLeg.yRot = 0.005F;
        this.leftLeg.yRot = -0.005F;
        this.rightLeg.roll = 0.005F;
        this.leftLeg.roll = -0.005F;
        if (state.hasVehicle) {
            this.rightLeg.xRot = -1.4137167F;
            this.rightLeg.yRot = ((float)Math.PI / 10F);
            this.rightLeg.roll = 0.07853982F;
            this.leftLeg.xRot = -1.4137167F;
            this.leftLeg.yRot = (-(float)Math.PI / 10F);
            this.leftLeg.roll = -0.07853982F;
        }
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    @Override
    public void setAngles(NpcEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }
}
