package net.jukoz.me.compat.client.render;

import net.jukoz.me.compat.client.render.state.BipedEntityRenderStateCompat;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Identifier;

/**
 * R2 base for donor-style 1.21.8 {@code BipedEntityRenderer<T, S, M>} renderers.
 *
 * <p>Same state pipeline as {@link MEMobEntityRenderer}; the biped pose fields are copied
 * into the state by the donor {@code updateRenderState} and read back by
 * {@link MEBipedEntityModel} while the model is posed.
 */
public abstract class MEBipedEntityRenderer<T extends MobEntity, S extends BipedEntityRenderStateCompat, M extends BipedEntityModel<T>>
        extends BipedEntityRenderer<T, M> {

    protected MEBipedEntityRenderer(EntityRendererFactory.Context ctx, M model, float shadowRadius) {
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

    public Identifier getTexture(S state) {
        return null;
    }

    public void render(S state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        Identifier texture = this.getTexture(state);
        VertexConsumer vertices = vertexConsumers.getBuffer(this.model.getLayer(texture));
        @SuppressWarnings("unchecked")
        T entity = (T) state.entity;
        this.model.setAngles(entity, state.limbSwingAnimationProgress, state.limbSwingAmplitude,
                state.age, state.relativeHeadYaw, state.relativeHeadPitch);
        this.model.render(matrices, vertices, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        for (FeatureRenderer<T, M> feature : this.features) {
            feature.render(matrices, vertexConsumers, light, entity, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
        }
    }

    @Override
    public Identifier getTexture(T entity) {
        S state = createRenderState();
        state.entity = entity;
        updateRenderState(entity, state, 0.0F);
        return getTexture(state);
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrices,
                             VertexConsumerProvider vertexConsumers, int light) {
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

    protected void setupTransforms(S state, MatrixStack matrices, float bodyYaw, float animationProgress) {
        super.setupTransforms(entityOf(state), matrices, bodyYaw, state.tickDelta, animationProgress);
    }

    protected void scale(S state, MatrixStack matrices) {
        super.scale(entityOf(state), matrices, state.tickDelta);
    }

    protected int getOverlay(S state, float whiteOverlayProgress) {
        return super.getOverlay(entityOf(state), whiteOverlayProgress);
    }

    protected float getAnimationCounter(S state) {
        return super.getAnimationCounter(entityOf(state), state.tickDelta);
    }

    protected void renderLabelIfPresent(S state, net.minecraft.text.Text text, MatrixStack matrices,
                                        VertexConsumerProvider vertexConsumers, int light) {
        super.renderLabelIfPresent(entityOf(state), text, matrices, vertexConsumers, light);
    }
}
