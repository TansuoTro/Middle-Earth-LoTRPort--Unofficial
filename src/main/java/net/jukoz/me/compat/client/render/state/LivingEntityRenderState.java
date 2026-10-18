package net.jukoz.me.compat.client.render.state;

/**
 * 1.20.1 stand-in for 1.21.8
 * {@code net.jukoz.me.compat.client.render.state.LivingEntityRenderState}. Bodies land in R2.
 */
public class LivingEntityRenderState extends EntityRenderState {
    public float limbSwingAnimationProgress;
    public float limbSwingAmplitude;
    public float limbAmplitudeInverse = 1.0F;
    public float relativeHeadYaw;
    public float relativeHeadPitch;
    public float bodyYaw;
    public float hurtTime;
    public boolean hurt;
    public boolean isGliding;
    public boolean hasVehicle;
}
