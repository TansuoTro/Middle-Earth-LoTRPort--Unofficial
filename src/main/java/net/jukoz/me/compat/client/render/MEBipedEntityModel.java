package net.jukoz.me.compat.client.render;

import java.util.function.Function;

import net.jukoz.me.compat.client.render.state.BipedEntityRenderStateCompat;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;

/**
 * R2 bridge for donor-style 1.21.8 models written as
 * {@code BipedEntityModel<S extends BipedEntityRenderState>}.
 *
 * <p>The entity-typed 1.20.1 {@link BipedEntityModel#setAngles} entry is intercepted:
 * the render state published on {@link MEEntityRenderContext} is copied onto the model
 * fields and the vanilla posing math is run against the state's owning entity.
 */
public abstract class MEBipedEntityModel<T extends LivingEntity, S extends BipedEntityRenderStateCompat>
        extends BipedEntityModel<T> {

    public MEBipedEntityModel(ModelPart root) {
        super(root);
    }

    public MEBipedEntityModel(ModelPart root, Function<Identifier, RenderLayer> layerFactory) {
        super(root, layerFactory);
    }

    @Override
    public final void setAngles(T entity, float limbSwing, float limbSwingAmount,
                                float ageInTicks, float netHeadYaw, float headPitch) {
        S state = MEEntityRenderContext.get();
        if (state != null) {
            setAngles(state);
        }
    }

    /**
     * Donor-style posing entry. The default mirrors the state onto the model and defers
     * to the vanilla biped math; donor models may override it and call this via super.
     */
    public void setAngles(S state) {
        this.handSwingProgress = state.handSwingProgress;
        this.sneaking = state.isSneaking;
        this.leaningPitch = state.leaningPitch;
        this.leftArmPose = state.leftArmPose;
        this.rightArmPose = state.rightArmPose;
        this.riding = state.hasVehicle;
        this.child = state.baby;
        if (state.entity instanceof LivingEntity living) {
            @SuppressWarnings("unchecked")
            T entity = (T) living;
            super.setAngles(entity, state.limbSwingAnimationProgress, state.limbSwingAmplitude,
                    state.age, state.relativeHeadYaw, state.relativeHeadPitch);
        }
    }
}
