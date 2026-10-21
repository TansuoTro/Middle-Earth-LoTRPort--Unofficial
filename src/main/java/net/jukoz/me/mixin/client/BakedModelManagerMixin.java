package net.jukoz.me.mixin.client;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.resources.model.BakedModelManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.ModTexturedRenderLayers;
import net.jukoz.me.registries.AtlasesME;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.resources.model.ModelManager;

@Mixin(ModelManager.class)
public class BakedModelManagerMixin{

    @Mutable
    @Shadow @Final
    private static Map<ResourceLocation, ResourceLocation> LAYERS_TO_LOADERS;

    // HEAD of a constructor lands before the super() call, and Mixin requires those handlers to
    // be static - which is what we want anyway, since LAYERS_TO_LOADERS is a static field.
    @Inject(method = "<init>", at=@At("HEAD"))
    private static void addNewAtlas(TextureManager textureManager, BlockColors colorMap, int mipmap, CallbackInfo ci)
    {
        HashMap<ResourceLocation, ResourceLocation> map = new HashMap<>(LAYERS_TO_LOADERS);
        map.put(ModTexturedRenderLayers.CHARACTER_ATLAS_TEXTURES, AtlasesME.CHARACTER_TEXTURES);

        /*
         * Load custom sprites' atlas. Used for direct render animations.
         */
        map.put(MiddleEarth.of("sprites"), MiddleEarth.of("sprites"));

        LAYERS_TO_LOADERS = map;
    }
}
