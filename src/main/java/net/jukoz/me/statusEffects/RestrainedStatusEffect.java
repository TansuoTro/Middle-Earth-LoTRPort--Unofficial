package net.jukoz.me.statusEffects;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.jukoz.me.utils.IEntityDataSaver;
import java.util.Map;

public class RestrainedStatusEffect extends MobEffect {
    public RestrainedStatusEffect(MobEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
    }

}
