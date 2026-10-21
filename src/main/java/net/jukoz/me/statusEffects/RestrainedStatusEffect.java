package net.jukoz.me.statusEffects;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.jukoz.me.utils.IEntityDataSaver;

import java.util.Map;

public class RestrainedStatusEffect extends StatusEffect {
    public RestrainedStatusEffect(StatusEffectCategory statusEffectCategory, int i) {
        super(statusEffectCategory, i);
    }

}
