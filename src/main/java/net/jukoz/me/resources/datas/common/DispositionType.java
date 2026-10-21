package net.jukoz.me.resources.datas.common;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;

public enum DispositionType {
    GOOD,
    NEUTRAL,
    EVIL;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public MutableComponent getName() {
        return Component.translatable("disposition.".concat(new ResourceLocation(MiddleEarth.MOD_ID, toString()).toTranslationKey()));
    }
}
