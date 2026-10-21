package net.jukoz.me.resources.datas.races;

import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.jukoz.me.resources.datas.common.RaceType;
import net.jukoz.me.resources.datas.attributes.AttributePool;
import net.jukoz.me.resources.persistent_datas.PlayerDataService;

public class RaceUtil {

    public static void updateRace(Player player, Race race, boolean shouldHeal){
        Race previousRace = PlayerDataService.getPlayerRace(player, player.getWorld());
        boolean havePreviousRace =  previousRace != null;
        boolean raceExists = race != null;

        // [RESET]
        if(havePreviousRace){
            previousRace.reverseAttributes(player);
            PlayerDataService.setRace(player, player.getWorld(), null);
        }

        reset(player);

        // [SET]
        if(raceExists){
            PlayerDataService.setRace(player, player.getWorld(), race.getId());
        }

        if(shouldHeal)
            player.heal(player.getMaxHealth());
    }

    public static RaceType getRaceType(Player player){
        Race race = PlayerDataService.getPlayerRace(player, player.getWorld());
        if(race != null)
            return race.getRaceType();
        else
            return null;
    }

    public static void initializeRace(ServerPlayer player) {
        Race race = PlayerDataService.getPlayerRace(player, player.getWorld());
        updateRace(player, race, false);
    }

    public static void reset(Player player) {
        AttributePool.reverse(player);
    }
}
