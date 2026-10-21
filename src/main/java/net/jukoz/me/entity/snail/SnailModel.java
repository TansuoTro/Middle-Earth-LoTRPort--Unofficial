package net.jukoz.me.entity.snail;

import net.jukoz.me.entity.spider.MirkwoodSpiderEntity;
import net.minecraft.client.model.*;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HierarchicalModel;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.util.Mth;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

// Made with Blockbench 4.8.3
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class SnailModel<T extends SnailEntity> extends HierarchicalModel<T> {
	private final ModelPart snail;
	private final ModelPart body;
	public SnailModel(ModelPart root) {
		this.snail = root.getChild("snail");
		this.body = snail.getChild("body");
	}
	public static LayerDefinition getTexturedModelData() {
		MeshDefinition modelData = new MeshDefinition();
		PartDefinition modelPartData = modelData.getRoot();
		PartDefinition snail = modelPartData.addChild("snail", CubeListBuilder.create(), PartPose.pivot(0.0F, 24.0F, -2.0F));

		PartDefinition house = snail.addChild("house", CubeListBuilder.create().uv(0, 9).cuboid(-1.5F, -5.5F, 1.0F, 3.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 0.0F, 0.0F));

		PartDefinition body = snail.addChild("body", CubeListBuilder.create().uv(0, 0).cuboid(-1.0F, -2.0F, -4.0F, 2.0F, 2.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.pivot(0.0F, 0.0F, 3.0F));

		PartDefinition eyes = body.addChild("eyes", CubeListBuilder.create(), PartPose.pivot(0.0F, 0.0F, -3.0F));

		PartDefinition eye_r1 = eyes.addChild("eye_r1", CubeListBuilder.create().uv(0, 0).cuboid(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(-1.0F, -2.0F, 0.0F, 0.0F, 0.0F, -0.3491F));

		PartDefinition eye_r2 = eyes.addChild("eye_r2", CubeListBuilder.create().uv(0, 2).cuboid(0.0F, -2.0F, -1.0F, 0.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.of(1.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.3491F));
		return LayerDefinition.of(modelData, 32, 32);
	}
	@Override
	public void setAngles(SnailEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);

		float percentage = (float) entity.getClimbingTicks() / SnailEntity.CLIMBING_TIME_TRANSITION;

		snail.pitch = -1.6f * percentage;

		if(limbSwingAmount >= 0.001) {
			float stretch = ((ageInTicks % 201) / 500);
			if(stretch >= 0.2) {
				this.body.zScale = 1f - (0.4f - stretch);
			}
			else {
				this.body.zScale = 1f - stretch;
			}
		}

	}
	@Override
	public void render(PoseStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		snail.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return snail;
	}

}
