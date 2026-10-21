package net.jukoz.me.entity.goals;

import net.jukoz.me.entity.beasts.BeastEntity;
import net.jukoz.me.entity.beasts.trolls.TrollEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import java.util.EnumSet;
import net.minecraft.world.entity.ai.control.Control;

public class BeastTrackOwnerAttackerGoal extends TargetGoal {
    private final BeastEntity mob;
    private LivingEntity attacker;
    private int lastAttackedTime;

    public BeastTrackOwnerAttackerGoal(BeastEntity mob) {
        super(mob, false);
        this.mob = mob;
        this.setControls(EnumSet.of(Goal.Flag.TARGET));
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
        this.attacker = livingEntity.getAttacker();
        int i = livingEntity.getLastHurtByMobTimestamp();
        return i != this.lastAttackedTime && this.canTrack(this.attacker, TargetingConditions.DEFAULT);
    }

    @Override
    public void start() {
        this.mob.setTarget(this.attacker);
        LivingEntity livingEntity = this.mob.getOwner();
        if (livingEntity != null) {
            this.lastAttackedTime = livingEntity.getLastHurtByMobTimestamp();
        }
        super.start();
    }
}
