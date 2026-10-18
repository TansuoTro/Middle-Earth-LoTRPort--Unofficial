package net.jukoz.me.api.entity.ai.brain;

import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.jukoz.me.api.SevenStarsApi;

import java.util.function.Supplier;

public class SensorsAPI {

    private static <U extends Sensor<?>> SensorType<U> register(String id, Supplier<U> factory) {
        return Registry.register(Registries.SENSOR_TYPE, new Identifier(SevenStarsApi.MOD_ID, id), new SensorType<>(factory));
    }

    public static void registerModSensors() {
        SevenStarsApi.LOGGER.logDebugMsg("Registering Mod Sensors for " + SevenStarsApi.MOD_ID);
    }
}
