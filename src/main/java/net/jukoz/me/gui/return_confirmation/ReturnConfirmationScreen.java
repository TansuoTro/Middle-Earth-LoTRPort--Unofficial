package net.jukoz.me.gui.return_confirmation;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Colors;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.utils.widgets.ModWidget;
import net.jukoz.me.network.packets.C2S.PacketTeleportToCurrentOverworldSpawn;

import java.awt.event.KeyEvent;

public class ReturnConfirmationScreen extends Screen {
    private static final Component RETURN_CONFIRMATION_TITLE = Component.translatable("ui.%s.return_confirmation.title".formatted(MiddleEarth.MOD_ID));
    private static final ResourceLocation BUTTON_WIDGET = new ResourceLocation(MiddleEarth.MOD_ID,"textures/gui/widget/button_widget.png");
    public ButtonWidget returnToOverworldButton;
    public ButtonWidget closeButton;
    float currentDelay;
    public ReturnConfirmationScreen(float delay) {
        super(RETURN_CONFIRMATION_TITLE);
        currentDelay = delay;
    }

    @Override
    protected void init() {
        ButtonWidget.PressAction returnToOverworldAction = button -> {
            returnToOverworld();
        };
        returnToOverworldButton = ButtonWidget.builder(Component.translatable("ui.%s.return_confirmation.continue_character.title".formatted(MiddleEarth.MOD_ID)), returnToOverworldAction).build();
        addDrawableChild(returnToOverworldButton);
        if(currentDelay > 0)
            returnToOverworldButton.active = false;
    }

    private void returnToOverworld() {
        ClientPlayNetworking.send(PacketTeleportToCurrentOverworldSpawn.INSTANCE);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        ModWidget.updateMouse(mouseX, mouseY);
        this.drawContent(context);
    }

    @Override
    public void tick() {
        if(currentDelay > 0){
            currentDelay = Math.max(0, currentDelay - (1f / 20));
            if(currentDelay == 0) {
                returnToOverworldButton.active = true;
            }
        }
        super.tick();
    }

    private void drawContent(DrawContext context) {
        int panelSizeX = 102;
        int panelSizeY = 18;
        int margin = 5;

        // Draw buttons
        int startX = (width / 2) - (panelSizeX / 2);
        int startY = (height / 2) - (panelSizeY / 2);
        if(returnToOverworldButton.active){
            context.drawTexture(BUTTON_WIDGET, startX, startY, 0, returnToOverworldButton.isFocused() || isMouseOver(startX, panelSizeX, startY, panelSizeY) ? 19 : 0, panelSizeX, panelSizeY, 256, 256);

            Component continueText = Component.translatable("ui.%s.return_confirmation.continue_character.content".formatted(MiddleEarth.MOD_ID));
            context.drawText(textRenderer, continueText,
                    startX + (int)((panelSizeX - textRenderer.getWidth(continueText)) / 2f),
                    startY + (int) ((panelSizeY / 2f) - (textRenderer.fontHeight / 2f)) + 1,
                    Colors.BLACK, false);

            net.jukoz.me.compat.client.gui.MEGui.setBounds(returnToOverworldButton, panelSizeX, panelSizeY, startX, startY);
            if(ModWidget.getFocusEnabled() && returnToOverworldButton.isFocused()){
                context.drawTexture(BUTTON_WIDGET, startX, startY, 103, 0, panelSizeX, panelSizeY, 256, 256);
            }
        } else {
            context.drawTexture(BUTTON_WIDGET, startX, startY, 0, 38, panelSizeX, panelSizeY, 256, 256);

            Component delayText = Component.literal(String.valueOf((Math.round(this.currentDelay * 10f) /10f)));
            context.drawText(textRenderer, delayText,
                    startX + (panelSizeX / 2) - (textRenderer.getWidth(delayText) / 2),
                    startY + 5, 0xc4343e, true);
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Keybind : Tabulation
        if(keyCode == KeyEvent.VK_CODE_INPUT && !ModWidget.getFocusEnabled()){
            ModWidget.enableFocus(true);
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private boolean isMouseOver(int startX, int sizeX, int startY, int sizeY) {
        return ModWidget.isMouseOver(sizeX, sizeY, startX, startY);
    }
}
