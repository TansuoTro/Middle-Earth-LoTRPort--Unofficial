package net.jukoz.me.entity.nazguls;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.model.ModEntityModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.ZombieModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class NazgulRenderer extends HumanoidMobRenderer<NazgulEntity, NazgulModel<NazgulEntity>> {
    private static final String PATH = "textures/entities/nazgul/nazgul.png";

    public NazgulRenderer(EntityRendererProvider.Context ctx) {
        super(ctx, new NazgulModel<>(ctx.bakeLayer(ModEntityModelLayers.NAZGUL)), 0.5f);
        this.addFeature(new HumanoidArmorLayer<>(this, new ZombieModel(ctx.bakeLayer(ModelLayers.ZOMBIE_INNER_ARMOR)), new ZombieModel(ctx.bakeLayer(ModelLayers.ZOMBIE_OUTER_ARMOR)), ctx.getModelManager()));
    }

    @Override
    public ResourceLocation getTexture(NazgulEntity entity) {
        return new ResourceLocation(MiddleEarth.MOD_ID, PATH);
    }

    @Override
    public void render(NazgulEntity entity, float entityYaw, float partialTick, PoseStack poseStack,
                       MultiBufferSource bufferSource, int packedLight) {

        if(entity.isFading()) {
            int fadingTicks = entity.getFadingTicks();
            float percentage = (float) fadingTicks / NazgulEntity.FADING_TIME;
            //poseStack.translate(Math.cos(fadingTicks * 0.3f) * percentage, Math.sin(fadingTicks * 0.35f) * percentage, Math.cos(fadingTicks * 0.4f) * percentage);
            entityYaw += (float)(Math.cos((double)(entity).age * 10 * percentage)) * Math.PI * ((double)1.35f + percentage);
            float entityPitch = (float)(Math.cos((double)(entity).age * (5 + percentage)) * Math.PI * (double)0.3f);
            poseStack.multiply(Axis.POSITIVE_Y.rotationDegrees(180.0f - entityYaw));
            //poseStack.multiply(Axis.POSITIVE_X.rotationDegrees(60.0f + entityPitch));
        }

        float scale = 1 - (float)entity.getFadingTicks() / NazgulEntity.FADING_TIME;
        scale = (float) Math.pow(scale, 0.35f);
        poseStack.scale(1.05f * scale, 1.05f * scale, 1.05f * scale);
        if(entity.isPassenger()) {
            poseStack.translate(0, -0.4f, 0);
        }

        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }
}
