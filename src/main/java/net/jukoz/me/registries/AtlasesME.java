package net.jukoz.me.registries;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.SpriteLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.jukoz.me.MiddleEarth;

/**
 * Middle-earth mod atlases<br>
 */
public class AtlasesME {
    public static final Identifier CHARACTER_TEXTURES = MiddleEarth.of("character_textures");
    public static final Identifier SPRITES = MiddleEarth.of("sprites");

    public static final Identifier SKIN_PREFIX = MiddleEarth.ofPath("character", "skins");
    public static final Identifier HAIR_PREFIX = MiddleEarth.ofPath("character", "hairs");
    public static final Identifier EYE_PREFIX = MiddleEarth.ofPath("character", "eyes");
    public static final Identifier CLOTHES_BASE_PREFIX = MiddleEarth.ofPath("character", "clothes", "base");
    public static final Identifier CLOTHES_OVER_PREFIX = MiddleEarth.ofPath("character", "clothes", "over");
    public static final Identifier CLOTHES_EXTRA_PREFIX = MiddleEarth.ofPath("character", "clothes", "extra");

    public static Identifier getAtlasPath(Identifier atlasIdentifier) {
        return MiddleEarth.ofPath("textures", "atlas", String.format("%s.png", atlasIdentifier.getPath()));
    }

    @Environment(EnvType.CLIENT)
    public static SpriteAtlasTexture getAtlasFromPath(Identifier atlasPath){
        if (atlasPath.equals(getAtlasPath(CHARACTER_TEXTURES))) {
            return net.jukoz.me.client.CharacterAtlasME.getAtlas();
        }
        if (atlasPath.equals(getAtlasPath(SPRITES))) {
            return net.jukoz.me.client.SpritesAtlasME.getAtlas();
        }
        MinecraftClient client = MinecraftClient.getInstance();
        return client.getBakedModelManager().getAtlas(atlasPath);
    }

    /**
     * Stitches and uploads a hand-created atlas.
     *
     * <p>1.20.1's {@code SpriteAtlasTexture#load(ResourceManager)} is an empty method - it only
     * exists for the (removed) self-loading path. Calling it leaves the sprite map empty, so
     * every {@code getSprite} returns null and the model silently draws nothing. Vanilla stitches
     * through {@code SpriteLoader} and then hands the result to {@code upload}.
     */
    @Environment(EnvType.CLIENT)
    public static void stitchAndUpload(SpriteAtlasTexture atlas, MinecraftClient client, String name) {
        try {
            SpriteLoader loader = SpriteLoader.fromAtlas(atlas);
            SpriteLoader.StitchResult result = loader
                    .load(client.getResourceManager(), atlas.getId(), 0, Util.getMainWorkerExecutor())
                    .join();
            atlas.upload(result);
            atlas.applyTextureFilter(result);
        } catch (Exception e) {
            MiddleEarth.LOGGER.logError("Failed to load %s".formatted(name), e);
        }
    }

}
