package net.jukoz.me.mixin.client;
import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.*;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.jukoz.me.compat.client.render.state.BipedEntityRenderState;
import net.jukoz.me.compat.client.render.state.PlayerEntityRenderState;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.renderer.ArmedEntityRenderStateAccess;
import net.jukoz.me.entity.spider.EnwebbedFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRenderer<AbstractClientPlayerEntity, PlayerEntityRenderState, PlayerEntityModel> {

    public PlayerEntityRendererMixin(EntityRendererFactory.Context ctx, PlayerEntityModel model, float shadowRadius) {
        super(ctx, model, shadowRadius);
    }

    @Inject(at = @At("TAIL"), method = "<init>")
    public <S extends BipedEntityRenderState, M extends BipedEntityModel<S>>
    void PlayerEntityRenderer(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        this.addFeature(new EnwebbedFeatureRenderer<>(this, new LoadedEntityModelsCompat(ctx.getModelLoader()), ctx.getEquipmentRenderer()));
    }
}
