package net.jukoz.me.mixin.client;

import net.jukoz.me.datageneration.VariantsModelProvider;
import net.jukoz.me.datageneration.content.models.SimpleBigItemModel;
import net.minecraft.client.renderer.ItemModelShaper;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @Shadow @Final private ItemModelShaper models;

    @ModifyVariable(method = "renderItem", at = @At("HEAD"), argsOnly = true)
    private BakedModel renderItem(BakedModel bakedModel, ItemStack itemStack, ItemDisplayContext renderMode) {
        if(renderMode == ItemDisplayContext.GUI || renderMode == ItemDisplayContext.GROUND || renderMode == ItemDisplayContext.FIXED) {
            if(SimpleBigItemModel.items.contains(itemStack.getItem())) {
                ModelResourceLocation modelIdentifier = VariantsModelProvider.getInventoryModelIdentifierVariant(itemStack.getItem()); // new ModelResourceLocation(MiddleEarth.MOD_ID, itemStack.getTranslationKey(), "inventory");
                return models.getModelManager().getModel(modelIdentifier);
            }
        }
        return bakedModel;
    }
}
