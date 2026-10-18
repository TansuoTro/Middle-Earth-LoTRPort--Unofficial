package net.jukoz.me.mixin.client;

import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;
import net.jukoz.me.compat.client.render.equipment.EquipmentRenderer;
import net.jukoz.me.entity.spider.EnwebbedFeatureRenderer;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * R3: 1.20.1 rewrite. The donor version hooked the 1.21.8 render-state pipeline;
 * here we only add the enwebbed overlay feature after the vanilla constructor.
 */
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin
        extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {

    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx,
                                     PlayerEntityModel<AbstractClientPlayerEntity> model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;Z)V", at = @At("TAIL"))
    private void me$addEnwebbedFeature(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        this.addFeature(new EnwebbedFeatureRenderer<>(this,
                new LoadedEntityModelsCompat(ctx.getModelLoader()), EquipmentRenderer.INSTANCE));
    }
}
