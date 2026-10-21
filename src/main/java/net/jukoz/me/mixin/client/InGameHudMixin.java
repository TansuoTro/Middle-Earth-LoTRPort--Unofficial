package net.jukoz.me.mixin.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.statusEffects.ModStatusEffects;
import net.jukoz.me.utils.HallucinationData;
import net.jukoz.me.utils.IEntityDataSaver;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.world.entity.player.Player;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Unique
    private static final ResourceLocation HALLUCINATION_OUTLINE = new ResourceLocation(MiddleEarth.MOD_ID, "textures/misc/hallucination_outline.png");

    @Shadow @Final private MinecraftClient client;


    @Shadow protected abstract void renderOverlay(DrawContext context, ResourceLocation texture, float opacity);

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;getLastFrameDuration()F", shift = At.Shift.AFTER))
    public void injected(DrawContext context, float tickDelta, CallbackInfo ci) {
        Player player = MinecraftClient.getInstance().player;
        assert player != null;

        if(player.hasStatusEffect(ModStatusEffects.HALLUCINATION)) {
            float intensity = (float) HallucinationData.readHallucination((IEntityDataSaver) player) / 100f;
            this.renderOverlay(context, HALLUCINATION_OUTLINE, intensity);

        }
    }
}



