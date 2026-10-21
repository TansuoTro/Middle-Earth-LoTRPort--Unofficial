package net.jukoz.me.commands.custom;

import net.jukoz.me.compat.METext;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.Commands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Component;
import net.jukoz.me.MiddleEarth;
import net.jukoz.me.commands.CommandUtils;
import net.jukoz.me.commands.ModCommands;
import net.jukoz.me.config.ModServerConfigs;
import net.jukoz.me.network.packets.S2C.PacketForceOnboardingScreen;
import net.jukoz.me.resources.persistent_datas.PlayerDataService;
import net.jukoz.me.utils.ModColors;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CommandOnboarding {
    public static String ONBOARDING_BASE_COMMAND = "onboarding";
    private static final String OPEN = "open";
    private static final String TRY = "try";
    private static final String PLAYER = "player";
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandRegistryAccess, Commands.RegistrationEnvironment registrationEnvironment) {
        // [TRY OPEN]
        dispatcher.register(literal(ModCommands.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)) // Require OP
                .then(literal(ONBOARDING_BASE_COMMAND)
                        .then(literal(TRY)
                        .then(literal(OPEN)
                        .then(argument(PLAYER, EntityArgument.player())
                        .executes(CommandOnboarding::tryOpenForTarget)))))
                .then(literal(ONBOARDING_BASE_COMMAND)
                        .then(literal(TRY)
                        .then(literal(OPEN)
                        .executes(CommandOnboarding::tryOpen)))));

        // [OPEN]
        CommandUtils.simpleCommand(dispatcher, ONBOARDING_BASE_COMMAND,
                literal(OPEN).executes(CommandOnboarding::open),
                PLAYER, literal(OPEN).executes(CommandOnboarding::openForTarget));
    }

    private static int open(CommandContext<CommandSourceStack> context) {
        if(context.getSource().isExecutedByPlayer()) {
            ServerPlayer source = context.getSource().getPlayer();
            if(source != null){
                ServerPlayNetworking.send(source, new PacketForceOnboardingScreen(ModServerConfigs.DELAY_ON_TELEPORT_CONFIRMATION, source));
            }
        }
        return 0;
    }

    private static int openForTarget(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer targetPlayer = EntityArgument.getPlayer(context, PLAYER);
        if(targetPlayer != null){
            ServerPlayNetworking.send(targetPlayer, new PacketForceOnboardingScreen(ModServerConfigs.DELAY_ON_TELEPORT_CONFIRMATION, targetPlayer));
        }

        return 0;
    }

    private static int tryOpen(CommandContext<CommandSourceStack> context) {
        if(context.getSource().isExecutedByPlayer()) {
            ServerPlayer playerSource = context.getSource().getPlayer();
            if(playerSource != null){
                boolean playerPassedOnboarding = PlayerDataService.playerPassedOnboarding(playerSource);
                if(playerPassedOnboarding){
                    ServerPlayNetworking.send(playerSource, new PacketForceOnboardingScreen(ModServerConfigs.DELAY_ON_TELEPORT_CONFIRMATION, playerSource));
                } else {
                    MutableComponent sourceText = Component.translatable("command.%s.open.onboarding.error".formatted(MiddleEarth.MOD_ID));
                    playerSource.sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
                }
            }
        }
        return 0;
    }

    private static int tryOpenForTarget(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer playerTarget = EntityArgument.getPlayer(context, PLAYER);
        if(playerTarget != null){
            boolean playerPassedOnboarding = PlayerDataService.playerPassedOnboarding(playerTarget);
            if(playerPassedOnboarding){
                ServerPlayNetworking.send(playerTarget, new PacketForceOnboardingScreen(ModServerConfigs.DELAY_ON_TELEPORT_CONFIRMATION, playerTarget));
                MutableComponent sourceText = Component.translatable("command.%s.open_target.onboarding.success".formatted(MiddleEarth.MOD_ID), playerTarget.getName());
                context.getSource().sendMessage(METext.withColor(sourceText, ModColors.SUCCESS.color));
            } else {
                MutableComponent sourceText = Component.translatable("command.%s.open_target.onboarding.error".formatted(MiddleEarth.MOD_ID), playerTarget.getName());
                context.getSource().sendMessage(METext.withColor(sourceText, ModColors.WARNING.color));
            }
        }

        return 0;
    }
}
