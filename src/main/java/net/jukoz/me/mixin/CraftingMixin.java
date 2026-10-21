package net.jukoz.me.mixin;

import net.minecraft.server.level.ServerLevel;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.inventory.CraftingMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(CraftingMenu.class)
public class CraftingMixin {

    @Inject(method = "updateResult", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/inventory/ResultContainer;setStack(ILnet/minecraft/item/ItemStack;)V", ordinal = 0),
            cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void updateResultCancel(AbstractContainerMenu handler, ServerLevel world, Player player,
                                           CraftingContainer craftingInventory, ResultContainer resultInventory,
                                           @Nullable CraftingRecipe recipe, CallbackInfo ci, CraftingContainer craftingRecipeInput,
                                           ServerPlayer serverPlayerEntity, ItemStack itemStack){
        if (world.isClient){
            return;
        }
        if (!ModServerConfigs.ENABLE_GOLDEN_FOOD_RECIPES && ModDimensions.isInMiddleEarth(world)
                && (itemStack.isOf(Items.GOLDEN_APPLE) || itemStack.isOf(Items.GOLDEN_CARROT))){
            itemStack = new ItemStack(Items.AIR);
        }
        resultInventory.setStack(0, itemStack);
        ci.cancel();
    }
}
