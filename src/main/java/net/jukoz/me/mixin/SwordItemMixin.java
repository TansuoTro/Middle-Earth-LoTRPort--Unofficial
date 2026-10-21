package net.jukoz.me.mixin;

import net.jukoz.me.MiddleEarth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.block.CobwebBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SwordItem.class)
public class SwordItemMixin {

    @Inject(method = "getMiningSpeedMultiplier", at = @At(value = "HEAD"), cancellable = true)
    public void getMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
        if (state.isIn(TagKey.of(Registries.BLOCK, new ResourceLocation(MiddleEarth.MOD_ID, "cobwebs")))) {
            cir.setReturnValue(15.0F);
        }
    }
}
