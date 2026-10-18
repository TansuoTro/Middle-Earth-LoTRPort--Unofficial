package net.jukoz.me.compat;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.Direction;

/**
 * 1.21.2 moved {@code Entity#getFacing()} (3D, derived from the look vector) onto the entity.
 * 1.20.1 only exposes the static {@link Direction#getFacing(double, double, double)} helper, so
 * every ported call site is routed through here.
 */
public final class MEDirection {
    private MEDirection() {
    }

    public static Direction facing(Entity entity) {
        net.minecraft.util.math.Vec3d rotation = entity.getRotationVector();
        return Direction.getFacing(rotation.x, rotation.y, rotation.z);
    }
}
