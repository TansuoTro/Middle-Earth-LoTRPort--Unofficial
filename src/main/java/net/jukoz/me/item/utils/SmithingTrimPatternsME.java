package net.jukoz.me.item.utils;

import net.minecraft.world.item.armortrim.ArmorTrimPattern;
import net.jukoz.me.MiddleEarth;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.minecraft.world.item.Items;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.armortrim.TrimPattern;

public class SmithingTrimPatternsME {
    public static final ResourceKey<TrimPattern> SMITHING_PART = of("smithing_part");

    public static void bootstrap(BootstapContext<TrimPattern> registry) {
        register(registry, SMITHING_PART);
    }

    public static void register(BootstapContext<TrimPattern> registry, ResourceKey<TrimPattern> key) {
        TrimPattern armorTrimPattern = new TrimPattern(key.getValue(), net.minecraft.core.registries.Registries.ITEM.getEntry(Items.IRON_INGOT), Component.translatable(Util.createTranslationKey("trim_pattern", key.getValue())));
        registry.register(key, armorTrimPattern);
    }

    private static ResourceKey<TrimPattern> of(String id) {
        return ResourceKey.of(Registries.TRIM_PATTERN, new ResourceLocation(MiddleEarth.MOD_ID, id));
    }
}
