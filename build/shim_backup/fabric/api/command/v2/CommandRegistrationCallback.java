package net.fabricmc.fabric.api.command.v2;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.jukoz.me.MiddleEarth;

import java.util.ArrayList;
import java.util.List;

/** Forge compat shim for Fabric's {@code CommandRegistrationCallback}. */
@Mod.EventBusSubscriber(modid = MiddleEarth.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class CommandRegistrationCallback {
    /** Fabric's handler shape: {@code (dispatcher, registry, environment) -> ...}. */
    public interface Handler {
        void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext registries,
                      RegistrationEnvironment environment);
    }

    public enum RegistrationEnvironment {
        DEDICATED_SERVER, INTEGRATED_SERVER
    }

    public static final class Event {
        private final List<Handler> listeners = new ArrayList<>();

        public void register(Handler listener) {
            listeners.add(listener);
        }
    }

    public static final Event EVENT = new Event();

    private CommandRegistrationCallback() {}

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        RegistrationEnvironment environment = event.getCommandSelection() == net.minecraft.commands.Commands.CommandSelection.DEDICATED
                ? RegistrationEnvironment.DEDICATED_SERVER
                : RegistrationEnvironment.INTEGRATED_SERVER;
        for (Handler listener : EVENT.listeners) {
            listener.register(event.getDispatcher(), event.getBuildContext(), environment);
        }
    }
}
