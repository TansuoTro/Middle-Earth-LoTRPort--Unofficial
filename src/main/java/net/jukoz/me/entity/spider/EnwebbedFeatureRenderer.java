package net.jukoz.me.entity.spider;

import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.jukoz.me.compat.client.render.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Identifier;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.EntityModelLayersME;
import net.jukoz.me.statusEffects.ModStatusEffects;

/**
 * R3: rewritten as a plain 1.20.1 entity-typed feature renderer (the donor version
 * was state-typed and only worked through the deleted 1.21.8 state mixins).
 */
@Environment(EnvType.CLIENT)
public class EnwebbedFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private static final Identifier TEXTURE = new Identifier(MiddleEarth.MOD_ID, "textures/entities/spiders/enwebbed.png");

    private final EnwebbedModel model;

    public EnwebbedFeatureRenderer(FeatureRendererContext<T, M> context, LoadedEntityModelsCompat loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new EnwebbedModel(loader.getModelPart(EntityModelLayersME.ENWEBBED));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity,
                       float limbAngle, float limbDistance, float tickDelta, float animationProgress,
                       float headYaw, float headPitch) {
        StatusEffectInstance effect = entity.getStatusEffect(ModStatusEffects.RESTRAINED);
        if (effect == null || effect.getDuration() <= 0) {
            return;
        }
        this.model.setAngles(entity, limbAngle, limbDistance, animationProgress, headYaw, headPitch);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE));
        this.model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
    }
}
