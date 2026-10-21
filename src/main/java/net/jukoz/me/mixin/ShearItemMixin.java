package net.jukoz.me.mixin;

import net.jukoz.me.MiddleEarth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShearsItem;
import net.minecraft.world.item.SwordItem;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShearsItem.class)
public class ShearItemMixin {

    @Inject(method = "getMiningSpeedMultiplier", at = @At(value = "HEAD"), cancellable = true)
    public void getMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
        if (state.isIn(TagKey.of(Registries.BLOCK, new ResourceLocation(MiddleEarth.MOD_ID, "cobwebs")))) {
            cir.setReturnValue(15.0F);
        }
    }

    @Inject(method = "isSuitableFor", at = @At(value = "HEAD"), cancellable = true)
    public void isSuitableFor(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(state.isIn(TagKey.of(Registries.BLOCK, new ResourceLocation(MiddleEarth.MOD_ID, "cobwebs"))) || state.isOf(Blocks.REDSTONE_WIRE) || state.isOf(Blocks.TRIPWIRE));
    }
}
