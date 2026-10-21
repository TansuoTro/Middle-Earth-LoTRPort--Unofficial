package net.jukoz.me.item.utils;

import net.minecraft.world.item.armortrim.ArmorTrimPattern;
import net.jukoz.me.MiddleEarth;
import net.minecraft.registry.*;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.minecraft.world.item.Items;

public class SmithingTrimPatternsME {
    public static final RegistryKey<ArmorTrimPattern> SMITHING_PART = of("smithing_part");

    public static void bootstrap(Registerable<ArmorTrimPattern> registry) {
        register(registry, SMITHING_PART);
    }

    public static void register(Registerable<ArmorTrimPattern> registry, RegistryKey<ArmorTrimPattern> key) {
        ArmorTrimPattern armorTrimPattern = new ArmorTrimPattern(key.getValue(), net.minecraft.core.registries.Registries.ITEM.getEntry(Items.IRON_INGOT), Component.translatable(Util.createTranslationKey("trim_pattern", key.getValue())));
        registry.register(key, armorTrimPattern);
    }

    private static RegistryKey<ArmorTrimPattern> of(String id) {
        return RegistryKey.of(RegistryKeys.TRIM_PATTERN, new ResourceLocation(MiddleEarth.MOD_ID, id));
    }
}
