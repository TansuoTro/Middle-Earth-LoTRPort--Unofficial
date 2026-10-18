package net.jukoz.me.mixin.client;
import net.jukoz.me.compat.client.render.MEAnimationStates;

import net.minecraft.entity.AnimationState;
import org.spongepowered.asm.mixin.Mixin;

/**
 * R1/R2 shim for 1.21.8 {@code AnimationState#copyFrom(AnimationState)}.
 *
 * <p>The donor renderer pipeline copies one state's progress onto another
 * ({@code MEAnimationStates.copyFrom(state.idleAnimationState, entity.idleAnimationState)}), but 1.20.1
 * {@link AnimationState} has no such method. Verified against the yarn
 * 1.20.1+build.10 jar: the backing fields are {@code updatedAt} and {@code timeRunning}.
 * {@link AnimationStateAccessor} exposes them; the copy itself lives in
 * {@code net.jukoz.me.compat.client.render.MEAnimationStates} so plain (non-mixin) code
 * can call it.
 */
@Mixin(AnimationState.class)
public abstract class AnimationStateAccessorMixin implements AnimationStateAccessor {
}
