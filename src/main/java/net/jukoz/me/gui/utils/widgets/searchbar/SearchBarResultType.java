package net.jukoz.me.gui.utils.widgets.searchbar;

import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;

public enum SearchBarResultType
{
    NORMAL(new ResourceLocation(MiddleEarth.MOD_ID, "textures/gui/widget/search_widget.png"), 0,75, 0, 89),
    SUB(new ResourceLocation(MiddleEarth.MOD_ID, "textures/gui/widget/search_widget.png"), 0,103, 0, 117);

    public static final int WIDTH = 93;
    public static final int HEIGHT = 14;

    private ResourceLocation textureId;
    private int uvX;
    private int uvY;
    private int activeUvX;
    private int activeUvY;

    SearchBarResultType(ResourceLocation textureId, int uvX, int uvY, int activeUvX, int activeUvY){
        this.textureId = textureId;
        this.uvX = uvX;
        this.uvY = uvY;
        this.activeUvX = activeUvX;
        this.activeUvY = activeUvY;
    }

    public ResourceLocation getTextureId(){
        return textureId;
    }
    public int getUvX(){
        return uvX;
    }
    public int getUvY(){
        return uvY;
    }
    public int getActiveUvX(){
        return activeUvX;
    }
    public int getActiveUvY(){
        return activeUvY;
    }
}
