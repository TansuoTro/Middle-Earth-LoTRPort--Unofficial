package net.jukoz.me.mixin;

import net.jukoz.me.entity.EntityAttributesME;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.level.ServerPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerGamePacketListenerImpl.class)
public abstract class ServerReachCompatMixin {
    @Shadow public ServerPlayer player;

    @Redirect(method = "onPlayerInteractEntity", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;MAX_BREAK_SQUARED_DISTANCE:D"))
    private double me$entityReach() {
        double reach = EntityAttributesME.entityReach(player) + 1.0;
        return reach * reach;
    }

    @Redirect(method = "onPlayerInteractBlock", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;MAX_BREAK_SQUARED_DISTANCE:D"))
    private double me$blockReach() {
        double reach = EntityAttributesME.blockReach(player) + 1.5;
        return reach * reach;
    }
}
