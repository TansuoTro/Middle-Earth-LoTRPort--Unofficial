package net.jukoz.me.mixin.client;

import net.jukoz.me.entity.EntityAttributesME;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputAttributeMixin {
    @ModifyVariable(method = "tick", at = @At("HEAD"), argsOnly = true)
    private float me$sneakingSpeed(float vanillaSpeed) {
        var player = MinecraftClient.getInstance().player;
        return player == null ? vanillaSpeed : MathHelper.clamp(vanillaSpeed - 0.3F
                + (float) EntityAttributesME.value(player, EntityAttributesME.SNEAKING_SPEED), 0.0F, 1.0F);
    }
}
