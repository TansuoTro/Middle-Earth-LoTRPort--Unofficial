package net.jukoz.me.compat.client.render.state;

/**
 * R2 field carrier for the living-entity render state family. The donor-named
 * {@link LivingEntityRenderState} simply extends this class.
 */
public class LivingEntityRenderStateCompat extends EntityRenderStateCompat {
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
