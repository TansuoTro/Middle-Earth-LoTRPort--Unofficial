package net.jukoz.me.entity.beasts.great_horn.features;

import net.jukoz.me.entity.beasts.great_horn.GreatHornEntity;
import net.jukoz.me.compat.client.render.MEFeatureRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.jukoz.me.compat.client.render.LoadedEntityModelsCompat;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.EntityModelLayersME;
import net.jukoz.me.entity.beasts.great_horn.GreatHornEntityRenderState;
import net.jukoz.me.entity.beasts.great_horn.GreatHornModel;
import java.time.LocalDate;
import java.time.Month;

@OnlyIn(Dist.CLIENT)
public class GreatHornNoseFeatureRenderer extends MEFeatureRenderer<GreatHornEntity, GreatHornEntityRenderState, GreatHornModel> {
	private final GreatHornModel model;
	private final static ResourceLocation TEXTURE = new ResourceLocation(MiddleEarth.MOD_ID, "textures/entities/great_horn/feature/great_horn_red_nose.png");

	public GreatHornNoseFeatureRenderer(RenderLayerParent<GreatHornEntity, GreatHornModel> context, LoadedEntityModelsCompat loader) {
		super(context);
		this.model = new GreatHornModel(loader.getModelPart(EntityModelLayersME.GREAT_HORN));
	}

	private boolean isChristmas() {
		LocalDate date = LocalDate.now();
		return date.getMonth() == Month.DECEMBER && date.getDayOfMonth() >= 24;
	}

	@Override
	public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, GreatHornEntityRenderState state, float limbAngle, float limbDistance) {
		if(state.hasRedNose() || isChristmas()) {
			VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(TEXTURE));
			this.getContextModel().render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
		}
	}
}
