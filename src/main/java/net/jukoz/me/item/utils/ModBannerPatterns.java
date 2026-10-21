package net.jukoz.me.item.utils;

import net.jukoz.me.MiddleEarth;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

public class ModBannerPatterns {
    public static final ResourceKey<BannerPattern> GONDOR_BANNER_PATTERN = key("gondor");
    public static final ResourceKey<BannerPattern> ROHAN_BANNER_PATTERN = key("rohan");
    public static final ResourceKey<BannerPattern> LONGBEARD_BANNER_PATTERN = key("longbeard");
    public static final ResourceKey<BannerPattern> LOTHLORIEN_BANNER_PATTERN = key("lothlorien");
    public static final ResourceKey<BannerPattern> MORDOR_GREAT_EYE_BANNER_PATTERN = key("mordor_great_eye");
    public static final ResourceKey<BannerPattern> MORDOR_EYE_BANNER_PATTERN = key("mordor_eye");
    public static final ResourceKey<BannerPattern> MISTY_ORCS_EYE_BANNER_PATTERN = key("misty_orcs_eye");
    public static final ResourceKey<BannerPattern> MISTY_ORCS_PEAKS_BANNER_PATTERN = key("misty_orcs_peaks");

    private static ResourceKey<BannerPattern> key(String id) {
        return ResourceKey.of(Registries.BANNER_PATTERN, new ResourceLocation(id));
    }

    public static void register(Registry<BannerPattern> registry) {
        Registry.register(registry, GONDOR_BANNER_PATTERN, new BannerPattern("gon"));
        Registry.register(registry, ROHAN_BANNER_PATTERN, new BannerPattern("roh"));
        Registry.register(registry, LONGBEARD_BANNER_PATTERN, new BannerPattern("longb"));
        Registry.register(registry, LOTHLORIEN_BANNER_PATTERN, new BannerPattern("loth"));
        Registry.register(registry, MORDOR_GREAT_EYE_BANNER_PATTERN, new BannerPattern("moreye"));
        Registry.register(registry, MORDOR_EYE_BANNER_PATTERN, new BannerPattern("morgreye"));
        Registry.register(registry, MISTY_ORCS_EYE_BANNER_PATTERN, new BannerPattern("misteye"));
        Registry.register(registry, MISTY_ORCS_PEAKS_BANNER_PATTERN, new BannerPattern("mistpeaks"));
    }
}
