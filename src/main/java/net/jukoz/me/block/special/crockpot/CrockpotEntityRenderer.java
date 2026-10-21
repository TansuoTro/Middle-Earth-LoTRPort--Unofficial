package net.jukoz.me.block.special.crockpot;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererFactory;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import com.mojang.math.Axis;
import net.minecraft.world.phys.Vec3;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.util.profiling.jfr.Environment;

@Environment(value= EnvType.CLIENT)
public class CrockpotEntityRenderer implements BlockEntityRenderer<CrockpotBlockEntity> {
    private static final float SIZE_FACTOR = 0.25f;
    private static Material waterSpriteId = new Material(TextureAtlas.BLOCK_ATLAS_TEXTURE, new ResourceLocation("minecraft", "block/water_still"));

    public CrockpotEntityRenderer(BlockEntityRendererProvider.Context context) {
    }

    @Override
    public void render(CrockpotBlockEntity entity, float tickProgress, PoseStack matrices,
                       MultiBufferSource vertexConsumers, int light, int overlay) {
        int color = 4161734;
        int red = color >> 16 & 255;
        int green = color >> 8 & 255;
        int blue = color & 255;

        matrices.push();
        if(entity.hasOutput()) {
            float liquidTopLevel = entity.getLiquidTopLevel();
            matrices.translate(0, liquidTopLevel, 0); // 0.0625 per pixel

            VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getTranslucentMovingBlock());
            //matrices.peek();

            TextureAtlasSprite waterSprite = waterSpriteId.getSprite();

            PoseStack.Entry entry = matrices.peek();

            float minU = waterSprite.getMinU();
            float minV = waterSprite.getMinV();
            float maxU = waterSprite.getMaxU();
            float maxV = waterSprite.getMaxV();

            float tempMin = minU;
            minU = Mth.lerp(SIZE_FACTOR, minU, maxU);
            maxU = Mth.lerp(1 - SIZE_FACTOR, tempMin, maxU);
            tempMin = minV;
            minV = Mth.lerp(SIZE_FACTOR, minV, maxV);
            maxV = Mth.lerp(1 - SIZE_FACTOR, tempMin, maxV);

            consumer.vertex(entry.getPositionMatrix(), SIZE_FACTOR, 0, SIZE_FACTOR)               .color(50, 80, 240, 190).texture(minU, minV).light(light).overlay(overlay).normal(1, 1, 1);
            consumer.vertex(entry.getPositionMatrix(), SIZE_FACTOR, 0, 1 - SIZE_FACTOR)  .color(50, 80, 190, 190).texture(minU, maxV).light(light).overlay(overlay).normal(1, 1, 1);
            consumer.vertex(entry.getPositionMatrix(), 1 - SIZE_FACTOR, 0, 1 - SIZE_FACTOR)        .color(50, 80, 240, 190).texture(maxU, maxV).light(light).overlay(overlay).normal(1, 1, 1);
            consumer.vertex(entry.getPositionMatrix(), 1 - SIZE_FACTOR, 0, SIZE_FACTOR)               .color(50, 80, 240, 190).texture(maxU, minV).light(light).overlay(overlay).normal(1, 1, 1);
        }

        matrices.pop();
    }
}
