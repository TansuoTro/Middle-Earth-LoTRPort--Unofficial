package net.jukoz.me.mixin.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.jukoz.me.config.ModClientConfigs;
import net.jukoz.me.world.dimension.ModDimensions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@OnlyIn(Dist.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow @Final
    public GameOptions options;

    @Inject(method = "setWorld", at = @At("HEAD"))
    private void resetGlintInNether(ClientWorld world, CallbackInfo ci) {
        if (ModClientConfigs.DISABLE_GLINT && world != null && ModDimensions.isInMiddleEarth(world)) {
            this.options.getGlintStrength().setValue(0.0);
        }
    }
}
