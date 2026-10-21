package net.jukoz.me.entity.goals;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.Mob;

public class BirdFlightGoal extends Goal {
    private final Mob mob;
    private LivingEntity target;

    public BirdFlightGoal(Mob mob) {
        this.mob = mob;
    }

    @Override
    public boolean canStart() {
        this.target = this.mob.getTarget();

        if(!this.mob.isOnGround()){
            return false;
        } else if(this.target == null) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean shouldContinue() {
        return !this.mob.isOnGround();
    }

    @Override
    public void start() {
        this.mob.setVelocity(this.mob.getVelocity().x * 4, 0.8D, this.mob.getVelocity().z * 4);
    }
}
