package net.jukoz.me.entity.goose;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.world.entity.EquipmentSlot;
import com.mojang.math.Axis;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.ShovelItem;
import net.minecraft.world.item.SwordItem;

//
// Channel code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

@OnlyIn(Dist.CLIENT)
public class GooseHeldItemFeatureRenderer extends RenderLayer<GooseEntity, GooseModel<GooseEntity>> {
    private final ItemInHandRenderer heldItemRenderer;

    public GooseHeldItemFeatureRenderer(RenderLayerParent<GooseEntity, GooseModel<GooseEntity>> context, ItemInHandRenderer heldItemRenderer) {
        super(context);
        this.heldItemRenderer = heldItemRenderer;
    }

    public void render(PoseStack matrixStack, MultiBufferSource vertexConsumerProvider, int i, GooseEntity gooseEntity, float f, float g, float h, float j, float k, float l) {
        matrixStack.push();

        matrixStack.translate((this.getContextModel()).head.pivotX / 16.0F + (this.getContextModel()).neck.pivotX / 16.0F, (this.getContextModel()).neck.pivotY / 16.0F, (this.getContextModel()).head.pivotZ / 16.0F + (this.getContextModel()).neck.pivotZ / 16.0F);
        matrixStack.multiply(Axis.POSITIVE_Y.rotationDegrees(k));
        matrixStack.multiply(Axis.POSITIVE_X.rotationDegrees(l));

        ItemStack itemStack = gooseEntity.getEquippedStack(EquipmentSlot.MAINHAND);
        if(itemStack.getItem() instanceof SwordItem || itemStack.getItem() instanceof PickaxeItem || itemStack.getItem() instanceof AxeItem || itemStack.getItem() instanceof ShovelItem || itemStack.getItem() instanceof HoeItem) {
            matrixStack.translate(-0.1F, -0.4F, -0.2F);

            matrixStack.multiply(Axis.POSITIVE_X.rotationDegrees(90.0F));
            matrixStack.multiply(Axis.POSITIVE_Z.rotationDegrees(135.0F));
        } else {
            matrixStack.translate(-0.0F, -0.4F, -0.5F);

            matrixStack.multiply(Axis.POSITIVE_X.rotationDegrees(90.0F));
        }
        if(gooseEntity.isAttacking()){

        }

        this.heldItemRenderer.renderItem(gooseEntity, itemStack, ItemDisplayContext.GROUND, false, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }
}
