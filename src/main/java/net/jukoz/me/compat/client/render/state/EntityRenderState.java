package net.jukoz.me.compat.client.render.state;

/**
 * 1.20.1 stand-in for 1.21.8
 * {@code net.jukoz.me.compat.client.render.state.EntityRenderState}.
 *
 * <p>Fields live on {@link EntityRenderStateCompat} so the whole state family shares a
 * single root; this class exists only so the retargeted donor imports still resolve.
 */
public class EntityRenderState extends EntityRenderStateCompat {
}
