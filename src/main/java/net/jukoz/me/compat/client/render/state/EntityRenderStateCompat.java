package net.jukoz.me.compat.client.render.state;

/**
 * R1 pass-through shim for the 1.21.8 client render-state system.
 *
 * <p>1.21.8 moved every entity render state into
 * {@code net.minecraft.client.render.entity.state}; 1.20.1 has no such package.
 * The port rule {@code render-state-pkg} repoints those imports here. This base
 * skeleton only carries {@link #tickDelta}; R2 fills in the adapters.
 */
public class EntityRenderStateCompat {
    public float tickDelta;
}
