package net.jukoz.me.mixin;

import net.jukoz.me.entity.EntityAttributesME;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerBlockReachCompatMixin {
    @Shadow @Final protected ServerPlayer player;

    @Redirect(method = "processBlockBreakingAction", at = @At(value = "FIELD", target = "Lnet/minecraft/server/network/ServerPlayNetworkHandler;MAX_BREAK_SQUARED_DISTANCE:D"))
    private double me$blockReach() {
        double reach = EntityAttributesME.blockReach(player) + 1.5;
        return reach * reach;
    }
}
