package net.jukoz.me.entity.projectile.smoke;

import net.jukoz.me.compat.client.render.state.ProjectileEntityRenderState;
import org.joml.Quaternionf;

public class SmokeRingProjectileRenderState extends ProjectileEntityRenderState {
    public Quaternionf orientationQuat;
    public int maxLifespan;
    public boolean failed;
}
