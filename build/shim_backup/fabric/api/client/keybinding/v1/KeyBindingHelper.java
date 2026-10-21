package net.fabricmc.fabric.api.client.keybinding.v1;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.jukoz.me.MiddleEarth;

import java.util.ArrayList;
import java.util.List;

/** Forge compat shim for Fabric's {@code KeyBindingHelper}. */
@Mod.EventBusSubscriber(modid = MiddleEarth.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD,
        value = net.minecraftforge.api.distmarker.Dist.CLIENT)
public final class KeyBindingHelper {
    private static final List<KeyMapping> BINDINGS = new ArrayList<>();

    private KeyBindingHelper() {}

    public static KeyMapping registerKeyBinding(KeyMapping binding) {
        BINDINGS.add(binding);
        return binding;
    }

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        BINDINGS.forEach(event::register);
    }
}
