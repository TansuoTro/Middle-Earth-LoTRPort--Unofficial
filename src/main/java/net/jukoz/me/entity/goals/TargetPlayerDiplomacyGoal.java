package net.jukoz.me.entity.goals;

import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Difficulty;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.FactionLookup;
import net.jukoz.me.utils.PlayerUtil;

public class TargetPlayerDiplomacyGoal extends ActiveTargetGoal<Player> {
    NpcEntity mob;
    public TargetPlayerDiplomacyGoal(NpcEntity mob) {
        super(mob, Player.class, true);
        this.mob = mob;
    }

    @Override
    public boolean canStart() {
        if (mob.getWorld().getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        } else {
            if(mob.getTarget() instanceof Player playerEntity && !playerEntity.isCreative() && !playerEntity.isSpectator()) {
                try {
                    Faction currentFaction = FactionLookup.getFactionById(mob.getWorld(), mob.getFactionIdentifier());
                    if(currentFaction == null)
                        return true;
                    Faction playerFaction = PlayerUtil.fetchFaction(playerEntity);
                    if(playerFaction != null && !currentFaction.isHostileToward(playerFaction.getId())) return false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return super.canStart();
        }
    }
}
