package net.jukoz.me.compat;

import net.minecraft.world.entity.Entity;
import net.minecraft.core.Direction;

/**
 * 1.21.2 moved {@code Entity#getFacing()} (3D, derived from the look vector) onto the entity.
 * 1.20.1 only exposes the static {@link Direction#getFacing(double, double, double)} helper, so
 * every ported call site is routed through here.
 */
public final class MEDirection {
    private MEDirection() {
    }

    public static Direction facing(Entity entity) {
        net.minecraft.world.phys.Vec3 rotation = entity.getRotationVector();
        return Direction.getFacing(rotation.x, rotation.y, rotation.z);
    }
}
