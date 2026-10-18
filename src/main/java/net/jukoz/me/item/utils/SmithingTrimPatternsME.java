package net.jukoz.me.item.utils;

import net.minecraft.item.trim.ArmorTrimPattern;
import net.jukoz.me.MiddleEarth;
import net.minecraft.registry.*;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.item.Items;

public class SmithingTrimPatternsME {
    public static final RegistryKey<ArmorTrimPattern> SMITHING_PART = of("smithing_part");

    public static void bootstrap(Registerable<ArmorTrimPattern> registry) {
        register(registry, SMITHING_PART);
    }

    public static void register(Registerable<ArmorTrimPattern> registry, RegistryKey<ArmorTrimPattern> key) {
        ArmorTrimPattern armorTrimPattern = new ArmorTrimPattern(key.getValue(), net.minecraft.registry.Registries.ITEM.getEntry(Items.IRON_INGOT), Text.translatable(Util.createTranslationKey("trim_pattern", key.getValue())));
        registry.register(key, armorTrimPattern);
    }

    private static RegistryKey<ArmorTrimPattern> of(String id) {
        return RegistryKey.of(RegistryKeys.TRIM_PATTERN, new Identifier(MiddleEarth.MOD_ID, id));
    }
}
