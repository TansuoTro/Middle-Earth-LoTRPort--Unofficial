package net.jukoz.me.entity.beasts.warg;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.entity.AnimationState;
import net.minecraft.world.item.ItemStack;
import net.jukoz.me.entity.beasts.BeastEntityRenderState;

@OnlyIn(Dist.CLIENT)
public class WargEntityRenderState extends BeastEntityRenderState {
    WargVariant variant = WargVariant.BROWN;
    public WargEyeVariant eyeVariant = WargEyeVariant.BLUE;
    public ItemStack armor = ItemStack.EMPTY;
    public ItemStack saddle = ItemStack.EMPTY;
    public boolean haveEmissiveEyes;
    public boolean isRunning;

    public AnimationState idleAnimationState = new AnimationState();
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState sittingAnimationState = new AnimationState();
}
