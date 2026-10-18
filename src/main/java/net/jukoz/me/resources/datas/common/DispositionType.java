package net.jukoz.me.resources.datas.common;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.jukoz.me.MiddleEarth;

public enum DispositionType {
    GOOD,
    NEUTRAL,
    EVIL;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public MutableText getName() {
        return Text.translatable("disposition.".concat(new Identifier(MiddleEarth.MOD_ID, toString()).toTranslationKey()));
    }
}
