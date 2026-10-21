package net.jukoz.me.gui.onboarding;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.onboarding.onboarding_faction.OnboardingFactionScreenController;
import net.jukoz.me.network.packets.C2S.PacketTeleportToCurrentSpawn;
import net.jukoz.me.resources.datas.attributes.AttributePool;
import net.jukoz.me.resources.datas.attributes.AttributePoolElement;
import java.awt.event.KeyEvent;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class OnboardingSelectionScreen extends Screen {
private static final Component ONBOARDING_SELECTION_TITLE = Component.translatable("ui.%s.onboarding_selection.title".formatted(MiddleEarth.MOD_ID));
    private static final ResourceLocation BUTTON_WIDGET = new ResourceLocation(MiddleEarth.MOD_ID,"textures/gui/widget/button_widget.png");
    private boolean focusEnabled;
    public ButtonWidget continueAsCharacterButton;
    public ButtonWidget resetCharacterButton;

    private int mouseX;
    private int mouseY;
    private final boolean canResetCharacter;
    private LocalPlayer player;
    float currentDelay;

    List<AttributePoolElement> playerAttributes;

    public OnboardingSelectionScreen(float delay, boolean canResetCharacter, List<AttributePoolElement> playerAttributes) {
        super(ONBOARDING_SELECTION_TITLE);
        this.canResetCharacter = canResetCharacter;
        this.playerAttributes = playerAttributes;
        focusEnabled = false;
        currentDelay = delay;
    }

    @Override
    protected void init() {
        ButtonWidget.PressAction continueAsFaction = button -> {
            teleportPlayerToMiddleEarth();
        };
        continueAsCharacterButton = ButtonWidget.builder(Component.of("continue_character"), continueAsFaction).build();
        addDrawableChild(continueAsCharacterButton);
        if(currentDelay > 0)
            continueAsCharacterButton.active = false;

        if(canResetCharacter){
            ButtonWidget.PressAction resetCharacterAction = button -> {
                var controller = new OnboardingFactionScreenController(this.player.getWorld(), currentDelay, playerAttributes);
                controller.open();
            };
            resetCharacterButton = ButtonWidget.builder(Component.of("reset_character"), resetCharacterAction).build();
            addDrawableChild(resetCharacterButton);
        }
    }

    private void teleportPlayerToMiddleEarth() {
        ClientPlayNetworking.send(new PacketTeleportToCurrentSpawn(false));
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        Entity cameraEntity = this.client.getCameraEntity();
        if (cameraEntity != null) {
            if (cameraEntity instanceof LocalPlayer clientPlayerEntity) {
                this.player = clientPlayerEntity;
                this.mouseX = mouseX;
                this.mouseY = mouseY;
                this.drawContent(context);
            } else {
                this.player = null;
            }
        }
    }

    @Override
    public void tick() {
        if(currentDelay > 0){
            currentDelay = Math.max(0, currentDelay - (1f / 20));
            if(currentDelay == 0) {
                continueAsCharacterButton.active = true;
            }
        }
        super.tick();
    }

    private void drawContent(GuiGraphics context) {
        int panelSizeX = 102;
        int panelSizeY = 18;
        int margin = 5;

        // Draw buttons
        int startX = (width / 2) - (panelSizeX / 2);
        int startY = (height / 2) - (panelSizeY / 2);
        if(continueAsCharacterButton.active){
            context.drawTexture(BUTTON_WIDGET, startX, startY, 0, continueAsCharacterButton.isFocused() || isMouseOver(startX, panelSizeX, startY, panelSizeY) ? 19 : 0, panelSizeX, panelSizeY, 256, 256);

            Component continueText = Component.translatable("ui.%s.continue_character".formatted(MiddleEarth.MOD_ID));
            context.drawText(textRenderer, continueText,
                    startX + (int)((panelSizeX - textRenderer.getWidth(continueText)) / 2f),
                    startY + (int) ((panelSizeY / 2f) - (textRenderer.fontHeight / 2f)) + 1,
                    CommonColors.BLACK, false);

            net.jukoz.me.compat.client.gui.MEGui.setBounds(continueAsCharacterButton, panelSizeX, panelSizeY, startX, startY);
            if(focusEnabled && continueAsCharacterButton.isFocused()){
                context.drawTexture(BUTTON_WIDGET, startX, startY, 103, 0, panelSizeX, panelSizeY, 256, 256);
            }
        } else {
            context.drawTexture(BUTTON_WIDGET, startX, startY, 0, 38, panelSizeX, panelSizeY, 256, 256);
            Component delayText = Component.literal(String.valueOf((Math.round(this.currentDelay * 10f) /10f)));
            context.drawText(textRenderer, delayText,
                    startX + (panelSizeX / 2) - (textRenderer.getWidth(delayText) / 2),
                    startY + 5, CommonColors.RED, true);
        }

        if(canResetCharacter) {

            startY += panelSizeY + margin;

            context.drawTexture(BUTTON_WIDGET, startX, startY, 0, resetCharacterButton.isFocused() || isMouseOver(startX, panelSizeX, startY, panelSizeY) ? 19 : 0, panelSizeX, panelSizeY, 256, 256);

            Component resetText = Component.translatable("ui.%s.reset_character".formatted(MiddleEarth.MOD_ID));
            context.drawText(textRenderer, resetText,
                    startX + (int) ((panelSizeX - textRenderer.getWidth(resetText)) / 2f),
                    startY + (int) ((panelSizeY / 2f) - (textRenderer.fontHeight / 2f)) + 1,
                    CommonColors.BLACK, false);
            net.jukoz.me.compat.client.gui.MEGui.setBounds(resetCharacterButton, panelSizeX, panelSizeY, startX, startY);
            if(focusEnabled && resetCharacterButton.isFocused()){
                context.drawTexture(BUTTON_WIDGET, startX, startY, 0, 103, 0, panelSizeX, panelSizeY, 256, 256);
            }
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Keybind : Tabulation
        if(keyCode == KeyEvent.VK_CODE_INPUT && !focusEnabled){
            focusEnabled = true;
            return true;
        }

        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    private boolean isMouseOver(int startX, int sizeX, int startY, int sizeY) {
        return mouseX >= startX && mouseX <= startX + sizeX
                && mouseY >= startY && mouseY <= startY + sizeY;
    }
}
