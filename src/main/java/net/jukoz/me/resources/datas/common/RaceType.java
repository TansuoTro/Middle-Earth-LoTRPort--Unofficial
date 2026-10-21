package net.jukoz.me.resources.datas.common;

import net.jukoz.me.MiddleEarth;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public enum RaceType {
    NONE,
    HUMAN,
    HOBBIT,
    DWARF,
    ELF,
    ORC,
    URUK,
    GOBLIN,
    SNAGA,
    BEAST,
    OTHER;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public MutableComponent getName() {
        return Component.translatable("race_type.".concat(new ResourceLocation(MiddleEarth.MOD_ID, toString()).toTranslationKey()));
    }
}
