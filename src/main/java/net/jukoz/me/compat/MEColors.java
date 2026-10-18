package net.jukoz.me.compat;

import net.minecraft.util.math.ColorHelper;

/** 1.20.1 bridge for the 1.21.x {@code net.jukoz.me.compat.MEColors.getWhite(float alpha)} helper. */
public final class MEColors {

    private MEColors() {
    }

    public static int getWhite(float alpha) {
        return ColorHelper.Argb.getArgb(Math.round(alpha * 255.0F), 255, 255, 255);
    }

    /** 1.21's {@code ColorHelper.withAlpha(float, int)} (absent from 1.20.1). */
    public static int withAlpha(float alpha, int color) {
        return (Math.round(alpha * 255.0F) << 24) | (color & 0xFFFFFF);
    }

    /** 1.21's {@code ColorHelper.fullAlpha(int)} (absent from 1.20.1). */
    public static int fullAlpha(int color) {
        return 0xFF000000 | (color & 0xFFFFFF);
    }
}
