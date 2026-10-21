package net.fabricmc.fabric.api.datagen.v1.provider;

import net.minecraft.data.PackOutput;

/**
 * Forge compat shim for Fabric's {@code FabricModelProvider}. Fabric's variant splits model
 * generation into block-state and item generators; vanilla 1.20.1 exposes the same through
 * {@code ModelProvider} plus {@code BlockModelGenerators} / {@code ItemModelGenerators}.
 */
public abstract class FabricModelProvider {
    private final PackOutput output;

    protected FabricModelProvider(net.fabricmc.fabric.api.datagen.v1.FabricDataOutput output) {
        this.output = output.getOutput();
    }

    protected FabricModelProvider(PackOutput output) {
        this.output = output;
    }

    /** Fabric's block-state entry point; ported providers override this. */
    public abstract void generateBlockStateModels(BlockModelGenerators generator);

    /** Fabric's item entry point; ported providers override this. */
    public abstract void generateItemModels(ItemModelGenerators generator);

    public final PackOutput getPackOutput() {
        return output;
    }

    /** Placeholder for Fabric's {@code BlockModelGenerators}; the real run is wired separately. */
    public static final class BlockModelGenerators {
    }

    /** Placeholder for Fabric's {@code ItemModelGenerators}; the real run is wired separately. */
    public static final class ItemModelGenerators {
    }
}
