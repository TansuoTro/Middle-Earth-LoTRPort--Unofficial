package net.jukoz.me.entity.projectile.spear;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.client.renderer.texture.TextureAtlas;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

@OnlyIn(Dist.CLIENT)
public class JavelinEntityRenderer<T extends Entity & ItemSupplier> extends EntityRenderer<T> {
    private static final float MIN_DISTANCE = 12.25F;
    private static final float SCALE = 1.5F;
    private final ItemRenderer itemRenderer;
    private final float scale;
    private final boolean lit;

    public JavelinEntityRenderer(EntityRendererProvider.Context ctx, float scale, boolean lit) {
        super(ctx);
        this.itemRenderer = ctx.getItemRenderer();
        this.scale = SCALE * scale;
        this.lit = lit;
    }

    public JavelinEntityRenderer(EntityRendererProvider.Context context) {
        this(context, 1.0F, false);
    }

    protected int getBlockLight(T entity, BlockPos pos) {
        return this.lit ? 15 : super.getBlockLight(entity, pos);
    }

    public void render(T entity, float yaw, float tickDelta, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        matrices.push();

        matrices.multiply(Axis.POSITIVE_Y.rotationDegrees(Mth.lerp(tickDelta, entity.prevYaw, entity.getYaw()) - 100.0F));
        matrices.multiply(Axis.POSITIVE_Z.rotationDegrees(Mth.lerp(tickDelta, entity.prevPitch, entity.getPitch()) + 90.0F));
        matrices.multiply(Axis.POSITIVE_X.rotationDegrees(180.0F));

        matrices.scale(scale, scale, scale);
        matrices.translate(0.0D, -0.2D, 0.0D);

        this.itemRenderer.renderItem(entity.getItem(), ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.level(), entity.getId());
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }

    public ResourceLocation getTexture(Entity entity) {
        return TextureAtlas.BLOCK_ATLAS_TEXTURE;
    }
}
