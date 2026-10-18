package net.jukoz.me.compat.client.render;

import net.jukoz.me.compat.client.render.state.EntityRenderStateCompat;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

/**
 * R2 bridge for donor-style 1.21.8 feature renderers written as
 * {@code FeatureRenderer<S extends EntityRenderState, M>}.
 *
 * <p>1.20.1's {@link FeatureRenderer#render} is entity-typed. This adapter implements it
 * as a bridge that pulls the state off {@link MEEntityRenderContext} and forwards to the
 * donor-style {@link #render(MatrixStack, VertexConsumerProvider, int, EntityRenderStateCompat, float, float)}.
 */
public abstract class MEFeatureRenderer<T extends LivingEntity, S extends EntityRenderStateCompat, M extends EntityModel<T>>
        extends FeatureRenderer<T, M> {

    public MEFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    protected M getModel() {
        return this.getContextModel();
    }

    public abstract void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light,
                                S state, float limbAngle, float limbDistance);

    @Override
    public final void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light,
                             T entity, float limbAngle, float limbDistance, float tickDelta,
                             float animationProgress, float headYaw, float headPitch) {
        S state = MEEntityRenderContext.get();
        if (state != null) {
            render(matrices, vertexConsumers, light, state, limbAngle, limbDistance);
        }
    }
}
