package net.jukoz.me.compat.client.render;

import net.jukoz.me.compat.client.render.state.LivingEntityRenderStateCompat;
import net.minecraft.client.renderer.entity.EntityRendererFactory;
import net.minecraft.client.model.EntityModel;
import net.minecraft.world.entity.Mob;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

/**
 * R2 base for donor-style renderers that used 1.21.8's {@code AgeableMobEntityRenderer}
 * (an adult model plus a baby model selected from the render state). 1.20.1 has no such
 * vanilla class, so the selection lives here on top of {@link MEMobEntityRenderer}.
 */
public abstract class MEMAgeableMobEntityRenderer<T extends Mob, S extends LivingEntityRenderStateCompat, M extends EntityModel<T>>
        extends MEMobEntityRenderer<T, S, M> {

    private final M babyModel;

    protected MEMAgeableMobEntityRenderer(EntityRendererProvider.Context ctx, M adultModel, M babyModel, float shadowRadius) {
        super(ctx, adultModel, shadowRadius);
        this.babyModel = babyModel;
    }

    @Override
    protected M getModel(S state) {
        return state.baby ? this.babyModel : this.model;
    }

    protected float getShadowRadius(S state) {
        return this.shadowRadius;
    }
}
