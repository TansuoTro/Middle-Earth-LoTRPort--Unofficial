package net.jukoz.me.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.jukoz.me.world.biomes.MEBiomeFogData;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.Camera;
import com.mojang.blaze3d.shaders.FogShape;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.Optional;

@OnlyIn(Dist.CLIENT)
@Mixin(FogRenderer.class)
public class BackgroundRendererMixin {
    private static final float TICK_SPEED = 0.001f;
    private static float fogStartMultiplier = 0.25f;
    private static float fogEndMultiplier = 1;

    @Inject(method = "applyFog", at = @At("TAIL"))
    private static void applyFog(Camera camera, FogRenderer.FogMode fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        Entity entity = camera.getEntity();

        if (entity instanceof LocalPlayer clientPlayerEntity) {
            if(
                    !clientPlayerEntity.hasStatusEffect(MobEffects.DARKNESS) &&
                    !clientPlayerEntity.hasStatusEffect(MobEffects.BLINDNESS) &&
                    !clientPlayerEntity.isUnderWater() &&
                    ModDimensions.isInMiddleEarth(clientPlayerEntity.getWorld()))
            {
                Optional<ResourceKey<Biome>> biomeRegistry = clientPlayerEntity.getWorld().getBiome(clientPlayerEntity.getBlockPos()).getKey();
                if(biomeRegistry.isPresent() && MEBiomeFogData.DATA.containsKey(biomeRegistry.get())){
                    MEBiomeFogData fogData = MEBiomeFogData.DATA.get(biomeRegistry.get());
                    
                    if (fogStartMultiplier < fogData.fogStart) {
                        fogStartMultiplier = Math.min(fogStartMultiplier + (tickDelta * TICK_SPEED), fogData.fogStart);
                    } else if (fogStartMultiplier > fogData.fogStart) {
                        fogStartMultiplier = Math.max(fogStartMultiplier - (tickDelta * TICK_SPEED), fogData.fogStart);
                    }

                    if (fogEndMultiplier < fogData.fogEnd) {
                        fogEndMultiplier = Math.min(fogEndMultiplier + (tickDelta * TICK_SPEED), fogData.fogEnd);
                    } else if (fogEndMultiplier > fogData.fogEnd) {
                        fogEndMultiplier = Math.max(fogEndMultiplier - (tickDelta * TICK_SPEED), fogData.fogEnd);
                    }
                } else {
                    fogEndMultiplier = Math.min(fogEndMultiplier + (tickDelta * TICK_SPEED), 1);
                    fogStartMultiplier = Math.min(fogStartMultiplier + (tickDelta * TICK_SPEED), 1);
                }

                float f = Mth.clamp(viewDistance / 10.0F, 4.0F, 64.0F);
                float fogStart = (viewDistance - f) * fogStartMultiplier;
                float fogEnd = viewDistance * fogEndMultiplier;

                RenderSystem.setShaderFogStart(fogStart);
                RenderSystem.setShaderFogEnd(fogEnd);
                RenderSystem.setShaderFogShape(FogShape.SPHERE);
            }
        }
    }
}
