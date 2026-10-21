package net.jukoz.me.entity.goals;

import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Difficulty;
import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.jukoz.me.resources.persistent_datas.PlayerDataService;

public class BeastTargetPlayerGoal extends NearestAttackableTargetGoal<Player> {
    AbstractBeastEntity mob;
    DispositionType beastDispositionType;

    public BeastTargetPlayerGoal(AbstractBeastEntity mob, DispositionType beastDispositionType) {
        super(mob, Player.class, true);
        this.mob = mob;
        this.beastDispositionType = beastDispositionType;
    }

    @Override
    public boolean canStart() {
        return super.canStart() && canTargetMob();
    }

    @Override
    public boolean shouldContinue() {
        return super.shouldContinue() && canTargetMob();
    }

    private boolean canTargetMob(){
        if(this.mob.level() instanceof ServerLevel serverWorld) {
            Player player = serverWorld.getClosestPlayer(this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());;
            if(player == null || mob.level().getDifficulty() == Difficulty.PEACEFUL || mob.isTame() || player == mob.getOwner()){
                return false;
            }
            if(beastDispositionType != null){
                return PlayerDataService.getPlayerDisposition(player, player.level()) != beastDispositionType;
            }
            return true;
        }
        return false;
    }
}
