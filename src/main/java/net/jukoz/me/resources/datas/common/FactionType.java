package net.jukoz.me.resources.datas.common;

import net.jukoz.me.MiddleEarth;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public enum FactionType {
    FACTION,
    SUBFACTION;

    @Override
    public String toString() {
        return super.toString().lowercaseVariant();
    }

    public MutableComponent getName() {
        return Component.translatable("faction_type.".concat(new ResourceLocation(MiddleEarth.MOD_ID, toString()).toLanguageKey()));
    }
}
