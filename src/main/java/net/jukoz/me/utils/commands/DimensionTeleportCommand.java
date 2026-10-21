package net.jukoz.me.utils.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.jukoz.me.world.dimension.ModDimensions;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.Commands;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3i;
import java.util.Objects;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.Coordinates;
import net.minecraft.commands.arguments.coordinates.Vec3Argument;

public class DimensionTeleportCommand {
    private static final String TP = "tp";
    private static final String PLAYER = "player";
    private static final String LOCATION = "location";
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext commandRegistryAccess, Commands.RegistrationEnvironment registrationEnvironment) {
        dispatcher.register((Commands.literal(ModCommandRegistry.BASE_COMMAND)
                .requires(source -> source.hasPermissionLevel(2)))
                        .then((Commands.literal(TP))
                            .then(Commands.argument(PLAYER, EntityArgument.player())
                                .then(Commands.argument(LOCATION, Vec3Argument.vec3())
                                        .executes(DimensionTeleportCommand::run)))));
    }

    public static int run(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        try{
            Coordinates posArgument = Vec3Argument.getPosArgument(context, LOCATION);
            Vec3 coordinatesInDouble = posArgument.toAbsolutePos(context.getSource());

            Vector3i coordinates = new Vector3i((int) coordinatesInDouble.x, (int) coordinatesInDouble.y, (int) coordinatesInDouble.z);
            ServerPlayer targettedPlayer = EntityArgument.getPlayer(context, PLAYER);

            ModDimensions.teleportPlayerToMe(targettedPlayer, coordinates);

            return 1;
        } catch (Exception e){
            return 0;
        }
    }
}
