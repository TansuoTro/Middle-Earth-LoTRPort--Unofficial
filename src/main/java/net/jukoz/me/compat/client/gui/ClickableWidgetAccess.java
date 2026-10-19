package net.jukoz.me.compat.client.gui;

/** Exposes 1.20.1's package-private widget height so the 1.21.8 {@code setDimensions} calls can be emulated. */
public interface ClickableWidgetAccess {
    void me$setHeight(int height);
}
