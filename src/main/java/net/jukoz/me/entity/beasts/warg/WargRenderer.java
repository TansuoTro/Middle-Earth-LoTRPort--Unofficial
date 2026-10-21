package net.jukoz.me.entity.beasts.warg;

import net.jukoz.me.compat.client.render.equipment.EquipmentRenderer;
import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;
import net.jukoz.me.compat.client.render.MEMobEntityRenderer;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.MultiBufferSource;
import net.jukoz.me.compat.client.render.equipment.EquipmentModel;
import net.jukoz.me.compat.client.render.equipment.SaddleFeatureRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.warg.features.*;
import net.jukoz.me.entity.EntityModelLayersME;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import java.util.Map;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class WargRenderer extends MEMobEntityRenderer<WargEntity, WargEntityRenderState, WargModel> {
    private static final String PATH = "textures/entities/warg/";
    private static final float SIZE = 1f;
    private static final int LIGHT_LEVEL_EMISSIVE_EYES = 8;

    public WargRenderer(EntityRendererProvider.Context context) {
        super(context, new WargModel(context.getPart(EntityModelLayersME.WARG)), 0.8f);
        this.addFeature(new WargEyesFeatureRenderer(this));
        this.addFeature( // Armor Feature
                new SaddleFeatureRenderer<>(
                        this,
                        EquipmentRenderer.INSTANCE,
                        new WargModel(context.getPart(EntityModelLayersME.WARG_ARMOR)),
                        EquipmentModel.LayerType.HORSE_BODY,
                        (WargEntityRenderState s) -> s.armor
                )
        );
        this.addFeature(new WargArmorSpineFeatureRenderer(this, new LoadedEntityModelsCompat(context.getModelSet()), EquipmentRenderer.INSTANCE));
        this.addFeature(new WargArmorSideSkullsFeatureRenderer(this, new LoadedEntityModelsCompat(context.getModelSet()), EquipmentRenderer.INSTANCE));
        this.addFeature(new WargArmorFrontSkullFeatureRenderer(this, new LoadedEntityModelsCompat(context.getModelSet()), EquipmentRenderer.INSTANCE));
        this.addFeature(new WargArmorBackSkullFeatureRenderer(this, new LoadedEntityModelsCompat(context.getModelSet()), EquipmentRenderer.INSTANCE));
        this.addFeature(new WargSaddleFeatureRenderer(this, new LoadedEntityModelsCompat(context.getModelSet()), EquipmentRenderer.INSTANCE));
    }

    @Override
    public void render(WargEntityRenderState state, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        if(state.baby) {
            matrixStack.scale(SIZE/2, SIZE/2, SIZE/2);
        }
        else {
            matrixStack.scale(SIZE, SIZE, SIZE);
        }

        super.render(state, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public WargEntityRenderState createRenderState() {
        return new WargEntityRenderState();
    }

    public static final Map<WargVariant, ResourceLocation> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(WargVariant.class), (map) -> {
                map.put(WargVariant.BROWN,
                        new ResourceLocation(MiddleEarth.MOD_ID, PATH + "warg_brown.png"));
                map.put(WargVariant.BLACK,
                        new ResourceLocation(MiddleEarth.MOD_ID, PATH + "warg_black.png"));
                map.put(WargVariant.GRAY,
                        new ResourceLocation(MiddleEarth.MOD_ID, PATH + "warg_gray.png"));
                map.put(WargVariant.LIGHT_GRAY,
                        new ResourceLocation(MiddleEarth.MOD_ID, PATH + "warg_light_gray.png"));
                map.put(WargVariant.SNOW,
                        new ResourceLocation(MiddleEarth.MOD_ID, PATH + "warg_snow.png"));
                map.put(WargVariant.MOTTLED,
                        new ResourceLocation(MiddleEarth.MOD_ID, PATH + "warg_mottled.png"));
                map.put(WargVariant.TAN,
                        new ResourceLocation(MiddleEarth.MOD_ID, PATH + "warg_tan.png"));
            });

    @Override
    public ResourceLocation getTexture(WargEntityRenderState state) {
        return LOCATION_BY_VARIANT.get(state.variant);
    }

    @Override
    public void updateRenderState(WargEntity warg, WargEntityRenderState state, float f) {
        super.updateRenderState(warg, state, f);

        state.variant = warg.getVariant();
        state.eyeVariant = warg.getEyeVariant();

        state.isSprinting = warg.isSprinting();
        state.isRunning = warg.isRunning();
        state.isCharging = warg.isCharging();
        state.isTame = warg.isTame();
        state.conrollingPassenger = warg.getControllingPassenger();
        state.saddle = warg.getEquippedStack(EquipmentSlot.CHEST);
        state.armor = warg.getBodyArmor();
        state.haveEmissiveEyes = warg.getWorld().isNight() || warg.getWorld().getLightLevel(warg.getBlockPos()) <= LIGHT_LEVEL_EMISSIVE_EYES;

        state.chargeAnimationState = warg.chargeAnimationState;
        state.startSittingAnimationState = warg.startSittingAnimationState;
        state.stopSittingAnimationState = warg.stopSittingAnimationState;
        state.sittingAnimationState = warg.sittingAnimationState;
        state.attackAnimationState = warg.attackAnimationState;
        state.idleAnimationState = warg.idleAnimationState;
    }
}
