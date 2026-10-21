package net.jukoz.me.entity.goals;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.goal.FleeSunGoal;
import net.minecraft.world.entity.PathfinderMob;

public class PanicFireGoal extends FleeSunGoal {
    public PanicFireGoal(PathfinderMob mob, double speed) {
        super(mob, speed);
    }

    @Override
    public boolean canStart() {
        if (this.mob.getTarget() != null) {
            return false;
        }
        if (!this.mob.isOnFire()) {
            return false;
        }
        return this.targetShadedPos();
    }
}
