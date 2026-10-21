package net.fabricmc.fabric.api.datagen.v1;

import net.minecraft.data.PackOutput;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraft.core.RegistryAccess;

/**
 * Forge compat shim for Fabric's {@code FabricDataOutput}. It only carries the pack output and
 * the Forge {@link GatherDataEvent}, which is all the port's providers need.
 */
public final class FabricDataOutput {
    private final PackOutput output;
    private final GatherDataEvent event;
    private final boolean running;

    public FabricDataOutput(PackOutput output, GatherDataEvent event) {
        this.output = output;
        this.event = event;
        this.running = event != null;
    }

    public PackOutput getOutput() {
        return output;
    }

    public PackOutput getPackOutput() {
        return output;
    }

    /** Fabric's spelling; the port calls {@code output.getItem()} on recipe outputs only. */
    public Object getModContainer() {
        return null;
    }

    public boolean isRunning() {
        return running;
    }

    public GatherDataEvent getEvent() {
        return event;
    }

    public net.minecraft.core.RegistryAccess registryAccess() {
        return net.minecraft.core.RegistryAccess.BUILTIN.get();
    }
}
