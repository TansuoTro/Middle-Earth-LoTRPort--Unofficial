package net.jukoz.me.mixin;

import net.jukoz.me.item.items.shields.CustomSiegeShieldItem;
import net.jukoz.me.item.items.weapons.ReachWeaponItem;
import net.jukoz.me.item.items.weapons.ranged.CustomLongbowWeaponItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class ServerPlayNetworkHandlerMixin {

    @Shadow public ServerPlayer player;

    @Inject(method = "onPlayerAction", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, ordinal = 0,
            target = "Lnet/minecraft/server/network/ServerPlayer;getItemInHand(Lnet/minecraft/util/InteractionHand;)Lnet/minecraft/item/ItemStack;"), cancellable = true)
    public void onPlayerAction(ServerboundPlayerActionPacket packet, CallbackInfo ci) {
        ItemStack stackMainHand = this.player.getItemInHand(InteractionHand.MAIN_HAND);
        if(stackMainHand != null){
            if ((stackMainHand.getItem() instanceof ReachWeaponItem && (((ReachWeaponItem) stackMainHand.getItem()).type.twoHanded))
                    || (stackMainHand.getItem() instanceof CustomSiegeShieldItem)
                    || (stackMainHand.getItem() instanceof CustomLongbowWeaponItem)) {
                ci.cancel();
            }
        }
    }
}
