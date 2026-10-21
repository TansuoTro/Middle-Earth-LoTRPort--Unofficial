package net.jukoz.me.entity.beasts.cave_troll;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.item.ItemDisplayContext;
import net.jukoz.me.entity.beasts.BeastEntityRenderState;

@OnlyIn(Dist.CLIENT)
public class CaveTrollEntityRenderState extends BeastEntityRenderState {
    public CaveTrollVariant variant = CaveTrollVariant.GREEN;
    public boolean isEnraged = false;
    public AnimationState chaseAnimationState = new AnimationState();
    public AnimationState scavengingAnimationState = new AnimationState();
    public AnimationState startSleepingAnimationState = new AnimationState();
    public AnimationState sleepingAnimationState = new AnimationState();
    public AnimationState stopSleepingAnimationState = new AnimationState();
    public AnimationState roaringAnimationState = new AnimationState();
    public AnimationState smashingAnimationState = new AnimationState();

}
