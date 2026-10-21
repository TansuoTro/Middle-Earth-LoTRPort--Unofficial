package net.jukoz.me.entity.goals.interfaces;

import net.minecraft.world.entity.monster.RangedAttackMob;

public interface CooldownRangedAttackMob extends RangedAttackMob {
    int getRangeAttackCooldown();
    void setRangeAttackCooldown(int cooldown);
}
