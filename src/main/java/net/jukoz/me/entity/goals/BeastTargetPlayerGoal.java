package net.jukoz.me.entity.goals;

import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Difficulty;
import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.jukoz.me.resources.datas.common.DispositionType;
import net.jukoz.me.resources.persistent_datas.PlayerDataService;

public class BeastTargetPlayerGoal extends ActiveTargetGoal<PlayerEntity> {
    AbstractBeastEntity mob;
    DispositionType beastDispositionType;

    public BeastTargetPlayerGoal(AbstractBeastEntity mob, DispositionType beastDispositionType) {
        super(mob, PlayerEntity.class, true);
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
        if(this.mob.getWorld() instanceof ServerWorld serverWorld) {
            PlayerEntity player = serverWorld.getClosestPlayer(this.targetPredicate, this.mob, this.mob.getX(), this.mob.getEyeY(), this.mob.getZ());;
            if(player == null || mob.getWorld().getDifficulty() == Difficulty.PEACEFUL || mob.isTame() || player == mob.getOwner()){
                return false;
            }
            if(beastDispositionType != null){
                return PlayerDataService.getPlayerDisposition(player, player.getWorld()) != beastDispositionType;
            }
            return true;
        }
        return false;
    }
}
