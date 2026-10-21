package net.jukoz.me.mixin;

import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.recipe.CraftingRecipe;

import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.world.dimension.ModDimensions;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CarvedPumpkinBlock.class)
public class CarvedPumpkinBlockMixin {

    @Inject(method = "trySpawnEntity", at = @At("HEAD"), cancellable = true)
    private void abortGolemSpawn(Level world, BlockPos pos, CallbackInfo ci) {
        if (ModDimensions.isInMiddleEarth(world)) {
            ci.cancel();
        }
    }
}