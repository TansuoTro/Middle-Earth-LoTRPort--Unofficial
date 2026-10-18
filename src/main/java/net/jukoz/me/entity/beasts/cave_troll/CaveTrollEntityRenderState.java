package net.jukoz.me.entity.beasts.cave_troll;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.AnimationState;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.jukoz.me.entity.beasts.BeastEntityRenderState;

@Environment(EnvType.CLIENT)
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
