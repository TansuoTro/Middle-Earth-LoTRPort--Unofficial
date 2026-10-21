package net.jukoz.me.entity.npcs.renderer.features.hair;

import net.minecraft.client.model.*;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.minecraft.util.Mth;
import net.jukoz.me.entity.npcs.renderer.NpcEntityRenderState;

public class HairModel extends SinglePartEntityModel<NpcEntity> {
    private final ModelPart root;
    // https://i.pinimg.com/736x/9c/56/05/9c560508ceba0bc87b9d5beda7391adc.jpg
    public static final String BEARD = "beard";
    public final ModelPart hair;
    public final ModelPart hairBase;
    public final ModelPart hairHat;
    public final ModelPart largeBeard;

    public HairModel(ModelPart modelPart) {
        this.root = modelPart;

        this.hair = modelPart.getChild("hair");
        this.hairBase = hair.getChild("hairBase");
        this.hairHat = hair.getChild("hairHat");
        this.largeBeard = hair.getChild("largeBeard");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData hairGroup = modelPartData.addChild("hair", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        hairGroup.addChild("hairBase", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0f, 0f, -4.0f, 8, 11, 8, Dilation.NONE), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        hairGroup.addChild("hairHat", ModelPartBuilder.create().uv(32, 0).cuboid(-4.0f, 1f, -4f, 8, 11, 8, Dilation.NONE.add(0.5f)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));
        hairGroup.addChild("largeBeard", ModelPartBuilder.create().uv(37, 37).cuboid(-6.5F, -3.5F, -4.1F,14.0F, 25.0F, -0.5F, Dilation.NONE.add(0.25f)), ModelTransform.NONE);
        return TexturedModelData.of(modelData, 64, 64);
    }

    public void setAngles(NpcEntityRenderState state) {

        // Taken from BipedEntityModel.class
        float f = state.leaningPitch;
        boolean bl = state.isGliding;
        this.hair.pitch = state.pitch * 0.017453292F;
        this.hair.yaw = state.relativeHeadYaw * 0.017453292F;

        if (bl) {
            this.hair.pitch = -0.7853982F;
        } else if (f > 0.0F) {
            this.hair.pitch = net.jukoz.me.compat.MEAngles.lerpAngleRadians(f, this.hair.pitch, -0.7853982F);
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
