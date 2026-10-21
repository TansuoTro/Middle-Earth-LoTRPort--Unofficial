package net.jukoz.me.mixin.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.Options;
import net.minecraft.client.multiplayer.ClientLevel;
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
@Mixin(Minecraft.class)
public abstract class MinecraftClientMixin {
    @Shadow @Final
    public Options options;

    @Inject(method = "setWorld", at = @At("HEAD"))
    private void resetGlintInNether(ClientLevel world, CallbackInfo ci) {
        if (ModClientConfigs.DISABLE_GLINT && world != null && ModDimensions.isInMiddleEarth(world)) {
            this.options.glintStrength().setValue(0.0);
        }
    }
}
