package net.jukoz.me.gui.forge;

import net.jukoz.me.compat.METext;
import com.google.common.collect.Lists;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.StateSwitchingButton;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.special.forge.MetalTypes;
import net.jukoz.me.network.packets.C2S.ForgeModeSwitchPacket;
import net.jukoz.me.network.packets.C2S.ForgeOutputPacket;
import java.util.List;

public class ForgeAlloyingScreen extends AbstractContainerScreen<ForgeAlloyingScreenHandler> {
    private static final ResourceLocation TEXTURE = MiddleEarth.ofPath( "textures", "gui", "forge.png");

    private static final ResourceLocation EXTRACT_BUTTON = MiddleEarth.of("extract");
    private static final ResourceLocation EXTRACT_BUTTON_DISABLED = MiddleEarth.of("extract_disabled");
    private static final ResourceLocation EXTRACT_BUTTON_FOCUSED = MiddleEarth.of("extract_focused");

    private static final ResourceLocation LEFT_CYCLE_EXTRACT_BUTTON = MiddleEarth.of("left_cycle_arrow");
    private static final ResourceLocation LEFT_CYCLE_EXTRACT_BUTTON_FOCUSED = MiddleEarth.of("left_cycle_arrow_focused");

    private static final ResourceLocation RIGHT_CYCLE_EXTRACT_BUTTON = MiddleEarth.of("right_cycle_arrow");
    private static final ResourceLocation RIGHT_CYCLE_EXTRACT_BUTTON_FOCUSED = MiddleEarth.of("right_cycle_arrow_focused");

    private static final ResourceLocation ALLOYING_SWITCH_BUTTON = MiddleEarth.of("alloying_mode");
    private static final ResourceLocation ALLOYING_SWITCH_BUTTON_FOCUSED = MiddleEarth.of("alloying_mode_highlighted");
    private static final ResourceLocation HEATING_SWITCH_BUTTON = MiddleEarth.of("heating_mode");
    private static final ResourceLocation HEATING_SWITCH_BUTTON_FOCUSED = MiddleEarth.of("heating_mode_highlighted");

    private static final int PROGRESS_ARROW_SIZE = 27;
    private static final int COOKING_FIRE_SIZE = 14;
    public static final int LIQUID_HEIGHT = 26;

    private static final int EXTRACT_BUTTON_ITEM_X = 136;
    private static final int EXTRACT_BUTTON_ITEM_Y = 18;
    private static final int EXTRACT_BUTTON_ITEM_U = 235;

    private static final int TEXTURE_SIZE = 256;

    public ImageButton extractButton;
    public StateSwitchingButton leftExtractCycleButton;
    public StateSwitchingButton rightExtractCycleButton;

    public ImageButton modeSwitchToAlloyButton;
    public ImageButton modeSwitchToHeatingButton;

    private int outputMode = 0;
    private boolean heatingMode = true;

    public ForgeAlloyingScreen(ForgeAlloyingScreenHandler handler, Inventory inventory, Component title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth - font.getWidth(title)) / 2;
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        this.heatingMode = handler.heatingMode();

        this.leftExtractCycleButton = new StateSwitchingButton(x + 121, y + 24, 7 ,11, true);
        this.leftExtractCycleButton.setTextureUV(0, 0, 0, 0, LEFT_CYCLE_EXTRACT_BUTTON);
        this.leftExtractCycleButton.visible = false;

        this.rightExtractCycleButton = new StateSwitchingButton(x + 162, y + 24, 7,11, true);
        this.rightExtractCycleButton.setTextureUV(0, 0, 0, 0, RIGHT_CYCLE_EXTRACT_BUTTON);
        this.rightExtractCycleButton.visible = false;

        this.extractButton = new ImageButton(x + 131, y + 12, 28 ,36, 0, 0, 0, EXTRACT_BUTTON, 256, 256, (button)-> {
            int amount = 0;
            switch (outputMode){
                case 1 -> amount = 16;
                case 2 -> amount = 144;
                case 3, 4 -> amount = 288;
                case 5 -> amount = 432;
            }

            ClientPlayNetworking.send(new ForgeOutputPacket(amount, handler.getPos().getX(),handler.getPos().getY(),handler.getPos().getZ(), outputMode));
            }, Component.translatable("button." + MiddleEarth.MOD_ID + ".extract_metal")
        );

        if(this.outputMode == 0 && handler.checkMaxOutput() > 0) {
            this.extractButton.setTooltip(Tooltip.of(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".forge_output_mode_await")));
        } else {
            setExtractButtonTooltip();
        }

        this.modeSwitchToAlloyButton = new ImageButton(x + 23, y + 68, 10 ,10, 0, 0, 0, HEATING_SWITCH_BUTTON, 256, 256, (button)-> {
            ClientPlayNetworking.send(new ForgeModeSwitchPacket(handler.getPos().getX(),handler.getPos().getY(),handler.getPos().getZ()));
            this.modeSwitchToAlloyButton.visible = false;
            this.modeSwitchToAlloyButton.active = false;
            this.modeSwitchToHeatingButton.visible = true;
            this.modeSwitchToHeatingButton.active = true;
        }, Component.translatable("button." + MiddleEarth.MOD_ID + ".switch_mode"));
        this.modeSwitchToHeatingButton = new ImageButton(x + 23, y + 60, 10 ,10, 0, 0, 0, ALLOYING_SWITCH_BUTTON, 256, 256, (button)-> {
            ClientPlayNetworking.send(new ForgeModeSwitchPacket(handler.getPos().getX(),handler.getPos().getY(),handler.getPos().getZ()));
            this.modeSwitchToHeatingButton.visible = false;
            this.modeSwitchToHeatingButton.active = false;
            this.modeSwitchToAlloyButton.visible = true;
            this.modeSwitchToAlloyButton.active = true;
        }, Component.translatable("button." + MiddleEarth.MOD_ID + ".switch_mode"));

        this.modeSwitchToHeatingButton.setTooltip(Tooltip.of(Component.translatable("tooltip." + MiddleEarth.MOD_ID +".forge_mode_switch_heating")));
        this.modeSwitchToAlloyButton.setTooltip(Tooltip.of(Component.translatable("tooltip." + MiddleEarth.MOD_ID +".forge_mode_switch_alloying")));

        addDrawableChild(leftExtractCycleButton);
        addDrawableChild(extractButton);
        addDrawableChild(rightExtractCycleButton);

        addDrawableChild(modeSwitchToHeatingButton);
        addDrawableChild(modeSwitchToAlloyButton);
    }

    @Override
    protected void handledScreenTick() {
        super.handledScreenTick();
        heatingMode = handler.heatingMode();
        updateSwitchState();

        this.leftExtractCycleButton.visible = true;
        this.rightExtractCycleButton.visible = true;
        this.extractButton.visible = true;

        if(handler.checkMaxOutput() == 4 && outputMode >= 5){
            outputMode = 5;
        }
        if(handler.checkMaxOutput() == 3 && outputMode >= 4){
            outputMode = 4;
        }
        if(handler.checkMaxOutput() == 2 && outputMode >= 2){
            outputMode = 2;
        }
        if(handler.checkMaxOutput() == 1 && outputMode >= 1){
            outputMode = 1;
        }
        if(handler.checkMaxOutput() == 0 && outputMode >= 1) {
            outputMode = 0;
        }

        extractButton.active = handler.checkMaxOutput() > 0 && outputMode != 0;

        if(handler.checkMaxOutput() <= 1){
            this.leftExtractCycleButton.visible = false;
            this.rightExtractCycleButton.visible = false;
        } else {
            this.leftExtractCycleButton.visible = true;
            this.rightExtractCycleButton.visible = true;
        }

        if(this.outputMode == 0 && handler.checkMaxOutput() > 0) {
            this.extractButton.setTooltip(Tooltip.of(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".forge_output_mode_await")));
        } else {
            setExtractButtonTooltip();
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.leftExtractCycleButton.mouseClicked(mouseX, mouseY, button)) {
            if(outputMode == 1){
                outputMode = handler.checkMaxOutput();
            } else if(outputMode > 1){
                outputMode--;
            }

            setExtractButtonTooltip();
            return true;
        }

        if (this.rightExtractCycleButton.mouseClicked(mouseX, mouseY, button)) {
            if(outputMode == handler.checkMaxOutput()){
                outputMode = 1;
            } else if(outputMode < 5){
                outputMode++;
            }

            setExtractButtonTooltip();
            return true;
        }
        return super.mouseClicked(mouseX, mouseY, button);
    }

    protected void setExtractButtonTooltip() {
        int id = outputMode;
        if(outputMode == 4) id = 3;
        this.extractButton.setTooltip(Tooltip.of(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".forge_output_mode" + id)));
    }

    protected void updateSwitchState() {
        if(!handler.heatingMode()) {
            this.modeSwitchToHeatingButton.visible = false;
            this.modeSwitchToHeatingButton.active = false;
            this.modeSwitchToAlloyButton.visible = true;
            this.modeSwitchToAlloyButton.active = true;
        } else {
            this.modeSwitchToHeatingButton.visible = true;
            this.modeSwitchToHeatingButton.active = true;
            this.modeSwitchToAlloyButton.visible = false;
            this.modeSwitchToAlloyButton.active = false;
        }
    }

    @Override
    protected void drawBackground(GuiGraphics context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, this.x, this.y, 0, 0, this.backgroundWidth, this.backgroundHeight + 6, TEXTURE_SIZE, TEXTURE_SIZE);

        net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, this.x + 26, this.y + 61, 209, 116, 4, 16, TEXTURE_SIZE, TEXTURE_SIZE);

        renderProgressArrow(context, x, y);
        renderLiquidStorage(context, x, y);
    }

    private void renderProgressArrow(GuiGraphics context, int x, int y) {
        if(heatingMode) {
            net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, x + 36, y + 45, 202, 0, COOKING_FIRE_SIZE, COOKING_FIRE_SIZE, TEXTURE_SIZE, TEXTURE_SIZE);
            if(handler.isCooking()) {
                int cookingTime = (int) (handler.getScaledCooking() * COOKING_FIRE_SIZE);
                net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, x + 36, y + COOKING_FIRE_SIZE + 46 - cookingTime, 202, 15 + COOKING_FIRE_SIZE - cookingTime, COOKING_FIRE_SIZE, cookingTime, TEXTURE_SIZE, TEXTURE_SIZE);
            }
        } else {
            net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, x + 33, y + 45, 218, 0, 20, 13, TEXTURE_SIZE, TEXTURE_SIZE);
            if(handler.isCooking()) {
                int cookingTime = (int) (handler.getScaledCooking() * 15);
                net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, x + 33, y + 59 - cookingTime, 218, 15 + 14 - cookingTime, 20, cookingTime, TEXTURE_SIZE, TEXTURE_SIZE);
            }
        }

        if(handler.isCrafting()) {
            net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, x + 90, y + 16, 212, 83, 7, (int) (handler.getScaledProgress() * PROGRESS_ARROW_SIZE), TEXTURE_SIZE, TEXTURE_SIZE);
        }
    }

    private void renderLiquidStorage(GuiGraphics context, int x, int y) {
        int storedLiquid = (int) (handler.getScaledStoredLiquid() * LIQUID_HEIGHT);
        net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, x + 73, y + 77 - storedLiquid, 211, 76 - storedLiquid, 20, storedLiquid, TEXTURE_SIZE, TEXTURE_SIZE);
    }

    private void renderLiquidStorageTooltip(GuiGraphics context, int mouseX, int mouseY) {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        if (mouseX >= x + 73 && mouseX <= x + 93 && mouseY >= y + 52 && mouseY <= y + 77){
            MetalTypes metal = MetalTypes.getValue(handler.getCurrentMetal());
            if(metal != MetalTypes.EMPTY){
                context.drawOrderedTooltip(this.client.font, Lists.transform(
                        List.of(METext.withColor( Component.translatable("tooltip." + MiddleEarth.MOD_ID +".liquid_" + metal.asString().lowercaseVariant()), metal.getColor()),
                                Component.literal(handler.getStoredLiquid() / 144  + " ").append(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".ingots_number")),
                                Component.literal(handler.getStoredLiquid() % 144 / 16  + " ").append(Component.translatable("tooltip." + MiddleEarth.MOD_ID + ".nuggets_number"))
                        ), Component::asOrderedText), mouseX, mouseY);
            }
        }
    }

    @Override
    protected void drawForeground(GuiGraphics context, int mouseX, int mouseY) {
        context.drawText(this.font, this.title, this.titleX, this.titleY, -12566464, false);
        context.drawText(this.font, this.playerInventoryTitle, this.playerInventoryTitleX, this.playerInventoryTitleY + 7, -12566464, false);
    }

    @Override
    public void render(GuiGraphics context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawMouseoverTooltip(context, mouseX, mouseY);
        renderLiquidStorageTooltip(context, mouseX, mouseY);

        int v = switch (outputMode) {
            case 1 -> 111;
            case 2 -> 32;
            case 3 -> 85;
            case 4 -> 137;
            case 5 -> 59;
            default -> -1;
        };
        if(v >= 0) net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, x + EXTRACT_BUTTON_ITEM_X, y + EXTRACT_BUTTON_ITEM_Y, EXTRACT_BUTTON_ITEM_U, v, 18, 24, TEXTURE_SIZE, TEXTURE_SIZE);
        else {
            int u = 0;
            if (handler.checkMaxOutput() > 0) {
                u = 16;
            }
            net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, x + EXTRACT_BUTTON_ITEM_X + 2, y + EXTRACT_BUTTON_ITEM_Y + 4, 204 + u, 30, 14, 16, TEXTURE_SIZE, TEXTURE_SIZE);
            net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, x + 102, y + 57, 178, 21, 22, 15, TEXTURE_SIZE, TEXTURE_SIZE);
        }
    }
}
