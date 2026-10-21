package net.jukoz.me.block.special.crockpot;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;

public class CrockpotScreen extends AbstractContainerScreen<CrockpotScreenHandler> {
    private static final ResourceLocation WATER_TEXTURE = new ResourceLocation("minecraft", "textures/block/water_still.png");
    private static final ResourceLocation TEXTURE = MiddleEarth.ofPath( "textures", "gui", "crockpot.png");
    private static final ResourceLocation BUBBLES_TEXTURE = new ResourceLocation("minecraft", "container/brewing_stand/bubbles");
    private static final int[] BUBBLE_PROGRESS = new int[]{29, 24, 20, 16, 11, 6, 0};
    private static final int LIQUID_SIZE = 24;

    private static final int PROGRESS_ARROW_SIZE = 24;

    public CrockpotScreen(CrockpotScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
        super.init();
    }

    @Override
    protected void drawBackground(GuiGraphics context, float deltaTicks, int mouseX, int mouseY) {
        context.drawTexture(TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight, 256, 256);
        context.drawTexture(WATER_TEXTURE, this.x + 120, this.y + 31, 0, 0, 24, 24, 16, 16, 0xFF66AAEE);
    }
}
