package net.jukoz.me.entity.goals;

import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.Difficulty;
import net.jukoz.me.entity.beasts.AbstractBeastEntity;
import net.jukoz.me.entity.npcs.NpcEntity;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.FactionLookup;

public class TargetNPCDiplomacyGoal extends NearestAttackableTargetGoal<NpcEntity> {
    NpcEntity mob;
    public TargetNPCDiplomacyGoal(NpcEntity mob) {
        super(mob, NpcEntity.class, true);
        this.mob = mob;
    }

    @Override
    public boolean canStart() {
        if (mob.level().getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        } else {
            if(mob.getTarget() instanceof NpcEntity npcEntity) {
                try {
                    Faction currentFaction = FactionLookup.getFactionById(mob.level(), mob.getFactionIdentifier());
                    if(!currentFaction.isHostileToward(npcEntity.getFactionIdentifier())) return false;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return super.canStart();
        }
    }
}
