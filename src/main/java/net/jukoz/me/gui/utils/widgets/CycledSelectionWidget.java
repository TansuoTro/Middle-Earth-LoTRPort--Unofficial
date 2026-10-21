package net.jukoz.me.gui.utils.widgets;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.CommonColors;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.gui.utils.CycledSelectionButtonType;
import java.util.ArrayList;
import java.util.List;

public class CycledSelectionWidget extends ModWidget{
    private static final ResourceLocation TEXTURE = new ResourceLocation(MiddleEarth.MOD_ID,"textures/gui/widget/cycled_selection_widget.png");
    boolean leftCanBeActive = true;
    boolean rightCanBeActive = true;
    boolean centerCanBeActive = true;
    private final Button buttonLeft;
    private final Button buttonRight;
    private final Button selectionButton;
    private final CycledSelectionButtonType buttonType;
    private boolean shouldDisplay = true;
    static final int ARROW_SIZE_X = 7;
    static final int ARROW_SIZE_Y = 11;
    static final int PANEL_SIZE_X = CycledSelectionButtonType.WIDTH;
    static final int PANEL_SIZE_Y = CycledSelectionButtonType.HEIGHT;
    public static final int TOTAL_WIDTH = ((MARGIN + ARROW_SIZE_X) * 2) + PANEL_SIZE_X;
    public static final int TOTAL_HEIGHT = Math.max(PANEL_SIZE_Y, ARROW_SIZE_Y);
    public MutableComponent text;

    public CycledSelectionWidget(Button.PressAction leftAction, Button.PressAction rightAction, Button.PressAction selectionAction, CycledSelectionButtonType buttonType){
        this.buttonType = buttonType;
        buttonLeft = Button.builder(Component.of("Cycled Selection Left"), leftAction).build();
        net.jukoz.me.compat.client.gui.MEGui.setSize(buttonLeft, ARROW_SIZE_X, ARROW_SIZE_Y);

        buttonRight = Button.builder(Component.of("Cycled Selection Right"), rightAction).build();
        net.jukoz.me.compat.client.gui.MEGui.setSize(buttonRight, ARROW_SIZE_X, ARROW_SIZE_Y);

        selectionButton = Button.builder(Component.of("Cycled Selection"), selectionAction).build();
        net.jukoz.me.compat.client.gui.MEGui.setSize(selectionButton, PANEL_SIZE_X, PANEL_SIZE_Y);

        if(selectionAction == null){
            centerCanBeActive = false;
            selectionButton.active = false;
        }
        if(leftAction == null){
            leftCanBeActive = false;
            buttonLeft.active = false;
        }
        if(rightAction == null){
            rightCanBeActive = false;
            buttonRight.active = false;
        }
    }

    public List<Button> getButtons(){
        ArrayList<Button> listOfButtons = new ArrayList<>();
        listOfButtons.add(buttonLeft);
        listOfButtons.add(selectionButton);
        listOfButtons.add(buttonRight);

        return listOfButtons;
    }

    public void enableArrows(boolean activate){
        this.buttonLeft.active = activate && leftCanBeActive;
        this.buttonRight.active =  activate && rightCanBeActive;
    }

    public void enableVisuals(boolean activate){
        shouldDisplay = activate;
        this.buttonLeft.active = activate && leftCanBeActive;
        this.buttonRight.active = activate && rightCanBeActive;
        this.selectionButton.active = activate && centerCanBeActive;
    }

    public void setText(MutableComponent text){
        this.text = text;
    }

    public int drawAnchored(GuiGraphics context, int anchorX, int startY, boolean isLeftAnchor, Font font){
        int startX = anchorX;
        if(!isLeftAnchor)
            startX -= TOTAL_WIDTH;
        return draw(context, startX, startY, this.text, font);
    }
    public int drawAnchored(GuiGraphics context, int anchorX, int startY, boolean isLeftAnchor, MutableComponent text, Font font){
        int startX = anchorX;
        if(!isLeftAnchor)
            startX -= TOTAL_WIDTH;
        return draw(context, startX, startY, text, font);
    }

    public int drawCentered(GuiGraphics context, int centerX, int startY, MutableComponent text, Font font){
        int startX = centerX - (TOTAL_WIDTH / 2);
        return draw(context, startX, startY, text, font);
    }

    protected int draw(GuiGraphics context, int startX, int startY, MutableComponent text, Font font){
        if(!shouldDisplay){
            return 0;
        }

        int x = startX;
        int y = startY;

        int arrowStartOffsetY = (TOTAL_HEIGHT - ARROW_SIZE_Y) / 2;
        int buttonStartOffsetY = (TOTAL_HEIGHT - PANEL_SIZE_Y) / 2;

        boolean buttonIsHovered = false;

        // Left arrow
        if(buttonLeft.active){
            buttonLeft.setPosition(x, y + arrowStartOffsetY);
            buttonIsHovered = buttonLeft.isFocused() || isMouseOver(ARROW_SIZE_X, ARROW_SIZE_Y, x, y + arrowStartOffsetY);
            net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, x, y + arrowStartOffsetY, 206, buttonIsHovered ? 11 : 0, ARROW_SIZE_X, ARROW_SIZE_Y, 256, 256);

            if(buttonLeft.isFocused() && getFocusEnabled())
                net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, x, y + arrowStartOffsetY, 206, 33, ARROW_SIZE_X, ARROW_SIZE_Y, 256, 256);
        }

        // Center Button
        x += ARROW_SIZE_X + MARGIN;
        selectionButton.setPosition(x, y + buttonStartOffsetY);
        buttonIsHovered = selectionButton.active && (selectionButton.isFocused() || isMouseOver(PANEL_SIZE_X, PANEL_SIZE_Y, x, y + buttonStartOffsetY));
        net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, x, y + buttonStartOffsetY, buttonIsHovered ? buttonType.hoveredUvX : buttonType.uvX, buttonIsHovered ? buttonType.hoveredUvY : buttonType.uvY, PANEL_SIZE_X, PANEL_SIZE_Y, 256, 256);

        if(selectionButton.isFocused() && getFocusEnabled())
            net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, x, y + buttonStartOffsetY, CycledSelectionButtonType.FOCUS_UV_X, CycledSelectionButtonType.FOCUS_UV_Y, PANEL_SIZE_X, PANEL_SIZE_Y, 256, 256);

        if(text == null)
            text = Component.translatable("me.ui.selection.none");
        context.drawText(font, text,
                x + (int)((PANEL_SIZE_X - font.getWidth(text)) / 2f),
                startY + (int) ((PANEL_SIZE_Y / 2f) - (font.fontHeight / 2f)) + 1,
                CommonColors.BLACK, false);

        x += PANEL_SIZE_X + MARGIN;
        // Left arrow
        if(buttonRight.active){
            buttonRight.setPosition(x, y + arrowStartOffsetY);
            buttonIsHovered = buttonRight.isFocused() || isMouseOver(ARROW_SIZE_X, ARROW_SIZE_Y, x, y + arrowStartOffsetY);

            net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, x, y + arrowStartOffsetY, 215, buttonIsHovered ? 11 : 0, ARROW_SIZE_X, ARROW_SIZE_Y, 256, 256);

            if(buttonRight.isFocused() && getFocusEnabled())
                net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, TEXTURE, x, y + arrowStartOffsetY, 215, 33, ARROW_SIZE_X, ARROW_SIZE_Y, 256, 256);
        }

        return PANEL_SIZE_Y;
    }
}
