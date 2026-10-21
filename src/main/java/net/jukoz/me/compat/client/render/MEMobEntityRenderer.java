package net.jukoz.me.compat.client.render;

import net.jukoz.me.compat.client.render.state.LivingEntityRenderStateCompat;
import net.minecraft.client.renderer.texture.OverlayTexture;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererFactory;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.model.EntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Mob;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

/**
 * R2 base for donor-style 1.21.8 {@code MobRenderer<T, S, M>} renderers.
 *
 * <p>Mirrors the donor state pipeline: build the state, fill it, publish it on
 * {@link MEEntityRenderContext}, then let the donor subclass draw from the state. Also
 * re-implements the basic model + feature pass that 1.20.1's
 * {@link MobRenderer#render} performs from the entity.
 */
public abstract class MEMobEntityRenderer<T extends Mob, S extends LivingEntityRenderStateCompat, M extends EntityModel<T>>
        extends MobRenderer<T, M> {

    protected MEMobEntityRenderer(EntityRendererProvider.Context ctx, M model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    public abstract S createRenderState();

    /** Fills the shared living-entity fields; donor subclasses call this via {@code super}. */
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
        if (state instanceof net.jukoz.me.compat.client.render.state.BipedEntityRenderStateCompat biped) {
            biped.baseScale = net.jukoz.me.entity.EntityAttributesME.scale(entity);
        }
    }

    public ResourceLocation getTexture(S state) {
        return null;
    }

    /** Hook so ageable renderers can swap in an adult/baby model. */
    protected M getModel(S state) {
        return this.model;
    }

    // --- R3: state-typed helpers the donor bodies call (1.20.1 equivalents are entity-typed) ---

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

    public void render(S state, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        M model = this.getModel(state);
        ResourceLocation texture = this.getTexture(state);
        VertexConsumer vertices = vertexConsumers.getBuffer(model.getLayer(texture));
        @SuppressWarnings("unchecked")
        T entity = (T) state.entity;
        model.setAngles(entity, state.limbSwingAnimationProgress, state.limbSwingAmplitude,
                state.age, state.relativeHeadYaw, state.relativeHeadPitch);
        model.render(matrices, vertices, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
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
}
