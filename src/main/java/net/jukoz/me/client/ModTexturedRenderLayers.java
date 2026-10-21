package net.jukoz.me.client;

import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.registries.AtlasesME;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.RenderType;

public class ModTexturedRenderLayers extends Sheets {
    public static final Material HEATER_SHIELD_BASE;
    public static final Material KITE_SHIELD_BASE;
    public static final Material ROUND_SHIELD_BASE;

    private static final Map<ResourceLocation, Material> HEATER_SHIELD_PATTERN_TEXTURES;
    private static final Map<ResourceLocation, Material> KITE_SHIELD_PATTERN_TEXTURES;
    private static final Map<ResourceLocation, Material> ROUND_SHIELD_PATTERN_TEXTURES;

    public static final ResourceLocation CHARACTER_ATLAS_TEXTURES = AtlasesME.getAtlasPath(AtlasesME.CHARACTER_TEXTURES);
    public static final RenderType CHARACTER_TEXTURES_RENDER_LAYER;
    public static final RenderType CHARACTER_TEXTURES_EMISSIVE_RENDER_LAYER;

    static {
        CHARACTER_TEXTURES_RENDER_LAYER = RenderType.entityCutoutNoCull(CHARACTER_ATLAS_TEXTURES);
        CHARACTER_TEXTURES_EMISSIVE_RENDER_LAYER = RenderType.getEntityTranslucentEmissive(CHARACTER_ATLAS_TEXTURES);

        HEATER_SHIELD_BASE = new Material(SHIELD_PATTERNS_ATLAS_TEXTURE, MiddleEarth.ofVanillaPath("entity", "heater_shield", "base" ));
        KITE_SHIELD_BASE = new Material(SHIELD_PATTERNS_ATLAS_TEXTURE, MiddleEarth.ofVanillaPath("entity", "kite_shield", "base" ));
        ROUND_SHIELD_BASE = new Material(SHIELD_PATTERNS_ATLAS_TEXTURE, MiddleEarth.ofVanillaPath("entity", "round_shield", "base" ));

        HEATER_SHIELD_PATTERN_TEXTURES = new HashMap<>();
        KITE_SHIELD_PATTERN_TEXTURES = new HashMap<>();
        ROUND_SHIELD_PATTERN_TEXTURES = new HashMap<>();
    }

    public static RenderType getCharacterTexturesRenderLayer() {
        return CHARACTER_TEXTURES_RENDER_LAYER;
    }

    public static RenderType getCharacterTexturesEmissiveRenderLayer() {
        return CHARACTER_TEXTURES_EMISSIVE_RENDER_LAYER;
    }

    public static Material getHeaterShieldPatternTextureId(Holder<BannerPattern> pattern) {
        return HEATER_SHIELD_PATTERN_TEXTURES.computeIfAbsent(pattern.getKey().orElseThrow().getValue(), (id) -> {
            ResourceLocation identifier = id.withPrefixedPath("entity/heater_shield/");
            return new Material(SHIELD_PATTERNS_ATLAS_TEXTURE, identifier);
        });
    }

    public static Material getKiteShieldPatternTextureId(Holder<BannerPattern> pattern) {
        return KITE_SHIELD_PATTERN_TEXTURES.computeIfAbsent(pattern.getKey().orElseThrow().getValue(), (id) -> {
            ResourceLocation identifier = id.withPrefixedPath("entity/kite_shield/");
            return new Material(SHIELD_PATTERNS_ATLAS_TEXTURE, identifier);
        });
    }

    public static Material getRoundShieldPatternTextureId(Holder<BannerPattern> pattern) {
        return ROUND_SHIELD_PATTERN_TEXTURES.computeIfAbsent(pattern.getKey().orElseThrow().getValue(), (id) -> {
            ResourceLocation identifier = id.withPrefixedPath("entity/round_shield/");
            return new Material(SHIELD_PATTERNS_ATLAS_TEXTURE, identifier);
        });
    }
}
