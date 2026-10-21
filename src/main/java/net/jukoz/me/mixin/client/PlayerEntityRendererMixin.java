package net.jukoz.me.mixin.client;

import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;
import net.jukoz.me.compat.client.render.equipment.EquipmentRenderer;
import net.jukoz.me.entity.spider.EnwebbedFeatureRenderer;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.model.PlayerModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

/**
 * R3: 1.20.1 rewrite. The donor version hooked the 1.21.8 render-state pipeline;
 * here we only add the enwebbed overlay feature after the vanilla constructor.
 */
@Mixin(PlayerRenderer.class)
public abstract class PlayerEntityRendererMixin
        extends LivingEntityRenderer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {

    public PlayerEntityRendererMixin(EntityRendererProvider.Context ctx,
                                     PlayerModel<AbstractClientPlayer> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererProvider$Context;Z)V", at = @At("TAIL"))
    private void me$addEnwebbedFeature(EntityRendererProvider.Context ctx, boolean slim, CallbackInfo ci) {
        this.addFeature(new EnwebbedFeatureRenderer<>(this,
                new LoadedEntityModelsCompat(ctx.getModelSet()), EquipmentRenderer.INSTANCE));
    }

    @Inject(method = "scale(Lnet/minecraft/client/network/AbstractClientPlayer;Lnet/minecraft/client/util/math/PoseStack;F)V", at = @At("HEAD"))
    private void me$raceScale(AbstractClientPlayer player, com.mojang.blaze3d.vertex.PoseStack matrices,
                              float tickDelta, CallbackInfo ci) {
        float scale = net.jukoz.me.entity.EntityAttributesME.scale(player);
        float width = (float) net.jukoz.me.entity.EntityAttributesME.value(player, net.jukoz.me.entity.EntityAttributesME.WIDTH_SCALE);
        matrices.scale(scale * width, scale, scale * width);
    }
}
