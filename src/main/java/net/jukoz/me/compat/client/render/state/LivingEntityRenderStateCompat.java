package net.jukoz.me.compat.client.render.state;

/**
 * R1 pass-through shim for 1.21.8 {@code LivingEntityRenderState}. Fields mirror the
 * donor reads; R2 supplies the real adapter behaviour.
 */
public class LivingEntityRenderStateCompat extends EntityRenderStateCompat {
    public float limbSwingAnimationProgress;
    public float limbSwingAmplitude;
    public float relativeHeadYaw;
    public float relativeHeadPitch;
    public float bodyYaw;
    public float hurtTime;
    public boolean hurt;
}
