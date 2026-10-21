package net.jukoz.me.entity.ai.brain;

import net.minecraft.world.entity.schedule.Activity;
import net.minecraft.core.registries.Registries;
import net.minecraft.core.Registry;
import net.jukoz.me.api.SevenStarsApi;
import net.jukoz.me.MiddleEarth;

public class ActivitiesME {
    public static final Activity TAMED = register("tamed");

    private static Activity register(String id) {
        return Registry.register(Registries.ACTIVITY, id, new Activity(id));
    }

    public static void registerModActivities() {
        SevenStarsApi.LOGGER.logDebugMsg("Registering ModActivities for " + MiddleEarth.MOD_ID);
    }
}
