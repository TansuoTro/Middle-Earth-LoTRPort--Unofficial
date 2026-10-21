package net.jukoz.me.compat.client.render.equipment;

import net.jukoz.me.compat.client.render.equipment.EquipmentRenderer;
import net.jukoz.me.compat.client.render.MEFeatureRenderer;
import net.jukoz.me.compat.client.render.state.EntityRenderStateCompat;
import net.jukoz.me.item.MEComponents;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.model.EntityModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

/**
 * 1.20.1 bridge for the generic 1.21.x
 * {@code net.minecraft.client.render.entity.feature.SaddleFeatureRenderer}
 * (the {@code (context, equipmentRenderer, model, LayerType, stackGetter)} overload that
 * renders a mount's body armor from its EQUIPPABLE asset). Renders through
 * {@link EquipmentRenderer} on the compat texture convention.
 */
public class SaddleFeatureRenderer<T extends LivingEntity, S extends EntityRenderStateCompat, M extends EntityModel<T>>
        extends MEFeatureRenderer<T, S, M> {

    private final M model;
    private final EquipmentModel.LayerType layerType;
    private final Function<S, ItemStack> stackGetter;

    public SaddleFeatureRenderer(FeatureRendererContext<T, M> context, EquipmentRenderer equipmentRenderer,
                                 M model, EquipmentModel.LayerType layerType, Function<S, ItemStack> stackGetter) {
        super(context);
        this.model = model;
        this.layerType = layerType;
        this.stackGetter = stackGetter;
    }

    @Override
    public void render(PoseStack matrices, VertexConsumerProvider vertexConsumers, int light,
                       S state, float limbAngle, float limbDistance) {
        ItemStack stack = this.stackGetter.apply(state);
        if (stack == null || stack.isEmpty()) {
            return;
        }
        ResourceLocation assetId = MEComponents.get(stack, MEComponents.EQUIPPABLE_ASSET);
        if (assetId == null) {
            return;
        }
        EquipmentRenderer.INSTANCE.render(this.layerType, assetId, this.model, stack, matrices, vertexConsumers, light);
    }
}
