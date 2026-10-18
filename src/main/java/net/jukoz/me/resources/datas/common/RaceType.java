package net.jukoz.me.resources.datas.common;

import net.jukoz.me.MiddleEarth;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

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

    public MutableText getName() {
        return Text.translatable("race_type.".concat(new Identifier(MiddleEarth.MOD_ID, toString()).toTranslationKey()));
    }
}
