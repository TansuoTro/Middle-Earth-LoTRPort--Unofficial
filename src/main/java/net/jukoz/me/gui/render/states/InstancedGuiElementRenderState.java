package net.jukoz.me.gui.render.states;

import net.minecraft.client.gui.render.state.special.SpecialGuiElementRenderState;
import net.minecraft.client.render.VertexConsumerProvider;
import net.jukoz.me.gui.render.InstancedGuiElementRenderer;

public interface InstancedGuiElementRenderState extends SpecialGuiElementRenderState {
    InstancedGuiElementRenderer<? extends InstancedGuiElementRenderState> newRenderer(VertexConsumerProvider.Immediate vertexConsumers);
}
