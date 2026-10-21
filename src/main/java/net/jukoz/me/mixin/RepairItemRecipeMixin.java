package net.jukoz.me.mixin;

import net.jukoz.me.MiddleEarth;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RepairItemRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RepairItemRecipe.class)
public class RepairItemRecipeMixin {

    @Inject(at = @At("HEAD"), method = "canCombineStacks", cancellable = true)
    private static void canCombineStacks(ItemStack first, ItemStack second, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(second.isOf(first.getItem())
                && first.getCount() == 1
                && second.getCount() == 1

                && first.isDamageable()
                && second.isDamageable()

                && first.isDamageable()
                && second.isDamageable()

                && !first.isIn(TagKey.of(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "anvil_items")))
                && !second.isIn(TagKey.of(Registries.ITEM, new ResourceLocation(MiddleEarth.MOD_ID, "anvil_items")))
                );
    }
}
