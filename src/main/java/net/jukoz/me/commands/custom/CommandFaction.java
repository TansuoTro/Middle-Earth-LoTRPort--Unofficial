package net.jukoz.me.commands.custom;

import net.jukoz.me.compat.METext;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.commands.CommandUtils;
import net.jukoz.me.commands.ModCommands;
import net.jukoz.me.commands.suggestions.AllAvailableSpawnSuggestionProvider;
import net.jukoz.me.commands.suggestions.FactionSuggestionProvider;
import net.jukoz.me.exceptions.FactionIdentifierException;
import net.jukoz.me.exceptions.IdenticalFactionException;
import net.jukoz.me.exceptions.NoFactionException;
import net.jukoz.me.exceptions.SpawnIdentifierException;
import net.jukoz.me.resources.datas.factions.Faction;
import net.jukoz.me.resources.datas.factions.FactionLookup;
import net.jukoz.me.resources.datas.factions.FactionUtil;
import net.jukoz.me.resources.persistent_datas.PlayerDataService;
import net.jukoz.me.utils.ModColors;
import org.jetbrains.annotations.Nullable;
import static net.minecraft.server.command.CommandManager.*;

public class CommandFaction {
    public static String FACTION_BASE_COMMAND = "faction";
    private static final String JOIN = "join";
    private static final String GET = "get";
    private static final String CLEAR = "clear";
    private static final String FACTION_ID = "faction_id";
    private static final String SPAWN_ID = "spawn_id";
    private static final String PLAYER = "player";
    private static final String BANNER = "banner";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandRegistryAccess, RegistrationEnvironment registrationEnvironment) {
        // [GET]
        CommandUtils.simpleCommand(dispatcher, FACTION_BASE_COMMAND, literal(GET) // With Player Target
        .executes(CommandFaction::getFaction), PLAYER, literal(GET) // With Player Target
        .executes(CommandFaction::getTargetFaction));

        // [CLEAR]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(FACTION_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgument.player())
                    .then(literal(CLEAR) // With Player Target
                    .executes(CommandFaction::clearTargetFaction))))
                .then(literal(FACTION_BASE_COMMAND)
                .then(literal(CLEAR) // Without Target
                .executes(CommandFaction::clearFaction))));

        // [JOIN]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(FACTION_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgument.player())
                    .then(literal(JOIN) // With Player Target
                    .then(argument(FACTION_ID, ResourceLocationArgument.identifier())
                    .suggests(new FactionSuggestionProvider())
                    .executes(CommandFaction::forceTargetToJoinFaction))))
                .then(literal(JOIN) // Without Target
                .then(argument(FACTION_ID, ResourceLocationArgument.identifier())
                .suggests(new FactionSuggestionProvider())
                .executes(CommandFaction::joinFaction)))));

        // [JOIN + SET SPAWN]
        dispatcher.register((literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2))) // Require OP
                .then((literal(FACTION_BASE_COMMAND))
                    .then(argument(PLAYER, EntityArgument.player())
                        .then((literal(JOIN) // With Player Target
                        .then(argument(FACTION_ID, ResourceLocationArgument.identifier())
                        .suggests(new FactionSuggestionProvider())
                        .then(argument(SPAWN_ID, ResourceLocationArgument.identifier())
                        .suggests(new AllAvailableSpawnSuggestionProvider())
                        .executes(CommandFaction::forceTargetToJoinFaction))))))

                    .then((literal(JOIN)) // No Player Target
                    .then(argument(FACTION_ID, ResourceLocationArgument.identifier())
                    .suggests(new FactionSuggestionProvider())
                    .then(argument(SPAWN_ID, ResourceLocationArgument.identifier())
                    .suggests(new AllAvailableSpawnSuggestionProvider())
                    .executes(CommandFaction::joinFaction))))));

        // [GET BANNER]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(FACTION_BASE_COMMAND)
                    .then(literal(BANNER)
                    .then(argument(FACTION_ID, ResourceLocationArgument.identifier())
                    .suggests(new FactionSuggestionProvider())
                    .executes(CommandFaction::getBanner)))));
    }

    private static int getBanner(CommandContext<CommandSourceStack> context) {
        if(context.getSource().isExecutedByPlayer()) {
            ServerPlayer source = context.getSource().getPlayer();
            ResourceLocation factionIdentifier = ResourceLocationArgument.getIdentifier(context, FACTION_ID);;

            try{
                Faction faction = FactionLookup.getFactionById(source.getWorld(), factionIdentifier);
                source.giveItemStack(faction.getBannerItem(source.getWorld().getRegistryManager()));
                MutableComponent sourceText = Component.translatable("command.%s.faction.banner.success".formatted(MiddleEarth.MOD_ID), faction.getFullName().formatted(ChatFormatting.GOLD));
                source.sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
            } catch (FactionIdentifierException e){
                MutableComponent sourceText = Component.translatable("command.%s.faction.banner.fail_id".formatted(MiddleEarth.MOD_ID), Component.of(factionIdentifier.toString()));
                source.sendMessage(METext.withColor(sourceText, ModColors.ALERT.color));
                return 0;
            } catch (Exception e){
                MutableComponent sourceText = Component.translatable("command.%s.faction.banner.fail_error".formatted(MiddleEarth.MOD_ID), Component.of(factionIdentifier.toString()));
                source.sendMessage(METext.withColor(sourceText, ModColors.ALERT.color));
                return 0;
            }
        }
        return 1;
    }

    private static int getFaction(CommandContext<CommandSourceStack> context) {
        if(context.getSource().isExecutedByPlayer()) {
            ServerPlayer source = context.getSource().getPlayer();
            if(source != null){
                Faction currentFaction = PlayerDataService.getPlayerFaction(source, source.getWorld());
                if(currentFaction == null){
                    MutableComponent sourceText = Component.translatable("command.%s.get.faction.no_faction".formatted(MiddleEarth.MOD_ID));
                    source.sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
                    return 0;
                }
                MutableComponent sourceText = Component.translatable("command.%s.get.faction.success".formatted(MiddleEarth.MOD_ID), currentFaction.getFullName());
                source.sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
            }
        }
        return 0;
    }

    private static int getTargetFaction(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer targetedPlayer = EntityArgument.getPlayer(context, PLAYER);
        if(targetedPlayer != null && context.getSource().isExecutedByPlayer()) {
            ServerPlayer source = context.getSource().getPlayer();
            if(source != null){
                Faction currentFaction = PlayerDataService.getPlayerFaction(source, source.getWorld());
                if(currentFaction == null){
                    MutableComponent sourceText = Component.translatable("command.%s.get.player.faction.no_faction".formatted(MiddleEarth.MOD_ID), targetedPlayer.getName());
                    source.sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
                    return 0;
                }
                MutableComponent sourceText = Component.translatable("command.%s.get.player.faction.success".formatted(MiddleEarth.MOD_ID), targetedPlayer.getName(), currentFaction.getFullName());
                source.sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
            }
        }
        return 0;
    }

    private static int clearFaction(CommandContext<CommandSourceStack> context) {
        if(context.getSource().isExecutedByPlayer()) {
            ServerPlayer source = context.getSource().getPlayer();
            if(source != null) {
                try {
                    if (FactionUtil.clearFaction(source)) {
                        MutableComponent sourceText = Component.translatable("command.%s.clear.faction.success".formatted(MiddleEarth.MOD_ID));
                        source.sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
                    }
                    return 1;
                } catch (NoFactionException e) {
                    MutableComponent sourceText = Component.translatable(NoFactionException.KEY_SOURCE);
                    source.sendMessage(METext.withColor(sourceText, ModColors.ALERT.color));
                } catch (FactionIdentifierException e) {
                    MutableComponent sourceText = Component.translatable(FactionIdentifierException.KEY);
                    source.sendMessage(METext.withColor(sourceText, ModColors.ALERT.color));
                }
            }
        }
        return 0;
    }

    private static int clearTargetFaction(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer targetedPlayer = EntityArgument.getPlayer(context, PLAYER);
        boolean isPlayerSource = context.getSource().isExecutedByPlayer();

        if(targetedPlayer != null){
            ServerPlayer playerSource = null;
            if(isPlayerSource)
                playerSource = context.getSource().getPlayer();
            if(context.getSource() != null) {
                try {
                    if (FactionUtil.clearFaction(playerSource)) {
                        MutableComponent sourceText = Component.translatable("command.%s.clear.player.faction.success".formatted(MiddleEarth.MOD_ID), targetedPlayer.getName());
                        context.getSource().sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
                    }
                    return 1;
                } catch (NoFactionException e) {
                    MutableComponent sourceText = Component.translatable(NoFactionException.KEY_TARGET, targetedPlayer.getName());
                    context.getSource().sendMessage(METext.withColor(sourceText, ModColors.ALERT.color));
                } catch (FactionIdentifierException e) {
                    MutableComponent sourceText = Component.translatable(FactionIdentifierException.KEY);
                    context.getSource().sendMessage(METext.withColor(sourceText, ModColors.ALERT.color));
                }
            }
        }

        return 0;
    }
    private static int joinFaction(CommandContext<CommandSourceStack> context) {
        ServerPlayer targetedPlayer = context.getSource().getPlayer();
        ResourceLocation factionIdentifier = ResourceLocationArgument.getIdentifier(context, FACTION_ID);;
        if(targetedPlayer != null){
            ResourceLocation spawnIdentifier = null;
            try{
                spawnIdentifier = ResourceLocationArgument.getIdentifier(context, SPAWN_ID);
            } catch (Exception ignored){
            }
            boolean success =  updateFactionFromCommand(targetedPlayer, context.getSource(), factionIdentifier, spawnIdentifier);
            if(success){
                return 1;
            }
        }

        return 0;
    }

    private static int forceTargetToJoinFaction(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer targetedPlayer = EntityArgument.getPlayer(context, PLAYER);
        ResourceLocation factionIdentifier = ResourceLocationArgument.getIdentifier(context, FACTION_ID);
        ResourceLocation spawnIdentifier = null;
        try{
            spawnIdentifier = ResourceLocationArgument.getIdentifier(context, SPAWN_ID);
        } catch (Exception ignored){
        }

        boolean success = updateFactionFromCommand(targetedPlayer, context.getSource(), factionIdentifier, spawnIdentifier);
        if(success){
            try{
                Faction faction = FactionLookup.getFactionById(context.getSource().getWorld(), factionIdentifier);
                if(context.getSource().isExecutedByPlayer()){
                    ServerPlayer source = context.getSource().getPlayer();
                    MutableComponent sourceText = Component.translatable("command.%s.join.faction.join.success".formatted(MiddleEarth.MOD_ID), targetedPlayer.getName(), faction.getFullName());
                    source.sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
                }
            } catch (FactionIdentifierException e) {
                MiddleEarth.LOGGER.logDebugMsg("PlayerFactionPayload Id does not exist");
            }

            return 1;
        }
        return 0;
    }

    public static boolean updateFactionFromCommand(ServerPlayer target, CommandSourceStack source, ResourceLocation factionIdentifier, @Nullable ResourceLocation spawnId) {
        Faction foundFaction = null;
        boolean commandOnSelf = source.isExecutedByPlayer() && source.getPlayer() == target;
        try {
            foundFaction = FactionLookup.getFactionById(source.getWorld(), factionIdentifier);
            FactionUtil.updateFaction(target, foundFaction, spawnId);
            return true;
        } catch (FactionIdentifierException e){
            MutableComponent errorMessage = Component.translatable(FactionIdentifierException.KEY, factionIdentifier.toString());
            source.sendMessage(METext.withColor(errorMessage, ModColors.ALERT.color));
            return false;
        } catch (IdenticalFactionException e){
            if(commandOnSelf){ // Player on himself
                MutableComponent errorMessage = Component.translatable(IdenticalFactionException.KEY_SOURCE, (foundFaction == null) ? factionIdentifier.toString() : foundFaction.getFullName());
                source.sendMessage(METext.withColor(errorMessage, ModColors.ALERT.color));
                return false;
            }  else { // Player on another target or Command block
                MutableComponent errorMessage = Component.translatable(IdenticalFactionException.KEY_TARGET, target.getName(), (foundFaction == null) ? factionIdentifier.toString() : foundFaction.getFullName());
                source.sendMessage(METext.withColor(errorMessage, ModColors.ALERT.color));
                return false;
            }
        } catch (SpawnIdentifierException e){
                MutableComponent errorMessage;
                if(spawnId != null){
                    errorMessage = Component.translatable(SpawnIdentifierException.KEY, spawnId.toString());
                } else {
                    errorMessage = Component.translatable("command.%s.fail".formatted(MiddleEarth.MOD_ID));
                }
                source.sendMessage(METext.withColor(errorMessage, ModColors.ALERT.color));
                return false;
        } catch (NoFactionException e){
            if(commandOnSelf){ // Player on himself
                MutableComponent errorMessage = Component.translatable(NoFactionException.KEY_SOURCE);
                source.sendMessage(METext.withColor(errorMessage, ModColors.ALERT.color));
                return false;
            }  else { // Player on another target or Command block
                MutableComponent errorMessage = Component.translatable(NoFactionException.KEY_TARGET, target);
                source.sendMessage(METext.withColor(errorMessage, ModColors.ALERT.color));
                return false;
            }
        }
    }
}
