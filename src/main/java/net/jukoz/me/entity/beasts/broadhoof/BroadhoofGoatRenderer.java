package net.jukoz.me.entity.beasts.broadhoof;

import net.jukoz.me.compat.client.render.equipment.EquipmentRenderer;
import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;
import net.jukoz.me.compat.client.render.MEMobEntityRenderer;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.jukoz.me.compat.client.render.equipment.EquipmentModel;
import net.minecraft.client.renderer.entity.layers.HorseMarkingLayer;
import net.jukoz.me.compat.client.render.equipment.SaddleFeatureRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.animal.horse.Variant;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.Util;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.EntityModelLayersME;
import net.jukoz.me.entity.beasts.broadhoof.features.BroadhoofGoatBeadsFeatureRenderer;
import net.jukoz.me.entity.beasts.broadhoof.features.BroadhoofGoatPatternFeatureRenderer;
import net.jukoz.me.entity.beasts.broadhoof.features.BroadhoofGoatSaddleFeatureRenderer;
import java.util.Map;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

public class BroadhoofGoatRenderer extends MEMobEntityRenderer<BroadhoofGoatEntity, BroadhoofGoatEntityRenderState, BroadhoofGoatModel> {
    private static final String PATH = "textures/entities/broadhoof_goat/";
    private static final float SIZE = 1f;

    public BroadhoofGoatRenderer(EntityRendererProvider.Context context) {
        super(context, new BroadhoofGoatModel(context.getPart(EntityModelLayersME.BROADHOOF_GOAT)), 0.8f);
        this.addFeature(new BroadhoofGoatPatternFeatureRenderer(this));
        this.addFeature(new BroadhoofGoatBeadsFeatureRenderer(this));
        this.addFeature(
                new SaddleFeatureRenderer<>(
                        this,
                        EquipmentRenderer.INSTANCE,
                        new BroadhoofGoatModel(context.getPart(EntityModelLayersME.BROADHOOF_GOAT_ARMOR)),
                        EquipmentModel.LayerType.HORSE_BODY,
                        (BroadhoofGoatEntityRenderState s) -> s.armor

                )
        );
        this.addFeature(new BroadhoofGoatSaddleFeatureRenderer(this, new LoadedEntityModelsCompat(context.getModelSet()), EquipmentRenderer.INSTANCE));
    }

    @Override
    public void render(BroadhoofGoatEntityRenderState state, PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i) {
        if(state.baby) {
            matrixStack.scale(SIZE/2, SIZE/2, SIZE/2);
        }
        else {
            matrixStack.scale(SIZE, SIZE, SIZE);
        }

        super.render(state, matrixStack, vertexConsumerProvider, i);
    }

    @Override
    public BroadhoofGoatEntityRenderState createRenderState() {
        return new BroadhoofGoatEntityRenderState();
    }

    private static final Map<BroadhoofGoatColor, ResourceLocation> TEXTURES = Maps.newEnumMap(
            Map.of(
                    BroadhoofGoatColor.WHITE,
                    MiddleEarth.of(PATH + "broadhoof_goat_white.png"),
                    BroadhoofGoatColor.LIGHT_GRAY,
                    MiddleEarth.of(PATH + "broadhoof_goat_light_gray.png"),
                    BroadhoofGoatColor.PALE,
                    MiddleEarth.of(PATH + "broadhoof_goat_pale.png"),
                    BroadhoofGoatColor.RED,
                    MiddleEarth.of(PATH + "broadhoof_goat_red.png"),
                    BroadhoofGoatColor.BROWN,
                    MiddleEarth.of(PATH + "broadhoof_goat_brown.png"),
                    BroadhoofGoatColor.GRAY,
                    MiddleEarth.of(PATH + "broadhoof_goat_gray.png"),
                    BroadhoofGoatColor.BLACK,
                    MiddleEarth.of(PATH + "broadhoof_goat_black.png")
            )
    );

    @Override
    public void updateRenderState(BroadhoofGoatEntity goat, BroadhoofGoatEntityRenderState state, float f) {
        super.updateRenderState(goat, state, f);

        state.color = goat.getGoatColor();
        state.pattern = goat.getPattern();
        state.armor = goat.getBodyArmor().copy();

        state.beads = goat.getGoatBeads();
        state.horns = goat.getHorns();
        state.hair = goat.hasHair();
        state.hasLeftHorn = goat.hasLeftHorn();
        state.hasRightHorn = goat.hasRightHorn();
        state.beardBrushed = goat.hasBrushedBeard();

        state.isSprinting = goat.isSprinting();
        state.isCharging = goat.isCharging();
        state.isTame = goat.isTame();
        state.conrollingPassenger = goat.getControllingPassenger();
        state.saddle = goat.getEquippedStack(EquipmentSlot.CHEST);
        state.armor = goat.getBodyArmor();

        state.chargeAnimationState = goat.chargeAnimationState;
        state.startSittingAnimationState = goat.startSittingAnimationState;
        state.stopSittingAnimationState = goat.stopSittingAnimationState;
        state.sittingAnimationState = goat.sittingAnimationState;
        state.attackAnimationState = goat.attackAnimationState;
        state.idleAnimationState = goat.idleAnimationState;
        state.jumpAnimationState = goat.jumpAnimationState;

    }

    @Override
    public ResourceLocation getTexture(BroadhoofGoatEntityRenderState state) {
        return TEXTURES.get(state.color);
    }
}
