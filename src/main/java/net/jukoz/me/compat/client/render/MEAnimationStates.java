package net.jukoz.me.compat.client.render;

import net.jukoz.me.mixin.client.AnimationStateAccessor;
import net.minecraft.world.entity.AnimationState;

/**
 * R2 replacement for 1.21.8's {@code AnimationState#copyFrom(AnimationState)}.
 *
 * <p>1.20.1's {@link AnimationState} has no copy method; the port rule
 * {@code anim-state-copyfrom} rewrites donor call sites to this helper, which copies the
 * private progress fields through the {@link AnimationStateAccessor} mixin.
 */
public final class MEAnimationStates {
    private MEAnimationStates() {
    }

    public static void copyFrom(AnimationState target, AnimationState source) {
        if (target == null || source == null) {
            return;
        }
        AnimationStateAccessor to = (AnimationStateAccessor) (Object) target;
        AnimationStateAccessor from = (AnimationStateAccessor) (Object) source;
        to.setUpdatedAt(from.getUpdatedAt());
        to.setRawTimeRunning(from.getRawTimeRunning());
    }
}
