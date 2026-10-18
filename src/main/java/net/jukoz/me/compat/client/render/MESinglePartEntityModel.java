package net.jukoz.me.compat.client.render;

import net.jukoz.me.compat.client.render.state.EntityRenderStateCompat;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

/**
 * R2 bridge for donor-style 1.21.8 models written as
 * {@code SinglePartEntityModel<S extends EntityRenderState>}.
 *
 * <p>1.20.1's {@link SinglePartEntityModel} is entity-typed, so the entity arguments are
 * ignored: the state currently published on {@link MEEntityRenderContext} is handed to
 * the donor-style {@link #setAngles(EntityRenderStateCompat)} instead.
 */
public abstract class MESinglePartEntityModel<S extends EntityRenderStateCompat>
        extends SinglePartEntityModel<LivingEntity> {

    private final ModelPart part;

    public MESinglePartEntityModel(ModelPart part) {
        this.part = part;
    }

    @Override
    public ModelPart getPart() {
        return this.part;
    }

    public ModelPart getRootPart() {
        return this.part;
    }

    public abstract void setAngles(S state);

    @Override
    public final void setAngles(LivingEntity entity, float limbSwing, float limbSwingAmount,
                                float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        S state = MEEntityRenderContext.get();
        if (state != null) {
            setAngles(state);
        }
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay,
                       float red, float green, float blue, float alpha) {
        this.getPart().render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }

    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        float alpha = (color >> 24 & 0xFF) / 255.0F;
        float red = (color >> 16 & 0xFF) / 255.0F;
        float green = (color >> 8 & 0xFF) / 255.0F;
        float blue = (color & 0xFF) / 255.0F;
        this.getPart().render(matrices, vertices, light, overlay, red, green, blue, alpha);
    }
}
