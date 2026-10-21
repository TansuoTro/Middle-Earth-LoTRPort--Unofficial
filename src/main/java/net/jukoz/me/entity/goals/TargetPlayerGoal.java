package net.jukoz.me.entity.goals;

import net.jukoz.me.entity.beasts.BeastEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Difficulty;

public class TargetPlayerGoal extends ActiveTargetGoal<Player> {
    BeastEntity mob;
    public TargetPlayerGoal(BeastEntity mob) {
        super((Mob)mob, Player.class, true);
        this.mob = mob;
    }

    @Override
    public boolean canStart() {
        if (mob.getWorld().getDifficulty() == Difficulty.PEACEFUL || mob.isTame()) {
            if(mob.getTarget() instanceof Player) {
                mob.setTarget(null);
            }

            return false;
        }
        return super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        if(!mob.isTame()) {
            return super.shouldContinue();
        }
        return false;
    }
}
