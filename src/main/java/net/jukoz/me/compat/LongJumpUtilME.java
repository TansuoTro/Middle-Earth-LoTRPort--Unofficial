package net.jukoz.me.compat;

import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.Mob;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import java.util.Optional;
import net.minecraft.world.entity.Pose;

/** 1.20.1 port of 1.21's {@code LongJumpUtil.calculateJumpVectorForAngle}. */
public final class LongJumpUtilME {
    private static final double GRAVITY = 0.08;

    private LongJumpUtilME() {
    }

    public static Optional<Vec3> getJumpingVelocity(Mob entity, Vec3 targetPos, float power, int angleDegrees, boolean checkClear) {
        Vec3 pos = entity.getPos();
        Vec3 approach = new Vec3(targetPos.x - pos.x, 0.0, targetPos.z - pos.z).normalize().multiply(0.5);
        Vec3 target = targetPos.subtract(approach);
        Vec3 delta = target.subtract(pos);

        float angle = (float) angleDegrees * (float) Math.PI / 180.0F;
        double direction = Math.atan2(delta.z, delta.x);
        double horizontalSqr = delta.subtract(0.0, delta.y, 0.0).lengthSquared();
        double horizontal = Math.sqrt(horizontalSqr);
        double vertical = delta.y;
        double sin2 = Math.sin(2.0F * angle);
        double cos2 = Math.pow(Math.cos(angle), 2.0);
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);
        double sinDir = Math.sin(direction);
        double cosDir = Math.cos(direction);
        double speedSqr = horizontalSqr * GRAVITY / (horizontal * sin2 - 2.0 * vertical * cos2);
        if (speedSqr < 0.0) {
            return Optional.empty();
        }

        double speed = Math.sqrt(speedSqr);
        if (speed > power) {
            return Optional.empty();
        }

        double horizontalSpeed = speed * cos;
        double upwardSpeed = speed * sin;

        if (checkClear) {
            int steps = Mth.ceil(horizontal / horizontalSpeed) * 2;
            double travelled = 0.0;
            Vec3 previous = null;
            EntityDimensions dimensions = entity.getDimensions(Pose.LONG_JUMPING);

            for (int i = 0; i < steps - 1; i++) {
                travelled += horizontal / (double) steps;
                double y = sin / cos * travelled - Math.pow(travelled, 2.0) * GRAVITY / (2.0 * speedSqr * Math.pow(cos, 2.0));
                Vec3 next = new Vec3(pos.x + travelled * cosDir, pos.y + y, pos.z + travelled * sinDir);
                if (previous != null && !isClearTransition(entity, dimensions, previous, next)) {
                    return Optional.empty();
                }
                previous = next;
            }
        }

        return Optional.of(new Vec3(horizontalSpeed * cosDir, upwardSpeed, horizontalSpeed * sinDir).multiply(0.95F));
    }

    private static boolean isClearTransition(Mob entity, EntityDimensions dimensions, Vec3 from, Vec3 to) {
        Vec3 delta = to.subtract(from);
        double step = Math.min(dimensions.width, dimensions.height);
        int steps = Mth.ceil(delta.length() / step);
        Vec3 unit = delta.normalize();
        Vec3 current = from;

        for (int i = 0; i < steps; i++) {
            current = i == steps - 1 ? to : current.add(unit.multiply(step * 0.9F));
            if (!entity.getWorld().isSpaceEmpty(entity, dimensions.getBoxAt(current))) {
                return false;
            }
        }
        return true;
    }
}
