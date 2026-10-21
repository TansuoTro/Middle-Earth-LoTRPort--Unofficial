package net.jukoz.me.entity.goals;

import net.jukoz.me.entity.beasts.BeastEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.control.Control;

public class BeastSitGoal extends Goal {
    private final BeastEntity mob;

    public BeastSitGoal(BeastEntity mob) {
        this.mob = mob;
        this.setControls(EnumSet.of(Goal.Flag.JUMP, Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldContinue() {
        return this.mob.isSitting();
    }

    @Override
    public boolean canStart() {
        if (!this.mob.isTame()) {
            return false;
        }
        if (this.mob.isInWaterOrBubble()) {
            return false;
        }
        if (!this.mob.isOnGround()) {
            return false;
        }
        return this.mob.isSitting();
    }

    @Override
    public void start() {
        this.mob.getNavigation().stop();
    }

    @Override
    public void stop() {
    }
}
