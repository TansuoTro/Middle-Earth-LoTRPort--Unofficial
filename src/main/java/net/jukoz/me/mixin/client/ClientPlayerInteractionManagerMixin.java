package net.jukoz.me.mixin.client;

import net.jukoz.me.item.items.ReachWeaponItem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin {
    @Inject(method="getReachDistance", at=@At("HEAD"), cancellable = true)
    public void getReachDistance(CallbackInfoReturnable<Float> cir){
        Player player = MinecraftClient.getInstance().player;
        if(player != null){
            cir.setReturnValue((float) net.jukoz.me.entity.EntityAttributesME.blockReach(player));
        }
    }
}
