package net.jukoz.me.compat.client.render;

import net.jukoz.me.compat.client.render.state.BipedEntityRenderStateCompat;
import net.minecraft.client.renderer.texture.OverlayTexture;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererFactory;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.model.HumanoidModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Mob;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

/**
 * R2 base for donor-style 1.21.8 {@code HumanoidMobRenderer<T, S, M>} renderers.
 *
 * <p>Same state pipeline as {@link MEMobEntityRenderer}; the biped pose fields are copied
 * into the state by the donor {@code updateRenderState} and read back by
 * {@link MEBipedEntityModel} while the model is posed.
 */
public abstract class MEBipedEntityRenderer<T extends Mob, S extends BipedEntityRenderStateCompat, M extends HumanoidModel<T>>
        extends HumanoidMobRenderer<T, M> {

    protected MEBipedEntityRenderer(EntityRendererProvider.Context ctx, M model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    public abstract S createRenderState();

    /** Fills the shared living/biped fields; donor subclasses call this via {@code super}. */
    public void updateRenderState(T entity, S state, float tickDelta) {
        state.limbSwingAnimationProgress = entity.limbAnimator.getPos(tickDelta);
        state.limbSwingAmplitude = entity.limbAnimator.getSpeed(tickDelta);
        state.age = entity.age + tickDelta;
        state.bodyYaw = entity.getBodyYaw();
        state.relativeHeadYaw = entity.getHeadYaw() - entity.getBodyYaw();
        state.relativeHeadPitch = entity.getPitch();
        state.hurtTime = entity.hurtTime > 0 ? entity.hurtTime - tickDelta : 0.0F;
        state.hurt = entity.hurtTime > 0;
        state.isGliding = entity.isFallFlying();
        state.hasVehicle = entity.hasVehicle();
        state.isSneaking = entity.isSneaking();
        state.isInSneakingPose = entity.isInSneakingPose();
        state.leaningPitch = entity.getLeaningPitch(tickDelta);
        state.handSwingProgress = entity.getHandSwingProgress(tickDelta);
    }

    public ResourceLocation getTexture(S state) {
        return null;
    }

    public void render(S state, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        ResourceLocation texture = this.getTexture(state);
        VertexConsumer vertices = vertexConsumers.getBuffer(this.model.getLayer(texture));
        @SuppressWarnings("unchecked")
        T entity = (T) state.entity;
        this.model.setAngles(entity, state.limbSwingAnimationProgress, state.limbSwingAmplitude,
                state.age, state.relativeHeadYaw, state.relativeHeadPitch);
        this.model.render(matrices, vertices, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        for (RenderLayer<T, M> feature : this.features) {
            feature.render(matrices, vertexConsumers, light, entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        }
    }

    @Override
    public ResourceLocation getTexture(T entity) {
        S state = createRenderState();
        state.entity = entity;
        updateRenderState(entity, state, 0.0F);
        return getTexture(state);
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, PoseStack matrices,
                             MultiBufferSource vertexConsumers, int light) {
        S state = createRenderState();
        state.entity = entity;
        state.tickDelta = tickDelta;
        updateRenderState(entity, state, tickDelta);
        MEEntityRenderContext.set(state);
        try {
            render(state, matrices, vertexConsumers, light);
        } finally {
            MEEntityRenderContext.clear();
        }
    }

    @SuppressWarnings("unchecked")
    protected T entityOf(S state) {
        return (T) state.entity;
    }

    protected void setupTransforms(S state, PoseStack matrices, float bodyYaw, float animationProgress) {
        super.setupTransforms(entityOf(state), matrices, bodyYaw, state.tickDelta, animationProgress);
    }

    protected void scale(S state, PoseStack matrices) {
        super.scale(entityOf(state), matrices, state.tickDelta);
    }

    protected int getOverlay(S state, float whiteOverlayProgress) {
        return super.getOverlay(entityOf(state), whiteOverlayProgress);
    }

    protected float getAnimationCounter(S state) {
        return super.getAnimationCounter(entityOf(state), state.tickDelta);
    }

    protected void renderLabelIfPresent(S state, net.minecraft.network.chat.Component text, PoseStack matrices,
                                        MultiBufferSource vertexConsumers, int light) {
        super.renderLabelIfPresent(entityOf(state), text, matrices, vertexConsumers, light);
    }
}
