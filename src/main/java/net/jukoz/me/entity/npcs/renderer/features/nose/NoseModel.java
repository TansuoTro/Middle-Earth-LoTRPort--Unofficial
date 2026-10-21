package net.jukoz.me.entity.npcs.renderer.features.nose;

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

public class NoseModel extends HierarchicalModel<NpcEntity> {
    private final ModelPart root;
    public final ModelPart noseRoot;

    public final ModelPart squareCube;
    public final ModelPart villagerCube;
    public final ModelPart largeHighCube;
    public final ModelPart largeCenterCube;

    public NoseModel(ModelPart modelPart) {
        this.root = modelPart;
        this.noseRoot = modelPart.getChild("noseRoot");
        this.squareCube = noseRoot.getChild("squareCube");
        this.villagerCube = noseRoot.getChild("villagerCube");

        this.largeHighCube = noseRoot.getChild("largeHighCube");
        this.largeCenterCube = noseRoot.getChild("largeCenterCube");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition cubeRootPart = modelPartData.addOrReplaceChild("noseRoot", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        PartDefinition cubeNosePart = cubeRootPart.addOrReplaceChild("squareCube", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        cubeNosePart.addOrReplaceChild("cube", CubeListBuilder.create().texOffs(0, 2).addBox(-1.0f, -4f, -4.5f, 2, 2, 1, CubeDeformation.NONE), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition villagerCubePart = cubeRootPart.addOrReplaceChild("villagerCube", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        villagerCubePart.addOrReplaceChild("cube", CubeListBuilder.create().texOffs(0, 6).addBox(-1.0f, -4f, -6f, 2, 4, 2, CubeDeformation.NONE), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition largeHighCubePart = cubeRootPart.addOrReplaceChild("largeHighCube", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        largeHighCubePart.addOrReplaceChild("cube", CubeListBuilder.create().texOffs(11, 0).addBox(-2.0f, -6f, -5f, 4, 3, 1, CubeDeformation.NONE), PartPose.offset(0.0F, 0.0F, 0.0F));

        PartDefinition largeCenterCubePart = cubeRootPart.addOrReplaceChild("largeCenterCube", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        largeCenterCubePart.addOrReplaceChild("cube", CubeListBuilder.create().texOffs(11, 5).addBox(-2.0f, -5f, -5f, 4, 3, 1, CubeDeformation.NONE), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.of(modelData, 32, 32);
    }

    public void setAngles(NpcEntityRenderState state) {

        float f = state.leaningPitch;
        boolean bl = state.isGliding;

        this.noseRoot.xRot = state.xRot * 0.017453292F;
        this.noseRoot.yRot = state.relativeHeadYaw * 0.017453292F;

        if (bl) {
            this.noseRoot.xRot = -0.7853982F;

        } else if (f > 0.0F) {
            this.noseRoot.xRot = net.jukoz.me.compat.MEAngles.lerpAngleRadians(f, this.noseRoot.xRot, -0.7853982F);
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
