package net.jukoz.me.commands.custom;
import net.jukoz.me.compat.METext;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.Level;
import net.minecraft.world.dimension.DimensionTypes;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.commands.ModCommands;
import net.jukoz.me.commands.suggestions.AllAvailableSpawnSuggestionProvider;
import net.jukoz.me.commands.suggestions.AllSpawnSuggestionProvider;
import net.jukoz.me.resources.StateSaverAndLoader;
import net.jukoz.me.resources.datas.factions.FactionUtil;
import net.jukoz.me.resources.datas.factions.data.SpawnData;
import net.jukoz.me.resources.persistent_datas.PlayerData;
import net.jukoz.me.resources.persistent_datas.PlayerDataService;
import net.jukoz.me.utils.ModColors;
import net.jukoz.me.world.dimension.ModDimensions;
import org.joml.Vector3i;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CommandSpawn {
    private static final String SPAWN_BASE_COMMAND = "spawn";
    private static final String SET = "set";
    private static final String OVERWORLD = "overworld";
    private static final String MIDDLE_EARTH = "middle_earth";
    private static final String OVERWORLD_COORD = "overworld_blockpos";
    private static final String GET = "get";
    private static final String TP = "tp";
    private static final String TO = "to";
    private static final String RESET = "reset";
    private static final String WELCOME = "welcome_needed";
    private static final String SPAWN_ID = "spawn_id";
    private static final String PLAYER = "player";

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess commandRegistryAccess, CommandManager.RegistrationEnvironment registrationEnvironment) {
        // [GET OVERWORLD SPAWN]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgumentType.player()) // With Player Target
                    .then(literal(GET)
                    .then(literal(OVERWORLD)
                    .executes(CommandSpawn::getPlayerSpawnOverworld))))
                .then(literal(GET) // Without Target
                .then(literal(OVERWORLD)
                .executes(CommandSpawn::getSpawnOverworld)))));

        // [GET SPAWN ID]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                    .then(argument(PLAYER, EntityArgumentType.player()) // With Player Target
                        .then(literal(GET)
                        .then(literal(MIDDLE_EARTH)
                        .executes(CommandSpawn::getPlayerSpawnMiddleEarth))))
                    .then(literal(GET) // Without Target
                    .then(literal(MIDDLE_EARTH)
                    .executes(CommandSpawn::getSpawnMiddleEarth)))));

        // [TP - MIDDLE_EARTH]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                    .then(argument(PLAYER, EntityArgumentType.player())
                        .then(literal(TP) // With Player Target
                        .then(literal(MIDDLE_EARTH)
                        .then(argument(WELCOME, BoolArgumentType.bool())
                        .executes(CommandSpawn::teleportPlayerToSpawnMiddleEarth)))))
                    .then(literal(TP) // Without Target
                    .then(literal(MIDDLE_EARTH)
                    .then(argument(WELCOME, BoolArgumentType.bool())
                    .executes(CommandSpawn::teleportToSpawnMiddleEarth))))));

        // [TP - OVERWORLD]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgumentType.player())
                    .then(literal(TP) // With Player Target
                    .then(literal(OVERWORLD)
                    .executes(CommandSpawn::teleportPlayerToSpawnOverworld))))
                .then(literal(TP) // Without Target
                .then(literal(OVERWORLD)
                .executes(CommandSpawn::teleportToSpawnOverworld)))));

        // [SET - OVERWORLD]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                        .then(argument(PLAYER, EntityArgumentType.player())
                                .then(literal(SET)
                                .then(literal(OVERWORLD) // With Player Target
                                .then(argument(OVERWORLD_COORD, BlockPosArgumentType.blockPos())
                                .executes(CommandSpawn::setPlayerSpawnOverworld)))))
                        .then(literal(SET) // Without Target
                        .then(literal(OVERWORLD)
                        .then(argument(OVERWORLD_COORD, BlockPosArgumentType.blockPos())
                        .executes(CommandSpawn::setSpawnOverworld))))));

        // [SET - MIDDLE_EARTH]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                    .then(argument(PLAYER, EntityArgumentType.player())
                        .then(literal(SET)
                        .then(literal(MIDDLE_EARTH) // With Player Target
                        .then(argument(SPAWN_ID, IdentifierArgumentType.identifier())
                        .suggests(new AllAvailableSpawnSuggestionProvider())
                        .executes(CommandSpawn::setPlayerSpawnMiddleEarth)))))
                    .then(literal(SET) // Without Target
                    .then(literal(MIDDLE_EARTH)
                    .then(argument(SPAWN_ID, IdentifierArgumentType.identifier())
                    .suggests(new AllAvailableSpawnSuggestionProvider())
                    .executes(CommandSpawn::setSpawnMiddleEarth))))));

        // [RESET - OVERWORLD]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
            .requires(source -> source.hasPermissionLevel(2)) // Require OP
            .then(literal(SPAWN_BASE_COMMAND)
            .then(argument(PLAYER, EntityArgumentType.player())
                .then(literal(RESET) // With Player Target
                .then(literal(OVERWORLD)
                .executes(CommandSpawn::resetPlayerSpawnOverworld))))
            .then(literal(RESET) // Without Target
            .then(literal(OVERWORLD)
            .executes(CommandSpawn::resetSpawnOverworld)))));

        // [RESET - MIDDLE_EARTH]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(SPAWN_BASE_COMMAND)
                .then(argument(PLAYER, EntityArgumentType.player())
                    .then(literal(RESET) // With Player Target
                    .then(literal(MIDDLE_EARTH)
                    .executes(CommandSpawn::resetPlayerSpawnMiddleEarth))))
                .then(literal(RESET) // Without Target
                .then(literal(MIDDLE_EARTH)
                .executes(CommandSpawn::resetSpawnMiddleEarth)))));

        // [TP TO - SPAWN ID]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(TP)
                    .then(argument(PLAYER, EntityArgumentType.player())
                    .then(literal(TO)
                    .then(argument(SPAWN_ID, IdentifierArgumentType.identifier())
                    .suggests(new AllSpawnSuggestionProvider())
                    .executes(CommandSpawn::forceTeleportPlayerToSpawnMiddleEarth))))
                .then(literal(TO) // Without Target
                .then(argument(SPAWN_ID, IdentifierArgumentType.identifier())
                .suggests(new AllSpawnSuggestionProvider())
                .executes(CommandSpawn::forceTeleportToSpawnMiddleEarth)))));
    }

    // region Getters
    private static int getSpawnOverworld(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        ServerPlayer playerSource = context.getSource().getPlayer();
        PlayerDataService.OriginAggregate originAggregate = PlayerDataService.getOriginAggregateOrDefault(playerSource, playerSource.getWorld());
        BlockPos pos = originAggregate.origin();
        MutableComponent sourceText = Component.translatable("command.%s.get.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), pos.getX(), pos.getY(), pos.getZ());
        context.getSource().getPlayer().sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));

        return 0;
    }

    private static int getPlayerSpawnOverworld(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayer playerTarget = EntityArgumentType.getPlayer(context, PLAYER);
        PlayerData data = StateSaverAndLoader.getPlayerState(playerTarget);
        PlayerDataService.OriginAggregate originAggregate = PlayerDataService.getOriginAggregateOrDefault(playerTarget, playerTarget.getWorld());
        BlockPos pos = originAggregate.origin();
        MutableComponent sourceText = Component.translatable("command.%s.get.player.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName(), pos.getX(), pos.getY(), pos.getZ());
        context.getSource().sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));

        return 0;
    }

    private static int getSpawnMiddleEarth(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        Player playerSource = context.getSource().getPlayer();
        SpawnData spawnData = PlayerDataService.getPlayerSpawnData(playerSource, playerSource.getWorld());
        if(spawnData != null){
            BlockPos pos = spawnData.getBlockPos();
            MutableComponent sourceText = Component.translatable("command.%s.get.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), Component.translatable("spawn."+ spawnData.getIdentifier().toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
            context.getSource().getPlayer().sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
        } else {
            MutableComponent sourceText = Component.translatable("command.%s.get.spawn.middle_earth.no_spawn".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
        }
        return 0;

    }

    private static int getPlayerSpawnMiddleEarth(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        if(!context.getSource().isExecutedByPlayer()) return 0;

        ServerPlayer playerTarget = EntityArgumentType.getPlayer(context, PLAYER);
        SpawnData spawnData = PlayerDataService.getPlayerSpawnData(playerTarget, playerTarget.getWorld());
        if(spawnData != null){
            BlockPos pos = spawnData.getBlockPos();
            MutableComponent sourceText = Component.translatable("command.%s.get.player.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName(), Component.translatable("spawn."+spawnData.getIdentifier().toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
            context.getSource().sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
        } else {
            MutableComponent sourceText = Component.translatable("command.%s.get.player.spawn.middle_earth.no_spawn".formatted(MiddleEarth.MOD_ID), playerTarget.getName());
            context.getSource().sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
        }
        return 0;
    }
    // endregion

    // region Setters
    private static int setSpawnMiddleEarth(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        ServerPlayer playerSource = context.getSource().getPlayer();

        ResourceLocation spawnIdInput = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);

        if(!PlayerDataService.playerPassedOnboarding(playerSource)){
            MutableComponent sourceText = Component.translatable("command.%s.set.spawn.middle_earth.no_faction".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
            return 0;
        }

        if(PlayerDataService.setSpawn(playerSource, playerSource.getWorld(), spawnIdInput)){
            SpawnData spawnData =  PlayerDataService.getPlayerSpawnData(playerSource, playerSource.getWorld());
            BlockPos pos =  spawnData.getBlockPos();
            if(pos != null) {
                if(ModDimensions.isInMiddleEarth(playerSource.getWorld())){
                    playerSource.setSpawnPoint(ModDimensions.ME_WORLD_KEY, pos, 0, true, false);
                }
                MutableComponent sourceText = Component.translatable("command.%s.set.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), Component.translatable("spawn."+spawnIdInput.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
                playerSource.sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
                return 0;
            }
        }
        MutableComponent sourceText = Component.translatable("command.%s.set.spawn.middle_earth.no_spawn_found".formatted(MiddleEarth.MOD_ID), spawnIdInput.toString());
        playerSource.sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
        return 0;
    }

    private static int setPlayerSpawnMiddleEarth(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayer playerTarget = EntityArgumentType.getPlayer(context, PLAYER);

        ResourceLocation spawnIdInput = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);

        if(!PlayerDataService.playerPassedOnboarding(playerTarget)){
            MutableComponent sourceText = Component.translatable("command.%s.set.player.spawn.middle_earth.no_faction".formatted(MiddleEarth.MOD_ID), playerTarget.getName());
            context.getSource().sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
            return 0;
        }

        if(PlayerDataService.setSpawn(playerTarget, playerTarget.getWorld(), spawnIdInput)){
            SpawnData spawnData =  PlayerDataService.getPlayerSpawnData(playerTarget, playerTarget.getWorld());
            BlockPos pos =  spawnData.getBlockPos();
            if(pos != null) {
                if(ModDimensions.isInMiddleEarth(playerTarget.getWorld())){
                    playerTarget.setSpawnPoint(ModDimensions.ME_WORLD_KEY, pos, 0, true, false);
                }

                MutableComponent targetText = Component.translatable("command.%s.set.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), Component.translatable("spawn."+spawnIdInput.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
                playerTarget.sendMessage(METext.withColor(targetText, ModColors.SUCCESS.color));
                MutableComponent sourceText = Component.translatable("command.%s.set.player.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName(),Component.translatable("spawn."+spawnIdInput.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
                context.getSource().sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
                return 0;
            }
        }
        MutableComponent sourceText = Component.translatable("command.%s.set.spawn.middle_earth.no_spawn_found".formatted(MiddleEarth.MOD_ID), spawnIdInput.toString());
        context.getSource().sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));


        return 0;
    }

    private static int setSpawnOverworld(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        BlockPos posInput = BlockPosArgumentType.getBlockPos(context, OVERWORLD_COORD);

        ServerPlayer playerSource = context.getSource().getPlayer();

        if(PlayerDataService.setOrigin(playerSource, playerSource.getWorld(), DimensionTypes.OVERWORLD.getRegistry(), posInput)) {
            if(ModDimensions.isInOverworld(playerSource.getWorld())){
                playerSource.setSpawnPoint(Level.OVERWORLD, posInput, 0, true, false);
                return 0;
            }
        }
        MutableComponent sourceText = Component.translatable("command.%s.set.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), posInput.getX(), posInput.getY(), posInput.getZ());
        context.getSource().sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
        return 0;
    }

    private static int setPlayerSpawnOverworld(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        BlockPos posInput = BlockPosArgumentType.getBlockPos(context, OVERWORLD_COORD);
        ServerPlayer playerTarget = EntityArgumentType.getPlayer(context, PLAYER);

        PlayerDataService.setOrigin(playerTarget, playerTarget.getWorld(), DimensionTypes.OVERWORLD.getRegistry(), posInput);

        MutableComponent sourceText = Component.translatable("command.%s.set.player.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName() ,posInput.getX(), posInput.getY(), posInput.getZ());
        context.getSource().sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));

        MutableComponent targetText = Component.translatable("command.%s.set.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), posInput.getX(), posInput.getY(), posInput.getZ());
        playerTarget.sendMessage(METext.withColor(targetText, ModColors.SUCCESS.color));

        return 0;
    }

    // endregion

    // region Resets
    private static int resetPlayerSpawnOverworld(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayer playerTarget = EntityArgumentType.getPlayer(context, PLAYER);
        BlockPos posInput = context.getSource().getServer().getOverworld().getSpawnPos();
        PlayerDataService.setOrigin(playerTarget, playerTarget.getWorld(), DimensionTypes.OVERWORLD.getRegistry(), posInput);

        MutableComponent sourceText = Component.translatable("command.%s.reset.player.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName(), posInput.getX(), posInput.getY(), posInput.getZ());
        context.getSource().sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
        MutableComponent targetText = Component.translatable("command.%s.reset.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), posInput.getX(), posInput.getY(), posInput.getZ());
        playerTarget.sendMessage(METext.withColor(targetText, ModColors.SUCCESS.color));
        return 0;
    }

    private static int resetSpawnOverworld(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        ServerPlayer playerSource = context.getSource().getPlayer();
        PlayerDataService.resetOrigin(playerSource, playerSource.getWorld());
        PlayerDataService.OriginAggregate newOrigin = PlayerDataService.getOriginAggregateOrDefault(playerSource, playerSource.getWorld());
        MutableComponent sourceText = Component.translatable("command.%s.reset.spawn.overworld.success".formatted(MiddleEarth.MOD_ID), newOrigin.origin().getX(), newOrigin.origin().getY(), newOrigin.origin().getZ());
        playerSource.sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
        return 0;
    }

    private static int resetPlayerSpawnMiddleEarth(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayer playerTarget = EntityArgumentType.getPlayer(context, PLAYER);

        if(!PlayerDataService.playerPassedOnboarding(playerTarget)){
            MutableComponent sourceText = Component.translatable("command.%s.reset.player.spawn.middle_earth.no_faction".formatted(MiddleEarth.MOD_ID), playerTarget.getName());
            context.getSource().sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
            return 0;
        }
        if(PlayerDataService.resetSpawn(playerTarget, playerTarget.getWorld())){
            SpawnData data = PlayerDataService.getPlayerSpawnData(playerTarget, playerTarget.getWorld());

            MutableComponent sourceText = Component.translatable("command.%s.reset.player.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName(),
                    Component.translatable("spawn." + data.getIdentifier().toTranslationKey()), data.getBlockPos().getX(), data.getBlockPos().getY(), data.getBlockPos().getZ());
            context.getSource().sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));

            MutableComponent targetText = Component.translatable("command.%s.reset.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID),
                    Component.translatable("spawn." + data.getIdentifier().toTranslationKey()), data.getBlockPos().getX(), data.getBlockPos().getY(), data.getBlockPos().getZ());
            playerTarget.sendMessage(METext.withColor(targetText, ModColors.SUCCESS.color));
            return 0;

        }

        return 0;
    }

    private static int resetSpawnMiddleEarth(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        ServerPlayer playerSource = context.getSource().getPlayer();
        if(!PlayerDataService.playerPassedOnboarding(playerSource)){
            MutableComponent sourceText = Component.translatable("command.%s.reset.spawn.middle_earth.no_faction".formatted(MiddleEarth.MOD_ID));
            playerSource.sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
            return 0;
        }

        if(PlayerDataService.resetSpawn(playerSource, playerSource.getWorld())){
            SpawnData data = PlayerDataService.getPlayerSpawnData(playerSource, playerSource.getWorld());

            MutableComponent sourceText = Component.translatable("command.%s.reset.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID),
                    Component.translatable("spawn." + data.getIdentifier().toTranslationKey()), data.getBlockPos().getX(), data.getBlockPos().getY(), data.getBlockPos().getZ());
            playerSource.sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
        }
        return 0;
    }

    // endregion

    // region Teleports
    private static int teleportToSpawnMiddleEarth(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;

        boolean welcomeNeeded = BoolArgumentType.getBool(context, WELCOME);

        ServerPlayer playerSource = context.getSource().getPlayer();
        if(!PlayerDataService.playerPassedOnboarding(playerSource)){
            SpawnData spawnData = PlayerDataService.getPlayerSpawnData(playerSource, playerSource.getWorld());
            if(spawnData == null)
                return 0;

            PlayerDataService.OriginAggregate origin = PlayerDataService.getOriginAggregate(playerSource, playerSource.getWorld());
            if(ModDimensions.isInOverworld(playerSource.getWorld()) && origin != null){
                PlayerDataService.setOrigin(playerSource, playerSource.getWorld(), DimensionTypes.OVERWORLD_ID, playerSource.getBlockPos());
            }
            Vector3i spawnCoordinates = spawnData.getWorldCoordinates();
            if(spawnCoordinates != null) {
                BlockPos pos = new BlockPos(spawnCoordinates.x, spawnCoordinates.y,spawnCoordinates.z);
                if(ModDimensions.isInMiddleEarth(playerSource.getWorld())){
                    playerSource.setSpawnPoint(ModDimensions.ME_WORLD_KEY, pos, 0, true, false);
                }
                ModDimensions.teleportPlayerToMe(playerSource, new Vec3(spawnCoordinates.x, spawnCoordinates.y, spawnCoordinates.z), true, welcomeNeeded);
                MutableComponent sourceText = Component.translatable("command.%s.teleport.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID),
                        Component.translatable("spawn."+ spawnData.getIdentifier().toTranslationKey()));
                context.getSource().sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
                return 0;
            }

        }
        MutableComponent sourceText = Component.translatable("command.%s.teleport.spawn.middle_earth.no_spawn".formatted(MiddleEarth.MOD_ID));
        context.getSource().sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
        return 0;
    }

    private static int teleportPlayerToSpawnMiddleEarth(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayer playerTarget = EntityArgumentType.getPlayer(context, PLAYER);
        boolean welcomeNeeded = BoolArgumentType.getBool(context, WELCOME);

        if(PlayerDataService.playerPassedOnboarding(playerTarget)){
            SpawnData spawnData = PlayerDataService.getPlayerSpawnData(playerTarget, playerTarget.getWorld());
            if(spawnData == null)
                return 0;

            PlayerDataService.OriginAggregate origin = PlayerDataService.getOriginAggregate(playerTarget, playerTarget.getWorld());
            if(ModDimensions.isInOverworld(playerTarget.getWorld()) && origin != null){
                PlayerDataService.setOrigin(playerTarget, playerTarget.getWorld(), DimensionTypes.OVERWORLD_ID, playerTarget.getBlockPos());
            }
            Vector3i spawnCoordinates = spawnData.getWorldCoordinates();
            if(spawnCoordinates != null) {
                BlockPos pos = new BlockPos(spawnCoordinates.x, spawnCoordinates.y,spawnCoordinates.z);
                if(ModDimensions.isInMiddleEarth(playerTarget.getWorld())){
                    playerTarget.setSpawnPoint(ModDimensions.ME_WORLD_KEY, pos, 0, true, false);
                }
                ModDimensions.teleportPlayerToMe(playerTarget, new Vec3(spawnCoordinates.x, spawnCoordinates.y, spawnCoordinates.z), true, welcomeNeeded);
                MutableComponent sourceText = Component.translatable("command.%s.teleport.player.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName(), Component.translatable("spawn."+spawnData.getIdentifier().toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
                context.getSource().sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
                MutableComponent targetText = Component.translatable("command.%s.teleport.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), Component.translatable("spawn."+spawnData.getIdentifier().toTranslationKey()));
                playerTarget.sendMessage(METext.withColor(targetText, ModColors.SUCCESS.color));
                return 0;
            }

        }
        MutableComponent sourceText = Component.translatable("command.%s.teleport.player.spawn.middle_earth.no_spawn".formatted(MiddleEarth.MOD_ID), playerTarget.getName());
        context.getSource().sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
        return 0;
    }


    private static int teleportToSpawnOverworld(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;

        ServerPlayer player = context.getSource().getPlayer();
        if(ModDimensions.teleportPlayerToOverworld(player)){
            MutableComponent sourceText = Component.translatable("command.%s.teleport.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID));
            context.getSource().sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
            return 0;
        }
        MutableComponent sourceText = Component.translatable("command.%s.teleport.spawn.middle_earth.error".formatted(MiddleEarth.MOD_ID));
        context.getSource().sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
        return 0;
    }

    private static int teleportPlayerToSpawnOverworld(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayer player = EntityArgumentType.getPlayer(context, PLAYER);

        if(ModDimensions.teleportPlayerToOverworld(player)){
            MutableComponent sourceText = Component.translatable("command.%s.teleport.player.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), player.getName(), player.getX(), player.getY(), player.getZ());
            context.getSource().sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
            return 0;
        }
        MutableComponent sourceText = Component.translatable("command.%s.teleport.player.spawn.middle_earth.no_spawn".formatted(MiddleEarth.MOD_ID), player.getName());
        context.getSource().sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
        return 0;
    }

    private static int forceTeleportToSpawnMiddleEarth(CommandContext<ServerCommandSource> context) {
        if(!context.getSource().isExecutedByPlayer() || context.getSource().getPlayer() == null)
            return 0;
        ResourceLocation spawnId = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);

        if(FactionUtil.forceTeleportToSpawnMiddleEarthId(context.getSource().getPlayer(), spawnId)){
            BlockPos pos = FactionUtil.getSpawnBlockPos(context.getSource().getWorld(), spawnId);
            MutableComponent targetText = Component.translatable("command.%s.teleport.to.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), Component.translatable("spawn."+spawnId.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
            context.getSource().sendMessage(METext.withColor(targetText, ModColors.SUCCESS.color));
        } else {
            MutableComponent sourceText = Component.translatable("command.%s.teleport.to.spawn.middle_earth.error".formatted(MiddleEarth.MOD_ID), Component.translatable("spawn."+spawnId.toTranslationKey()));
            context.getSource().sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
        }
        return 0;
    }

    private static int forceTeleportPlayerToSpawnMiddleEarth(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayer targetedPlayer = EntityArgumentType.getPlayer(context, PLAYER);
        ResourceLocation spawnId = IdentifierArgumentType.getIdentifier(context, SPAWN_ID);

        if(FactionUtil.forceTeleportToSpawnMiddleEarthId(targetedPlayer, spawnId)){
            BlockPos pos = FactionUtil.getSpawnBlockPos(context.getSource().getWorld(), spawnId);
            MutableComponent targetText = Component.translatable("command.%s.teleport.to.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), Component.translatable("spawn."+spawnId.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
            targetedPlayer.sendMessage(METext.withColor(targetText, ModColors.SUCCESS.color));
            MutableComponent sourceText = Component.translatable("command.%s.teleport.player.to.spawn.middle_earth.success".formatted(MiddleEarth.MOD_ID), targetedPlayer.getName(),Component.translatable("spawn."+spawnId.toTranslationKey()), pos.getX(), pos.getY(), pos.getZ());
            context.getSource().sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
        } else {
            MutableComponent sourceText = Component.translatable("command.%s.teleport.player.to.spawn.middle_earth.error".formatted(MiddleEarth.MOD_ID), Component.translatable("spawn."+spawnId.toTranslationKey()));
            context.getSource().sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
        }

        return 0;
    }
    // endregion
}
