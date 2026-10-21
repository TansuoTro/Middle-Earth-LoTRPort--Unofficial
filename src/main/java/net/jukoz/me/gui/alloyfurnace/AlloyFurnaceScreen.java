package net.jukoz.me.gui.alloyfurnace;

import com.mojang.blaze3d.systems.RenderSystem;
import net.jukoz.me.MiddleEarth;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class AlloyFurnaceScreen extends AbstractContainerScreen<AlloyFurnaceScreenHandler> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(MiddleEarth.MOD_ID, "textures/gui/alloy_furnace.png");
    private static final int PROGRESS_ARROW_SIZE = 24;
    private static final int COOKING_FIRE_SIZE = 14;
    public AlloyFurnaceScreen(AlloyFurnaceScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - font.getWidth(title)) / 2;
    }

    @Override
    protected void drawBackground(GuiGraphics context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        renderProgressArrow(context, x, y);
    }

    private void renderProgressArrow(GuiGraphics context, int x, int y) {
        if(handler.isCooking()) {
            int cookingTime = (int) (handler.getScaledCooking() * COOKING_FIRE_SIZE);
            net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, x + 53, y + 50 - cookingTime, 176, COOKING_FIRE_SIZE - cookingTime, COOKING_FIRE_SIZE, cookingTime);
        }
        if(handler.isCrafting()) {
            net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, x + 94, y + 34, 176, 14, (int) (handler.getScaledProgress() * PROGRESS_ARROW_SIZE), 17);
        }
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
    }
}
