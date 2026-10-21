package net.jukoz.me.mixin;

import net.jukoz.me.entity.EntityAttributesME;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityPose;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerAttributeCompatMixin {
    @Inject(method = "getDimensions", at = @At("RETURN"), cancellable = true)
    private void me$dimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        Player player = (Player) (Object) this;
        if (pose != EntityPose.SLEEPING) {
            float scale = EntityAttributesME.scale(player);
            cir.setReturnValue(cir.getReturnValue().scaled(scale * (float) EntityAttributesME.value(player, EntityAttributesME.WIDTH_SCALE), scale));
        }
    }

    @ModifyVariable(method = "getBlockBreakingSpeed", at = @At(value = "STORE", ordinal = 0), ordinal = 0)
    private float me$miningEfficiency(float speed) {
        return speed > 1.0F ? speed + (float) EntityAttributesME.value((Player) (Object) this, EntityAttributesME.MINING_EFFICIENCY) : speed;
    }
}
