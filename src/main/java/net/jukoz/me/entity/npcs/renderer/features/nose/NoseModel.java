package net.jukoz.me.entity.npcs.renderer.features.nose;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.minecraft.util.math.MathHelper;
import net.jukoz.me.entity.npcs.renderer.NpcEntityRenderState;

public class NoseModel extends SinglePartEntityModel<NpcEntity> {
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

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData cubeRootPart = modelPartData.addChild("noseRoot", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        ModelPartData cubeNosePart = cubeRootPart.addChild("squareCube", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        cubeNosePart.addChild("cube", ModelPartBuilder.create().uv(0, 2).cuboid(-1.0f, -4f, -4.5f, 2, 2, 1, Dilation.NONE), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData villagerCubePart = cubeRootPart.addChild("villagerCube", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        villagerCubePart.addChild("cube", ModelPartBuilder.create().uv(0, 6).cuboid(-1.0f, -4f, -6f, 2, 4, 2, Dilation.NONE), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData largeHighCubePart = cubeRootPart.addChild("largeHighCube", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        largeHighCubePart.addChild("cube", ModelPartBuilder.create().uv(11, 0).cuboid(-2.0f, -6f, -5f, 4, 3, 1, Dilation.NONE), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        ModelPartData largeCenterCubePart = cubeRootPart.addChild("largeCenterCube", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        largeCenterCubePart.addChild("cube", ModelPartBuilder.create().uv(11, 5).cuboid(-2.0f, -5f, -5f, 4, 3, 1, Dilation.NONE), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 32, 32);
    }

    public void setAngles(NpcEntityRenderState state) {

        float f = state.leaningPitch;
        boolean bl = state.isGliding;

        this.noseRoot.pitch = state.pitch * 0.017453292F;
        this.noseRoot.yaw = state.relativeHeadYaw * 0.017453292F;

        if (bl) {
            this.noseRoot.pitch = -0.7853982F;

        } else if (f > 0.0F) {
            this.noseRoot.pitch = net.jukoz.me.compat.MEAngles.lerpAngleRadians(f, this.noseRoot.pitch, -0.7853982F);
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
