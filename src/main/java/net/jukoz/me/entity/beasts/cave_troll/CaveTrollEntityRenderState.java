package net.jukoz.me.entity.beasts.cave_troll;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.util.Arm;
import net.jukoz.me.entity.beasts.BeastEntityRenderState;

@Environment(EnvType.CLIENT)
public class CaveTrollEntityRenderState extends BeastEntityRenderState {
    public final ItemRenderState handItemState = new ItemRenderState();

    public CaveTrollVariant variant = CaveTrollVariant.GREEN;
    public boolean isEnraged = false;
    public AnimationState chaseAnimationState = new AnimationState();
    public AnimationState scavengingAnimationState = new AnimationState();
    public AnimationState startSleepingAnimationState = new AnimationState();
    public AnimationState sleepingAnimationState = new AnimationState();
    public AnimationState stopSleepingAnimationState = new AnimationState();
    public AnimationState roaringAnimationState = new AnimationState();
    public AnimationState smashingAnimationState = new AnimationState();

    public static void updateRenderState(LivingEntity entity, CaveTrollEntityRenderState state, ItemModelManager itemModelManager) {
        itemModelManager.updateForLivingEntity(state.handItemState, entity.getStackInArm(Arm.RIGHT), ModelTransformationMode.THIRD_PERSON_RIGHT_HAND, entity);
    }
}
