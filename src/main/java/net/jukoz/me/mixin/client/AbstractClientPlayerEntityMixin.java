package net.jukoz.me.mixin.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.jukoz.me.statusEffects.ModStatusEffects;
import net.jukoz.me.utils.HallucinationData;
import net.jukoz.me.utils.IEntityDataSaver;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@OnlyIn(Dist.CLIENT)
@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerEntityMixin {

    @Shadow @Final public ClientLevel clientWorld;
    @Inject(method = "getFovMultiplier()F", at =@At("TAIL"), cancellable = true)
    private void injected(CallbackInfoReturnable<Float> cir) {
    Player player = Minecraft.getInstance().player;
        assert player != null;
        if(player.hasStatusEffect(ModStatusEffects.HALLUCINATION)){
            float intensity = (float) HallucinationData.readHallucination((IEntityDataSaver) player) / 100f;
            cir.setReturnValue(cir.getReturnValue() * (1 - intensity/4));
        }
    }

    @Redirect(method = "getFovMultiplier", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;isOf(Lnet/minecraft/item/Item;)Z"))
    private boolean init(ItemStack itemStack, Item item) {
        return itemStack.getItem() instanceof BowItem;
    }
}
