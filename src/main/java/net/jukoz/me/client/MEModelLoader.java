package net.jukoz.me.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;

/**
 * 1.20.1 stand-in for the donor's model-baker subclass. The loader itself is gone (1.20.1
 * has no {@code ModelBaker}/{@code ItemAsset} pipeline); only the shield sprite identifiers
 * are still referenced by the built-in item renderer.
 */
@OnlyIn(Dist.CLIENT)
public final class MEModelLoader {
    public static final Material KITE_SHIELD_BASE;
    public static final Material KITE_SHIELD_BASE_NO_PATTERN;

    public static final Material HEATER_SHIELD_BASE;
    public static final Material HEATER_SHIELD_BASE_NO_PATTERN;

    public static final Material ROUND_SHIELD_BASE;
    public static final Material ROUND_SHIELD_BASE_NO_PATTERN;

    private MEModelLoader() {
    }

    static {
        KITE_SHIELD_BASE = new Material(Sheets.SHIELD_PATTERNS_ATLAS_TEXTURE, new ResourceLocation(MiddleEarth.MOD_ID, "entity/kite_shield_base"));
        KITE_SHIELD_BASE_NO_PATTERN = new Material(Sheets.SHIELD_PATTERNS_ATLAS_TEXTURE, new ResourceLocation(MiddleEarth.MOD_ID, "entity/kite_shield_base_nopattern"));

        HEATER_SHIELD_BASE = new Material(Sheets.SHIELD_PATTERNS_ATLAS_TEXTURE, new ResourceLocation(MiddleEarth.MOD_ID, "entity/heater_shield_base"));
        HEATER_SHIELD_BASE_NO_PATTERN = new Material(Sheets.SHIELD_PATTERNS_ATLAS_TEXTURE, new ResourceLocation(MiddleEarth.MOD_ID, "entity/heater_shield_base_nopattern"));

        ROUND_SHIELD_BASE = new Material(Sheets.SHIELD_PATTERNS_ATLAS_TEXTURE, new ResourceLocation(MiddleEarth.MOD_ID, "entity/round_shield_base"));
        ROUND_SHIELD_BASE_NO_PATTERN = new Material(Sheets.SHIELD_PATTERNS_ATLAS_TEXTURE, new ResourceLocation(MiddleEarth.MOD_ID, "entity/round_shield_base_nopattern"));
    }
}
