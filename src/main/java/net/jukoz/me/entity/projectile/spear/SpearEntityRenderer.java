package net.jukoz.me.entity.projectile.spear;

import net.jukoz.me.compat.client.render.MEEntityRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererFactory;
import net.jukoz.me.compat.client.render.equipment.EquipmentRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.TextureAtlas;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.entity.EntityRendererProvider;

@OnlyIn(Dist.CLIENT)
public class SpearEntityRenderer extends MEEntityRenderer<SpearEntity, SpearEntityRenderState> {
    private static final float MIN_DISTANCE = 12.25F;
    private static final float SCALE = 1.0F;
    private final EquipmentRenderer itemRenderer;
    private final float scale;
    private final boolean lit;

    public SpearEntityRenderer(EntityRendererProvider.Context ctx, float scale, boolean lit) {
        super(ctx);
        this.itemRenderer = EquipmentRenderer.INSTANCE;
        this.scale = SCALE * scale;
        this.lit = lit;
    }

    public SpearEntityRenderer(EntityRendererProvider.Context context) {
        this(context, 1.0F, false);
    }

    @Override
    public SpearEntityRenderState createRenderState() {
        return new SpearEntityRenderState();
    }

    @Override
    protected int getBlockLight(SpearEntity entity, BlockPos pos) {
        return this.lit ? 15 : super.getBlockLight(entity, pos);

    }

    //TODO fix all this
    @Override
    public void render(SpearEntityRenderState state, PoseStack matrices, MultiBufferSource vertexConsumers, int light) {
        /*
        matrices.push();
        float cosYaw = (float) Math.cos(Math.toRadians(state.getYaw()));
        float sinYaw = (float) Math.sin(Math.toRadians(state.getYaw()));
        matrices.translate(sinYaw * -1.3D, 1.55D * (state.getPitch() / -90), cosYaw * -1.3D);
        matrices.multiply(Axis.POSITIVE_Y.rotationDegrees(Mth.lerp(tickDelta, state.prevYaw, state.getYaw()) - 90.0f));
        matrices.multiply(Axis.POSITIVE_Z.rotationDegrees(-90));
        matrices.multiply(Axis.POSITIVE_Z.rotationDegrees(Mth.lerp(tickDelta, state.prevPitch, state.getPitch())));

        matrices.multiply(Axis.POSITIVE_Y.rotationDegrees(90.0f));

        matrices.scale(scale, scale, scale);

        ItemStack itemStack = state.getTrackedItemStackData();
        if(itemStack == null) state.getDefaultItemStack();
        this.itemRenderer.renderItem(itemStack, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, entity.getWorld(), entity.getId());
        matrices.pop();

        */
        super.render(state, matrices, vertexConsumers, light);
    }
}
