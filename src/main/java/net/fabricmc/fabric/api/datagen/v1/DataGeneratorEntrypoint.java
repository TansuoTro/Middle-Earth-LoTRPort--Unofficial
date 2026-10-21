package net.fabricmc.fabric.api.datagen.v1;

/** Forge compat marker for Fabric's datagen entry point. */
public interface DataGeneratorEntrypoint {
    void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator);

    default String getEffectiveModId() {
        return "me";
    }
}
