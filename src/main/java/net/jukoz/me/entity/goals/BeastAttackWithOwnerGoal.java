package net.jukoz.me.entity.goals;

import net.jukoz.me.entity.beasts.BeastEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.control.Control;

public class BeastAttackWithOwnerGoal extends TargetGoal {
    private final BeastEntity mob;
    private LivingEntity attacking;
    private int lastAttackTime;

    public BeastAttackWithOwnerGoal(BeastEntity mob) {
        super(mob, false);
        this.mob = mob;
        this.setControls(EnumSet.of(Goal.Control.TARGET));
    }

    @Override
    public boolean canStart() {
        if (!this.mob.isTame() || this.mob.isSitting()) {
            return false;
        }
        LivingEntity livingEntity = this.mob.getOwner();
        if (livingEntity == null) {
            return false;
        }
        this.attacking = livingEntity.getAttacking();
        int i = livingEntity.getLastAttackTime();
        return i != this.lastAttackTime && this.canTrack(this.attacking, TargetingConditions.DEFAULT);
    }

    @Override
    public void start() {
        this.mob.setTarget(this.attacking);
        LivingEntity livingEntity = this.mob.getOwner();
        if (livingEntity != null) {
            this.lastAttackTime = livingEntity.getLastAttackTime();
        }
        super.start();
    }
}
