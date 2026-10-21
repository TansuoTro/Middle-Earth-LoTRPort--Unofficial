package net.jukoz.me.mixin;

import net.minecraft.server.world.ServerWorld;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.recipe.CraftingRecipe;

import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CraftingScreenHandler.class)
public class CraftingMixin {

    @Inject(method = "updateResult", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/inventory/CraftingResultInventory;setStack(ILnet/minecraft/item/ItemStack;)V", ordinal = 0),
            cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void updateResultCancel(ScreenHandler handler, ServerWorld world, PlayerEntity player,
                                           RecipeInputInventory craftingInventory, CraftingResultInventory resultInventory,
                                           @Nullable CraftingRecipe recipe, CallbackInfo ci){
        if (world.isClient){
            return;
        }
        if (ModServerConfigs.ENABLE_GOLDEN_FOOD_RECIPES || !ModDimensions.isInMiddleEarth(world)){
            return;
        }
        if (recipe != null){
            ItemStack crafted = recipe.craft(craftingInventory, world.getRegistryManager());
            resultInventory.setStack(0, crafted.isOf(Items.GOLDEN_APPLE) || crafted.isOf(Items.GOLDEN_CARROT)
                    ? new ItemStack(Items.AIR) : crafted);
        }
        ci.cancel();
    }
}