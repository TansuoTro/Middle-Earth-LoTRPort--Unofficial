package net.jukoz.me.mixin.client;

import net.jukoz.me.compat.client.gui.ClickableWidgetAccess;
import net.minecraft.client.gui.components.AbstractWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(AbstractWidget.class)
public abstract class ClickableWidgetMixin implements ClickableWidgetAccess {

    @Shadow
    protected int height;

    @Override
    public void me$setHeight(int height) {
        this.height = height;
    }
}
