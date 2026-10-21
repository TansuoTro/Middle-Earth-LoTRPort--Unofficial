package net.jukoz.me.entity.beasts.trolls;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.jukoz.me.compat.client.render.state.LivingEntityRenderState;
import net.minecraft.entity.AnimationState;

@OnlyIn(Dist.CLIENT)
public class TrollEntityRenderState extends LivingEntityRenderState {
    public AnimationState attackAnimationState = new AnimationState();
    public AnimationState chargeAnimationState = new AnimationState();
    public AnimationState throwingAnimationState = new AnimationState();


}
