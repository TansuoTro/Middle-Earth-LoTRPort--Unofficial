package net.fabricmc.fabric.api.object.builder.v1.block;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

/** Forge compat shim for Fabric's {@code FabricBlockSettings}. */
public class FabricBlockSettings extends BlockBehaviour.Properties {
    public FabricBlockSettings() {
        super();
    }

    /** Fabric's {@code bind(Block)} copies the properties of an existing block. */
    public static BlockBehaviour.Properties bind(BlockBehaviour block) {
        return BlockBehaviour.Properties.copy(block);
    }
}
