package net.jukoz.me.compat;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;

/**
 * Component helpers for the 1.21 API shape.
 *
 * <p>1.21.5 gave {@code Component} a {@code withColor(int)}. 1.20.1 only has it on
 * {@code Style}, and {@code Component} itself is immutable, so the ported call sites go
 * through here instead of being expanded into {@code copy().styled(...)} by hand.
 */
public final class METext {
    private METext() {
    }

    public static MutableComponent withColor(Component text, int rgb) {
        return text.copy().styled(style -> style.withColor(rgb));
    }
}
