package net.jukoz.me.entity.beasts.warg.features;

import net.jukoz.me.item.MEComponents;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.equipment.EquipmentRenderer;
import net.minecraft.client.render.entity.model.LoadedEntityModels;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.util.Identifier;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.warg.WargEntityRenderState;
import net.jukoz.me.entity.beasts.warg.WargModel;
import net.jukoz.me.entity.EntityModelLayersME;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.jukoz.me.item.DataComponentTypesME;
import net.jukoz.me.item.EquipmentItemsME;
import net.jukoz.me.item.dataComponents.MountArmorAddonComponent;

public class WargArmorFrontSkullFeatureRenderer extends FeatureRenderer<WargEntityRenderState, WargModel> {
    private final WargArmorTopAddonsModel model;

    public WargArmorFrontSkullFeatureRenderer(FeatureRendererContext<WargEntityRenderState, WargModel> context, LoadedEntityModels loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new WargArmorTopAddonsModel(loader.getModelPart(EntityModelLayersME.WARG_ARMOR_ADDONS_FRONT));
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, WargEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.armor;

        MountArmorAddonComponent component = MEComponents.get(itemStack, MEComponents.MOUNT_ARMOR_DATA);

        if(component != null && component.topArmorAddon() && itemStack.isOf(EquipmentItemsME.WARG_MORDOR_PLATE_ARMOR)) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(new Identifier(MiddleEarth.MOD_ID, "textures/entities/warg/feature/warg_armor_addons.png")), itemStack.hasGlint());

            model.setAngles(state);
            model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV);
        }
    }
}
