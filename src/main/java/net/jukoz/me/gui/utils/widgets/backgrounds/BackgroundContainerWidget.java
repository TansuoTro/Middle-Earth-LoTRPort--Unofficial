package net.jukoz.me.gui.utils.widgets.backgrounds;

import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.jukoz.me.gui.utils.widgets.ModWidget;
import net.jukoz.me.gui.utils.widgets.UiDirections;
import net.jukoz.me.gui.utils.widgets.backgrounds.types.BackgroundContainerTypes;
import net.minecraft.client.gui.GuiGraphics;
import org.joml.Vector2i;

public class BackgroundContainerWidget extends ModWidget {
    BackgroundContainerTypes type;
    public BackgroundContainerWidget(BackgroundContainerTypes type){
        this.type = type;
    }

    public void draw(GuiGraphics context, int startX, int startY, int sizeX, int sizeY){
        // TODO : Find a fix for looping texture efficiently

        int size = type.size;
        // NORTH WEST
        Vector2i uv = type.getUvForDirection(UiDirections.NORTH_WEST);

        net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, type.textureId, startX, startY, uv.x, uv.y, size, size, 256, 256);

        // NORTH EAST
        uv = type.getUvForDirection(UiDirections.NORTH_EAST);
        net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, type.textureId, startX + sizeX - size, startY, uv.x, uv.y, size, size, 256, 256);

        // SOUTH WEST
        uv = type.getUvForDirection(UiDirections.SOUTH_WEST);
        net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, type.textureId, startX, startY + sizeY - size, uv.x, uv.y, size, size, 256, 256);

        // SOUTH EAST
        uv = type.getUvForDirection(UiDirections.SOUTH_EAST);
        net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, type.textureId, startX + sizeX - size, startY + sizeY - size, uv.x, uv.y, size, size, 256, 256);

        // NORTH/SOUTH
        Vector2i newUv = type.getUvForDirection(UiDirections.NORTH);
        uv = type.getUvForDirection(UiDirections.SOUTH);
        for(int x = startX + size; x < startX + sizeX - (size * 2); x += size) {
            // NORTH
            net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, type.textureId, x, startY, newUv.x, newUv.y, size, size, 256, 256);

            // SOUTH
            net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, type.textureId, x, startY + sizeY - size, uv.x, uv.y, size, size, 256, 256);
        }
        // NORTH
        net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, type.textureId, startX + sizeX - (size * 2), startY, newUv.x, newUv.y, size, size, 256, 256);
        // SOUTH
        net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, type.textureId, startX + sizeX - (size * 2), startY + sizeY - size, uv.x, uv.y, size, size, 256, 256);

        // WEST/EAST
        uv = type.getUvForDirection(UiDirections.WEST);
        newUv = type.getUvForDirection(UiDirections.EAST);
        for(int y = startY + size; y < startY + sizeY - (size * 2); y += size) {
            // WEST
            net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, type.textureId, startX, y, uv.x, uv.y, size, size, 256, 256);
            // EAST
            net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, type.textureId, startX + sizeX - size, y, newUv.x, newUv.y, size, size, 256, 256);
        }
        // WEST
        net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, type.textureId, startX, startY + sizeY - (size * 2), uv.x, uv.y, size, size, 256, 256);
        // EAST
        net.jukoz.me.compat.mcforge.GuiCompat.drawTexture(context, type.textureId, startX + sizeX - size, startY + sizeY - (size * 2), newUv.x, newUv.y, size, size, 256, 256);
    }
}
