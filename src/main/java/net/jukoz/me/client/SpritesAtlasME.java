package net.jukoz.me.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.util.Identifier;
import net.jukoz.me.registries.AtlasesME;

/**
 * Custom sprite atlas for projectile animations (smoke rings). Same approach as CharacterAtlasME:
 * a directly-created SpriteAtlasTexture stitched by hand from assets/me/atlases/sprites.json.
 */
@Environment(EnvType.CLIENT)
public class SpritesAtlasME {
    private static SpriteAtlasTexture atlas;

    public static void init() {
        if (atlas == null) {
            MinecraftClient client = MinecraftClient.getInstance();
            Identifier location = AtlasesME.getAtlasPath(AtlasesME.SPRITES);
            atlas = new SpriteAtlasTexture(location);
            client.getTextureManager().registerTexture(location, atlas);
            // Registered after the initial resource reload (CLIENT_STARTED), so stitch it here
            // from assets/me/atlases/sprites.json.
            AtlasesME.stitchAndUpload(atlas, client, "sprites atlas");
        }
    }

    public static SpriteAtlasTexture getAtlas() {
        return atlas;
    }

    public static Sprite getSprite(Identifier id) {
        return atlas.getSprite(id);
    }
}
