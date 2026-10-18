package net.jukoz.me.entity.beasts.great_horn;
import net.jukoz.me.compat.client.render.equipment.EquipmentRenderer;
import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;
import net.jukoz.me.compat.client.render.MEAnimationStates;
import net.jukoz.me.compat.client.render.MEMAgeableMobEntityRenderer;

import com.google.common.collect.Maps;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.EntityModelLayersME;
import net.jukoz.me.entity.beasts.great_horn.features.GreatHornArmorFeatureRenderer;
import net.jukoz.me.entity.beasts.great_horn.features.GreatHornNoseFeatureRenderer;
import net.jukoz.me.entity.beasts.great_horn.features.GreatHornSaddleFeatureRenderer;

import java.util.Map;

public class GreatHornRenderer extends MEMAgeableMobEntityRenderer<GreatHornEntity, GreatHornEntityRenderState, GreatHornModel> {
    private static final String PATH = "textures/entities/great_horn/";
    private static final float SIZE = 1f;

    public GreatHornRenderer(EntityRendererFactory.Context context) {
        super(context, new GreatHornModel(context.getPart(EntityModelLayersME.GREAT_HORN)),
                new GreatHornModel(context.getPart(EntityModelLayersME.GREAT_HORN_BABY)), 0.95f);
        this.addFeature(new GreatHornSaddleFeatureRenderer(this,  new LoadedEntityModelsCompat(context.getModelLoader()), EquipmentRenderer.INSTANCE));
        this.addFeature(new GreatHornArmorFeatureRenderer(this,  new LoadedEntityModelsCompat(context.getModelLoader()), EquipmentRenderer.INSTANCE));
        this.addFeature(new GreatHornNoseFeatureRenderer(this,  new LoadedEntityModelsCompat(context.getModelLoader())));
    }

    protected float getShadowRadius(GreatHornEntityRenderState greatHornEntityRenderState) {
        float f = super.getShadowRadius(greatHornEntityRenderState);
        return greatHornEntityRenderState.baby ? f * 0.8F : f;
    }

    @Override
    public GreatHornEntityRenderState createRenderState() {
        return new GreatHornEntityRenderState();
    }

    public static final Map<GreatHornVariantDep, Identifier> LOCATION_BY_VARIANT =
            Util.make(Maps.newEnumMap(GreatHornVariantDep.class), (map) -> {
                map.put(GreatHornVariantDep.BROWN,
                        new Identifier(MiddleEarth.MOD_ID, PATH + "brown_great_horn.png"));
                map.put(GreatHornVariantDep.TEMPERATE,
                        new Identifier(MiddleEarth.MOD_ID, PATH + "temperate_great_horn.png"));
                map.put(GreatHornVariantDep.COLD,
                        new Identifier(MiddleEarth.MOD_ID, PATH + "cold_great_horn.png"));
                map.put(GreatHornVariantDep.WARM,
                        new Identifier(MiddleEarth.MOD_ID, PATH + "warm_great_horn.png"));
                map.put(GreatHornVariantDep.WHITE,
                        new Identifier(MiddleEarth.MOD_ID, PATH + "white_great_horn.png"));

            });


    @Override
    public Identifier getTexture(GreatHornEntityRenderState state) {
        return state.greatHornVariant.assetInfo().id().texturePath();
    }

    @Override
    public void updateRenderState(GreatHornEntity greatHornEntity, GreatHornEntityRenderState state, float f) {
        super.updateRenderState(greatHornEntity, state, f);

        MEAnimationStates.copyFrom(state.idleAnimationState, greatHornEntity.idleAnimationState);
        MEAnimationStates.copyFrom(state.earWiggleAnimationState, greatHornEntity.earWigglingAnimationState);
        MEAnimationStates.copyFrom(state.gallopAnimationState, greatHornEntity.gallopAnimationState);
        MEAnimationStates.copyFrom(state.bowAnimationState, greatHornEntity.bowAnimationState);
        MEAnimationStates.copyFrom(state.attackAnimationState, greatHornEntity.attackAnimationState);
        state.saddle = greatHornEntity.getEquippedStack(EquipmentSlot.CHEST);
        state.armor = greatHornEntity.getEquippedStack(EquipmentSlot.CHEST);
        state.blueSaddle = greatHornEntity.hasBlueSaddle();
        state.hasRider = greatHornEntity.hasPlayerRider();
        state.greatHornVariant = greatHornEntity.getVariant();
    }
}
