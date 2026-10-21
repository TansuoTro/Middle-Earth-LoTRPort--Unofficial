package net.fabricmc.fabric.api.datagen.v1;

import net.minecraft.data.PackOutput;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.data.DataProvider;

/**
 * Forge compat shim for Fabric's {@code FabricDataGenerator}. It collects the providers the port
 * registers and exposes them so the mod's datagen entry point can hand them to Forge's
 * {@code GatherDataEvent}.
 */
public final class FabricDataGenerator {
    private final PackOutput output;
    private final List<Consumer<FabricDataOutput>> providers = new ArrayList<>();

    public FabricDataGenerator(PackOutput output) {
        this.output = output;
    }

    public Pack createPack() {
        return new Pack();
    }

    public PackOutput getOutput() {
        return this.output;
    }

    public List<Consumer<FabricDataOutput>> getProviders() {
        return providers;
    }

    /** Fabric's {@code Pack}: a group of providers sharing one output. */
    public final class Pack {
        public <T extends net.minecraft.data.DataProvider> void addProvider(
                java.util.function.Function<FabricDataOutput, T> factory) {
            providers.add(output -> {
                T provider = factory.apply(output);
                if (provider instanceof Runnable runnable) {
                    runnable.run();
                }
            });
        }

        public <T extends net.minecraft.data.DataProvider> void addProvider(
                java.util.function.Supplier<T> factory) {
            addProvider(output -> factory.get());
        }
    }
}
