package net.jukoz.me.commands.suggestions;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.FactionLookup;
import net.jukoz.me.resources.datas.factions.data.SpawnDataHandler;
import net.jukoz.me.resources.persistent_datas.PlayerDataService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class AllAvailableSpawnSuggestionProvider implements SuggestionProvider<ServerCommandSource> {
    @Override
    public CompletableFuture<Suggestions> getSuggestions(CommandContext<ServerCommandSource> context, SuggestionsBuilder builder) {
        try {
            List<ResourceLocation> candidates = null;
            candidates = getAllSpawns(context);
            return SuggestionUtil.getCorrespondingIdentifiers(candidates, builder);
        } catch (FactionIdentifierException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<ResourceLocation> getAllSpawns(CommandContext<ServerCommandSource> context) throws FactionIdentifierException {
        ServerPlayer targettedPlayer = null; // Null for command blocks
        try {
            targettedPlayer = EntityArgumentType.getPlayer(context, "player");  // Targeted player when the argument is there
        } catch (Exception e){ // There is no player argument in the command
            if(context.getSource().isExecutedByPlayer()){
                targettedPlayer = context.getSource().getPlayer(); // Source player when no player argument
            }
        }

        Faction currentSelectedFaction = null; // Null by default
        try {
            ResourceLocation factionId = context.getArgument("faction_id", ResourceLocation.class);
            currentSelectedFaction = FactionLookup.getFactionById(context.getSource().getWorld(), factionId);

        } catch (Exception e){ // There is no player argument in the command
            if(targettedPlayer != null){
                currentSelectedFaction = PlayerDataService.getPlayerFaction(targettedPlayer, targettedPlayer.getWorld());
            }
        }
        List<ResourceLocation> candidates = new ArrayList<>();

        if(currentSelectedFaction != null) {
            SpawnDataHandler spawnDataHandler = currentSelectedFaction.getSpawnData();
            if(spawnDataHandler != null && spawnDataHandler.getSpawnList() != null){
                List<ResourceLocation> factionSpawns = spawnDataHandler.getAllSpawnIdentifiers().stream().toList();
                candidates.addAll(factionSpawns);
                return candidates;
            }
        }

        // Return all faction spawns
        List<Faction> allFactions = FactionLookup.getAllJoinableFaction(context.getSource().getWorld());
        for(Faction faction : allFactions){
            SpawnDataHandler spawnDataHandler = faction.getSpawnData();
            if(spawnDataHandler != null && spawnDataHandler.getSpawnList() != null){
                List<ResourceLocation> factionSpawns = spawnDataHandler.getAllSpawnIdentifiers().stream().toList();
                candidates.addAll(factionSpawns);
            }
        }
        return candidates;
    }
}