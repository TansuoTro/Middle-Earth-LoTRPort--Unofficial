package net.jukoz.me.entity.beasts.warg.features;
import net.jukoz.me.entity.beasts.warg.WargEntity;
import net.jukoz.me.compat.client.render.MEFeatureRenderer;

import net.jukoz.me.item.MEComponents;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.jukoz.me.compat.client.render.equipment.EquipmentRenderer;
import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.beasts.warg.WargEntityRenderState;
import net.jukoz.me.entity.beasts.warg.WargModel;
import net.jukoz.me.entity.EntityModelLayersME;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemStack;
import net.jukoz.me.item.dataComponents.MountArmorAddonComponent;

public class WargArmorSideSkullsFeatureRenderer extends MEFeatureRenderer<WargEntity, WargEntityRenderState, WargModel> {
    private final WargArmorSideAddonsModel model;

    public WargArmorSideSkullsFeatureRenderer(FeatureRendererContext<WargEntity, WargModel> context, LoadedEntityModelsCompat loader, EquipmentRenderer equipmentRenderer) {
        super(context);
        this.model = new WargArmorSideAddonsModel(loader.getModelPart(EntityModelLayersME.WARG_ARMOR_ADDONS_SIDE_SKULL));
    }

    @Override
    public void render(PoseStack matrices, VertexConsumerProvider vertexConsumers, int light, WargEntityRenderState state, float limbAngle, float limbDistance) {
        ItemStack itemStack = state.armor;

        MountArmorAddonComponent component = MEComponents.get(itemStack, MEComponents.MOUNT_ARMOR_DATA);

        if(component != null && component.sideArmorAddon()) {
            VertexConsumer vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumers, RenderLayer.getArmorCutoutNoCull(new ResourceLocation(MiddleEarth.MOD_ID, "textures/entities/warg/feature/warg_armor_side_skull_addon.png")), true, itemStack.hasGlint());

            model.setAngles(state);
            model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
