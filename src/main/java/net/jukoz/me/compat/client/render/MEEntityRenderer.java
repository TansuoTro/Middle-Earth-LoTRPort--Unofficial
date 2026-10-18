package net.jukoz.me.compat.client.render;

import net.jukoz.me.compat.client.render.state.EntityRenderStateCompat;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

/**
 * R2 base for donor-style 1.21.8 renderers that were written against
 * {@code net.minecraft.client.render.entity.EntityRenderer<T, S>}.
 *
 * <p>1.20.1 has a single-type-parameter {@link EntityRenderer} whose {@code render} takes
 * the entity; the donor pipeline instead builds an {@code S extends EntityRenderState},
 * fills it, and renders from it. This adapter mirrors that pipeline on 1.20.1 and makes
 * the state available to models/features through {@link MEEntityRenderContext}.
 */
public abstract class MEEntityRenderer<T extends Entity, S extends EntityRenderStateCompat>
        extends EntityRenderer<T> {

    protected MEEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    public abstract S createRenderState();

    /** Fills the shared entity fields; donor subclasses call this via {@code super}. */
    public void updateRenderState(T entity, S state, float tickDelta) {
        state.age = entity.age + tickDelta;
        state.yaw = entity.getYaw(tickDelta);
        state.pitch = entity.getPitch(tickDelta);
        state.invisible = entity.isInvisible();
        state.customName = entity.getCustomName();
    }

    /**
     * Donor-style entry point: the state has already been built and published.
     * Overridden by donor subclasses; the default does nothing.
     */
    public void render(S state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
    }

    /**
     * Donor-style texture lookup. Not abstract so partially-ported donors (and renderers
     * that draw entirely from the entity, ignoring the state) still compile; the default
     * matches 1.20.1's nullable {@code getTexture} contract.
     */
    public Identifier getTexture(S state) {
        return null;
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
}
