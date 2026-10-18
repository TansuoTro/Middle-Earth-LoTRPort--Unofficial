package net.jukoz.me.mixin.client;
import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;
import net.jukoz.me.compat.client.render.MEMAgeableMobEntityRenderer;

import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.entity.AgeableMobEntityRenderer;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.jukoz.me.compat.client.render.state.BipedEntityRenderState;
import net.jukoz.me.compat.client.render.state.LivingEntityRenderState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.client.renderer.ArmedEntityRenderStateAccess;
import net.jukoz.me.client.renderer.BipedEntityRenderStateAccess;
import net.jukoz.me.entity.spider.EnwebbedFeatureRenderer;
import net.jukoz.me.statusEffects.ModStatusEffects;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityRenderer.class)
public abstract class BipedEntityRendererMixin<T extends MobEntity, S extends BipedEntityRenderState, M extends BipedEntityModel<S>>
        extends MEMAgeableMobEntityRenderer<T, S, M> {


    public BipedEntityRendererMixin(EntityRendererFactory.Context context, M model, M babyModel, float shadowRadius) {
        super(context, model, babyModel, shadowRadius);
    }

    @Inject(at = @At("TAIL"), method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;Lnet/minecraft/client/render/entity/model/BipedEntityModel;Lnet/minecraft/client/render/entity/model/BipedEntityModel;FLnet/minecraft/client/render/entity/feature/HeadFeatureRenderer$HeadTransformation;)V")
    public <S extends BipedEntityRenderState, M extends BipedEntityModel<S>>
    void BipedEntityRenderer(EntityRendererFactory.Context context, BipedEntityModel model, BipedEntityModel babyModel,
                             float scale, HeadFeatureRenderer.HeadTransformation headTransformation, CallbackInfo ci) {

        this.addFeature(new EnwebbedFeatureRenderer<>(this, new LoadedEntityModelsCompat(context.getModelLoader()), context.getEquipmentRenderer()));
    }


    @Inject(at = @At("TAIL"), method = "updateRenderState(Lnet/minecraft/entity/mob/MobEntity;Lnet/minecraft/client/render/entity/state/BipedEntityRenderState;F)V")
    private <T extends MobEntity, S extends BipedEntityRenderState>
    void positionRightArm(T mobEntity, S bipedEntityRenderState, float f, CallbackInfo ci) {
        ItemStack mainHandStack = mobEntity.getMainHandStack();
        ItemStack offHandStack = mobEntity.getOffHandStack();
        ((ArmedEntityRenderStateAccess)bipedEntityRenderState).setMainHandStack(mainHandStack);
        ((ArmedEntityRenderStateAccess)bipedEntityRenderState).setOffHandStack(offHandStack);
    }

    @Inject(at = @At("TAIL"), method = "updateBipedRenderState")
    private static <T extends LivingEntity, S extends LivingEntityRenderState>
    void updateRenderState(LivingEntity entity, BipedEntityRenderState state, float tickProgress, ItemModelManager itemModelResolver, CallbackInfo ci) {
        Vec3d velocity = entity.getVelocity();
        BipedEntityRenderStateAccess stateAccess = ((BipedEntityRenderStateAccess)state);
        stateAccess.setTickProgress(tickProgress);
        stateAccess.setPreviousVelocity(stateAccess.getVelocity());
        stateAccess.setVelocity(velocity);
    }

    @Override
    public Identifier getTexture(S state) {
        return null;
    }

    @Override
    public S createRenderState() {
        return null;
    }
}
