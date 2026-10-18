package net.jukoz.me.compat;

import net.minecraft.util.math.ColorHelper;

/** 1.20.1 bridge for the 1.21.x {@code net.jukoz.me.compat.MEColors.getWhite(float alpha)} helper. */
public final class MEColors {

    private MEColors() {
    }

    public static int getWhite(float alpha) {
        return ColorHelper.Argb.getArgb(Math.round(alpha * 255.0F), 255, 255, 255);
    }
}
