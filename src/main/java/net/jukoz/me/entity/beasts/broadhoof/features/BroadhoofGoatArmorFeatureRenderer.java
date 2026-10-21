package net.jukoz.me.entity.beasts.broadhoof.features;

import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatEntity;
import net.jukoz.me.compat.client.render.MEFeatureRenderer;
import net.jukoz.me.compat.client.render.equipment.EquipmentModel;
import net.jukoz.me.item.MEComponents;
import net.jukoz.me.compat.client.render.equipment.EquipmentRenderer;
import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.jukoz.me.entity.EntityModelLayersME;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatEntityRenderState;
import net.jukoz.me.entity.beasts.broadhoof.BroadhoofGoatModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemStack;

public class BroadhoofGoatArmorFeatureRenderer extends MEFeatureRenderer<BroadhoofGoatEntity, BroadhoofGoatEntityRenderState, BroadhoofGoatModel> {

    private final BroadhoofGoatArmorModel model;
    private final EquipmentRenderer equipmentRenderer;

    public BroadhoofGoatArmorFeatureRenderer(RenderLayerParent<BroadhoofGoatEntity, BroadhoofGoatModel> context, LoadedEntityModelsCompat loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.equipmentRenderer = equipmentRenderer;
        this.model = new BroadhoofGoatArmorModel(loader.getModelPart(EntityModelLayersME.BROADHOOF_GOAT_ARMOR));
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, BroadhoofGoatEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.armor;
        ResourceLocation equippableComponent = MEComponents.get(itemStack, MEComponents.EQUIPPABLE_ASSET);
        if (equippableComponent != null && !equippableComponent.getPath().isEmpty()) {
            model.setAngles(state);
            this.equipmentRenderer.render(EquipmentModel.LayerType.HORSE_BODY, equippableComponent, model, itemStack, matrices, vertexConsumers, light);
        }
    }
}
