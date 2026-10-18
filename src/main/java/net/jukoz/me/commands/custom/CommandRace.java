package net.jukoz.me.commands.custom;
import net.jukoz.me.compat.METext;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.commands.CommandUtils;
import net.jukoz.me.commands.ModCommands;
import net.jukoz.me.commands.suggestions.AllRaceSuggestionProvider;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.races.Race;
import net.jukoz.me.resources.datas.races.RaceLookup;
import net.jukoz.me.resources.datas.races.RaceUtil;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.resources.persistent_datas.PlayerDataService;
import net.jukoz.me.utils.ModColors;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CommandRace {
    public static String RACE_BASE_COMMAND = "race";
    private static final String SET = "set";
    private static final String GET = "get";
    private static final String RESET = "reset";
    private static final String PLAYER = "player";
    private static final String RACE_ID = "race_id";
    private static final int RACE_COLOR = ModColors.WARNING.color;
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        // [GET]
        CommandUtils.simpleCommand(dispatcher, RACE_BASE_COMMAND, literal(GET)
            .executes(CommandRace::getRace), PLAYER, literal(GET)
            .executes(CommandRace::getTargetRace));

        // [SET]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(RACE_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgumentType.player())
                    .then(literal(SET) // With Player Target
                    .then(argument(RACE_ID, IdentifierArgumentType.identifier())
                    .suggests(new AllRaceSuggestionProvider())
                    .executes(CommandRace::setTargetRace))))
                .then(literal(SET) // Without Target
                .then(argument(RACE_ID, IdentifierArgumentType.identifier())
                .suggests(new AllRaceSuggestionProvider())
                .executes(CommandRace::setRace)))));

        // [RESET]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(RACE_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgumentType.player())
                    .then(literal(RESET) // With Player Target
                    .executes(CommandRace::resetTargetRace)))
                .then(literal(RESET) // Without Target
                .executes(CommandRace::resetRace))));
    }

    private static int getRace(CommandContext<ServerCommandSource> context) {
        if(context.getSource().isExecutedByPlayer()) {
            ServerPlayerEntity playerSource = context.getSource().getPlayer();
            if(playerSource != null){
                Race playerRace = PlayerDataService.getPlayerRace(playerSource, playerSource.getWorld());
                if(playerRace != null){
                    MutableText sourceText = Text.translatable("command.%s.race.get.success".formatted(MiddleEarth.MOD_ID),
                            METext.withColor(METext, playerRace.getFullName().copyContentOnly(), RACE_COLOR));
                    playerSource.sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
                    return 0;
                }
                MutableText sourceText = Text.translatable("command.%s.race.get.fail".formatted(MiddleEarth.MOD_ID));
                playerSource.sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
            }
        }
        return 0;
    }

    private static int getTargetRace(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if(context.getSource().isExecutedByPlayer()) {
            ServerPlayerEntity targetPlayer = EntityArgumentType.getPlayer(context, PLAYER);
            ServerPlayerEntity source = context.getSource().getPlayer();
            if(source != null){
                PlayerData playerData = StateSaverAndLoader.getPlayerState(targetPlayer);
                if(playerData != null){
                    Identifier id = playerData.getRace();
                    if(id != null){
                        Race race = RaceLookup.getRace(context.getSource().getWorld(), id);
                        if(race != null){
                            MutableText sourceText = Text.translatable("command.%s.race.get.target.success".formatted(MiddleEarth.MOD_ID), targetPlayer.getName(),
                                    METext.withColor(METext, race.getFullName().copyContentOnly(), RACE_COLOR));
                            source.sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
                            return 0;
                        }
                    }
                }
                MutableText sourceText = Text.translatable("command.%s.race.get.target.fail".formatted(MiddleEarth.MOD_ID), targetPlayer.getName());
                source.sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
            }
        }
        return 0;
    }

    private static int setRace(CommandContext<ServerCommandSource> context) {
        if(context.getSource().isExecutedByPlayer()) {
            Identifier raceId = IdentifierArgumentType.getIdentifier(context, RACE_ID);
            ServerPlayerEntity source = context.getSource().getPlayer();

            if(raceId != null && source != null) {
                    PlayerData playerData = StateSaverAndLoader.getPlayerState(source);
                    if(playerData != null){
                        Race race = RaceLookup.getRace(source.getWorld(), raceId);
                        if(race != null){
                            RaceUtil.updateRace(source, race, true);
                            MutableText sourceText = Text.translatable("command.%s.race.set.success".formatted(MiddleEarth.MOD_ID),
                                    METext.withColor(METext, race.getFullName().copyContentOnly(), RACE_COLOR));
                            source.sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
                            return 0;
                        }
                    }
                    MutableText sourceText = Text.translatable("command.%s.race.set.fail".formatted(MiddleEarth.MOD_ID), raceId.toString());
                    source.sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
            }
        }
        return 0;
    }

    private static int setTargetRace(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Identifier raceId = IdentifierArgumentType.getIdentifier(context, RACE_ID);
        ServerPlayerEntity targetPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        ServerPlayerEntity source = context.getSource().getPlayer();

        if(raceId != null && targetPlayer != null) {
            PlayerData playerData = StateSaverAndLoader.getPlayerState(targetPlayer);
            if(playerData != null){
                Race race = RaceLookup.getRace(targetPlayer.getWorld(), raceId);
                if(race != null){
                    RaceUtil.updateRace(targetPlayer, race, true);
                    MutableText targetText = Text.translatable("command.%s.race.set.success".formatted(MiddleEarth.MOD_ID),
                            METext.withColor(METext, race.getFullName().copyContentOnly(), RACE_COLOR));
                    targetPlayer.sendMessage(METext.withColor(targetText, ModColors.SUCCESS.color));

                    if(source != null){
                        MutableText sourceText = Text.translatable("command.%s.race.set.target.success".formatted(MiddleEarth.MOD_ID), targetPlayer.getName(),
                                METext.withColor(METext, race.getFullName().copyContentOnly(), RACE_COLOR));
                        source.sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
                    }

                    return 0;
                }
            }
            MutableText sourceText = Text.translatable("command.%s.race.set.target.fail".formatted(MiddleEarth.MOD_ID), raceId.toString());
            source.sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
        }
        return 0;
    }


    private static int resetRace(CommandContext<ServerCommandSource> context) {
        if(context.getSource().isExecutedByPlayer()) {
            ServerPlayerEntity source = context.getSource().getPlayer();

            if(source != null) {
                PlayerData playerData = StateSaverAndLoader.getPlayerState(source);
                if(playerData != null){
                    RaceUtil.updateRace(source, null, true);
                    RaceUtil.reset(source);
                    MutableText sourceText = Text.translatable("command.%s.race.reset.success".formatted(MiddleEarth.MOD_ID));
                    source.sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
                    return 0;
                }
                MutableText sourceText = Text.translatable("command.%s.race.reset.fail".formatted(MiddleEarth.MOD_ID));
                source.sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
            }
        }
        return 0;
    }

    private static int resetTargetRace(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity targetPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        ServerPlayerEntity source = context.getSource().getPlayer();

        PlayerData playerData = StateSaverAndLoader.getPlayerState(targetPlayer);
        if(targetPlayer != null){
            RaceUtil.updateRace(targetPlayer, null, true);
            RaceUtil.reset(targetPlayer);

            if(source != null && targetPlayer != null) {
                MutableText sourceText = Text.translatable("command.%s.race.reset.target.success".formatted(MiddleEarth.MOD_ID), targetPlayer.getName());
                targetPlayer.sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
            }
            MutableText targetText = Text.translatable("command.%s.race.reset.success".formatted(MiddleEarth.MOD_ID));
            targetPlayer.sendMessage(METext.withColor(targetText, ModColors.SUCCESS.color));
            return 0;
        }
        MutableText sourceText = Text.translatable("command.%s.race.reset.target.fail".formatted(MiddleEarth.MOD_ID), targetPlayer.getName());
        source.sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));

        return 0;
    }
}
