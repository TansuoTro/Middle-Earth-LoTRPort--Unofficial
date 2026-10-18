package net.jukoz.me.compat.client.render.state;

import net.minecraft.entity.Entity;
import net.minecraft.text.Text;

/**
 * 1.20.1 stand-in for 1.21.8 {@code net.jukoz.me.compat.client.render.state.EntityRenderState}.
 *
 * <p>The port rule {@code render-state-pkg} rewrites every import of that FQCN to this
 * class. It carries everything the subclass skeletons inherit; the donor also reads
 * an entity hook off the state, hence {@link #entity}. Bodies land in R2.
 */
public class EntityRenderState extends EntityRenderStateCompat {
    public Entity entity;
    public Text customName;
    public boolean invisible;
    public boolean baby;
    public float age;
    public float yaw;
    public float pitch;
}
