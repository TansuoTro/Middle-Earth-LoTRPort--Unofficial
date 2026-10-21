package net.jukoz.me.mixin.client;

import net.jukoz.me.compat.client.render.MEAnimationStates;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import net.minecraft.world.entity.AnimationState;

/**
 * Accessor interface for the private backing fields of
 * {@link net.minecraft.world.entity.AnimationState}. Implemented by
 * {@link AnimationStateAccessorMixin}; non-mixin code (see
 * {@code net.jukoz.me.compat.client.render.MEAnimationStates}) can duck-type through it.
 */
@Mixin(net.minecraft.entity.AnimationState.class)
public interface AnimationStateAccessor {

    @Accessor("updatedAt")
    long getUpdatedAt();

    @Accessor("updatedAt")
    void setUpdatedAt(long updatedAt);

    /** Named "{@code Raw}" because {@link net.minecraft.world.entity.AnimationState#getTimeRunning()} already exists. */
    @Accessor("timeRunning")
    long getRawTimeRunning();

    @Accessor("timeRunning")
    void setRawTimeRunning(long timeRunning);
}
