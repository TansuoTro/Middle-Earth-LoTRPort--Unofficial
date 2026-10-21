package net.jukoz.me.compat.client.render;

import net.jukoz.me.compat.client.render.state.EntityRenderStateCompat;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.model.EntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.LivingEntity;

/**
 * R2 bridge for donor-style 1.21.8 feature renderers written as
 * {@code RenderLayer<S extends EntityRenderState, M>}.
 *
 * <p>1.20.1's {@link RenderLayer#render} is entity-typed. This adapter implements it
 * as a bridge that pulls the state off {@link MEEntityRenderContext} and forwards to the
 * donor-style {@link #render(PoseStack, MultiBufferSource, int, EntityRenderStateCompat, float, float)}.
 */
public abstract class MEFeatureRenderer<T extends LivingEntity, S extends EntityRenderStateCompat, M extends EntityModel<T>>
        extends RenderLayer<T, M> {

    public MEFeatureRenderer(RenderLayerParent<T, M> context) {
        super(context);
    }

    protected M getModel() {
        return this.getContextModel();
    }

    public abstract void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light,
                                S state, float limbAngle, float limbDistance);

    @Override
    public final void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light,
                             T entity, float limbAngle, float limbDistance, float tickDelta,
                             float animationProgress, float headYaw, float headPitch) {
        S state = MEEntityRenderContext.get();
        if (state != null) {
            render(matrices, vertexConsumers, light, state, limbAngle, limbDistance);
        }
    }
}
