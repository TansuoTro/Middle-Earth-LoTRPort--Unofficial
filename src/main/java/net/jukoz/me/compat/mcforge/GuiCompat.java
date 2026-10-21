package net.jukoz.me.compat.mcforge;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

/**
 * Forge compat layer for Fabric's {@code DrawableHelper.drawTexture} overloads. Fabric's
 * signature carries the texture dimensions (and an optional tint colour); 1.20.1's
 * {@link GuiGraphics#blit} does not, so the extra arguments are dropped here.
 */
public final class GuiCompat {
    private GuiCompat() {}

    /** Fabric's {@code drawTexture(texture, x, y, u, v, width, height, textureWidth, textureHeight)}. */
    public static void drawTexture(GuiGraphics graphics, ResourceLocation texture, int x, int y,
                                   int u, int v, int width, int height, int textureWidth,
                                   int textureHeight) {
        graphics.blit(texture, x, y, u, v, width, height);
    }

    /** Fabric's {@code drawTexture(texture, x, y, u, v, width, height, textureWidth, textureHeight, color)}. */
    public static void drawTexture(GuiGraphics graphics, ResourceLocation texture, int x, int y,
                                   int u, int v, int width, int height, int textureWidth,
                                   int textureHeight, int color) {
        drawTexture(graphics, texture, x, y, u, v, width, height, textureWidth, textureHeight);
    }

    /** Fabric's {@code drawTexture(texture, x, y, z, u, v, width, height, textureWidth, textureHeight)}. */
    public static void drawTexture(GuiGraphics graphics, ResourceLocation texture, int x, int y,
                                   float z, int u, int v, int width, int height, int textureWidth,
                                   int textureHeight) {
        drawTexture(graphics, texture, x, y, u, v, width, height, textureWidth, textureHeight);
    }
}
