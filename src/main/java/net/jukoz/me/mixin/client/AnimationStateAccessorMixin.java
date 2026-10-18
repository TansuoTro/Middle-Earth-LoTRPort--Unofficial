package net.jukoz.me.mixin.client;

import net.minecraft.entity.AnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * R1 shim for 1.21.8 {@code AnimationState#copyFrom(AnimationState)}.
 *
 * <p>The donor renderer pipeline copies one state's progress onto another
 * ({@code state.idleAnimationState.copyFrom(entity.idleAnimationState)}), but 1.20.1
 * {@link AnimationState} has no such method. Verified against the yarn
 * 1.20.1+build.10 jar: the backing fields are {@code updatedAt} and {@code timeRunning}
 * (there is no {@code startRelatedTick}). The accessors below expose them so the
 * {@link #copyFrom} helper can replicate the donor semantics. R2 re-points call sites.
 */
@Mixin(AnimationState.class)
public abstract class AnimationStateAccessorMixin {

    @Accessor("updatedAt")
    public abstract long getUpdatedAt();

    @Accessor("updatedAt")
    public abstract void setUpdatedAt(long updatedAt);

    /** Named "{@code Raw}" because {@link AnimationState#getTimeRunning()} already exists. */
    @Accessor("timeRunning")
    public abstract long getRawTimeRunning();

    @Accessor("timeRunning")
    public abstract void setRawTimeRunning(long timeRunning);

    @Unique
    public void copyFrom(AnimationState other) {
        AnimationStateAccessorMixin source = (AnimationStateAccessorMixin) (Object) other;
        this.setUpdatedAt(source.getUpdatedAt());
        this.setRawTimeRunning(source.getRawTimeRunning());
    }
}
