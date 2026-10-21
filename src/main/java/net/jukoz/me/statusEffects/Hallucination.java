package net.jukoz.me.statusEffects;

import net.jukoz.me.utils.HallucinationData;
import net.jukoz.me.utils.IEntityDataSaver;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import java.util.Map;

public class Hallucination extends MobEffect {
    public Hallucination(){
        super(
                MobEffectCategory.HARMFUL,
                0x006666
        );
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        if(entity instanceof Player){
            Map<MobEffect, MobEffectInstance> map = entity.getActiveEffectsMap();
            int ticksLeft = map.get(this).getDuration();
            if(ticksLeft != -1 && ticksLeft < HallucinationData.STOPPING_TICK)
                HallucinationData.addHallucination((IEntityDataSaver) entity, -2);
            else{
                HallucinationData.addHallucination((IEntityDataSaver) entity, 2);
            }
        }
    }
    @Override
    public void onRemoved(LivingEntity entity, AttributeMap attributes, int amplifier) {
        HallucinationData.resetHallucination((IEntityDataSaver) entity);
        HallucinationData.addHallucination((IEntityDataSaver) entity, -100);

        super.onRemoved(entity, attributes, amplifier);
    }

    public void stop(LivingEntity entity){
        HallucinationData.stopHallucination((IEntityDataSaver) entity);
    }

}
