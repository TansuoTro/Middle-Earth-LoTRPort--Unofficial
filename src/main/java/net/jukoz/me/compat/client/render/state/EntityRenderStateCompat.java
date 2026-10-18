package net.jukoz.me.compat.client.render.state;

import net.minecraft.entity.Entity;
import net.minecraft.text.Text;

/**
 * 1.20.1 stand-in for the 1.21.8 client render-state system.
 *
 * <p>1.21.8 moved every entity render state into
 * {@code net.minecraft.client.render.entity.state}; 1.20.1 has no such package, so the
 * port rule {@code render-state-pkg} repoints those imports here. R2 makes this the
 * single root of the state hierarchy so the donor-named subclasses and the renderer
 * adapters can all agree on one type.
 */
public class EntityRenderStateCompat {
    public float tickDelta;
    public Entity entity;
    public Text customName;
    public boolean invisible;
    public boolean baby;
    public float age;
    public float yaw;
    public float pitch;
}
