package net.jukoz.me.compat.client.render;

import net.jukoz.me.compat.client.render.state.EntityRenderStateCompat;

/**
 * R2 bridge that hands the donor-style render state to the model/feature layer while a
 * single entity is being rendered.
 *
 * <p>1.21.8 passes an {@code EntityRenderState} into every model ({@code setAngles(S)})
 * and feature ({@code render(..., S, ...)}). 1.20.1 keeps the entity-based signatures,
 * so the adapters publish the state for the duration of one render call and the bridged
 * models/features read it back from here (matching by the renderer that is currently
 * active, which is safe because entity rendering is single-threaded per frame).
 */
public final class MEEntityRenderContext {
    private static final ThreadLocal<EntityRenderStateCompat> CURRENT = new ThreadLocal<>();

    private MEEntityRenderContext() {
    }

    public static <S extends EntityRenderStateCompat> void set(S state) {
        CURRENT.set(state);
    }

    @SuppressWarnings("unchecked")
    public static <S extends EntityRenderStateCompat> S get() {
        return (S) CURRENT.get();
    }

    public static void clear() {
        CURRENT.remove();
    }
}
