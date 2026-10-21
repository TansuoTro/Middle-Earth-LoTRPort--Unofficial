package net.jukoz.me.sound;

import net.jukoz.me.MiddleEarth;
import net.jukoz.me.utils.LoggerUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;

public class ModSounds {
    public static SoundEvent NAZGUL_FADE = registerSoundEvent("nazgul_fade");
    public static SoundEvent NAZGUL_SCREAM = registerSoundEvent("nazgul_scream");

    private static SoundEvent registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(MiddleEarth.MOD_ID, name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void registerModSounds() {
        LoggerUtil.getInstance().logDebugMsg("Registering Mod Entities for " + MiddleEarth.MOD_ID);
    }
}
