package net.jukoz.me.compat.client.gui;

import net.jukoz.me.mixin.client.ClickableWidgetAccess;
import net.minecraft.client.gui.widget.ClickableWidget;

/** 1.20.1 bridge for the 1.21.x widget {@code setDimensions}/{@code setDimensionsAndPosition} helpers. */
public final class MEGui {

    private MEGui() {
    }

    public static void setSize(ClickableWidget widget, int width, int height) {
        widget.setWidth(width);
        ((ClickableWidgetAccess) widget).me$setHeight(height);
    }

    public static void setBounds(ClickableWidget widget, int width, int height, int x, int y) {
        setSize(widget, width, height);
        widget.setPosition(x, y);
    }
}
