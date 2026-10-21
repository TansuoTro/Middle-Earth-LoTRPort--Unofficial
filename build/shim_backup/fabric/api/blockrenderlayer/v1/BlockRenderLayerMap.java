package net.fabricmc.fabric.api.blockrenderlayer.v1;

import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;

/** Forge compat shim for Fabric's {@code BlockRenderLayerMap}. */
public interface BlockRenderLayerMap {
    BlockRenderLayerMap INSTANCE = new BlockRenderLayerMap() {
        @Override
        public void putBlock(Block block, RenderType layer) {
            ItemBlockRenderTypes.setRenderLayer(block, layer);
        }
    };

    void putBlock(Block block, RenderType layer);
}
