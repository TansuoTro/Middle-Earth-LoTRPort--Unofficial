package net.jukoz.me.block.special.bellows;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.world.phys.Vec3;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.block.registration.ModDecorativeBlocks;
import net.jukoz.me.entity.EntityModelLayersME;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererFactory;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.Direction;
import com.mojang.math.Axis;
import net.minecraft.world.level.Level;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

@OnlyIn(Dist.CLIENT)
public class BellowsBlockEntityRenderer implements BlockEntityRenderer<BellowsBlockEntity>  {
    private final float BELLOW_MAX_ANGLE = 0.72f;
    private final ModelPart bottom;
    private final ModelPart top;
    private final ModelPart cavity;

    public BellowsBlockEntityRenderer(BlockEntityRendererProvider.Context ctx) {
        ModelPart modelPart = ctx.getLayerModelPart(EntityModelLayersME.BELLOWS);
        this.bottom = modelPart.getChild("bottom");
        this.top = modelPart.getChild("top");
        this.cavity = modelPart.getChild("cavity");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();
        modelPartData.addChild("bottom", CubeListBuilder.create().uv(0, 12)
                .cuboid(-9.0F, -1.0F, 14.0F, 2.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
                .uv(10, 12).cuboid(-9.0F, -3.0F, 1.0F, 2.0F, 2.0F, 2.0F,
                        new CubeDeformation(0.0F))
                .uv(1, 1).mirrored().cuboid(-13.0F, -1.0F, 4.0F, 10.0F, 1.0F, 10.0F,
                        new CubeDeformation(0.0F)).mirrored(false)
                .uv(1, 1).mirrored().cuboid(-13.0F, -3.0F, 4.0F, 10.0F, 1.0F, 10.0F,
                        new CubeDeformation(0.0F)).mirrored(false)
                .uv(0, 17).cuboid(-10.0F, -4.0F, 3.0F, 4.0F, 4.0F, 3.0F,
                        new CubeDeformation(0.0F)), PartPose.pivot(8.0F, 24.0F, -9.0F));

        modelPartData.addChild("top", CubeListBuilder.create().uv(0, 0)
                .cuboid(-5.0F, 0.0F, 0.0F, 10.0F, 1.0F, 11.0F, new CubeDeformation(0.0F))
                .uv(18, 12).cuboid(-1.0F, 0.0F, 11.0F, 2.0F, 1.0F, 4.0F,
                        new CubeDeformation(0.0F)), PartPose.of(0.0F, 19.0F, -5.0F, 0.3316F, 0.0F, 0.0F));

        modelPartData.addChild("cavity", CubeListBuilder.create().uv(10, 17).mirrored()
                .cuboid(-4.0F, -7.0F, -4.0F, 8.0F, 6.0F, 8.0F,
                        new CubeDeformation(0.0F)).mirrored(false), PartPose.pivot(0.0F, 24.0F, 0.0F));

        return LayerDefinition.of(modelData, 48, 48);
    }

    @Override
    public void render(BellowsBlockEntity entity, float tickProgress, PoseStack matrices, MultiBufferSource vertexConsumers, int light, int overlay) {
        Level world = entity.getWorld();
        BlockState blockState = world != null
                ? entity.getCachedState()
                : ModDecorativeBlocks.BELLOWS.getDefaultState().with(BellowsBlock.FACING, Direction.SOUTH);

        float animationProgress = getAnimationProgress(entity);

        float rotation = blockState.get(ChestBlock.FACING).asRotation();
        matrices.translate(0.5D, 1.5D, 0.5D);
        matrices.multiply(Axis.POSITIVE_Y.rotationDegrees(-rotation));
        matrices.multiply(Axis.POSITIVE_X.rotationDegrees(180));

        VertexConsumer vertexConsumer = getSpriteIdentifier(animationProgress).getVertexConsumer(vertexConsumers, RenderLayer::getEntityCutout);

        this.top.pitch = 0.37f + (animationProgress * -BELLOW_MAX_ANGLE);
        this.top.render(matrices, vertexConsumer, light, overlay);
        this.bottom.render(matrices, vertexConsumer, light, overlay);
        this.cavity.render(matrices, vertexConsumer, light, overlay);
    }

    private float getAnimationProgress(BellowsBlockEntity bellowsBlockEntity){
        float animationProgress = 0;
        if (bellowsBlockEntity.pumping){
            animationProgress = bellowsBlockEntity.animationProgress;
            if(animationProgress > (float) BellowsBlockEntity.MAX_TICKS / 2) animationProgress = BellowsBlockEntity.MAX_TICKS - animationProgress;
            animationProgress /= BellowsBlockEntity.MAX_TICKS;
        }
        return animationProgress;
    }

    private Material getSpriteIdentifier(float animationProgress){
        int spriteState = (int) Math.max(0, Math.min(2, animationProgress * 7.5f));
        return new Material(TextureAtlas.BLOCK_ATLAS_TEXTURE,
                new ResourceLocation(MiddleEarth.MOD_ID, "model/bellows/bellows_" + spriteState));
    }
}
