package net.fabricmc.fabric.api.item.v1;

import net.minecraft.world.item.Item;

/** Forge compat shim for Fabric's {@code FabricItemSettings} (vanilla {@link Item.Properties}). */
public class FabricItemSettings extends Item.Properties {
    public FabricItemSettings() {
        super();
    }

    public FabricItemSettings(FabricItemSettings other) {
        super(other);
    }
}
