package net.jukoz.me.compat.client.render.state;

import java.util.UUID;

/**
 * R1 pass-through shim for 1.21.8 {@code BipedEntityRenderState}. Kept minimal on
 * purpose; R2 fills in the rest.
 */
public class BipedEntityRenderStateCompat extends LivingEntityRenderStateCompat {
    public boolean isSneaking;
    public float leaningPitch;
    public float handSwingProgress;
    public UUID uuid;
}
