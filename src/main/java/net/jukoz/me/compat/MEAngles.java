package net.jukoz.me.compat;

import net.minecraft.util.Mth;

/**
 * 1.21's {@code Mth.lerpAngleRadians} shim. 1.20.1 only ships the degree variant.
 */
public final class MEAngles {
    private MEAngles() {
    }

    public static float lerpAngleRadians(float delta, float start, float end) {
        float startDeg = (float) Math.toDegrees(start);
        float endDeg = (float) Math.toDegrees(end);
        return (float) Math.toRadians(Mth.lerpAngleDegrees(delta, startDeg, endDeg));
    }
}
