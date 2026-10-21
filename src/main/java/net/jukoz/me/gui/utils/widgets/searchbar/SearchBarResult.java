package net.jukoz.me.gui.utils.widgets.searchbar;

import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

public class SearchBarResult {
    private MutableComponent text;
    private SearchBarResultType type;
    private ResourceLocation targetIdentifier;
    private ButtonWidget.PressAction action;

    public SearchBarResult(MutableComponent text, ResourceLocation targetIdentifier, SearchBarResultType type, ButtonWidget.PressAction action){
        this.text = text;
        this.targetIdentifier = targetIdentifier;
        this.type = type;
        this.action = action;
    }

    public MutableComponent getText(){
        return text;
    }

    public ResourceLocation getTargetIdentifier(){
        return targetIdentifier;
    }

    public SearchBarResultType getType(){
        return type;
    }

    public ButtonWidget.PressAction getAction(){
        return action;
    }
}
