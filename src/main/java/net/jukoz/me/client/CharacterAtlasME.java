package net.jukoz.me.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.util.Identifier;
import net.jukoz.me.registries.AtlasesME;

/**
 * Custom character-texture atlas. Instead of relying on Fabric's ModelManager.LAYERS_TO_LOADERS
 * (which only exists on Fabric), we create a SpriteAtlasTexture directly and register it with the
 * TextureManager and stitch it by hand from the atlas-info JSON at
 * assets/me/atlases/character_textures.json, so this works on both Fabric and Forge (Connector).
 */
@Environment(EnvType.CLIENT)
public class CharacterAtlasME {
    private static SpriteAtlasTexture atlas;

    public static void init() {
        if (atlas == null) {
            MinecraftClient client = MinecraftClient.getInstance();
            Identifier location = AtlasesME.getAtlasPath(AtlasesME.CHARACTER_TEXTURES);
            atlas = new SpriteAtlasTexture(location);
            client.getTextureManager().registerTexture(location, atlas);
            // Registered after the initial resource reload (CLIENT_STARTED), so stitch it here
            // from assets/me/atlases/character_textures.json.
            AtlasesME.stitchAndUpload(atlas, client, "character atlas");
        }
    }

    public static SpriteAtlasTexture getAtlas() {
        return atlas;
    }

    public static Sprite getSprite(Identifier id) {
        return atlas.getSprite(id);
    }
}
