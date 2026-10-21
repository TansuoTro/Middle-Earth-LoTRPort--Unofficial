package net.jukoz.me.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.item.FoodItemsME;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractHorse.class)
public abstract class AbstractHorseEntityMixin {

    @Shadow
    @Nullable
    public abstract LivingEntity getControllingPassenger();
    @Shadow
    @Nullable
    protected SimpleContainer items;

    @Shadow
    protected abstract void dropInventory(ServerLevel world);

    @WrapOperation(method = "receiveFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;is(Lnet/minecraft/item/Item;)Z", ordinal = 3))
    private boolean receiveFoodLettuce(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.is(FoodItemsME.LETTUCE);
    }

    @WrapOperation(method = "receiveFood", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;is(Lnet/minecraft/item/Item;)Z", ordinal = 5))
    private boolean receiveFoodHorseFeed(ItemStack instance, Item item, Operation<Boolean> original) {
        return original.call(instance, item) || instance.is(FoodItemsME.SACK_OF_HORSEFEED);
    }

    @Inject(at = @At("HEAD"), method = "dropInventory", cancellable = true)
    protected void dropInventory(ServerLevel world, CallbackInfo ci) {
        if(getControllingPassenger() != null && getControllingPassenger() instanceof NpcEntity){
            this.items.clear();
            ci.cancel();
        }
    }
}
