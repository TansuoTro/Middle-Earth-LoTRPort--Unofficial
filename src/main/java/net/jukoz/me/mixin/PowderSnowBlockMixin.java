package net.jukoz.me.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.level.block.PowderSnowBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.entity.EntityAttributesME;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {

    @WrapOperation(method = "canWalkOnPowderSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z", ordinal = 0))
    private static boolean canWalkOnPowderSnowTag(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.isIn(TagKey.of(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "powder_snow_walk_on")));
    }

    @Inject(method = "canWalkOnPowderSnow", at = @At("HEAD"), cancellable = true)
    private static void canWalkOnPowderSnowEntity(Entity entity, final CallbackInfoReturnable<Boolean> info) {
        if(entity instanceof LivingEntity livingEntity){
            AttributeContainer container = livingEntity.getAttributes();
            if(container.hasAttribute(EntityAttributesME.POWDERED_SNOW_IMMUNITY.value()) && container.getValue(EntityAttributesME.POWDERED_SNOW_IMMUNITY.value()) != 0.0){
                info.setReturnValue(true);
            }
        }
    }
}
