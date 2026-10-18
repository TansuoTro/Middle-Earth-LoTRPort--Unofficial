package net.jukoz.me.mixin.client;
import net.jukoz.me.compat.client.render.MEAnimationStates;

import org.spongepowered.asm.mixin.gen.Accessor;

/**
 * Accessor interface for the private backing fields of
 * {@link net.minecraft.entity.AnimationState}. Implemented by
 * {@link AnimationStateAccessorMixin}; non-mixin code (see
 * {@code net.jukoz.me.compat.client.render.MEAnimationStates}) can duck-type through it.
 */
public interface AnimationStateAccessor {

    @Accessor("updatedAt")
    long getUpdatedAt();

    @Accessor("updatedAt")
    void setUpdatedAt(long updatedAt);

    /** Named "{@code Raw}" because {@link net.minecraft.entity.AnimationState#getTimeRunning()} already exists. */
    @Accessor("timeRunning")
    long getRawTimeRunning();

    @Accessor("timeRunning")
    void setRawTimeRunning(long timeRunning);
}
